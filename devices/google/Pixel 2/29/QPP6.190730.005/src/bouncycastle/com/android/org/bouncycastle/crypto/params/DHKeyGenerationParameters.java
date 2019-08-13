/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import java.math.BigInteger;
import java.security.SecureRandom;

public class DHKeyGenerationParameters
extends KeyGenerationParameters {
    private DHParameters params;

    public DHKeyGenerationParameters(SecureRandom secureRandom, DHParameters dHParameters) {
        super(secureRandom, DHKeyGenerationParameters.getStrength(dHParameters));
        this.params = dHParameters;
    }

    static int getStrength(DHParameters dHParameters) {
        int n = dHParameters.getL() != 0 ? dHParameters.getL() : dHParameters.getP().bitLength();
        return n;
    }

    public DHParameters getParameters() {
        return this.params;
    }
}

