/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.DSAParameters;

public class DSAKeyParameters
extends AsymmetricKeyParameter {
    private DSAParameters params;

    public DSAKeyParameters(boolean bl, DSAParameters dSAParameters) {
        super(bl);
        this.params = dSAParameters;
    }

    public DSAParameters getParameters() {
        return this.params;
    }
}

