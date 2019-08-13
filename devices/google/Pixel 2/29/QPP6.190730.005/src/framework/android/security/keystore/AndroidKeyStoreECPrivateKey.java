/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.keystore.AndroidKeyStorePrivateKey;
import java.security.interfaces.ECKey;
import java.security.spec.ECParameterSpec;

public class AndroidKeyStoreECPrivateKey
extends AndroidKeyStorePrivateKey
implements ECKey {
    private final ECParameterSpec mParams;

    public AndroidKeyStoreECPrivateKey(String string2, int n, ECParameterSpec eCParameterSpec) {
        super(string2, n, "EC");
        this.mParams = eCParameterSpec;
    }

    @Override
    public ECParameterSpec getParams() {
        return this.mParams;
    }
}

