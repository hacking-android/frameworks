/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.math.BigInteger;

public class ECPoint {
    public static final ECPoint POINT_INFINITY = new ECPoint();
    private final BigInteger x;
    private final BigInteger y;

    private ECPoint() {
        this.x = null;
        this.y = null;
    }

    public ECPoint(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger != null && bigInteger2 != null) {
            this.x = bigInteger;
            this.y = bigInteger2;
            return;
        }
        throw new NullPointerException("affine coordinate x or y is null");
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (this == POINT_INFINITY) {
            return false;
        }
        if (object instanceof ECPoint) {
            if (!this.x.equals(((ECPoint)object).x) || !this.y.equals(((ECPoint)object).y)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public BigInteger getAffineX() {
        return this.x;
    }

    public BigInteger getAffineY() {
        return this.y;
    }

    public int hashCode() {
        if (this == POINT_INFINITY) {
            return 0;
        }
        return this.x.hashCode() << this.y.hashCode() + 5;
    }
}

