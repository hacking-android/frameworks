/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.params.DSAKeyParameters;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import java.math.BigInteger;

public class DSAPrivateKeyParameters
extends DSAKeyParameters {
    private BigInteger x;

    public DSAPrivateKeyParameters(BigInteger bigInteger, DSAParameters dSAParameters) {
        super(true, dSAParameters);
        this.x = bigInteger;
    }

    public BigInteger getX() {
        return this.x;
    }
}

