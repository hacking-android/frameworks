/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.keystore.AndroidKeyStorePublicKey;
import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;

public class AndroidKeyStoreRSAPublicKey
extends AndroidKeyStorePublicKey
implements RSAPublicKey {
    private final BigInteger mModulus;
    private final BigInteger mPublicExponent;

    public AndroidKeyStoreRSAPublicKey(String charSequence, int n, RSAPublicKey rSAPublicKey) {
        this((String)charSequence, n, rSAPublicKey.getEncoded(), rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
        if ("X.509".equalsIgnoreCase(rSAPublicKey.getFormat())) {
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unsupported key export format: ");
        ((StringBuilder)charSequence).append(rSAPublicKey.getFormat());
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public AndroidKeyStoreRSAPublicKey(String string2, int n, byte[] arrby, BigInteger bigInteger, BigInteger bigInteger2) {
        super(string2, n, "RSA", arrby);
        this.mModulus = bigInteger;
        this.mPublicExponent = bigInteger2;
    }

    @Override
    public BigInteger getModulus() {
        return this.mModulus;
    }

    @Override
    public BigInteger getPublicExponent() {
        return this.mPublicExponent;
    }
}

