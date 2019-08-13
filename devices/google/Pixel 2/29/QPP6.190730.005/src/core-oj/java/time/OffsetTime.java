/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$I08rBDhAPdxOG_R3AeLRKYX7Z-o
 */
package java.time;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.time.-$;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Ser;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time._$$Lambda$I08rBDhAPdxOG_R3AeLRKYX7Z_o;
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
import java.time.zone.ZoneRules;
import java.util.Objects;

public final class OffsetTime
implements Temporal,
TemporalAdjuster,
Comparable<OffsetTime>,
Serializable {
    public static final OffsetTime MAX;
    public static final OffsetTime MIN;
    private static final long serialVersionUID = 7264499704384272492L;
    private final ZoneOffset offset;
    private final LocalTime time;

    static {
        MIN = LocalTime.MIN.atOffset(ZoneOffset.MAX);
        MAX = LocalTime.MAX.atOffset(ZoneOffset.MIN);
    }

    private OffsetTime(LocalTime localTime, ZoneOffset zoneOffset) {
        this.time = Objects.requireNonNull(localTime, "time");
        this.offset = Objects.requireNonNull(zoneOffset, "offset");
    }

    public static OffsetTime from(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof OffsetTime) {
            return (OffsetTime)temporalAccessor;
        }
        try {
            OffsetTime offsetTime = new OffsetTime(LocalTime.from(temporalAccessor), ZoneOffset.from(temporalAccessor));
            return offsetTime;
        }
        catch (DateTimeException dateTimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to obtain OffsetTime from TemporalAccessor: ");
            stringBuilder.append(temporalAccessor);
            stringBuilder.append(" of type ");
            stringBuilder.append(temporalAccessor.getClass().getName());
            throw new DateTimeException(stringBuilder.toString(), dateTimeException);
        }
    }

    public static OffsetTime now() {
        return OffsetTime.now(Clock.systemDefaultZone());
    }

    public static OffsetTime now(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        Instant instant = clock.instant();
        return OffsetTime.ofInstant(instant, clock.getZone().getRules().getOffset(instant));
    }

    public static OffsetTime now(ZoneId zoneId) {
        return OffsetTime.now(Clock.system(zoneId));
    }

    public static OffsetTime of(int n, int n2, int n3, int n4, ZoneOffset zoneOffset) {
        return new OffsetTime(LocalTime.of(n, n2, n3, n4), zoneOffset);
    }

    public static OffsetTime of(LocalTime localTime, ZoneOffset zoneOffset) {
        return new OffsetTime(localTime, zoneOffset);
    }

    public static OffsetTime ofInstant(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        zoneId = zoneId.getRules().getOffset(instant);
        return new OffsetTime(LocalTime.ofNanoOfDay((long)((int)Math.floorMod(instant.getEpochSecond() + (long)((ZoneOffset)zoneId).getTotalSeconds(), 86400L)) * 1000000000L + (long)instant.getNano()), (ZoneOffset)zoneId);
    }

    public static OffsetTime parse(CharSequence charSequence) {
        return OffsetTime.parse(charSequence, DateTimeFormatter.ISO_OFFSET_TIME);
    }

    public static OffsetTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (OffsetTime)dateTimeFormatter.parse(charSequence, _$$Lambda$I08rBDhAPdxOG_R3AeLRKYX7Z_o.INSTANCE);
    }

    static OffsetTime readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return OffsetTime.of(LocalTime.readExternal(objectInput), ZoneOffset.readExternal(objectInput));
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private long toEpochNano() {
        return this.time.toNanoOfDay() - (long)this.offset.getTotalSeconds() * 1000000000L;
    }

    private OffsetTime with(LocalTime localTime, ZoneOffset zoneOffset) {
        if (this.time == localTime && this.offset.equals(zoneOffset)) {
            return this;
        }
        return new OffsetTime(localTime, zoneOffset);
    }

    private Object writeReplace() {
        return new Ser(9, this);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.NANO_OF_DAY, this.time.toNanoOfDay()).with(ChronoField.OFFSET_SECONDS, this.offset.getTotalSeconds());
    }

    public OffsetDateTime atDate(LocalDate localDate) {
        return OffsetDateTime.of(localDate, this.time, this.offset);
    }

    @Override
    public int compareTo(OffsetTime offsetTime) {
        int n;
        if (this.offset.equals(offsetTime.offset)) {
            return this.time.compareTo(offsetTime.time);
        }
        int n2 = n = Long.compare(this.toEpochNano(), offsetTime.toEpochNano());
        if (n == 0) {
            n2 = this.time.compareTo(offsetTime.time);
        }
        return n2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof OffsetTime) {
            object = (OffsetTime)object;
            if (!this.time.equals(((OffsetTime)object).time) || !this.offset.equals(((OffsetTime)object).offset)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public String format(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return dateTimeFormatter.format(this);
    }

    @Override
    public int get(TemporalField temporalField) {
        return Temporal.super.get(temporalField);
    }

    public int getHour() {
        return this.time.getHour();
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (temporalField == ChronoField.OFFSET_SECONDS) {
                return this.offset.getTotalSeconds();
            }
            return this.time.getLong(temporalField);
        }
        return temporalField.getFrom(this);
    }

    public int getMinute() {
        return this.time.getMinute();
    }

    public int getNano() {
        return this.time.getNano();
    }

    public ZoneOffset getOffset() {
        return this.offset;
    }

    public int getSecond() {
        return this.time.getSecond();
    }

    public int hashCode() {
        return this.time.hashCode() ^ this.offset.hashCode();
    }

    public boolean isAfter(OffsetTime offsetTime) {
        boolean bl = this.toEpochNano() > offsetTime.toEpochNano();
        return bl;
    }

    public boolean isBefore(OffsetTime offsetTime) {
        boolean bl = this.toEpochNano() < offsetTime.toEpochNano();
        return bl;
    }

    public boolean isEqual(OffsetTime offsetTime) {
        boolean bl = this.toEpochNano() == offsetTime.toEpochNano();
        return bl;
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        boolean bl = temporalField instanceof ChronoField;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            bl2 = bl3;
            if (!temporalField.isTimeBased()) {
                bl2 = temporalField == ChronoField.OFFSET_SECONDS ? bl3 : false;
            }
            return bl2;
        }
        if (temporalField == null || !temporalField.isSupportedBy(this)) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public boolean isSupported(TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            return temporalUnit.isTimeBased();
        }
        boolean bl = temporalUnit != null && temporalUnit.isSupportedBy(this);
        return bl;
    }

    @Override
    public OffsetTime minus(long l, TemporalUnit object) {
        object = l == Long.MIN_VALUE ? this.plus(Long.MAX_VALUE, (TemporalUnit)object).plus(1L, (TemporalUnit)object) : this.plus(-l, (TemporalUnit)object);
        return object;
    }

    @Override
    public OffsetTime minus(TemporalAmount temporalAmount) {
        return (OffsetTime)temporalAmount.subtractFrom(this);
    }

    public OffsetTime minusHours(long l) {
        return this.with(this.time.minusHours(l), this.offset);
    }

    public OffsetTime minusMinutes(long l) {
        return this.with(this.time.minusMinutes(l), this.offset);
    }

    public OffsetTime minusNanos(long l) {
        return this.with(this.time.minusNanos(l), this.offset);
    }

    public OffsetTime minusSeconds(long l) {
        return this.with(this.time.minusSeconds(l), this.offset);
    }

    @Override
    public OffsetTime plus(long l, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            return this.with(this.time.plus(l, temporalUnit), this.offset);
        }
        return temporalUnit.addTo(this, l);
    }

    @Override
    public OffsetTime plus(TemporalAmount temporalAmount) {
        return (OffsetTime)temporalAmount.addTo(this);
    }

    public OffsetTime plusHours(long l) {
        return this.with(this.time.plusHours(l), this.offset);
    }

    public OffsetTime plusMinutes(long l) {
        return this.with(this.time.plusMinutes(l), this.offset);
    }

    public OffsetTime plusNanos(long l) {
        return this.with(this.time.plusNanos(l), this.offset);
    }

    public OffsetTime plusSeconds(long l) {
        return this.with(this.time.plusSeconds(l), this.offset);
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery != TemporalQueries.offset() && temporalQuery != TemporalQueries.zone()) {
            TemporalQuery<ZoneId> temporalQuery2 = TemporalQueries.zoneId();
            boolean bl = true;
            boolean bl2 = temporalQuery == temporalQuery2;
            if (temporalQuery != TemporalQueries.chronology()) {
                bl = false;
            }
            if (!(bl2 | bl) && temporalQuery != TemporalQueries.localDate()) {
                if (temporalQuery == TemporalQueries.localTime()) {
                    return (R)this.time;
                }
                if (temporalQuery == TemporalQueries.precision()) {
                    return (R)ChronoUnit.NANOS;
                }
                return temporalQuery.queryFrom(this);
            }
            return null;
        }
        return (R)this.offset;
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (temporalField == ChronoField.OFFSET_SECONDS) {
                return temporalField.range();
            }
            return this.time.range(temporalField);
        }
        return temporalField.rangeRefinedBy(this);
    }

    public LocalTime toLocalTime() {
        return this.time;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.time.toString());
        stringBuilder.append(this.offset.toString());
        return stringBuilder.toString();
    }

    public OffsetTime truncatedTo(TemporalUnit temporalUnit) {
        return this.with(this.time.truncatedTo(temporalUnit), this.offset);
    }

    @Override
    public long until(Temporal object, TemporalUnit temporalUnit) {
        object = OffsetTime.from((TemporalAccessor)object);
        if (temporalUnit instanceof ChronoUnit) {
            long l = OffsetTime.super.toEpochNano() - this.toEpochNano();
            switch ((ChronoUnit)temporalUnit) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unsupported unit: ");
                    ((StringBuilder)object).append(temporalUnit);
                    throw new UnsupportedTemporalTypeException(((StringBuilder)object).toString());
                }
                case HALF_DAYS: {
                    return l / 43200000000000L;
                }
                case HOURS: {
                    return l / 3600000000000L;
                }
                case MINUTES: {
                    return l / 60000000000L;
                }
                case SECONDS: {
                    return l / 1000000000L;
                }
                case MILLIS: {
                    return l / 1000000L;
                }
                case MICROS: {
                    return l / 1000L;
                }
                case NANOS: 
            }
            return l;
        }
        return temporalUnit.between(this, (Temporal)object);
    }

    @Override
    public OffsetTime with(TemporalAdjuster temporalAdjuster) {
        if (temporalAdjuster instanceof LocalTime) {
            return this.with((LocalTime)temporalAdjuster, this.offset);
        }
        if (temporalAdjuster instanceof ZoneOffset) {
            return this.with(this.time, (ZoneOffset)temporalAdjuster);
        }
        if (temporalAdjuster instanceof OffsetTime) {
            return (OffsetTime)temporalAdjuster;
        }
        return (OffsetTime)temporalAdjuster.adjustInto(this);
    }

    @Override
    public OffsetTime with(TemporalField temporalField, long l) {
        if (temporalField instanceof ChronoField) {
            if (temporalField == ChronoField.OFFSET_SECONDS) {
                temporalField = (ChronoField)temporalField;
                return this.with(this.time, ZoneOffset.ofTotalSeconds(((ChronoField)temporalField).checkValidIntValue(l)));
            }
            return this.with(this.time.with(temporalField, l), this.offset);
        }
        return temporalField.adjustInto(this, l);
    }

    public OffsetTime withHour(int n) {
        return this.with(this.time.withHour(n), this.offset);
    }

    public OffsetTime withMinute(int n) {
        return this.with(this.time.withMinute(n), this.offset);
    }

    public OffsetTime withNano(int n) {
        return this.with(this.time.withNano(n), this.offset);
    }

    public OffsetTime withOffsetSameInstant(ZoneOffset zoneOffset) {
        if (zoneOffset.equals(this.offset)) {
            return this;
        }
        int n = zoneOffset.getTotalSeconds();
        int n2 = this.offset.getTotalSeconds();
        return new OffsetTime(this.time.plusSeconds(n - n2), zoneOffset);
    }

    public OffsetTime withOffsetSameLocal(ZoneOffset temporalAccessor) {
        temporalAccessor = temporalAccessor != null && temporalAccessor.equals(this.offset) ? this : new OffsetTime(this.time, (ZoneOffset)temporalAccessor);
        return temporalAccessor;
    }

    public OffsetTime withSecond(int n) {
        return this.with(this.time.withSecond(n), this.offset);
    }

    void writeExternal(ObjectOutput objectOutput) throws IOException {
        this.time.writeExternal(objectOutput);
        this.offset.writeExternal(objectOutput);
    }

}

