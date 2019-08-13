/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.params.DHKeyParameters;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import java.math.BigInteger;

public class DHPrivateKeyParameters
extends DHKeyParameters {
    private BigInteger x;

    public DHPrivateKeyParameters(BigInteger bigInteger, DHParameters dHParameters) {
        super(true, dHParameters);
        this.x = bigInteger;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof DHPrivateKeyParameters;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        bl = bl2;
        if (((DHPrivateKeyParameters)object).getX().equals(this.x)) {
            bl = bl2;
            if (super.equals(object)) {
                bl = true;
            }
        }
        return bl;
    }

    public BigInteger getX() {
        return this.x;
    }

    @Override
    public int hashCode() {
        return this.x.hashCode() ^ super.hashCode();
    }
}

