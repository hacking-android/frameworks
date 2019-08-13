/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.number.MacroProps;
import android.icu.impl.number.Padder;
import android.icu.number.IntegerWidth;
import android.icu.number.Notation;
import android.icu.number.NumberFormatter;
import android.icu.number.NumberSkeletonImpl;
import android.icu.number.Precision;
import android.icu.number.Scale;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.NumberingSystem;
import android.icu.util.MeasureUnit;
import android.icu.util.ULocale;
import java.math.RoundingMode;

public abstract class NumberFormatterSettings<T extends NumberFormatterSettings<?>> {
    static final int KEY_DECIMAL = 12;
    static final int KEY_GROUPING = 6;
    static final int KEY_INTEGER = 8;
    static final int KEY_LOCALE = 1;
    static final int KEY_MACROS = 0;
    static final int KEY_MAX = 16;
    static final int KEY_NOTATION = 2;
    static final int KEY_PADDER = 7;
    static final int KEY_PER_UNIT = 15;
    static final int KEY_PRECISION = 4;
    static final int KEY_ROUNDING_MODE = 5;
    static final int KEY_SCALE = 13;
    static final int KEY_SIGN = 11;
    static final int KEY_SYMBOLS = 9;
    static final int KEY_THRESHOLD = 14;
    static final int KEY_UNIT = 3;
    static final int KEY_UNIT_WIDTH = 10;
    private final int key;
    private final NumberFormatterSettings<?> parent;
    private volatile MacroProps resolvedMacros;
    private final Object value;

    NumberFormatterSettings(NumberFormatterSettings<?> numberFormatterSettings, int n, Object object) {
        this.parent = numberFormatterSettings;
        this.key = n;
        this.value = object;
    }

    abstract T create(int var1, Object var2);

    public T decimal(NumberFormatter.DecimalSeparatorDisplay decimalSeparatorDisplay) {
        return this.create(12, (Object)((Object)decimalSeparatorDisplay));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof NumberFormatterSettings)) {
            return false;
        }
        return this.resolve().equals(((NumberFormatterSettings)object).resolve());
    }

    public T grouping(NumberFormatter.GroupingStrategy groupingStrategy) {
        return this.create(6, (Object)((Object)groupingStrategy));
    }

    public int hashCode() {
        return this.resolve().hashCode();
    }

    public T integerWidth(IntegerWidth integerWidth) {
        return this.create(8, integerWidth);
    }

    @Deprecated
    public T macros(MacroProps macroProps) {
        return this.create(0, macroProps);
    }

    public T notation(Notation notation) {
        return this.create(2, notation);
    }

    @Deprecated
    public T padding(Padder padder) {
        return this.create(7, padder);
    }

    public T perUnit(MeasureUnit measureUnit) {
        return this.create(15, measureUnit);
    }

    public T precision(Precision precision) {
        return this.create(4, precision);
    }

    MacroProps resolve() {
        if (this.resolvedMacros != null) {
            return this.resolvedMacros;
        }
        Object object = new MacroProps();
        NumberFormatterSettings<?> numberFormatterSettings = this;
        while (numberFormatterSettings != null) {
            switch (numberFormatterSettings.key) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown key: ");
                    ((StringBuilder)object).append(numberFormatterSettings.key);
                    throw new AssertionError((Object)((StringBuilder)object).toString());
                }
                case 15: {
                    if (((MacroProps)object).perUnit != null) break;
                    ((MacroProps)object).perUnit = (MeasureUnit)numberFormatterSettings.value;
                    break;
                }
                case 14: {
                    if (((MacroProps)object).threshold != null) break;
                    ((MacroProps)object).threshold = (Long)numberFormatterSettings.value;
                    break;
                }
                case 13: {
                    if (((MacroProps)object).scale != null) break;
                    ((MacroProps)object).scale = (Scale)numberFormatterSettings.value;
                    break;
                }
                case 12: {
                    if (((MacroProps)object).decimal != null) break;
                    ((MacroProps)object).decimal = (NumberFormatter.DecimalSeparatorDisplay)((Object)numberFormatterSettings.value);
                    break;
                }
                case 11: {
                    if (((MacroProps)object).sign != null) break;
                    ((MacroProps)object).sign = (NumberFormatter.SignDisplay)((Object)numberFormatterSettings.value);
                    break;
                }
                case 10: {
                    if (((MacroProps)object).unitWidth != null) break;
                    ((MacroProps)object).unitWidth = (NumberFormatter.UnitWidth)((Object)numberFormatterSettings.value);
                    break;
                }
                case 9: {
                    if (((MacroProps)object).symbols != null) break;
                    ((MacroProps)object).symbols = numberFormatterSettings.value;
                    break;
                }
                case 8: {
                    if (((MacroProps)object).integerWidth != null) break;
                    ((MacroProps)object).integerWidth = (IntegerWidth)numberFormatterSettings.value;
                    break;
                }
                case 7: {
                    if (((MacroProps)object).padder != null) break;
                    ((MacroProps)object).padder = (Padder)numberFormatterSettings.value;
                    break;
                }
                case 6: {
                    if (((MacroProps)object).grouping != null) break;
                    ((MacroProps)object).grouping = numberFormatterSettings.value;
                    break;
                }
                case 5: {
                    if (((MacroProps)object).roundingMode != null) break;
                    ((MacroProps)object).roundingMode = (RoundingMode)((Object)numberFormatterSettings.value);
                    break;
                }
                case 4: {
                    if (((MacroProps)object).precision != null) break;
                    ((MacroProps)object).precision = (Precision)numberFormatterSettings.value;
                    break;
                }
                case 3: {
                    if (((MacroProps)object).unit != null) break;
                    ((MacroProps)object).unit = (MeasureUnit)numberFormatterSettings.value;
                    break;
                }
                case 2: {
                    if (((MacroProps)object).notation != null) break;
                    ((MacroProps)object).notation = (Notation)numberFormatterSettings.value;
                    break;
                }
                case 1: {
                    if (((MacroProps)object).loc != null) break;
                    ((MacroProps)object).loc = (ULocale)numberFormatterSettings.value;
                    break;
                }
                case 0: {
                    ((MacroProps)object).fallback((MacroProps)numberFormatterSettings.value);
                }
            }
            numberFormatterSettings = numberFormatterSettings.parent;
        }
        this.resolvedMacros = object;
        return object;
    }

    @Deprecated
    public T rounding(Precision precision) {
        return this.precision(precision);
    }

    public T roundingMode(RoundingMode roundingMode) {
        return this.create(5, (Object)((Object)roundingMode));
    }

    public T scale(Scale scale) {
        return this.create(13, scale);
    }

    public T sign(NumberFormatter.SignDisplay signDisplay) {
        return this.create(11, (Object)((Object)signDisplay));
    }

    public T symbols(DecimalFormatSymbols decimalFormatSymbols) {
        return this.create(9, (DecimalFormatSymbols)decimalFormatSymbols.clone());
    }

    public T symbols(NumberingSystem numberingSystem) {
        return this.create(9, numberingSystem);
    }

    @Deprecated
    public T threshold(Long l) {
        return this.create(14, l);
    }

    public String toSkeleton() {
        return NumberSkeletonImpl.generate(this.resolve());
    }

    public T unit(MeasureUnit measureUnit) {
        return this.create(3, measureUnit);
    }

    public T unitWidth(NumberFormatter.UnitWidth unitWidth) {
        return this.create(10, (Object)((Object)unitWidth));
    }
}

