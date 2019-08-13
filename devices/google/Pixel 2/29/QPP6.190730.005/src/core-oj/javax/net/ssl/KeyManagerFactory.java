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
import java.security.UnrecoverableKeyException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactorySpi;
import javax.net.ssl.ManagerFactoryParameters;
import sun.security.jca.GetInstance;

public class KeyManagerFactory {
    private String algorithm;
    private KeyManagerFactorySpi factorySpi;
    private Provider provider;

    protected KeyManagerFactory(KeyManagerFactorySpi keyManagerFactorySpi, Provider provider, String string) {
        this.factorySpi = keyManagerFactorySpi;
        this.provider = provider;
        this.algorithm = string;
    }

    public static final String getDefaultAlgorithm() {
        String string;
        String string2 = string = AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                return Security.getProperty("ssl.KeyManagerFactory.algorithm");
            }
        });
        if (string == null) {
            string2 = "SunX509";
        }
        return string2;
    }

    public static final KeyManagerFactory getInstance(String string) throws NoSuchAlgorithmException {
        GetInstance.Instance instance = GetInstance.getInstance("KeyManagerFactory", KeyManagerFactorySpi.class, string);
        return new KeyManagerFactory((KeyManagerFactorySpi)instance.impl, instance.provider, string);
    }

    public static final KeyManagerFactory getInstance(String string, String object) throws NoSuchAlgorithmException, NoSuchProviderException {
        object = GetInstance.getInstance("KeyManagerFactory", KeyManagerFactorySpi.class, string, (String)object);
        return new KeyManagerFactory((KeyManagerFactorySpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public static final KeyManagerFactory getInstance(String string, Provider object) throws NoSuchAlgorithmException {
        object = GetInstance.getInstance("KeyManagerFactory", KeyManagerFactorySpi.class, string, (Provider)object);
        return new KeyManagerFactory((KeyManagerFactorySpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    public final KeyManager[] getKeyManagers() {
        return this.factorySpi.engineGetKeyManagers();
    }

    public final Provider getProvider() {
        return this.provider;
    }

    public final void init(KeyStore keyStore, char[] arrc) throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {
        this.factorySpi.engineInit(keyStore, arrc);
    }

    public final void init(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
        this.factorySpi.engineInit(managerFactoryParameters);
    }

}

