/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.DateFormatter;
import android.icu.impl.duration.DurationFormatter;
import android.icu.impl.duration.Period;
import android.icu.impl.duration.PeriodBuilder;
import android.icu.impl.duration.PeriodFormatter;
import java.util.Date;
import java.util.TimeZone;

class BasicDurationFormatter
implements DurationFormatter {
    private PeriodBuilder builder;
    private DateFormatter fallback;
    private long fallbackLimit;
    private PeriodFormatter formatter;
    private String localeName;
    private TimeZone timeZone;

    public BasicDurationFormatter(PeriodFormatter periodFormatter, PeriodBuilder periodBuilder, DateFormatter dateFormatter, long l) {
        this.formatter = periodFormatter;
        this.builder = periodBuilder;
        this.fallback = dateFormatter;
        long l2 = 0L;
        if (l < 0L) {
            l = l2;
        }
        this.fallbackLimit = l;
    }

    protected BasicDurationFormatter(PeriodFormatter periodFormatter, PeriodBuilder periodBuilder, DateFormatter dateFormatter, long l, String string, TimeZone timeZone) {
        this.formatter = periodFormatter;
        this.builder = periodBuilder;
        this.fallback = dateFormatter;
        this.fallbackLimit = l;
        this.localeName = string;
        this.timeZone = timeZone;
    }

    protected Period doBuild(long l, long l2) {
        return this.builder.createWithReferenceDate(l, l2);
    }

    protected String doFallback(long l, long l2) {
        if (this.fallback != null && this.fallbackLimit > 0L && Math.abs(l) >= this.fallbackLimit) {
            return this.fallback.format(l2 + l);
        }
        return null;
    }

    protected String doFormat(Period period) {
        if (period.isSet()) {
            return this.formatter.format(period);
        }
        throw new IllegalArgumentException("period is not set");
    }

    @Override
    public String formatDurationFrom(long l, long l2) {
        String string;
        String string2 = string = this.doFallback(l, l2);
        if (string == null) {
            string2 = this.doFormat(this.doBuild(l, l2));
        }
        return string2;
    }

    @Override
    public String formatDurationFromNow(long l) {
        return this.formatDurationFrom(l, System.currentTimeMillis());
    }

    @Override
    public String formatDurationFromNowTo(Date date) {
        long l = System.currentTimeMillis();
        return this.formatDurationFrom(date.getTime() - l, l);
    }

    @Override
    public DurationFormatter withLocale(String string) {
        if (!string.equals(this.localeName)) {
            PeriodFormatter periodFormatter = this.formatter.withLocale(string);
            PeriodBuilder periodBuilder = this.builder.withLocale(string);
            DateFormatter dateFormatter = this.fallback;
            dateFormatter = dateFormatter == null ? null : dateFormatter.withLocale(string);
            return new BasicDurationFormatter(periodFormatter, periodBuilder, dateFormatter, this.fallbackLimit, string, this.timeZone);
        }
        return this;
    }

    @Override
    public DurationFormatter withTimeZone(TimeZone timeZone) {
        if (!timeZone.equals(this.timeZone)) {
            PeriodBuilder periodBuilder = this.builder.withTimeZone(timeZone);
            DateFormatter dateFormatter = this.fallback;
            dateFormatter = dateFormatter == null ? null : dateFormatter.withTimeZone(timeZone);
            return new BasicDurationFormatter(this.formatter, periodBuilder, dateFormatter, this.fallbackLimit, this.localeName, timeZone);
        }
        return this;
    }
}

