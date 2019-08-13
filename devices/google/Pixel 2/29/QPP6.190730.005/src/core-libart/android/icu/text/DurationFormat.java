/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.duration.BasicDurationFormat;
import android.icu.text.UFormat;
import android.icu.util.ULocale;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

@Deprecated
public abstract class DurationFormat
extends UFormat {
    private static final long serialVersionUID = -2076961954727774282L;

    @Deprecated
    protected DurationFormat() {
    }

    @Deprecated
    protected DurationFormat(ULocale uLocale) {
        this.setLocale(uLocale, uLocale);
    }

    @Deprecated
    public static DurationFormat getInstance(ULocale uLocale) {
        return BasicDurationFormat.getInstance(uLocale);
    }

    @Deprecated
    @Override
    public abstract StringBuffer format(Object var1, StringBuffer var2, FieldPosition var3);

    @Deprecated
    public abstract String formatDurationFrom(long var1, long var3);

    @Deprecated
    public abstract String formatDurationFromNow(long var1);

    @Deprecated
    public abstract String formatDurationFromNowTo(Date var1);

    @Deprecated
    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }
}

