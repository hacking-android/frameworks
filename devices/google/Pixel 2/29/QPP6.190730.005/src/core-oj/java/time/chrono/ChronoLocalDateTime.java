/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTimeImpl;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
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
import java.util.Comparator;
import java.util.Objects;

public interface ChronoLocalDateTime<D extends ChronoLocalDate>
extends Temporal,
TemporalAdjuster,
Comparable<ChronoLocalDateTime<?>> {
    public static ChronoLocalDateTime<?> from(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof ChronoLocalDateTime) {
            return (ChronoLocalDateTime)temporalAccessor;
        }
        Objects.requireNonNull(temporalAccessor, "temporal");
        Object object = temporalAccessor.query(TemporalQueries.chronology());
        if (object != null) {
            return object.localDateTime(temporalAccessor);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to obtain ChronoLocalDateTime from TemporalAccessor: ");
        ((StringBuilder)object).append(temporalAccessor.getClass());
        throw new DateTimeException(((StringBuilder)object).toString());
    }

    public static Comparator<ChronoLocalDateTime<?>> timeLineOrder() {
        return AbstractChronology.DATE_TIME_ORDER;
    }

    @Override
    default public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.EPOCH_DAY, this.toLocalDate().toEpochDay()).with(ChronoField.NANO_OF_DAY, this.toLocalTime().toNanoOfDay());
    }

    public ChronoZonedDateTime<D> atZone(ZoneId var1);

    @Override
    default public int compareTo(ChronoLocalDateTime<?> chronoLocalDateTime) {
        int n;
        int n2 = n = this.toLocalDate().compareTo((ChronoLocalDate)chronoLocalDateTime.toLocalDate());
        if (n == 0) {
            n2 = n = this.toLocalTime().compareTo(chronoLocalDateTime.toLocalTime());
            if (n == 0) {
                n2 = this.getChronology().compareTo(chronoLocalDateTime.getChronology());
            }
        }
        return n2;
    }

    public boolean equals(Object var1);

    default public String format(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return dateTimeFormatter.format(this);
    }

    default public Chronology getChronology() {
        return this.toLocalDate().getChronology();
    }

    public int hashCode();

    default public boolean isAfter(ChronoLocalDateTime<?> chronoLocalDateTime) {
        long l;
        long l2 = this.toLocalDate().toEpochDay();
        boolean bl = l2 > (l = chronoLocalDateTime.toLocalDate().toEpochDay()) || l2 == l && this.toLocalTime().toNanoOfDay() > chronoLocalDateTime.toLocalTime().toNanoOfDay();
        return bl;
    }

    default public boolean isBefore(ChronoLocalDateTime<?> chronoLocalDateTime) {
        long l;
        long l2 = this.toLocalDate().toEpochDay();
        boolean bl = l2 < (l = chronoLocalDateTime.toLocalDate().toEpochDay()) || l2 == l && this.toLocalTime().toNanoOfDay() < chronoLocalDateTime.toLocalTime().toNanoOfDay();
        return bl;
    }

    default public boolean isEqual(ChronoLocalDateTime<?> chronoLocalDateTime) {
        boolean bl = this.toLocalTime().toNanoOfDay() == chronoLocalDateTime.toLocalTime().toNanoOfDay() && this.toLocalDate().toEpochDay() == chronoLocalDateTime.toLocalDate().toEpochDay();
        return bl;
    }

    @Override
    public boolean isSupported(TemporalField var1);

    @Override
    default public boolean isSupported(TemporalUnit temporalUnit) {
        boolean bl = temporalUnit instanceof ChronoUnit;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            if (temporalUnit == ChronoUnit.FOREVER) {
                bl3 = false;
            }
            return bl3;
        }
        bl3 = temporalUnit != null && temporalUnit.isSupportedBy(this) ? bl2 : false;
        return bl3;
    }

    @Override
    default public ChronoLocalDateTime<D> minus(long l, TemporalUnit temporalUnit) {
        return ChronoLocalDateTimeImpl.ensureValid(this.getChronology(), Temporal.super.minus(l, temporalUnit));
    }

    @Override
    default public ChronoLocalDateTime<D> minus(TemporalAmount temporalAmount) {
        return ChronoLocalDateTimeImpl.ensureValid(this.getChronology(), Temporal.super.minus(temporalAmount));
    }

    @Override
    public ChronoLocalDateTime<D> plus(long var1, TemporalUnit var3);

    @Override
    default public ChronoLocalDateTime<D> plus(TemporalAmount temporalAmount) {
        return ChronoLocalDateTimeImpl.ensureValid(this.getChronology(), Temporal.super.plus(temporalAmount));
    }

    @Override
    default public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery != TemporalQueries.zoneId() && temporalQuery != TemporalQueries.zone() && temporalQuery != TemporalQueries.offset()) {
            if (temporalQuery == TemporalQueries.localTime()) {
                return (R)this.toLocalTime();
            }
            if (temporalQuery == TemporalQueries.chronology()) {
                return (R)this.getChronology();
            }
            if (temporalQuery == TemporalQueries.precision()) {
                return (R)ChronoUnit.NANOS;
            }
            return temporalQuery.queryFrom(this);
        }
        return null;
    }

    default public long toEpochSecond(ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        return 86400L * this.toLocalDate().toEpochDay() + (long)this.toLocalTime().toSecondOfDay() - (long)zoneOffset.getTotalSeconds();
    }

    default public Instant toInstant(ZoneOffset zoneOffset) {
        return Instant.ofEpochSecond(this.toEpochSecond(zoneOffset), this.toLocalTime().getNano());
    }

    public D toLocalDate();

    public LocalTime toLocalTime();

    public String toString();

    @Override
    default public ChronoLocalDateTime<D> with(TemporalAdjuster temporalAdjuster) {
        return ChronoLocalDateTimeImpl.ensureValid(this.getChronology(), Temporal.super.with(temporalAdjuster));
    }

    @Override
    public ChronoLocalDateTime<D> with(TemporalField var1, long var2);
}

