/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public abstract class AlgorithmParameterGeneratorSpi {
    protected abstract AlgorithmParameters engineGenerateParameters();

    protected abstract void engineInit(int var1, SecureRandom var2);

    protected abstract void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException;
}

