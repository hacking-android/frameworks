/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.NetworkSecurityPolicy
 */
package android.security.net.config;

import android.security.net.config.ApplicationConfig;
import libcore.net.NetworkSecurityPolicy;

public class ConfigNetworkSecurityPolicy
extends NetworkSecurityPolicy {
    private final ApplicationConfig mConfig;

    public ConfigNetworkSecurityPolicy(ApplicationConfig applicationConfig) {
        this.mConfig = applicationConfig;
    }

    public boolean isCertificateTransparencyVerificationRequired(String string2) {
        return false;
    }

    public boolean isCleartextTrafficPermitted() {
        return this.mConfig.isCleartextTrafficPermitted();
    }

    public boolean isCleartextTrafficPermitted(String string2) {
        return this.mConfig.isCleartextTrafficPermitted(string2);
    }
}

