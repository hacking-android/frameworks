/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.Period;
import java.util.TimeZone;

public interface PeriodBuilder {
    public Period create(long var1);

    public Period createWithReferenceDate(long var1, long var3);

    public PeriodBuilder withLocale(String var1);

    public PeriodBuilder withTimeZone(TimeZone var1);
}

