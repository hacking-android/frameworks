/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.keystore.AndroidKeyStorePublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;

public class AndroidKeyStoreECPublicKey
extends AndroidKeyStorePublicKey
implements ECPublicKey {
    private final ECParameterSpec mParams;
    private final ECPoint mW;

    public AndroidKeyStoreECPublicKey(String charSequence, int n, ECPublicKey eCPublicKey) {
        this((String)charSequence, n, eCPublicKey.getEncoded(), eCPublicKey.getParams(), eCPublicKey.getW());
        if ("X.509".equalsIgnoreCase(eCPublicKey.getFormat())) {
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unsupported key export format: ");
        ((StringBuilder)charSequence).append(eCPublicKey.getFormat());
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public AndroidKeyStoreECPublicKey(String string2, int n, byte[] arrby, ECParameterSpec eCParameterSpec, ECPoint eCPoint) {
        super(string2, n, "EC", arrby);
        this.mParams = eCParameterSpec;
        this.mW = eCPoint;
    }

    @Override
    public ECParameterSpec getParams() {
        return this.mParams;
    }

    @Override
    public ECPoint getW() {
        return this.mW;
    }
}

