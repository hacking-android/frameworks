/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.engines;

import com.android.org.bouncycastle.crypto.AsymmetricBlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.engines.RSACoreEngine;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import com.android.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSABlindedEngine
implements AsymmetricBlockCipher {
    private static final BigInteger ONE = BigInteger.valueOf(1L);
    private RSACoreEngine core = new RSACoreEngine();
    private RSAKeyParameters key;
    private SecureRandom random;

    @Override
    public int getInputBlockSize() {
        return this.core.getInputBlockSize();
    }

    @Override
    public int getOutputBlockSize() {
        return this.core.getOutputBlockSize();
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) {
        this.core.init(bl, cipherParameters);
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters = (ParametersWithRandom)cipherParameters;
            this.key = (RSAKeyParameters)((ParametersWithRandom)cipherParameters).getParameters();
            this.random = ((ParametersWithRandom)cipherParameters).getRandom();
        } else {
            this.key = (RSAKeyParameters)cipherParameters;
            this.random = CryptoServicesRegistrar.getSecureRandom();
        }
    }

    @Override
    public byte[] processBlock(byte[] object, int n, int n2) {
        if (this.key != null) {
            BigInteger bigInteger = this.core.convertInput((byte[])object, n, n2);
            object = this.key;
            if (object instanceof RSAPrivateCrtKeyParameters) {
                BigInteger bigInteger2 = ((RSAPrivateCrtKeyParameters)(object = (RSAPrivateCrtKeyParameters)object)).getPublicExponent();
                if (bigInteger2 != null) {
                    BigInteger bigInteger3 = ((RSAKeyParameters)object).getModulus();
                    object = ONE;
                    BigInteger bigInteger4 = BigIntegers.createRandomInRange((BigInteger)object, bigInteger3.subtract((BigInteger)object), this.random);
                    object = bigInteger4.modPow(bigInteger2, bigInteger3).multiply(bigInteger).mod(bigInteger3);
                    if (!bigInteger.equals(((BigInteger)(object = this.core.processBlock((BigInteger)object).multiply(bigInteger4.modInverse(bigInteger3)).mod(bigInteger3))).modPow(bigInteger2, bigInteger3))) {
                        throw new IllegalStateException("RSA engine faulty decryption/signing detected");
                    }
                } else {
                    object = this.core.processBlock(bigInteger);
                }
            } else {
                object = this.core.processBlock(bigInteger);
            }
            return this.core.convertOutput((BigInteger)object);
        }
        throw new IllegalStateException("RSA engine not initialised");
    }
}

