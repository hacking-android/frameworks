/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAKeyGenerationParameters
extends KeyGenerationParameters {
    private int certainty;
    private BigInteger publicExponent;

    public RSAKeyGenerationParameters(BigInteger bigInteger, SecureRandom secureRandom, int n, int n2) {
        super(secureRandom, n);
        if (n >= 12) {
            if (bigInteger.testBit(0)) {
                this.publicExponent = bigInteger;
                this.certainty = n2;
                return;
            }
            throw new IllegalArgumentException("public exponent cannot be even");
        }
        throw new IllegalArgumentException("key strength too small");
    }

    public int getCertainty() {
        return this.certainty;
    }

    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }
}

