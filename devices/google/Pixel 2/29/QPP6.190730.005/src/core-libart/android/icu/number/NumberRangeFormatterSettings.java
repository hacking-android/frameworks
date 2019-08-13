/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.impl.number.MacroProps;
import android.icu.impl.number.range.RangeMacroProps;
import android.icu.number.NumberRangeFormatter;
import android.icu.number.UnlocalizedNumberFormatter;
import android.icu.util.ULocale;

public abstract class NumberRangeFormatterSettings<T extends NumberRangeFormatterSettings<?>> {
    static final int KEY_COLLAPSE = 5;
    static final int KEY_FORMATTER_1 = 2;
    static final int KEY_FORMATTER_2 = 3;
    static final int KEY_IDENTITY_FALLBACK = 6;
    static final int KEY_LOCALE = 1;
    static final int KEY_MACROS = 0;
    static final int KEY_MAX = 7;
    static final int KEY_SAME_FORMATTERS = 4;
    private final int key;
    private final NumberRangeFormatterSettings<?> parent;
    private volatile RangeMacroProps resolvedMacros;
    private final Object value;

    NumberRangeFormatterSettings(NumberRangeFormatterSettings<?> numberRangeFormatterSettings, int n, Object object) {
        this.parent = numberRangeFormatterSettings;
        this.key = n;
        this.value = object;
    }

    public T collapse(NumberRangeFormatter.RangeCollapse rangeCollapse) {
        return this.create(5, (Object)rangeCollapse);
    }

    abstract T create(int var1, Object var2);

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof NumberRangeFormatterSettings)) {
            return false;
        }
        return this.resolve().equals(((NumberRangeFormatterSettings)object).resolve());
    }

    public int hashCode() {
        return this.resolve().hashCode();
    }

    public T identityFallback(NumberRangeFormatter.RangeIdentityFallback rangeIdentityFallback) {
        return this.create(6, (Object)((Object)rangeIdentityFallback));
    }

    public T numberFormatterBoth(UnlocalizedNumberFormatter unlocalizedNumberFormatter) {
        return ((NumberRangeFormatterSettings)this.create(4, true)).create(2, unlocalizedNumberFormatter);
    }

    public T numberFormatterFirst(UnlocalizedNumberFormatter unlocalizedNumberFormatter) {
        return ((NumberRangeFormatterSettings)this.create(4, false)).create(2, unlocalizedNumberFormatter);
    }

    public T numberFormatterSecond(UnlocalizedNumberFormatter unlocalizedNumberFormatter) {
        return ((NumberRangeFormatterSettings)this.create(4, false)).create(3, unlocalizedNumberFormatter);
    }

    RangeMacroProps resolve() {
        if (this.resolvedMacros != null) {
            return this.resolvedMacros;
        }
        Object object = new RangeMacroProps();
        NumberRangeFormatterSettings<?> numberRangeFormatterSettings = this;
        while (numberRangeFormatterSettings != null) {
            switch (numberRangeFormatterSettings.key) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown key: ");
                    ((StringBuilder)object).append(numberRangeFormatterSettings.key);
                    throw new AssertionError((Object)((StringBuilder)object).toString());
                }
                case 6: {
                    if (((RangeMacroProps)object).identityFallback != null) break;
                    ((RangeMacroProps)object).identityFallback = (NumberRangeFormatter.RangeIdentityFallback)((Object)numberRangeFormatterSettings.value);
                    break;
                }
                case 5: {
                    if (((RangeMacroProps)object).collapse != null) break;
                    ((RangeMacroProps)object).collapse = (NumberRangeFormatter.RangeCollapse)((Object)numberRangeFormatterSettings.value);
                    break;
                }
                case 4: {
                    if (((RangeMacroProps)object).sameFormatters != -1) break;
                    ((RangeMacroProps)object).sameFormatters = ((Boolean)numberRangeFormatterSettings.value).booleanValue() ? 1 : 0;
                    break;
                }
                case 3: {
                    if (((RangeMacroProps)object).formatter2 != null) break;
                    ((RangeMacroProps)object).formatter2 = (UnlocalizedNumberFormatter)numberRangeFormatterSettings.value;
                    break;
                }
                case 2: {
                    if (((RangeMacroProps)object).formatter1 != null) break;
                    ((RangeMacroProps)object).formatter1 = (UnlocalizedNumberFormatter)numberRangeFormatterSettings.value;
                    break;
                }
                case 1: {
                    if (((RangeMacroProps)object).loc != null) break;
                    ((RangeMacroProps)object).loc = (ULocale)numberRangeFormatterSettings.value;
                }
                case 0: 
            }
            numberRangeFormatterSettings = numberRangeFormatterSettings.parent;
        }
        if (((RangeMacroProps)object).formatter1 != null) {
            object.formatter1.resolve().loc = ((RangeMacroProps)object).loc;
        }
        if (((RangeMacroProps)object).formatter2 != null) {
            object.formatter2.resolve().loc = ((RangeMacroProps)object).loc;
        }
        this.resolvedMacros = object;
        return object;
    }
}

