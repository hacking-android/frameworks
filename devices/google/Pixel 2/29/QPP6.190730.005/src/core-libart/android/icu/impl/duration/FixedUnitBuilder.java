/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.BasicPeriodBuilderFactory;
import android.icu.impl.duration.Period;
import android.icu.impl.duration.PeriodBuilder;
import android.icu.impl.duration.PeriodBuilderImpl;
import android.icu.impl.duration.TimeUnit;

class FixedUnitBuilder
extends PeriodBuilderImpl {
    private TimeUnit unit;

    FixedUnitBuilder(TimeUnit timeUnit, BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
        this.unit = timeUnit;
    }

    public static FixedUnitBuilder get(TimeUnit timeUnit, BasicPeriodBuilderFactory.Settings settings) {
        if (settings != null && (settings.effectiveSet() & 1 << timeUnit.ordinal) != 0) {
            return new FixedUnitBuilder(timeUnit, settings);
        }
        return null;
    }

    @Override
    protected Period handleCreate(long l, long l2, boolean bl) {
        TimeUnit timeUnit = this.unit;
        if (timeUnit == null) {
            return null;
        }
        l2 = this.approximateDurationOf(timeUnit);
        return Period.at((float)((double)l / (double)l2), this.unit).inPast(bl);
    }

    @Override
    protected PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings settings) {
        return FixedUnitBuilder.get(this.unit, settings);
    }
}

