/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import com.android.org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.android.org.bouncycastle.math.ec.ECConstants;
import com.android.org.bouncycastle.math.ec.ECMultiplier;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.FixedPointCombMultiplier;
import com.android.org.bouncycastle.math.ec.WNafUtil;
import com.android.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.SecureRandom;

public class ECKeyPairGenerator
implements AsymmetricCipherKeyPairGenerator,
ECConstants {
    ECDomainParameters params;
    SecureRandom random;

    protected ECMultiplier createBasePointMultiplier() {
        return new FixedPointCombMultiplier();
    }

    @Override
    public AsymmetricCipherKeyPair generateKeyPair() {
        BigInteger bigInteger;
        BigInteger bigInteger2 = this.params.getN();
        int n = bigInteger2.bitLength();
        while ((bigInteger = BigIntegers.createRandomBigInteger(n, this.random)).compareTo(TWO) < 0 || bigInteger.compareTo(bigInteger2) >= 0 || WNafUtil.getNafWeight(bigInteger) < n >>> 2) {
        }
        return new AsymmetricCipherKeyPair(new ECPublicKeyParameters(this.createBasePointMultiplier().multiply(this.params.getG(), bigInteger), this.params), new ECPrivateKeyParameters(bigInteger, this.params));
    }

    @Override
    public void init(KeyGenerationParameters keyGenerationParameters) {
        keyGenerationParameters = (ECKeyGenerationParameters)keyGenerationParameters;
        this.random = keyGenerationParameters.getRandom();
        this.params = ((ECKeyGenerationParameters)keyGenerationParameters).getDomainParameters();
        if (this.random == null) {
            this.random = CryptoServicesRegistrar.getSecureRandom();
        }
    }
}

