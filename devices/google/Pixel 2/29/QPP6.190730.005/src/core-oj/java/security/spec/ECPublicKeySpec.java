/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.KeySpec;

public class ECPublicKeySpec
implements KeySpec {
    private ECParameterSpec params;
    private ECPoint w;

    public ECPublicKeySpec(ECPoint eCPoint, ECParameterSpec eCParameterSpec) {
        if (eCPoint != null) {
            if (eCParameterSpec != null) {
                if (eCPoint != ECPoint.POINT_INFINITY) {
                    this.w = eCPoint;
                    this.params = eCParameterSpec;
                    return;
                }
                throw new IllegalArgumentException("w is ECPoint.POINT_INFINITY");
            }
            throw new NullPointerException("params is null");
        }
        throw new NullPointerException("w is null");
    }

    public ECParameterSpec getParams() {
        return this.params;
    }

    public ECPoint getW() {
        return this.w;
    }
}

