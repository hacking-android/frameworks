/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric.util;

import com.android.org.bouncycastle.crypto.CipherKeyGenerator;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class BaseKeyGenerator
extends KeyGeneratorSpi {
    protected String algName;
    protected int defaultKeySize;
    protected CipherKeyGenerator engine;
    protected int keySize;
    protected boolean uninitialised = true;

    protected BaseKeyGenerator(String string, int n, CipherKeyGenerator cipherKeyGenerator) {
        this.algName = string;
        this.defaultKeySize = n;
        this.keySize = n;
        this.engine = cipherKeyGenerator;
    }

    @Override
    protected SecretKey engineGenerateKey() {
        if (this.uninitialised) {
            this.engine.init(new KeyGenerationParameters(CryptoServicesRegistrar.getSecureRandom(), this.defaultKeySize));
            this.uninitialised = false;
        }
        return new SecretKeySpec(this.engine.generateKey(), this.algName);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected void engineInit(int var1_1, SecureRandom var2_2) {
        var3_4 = var2_2;
        if (var2_2 != null) ** GOTO lbl5
        try {
            var3_4 = CryptoServicesRegistrar.getSecureRandom();
lbl5: // 2 sources:
            var4_5 = this.engine;
            var2_2 = new KeyGenerationParameters((SecureRandom)var3_4, var1_1);
            var4_5.init((KeyGenerationParameters)var2_2);
            this.uninitialised = false;
            return;
        }
        catch (IllegalArgumentException var2_3) {
            throw new InvalidParameterException(var2_3.getMessage());
        }
    }

    @Override
    protected void engineInit(SecureRandom secureRandom) {
        if (secureRandom != null) {
            this.engine.init(new KeyGenerationParameters(secureRandom, this.defaultKeySize));
            this.uninitialised = false;
        }
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        throw new InvalidAlgorithmParameterException("Not Implemented");
    }
}

