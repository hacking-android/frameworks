/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;

public final class Rational
extends Number
implements Comparable<Rational> {
    public static final Rational NEGATIVE_INFINITY;
    public static final Rational NaN;
    public static final Rational POSITIVE_INFINITY;
    public static final Rational ZERO;
    private static final long serialVersionUID = 1L;
    @UnsupportedAppUsage
    private final int mDenominator;
    @UnsupportedAppUsage
    private final int mNumerator;

    static {
        NaN = new Rational(0, 0);
        POSITIVE_INFINITY = new Rational(1, 0);
        NEGATIVE_INFINITY = new Rational(-1, 0);
        ZERO = new Rational(0, 1);
    }

    public Rational(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        if (n2 < 0) {
            n3 = -n;
            n4 = -n2;
        }
        if (n4 == 0 && n3 > 0) {
            this.mNumerator = 1;
            this.mDenominator = 0;
        } else if (n4 == 0 && n3 < 0) {
            this.mNumerator = -1;
            this.mDenominator = 0;
        } else if (n4 == 0 && n3 == 0) {
            this.mNumerator = 0;
            this.mDenominator = 0;
        } else if (n3 == 0) {
            this.mNumerator = 0;
            this.mDenominator = 1;
        } else {
            n = Rational.gcd(n3, n4);
            this.mNumerator = n3 / n;
            this.mDenominator = n4 / n;
        }
    }

    private boolean equals(Rational rational) {
        boolean bl = this.mNumerator == rational.mNumerator && this.mDenominator == rational.mDenominator;
        return bl;
    }

    public static int gcd(int n, int n2) {
        int n3 = n;
        n = n2;
        while (n != 0) {
            n2 = n3 % n;
            n3 = n;
            n = n2;
        }
        return Math.abs(n3);
    }

    private static NumberFormatException invalidRational(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Rational: \"");
        stringBuilder.append(string2);
        stringBuilder.append("\"");
        throw new NumberFormatException(stringBuilder.toString());
    }

    private boolean isNegInf() {
        boolean bl = this.mDenominator == 0 && this.mNumerator < 0;
        return bl;
    }

    private boolean isPosInf() {
        boolean bl = this.mDenominator == 0 && this.mNumerator > 0;
        return bl;
    }

    public static Rational parseRational(String string2) throws NumberFormatException {
        int n;
        Preconditions.checkNotNull(string2, "string must not be null");
        if (string2.equals("NaN")) {
            return NaN;
        }
        if (string2.equals("Infinity")) {
            return POSITIVE_INFINITY;
        }
        if (string2.equals("-Infinity")) {
            return NEGATIVE_INFINITY;
        }
        int n2 = n = string2.indexOf(58);
        if (n < 0) {
            n2 = string2.indexOf(47);
        }
        if (n2 >= 0) {
            try {
                Rational rational = new Rational(Integer.parseInt(string2.substring(0, n2)), Integer.parseInt(string2.substring(n2 + 1)));
                return rational;
            }
            catch (NumberFormatException numberFormatException) {
                throw Rational.invalidRational(string2);
            }
        }
        throw Rational.invalidRational(string2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int n = this.mNumerator;
        if (n == 0) {
            n = this.mDenominator;
            if (n != 1 && n != 0) {
                throw new InvalidObjectException("Rational must be deserialized from a reduced form for zero values");
            }
            return;
        }
        int n2 = this.mDenominator;
        if (n2 == 0) {
            if (n != 1 && n != -1) {
                throw new InvalidObjectException("Rational must be deserialized from a reduced form for infinity values");
            }
            return;
        }
        if (Rational.gcd(n, n2) <= 1) {
            return;
        }
        throw new InvalidObjectException("Rational must be deserialized from a reduced form for finite values");
    }

    @Override
    public int compareTo(Rational rational) {
        Preconditions.checkNotNull(rational, "another must not be null");
        if (this.equals(rational)) {
            return 0;
        }
        if (this.isNaN()) {
            return 1;
        }
        if (rational.isNaN()) {
            return -1;
        }
        if (!this.isPosInf() && !rational.isNegInf()) {
            if (!this.isNegInf() && !rational.isPosInf()) {
                long l = (long)this.mNumerator * (long)rational.mDenominator;
                long l2 = (long)rational.mNumerator * (long)this.mDenominator;
                if (l < l2) {
                    return -1;
                }
                return l > l2;
            }
            return -1;
        }
        return 1;
    }

    @Override
    public double doubleValue() {
        return (double)this.mNumerator / (double)this.mDenominator;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Rational && this.equals((Rational)object);
        return bl;
    }

    @Override
    public float floatValue() {
        return (float)this.mNumerator / (float)this.mDenominator;
    }

    public int getDenominator() {
        return this.mDenominator;
    }

    public int getNumerator() {
        return this.mNumerator;
    }

    public int hashCode() {
        int n = this.mNumerator;
        return this.mDenominator ^ (n >>> 16 | n << 16);
    }

    @Override
    public int intValue() {
        if (this.isPosInf()) {
            return Integer.MAX_VALUE;
        }
        if (this.isNegInf()) {
            return Integer.MIN_VALUE;
        }
        if (this.isNaN()) {
            return 0;
        }
        return this.mNumerator / this.mDenominator;
    }

    public boolean isFinite() {
        boolean bl = this.mDenominator != 0;
        return bl;
    }

    public boolean isInfinite() {
        boolean bl = this.mNumerator != 0 && this.mDenominator == 0;
        return bl;
    }

    public boolean isNaN() {
        boolean bl = this.mDenominator == 0 && this.mNumerator == 0;
        return bl;
    }

    public boolean isZero() {
        boolean bl = this.isFinite() && this.mNumerator == 0;
        return bl;
    }

    @Override
    public long longValue() {
        if (this.isPosInf()) {
            return Long.MAX_VALUE;
        }
        if (this.isNegInf()) {
            return Long.MIN_VALUE;
        }
        if (this.isNaN()) {
            return 0L;
        }
        return this.mNumerator / this.mDenominator;
    }

    @Override
    public short shortValue() {
        return (short)this.intValue();
    }

    public float toFloat() {
        return this.floatValue();
    }

    public String toString() {
        if (this.isNaN()) {
            return "NaN";
        }
        if (this.isPosInf()) {
            return "Infinity";
        }
        if (this.isNegInf()) {
            return "-Infinity";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mNumerator);
        stringBuilder.append("/");
        stringBuilder.append(this.mDenominator);
        return stringBuilder.toString();
    }
}

