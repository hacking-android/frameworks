/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat384;
import java.math.BigInteger;

public class SecP384R1Field {
    private static final long M = 0xFFFFFFFFL;
    static final int[] P = new int[]{-1, 0, 0, -1, -2, -1, -1, -1, -1, -1, -1, -1};
    private static final int P11 = -1;
    static final int[] PExt = new int[]{1, -2, 0, 2, 0, -2, 0, 2, 1, 0, 0, 0, -2, 1, 0, -2, -3, -1, -1, -1, -1, -1, -1, -1};
    private static final int PExt23 = -1;
    private static final int[] PExtInv = new int[]{-1, 1, -1, -3, -1, 1, -1, -3, -2, -1, -1, -1, 1, -2, -1, 1, 2};

    public static void add(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat.add(12, arrn, arrn2, arrn3) != 0 || arrn3[11] == -1 && Nat.gte(12, arrn3, P)) {
            SecP384R1Field.addPInvTo(arrn3);
        }
    }

    public static void addExt(int[] arrn, int[] arrn2, int[] arrn3) {
        if ((Nat.add(24, arrn, arrn2, arrn3) != 0 || arrn3[23] == -1 && Nat.gte(24, arrn3, PExt)) && Nat.addTo((arrn = PExtInv).length, arrn, arrn3) != 0) {
            Nat.incAt(24, arrn3, PExtInv.length);
        }
    }

    public static void addOne(int[] arrn, int[] arrn2) {
        if (Nat.inc(12, arrn, arrn2) != 0 || arrn2[11] == -1 && Nat.gte(12, arrn2, P)) {
            SecP384R1Field.addPInvTo(arrn2);
        }
    }

    private static void addPInvTo(int[] arrn) {
        long l;
        long l2 = ((long)arrn[0] & 0xFFFFFFFFL) + 1L;
        arrn[0] = (int)l2;
        l2 = (l2 >> 32) + (((long)arrn[1] & 0xFFFFFFFFL) - 1L);
        arrn[1] = (int)l2;
        l2 = l = l2 >> 32;
        if (l != 0L) {
            l2 = l + ((long)arrn[2] & 0xFFFFFFFFL);
            arrn[2] = (int)l2;
            l2 >>= 32;
        }
        arrn[3] = (int)(l2 += ((long)arrn[3] & 0xFFFFFFFFL) + 1L);
        l2 = (l2 >> 32) + ((0xFFFFFFFFL & (long)arrn[4]) + 1L);
        arrn[4] = (int)l2;
        if (l2 >> 32 != 0L) {
            Nat.incAt(12, arrn, 5);
        }
    }

    public static int[] fromBigInteger(BigInteger arrn) {
        if ((arrn = Nat.fromBigInteger(384, (BigInteger)arrn))[11] == -1 && Nat.gte(12, arrn, P)) {
            Nat.subFrom(12, P, arrn);
        }
        return arrn;
    }

    public static void half(int[] arrn, int[] arrn2) {
        if ((arrn[0] & 1) == 0) {
            Nat.shiftDownBit(12, arrn, 0, arrn2);
        } else {
            Nat.shiftDownBit(12, arrn2, Nat.add(12, arrn, P, arrn2));
        }
    }

    public static void multiply(int[] arrn, int[] arrn2, int[] arrn3) {
        int[] arrn4 = Nat.create(24);
        Nat384.mul(arrn, arrn2, arrn4);
        SecP384R1Field.reduce(arrn4, arrn3);
    }

    public static void negate(int[] arrn, int[] arrn2) {
        if (Nat.isZero(12, arrn)) {
            Nat.zero(12, arrn2);
        } else {
            Nat.sub(12, P, arrn, arrn2);
        }
    }

    public static void reduce(int[] arrn, int[] arrn2) {
        long l = (long)arrn[16] & 0xFFFFFFFFL;
        long l2 = (long)arrn[17] & 0xFFFFFFFFL;
        long l3 = (long)arrn[18] & 0xFFFFFFFFL;
        long l4 = (long)arrn[19] & 0xFFFFFFFFL;
        long l5 = (long)arrn[20] & 0xFFFFFFFFL;
        long l6 = (long)arrn[21] & 0xFFFFFFFFL;
        long l7 = (long)arrn[22] & 0xFFFFFFFFL;
        long l8 = (long)arrn[23] & 0xFFFFFFFFL;
        long l9 = ((long)arrn[12] & 0xFFFFFFFFL) + l5 - 1L;
        long l10 = ((long)arrn[13] & 0xFFFFFFFFL) + l7;
        long l11 = ((long)arrn[14] & 0xFFFFFFFFL) + l7 + l8;
        long l12 = ((long)arrn[15] & 0xFFFFFFFFL) + l8;
        long l13 = l2 + l6;
        long l14 = l6 - l8;
        long l15 = l9 + l14;
        long l16 = 0L + (((long)arrn[0] & 0xFFFFFFFFL) + l15);
        arrn2[0] = (int)l16;
        l9 = (l16 >> 32) + (((long)arrn[1] & 0xFFFFFFFFL) + l8 - l9 + l10);
        arrn2[1] = (int)l9;
        l9 = (l9 >> 32) + (((long)arrn[2] & 0xFFFFFFFFL) - l6 - l10 + l11);
        arrn2[2] = (int)l9;
        l9 = (l9 >> 32) + (((long)arrn[3] & 0xFFFFFFFFL) - l11 + l12 + l15);
        arrn2[3] = (int)l9;
        l6 = (l9 >> 32) + (((long)arrn[4] & 0xFFFFFFFFL) + l + l6 + l10 - l12 + l15);
        arrn2[4] = (int)l6;
        l10 = (l6 >> 32) + (((long)arrn[5] & 0xFFFFFFFFL) - l + l10 + l11 + l13);
        arrn2[5] = (int)l10;
        l11 = (l10 >> 32) + (((long)arrn[6] & 0xFFFFFFFFL) + l3 - l2 + l11 + l12);
        arrn2[6] = (int)l11;
        l12 = (l11 >> 32) + (((long)arrn[7] & 0xFFFFFFFFL) + l + l4 - l3 + l12);
        arrn2[7] = (int)l12;
        l2 = (l12 >> 32) + (((long)arrn[8] & 0xFFFFFFFFL) + l + l2 + l5 - l4);
        arrn2[8] = (int)l2;
        l13 = (l2 >> 32) + (((long)arrn[9] & 0xFFFFFFFFL) + l3 - l5 + l13);
        arrn2[9] = (int)l13;
        l14 = (l13 >> 32) + (((long)arrn[10] & 0xFFFFFFFFL) + l3 + l4 - l14 + (l7 -= l8));
        arrn2[10] = (int)l14;
        l5 = (l14 >> 32) + (((long)arrn[11] & 0xFFFFFFFFL) + l4 + l5 - l7);
        arrn2[11] = (int)l5;
        SecP384R1Field.reduce32((int)((l5 >> 32) + 1L), arrn2);
    }

    public static void reduce32(int n, int[] arrn) {
        long l = 0L;
        if (n != 0) {
            long l2;
            long l3 = (long)n & 0xFFFFFFFFL;
            l = 0L + (((long)arrn[0] & 0xFFFFFFFFL) + l3);
            arrn[0] = (int)l;
            l = (l >> 32) + (((long)arrn[1] & 0xFFFFFFFFL) - l3);
            arrn[1] = (int)l;
            l = l2 = l >> 32;
            if (l2 != 0L) {
                l = l2 + ((long)arrn[2] & 0xFFFFFFFFL);
                arrn[2] = (int)l;
                l >>= 32;
            }
            arrn[3] = (int)(l += ((long)arrn[3] & 0xFFFFFFFFL) + l3);
            l = (l >> 32) + ((0xFFFFFFFFL & (long)arrn[4]) + l3);
            arrn[4] = (int)l;
            l >>= 32;
        }
        if (l != 0L && Nat.incAt(12, arrn, 5) != 0 || arrn[11] == -1 && Nat.gte(12, arrn, P)) {
            SecP384R1Field.addPInvTo(arrn);
        }
    }

    public static void square(int[] arrn, int[] arrn2) {
        int[] arrn3 = Nat.create(24);
        Nat384.square(arrn, arrn3);
        SecP384R1Field.reduce(arrn3, arrn2);
    }

    public static void squareN(int[] arrn, int n, int[] arrn2) {
        int[] arrn3 = Nat.create(24);
        Nat384.square(arrn, arrn3);
        SecP384R1Field.reduce(arrn3, arrn2);
        while (--n > 0) {
            Nat384.square(arrn2, arrn3);
            SecP384R1Field.reduce(arrn3, arrn2);
        }
    }

    private static void subPInvFrom(int[] arrn) {
        long l;
        long l2 = ((long)arrn[0] & 0xFFFFFFFFL) - 1L;
        arrn[0] = (int)l2;
        l2 = (l2 >> 32) + (((long)arrn[1] & 0xFFFFFFFFL) + 1L);
        arrn[1] = (int)l2;
        l2 = l = l2 >> 32;
        if (l != 0L) {
            l2 = l + ((long)arrn[2] & 0xFFFFFFFFL);
            arrn[2] = (int)l2;
            l2 >>= 32;
        }
        arrn[3] = (int)(l2 += ((long)arrn[3] & 0xFFFFFFFFL) - 1L);
        l2 = (l2 >> 32) + ((0xFFFFFFFFL & (long)arrn[4]) - 1L);
        arrn[4] = (int)l2;
        if (l2 >> 32 != 0L) {
            Nat.decAt(12, arrn, 5);
        }
    }

    public static void subtract(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat.sub(12, arrn, arrn2, arrn3) != 0) {
            SecP384R1Field.subPInvFrom(arrn3);
        }
    }

    public static void subtractExt(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat.sub(24, arrn, arrn2, arrn3) != 0 && Nat.subFrom((arrn = PExtInv).length, arrn, arrn3) != 0) {
            Nat.decAt(24, arrn3, PExtInv.length);
        }
    }

    public static void twice(int[] arrn, int[] arrn2) {
        if (Nat.shiftUpBit(12, arrn, 0, arrn2) != 0 || arrn2[11] == -1 && Nat.gte(12, arrn2, P)) {
            SecP384R1Field.addPInvTo(arrn2);
        }
    }
}

