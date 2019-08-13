/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.keystore.AndroidKeyStorePrivateKey;
import java.math.BigInteger;
import java.security.interfaces.RSAKey;

public class AndroidKeyStoreRSAPrivateKey
extends AndroidKeyStorePrivateKey
implements RSAKey {
    private final BigInteger mModulus;

    public AndroidKeyStoreRSAPrivateKey(String string2, int n, BigInteger bigInteger) {
        super(string2, n, "RSA");
        this.mModulus = bigInteger;
    }

    @Override
    public BigInteger getModulus() {
        return this.mModulus;
    }
}

