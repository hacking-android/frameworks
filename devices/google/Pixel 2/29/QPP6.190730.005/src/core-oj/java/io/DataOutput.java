/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.IOException;

public interface DataOutput {
    public void write(int var1) throws IOException;

    public void write(byte[] var1) throws IOException;

    public void write(byte[] var1, int var2, int var3) throws IOException;

    public void writeBoolean(boolean var1) throws IOException;

    public void writeByte(int var1) throws IOException;

    public void writeBytes(String var1) throws IOException;

    public void writeChar(int var1) throws IOException;

    public void writeChars(String var1) throws IOException;

    public void writeDouble(double var1) throws IOException;

    public void writeFloat(float var1) throws IOException;

    public void writeInt(int var1) throws IOException;

    public void writeLong(long var1) throws IOException;

    public void writeShort(int var1) throws IOException;

    public void writeUTF(String var1) throws IOException;
}

