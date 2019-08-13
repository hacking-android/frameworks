/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.math.BigInteger;
import java.security.spec.ECField;

public class ECFieldFp
implements ECField {
    private BigInteger p;

    public ECFieldFp(BigInteger bigInteger) {
        if (bigInteger.signum() == 1) {
            this.p = bigInteger;
            return;
        }
        throw new IllegalArgumentException("p is not positive");
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof ECFieldFp) {
            return this.p.equals(((ECFieldFp)object).p);
        }
        return false;
    }

    @Override
    public int getFieldSize() {
        return this.p.bitLength();
    }

    public BigInteger getP() {
        return this.p;
    }

    public int hashCode() {
        return this.p.hashCode();
    }
}

