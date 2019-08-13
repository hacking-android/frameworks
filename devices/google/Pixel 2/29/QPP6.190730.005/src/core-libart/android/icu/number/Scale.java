/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.RoundingUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Scale {
    private static final BigDecimal BIG_DECIMAL_100;
    private static final BigDecimal BIG_DECIMAL_1000;
    private static final Scale DEFAULT;
    private static final Scale HUNDRED;
    private static final Scale THOUSAND;
    final BigDecimal arbitrary;
    final int magnitude;
    final MathContext mc;
    final BigDecimal reciprocal;

    static {
        DEFAULT = new Scale(0, null);
        HUNDRED = new Scale(2, null);
        THOUSAND = new Scale(3, null);
        BIG_DECIMAL_100 = BigDecimal.valueOf(100L);
        BIG_DECIMAL_1000 = BigDecimal.valueOf(1000L);
    }

    private Scale(int n, BigDecimal bigDecimal) {
        this(n, bigDecimal, RoundingUtils.DEFAULT_MATH_CONTEXT_34_DIGITS);
    }

    private Scale(int n, BigDecimal bigDecimal, MathContext mathContext) {
        int n2 = n;
        BigDecimal bigDecimal2 = bigDecimal;
        if (bigDecimal != null) {
            bigDecimal = bigDecimal.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : bigDecimal.stripTrailingZeros();
            n2 = n;
            bigDecimal2 = bigDecimal;
            if (bigDecimal.precision() == 1) {
                n2 = n;
                bigDecimal2 = bigDecimal;
                if (bigDecimal.unscaledValue().equals(BigInteger.ONE)) {
                    n2 = n - bigDecimal.scale();
                    bigDecimal2 = null;
                }
            }
        }
        this.magnitude = n2;
        this.arbitrary = bigDecimal2;
        this.mc = mathContext;
        this.reciprocal = bigDecimal2 != null && BigDecimal.ZERO.compareTo(bigDecimal2) != 0 ? BigDecimal.ONE.divide(bigDecimal2, mathContext) : null;
    }

    public static Scale byBigDecimal(BigDecimal bigDecimal) {
        if (bigDecimal.compareTo(BigDecimal.ONE) == 0) {
            return DEFAULT;
        }
        if (bigDecimal.compareTo(BIG_DECIMAL_100) == 0) {
            return HUNDRED;
        }
        if (bigDecimal.compareTo(BIG_DECIMAL_1000) == 0) {
            return THOUSAND;
        }
        return new Scale(0, bigDecimal);
    }

    public static Scale byDouble(double d) {
        if (d == 1.0) {
            return DEFAULT;
        }
        if (d == 100.0) {
            return HUNDRED;
        }
        if (d == 1000.0) {
            return THOUSAND;
        }
        return new Scale(0, BigDecimal.valueOf(d));
    }

    public static Scale byDoubleAndPowerOfTen(double d, int n) {
        return new Scale(n, BigDecimal.valueOf(d));
    }

    public static Scale none() {
        return DEFAULT;
    }

    public static Scale powerOfTen(int n) {
        if (n == 0) {
            return DEFAULT;
        }
        if (n == 2) {
            return HUNDRED;
        }
        if (n == 3) {
            return THOUSAND;
        }
        return new Scale(n, null);
    }

    @Deprecated
    public void applyReciprocalTo(DecimalQuantity decimalQuantity) {
        decimalQuantity.adjustMagnitude(-this.magnitude);
        BigDecimal bigDecimal = this.reciprocal;
        if (bigDecimal != null) {
            decimalQuantity.multiplyBy(bigDecimal);
            decimalQuantity.roundToMagnitude(decimalQuantity.getMagnitude() - this.mc.getPrecision(), this.mc);
        }
    }

    @Deprecated
    public void applyTo(DecimalQuantity decimalQuantity) {
        decimalQuantity.adjustMagnitude(this.magnitude);
        BigDecimal bigDecimal = this.arbitrary;
        if (bigDecimal != null) {
            decimalQuantity.multiplyBy(bigDecimal);
        }
    }

    boolean isValid() {
        boolean bl = this.magnitude != 0 || this.arbitrary != null;
        return bl;
    }

    @Deprecated
    public Scale withMathContext(MathContext mathContext) {
        if (this.mc.equals(mathContext)) {
            return this;
        }
        return new Scale(this.magnitude, this.arbitrary, mathContext);
    }
}

