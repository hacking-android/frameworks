/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.crypto.KeyGenerationParameters;
import com.android.org.bouncycastle.crypto.generators.DESKeyGenerator;
import com.android.org.bouncycastle.crypto.params.DESedeParameters;
import java.security.SecureRandom;

public class DESedeKeyGenerator
extends DESKeyGenerator {
    private static final int MAX_IT = 20;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public byte[] generateKey() {
        var1_1 = new byte[this.strength];
        var2_2 = 0;
        do lbl-1000: // 3 sources:
        {
            this.random.nextBytes(var1_1);
            DESedeParameters.setOddParity(var1_1);
            var3_3 = var2_2 + 1;
            if (var3_3 >= 20) break;
            var2_2 = var3_3;
            if (DESedeParameters.isWeakKey(var1_1, 0, var1_1.length)) ** GOTO lbl-1000
            var2_2 = var3_3;
        } while (!DESedeParameters.isRealEDEKey(var1_1, 0));
        if (DESedeParameters.isWeakKey(var1_1, 0, var1_1.length) != false) throw new IllegalStateException("Unable to generate DES-EDE key");
        if (DESedeParameters.isRealEDEKey(var1_1, 0) == false) throw new IllegalStateException("Unable to generate DES-EDE key");
        return var1_1;
    }

    @Override
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.random = keyGenerationParameters.getRandom();
        this.strength = (keyGenerationParameters.getStrength() + 7) / 8;
        if (this.strength != 0 && this.strength != 21) {
            if (this.strength == 14) {
                this.strength = 16;
            } else if (this.strength != 24 && this.strength != 16) {
                throw new IllegalArgumentException("DESede key must be 192 or 128 bits long.");
            }
        } else {
            this.strength = 24;
        }
    }
}

