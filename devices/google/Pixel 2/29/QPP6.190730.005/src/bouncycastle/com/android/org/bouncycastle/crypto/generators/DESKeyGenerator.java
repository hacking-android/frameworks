/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.crypto.CipherKeyGenerator;
import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.params.DESParameters;
import java.security.SecureRandom;

public class DESKeyGenerator
extends CipherKeyGenerator {
    @Override
    public byte[] generateKey() {
        byte[] arrby = new byte[8];
        do {
            this.random.nextBytes(arrby);
            DESParameters.setOddParity(arrby);
        } while (DESParameters.isWeakKey(arrby, 0));
        return arrby;
    }

    @Override
    public void init(KeyGenerationParameters keyGenerationParameters) {
        super.init(keyGenerationParameters);
        if (this.strength != 0 && this.strength != 7) {
            if (this.strength != 8) {
                throw new IllegalArgumentException("DES key must be 64 bits long.");
            }
        } else {
            this.strength = 8;
        }
    }
}

