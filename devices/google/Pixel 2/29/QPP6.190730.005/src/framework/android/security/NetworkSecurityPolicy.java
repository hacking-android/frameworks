/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.NetworkSecurityPolicy
 */
package android.security;

import android.content.Context;
import android.content.pm.PackageManager;
import android.security.FrameworkNetworkSecurityPolicy;
import android.security.net.config.ApplicationConfig;
import android.security.net.config.ConfigSource;
import android.security.net.config.ManifestConfigSource;

public class NetworkSecurityPolicy {
    private static final NetworkSecurityPolicy INSTANCE = new NetworkSecurityPolicy();

    private NetworkSecurityPolicy() {
    }

    public static ApplicationConfig getApplicationConfigForPackage(Context context, String string2) throws PackageManager.NameNotFoundException {
        return new ApplicationConfig(new ManifestConfigSource(context.createPackageContext(string2, 0)));
    }

    public static NetworkSecurityPolicy getInstance() {
        return INSTANCE;
    }

    public void handleTrustStorageUpdate() {
        ApplicationConfig applicationConfig = ApplicationConfig.getDefaultInstance();
        if (applicationConfig != null) {
            applicationConfig.handleTrustStorageUpdate();
        }
    }

    public boolean isCleartextTrafficPermitted() {
        return libcore.net.NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted();
    }

    public boolean isCleartextTrafficPermitted(String string2) {
        return libcore.net.NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted(string2);
    }

    public void setCleartextTrafficPermitted(boolean bl) {
        libcore.net.NetworkSecurityPolicy.setInstance((libcore.net.NetworkSecurityPolicy)new FrameworkNetworkSecurityPolicy(bl));
    }
}

