/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.util.Selector;
import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

public class X509CertStoreSelector
extends X509CertSelector
implements Selector {
    public static X509CertStoreSelector getInstance(X509CertSelector x509CertSelector) {
        if (x509CertSelector != null) {
            Object object = new X509CertStoreSelector();
            ((X509CertSelector)object).setAuthorityKeyIdentifier(x509CertSelector.getAuthorityKeyIdentifier());
            ((X509CertSelector)object).setBasicConstraints(x509CertSelector.getBasicConstraints());
            ((X509CertSelector)object).setCertificate(x509CertSelector.getCertificate());
            ((X509CertSelector)object).setCertificateValid(x509CertSelector.getCertificateValid());
            ((X509CertSelector)object).setMatchAllSubjectAltNames(x509CertSelector.getMatchAllSubjectAltNames());
            try {
                ((X509CertSelector)object).setPathToNames(x509CertSelector.getPathToNames());
                ((X509CertSelector)object).setExtendedKeyUsage(x509CertSelector.getExtendedKeyUsage());
                ((X509CertSelector)object).setNameConstraints(x509CertSelector.getNameConstraints());
                ((X509CertSelector)object).setPolicy(x509CertSelector.getPolicy());
                ((X509CertSelector)object).setSubjectPublicKeyAlgID(x509CertSelector.getSubjectPublicKeyAlgID());
                ((X509CertSelector)object).setSubjectAlternativeNames(x509CertSelector.getSubjectAlternativeNames());
            }
            catch (IOException iOException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("error in passed in selector: ");
                ((StringBuilder)object).append(iOException);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            ((X509CertSelector)object).setIssuer(x509CertSelector.getIssuer());
            ((X509CertSelector)object).setKeyUsage(x509CertSelector.getKeyUsage());
            ((X509CertSelector)object).setPrivateKeyValid(x509CertSelector.getPrivateKeyValid());
            ((X509CertSelector)object).setSerialNumber(x509CertSelector.getSerialNumber());
            ((X509CertSelector)object).setSubject(x509CertSelector.getSubject());
            ((X509CertSelector)object).setSubjectKeyIdentifier(x509CertSelector.getSubjectKeyIdentifier());
            ((X509CertSelector)object).setSubjectPublicKey(x509CertSelector.getSubjectPublicKey());
            return object;
        }
        throw new IllegalArgumentException("cannot create from null selector");
    }

    @Override
    public Object clone() {
        return (X509CertStoreSelector)super.clone();
    }

    public boolean match(Object object) {
        if (!(object instanceof X509Certificate)) {
            return false;
        }
        return super.match((X509Certificate)object);
    }

    @Override
    public boolean match(Certificate certificate) {
        return this.match((Object)certificate);
    }
}

