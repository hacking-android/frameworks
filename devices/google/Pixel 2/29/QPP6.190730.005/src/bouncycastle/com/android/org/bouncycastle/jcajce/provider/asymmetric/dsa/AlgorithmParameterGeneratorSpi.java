/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa;

import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.digests.SHA256Digest;
import com.android.org.bouncycastle.crypto.generators.DSAParametersGenerator;
import com.android.org.bouncycastle.crypto.params.DSAParameterGenerationParameters;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAlgorithmParameterGeneratorSpi;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.DSAParameterSpec;

public class AlgorithmParameterGeneratorSpi
extends BaseAlgorithmParameterGeneratorSpi {
    protected DSAParameterGenerationParameters params;
    protected SecureRandom random;
    protected int strength = 1024;

    @Override
    protected AlgorithmParameters engineGenerateParameters() {
        Object object = this.strength <= 1024 ? new DSAParametersGenerator() : new DSAParametersGenerator(new SHA256Digest());
        if (this.random == null) {
            this.random = CryptoServicesRegistrar.getSecureRandom();
        }
        int n = PrimeCertaintyCalculator.getDefaultCertainty(this.strength);
        int n2 = this.strength;
        if (n2 == 1024) {
            this.params = new DSAParameterGenerationParameters(1024, 160, n, this.random);
            ((DSAParametersGenerator)object).init(this.params);
        } else if (n2 > 1024) {
            this.params = new DSAParameterGenerationParameters(n2, 256, n, this.random);
            ((DSAParametersGenerator)object).init(this.params);
        } else {
            ((DSAParametersGenerator)object).init(n2, n, this.random);
        }
        DSAParameters dSAParameters = ((DSAParametersGenerator)object).generateParameters();
        try {
            AlgorithmParameters algorithmParameters = this.createParametersInstance("DSA");
            object = new DSAParameterSpec(dSAParameters.getP(), dSAParameters.getQ(), dSAParameters.getG());
            algorithmParameters.init((AlgorithmParameterSpec)object);
            return algorithmParameters;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    protected void engineInit(int n, SecureRandom secureRandom) {
        if (n >= 512 && n <= 3072) {
            if (n <= 1024 && n % 64 != 0) {
                throw new InvalidParameterException("strength must be a multiple of 64 below 1024 bits.");
            }
            if (n > 1024 && n % 1024 != 0) {
                throw new InvalidParameterException("strength must be a multiple of 1024 above 1024 bits.");
            }
            this.strength = n;
            this.random = secureRandom;
            return;
        }
        throw new InvalidParameterException("strength must be from 512 - 3072");
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for DSA parameter generation.");
    }
}

