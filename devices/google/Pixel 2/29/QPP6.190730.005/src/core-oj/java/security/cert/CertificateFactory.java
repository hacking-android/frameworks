/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import sun.security.jca.GetInstance;
import sun.security.jca.Providers;

public class CertificateFactory {
    private CertificateFactorySpi certFacSpi;
    private Provider provider;
    private String type;

    protected CertificateFactory(CertificateFactorySpi certificateFactorySpi, Provider provider, String string) {
        this.certFacSpi = certificateFactorySpi;
        this.provider = provider;
        this.type = string;
    }

    public static final CertificateFactory getInstance(String string) throws CertificateException {
        try {
            Object object = GetInstance.getInstance("CertificateFactory", CertificateFactorySpi.class, string);
            object = new CertificateFactory((CertificateFactorySpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" not found");
            throw new CertificateException(stringBuilder.toString(), noSuchAlgorithmException);
        }
    }

    public static final CertificateFactory getInstance(String string, String object) throws CertificateException, NoSuchProviderException {
        try {
            Providers.checkBouncyCastleDeprecation((String)object, "CertificateFactory", string);
            object = GetInstance.getInstance("CertificateFactory", CertificateFactorySpi.class, string, (String)object);
            object = new CertificateFactory((CertificateFactorySpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" not found");
            throw new CertificateException(stringBuilder.toString(), noSuchAlgorithmException);
        }
    }

    public static final CertificateFactory getInstance(String string, Provider object) throws CertificateException {
        try {
            Providers.checkBouncyCastleDeprecation((Provider)object, "CertificateFactory", string);
            object = GetInstance.getInstance("CertificateFactory", CertificateFactorySpi.class, string, (Provider)object);
            object = new CertificateFactory((CertificateFactorySpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" not found");
            throw new CertificateException(((StringBuilder)object).toString(), noSuchAlgorithmException);
        }
    }

    public final CRL generateCRL(InputStream inputStream) throws CRLException {
        return this.certFacSpi.engineGenerateCRL(inputStream);
    }

    public final Collection<? extends CRL> generateCRLs(InputStream inputStream) throws CRLException {
        return this.certFacSpi.engineGenerateCRLs(inputStream);
    }

    public final CertPath generateCertPath(InputStream inputStream) throws CertificateException {
        return this.certFacSpi.engineGenerateCertPath(inputStream);
    }

    public final CertPath generateCertPath(InputStream inputStream, String string) throws CertificateException {
        return this.certFacSpi.engineGenerateCertPath(inputStream, string);
    }

    public final CertPath generateCertPath(List<? extends Certificate> list) throws CertificateException {
        return this.certFacSpi.engineGenerateCertPath(list);
    }

    public final Certificate generateCertificate(InputStream inputStream) throws CertificateException {
        return this.certFacSpi.engineGenerateCertificate(inputStream);
    }

    public final Collection<? extends Certificate> generateCertificates(InputStream inputStream) throws CertificateException {
        return this.certFacSpi.engineGenerateCertificates(inputStream);
    }

    public final Iterator<String> getCertPathEncodings() {
        return this.certFacSpi.engineGetCertPathEncodings();
    }

    public final Provider getProvider() {
        return this.provider;
    }

    public final String getType() {
        return this.type;
    }
}

