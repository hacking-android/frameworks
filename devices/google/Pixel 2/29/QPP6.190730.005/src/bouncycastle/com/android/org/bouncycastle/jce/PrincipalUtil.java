/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce;

import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.TBSCertList;
import com.android.org.bouncycastle.asn1.x509.TBSCertificateStructure;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import com.android.org.bouncycastle.jce.X509Principal;
import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

public class PrincipalUtil {
    public static X509Principal getIssuerX509Principal(X509CRL object) throws CRLException {
        try {
            object = new X509Principal(X509Name.getInstance(TBSCertList.getInstance(ASN1Primitive.fromByteArray(((X509CRL)object).getTBSCertList())).getIssuer()));
            return object;
        }
        catch (IOException iOException) {
            throw new CRLException(iOException.toString());
        }
    }

    public static X509Principal getIssuerX509Principal(X509Certificate object) throws CertificateEncodingException {
        try {
            object = new X509Principal(X509Name.getInstance(TBSCertificateStructure.getInstance(ASN1Primitive.fromByteArray(((X509Certificate)object).getTBSCertificate())).getIssuer()));
            return object;
        }
        catch (IOException iOException) {
            throw new CertificateEncodingException(iOException.toString());
        }
    }

    public static X509Principal getSubjectX509Principal(X509Certificate object) throws CertificateEncodingException {
        try {
            object = new X509Principal(X509Name.getInstance(TBSCertificateStructure.getInstance(ASN1Primitive.fromByteArray(((X509Certificate)object).getTBSCertificate())).getSubject()));
            return object;
        }
        catch (IOException iOException) {
            throw new CertificateEncodingException(iOException.toString());
        }
    }
}

