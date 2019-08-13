/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.BasicPeriodBuilderFactory;
import android.icu.impl.duration.Period;
import android.icu.impl.duration.PeriodBuilder;
import android.icu.impl.duration.PeriodBuilderImpl;
import android.icu.impl.duration.TimeUnit;

class OneOrTwoUnitBuilder
extends PeriodBuilderImpl {
    OneOrTwoUnitBuilder(BasicPeriodBuilderFactory.Settings settings) {
        super(settings);
    }

    public static OneOrTwoUnitBuilder get(BasicPeriodBuilderFactory.Settings settings) {
        if (settings == null) {
            return null;
        }
        return new OneOrTwoUnitBuilder(settings);
    }

    @Override
    protected Period handleCreate(long l, long l2, boolean bl) {
        Period period;
        Period period2 = null;
        short s = this.settings.effectiveSet();
        int n = 0;
        l2 = l;
        do {
            block7 : {
                TimeUnit timeUnit;
                long l3;
                block8 : {
                    period = period2;
                    if (n >= TimeUnit.units.length) break;
                    period = period2;
                    l = l2;
                    if ((1 << n & s) == 0) break block7;
                    timeUnit = TimeUnit.units[n];
                    l3 = this.approximateDurationOf(timeUnit);
                    if (l2 >= l3) break block8;
                    period = period2;
                    l = l2;
                    if (period2 == null) break block7;
                }
                double d = (double)l2 / (double)l3;
                if (period2 == null) {
                    if (d >= 2.0) {
                        period = Period.at((float)d, timeUnit);
                        break;
                    }
                    period = Period.at(1.0f, timeUnit).inPast(bl);
                    l = l2 - l3;
                } else {
                    period = period2;
                    if (!(d >= 1.0)) break;
                    period = period2.and((float)d, timeUnit);
                    break;
                }
            }
            ++n;
            period2 = period;
            l2 = l;
        } while (true);
        return period;
    }

    @Override
    protected PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings settings) {
        return OneOrTwoUnitBuilder.get(settings);
    }
}

