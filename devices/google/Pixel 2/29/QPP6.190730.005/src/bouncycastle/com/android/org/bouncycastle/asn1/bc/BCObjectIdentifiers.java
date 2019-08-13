/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.bc;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface BCObjectIdentifiers {
    public static final ASN1ObjectIdentifier bc = new ASN1ObjectIdentifier("1.3.6.1.4.1.22554");
    public static final ASN1ObjectIdentifier bc_pbe = bc.branch("1");
    public static final ASN1ObjectIdentifier bc_pbe_sha1 = bc_pbe.branch("1");
    public static final ASN1ObjectIdentifier bc_pbe_sha1_pkcs12;
    public static final ASN1ObjectIdentifier bc_pbe_sha1_pkcs12_aes128_cbc;
    public static final ASN1ObjectIdentifier bc_pbe_sha1_pkcs12_aes192_cbc;
    public static final ASN1ObjectIdentifier bc_pbe_sha1_pkcs12_aes256_cbc;
    public static final ASN1ObjectIdentifier bc_pbe_sha1_pkcs5;
    public static final ASN1ObjectIdentifier bc_pbe_sha224;
    public static final ASN1ObjectIdentifier bc_pbe_sha256;
    public static final ASN1ObjectIdentifier bc_pbe_sha256_pkcs12;
    public static final ASN1ObjectIdentifier bc_pbe_sha256_pkcs12_aes128_cbc;
    public static final ASN1ObjectIdentifier bc_pbe_sha256_pkcs12_aes192_cbc;
    public static final ASN1ObjectIdentifier bc_pbe_sha256_pkcs12_aes256_cbc;
    public static final ASN1ObjectIdentifier bc_pbe_sha256_pkcs5;
    public static final ASN1ObjectIdentifier bc_pbe_sha384;
    public static final ASN1ObjectIdentifier bc_pbe_sha512;
    public static final ASN1ObjectIdentifier bc_sig;
    public static final ASN1ObjectIdentifier qTESLA;
    public static final ASN1ObjectIdentifier qTESLA_I;
    public static final ASN1ObjectIdentifier qTESLA_III_size;
    public static final ASN1ObjectIdentifier qTESLA_III_speed;
    public static final ASN1ObjectIdentifier qTESLA_p_I;
    public static final ASN1ObjectIdentifier qTESLA_p_III;
    public static final ASN1ObjectIdentifier xmss;
    public static final ASN1ObjectIdentifier xmss_SHA256;
    public static final ASN1ObjectIdentifier xmss_SHA256ph;
    public static final ASN1ObjectIdentifier xmss_SHA512;
    public static final ASN1ObjectIdentifier xmss_SHA512ph;
    public static final ASN1ObjectIdentifier xmss_SHAKE128;
    public static final ASN1ObjectIdentifier xmss_SHAKE128ph;
    public static final ASN1ObjectIdentifier xmss_SHAKE256;
    public static final ASN1ObjectIdentifier xmss_SHAKE256ph;
    public static final ASN1ObjectIdentifier xmss_mt;
    public static final ASN1ObjectIdentifier xmss_mt_SHA256;
    public static final ASN1ObjectIdentifier xmss_mt_SHA256ph;
    public static final ASN1ObjectIdentifier xmss_mt_SHA512;
    public static final ASN1ObjectIdentifier xmss_mt_SHA512ph;
    public static final ASN1ObjectIdentifier xmss_mt_SHAKE128;
    public static final ASN1ObjectIdentifier xmss_mt_SHAKE128ph;
    public static final ASN1ObjectIdentifier xmss_mt_SHAKE256;
    public static final ASN1ObjectIdentifier xmss_mt_SHAKE256ph;
    public static final ASN1ObjectIdentifier xmss_mt_with_SHA256;
    public static final ASN1ObjectIdentifier xmss_mt_with_SHA512;
    public static final ASN1ObjectIdentifier xmss_mt_with_SHAKE128;
    public static final ASN1ObjectIdentifier xmss_mt_with_SHAKE256;
    public static final ASN1ObjectIdentifier xmss_with_SHA256;
    public static final ASN1ObjectIdentifier xmss_with_SHA512;
    public static final ASN1ObjectIdentifier xmss_with_SHAKE128;
    public static final ASN1ObjectIdentifier xmss_with_SHAKE256;

    static {
        bc_pbe_sha256 = bc_pbe.branch("2.1");
        bc_pbe_sha384 = bc_pbe.branch("2.2");
        bc_pbe_sha512 = bc_pbe.branch("2.3");
        bc_pbe_sha224 = bc_pbe.branch("2.4");
        bc_pbe_sha1_pkcs5 = bc_pbe_sha1.branch("1");
        bc_pbe_sha1_pkcs12 = bc_pbe_sha1.branch("2");
        bc_pbe_sha256_pkcs5 = bc_pbe_sha256.branch("1");
        bc_pbe_sha256_pkcs12 = bc_pbe_sha256.branch("2");
        bc_pbe_sha1_pkcs12_aes128_cbc = bc_pbe_sha1_pkcs12.branch("1.2");
        bc_pbe_sha1_pkcs12_aes192_cbc = bc_pbe_sha1_pkcs12.branch("1.22");
        bc_pbe_sha1_pkcs12_aes256_cbc = bc_pbe_sha1_pkcs12.branch("1.42");
        bc_pbe_sha256_pkcs12_aes128_cbc = bc_pbe_sha256_pkcs12.branch("1.2");
        bc_pbe_sha256_pkcs12_aes192_cbc = bc_pbe_sha256_pkcs12.branch("1.22");
        bc_pbe_sha256_pkcs12_aes256_cbc = bc_pbe_sha256_pkcs12.branch("1.42");
        bc_sig = bc.branch("2");
        xmss = bc_sig.branch("2");
        xmss_SHA256ph = xmss.branch("1");
        xmss_SHA512ph = xmss.branch("2");
        xmss_SHAKE128ph = xmss.branch("3");
        xmss_SHAKE256ph = xmss.branch("4");
        xmss_SHA256 = xmss.branch("5");
        xmss_SHA512 = xmss.branch("6");
        xmss_SHAKE128 = xmss.branch("7");
        xmss_SHAKE256 = xmss.branch("8");
        xmss_mt = bc_sig.branch("3");
        xmss_mt_SHA256ph = xmss_mt.branch("1");
        xmss_mt_SHA512ph = xmss_mt.branch("2");
        xmss_mt_SHAKE128ph = xmss_mt.branch("3");
        xmss_mt_SHAKE256ph = xmss_mt.branch("4");
        xmss_mt_SHA256 = xmss_mt.branch("5");
        xmss_mt_SHA512 = xmss_mt.branch("6");
        xmss_mt_SHAKE128 = xmss_mt.branch("7");
        xmss_mt_SHAKE256 = xmss_mt.branch("8");
        xmss_with_SHA256 = xmss_SHA256ph;
        xmss_with_SHA512 = xmss_SHA512ph;
        xmss_with_SHAKE128 = xmss_SHAKE128ph;
        xmss_with_SHAKE256 = xmss_SHAKE256ph;
        xmss_mt_with_SHA256 = xmss_mt_SHA256ph;
        xmss_mt_with_SHA512 = xmss_mt_SHA512ph;
        xmss_mt_with_SHAKE128 = xmss_mt_SHAKE128;
        xmss_mt_with_SHAKE256 = xmss_mt_SHAKE256;
        qTESLA = bc_sig.branch("4");
        qTESLA_I = qTESLA.branch("1");
        qTESLA_III_size = qTESLA.branch("2");
        qTESLA_III_speed = qTESLA.branch("3");
        qTESLA_p_I = qTESLA.branch("4");
        qTESLA_p_III = qTESLA.branch("5");
    }
}

