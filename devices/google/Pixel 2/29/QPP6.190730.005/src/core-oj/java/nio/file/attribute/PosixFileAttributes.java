/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.Set;

public interface PosixFileAttributes
extends BasicFileAttributes {
    public GroupPrincipal group();

    public UserPrincipal owner();

    public Set<PosixFilePermission> permissions();
}

