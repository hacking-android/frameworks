/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.DataInput;
import java.io.IOException;

public interface ObjectInput
extends DataInput,
AutoCloseable {
    public int available() throws IOException;

    @Override
    public void close() throws IOException;

    public int read() throws IOException;

    public int read(byte[] var1) throws IOException;

    public int read(byte[] var1, int var2, int var3) throws IOException;

    public Object readObject() throws ClassNotFoundException, IOException;

    public long skip(long var1) throws IOException;
}

