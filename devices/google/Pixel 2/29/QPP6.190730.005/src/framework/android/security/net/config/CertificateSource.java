/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import java.security.cert.X509Certificate;
import java.util.Set;

public interface CertificateSource {
    public Set<X509Certificate> findAllByIssuerAndSignature(X509Certificate var1);

    public X509Certificate findByIssuerAndSignature(X509Certificate var1);

    public X509Certificate findBySubjectAndPublicKey(X509Certificate var1);

    public Set<X509Certificate> getCertificates();

    public void handleTrustStorageUpdate();
}

