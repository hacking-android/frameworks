/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.math.ec.WNafUtil;
import com.android.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;

class DHParametersHelper {
    private static final BigInteger ONE;
    private static final BigInteger TWO;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(DHParametersHelper.class.getName());
        ONE = BigInteger.valueOf(1L);
        TWO = BigInteger.valueOf(2L);
    }

    DHParametersHelper() {
    }

    static BigInteger[] generateSafePrimes(int n, int n2, SecureRandom object) {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        logger.info("Generating safe primes. This may take a long time.");
        long l = System.currentTimeMillis();
        int n3 = 0;
        do {
            ++n3;
        } while (!(bigInteger = (bigInteger2 = BigIntegers.createRandomPrime(n - 1, 2, (SecureRandom)object)).shiftLeft(1).add(ONE)).isProbablePrime(n2) || n2 > 2 && !bigInteger2.isProbablePrime(n2 - 2) || WNafUtil.getNafWeight(bigInteger) < n >>> 2);
        long l2 = System.currentTimeMillis();
        object = logger;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Generated safe primes: ");
        stringBuilder.append(n3);
        stringBuilder.append(" tries took ");
        stringBuilder.append(l2 - l);
        stringBuilder.append("ms");
        ((Logger)object).info(stringBuilder.toString());
        return new BigInteger[]{bigInteger, bigInteger2};
    }

    static BigInteger selectGenerator(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger bigInteger3 = bigInteger.subtract(TWO);
        while ((bigInteger2 = BigIntegers.createRandomInRange(TWO, bigInteger3, secureRandom).modPow(TWO, bigInteger)).equals(ONE)) {
        }
        return bigInteger2;
    }
}

