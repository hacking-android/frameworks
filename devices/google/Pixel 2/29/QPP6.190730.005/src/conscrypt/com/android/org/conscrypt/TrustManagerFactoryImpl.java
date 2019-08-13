/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.Platform;
import com.android.org.conscrypt.TrustManagerImpl;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;

public class TrustManagerFactoryImpl
extends TrustManagerFactorySpi {
    private KeyStore keyStore;

    @Override
    public TrustManager[] engineGetTrustManagers() {
        KeyStore keyStore = this.keyStore;
        if (keyStore != null) {
            return new TrustManager[]{new TrustManagerImpl(keyStore)};
        }
        throw new IllegalStateException("TrustManagerFactory is not initialized");
    }

    @Override
    public void engineInit(KeyStore keyStore) throws KeyStoreException {
        this.keyStore = keyStore != null ? keyStore : Platform.getDefaultCertKeyStore();
    }

    @Override
    public void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
        throw new InvalidAlgorithmParameterException("ManagerFactoryParameters not supported");
    }
}

