/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.BasicPeriodFormatterService;
import android.icu.impl.duration.DurationFormatter;
import android.icu.impl.duration.DurationFormatterFactory;
import android.icu.impl.duration.Period;
import android.icu.impl.duration.PeriodFormatter;
import android.icu.impl.duration.PeriodFormatterFactory;
import android.icu.impl.duration.PeriodFormatterService;
import android.icu.impl.duration.TimeUnit;
import android.icu.text.DurationFormat;
import android.icu.util.ULocale;
import java.text.FieldPosition;
import java.util.Date;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;

public class BasicDurationFormat
extends DurationFormat {
    private static final long serialVersionUID = -3146984141909457700L;
    transient DurationFormatter formatter;
    transient PeriodFormatter pformatter;
    transient PeriodFormatterService pfs = BasicPeriodFormatterService.getInstance();

    public BasicDurationFormat() {
        this.formatter = this.pfs.newDurationFormatterFactory().getFormatter();
        this.pformatter = this.pfs.newPeriodFormatterFactory().setDisplayPastFuture(false).getFormatter();
    }

    public BasicDurationFormat(ULocale uLocale) {
        super(uLocale);
        this.formatter = this.pfs.newDurationFormatterFactory().setLocale(uLocale.getName()).getFormatter();
        this.pformatter = this.pfs.newPeriodFormatterFactory().setDisplayPastFuture(false).setLocale(uLocale.getName()).getFormatter();
    }

    public static BasicDurationFormat getInstance(ULocale uLocale) {
        return new BasicDurationFormat(uLocale);
    }

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (object instanceof Long) {
            stringBuffer.append(this.formatDurationFromNow((Long)object));
            return stringBuffer;
        }
        if (object instanceof Date) {
            stringBuffer.append(this.formatDurationFromNowTo((Date)object));
            return stringBuffer;
        }
        if (object instanceof Duration) {
            stringBuffer.append(this.formatDuration(object));
            return stringBuffer;
        }
        throw new IllegalArgumentException("Cannot format given Object as a Duration");
    }

    public String formatDuration(Object object) {
        DatatypeConstants.Field[] arrfield = new DatatypeConstants.Field[]{DatatypeConstants.YEARS, DatatypeConstants.MONTHS, DatatypeConstants.DAYS, DatatypeConstants.HOURS, DatatypeConstants.MINUTES, DatatypeConstants.SECONDS};
        TimeUnit[] arrtimeUnit = new TimeUnit[]{TimeUnit.YEAR, TimeUnit.MONTH, TimeUnit.DAY, TimeUnit.HOUR, TimeUnit.MINUTE, TimeUnit.SECOND};
        Duration duration = (Duration)object;
        Object object2 = null;
        object = duration;
        boolean bl = false;
        if (duration.getSign() < 0) {
            object = duration.negate();
            bl = true;
        }
        boolean bl2 = false;
        Object object3 = object;
        object = object2;
        for (int i = 0; i < arrfield.length; ++i) {
            if (!((Duration)object3).isSet(arrfield[i]) || ((Number)(object2 = ((Duration)object3).getField(arrfield[i]))).intValue() == 0 && !bl2) continue;
            bl2 = true;
            float f = ((Number)object2).floatValue();
            object2 = null;
            float f2 = 0.0f;
            if (arrtimeUnit[i] == TimeUnit.SECOND) {
                double d = f;
                double d2 = Math.floor(f);
                if ((d = (d - d2) * 1000.0) > 0.0) {
                    object2 = TimeUnit.MILLISECOND;
                    f2 = (float)d;
                    f = (float)d2;
                }
            }
            object = object == null ? Period.at(f, arrtimeUnit[i]) : ((Period)object).and(f, arrtimeUnit[i]);
            if (object2 == null) continue;
            object = ((Period)object).and(f2, (TimeUnit)object2);
        }
        if (object == null) {
            return this.formatDurationFromNow(0L);
        }
        object = bl ? ((Period)object).inPast() : ((Period)object).inFuture();
        return this.pformatter.format((Period)object);
    }

    @Override
    public String formatDurationFrom(long l, long l2) {
        return this.formatter.formatDurationFrom(l, l2);
    }

    @Override
    public String formatDurationFromNow(long l) {
        return this.formatter.formatDurationFromNow(l);
    }

    @Override
    public String formatDurationFromNowTo(Date date) {
        return this.formatter.formatDurationFromNowTo(date);
    }
}

