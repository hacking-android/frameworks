#!/usr/bin/env python3

import requests
import itertools
import os
from io import BytesIO
from zipfile import ZipFile

DL_API_NAMES = {
     7: "2.1",
     8: "2.2",
     9: "2.3.1",
    10: "2.3.3",
    11: "3.0",
    12: "3.1",
    13: "3.2",
}

missing_apis = 0
for api in itertools.count(start=7):
    latest_url = None

    api_name = DL_API_NAMES.get(api, api)
    url_patterns = [
        "https://dl.google.com/android/repository/platform-{api}_r{release:02}.zip",
        "https://dl.google.com/android/repository/android-{api}_r{release:02}.zip"
    ]

    missing_releases = 0
    for release in itertools.count(start=1):
        release_found = False
        for url_pattern in url_patterns:
            url = url_pattern.format(api=DL_API_NAMES.get(api, api), release=release)
            response = requests.head(url)
            #print(url, response.status_code)
            if response.status_code == 200:
                release_found = True
                latest_url = url
        if not release_found:
            missing_releases += 1
            if missing_releases >= 10:
                break


    if latest_url is None:
        missing_apis += 1
        if missing_apis >= 5:
            break
    else:
        print(f"Fetching API {api} from {latest_url}")
        response = BytesIO(requests.get(latest_url).content)
        zipfile = ZipFile(response)
        androidjar_zipname = [
            name
            for name in zipfile.namelist()
            if name.endswith("/android.jar")
        ]
        if len(androidjar_zipname) != 1:
            raise ValueError("More than 1 android.jar file")

        androidjar_file = f"apis/{api}/android.jar"
        os.makedirs(os.path.dirname(androidjar_file), exist_ok=True)
        with open(androidjar_file, "wb") as f:
            f.write(zipfile.read(androidjar_zipname[0]))
        print(f"Saved API {api}'s android.jar to {androidjar_file}")

