/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.raw;

import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.util.Pack;
import java.math.BigInteger;

public abstract class Nat192 {
    private static final long M = 0xFFFFFFFFL;

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
        l = (l >>> 32) + (((long)arrn[n + 5] & 0xFFFFFFFFL) + (0xFFFFFFFFL & (long)arrn2[n2 + 5]));
        arrn2[n2 + 5] = (int)l;
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
        return (int)(l >>> 32);
    }

    public static void copy(int[] arrn, int n, int[] arrn2, int n2) {
        arrn2[n2 + 0] = arrn[n + 0];
        arrn2[n2 + 1] = arrn[n + 1];
        arrn2[n2 + 2] = arrn[n + 2];
        arrn2[n2 + 3] = arrn[n + 3];
        arrn2[n2 + 4] = arrn[n + 4];
        arrn2[n2 + 5] = arrn[n + 5];
    }

    public static void copy(int[] arrn, int[] arrn2) {
        arrn2[0] = arrn[0];
        arrn2[1] = arrn[1];
        arrn2[2] = arrn[2];
        arrn2[3] = arrn[3];
        arrn2[4] = arrn[4];
        arrn2[5] = arrn[5];
    }

    public static void copy64(long[] arrl, int n, long[] arrl2, int n2) {
        arrl2[n2 + 0] = arrl[n + 0];
        arrl2[n2 + 1] = arrl[n + 1];
        arrl2[n2 + 2] = arrl[n + 2];
    }

    public static void copy64(long[] arrl, long[] arrl2) {
        arrl2[0] = arrl[0];
        arrl2[1] = arrl[1];
        arrl2[2] = arrl[2];
    }

    public static int[] create() {
        return new int[6];
    }

    public static long[] create64() {
        return new long[3];
    }

    public static int[] createExt() {
        return new int[12];
    }

    public static long[] createExt64() {
        return new long[6];
    }

    public static boolean diff(int[] arrn, int n, int[] arrn2, int n2, int[] arrn3, int n3) {
        boolean bl = Nat192.gte(arrn, n, arrn2, n2);
        if (bl) {
            Nat192.sub(arrn, n, arrn2, n2, arrn3, n3);
        } else {
            Nat192.sub(arrn2, n2, arrn, n, arrn3, n3);
        }
        return bl;
    }

    public static boolean eq(int[] arrn, int[] arrn2) {
        for (int i = 5; i >= 0; --i) {
            if (arrn[i] == arrn2[i]) continue;
            return false;
        }
        return true;
    }

    public static boolean eq64(long[] arrl, long[] arrl2) {
        for (int i = 2; i >= 0; --i) {
            if (arrl[i] == arrl2[i]) continue;
            return false;
        }
        return true;
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        if (bigInteger.signum() >= 0 && bigInteger.bitLength() <= 192) {
            int[] arrn = Nat192.create();
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

    public static long[] fromBigInteger64(BigInteger bigInteger) {
        if (bigInteger.signum() >= 0 && bigInteger.bitLength() <= 192) {
            long[] arrl = Nat192.create64();
            int n = 0;
            while (bigInteger.signum() != 0) {
                arrl[n] = bigInteger.longValue();
                bigInteger = bigInteger.shiftRight(64);
                ++n;
            }
            return arrl;
        }
        throw new IllegalArgumentException();
    }

    public static int getBit(int[] arrn, int n) {
        if (n == 0) {
            return arrn[0] & 1;
        }
        int n2 = n >> 5;
        if (n2 >= 0 && n2 < 6) {
            return arrn[n2] >>> (n & 31) & 1;
        }
        return 0;
    }

    public static boolean gte(int[] arrn, int n, int[] arrn2, int n2) {
        for (int i = 5; i >= 0; --i) {
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
        for (int i = 5; i >= 0; --i) {
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
        for (int i = 1; i < 6; ++i) {
            if (arrn[i] == 0) continue;
            return false;
        }
        return true;
    }

    public static boolean isOne64(long[] arrl) {
        if (arrl[0] != 1L) {
            return false;
        }
        for (int i = 1; i < 3; ++i) {
            if (arrl[i] == 0L) continue;
            return false;
        }
        return true;
    }

    public static boolean isZero(int[] arrn) {
        for (int i = 0; i < 6; ++i) {
            if (arrn[i] == 0) continue;
            return false;
        }
        return true;
    }

    public static boolean isZero64(long[] arrl) {
        for (int i = 0; i < 3; ++i) {
            if (arrl[i] == 0L) continue;
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
        long l7 = (long)arrn[n + 0] & 0xFFFFFFFFL;
        long l8 = 0L + l7 * l;
        arrn3[n3 + 0] = (int)l8;
        l8 = (l8 >>> 32) + l7 * l2;
        arrn3[n3 + 1] = (int)l8;
        l8 = (l8 >>> 32) + l7 * l3;
        arrn3[n3 + 2] = (int)l8;
        l8 = (l8 >>> 32) + l7 * l4;
        arrn3[n3 + 3] = (int)l8;
        l8 = (l8 >>> 32) + l7 * l5;
        arrn3[n3 + 4] = (int)l8;
        l7 = (l8 >>> 32) + l7 * l6;
        arrn3[n3 + 5] = (int)l7;
        arrn3[n3 + 6] = (int)(l7 >>> 32);
        for (n2 = 1; n2 < 6; ++n2) {
            l7 = (long)arrn[n + n2] & 0xFFFFFFFFL;
            l8 = 0L + (l7 * l + ((long)arrn3[++n3 + 0] & 0xFFFFFFFFL));
            arrn3[n3 + 0] = (int)l8;
            l8 = (l8 >>> 32) + (l7 * l2 + ((long)arrn3[n3 + 1] & 0xFFFFFFFFL));
            arrn3[n3 + 1] = (int)l8;
            l8 = (l8 >>> 32) + (l7 * l3 + ((long)arrn3[n3 + 2] & 0xFFFFFFFFL));
            arrn3[n3 + 2] = (int)l8;
            l8 = (l8 >>> 32) + (l7 * l4 + ((long)arrn3[n3 + 3] & 0xFFFFFFFFL));
            arrn3[n3 + 3] = (int)l8;
            l8 = (l8 >>> 32) + (l7 * l5 + ((long)arrn3[n3 + 4] & 0xFFFFFFFFL));
            arrn3[n3 + 4] = (int)l8;
            l7 = (l8 >>> 32) + (l7 * l6 + ((long)arrn3[n3 + 5] & 0xFFFFFFFFL));
            arrn3[n3 + 5] = (int)l7;
            arrn3[n3 + 6] = (int)(l7 >>> 32);
        }
    }

    public static void mul(int[] arrn, int[] arrn2, int[] arrn3) {
        long l = (long)arrn2[0] & 0xFFFFFFFFL;
        long l2 = (long)arrn2[1] & 0xFFFFFFFFL;
        long l3 = (long)arrn2[2] & 0xFFFFFFFFL;
        long l4 = (long)arrn2[3] & 0xFFFFFFFFL;
        long l5 = (long)arrn2[4] & 0xFFFFFFFFL;
        long l6 = (long)arrn2[5] & 0xFFFFFFFFL;
        long l7 = (long)arrn[0] & 0xFFFFFFFFL;
        long l8 = 0L + l7 * l;
        arrn3[0] = (int)l8;
        l8 = (l8 >>> 32) + l7 * l2;
        arrn3[1] = (int)l8;
        l8 = (l8 >>> 32) + l7 * l3;
        arrn3[2] = (int)l8;
        l8 = (l8 >>> 32) + l7 * l4;
        arrn3[3] = (int)l8;
        l8 = (l8 >>> 32) + l7 * l5;
        arrn3[4] = (int)l8;
        l7 = (l8 >>> 32) + l7 * l6;
        arrn3[5] = (int)l7;
        arrn3[6] = (int)(l7 >>> 32);
        for (int i = 1; i < 6; ++i) {
            l7 = (long)arrn[i] & 0xFFFFFFFFL;
            l8 = 0L + (l7 * l + ((long)arrn3[i + 0] & 0xFFFFFFFFL));
            arrn3[i + 0] = (int)l8;
            l8 = (l8 >>> 32) + (l7 * l2 + ((long)arrn3[i + 1] & 0xFFFFFFFFL));
            arrn3[i + 1] = (int)l8;
            l8 = (l8 >>> 32) + (l7 * l3 + ((long)arrn3[i + 2] & 0xFFFFFFFFL));
            arrn3[i + 2] = (int)l8;
            l8 = (l8 >>> 32) + (l7 * l4 + ((long)arrn3[i + 3] & 0xFFFFFFFFL));
            arrn3[i + 3] = (int)l8;
            l8 = (l8 >>> 32) + (l7 * l5 + ((long)arrn3[i + 4] & 0xFFFFFFFFL));
            arrn3[i + 4] = (int)l8;
            l7 = (l8 >>> 32) + (l7 * l6 + ((long)arrn3[i + 5] & 0xFFFFFFFFL));
            arrn3[i + 5] = (int)l7;
            arrn3[i + 6] = (int)(l7 >>> 32);
        }
    }

    public static long mul33Add(int n, int[] arrn, int n2, int[] arrn2, int n3, int[] arrn3, int n4) {
        long l = (long)n & 0xFFFFFFFFL;
        long l2 = (long)arrn[n2 + 0] & 0xFFFFFFFFL;
        long l3 = 0L + (l * l2 + ((long)arrn2[n3 + 0] & 0xFFFFFFFFL));
        arrn3[n4 + 0] = (int)l3;
        long l4 = (long)arrn[n2 + 1] & 0xFFFFFFFFL;
        l3 = (l3 >>> 32) + (l * l4 + l2 + ((long)arrn2[n3 + 1] & 0xFFFFFFFFL));
        arrn3[n4 + 1] = (int)l3;
        l2 = (long)arrn[n2 + 2] & 0xFFFFFFFFL;
        l4 = (l3 >>> 32) + (l * l2 + l4 + ((long)arrn2[n3 + 2] & 0xFFFFFFFFL));
        arrn3[n4 + 2] = (int)l4;
        l3 = (long)arrn[n2 + 3] & 0xFFFFFFFFL;
        l2 = (l4 >>> 32) + (l * l3 + l2 + ((long)arrn2[n3 + 3] & 0xFFFFFFFFL));
        arrn3[n4 + 3] = (int)l2;
        l4 = (long)arrn[n2 + 4] & 0xFFFFFFFFL;
        l2 = (l2 >>> 32) + (l * l4 + l3 + ((long)arrn2[n3 + 4] & 0xFFFFFFFFL));
        arrn3[n4 + 4] = (int)l2;
        l3 = (long)arrn[n2 + 5] & 0xFFFFFFFFL;
        l = (l2 >>> 32) + (l * l3 + l4 + ((long)arrn2[n3 + 5] & 0xFFFFFFFFL));
        arrn3[n4 + 5] = (int)l;
        return (l >>> 32) + l3;
    }

    public static int mul33DWordAdd(int n, long l, int[] arrn, int n2) {
        long l2 = (long)n & 0xFFFFFFFFL;
        long l3 = l & 0xFFFFFFFFL;
        long l4 = 0L + (l2 * l3 + ((long)arrn[n2 + 0] & 0xFFFFFFFFL));
        arrn[n2 + 0] = (int)l4;
        l3 = (l4 >>> 32) + (l2 * (l >>>= 32) + l3 + ((long)arrn[n2 + 1] & 0xFFFFFFFFL));
        arrn[n2 + 1] = (int)l3;
        l = (l3 >>> 32) + (((long)arrn[n2 + 2] & 0xFFFFFFFFL) + l);
        arrn[n2 + 2] = (int)l;
        l = (l >>> 32) + ((long)arrn[n2 + 3] & 0xFFFFFFFFL);
        arrn[n2 + 3] = (int)l;
        n = l >>> 32 == 0L ? 0 : Nat.incAt(6, arrn, n2, 4);
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
        n = l2 >>> 32 == 0L ? 0 : Nat.incAt(6, arrn, n3, 3);
        return n;
    }

    public static int mulAddTo(int[] arrn, int n, int[] arrn2, int n2, int[] arrn3, int n3) {
        long l = (long)arrn2[n2 + 0] & 0xFFFFFFFFL;
        long l2 = (long)arrn2[n2 + 1] & 0xFFFFFFFFL;
        long l3 = arrn2[n2 + 2];
        long l4 = arrn2[n2 + 3];
        long l5 = arrn2[n2 + 4];
        long l6 = (long)arrn2[n2 + 5] & 0xFFFFFFFFL;
        long l7 = 0L;
        for (n2 = 0; n2 < 6; ++n2) {
            long l8 = (long)arrn[n + n2] & 0xFFFFFFFFL;
            long l9 = 0L + (l8 * l + ((long)arrn3[n3 + 0] & 0xFFFFFFFFL));
            arrn3[n3 + 0] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * l2 + ((long)arrn3[n3 + 1] & 0xFFFFFFFFL));
            arrn3[n3 + 1] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * (l3 & 0xFFFFFFFFL) + ((long)arrn3[n3 + 2] & 0xFFFFFFFFL));
            arrn3[n3 + 2] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * (l4 & 0xFFFFFFFFL) + ((long)arrn3[n3 + 3] & 0xFFFFFFFFL));
            arrn3[n3 + 3] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * (l5 & 0xFFFFFFFFL) + ((long)arrn3[n3 + 4] & 0xFFFFFFFFL));
            arrn3[n3 + 4] = (int)l9;
            l8 = (l9 >>> 32) + (l8 * l6 + ((long)arrn3[n3 + 5] & 0xFFFFFFFFL));
            arrn3[n3 + 5] = (int)l8;
            l7 = (l8 >>> 32) + (l7 + ((long)arrn3[n3 + 6] & 0xFFFFFFFFL));
            arrn3[n3 + 6] = (int)l7;
            l7 >>>= 32;
            ++n3;
        }
        return (int)l7;
    }

    public static int mulAddTo(int[] arrn, int[] arrn2, int[] arrn3) {
        long l = (long)arrn2[0] & 0xFFFFFFFFL;
        long l2 = (long)arrn2[1] & 0xFFFFFFFFL;
        long l3 = arrn2[2];
        long l4 = arrn2[3];
        long l5 = arrn2[4];
        long l6 = (long)arrn2[5] & 0xFFFFFFFFL;
        long l7 = 0L;
        for (int i = 0; i < 6; ++i) {
            long l8 = (long)arrn[i] & 0xFFFFFFFFL;
            long l9 = 0L + (l8 * l + ((long)arrn3[i + 0] & 0xFFFFFFFFL));
            arrn3[i + 0] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * l2 + ((long)arrn3[i + 1] & 0xFFFFFFFFL));
            arrn3[i + 1] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * (l3 & 0xFFFFFFFFL) + ((long)arrn3[i + 2] & 0xFFFFFFFFL));
            arrn3[i + 2] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * (l4 & 0xFFFFFFFFL) + ((long)arrn3[i + 3] & 0xFFFFFFFFL));
            arrn3[i + 3] = (int)l9;
            l9 = (l9 >>> 32) + (l8 * (l5 & 0xFFFFFFFFL) + ((long)arrn3[i + 4] & 0xFFFFFFFFL));
            arrn3[i + 4] = (int)l9;
            l8 = (l9 >>> 32) + (l8 * l6 + ((long)arrn3[i + 5] & 0xFFFFFFFFL));
            arrn3[i + 5] = (int)l8;
            l7 = (l8 >>> 32) + (l7 + ((long)arrn3[i + 6] & 0xFFFFFFFFL));
            arrn3[i + 6] = (int)l7;
            l7 >>>= 32;
        }
        return (int)l7;
    }

    public static int mulWord(int n, int[] arrn, int[] arrn2, int n2) {
        long l = 0L;
        long l2 = n;
        n = 0;
        do {
            arrn2[n2 + n] = (int)(l += ((long)arrn[n] & 0xFFFFFFFFL) * (l2 & 0xFFFFFFFFL));
            l >>>= 32;
        } while (++n < 6);
        return (int)l;
    }

    public static int mulWordAddExt(int n, int[] arrn, int n2, int[] arrn2, int n3) {
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
        l = (l2 >>> 32) + (((long)arrn[n2 + 5] & 0xFFFFFFFFL) * l + (0xFFFFFFFFL & (long)arrn2[n3 + 5]));
        arrn2[n3 + 5] = (int)l;
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
        n = l >>> 32 == 0L ? 0 : Nat.incAt(6, arrn, n2, 3);
        return n;
    }

    public static void square(int[] arrn, int n, int[] arrn2, int n2) {
        long l = (long)arrn[n + 0] & 0xFFFFFFFFL;
        int n3 = 0;
        int n4 = 5;
        int n5 = 12;
        do {
            int n6 = n4 - 1;
            long l2 = (long)arrn[n + n4] & 0xFFFFFFFFL;
            l2 *= l2;
            n4 = n5 - 1;
            arrn2[n2 + n4] = n3 << 31 | (int)(l2 >>> 33);
            n5 = n4 - 1;
            arrn2[n2 + n5] = (int)(l2 >>> 1);
            n3 = (int)l2;
            if (n6 <= 0) {
                long l3 = l * l;
                long l4 = n3 << 31;
                arrn2[n2 + 0] = (int)l3;
                n3 = (int)(l3 >>> 32);
                l2 = (long)arrn[n + 1] & 0xFFFFFFFFL;
                long l5 = arrn2[n2 + 2];
                long l6 = (l4 & 0xFFFFFFFFL | l3 >>> 33) + l2 * l;
                n4 = (int)l6;
                arrn2[n2 + 1] = n4 << 1 | n3 & 1;
                l4 = (long)arrn[n + 2] & 0xFFFFFFFFL;
                long l7 = arrn2[n2 + 3];
                l3 = arrn2[n2 + 4];
                l5 = (l5 & 0xFFFFFFFFL) + (l6 >>> 32) + l4 * l;
                n3 = (int)l5;
                arrn2[n2 + 2] = n3 << 1 | n4 >>> 31;
                long l8 = (l7 & 0xFFFFFFFFL) + ((l5 >>> 32) + l4 * l2);
                l6 = (l3 & 0xFFFFFFFFL) + (l8 >>> 32);
                l3 = (long)arrn[n + 3] & 0xFFFFFFFFL;
                l7 = ((long)arrn2[n2 + 5] & 0xFFFFFFFFL) + (l6 >>> 32);
                l5 = arrn2[n2 + 6];
                l8 = (l8 & 0xFFFFFFFFL) + l3 * l;
                n4 = (int)l8;
                arrn2[n2 + 3] = n4 << 1 | n3 >>> 31;
                long l9 = (l6 & 0xFFFFFFFFL) + ((l8 >>> 32) + l3 * l2);
                l8 = (l7 & 0xFFFFFFFFL) + ((l9 >>> 32) + l3 * l4);
                long l10 = (l5 & 0xFFFFFFFFL) + (l7 >>> 32) + (l8 >>> 32);
                l5 = (long)arrn[n + 4] & 0xFFFFFFFFL;
                l6 = ((long)arrn2[n2 + 7] & 0xFFFFFFFFL) + (l10 >>> 32);
                l7 = arrn2[n2 + 8];
                l9 = (l9 & 0xFFFFFFFFL) + l5 * l;
                n3 = (int)l9;
                arrn2[n2 + 4] = n3 << 1 | n4 >>> 31;
                long l11 = (l8 & 0xFFFFFFFFL) + ((l9 >>> 32) + l5 * l2);
                l10 = (l10 & 0xFFFFFFFFL) + ((l11 >>> 32) + l5 * l4);
                l8 = (l6 & 0xFFFFFFFFL) + ((l10 >>> 32) + l5 * l3);
                long l12 = (l7 & 0xFFFFFFFFL) + (l6 >>> 32) + (l8 >>> 32);
                l9 = (long)arrn[n + 5] & 0xFFFFFFFFL;
                l6 = ((long)arrn2[n2 + 9] & 0xFFFFFFFFL) + (l12 >>> 32);
                l7 = arrn2[n2 + 10];
                l = (l11 & 0xFFFFFFFFL) + l9 * l;
                n = (int)l;
                arrn2[n2 + 5] = n << 1 | n3 >>> 31;
                l = (l10 & 0xFFFFFFFFL) + ((l >>> 32) + l9 * l2);
                l2 = (l8 & 0xFFFFFFFFL) + ((l >>> 32) + l9 * l4);
                l4 = (l12 & 0xFFFFFFFFL) + ((l2 >>> 32) + l9 * l3);
                l3 = (l6 & 0xFFFFFFFFL) + ((l4 >>> 32) + l9 * l5);
                l5 = (l7 & 0xFFFFFFFFL) + (l6 >>> 32) + (l3 >>> 32);
                n3 = (int)l;
                arrn2[n2 + 6] = n3 << 1 | n >>> 31;
                n = (int)l2;
                arrn2[n2 + 7] = n << 1 | n3 >>> 31;
                n3 = (int)l4;
                arrn2[n2 + 8] = n3 << 1 | n >>> 31;
                n = (int)l3;
                arrn2[n2 + 9] = n << 1 | n3 >>> 31;
                n3 = (int)l5;
                arrn2[n2 + 10] = n3 << 1 | n >>> 31;
                arrn2[n2 + 11] = arrn2[n2 + 11] + (int)(l5 >>> 32) << 1 | n3 >>> 31;
                return;
            }
            n4 = n6;
        } while (true);
    }

    public static void square(int[] arrn, int[] arrn2) {
        long l = (long)arrn[0] & 0xFFFFFFFFL;
        int n = 0;
        int n2 = 5;
        int n3 = 12;
        do {
            int n4 = n2 - 1;
            long l2 = (long)arrn[n2] & 0xFFFFFFFFL;
            l2 *= l2;
            arrn2[--n3] = n << 31 | (int)(l2 >>> 33);
            arrn2[--n3] = (int)(l2 >>> 1);
            n = (int)l2;
            if (n4 <= 0) {
                long l3 = l * l;
                long l4 = n << 31;
                arrn2[0] = (int)l3;
                n = (int)(l3 >>> 32);
                l2 = (long)arrn[1] & 0xFFFFFFFFL;
                long l5 = arrn2[2];
                long l6 = (l4 & 0xFFFFFFFFL | l3 >>> 33) + l2 * l;
                n3 = (int)l6;
                arrn2[1] = n3 << 1 | n & 1;
                l4 = (long)arrn[2] & 0xFFFFFFFFL;
                long l7 = arrn2[3];
                l3 = arrn2[4];
                l5 = (l5 & 0xFFFFFFFFL) + (l6 >>> 32) + l4 * l;
                n = (int)l5;
                arrn2[2] = n << 1 | n3 >>> 31;
                l6 = (l7 & 0xFFFFFFFFL) + ((l5 >>> 32) + l4 * l2);
                long l8 = (l3 & 0xFFFFFFFFL) + (l6 >>> 32);
                l5 = (long)arrn[3] & 0xFFFFFFFFL;
                l3 = ((long)arrn2[5] & 0xFFFFFFFFL) + (l8 >>> 32);
                l7 = arrn2[6];
                l6 = (l6 & 0xFFFFFFFFL) + l5 * l;
                n2 = (int)l6;
                arrn2[3] = n2 << 1 | n >>> 31;
                long l9 = (l8 & 0xFFFFFFFFL) + ((l6 >>> 32) + l5 * l2);
                l8 = (l3 & 0xFFFFFFFFL) + ((l9 >>> 32) + l5 * l4);
                long l10 = (l7 & 0xFFFFFFFFL) + (l3 >>> 32) + (l8 >>> 32);
                l3 = (long)arrn[4] & 0xFFFFFFFFL;
                l6 = ((long)arrn2[7] & 0xFFFFFFFFL) + (l10 >>> 32);
                l7 = arrn2[8];
                l9 = (l9 & 0xFFFFFFFFL) + l3 * l;
                n3 = (int)l9;
                arrn2[4] = n3 << 1 | n2 >>> 31;
                long l11 = (l8 & 0xFFFFFFFFL) + ((l9 >>> 32) + l3 * l2);
                long l12 = (l10 & 0xFFFFFFFFL) + ((l11 >>> 32) + l3 * l4);
                l8 = (l6 & 0xFFFFFFFFL) + ((l12 >>> 32) + l3 * l5);
                l9 = (l7 & 0xFFFFFFFFL) + (l6 >>> 32) + (l8 >>> 32);
                l10 = (long)arrn[5] & 0xFFFFFFFFL;
                l7 = ((long)arrn2[9] & 0xFFFFFFFFL) + (l9 >>> 32);
                l6 = arrn2[10];
                l = (l11 & 0xFFFFFFFFL) + l10 * l;
                n = (int)l;
                arrn2[5] = n << 1 | n3 >>> 31;
                l = (l12 & 0xFFFFFFFFL) + ((l >>> 32) + l10 * l2);
                l2 = (l8 & 0xFFFFFFFFL) + ((l >>> 32) + l10 * l4);
                l4 = (l9 & 0xFFFFFFFFL) + ((l2 >>> 32) + l10 * l5);
                l5 = (l7 & 0xFFFFFFFFL) + ((l4 >>> 32) + l10 * l3);
                l3 = (l6 & 0xFFFFFFFFL) + (l7 >>> 32) + (l5 >>> 32);
                n3 = (int)l;
                arrn2[6] = n3 << 1 | n >>> 31;
                n = (int)l2;
                arrn2[7] = n << 1 | n3 >>> 31;
                n3 = (int)l4;
                arrn2[8] = n3 << 1 | n >>> 31;
                n = (int)l5;
                arrn2[9] = n << 1 | n3 >>> 31;
                n3 = (int)l3;
                arrn2[10] = n3 << 1 | n >>> 31;
                arrn2[11] = arrn2[11] + (int)(l3 >>> 32) << 1 | n3 >>> 31;
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
        return (int)(l >> 32);
    }

    public static BigInteger toBigInteger(int[] arrn) {
        byte[] arrby = new byte[24];
        for (int i = 0; i < 6; ++i) {
            int n = arrn[i];
            if (n == 0) continue;
            Pack.intToBigEndian(n, arrby, 5 - i << 2);
        }
        return new BigInteger(1, arrby);
    }

    public static BigInteger toBigInteger64(long[] arrl) {
        byte[] arrby = new byte[24];
        for (int i = 0; i < 3; ++i) {
            long l = arrl[i];
            if (l == 0L) continue;
            Pack.longToBigEndian(l, arrby, 2 - i << 3);
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
    }
}

