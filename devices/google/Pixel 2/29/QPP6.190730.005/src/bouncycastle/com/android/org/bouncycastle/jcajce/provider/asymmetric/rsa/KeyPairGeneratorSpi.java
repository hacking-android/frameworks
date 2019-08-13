/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;

public class KeyPairGeneratorSpi
extends KeyPairGenerator {
    static final BigInteger defaultPublicExponent = BigInteger.valueOf(65537L);
    RSAKeyPairGenerator engine;
    RSAKeyGenerationParameters param;

    public KeyPairGeneratorSpi() {
        super("RSA");
        this.engine = new RSAKeyPairGenerator();
        this.param = new RSAKeyGenerationParameters(defaultPublicExponent, CryptoServicesRegistrar.getSecureRandom(), 2048, PrimeCertaintyCalculator.getDefaultCertainty(2048));
        this.engine.init(this.param);
    }

    public KeyPairGeneratorSpi(String string) {
        super(string);
    }

    @Override
    public KeyPair generateKeyPair() {
        Object object = this.engine.generateKeyPair();
        RSAKeyParameters rSAKeyParameters = (RSAKeyParameters)((AsymmetricCipherKeyPair)object).getPublic();
        object = (RSAPrivateCrtKeyParameters)((AsymmetricCipherKeyPair)object).getPrivate();
        return new KeyPair(new BCRSAPublicKey(rSAKeyParameters), new BCRSAPrivateCrtKey((RSAPrivateCrtKeyParameters)object));
    }

    @Override
    public void initialize(int n, SecureRandom secureRandom) {
        BigInteger bigInteger = defaultPublicExponent;
        if (secureRandom == null) {
            secureRandom = new SecureRandom();
        }
        this.param = new RSAKeyGenerationParameters(bigInteger, secureRandom, n, PrimeCertaintyCalculator.getDefaultCertainty(n));
        this.engine.init(this.param);
    }

    @Override
    public void initialize(AlgorithmParameterSpec object, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (object instanceof RSAKeyGenParameterSpec) {
            RSAKeyGenParameterSpec rSAKeyGenParameterSpec = (RSAKeyGenParameterSpec)object;
            BigInteger bigInteger = rSAKeyGenParameterSpec.getPublicExponent();
            object = secureRandom != null ? secureRandom : new SecureRandom();
            this.param = new RSAKeyGenerationParameters(bigInteger, (SecureRandom)object, rSAKeyGenParameterSpec.getKeysize(), PrimeCertaintyCalculator.getDefaultCertainty(2048));
            this.engine.init(this.param);
            return;
        }
        throw new InvalidAlgorithmParameterException("parameter object not a RSAKeyGenParameterSpec");
    }
}

