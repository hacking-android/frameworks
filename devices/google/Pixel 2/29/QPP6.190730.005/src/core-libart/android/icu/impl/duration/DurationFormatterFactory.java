/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.DateFormatter;
import android.icu.impl.duration.DurationFormatter;
import android.icu.impl.duration.PeriodBuilder;
import android.icu.impl.duration.PeriodFormatter;
import java.util.TimeZone;

public interface DurationFormatterFactory {
    public DurationFormatter getFormatter();

    public DurationFormatterFactory setFallback(DateFormatter var1);

    public DurationFormatterFactory setFallbackLimit(long var1);

    public DurationFormatterFactory setLocale(String var1);

    public DurationFormatterFactory setPeriodBuilder(PeriodBuilder var1);

    public DurationFormatterFactory setPeriodFormatter(PeriodFormatter var1);

    public DurationFormatterFactory setTimeZone(TimeZone var1);
}

