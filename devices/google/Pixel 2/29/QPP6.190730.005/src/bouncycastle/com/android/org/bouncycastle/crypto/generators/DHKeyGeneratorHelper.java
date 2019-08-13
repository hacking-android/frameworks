/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.math.ec.WNafUtil;
import com.android.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.SecureRandom;

class DHKeyGeneratorHelper {
    static final DHKeyGeneratorHelper INSTANCE = new DHKeyGeneratorHelper();
    private static final BigInteger ONE = BigInteger.valueOf(1L);
    private static final BigInteger TWO = BigInteger.valueOf(2L);

    private DHKeyGeneratorHelper() {
    }

    BigInteger calculatePrivate(DHParameters object, SecureRandom secureRandom) {
        BigInteger bigInteger;
        int n = ((DHParameters)object).getL();
        if (n != 0) {
            while (WNafUtil.getNafWeight((BigInteger)(object = BigIntegers.createRandomBigInteger(n, secureRandom).setBit(n - 1))) < n >>> 2) {
            }
            return object;
        }
        BigInteger bigInteger2 = TWO;
        n = ((DHParameters)object).getM();
        if (n != 0) {
            bigInteger2 = ONE.shiftLeft(n - 1);
        }
        BigInteger bigInteger3 = bigInteger = ((DHParameters)object).getQ();
        if (bigInteger == null) {
            bigInteger3 = ((DHParameters)object).getP();
        }
        bigInteger3 = bigInteger3.subtract(TWO);
        n = bigInteger3.bitLength();
        while (WNafUtil.getNafWeight((BigInteger)(object = BigIntegers.createRandomInRange(bigInteger2, bigInteger3, secureRandom))) < n >>> 2) {
        }
        return object;
    }

    BigInteger calculatePublic(DHParameters dHParameters, BigInteger bigInteger) {
        return dHParameters.getG().modPow(bigInteger, dHParameters.getP());
    }
}

