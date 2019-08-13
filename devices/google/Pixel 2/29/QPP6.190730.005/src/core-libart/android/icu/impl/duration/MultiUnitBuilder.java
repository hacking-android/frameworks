/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.BasicPeriodBuilderFactory;
import android.icu.impl.duration.Period;
import android.icu.impl.duration.PeriodBuilder;
import android.icu.impl.duration.PeriodBuilderImpl;
import android.icu.impl.duration.TimeUnit;

class MultiUnitBuilder
extends PeriodBuilderImpl {
    private int nPeriods;

    MultiUnitBuilder(int n, BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
        this.nPeriods = n;
    }

    public static MultiUnitBuilder get(int n, BasicPeriodBuilderFactory.Settings settings) {
        if (n > 0 && settings != null) {
            return new MultiUnitBuilder(n, settings);
        }
        return null;
    }

    @Override
    protected Period handleCreate(long l, long l2, boolean bl) {
        Period period = null;
        int n = 0;
        short s = this.settings.effectiveSet();
        l2 = l;
        for (int i = 0; i < TimeUnit.units.length; ++i) {
            if ((1 << i & s) != 0) {
                TimeUnit timeUnit = TimeUnit.units[i];
                if (n == this.nPeriods) break;
                long l3 = this.approximateDurationOf(timeUnit);
                if (l2 < l3 && n <= 0) {
                    l = l2;
                } else {
                    double d = (double)l2 / (double)l3;
                    l = l2;
                    double d2 = d;
                    if (++n < this.nPeriods) {
                        d2 = Math.floor(d);
                        l = l2 - (long)((double)l3 * d2);
                    }
                    period = period == null ? Period.at((float)d2, timeUnit).inPast(bl) : period.and((float)d2, timeUnit);
                }
            } else {
                l = l2;
            }
            l2 = l;
        }
        return period;
    }

    @Override
    protected PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings settings) {
        return MultiUnitBuilder.get(this.nPeriods, settings);
    }
}

