/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$OffsetDateTime
 *  java.time.-$$Lambda$OffsetDateTime$d2QSmDYEJWeXCg2rcQuxVNPk3n4
 *  java.time.-$$Lambda$sdbO4BiAEcJ0Ab-aR8ZYLiX9zuo
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
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetTime;
import java.time.Ser;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time._$$Lambda$OffsetDateTime$d2QSmDYEJWeXCg2rcQuxVNPk3n4;
import java.time._$$Lambda$sdbO4BiAEcJ0Ab_aR8ZYLiX9zuo;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.IsoChronology;
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
import java.util.Comparator;
import java.util.Objects;

public final class OffsetDateTime
implements Temporal,
TemporalAdjuster,
Comparable<OffsetDateTime>,
Serializable {
    public static final OffsetDateTime MAX;
    public static final OffsetDateTime MIN;
    private static final long serialVersionUID = 2287754244819255394L;
    private final LocalDateTime dateTime;
    private final ZoneOffset offset;

    static {
        MIN = LocalDateTime.MIN.atOffset(ZoneOffset.MAX);
        MAX = LocalDateTime.MAX.atOffset(ZoneOffset.MIN);
    }

    private OffsetDateTime(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        this.dateTime = Objects.requireNonNull(localDateTime, "dateTime");
        this.offset = Objects.requireNonNull(zoneOffset, "offset");
    }

    private static int compareInstant(OffsetDateTime offsetDateTime, OffsetDateTime offsetDateTime2) {
        int n;
        if (offsetDateTime.getOffset().equals(offsetDateTime2.getOffset())) {
            return offsetDateTime.toLocalDateTime().compareTo(offsetDateTime2.toLocalDateTime());
        }
        int n2 = n = Long.compare(offsetDateTime.toEpochSecond(), offsetDateTime2.toEpochSecond());
        if (n == 0) {
            n2 = offsetDateTime.toLocalTime().getNano() - offsetDateTime2.toLocalTime().getNano();
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static OffsetDateTime from(TemporalAccessor temporalAccessor) {
        LocalDate localDate;
        Serializable serializable;
        ZoneOffset zoneOffset;
        if (temporalAccessor instanceof OffsetDateTime) {
            return (OffsetDateTime)temporalAccessor;
        }
        try {
            zoneOffset = ZoneOffset.from(temporalAccessor);
            localDate = temporalAccessor.query(TemporalQueries.localDate());
            serializable = temporalAccessor.query(TemporalQueries.localTime());
            if (localDate == null) return OffsetDateTime.ofInstant(Instant.from(temporalAccessor), zoneOffset);
            if (serializable == null) return OffsetDateTime.ofInstant(Instant.from(temporalAccessor), zoneOffset);
        }
        catch (DateTimeException dateTimeException) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Unable to obtain OffsetDateTime from TemporalAccessor: ");
            ((StringBuilder)serializable).append(temporalAccessor);
            ((StringBuilder)serializable).append(" of type ");
            ((StringBuilder)serializable).append(temporalAccessor.getClass().getName());
            throw new DateTimeException(((StringBuilder)serializable).toString(), dateTimeException);
        }
        return OffsetDateTime.of(localDate, (LocalTime)serializable, zoneOffset);
    }

    public static /* synthetic */ int lambda$d2QSmDYEJWeXCg2rcQuxVNPk3n4(OffsetDateTime offsetDateTime, OffsetDateTime offsetDateTime2) {
        return OffsetDateTime.compareInstant(offsetDateTime, offsetDateTime2);
    }

    public static OffsetDateTime now() {
        return OffsetDateTime.now(Clock.systemDefaultZone());
    }

    public static OffsetDateTime now(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        Instant instant = clock.instant();
        return OffsetDateTime.ofInstant(instant, clock.getZone().getRules().getOffset(instant));
    }

    public static OffsetDateTime now(ZoneId zoneId) {
        return OffsetDateTime.now(Clock.system(zoneId));
    }

    public static OffsetDateTime of(int n, int n2, int n3, int n4, int n5, int n6, int n7, ZoneOffset zoneOffset) {
        return new OffsetDateTime(LocalDateTime.of(n, n2, n3, n4, n5, n6, n7), zoneOffset);
    }

    public static OffsetDateTime of(LocalDate localDate, LocalTime localTime, ZoneOffset zoneOffset) {
        return new OffsetDateTime(LocalDateTime.of(localDate, localTime), zoneOffset);
    }

    public static OffsetDateTime of(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return new OffsetDateTime(localDateTime, zoneOffset);
    }

    public static OffsetDateTime ofInstant(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        zoneId = zoneId.getRules().getOffset(instant);
        return new OffsetDateTime(LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), (ZoneOffset)zoneId), (ZoneOffset)zoneId);
    }

    public static OffsetDateTime parse(CharSequence charSequence) {
        return OffsetDateTime.parse(charSequence, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static OffsetDateTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (OffsetDateTime)dateTimeFormatter.parse(charSequence, _$$Lambda$sdbO4BiAEcJ0Ab_aR8ZYLiX9zuo.INSTANCE);
    }

    static OffsetDateTime readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return OffsetDateTime.of(LocalDateTime.readExternal(objectInput), ZoneOffset.readExternal(objectInput));
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public static Comparator<OffsetDateTime> timeLineOrder() {
        return _$$Lambda$OffsetDateTime$d2QSmDYEJWeXCg2rcQuxVNPk3n4.INSTANCE;
    }

    private OffsetDateTime with(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        if (this.dateTime == localDateTime && this.offset.equals(zoneOffset)) {
            return this;
        }
        return new OffsetDateTime(localDateTime, zoneOffset);
    }

    private Object writeReplace() {
        return new Ser(10, this);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.EPOCH_DAY, this.toLocalDate().toEpochDay()).with(ChronoField.NANO_OF_DAY, this.toLocalTime().toNanoOfDay()).with(ChronoField.OFFSET_SECONDS, this.getOffset().getTotalSeconds());
    }

    public ZonedDateTime atZoneSameInstant(ZoneId zoneId) {
        return ZonedDateTime.ofInstant(this.dateTime, this.offset, zoneId);
    }

    public ZonedDateTime atZoneSimilarLocal(ZoneId zoneId) {
        return ZonedDateTime.ofLocal(this.dateTime, zoneId, this.offset);
    }

    @Override
    public int compareTo(OffsetDateTime offsetDateTime) {
        int n;
        int n2 = n = OffsetDateTime.compareInstant(this, offsetDateTime);
        if (n == 0) {
            n2 = this.toLocalDateTime().compareTo(offsetDateTime.toLocalDateTime());
        }
        return n2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof OffsetDateTime) {
            object = (OffsetDateTime)object;
            if (!this.dateTime.equals(((OffsetDateTime)object).dateTime) || !this.offset.equals(((OffsetDateTime)object).offset)) {
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
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)temporalField).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    return this.dateTime.get(temporalField);
                }
                return this.getOffset().getTotalSeconds();
            }
            throw new UnsupportedTemporalTypeException("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
        }
        return Temporal.super.get(temporalField);
    }

    public int getDayOfMonth() {
        return this.dateTime.getDayOfMonth();
    }

    public DayOfWeek getDayOfWeek() {
        return this.dateTime.getDayOfWeek();
    }

    public int getDayOfYear() {
        return this.dateTime.getDayOfYear();
    }

    public int getHour() {
        return this.dateTime.getHour();
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)temporalField).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    return this.dateTime.getLong(temporalField);
                }
                return this.getOffset().getTotalSeconds();
            }
            return this.toEpochSecond();
        }
        return temporalField.getFrom(this);
    }

    public int getMinute() {
        return this.dateTime.getMinute();
    }

    public Month getMonth() {
        return this.dateTime.getMonth();
    }

    public int getMonthValue() {
        return this.dateTime.getMonthValue();
    }

    public int getNano() {
        return this.dateTime.getNano();
    }

    public ZoneOffset getOffset() {
        return this.offset;
    }

    public int getSecond() {
        return this.dateTime.getSecond();
    }

    public int getYear() {
        return this.dateTime.getYear();
    }

    public int hashCode() {
        return this.dateTime.hashCode() ^ this.offset.hashCode();
    }

    public boolean isAfter(OffsetDateTime offsetDateTime) {
        long l;
        long l2 = this.toEpochSecond();
        boolean bl = l2 > (l = offsetDateTime.toEpochSecond()) || l2 == l && this.toLocalTime().getNano() > offsetDateTime.toLocalTime().getNano();
        return bl;
    }

    public boolean isBefore(OffsetDateTime offsetDateTime) {
        long l;
        long l2 = this.toEpochSecond();
        boolean bl = l2 < (l = offsetDateTime.toEpochSecond()) || l2 == l && this.toLocalTime().getNano() < offsetDateTime.toLocalTime().getNano();
        return bl;
    }

    public boolean isEqual(OffsetDateTime offsetDateTime) {
        boolean bl = this.toEpochSecond() == offsetDateTime.toEpochSecond() && this.toLocalTime().getNano() == offsetDateTime.toLocalTime().getNano();
        return bl;
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        boolean bl = temporalField instanceof ChronoField || temporalField != null && temporalField.isSupportedBy(this);
        return bl;
    }

    @Override
    public boolean isSupported(TemporalUnit temporalUnit) {
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
    public OffsetDateTime minus(long l, TemporalUnit object) {
        object = l == Long.MIN_VALUE ? this.plus(Long.MAX_VALUE, (TemporalUnit)object).plus(1L, (TemporalUnit)object) : this.plus(-l, (TemporalUnit)object);
        return object;
    }

    @Override
    public OffsetDateTime minus(TemporalAmount temporalAmount) {
        return (OffsetDateTime)temporalAmount.subtractFrom(this);
    }

    public OffsetDateTime minusDays(long l) {
        OffsetDateTime offsetDateTime = l == Long.MIN_VALUE ? this.plusDays(Long.MAX_VALUE).plusDays(1L) : this.plusDays(-l);
        return offsetDateTime;
    }

    public OffsetDateTime minusHours(long l) {
        OffsetDateTime offsetDateTime = l == Long.MIN_VALUE ? this.plusHours(Long.MAX_VALUE).plusHours(1L) : this.plusHours(-l);
        return offsetDateTime;
    }

    public OffsetDateTime minusMinutes(long l) {
        OffsetDateTime offsetDateTime = l == Long.MIN_VALUE ? this.plusMinutes(Long.MAX_VALUE).plusMinutes(1L) : this.plusMinutes(-l);
        return offsetDateTime;
    }

    public OffsetDateTime minusMonths(long l) {
        OffsetDateTime offsetDateTime = l == Long.MIN_VALUE ? this.plusMonths(Long.MAX_VALUE).plusMonths(1L) : this.plusMonths(-l);
        return offsetDateTime;
    }

    public OffsetDateTime minusNanos(long l) {
        OffsetDateTime offsetDateTime = l == Long.MIN_VALUE ? this.plusNanos(Long.MAX_VALUE).plusNanos(1L) : this.plusNanos(-l);
        return offsetDateTime;
    }

    public OffsetDateTime minusSeconds(long l) {
        OffsetDateTime offsetDateTime = l == Long.MIN_VALUE ? this.plusSeconds(Long.MAX_VALUE).plusSeconds(1L) : this.plusSeconds(-l);
        return offsetDateTime;
    }

    public OffsetDateTime minusWeeks(long l) {
        OffsetDateTime offsetDateTime = l == Long.MIN_VALUE ? this.plusWeeks(Long.MAX_VALUE).plusWeeks(1L) : this.plusWeeks(-l);
        return offsetDateTime;
    }

    public OffsetDateTime minusYears(long l) {
        OffsetDateTime offsetDateTime = l == Long.MIN_VALUE ? this.plusYears(Long.MAX_VALUE).plusYears(1L) : this.plusYears(-l);
        return offsetDateTime;
    }

    @Override
    public OffsetDateTime plus(long l, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            return this.with(this.dateTime.plus(l, temporalUnit), this.offset);
        }
        return temporalUnit.addTo(this, l);
    }

    @Override
    public OffsetDateTime plus(TemporalAmount temporalAmount) {
        return (OffsetDateTime)temporalAmount.addTo(this);
    }

    public OffsetDateTime plusDays(long l) {
        return this.with(this.dateTime.plusDays(l), this.offset);
    }

    public OffsetDateTime plusHours(long l) {
        return this.with(this.dateTime.plusHours(l), this.offset);
    }

    public OffsetDateTime plusMinutes(long l) {
        return this.with(this.dateTime.plusMinutes(l), this.offset);
    }

    public OffsetDateTime plusMonths(long l) {
        return this.with(this.dateTime.plusMonths(l), this.offset);
    }

    public OffsetDateTime plusNanos(long l) {
        return this.with(this.dateTime.plusNanos(l), this.offset);
    }

    public OffsetDateTime plusSeconds(long l) {
        return this.with(this.dateTime.plusSeconds(l), this.offset);
    }

    public OffsetDateTime plusWeeks(long l) {
        return this.with(this.dateTime.plusWeeks(l), this.offset);
    }

    public OffsetDateTime plusYears(long l) {
        return this.with(this.dateTime.plusYears(l), this.offset);
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery != TemporalQueries.offset() && temporalQuery != TemporalQueries.zone()) {
            if (temporalQuery == TemporalQueries.zoneId()) {
                return null;
            }
            if (temporalQuery == TemporalQueries.localDate()) {
                return (R)this.toLocalDate();
            }
            if (temporalQuery == TemporalQueries.localTime()) {
                return (R)this.toLocalTime();
            }
            if (temporalQuery == TemporalQueries.chronology()) {
                return (R)IsoChronology.INSTANCE;
            }
            if (temporalQuery == TemporalQueries.precision()) {
                return (R)ChronoUnit.NANOS;
            }
            return temporalQuery.queryFrom(this);
        }
        return (R)this.getOffset();
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (temporalField != ChronoField.INSTANT_SECONDS && temporalField != ChronoField.OFFSET_SECONDS) {
                return this.dateTime.range(temporalField);
            }
            return temporalField.range();
        }
        return temporalField.rangeRefinedBy(this);
    }

    public long toEpochSecond() {
        return this.dateTime.toEpochSecond(this.offset);
    }

    public Instant toInstant() {
        return this.dateTime.toInstant(this.offset);
    }

    public LocalDate toLocalDate() {
        return this.dateTime.toLocalDate();
    }

    public LocalDateTime toLocalDateTime() {
        return this.dateTime;
    }

    public LocalTime toLocalTime() {
        return this.dateTime.toLocalTime();
    }

    public OffsetTime toOffsetTime() {
        return OffsetTime.of(this.dateTime.toLocalTime(), this.offset);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.dateTime.toString());
        stringBuilder.append(this.offset.toString());
        return stringBuilder.toString();
    }

    public ZonedDateTime toZonedDateTime() {
        return ZonedDateTime.of(this.dateTime, this.offset);
    }

    public OffsetDateTime truncatedTo(TemporalUnit temporalUnit) {
        return this.with(this.dateTime.truncatedTo(temporalUnit), this.offset);
    }

    @Override
    public long until(Temporal temporal, TemporalUnit temporalUnit) {
        temporal = OffsetDateTime.from(temporal);
        if (temporalUnit instanceof ChronoUnit) {
            temporal = ((OffsetDateTime)temporal).withOffsetSameInstant(this.offset);
            return this.dateTime.until(((OffsetDateTime)temporal).dateTime, temporalUnit);
        }
        return temporalUnit.between(this, temporal);
    }

    @Override
    public OffsetDateTime with(TemporalAdjuster temporalAdjuster) {
        if (!(temporalAdjuster instanceof LocalDate || temporalAdjuster instanceof LocalTime || temporalAdjuster instanceof LocalDateTime)) {
            if (temporalAdjuster instanceof Instant) {
                return OffsetDateTime.ofInstant((Instant)temporalAdjuster, this.offset);
            }
            if (temporalAdjuster instanceof ZoneOffset) {
                return this.with(this.dateTime, (ZoneOffset)temporalAdjuster);
            }
            if (temporalAdjuster instanceof OffsetDateTime) {
                return (OffsetDateTime)temporalAdjuster;
            }
            return (OffsetDateTime)temporalAdjuster.adjustInto(this);
        }
        return this.with(this.dateTime.with(temporalAdjuster), this.offset);
    }

    @Override
    public OffsetDateTime with(TemporalField temporalField, long l) {
        if (temporalField instanceof ChronoField) {
            ChronoField chronoField = (ChronoField)temporalField;
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    return this.with(this.dateTime.with(temporalField, l), this.offset);
                }
                return this.with(this.dateTime, ZoneOffset.ofTotalSeconds(chronoField.checkValidIntValue(l)));
            }
            return OffsetDateTime.ofInstant(Instant.ofEpochSecond(l, this.getNano()), this.offset);
        }
        return temporalField.adjustInto(this, l);
    }

    public OffsetDateTime withDayOfMonth(int n) {
        return this.with(this.dateTime.withDayOfMonth(n), this.offset);
    }

    public OffsetDateTime withDayOfYear(int n) {
        return this.with(this.dateTime.withDayOfYear(n), this.offset);
    }

    public OffsetDateTime withHour(int n) {
        return this.with(this.dateTime.withHour(n), this.offset);
    }

    public OffsetDateTime withMinute(int n) {
        return this.with(this.dateTime.withMinute(n), this.offset);
    }

    public OffsetDateTime withMonth(int n) {
        return this.with(this.dateTime.withMonth(n), this.offset);
    }

    public OffsetDateTime withNano(int n) {
        return this.with(this.dateTime.withNano(n), this.offset);
    }

    public OffsetDateTime withOffsetSameInstant(ZoneOffset zoneOffset) {
        if (zoneOffset.equals(this.offset)) {
            return this;
        }
        int n = zoneOffset.getTotalSeconds();
        int n2 = this.offset.getTotalSeconds();
        return new OffsetDateTime(this.dateTime.plusSeconds(n - n2), zoneOffset);
    }

    public OffsetDateTime withOffsetSameLocal(ZoneOffset zoneOffset) {
        return this.with(this.dateTime, zoneOffset);
    }

    public OffsetDateTime withSecond(int n) {
        return this.with(this.dateTime.withSecond(n), this.offset);
    }

    public OffsetDateTime withYear(int n) {
        return this.with(this.dateTime.withYear(n), this.offset);
    }

    void writeExternal(ObjectOutput objectOutput) throws IOException {
        this.dateTime.writeExternal(objectOutput);
        this.offset.writeExternal(objectOutput);
    }

}

