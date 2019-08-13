/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.ECConstants;
import java.math.BigInteger;

class SimpleBigDecimal {
    private static final long serialVersionUID = 1L;
    private final BigInteger bigInt;
    private final int scale;

    public SimpleBigDecimal(BigInteger bigInteger, int n) {
        if (n >= 0) {
            this.bigInt = bigInteger;
            this.scale = n;
            return;
        }
        throw new IllegalArgumentException("scale may not be negative");
    }

    private void checkScale(SimpleBigDecimal simpleBigDecimal) {
        if (this.scale == simpleBigDecimal.scale) {
            return;
        }
        throw new IllegalArgumentException("Only SimpleBigDecimal of same scale allowed in arithmetic operations");
    }

    public static SimpleBigDecimal getInstance(BigInteger bigInteger, int n) {
        return new SimpleBigDecimal(bigInteger.shiftLeft(n), n);
    }

    public SimpleBigDecimal add(SimpleBigDecimal simpleBigDecimal) {
        this.checkScale(simpleBigDecimal);
        return new SimpleBigDecimal(this.bigInt.add(simpleBigDecimal.bigInt), this.scale);
    }

    public SimpleBigDecimal add(BigInteger bigInteger) {
        return new SimpleBigDecimal(this.bigInt.add(bigInteger.shiftLeft(this.scale)), this.scale);
    }

    public SimpleBigDecimal adjustScale(int n) {
        if (n >= 0) {
            int n2 = this.scale;
            if (n == n2) {
                return this;
            }
            return new SimpleBigDecimal(this.bigInt.shiftLeft(n - n2), n);
        }
        throw new IllegalArgumentException("scale may not be negative");
    }

    public int compareTo(SimpleBigDecimal simpleBigDecimal) {
        this.checkScale(simpleBigDecimal);
        return this.bigInt.compareTo(simpleBigDecimal.bigInt);
    }

    public int compareTo(BigInteger bigInteger) {
        return this.bigInt.compareTo(bigInteger.shiftLeft(this.scale));
    }

    public SimpleBigDecimal divide(SimpleBigDecimal simpleBigDecimal) {
        this.checkScale(simpleBigDecimal);
        return new SimpleBigDecimal(this.bigInt.shiftLeft(this.scale).divide(simpleBigDecimal.bigInt), this.scale);
    }

    public SimpleBigDecimal divide(BigInteger bigInteger) {
        return new SimpleBigDecimal(this.bigInt.divide(bigInteger), this.scale);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof SimpleBigDecimal)) {
            return false;
        }
        object = (SimpleBigDecimal)object;
        if (!this.bigInt.equals(((SimpleBigDecimal)object).bigInt) || this.scale != ((SimpleBigDecimal)object).scale) {
            bl = false;
        }
        return bl;
    }

    public BigInteger floor() {
        return this.bigInt.shiftRight(this.scale);
    }

    public int getScale() {
        return this.scale;
    }

    public int hashCode() {
        return this.bigInt.hashCode() ^ this.scale;
    }

    public int intValue() {
        return this.floor().intValue();
    }

    public long longValue() {
        return this.floor().longValue();
    }

    public SimpleBigDecimal multiply(SimpleBigDecimal object) {
        this.checkScale((SimpleBigDecimal)object);
        object = this.bigInt.multiply(((SimpleBigDecimal)object).bigInt);
        int n = this.scale;
        return new SimpleBigDecimal((BigInteger)object, n + n);
    }

    public SimpleBigDecimal multiply(BigInteger bigInteger) {
        return new SimpleBigDecimal(this.bigInt.multiply(bigInteger), this.scale);
    }

    public SimpleBigDecimal negate() {
        return new SimpleBigDecimal(this.bigInt.negate(), this.scale);
    }

    public BigInteger round() {
        return this.add(new SimpleBigDecimal(ECConstants.ONE, 1).adjustScale(this.scale)).floor();
    }

    public SimpleBigDecimal shiftLeft(int n) {
        return new SimpleBigDecimal(this.bigInt.shiftLeft(n), this.scale);
    }

    public SimpleBigDecimal subtract(SimpleBigDecimal simpleBigDecimal) {
        return this.add(simpleBigDecimal.negate());
    }

    public SimpleBigDecimal subtract(BigInteger bigInteger) {
        return new SimpleBigDecimal(this.bigInt.subtract(bigInteger.shiftLeft(this.scale)), this.scale);
    }

    public String toString() {
        int n;
        Object object;
        if (this.scale == 0) {
            return this.bigInt.toString();
        }
        char[] arrc = this.floor();
        Object object2 = object = this.bigInt.subtract(arrc.shiftLeft(this.scale));
        if (this.bigInt.signum() == -1) {
            object2 = ECConstants.ONE.shiftLeft(this.scale).subtract((BigInteger)object);
        }
        object = arrc;
        if (arrc.signum() == -1) {
            object = arrc;
            if (!((BigInteger)object2).equals(ECConstants.ZERO)) {
                object = arrc.add(ECConstants.ONE);
            }
        }
        object = ((BigInteger)object).toString();
        arrc = new char[this.scale];
        object2 = ((BigInteger)object2).toString(2);
        int n2 = ((String)object2).length();
        int n3 = this.scale - n2;
        for (n = 0; n < n3; ++n) {
            arrc[n] = (char)48;
        }
        for (n = 0; n < n2; ++n) {
            arrc[n3 + n] = ((String)object2).charAt(n);
        }
        object2 = new String(arrc);
        object = new StringBuffer((String)object);
        ((StringBuffer)object).append(".");
        ((StringBuffer)object).append((String)object2);
        return ((StringBuffer)object).toString();
    }
}

