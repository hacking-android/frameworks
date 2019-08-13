/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce;

import com.android.org.bouncycastle.util.Selector;
import java.io.IOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class PKIXCertStoreSelector<T extends Certificate>
implements Selector<T> {
    private final CertSelector baseSelector;

    private PKIXCertStoreSelector(CertSelector certSelector) {
        this.baseSelector = certSelector;
    }

    public static Collection<? extends Certificate> getCertificates(PKIXCertStoreSelector pKIXCertStoreSelector, CertStore certStore) throws CertStoreException {
        return certStore.getCertificates(new SelectorClone(pKIXCertStoreSelector));
    }

    @Override
    public Object clone() {
        return new PKIXCertStoreSelector<T>(this.baseSelector);
    }

    @Override
    public boolean match(Certificate certificate) {
        return this.baseSelector.match(certificate);
    }

    public static class Builder {
        private final CertSelector baseSelector;

        public Builder(CertSelector certSelector) {
            this.baseSelector = (CertSelector)certSelector.clone();
        }

        public PKIXCertStoreSelector<? extends Certificate> build() {
            return new PKIXCertStoreSelector(this.baseSelector);
        }
    }

    private static class SelectorClone
    extends X509CertSelector {
        private final PKIXCertStoreSelector selector;

        SelectorClone(PKIXCertStoreSelector cloneable) {
            this.selector = cloneable;
            if (((PKIXCertStoreSelector)cloneable).baseSelector instanceof X509CertSelector) {
                cloneable = (X509CertSelector)((PKIXCertStoreSelector)cloneable).baseSelector;
                this.setAuthorityKeyIdentifier(((X509CertSelector)cloneable).getAuthorityKeyIdentifier());
                this.setBasicConstraints(((X509CertSelector)cloneable).getBasicConstraints());
                this.setCertificate(((X509CertSelector)cloneable).getCertificate());
                this.setCertificateValid(((X509CertSelector)cloneable).getCertificateValid());
                this.setKeyUsage(((X509CertSelector)cloneable).getKeyUsage());
                this.setMatchAllSubjectAltNames(((X509CertSelector)cloneable).getMatchAllSubjectAltNames());
                this.setPrivateKeyValid(((X509CertSelector)cloneable).getPrivateKeyValid());
                this.setSerialNumber(((X509CertSelector)cloneable).getSerialNumber());
                this.setSubjectKeyIdentifier(((X509CertSelector)cloneable).getSubjectKeyIdentifier());
                this.setSubjectPublicKey(((X509CertSelector)cloneable).getSubjectPublicKey());
                try {
                    this.setExtendedKeyUsage(((X509CertSelector)cloneable).getExtendedKeyUsage());
                    this.setIssuer(((X509CertSelector)cloneable).getIssuerAsBytes());
                    this.setNameConstraints(((X509CertSelector)cloneable).getNameConstraints());
                    this.setPathToNames(((X509CertSelector)cloneable).getPathToNames());
                    this.setPolicy(((X509CertSelector)cloneable).getPolicy());
                    this.setSubject(((X509CertSelector)cloneable).getSubjectAsBytes());
                    this.setSubjectAlternativeNames(((X509CertSelector)cloneable).getSubjectAlternativeNames());
                    this.setSubjectPublicKeyAlgID(((X509CertSelector)cloneable).getSubjectPublicKeyAlgID());
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("base selector invalid: ");
                    stringBuilder.append(iOException.getMessage());
                    throw new IllegalStateException(stringBuilder.toString(), iOException);
                }
            }
        }

        @Override
        public boolean match(Certificate certificate) {
            PKIXCertStoreSelector pKIXCertStoreSelector = this.selector;
            boolean bl = pKIXCertStoreSelector == null ? certificate != null : pKIXCertStoreSelector.match(certificate);
            return bl;
        }
    }

}

