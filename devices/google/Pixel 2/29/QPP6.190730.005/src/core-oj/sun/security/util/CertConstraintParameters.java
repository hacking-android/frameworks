/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.security.cert.X509Certificate;

public class CertConstraintParameters {
    private final X509Certificate cert;
    private final boolean trustedMatch;

    public CertConstraintParameters(X509Certificate x509Certificate) {
        this(x509Certificate, false);
    }

    public CertConstraintParameters(X509Certificate x509Certificate, boolean bl) {
        this.cert = x509Certificate;
        this.trustedMatch = bl;
    }

    public X509Certificate getCertificate() {
        return this.cert;
    }

    public boolean isTrustedMatch() {
        return this.trustedMatch;
    }
}

