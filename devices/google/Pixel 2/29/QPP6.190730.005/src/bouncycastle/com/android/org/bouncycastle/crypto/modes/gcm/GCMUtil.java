/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes.gcm;

import com.android.org.bouncycastle.math.raw.Interleave;
import com.android.org.bouncycastle.util.Pack;

public abstract class GCMUtil {
    private static final int E1 = -520093696;
    private static final long E1L = -2233785415175766016L;

    public static void asBytes(int[] arrn, byte[] arrby) {
        Pack.intToBigEndian(arrn, arrby, 0);
    }

    public static void asBytes(long[] arrl, byte[] arrby) {
        Pack.longToBigEndian(arrl, arrby, 0);
    }

    public static byte[] asBytes(int[] arrn) {
        byte[] arrby = new byte[16];
        Pack.intToBigEndian(arrn, arrby, 0);
        return arrby;
    }

    public static byte[] asBytes(long[] arrl) {
        byte[] arrby = new byte[16];
        Pack.longToBigEndian(arrl, arrby, 0);
        return arrby;
    }

    public static void asInts(byte[] arrby, int[] arrn) {
        Pack.bigEndianToInt(arrby, 0, arrn);
    }

    public static int[] asInts(byte[] arrby) {
        int[] arrn = new int[4];
        Pack.bigEndianToInt(arrby, 0, arrn);
        return arrn;
    }

    public static void asLongs(byte[] arrby, long[] arrl) {
        Pack.bigEndianToLong(arrby, 0, arrl);
    }

    public static long[] asLongs(byte[] arrby) {
        long[] arrl = new long[2];
        Pack.bigEndianToLong(arrby, 0, arrl);
        return arrl;
    }

    public static void copy(int[] arrn, int[] arrn2) {
        arrn2[0] = arrn[0];
        arrn2[1] = arrn[1];
        arrn2[2] = arrn[2];
        arrn2[3] = arrn[3];
    }

    public static void copy(long[] arrl, long[] arrl2) {
        arrl2[0] = arrl[0];
        arrl2[1] = arrl[1];
    }

    public static void divideP(long[] arrl, long[] arrl2) {
        long l = arrl[0];
        long l2 = arrl[1];
        long l3 = l >> 63;
        arrl2[0] = (l ^ -2233785415175766016L & l3) << 1 | l2 >>> 63;
        arrl2[1] = l2 << 1 | -l3;
    }

    public static void multiply(byte[] arrby, byte[] arrby2) {
        long[] arrl = GCMUtil.asLongs(arrby);
        GCMUtil.multiply(arrl, GCMUtil.asLongs(arrby2));
        GCMUtil.asBytes(arrl, arrby);
    }

    public static void multiply(int[] arrn, int[] arrn2) {
        int n = arrn2[0];
        int n2 = arrn2[1];
        int n3 = arrn2[2];
        int n4 = arrn2[3];
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        for (int i = 0; i < 4; ++i) {
            int n9 = arrn[i];
            for (int j = 0; j < 32; ++j) {
                int n10 = n9 >> 31;
                n9 <<= 1;
                n5 ^= n & n10;
                n6 ^= n2 & n10;
                n7 ^= n3 & n10;
                n8 ^= n4 & n10;
                n10 = n4 >>> 1 | n3 << 31;
                n3 = n3 >>> 1 | n2 << 31;
                n2 = n2 >>> 1 | n << 31;
                n = n >>> 1 ^ n4 << 31 >> 8 & -520093696;
                n4 = n10;
            }
        }
        arrn[0] = n5;
        arrn[1] = n6;
        arrn[2] = n7;
        arrn[3] = n8;
    }

    public static void multiply(long[] arrl, long[] arrl2) {
        long l = arrl[0];
        long l2 = arrl[1];
        long l3 = arrl2[0];
        long l4 = arrl2[1];
        long l5 = 0L;
        long l6 = 0L;
        long l7 = 0L;
        for (int i = 0; i < 64; ++i) {
            long l8 = l >> 63;
            l <<= 1;
            l5 ^= l3 & l8;
            long l9 = l2 >> 63;
            l2 <<= 1;
            l6 = l6 ^ l4 & l8 ^ l3 & l9;
            l7 ^= l4 & l9;
            l8 = l4 >>> 1 | l3 << 63;
            l3 = l3 >>> 1 ^ l4 << 63 >> 8 & -2233785415175766016L;
            l4 = l8;
        }
        arrl[0] = l7 >>> 1 ^ l7 ^ l7 >>> 2 ^ l7 >>> 7 ^ l5;
        arrl[1] = l7 << 63 ^ l7 << 62 ^ l7 << 57 ^ l6;
    }

