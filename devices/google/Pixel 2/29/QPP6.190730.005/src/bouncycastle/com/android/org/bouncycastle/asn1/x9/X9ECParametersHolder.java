/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x9;

import com.android.org.bouncycastle.asn1.x9.X9ECParameters;

public abstract class X9ECParametersHolder {
    private X9ECParameters params;

    protected abstract X9ECParameters createParameters();

    public X9ECParameters getParameters() {
        synchronized (this) {
            if (this.params == null) {
                this.params = this.createParameters();
            }
            X9ECParameters x9ECParameters = this.params;
            return x9ECParameters;
        }
    }
}

