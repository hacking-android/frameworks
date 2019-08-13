/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.CipherParameters;

public class AsymmetricKeyParameter
implements CipherParameters {
    boolean privateKey;

    public AsymmetricKeyParameter(boolean bl) {
        this.privateKey = bl;
    }

    public boolean isPrivate() {
        return this.privateKey;
    }
}

