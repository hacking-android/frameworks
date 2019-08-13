/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class CertificateFactorySpi {
    public abstract CRL engineGenerateCRL(InputStream var1) throws CRLException;

    public abstract Collection<? extends CRL> engineGenerateCRLs(InputStream var1) throws CRLException;

    public CertPath engineGenerateCertPath(InputStream inputStream) throws CertificateException {
        throw new UnsupportedOperationException();
    }

    public CertPath engineGenerateCertPath(InputStream inputStream, String string) throws CertificateException {
        throw new UnsupportedOperationException();
    }

    public CertPath engineGenerateCertPath(List<? extends Certificate> list) throws CertificateException {
        throw new UnsupportedOperationException();
    }

    public abstract Certificate engineGenerateCertificate(InputStream var1) throws CertificateException;

    public abstract Collection<? extends Certificate> engineGenerateCertificates(InputStream var1) throws CertificateException;

    public Iterator<String> engineGetCertPathEncodings() {
        throw new UnsupportedOperationException();
    }
}

