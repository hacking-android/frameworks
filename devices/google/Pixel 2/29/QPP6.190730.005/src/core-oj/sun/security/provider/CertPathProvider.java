/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider;

import java.security.Provider;

public final class CertPathProvider
extends Provider {
    public CertPathProvider() {
        super("CertPathProvider", 1.0, "Provider of CertPathBuilder and CertPathVerifier");
        this.put("CertPathBuilder.PKIX", "sun.security.provider.certpath.SunCertPathBuilder");
        this.put("CertPathBuilder.PKIX ImplementedIn", "Software");
        this.put("CertPathBuilder.PKIX ValidationAlgorithm", "RFC3280");
        this.put("CertPathValidator.PKIX", "sun.security.provider.certpath.PKIXCertPathValidator");
        this.put("CertPathValidator.PKIX ImplementedIn", "Software");
        this.put("CertPathValidator.PKIX ValidationAlgorithm", "RFC3280");
    }
}

