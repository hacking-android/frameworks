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
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTimeImpl;
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
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Comparator;
import java.util.Objects;

public interface ChronoZonedDateTime<D extends ChronoLocalDate>
extends Temporal,
Comparable<ChronoZonedDateTime<?>> {
    public static ChronoZonedDateTime<?> from(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof ChronoZonedDateTime) {
            return (ChronoZonedDateTime)temporalAccessor;
        }
        Objects.requireNonNull(temporalAccessor, "temporal");
        Object object = temporalAccessor.query(TemporalQueries.chronology());
        if (object != null) {
            return object.zonedDateTime(temporalAccessor);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to obtain ChronoZonedDateTime from TemporalAccessor: ");
        ((StringBuilder)object).append(temporalAccessor.getClass());
        throw new DateTimeException(((StringBuilder)object).toString());
    }

    public static Comparator<ChronoZonedDateTime<?>> timeLineOrder() {
        return AbstractChronology.INSTANT_ORDER;
    }

    @Override
    default public int compareTo(ChronoZonedDateTime<?> chronoZonedDateTime) {
        int n;
        int n2 = n = Long.compare(this.toEpochSecond(), chronoZonedDateTime.toEpochSecond());
        if (n == 0) {
            n2 = n = this.toLocalTime().getNano() - chronoZonedDateTime.toLocalTime().getNano();
            if (n == 0) {
                n2 = n = this.toLocalDateTime().compareTo(chronoZonedDateTime.toLocalDateTime());
                if (n == 0) {
                    n2 = n = this.getZone().getId().compareTo(chronoZonedDateTime.getZone().getId());
                    if (n == 0) {
                        n2 = this.getChronology().compareTo(chronoZonedDateTime.getChronology());
                    }
                }
            }
        }
        return n2;
    }

    public boolean equals(Object var1);

    default public String format(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return dateTimeFormatter.format(this);
    }

    @Override
    default public int get(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)temporalField).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    return this.toLocalDateTime().get(temporalField);
                }
                return this.getOffset().getTotalSeconds();
            }
            throw new UnsupportedTemporalTypeException("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
        }
        return Temporal.super.get(temporalField);
    }

    default public Chronology getChronology() {
        return this.toLocalDate().getChronology();
    }

    @Override
    default public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)temporalField).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    return this.toLocalDateTime().getLong(temporalField);
                }
                return this.getOffset().getTotalSeconds();
            }
            return this.toEpochSecond();
        }
        return temporalField.getFrom(this);
    }

    public ZoneOffset getOffset();

    public ZoneId getZone();

    public int hashCode();

    default public boolean isAfter(ChronoZonedDateTime<?> chronoZonedDateTime) {
        long l;
        long l2 = this.toEpochSecond();
        boolean bl = l2 > (l = chronoZonedDateTime.toEpochSecond()) || l2 == l && this.toLocalTime().getNano() > chronoZonedDateTime.toLocalTime().getNano();
        return bl;
    }

    default public boolean isBefore(ChronoZonedDateTime<?> chronoZonedDateTime) {
        long l;
        long l2 = this.toEpochSecond();
        boolean bl = l2 < (l = chronoZonedDateTime.toEpochSecond()) || l2 == l && this.toLocalTime().getNano() < chronoZonedDateTime.toLocalTime().getNano();
        return bl;
    }

    default public boolean isEqual(ChronoZonedDateTime<?> chronoZonedDateTime) {
        boolean bl = this.toEpochSecond() == chronoZonedDateTime.toEpochSecond() && this.toLocalTime().getNano() == chronoZonedDateTime.toLocalTime().getNano();
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
    default public ChronoZonedDateTime<D> minus(long l, TemporalUnit temporalUnit) {
        return ChronoZonedDateTimeImpl.ensureValid(this.getChronology(), Temporal.super.minus(l, temporalUnit));
    }

    @Override
    default public ChronoZonedDateTime<D> minus(TemporalAmount temporalAmount) {
        return ChronoZonedDateTimeImpl.ensureValid(this.getChronology(), Temporal.super.minus(temporalAmount));
    }

    @Override
    public ChronoZonedDateTime<D> plus(long var1, TemporalUnit var3);

    @Override
    default public ChronoZonedDateTime<D> plus(TemporalAmount temporalAmount) {
        return ChronoZonedDateTimeImpl.ensureValid(this.getChronology(), Temporal.super.plus(temporalAmount));
    }

    @Override
    default public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery != TemporalQueries.zone() && temporalQuery != TemporalQueries.zoneId()) {
            if (temporalQuery == TemporalQueries.offset()) {
                return (R)this.getOffset();
            }
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
        return (R)this.getZone();
    }

    @Override
    default public ValueRange range(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (temporalField != ChronoField.INSTANT_SECONDS && temporalField != ChronoField.OFFSET_SECONDS) {
                return this.toLocalDateTime().range(temporalField);
            }
            return temporalField.range();
        }
        return temporalField.rangeRefinedBy(this);
    }

    default public long toEpochSecond() {
        return 86400L * this.toLocalDate().toEpochDay() + (long)this.toLocalTime().toSecondOfDay() - (long)this.getOffset().getTotalSeconds();
    }

    default public Instant toInstant() {
        return Instant.ofEpochSecond(this.toEpochSecond(), this.toLocalTime().getNano());
    }

    default public D toLocalDate() {
        return this.toLocalDateTime().toLocalDate();
    }

    public ChronoLocalDateTime<D> toLocalDateTime();

    default public LocalTime toLocalTime() {
        return this.toLocalDateTime().toLocalTime();
    }

    public String toString();

    @Override
    default public ChronoZonedDateTime<D> with(TemporalAdjuster temporalAdjuster) {
        return ChronoZonedDateTimeImpl.ensureValid(this.getChronology(), Temporal.super.with(temporalAdjuster));
    }

    @Override
    public ChronoZonedDateTime<D> with(TemporalField var1, long var2);

    public ChronoZonedDateTime<D> withEarlierOffsetAtOverlap();

    public ChronoZonedDateTime<D> withLaterOffsetAtOverlap();

    public ChronoZonedDateTime<D> withZoneSameInstant(ZoneId var1);

    public ChronoZonedDateTime<D> withZoneSameLocal(ZoneId var1);

}

