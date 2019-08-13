/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import com.android.org.bouncycastle.crypto.KeyGenerationParameters;

public interface AsymmetricCipherKeyPairGenerator {
    public AsymmetricCipherKeyPair generateKeyPair();

    public void init(KeyGenerationParameters var1);
}

