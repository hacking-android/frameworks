/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.keystore.AndroidKeyStoreKey;
import android.security.keystore.ArrayUtils;
import java.security.PublicKey;
import java.util.Arrays;

public class AndroidKeyStorePublicKey
extends AndroidKeyStoreKey
implements PublicKey {
    private final byte[] mEncoded;

    public AndroidKeyStorePublicKey(String string2, int n, String string3, byte[] arrby) {
        super(string2, n, string3);
        this.mEncoded = ArrayUtils.cloneIfNotEmpty(arrby);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!super.equals(object)) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (AndroidKeyStorePublicKey)object;
        return Arrays.equals(this.mEncoded, ((AndroidKeyStorePublicKey)object).mEncoded);
    }

    @Override
    public byte[] getEncoded() {
        return ArrayUtils.cloneIfNotEmpty(this.mEncoded);
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 31 + Arrays.hashCode(this.mEncoded);
    }
}

