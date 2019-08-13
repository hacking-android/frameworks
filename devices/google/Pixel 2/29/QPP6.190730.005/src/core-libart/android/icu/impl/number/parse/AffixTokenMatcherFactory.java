/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number.parse;

import android.icu.impl.number.parse.CombinedCurrencyMatcher;
import android.icu.impl.number.parse.IgnorablesMatcher;
import android.icu.impl.number.parse.MinusSignMatcher;
import android.icu.impl.number.parse.PercentMatcher;
import android.icu.impl.number.parse.PermilleMatcher;
import android.icu.impl.number.parse.PlusSignMatcher;
import android.icu.text.DecimalFormatSymbols;
import android.icu.util.Currency;
import android.icu.util.ULocale;

public class AffixTokenMatcherFactory {
    public Currency currency;
    public IgnorablesMatcher ignorables;
    public ULocale locale;
    public int parseFlags;
    public DecimalFormatSymbols symbols;

    public CombinedCurrencyMatcher currency() {
        return CombinedCurrencyMatcher.getInstance(this.currency, this.symbols, this.parseFlags);
    }

    public IgnorablesMatcher ignorables() {
        return this.ignorables;
    }

    public MinusSignMatcher minusSign() {
        return MinusSignMatcher.getInstance(this.symbols, true);
    }

    public PercentMatcher percent() {
        return PercentMatcher.getInstance(this.symbols);
    }

    public PermilleMatcher permille() {
        return PermilleMatcher.getInstance(this.symbols);
    }

    public PlusSignMatcher plusSign() {
        return PlusSignMatcher.getInstance(this.symbols, true);
    }
}

