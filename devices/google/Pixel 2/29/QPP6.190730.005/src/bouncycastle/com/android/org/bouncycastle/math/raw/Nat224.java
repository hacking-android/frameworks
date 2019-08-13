/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.raw;

import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.util.Pack;
import java.math.BigInteger;

public abstract class Nat224 {
    private static final long M = 0xFFFFFFFFL;

    public static int add(int[] arrn, int n, int[] arrn2, int n2, int[] arrn3, int n3) {
        long l = 0L + (((long)arrn[n + 0] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 0] & 0xFFFFFFFFL));
        arrn3[n3 + 0] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 1] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 1] & 0xFFFFFFFFL));
        arrn3[n3 + 1] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 2] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 2] & 0xFFFFFFFFL));
        arrn3[n3 + 2] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 3] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 3] & 0xFFFFFFFFL));
        arrn3[n3 + 3] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 4] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 4] & 0xFFFFFFFFL));
        arrn3[n3 + 4] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 5] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 5] & 0xFFFFFFFFL));
        arrn3[n3 + 5] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 6] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 6] & 0xFFFFFFFFL));
        arrn3[n3 + 6] = (int)l;
        return (int)(l >>> 32);
    }

    public static int add(int[] arrn, int[] arrn2, int[] arrn3) {
        long l = 0L + (((long)arrn[0] & 0xFFFFFFFFL) + ((long)arrn2[0] & 0xFFFFFFFFL));
        arrn3[0] = (int)l;
        l = (l >>> 32) + (((long)arrn[1] & 0xFFFFFFFFL) + ((long)arrn2[1] & 0xFFFFFFFFL));
        arrn3[1] = (int)l;
        l = (l >>> 32) + (((long)arrn[2] & 0xFFFFFFFFL) + ((long)arrn2[2] & 0xFFFFFFFFL));
        arrn3[2] = (int)l;
        l = (l >>> 32) + (((long)arrn[3] & 0xFFFFFFFFL) + ((long)arrn2[3] & 0xFFFFFFFFL));
        arrn3[3] = (int)l;
        l = (l >>> 32) + (((long)arrn[4] & 0xFFFFFFFFL) + ((long)arrn2[4] & 0xFFFFFFFFL));
        arrn3[4] = (int)l;
        l = (l >>> 32) + (((long)arrn[5] & 0xFFFFFFFFL) + ((long)arrn2[5] & 0xFFFFFFFFL));
        arrn3[5] = (int)l;
        l = (l >>> 32) + (((long)arrn[6] & 0xFFFFFFFFL) + ((long)arrn2[6] & 0xFFFFFFFFL));
        arrn3[6] = (int)l;
        return (int)(l >>> 32);
    }

    public static int addBothTo(int[] arrn, int n, int[] arrn2, int n2, int[] arrn3, int n3) {
        long l = 0L + (((long)arrn[n + 0] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 0] & 0xFFFFFFFFL) + ((long)arrn3[n3 + 0] & 0xFFFFFFFFL));
        arrn3[n3 + 0] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 1] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 1] & 0xFFFFFFFFL) + ((long)arrn3[n3 + 1] & 0xFFFFFFFFL));
        arrn3[n3 + 1] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 2] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 2] & 0xFFFFFFFFL) + ((long)arrn3[n3 + 2] & 0xFFFFFFFFL));
        arrn3[n3 + 2] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 3] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 3] & 0xFFFFFFFFL) + ((long)arrn3[n3 + 3] & 0xFFFFFFFFL));
        arrn3[n3 + 3] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 4] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 4] & 0xFFFFFFFFL) + ((long)arrn3[n3 + 4] & 0xFFFFFFFFL));
        arrn3[n3 + 4] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 5] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 5] & 0xFFFFFFFFL) + ((long)arrn3[n3 + 5] & 0xFFFFFFFFL));
        arrn3[n3 + 5] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 6] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 6] & 0xFFFFFFFFL) + ((long)arrn3[n3 + 6] & 0xFFFFFFFFL));
        arrn3[n3 + 6] = (int)l;
        return (int)(l >>> 32);
    }

    public static int addBothTo(int[] arrn, int[] arrn2, int[] arrn3) {
        long l = 0L + (((long)arrn[0] & 0xFFFFFFFFL) + ((long)arrn2[0] & 0xFFFFFFFFL) + ((long)arrn3[0] & 0xFFFFFFFFL));
        arrn3[0] = (int)l;
        l = (l >>> 32) + (((long)arrn[1] & 0xFFFFFFFFL) + ((long)arrn2[1] & 0xFFFFFFFFL) + ((long)arrn3[1] & 0xFFFFFFFFL));
        arrn3[1] = (int)l;
        l = (l >>> 32) + (((long)arrn[2] & 0xFFFFFFFFL) + ((long)arrn2[2] & 0xFFFFFFFFL) + ((long)arrn3[2] & 0xFFFFFFFFL));
        arrn3[2] = (int)l;
        l = (l >>> 32) + (((long)arrn[3] & 0xFFFFFFFFL) + ((long)arrn2[3] & 0xFFFFFFFFL) + ((long)arrn3[3] & 0xFFFFFFFFL));
        arrn3[3] = (int)l;
        l = (l >>> 32) + (((long)arrn[4] & 0xFFFFFFFFL) + ((long)arrn2[4] & 0xFFFFFFFFL) + ((long)arrn3[4] & 0xFFFFFFFFL));
        arrn3[4] = (int)l;
        l = (l >>> 32) + (((long)arrn[5] & 0xFFFFFFFFL) + ((long)arrn2[5] & 0xFFFFFFFFL) + ((long)arrn3[5] & 0xFFFFFFFFL));
        arrn3[5] = (int)l;
        l = (l >>> 32) + (((long)arrn[6] & 0xFFFFFFFFL) + ((long)arrn2[6] & 0xFFFFFFFFL) + ((long)arrn3[6] & 0xFFFFFFFFL));
        arrn3[6] = (int)l;
        return (int)(l >>> 32);
    }

    public static int addTo(int[] arrn, int n, int[] arrn2, int n2, int n3) {
        long l = ((long)n3 & 0xFFFFFFFFL) + (((long)arrn[n + 0] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 0] & 0xFFFFFFFFL));
        arrn2[n2 + 0] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 1] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 1] & 0xFFFFFFFFL));
        arrn2[n2 + 1] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 2] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 2] & 0xFFFFFFFFL));
        arrn2[n2 + 2] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 3] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 3] & 0xFFFFFFFFL));
        arrn2[n2 + 3] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 4] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 4] & 0xFFFFFFFFL));
        arrn2[n2 + 4] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 5] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 5] & 0xFFFFFFFFL));
        arrn2[n2 + 5] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 6] & 0xFFFFFFFFL) + (0xFFFFFFFFL & (long)arrn2[n2 + 6]));
        arrn2[n2 + 6] = (int)l;
        return (int)(l >>> 32);
    }

    public static int addTo(int[] arrn, int[] arrn2) {
        long l = 0L + (((long)arrn[0] & 0xFFFFFFFFL) + ((long)arrn2[0] & 0xFFFFFFFFL));
        arrn2[0] = (int)l;
        l = (l >>> 32) + (((long)arrn[1] & 0xFFFFFFFFL) + ((long)arrn2[1] & 0xFFFFFFFFL));
        arrn2[1] = (int)l;
        l = (l >>> 32) + (((long)arrn[2] & 0xFFFFFFFFL) + ((long)arrn2[2] & 0xFFFFFFFFL));
        arrn2[2] = (int)l;
        l = (l >>> 32) + (((long)arrn[3] & 0xFFFFFFFFL) + ((long)arrn2[3] & 0xFFFFFFFFL));
        arrn2[3] = (int)l;
        l = (l >>> 32) + (((long)arrn[4] & 0xFFFFFFFFL) + ((long)arrn2[4] & 0xFFFFFFFFL));
        arrn2[4] = (int)l;
        l = (l >>> 32) + (((long)arrn[5] & 0xFFFFFFFFL) + ((long)arrn2[5] & 0xFFFFFFFFL));
        arrn2[5] = (int)l;
        l = (l >>> 32) + (((long)arrn[6] & 0xFFFFFFFFL) + ((long)arrn2[6] & 0xFFFFFFFFL));
        arrn2[6] = (int)l;
        return (int)(l >>> 32);
    }

    public static int addToEachOther(int[] arrn, int n, int[] arrn2, int n2) {
        long l = 0L + (((long)arrn[n + 0] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 0] & 0xFFFFFFFFL));
        arrn[n + 0] = (int)l;
        arrn2[n2 + 0] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 1] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 1] & 0xFFFFFFFFL));
        arrn[n + 1] = (int)l;
        arrn2[n2 + 1] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 2] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 2] & 0xFFFFFFFFL));
        arrn[n + 2] = (int)l;
        arrn2[n2 + 2] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 3] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 3] & 0xFFFFFFFFL));
        arrn[n + 3] = (int)l;
        arrn2[n2 + 3] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 4] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 4] & 0xFFFFFFFFL));
        arrn[n + 4] = (int)l;
        arrn2[n2 + 4] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 5] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 5] & 0xFFFFFFFFL));
        arrn[n + 5] = (int)l;
        arrn2[n2 + 5] = (int)l;
        l = (l >>> 32) + (((long)arrn[n + 6] & 0xFFFFFFFFL) + ((long)arrn2[n2 + 6] & 0xFFFFFFFFL));
        arrn[n + 6] = (int)l;
        arrn2[n2 + 6] = (int)l;
        return (int)(l >>> 32);
    }

    public static void copy(int[] arrn, int n, int[] arrn2, int n2) {
        arrn2[n2 + 0] = arrn[n + 0];
        arrn2[n2 + 1] = arrn[n + 1];
        arrn2[n2 + 2] = arrn[n + 2];
        arrn2[n2 + 3] = arrn[n + 3];
        arrn2[n2 + 4] = arrn[n + 4];
        arrn2[n2 + 5] = arrn[n + 5];
        arrn2[n2 + 6] = arrn[n + 6];
    }

    public static void copy(int[] arrn, int[] arrn2) {
        arrn2[0] = arrn[0];
        arrn2[1] = arrn[1];
        arrn2[2] = arrn[2];
        arrn2[3] = arrn[3];
        arrn2[4] = arrn[4];
        arrn2[5] = arrn[5];
        arrn2[6] = arrn[6];
    }

    public static int[] create() {
        return new int[7];
    }

    public static int[] createExt() {
        return new int[14];
    }

    public static boolean diff(int[] arrn, int n, int[] arrn2, int n2, int[] arrn3, int n3) {
        boolean bl = Nat224.gte(arrn, n, arrn2, n2);
        if (bl) {
            Nat224.sub(arrn, n, arrn2, n2, arrn3, n3);
        } else {
            Nat224.sub(arrn2, n2, arrn, n, arrn3, n3);
        }
        return bl;
    }

    public static boolean eq(int[] arrn, int[] arrn2) {
        for (int i = 6; i >= 0; --i) {
            if (arrn[i] == arrn2[i]) continue;
            return false;
        }
        return true;
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        if (bigInteger.signum() >= 0 && bigInteger.bitLength() <= 224) {
            int[] arrn = Nat224.create();
            int n = 0;
            while (bigInteger.signum() != 0) {
                arrn[n] = bigInteger.intValue();
                bigInteger = bigInteger.shiftRight(32);
                ++n;
            }
            return arrn;
        }
        throw new IllegalArgumentException();
    }

    public static int getBit(int[] arrn, int n) {
        if (n == 0) {
            return arrn[0] & 1;
        }
        int n2 = n >> 5;
        if (n2 >= 0 && n2 < 7) {
            return arrn[n2] >>> (n & 31) & 1;
        }
        return 0;
    }

    public static boolean gte(int[] arrn, int n, int[] arrn2, int n2) {
        for (int i = 6; i >= 0; --i) {
            int n3 = arrn[n + i] ^ Integer.MIN_VALUE;
            int n4 = Integer.MIN_VALUE ^ arrn2[n2 + i];
            if (n3 < n4) {
                return false;
            }
            if (n3 <= n4) continue;
            return true;
        }
        return true;
    }

    public static boolean gte(int[] arrn, int[] arrn2) {
        for (int i = 6; i >= 0; --i) {
            int n = arrn[i] ^ Integer.MIN_VALUE;
            int n2 = Integer.MIN_VALUE ^ arrn2[i];
            if (n < n2) {
                return false;
            }
            if (n <= n2) continue;
            return true;
        }
        return true;
    }

    public static boolean isOne(int[] arrn) {
        if (arrn[0] != 1) {
            return false;
        }
        for (int i = 1; i < 7; ++i) {
            if (arrn[i] == 0) continue;
            return false;
        }
        return true;
    }

    public static boolean isZero(int[] arrn) {
        for (int i = 0; i < 7; ++i) {
            if (arrn[i] == 0) continue;
            return false;
        }
        return true;
    }

    public static void mul(int[] arrn, int n, int[] arrn2, int n2, int[] arrn3, int n3) {
        long l = (long)arrn2[n2 + 0] & 0xFFFFFFFFL;
        long l2 = (long)arrn2[n2 + 1] & 0xFFFFFFFFL;
        long l3 = (long)arrn2[n2 + 2] & 0xFFFFFFFFL;
        long l4 = (long)arrn2[n2 + 3] & 0xFFFFFFFFL;
        long l5 = (long)arrn2[n2 + 4] & 0xFFFFFFFFL;
        long l6 = (long)arrn2[n2 + 5] & 0xFFFFFFFFL;
        long l7 = (long)arrn2[n2 + 6] & 0xFFFFFFFFL;
        long l8 = (long)arrn[n + 0] & 0xFFFFFFFFL;
        long l9 = 0L + l8 * l;
        arrn3[n3 + 0] = (int)l9;
        l9 = (l9 >>> 32) + l8 * l2;
        arrn3[n3 + 1] = (int)l9;
        l9 = (l9 >>> 32) + l8 * l3;
        arrn3[n3 + 2] = (int)l9;
        l9 = (l9 >>> 32) + l8 * l4;
        arrn3[n3 + 3] = (int)l9;
        l9 = (l9 >>> 32) + l8 * l5;
        arrn3[n3 + 4] = (int)l9;
        l9 = (l9 >>> 32) + l8 * l6;
        arrn3[n3 + 5] = (int)l9;
        l8 = (l9 >>> 32) + l8 * l7;
        arrn3[n3 + 6] = (int)l8;
        arrn3[n3 + 7] = (int)(l8 >>> 32);
        for (n2 = 1; n2 < 7; ++n2) {
            l8 = (long)arrn[n + n2] & 0xFFFFFFFFL;
            l9 = 0L + (l8 * l + ((long)arrn3[++n3 + 0] & 0xFFFFFFFFL));
            arrn3[n3 + 0] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * l2 + ((long)arrn3[n3 + 1] & 0xFFFFFFFFL));
            arrn3[n3 + 1] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * l3 + ((long)arrn3[n3 + 2] & 0xFFFFFFFFL));
            arrn3[n3 + 2] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * l4 + ((long)arrn3[n3 + 3] & 0xFFFFFFFFL));
            arrn3[n3 + 3] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * l5 + ((long)arrn3[n3 + 4] & 0xFFFFFFFFL));
            arrn3[n3 + 4] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * l6 + ((long)arrn3[n3 + 5] & 0xFFFFFFFFL));
            arrn3[n3 + 5] = (int)l9;
            l8 = (l9 >>> 32) + (l8 * l7 + ((long)arrn3[n3 + 6] & 0xFFFFFFFFL));
            arrn3[n3 + 6] = (int)l8;
            arrn3[n3 + 7] = (int)(l8 >>> 32);
        }
    }

    public static void mul(int[] arrn, int[] arrn2, int[] arrn3) {
        long l = (long)arrn2[0] & 0xFFFFFFFFL;
        long l2 = (long)arrn2[1] & 0xFFFFFFFFL;
        long l3 = (long)arrn2[2] & 0xFFFFFFFFL;
        long l4 = (long)arrn2[3] & 0xFFFFFFFFL;
        long l5 = (long)arrn2[4] & 0xFFFFFFFFL;
        long l6 = (long)arrn2[5] & 0xFFFFFFFFL;
        long l7 = (long)arrn2[6] & 0xFFFFFFFFL;
        long l8 = (long)arrn[0] & 0xFFFFFFFFL;
        long l9 = 0L + l8 * l;
        arrn3[0] = (int)l9;
        l9 = (l9 >>> 32) + l8 * l2;
        arrn3[1] = (int)l9;
        l9 = (l9 >>> 32) + l8 * l3;
        arrn3[2] = (int)l9;
        l9 = (l9 >>> 32) + l8 * l4;
        arrn3[3] = (int)l9;
        l9 = (l9 >>> 32) + l8 * l5;
        arrn3[4] = (int)l9;
        l9 = (l9 >>> 32) + l8 * l6;
        arrn3[5] = (int)l9;
        l8 = (l9 >>> 32) + l8 * l7;
        arrn3[6] = (int)l8;
        arrn3[7] = (int)(l8 >>> 32);
        for (int i = 1; i < 7; ++i) {
            l8 = (long)arrn[i] & 0xFFFFFFFFL;
            l9 = 0L + (l8 * l + ((long)arrn3[i + 0] & 0xFFFFFFFFL));
            arrn3[i + 0] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * l2 + ((long)arrn3[i + 1] & 0xFFFFFFFFL));
            arrn3[i + 1] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * l3 + ((long)arrn3[i + 2] & 0xFFFFFFFFL));
            arrn3[i + 2] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * l4 + ((long)arrn3[i + 3] & 0xFFFFFFFFL));
            arrn3[i + 3] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * l5 + ((long)arrn3[i + 4] & 0xFFFFFFFFL));
            arrn3[i + 4] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * l6 + ((long)arrn3[i + 5] & 0xFFFFFFFFL));
            arrn3[i + 5] = (int)l9;
            l8 = (l9 >>> 32) + (l8 * l7 + ((long)arrn3[i + 6] & 0xFFFFFFFFL));
            arrn3[i + 6] = (int)l8;
            arrn3[i + 7] = (int)(l8 >>> 32);
        }
    }

    public static long mul33Add(int n, int[] arrn, int n2, int[] arrn2, int n3, int[] arrn3, int n4) {
        long l = (long)n & 0xFFFFFFFFL;
        long l2 = (long)arrn[n2 + 0] & 0xFFFFFFFFL;
        long l3 = 0L + (l * l2 + ((long)arrn2[n3 + 0] & 0xFFFFFFFFL));
        arrn3[n4 + 0] = (int)l3;
        long l4 = (long)arrn[n2 + 1] & 0xFFFFFFFFL;
        l2 = (l3 >>> 32) + (l * l4 + l2 + ((long)arrn2[n3 + 1] & 0xFFFFFFFFL));
        arrn3[n4 + 1] = (int)l2;
        l3 = (long)arrn[n2 + 2] & 0xFFFFFFFFL;
        l2 = (l2 >>> 32) + (l * l3 + l4 + ((long)arrn2[n3 + 2] & 0xFFFFFFFFL));
        arrn3[n4 + 2] = (int)l2;
        l4 = (long)arrn[n2 + 3] & 0xFFFFFFFFL;
        l2 = (l2 >>> 32) + (l * l4 + l3 + ((long)arrn2[n3 + 3] & 0xFFFFFFFFL));
        arrn3[n4 + 3] = (int)l2;
        l3 = (long)arrn[n2 + 4] & 0xFFFFFFFFL;
        l2 = (l2 >>> 32) + (l * l3 + l4 + ((long)arrn2[n3 + 4] & 0xFFFFFFFFL));
        arrn3[n4 + 4] = (int)l2;
        l4 = (long)arrn[n2 + 5] & 0xFFFFFFFFL;
        l2 = (l2 >>> 32) + (l * l4 + l3 + ((long)arrn2[n3 + 5] & 0xFFFFFFFFL));
        arrn3[n4 + 5] = (int)l2;
        l3 = (long)arrn[n2 + 6] & 0xFFFFFFFFL;
        l = (l2 >>> 32) + (l * l3 + l4 + ((long)arrn2[n3 + 6] & 0xFFFFFFFFL));
        arrn3[n4 + 6] = (int)l;
        return (l >>> 32) + l3;
    }

    public static int mul33DWordAdd(int n, long l, int[] arrn, int n2) {
        long l2 = (long)n & 0xFFFFFFFFL;
        long l3 = l & 0xFFFFFFFFL;
        long l4 = 0L + (l2 * l3 + ((long)arrn[n2 + 0] & 0xFFFFFFFFL));
        arrn[n2 + 0] = (int)l4;
        l2 = (l4 >>> 32) + (l2 * (l >>>= 32) + l3 + ((long)arrn[n2 + 1] & 0xFFFFFFFFL));
        arrn[n2 + 1] = (int)l2;
        l = (l2 >>> 32) + (((long)arrn[n2 + 2] & 0xFFFFFFFFL) + l);
        arrn[n2 + 2] = (int)l;
        l = (l >>> 32) + ((long)arrn[n2 + 3] & 0xFFFFFFFFL);
        arrn[n2 + 3] = (int)l;
        n = l >>> 32 == 0L ? 0 : Nat.incAt(7, arrn, n2, 4);
        return n;
    }

    public static int mul33WordAdd(int n, int n2, int[] arrn, int n3) {
        long l = n;
        long l2 = (long)n2 & 0xFFFFFFFFL;
        l = 0L + (l2 * (l & 0xFFFFFFFFL) + ((long)arrn[n3 + 0] & 0xFFFFFFFFL));
        arrn[n3 + 0] = (int)l;
        l2 = (l >>> 32) + (((long)arrn[n3 + 1] & 0xFFFFFFFFL) + l2);
        arrn[n3 + 1] = (int)l2;
        l2 = (l2 >>> 32) + (0xFFFFFFFFL & (long)arrn[n3 + 2]);
        arrn[n3 + 2] = (int)l2;
        n = l2 >>> 32 == 0L ? 0 : Nat.incAt(7, arrn, n3, 3);
        return n;
    }

    public static int mulAddTo(int[] arrn, int n, int[] arrn2, int n2, int[] arrn3, int n3) {
        long l = (long)arrn2[n2 + 0] & 0xFFFFFFFFL;
        long l2 = (long)arrn2[n2 + 1] & 0xFFFFFFFFL;
        long l3 = arrn2[n2 + 2];
        long l4 = arrn2[n2 + 3];
        long l5 = arrn2[n2 + 4];
        long l6 = (long)arrn2[n2 + 5] & 0xFFFFFFFFL;
        long l7 = (long)arrn2[n2 + 6] & 0xFFFFFFFFL;
        n2 = n3;
        long l8 = 0L;
        for (n3 = 0; n3 < 7; ++n3) {
            long l9 = (long)arrn[n + n3] & 0xFFFFFFFFL;
            long l10 = 0L + (l9 * l + ((long)arrn3[n2 + 0] & 0xFFFFFFFFL));
            arrn3[n2 + 0] = (int)l10;
            l10 = (l10 >>> 32) + (l9 * l2 + ((long)arrn3[n2 + 1] & 0xFFFFFFFFL));
            arrn3[n2 + 1] = (int)l10;
            l10 = (l10 >>> 32) + (l9 * (l3 & 0xFFFFFFFFL) + ((long)arrn3[n2 + 2] & 0xFFFFFFFFL));
            arrn3[n2 + 2] = (int)l10;
            l10 = (l10 >>> 32) + (l9 * (l4 & 0xFFFFFFFFL) + ((long)arrn3[n2 + 3] & 0xFFFFFFFFL));
            arrn3[n2 + 3] = (int)l10;
            l10 = (l10 >>> 32) + (l9 * (l5 & 0xFFFFFFFFL) + ((long)arrn3[n2 + 4] & 0xFFFFFFFFL));
            arrn3[n2 + 4] = (int)l10;
            l10 = (l10 >>> 32) + (l9 * l6 + ((long)arrn3[n2 + 5] & 0xFFFFFFFFL));
            arrn3[n2 + 5] = (int)l10;
            l9 = (l10 >>> 32) + (l9 * l7 + ((long)arrn3[n2 + 6] & 0xFFFFFFFFL));
            arrn3[n2 + 6] = (int)l9;
            l8 = (l9 >>> 32) + (l8 + ((long)arrn3[n2 + 7] & 0xFFFFFFFFL));
            arrn3[n2 + 7] = (int)l8;
            l8 >>>= 32;
            ++n2;
        }
        return (int)l8;
    }

    public static int mulAddTo(int[] arrn, int[] arrn2, int[] arrn3) {
        long l = (long)arrn2[0] & 0xFFFFFFFFL;
        long l2 = (long)arrn2[1] & 0xFFFFFFFFL;
        long l3 = arrn2[2];
        long l4 = arrn2[3];
        long l5 = arrn2[4];
        long l6 = (long)arrn2[5] & 0xFFFFFFFFL;
        long l7 = (long)arrn2[6] & 0xFFFFFFFFL;
        long l8 = 0L;
        for (int i = 0; i < 7; ++i) {
            long l9 = (long)arrn[i] & 0xFFFFFFFFL;
            long l10 = 0L + (l9 * l + ((long)arrn3[i + 0] & 0xFFFFFFFFL));
            arrn3[i + 0] = (int)l10;
            l10 = (l10 >>> 32) + (l9 * l2 + ((long)arrn3[i + 1] & 0xFFFFFFFFL));
            arrn3[i + 1] = (int)l10;
            l10 = (l10 >>> 32) + (l9 * (l3 & 0xFFFFFFFFL) + ((long)arrn3[i + 2] & 0xFFFFFFFFL));
            arrn3[i + 2] = (int)l10;
            l10 = (l10 >>> 32) + (l9 * (l4 & 0xFFFFFFFFL) + ((long)arrn3[i + 3] & 0xFFFFFFFFL));
            arrn3[i + 3] = (int)l10;
            l10 = (l10 >>> 32) + (l9 * (l5 & 0xFFFFFFFFL) + ((long)arrn3[i + 4] & 0xFFFFFFFFL));
            arrn3[i + 4] = (int)l10;
            l10 = (l10 >>> 32) + (l9 * l6 + ((long)arrn3[i + 5] & 0xFFFFFFFFL));
            arrn3[i + 5] = (int)l10;
            l9 = (l10 >>> 32) + (l9 * l7 + ((long)arrn3[i + 6] & 0xFFFFFFFFL));
            arrn3[i + 6] = (int)l9;
            l8 = (l9 >>> 32) + (l8 + ((long)arrn3[i + 7] & 0xFFFFFFFFL));
            arrn3[i + 7] = (int)l8;
            l8 >>>= 32;
        }
        return (int)l8;
    }

    public static int mulByWord(int n, int[] arrn) {
        long l = (long)n & 0xFFFFFFFFL;
        long l2 = 0L + ((long)arrn[0] & 0xFFFFFFFFL) * l;
        arrn[0] = (int)l2;
        l2 = (l2 >>> 32) + ((long)arrn[1] & 0xFFFFFFFFL) * l;
        arrn[1] = (int)l2;
        l2 = (l2 >>> 32) + ((long)arrn[2] & 0xFFFFFFFFL) * l;
        arrn[2] = (int)l2;
        l2 = (l2 >>> 32) + ((long)arrn[3] & 0xFFFFFFFFL) * l;
        arrn[3] = (int)l2;
        l2 = (l2 >>> 32) + ((long)arrn[4] & 0xFFFFFFFFL) * l;
        arrn[4] = (int)l2;
        l2 = (l2 >>> 32) + ((long)arrn[5] & 0xFFFFFFFFL) * l;
        arrn[5] = (int)l2;
        l = (l2 >>> 32) + (0xFFFFFFFFL & (long)arrn[6]) * l;
        arrn[6] = (int)l;
        return (int)(l >>> 32);
    }

    public static int mulByWordAddTo(int n, int[] arrn, int[] arrn2) {
        long l = (long)n & 0xFFFFFFFFL;
        long l2 = 0L + (((long)arrn2[0] & 0xFFFFFFFFL) * l + ((long)arrn[0] & 0xFFFFFFFFL));
        arrn2[0] = (int)l2;
        l2 = (l2 >>> 32) + (((long)arrn2[1] & 0xFFFFFFFFL) * l + ((long)arrn[1] & 0xFFFFFFFFL));
        arrn2[1] = (int)l2;
        l2 = (l2 >>> 32) + (((long)arrn2[2] & 0xFFFFFFFFL) * l + ((long)arrn[2] & 0xFFFFFFFFL));
        arrn2[2] = (int)l2;
        l2 = (l2 >>> 32) + (((long)arrn2[3] & 0xFFFFFFFFL) * l + ((long)arrn[3] & 0xFFFFFFFFL));
        arrn2[3] = (int)l2;
        l2 = (l2 >>> 32) + (((long)arrn2[4] & 0xFFFFFFFFL) * l + ((long)arrn[4] & 0xFFFFFFFFL));
        arrn2[4] = (int)l2;
        l2 = (l2 >>> 32) + (((long)arrn2[5] & 0xFFFFFFFFL) * l + ((long)arrn[5] & 0xFFFFFFFFL));
        arrn2[5] = (int)l2;
        l = (l2 >>> 32) + (((long)arrn2[6] & 0xFFFFFFFFL) * l + (0xFFFFFFFFL & (long)arrn[6]));
        arrn2[6] = (int)l;
        return (int)(l >>> 32);
    }

    public static int mulWord(int n, int[] arrn, int[] arrn2, int n2) {
        long l = 0L;
        long l2 = n;
        n = 0;
        do {
            arrn2[n2 + n] = (int)(l += ((long)arrn[n] & 0xFFFFFFFFL) * (l2 & 0xFFFFFFFFL));
            l >>>= 32;
        } while (++n < 7);
        return (int)l;
    }

    public static int mulWordAddTo(int n, int[] arrn, int n2, int[] arrn2, int n3) {
        long l = (long)n & 0xFFFFFFFFL;
        long l2 = 0L + (((long)arrn[n2 + 0] & 0xFFFFFFFFL) * l + ((long)arrn2[n3 + 0] & 0xFFFFFFFFL));
        arrn2[n3 + 0] = (int)l2;
        l2 = (l2 >>> 32) + (((long)arrn[n2 + 1] & 0xFFFFFFFFL) * l + ((long)arrn2[n3 + 1] & 0xFFFFFFFFL));
        arrn2[n3 + 1] = (int)l2;
        l2 = (l2 >>> 32) + (((long)arrn[n2 + 2] & 0xFFFFFFFFL) * l + ((long)arrn2[n3 + 2] & 0xFFFFFFFFL));
        arrn2[n3 + 2] = (int)l2;
        l2 = (l2 >>> 32) + (((long)arrn[n2 + 3] & 0xFFFFFFFFL) * l + ((long)arrn2[n3 + 3] & 0xFFFFFFFFL));
        arrn2[n3 + 3] = (int)l2;
        l2 = (l2 >>> 32) + (((long)arrn[n2 + 4] & 0xFFFFFFFFL) * l + ((long)arrn2[n3 + 4] & 0xFFFFFFFFL));
        arrn2[n3 + 4] = (int)l2;
        l2 = (l2 >>> 32) + (((long)arrn[n2 + 5] & 0xFFFFFFFFL) * l + ((long)arrn2[n3 + 5] & 0xFFFFFFFFL));
        arrn2[n3 + 5] = (int)l2;
        l = (l2 >>> 32) + (((long)arrn[n2 + 6] & 0xFFFFFFFFL) * l + (0xFFFFFFFFL & (long)arrn2[n3 + 6]));
        arrn2[n3 + 6] = (int)l;
        return (int)(l >>> 32);
    }

    public static int mulWordDwordAdd(int n, long l, int[] arrn, int n2) {
        long l2 = (long)n & 0xFFFFFFFFL;
        long l3 = 0L + ((l & 0xFFFFFFFFL) * l2 + ((long)arrn[n2 + 0] & 0xFFFFFFFFL));
        arrn[n2 + 0] = (int)l3;
        l = (l3 >>> 32) + ((l >>> 32) * l2 + ((long)arrn[n2 + 1] & 0xFFFFFFFFL));
        arrn[n2 + 1] = (int)l;
        l = (l >>> 32) + (0xFFFFFFFFL & (long)arrn[n2 + 2]);
        arrn[n2 + 2] = (int)l;
        n = l >>> 32 == 0L ? 0 : Nat.incAt(7, arrn, n2, 3);
        return n;
    }

    public static void square(int[] arrn, int n, int[] arrn2, int n2) {
        long l = (long)arrn[n + 0] & 0xFFFFFFFFL;
        int n3 = 0;
        int n4 = 6;
        int n5 = 14;
        do {
            int n6 = n4 - 1;
            long l2 = (long)arrn[n + n4] & 0xFFFFFFFFL;
            l2 *= l2;
            arrn2[n2 + --n5] = n3 << 31 | (int)(l2 >>> 33);
            arrn2[n2 + --n5] = (int)(l2 >>> 1);
            n3 = (int)l2;
            if (n6 <= 0) {
                long l3 = l * l;
                long l4 = n3 << 31;
                arrn2[n2 + 0] = (int)l3;
                n5 = (int)(l3 >>> 32);
                l2 = (long)arrn[n + 1] & 0xFFFFFFFFL;
                long l5 = arrn2[n2 + 2];
                long l6 = (l4 & 0xFFFFFFFFL | l3 >>> 33) + l2 * l;
                n3 = (int)l6;
                arrn2[n2 + 1] = n3 << 1 | n5 & 1;
                l4 = (long)arrn[n + 2] & 0xFFFFFFFFL;
                long l7 = arrn2[n2 + 3];
                l3 = arrn2[n2 + 4];
                l5 = (l5 & 0xFFFFFFFFL) + (l6 >>> 32) + l4 * l;
                n5 = (int)l5;
                arrn2[n2 + 2] = n5 << 1 | n3 >>> 31;
                long l8 = (l7 & 0xFFFFFFFFL) + ((l5 >>> 32) + l4 * l2);
                l6 = (l3 & 0xFFFFFFFFL) + (l8 >>> 32);
                l3 = (long)arrn[n + 3] & 0xFFFFFFFFL;
                l5 = ((long)arrn2[n2 + 5] & 0xFFFFFFFFL) + (l6 >>> 32);
                l7 = arrn2[n2 + 6];
                l8 = (l8 & 0xFFFFFFFFL) + l3 * l;
                n3 = (int)l8;
                arrn2[n2 + 3] = n3 << 1 | n5 >>> 31;
                long l9 = (l6 & 0xFFFFFFFFL) + ((l8 >>> 32) + l3 * l2);
                l8 = (l5 & 0xFFFFFFFFL) + ((l9 >>> 32) + l3 * l4);
                long l10 = (l7 & 0xFFFFFFFFL) + (l5 >>> 32) + (l8 >>> 32);
                l5 = (long)arrn[n + 4] & 0xFFFFFFFFL;
                l6 = ((long)arrn2[n2 + 7] & 0xFFFFFFFFL) + (l10 >>> 32);
                l7 = arrn2[n2 + 8];
                l9 = (l9 & 0xFFFFFFFFL) + l5 * l;
                n5 = (int)l9;
                arrn2[n2 + 4] = n5 << 1 | n3 >>> 31;
                long l11 = (l8 & 0xFFFFFFFFL) + ((l9 >>> 32) + l5 * l2);
                l10 = (l10 & 0xFFFFFFFFL) + ((l11 >>> 32) + l5 * l4);
                l9 = (l6 & 0xFFFFFFFFL) + ((l10 >>> 32) + l5 * l3);
                long l12 = (l7 & 0xFFFFFFFFL) + (l6 >>> 32) + (l9 >>> 32);
                l7 = (long)arrn[n + 5] & 0xFFFFFFFFL;
                l6 = ((long)arrn2[n2 + 9] & 0xFFFFFFFFL) + (l12 >>> 32);
                l8 = arrn2[n2 + 10];
                l11 = (l11 & 0xFFFFFFFFL) + l7 * l;
                n3 = (int)l11;
                arrn2[n2 + 5] = n3 << 1 | n5 >>> 31;
                long l13 = (l10 & 0xFFFFFFFFL) + ((l11 >>> 32) + l7 * l2);
                l11 = (l9 & 0xFFFFFFFFL) + ((l13 >>> 32) + l7 * l4);
                l12 = (l12 & 0xFFFFFFFFL) + ((l11 >>> 32) + l7 * l3);
                l9 = (l6 & 0xFFFFFFFFL) + ((l12 >>> 32) + l7 * l5);
                long l14 = (l8 & 0xFFFFFFFFL) + (l6 >>> 32) + (l9 >>> 32);
                l10 = (long)arrn[n + 6] & 0xFFFFFFFFL;
                l6 = ((long)arrn2[n2 + 11] & 0xFFFFFFFFL) + (l14 >>> 32);
                l8 = arrn2[n2 + 12];
                l = (l13 & 0xFFFFFFFFL) + l10 * l;
                n = (int)l;
                arrn2[n2 + 6] = n << 1 | n3 >>> 31;
                l = (l11 & 0xFFFFFFFFL) + ((l >>> 32) + l10 * l2);
                l2 = (l12 & 0xFFFFFFFFL) + ((l >>> 32) + l10 * l4);
                l4 = (l9 & 0xFFFFFFFFL) + ((l2 >>> 32) + l10 * l3);
                l3 = (l14 & 0xFFFFFFFFL) + ((l4 >>> 32) + l10 * l5);
                l5 = (l6 & 0xFFFFFFFFL) + ((l3 >>> 32) + l10 * l7);
                l7 = (l8 & 0xFFFFFFFFL) + (l6 >>> 32) + (l5 >>> 32);
                n3 = (int)l;
                arrn2[n2 + 7] = n3 << 1 | n >>> 31;
                n = (int)l2;
                arrn2[n2 + 8] = n << 1 | n3 >>> 31;
                n3 = (int)l4;
                arrn2[n2 + 9] = n3 << 1 | n >>> 31;
                n = (int)l3;
                arrn2[n2 + 10] = n << 1 | n3 >>> 31;
                n3 = (int)l5;
                arrn2[n2 + 11] = n3 << 1 | n >>> 31;
                n = (int)l7;
                arrn2[n2 + 12] = n << 1 | n3 >>> 31;
                arrn2[n2 + 13] = arrn2[n2 + 13] + (int)(l7 >>> 32) << 1 | n >>> 31;
                return;
            }
            n4 = n6;
        } while (true);
    }

    public static void square(int[] arrn, int[] arrn2) {
        long l = (long)arrn[0] & 0xFFFFFFFFL;
        int n = 0;
        int n2 = 6;
        int n3 = 14;
        do {
            int n4 = n2 - 1;
            long l2 = (long)arrn[n2] & 0xFFFFFFFFL;
            l2 *= l2;
            n2 = n3 - 1;
            arrn2[n2] = n << 31 | (int)(l2 >>> 33);
            n3 = n2 - 1;
            arrn2[n3] = (int)(l2 >>> 1);
            n = (int)l2;
            if (n4 <= 0) {
                long l3 = l * l;
                long l4 = n << 31;
                arrn2[0] = (int)l3;
                n = (int)(l3 >>> 32);
                l2 = (long)arrn[1] & 0xFFFFFFFFL;
                long l5 = arrn2[2];
                long l6 = (l4 & 0xFFFFFFFFL | l3 >>> 33) + l2 * l;
                n2 = (int)l6;
                arrn2[1] = n2 << 1 | n & 1;
                l4 = (long)arrn[2] & 0xFFFFFFFFL;
                long l7 = arrn2[3];
                l3 = arrn2[4];
                l5 = (l5 & 0xFFFFFFFFL) + (l6 >>> 32) + l4 * l;
                n = (int)l5;
                arrn2[2] = n << 1 | n2 >>> 31;
                long l8 = (l7 & 0xFFFFFFFFL) + ((l5 >>> 32) + l4 * l2);
                l6 = (l3 & 0xFFFFFFFFL) + (l8 >>> 32);
                l5 = (long)arrn[3] & 0xFFFFFFFFL;
                l7 = ((long)arrn2[5] & 0xFFFFFFFFL) + (l6 >>> 32);
                l3 = arrn2[6];
                l8 = (l8 & 0xFFFFFFFFL) + l5 * l;
                n2 = (int)l8;
                arrn2[3] = n2 << 1 | n >>> 31;
                long l9 = (l6 & 0xFFFFFFFFL) + ((l8 >>> 32) + l5 * l2);
                l8 = (l7 & 0xFFFFFFFFL) + ((l9 >>> 32) + l5 * l4);
                long l10 = (l3 & 0xFFFFFFFFL) + (l7 >>> 32) + (l8 >>> 32);
                l3 = (long)arrn[4] & 0xFFFFFFFFL;
                l7 = ((long)arrn2[7] & 0xFFFFFFFFL) + (l10 >>> 32);
                l6 = arrn2[8];
                l9 = (l9 & 0xFFFFFFFFL) + l3 * l;
                n = (int)l9;
                arrn2[4] = n << 1 | n2 >>> 31;
                long l11 = (l8 & 0xFFFFFFFFL) + ((l9 >>> 32) + l3 * l2);
                l9 = (l10 & 0xFFFFFFFFL) + ((l11 >>> 32) + l3 * l4);
                l10 = (l7 & 0xFFFFFFFFL) + ((l9 >>> 32) + l3 * l5);
                long l12 = (l6 & 0xFFFFFFFFL) + (l7 >>> 32) + (l10 >>> 32);
                l7 = (long)arrn[5] & 0xFFFFFFFFL;
                l8 = ((long)arrn2[9] & 0xFFFFFFFFL) + (l12 >>> 32);
                l6 = arrn2[10];
                l11 = (l11 & 0xFFFFFFFFL) + l7 * l;
                n3 = (int)l11;
                arrn2[5] = n3 << 1 | n >>> 31;
                l11 = (l9 & 0xFFFFFFFFL) + ((l11 >>> 32) + l7 * l2);
                long l13 = (l10 & 0xFFFFFFFFL) + ((l11 >>> 32) + l7 * l4);
                long l14 = (l12 & 0xFFFFFFFFL) + ((l13 >>> 32) + l7 * l5);
                l9 = (l8 & 0xFFFFFFFFL) + ((l14 >>> 32) + l7 * l3);
                l12 = (l6 & 0xFFFFFFFFL) + (l8 >>> 32) + (l9 >>> 32);
                l10 = (long)arrn[6] & 0xFFFFFFFFL;
                l6 = ((long)arrn2[11] & 0xFFFFFFFFL) + (l12 >>> 32);
                l8 = arrn2[12];
                l = (l11 & 0xFFFFFFFFL) + l10 * l;
                n2 = (int)l;
                arrn2[6] = n2 << 1 | n3 >>> 31;
                l = (l13 & 0xFFFFFFFFL) + ((l >>> 32) + l10 * l2);
                l2 = (l14 & 0xFFFFFFFFL) + ((l >>> 32) + l10 * l4);
                l4 = (l9 & 0xFFFFFFFFL) + ((l2 >>> 32) + l10 * l5);
                l5 = (l12 & 0xFFFFFFFFL) + ((l4 >>> 32) + l10 * l3);
                l3 = (0xFFFFFFFFL & l6) + ((l5 >>> 32) + l10 * l7);
                l7 = (l8 & 0xFFFFFFFFL) + (l6 >>> 32) + (l3 >>> 32);
                n = (int)l;
                arrn2[7] = n << 1 | n2 >>> 31;
                n3 = (int)l2;
                arrn2[8] = n3 << 1 | n >>> 31;
                n2 = (int)l4;
                arrn2[9] = n2 << 1 | n3 >>> 31;
                n = (int)l5;
                arrn2[10] = n << 1 | n2 >>> 31;
                n2 = (int)l3;
                arrn2[11] = n2 << 1 | n >>> 31;
                n = (int)l7;
                arrn2[12] = n << 1 | n2 >>> 31;
                arrn2[13] = arrn2[13] + (int)(l7 >>> 32) << 1 | n >>> 31;
                return;
            }
            n2 = n4;
        } while (true);
    }

    public static int sub(int[] arrn, int n, int[] arrn2, int n2, int[] arrn3, int n3) {
        long l = 0L + (((long)arrn[n + 0] & 0xFFFFFFFFL) - ((long)arrn2[n2 + 0] & 0xFFFFFFFFL));
        arrn3[n3 + 0] = (int)l;
        l = (l >> 32) + (((long)arrn[n + 1] & 0xFFFFFFFFL) - ((long)arrn2[n2 + 1] & 0xFFFFFFFFL));
        arrn3[n3 + 1] = (int)l;
        l = (l >> 32) + (((long)arrn[n + 2] & 0xFFFFFFFFL) - ((long)arrn2[n2 + 2] & 0xFFFFFFFFL));
        arrn3[n3 + 2] = (int)l;
        l = (l >> 32) + (((long)arrn[n + 3] & 0xFFFFFFFFL) - ((long)arrn2[n2 + 3] & 0xFFFFFFFFL));
        arrn3[n3 + 3] = (int)l;
        l = (l >> 32) + (((long)arrn[n + 4] & 0xFFFFFFFFL) - ((long)arrn2[n2 + 4] & 0xFFFFFFFFL));
        arrn3[n3 + 4] = (int)l;
        l = (l >> 32) + (((long)arrn[n + 5] & 0xFFFFFFFFL) - ((long)arrn2[n2 + 5] & 0xFFFFFFFFL));
        arrn3[n3 + 5] = (int)l;
        l = (l >> 32) + (((long)arrn[n + 6] & 0xFFFFFFFFL) - ((long)arrn2[n2 + 6] & 0xFFFFFFFFL));
        arrn3[n3 + 6] = (int)l;
        return (int)(l >> 32);
    }

    public static int sub(int[] arrn, int[] arrn2, int[] arrn3) {
        long l = 0L + (((long)arrn[0] & 0xFFFFFFFFL) - ((long)arrn2[0] & 0xFFFFFFFFL));
        arrn3[0] = (int)l;
        l = (l >> 32) + (((long)arrn[1] & 0xFFFFFFFFL) - ((long)arrn2[1] & 0xFFFFFFFFL));
        arrn3[1] = (int)l;
        l = (l >> 32) + (((long)arrn[2] & 0xFFFFFFFFL) - ((long)arrn2[2] & 0xFFFFFFFFL));
        arrn3[2] = (int)l;
        l = (l >> 32) + (((long)arrn[3] & 0xFFFFFFFFL) - ((long)arrn2[3] & 0xFFFFFFFFL));
        arrn3[3] = (int)l;
        l = (l >> 32) + (((long)arrn[4] & 0xFFFFFFFFL) - ((long)arrn2[4] & 0xFFFFFFFFL));
        arrn3[4] = (int)l;
        l = (l >> 32) + (((long)arrn[5] & 0xFFFFFFFFL) - ((long)arrn2[5] & 0xFFFFFFFFL));
        arrn3[5] = (int)l;
        l = (l >> 32) + (((long)arrn[6] & 0xFFFFFFFFL) - ((long)arrn2[6] & 0xFFFFFFFFL));
        arrn3[6] = (int)l;
        return (int)(l >> 32);
    }

    public static int subBothFrom(int[] arrn, int[] arrn2, int[] arrn3) {
        long l = 0L + (((long)arrn3[0] & 0xFFFFFFFFL) - ((long)arrn[0] & 0xFFFFFFFFL) - ((long)arrn2[0] & 0xFFFFFFFFL));
        arrn3[0] = (int)l;
        l = (l >> 32) + (((long)arrn3[1] & 0xFFFFFFFFL) - ((long)arrn[1] & 0xFFFFFFFFL) - ((long)arrn2[1] & 0xFFFFFFFFL));
        arrn3[1] = (int)l;
        l = (l >> 32) + (((long)arrn3[2] & 0xFFFFFFFFL) - ((long)arrn[2] & 0xFFFFFFFFL) - ((long)arrn2[2] & 0xFFFFFFFFL));
        arrn3[2] = (int)l;
        l = (l >> 32) + (((long)arrn3[3] & 0xFFFFFFFFL) - ((long)arrn[3] & 0xFFFFFFFFL) - ((long)arrn2[3] & 0xFFFFFFFFL));
        arrn3[3] = (int)l;
        l = (l >> 32) + (((long)arrn3[4] & 0xFFFFFFFFL) - ((long)arrn[4] & 0xFFFFFFFFL) - ((long)arrn2[4] & 0xFFFFFFFFL));
        arrn3[4] = (int)l;
        l = (l >> 32) + (((long)arrn3[5] & 0xFFFFFFFFL) - ((long)arrn[5] & 0xFFFFFFFFL) - ((long)arrn2[5] & 0xFFFFFFFFL));
        arrn3[5] = (int)l;
        l = (l >> 32) + (((long)arrn3[6] & 0xFFFFFFFFL) - ((long)arrn[6] & 0xFFFFFFFFL) - ((long)arrn2[6] & 0xFFFFFFFFL));
        arrn3[6] = (int)l;
        return (int)(l >> 32);
    }

    public static int subFrom(int[] arrn, int n, int[] arrn2, int n2) {
        long l = 0L + (((long)arrn2[n2 + 0] & 0xFFFFFFFFL) - ((long)arrn[n + 0] & 0xFFFFFFFFL));
        arrn2[n2 + 0] = (int)l;
        l = (l >> 32) + (((long)arrn2[n2 + 1] & 0xFFFFFFFFL) - ((long)arrn[n + 1] & 0xFFFFFFFFL));
        arrn2[n2 + 1] = (int)l;
        l = (l >> 32) + (((long)arrn2[n2 + 2] & 0xFFFFFFFFL) - ((long)arrn[n + 2] & 0xFFFFFFFFL));
        arrn2[n2 + 2] = (int)l;
        l = (l >> 32) + (((long)arrn2[n2 + 3] & 0xFFFFFFFFL) - ((long)arrn[n + 3] & 0xFFFFFFFFL));
        arrn2[n2 + 3] = (int)l;
        l = (l >> 32) + (((long)arrn2[n2 + 4] & 0xFFFFFFFFL) - ((long)arrn[n + 4] & 0xFFFFFFFFL));
        arrn2[n2 + 4] = (int)l;
        l = (l >> 32) + (((long)arrn2[n2 + 5] & 0xFFFFFFFFL) - ((long)arrn[n + 5] & 0xFFFFFFFFL));
        arrn2[n2 + 5] = (int)l;
        l = (l >> 32) + (((long)arrn2[n2 + 6] & 0xFFFFFFFFL) - ((long)arrn[n + 6] & 0xFFFFFFFFL));
        arrn2[n2 + 6] = (int)l;
        return (int)(l >> 32);
    }

    public static int subFrom(int[] arrn, int[] arrn2) {
        long l = 0L + (((long)arrn2[0] & 0xFFFFFFFFL) - ((long)arrn[0] & 0xFFFFFFFFL));
        arrn2[0] = (int)l;
        l = (l >> 32) + (((long)arrn2[1] & 0xFFFFFFFFL) - ((long)arrn[1] & 0xFFFFFFFFL));
        arrn2[1] = (int)l;
        l = (l >> 32) + (((long)arrn2[2] & 0xFFFFFFFFL) - ((long)arrn[2] & 0xFFFFFFFFL));
        arrn2[2] = (int)l;
        l = (l >> 32) + (((long)arrn2[3] & 0xFFFFFFFFL) - ((long)arrn[3] & 0xFFFFFFFFL));
        arrn2[3] = (int)l;
        l = (l >> 32) + (((long)arrn2[4] & 0xFFFFFFFFL) - ((long)arrn[4] & 0xFFFFFFFFL));
        arrn2[4] = (int)l;
        l = (l >> 32) + (((long)arrn2[5] & 0xFFFFFFFFL) - ((long)arrn[5] & 0xFFFFFFFFL));
        arrn2[5] = (int)l;
        l = (l >> 32) + (((long)arrn2[6] & 0xFFFFFFFFL) - ((long)arrn[6] & 0xFFFFFFFFL));
        arrn2[6] = (int)l;
        return (int)(l >> 32);
    }

    public static BigInteger toBigInteger(int[] arrn) {
        byte[] arrby = new byte[28];
        for (int i = 0; i < 7; ++i) {
            int n = arrn[i];
            if (n == 0) continue;
            Pack.intToBigEndian(n, arrby, 6 - i << 2);
        }
        return new BigInteger(1, arrby);
    }

    public static void zero(int[] arrn) {
        arrn[0] = 0;
        arrn[1] = 0;
        arrn[2] = 0;
        arrn[3] = 0;
        arrn[4] = 0;
        arrn[5] = 0;
        arrn[6] = 0;
    }
}

