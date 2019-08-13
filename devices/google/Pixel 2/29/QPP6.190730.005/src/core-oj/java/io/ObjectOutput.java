/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.DataOutput;
import java.io.IOException;

public interface ObjectOutput
extends DataOutput,
AutoCloseable {
    @Override
    public void close() throws IOException;

    public void flush() throws IOException;

    @Override
    public void write(int var1) throws IOException;

    @Override
    public void write(byte[] var1) throws IOException;

    @Override
    public void write(byte[] var1, int var2, int var3) throws IOException;

    public void writeObject(Object var1) throws IOException;
}

