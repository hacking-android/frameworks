/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.nio.file.attribute.FileTime;

public interface BasicFileAttributes {
    public FileTime creationTime();

    public Object fileKey();

    public boolean isDirectory();

    public boolean isOther();

    public boolean isRegularFile();

    public boolean isSymbolicLink();

    public FileTime lastAccessTime();

    public FileTime lastModifiedTime();

    public long size();
}

