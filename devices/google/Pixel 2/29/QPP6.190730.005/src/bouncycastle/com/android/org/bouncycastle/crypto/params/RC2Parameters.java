/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.params.KeyParameter;

public class RC2Parameters
extends KeyParameter {
    private int bits;

    public RC2Parameters(byte[] arrby) {
        int n = arrby.length > 128 ? 1024 : arrby.length * 8;
        this(arrby, n);
    }

    public RC2Parameters(byte[] arrby, int n) {
        super(arrby);
        this.bits = n;
    }

    public int getEffectiveKeyBits() {
        return this.bits;
    }
}

