/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.security.cert.CertPathParameters;
import javax.net.ssl.ManagerFactoryParameters;

public class CertPathTrustManagerParameters
implements ManagerFactoryParameters {
    private final CertPathParameters parameters;

    public CertPathTrustManagerParameters(CertPathParameters certPathParameters) {
        this.parameters = (CertPathParameters)certPathParameters.clone();
    }

    public CertPathParameters getParameters() {
        return (CertPathParameters)this.parameters.clone();
    }
}

