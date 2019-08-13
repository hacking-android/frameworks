/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

public interface PosixFileAttributeView
extends BasicFileAttributeView,
FileOwnerAttributeView {
    @Override
    public String name();

    @Override
    public PosixFileAttributes readAttributes() throws IOException;

    public void setGroup(GroupPrincipal var1) throws IOException;

    public void setPermissions(Set<PosixFilePermission> var1) throws IOException;
}

