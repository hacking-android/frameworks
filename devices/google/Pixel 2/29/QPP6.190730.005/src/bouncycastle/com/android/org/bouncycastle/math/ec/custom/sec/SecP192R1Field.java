/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat192;
import java.math.BigInteger;

public class SecP192R1Field {
    private static final long M = 0xFFFFFFFFL;
    static final int[] P = new int[]{-1, -1, -2, -1, -1, -1};
    private static final int P5 = -1;
    static final int[] PExt = new int[]{1, 0, 2, 0, 1, 0, -2, -1, -3, -1, -1, -1};
    private static final int PExt11 = -1;
    private static final int[] PExtInv = new int[]{-1, -1, -3, -1, -2, -1, 1, 0, 2};

    public static void add(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat192.add(arrn, arrn2, arrn3) != 0 || arrn3[5] == -1 && Nat192.gte(arrn3, P)) {
            SecP192R1Field.addPInvTo(arrn3);
        }
    }

    public static void addExt(int[] arrn, int[] arrn2, int[] arrn3) {
        if ((Nat.add(12, arrn, arrn2, arrn3) != 0 || arrn3[11] == -1 && Nat.gte(12, arrn3, PExt)) && Nat.addTo((arrn = PExtInv).length, arrn, arrn3) != 0) {
            Nat.incAt(12, arrn3, PExtInv.length);
        }
    }

    public static void addOne(int[] arrn, int[] arrn2) {
        if (Nat.inc(6, arrn, arrn2) != 0 || arrn2[5] == -1 && Nat192.gte(arrn2, P)) {
            SecP192R1Field.addPInvTo(arrn2);
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
            l2 >>= 32;
        }
        arrn[2] = (int)(l2 += (0xFFFFFFFFL & (long)arrn[2]) + 1L);
        if (l2 >> 32 != 0L) {
            Nat.incAt(6, arrn, 3);
        }
    }

    public static int[] fromBigInteger(BigInteger arrn) {
        if ((arrn = Nat192.fromBigInteger((BigInteger)arrn))[5] == -1 && Nat192.gte(arrn, P)) {
            Nat192.subFrom(P, arrn);
        }
        return arrn;
    }

    public static void half(int[] arrn, int[] arrn2) {
        if ((arrn[0] & 1) == 0) {
            Nat.shiftDownBit(6, arrn, 0, arrn2);
        } else {
            Nat.shiftDownBit(6, arrn2, Nat192.add(arrn, P, arrn2));
        }
    }

    public static void multiply(int[] arrn, int[] arrn2, int[] arrn3) {
        int[] arrn4 = Nat192.createExt();
        Nat192.mul(arrn, arrn2, arrn4);
        SecP192R1Field.reduce(arrn4, arrn3);
    }

    public static void multiplyAddToExt(int[] arrn, int[] arrn2, int[] arrn3) {
        if ((Nat192.mulAddTo(arrn, arrn2, arrn3) != 0 || arrn3[11] == -1 && Nat.gte(12, arrn3, PExt)) && Nat.addTo((arrn = PExtInv).length, arrn, arrn3) != 0) {
            Nat.incAt(12, arrn3, PExtInv.length);
        }
    }

    public static void negate(int[] arrn, int[] arrn2) {
        if (Nat192.isZero(arrn)) {
            Nat192.zero(arrn2);
        } else {
            Nat192.sub(P, arrn, arrn2);
        }
    }

    public static void reduce(int[] arrn, int[] arrn2) {
        long l = (long)arrn[6] & 0xFFFFFFFFL;
        long l2 = (long)arrn[7] & 0xFFFFFFFFL;
        long l3 = arrn[8];
        long l4 = arrn[9];
        long l5 = arrn[10];
        long l6 = arrn[11];
        long l7 = l + (l5 & 0xFFFFFFFFL);
        l6 = l2 + (l6 & 0xFFFFFFFFL);
        l5 = 0L + (((long)arrn[0] & 0xFFFFFFFFL) + l7);
        int n = (int)l5;
        l5 = (l5 >> 32) + (((long)arrn[1] & 0xFFFFFFFFL) + l6);
        arrn2[1] = (int)l5;
        l3 = l7 + (l3 & 0xFFFFFFFFL);
        l6 += l4 & 0xFFFFFFFFL;
        l4 = (l5 >> 32) + (((long)arrn[2] & 0xFFFFFFFFL) + l3);
        l5 = (l4 >> 32) + (((long)arrn[3] & 0xFFFFFFFFL) + l6);
        arrn2[3] = (int)l5;
        l = (l5 >> 32) + (((long)arrn[4] & 0xFFFFFFFFL) + (l3 - l));
        arrn2[4] = (int)l;
        l2 = (l >> 32) + (((long)arrn[5] & 0xFFFFFFFFL) + (l6 - l2));
        arrn2[5] = (int)l2;
        l = (l4 & 0xFFFFFFFFL) + (l2 >>= 32);
        arrn2[0] = (int)(l2 += (long)n & 0xFFFFFFFFL);
        l4 = l2 >> 32;
        l2 = l;
        if (l4 != 0L) {
            l2 = l4 + ((long)arrn2[1] & 0xFFFFFFFFL);
            arrn2[1] = (int)l2;
            l2 = l + (l2 >> 32);
        }
        arrn2[2] = (int)l2;
        if (l2 >> 32 != 0L && Nat.incAt(6, arrn2, 3) != 0 || arrn2[5] == -1 && Nat192.gte(arrn2, P)) {
            SecP192R1Field.addPInvTo(arrn2);
        }
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
                l >>= 32;
            }
            arrn[2] = (int)(l += (0xFFFFFFFFL & (long)arrn[2]) + l3);
            l >>= 32;
        }
        if (l != 0L && Nat.incAt(6, arrn, 3) != 0 || arrn[5] == -1 && Nat192.gte(arrn, P)) {
            SecP192R1Field.addPInvTo(arrn);
        }
    }

    public static void square(int[] arrn, int[] arrn2) {
        int[] arrn3 = Nat192.createExt();
        Nat192.square(arrn, arrn3);
        SecP192R1Field.reduce(arrn3, arrn2);
    }

    public static void squareN(int[] arrn, int n, int[] arrn2) {
        int[] arrn3 = Nat192.createExt();
        Nat192.square(arrn, arrn3);
        SecP192R1Field.reduce(arrn3, arrn2);
        while (--n > 0) {
            Nat192.square(arrn2, arrn3);
            SecP192R1Field.reduce(arrn3, arrn2);
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
            l2 >>= 32;
        }
        arrn[2] = (int)(l2 += (0xFFFFFFFFL & (long)arrn[2]) - 1L);
        if (l2 >> 32 != 0L) {
            Nat.decAt(6, arrn, 3);
        }
    }

    public static void subtract(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat192.sub(arrn, arrn2, arrn3) != 0) {
            SecP192R1Field.subPInvFrom(arrn3);
        }
    }

    public static void subtractExt(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat.sub(12, arrn, arrn2, arrn3) != 0 && Nat.subFrom((arrn = PExtInv).length, arrn, arrn3) != 0) {
            Nat.decAt(12, arrn3, PExtInv.length);
        }
    }

    public static void twice(int[] arrn, int[] arrn2) {
        if (Nat.shiftUpBit(6, arrn, 0, arrn2) != 0 || arrn2[5] == -1 && Nat192.gte(arrn2, P)) {
            SecP192R1Field.addPInvTo(arrn2);
        }
    }
}

