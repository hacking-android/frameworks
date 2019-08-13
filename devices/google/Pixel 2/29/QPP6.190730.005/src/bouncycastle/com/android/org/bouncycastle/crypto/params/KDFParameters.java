/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.DerivationParameters;

public class KDFParameters
implements DerivationParameters {
    byte[] iv;
    byte[] shared;

    public KDFParameters(byte[] arrby, byte[] arrby2) {
        this.shared = arrby;
        this.iv = arrby2;
    }

    public byte[] getIV() {
        return this.iv;
    }

    public byte[] getSharedSecret() {
        return this.shared;
    }
}

