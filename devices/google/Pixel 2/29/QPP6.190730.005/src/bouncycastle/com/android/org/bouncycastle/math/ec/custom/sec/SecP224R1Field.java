/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat224;
import java.math.BigInteger;

public class SecP224R1Field {
    private static final long M = 0xFFFFFFFFL;
    static final int[] P = new int[]{1, 0, 0, -1, -1, -1, -1};
    private static final int P6 = -1;
    static final int[] PExt = new int[]{1, 0, 0, -2, -1, -1, 0, 2, 0, 0, -2, -1, -1, -1};
    private static final int PExt13 = -1;
    private static final int[] PExtInv = new int[]{-1, -1, -1, 1, 0, 0, -1, -3, -1, -1, 1};

    public static void add(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat224.add(arrn, arrn2, arrn3) != 0 || arrn3[6] == -1 && Nat224.gte(arrn3, P)) {
            SecP224R1Field.addPInvTo(arrn3);
        }
    }

    public static void addExt(int[] arrn, int[] arrn2, int[] arrn3) {
        if ((Nat.add(14, arrn, arrn2, arrn3) != 0 || arrn3[13] == -1 && Nat.gte(14, arrn3, PExt)) && Nat.addTo((arrn = PExtInv).length, arrn, arrn3) != 0) {
            Nat.incAt(14, arrn3, PExtInv.length);
        }
    }

    public static void addOne(int[] arrn, int[] arrn2) {
        if (Nat.inc(7, arrn, arrn2) != 0 || arrn2[6] == -1 && Nat224.gte(arrn2, P)) {
            SecP224R1Field.addPInvTo(arrn2);
        }
    }

    private static void addPInvTo(int[] arrn) {
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
        arrn[3] = (int)(l2 += (0xFFFFFFFFL & (long)arrn[3]) + 1L);
        if (l2 >> 32 != 0L) {
            Nat.incAt(7, arrn, 4);
        }
    }

    public static int[] fromBigInteger(BigInteger arrn) {
        if ((arrn = Nat224.fromBigInteger((BigInteger)arrn))[6] == -1 && Nat224.gte(arrn, P)) {
            Nat224.subFrom(P, arrn);
        }
        return arrn;
    }

    public static void half(int[] arrn, int[] arrn2) {
        if ((arrn[0] & 1) == 0) {
            Nat.shiftDownBit(7, arrn, 0, arrn2);
        } else {
            Nat.shiftDownBit(7, arrn2, Nat224.add(arrn, P, arrn2));
        }
    }

    public static void multiply(int[] arrn, int[] arrn2, int[] arrn3) {
        int[] arrn4 = Nat224.createExt();
        Nat224.mul(arrn, arrn2, arrn4);
        SecP224R1Field.reduce(arrn4, arrn3);
    }

    public static void multiplyAddToExt(int[] arrn, int[] arrn2, int[] arrn3) {
        if ((Nat224.mulAddTo(arrn, arrn2, arrn3) != 0 || arrn3[13] == -1 && Nat.gte(14, arrn3, PExt)) && Nat.addTo((arrn = PExtInv).length, arrn, arrn3) != 0) {
            Nat.incAt(14, arrn3, PExtInv.length);
        }
    }

    public static void negate(int[] arrn, int[] arrn2) {
        if (Nat224.isZero(arrn)) {
            Nat224.zero(arrn2);
        } else {
            Nat224.sub(P, arrn, arrn2);
        }
    }

    public static void reduce(int[] arrn, int[] arrn2) {
        long l = (long)arrn[10] & 0xFFFFFFFFL;
        long l2 = (long)arrn[11] & 0xFFFFFFFFL;
        long l3 = (long)arrn[12] & 0xFFFFFFFFL;
        long l4 = (long)arrn[13] & 0xFFFFFFFFL;
        long l5 = ((long)arrn[7] & 0xFFFFFFFFL) + l2 - 1L;
        long l6 = ((long)arrn[8] & 0xFFFFFFFFL) + l3;
        long l7 = ((long)arrn[9] & 0xFFFFFFFFL) + l4;
        long l8 = 0L + (((long)arrn[0] & 0xFFFFFFFFL) - l5);
        long l9 = (l8 >> 32) + (((long)arrn[1] & 0xFFFFFFFFL) - l6);
        arrn2[1] = (int)l9;
        l9 = (l9 >> 32) + (((long)arrn[2] & 0xFFFFFFFFL) - l7);
        arrn2[2] = (int)l9;
        l5 = (l9 >> 32) + (((long)arrn[3] & 0xFFFFFFFFL) + l5 - l);
        l2 = (l5 >> 32) + (((long)arrn[4] & 0xFFFFFFFFL) + l6 - l2);
        arrn2[4] = (int)l2;
        l7 = (l2 >> 32) + (((long)arrn[5] & 0xFFFFFFFFL) + l7 - l3);
        arrn2[5] = (int)l7;
        l4 = (l7 >> 32) + (((long)arrn[6] & 0xFFFFFFFFL) + l - l4);
        arrn2[6] = (int)l4;
        l = (l4 >> 32) + 1L;
        l4 = (l5 & 0xFFFFFFFFL) + l;
        l8 = (l8 & 0xFFFFFFFFL) - l;
        arrn2[0] = (int)l8;
        if ((l8 >>= 32) != 0L) {
            arrn2[1] = (int)(l8 += (long)arrn2[1] & 0xFFFFFFFFL);
            l8 = (l8 >> 32) + (0xFFFFFFFFL & (long)arrn2[2]);
            arrn2[2] = (int)l8;
            l4 += l8 >> 32;
        }
        arrn2[3] = (int)l4;
        if (l4 >> 32 != 0L && Nat.incAt(7, arrn2, 4) != 0 || arrn2[6] == -1 && Nat224.gte(arrn2, P)) {
            SecP224R1Field.addPInvTo(arrn2);
        }
    }

    public static void reduce32(int n, int[] arrn) {
        long l = 0L;
        if (n != 0) {
            long l2;
            long l3 = (long)n & 0xFFFFFFFFL;
            l = 0L + (((long)arrn[0] & 0xFFFFFFFFL) - l3);
            arrn[0] = (int)l;
            l = l2 = l >> 32;
            if (l2 != 0L) {
                l = l2 + ((long)arrn[1] & 0xFFFFFFFFL);
                arrn[1] = (int)l;
                l = (l >> 32) + ((long)arrn[2] & 0xFFFFFFFFL);
                arrn[2] = (int)l;
                l >>= 32;
            }
            arrn[3] = (int)(l += (0xFFFFFFFFL & (long)arrn[3]) + l3);
            l >>= 32;
        }
        if (l != 0L && Nat.incAt(7, arrn, 4) != 0 || arrn[6] == -1 && Nat224.gte(arrn, P)) {
            SecP224R1Field.addPInvTo(arrn);
        }
    }

    public static void square(int[] arrn, int[] arrn2) {
        int[] arrn3 = Nat224.createExt();
        Nat224.square(arrn, arrn3);
        SecP224R1Field.reduce(arrn3, arrn2);
    }

    public static void squareN(int[] arrn, int n, int[] arrn2) {
        int[] arrn3 = Nat224.createExt();
        Nat224.square(arrn, arrn3);
        SecP224R1Field.reduce(arrn3, arrn2);
        while (--n > 0) {
            Nat224.square(arrn2, arrn3);
            SecP224R1Field.reduce(arrn3, arrn2);
        }
    }

    private static void subPInvFrom(int[] arrn) {
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
        arrn[3] = (int)(l2 += (0xFFFFFFFFL & (long)arrn[3]) - 1L);
        if (l2 >> 32 != 0L) {
            Nat.decAt(7, arrn, 4);
        }
    }

    public static void subtract(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat224.sub(arrn, arrn2, arrn3) != 0) {
            SecP224R1Field.subPInvFrom(arrn3);
        }
    }

    public static void subtractExt(int[] arrn, int[] arrn2, int[] arrn3) {
        if (Nat.sub(14, arrn, arrn2, arrn3) != 0 && Nat.subFrom((arrn = PExtInv).length, arrn, arrn3) != 0) {
            Nat.decAt(14, arrn3, PExtInv.length);
        }
    }

    public static void twice(int[] arrn, int[] arrn2) {
        if (Nat.shiftUpBit(7, arrn, 0, arrn2) != 0 || arrn2[6] == -1 && Nat224.gte(arrn2, P)) {
            SecP224R1Field.addPInvTo(arrn2);
        }
    }
}

