/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import android.security.net.config.CertificateSource;
import android.security.net.config.TrustAnchor;
import android.util.ArraySet;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Set;

public final class CertificatesEntryRef {
    private final boolean mOverridesPins;
    private final CertificateSource mSource;

    public CertificatesEntryRef(CertificateSource certificateSource, boolean bl) {
        this.mSource = certificateSource;
        this.mOverridesPins = bl;
    }

    public Set<X509Certificate> findAllCertificatesByIssuerAndSignature(X509Certificate x509Certificate) {
        return this.mSource.findAllByIssuerAndSignature(x509Certificate);
    }

    public TrustAnchor findByIssuerAndSignature(X509Certificate x509Certificate) {
        if ((x509Certificate = this.mSource.findByIssuerAndSignature(x509Certificate)) == null) {
            return null;
        }
        return new TrustAnchor(x509Certificate, this.mOverridesPins);
    }

    public TrustAnchor findBySubjectAndPublicKey(X509Certificate x509Certificate) {
        if ((x509Certificate = this.mSource.findBySubjectAndPublicKey(x509Certificate)) == null) {
            return null;
        }
        return new TrustAnchor(x509Certificate, this.mOverridesPins);
    }

    public Set<TrustAnchor> getTrustAnchors() {
        ArraySet<TrustAnchor> arraySet = new ArraySet<TrustAnchor>();
        Iterator<X509Certificate> iterator = this.mSource.getCertificates().iterator();
        while (iterator.hasNext()) {
            arraySet.add(new TrustAnchor(iterator.next(), this.mOverridesPins));
        }
        return arraySet;
    }

    public void handleTrustStorageUpdate() {
        this.mSource.handleTrustStorageUpdate();
    }

    boolean overridesPins() {
        return this.mOverridesPins;
    }
}

