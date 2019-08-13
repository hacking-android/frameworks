/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECField;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class CertificatePriorityComparator
implements Comparator<X509Certificate> {
    private static final Map<String, Integer> ALGORITHM_OID_PRIORITY_MAP;
    private static final Integer PRIORITY_MD5;
    private static final Integer PRIORITY_SHA1;
    private static final Integer PRIORITY_SHA224;
    private static final Integer PRIORITY_SHA256;
    private static final Integer PRIORITY_SHA384;
    private static final Integer PRIORITY_SHA512;
    private static final Integer PRIORITY_UNKNOWN;

    static {
        PRIORITY_MD5 = 6;
        PRIORITY_SHA1 = 5;
        PRIORITY_SHA224 = 4;
        PRIORITY_SHA256 = 3;
        PRIORITY_SHA384 = 2;
        PRIORITY_SHA512 = 1;
        PRIORITY_UNKNOWN = -1;
        ALGORITHM_OID_PRIORITY_MAP = new HashMap<String, Integer>();
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.113549.1.1.13", PRIORITY_SHA512);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.113549.1.1.12", PRIORITY_SHA384);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.113549.1.1.11", PRIORITY_SHA256);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.113549.1.1.14", PRIORITY_SHA224);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.113549.1.1.5", PRIORITY_SHA1);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.113549.1.1.4", PRIORITY_MD5);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.10045.4.3.4", PRIORITY_SHA512);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.10045.4.3.3", PRIORITY_SHA384);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.10045.4.3.2", PRIORITY_SHA256);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.10045.4.3.1", PRIORITY_SHA224);
        ALGORITHM_OID_PRIORITY_MAP.put("1.2.840.10045.4.1", PRIORITY_SHA1);
    }

    private int compareKeyAlgorithm(PublicKey object, PublicKey publicKey) {
        if (((String)(object = object.getAlgorithm())).equalsIgnoreCase(publicKey.getAlgorithm())) {
            return 0;
        }
        if ("EC".equalsIgnoreCase((String)object)) {
            return 1;
        }
        return -1;
    }

    private int compareKeySize(PublicKey publicKey, PublicKey publicKey2) {
        if (publicKey.getAlgorithm().equalsIgnoreCase(publicKey2.getAlgorithm())) {
            return this.getKeySize(publicKey) - this.getKeySize(publicKey2);
        }
        throw new IllegalArgumentException("Keys are not of the same type");
    }

    private int compareSignatureAlgorithm(X509Certificate serializable, X509Certificate serializable2) {
        Integer n = ALGORITHM_OID_PRIORITY_MAP.get(((X509Certificate)serializable).getSigAlgOID());
        Integer n2 = ALGORITHM_OID_PRIORITY_MAP.get(serializable2.getSigAlgOID());
        serializable = n;
        if (n == null) {
            serializable = PRIORITY_UNKNOWN;
        }
        serializable2 = n2;
        if (n2 == null) {
            serializable2 = PRIORITY_UNKNOWN;
        }
        return (Integer)serializable2 - (Integer)serializable;
    }

    private int compareStrength(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        PublicKey publicKey;
        PublicKey publicKey2 = x509Certificate.getPublicKey();
        int n = this.compareKeyAlgorithm(publicKey2, publicKey = x509Certificate2.getPublicKey());
        if (n != 0) {
            return n;
        }
        n = this.compareKeySize(publicKey2, publicKey);
        if (n != 0) {
            return n;
        }
        return this.compareSignatureAlgorithm(x509Certificate, x509Certificate2);
    }

    private int getKeySize(PublicKey publicKey) {
        if (publicKey instanceof ECPublicKey) {
            return ((ECPublicKey)publicKey).getParams().getCurve().getField().getFieldSize();
        }
        if (publicKey instanceof RSAPublicKey) {
            return ((RSAPublicKey)publicKey).getModulus().bitLength();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported public key type: ");
        stringBuilder.append(publicKey.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int compare(X509Certificate serializable, X509Certificate x509Certificate) {
        boolean bl;
        boolean bl2 = serializable.getSubjectDN().equals(serializable.getIssuerDN());
        if (bl2 != (bl = x509Certificate.getSubjectDN().equals(x509Certificate.getIssuerDN()))) {
            int n = bl ? 1 : -1;
            return n;
        }
        int n = this.compareStrength(x509Certificate, (X509Certificate)serializable);
        if (n != 0) {
            return n;
        }
        Date date = serializable.getNotAfter();
        n = x509Certificate.getNotAfter().compareTo(date);
        if (n != 0) {
            return n;
        }
        serializable = serializable.getNotBefore();
        return x509Certificate.getNotBefore().compareTo((Date)serializable);
    }
}

