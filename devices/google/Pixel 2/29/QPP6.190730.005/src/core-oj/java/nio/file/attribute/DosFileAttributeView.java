/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;

public interface DosFileAttributeView
extends BasicFileAttributeView {
    @Override
    public String name();

    @Override
    public DosFileAttributes readAttributes() throws IOException;

    public void setArchive(boolean var1) throws IOException;

    public void setHidden(boolean var1) throws IOException;

    public void setReadOnly(boolean var1) throws IOException;

    public void setSystem(boolean var1) throws IOException;
}

