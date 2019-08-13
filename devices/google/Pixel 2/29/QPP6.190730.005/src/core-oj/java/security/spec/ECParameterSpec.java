/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;

public class ECParameterSpec
implements AlgorithmParameterSpec {
    private final EllipticCurve curve;
    private String curveName;
    private final ECPoint g;
    private final int h;
    private final BigInteger n;

    public ECParameterSpec(EllipticCurve ellipticCurve, ECPoint eCPoint, BigInteger bigInteger, int n) {
        if (ellipticCurve != null) {
            if (eCPoint != null) {
                if (bigInteger != null) {
                    if (bigInteger.signum() == 1) {
                        if (n > 0) {
                            this.curve = ellipticCurve;
                            this.g = eCPoint;
                            this.n = bigInteger;
                            this.h = n;
                            return;
                        }
                        throw new IllegalArgumentException("h is not positive");
                    }
                    throw new IllegalArgumentException("n is not positive");
                }
                throw new NullPointerException("n is null");
            }
            throw new NullPointerException("g is null");
        }
        throw new NullPointerException("curve is null");
    }

    public int getCofactor() {
        return this.h;
    }

    public EllipticCurve getCurve() {
        return this.curve;
    }

    public String getCurveName() {
        return this.curveName;
    }

    public ECPoint getGenerator() {
        return this.g;
    }

    public BigInteger getOrder() {
        return this.n;
    }

    public void setCurveName(String string) {
        this.curveName = string;
    }
}

