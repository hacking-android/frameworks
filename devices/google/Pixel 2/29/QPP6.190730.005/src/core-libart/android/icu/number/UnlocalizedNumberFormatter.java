/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.number.LocalizedNumberFormatter;
import android.icu.number.NumberFormatterSettings;
import android.icu.util.ULocale;
import java.util.Locale;

public class UnlocalizedNumberFormatter
extends NumberFormatterSettings<UnlocalizedNumberFormatter> {
    UnlocalizedNumberFormatter() {
        super(null, 14, new Long(3L));
    }

    UnlocalizedNumberFormatter(NumberFormatterSettings<?> numberFormatterSettings, int n, Object object) {
        super(numberFormatterSettings, n, object);
    }

    @Override
    UnlocalizedNumberFormatter create(int n, Object object) {
        return new UnlocalizedNumberFormatter(this, n, object);
    }

    public LocalizedNumberFormatter locale(ULocale uLocale) {
        return new LocalizedNumberFormatter(this, 1, uLocale);
    }

    public LocalizedNumberFormatter locale(Locale locale) {
        return new LocalizedNumberFormatter(this, 1, ULocale.forLocale(locale));
    }
}