    public static void multiplyP(int[] arrn) {
        int n = arrn[0];
        int n2 = arrn[1];
        int n3 = arrn[2];
        int n4 = arrn[3];
        arrn[0] = n >>> 1 ^ -520093696 & n4 << 31 >> 31;
        arrn[1] = n2 >>> 1 | n << 31;
        arrn[2] = n3 >>> 1 | n2 << 31;
        arrn[3] = n4 >>> 1 | n3 << 31;
    }

    public static void multiplyP(int[] arrn, int[] arrn2) {
        int n = arrn[0];
        int n2 = arrn[1];
        int n3 = arrn[2];
        int n4 = arrn[3];
        arrn2[0] = n >>> 1 ^ -520093696 & n4 << 31 >> 31;
        arrn2[1] = n2 >>> 1 | n << 31;
        arrn2[2] = n3 >>> 1 | n2 << 31;
        arrn2[3] = n4 >>> 1 | n3 << 31;
    }

    public static void multiplyP(long[] arrl) {
        long l = arrl[0];
        long l2 = arrl[1];
        arrl[0] = l >>> 1 ^ -2233785415175766016L & l2 << 63 >> 63;
        arrl[1] = l2 >>> 1 | l << 63;
    }

    public static void multiplyP(long[] arrl, long[] arrl2) {
        long l = arrl[0];
        long l2 = arrl[1];
        arrl2[0] = l >>> 1 ^ -2233785415175766016L & l2 << 63 >> 63;
        arrl2[1] = l2 >>> 1 | l << 63;
    }

    public static void multiplyP3(long[] arrl, long[] arrl2) {
        long l = arrl[0];
        long l2 = arrl[1];
        long l3 = l2 << 61;
        arrl2[0] = l >>> 3 ^ l3 ^ l3 >>> 1 ^ l3 >>> 2 ^ l3 >>> 7;
        arrl2[1] = l2 >>> 3 | l << 61;
    }

    public static void multiplyP4(long[] arrl, long[] arrl2) {
        long l = arrl[0];
        long l2 = arrl[1];
        long l3 = l2 << 60;
        arrl2[0] = l >>> 4 ^ l3 ^ l3 >>> 1 ^ l3 >>> 2 ^ l3 >>> 7;
        arrl2[1] = l2 >>> 4 | l << 60;
    }

    public static void multiplyP7(long[] arrl, long[] arrl2) {
        long l = arrl[0];
        long l2 = arrl[1];
        long l3 = l2 << 57;
        arrl2[0] = l >>> 7 ^ l3 ^ l3 >>> 1 ^ l3 >>> 2 ^ l3 >>> 7;
        arrl2[1] = l2 >>> 7 | l << 57;
    }

    public static void multiplyP8(int[] arrn) {
        int n = arrn[0];
        int n2 = arrn[1];
        int n3 = arrn[2];
        int n4 = arrn[3];
        int n5 = n4 << 24;
        arrn[0] = n >>> 8 ^ n5 ^ n5 >>> 1 ^ n5 >>> 2 ^ n5 >>> 7;
        arrn[1] = n2 >>> 8 | n << 24;
        arrn[2] = n3 >>> 8 | n2 << 24;
        arrn[3] = n4 >>> 8 | n3 << 24;
    }

    public static void multiplyP8(int[] arrn, int[] arrn2) {
        int n = arrn[0];
        int n2 = arrn[1];
        int n3 = arrn[2];
        int n4 = arrn[3];
        int n5 = n4 << 24;
        arrn2[0] = n >>> 8 ^ n5 ^ n5 >>> 1 ^ n5 >>> 2 ^ n5 >>> 7;
        arrn2[1] = n2 >>> 8 | n << 24;
        arrn2[2] = n3 >>> 8 | n2 << 24;
        arrn2[3] = n4 >>> 8 | n3 << 24;
    }

    public static void multiplyP8(long[] arrl) {
        long l = arrl[0];
        long l2 = arrl[1];
        long l3 = l2 << 56;
        arrl[0] = l >>> 8 ^ l3 ^ l3 >>> 1 ^ l3 >>> 2 ^ l3 >>> 7;
        arrl[1] = l2 >>> 8 | l << 56;
    }

    public static void multiplyP8(long[] arrl, long[] arrl2) {
        long l = arrl[0];
        long l2 = arrl[1];
        long l3 = l2 << 56;
        arrl2[0] = l >>> 8 ^ l3 ^ l3 >>> 1 ^ l3 >>> 2 ^ l3 >>> 7;
        arrl2[1] = l2 >>> 8 | l << 56;
    }

    public static byte[] oneAsBytes() {
        byte[] arrby = new byte[16];
        arrby[0] = (byte)-128;
        return arrby;
    }

    public static int[] oneAsInts() {
        int[] arrn = new int[4];
        arrn[0] = Integer.MIN_VALUE;
        return arrn;
    }

