/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.number.LocalizedNumberRangeFormatter;
import android.icu.number.NumberRangeFormatterSettings;
import android.icu.util.ULocale;
import java.util.Locale;

public class UnlocalizedNumberRangeFormatter
extends NumberRangeFormatterSettings<UnlocalizedNumberRangeFormatter> {
    UnlocalizedNumberRangeFormatter() {
        super(null, 0, null);
    }

    UnlocalizedNumberRangeFormatter(NumberRangeFormatterSettings<?> numberRangeFormatterSettings, int n, Object object) {
        super(numberRangeFormatterSettings, n, object);
    }

    @Override
    UnlocalizedNumberRangeFormatter create(int n, Object object) {
        return new UnlocalizedNumberRangeFormatter(this, n, object);
    }

    public LocalizedNumberRangeFormatter locale(ULocale uLocale) {
        return new LocalizedNumberRangeFormatter(this, 1, uLocale);
    }

    public LocalizedNumberRangeFormatter locale(Locale locale) {
        return new LocalizedNumberRangeFormatter(this, 1, ULocale.forLocale(locale));
    }
}

