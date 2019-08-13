/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.number.LocalizedNumberRangeFormatter;
import android.icu.number.UnlocalizedNumberRangeFormatter;
import android.icu.util.ULocale;
import java.util.Locale;

public abstract class NumberRangeFormatter {
    private static final UnlocalizedNumberRangeFormatter BASE = new UnlocalizedNumberRangeFormatter();

    private NumberRangeFormatter() {
    }

    public static UnlocalizedNumberRangeFormatter with() {
        return BASE;
    }

    public static LocalizedNumberRangeFormatter withLocale(ULocale uLocale) {
        return BASE.locale(uLocale);
    }

    public static LocalizedNumberRangeFormatter withLocale(Locale locale) {
        return BASE.locale(locale);
    }

    public static enum RangeCollapse {
        AUTO,
        NONE,
        UNIT,
        ALL;
        
    }

    public static enum RangeIdentityFallback {
        SINGLE_VALUE,
        APPROXIMATELY_OR_SINGLE_VALUE,
        APPROXIMATELY,
        RANGE;
        
    }

    public static enum RangeIdentityResult {
        EQUAL_BEFORE_ROUNDING,
        EQUAL_AFTER_ROUNDING,
        NOT_EQUAL;
        
    }

}

