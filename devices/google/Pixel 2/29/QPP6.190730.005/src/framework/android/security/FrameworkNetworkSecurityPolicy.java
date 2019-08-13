/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.NetworkSecurityPolicy
 */
package android.security;

import libcore.net.NetworkSecurityPolicy;

public class FrameworkNetworkSecurityPolicy
extends NetworkSecurityPolicy {
    private final boolean mCleartextTrafficPermitted;

    public FrameworkNetworkSecurityPolicy(boolean bl) {
        this.mCleartextTrafficPermitted = bl;
    }

    public boolean isCertificateTransparencyVerificationRequired(String string2) {
        return false;
    }

    public boolean isCleartextTrafficPermitted() {
        return this.mCleartextTrafficPermitted;
    }

    public boolean isCleartextTrafficPermitted(String string2) {
        return this.isCleartextTrafficPermitted();
    }
}

