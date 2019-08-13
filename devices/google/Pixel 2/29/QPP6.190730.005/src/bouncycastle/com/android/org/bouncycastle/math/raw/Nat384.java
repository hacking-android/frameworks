/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.raw;

import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat192;

public abstract class Nat384 {
    public static void mul(int[] arrn, int[] arrn2, int[] arrn3) {
        Nat192.mul(arrn, arrn2, arrn3);
        Nat192.mul(arrn, 6, arrn2, 6, arrn3, 12);
        int n = Nat192.addToEachOther(arrn3, 6, arrn3, 12);
        int n2 = Nat192.addTo(arrn3, 18, arrn3, 12, Nat192.addTo(arrn3, 0, arrn3, 6, 0) + n);
        int[] arrn4 = Nat192.create();
        int[] arrn5 = Nat192.create();
        int n3 = Nat192.diff(arrn, 6, arrn, 0, arrn4, 0) != Nat192.diff(arrn2, 6, arrn2, 0, arrn5, 0) ? 1 : 0;
        arrn = Nat192.createExt();
        Nat192.mul(arrn4, arrn5, arrn);
        n3 = n3 != 0 ? Nat.addTo(12, arrn, 0, arrn3, 6) : Nat.subFrom(12, arrn, 0, arrn3, 6);
        Nat.addWordAt(24, n + n2 + n3, arrn3, 18);
    }

    public static void square(int[] arrn, int[] arrn2) {
        Nat192.square(arrn, arrn2);
        Nat192.square(arrn, 6, arrn2, 12);
        int n = Nat192.addToEachOther(arrn2, 6, arrn2, 12);
        int n2 = Nat192.addTo(arrn2, 18, arrn2, 12, Nat192.addTo(arrn2, 0, arrn2, 6, 0) + n);
        int[] arrn3 = Nat192.create();
        Nat192.diff(arrn, 6, arrn, 0, arrn3, 0);
        arrn = Nat192.createExt();
        Nat192.square(arrn3, arrn);
        Nat.addWordAt(24, n + n2 + Nat.subFrom(12, arrn, 0, arrn2, 6), arrn2, 18);
    }
}

