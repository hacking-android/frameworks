/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.security.AccessController;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.Security;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import sun.security.jca.GetInstance;

public class TrustManagerFactory {
    private String algorithm;
    private TrustManagerFactorySpi factorySpi;
    private Provider provider;

    protected TrustManagerFactory(TrustManagerFactorySpi trustManagerFactorySpi, Provider provider, String string) {
        this.factorySpi = trustManagerFactorySpi;
        this.provider = provider;
        this.algorithm = string;
    }

    public static final String getDefaultAlgorithm() {
        String string;
        String string2 = string = AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                return Security.getProperty("ssl.TrustManagerFactory.algorithm");
            }
        });
        if (string == null) {
            string2 = "SunX509";
        }
        return string2;
    }

    public static final TrustManagerFactory getInstance(String string) throws NoSuchAlgorithmException {
        GetInstance.Instance instance = GetInstance.getInstance("TrustManagerFactory", TrustManagerFactorySpi.class, string);
        return new TrustManagerFactory((TrustManagerFactorySpi)instance.impl, instance.provider, string);
    }

    public static final TrustManagerFactory getInstance(String string, String object) throws NoSuchAlgorithmException, NoSuchProviderException {
        object = GetInstance.getInstance("TrustManagerFactory", TrustManagerFactorySpi.class, string, (String)object);
        return new TrustManagerFactory((TrustManagerFactorySpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public static final TrustManagerFactory getInstance(String string, Provider object) throws NoSuchAlgorithmException {
        object = GetInstance.getInstance("TrustManagerFactory", TrustManagerFactorySpi.class, string, (Provider)object);
        return new TrustManagerFactory((TrustManagerFactorySpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    public final Provider getProvider() {
        return this.provider;
    }

    public final TrustManager[] getTrustManagers() {
        return this.factorySpi.engineGetTrustManagers();
    }

    public final void init(KeyStore keyStore) throws KeyStoreException {
        this.factorySpi.engineInit(keyStore);
    }

    public final void init(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
        this.factorySpi.engineInit(managerFactoryParameters);
    }

}

