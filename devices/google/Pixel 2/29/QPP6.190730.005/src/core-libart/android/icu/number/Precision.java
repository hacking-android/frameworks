/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.number.DecimalQuantity;
import android.icu.impl.number.MultiplierProducer;
import android.icu.impl.number.RoundingUtils;
import android.icu.number.CurrencyPrecision;
import android.icu.number.FractionPrecision;
import android.icu.util.Currency;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public abstract class Precision
implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final FracSigRounderImpl COMPACT_STRATEGY;
    static final FractionRounderImpl DEFAULT_MAX_FRAC_6;
    static final FractionRounderImpl FIXED_FRAC_0;
    static final FractionRounderImpl FIXED_FRAC_2;
    static final SignificantRounderImpl FIXED_SIG_2;
    static final SignificantRounderImpl FIXED_SIG_3;
    static final CurrencyRounderImpl MONETARY_CASH;
    static final CurrencyRounderImpl MONETARY_STANDARD;
    static final IncrementRounderImpl NICKEL;
    static final InfiniteRounderImpl NONE;
    static final PassThroughRounderImpl PASS_THROUGH;
    static final SignificantRounderImpl RANGE_SIG_2_3;
    MathContext mathContext = RoundingUtils.DEFAULT_MATH_CONTEXT_UNLIMITED;

    static {
        NONE = new InfiniteRounderImpl();
        FIXED_FRAC_0 = new FractionRounderImpl(0, 0);
        FIXED_FRAC_2 = new FractionRounderImpl(2, 2);
        DEFAULT_MAX_FRAC_6 = new FractionRounderImpl(0, 6);
        FIXED_SIG_2 = new SignificantRounderImpl(2, 2);
        FIXED_SIG_3 = new SignificantRounderImpl(3, 3);
        RANGE_SIG_2_3 = new SignificantRounderImpl(2, 3);
        COMPACT_STRATEGY = new FracSigRounderImpl(0, 0, 2, -1);
        NICKEL = new IncrementRounderImpl(BigDecimal.valueOf(0.05));
        MONETARY_STANDARD = new CurrencyRounderImpl(Currency.CurrencyUsage.STANDARD);
        MONETARY_CASH = new CurrencyRounderImpl(Currency.CurrencyUsage.CASH);
        PASS_THROUGH = new PassThroughRounderImpl();
    }

    Precision() {
    }

    static CurrencyPrecision constructCurrency(Currency.CurrencyUsage currencyUsage) {
        if (currencyUsage == Currency.CurrencyUsage.STANDARD) {
            return MONETARY_STANDARD;
        }
        if (currencyUsage == Currency.CurrencyUsage.CASH) {
            return MONETARY_CASH;
        }
        throw new AssertionError();
    }

    static FractionPrecision constructFraction(int n, int n2) {
        if (n == 0 && n2 == 0) {
            return FIXED_FRAC_0;
        }
        if (n == 2 && n2 == 2) {
            return FIXED_FRAC_2;
        }
        if (n == 0 && n2 == 6) {
            return DEFAULT_MAX_FRAC_6;
        }
        return new FractionRounderImpl(n, n2);
    }

    static Precision constructFractionSignificant(FractionPrecision fractionPrecision, int n, int n2) {
        fractionPrecision = (FractionRounderImpl)fractionPrecision;
        if (((FractionRounderImpl)fractionPrecision).minFrac == 0 && ((FractionRounderImpl)fractionPrecision).maxFrac == 0 && n == 2) {
            return COMPACT_STRATEGY;
        }
        return new FracSigRounderImpl(((FractionRounderImpl)fractionPrecision).minFrac, ((FractionRounderImpl)fractionPrecision).maxFrac, n, n2);
    }

    static Precision constructFromCurrency(CurrencyPrecision currencyPrecision, Currency currency) {
        currencyPrecision = (CurrencyRounderImpl)currencyPrecision;
        double d = currency.getRoundingIncrement(((CurrencyRounderImpl)currencyPrecision).usage);
        if (d != 0.0) {
            return Precision.constructIncrement(BigDecimal.valueOf(d));
        }
        int n = currency.getDefaultFractionDigits(((CurrencyRounderImpl)currencyPrecision).usage);
        return Precision.constructFraction(n, n);
    }

    static Precision constructIncrement(BigDecimal bigDecimal) {
        if (bigDecimal.equals(Precision.NICKEL.increment)) {
            return NICKEL;
        }
        return new IncrementRounderImpl(bigDecimal);
    }

    static Precision constructInfinite() {
        return NONE;
    }

    static Precision constructPassThrough() {
        return PASS_THROUGH;
    }

    static Precision constructSignificant(int n, int n2) {
        if (n == 2 && n2 == 2) {
            return FIXED_SIG_2;
        }
        if (n == 3 && n2 == 3) {
            return FIXED_SIG_3;
        }
        if (n == 2 && n2 == 3) {
            return RANGE_SIG_2_3;
        }
        return new SignificantRounderImpl(n, n2);
    }

    public static CurrencyPrecision currency(Currency.CurrencyUsage currencyUsage) {
        if (currencyUsage != null) {
            return Precision.constructCurrency(currencyUsage);
        }
        throw new IllegalArgumentException("CurrencyUsage must be non-null");
    }

    @Deprecated
    public static Precision fixedDigits(int n) {
        return Precision.fixedSignificantDigits(n);
    }

    public static FractionPrecision fixedFraction(int n) {
        if (n >= 0 && n <= 999) {
            return Precision.constructFraction(n, n);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }

    public static Precision fixedSignificantDigits(int n) {
        if (n >= 1 && n <= 999) {
            return Precision.constructSignificant(n, n);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    private static int getDisplayMagnitudeFraction(int n) {
        if (n == 0) {
            return Integer.MAX_VALUE;
        }
        return -n;
    }

    private static int getDisplayMagnitudeSignificant(DecimalQuantity decimalQuantity, int n) {
        int n2 = decimalQuantity.isZero() ? 0 : decimalQuantity.getMagnitude();
        return n2 - n + 1;
    }

    private static int getRoundingMagnitudeFraction(int n) {
        if (n == -1) {
            return Integer.MIN_VALUE;
        }
        return -n;
    }

    private static int getRoundingMagnitudeSignificant(DecimalQuantity decimalQuantity, int n) {
        if (n == -1) {
            return Integer.MIN_VALUE;
        }
        int n2 = decimalQuantity.isZero() ? 0 : decimalQuantity.getMagnitude();
        return n2 - n + 1;
    }

    public static Precision increment(BigDecimal bigDecimal) {
        if (bigDecimal != null && bigDecimal.compareTo(BigDecimal.ZERO) > 0) {
            return Precision.constructIncrement(bigDecimal);
        }
        throw new IllegalArgumentException("Rounding increment must be positive and non-null");
    }

    public static FractionPrecision integer() {
        return Precision.constructFraction(0, 0);
    }

    @Deprecated
    public static Precision maxDigits(int n) {
        return Precision.maxSignificantDigits(n);
    }

    public static FractionPrecision maxFraction(int n) {
        if (n >= 0 && n <= 999) {
            return Precision.constructFraction(0, n);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }

    public static Precision maxSignificantDigits(int n) {
        if (n >= 1 && n <= 999) {
            return Precision.constructSignificant(1, n);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    @Deprecated
    public static Precision minDigits(int n) {
        return Precision.minSignificantDigits(n);
    }

    public static FractionPrecision minFraction(int n) {
        if (n >= 0 && n <= 999) {
            return Precision.constructFraction(n, -1);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }

    @Deprecated
    public static Precision minMaxDigits(int n, int n2) {
        return Precision.minMaxSignificantDigits(n, n2);
    }

    public static FractionPrecision minMaxFraction(int n, int n2) {
        if (n >= 0 && n2 <= 999 && n <= n2) {
            return Precision.constructFraction(n, n2);
        }
        throw new IllegalArgumentException("Fraction length must be between 0 and 999 (inclusive)");
    }

    public static Precision minMaxSignificantDigits(int n, int n2) {
        if (n >= 1 && n2 <= 999 && n <= n2) {
            return Precision.constructSignificant(n, n2);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public static Precision minSignificantDigits(int n) {
        if (n >= 1 && n <= 999) {
            return Precision.constructSignificant(n, -1);
        }
        throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
    }

    public static Precision unlimited() {
        return Precision.constructInfinite();
    }

    @Deprecated
    public abstract void apply(DecimalQuantity var1);

    int chooseMultiplierAndApply(DecimalQuantity decimalQuantity, MultiplierProducer multiplierProducer) {
        int n = decimalQuantity.getMagnitude();
        int n2 = multiplierProducer.getMultiplier(n);
        decimalQuantity.adjustMagnitude(n2);
        this.apply(decimalQuantity);
        if (decimalQuantity.isZero()) {
            return n2;
        }
        if (decimalQuantity.getMagnitude() == n + n2) {
            return n2;
        }
        if (n2 == (n = multiplierProducer.getMultiplier(n + 1))) {
            return n2;
        }
        decimalQuantity.adjustMagnitude(n - n2);
        this.apply(decimalQuantity);
        return n;
    }

    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

    Precision withLocaleData(Currency currency) {
        if (this instanceof CurrencyPrecision) {
            return ((CurrencyPrecision)this).withCurrency(currency);
        }
        return this;
    }

    @Deprecated
    public Precision withMode(MathContext mathContext) {
        if (this.mathContext.equals(mathContext)) {
            return this;
        }
        Precision precision = (Precision)this.clone();
        precision.mathContext = mathContext;
        return precision;
    }

    @Deprecated
    public Precision withMode(RoundingMode roundingMode) {
        return this.withMode(RoundingUtils.mathContextUnlimited(roundingMode));
    }

    static class CurrencyRounderImpl
    extends CurrencyPrecision {
        final Currency.CurrencyUsage usage;

        public CurrencyRounderImpl(Currency.CurrencyUsage currencyUsage) {
            this.usage = currencyUsage;
        }

        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            throw new AssertionError();
        }
    }

    static class FracSigRounderImpl
    extends Precision {
        final int maxFrac;
        final int maxSig;
        final int minFrac;
        final int minSig;

        public FracSigRounderImpl(int n, int n2, int n3, int n4) {
            this.minFrac = n;
            this.maxFrac = n2;
            this.minSig = n3;
            this.maxSig = n4;
        }

        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            int n = Precision.getDisplayMagnitudeFraction(this.minFrac);
            int n2 = Precision.getRoundingMagnitudeFraction(this.maxFrac);
            int n3 = this.minSig;
            n3 = n3 == -1 ? Math.max(n2, Precision.getRoundingMagnitudeSignificant(decimalQuantity, this.maxSig)) : Math.min(n2, Precision.getDisplayMagnitudeSignificant(decimalQuantity, n3));
            decimalQuantity.roundToMagnitude(n3, this.mathContext);
            decimalQuantity.setFractionLength(Math.max(0, -n), Integer.MAX_VALUE);
        }
    }

    static class FractionRounderImpl
    extends FractionPrecision {
        final int maxFrac;
        final int minFrac;

        public FractionRounderImpl(int n, int n2) {
            this.minFrac = n;
            this.maxFrac = n2;
        }

        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToMagnitude(Precision.getRoundingMagnitudeFraction(this.maxFrac), this.mathContext);
            decimalQuantity.setFractionLength(Math.max(0, -Precision.getDisplayMagnitudeFraction(this.minFrac)), Integer.MAX_VALUE);
        }
    }

    static class IncrementRounderImpl
    extends Precision {
        final BigDecimal increment;

        public IncrementRounderImpl(BigDecimal bigDecimal) {
            this.increment = bigDecimal;
        }

        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToIncrement(this.increment, this.mathContext);
            decimalQuantity.setFractionLength(this.increment.scale(), this.increment.scale());
        }
    }

    static class InfiniteRounderImpl
    extends Precision {
        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToInfinity();
            decimalQuantity.setFractionLength(0, Integer.MAX_VALUE);
        }
    }

    static class PassThroughRounderImpl
    extends Precision {
        @Override
        public void apply(DecimalQuantity decimalQuantity) {
        }
    }

    static class SignificantRounderImpl
    extends Precision {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final int maxSig;
        final int minSig;

        public SignificantRounderImpl(int n, int n2) {
            this.minSig = n;
            this.maxSig = n2;
        }

        @Override
        public void apply(DecimalQuantity decimalQuantity) {
            decimalQuantity.roundToMagnitude(Precision.getRoundingMagnitudeSignificant(decimalQuantity, this.maxSig), this.mathContext);
            decimalQuantity.setFractionLength(Math.max(0, -Precision.getDisplayMagnitudeSignificant(decimalQuantity, this.minSig)), Integer.MAX_VALUE);
            if (decimalQuantity.isZero() && this.minSig > 0) {
                decimalQuantity.setIntegerLength(1, Integer.MAX_VALUE);
            }
        }

        public void apply(DecimalQuantity decimalQuantity, int n) {
            decimalQuantity.setFractionLength(this.minSig - n, Integer.MAX_VALUE);
        }
    }

}

