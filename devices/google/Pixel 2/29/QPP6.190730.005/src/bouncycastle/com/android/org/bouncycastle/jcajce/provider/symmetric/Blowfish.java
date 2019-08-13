/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherKeyGenerator;
import com.android.org.bouncycastle.crypto.engines.BlowfishEngine;
import com.android.org.bouncycastle.crypto.modes.CBCBlockCipher;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import com.android.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class Blowfish {
    private Blowfish() {
    }

    public static class AlgParams
    extends IvAlgorithmParameters {
        @Override
        protected String engineToString() {
            return "Blowfish IV";
        }
    }

    public static class CBC
    extends BaseBlockCipher {
        public CBC() {
            super(new CBCBlockCipher(new BlowfishEngine()), 64);
        }
    }

    public static class ECB
    extends BaseBlockCipher {
        public ECB() {
            super(new BlowfishEngine());
        }
    }

    public static class KeyGen
    extends BaseKeyGenerator {
        public KeyGen() {
            super("Blowfish", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings
    extends AlgorithmProvider {
        private static final String PREFIX = Blowfish.class.getName();

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$ECB");
            configurableProvider.addAlgorithm("Cipher.BLOWFISH", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator.BLOWFISH", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.KeyGenerator", MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CBC, "BLOWFISH");
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.BLOWFISH", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters", MiscObjectIdentifiers.cryptlib_algorithm_blowfish_CBC, "BLOWFISH");
        }
    }

}

