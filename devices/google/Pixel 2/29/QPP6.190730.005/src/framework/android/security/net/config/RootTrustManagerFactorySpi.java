/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import android.security.net.config.ApplicationConfig;
import android.security.net.config.ConfigSource;
import android.security.net.config.KeyStoreConfigSource;
import android.security.net.config.NetworkSecurityConfig;
import com.android.internal.annotations.VisibleForTesting;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;

public class RootTrustManagerFactorySpi
extends TrustManagerFactorySpi {
    private ApplicationConfig mApplicationConfig;
    private NetworkSecurityConfig mConfig;

    @Override
    public TrustManager[] engineGetTrustManagers() {
        ApplicationConfig applicationConfig = this.mApplicationConfig;
        if (applicationConfig != null) {
            return new TrustManager[]{applicationConfig.getTrustManager()};
        }
        throw new IllegalStateException("TrustManagerFactory not initialized");
    }

    @Override
    public void engineInit(KeyStore keyStore) throws KeyStoreException {
        this.mApplicationConfig = keyStore != null ? new ApplicationConfig(new KeyStoreConfigSource(keyStore)) : ApplicationConfig.getDefaultInstance();
    }

    @Override
    public void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
        if (managerFactoryParameters instanceof ApplicationConfigParameters) {
            this.mApplicationConfig = ((ApplicationConfigParameters)managerFactoryParameters).config;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported spec: ");
        stringBuilder.append(managerFactoryParameters);
        stringBuilder.append(". Only ");
        stringBuilder.append(ApplicationConfigParameters.class.getName());
        stringBuilder.append(" supported");
        throw new InvalidAlgorithmParameterException(stringBuilder.toString());
    }

    @VisibleForTesting
    public static final class ApplicationConfigParameters
    implements ManagerFactoryParameters {
        public final ApplicationConfig config;

        public ApplicationConfigParameters(ApplicationConfig applicationConfig) {
            this.config = applicationConfig;
        }
    }

}

