/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.digest;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.crypto.CipherKeyGenerator;
import com.android.org.bouncycastle.crypto.Mac;
import com.android.org.bouncycastle.crypto.digests.SHA224Digest;
import com.android.org.bouncycastle.crypto.macs.HMac;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.digest.BCMessageDigest;
import com.android.org.bouncycastle.jcajce.provider.digest.DigestAlgorithmProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public class SHA224 {
    private SHA224() {
    }

    public static class Digest
    extends BCMessageDigest
    implements Cloneable {
        public Digest() {
            super(new SHA224Digest());
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            Digest digest = (Digest)super.clone();
            digest.digest = new SHA224Digest((SHA224Digest)this.digest);
            return digest;
        }
    }

    public static class HashMac
    extends BaseMac {
        public HashMac() {
            super(new HMac(new SHA224Digest()));
        }
    }

    public static class KeyGenerator
    extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACSHA224", 224, new CipherKeyGenerator());
        }
    }

    public static class Mappings
    extends DigestAlgorithmProvider {
        private static final String PREFIX = SHA224.class.getName();

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(PREFIX);
            charSequence.append("$Digest");
            configurableProvider.addAlgorithm("MessageDigest.SHA-224", charSequence.toString());
            configurableProvider.addAlgorithm("Alg.Alias.MessageDigest.SHA224", "SHA-224");
            charSequence = new StringBuilder();
            charSequence.append("Alg.Alias.MessageDigest.");
            charSequence.append(NISTObjectIdentifiers.id_sha224);
            configurableProvider.addAlgorithm(charSequence.toString(), "SHA-224");
            charSequence = new StringBuilder();
            charSequence.append(PREFIX);
            charSequence.append("$HashMac");
            configurableProvider.addAlgorithm("Mac.PBEWITHHMACSHA224", charSequence.toString());
            charSequence = new StringBuilder();
            charSequence.append(PREFIX);
            charSequence.append("$HashMac");
            charSequence = charSequence.toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$KeyGenerator");
            this.addHMACAlgorithm(configurableProvider, "SHA224", (String)charSequence, stringBuilder.toString());
            this.addHMACAlias(configurableProvider, "SHA224", PKCSObjectIdentifiers.id_hmacWithSHA224);
        }
    }

}

