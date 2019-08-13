/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.util;

public abstract class Pack {
    public static int bigEndianToInt(byte[] arrby, int n) {
        byte by = arrby[n];
        int n2 = n + 1;
        n = arrby[n2];
        return by << 24 | (n & 255) << 16 | (arrby[++n2] & 255) << 8 | arrby[n2 + 1] & 255;
    }

    public static void bigEndianToInt(byte[] arrby, int n, int[] arrn) {
        for (int i = 0; i < arrn.length; ++i) {
            arrn[i] = Pack.bigEndianToInt(arrby, n);
            n += 4;
        }
    }

    public static long bigEndianToLong(byte[] arrby, int n) {
        int n2 = Pack.bigEndianToInt(arrby, n);
        n = Pack.bigEndianToInt(arrby, n + 4);
        return ((long)n2 & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL & (long)n;
    }

    public static void bigEndianToLong(byte[] arrby, int n, long[] arrl) {
        for (int i = 0; i < arrl.length; ++i) {
            arrl[i] = Pack.bigEndianToLong(arrby, n);
            n += 8;
        }
    }

    public static void intToBigEndian(int n, byte[] arrby, int n2) {
        arrby[n2] = (byte)(n >>> 24);
        arrby[++n2] = (byte)(n >>> 16);
        arrby[++n2] = (byte)(n >>> 8);
        arrby[n2 + 1] = (byte)n;
    }

    public static void intToBigEndian(int[] arrn, byte[] arrby, int n) {
        for (int i = 0; i < arrn.length; ++i) {
            Pack.intToBigEndian(arrn[i], arrby, n);
            n += 4;
        }
    }

    public static byte[] intToBigEndian(int n) {
        byte[] arrby = new byte[4];
        Pack.intToBigEndian(n, arrby, 0);
        return arrby;
    }

    public static byte[] intToBigEndian(int[] arrn) {
        byte[] arrby = new byte[arrn.length * 4];
        Pack.intToBigEndian(arrn, arrby, 0);
        return arrby;
    }

    public static void intToLittleEndian(int n, byte[] arrby, int n2) {
        arrby[n2] = (byte)n;
        arrby[++n2] = (byte)(n >>> 8);
        arrby[++n2] = (byte)(n >>> 16);
        arrby[n2 + 1] = (byte)(n >>> 24);
    }

    public static void intToLittleEndian(int[] arrn, byte[] arrby, int n) {
        int n2 = 0;
        int n3 = n;
        for (n = n2; n < arrn.length; ++n) {
            Pack.intToLittleEndian(arrn[n], arrby, n3);
            n3 += 4;
        }
    }

    public static byte[] intToLittleEndian(int n) {
        byte[] arrby = new byte[4];
        Pack.intToLittleEndian(n, arrby, 0);
        return arrby;
    }

    public static byte[] intToLittleEndian(int[] arrn) {
        byte[] arrby = new byte[arrn.length * 4];
        Pack.intToLittleEndian(arrn, arrby, 0);
        return arrby;
    }

    public static int littleEndianToInt(byte[] arrby, int n) {
        byte by = arrby[n];
        int n2 = n + 1;
        n = arrby[n2];
        return by & 255 | (n & 255) << 8 | (arrby[++n2] & 255) << 16 | arrby[n2 + 1] << 24;
    }

    public static void littleEndianToInt(byte[] arrby, int n, int[] arrn) {
        int n2 = 0;
        int n3 = n;
        for (n = n2; n < arrn.length; ++n) {
            arrn[n] = Pack.littleEndianToInt(arrby, n3);
            n3 += 4;
        }
    }

    public static void littleEndianToInt(byte[] arrby, int n, int[] arrn, int n2, int n3) {
        int n4 = 0;
        int n5 = n;
        for (n = n4; n < n3; ++n) {
            arrn[n2 + n] = Pack.littleEndianToInt(arrby, n5);
            n5 += 4;
        }
    }

    public static long littleEndianToLong(byte[] arrby, int n) {
        int n2 = Pack.littleEndianToInt(arrby, n);
        return ((long)Pack.littleEndianToInt(arrby, n + 4) & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL & (long)n2;
    }

    public static void littleEndianToLong(byte[] arrby, int n, long[] arrl) {
        for (int i = 0; i < arrl.length; ++i) {
            arrl[i] = Pack.littleEndianToLong(arrby, n);
            n += 8;
        }
    }

    public static void longToBigEndian(long l, byte[] arrby, int n) {
        Pack.intToBigEndian((int)(l >>> 32), arrby, n);
        Pack.intToBigEndian((int)(0xFFFFFFFFL & l), arrby, n + 4);
    }

    public static void longToBigEndian(long[] arrl, byte[] arrby, int n) {
        for (int i = 0; i < arrl.length; ++i) {
            Pack.longToBigEndian(arrl[i], arrby, n);
            n += 8;
        }
    }

    public static byte[] longToBigEndian(long l) {
        byte[] arrby = new byte[8];
        Pack.longToBigEndian(l, arrby, 0);
        return arrby;
    }

    public static byte[] longToBigEndian(long[] arrl) {
        byte[] arrby = new byte[arrl.length * 8];
        Pack.longToBigEndian(arrl, arrby, 0);
        return arrby;
    }

    public static void longToLittleEndian(long l, byte[] arrby, int n) {
        Pack.intToLittleEndian((int)(0xFFFFFFFFL & l), arrby, n);
        Pack.intToLittleEndian((int)(l >>> 32), arrby, n + 4);
    }

    public static void longToLittleEndian(long[] arrl, byte[] arrby, int n) {
        int n2 = 0;
        int n3 = n;
        for (n = n2; n < arrl.length; ++n) {
            Pack.longToLittleEndian(arrl[n], arrby, n3);
            n3 += 8;
        }
    }

    public static byte[] longToLittleEndian(long l) {
        byte[] arrby = new byte[8];
        Pack.longToLittleEndian(l, arrby, 0);
        return arrby;
    }

    public static byte[] longToLittleEndian(long[] arrl) {
        byte[] arrby = new byte[arrl.length * 8];
        Pack.longToLittleEndian(arrl, arrby, 0);
        return arrby;
    }
}

