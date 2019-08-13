/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import java.math.BigInteger;
import java.security.SecureRandom;

public class ECKeyGenerationParameters
extends KeyGenerationParameters {
    private ECDomainParameters domainParams;

    public ECKeyGenerationParameters(ECDomainParameters eCDomainParameters, SecureRandom secureRandom) {
        super(secureRandom, eCDomainParameters.getN().bitLength());
        this.domainParams = eCDomainParameters;
    }

    public ECDomainParameters getDomainParameters() {
        return this.domainParams;
    }
}

