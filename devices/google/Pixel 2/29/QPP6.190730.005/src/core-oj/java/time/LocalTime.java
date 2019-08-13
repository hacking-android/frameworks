/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$2Dm_gBEmfWAFyI8wDj_HTrgAgUc
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.Ser;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time._$$Lambda$2Dm_gBEmfWAFyI8wDj_HTrgAgUc;
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

public final class LocalTime
implements Temporal,
TemporalAdjuster,
Comparable<LocalTime>,
Serializable {
    private static final LocalTime[] HOURS;
    static final int HOURS_PER_DAY = 24;
    public static final LocalTime MAX;
    static final long MICROS_PER_DAY = 86400000000L;
    public static final LocalTime MIDNIGHT;
    static final long MILLIS_PER_DAY = 86400000L;
    public static final LocalTime MIN;
    static final int MINUTES_PER_DAY = 1440;
    static final int MINUTES_PER_HOUR = 60;
    static final long NANOS_PER_DAY = 86400000000000L;
    static final long NANOS_PER_HOUR = 3600000000000L;
    static final long NANOS_PER_MINUTE = 60000000000L;
    static final long NANOS_PER_SECOND = 1000000000L;
    public static final LocalTime NOON;
    static final int SECONDS_PER_DAY = 86400;
    static final int SECONDS_PER_HOUR = 3600;
    static final int SECONDS_PER_MINUTE = 60;
    private static final long serialVersionUID = 6414437269572265201L;
    private final byte hour;
    private final byte minute;
    private final int nano;
    private final byte second;

    static {
        LocalTime[] arrlocalTime;
        HOURS = new LocalTime[24];
        for (int i = 0; i < (arrlocalTime = HOURS).length; ++i) {
            arrlocalTime[i] = new LocalTime(i, 0, 0, 0);
        }
        MIDNIGHT = arrlocalTime[0];
        NOON = arrlocalTime[12];
        MIN = arrlocalTime[0];
        MAX = new LocalTime(23, 59, 59, 999999999);
    }

    private LocalTime(int n, int n2, int n3, int n4) {
        this.hour = (byte)n;
        this.minute = (byte)n2;
        this.second = (byte)n3;
        this.nano = n4;
    }

    private static LocalTime create(int n, int n2, int n3, int n4) {
        if ((n2 | n3 | n4) == 0) {
            return HOURS[n];
        }
        return new LocalTime(n, n2, n3, n4);
    }

    public static LocalTime from(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        Serializable serializable = temporalAccessor.query(TemporalQueries.localTime());
        if (serializable != null) {
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unable to obtain LocalTime from TemporalAccessor: ");
        ((StringBuilder)serializable).append(temporalAccessor);
        ((StringBuilder)serializable).append(" of type ");
        ((StringBuilder)serializable).append(temporalAccessor.getClass().getName());
        throw new DateTimeException(((StringBuilder)serializable).toString());
    }

    private int get0(TemporalField temporalField) {
        int n = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)temporalField).ordinal()];
        int n2 = 12;
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported field: ");
                stringBuilder.append(temporalField);
                throw new UnsupportedTemporalTypeException(stringBuilder.toString());
            }
            case 15: {
                return this.hour / 12;
            }
            case 14: {
                n2 = n = (int)this.hour;
                if (n == 0) {
                    n2 = 24;
                }
                return n2;
            }
            case 13: {
                return this.hour;
            }
            case 12: {
                n = this.hour % 12;
                if (n % 12 != 0) {
                    n2 = n;
                }
                return n2;
            }
            case 11: {
                return this.hour % 12;
            }
            case 10: {
                return this.hour * 60 + this.minute;
            }
            case 9: {
                return this.minute;
            }
            case 8: {
                return this.toSecondOfDay();
            }
            case 7: {
                return this.second;
            }
            case 6: {
                return (int)(this.toNanoOfDay() / 1000000L);
            }
            case 5: {
                return this.nano / 1000000;
            }
            case 4: {
                throw new UnsupportedTemporalTypeException("Invalid field 'MicroOfDay' for get() method, use getLong() instead");
            }
            case 3: {
                return this.nano / 1000;
            }
            case 2: {
                throw new UnsupportedTemporalTypeException("Invalid field 'NanoOfDay' for get() method, use getLong() instead");
            }
            case 1: 
        }
        return this.nano;
    }

    public static LocalTime now() {
        return LocalTime.now(Clock.systemDefaultZone());
    }

    public static LocalTime now(Clock object) {
        Objects.requireNonNull(object, "clock");
        Instant instant = ((Clock)object).instant();
        object = ((Clock)object).getZone().getRules().getOffset(instant);
        return LocalTime.ofNanoOfDay((long)((int)Math.floorMod(instant.getEpochSecond() + (long)((ZoneOffset)object).getTotalSeconds(), 86400L)) * 1000000000L + (long)instant.getNano());
    }

    public static LocalTime now(ZoneId zoneId) {
        return LocalTime.now(Clock.system(zoneId));
    }

    public static LocalTime of(int n, int n2) {
        ChronoField.HOUR_OF_DAY.checkValidValue(n);
        if (n2 == 0) {
            return HOURS[n];
        }
        ChronoField.MINUTE_OF_HOUR.checkValidValue(n2);
        return new LocalTime(n, n2, 0, 0);
    }

    public static LocalTime of(int n, int n2, int n3) {
        ChronoField.HOUR_OF_DAY.checkValidValue(n);
        if ((n2 | n3) == 0) {
            return HOURS[n];
        }
        ChronoField.MINUTE_OF_HOUR.checkValidValue(n2);
        ChronoField.SECOND_OF_MINUTE.checkValidValue(n3);
        return new LocalTime(n, n2, n3, 0);
    }

    public static LocalTime of(int n, int n2, int n3, int n4) {
        ChronoField.HOUR_OF_DAY.checkValidValue(n);
        ChronoField.MINUTE_OF_HOUR.checkValidValue(n2);
        ChronoField.SECOND_OF_MINUTE.checkValidValue(n3);
        ChronoField.NANO_OF_SECOND.checkValidValue(n4);
        return LocalTime.create(n, n2, n3, n4);
    }

    public static LocalTime ofNanoOfDay(long l) {
        ChronoField.NANO_OF_DAY.checkValidValue(l);
        int n = (int)(l / 3600000000000L);
        int n2 = (int)((l -= (long)n * 3600000000000L) / 60000000000L);
        int n3 = (int)((l -= (long)n2 * 60000000000L) / 1000000000L);
        return LocalTime.create(n, n2, n3, (int)(l - (long)n3 * 1000000000L));
    }

    public static LocalTime ofSecondOfDay(long l) {
        ChronoField.SECOND_OF_DAY.checkValidValue(l);
        int n = (int)(l / 3600L);
        int n2 = (int)((l -= (long)(n * 3600)) / 60L);
        return LocalTime.create(n, n2, (int)(l - (long)(n2 * 60)), 0);
    }

    public static LocalTime parse(CharSequence charSequence) {
        return LocalTime.parse(charSequence, DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static LocalTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (LocalTime)dateTimeFormatter.parse(charSequence, _$$Lambda$2Dm_gBEmfWAFyI8wDj_HTrgAgUc.INSTANCE);
    }

    static LocalTime readExternal(DataInput dataInput) throws IOException {
        byte by = dataInput.readByte();
        byte by2 = 0;
        int n = 0;
        int n2 = 0;
        if (by >= 0 && (by2 = dataInput.readByte()) >= 0 && (n = (int)dataInput.readByte()) >= 0) {
            n2 = dataInput.readInt();
        }
        return LocalTime.of(by, by2, n, n2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser(4, this);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.NANO_OF_DAY, this.toNanoOfDay());
    }

    public LocalDateTime atDate(LocalDate localDate) {
        return LocalDateTime.of(localDate, this);
    }

    public OffsetTime atOffset(ZoneOffset zoneOffset) {
        return OffsetTime.of(this, zoneOffset);
    }

    @Override
    public int compareTo(LocalTime localTime) {
        int n;
        int n2 = n = Integer.compare(this.hour, localTime.hour);
        if (n == 0) {
            n2 = n = Integer.compare(this.minute, localTime.minute);
            if (n == 0) {
                n2 = n = Integer.compare(this.second, localTime.second);
                if (n == 0) {
                    n2 = Integer.compare(this.nano, localTime.nano);
                }
            }
        }
        return n2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof LocalTime) {
            object = (LocalTime)object;
            if (this.hour != ((LocalTime)object).hour || this.minute != ((LocalTime)object).minute || this.second != ((LocalTime)object).second || this.nano != ((LocalTime)object).nano) {
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
        if (temporalField instanceof ChronoField) {
            return this.get0(temporalField);
        }
        return Temporal.super.get(temporalField);
    }

    public int getHour() {
        return this.hour;
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (temporalField == ChronoField.NANO_OF_DAY) {
                return this.toNanoOfDay();
            }
            if (temporalField == ChronoField.MICRO_OF_DAY) {
                return this.toNanoOfDay() / 1000L;
            }
            return this.get0(temporalField);
        }
        return temporalField.getFrom(this);
    }

    public int getMinute() {
        return this.minute;
    }

    public int getNano() {
        return this.nano;
    }

    public int getSecond() {
        return this.second;
    }

    public int hashCode() {
        long l = this.toNanoOfDay();
        return (int)(l >>> 32 ^ l);
    }

    public boolean isAfter(LocalTime localTime) {
        boolean bl = this.compareTo(localTime) > 0;
        return bl;
    }

    public boolean isBefore(LocalTime localTime) {
        boolean bl = this.compareTo(localTime) < 0;
        return bl;
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            return temporalField.isTimeBased();
        }
        boolean bl = temporalField != null && temporalField.isSupportedBy(this);
        return bl;
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
    public LocalTime minus(long l, TemporalUnit object) {
        object = l == Long.MIN_VALUE ? this.plus(Long.MAX_VALUE, (TemporalUnit)object).plus(1L, (TemporalUnit)object) : this.plus(-l, (TemporalUnit)object);
        return object;
    }

    @Override
    public LocalTime minus(TemporalAmount temporalAmount) {
        return (LocalTime)temporalAmount.subtractFrom(this);
    }

    public LocalTime minusHours(long l) {
        return this.plusHours(-(l % 24L));
    }

    public LocalTime minusMinutes(long l) {
        return this.plusMinutes(-(l % 1440L));
    }

    public LocalTime minusNanos(long l) {
        return this.plusNanos(-(l % 86400000000000L));
    }

    public LocalTime minusSeconds(long l) {
        return this.plusSeconds(-(l % 86400L));
    }

    @Override
    public LocalTime plus(long l, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            switch ((ChronoUnit)temporalUnit) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported unit: ");
                    stringBuilder.append(temporalUnit);
                    throw new UnsupportedTemporalTypeException(stringBuilder.toString());
                }
                case HALF_DAYS: {
                    return this.plusHours(l % 2L * 12L);
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
                    return this.plusNanos(l % 86400000L * 1000000L);
                }
                case MICROS: {
                    return this.plusNanos(l % 86400000000L * 1000L);
                }
                case NANOS: 
            }
            return this.plusNanos(l);
        }
        return temporalUnit.addTo(this, l);
    }

    @Override
    public LocalTime plus(TemporalAmount temporalAmount) {
        return (LocalTime)temporalAmount.addTo(this);
    }

    public LocalTime plusHours(long l) {
        if (l == 0L) {
            return this;
        }
        return LocalTime.create(((int)(l % 24L) + this.hour + 24) % 24, this.minute, this.second, this.nano);
    }

    public LocalTime plusMinutes(long l) {
        if (l == 0L) {
            return this;
        }
        int n = this.hour * 60 + this.minute;
        int n2 = ((int)(l % 1440L) + n + 1440) % 1440;
        if (n == n2) {
            return this;
        }
        return LocalTime.create(n2 / 60, n2 % 60, this.second, this.nano);
    }

    public LocalTime plusNanos(long l) {
        if (l == 0L) {
            return this;
        }
        long l2 = this.toNanoOfDay();
        if (l2 == (l = (l % 86400000000000L + l2 + 86400000000000L) % 86400000000000L)) {
            return this;
        }
        return LocalTime.create((int)(l / 3600000000000L), (int)(l / 60000000000L % 60L), (int)(l / 1000000000L % 60L), (int)(l % 1000000000L));
    }

    public LocalTime plusSeconds(long l) {
        if (l == 0L) {
            return this;
        }
        int n = this.hour * 3600 + this.minute * 60 + this.second;
        int n2 = ((int)(l % 86400L) + n + 86400) % 86400;
        if (n == n2) {
            return this;
        }
        return LocalTime.create(n2 / 3600, n2 / 60 % 60, n2 % 60, this.nano);
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery != TemporalQueries.chronology() && temporalQuery != TemporalQueries.zoneId() && temporalQuery != TemporalQueries.zone() && temporalQuery != TemporalQueries.offset()) {
            if (temporalQuery == TemporalQueries.localTime()) {
                return (R)this;
            }
            if (temporalQuery == TemporalQueries.localDate()) {
                return null;
            }
            if (temporalQuery == TemporalQueries.precision()) {
                return (R)ChronoUnit.NANOS;
            }
            return temporalQuery.queryFrom(this);
        }
        return null;
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        return Temporal.super.range(temporalField);
    }

    public long toNanoOfDay() {
        return (long)this.hour * 3600000000000L + (long)this.minute * 60000000000L + (long)this.second * 1000000000L + (long)this.nano;
    }

    public int toSecondOfDay() {
        return this.hour * 3600 + this.minute * 60 + this.second;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(18);
        byte by = this.hour;
        byte by2 = this.minute;
        byte by3 = this.second;
        int n = this.nano;
        String string = by < 10 ? "0" : "";
        stringBuilder.append(string);
        stringBuilder.append(by);
        String string2 = ":0";
        string = by2 < 10 ? ":0" : ":";
        stringBuilder.append(string);
        stringBuilder.append(by2);
        if (by3 > 0 || n > 0) {
            string = by3 < 10 ? string2 : ":";
            stringBuilder.append(string);
            stringBuilder.append(by3);
            if (n > 0) {
                stringBuilder.append('.');
                if (n % 1000000 == 0) {
                    stringBuilder.append(Integer.toString(n / 1000000 + 1000).substring(1));
                } else if (n % 1000 == 0) {
                    stringBuilder.append(Integer.toString(n / 1000 + 1000000).substring(1));
                } else {
                    stringBuilder.append(Integer.toString(1000000000 + n).substring(1));
                }
            }
        }
        return stringBuilder.toString();
    }

    public LocalTime truncatedTo(TemporalUnit object) {
        if (object == ChronoUnit.NANOS) {
            return this;
        }
        if (((Duration)(object = object.getDuration())).getSeconds() <= 86400L) {
            long l = ((Duration)object).toNanos();
            if (86400000000000L % l == 0L) {
                return LocalTime.ofNanoOfDay(this.toNanoOfDay() / l * l);
            }
            throw new UnsupportedTemporalTypeException("Unit must divide into a standard day without remainder");
        }
        throw new UnsupportedTemporalTypeException("Unit is too large to be used for truncation");
    }

    @Override
    public long until(Temporal object, TemporalUnit temporalUnit) {
        object = LocalTime.from((TemporalAccessor)object);
        if (temporalUnit instanceof ChronoUnit) {
            long l = ((LocalTime)object).toNanoOfDay() - this.toNanoOfDay();
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
    public LocalTime with(TemporalAdjuster temporalAdjuster) {
        if (temporalAdjuster instanceof LocalTime) {
            return (LocalTime)temporalAdjuster;
        }
        return (LocalTime)temporalAdjuster.adjustInto(this);
    }

    @Override
    public LocalTime with(TemporalField temporalField, long l) {
        if (temporalField instanceof ChronoField) {
            Object object = (ChronoField)temporalField;
            ((ChronoField)object).checkValidValue(l);
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((Enum)object).ordinal()];
            long l2 = 0L;
            switch (n) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unsupported field: ");
                    ((StringBuilder)object).append(temporalField);
                    throw new UnsupportedTemporalTypeException(((StringBuilder)object).toString());
                }
                case 15: {
                    return this.plusHours((l - (long)(this.hour / 12)) * 12L);
                }
                case 14: {
                    if (l != 24L) {
                        l2 = l;
                    }
                    return this.withHour((int)l2);
                }
                case 13: {
                    return this.withHour((int)l);
                }
                case 12: {
                    if (l != 12L) {
                        l2 = l;
                    }
                    return this.plusHours(l2 - (long)(this.hour % 12));
                }
                case 11: {
                    return this.plusHours(l - (long)(this.hour % 12));
                }
                case 10: {
                    return this.plusMinutes(l - (long)(this.hour * 60 + this.minute));
                }
                case 9: {
                    return this.withMinute((int)l);
                }
                case 8: {
                    return this.plusSeconds(l - (long)this.toSecondOfDay());
                }
                case 7: {
                    return this.withSecond((int)l);
                }
                case 6: {
                    return LocalTime.ofNanoOfDay(1000000L * l);
                }
                case 5: {
                    return this.withNano((int)l * 1000000);
                }
                case 4: {
                    return LocalTime.ofNanoOfDay(1000L * l);
                }
                case 3: {
                    return this.withNano((int)l * 1000);
                }
                case 2: {
                    return LocalTime.ofNanoOfDay(l);
                }
                case 1: 
            }
            return this.withNano((int)l);
        }
        return temporalField.adjustInto(this, l);
    }

    public LocalTime withHour(int n) {
        if (this.hour == n) {
            return this;
        }
        ChronoField.HOUR_OF_DAY.checkValidValue(n);
        return LocalTime.create(n, this.minute, this.second, this.nano);
    }

    public LocalTime withMinute(int n) {
        if (this.minute == n) {
            return this;
        }
        ChronoField.MINUTE_OF_HOUR.checkValidValue(n);
        return LocalTime.create(this.hour, n, this.second, this.nano);
    }

    public LocalTime withNano(int n) {
        if (this.nano == n) {
            return this;
        }
        ChronoField.NANO_OF_SECOND.checkValidValue(n);
        return LocalTime.create(this.hour, this.minute, this.second, n);
    }

    public LocalTime withSecond(int n) {
        if (this.second == n) {
            return this;
        }
        ChronoField.SECOND_OF_MINUTE.checkValidValue(n);
        return LocalTime.create(this.hour, this.minute, n, this.nano);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        if (this.nano == 0) {
            if (this.second == 0) {
                if (this.minute == 0) {
                    dataOutput.writeByte(this.hour);
                } else {
                    dataOutput.writeByte(this.hour);
                    dataOutput.writeByte(this.minute);
                }
            } else {
                dataOutput.writeByte(this.hour);
                dataOutput.writeByte(this.minute);
                dataOutput.writeByte(this.second);
            }
        } else {
            dataOutput.writeByte(this.hour);
            dataOutput.writeByte(this.minute);
            dataOutput.writeByte(this.second);
            dataOutput.writeInt(this.nano);
        }
    }

}

