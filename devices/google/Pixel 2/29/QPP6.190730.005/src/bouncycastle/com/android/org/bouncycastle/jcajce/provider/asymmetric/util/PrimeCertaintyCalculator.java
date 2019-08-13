/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.util;

public class PrimeCertaintyCalculator {
    private PrimeCertaintyCalculator() {
    }

    public static int getDefaultCertainty(int n) {
        n = n <= 1024 ? 80 : (n - 1) / 1024 * 16 + 96;
        return n;
    }
}

