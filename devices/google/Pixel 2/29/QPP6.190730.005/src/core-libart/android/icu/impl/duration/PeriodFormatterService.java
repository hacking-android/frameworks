/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.DurationFormatterFactory;
import android.icu.impl.duration.PeriodBuilderFactory;
import android.icu.impl.duration.PeriodFormatterFactory;
import java.util.Collection;

public interface PeriodFormatterService {
    public Collection<String> getAvailableLocaleNames();

    public DurationFormatterFactory newDurationFormatterFactory();

    public PeriodBuilderFactory newPeriodBuilderFactory();

    public PeriodFormatterFactory newPeriodFormatterFactory();
}

