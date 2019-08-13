/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.org.conscrypt.TrustedCertificateStore
 */
package android.security.net.config;

import android.security.net.config.NetworkSecurityConfig;
import android.security.net.config.TrustAnchor;
import com.android.org.conscrypt.TrustedCertificateStore;
import java.io.File;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

public class TrustedCertificateStoreAdapter
extends TrustedCertificateStore {
    private final NetworkSecurityConfig mConfig;

    public TrustedCertificateStoreAdapter(NetworkSecurityConfig networkSecurityConfig) {
        this.mConfig = networkSecurityConfig;
    }

    public Set<String> aliases() {
        throw new UnsupportedOperationException();
    }

    public Set<String> allSystemAliases() {
        throw new UnsupportedOperationException();
    }

    public boolean containsAlias(String string2) {
        throw new UnsupportedOperationException();
    }

    public Set<X509Certificate> findAllIssuers(X509Certificate x509Certificate) {
        return this.mConfig.findAllCertificatesByIssuerAndSignature(x509Certificate);
    }

    public X509Certificate findIssuer(X509Certificate object) {
        if ((object = this.mConfig.findTrustAnchorByIssuerAndSignature((X509Certificate)object)) == null) {
            return null;
        }
        return ((TrustAnchor)object).certificate;
    }

    public Certificate getCertificate(String string2) {
        throw new UnsupportedOperationException();
    }

    public Certificate getCertificate(String string2, boolean bl) {
        throw new UnsupportedOperationException();
    }

    public String getCertificateAlias(Certificate certificate) {
        throw new UnsupportedOperationException();
    }

    public String getCertificateAlias(Certificate certificate, boolean bl) {
        throw new UnsupportedOperationException();
    }

    public File getCertificateFile(File file, X509Certificate x509Certificate) {
        throw new UnsupportedOperationException();
    }

    public Date getCreationDate(String string2) {
        throw new UnsupportedOperationException();
    }

    public X509Certificate getTrustAnchor(X509Certificate object) {
        if ((object = this.mConfig.findTrustAnchorBySubjectAndPublicKey((X509Certificate)object)) == null) {
            return null;
        }
        return ((TrustAnchor)object).certificate;
    }

    public boolean isUserAddedCertificate(X509Certificate object) {
        if ((object = this.mConfig.findTrustAnchorBySubjectAndPublicKey((X509Certificate)object)) == null) {
            return false;
        }
        return ((TrustAnchor)object).overridesPins;
    }

    public Set<String> userAliases() {
        throw new UnsupportedOperationException();
    }
}

