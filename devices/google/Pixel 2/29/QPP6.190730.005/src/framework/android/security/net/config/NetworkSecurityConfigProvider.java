/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.NetworkSecurityPolicy
 */
package android.security.net.config;

import android.content.Context;
import android.security.net.config.ApplicationConfig;
import android.security.net.config.ConfigNetworkSecurityPolicy;
import android.security.net.config.ConfigSource;
import android.security.net.config.ManifestConfigSource;
import java.security.Provider;
import java.security.Security;
import libcore.net.NetworkSecurityPolicy;

public final class NetworkSecurityConfigProvider
extends Provider {
    private static final String PREFIX;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(NetworkSecurityConfigProvider.class.getPackage().getName());
        stringBuilder.append(".");
        PREFIX = stringBuilder.toString();
    }

    public NetworkSecurityConfigProvider() {
        super("AndroidNSSP", 1.0, "Android Network Security Policy Provider");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX);
        stringBuilder.append("RootTrustManagerFactorySpi");
        this.put("TrustManagerFactory.PKIX", stringBuilder.toString());
        this.put("Alg.Alias.TrustManagerFactory.X509", "PKIX");
    }

    public static void install(Context object) {
        object = new ApplicationConfig(new ManifestConfigSource((Context)object));
        ApplicationConfig.setDefaultInstance((ApplicationConfig)object);
        int n = Security.insertProviderAt(new NetworkSecurityConfigProvider(), 1);
        if (n == 1) {
            NetworkSecurityPolicy.setInstance((NetworkSecurityPolicy)new ConfigNetworkSecurityPolicy((ApplicationConfig)object));
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Failed to install provider as highest priority provider. Provider was installed at position ");
        ((StringBuilder)object).append(n);
        throw new RuntimeException(((StringBuilder)object).toString());
    }
}

