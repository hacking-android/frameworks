/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.annotation.optimization.FastNative;
import java.nio.ByteOrder;

public final class Memory {
    private Memory() {
    }

    public static native void memmove(Object var0, int var1, Object var2, int var3, long var4);

    @UnsupportedAppUsage
    @FastNative
    public static native byte peekByte(long var0);

    @UnsupportedAppUsage
    public static native void peekByteArray(long var0, byte[] var2, int var3, int var4);

    public static native void peekCharArray(long var0, char[] var2, int var3, int var4, boolean var5);

    public static native void peekDoubleArray(long var0, double[] var2, int var3, int var4, boolean var5);

    public static native void peekFloatArray(long var0, float[] var2, int var3, int var4, boolean var5);

    @UnsupportedAppUsage
    public static int peekInt(long l, boolean bl) {
        int n;
        int n2 = n = Memory.peekIntNative(l);
        if (bl) {
            n2 = Integer.reverseBytes(n);
        }
        return n2;
    }

    public static int peekInt(byte[] arrby, int n, ByteOrder byteOrder) {
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            int n2 = n + 1;
            byte by = arrby[n];
            n = n2 + 1;
            return (by & 255) << 24 | (arrby[n2] & 255) << 16 | (arrby[n] & 255) << 8 | (arrby[n + 1] & 255) << 0;
        }
        int n3 = n + 1;
        n = arrby[n];
        int n4 = n3 + 1;
        return (n & 255) << 0 | (arrby[n3] & 255) << 8 | (arrby[n4] & 255) << 16 | (arrby[n4 + 1] & 255) << 24;
    }

    public static native void peekIntArray(long var0, int[] var2, int var3, int var4, boolean var5);

    @FastNative
    private static native int peekIntNative(long var0);

    @UnsupportedAppUsage
    public static long peekLong(long l, boolean bl) {
        long l2;
        l = l2 = Memory.peekLongNative(l);
        if (bl) {
            l = Long.reverseBytes(l2);
        }
        return l;
    }

    public static long peekLong(byte[] arrby, int n, ByteOrder byteOrder) {
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            int n2 = n + 1;
            n = arrby[n];
            int n3 = n2 + 1;
            n2 = arrby[n2];
            int n4 = n3 + 1;
            n3 = arrby[n3];
            int n5 = n4 + 1;
            n4 = arrby[n4];
            int n6 = n5 + 1;
            n5 = arrby[n5];
            int n7 = n6 + 1;
            n6 = arrby[n6];
            byte by = arrby[n7];
            n7 = arrby[n7 + 1];
            long l = (n & 255) << 24 | (n2 & 255) << 16 | (n3 & 255) << 8 | (n4 & 255) << 0;
            return 0xFFFFFFFFL & (long)((n6 & 255) << 16 | (n5 & 255) << 24 | (by & 255) << 8 | (n7 & 255) << 0) | l << 32;
        }
        int n8 = n + 1;
        n = arrby[n];
        int n9 = n8 + 1;
        n8 = arrby[n8];
        int n10 = n9 + 1;
        n9 = arrby[n9];
        int n11 = n10 + 1;
        byte by = arrby[n10];
        n10 = n11 + 1;
        n11 = arrby[n11];
        int n12 = n10 + 1;
        long l = (arrby[n10] & 255) << 8 | (n11 & 255) << 0 | (arrby[n12] & 255) << 16 | (arrby[n12 + 1] & 255) << 24;
        return 0xFFFFFFFFL & (long)((n & 255) << 0 | (n8 & 255) << 8 | (n9 & 255) << 16 | (by & 255) << 24) | l << 32;
    }

    public static native void peekLongArray(long var0, long[] var2, int var3, int var4, boolean var5);

    @FastNative
    private static native long peekLongNative(long var0);

    public static short peekShort(long l, boolean bl) {
        short s;
        short s2 = s = Memory.peekShortNative(l);
        if (bl) {
            short s3;
            s2 = s3 = Short.reverseBytes(s);
        }
        return s2;
    }

    public static short peekShort(byte[] arrby, int n, ByteOrder byteOrder) {
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            return (short)(arrby[n] << 8 | arrby[n + 1] & 255);
        }
        return (short)(arrby[n + 1] << 8 | arrby[n] & 255);
    }

    public static native void peekShortArray(long var0, short[] var2, int var3, int var4, boolean var5);

    @FastNative
    private static native short peekShortNative(long var0);

    @UnsupportedAppUsage
    @FastNative
    public static native void pokeByte(long var0, byte var2);

    @UnsupportedAppUsage
    public static native void pokeByteArray(long var0, byte[] var2, int var3, int var4);

    public static native void pokeCharArray(long var0, char[] var2, int var3, int var4, boolean var5);

    public static native void pokeDoubleArray(long var0, double[] var2, int var3, int var4, boolean var5);

    public static native void pokeFloatArray(long var0, float[] var2, int var3, int var4, boolean var5);

    @UnsupportedAppUsage
    public static void pokeInt(long l, int n, boolean bl) {
        int n2 = n;
        if (bl) {
            n2 = Integer.reverseBytes(n);
        }
        Memory.pokeIntNative(l, n2);
    }

    public static void pokeInt(byte[] arrby, int n, int n2, ByteOrder byteOrder) {
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            int n3 = n + 1;
            arrby[n] = (byte)(n2 >> 24 & 255);
            n = n3 + 1;
            arrby[n3] = (byte)(n2 >> 16 & 255);
            arrby[n] = (byte)(n2 >> 8 & 255);
            arrby[n + 1] = (byte)(n2 >> 0 & 255);
        } else {
            int n4 = n + 1;
            arrby[n] = (byte)(n2 >> 0 & 255);
            n = n4 + 1;
            arrby[n4] = (byte)(n2 >> 8 & 255);
            arrby[n] = (byte)(n2 >> 16 & 255);
            arrby[n + 1] = (byte)(n2 >> 24 & 255);
        }
    }

    public static native void pokeIntArray(long var0, int[] var2, int var3, int var4, boolean var5);

    @FastNative
    private static native void pokeIntNative(long var0, int var2);

    @UnsupportedAppUsage
    public static void pokeLong(long l, long l2, boolean bl) {
        long l3 = l2;
        if (bl) {
            l3 = Long.reverseBytes(l2);
        }
        Memory.pokeLongNative(l, l3);
    }

    public static void pokeLong(byte[] arrby, int n, long l, ByteOrder byteOrder) {
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            int n2 = (int)(l >> 32);
            int n3 = n + 1;
            arrby[n] = (byte)(n2 >> 24 & 255);
            n = n3 + 1;
            arrby[n3] = (byte)(n2 >> 16 & 255);
            n3 = n + 1;
            arrby[n] = (byte)(n2 >> 8 & 255);
            n = n3 + 1;
            arrby[n3] = (byte)(n2 >> 0 & 255);
            n3 = (int)l;
            n2 = n + 1;
            arrby[n] = (byte)(n3 >> 24 & 255);
            n = n2 + 1;
            arrby[n2] = (byte)(n3 >> 16 & 255);
            arrby[n] = (byte)(n3 >> 8 & 255);
            arrby[n + 1] = (byte)(n3 >> 0 & 255);
        } else {
            int n4 = (int)l;
            int n5 = n + 1;
            arrby[n] = (byte)(n4 >> 0 & 255);
            n = n5 + 1;
            arrby[n5] = (byte)(n4 >> 8 & 255);
            n5 = n + 1;
            arrby[n] = (byte)(n4 >> 16 & 255);
            n = n5 + 1;
            arrby[n5] = (byte)(n4 >> 24 & 255);
            n4 = (int)(l >> 32);
            n5 = n + 1;
            arrby[n] = (byte)(n4 >> 0 & 255);
            n = n5 + 1;
            arrby[n5] = (byte)(n4 >> 8 & 255);
            arrby[n] = (byte)(n4 >> 16 & 255);
            arrby[n + 1] = (byte)(n4 >> 24 & 255);
        }
    }

    public static native void pokeLongArray(long var0, long[] var2, int var3, int var4, boolean var5);

    @FastNative
    private static native void pokeLongNative(long var0, long var2);

    public static void pokeShort(long l, short s, boolean bl) {
        short s2 = s;
        if (bl) {
            short s3;
            s2 = s3 = Short.reverseBytes(s);
        }
        Memory.pokeShortNative(l, s2);
    }

    public static void pokeShort(byte[] arrby, int n, short s, ByteOrder byteOrder) {
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            arrby[n] = (byte)(s >> 8 & 255);
            arrby[n + 1] = (byte)(s >> 0 & 255);
        } else {
            arrby[n] = (byte)(s >> 0 & 255);
            arrby[n + 1] = (byte)(s >> 8 & 255);
        }
    }

    public static native void pokeShortArray(long var0, short[] var2, int var3, int var4, boolean var5);

    @FastNative
    private static native void pokeShortNative(long var0, short var2);

    public static native void unsafeBulkGet(Object var0, int var1, int var2, byte[] var3, int var4, int var5, boolean var6);

    public static native void unsafeBulkPut(byte[] var0, int var1, int var2, Object var3, int var4, int var5, boolean var6);
}

