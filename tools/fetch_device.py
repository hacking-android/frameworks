#!/usr/bin/env python3

import re
import os
import time
import yaml
import logging
import subprocess
from shlex import quote
import shutil
import tempfile
from git import Repo
from github import Github
from markdown_strings import esc_format as escape_markdown

TOOLS_DIR = os.path.dirname(os.path.abspath(__file__))
PROJECT_DIR = os.path.dirname(TOOLS_DIR)

logging.basicConfig(level=logging.INFO)
main_log = logging.getLogger('extract_jars')
subprocess_log = logging.getLogger('subprocess')
subprocess_stdout_log = subprocess_log.getChild('stdout')
subprocess_stderr_log = subprocess_log.getChild('stderr')

def run(msg, *cmd, quiet=False, splitlines=False):
    if not quiet:
        main_log.info(msg)
        subprocess_log.debug(f"${' '.join(quote(x) for x in cmd)}")

    try:
        completed_process = subprocess.run(
            cmd,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            check=True, encoding='utf8'
        )
    except subprocess.CalledProcessError as error:
        subprocess_log.error(f"Failed to execute {' '.join(quote(x) for x in cmd)}: Exit code {error.returncode}")
        for line in error.stdout.splitlines():
            subprocess_stdout_log.error(f' | {line}')
        for line in error.stderr.splitlines():
            subprocess_stderr_log.error(f' | {line}')
        raise

    if not quiet:
        for line in completed_process.stdout.splitlines():
            subprocess_stdout_log.debug(f' | {line}')
        for line in completed_process.stderr.splitlines():
            subprocess_stderr_log.debug(f' | {line}')
    if splitlines:
        return completed_process.stdout.splitlines()
    else:
        return completed_process.stdout

def get_bootclasspath():
    return run(
        "Finding device's bootclasspath",
        "adb", "shell", "echo", "$BOOTCLASSPATH"
    ).strip().split(':')

def get_device_props():
    blob = run(
        "Finding device's properties",
        "adb", "shell", "getprop"
    )
    return {
        match.group(1): match.group(2)
        for match in re.finditer(r"\[([^\]]*)\]: \[([^\]]*)\]\n", blob, re.MULTILINE | re.DOTALL)
    }

def get_file(remote_file, local_file):
    run(
        f"Fetching {remote_file} -> {local_file}",
        "adb", "pull", remote_file, local_file)

def undex(src, dest):
    run(
        f"Undexing {src}",
        f"{TOOLS_DIR}/dex-tools-2.1-SNAPSHOT/d2j-dex2jar.sh", "--exception-file", "/dev/null", "-f", src, "--output", dest)

def decompile(src, dest):
    run(
        f"Decompiling {src} -> {dest}",
        "java", "-jar", f"{TOOLS_DIR}/cfr-0.145.jar", src, "--outputdir", dest)

device_props = get_device_props()
dev_manufacturer = device_props['ro.product.manufacturer']
dev_brand = device_props['ro.product.brand']
dev_product = device_props['ro.product.name']
dev_model = device_props['ro.product.model']
dev_device = device_props['ro.product.device']
dev_board = device_props['ro.product.board']
dev_build = device_props['ro.build.display.id']
dev_build_id = device_props['ro.build.id']
dev_sdk = int(device_props['ro.build.version.sdk'])
dev_preview_sdk = int(device_props['ro.build.version.preview_sdk'])
api = dev_sdk if dev_preview_sdk == 0 else dev_sdk + 1

def extract(root_dir=PROJECT_DIR):

    # Create output directory
    dest_dir = f"{root_dir}/devices/{dev_brand}/{dev_model}/{api}/{dev_build}"
    shutil.rmtree(dest_dir, ignore_errors=True)
    os.makedirs(dest_dir)
    os.makedirs(f"{dest_dir}/dex")
    os.makedirs(f"{dest_dir}/jar")
    os.makedirs(f"{dest_dir}/src")

    # Create link in API directory
    link = f"{root_dir}/apis/{api}/devices/{dev_brand}/{dev_model}/{dev_build}"
    os.makedirs(os.path.dirname(link), exist_ok=True)
    os.symlink(os.path.relpath(dest_dir, os.path.dirname(link)), link)

    # Write device info
    with open(os.path.join(dest_dir, "device_props.yaml"), "w") as f:
        f.write(yaml.dump(device_props, default_flow_style=False, width=200))

    # Extract each jar file in the boothclasspath
    for remote_file in get_bootclasspath():
        # FIXME: Some devices have empty jars in /system/frameworks -- We have to extract from OAT/vdex instead
        name =  os.path.splitext(os.path.basename(remote_file))[0]
        local_dex_file = f"{dest_dir}/dex/{name}.jar"
        local_class_file = f"{dest_dir}/jar/{name}.jar"
        local_src_dir = f"{dest_dir}/src/{name}"
        get_file(remote_file, local_dex_file)
        undex(local_dex_file, local_class_file)
        decompile(local_class_file, local_src_dir)

def extract_to_pr():
    github = Github("ed5fb881c25e4266ef90527c48b7485a53c998fd")
    github_repo = github.get_repo("hacking-android/frameworks")

    with tempfile.TemporaryDirectory(prefix="frameworks") as git_dir:
        repo = Repo.init(git_dir)
        origin = repo.create_remote('origin', "git@github.com:hacking-android/frameworks.git")
        origin.fetch("device_PR")

        branch_name = f"device={dev_model}/api={api}/build={dev_build}"
        branch_name = re.sub(r"[^a-zA-Z0-9/=]", "", branch_name)
        print(f"branch={branch_name}")
        branch = repo.create_head(branch_name, origin.refs.device_PR)
        branch.set_tracking_branch(origin.refs.device_PR)
        branch.checkout()

        extract(git_dir)

        repo.index.add("*")
        repo.index.commit(f"Added device libraries for {dev_model}, api={api}, build={dev_build}")
        origin.push(branch);

        pr = github_repo.create_pull(
            head=branch_name,
            base="master",
            title=f"Device Libraries: {dev_model}, api={api}, build={dev_build}",
            body=f"""
| Field                       | value |
| ---                         | --- |
| Manufaturer                 | {escape_markdown(dev_manufacturer)} |
| Brand                       | {escape_markdown(dev_brand)} |
| Product                     | {escape_markdown(dev_product)} |
| Device                      | {escape_markdown(dev_device)} |
| Board                       | {escape_markdown(dev_board)} |
| Build                       | {escape_markdown(dev_build)} |
| Build ID                    | {escape_markdown(dev_build_id)} |
| Version/SDK                 | {str(api) + (' (Preview ' + str(dev_preview_sdk) + ')' if dev_preview_sdk != 0 else '')} |
| Version/Release             | {escape_markdown(device_props['ro.build.version.release'])} |
| Version/Incremental         | {escape_markdown(device_props['ro.build.version.incremental'])} |
| Version/Security Patch      | {escape_markdown(device_props['ro.build.version.security_patch'])} |
""",
        )
        print(f"Create PR: {pr.html_url}")


extract()
