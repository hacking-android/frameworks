/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

interface State
extends Cloneable {
    public Object clone();

    public boolean isInitial();

    public boolean keyParamsNeeded();

    public void updateState(X509Certificate var1) throws CertificateException, IOException, CertPathValidatorException;
}

