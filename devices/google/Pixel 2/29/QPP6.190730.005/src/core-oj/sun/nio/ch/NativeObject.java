/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.nio.ByteOrder;
import sun.misc.Unsafe;

class NativeObject {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static ByteOrder byteOrder;
    private static int pageSize;
    protected static final Unsafe unsafe;
    private final long address;
    protected long allocationAddress;

    static {
        unsafe = Unsafe.getUnsafe();
        byteOrder = null;
        pageSize = -1;
    }

    protected NativeObject(int n, boolean bl) {
        if (!bl) {
            this.address = this.allocationAddress = unsafe.allocateMemory(n);
        } else {
            long l;
            int n2 = NativeObject.pageSize();
            this.allocationAddress = l = unsafe.allocateMemory(n + n2);
            this.address = (long)n2 + l - ((long)(n2 - 1) & l);
        }
    }

    NativeObject(long l) {
        this.allocationAddress = l;
        this.address = l;
    }

    NativeObject(long l, long l2) {
        this.allocationAddress = l;
        this.address = l + l2;
    }

    static int addressSize() {
        return unsafe.addressSize();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static ByteOrder byteOrder() {
        ByteOrder byteOrder = NativeObject.byteOrder;
        if (byteOrder != null) {
            return byteOrder;
        }
        long l = unsafe.allocateMemory(8L);
        try {
            unsafe.putLong(l, 72623859790382856L);
            byte by = unsafe.getByte(l);
            if (by != 1) {
                if (by != 8) {
                    return NativeObject.byteOrder;
                }
                NativeObject.byteOrder = ByteOrder.LITTLE_ENDIAN;
                return NativeObject.byteOrder;
            }
            NativeObject.byteOrder = ByteOrder.BIG_ENDIAN;
            return NativeObject.byteOrder;
        }
        finally {
            unsafe.freeMemory(l);
        }
    }

    static int pageSize() {
        if (pageSize == -1) {
            pageSize = unsafe.pageSize();
        }
        return pageSize;
    }

    long address() {
        return this.address;
    }

    long allocationAddress() {
        return this.allocationAddress;
    }

    final byte getByte(int n) {
        return unsafe.getByte((long)n + this.address);
    }

    final char getChar(int n) {
        return unsafe.getChar((long)n + this.address);
    }

    final double getDouble(int n) {
        return unsafe.getDouble((long)n + this.address);
    }

    final float getFloat(int n) {
        return unsafe.getFloat((long)n + this.address);
    }

    final int getInt(int n) {
        return unsafe.getInt((long)n + this.address);
    }

    final long getLong(int n) {
        return unsafe.getLong((long)n + this.address);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    NativeObject getObject(int n) {
        long l;
        int n2 = NativeObject.addressSize();
        if (n2 != 4) {
            if (n2 != 8) throw new InternalError("Address size not supported");
            l = unsafe.getLong((long)n + this.address);
            return new NativeObject(l);
        } else {
            l = unsafe.getInt((long)n + this.address) & -1;
        }
        return new NativeObject(l);
    }

    final short getShort(int n) {
        return unsafe.getShort((long)n + this.address);
    }

    final void putByte(int n, byte by) {
        unsafe.putByte((long)n + this.address, by);
    }

    final void putChar(int n, char c) {
        unsafe.putChar((long)n + this.address, c);
    }

    final void putDouble(int n, double d) {
        unsafe.putDouble((long)n + this.address, d);
    }

    final void putFloat(int n, float f) {
        unsafe.putFloat((long)n + this.address, f);
    }

    final void putInt(int n, int n2) {
        unsafe.putInt((long)n + this.address, n2);
    }

    final void putLong(int n, long l) {
        unsafe.putLong((long)n + this.address, l);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void putObject(int n, NativeObject nativeObject) {
        int n2 = NativeObject.addressSize();
        if (n2 != 4) {
            if (n2 != 8) throw new InternalError("Address size not supported");
            this.putLong(n, nativeObject.address);
            return;
        } else {
            this.putInt(n, (int)(nativeObject.address & -1L));
        }
    }

    final void putShort(int n, short s) {
        unsafe.putShort((long)n + this.address, s);
    }

    NativeObject subObject(int n) {
        return new NativeObject((long)n + this.address);
    }
}