    public static long[] oneAsLongs() {
        long[] arrl = new long[2];
        arrl[0] = Long.MIN_VALUE;
        return arrl;
    }

    public static long[] pAsLongs() {
        long[] arrl = new long[2];
        arrl[0] = 0x4000000000000000L;
        return arrl;
    }

    public static void square(long[] arrl, long[] arrl2) {
        long[] arrl3 = new long[4];
        Interleave.expand64To128Rev(arrl[0], arrl3, 0);
        Interleave.expand64To128Rev(arrl[1], arrl3, 2);
        long l = arrl3[0];
        long l2 = arrl3[1];
        long l3 = arrl3[2];
        long l4 = arrl3[3];
        arrl2[0] = l ^ ((l3 ^= l4 << 63 ^ l4 << 62 ^ l4 << 57) >>> 1 ^ l3 ^ l3 >>> 2 ^ l3 >>> 7);
        arrl2[1] = l2 ^ (l4 >>> 1 ^ l4 ^ l4 >>> 2 ^ l4 >>> 7) ^ (l3 << 62 ^ l3 << 63 ^ l3 << 57);
    }

    public static void xor(byte[] arrby, int n, byte[] arrby2, int n2, int n3) {
        while (--n3 >= 0) {
            int n4 = n + n3;
            arrby[n4] = (byte)(arrby[n4] ^ arrby2[n2 + n3]);
        }
    }

    public static void xor(byte[] arrby, int n, byte[] arrby2, int n2, byte[] arrby3, int n3) {
        int n4 = 0;
        do {
            arrby3[n3 + n4] = (byte)(arrby[n + n4] ^ arrby2[n2 + n4]);
            arrby3[n3 + ++n4] = (byte)(arrby[n + n4] ^ arrby2[n2 + n4]);
            arrby3[n3 + ++n4] = (byte)(arrby[n + n4] ^ arrby2[n2 + n4]);
            arrby3[n3 + ++n4] = (byte)(arrby[n + n4] ^ arrby2[n2 + n4]);
        } while (++n4 < 16);
    }

    public static void xor(byte[] arrby, byte[] arrby2) {
        int n = 0;
        do {
            arrby[n] = (byte)(arrby[n] ^ arrby2[n]);
            arrby[++n] = (byte)(arrby[n] ^ arrby2[n]);
            arrby[++n] = (byte)(arrby[n] ^ arrby2[n]);
            arrby[++n] = (byte)(arrby[n] ^ arrby2[n]);
        } while (++n < 16);
    }

    public static void xor(byte[] arrby, byte[] arrby2, int n) {
        int n2 = 0;
        do {
            arrby[n2] = (byte)(arrby[n2] ^ arrby2[n + n2]);
            arrby[++n2] = (byte)(arrby[n2] ^ arrby2[n + n2]);
            arrby[++n2] = (byte)(arrby[n2] ^ arrby2[n + n2]);
            arrby[++n2] = (byte)(arrby[n2] ^ arrby2[n + n2]);
        } while (++n2 < 16);
    }

    public static void xor(byte[] arrby, byte[] arrby2, int n, int n2) {
        while (--n2 >= 0) {
            arrby[n2] = (byte)(arrby[n2] ^ arrby2[n + n2]);
        }
    }

    public static void xor(byte[] arrby, byte[] arrby2, byte[] arrby3) {
        int n = 0;
        do {
            arrby3[n] = (byte)(arrby[n] ^ arrby2[n]);
            arrby3[++n] = (byte)(arrby[n] ^ arrby2[n]);
            arrby3[++n] = (byte)(arrby[n] ^ arrby2[n]);
            arrby3[++n] = (byte)(arrby[n] ^ arrby2[n]);
        } while (++n < 16);
    }

    public static void xor(int[] arrn, int[] arrn2) {
        arrn[0] = arrn[0] ^ arrn2[0];
        arrn[1] = arrn[1] ^ arrn2[1];
        arrn[2] = arrn[2] ^ arrn2[2];
        arrn[3] = arrn[3] ^ arrn2[3];
    }

    public static void xor(int[] arrn, int[] arrn2, int[] arrn3) {
        arrn3[0] = arrn[0] ^ arrn2[0];
        arrn3[1] = arrn[1] ^ arrn2[1];
        arrn3[2] = arrn[2] ^ arrn2[2];
        arrn3[3] = arrn[3] ^ arrn2[3];
    }

    public static void xor(long[] arrl, long[] arrl2) {
        arrl[0] = arrl[0] ^ arrl2[0];
        arrl[1] = arrl[1] ^ arrl2[1];
    }

    public static void xor(long[] arrl, long[] arrl2, long[] arrl3) {
        arrl3[0] = arrl[0] ^ arrl2[0];
        arrl3[1] = arrl[1] ^ arrl2[1];
    }
}

