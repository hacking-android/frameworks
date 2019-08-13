/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.spec;

import com.android.org.bouncycastle.jce.spec.ECParameterSpec;
import java.security.spec.KeySpec;

public class ECKeySpec
implements KeySpec {
    private ECParameterSpec spec;

    protected ECKeySpec(ECParameterSpec eCParameterSpec) {
        this.spec = eCParameterSpec;
    }

    public ECParameterSpec getParams() {
        return this.spec;
    }
}

