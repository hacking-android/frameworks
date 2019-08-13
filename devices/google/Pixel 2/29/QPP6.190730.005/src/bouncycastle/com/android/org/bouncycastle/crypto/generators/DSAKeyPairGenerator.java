/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import com.android.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.DSAKeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import com.android.org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import com.android.org.bouncycastle.math.ec.WNafUtil;
import com.android.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.SecureRandom;

public class DSAKeyPairGenerator
implements AsymmetricCipherKeyPairGenerator {
    private static final BigInteger ONE = BigInteger.valueOf(1L);
    private DSAKeyGenerationParameters param;

    private static BigInteger calculatePublicKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        return bigInteger2.modPow(bigInteger3, bigInteger);
    }

    private static BigInteger generatePrivateKey(BigInteger bigInteger, SecureRandom secureRandom) {
        BigInteger bigInteger2;
        int n = bigInteger.bitLength();
        do {
            bigInteger2 = ONE;
        } while (WNafUtil.getNafWeight(bigInteger2 = BigIntegers.createRandomInRange(bigInteger2, bigInteger.subtract(bigInteger2), secureRandom)) < n >>> 2);
        return bigInteger2;
    }

    @Override
    public AsymmetricCipherKeyPair generateKeyPair() {
        DSAParameters dSAParameters = this.param.getParameters();
        BigInteger bigInteger = DSAKeyPairGenerator.generatePrivateKey(dSAParameters.getQ(), this.param.getRandom());
        return new AsymmetricCipherKeyPair(new DSAPublicKeyParameters(DSAKeyPairGenerator.calculatePublicKey(dSAParameters.getP(), dSAParameters.getG(), bigInteger), dSAParameters), new DSAPrivateKeyParameters(bigInteger, dSAParameters));
    }

    @Override
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.param = (DSAKeyGenerationParameters)keyGenerationParameters;
    }
}

