/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import java.security.KeyStore;

class AndroidKeyStoreLoadStoreParameter
implements KeyStore.LoadStoreParameter {
    private final int mUid;

    AndroidKeyStoreLoadStoreParameter(int n) {
        this.mUid = n;
    }

    @Override
    public KeyStore.ProtectionParameter getProtectionParameter() {
        return null;
    }

    int getUid() {
        return this.mUid;
    }
}

