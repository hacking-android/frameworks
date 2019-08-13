/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.number.DecimalFormatProperties;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.icu.util.CurrencyAmount;
import android.icu.util.ULocale;
import java.text.ParsePosition;
import java.util.Locale;

public class CompactDecimalFormat
extends DecimalFormat {
    private static final long serialVersionUID = 4716293295276629682L;

    CompactDecimalFormat(ULocale uLocale, CompactStyle compactStyle) {
        this.symbols = DecimalFormatSymbols.getInstance(uLocale);
        this.properties = new DecimalFormatProperties();
        this.properties.setCompactStyle(compactStyle);
        this.properties.setGroupingSize(-2);
        this.properties.setMinimumGroupingDigits(2);
        this.exportedProperties = new DecimalFormatProperties();
        this.refreshFormatter();
    }

    public static CompactDecimalFormat getInstance(ULocale uLocale, CompactStyle compactStyle) {
        return new CompactDecimalFormat(uLocale, compactStyle);
    }

    public static CompactDecimalFormat getInstance(Locale locale, CompactStyle compactStyle) {
        return new CompactDecimalFormat(ULocale.forLocale(locale), compactStyle);
    }

    @Override
    public Number parse(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CurrencyAmount parseCurrency(CharSequence charSequence, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    public static enum CompactStyle {
        SHORT,
        LONG;
        
    }

}

