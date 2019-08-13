/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.raw;

import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat256;

public abstract class Nat512 {
    public static void mul(int[] arrn, int[] arrn2, int[] arrn3) {
        Nat256.mul(arrn, arrn2, arrn3);
        Nat256.mul(arrn, 8, arrn2, 8, arrn3, 16);
        int n = Nat256.addToEachOther(arrn3, 8, arrn3, 16);
        int n2 = Nat256.addTo(arrn3, 24, arrn3, 16, Nat256.addTo(arrn3, 0, arrn3, 8, 0) + n);
        int[] arrn4 = Nat256.create();
        int[] arrn5 = Nat256.create();
        int n3 = Nat256.diff(arrn, 8, arrn, 0, arrn4, 0) != Nat256.diff(arrn2, 8, arrn2, 0, arrn5, 0) ? 1 : 0;
        arrn = Nat256.createExt();
        Nat256.mul(arrn4, arrn5, arrn);
        n3 = n3 != 0 ? Nat.addTo(16, arrn, 0, arrn3, 8) : Nat.subFrom(16, arrn, 0, arrn3, 8);
        Nat.addWordAt(32, n + n2 + n3, arrn3, 24);
    }

    public static void square(int[] arrn, int[] arrn2) {
        Nat256.square(arrn, arrn2);
        Nat256.square(arrn, 8, arrn2, 16);
        int n = Nat256.addToEachOther(arrn2, 8, arrn2, 16);
        int n2 = Nat256.addTo(arrn2, 24, arrn2, 16, Nat256.addTo(arrn2, 0, arrn2, 8, 0) + n);
        int[] arrn3 = Nat256.create();
        Nat256.diff(arrn, 8, arrn, 0, arrn3, 0);
        arrn = Nat256.createExt();
        Nat256.square(arrn3, arrn);
        Nat.addWordAt(32, n + n2 + Nat.subFrom(16, arrn, 0, arrn2, 8), arrn2, 24);
    }
}

