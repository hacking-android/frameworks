/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.Currency;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;

public class CurrencyAmount
extends Measure {
    public CurrencyAmount(double d, Currency currency) {
        super(new Double(d), currency);
    }

    public CurrencyAmount(double d, java.util.Currency currency) {
        this(d, Currency.fromJavaCurrency(currency));
    }

    public CurrencyAmount(Number number, Currency currency) {
        super(number, currency);
    }

    public CurrencyAmount(Number number, java.util.Currency currency) {
        this(number, Currency.fromJavaCurrency(currency));
    }

    public Currency getCurrency() {
        return (Currency)this.getUnit();
    }
}

