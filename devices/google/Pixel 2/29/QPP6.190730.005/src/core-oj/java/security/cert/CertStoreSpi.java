/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.Certificate;
import java.util.Collection;

public abstract class CertStoreSpi {
    public CertStoreSpi(CertStoreParameters certStoreParameters) throws InvalidAlgorithmParameterException {
    }

    public abstract Collection<? extends CRL> engineGetCRLs(CRLSelector var1) throws CertStoreException;

    public abstract Collection<? extends Certificate> engineGetCertificates(CertSelector var1) throws CertStoreException;
}

