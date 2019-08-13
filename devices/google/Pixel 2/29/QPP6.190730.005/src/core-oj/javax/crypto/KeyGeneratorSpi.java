/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.SecretKey;

public abstract class KeyGeneratorSpi {
    protected abstract SecretKey engineGenerateKey();

    protected abstract void engineInit(int var1, SecureRandom var2);

    protected abstract void engineInit(SecureRandom var1);

    protected abstract void engineInit(AlgorithmParameterSpec var1, SecureRandom var2) throws InvalidAlgorithmParameterException;
}

