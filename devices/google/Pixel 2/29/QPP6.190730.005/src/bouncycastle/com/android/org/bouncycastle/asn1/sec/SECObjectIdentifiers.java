/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.sec;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;

public interface SECObjectIdentifiers {
    public static final ASN1ObjectIdentifier dhSinglePass_cofactorDH_sha224kdf_scheme;
    public static final ASN1ObjectIdentifier dhSinglePass_cofactorDH_sha256kdf_scheme;
    public static final ASN1ObjectIdentifier dhSinglePass_cofactorDH_sha384kdf_scheme;
    public static final ASN1ObjectIdentifier dhSinglePass_cofactorDH_sha512kdf_scheme;
    public static final ASN1ObjectIdentifier dhSinglePass_stdDH_sha224kdf_scheme;
    public static final ASN1ObjectIdentifier dhSinglePass_stdDH_sha256kdf_scheme;
    public static final ASN1ObjectIdentifier dhSinglePass_stdDH_sha384kdf_scheme;
    public static final ASN1ObjectIdentifier dhSinglePass_stdDH_sha512kdf_scheme;
    public static final ASN1ObjectIdentifier ellipticCurve;
    public static final ASN1ObjectIdentifier mqvFull_sha224kdf_scheme;
    public static final ASN1ObjectIdentifier mqvFull_sha256kdf_scheme;
    public static final ASN1ObjectIdentifier mqvFull_sha384kdf_scheme;
    public static final ASN1ObjectIdentifier mqvFull_sha512kdf_scheme;
    public static final ASN1ObjectIdentifier mqvSinglePass_sha224kdf_scheme;
    public static final ASN1ObjectIdentifier mqvSinglePass_sha256kdf_scheme;
    public static final ASN1ObjectIdentifier mqvSinglePass_sha384kdf_scheme;
    public static final ASN1ObjectIdentifier mqvSinglePass_sha512kdf_scheme;
    public static final ASN1ObjectIdentifier secg_scheme;
    public static final ASN1ObjectIdentifier secp112r1;
    public static final ASN1ObjectIdentifier secp112r2;
    public static final ASN1ObjectIdentifier secp128r1;
    public static final ASN1ObjectIdentifier secp128r2;
    public static final ASN1ObjectIdentifier secp160k1;
    public static final ASN1ObjectIdentifier secp160r1;
    public static final ASN1ObjectIdentifier secp160r2;
    public static final ASN1ObjectIdentifier secp192k1;
    public static final ASN1ObjectIdentifier secp192r1;
    public static final ASN1ObjectIdentifier secp224k1;
    public static final ASN1ObjectIdentifier secp224r1;
    public static final ASN1ObjectIdentifier secp256k1;
    public static final ASN1ObjectIdentifier secp256r1;
    public static final ASN1ObjectIdentifier secp384r1;
    public static final ASN1ObjectIdentifier secp521r1;
    public static final ASN1ObjectIdentifier sect113r1;
    public static final ASN1ObjectIdentifier sect113r2;
    public static final ASN1ObjectIdentifier sect131r1;
    public static final ASN1ObjectIdentifier sect131r2;
    public static final ASN1ObjectIdentifier sect163k1;
    public static final ASN1ObjectIdentifier sect163r1;
    public static final ASN1ObjectIdentifier sect163r2;
    public static final ASN1ObjectIdentifier sect193r1;
    public static final ASN1ObjectIdentifier sect193r2;
    public static final ASN1ObjectIdentifier sect233k1;
    public static final ASN1ObjectIdentifier sect233r1;
    public static final ASN1ObjectIdentifier sect239k1;
    public static final ASN1ObjectIdentifier sect283k1;
    public static final ASN1ObjectIdentifier sect283r1;
    public static final ASN1ObjectIdentifier sect409k1;
    public static final ASN1ObjectIdentifier sect409r1;
    public static final ASN1ObjectIdentifier sect571k1;
    public static final ASN1ObjectIdentifier sect571r1;

    static {
        ellipticCurve = new ASN1ObjectIdentifier("1.3.132.0");
        sect163k1 = ellipticCurve.branch("1");
        sect163r1 = ellipticCurve.branch("2");
        sect239k1 = ellipticCurve.branch("3");
        sect113r1 = ellipticCurve.branch("4");
        sect113r2 = ellipticCurve.branch("5");
        secp112r1 = ellipticCurve.branch("6");
        secp112r2 = ellipticCurve.branch("7");
        secp160r1 = ellipticCurve.branch("8");
        secp160k1 = ellipticCurve.branch("9");
        secp256k1 = ellipticCurve.branch("10");
        sect163r2 = ellipticCurve.branch("15");
        sect283k1 = ellipticCurve.branch("16");
        sect283r1 = ellipticCurve.branch("17");
        sect131r1 = ellipticCurve.branch("22");
        sect131r2 = ellipticCurve.branch("23");
        sect193r1 = ellipticCurve.branch("24");
        sect193r2 = ellipticCurve.branch("25");
        sect233k1 = ellipticCurve.branch("26");
        sect233r1 = ellipticCurve.branch("27");
        secp128r1 = ellipticCurve.branch("28");
        secp128r2 = ellipticCurve.branch("29");
        secp160r2 = ellipticCurve.branch("30");
        secp192k1 = ellipticCurve.branch("31");
        secp224k1 = ellipticCurve.branch("32");
        secp224r1 = ellipticCurve.branch("33");
        secp384r1 = ellipticCurve.branch("34");
        secp521r1 = ellipticCurve.branch("35");
        sect409k1 = ellipticCurve.branch("36");
        sect409r1 = ellipticCurve.branch("37");
        sect571k1 = ellipticCurve.branch("38");
        sect571r1 = ellipticCurve.branch("39");
        secp192r1 = X9ObjectIdentifiers.prime192v1;
        secp256r1 = X9ObjectIdentifiers.prime256v1;
        secg_scheme = new ASN1ObjectIdentifier("1.3.132.1");
        dhSinglePass_stdDH_sha224kdf_scheme = secg_scheme.branch("11.0");
        dhSinglePass_stdDH_sha256kdf_scheme = secg_scheme.branch("11.1");
        dhSinglePass_stdDH_sha384kdf_scheme = secg_scheme.branch("11.2");
        dhSinglePass_stdDH_sha512kdf_scheme = secg_scheme.branch("11.3");
        dhSinglePass_cofactorDH_sha224kdf_scheme = secg_scheme.branch("14.0");
        dhSinglePass_cofactorDH_sha256kdf_scheme = secg_scheme.branch("14.1");
        dhSinglePass_cofactorDH_sha384kdf_scheme = secg_scheme.branch("14.2");
        dhSinglePass_cofactorDH_sha512kdf_scheme = secg_scheme.branch("14.3");
        mqvSinglePass_sha224kdf_scheme = secg_scheme.branch("15.0");
        mqvSinglePass_sha256kdf_scheme = secg_scheme.branch("15.1");
        mqvSinglePass_sha384kdf_scheme = secg_scheme.branch("15.2");
        mqvSinglePass_sha512kdf_scheme = secg_scheme.branch("15.3");
        mqvFull_sha224kdf_scheme = secg_scheme.branch("16.0");
        mqvFull_sha256kdf_scheme = secg_scheme.branch("16.1");
        mqvFull_sha384kdf_scheme = secg_scheme.branch("16.2");
        mqvFull_sha512kdf_scheme = secg_scheme.branch("16.3");
    }
}

