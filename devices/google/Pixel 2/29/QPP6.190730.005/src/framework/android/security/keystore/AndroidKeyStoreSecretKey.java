/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.keystore.AndroidKeyStoreKey;
import javax.crypto.SecretKey;

public class AndroidKeyStoreSecretKey
extends AndroidKeyStoreKey
implements SecretKey {
    public AndroidKeyStoreSecretKey(String string2, int n, String string3) {
        super(string2, n, string3);
    }
}

