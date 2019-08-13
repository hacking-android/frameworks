/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import java.security.Key;

public class AndroidKeyStoreKey
implements Key {
    private final String mAlgorithm;
    private final String mAlias;
    private final int mUid;

    public AndroidKeyStoreKey(String string2, int n, String string3) {
        this.mAlias = string2;
        this.mUid = n;
        this.mAlgorithm = string3;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (AndroidKeyStoreKey)object;
        String string2 = this.mAlgorithm;
        if (string2 == null ? ((AndroidKeyStoreKey)object).mAlgorithm != null : !string2.equals(((AndroidKeyStoreKey)object).mAlgorithm)) {
            return false;
        }
        string2 = this.mAlias;
        if (string2 == null ? ((AndroidKeyStoreKey)object).mAlias != null : !string2.equals(((AndroidKeyStoreKey)object).mAlias)) {
            return false;
        }
        return this.mUid == ((AndroidKeyStoreKey)object).mUid;
    }

    @Override
    public String getAlgorithm() {
        return this.mAlgorithm;
    }

    String getAlias() {
        return this.mAlias;
    }

    @Override
    public byte[] getEncoded() {
        return null;
    }

    @Override
    public String getFormat() {
        return null;
    }

    int getUid() {
        return this.mUid;
    }

    public int hashCode() {
        String string2 = this.mAlgorithm;
        int n = 0;
        int n2 = string2 == null ? 0 : string2.hashCode();
        string2 = this.mAlias;
        if (string2 != null) {
            n = string2.hashCode();
        }
        return ((1 * 31 + n2) * 31 + n) * 31 + this.mUid;
    }
}

