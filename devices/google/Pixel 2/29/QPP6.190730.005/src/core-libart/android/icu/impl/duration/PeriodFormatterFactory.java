/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.PeriodFormatter;

public interface PeriodFormatterFactory {
    public PeriodFormatter getFormatter();

    public PeriodFormatterFactory setCountVariant(int var1);

    public PeriodFormatterFactory setDisplayLimit(boolean var1);

    public PeriodFormatterFactory setDisplayPastFuture(boolean var1);

    public PeriodFormatterFactory setLocale(String var1);

    public PeriodFormatterFactory setSeparatorVariant(int var1);

    public PeriodFormatterFactory setUnitVariant(int var1);
}

