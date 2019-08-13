/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.keystore;

import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.util.AsymmetricAlgorithmProvider;

public class BC {
    private static final String PREFIX = "com.android.org.bouncycastle.jcajce.provider.keystore.bc.";

    public static class Mappings
    extends AsymmetricAlgorithmProvider {
        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("KeyStore.BKS", "com.android.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi$Std");
            configurableProvider.addAlgorithm("KeyStore.BouncyCastle", "com.android.org.bouncycastle.jcajce.provider.keystore.bc.BcKeyStoreSpi$BouncyCastleStore");
            configurableProvider.addAlgorithm("Alg.Alias.KeyStore.UBER", "BouncyCastle");
            configurableProvider.addAlgorithm("Alg.Alias.KeyStore.BOUNCYCASTLE", "BouncyCastle");
            configurableProvider.addAlgorithm("Alg.Alias.KeyStore.bouncycastle", "BouncyCastle");
        }
    }

}

