/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECField;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.x500.X500Principal;

public final class ChainStrengthAnalyzer {
    private static final int MIN_DSA_P_LEN_BITS = 1024;
    private static final int MIN_DSA_Q_LEN_BITS = 160;
    private static final int MIN_EC_FIELD_SIZE_BITS = 160;
    private static final int MIN_RSA_MODULUS_LEN_BITS = 1024;
    private static final String[] SIGNATURE_ALGORITHM_OID_BLACKLIST = new String[]{"1.2.840.113549.1.1.2", "1.2.840.113549.1.1.3", "1.2.840.113549.1.1.4", "1.2.840.113549.1.1.5", "1.2.840.10040.4.3", "1.2.840.10045.4.1"};

    public static final void check(List<X509Certificate> object) throws CertificateException {
        Object object2 = object.iterator();
        while (object2.hasNext()) {
            object = object2.next();
            try {
                ChainStrengthAnalyzer.checkCert((X509Certificate)object);
            }
            catch (CertificateException certificateException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unacceptable certificate: ");
                ((StringBuilder)object2).append(((X509Certificate)object).getSubjectX500Principal());
                throw new CertificateException(((StringBuilder)object2).toString(), certificateException);
            }
        }
    }

    public static final void check(X509Certificate[] object) throws CertificateException {
        int n = ((X509Certificate[])object).length;
        for (int i = 0; i < n; ++i) {
            X509Certificate x509Certificate = object[i];
            try {
                ChainStrengthAnalyzer.checkCert(x509Certificate);
                continue;
            }
            catch (CertificateException certificateException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unacceptable certificate: ");
                ((StringBuilder)object).append(x509Certificate.getSubjectX500Principal());
                throw new CertificateException(((StringBuilder)object).toString(), certificateException);
            }
        }
    }

    public static final void checkCert(X509Certificate x509Certificate) throws CertificateException {
        ChainStrengthAnalyzer.checkKeyLength(x509Certificate);
        ChainStrengthAnalyzer.checkSignatureAlgorithm(x509Certificate);
    }

    private static void checkKeyLength(X509Certificate serializable) throws CertificateException {
        block9 : {
            block10 : {
                block7 : {
                    block8 : {
                        block6 : {
                            if (!((serializable = ((Certificate)serializable).getPublicKey()) instanceof RSAPublicKey)) break block6;
                            if (((RSAPublicKey)serializable).getModulus().bitLength() < 1024) {
                                throw new CertificateException("RSA modulus is < 1024 bits");
                            }
                            break block7;
                        }
                        if (!(serializable instanceof ECPublicKey)) break block8;
                        if (((ECPublicKey)serializable).getParams().getCurve().getField().getFieldSize() < 160) {
                            throw new CertificateException("EC key field size is < 160 bits");
                        }
                        break block7;
                    }
                    if (!(serializable instanceof DSAPublicKey)) break block9;
                    int n = ((DSAPublicKey)serializable).getParams().getP().bitLength();
                    int n2 = ((DSAPublicKey)serializable).getParams().getQ().bitLength();
                    if (n < 1024 || n2 < 160) break block10;
                }
                return;
            }
            throw new CertificateException("DSA key length is < (1024, 160) bits");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Rejecting unknown key class ");
        stringBuilder.append(serializable.getClass().getName());
        throw new CertificateException(stringBuilder.toString());
    }

    private static void checkSignatureAlgorithm(X509Certificate object) throws CertificateException {
        object = ((X509Certificate)object).getSigAlgOID();
        Object object2 = SIGNATURE_ALGORITHM_OID_BLACKLIST;
        int n = ((String[])object2).length;
        for (int i = 0; i < n; ++i) {
            if (!((String)object).equals(object2[i])) {
                continue;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Signature uses an insecure hash function: ");
            ((StringBuilder)object2).append((String)object);
            throw new CertificateException(((StringBuilder)object2).toString());
        }
    }
}

