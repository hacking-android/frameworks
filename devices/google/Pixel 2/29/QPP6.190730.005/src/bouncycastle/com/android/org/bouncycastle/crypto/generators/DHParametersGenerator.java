/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.crypto.generators.DHParametersHelper;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.crypto.params.DHValidationParameters;
import java.math.BigInteger;
import java.security.SecureRandom;

public class DHParametersGenerator {
    private static final BigInteger TWO = BigInteger.valueOf(2L);
    private int certainty;
    private SecureRandom random;
    private int size;

    public DHParameters generateParameters() {
        Object object = DHParametersHelper.generateSafePrimes(this.size, this.certainty, this.random);
        BigInteger bigInteger = object[0];
        object = object[1];
        return new DHParameters(bigInteger, DHParametersHelper.selectGenerator(bigInteger, (BigInteger)object, this.random), (BigInteger)object, TWO, null);
    }

    public void init(int n, int n2, SecureRandom secureRandom) {
        this.size = n;
        this.certainty = n2;
        this.random = secureRandom;
    }
}

