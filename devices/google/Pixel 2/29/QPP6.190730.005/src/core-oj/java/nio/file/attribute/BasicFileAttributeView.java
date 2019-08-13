/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;

public interface BasicFileAttributeView
extends FileAttributeView {
    @Override
    public String name();

    public BasicFileAttributes readAttributes() throws IOException;

    public void setTimes(FileTime var1, FileTime var2, FileTime var3) throws IOException;
}

