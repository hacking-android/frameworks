/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.DHParameters;

public class DHKeyParameters
extends AsymmetricKeyParameter {
    private DHParameters params;

    protected DHKeyParameters(boolean bl, DHParameters dHParameters) {
        super(bl);
        this.params = dHParameters;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof DHKeyParameters;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (DHKeyParameters)object;
        DHParameters dHParameters = this.params;
        if (dHParameters == null) {
            if (((DHKeyParameters)object).getParameters() == null) {
                bl2 = true;
            }
            return bl2;
        }
        return dHParameters.equals(((DHKeyParameters)object).getParameters());
    }

    public DHParameters getParameters() {
        return this.params;
    }

    public int hashCode() {
        int n = this.isPrivate() ^ true;
        DHParameters dHParameters = this.params;
        int n2 = n;
        if (dHParameters != null) {
            n2 = n ^ dHParameters.hashCode();
        }
        return n2;
    }
}

