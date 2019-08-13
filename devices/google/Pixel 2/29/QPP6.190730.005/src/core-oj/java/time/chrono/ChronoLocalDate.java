/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDateImpl;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoLocalDateTimeImpl;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Comparator;
import java.util.Objects;

public interface ChronoLocalDate
extends Temporal,
TemporalAdjuster,
Comparable<ChronoLocalDate> {
    public static ChronoLocalDate from(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof ChronoLocalDate) {
            return (ChronoLocalDate)temporalAccessor;
        }
        Objects.requireNonNull(temporalAccessor, "temporal");
        Object object = temporalAccessor.query(TemporalQueries.chronology());
        if (object != null) {
            return object.date(temporalAccessor);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to obtain ChronoLocalDate from TemporalAccessor: ");
        ((StringBuilder)object).append(temporalAccessor.getClass());
        throw new DateTimeException(((StringBuilder)object).toString());
    }

    public static Comparator<ChronoLocalDate> timeLineOrder() {
        return AbstractChronology.DATE_ORDER;
    }

    @Override
    default public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.EPOCH_DAY, this.toEpochDay());
    }

    default public ChronoLocalDateTime<?> atTime(LocalTime localTime) {
        return ChronoLocalDateTimeImpl.of(this, localTime);
    }

    @Override
    default public int compareTo(ChronoLocalDate chronoLocalDate) {
        int n;
        int n2 = n = Long.compare(this.toEpochDay(), chronoLocalDate.toEpochDay());
        if (n == 0) {
            n2 = this.getChronology().compareTo(chronoLocalDate.getChronology());
        }
        return n2;
    }

    public boolean equals(Object var1);

    default public String format(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return dateTimeFormatter.format(this);
    }

    public Chronology getChronology();

    default public Era getEra() {
        return this.getChronology().eraOf(this.get(ChronoField.ERA));
    }

    public int hashCode();

    default public boolean isAfter(ChronoLocalDate chronoLocalDate) {
        boolean bl = this.toEpochDay() > chronoLocalDate.toEpochDay();
        return bl;
    }

    default public boolean isBefore(ChronoLocalDate chronoLocalDate) {
        boolean bl = this.toEpochDay() < chronoLocalDate.toEpochDay();
        return bl;
    }

    default public boolean isEqual(ChronoLocalDate chronoLocalDate) {
        boolean bl = this.toEpochDay() == chronoLocalDate.toEpochDay();
        return bl;
    }

    default public boolean isLeapYear() {
        return this.getChronology().isLeapYear(this.getLong(ChronoField.YEAR));
    }

    @Override
    default public boolean isSupported(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            return temporalField.isDateBased();
        }
        boolean bl = temporalField != null && temporalField.isSupportedBy(this);
        return bl;
    }

    @Override
    default public boolean isSupported(TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            return temporalUnit.isDateBased();
        }
        boolean bl = temporalUnit != null && temporalUnit.isSupportedBy(this);
        return bl;
    }

    public int lengthOfMonth();

    default public int lengthOfYear() {
        int n = this.isLeapYear() ? 366 : 365;
        return n;
    }

    @Override
    default public ChronoLocalDate minus(long l, TemporalUnit temporalUnit) {
        return ChronoLocalDateImpl.ensureValid(this.getChronology(), Temporal.super.minus(l, temporalUnit));
    }

    @Override
    default public ChronoLocalDate minus(TemporalAmount temporalAmount) {
        return ChronoLocalDateImpl.ensureValid(this.getChronology(), Temporal.super.minus(temporalAmount));
    }

    @Override
    default public ChronoLocalDate plus(long l, TemporalUnit temporalUnit) {
        if (!(temporalUnit instanceof ChronoUnit)) {
            return ChronoLocalDateImpl.ensureValid(this.getChronology(), temporalUnit.addTo(this, l));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported unit: ");
        stringBuilder.append(temporalUnit);
        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
    }

    @Override
    default public ChronoLocalDate plus(TemporalAmount temporalAmount) {
        return ChronoLocalDateImpl.ensureValid(this.getChronology(), Temporal.super.plus(temporalAmount));
    }

    @Override
    default public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery != TemporalQueries.zoneId() && temporalQuery != TemporalQueries.zone() && temporalQuery != TemporalQueries.offset()) {
            if (temporalQuery == TemporalQueries.localTime()) {
                return null;
            }
            if (temporalQuery == TemporalQueries.chronology()) {
                return (R)this.getChronology();
            }
            if (temporalQuery == TemporalQueries.precision()) {
                return (R)ChronoUnit.DAYS;
            }
            return temporalQuery.queryFrom(this);
        }
        return null;
    }

    default public long toEpochDay() {
        return this.getLong(ChronoField.EPOCH_DAY);
    }

    public String toString();

    @Override
    public long until(Temporal var1, TemporalUnit var2);

    public ChronoPeriod until(ChronoLocalDate var1);

    @Override
    default public ChronoLocalDate with(TemporalAdjuster temporalAdjuster) {
        return ChronoLocalDateImpl.ensureValid(this.getChronology(), Temporal.super.with(temporalAdjuster));
    }

    @Override
    default public ChronoLocalDate with(TemporalField temporalField, long l) {
        if (!(temporalField instanceof ChronoField)) {
            return ChronoLocalDateImpl.ensureValid(this.getChronology(), temporalField.adjustInto(this, l));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported field: ");
        stringBuilder.append(temporalField);
        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
    }
}

