/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import com.android.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import com.android.org.bouncycastle.math.Primes;
import com.android.org.bouncycastle.math.ec.WNafUtil;
import com.android.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAKeyPairGenerator
implements AsymmetricCipherKeyPairGenerator {
    private static final BigInteger ONE = BigInteger.valueOf(1L);
    private RSAKeyGenerationParameters param;

    private static int getNumberOfIterations(int n, int n2) {
        int n3 = 4;
        if (n >= 1536) {
            n = n2 <= 100 ? 3 : (n2 <= 128 ? n3 : 4 + (n2 - 128 + 1) / 2);
            return n;
        }
        int n4 = 5;
        if (n >= 1024) {
            n = n2 <= 100 ? n3 : (n2 <= 112 ? 5 : (n2 - 112 + 1) / 2 + 5);
            return n;
        }
        if (n >= 512) {
            n = n2 <= 80 ? n4 : (n2 <= 100 ? 7 : (n2 - 100 + 1) / 2 + 7);
            return n;
        }
        n = 40;
        if (n2 > 80) {
            n = 40 + (n2 - 80 + 1) / 2;
        }
        return n;
    }

    protected BigInteger chooseRandomPrime(int n, BigInteger bigInteger, BigInteger bigInteger2) {
        for (int i = 0; i != n * 5; ++i) {
            BigInteger bigInteger3 = BigIntegers.createRandomPrime(n, 1, this.param.getRandom());
            if (bigInteger3.mod(bigInteger).equals(ONE) || bigInteger3.multiply(bigInteger3).compareTo(bigInteger2) < 0 || !this.isProbablePrime(bigInteger3) || !bigInteger.gcd(bigInteger3.subtract(ONE)).equals(ONE)) {
                continue;
            }
            return bigInteger3;
        }
        throw new IllegalStateException("unable to generate prime number for RSA key");
    }

    @Override
    public AsymmetricCipherKeyPair generateKeyPair() {
        int n;
        Object object = null;
        boolean bl = false;
        int n2 = this.param.getStrength();
        int n3 = (n2 + 1) / 2;
        int n4 = n2 - n3;
        int n5 = n = n2 / 2 - 100;
        if (n < n2 / 3) {
            n5 = n2 / 3;
        }
        BigInteger bigInteger = BigInteger.valueOf(2L).pow(n2 / 2);
        BigInteger bigInteger2 = ONE.shiftLeft(n2 - 1);
        BigInteger bigInteger3 = ONE.shiftLeft(n5);
        n = n2;
        do {
            Object object2;
            Object object3;
            BigInteger bigInteger4;
            BigInteger bigInteger5;
            BigInteger bigInteger6;
            Object object4 = this;
            if (bl) break;
            BigInteger bigInteger7 = ((RSAKeyPairGenerator)object4).param.getPublicExponent();
            object4 = ((RSAKeyPairGenerator)object4).chooseRandomPrime(n3, bigInteger7, bigInteger2);
            do {
                if ((bigInteger4 = ((BigInteger)(object3 = ((RSAKeyPairGenerator)(object2 = this)).chooseRandomPrime(n4, bigInteger7, bigInteger2))).subtract((BigInteger)object4).abs()).bitLength() < n5 || bigInteger4.compareTo(bigInteger3) <= 0) {
                    continue;
                }
                bigInteger4 = ((BigInteger)object4).multiply((BigInteger)object3);
                if (bigInteger4.bitLength() != n) {
                    object4 = ((BigInteger)object4).max((BigInteger)object3);
                    continue;
                }
                if (WNafUtil.getNafWeight(bigInteger4) >= n2 >> 2) break;
                object4 = ((RSAKeyPairGenerator)object2).chooseRandomPrime(n3, bigInteger7, bigInteger2);
            } while (true);
            if (((BigInteger)object4).compareTo((BigInteger)object3) < 0) {
                object2 = object4;
                object4 = object3;
                object3 = object2;
            }
            if (((BigInteger)(object2 = bigInteger7.modInverse((bigInteger6 = ((BigInteger)object4).subtract(ONE)).divide(bigInteger6.gcd(bigInteger5 = ((BigInteger)object3).subtract(ONE))).multiply(bigInteger5)))).compareTo(bigInteger) <= 0) continue;
            object = ((BigInteger)object2).remainder(bigInteger6);
            bigInteger6 = ((BigInteger)object2).remainder(bigInteger5);
            bigInteger5 = ((BigInteger)object3).modInverse((BigInteger)object4);
            object = new AsymmetricCipherKeyPair(new RSAKeyParameters(false, bigInteger4, bigInteger7), new RSAPrivateCrtKeyParameters(bigInteger4, bigInteger7, (BigInteger)object2, (BigInteger)object4, (BigInteger)object3, (BigInteger)object, bigInteger6, bigInteger5));
            bl = true;
        } while (true);
        return object;
    }

    @Override
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.param = (RSAKeyGenerationParameters)keyGenerationParameters;
    }

    protected boolean isProbablePrime(BigInteger bigInteger) {
        int n = RSAKeyPairGenerator.getNumberOfIterations(bigInteger.bitLength(), this.param.getCertainty());
        boolean bl = !Primes.hasAnySmallFactors(bigInteger) && Primes.isMRProbablePrime(bigInteger, this.param.getRandom(), n);
        return bl;
    }
}

