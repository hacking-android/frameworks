/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.attribute.FileAttributeView;
import java.util.List;

public interface UserDefinedFileAttributeView
extends FileAttributeView {
    public void delete(String var1) throws IOException;

    public List<String> list() throws IOException;

    @Override
    public String name();

    public int read(String var1, ByteBuffer var2) throws IOException;

    public int size(String var1) throws IOException;

    public int write(String var1, ByteBuffer var2) throws IOException;
}

