/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;

public class EllipticCurve {
    private final BigInteger a;
    private final BigInteger b;
    private final ECField field;
    private final byte[] seed;

    public EllipticCurve(ECField eCField, BigInteger bigInteger, BigInteger bigInteger2) {
        this(eCField, bigInteger, bigInteger2, null);
    }

    public EllipticCurve(ECField eCField, BigInteger bigInteger, BigInteger bigInteger2, byte[] arrby) {
        if (eCField != null) {
            if (bigInteger != null) {
                if (bigInteger2 != null) {
                    EllipticCurve.checkValidity(eCField, bigInteger, "first coefficient");
                    EllipticCurve.checkValidity(eCField, bigInteger2, "second coefficient");
                    this.field = eCField;
                    this.a = bigInteger;
                    this.b = bigInteger2;
                    this.seed = arrby != null ? (byte[])arrby.clone() : null;
                    return;
                }
                throw new NullPointerException("second coefficient is null");
            }
            throw new NullPointerException("first coefficient is null");
        }
        throw new NullPointerException("field is null");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void checkValidity(ECField object, BigInteger bigInteger, String string) {
        if (object instanceof ECFieldFp) {
            if (((ECFieldFp)object).getP().compareTo(bigInteger) == 1) {
                if (bigInteger.signum() >= 0) return;
                object = new StringBuilder();
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(" is negative");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" is too large");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        if (!(object instanceof ECFieldF2m)) return;
        int n = ((ECFieldF2m)object).getM();
        if (bigInteger.bitLength() <= n) return;
        object = new StringBuilder();
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" is too large");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof EllipticCurve) {
            object = (EllipticCurve)object;
            if (this.field.equals(((EllipticCurve)object).field) && this.a.equals(((EllipticCurve)object).a) && this.b.equals(((EllipticCurve)object).b)) {
                return true;
            }
        }
        return false;
    }

    public BigInteger getA() {
        return this.a;
    }

    public BigInteger getB() {
        return this.b;
    }

    public ECField getField() {
        return this.field;
    }

    public byte[] getSeed() {
        byte[] arrby = this.seed;
        if (arrby == null) {
            return null;
        }
        return (byte[])arrby.clone();
    }

    public int hashCode() {
        return this.field.hashCode() << (this.a.hashCode() << 4) + 6 + (this.b.hashCode() << 2);
    }
}

