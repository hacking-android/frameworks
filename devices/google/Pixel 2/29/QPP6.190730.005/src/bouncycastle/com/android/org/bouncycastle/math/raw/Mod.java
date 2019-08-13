/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.raw;

import com.android.org.bouncycastle.math.raw.Nat;
import java.util.Random;

public abstract class Mod {
    public static void add(int[] arrn, int[] arrn2, int[] arrn3, int[] arrn4) {
        int n = arrn.length;
        if (Nat.add(n, arrn2, arrn3, arrn4) != 0) {
            Nat.subFrom(n, arrn, arrn4);
        }
    }

    private static int getTrailingZeroes(int n) {
        int n2 = 0;
        while ((n & 1) == 0) {
            n >>>= 1;
            ++n2;
        }
        return n2;
    }

    public static int inverse32(int n) {
        int n2 = n * (2 - n * n);
        n2 *= 2 - n * n2;
        n2 *= 2 - n * n2;
        return n2 * (2 - n * n2);
    }

    private static void inversionResult(int[] arrn, int n, int[] arrn2, int[] arrn3) {
        if (n < 0) {
            Nat.add(arrn.length, arrn2, arrn, arrn3);
        } else {
            System.arraycopy(arrn2, 0, arrn3, 0, arrn.length);
        }
    }

    private static int inversionStep(int[] arrn, int[] arrn2, int n, int[] arrn3, int n2) {
        int n3 = arrn.length;
        int n4 = 0;
        while (arrn2[0] == 0) {
            Nat.shiftDownWord(n, arrn2, 0);
            n4 += 32;
        }
        int n5 = Mod.getTrailingZeroes(arrn2[0]);
        int n6 = n4;
        if (n5 > 0) {
            Nat.shiftDownBits(n, arrn2, n5, 0);
            n6 = n4 + n5;
        }
        n = 0;
        n4 = n2;
        for (n2 = n; n2 < n6; ++n2) {
            n = n4;
            if ((arrn3[0] & 1) != 0) {
                n = n4 < 0 ? n4 + Nat.addTo(n3, arrn, arrn3) : n4 + Nat.subFrom(n3, arrn, arrn3);
            }
            Nat.shiftDownBit(n3, arrn3, n);
            n4 = n;
        }
        return n4;
    }

    public static void invert(int[] arrn, int[] arrn2, int[] arrn3) {
        int n = arrn.length;
        if (!Nat.isZero(n, arrn2)) {
            int n2;
            if (Nat.isOne(n, arrn2)) {
                System.arraycopy(arrn2, 0, arrn3, 0, n);
                return;
            }
            int[] arrn4 = Nat.copy(n, arrn2);
            int[] arrn5 = Nat.create(n);
            arrn5[0] = 1;
            int n3 = 0;
            if ((arrn4[0] & 1) == 0) {
                n3 = Mod.inversionStep(arrn, arrn4, n, arrn5, 0);
            }
            if (Nat.isOne(n, arrn4)) {
                Mod.inversionResult(arrn, n3, arrn5, arrn3);
                return;
            }
            int[] arrn6 = Nat.copy(n, arrn);
            arrn2 = Nat.create(n);
            int n4 = 0;
            int n5 = n;
            do {
                if (arrn4[n5 - 1] == 0 && arrn6[n5 - 1] == 0) {
                    --n5;
                    continue;
                }
                if (Nat.gte(n5, arrn4, arrn6)) {
                    Nat.subFrom(n5, arrn6, arrn4);
                    n3 = n2 = Mod.inversionStep(arrn, arrn4, n5, arrn5, n3 + (Nat.subFrom(n, arrn2, arrn5) - n4));
                    if (!Nat.isOne(n5, arrn4)) continue;
                    Mod.inversionResult(arrn, n2, arrn5, arrn3);
                    return;
                }
                Nat.subFrom(n5, arrn4, arrn6);
                n4 = n2 = Mod.inversionStep(arrn, arrn6, n5, arrn2, n4 + (Nat.subFrom(n, arrn5, arrn2) - n3));
                if (Nat.isOne(n5, arrn6)) break;
            } while (true);
            Mod.inversionResult(arrn, n2, arrn2, arrn3);
            return;
        }
        throw new IllegalArgumentException("'x' cannot be 0");
    }

    public static int[] random(int[] arrn) {
        int n = arrn.length;
        Random random = new Random();
        int[] arrn2 = Nat.create(n);
        int n2 = arrn[n - 1];
        n2 |= n2 >>> 1;
        n2 |= n2 >>> 2;
        n2 |= n2 >>> 4;
        int n3 = n2 | n2 >>> 8;
        do {
            for (n2 = 0; n2 != n; ++n2) {
                arrn2[n2] = random.nextInt();
            }
            n2 = n - 1;
            arrn2[n2] = arrn2[n2] & (n3 | n3 >>> 16);
        } while (Nat.gte(n, arrn2, arrn));
        return arrn2;
    }

    public static void subtract(int[] arrn, int[] arrn2, int[] arrn3, int[] arrn4) {
        int n = arrn.length;
        if (Nat.sub(n, arrn2, arrn3, arrn4) != 0) {
            Nat.addTo(n, arrn, arrn4);
        }
    }
}

