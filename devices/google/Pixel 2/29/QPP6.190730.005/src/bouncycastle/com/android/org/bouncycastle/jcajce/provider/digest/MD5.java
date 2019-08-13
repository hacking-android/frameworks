/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.digest;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.crypto.CipherKeyGenerator;
import com.android.org.bouncycastle.crypto.Mac;
import com.android.org.bouncycastle.crypto.digests.MD5Digest;
import com.android.org.bouncycastle.crypto.macs.HMac;
import com.android.org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import com.android.org.bouncycastle.jcajce.provider.digest.BCMessageDigest;
import com.android.org.bouncycastle.jcajce.provider.digest.DigestAlgorithmProvider;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import com.android.org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;

public class MD5 {
    private MD5() {
    }

    public static class Digest
    extends BCMessageDigest
    implements Cloneable {
        public Digest() {
            super(new MD5Digest());
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            Digest digest = (Digest)super.clone();
            digest.digest = new MD5Digest((MD5Digest)this.digest);
            return digest;
        }
    }

    public static class HashMac
    extends BaseMac {
        public HashMac() {
            super(new HMac(new MD5Digest()));
        }
    }

    public static class KeyGenerator
    extends BaseKeyGenerator {
        public KeyGenerator() {
            super("HMACMD5", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings
    extends DigestAlgorithmProvider {
        private static final String PREFIX = MD5.class.getName();

        @Override
        public void configure(ConfigurableProvider configurableProvider) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append(PREFIX);
            charSequence.append("$Digest");
            configurableProvider.addAlgorithm("MessageDigest.MD5", charSequence.toString());
            charSequence = new StringBuilder();
            charSequence.append("Alg.Alias.MessageDigest.");
            charSequence.append(PKCSObjectIdentifiers.md5);
            configurableProvider.addAlgorithm(charSequence.toString(), "MD5");
            charSequence = new StringBuilder();
            charSequence.append(PREFIX);
            charSequence.append("$HashMac");
            charSequence = charSequence.toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PREFIX);
            stringBuilder.append("$KeyGenerator");
            this.addHMACAlgorithm(configurableProvider, "MD5", (String)charSequence, stringBuilder.toString());
            this.addHMACAlias(configurableProvider, "MD5", IANAObjectIdentifiers.hmacMD5);
        }
    }

}

