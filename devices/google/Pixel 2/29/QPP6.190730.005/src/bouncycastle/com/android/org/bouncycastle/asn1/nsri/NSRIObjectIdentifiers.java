/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.nsri;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface NSRIObjectIdentifiers {
    public static final ASN1ObjectIdentifier id_algorithm;
    public static final ASN1ObjectIdentifier id_aria128_cbc;
    public static final ASN1ObjectIdentifier id_aria128_ccm;
    public static final ASN1ObjectIdentifier id_aria128_cfb;
    public static final ASN1ObjectIdentifier id_aria128_cmac;
    public static final ASN1ObjectIdentifier id_aria128_ctr;
    public static final ASN1ObjectIdentifier id_aria128_ecb;
    public static final ASN1ObjectIdentifier id_aria128_gcm;
    public static final ASN1ObjectIdentifier id_aria128_kw;
    public static final ASN1ObjectIdentifier id_aria128_kwp;
    public static final ASN1ObjectIdentifier id_aria128_ocb2;
    public static final ASN1ObjectIdentifier id_aria128_ofb;
    public static final ASN1ObjectIdentifier id_aria192_cbc;
    public static final ASN1ObjectIdentifier id_aria192_ccm;
    public static final ASN1ObjectIdentifier id_aria192_cfb;
    public static final ASN1ObjectIdentifier id_aria192_cmac;
    public static final ASN1ObjectIdentifier id_aria192_ctr;
    public static final ASN1ObjectIdentifier id_aria192_ecb;
    public static final ASN1ObjectIdentifier id_aria192_gcm;
    public static final ASN1ObjectIdentifier id_aria192_kw;
    public static final ASN1ObjectIdentifier id_aria192_kwp;
    public static final ASN1ObjectIdentifier id_aria192_ocb2;
    public static final ASN1ObjectIdentifier id_aria192_ofb;
    public static final ASN1ObjectIdentifier id_aria256_cbc;
    public static final ASN1ObjectIdentifier id_aria256_ccm;
    public static final ASN1ObjectIdentifier id_aria256_cfb;
    public static final ASN1ObjectIdentifier id_aria256_cmac;
    public static final ASN1ObjectIdentifier id_aria256_ctr;
    public static final ASN1ObjectIdentifier id_aria256_ecb;
    public static final ASN1ObjectIdentifier id_aria256_gcm;
    public static final ASN1ObjectIdentifier id_aria256_kw;
    public static final ASN1ObjectIdentifier id_aria256_kwp;
    public static final ASN1ObjectIdentifier id_aria256_ocb2;
    public static final ASN1ObjectIdentifier id_aria256_ofb;
    public static final ASN1ObjectIdentifier id_pad;
    public static final ASN1ObjectIdentifier id_pad_1;
    public static final ASN1ObjectIdentifier id_pad_null;
    public static final ASN1ObjectIdentifier id_sea;
    public static final ASN1ObjectIdentifier nsri;

    static {
        nsri = new ASN1ObjectIdentifier("1.2.410.200046");
        id_algorithm = nsri.branch("1");
        id_sea = id_algorithm.branch("1");
        id_pad = id_algorithm.branch("2");
        id_pad_null = id_algorithm.branch("0");
        id_pad_1 = id_algorithm.branch("1");
        id_aria128_ecb = id_sea.branch("1");
        id_aria128_cbc = id_sea.branch("2");
        id_aria128_cfb = id_sea.branch("3");
        id_aria128_ofb = id_sea.branch("4");
        id_aria128_ctr = id_sea.branch("5");
        id_aria192_ecb = id_sea.branch("6");
        id_aria192_cbc = id_sea.branch("7");
        id_aria192_cfb = id_sea.branch("8");
        id_aria192_ofb = id_sea.branch("9");
        id_aria192_ctr = id_sea.branch("10");
        id_aria256_ecb = id_sea.branch("11");
        id_aria256_cbc = id_sea.branch("12");
        id_aria256_cfb = id_sea.branch("13");
        id_aria256_ofb = id_sea.branch("14");
        id_aria256_ctr = id_sea.branch("15");
        id_aria128_cmac = id_sea.branch("21");
        id_aria192_cmac = id_sea.branch("22");
        id_aria256_cmac = id_sea.branch("23");
        id_aria128_ocb2 = id_sea.branch("31");
        id_aria192_ocb2 = id_sea.branch("32");
        id_aria256_ocb2 = id_sea.branch("33");
        id_aria128_gcm = id_sea.branch("34");
        id_aria192_gcm = id_sea.branch("35");
        id_aria256_gcm = id_sea.branch("36");
        id_aria128_ccm = id_sea.branch("37");
        id_aria192_ccm = id_sea.branch("38");
        id_aria256_ccm = id_sea.branch("39");
        id_aria128_kw = id_sea.branch("40");
        id_aria192_kw = id_sea.branch("41");
        id_aria256_kw = id_sea.branch("42");
        id_aria128_kwp = id_sea.branch("43");
        id_aria192_kwp = id_sea.branch("44");
        id_aria256_kwp = id_sea.branch("45");
    }
}

