/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;

public class ECKeyParameters
extends AsymmetricKeyParameter {
    ECDomainParameters params;

    protected ECKeyParameters(boolean bl, ECDomainParameters eCDomainParameters) {
        super(bl);
        this.params = eCDomainParameters;
    }

    public ECDomainParameters getParameters() {
        return this.params;
    }
}

