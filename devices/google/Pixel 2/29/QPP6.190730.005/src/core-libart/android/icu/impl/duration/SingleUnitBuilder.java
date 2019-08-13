/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.BasicPeriodBuilderFactory;
import android.icu.impl.duration.Period;
import android.icu.impl.duration.PeriodBuilder;
import android.icu.impl.duration.PeriodBuilderImpl;
import android.icu.impl.duration.TimeUnit;

class SingleUnitBuilder
extends PeriodBuilderImpl {
    SingleUnitBuilder(BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
    }

    public static SingleUnitBuilder get(BasicPeriodBuilderFactory.Settings settings) {
        if (settings == null) {
            return null;
        }
        return new SingleUnitBuilder(settings);
    }

    @Override
    protected Period handleCreate(long l, long l2, boolean bl) {
        short s = this.settings.effectiveSet();
        for (int i = 0; i < TimeUnit.units.length; ++i) {
            TimeUnit timeUnit;
            if ((1 << i & s) == 0 || l < (l2 = this.approximateDurationOf(timeUnit = TimeUnit.units[i]))) continue;
            return Period.at((float)((double)l / (double)l2), timeUnit).inPast(bl);
        }
        return null;
    }

    @Override
    protected PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings settings) {
        return SingleUnitBuilder.get(settings);
    }
}

