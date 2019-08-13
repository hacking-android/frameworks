/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.BasicPeriodBuilderFactory;
import android.icu.impl.duration.Period;
import android.icu.impl.duration.PeriodBuilder;
import android.icu.impl.duration.TimeUnit;
import java.util.TimeZone;

abstract class PeriodBuilderImpl
implements PeriodBuilder {
    protected BasicPeriodBuilderFactory.Settings settings;

    protected PeriodBuilderImpl(BasicPeriodBuilderFactory.Settings settings) {
        this.settings = settings;
    }

    public long approximateDurationOf(TimeUnit timeUnit) {
        return BasicPeriodBuilderFactory.approximateDurationOf(timeUnit);
    }

    @Override
    public Period create(long l) {
        return this.createWithReferenceDate(l, System.currentTimeMillis());
    }

    @Override
    public Period createWithReferenceDate(long l, long l2) {
        Period period;
        boolean bl = l < 0L;
        long l3 = l;
        if (bl) {
            l3 = -l;
        }
        Period period2 = period = this.settings.createLimited(l3, bl);
        if (period == null) {
            period2 = period = this.handleCreate(l3, l2, bl);
            if (period == null) {
                period2 = Period.lessThan(1.0f, this.settings.effectiveMinUnit()).inPast(bl);
            }
        }
        return period2;
    }

    protected abstract Period handleCreate(long var1, long var3, boolean var5);

    @Override
    public PeriodBuilder withLocale(String object) {
        if ((object = this.settings.setLocale((String)object)) != this.settings) {
            return this.withSettings((BasicPeriodBuilderFactory.Settings)object);
        }
        return this;
    }

    protected abstract PeriodBuilder withSettings(BasicPeriodBuilderFactory.Settings var1);

    @Override
    public PeriodBuilder withTimeZone(TimeZone timeZone) {
        return this;
    }
}

