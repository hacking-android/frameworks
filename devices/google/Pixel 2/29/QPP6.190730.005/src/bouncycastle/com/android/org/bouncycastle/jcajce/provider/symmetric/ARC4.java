/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.symmetric;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.crypto.CipherKeyGenerator;
import com.android.org.bouncycastle.crypto.StreamCipher;
import com.android.org.bouncycastle.crypto.engines.RC4Engine;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseStreamCipher;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;
import com.android.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class ARC4 {
    private ARC4() {
    }

    public static class Base
    extends BaseStreamCipher {
        public Base() {
            super(new RC4Engine(), 0);
        }
    }

    public static class KeyGen
    extends BaseKeyGenerator {
        public KeyGen() {
            super("ARC4", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings
    extends AlgorithmProvider {
        private static final String PREFIX = ARC4.class.getName();

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$Base");
            configurableProvider.addAlgorithm("Cipher.ARC4", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.rc4, "ARC4");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.ARCFOUR", "ARC4");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RC4", "ARC4");
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator.ARC4", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.RC4", "ARC4");
            configurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.1.2.840.113549.3.4", "ARC4");
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHAAnd128BitKeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND128BITRC4", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHAAnd40BitKeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHSHAAND40BITRC4", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "PKCS12PBE");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.AlgorithmParameters.");
            stringBuilder.append(PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND40BITRC4", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITRC4", "PKCS12PBE");
            configurableProvider.addAlgorithm("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDRC4", "PKCS12PBE");
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHAAnd128Bit");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND128BITRC4", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithSHAAnd40Bit");
            configurableProvider.addAlgorithm("Cipher.PBEWITHSHAAND40BITRC4", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4, "PBEWITHSHAAND128BITRC4");
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory", PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4, "PBEWITHSHAAND40BITRC4");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND128BITRC4", "PBEWITHSHAAND128BITRC4");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.PBEWITHSHA1AND40BITRC4", "PBEWITHSHAAND40BITRC4");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4, "PBEWITHSHAAND128BITRC4");
            configurableProvider.addAlgorithm("Alg.Alias.Cipher", PKCSObjectIdentifiers.pbeWithSHAAnd40BitRC4, "PBEWITHSHAAND40BITRC4");
        }
    }

    public static class PBEWithSHAAnd128Bit
    extends BaseStreamCipher {
        public PBEWithSHAAnd128Bit() {
            super(new RC4Engine(), 0, 128, 1);
        }
    }

    public static class PBEWithSHAAnd128BitKeyFactory
    extends PBESecretKeyFactory {
        public PBEWithSHAAnd128BitKeyFactory() {
            super("PBEWithSHAAnd128BitRC4", PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4, true, 2, 1, 128, 0);
        }
    }

    public static class PBEWithSHAAnd40Bit
    extends BaseStreamCipher {
        public PBEWithSHAAnd40Bit() {
            super(new RC4Engine(), 0, 40, 1);
        }
    }

    public static class PBEWithSHAAnd40BitKeyFactory
    extends PBESecretKeyFactory {
        public PBEWithSHAAnd40BitKeyFactory() {
            super("PBEWithSHAAnd128BitRC4", PKCSObjectIdentifiers.pbeWithSHAAnd128BitRC4, true, 2, 1, 40, 0);
        }
    }

}

