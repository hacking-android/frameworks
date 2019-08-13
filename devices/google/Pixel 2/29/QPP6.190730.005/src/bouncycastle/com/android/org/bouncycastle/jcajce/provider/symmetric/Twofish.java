/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.engines.TwofishEngine;
import com.android.org.bouncycastle.crypto.modes.CBCBlockCipher;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.SymmetricAlgorithmProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public final class Twofish {
    private Twofish() {
    }

    public static class Mappings
    extends SymmetricAlgorithmProvider {
        private static final String PREFIX = Twofish.class.getName();

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDTWOFISH", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDTWOFISH-CBC", "PKCS12PBE");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHA");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAANDTWOFISH-CBC", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHAKeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAANDTWOFISH-CBC", stringBuilder.toString());
        }
    }

    public static class PBEWithSHA
    extends BaseBlockCipher {
        public PBEWithSHA() {
            super(new CBCBlockCipher(new TwofishEngine()), 2, 1, 256, 16);
        }
    }

    public static class PBEWithSHAKeyFactory
    extends PBESecretKeyFactory {
        public PBEWithSHAKeyFactory() {
            super("PBEwithSHAandTwofish-CBC", null, true, 2, 1, 256, 128);
        }
    }

}

