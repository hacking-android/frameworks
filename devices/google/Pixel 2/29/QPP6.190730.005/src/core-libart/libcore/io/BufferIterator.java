/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import dalvik.annotation.compat.UnsupportedAppUsage;

public abstract class BufferIterator {
    public abstract int pos();

    @UnsupportedAppUsage
    public abstract byte readByte();

    @UnsupportedAppUsage
    public abstract void readByteArray(byte[] var1, int var2, int var3);

    @UnsupportedAppUsage
    public abstract int readInt();

    @UnsupportedAppUsage
    public abstract void readIntArray(int[] var1, int var2, int var3);

    public abstract short readShort();

    @UnsupportedAppUsage
    public abstract void seek(int var1);

    @UnsupportedAppUsage
    public abstract void skip(int var1);
}

