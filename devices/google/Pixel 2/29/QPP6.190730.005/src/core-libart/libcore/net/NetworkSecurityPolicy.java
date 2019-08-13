/*
 * Decompiled with CFR 0.145.
 */
package libcore.net;

import dalvik.annotation.compat.UnsupportedAppUsage;

public abstract class NetworkSecurityPolicy {
    private static volatile NetworkSecurityPolicy instance = new DefaultNetworkSecurityPolicy();

    public static NetworkSecurityPolicy getInstance() {
        return instance;
    }

    public static void setInstance(NetworkSecurityPolicy networkSecurityPolicy) {
        if (networkSecurityPolicy != null) {
            instance = networkSecurityPolicy;
            return;
        }
        throw new NullPointerException("policy == null");
    }

    public abstract boolean isCertificateTransparencyVerificationRequired(String var1);

    @UnsupportedAppUsage
    public abstract boolean isCleartextTrafficPermitted();

    public abstract boolean isCleartextTrafficPermitted(String var1);

    public static final class DefaultNetworkSecurityPolicy
    extends NetworkSecurityPolicy {
        @Override
        public boolean isCertificateTransparencyVerificationRequired(String string) {
            return false;
        }

        @Override
        public boolean isCleartextTrafficPermitted() {
            return true;
        }

        @Override
        public boolean isCleartextTrafficPermitted(String string) {
            return this.isCleartextTrafficPermitted();
        }
    }

}

