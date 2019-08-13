/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.AccessController;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.security.cert.Certificate;
import java.util.Collection;
import sun.security.jca.GetInstance;

public class CertStore {
    private static final String CERTSTORE_TYPE = "certstore.type";
    private CertStoreParameters params;
    private Provider provider;
    private CertStoreSpi storeSpi;
    private String type;

    protected CertStore(CertStoreSpi certStoreSpi, Provider provider, String string, CertStoreParameters certStoreParameters) {
        this.storeSpi = certStoreSpi;
        this.provider = provider;
        this.type = string;
        if (certStoreParameters != null) {
            this.params = (CertStoreParameters)certStoreParameters.clone();
        }
    }

    public static final String getDefaultType() {
        String string;
        String string2 = string = AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                return Security.getProperty(CertStore.CERTSTORE_TYPE);
            }
        });
        if (string == null) {
            string2 = "LDAP";
        }
        return string2;
    }

    public static CertStore getInstance(String object, CertStoreParameters certStoreParameters) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException {
        try {
            GetInstance.Instance instance = GetInstance.getInstance("CertStore", CertStoreSpi.class, (String)object, certStoreParameters);
            object = new CertStore((CertStoreSpi)instance.impl, instance.provider, (String)object, certStoreParameters);
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return CertStore.handleException(noSuchAlgorithmException);
        }
    }

    public static CertStore getInstance(String object, CertStoreParameters certStoreParameters, String object2) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        try {
            object2 = GetInstance.getInstance("CertStore", CertStoreSpi.class, (String)object, (Object)certStoreParameters, (String)object2);
            object = new CertStore((CertStoreSpi)((GetInstance.Instance)object2).impl, ((GetInstance.Instance)object2).provider, (String)object, certStoreParameters);
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return CertStore.handleException(noSuchAlgorithmException);
        }
    }

    public static CertStore getInstance(String object, CertStoreParameters certStoreParameters, Provider object2) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        try {
            object2 = GetInstance.getInstance("CertStore", CertStoreSpi.class, (String)object, (Object)certStoreParameters, (Provider)object2);
            object = new CertStore((CertStoreSpi)((GetInstance.Instance)object2).impl, ((GetInstance.Instance)object2).provider, (String)object, certStoreParameters);
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return CertStore.handleException(noSuchAlgorithmException);
        }
    }

    private static CertStore handleException(NoSuchAlgorithmException noSuchAlgorithmException) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        Throwable throwable = noSuchAlgorithmException.getCause();
        if (throwable instanceof InvalidAlgorithmParameterException) {
            throw (InvalidAlgorithmParameterException)throwable;
        }
        throw noSuchAlgorithmException;
    }

    public final Collection<? extends CRL> getCRLs(CRLSelector cRLSelector) throws CertStoreException {
        return this.storeSpi.engineGetCRLs(cRLSelector);
    }

    public final CertStoreParameters getCertStoreParameters() {
        CertStoreParameters certStoreParameters = this.params;
        certStoreParameters = certStoreParameters == null ? null : (CertStoreParameters)certStoreParameters.clone();
        return certStoreParameters;
    }

    public final Collection<? extends Certificate> getCertificates(CertSelector certSelector) throws CertStoreException {
        return this.storeSpi.engineGetCertificates(certSelector);
    }

    public final Provider getProvider() {
        return this.provider;
    }

    public final String getType() {
        return this.type;
    }

}

