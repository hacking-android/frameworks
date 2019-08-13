/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.BasicDurationFormatterFactory;
import android.icu.impl.duration.BasicPeriodBuilderFactory;
import android.icu.impl.duration.BasicPeriodFormatterFactory;
import android.icu.impl.duration.DurationFormatterFactory;
import android.icu.impl.duration.PeriodBuilderFactory;
import android.icu.impl.duration.PeriodFormatterFactory;
import android.icu.impl.duration.PeriodFormatterService;
import android.icu.impl.duration.impl.PeriodFormatterDataService;
import android.icu.impl.duration.impl.ResourceBasedPeriodFormatterDataService;
import java.util.Collection;

public class BasicPeriodFormatterService
implements PeriodFormatterService {
    private static BasicPeriodFormatterService instance;
    private PeriodFormatterDataService ds;

    public BasicPeriodFormatterService(PeriodFormatterDataService periodFormatterDataService) {
        this.ds = periodFormatterDataService;
    }

    public static BasicPeriodFormatterService getInstance() {
        if (instance == null) {
            instance = new BasicPeriodFormatterService(ResourceBasedPeriodFormatterDataService.getInstance());
        }
        return instance;
    }

    @Override
    public Collection<String> getAvailableLocaleNames() {
        return this.ds.getAvailableLocales();
    }

    @Override
    public DurationFormatterFactory newDurationFormatterFactory() {
        return new BasicDurationFormatterFactory(this);
    }

    @Override
    public PeriodBuilderFactory newPeriodBuilderFactory() {
        return new BasicPeriodBuilderFactory(this.ds);
    }

    @Override
    public PeriodFormatterFactory newPeriodFormatterFactory() {
        return new BasicPeriodFormatterFactory(this.ds);
    }
}

