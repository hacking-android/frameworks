/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.Period;

public interface PeriodFormatter {
    public String format(Period var1);

    public PeriodFormatter withLocale(String var1);
}

