/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.x509.X509AttributeCertificate;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.util.Collection;
import java.util.Set;

public abstract class PKIXAttrCertChecker
implements Cloneable {
    public abstract void check(X509AttributeCertificate var1, CertPath var2, CertPath var3, Collection var4) throws CertPathValidatorException;

    public abstract Object clone();

    public abstract Set getSupportedExtensions();
}

