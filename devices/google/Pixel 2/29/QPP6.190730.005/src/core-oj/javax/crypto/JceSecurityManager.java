/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import javax.crypto.CryptoPermission;

final class JceSecurityManager
extends SecurityManager {
    static final JceSecurityManager INSTANCE = null;

    private JceSecurityManager() {
    }

    CryptoPermission getCryptoPermission(String string) {
        return null;
    }
}

