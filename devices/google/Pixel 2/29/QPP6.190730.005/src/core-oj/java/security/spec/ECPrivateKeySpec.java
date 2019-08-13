/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.math.BigInteger;
import java.security.spec.ECParameterSpec;
import java.security.spec.KeySpec;

public class ECPrivateKeySpec
implements KeySpec {
    private ECParameterSpec params;
    private BigInteger s;

    public ECPrivateKeySpec(BigInteger bigInteger, ECParameterSpec eCParameterSpec) {
        if (bigInteger != null) {
            if (eCParameterSpec != null) {
                this.s = bigInteger;
                this.params = eCParameterSpec;
                return;
            }
            throw new NullPointerException("params is null");
        }
        throw new NullPointerException("s is null");
    }

    public ECParameterSpec getParams() {
        return this.params;
    }

    public BigInteger getS() {
        return this.s;
    }
}

