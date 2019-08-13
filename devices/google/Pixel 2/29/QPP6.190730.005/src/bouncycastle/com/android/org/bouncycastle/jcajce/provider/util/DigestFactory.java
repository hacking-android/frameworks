/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.util;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import com.android.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.util.Strings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DigestFactory {
    private static Set md5 = new HashSet();
    private static Map oids;
    private static Set sha1;
    private static Set sha224;
    private static Set sha256;
    private static Set sha384;
    private static Set sha512;

    static {
        sha1 = new HashSet();
        sha224 = new HashSet();
        sha256 = new HashSet();
        sha384 = new HashSet();
        sha512 = new HashSet();
        oids = new HashMap();
        md5.add("MD5");
        md5.add(PKCSObjectIdentifiers.md5.getId());
        sha1.add("SHA1");
        sha1.add("SHA-1");
        sha1.add(OIWObjectIdentifiers.idSHA1.getId());
        sha224.add("SHA224");
        sha224.add("SHA-224");
        sha224.add(NISTObjectIdentifiers.id_sha224.getId());
        sha256.add("SHA256");
        sha256.add("SHA-256");
        sha256.add(NISTObjectIdentifiers.id_sha256.getId());
        sha384.add("SHA384");
        sha384.add("SHA-384");
        sha384.add(NISTObjectIdentifiers.id_sha384.getId());
        sha512.add("SHA512");
        sha512.add("SHA-512");
        sha512.add(NISTObjectIdentifiers.id_sha512.getId());
        oids.put("MD5", PKCSObjectIdentifiers.md5);
        oids.put(PKCSObjectIdentifiers.md5.getId(), PKCSObjectIdentifiers.md5);
        oids.put("SHA1", OIWObjectIdentifiers.idSHA1);
        oids.put("SHA-1", OIWObjectIdentifiers.idSHA1);
        oids.put(OIWObjectIdentifiers.idSHA1.getId(), OIWObjectIdentifiers.idSHA1);
        oids.put("SHA224", NISTObjectIdentifiers.id_sha224);
        oids.put("SHA-224", NISTObjectIdentifiers.id_sha224);
        oids.put(NISTObjectIdentifiers.id_sha224.getId(), NISTObjectIdentifiers.id_sha224);
        oids.put("SHA256", NISTObjectIdentifiers.id_sha256);
        oids.put("SHA-256", NISTObjectIdentifiers.id_sha256);
        oids.put(NISTObjectIdentifiers.id_sha256.getId(), NISTObjectIdentifiers.id_sha256);
        oids.put("SHA384", NISTObjectIdentifiers.id_sha384);
        oids.put("SHA-384", NISTObjectIdentifiers.id_sha384);
        oids.put(NISTObjectIdentifiers.id_sha384.getId(), NISTObjectIdentifiers.id_sha384);
        oids.put("SHA512", NISTObjectIdentifiers.id_sha512);
        oids.put("SHA-512", NISTObjectIdentifiers.id_sha512);
        oids.put(NISTObjectIdentifiers.id_sha512.getId(), NISTObjectIdentifiers.id_sha512);
        oids.put("SHA512(224)", NISTObjectIdentifiers.id_sha512_224);
        oids.put("SHA-512(224)", NISTObjectIdentifiers.id_sha512_224);
        oids.put(NISTObjectIdentifiers.id_sha512_224.getId(), NISTObjectIdentifiers.id_sha512_224);
        oids.put("SHA512(256)", NISTObjectIdentifiers.id_sha512_256);
        oids.put("SHA-512(256)", NISTObjectIdentifiers.id_sha512_256);
        oids.put(NISTObjectIdentifiers.id_sha512_256.getId(), NISTObjectIdentifiers.id_sha512_256);
        oids.put("SHA3-224", NISTObjectIdentifiers.id_sha3_224);
        oids.put(NISTObjectIdentifiers.id_sha3_224.getId(), NISTObjectIdentifiers.id_sha3_224);
        oids.put("SHA3-256", NISTObjectIdentifiers.id_sha3_256);
        oids.put(NISTObjectIdentifiers.id_sha3_256.getId(), NISTObjectIdentifiers.id_sha3_256);
        oids.put("SHA3-384", NISTObjectIdentifiers.id_sha3_384);
        oids.put(NISTObjectIdentifiers.id_sha3_384.getId(), NISTObjectIdentifiers.id_sha3_384);
        oids.put("SHA3-512", NISTObjectIdentifiers.id_sha3_512);
        oids.put(NISTObjectIdentifiers.id_sha3_512.getId(), NISTObjectIdentifiers.id_sha3_512);
    }

    public static Digest getDigest(String string) {
        if (sha1.contains(string = Strings.toUpperCase(string))) {
            return AndroidDigestFactory.getSHA1();
        }
        if (md5.contains(string)) {
            return AndroidDigestFactory.getMD5();
        }
        if (sha224.contains(string)) {
            return AndroidDigestFactory.getSHA224();
        }
        if (sha256.contains(string)) {
            return AndroidDigestFactory.getSHA256();
        }
        if (sha384.contains(string)) {
            return AndroidDigestFactory.getSHA384();
        }
        if (sha512.contains(string)) {
            return AndroidDigestFactory.getSHA512();
        }
        return null;
    }

    public static ASN1ObjectIdentifier getOID(String string) {
        return (ASN1ObjectIdentifier)oids.get(string);
    }

    public static boolean isSameDigest(String string, String string2) {
        boolean bl = sha1.contains(string) && sha1.contains(string2) || sha224.contains(string) && sha224.contains(string2) || sha256.contains(string) && sha256.contains(string2) || sha384.contains(string) && sha384.contains(string2) || sha512.contains(string) && sha512.contains(string2) || md5.contains(string) && md5.contains(string2);
        return bl;
    }
}

