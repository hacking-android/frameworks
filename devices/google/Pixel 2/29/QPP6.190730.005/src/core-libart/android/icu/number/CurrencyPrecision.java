/*
 * Decompiled with CFR 0.145.
 */
package android.icu.number;

import android.icu.number.Precision;
import android.icu.util.Currency;

public abstract class CurrencyPrecision
extends Precision {
    CurrencyPrecision() {
    }

    public Precision withCurrency(Currency currency) {
        if (currency != null) {
            return CurrencyPrecision.constructFromCurrency(this, currency);
        }
        throw new IllegalArgumentException("Currency must not be null");
    }
}

