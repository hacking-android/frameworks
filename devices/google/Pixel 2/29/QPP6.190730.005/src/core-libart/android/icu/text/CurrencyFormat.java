/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.MeasureFormat;
import android.icu.text.NumberFormat;
import android.icu.util.CurrencyAmount;
import android.icu.util.Measure;
import android.icu.util.ULocale;
import java.io.ObjectStreamException;
import java.text.FieldPosition;
import java.text.ParsePosition;

class CurrencyFormat
extends MeasureFormat {
    static final long serialVersionUID = -931679363692504634L;

    public CurrencyFormat(ULocale uLocale) {
        super(uLocale, MeasureFormat.FormatWidth.DEFAULT_CURRENCY);
    }

    private Object readResolve() throws ObjectStreamException {
        return new CurrencyFormat(this.getLocale(ULocale.ACTUAL_LOCALE));
    }

    private Object writeReplace() throws ObjectStreamException {
        return this.toCurrencyProxy();
    }

    @Override
    public StringBuffer format(Object object, StringBuffer abstractStringBuilder, FieldPosition fieldPosition) {
        if (object instanceof CurrencyAmount) {
            return super.format(object, (StringBuffer)abstractStringBuilder, fieldPosition);
        }
        abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append("Invalid type: ");
        ((StringBuilder)abstractStringBuilder).append(object.getClass().getName());
        throw new IllegalArgumentException(((StringBuilder)abstractStringBuilder).toString());
    }

    @Override
    public CurrencyAmount parseObject(String string, ParsePosition parsePosition) {
        return this.getNumberFormatInternal().parseCurrency(string, parsePosition);
    }
}

