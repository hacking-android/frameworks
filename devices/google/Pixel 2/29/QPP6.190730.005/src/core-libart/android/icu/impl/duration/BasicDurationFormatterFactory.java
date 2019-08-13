/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.BasicDurationFormatter;
import android.icu.impl.duration.BasicPeriodFormatterService;
import android.icu.impl.duration.DateFormatter;
import android.icu.impl.duration.DurationFormatter;
import android.icu.impl.duration.DurationFormatterFactory;
import android.icu.impl.duration.PeriodBuilder;
import android.icu.impl.duration.PeriodBuilderFactory;
import android.icu.impl.duration.PeriodFormatter;
import android.icu.impl.duration.PeriodFormatterFactory;
import java.util.Locale;
import java.util.TimeZone;

class BasicDurationFormatterFactory
implements DurationFormatterFactory {
    private PeriodBuilder builder;
    private BasicDurationFormatter f;
    private DateFormatter fallback;
    private long fallbackLimit;
    private PeriodFormatter formatter;
    private String localeName;
    private BasicPeriodFormatterService ps;
    private TimeZone timeZone;

    BasicDurationFormatterFactory(BasicPeriodFormatterService basicPeriodFormatterService) {
        this.ps = basicPeriodFormatterService;
        this.localeName = Locale.getDefault().toString();
        this.timeZone = TimeZone.getDefault();
    }

    protected BasicDurationFormatter createFormatter() {
        return new BasicDurationFormatter(this.formatter, this.builder, this.fallback, this.fallbackLimit, this.localeName, this.timeZone);
    }

    public DateFormatter getFallback() {
        return this.fallback;
    }

    public long getFallbackLimit() {
        long l = this.fallback == null ? 0L : this.fallbackLimit;
        return l;
    }

    @Override
    public DurationFormatter getFormatter() {
        if (this.f == null) {
            DateFormatter dateFormatter = this.fallback;
            if (dateFormatter != null) {
                this.fallback = dateFormatter.withLocale(this.localeName).withTimeZone(this.timeZone);
            }
            this.formatter = this.getPeriodFormatter();
            this.builder = this.getPeriodBuilder();
            this.f = this.createFormatter();
        }
        return this.f;
    }

    public String getLocaleName() {
        return this.localeName;
    }

    public PeriodBuilder getPeriodBuilder() {
        if (this.builder == null) {
            this.builder = this.ps.newPeriodBuilderFactory().setLocale(this.localeName).setTimeZone(this.timeZone).getSingleUnitBuilder();
        }
        return this.builder;
    }

    public PeriodFormatter getPeriodFormatter() {
        if (this.formatter == null) {
            this.formatter = this.ps.newPeriodFormatterFactory().setLocale(this.localeName).getFormatter();
        }
        return this.formatter;
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    protected void reset() {
        this.f = null;
    }

    @Override
    public DurationFormatterFactory setFallback(DateFormatter dateFormatter) {
        boolean bl = true;
        if (dateFormatter == null) {
            if (this.fallback == null) {
                bl = false;
            }
        } else if (dateFormatter.equals(this.fallback)) {
            bl = false;
        }
        if (bl) {
            this.fallback = dateFormatter;
            this.reset();
        }
        return this;
    }

    @Override
    public DurationFormatterFactory setFallbackLimit(long l) {
        long l2 = l;
        if (l < 0L) {
            l2 = 0L;
        }
        if (l2 != this.fallbackLimit) {
            this.fallbackLimit = l2;
            this.reset();
        }
        return this;
    }

    @Override
    public DurationFormatterFactory setLocale(String string) {
        if (!string.equals(this.localeName)) {
            this.localeName = string;
            Object object = this.builder;
            if (object != null) {
                this.builder = object.withLocale(string);
            }
            if ((object = this.formatter) != null) {
                this.formatter = object.withLocale(string);
            }
            this.reset();
        }
        return this;
    }

    @Override
    public DurationFormatterFactory setPeriodBuilder(PeriodBuilder periodBuilder) {
        if (periodBuilder != this.builder) {
            this.builder = periodBuilder;
            this.reset();
        }
        return this;
    }

    @Override
    public DurationFormatterFactory setPeriodFormatter(PeriodFormatter periodFormatter) {
        if (periodFormatter != this.formatter) {
            this.formatter = periodFormatter;
            this.reset();
        }
        return this;
    }

    @Override
    public DurationFormatterFactory setTimeZone(TimeZone timeZone) {
        if (!timeZone.equals(this.timeZone)) {
            this.timeZone = timeZone;
            PeriodBuilder periodBuilder = this.builder;
            if (periodBuilder != null) {
                this.builder = periodBuilder.withTimeZone(timeZone);
            }
            this.reset();
        }
        return this;
    }
}

