/*
 * Decompiled with CFR 0.145.
 */
package android.security;

import android.content.Context;
import java.security.KeyStore;

@Deprecated
public final class KeyStoreParameter
implements KeyStore.ProtectionParameter {
    private final int mFlags;

    private KeyStoreParameter(int n) {
        this.mFlags = n;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public boolean isEncryptionRequired() {
        int n = this.mFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    @Deprecated
    public static final class Builder {
        private int mFlags;

        public Builder(Context context) {
            if (context != null) {
                return;
            }
            throw new NullPointerException("context == null");
        }

        public KeyStoreParameter build() {
            return new KeyStoreParameter(this.mFlags);
        }

        public Builder setEncryptionRequired(boolean bl) {
            this.mFlags = bl ? (this.mFlags |= 1) : (this.mFlags &= -2);
            return this;
        }
    }

}

