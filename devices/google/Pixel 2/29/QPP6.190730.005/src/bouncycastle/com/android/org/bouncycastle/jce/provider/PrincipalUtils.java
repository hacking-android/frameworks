/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.x509.AttributeCertificateIssuer;
import com.android.org.bouncycastle.x509.X509AttributeCertificate;
import java.security.Principal;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;

class PrincipalUtils {
    PrincipalUtils() {
    }

    static X500Name getCA(TrustAnchor trustAnchor) {
        return X500Name.getInstance(trustAnchor.getCA().getEncoded());
    }

    static X500Name getEncodedIssuerPrincipal(Object object) {
        if (object instanceof X509Certificate) {
            return PrincipalUtils.getIssuerPrincipal((X509Certificate)object);
        }
        return X500Name.getInstance(((X500Principal)((X509AttributeCertificate)object).getIssuer().getPrincipals()[0]).getEncoded());
    }

    static X500Name getIssuerPrincipal(X509CRL x509CRL) {
        return X500Name.getInstance(x509CRL.getIssuerX500Principal().getEncoded());
    }

    static X500Name getIssuerPrincipal(X509Certificate x509Certificate) {
        return X500Name.getInstance(x509Certificate.getIssuerX500Principal().getEncoded());
    }

    static X500Name getSubjectPrincipal(X509Certificate x509Certificate) {
        return X500Name.getInstance(x509Certificate.getSubjectX500Principal().getEncoded());
    }
}

