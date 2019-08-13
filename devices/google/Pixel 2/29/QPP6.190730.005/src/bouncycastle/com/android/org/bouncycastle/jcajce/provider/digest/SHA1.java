/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.digest;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import com.android.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.crypto.CipherKeyGenerator;
import com.android.org.bouncycastle.crypto.Mac;
import com.android.org.bouncycastle.crypto.digests.SHA1Digest;
import com.android.org.bouncycastle.crypto.macs.HMac;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.digest.BCMessageDigest;
import com.android.org.bouncycastle.jcajce.provider.digest.DigestAlgorithmProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.PBESecretKeyFactory;

public class SHA1 {
    private SHA1() {
    }

    public static class Digest
    extends BCMessageDigest
    implements Cloneable {
        public Digest() {
            super(new SHA1Digest());
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            Digest digest = (Digest)super.clone();
            digest.digest = new SHA1Digest((SHA1Digest)this.digest);
            return digest;
        }
    }

    public static class HashMac
    extends BaseMac {
        public HashMac() {
            super(new HMac(new SHA1Digest()));
        }
    }

    public static class KeyGenerator
    extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA1", 160, new CipherKeyGenerator());
        }
    }

    public static class Mappings
    extends DigestAlgorithmProvider {
        private static final String PREFIX = SHA1.class.getName();

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$Digest");
            configurableProvider.addAlgorithm("MessageDigest.SHA-1", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA1", "SHA-1");
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA", "SHA-1");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.MessageDigest.");
            stringBuilder.append(OIWObjectIdentifiers.idSHA1);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "SHA-1");
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$HashMac");
            String string = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$KeyGenerator");
            this.addHMACAlgorithm(configurableProvider, "SHA1", string, stringBuilder.toString());
            this.addHMACAlias(configurableProvider, "SHA1", PKCSObjectIdentifiers.id_hmacWithSHA1);
            this.addHMACAlias(configurableProvider, "SHA1", IANAObjectIdentifiers.hmacSHA1);
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$SHA1Mac");
            configurableProvider.addAlgorithm("Mac.PBEWITHHMACSHA", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$SHA1Mac");
            configurableProvider.addAlgorithm("Mac.PBEWITHHMACSHA1", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.SecretKeyFactory.PBEWITHHMACSHA", "PBEWITHHMACSHA1");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.SecretKeyFactory.");
            stringBuilder.append(OIWObjectIdentifiers.idSHA1);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "PBEWITHHMACSHA1");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.Mac.");
            stringBuilder.append(OIWObjectIdentifiers.idSHA1);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "PBEWITHHMACSHA");
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$PBEWithMacKeyFactory");
            configurableProvider.addAlgorithm("SecretKeyFactory.PBEWITHHMACSHA1", stringBuilder.toString());
        }
    }

    public static class PBEWithMacKeyFactory
    extends PBESecretKeyFactory {
        public PBEWithMacKeyFactory() {
            super("PBEwithHmacSHA", null, false, 2, 1, 160, 0);
        }
    }

    public static class SHA1Mac
    extends BaseMac {
        public SHA1Mac() {
            super(new HMac(new SHA1Digest()));
        }
    }

}

