/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateImpl;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.ChronoZonedDateTimeImpl;
import java.time.chrono.Chronology;
import java.time.chrono.Ser;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;
import java.util.Objects;

final class ChronoLocalDateTimeImpl<D extends ChronoLocalDate>
implements ChronoLocalDateTime<D>,
Temporal,
TemporalAdjuster,
Serializable {
    static final int HOURS_PER_DAY = 24;
    static final long MICROS_PER_DAY = 86400000000L;
    static final long MILLIS_PER_DAY = 86400000L;
    static final int MINUTES_PER_DAY = 1440;
    static final int MINUTES_PER_HOUR = 60;
    static final long NANOS_PER_DAY = 86400000000000L;
    static final long NANOS_PER_HOUR = 3600000000000L;
    static final long NANOS_PER_MINUTE = 60000000000L;
    static final long NANOS_PER_SECOND = 1000000000L;
    static final int SECONDS_PER_DAY = 86400;
    static final int SECONDS_PER_HOUR = 3600;
    static final int SECONDS_PER_MINUTE = 60;
    private static final long serialVersionUID = 4556003607393004514L;
    private final transient D date;
    private final transient LocalTime time;

    private ChronoLocalDateTimeImpl(D d, LocalTime localTime) {
        Objects.requireNonNull(d, "date");
        Objects.requireNonNull(localTime, "time");
        this.date = d;
        this.time = localTime;
    }

    static <R extends ChronoLocalDate> ChronoLocalDateTimeImpl<R> ensureValid(Chronology chronology, Temporal temporal) {
        if (chronology.equals((temporal = (ChronoLocalDateTimeImpl)temporal).getChronology())) {
            return temporal;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Chronology mismatch, required: ");
        stringBuilder.append(chronology.getId());
        stringBuilder.append(", actual: ");
        stringBuilder.append(temporal.getChronology().getId());
        throw new ClassCastException(stringBuilder.toString());
    }

    static <R extends ChronoLocalDate> ChronoLocalDateTimeImpl<R> of(R r, LocalTime localTime) {
        return new ChronoLocalDateTimeImpl<R>(r, localTime);
    }

    private ChronoLocalDateTimeImpl<D> plusDays(long l) {
        return this.with(this.date.plus(l, ChronoUnit.DAYS), this.time);
    }

    private ChronoLocalDateTimeImpl<D> plusHours(long l) {
        return this.plusWithOverflow(this.date, l, 0L, 0L, 0L);
    }

    private ChronoLocalDateTimeImpl<D> plusMinutes(long l) {
        return this.plusWithOverflow(this.date, 0L, l, 0L, 0L);
    }

    private ChronoLocalDateTimeImpl<D> plusNanos(long l) {
        return this.plusWithOverflow(this.date, 0L, 0L, 0L, l);
    }

    private ChronoLocalDateTimeImpl<D> plusWithOverflow(D d, long l, long l2, long l3, long l4) {
        if ((l | l2 | l3 | l4) == 0L) {
            return this.with((Temporal)d, this.time);
        }
        long l5 = l4 / 86400000000000L;
        long l6 = l3 / 86400L;
        long l7 = l2 / 1440L;
        long l8 = l / 24L;
        long l9 = this.time.toNanoOfDay();
        l2 = l4 % 86400000000000L + l3 % 86400L * 1000000000L + l2 % 1440L * 60000000000L + l % 24L * 3600000000000L + l9;
        l = Math.floorDiv(l2, 86400000000000L);
        LocalTime localTime = (l2 = Math.floorMod(l2, 86400000000000L)) == l9 ? this.time : LocalTime.ofNanoOfDay(l2);
        return this.with(d.plus(l5 + l6 + l7 + l8 + l, ChronoUnit.DAYS), localTime);
    }

    static ChronoLocalDateTime<?> readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return ((ChronoLocalDate)objectInput.readObject()).atTime((LocalTime)objectInput.readObject());
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private ChronoLocalDateTimeImpl<D> with(Temporal temporal, LocalTime localTime) {
        if (this.date == temporal && this.time == localTime) {
            return this;
        }
        return new ChronoLocalDateTimeImpl(ChronoLocalDateImpl.ensureValid(this.date.getChronology(), temporal), localTime);
    }

    private Object writeReplace() {
        return new Ser(2, this);
    }

    @Override
    public ChronoZonedDateTime<D> atZone(ZoneId zoneId) {
        return ChronoZonedDateTimeImpl.ofBest(this, zoneId, null);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof ChronoLocalDateTime) {
            if (this.compareTo((ChronoLocalDateTime)object) != 0) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public int get(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            int n = ((ChronoField)temporalField).isTimeBased() ? this.time.get(temporalField) : this.date.get(temporalField);
            return n;
        }
        return this.range(temporalField).checkValidIntValue(this.getLong(temporalField), temporalField);
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            long l = ((ChronoField)temporalField).isTimeBased() ? this.time.getLong(temporalField) : this.date.getLong(temporalField);
            return l;
        }
        return temporalField.getFrom(this);
    }

    @Override
    public int hashCode() {
        return this.toLocalDate().hashCode() ^ this.toLocalTime().hashCode();
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        boolean bl = temporalField instanceof ChronoField;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            temporalField = (ChronoField)temporalField;
            bl2 = bl3;
            if (!((ChronoField)temporalField).isDateBased()) {
                bl2 = ((ChronoField)temporalField).isTimeBased() ? bl3 : false;
            }
            return bl2;
        }
        if (temporalField == null || !temporalField.isSupportedBy(this)) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public ChronoLocalDateTimeImpl<D> plus(long l, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            ChronoUnit chronoUnit = (ChronoUnit)temporalUnit;
            switch (chronoUnit) {
                default: {
                    return this.with(this.date.plus(l, temporalUnit), this.time);
                }
                case HALF_DAYS: {
                    return ChronoLocalDateTimeImpl.super.plusHours(l % 256L * 12L);
                }
                case HOURS: {
                    return this.plusHours(l);
                }
                case MINUTES: {
                    return this.plusMinutes(l);
                }
                case SECONDS: {
                    return this.plusSeconds(l);
                }
                case MILLIS: {
                    return ChronoLocalDateTimeImpl.super.plusNanos(l % 86400000L * 1000000L);
                }
                case MICROS: {
                    return ChronoLocalDateTimeImpl.super.plusNanos(l % 86400000000L * 1000L);
                }
                case NANOS: 
            }
            return this.plusNanos(l);
        }
        return ChronoLocalDateTimeImpl.ensureValid(this.date.getChronology(), temporalUnit.addTo(this, l));
    }

    ChronoLocalDateTimeImpl<D> plusSeconds(long l) {
        return this.plusWithOverflow(this.date, 0L, 0L, l, 0L);
    }

    @Override
    public ValueRange range(TemporalField object) {
        if (object instanceof ChronoField) {
            object = ((ChronoField)object).isTimeBased() ? this.time.range((TemporalField)object) : this.date.range((TemporalField)object);
            return object;
        }
        return object.rangeRefinedBy(this);
    }

    @Override
    public D toLocalDate() {
        return this.date;
    }

    @Override
    public LocalTime toLocalTime() {
        return this.time;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.toLocalDate().toString());
        stringBuilder.append('T');
        stringBuilder.append(this.toLocalTime().toString());
        return stringBuilder.toString();
    }

    @Override
    public long until(Temporal temporal, TemporalUnit temporalUnit) {
        Objects.requireNonNull(temporal, "endExclusive");
        ChronoLocalDateTime<? extends ChronoLocalDate> chronoLocalDateTime = this.getChronology().localDateTime(temporal);
        if (temporalUnit instanceof ChronoUnit) {
            if (temporalUnit.isTimeBased()) {
                long l = chronoLocalDateTime.getLong((TemporalField)ChronoField.EPOCH_DAY) - this.date.getLong(ChronoField.EPOCH_DAY);
                switch ((ChronoUnit)temporalUnit) {
                    default: {
                        break;
                    }
                    case HALF_DAYS: {
                        l = Math.multiplyExact(l, 2L);
                        break;
                    }
                    case HOURS: {
                        l = Math.multiplyExact(l, 24L);
                        break;
                    }
                    case MINUTES: {
                        l = Math.multiplyExact(l, 1440L);
                        break;
                    }
                    case SECONDS: {
                        l = Math.multiplyExact(l, 86400L);
                        break;
                    }
                    case MILLIS: {
                        l = Math.multiplyExact(l, 86400000L);
                        break;
                    }
                    case MICROS: {
                        l = Math.multiplyExact(l, 86400000000L);
                        break;
                    }
                    case NANOS: {
                        l = Math.multiplyExact(l, 86400000000000L);
                    }
                }
                return Math.addExact(l, this.time.until(chronoLocalDateTime.toLocalTime(), temporalUnit));
            }
            ChronoLocalDate chronoLocalDate = chronoLocalDateTime.toLocalDate();
            temporal = chronoLocalDate;
            if (chronoLocalDateTime.toLocalTime().isBefore(this.time)) {
                temporal = chronoLocalDate.minus(1L, ChronoUnit.DAYS);
            }
            return this.date.until(temporal, temporalUnit);
        }
        Objects.requireNonNull(temporalUnit, "unit");
        return temporalUnit.between(this, chronoLocalDateTime);
    }

    @Override
    public ChronoLocalDateTimeImpl<D> with(TemporalAdjuster temporalAdjuster) {
        if (temporalAdjuster instanceof ChronoLocalDate) {
            return this.with((ChronoLocalDate)temporalAdjuster, this.time);
        }
        if (temporalAdjuster instanceof LocalTime) {
            return this.with((Temporal)this.date, (LocalTime)temporalAdjuster);
        }
        if (temporalAdjuster instanceof ChronoLocalDateTimeImpl) {
            return ChronoLocalDateTimeImpl.ensureValid(this.date.getChronology(), (ChronoLocalDateTimeImpl)temporalAdjuster);
        }
        return ChronoLocalDateTimeImpl.ensureValid(this.date.getChronology(), (ChronoLocalDateTimeImpl)temporalAdjuster.adjustInto(this));
    }

    @Override
    public ChronoLocalDateTimeImpl<D> with(TemporalField temporalField, long l) {
        if (temporalField instanceof ChronoField) {
            if (((ChronoField)temporalField).isTimeBased()) {
                return this.with((Temporal)this.date, this.time.with(temporalField, l));
            }
            return this.with(this.date.with(temporalField, l), this.time);
        }
        return ChronoLocalDateTimeImpl.ensureValid(this.date.getChronology(), temporalField.adjustInto(this, l));
    }

    void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeObject(this.date);
        objectOutput.writeObject(this.time);
    }

}

