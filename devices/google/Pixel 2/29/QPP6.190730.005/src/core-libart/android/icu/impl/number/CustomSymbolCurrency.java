/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.number;

import android.icu.text.DecimalFormatSymbols;
import android.icu.util.Currency;
import android.icu.util.MeasureUnit;
import android.icu.util.ULocale;
import java.io.Serializable;

public class CustomSymbolCurrency
extends Currency {
    private static final long serialVersionUID = 2497493016770137670L;
    private String symbol1;
    private String symbol2;

    public CustomSymbolCurrency(String string, String string2, String string3) {
        super(string);
        this.symbol1 = string2;
        this.symbol2 = string3;
    }

    public static Currency resolve(Currency object, ULocale serializable, DecimalFormatSymbols object2) {
        serializable = object;
        if (object == null) {
            serializable = ((DecimalFormatSymbols)object2).getCurrency();
        }
        if (serializable == null) {
            return Currency.getInstance("XXX");
        }
        if (!((MeasureUnit)serializable).equals(((DecimalFormatSymbols)object2).getCurrency())) {
            return serializable;
        }
        object = ((DecimalFormatSymbols)object2).getCurrencySymbol();
        String string = ((DecimalFormatSymbols)object2).getInternationalCurrencySymbol();
        String string2 = ((Currency)serializable).getName(((DecimalFormatSymbols)object2).getULocale(), 0, null);
        object2 = ((Currency)serializable).getCurrencyCode();
        if (string2.equals(object) && ((String)object2).equals(string)) {
            return serializable;
        }
        return new CustomSymbolCurrency((String)object2, (String)object, string);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = super.equals(object) && ((CustomSymbolCurrency)object).symbol1.equals(this.symbol1) && ((CustomSymbolCurrency)object).symbol2.equals(this.symbol2);
        return bl;
    }

    @Override
    public String getCurrencyCode() {
        return this.symbol2;
    }

    @Override
    public String getName(ULocale uLocale, int n, String string, boolean[] arrbl) {
        return super.getName(uLocale, n, string, arrbl);
    }

    @Override
    public String getName(ULocale uLocale, int n, boolean[] arrbl) {
        if (n == 0) {
            return this.symbol1;
        }
        return super.getName(uLocale, n, arrbl);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.symbol1.hashCode() ^ this.symbol2.hashCode();
    }
}

