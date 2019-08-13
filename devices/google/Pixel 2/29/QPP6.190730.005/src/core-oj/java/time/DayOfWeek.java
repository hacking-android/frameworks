/*
 * Decompiled with CFR 0.145.
 */
package java.time;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Locale;

public enum DayOfWeek implements TemporalAccessor,
TemporalAdjuster
{
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;
    
    private static final DayOfWeek[] ENUMS = DayOfWeek.values();

    public static DayOfWeek from(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof DayOfWeek) {
            return (DayOfWeek)temporalAccessor;
        }
        try {
            DayOfWeek dayOfWeek = DayOfWeek.of(temporalAccessor.get(ChronoField.DAY_OF_WEEK));
            return dayOfWeek;
        }
        catch (DateTimeException dateTimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to obtain DayOfWeek from TemporalAccessor: ");
            stringBuilder.append(temporalAccessor);
            stringBuilder.append(" of type ");
            stringBuilder.append(temporalAccessor.getClass().getName());
            throw new DateTimeException(stringBuilder.toString(), dateTimeException);
        }
    }

    public static DayOfWeek of(int n) {
        if (n >= 1 && n <= 7) {
            return ENUMS[n - 1];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid value for DayOfWeek: ");
        stringBuilder.append(n);
        throw new DateTimeException(stringBuilder.toString());
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.DAY_OF_WEEK, this.getValue());
    }

    @Override
    public int get(TemporalField temporalField) {
        if (temporalField == ChronoField.DAY_OF_WEEK) {
            return this.getValue();
        }
        return TemporalAccessor.super.get(temporalField);
    }

    public String getDisplayName(TextStyle textStyle, Locale locale) {
        return new DateTimeFormatterBuilder().appendText((TemporalField)ChronoField.DAY_OF_WEEK, textStyle).toFormatter(locale).format(this);
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField == ChronoField.DAY_OF_WEEK) {
            return this.getValue();
        }
        if (!(temporalField instanceof ChronoField)) {
            return temporalField.getFrom(this);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported field: ");
        stringBuilder.append(temporalField);
        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
    }

    public int getValue() {
        return this.ordinal() + 1;
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        boolean bl = temporalField instanceof ChronoField;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            if (temporalField != ChronoField.DAY_OF_WEEK) {
                bl3 = false;
            }
            return bl3;
        }
        bl3 = temporalField != null && temporalField.isSupportedBy(this) ? bl2 : false;
        return bl3;
    }

    public DayOfWeek minus(long l) {
        return this.plus(-(l % 7L));
    }

    public DayOfWeek plus(long l) {
        int n = (int)(l % 7L);
        return ENUMS[(this.ordinal() + (n + 7)) % 7];
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery == TemporalQueries.precision()) {
            return (R)ChronoUnit.DAYS;
        }
        return TemporalAccessor.super.query(temporalQuery);
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        if (temporalField == ChronoField.DAY_OF_WEEK) {
            return temporalField.range();
        }
        return TemporalAccessor.super.range(temporalField);
    }
}

