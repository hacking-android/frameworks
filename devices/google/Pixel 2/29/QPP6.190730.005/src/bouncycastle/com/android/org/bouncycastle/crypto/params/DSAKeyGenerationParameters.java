/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import java.math.BigInteger;
import java.security.SecureRandom;

public class DSAKeyGenerationParameters
extends KeyGenerationParameters {
    private DSAParameters params;

    public DSAKeyGenerationParameters(SecureRandom secureRandom, DSAParameters dSAParameters) {
        super(secureRandom, dSAParameters.getP().bitLength() - 1);
        this.params = dSAParameters;
    }

    public DSAParameters getParameters() {
        return this.params;
    }
}

