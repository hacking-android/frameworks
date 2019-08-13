/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat256;
import java.math.BigInteger;

public class SecP256R1Field {
    private static final long M = 0xFFFFFFFFL;
    static final int[] P = new int[]{-1, -1, -1, 0, 0, 0, 1, -1};
    private static final int P7 = -1;
    static final int[] PExt = new int[]{1, 0, 0, -2, -1, -1, -2, 1, -2, 1, -2, 1, 1, -2, 2, -2};
    private static final int PExt15s1 = Integer.MAX_VALUE;

    public static void add(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat256.add(arrn, arrn2, arrn3) != 0 || arrn3[7] == -1 && Nat256.gte(arrn3, P)) {
            SecP256R1Field.addPInvTo(arrn3);
        }
    }

    public static void addExt(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat.add(16, arrn, arrn2, arrn3) != 0 || arrn3[15] >>> 1 >= Integer.MAX_VALUE && Nat.gte(16, arrn3, PExt)) {
            Nat.subFrom(16, PExt, arrn3);
        }
    }

    public static void addOne(int[] arrn, int[] arrn2) {
        if (Nat.inc(8, arrn, arrn2) != 0 || arrn2[7] == -1 && Nat256.gte(arrn2, P)) {
            SecP256R1Field.addPInvTo(arrn2);
        }
    }

    private static void addPInvTo(int[] arrn) {
        long l;
        long l2 = ((long)arrn[0] & 0xFFFFFFFFL) + 1L;
        arrn[0] = (int)l2;
        l2 = l = l2 >> 32;
        if (l != 0L) {
            l2 = l + ((long)arrn[1] & 0xFFFFFFFFL);
            arrn[1] = (int)l2;
            l2 = (l2 >> 32) + ((long)arrn[2] & 0xFFFFFFFFL);
            arrn[2] = (int)l2;
            l2 >>= 32;
        }
        arrn[3] = (int)(l2 += ((long)arrn[3] & 0xFFFFFFFFL) - 1L);
        l2 = l = l2 >> 32;
        if (l != 0L) {
            l2 = l + ((long)arrn[4] & 0xFFFFFFFFL);
            arrn[4] = (int)l2;
            l2 = (l2 >> 32) + ((long)arrn[5] & 0xFFFFFFFFL);
            arrn[5] = (int)l2;
            l2 >>= 32;
        }
        arrn[6] = (int)(l2 += ((long)arrn[6] & 0xFFFFFFFFL) - 1L);
        arrn[7] = (int)((l2 >> 32) + ((0xFFFFFFFFL & (long)arrn[7]) + 1L));
    }

    public static int[] fromBigInteger(BigInteger arrn) {
        if ((arrn = Nat256.fromBigInteger((BigInteger)arrn))[7] == -1 && Nat256.gte(arrn, P)) {
            Nat256.subFrom(P, arrn);
        }
        return arrn;
    }

    public static void half(int[] arrn, int[] arrn2) {
        if ((arrn[0] & 1) == 0) {
            Nat.shiftDownBit(8, arrn, 0, arrn2);
        } else {
            Nat.shiftDownBit(8, arrn2, Nat256.add(arrn, P, arrn2));
        }
    }

    public static void multiply(int[] arrn, int[] arrn2, int[] arrn3) {
        int[] arrn4 = Nat256.createExt();
        Nat256.mul(arrn, arrn2, arrn4);
        SecP256R1Field.reduce(arrn4, arrn3);
    }

    public static void multiplyAddToExt(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat256.mulAddTo(arrn, arrn2, arrn3) != 0 || arrn3[15] >>> 1 >= Integer.MAX_VALUE && Nat.gte(16, arrn3, PExt)) {
            Nat.subFrom(16, PExt, arrn3);
        }
    }

    public static void negate(int[] arrn, int[] arrn2) {
        if (Nat256.isZero(arrn)) {
            Nat256.zero(arrn2);
        } else {
            Nat256.sub(P, arrn, arrn2);
        }
    }

    public static void reduce(int[] arrn, int[] arrn2) {
        long l = arrn[8];
        long l2 = (long)arrn[9] & 0xFFFFFFFFL;
        long l3 = (long)arrn[10] & 0xFFFFFFFFL;
        long l4 = (long)arrn[11] & 0xFFFFFFFFL;
        long l5 = (long)arrn[12] & 0xFFFFFFFFL;
        long l6 = (long)arrn[13] & 0xFFFFFFFFL;
        long l7 = (long)arrn[14] & 0xFFFFFFFFL;
        long l8 = (long)arrn[15] & 0xFFFFFFFFL;
        l = (l & 0xFFFFFFFFL) - 6L;
        long l9 = l2 + l3;
        l3 = l3 + l4 - l8;
        l4 += l5;
        l5 += l6;
        long l10 = l6 + l7;
        l6 = l7 + l8;
        l2 = l10 - (l + l2);
        long l11 = 0L + (((long)arrn[0] & 0xFFFFFFFFL) - l4 - l2);
        arrn2[0] = (int)l11;
        l11 = (l11 >> 32) + (((long)arrn[1] & 0xFFFFFFFFL) + l9 - l5 - l6);
        arrn2[1] = (int)l11;
        l11 = (l11 >> 32) + (((long)arrn[2] & 0xFFFFFFFFL) + l3 - l10);
        arrn2[2] = (int)l11;
        l4 = (l11 >> 32) + (((long)arrn[3] & 0xFFFFFFFFL) + (l4 << 1) + l2 - l6);
        arrn2[3] = (int)l4;
        l7 = (l4 >> 32) + (((long)arrn[4] & 0xFFFFFFFFL) + (l5 << 1) + l7 - l9);
        arrn2[4] = (int)l7;
        l7 = (l7 >> 32) + (((long)arrn[5] & 0xFFFFFFFFL) + (l10 << 1) - l3);
        arrn2[5] = (int)l7;
        l7 = (l7 >> 32) + (((long)arrn[6] & 0xFFFFFFFFL) + (l6 << 1) + l2);
        arrn2[6] = (int)l7;
        l8 = (l7 >> 32) + (((long)arrn[7] & 0xFFFFFFFFL) + (l8 << 1) + l - l3 - l5);
        arrn2[7] = (int)l8;
        SecP256R1Field.reduce32((int)((l8 >> 32) + 6L), arrn2);
    }

    public static void reduce32(int n, int[] arrn) {
        long l = 0L;
        if (n != 0) {
            long l2;
            long l3 = (long)n & 0xFFFFFFFFL;
            l = 0L + (((long)arrn[0] & 0xFFFFFFFFL) + l3);
            arrn[0] = (int)l;
            l = l2 = l >> 32;
            if (l2 != 0L) {
                l = l2 + ((long)arrn[1] & 0xFFFFFFFFL);
                arrn[1] = (int)l;
                l = (l >> 32) + ((long)arrn[2] & 0xFFFFFFFFL);
                arrn[2] = (int)l;
                l >>= 32;
            }
            arrn[3] = (int)(l += ((long)arrn[3] & 0xFFFFFFFFL) - l3);
            l = l2 = l >> 32;
            if (l2 != 0L) {
                l = l2 + ((long)arrn[4] & 0xFFFFFFFFL);
                arrn[4] = (int)l;
                l = (l >> 32) + ((long)arrn[5] & 0xFFFFFFFFL);
                arrn[5] = (int)l;
                l >>= 32;
            }
            arrn[6] = (int)(l += ((long)arrn[6] & 0xFFFFFFFFL) - l3);
            l = (l >> 32) + ((0xFFFFFFFFL & (long)arrn[7]) + l3);
            arrn[7] = (int)l;
            l >>= 32;
        }
        if (l != 0L || arrn[7] == -1 && Nat256.gte(arrn, P)) {
            SecP256R1Field.addPInvTo(arrn);
        }
    }

    public static void square(int[] arrn, int[] arrn2) {
        int[] arrn3 = Nat256.createExt();
        Nat256.square(arrn, arrn3);
        SecP256R1Field.reduce(arrn3, arrn2);
    }

    public static void squareN(int[] arrn, int n, int[] arrn2) {
        int[] arrn3 = Nat256.createExt();
        Nat256.square(arrn, arrn3);
        SecP256R1Field.reduce(arrn3, arrn2);
        while (--n > 0) {
            Nat256.square(arrn2, arrn3);
            SecP256R1Field.reduce(arrn3, arrn2);
        }
    }

    private static void subPInvFrom(int[] arrn) {
        long l;
        long l2 = ((long)arrn[0] & 0xFFFFFFFFL) - 1L;
        arrn[0] = (int)l2;
        l2 = l = l2 >> 32;
        if (l != 0L) {
            l2 = l + ((long)arrn[1] & 0xFFFFFFFFL);
            arrn[1] = (int)l2;
            l2 = (l2 >> 32) + ((long)arrn[2] & 0xFFFFFFFFL);
            arrn[2] = (int)l2;
            l2 >>= 32;
        }
        arrn[3] = (int)(l2 += ((long)arrn[3] & 0xFFFFFFFFL) + 1L);
        l2 = l = l2 >> 32;
        if (l != 0L) {
            l2 = l + ((long)arrn[4] & 0xFFFFFFFFL);
            arrn[4] = (int)l2;
            l2 = (l2 >> 32) + ((long)arrn[5] & 0xFFFFFFFFL);
            arrn[5] = (int)l2;
            l2 >>= 32;
        }
        arrn[6] = (int)(l2 += ((long)arrn[6] & 0xFFFFFFFFL) + 1L);
        arrn[7] = (int)((l2 >> 32) + ((0xFFFFFFFFL & (long)arrn[7]) - 1L));
    }

    public static void subtract(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat256.sub(arrn, arrn2, arrn3) != 0) {
            SecP256R1Field.subPInvFrom(arrn3);
        }
    }

    public static void subtractExt(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat.sub(16, arrn, arrn2, arrn3) != 0) {
            Nat.addTo(16, PExt, arrn3);
        }
    }

    public static void twice(int[] arrn, int[] arrn2) {
        if (Nat.shiftUpBit(8, arrn, 0, arrn2) != 0 || arrn2[7] == -1 && Nat256.gte(arrn2, P)) {
            SecP256R1Field.addPInvTo(arrn2);
        }
    }
}

