/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$JBWLm7jbzHiLhHxYdB_IuO_vFO8
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
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.Ser;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time._$$Lambda$JBWLm7jbzHiLhHxYdB_IuO_vFO8;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
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
import java.time.temporal.ValueRange;
import java.time.zone.ZoneRules;
import java.util.Objects;

public final class LocalDateTime
implements Temporal,
TemporalAdjuster,
ChronoLocalDateTime<LocalDate>,
Serializable {
    public static final LocalDateTime MAX;
    public static final LocalDateTime MIN;
    private static final long serialVersionUID = 6207766400415563566L;
    private final LocalDate date;
    private final LocalTime time;

    static {
        MIN = LocalDateTime.of(LocalDate.MIN, LocalTime.MIN);
        MAX = LocalDateTime.of(LocalDate.MAX, LocalTime.MAX);
    }

    private LocalDateTime(LocalDate localDate, LocalTime localTime) {
        this.date = localDate;
        this.time = localTime;
    }

    private int compareTo0(LocalDateTime localDateTime) {
        int n;
        int n2 = n = this.date.compareTo0(localDateTime.toLocalDate());
        if (n == 0) {
            n2 = this.time.compareTo(localDateTime.toLocalTime());
        }
        return n2;
    }

    public static LocalDateTime from(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof LocalDateTime) {
            return (LocalDateTime)temporalAccessor;
        }
        if (temporalAccessor instanceof ZonedDateTime) {
            return ((ZonedDateTime)temporalAccessor).toLocalDateTime();
        }
        if (temporalAccessor instanceof OffsetDateTime) {
            return ((OffsetDateTime)temporalAccessor).toLocalDateTime();
        }
        try {
            LocalDateTime localDateTime = new LocalDateTime(LocalDate.from(temporalAccessor), LocalTime.from(temporalAccessor));
            return localDateTime;
        }
        catch (DateTimeException dateTimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to obtain LocalDateTime from TemporalAccessor: ");
            stringBuilder.append(temporalAccessor);
            stringBuilder.append(" of type ");
            stringBuilder.append(temporalAccessor.getClass().getName());
            throw new DateTimeException(stringBuilder.toString(), dateTimeException);
        }
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(Clock.systemDefaultZone());
    }

    public static LocalDateTime now(Clock object) {
        Objects.requireNonNull(object, "clock");
        Instant instant = ((Clock)object).instant();
        object = ((Clock)object).getZone().getRules().getOffset(instant);
        return LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), (ZoneOffset)object);
    }

    public static LocalDateTime now(ZoneId zoneId) {
        return LocalDateTime.now(Clock.system(zoneId));
    }

    public static LocalDateTime of(int n, int n2, int n3, int n4, int n5) {
        return new LocalDateTime(LocalDate.of(n, n2, n3), LocalTime.of(n4, n5));
    }

    public static LocalDateTime of(int n, int n2, int n3, int n4, int n5, int n6) {
        return new LocalDateTime(LocalDate.of(n, n2, n3), LocalTime.of(n4, n5, n6));
    }

    public static LocalDateTime of(int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        return new LocalDateTime(LocalDate.of(n, n2, n3), LocalTime.of(n4, n5, n6, n7));
    }

    public static LocalDateTime of(int n, Month month, int n2, int n3, int n4) {
        return new LocalDateTime(LocalDate.of(n, month, n2), LocalTime.of(n3, n4));
    }

    public static LocalDateTime of(int n, Month month, int n2, int n3, int n4, int n5) {
        return new LocalDateTime(LocalDate.of(n, month, n2), LocalTime.of(n3, n4, n5));
    }

    public static LocalDateTime of(int n, Month month, int n2, int n3, int n4, int n5, int n6) {
        return new LocalDateTime(LocalDate.of(n, month, n2), LocalTime.of(n3, n4, n5, n6));
    }

    public static LocalDateTime of(LocalDate localDate, LocalTime localTime) {
        Objects.requireNonNull(localDate, "date");
        Objects.requireNonNull(localTime, "time");
        return new LocalDateTime(localDate, localTime);
    }

    public static LocalDateTime ofEpochSecond(long l, int n, ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        ChronoField.NANO_OF_SECOND.checkValidValue(n);
        l = (long)zoneOffset.getTotalSeconds() + l;
        long l2 = Math.floorDiv(l, 86400L);
        int n2 = (int)Math.floorMod(l, 86400L);
        return new LocalDateTime(LocalDate.ofEpochDay(l2), LocalTime.ofNanoOfDay((long)n2 * 1000000000L + (long)n));
    }

    public static LocalDateTime ofInstant(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        zoneId = zoneId.getRules().getOffset(instant);
        return LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), (ZoneOffset)zoneId);
    }

    public static LocalDateTime parse(CharSequence charSequence) {
        return LocalDateTime.parse(charSequence, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static LocalDateTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (LocalDateTime)dateTimeFormatter.parse(charSequence, _$$Lambda$JBWLm7jbzHiLhHxYdB_IuO_vFO8.INSTANCE);
    }

    private LocalDateTime plusWithOverflow(LocalDate localDate, long l, long l2, long l3, long l4, int n) {
        if ((l | l2 | l3 | l4) == 0L) {
            return this.with(localDate, this.time);
        }
        long l5 = l4 / 86400000000000L;
        long l6 = l3 / 86400L;
        long l7 = l2 / 1440L;
        long l8 = l / 24L;
        long l9 = n;
        long l10 = this.time.toNanoOfDay();
        l2 = (long)n * (l4 % 86400000000000L + l3 % 86400L * 1000000000L + l2 % 1440L * 60000000000L + l % 24L * 3600000000000L) + l10;
        l = Math.floorDiv(l2, 86400000000000L);
        LocalTime localTime = (l2 = Math.floorMod(l2, 86400000000000L)) == l10 ? this.time : LocalTime.ofNanoOfDay(l2);
        return this.with(localDate.plusDays((l5 + l6 + l7 + l8) * l9 + l), localTime);
    }

    static LocalDateTime readExternal(DataInput dataInput) throws IOException {
        return LocalDateTime.of(LocalDate.readExternal(dataInput), LocalTime.readExternal(dataInput));
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private LocalDateTime with(LocalDate localDate, LocalTime localTime) {
        if (this.date == localDate && this.time == localTime) {
            return this;
        }
        return new LocalDateTime(localDate, localTime);
    }

    private Object writeReplace() {
        return new Ser(5, this);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        return ChronoLocalDateTime.super.adjustInto(temporal);
    }

    public OffsetDateTime atOffset(ZoneOffset zoneOffset) {
        return OffsetDateTime.of(this, zoneOffset);
    }

    public ZonedDateTime atZone(ZoneId zoneId) {
        return ZonedDateTime.of(this, zoneId);
    }

    @Override
    public int compareTo(ChronoLocalDateTime<?> chronoLocalDateTime) {
        if (chronoLocalDateTime instanceof LocalDateTime) {
            return this.compareTo0((LocalDateTime)chronoLocalDateTime);
        }
        return ChronoLocalDateTime.super.compareTo(chronoLocalDateTime);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof LocalDateTime) {
            object = (LocalDateTime)object;
            if (!this.date.equals(((LocalDateTime)object).date) || !this.time.equals(((LocalDateTime)object).time)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public String format(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return dateTimeFormatter.format(this);
    }

    @Override
    public int get(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            int n = ((ChronoField)temporalField).isTimeBased() ? this.time.get(temporalField) : this.date.get(temporalField);
            return n;
        }
        return ChronoLocalDateTime.super.get(temporalField);
    }

    public int getDayOfMonth() {
        return this.date.getDayOfMonth();
    }

    public DayOfWeek getDayOfWeek() {
        return this.date.getDayOfWeek();
    }

    public int getDayOfYear() {
        return this.date.getDayOfYear();
    }

    public int getHour() {
        return this.time.getHour();
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            long l = ((ChronoField)temporalField).isTimeBased() ? this.time.getLong(temporalField) : this.date.getLong(temporalField);
            return l;
        }
        return temporalField.getFrom(this);
    }

    public int getMinute() {
        return this.time.getMinute();
    }

    public Month getMonth() {
        return this.date.getMonth();
    }

    public int getMonthValue() {
        return this.date.getMonthValue();
    }

    public int getNano() {
        return this.time.getNano();
    }

    public int getSecond() {
        return this.time.getSecond();
    }

    public int getYear() {
        return this.date.getYear();
    }

    @Override
    public int hashCode() {
        return this.date.hashCode() ^ this.time.hashCode();
    }

    @Override
    public boolean isAfter(ChronoLocalDateTime<?> chronoLocalDateTime) {
        if (chronoLocalDateTime instanceof LocalDateTime) {
            boolean bl = this.compareTo0((LocalDateTime)chronoLocalDateTime) > 0;
            return bl;
        }
        return ChronoLocalDateTime.super.isAfter(chronoLocalDateTime);
    }

    @Override
    public boolean isBefore(ChronoLocalDateTime<?> chronoLocalDateTime) {
        if (chronoLocalDateTime instanceof LocalDateTime) {
            boolean bl = this.compareTo0((LocalDateTime)chronoLocalDateTime) < 0;
            return bl;
        }
        return ChronoLocalDateTime.super.isBefore(chronoLocalDateTime);
    }

    @Override
    public boolean isEqual(ChronoLocalDateTime<?> chronoLocalDateTime) {
        if (chronoLocalDateTime instanceof LocalDateTime) {
            boolean bl = this.compareTo0((LocalDateTime)chronoLocalDateTime) == 0;
            return bl;
        }
        return ChronoLocalDateTime.super.isEqual(chronoLocalDateTime);
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
    public boolean isSupported(TemporalUnit temporalUnit) {
        return ChronoLocalDateTime.super.isSupported(temporalUnit);
    }

    @Override
    public LocalDateTime minus(long l, TemporalUnit object) {
        object = l == Long.MIN_VALUE ? this.plus(Long.MAX_VALUE, (TemporalUnit)object).plus(1L, (TemporalUnit)object) : this.plus(-l, (TemporalUnit)object);
        return object;
    }

    @Override
    public LocalDateTime minus(TemporalAmount temporalAmount) {
        if (temporalAmount instanceof Period) {
            temporalAmount = (Period)temporalAmount;
            return this.with(this.date.minus(temporalAmount), this.time);
        }
        Objects.requireNonNull(temporalAmount, "amountToSubtract");
        return (LocalDateTime)temporalAmount.subtractFrom(this);
    }

    public LocalDateTime minusDays(long l) {
        LocalDateTime localDateTime = l == Long.MIN_VALUE ? this.plusDays(Long.MAX_VALUE).plusDays(1L) : this.plusDays(-l);
        return localDateTime;
    }

    public LocalDateTime minusHours(long l) {
        return this.plusWithOverflow(this.date, l, 0L, 0L, 0L, -1);
    }

    public LocalDateTime minusMinutes(long l) {
        return this.plusWithOverflow(this.date, 0L, l, 0L, 0L, -1);
    }

    public LocalDateTime minusMonths(long l) {
        LocalDateTime localDateTime = l == Long.MIN_VALUE ? this.plusMonths(Long.MAX_VALUE).plusMonths(1L) : this.plusMonths(-l);
        return localDateTime;
    }

    public LocalDateTime minusNanos(long l) {
        return this.plusWithOverflow(this.date, 0L, 0L, 0L, l, -1);
    }

    public LocalDateTime minusSeconds(long l) {
        return this.plusWithOverflow(this.date, 0L, 0L, l, 0L, -1);
    }

    public LocalDateTime minusWeeks(long l) {
        LocalDateTime localDateTime = l == Long.MIN_VALUE ? this.plusWeeks(Long.MAX_VALUE).plusWeeks(1L) : this.plusWeeks(-l);
        return localDateTime;
    }

    public LocalDateTime minusYears(long l) {
        LocalDateTime localDateTime = l == Long.MIN_VALUE ? this.plusYears(Long.MAX_VALUE).plusYears(1L) : this.plusYears(-l);
        return localDateTime;
    }

    @Override
    public LocalDateTime plus(long l, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            ChronoUnit chronoUnit = (ChronoUnit)temporalUnit;
            switch (chronoUnit) {
                default: {
                    return this.with(this.date.plus(l, temporalUnit), this.time);
                }
                case HALF_DAYS: {
                    return this.plusDays(l / 256L).plusHours(l % 256L * 12L);
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
                    return this.plusDays(l / 86400000L).plusNanos(l % 86400000L * 1000000L);
                }
                case MICROS: {
                    return this.plusDays(l / 86400000000L).plusNanos(l % 86400000000L * 1000L);
                }
                case NANOS: 
            }
            return this.plusNanos(l);
        }
        return temporalUnit.addTo(this, l);
    }

    @Override
    public LocalDateTime plus(TemporalAmount temporalAmount) {
        if (temporalAmount instanceof Period) {
            temporalAmount = (Period)temporalAmount;
            return this.with(this.date.plus(temporalAmount), this.time);
        }
        Objects.requireNonNull(temporalAmount, "amountToAdd");
        return (LocalDateTime)temporalAmount.addTo(this);
    }

    public LocalDateTime plusDays(long l) {
        return this.with(this.date.plusDays(l), this.time);
    }

    public LocalDateTime plusHours(long l) {
        return this.plusWithOverflow(this.date, l, 0L, 0L, 0L, 1);
    }

    public LocalDateTime plusMinutes(long l) {
        return this.plusWithOverflow(this.date, 0L, l, 0L, 0L, 1);
    }

    public LocalDateTime plusMonths(long l) {
        return this.with(this.date.plusMonths(l), this.time);
    }

    public LocalDateTime plusNanos(long l) {
        return this.plusWithOverflow(this.date, 0L, 0L, 0L, l, 1);
    }

    public LocalDateTime plusSeconds(long l) {
        return this.plusWithOverflow(this.date, 0L, 0L, l, 0L, 1);
    }

    public LocalDateTime plusWeeks(long l) {
        return this.with(this.date.plusWeeks(l), this.time);
    }

    public LocalDateTime plusYears(long l) {
        return this.with(this.date.plusYears(l), this.time);
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery == TemporalQueries.localDate()) {
            return (R)this.date;
        }
        return ChronoLocalDateTime.super.query(temporalQuery);
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
    public LocalDate toLocalDate() {
        return this.date;
    }

    @Override
    public LocalTime toLocalTime() {
        return this.time;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.date.toString());
        stringBuilder.append('T');
        stringBuilder.append(this.time.toString());
        return stringBuilder.toString();
    }

    public LocalDateTime truncatedTo(TemporalUnit temporalUnit) {
        return this.with(this.date, this.time.truncatedTo(temporalUnit));
    }

    @Override
    public long until(Temporal temporal, TemporalUnit temporalUnit) {
        LocalDateTime localDateTime = LocalDateTime.from(temporal);
        if (temporalUnit instanceof ChronoUnit) {
            if (temporalUnit.isTimeBased()) {
                long l = this.date.daysUntil(localDateTime.date);
                if (l == 0L) {
                    return this.time.until(localDateTime.time, temporalUnit);
                }
                long l2 = localDateTime.time.toNanoOfDay() - this.time.toNanoOfDay();
                if (l > 0L) {
                    --l;
                    l2 += 86400000000000L;
                } else {
                    ++l;
                    l2 -= 86400000000000L;
                }
                switch ((ChronoUnit)temporalUnit) {
                    default: {
                        break;
                    }
                    case HALF_DAYS: {
                        l = Math.multiplyExact(l, 2L);
                        l2 /= 43200000000000L;
                        break;
                    }
                    case HOURS: {
                        l = Math.multiplyExact(l, 24L);
                        l2 /= 3600000000000L;
                        break;
                    }
                    case MINUTES: {
                        l = Math.multiplyExact(l, 1440L);
                        l2 /= 60000000000L;
                        break;
                    }
                    case SECONDS: {
                        l = Math.multiplyExact(l, 86400L);
                        l2 /= 1000000000L;
                        break;
                    }
                    case MILLIS: {
                        l = Math.multiplyExact(l, 86400000L);
                        l2 /= 1000000L;
                        break;
                    }
                    case MICROS: {
                        l = Math.multiplyExact(l, 86400000000L);
                        l2 /= 1000L;
                        break;
                    }
                    case NANOS: {
                        l = Math.multiplyExact(l, 86400000000000L);
                    }
                }
                return Math.addExact(l, l2);
            }
            LocalDate localDate = localDateTime.date;
            if (localDate.isAfter(this.date) && localDateTime.time.isBefore(this.time)) {
                temporal = localDate.minusDays(1L);
            } else {
                temporal = localDate;
                if (localDate.isBefore(this.date)) {
                    temporal = localDate;
                    if (localDateTime.time.isAfter(this.time)) {
                        temporal = localDate.plusDays(1L);
                    }
                }
            }
            return this.date.until(temporal, temporalUnit);
        }
        return temporalUnit.between(this, localDateTime);
    }

    @Override
    public LocalDateTime with(TemporalAdjuster temporalAdjuster) {
        if (temporalAdjuster instanceof LocalDate) {
            return this.with((LocalDate)temporalAdjuster, this.time);
        }
        if (temporalAdjuster instanceof LocalTime) {
            return this.with(this.date, (LocalTime)temporalAdjuster);
        }
        if (temporalAdjuster instanceof LocalDateTime) {
            return (LocalDateTime)temporalAdjuster;
        }
        return (LocalDateTime)temporalAdjuster.adjustInto(this);
    }

    @Override
    public LocalDateTime with(TemporalField temporalField, long l) {
        if (temporalField instanceof ChronoField) {
            if (((ChronoField)temporalField).isTimeBased()) {
                return this.with(this.date, this.time.with(temporalField, l));
            }
            return this.with(this.date.with(temporalField, l), this.time);
        }
        return temporalField.adjustInto(this, l);
    }

    public LocalDateTime withDayOfMonth(int n) {
        return this.with(this.date.withDayOfMonth(n), this.time);
    }

    public LocalDateTime withDayOfYear(int n) {
        return this.with(this.date.withDayOfYear(n), this.time);
    }

    public LocalDateTime withHour(int n) {
        LocalTime localTime = this.time.withHour(n);
        return this.with(this.date, localTime);
    }

    public LocalDateTime withMinute(int n) {
        LocalTime localTime = this.time.withMinute(n);
        return this.with(this.date, localTime);
    }

    public LocalDateTime withMonth(int n) {
        return this.with(this.date.withMonth(n), this.time);
    }

    public LocalDateTime withNano(int n) {
        LocalTime localTime = this.time.withNano(n);
        return this.with(this.date, localTime);
    }

    public LocalDateTime withSecond(int n) {
        LocalTime localTime = this.time.withSecond(n);
        return this.with(this.date, localTime);
    }

    public LocalDateTime withYear(int n) {
        return this.with(this.date.withYear(n), this.time);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        this.date.writeExternal(dataOutput);
        this.time.writeExternal(dataOutput);
    }

}

