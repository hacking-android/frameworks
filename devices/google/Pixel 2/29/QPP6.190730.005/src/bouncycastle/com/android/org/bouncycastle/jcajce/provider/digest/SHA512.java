/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.digest;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.crypto.CipherKeyGenerator;
import com.android.org.bouncycastle.crypto.Mac;
import com.android.org.bouncycastle.crypto.digests.SHA512Digest;
import com.android.org.bouncycastle.crypto.macs.HMac;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.digest.BCMessageDigest;
import com.android.org.bouncycastle.jcajce.provider.digest.DigestAlgorithmProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public class SHA512 {
    private SHA512() {
    }

    public static class Digest
    extends BCMessageDigest
    implements Cloneable {
        public Digest() {
            super(new SHA512Digest());
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            Digest digest = (Digest)super.clone();
            digest.digest = new SHA512Digest((SHA512Digest)this.digest);
            return digest;
        }
    }

    public static class HashMac
    extends BaseMac {
        public HashMac() {
            super(new HMac(new SHA512Digest()));
        }
    }

    public static class KeyGenerator
    extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA512", 512, new CipherKeyGenerator());
        }
    }

    public static class Mappings
    extends DigestAlgorithmProvider {
        private static final String PREFIX = SHA512.class.getName();

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$Digest");
            configurableProvider.addAlgorithm("MessageDigest.SHA-512", stringBuilder.toString());
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA512", "SHA-512");
            stringBuilder = new StringBuilder();
            stringBuilder.append("Alg.Alias.MessageDigest.");
            stringBuilder.append(NISTObjectIdentifiers.id_sha512);
            configurableProvider.addAlgorithm(stringBuilder.toString(), "SHA-512");
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$HashMac");
            configurableProvider.addAlgorithm("Mac.PBEWITHHMACSHA512", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$HashMac");
            String string = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$KeyGenerator");
            this.addHMACAlgorithm(configurableProvider, "SHA512", string, stringBuilder.toString());
            this.addHMACAlias(configurableProvider, "SHA512", PKCSObjectIdentifiers.id_hmacWithSHA512);
        }
    }

}

