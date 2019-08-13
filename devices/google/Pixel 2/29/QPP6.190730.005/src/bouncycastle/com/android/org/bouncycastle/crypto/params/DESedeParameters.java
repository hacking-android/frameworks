/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.params.DESParameters;

public class DESedeParameters
extends DESParameters {
    public static final int DES_EDE_KEY_LENGTH = 24;

    public DESedeParameters(byte[] arrby) {
        super(arrby);
        if (!DESedeParameters.isWeakKey(arrby, 0, arrby.length)) {
            return;
        }
        throw new IllegalArgumentException("attempt to create weak DESede key");
    }

    public static boolean isReal2Key(byte[] arrby, int n) {
        boolean bl = false;
        for (int i = n; i != n + 8; ++i) {
            if (arrby[i] == arrby[i + 8]) continue;
            bl = true;
        }
        return bl;
    }

    public static boolean isReal3Key(byte[] arrby, int n) {
        boolean bl;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        int n2 = n;
        do {
            bl = false;
            boolean bl5 = false;
            if (n2 == n + 8) break;
            boolean bl6 = arrby[n2] != arrby[n2 + 8];
            bl6 = bl2 | bl6;
            bl2 = arrby[n2] != arrby[n2 + 16];
            boolean bl7 = bl3 | bl2;
            bl3 = bl5;
            if (arrby[n2 + 8] != arrby[n2 + 16]) {
                bl3 = true;
            }
            bl4 |= bl3;
            ++n2;
            bl2 = bl6;
            bl3 = bl7;
        } while (true);
        boolean bl8 = bl;
        if (bl2) {
            bl8 = bl;
            if (bl3) {
                bl8 = bl;
                if (bl4) {
                    bl8 = true;
                }
            }
        }
        return bl8;
    }

    public static boolean isRealEDEKey(byte[] arrby, int n) {
        boolean bl = arrby.length == 16 ? DESedeParameters.isReal2Key(arrby, n) : DESedeParameters.isReal3Key(arrby, n);
        return bl;
    }

    public static boolean isWeakKey(byte[] arrby, int n) {
        return DESedeParameters.isWeakKey(arrby, n, arrby.length - n);
    }

    public static boolean isWeakKey(byte[] arrby, int n, int n2) {
        while (n < n2) {
            if (DESParameters.isWeakKey(arrby, n)) {
                return true;
            }
            n += 8;
        }
        return false;
    }
}

