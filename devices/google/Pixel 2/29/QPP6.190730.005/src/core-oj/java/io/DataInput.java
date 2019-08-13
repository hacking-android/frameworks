/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;

public interface DataInput {
    public boolean readBoolean() throws IOException;

    public byte readByte() throws IOException;

    public char readChar() throws IOException;

    public double readDouble() throws IOException;

    public float readFloat() throws IOException;

    public void readFully(byte[] var1) throws IOException;

    public void readFully(byte[] var1, int var2, int var3) throws IOException;

    public int readInt() throws IOException;

    public String readLine() throws IOException;

    public long readLong() throws IOException;

    public short readShort() throws IOException;

    public String readUTF() throws IOException;

    public int readUnsignedByte() throws IOException;

    public int readUnsignedShort() throws IOException;

    public int skipBytes(int var1) throws IOException;
}

