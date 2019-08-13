/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public abstract class KeyPairGeneratorSpi {
    public abstract KeyPair generateKeyPair();

    public abstract void initialize(int var1, SecureRandom var2);

    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        throw new UnsupportedOperationException();
    }
}

