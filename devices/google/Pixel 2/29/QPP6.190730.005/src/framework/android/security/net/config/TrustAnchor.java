/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import java.security.cert.X509Certificate;

public final class TrustAnchor {
    public final X509Certificate certificate;
    public final boolean overridesPins;

    public TrustAnchor(X509Certificate x509Certificate, boolean bl) {
        if (x509Certificate != null) {
            this.certificate = x509Certificate;
            this.overridesPins = bl;
            return;
        }
        throw new NullPointerException("certificate");
    }
}

