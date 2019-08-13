/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$PTL8WkLA4o-1z4zIUBjrvwi808w
 */
package java.time;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.-$;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Ser;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time._$$Lambda$PTL8WkLA4o_1z4zIUBjrvwi808w;
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
import java.util.Objects;

public final class Instant
implements Temporal,
TemporalAdjuster,
Comparable<Instant>,
Serializable {
    public static final Instant EPOCH = new Instant(0L, 0);
    public static final Instant MAX;
    private static final long MAX_SECOND = 31556889864403199L;
    public static final Instant MIN;
    private static final long MIN_SECOND = -31557014167219200L;
    private static final long serialVersionUID = -665713676816604388L;
    private final int nanos;
    private final long seconds;

    static {
        MIN = Instant.ofEpochSecond(-31557014167219200L, 0L);
        MAX = Instant.ofEpochSecond(31556889864403199L, 999999999L);
    }

    private Instant(long l, int n) {
        this.seconds = l;
        this.nanos = n;
    }

    private static Instant create(long l, int n) {
        if (((long)n | l) == 0L) {
            return EPOCH;
        }
        if (l >= -31557014167219200L && l <= 31556889864403199L) {
            return new Instant(l, n);
        }
        throw new DateTimeException("Instant exceeds minimum or maximum instant");
    }

    public static Instant from(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof Instant) {
            return (Instant)temporalAccessor;
        }
        Objects.requireNonNull(temporalAccessor, "temporal");
        try {
            Instant instant = Instant.ofEpochSecond(temporalAccessor.getLong(ChronoField.INSTANT_SECONDS), temporalAccessor.get(ChronoField.NANO_OF_SECOND));
            return instant;
        }
        catch (DateTimeException dateTimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to obtain Instant from TemporalAccessor: ");
            stringBuilder.append(temporalAccessor);
            stringBuilder.append(" of type ");
            stringBuilder.append(temporalAccessor.getClass().getName());
            throw new DateTimeException(stringBuilder.toString(), dateTimeException);
        }
    }

    private long nanosUntil(Instant instant) {
        return Math.addExact(Math.multiplyExact(Math.subtractExact(instant.seconds, this.seconds), 1000000000L), (long)(instant.nanos - this.nanos));
    }

    public static Instant now() {
        return Clock.systemUTC().instant();
    }

    public static Instant now(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return clock.instant();
    }

    public static Instant ofEpochMilli(long l) {
        return Instant.create(Math.floorDiv(l, 1000L), 1000000 * (int)Math.floorMod(l, 1000L));
    }

    public static Instant ofEpochSecond(long l) {
        return Instant.create(l, 0);
    }

    public static Instant ofEpochSecond(long l, long l2) {
        return Instant.create(Math.addExact(l, Math.floorDiv(l2, 1000000000L)), (int)Math.floorMod(l2, 1000000000L));
    }

    public static Instant parse(CharSequence charSequence) {
        return (Instant)DateTimeFormatter.ISO_INSTANT.parse(charSequence, _$$Lambda$PTL8WkLA4o_1z4zIUBjrvwi808w.INSTANCE);
    }

    private Instant plus(long l, long l2) {
        if ((l | l2) == 0L) {
            return this;
        }
        return Instant.ofEpochSecond(Math.addExact(Math.addExact(this.seconds, l), l2 / 1000000000L), (long)this.nanos + l2 % 1000000000L);
    }

    static Instant readExternal(DataInput dataInput) throws IOException {
        return Instant.ofEpochSecond(dataInput.readLong(), dataInput.readInt());
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private long secondsUntil(Instant instant) {
        long l;
        long l2 = Math.subtractExact(instant.seconds, this.seconds);
        long l3 = instant.nanos - this.nanos;
        if (l2 > 0L && l3 < 0L) {
            l = l2 - 1L;
        } else {
            l = l2;
            if (l2 < 0L) {
                l = l2;
                if (l3 > 0L) {
                    l = l2 + 1L;
                }
            }
        }
        return l;
    }

    private Object writeReplace() {
        return new Ser(2, this);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.INSTANT_SECONDS, this.seconds).with(ChronoField.NANO_OF_SECOND, this.nanos);
    }

    public OffsetDateTime atOffset(ZoneOffset zoneOffset) {
        return OffsetDateTime.ofInstant(this, zoneOffset);
    }

    public ZonedDateTime atZone(ZoneId zoneId) {
        return ZonedDateTime.ofInstant(this, zoneId);
    }

    @Override
    public int compareTo(Instant instant) {
        int n = Long.compare(this.seconds, instant.seconds);
        if (n != 0) {
            return n;
        }
        return this.nanos - instant.nanos;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof Instant) {
            object = (Instant)object;
            if (this.seconds != ((Instant)object).seconds || this.nanos != ((Instant)object).nanos) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public int get(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)temporalField).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            ChronoField.INSTANT_SECONDS.checkValidIntValue(this.seconds);
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported field: ");
                        stringBuilder.append(temporalField);
                        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
                    }
                    return this.nanos / 1000000;
                }
                return this.nanos / 1000;
            }
            return this.nanos;
        }
        return this.range(temporalField).checkValidIntValue(temporalField.getFrom(this), temporalField);
    }

    public long getEpochSecond() {
        return this.seconds;
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)temporalField).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            return this.seconds;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported field: ");
                        stringBuilder.append(temporalField);
                        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
                    }
                    return this.nanos / 1000000;
                }
                return this.nanos / 1000;
            }
            return this.nanos;
        }
        return temporalField.getFrom(this);
    }

    public int getNano() {
        return this.nanos;
    }

    public int hashCode() {
        long l = this.seconds;
        return (int)(l ^ l >>> 32) + this.nanos * 51;
    }

    public boolean isAfter(Instant instant) {
        boolean bl = this.compareTo(instant) > 0;
        return bl;
    }

    public boolean isBefore(Instant instant) {
        boolean bl = this.compareTo(instant) < 0;
        return bl;
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        boolean bl = temporalField instanceof ChronoField;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            bl2 = bl3;
            if (temporalField != ChronoField.INSTANT_SECONDS) {
                bl2 = bl3;
                if (temporalField != ChronoField.NANO_OF_SECOND) {
                    bl2 = bl3;
                    if (temporalField != ChronoField.MICRO_OF_SECOND) {
                        bl2 = temporalField == ChronoField.MILLI_OF_SECOND ? bl3 : false;
                    }
                }
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
        boolean bl = temporalUnit instanceof ChronoUnit;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            bl2 = bl3;
            if (!temporalUnit.isTimeBased()) {
                bl2 = temporalUnit == ChronoUnit.DAYS ? bl3 : false;
            }
            return bl2;
        }
        if (temporalUnit == null || !temporalUnit.isSupportedBy(this)) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public Instant minus(long l, TemporalUnit object) {
        object = l == Long.MIN_VALUE ? this.plus(Long.MAX_VALUE, (TemporalUnit)object).plus(1L, (TemporalUnit)object) : this.plus(-l, (TemporalUnit)object);
        return object;
    }

    @Override
    public Instant minus(TemporalAmount temporalAmount) {
        return (Instant)temporalAmount.subtractFrom(this);
    }

    public Instant minusMillis(long l) {
        if (l == Long.MIN_VALUE) {
            return this.plusMillis(Long.MAX_VALUE).plusMillis(1L);
        }
        return this.plusMillis(-l);
    }

    public Instant minusNanos(long l) {
        if (l == Long.MIN_VALUE) {
            return this.plusNanos(Long.MAX_VALUE).plusNanos(1L);
        }
        return this.plusNanos(-l);
    }

    public Instant minusSeconds(long l) {
        if (l == Long.MIN_VALUE) {
            return this.plusSeconds(Long.MAX_VALUE).plusSeconds(1L);
        }
        return this.plusSeconds(-l);
    }

    @Override
    public Instant plus(long l, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            switch ((ChronoUnit)temporalUnit) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported unit: ");
                    stringBuilder.append(temporalUnit);
                    throw new UnsupportedTemporalTypeException(stringBuilder.toString());
                }
                case DAYS: {
                    return this.plusSeconds(Math.multiplyExact(l, 86400L));
                }
                case HALF_DAYS: {
                    return this.plusSeconds(Math.multiplyExact(l, 43200L));
                }
                case HOURS: {
                    return this.plusSeconds(Math.multiplyExact(l, 3600L));
                }
                case MINUTES: {
                    return this.plusSeconds(Math.multiplyExact(l, 60L));
                }
                case SECONDS: {
                    return this.plusSeconds(l);
                }
                case MILLIS: {
                    return this.plusMillis(l);
                }
                case MICROS: {
                    return this.plus(l / 1000000L, l % 1000000L * 1000L);
                }
                case NANOS: 
            }
            return this.plusNanos(l);
        }
        return temporalUnit.addTo(this, l);
    }

    @Override
    public Instant plus(TemporalAmount temporalAmount) {
        return (Instant)temporalAmount.addTo(this);
    }

    public Instant plusMillis(long l) {
        return this.plus(l / 1000L, l % 1000L * 1000000L);
    }

    public Instant plusNanos(long l) {
        return this.plus(0L, l);
    }

    public Instant plusSeconds(long l) {
        return this.plus(l, 0L);
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery == TemporalQueries.precision()) {
            return (R)ChronoUnit.NANOS;
        }
        if (temporalQuery != TemporalQueries.chronology() && temporalQuery != TemporalQueries.zoneId() && temporalQuery != TemporalQueries.zone() && temporalQuery != TemporalQueries.offset() && temporalQuery != TemporalQueries.localDate() && temporalQuery != TemporalQueries.localTime()) {
            return temporalQuery.queryFrom(this);
        }
        return null;
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        return Temporal.super.range(temporalField);
    }

    public long toEpochMilli() {
        long l = this.seconds;
        if (l < 0L && this.nanos > 0) {
            return Math.addExact(Math.multiplyExact(l + 1L, 1000L), (long)(this.nanos / 1000000 - 1000));
        }
        return Math.addExact(Math.multiplyExact(this.seconds, 1000L), (long)(this.nanos / 1000000));
    }

    public String toString() {
        return DateTimeFormatter.ISO_INSTANT.format(this);
    }

    public Instant truncatedTo(TemporalUnit object) {
        if (object == ChronoUnit.NANOS) {
            return this;
        }
        if (((Duration)(object = object.getDuration())).getSeconds() <= 86400L) {
            long l = ((Duration)object).toNanos();
            if (86400000000000L % l == 0L) {
                long l2 = this.seconds % 86400L * 1000000000L + (long)this.nanos;
                return this.plusNanos(l2 / l * l - l2);
            }
            throw new UnsupportedTemporalTypeException("Unit must divide into a standard day without remainder");
        }
        throw new UnsupportedTemporalTypeException("Unit is too large to be used for truncation");
    }

    @Override
    public long until(Temporal object, TemporalUnit temporalUnit) {
        Instant instant = Instant.from((TemporalAccessor)object);
        if (temporalUnit instanceof ChronoUnit) {
            object = (ChronoUnit)temporalUnit;
            switch (1.$SwitchMap$java$time$temporal$ChronoUnit[((Enum)object).ordinal()]) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unsupported unit: ");
                    ((StringBuilder)object).append(temporalUnit);
                    throw new UnsupportedTemporalTypeException(((StringBuilder)object).toString());
                }
                case 8: {
                    return this.secondsUntil(instant) / 86400L;
                }
                case 7: {
                    return this.secondsUntil(instant) / 43200L;
                }
                case 6: {
                    return this.secondsUntil(instant) / 3600L;
                }
                case 5: {
                    return this.secondsUntil(instant) / 60L;
                }
                case 4: {
                    return this.secondsUntil(instant);
                }
                case 3: {
                    return Math.subtractExact(instant.toEpochMilli(), this.toEpochMilli());
                }
                case 2: {
                    return this.nanosUntil(instant) / 1000L;
                }
                case 1: 
            }
            return this.nanosUntil(instant);
        }
        return temporalUnit.between(this, instant);
    }

    @Override
    public Instant with(TemporalAdjuster temporalAdjuster) {
        return (Instant)temporalAdjuster.adjustInto(this);
    }

    @Override
    public Instant with(TemporalField object, long l) {
        if (object instanceof ChronoField) {
            Object object2 = (ChronoField)object;
            ((ChronoField)object2).checkValidValue(l);
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((Enum)object2).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            object = l != this.seconds ? Instant.create(l, this.nanos) : this;
                            return object;
                        }
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Unsupported field: ");
                        ((StringBuilder)object2).append(object);
                        throw new UnsupportedTemporalTypeException(((StringBuilder)object2).toString());
                    }
                    n = (int)l * 1000000;
                    object = n != this.nanos ? Instant.create(this.seconds, n) : this;
                    return object;
                }
                n = (int)l * 1000;
                object = n != this.nanos ? Instant.create(this.seconds, n) : this;
                return object;
            }
            object = l != (long)this.nanos ? Instant.create(this.seconds, (int)l) : this;
            return object;
        }
        return object.adjustInto(this, l);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(this.seconds);
        dataOutput.writeInt(this.nanos);
    }

}

