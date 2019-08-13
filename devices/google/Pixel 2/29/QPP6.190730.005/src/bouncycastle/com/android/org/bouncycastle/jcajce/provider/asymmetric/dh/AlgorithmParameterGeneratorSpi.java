/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dh;

import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.generators.DHParametersGenerator;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAlgorithmParameterGeneratorSpi;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.PrimeCertaintyCalculator;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;

public class AlgorithmParameterGeneratorSpi
extends BaseAlgorithmParameterGeneratorSpi {
    private int l = 0;
    protected SecureRandom random;
    protected int strength = 2048;

    @Override
    protected AlgorithmParameters engineGenerateParameters() {
        Object object = new DHParametersGenerator();
        int n = PrimeCertaintyCalculator.getDefaultCertainty(this.strength);
        Object object2 = this.random;
        if (object2 != null) {
            ((DHParametersGenerator)object).init(this.strength, n, (SecureRandom)object2);
        } else {
            ((DHParametersGenerator)object).init(this.strength, n, CryptoServicesRegistrar.getSecureRandom());
        }
        object2 = ((DHParametersGenerator)object).generateParameters();
        try {
            object = this.createParametersInstance("DH");
            DHParameterSpec dHParameterSpec = new DHParameterSpec(((DHParameters)object2).getP(), ((DHParameters)object2).getG(), this.l);
            ((AlgorithmParameters)object).init(dHParameterSpec);
            return object;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    protected void engineInit(int n, SecureRandom secureRandom) {
        this.strength = n;
        this.random = secureRandom;
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec instanceof DHGenParameterSpec) {
            algorithmParameterSpec = (DHGenParameterSpec)algorithmParameterSpec;
            this.strength = ((DHGenParameterSpec)algorithmParameterSpec).getPrimeSize();
            this.l = ((DHGenParameterSpec)algorithmParameterSpec).getExponentSize();
            this.random = secureRandom;
            return;
        }
        throw new InvalidAlgorithmParameterException("DH parameter generator requires a DHGenParameterSpec for initialisation");
    }
}

