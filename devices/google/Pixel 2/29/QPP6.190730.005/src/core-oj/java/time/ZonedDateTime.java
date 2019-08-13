/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$up1HpCqucM_DXyY-rpDOyCcdmIA
 */
package java.time;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.-$;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.Ser;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time._$$Lambda$up1HpCqucM_DXyY_rpDOyCcdmIA;
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
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;
import java.util.List;
import java.util.Objects;

public final class ZonedDateTime
implements Temporal,
ChronoZonedDateTime<LocalDate>,
Serializable {
    private static final long serialVersionUID = -6260982410461394882L;
    private final LocalDateTime dateTime;
    private final ZoneOffset offset;
    private final ZoneId zone;

    private ZonedDateTime(LocalDateTime localDateTime, ZoneOffset zoneOffset, ZoneId zoneId) {
        this.dateTime = localDateTime;
        this.offset = zoneOffset;
        this.zone = zoneId;
    }

    private static ZonedDateTime create(long l, int n, ZoneId zoneId) {
        ZoneOffset zoneOffset = zoneId.getRules().getOffset(Instant.ofEpochSecond(l, n));
        return new ZonedDateTime(LocalDateTime.ofEpochSecond(l, n, zoneOffset), zoneOffset, zoneId);
    }

    public static ZonedDateTime from(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof ZonedDateTime) {
            return (ZonedDateTime)temporalAccessor;
        }
        try {
            Serializable serializable = ZoneId.from(temporalAccessor);
            if (temporalAccessor.isSupported(ChronoField.INSTANT_SECONDS)) {
                return ZonedDateTime.create(temporalAccessor.getLong(ChronoField.INSTANT_SECONDS), temporalAccessor.get(ChronoField.NANO_OF_SECOND), serializable);
            }
            serializable = ZonedDateTime.of(LocalDate.from(temporalAccessor), LocalTime.from(temporalAccessor), serializable);
            return serializable;
        }
        catch (DateTimeException dateTimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to obtain ZonedDateTime from TemporalAccessor: ");
            stringBuilder.append(temporalAccessor);
            stringBuilder.append(" of type ");
            stringBuilder.append(temporalAccessor.getClass().getName());
            throw new DateTimeException(stringBuilder.toString(), dateTimeException);
        }
    }

    public static ZonedDateTime now() {
        return ZonedDateTime.now(Clock.systemDefaultZone());
    }

    public static ZonedDateTime now(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return ZonedDateTime.ofInstant(clock.instant(), clock.getZone());
    }

    public static ZonedDateTime now(ZoneId zoneId) {
        return ZonedDateTime.now(Clock.system(zoneId));
    }

    public static ZonedDateTime of(int n, int n2, int n3, int n4, int n5, int n6, int n7, ZoneId zoneId) {
        return ZonedDateTime.ofLocal(LocalDateTime.of(n, n2, n3, n4, n5, n6, n7), zoneId, null);
    }

    public static ZonedDateTime of(LocalDate localDate, LocalTime localTime, ZoneId zoneId) {
        return ZonedDateTime.of(LocalDateTime.of(localDate, localTime), zoneId);
    }

    public static ZonedDateTime of(LocalDateTime localDateTime, ZoneId zoneId) {
        return ZonedDateTime.ofLocal(localDateTime, zoneId, null);
    }

    public static ZonedDateTime ofInstant(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        return ZonedDateTime.create(instant.getEpochSecond(), instant.getNano(), zoneId);
    }

    public static ZonedDateTime ofInstant(LocalDateTime localDateTime, ZoneOffset zoneOffset, ZoneId zoneId) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(zoneOffset, "offset");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId.getRules().isValidOffset(localDateTime, zoneOffset)) {
            return new ZonedDateTime(localDateTime, zoneOffset, zoneId);
        }
        return ZonedDateTime.create(localDateTime.toEpochSecond(zoneOffset), localDateTime.getNano(), zoneId);
    }

    private static ZonedDateTime ofLenient(LocalDateTime localDateTime, ZoneOffset zoneOffset, ZoneId zoneId) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(zoneOffset, "offset");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId instanceof ZoneOffset && !zoneOffset.equals(zoneId)) {
            throw new IllegalArgumentException("ZoneId must match ZoneOffset");
        }
        return new ZonedDateTime(localDateTime, zoneOffset, zoneId);
    }

    public static ZonedDateTime ofLocal(LocalDateTime localDateTime, ZoneId zoneId, ZoneOffset comparable) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId instanceof ZoneOffset) {
            return new ZonedDateTime(localDateTime, (ZoneOffset)zoneId, zoneId);
        }
        ZoneRules zoneRules = zoneId.getRules();
        List<ZoneOffset> list = zoneRules.getValidOffsets(localDateTime);
        if (list.size() == 1) {
            comparable = list.get(0);
        } else if (list.size() == 0) {
            comparable = zoneRules.getTransition(localDateTime);
            localDateTime = localDateTime.plusSeconds(((ZoneOffsetTransition)comparable).getDuration().getSeconds());
            comparable = ((ZoneOffsetTransition)comparable).getOffsetAfter();
        } else if (comparable == null || !list.contains(comparable)) {
            comparable = Objects.requireNonNull(list.get(0), "offset");
        }
        return new ZonedDateTime(localDateTime, (ZoneOffset)comparable, zoneId);
    }

    public static ZonedDateTime ofStrict(LocalDateTime localDateTime, ZoneOffset serializable, ZoneId zoneId) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(serializable, "offset");
        Objects.requireNonNull(zoneId, "zone");
        Serializable serializable2 = zoneId.getRules();
        if (!((ZoneRules)serializable2).isValidOffset(localDateTime, (ZoneOffset)serializable)) {
            if ((serializable2 = ((ZoneRules)serializable2).getTransition(localDateTime)) != null && ((ZoneOffsetTransition)serializable2).isGap()) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("LocalDateTime '");
                ((StringBuilder)serializable).append(localDateTime);
                ((StringBuilder)serializable).append("' does not exist in zone '");
                ((StringBuilder)serializable).append(zoneId);
                ((StringBuilder)serializable).append("' due to a gap in the local time-line, typically caused by daylight savings");
                throw new DateTimeException(((StringBuilder)serializable).toString());
            }
            serializable2 = new StringBuilder();
            ((StringBuilder)serializable2).append("ZoneOffset '");
            ((StringBuilder)serializable2).append(serializable);
            ((StringBuilder)serializable2).append("' is not valid for LocalDateTime '");
            ((StringBuilder)serializable2).append(localDateTime);
            ((StringBuilder)serializable2).append("' in zone '");
            ((StringBuilder)serializable2).append(zoneId);
            ((StringBuilder)serializable2).append("'");
            throw new DateTimeException(((StringBuilder)serializable2).toString());
        }
        return new ZonedDateTime(localDateTime, (ZoneOffset)serializable, zoneId);
    }

    public static ZonedDateTime parse(CharSequence charSequence) {
        return ZonedDateTime.parse(charSequence, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public static ZonedDateTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (ZonedDateTime)dateTimeFormatter.parse(charSequence, _$$Lambda$up1HpCqucM_DXyY_rpDOyCcdmIA.INSTANCE);
    }

    static ZonedDateTime readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        return ZonedDateTime.ofLenient(LocalDateTime.readExternal(objectInput), ZoneOffset.readExternal(objectInput), (ZoneId)Ser.read(objectInput));
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private ZonedDateTime resolveInstant(LocalDateTime localDateTime) {
        return ZonedDateTime.ofInstant(localDateTime, this.offset, this.zone);
    }

    private ZonedDateTime resolveLocal(LocalDateTime localDateTime) {
        return ZonedDateTime.ofLocal(localDateTime, this.zone, this.offset);
    }

    private ZonedDateTime resolveOffset(ZoneOffset zoneOffset) {
        if (!zoneOffset.equals(this.offset) && this.zone.getRules().isValidOffset(this.dateTime, zoneOffset)) {
            return new ZonedDateTime(this.dateTime, zoneOffset, this.zone);
        }
        return this;
    }

    private Object writeReplace() {
        return new Ser(6, this);
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof ZonedDateTime) {
            object = (ZonedDateTime)object;
            if (!(this.dateTime.equals(((ZonedDateTime)object).dateTime) && this.offset.equals(((ZonedDateTime)object).offset) && this.zone.equals(((ZonedDateTime)object).zone))) {
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
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)temporalField).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    return this.dateTime.get(temporalField);
                }
                return this.getOffset().getTotalSeconds();
            }
            throw new UnsupportedTemporalTypeException("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
        }
        return ChronoZonedDateTime.super.get(temporalField);
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

    @Override
    public ZoneOffset getOffset() {
        return this.offset;
    }

    public int getSecond() {
        return this.dateTime.getSecond();
    }

    public int getYear() {
        return this.dateTime.getYear();
    }

    @Override
    public ZoneId getZone() {
        return this.zone;
    }

    @Override
    public int hashCode() {
        return this.dateTime.hashCode() ^ this.offset.hashCode() ^ Integer.rotateLeft(this.zone.hashCode(), 3);
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        boolean bl = temporalField instanceof ChronoField || temporalField != null && temporalField.isSupportedBy(this);
        return bl;
    }

    @Override
    public boolean isSupported(TemporalUnit temporalUnit) {
        return ChronoZonedDateTime.super.isSupported(temporalUnit);
    }

    @Override
    public ZonedDateTime minus(long l, TemporalUnit object) {
        object = l == Long.MIN_VALUE ? this.plus(Long.MAX_VALUE, (TemporalUnit)object).plus(1L, (TemporalUnit)object) : this.plus(-l, (TemporalUnit)object);
        return object;
    }

    @Override
    public ZonedDateTime minus(TemporalAmount temporalAmount) {
        if (temporalAmount instanceof Period) {
            temporalAmount = (Period)temporalAmount;
            return this.resolveLocal(this.dateTime.minus(temporalAmount));
        }
        Objects.requireNonNull(temporalAmount, "amountToSubtract");
        return (ZonedDateTime)temporalAmount.subtractFrom(this);
    }

    public ZonedDateTime minusDays(long l) {
        ZonedDateTime zonedDateTime = l == Long.MIN_VALUE ? this.plusDays(Long.MAX_VALUE).plusDays(1L) : this.plusDays(-l);
        return zonedDateTime;
    }

    public ZonedDateTime minusHours(long l) {
        ZonedDateTime zonedDateTime = l == Long.MIN_VALUE ? this.plusHours(Long.MAX_VALUE).plusHours(1L) : this.plusHours(-l);
        return zonedDateTime;
    }

    public ZonedDateTime minusMinutes(long l) {
        ZonedDateTime zonedDateTime = l == Long.MIN_VALUE ? this.plusMinutes(Long.MAX_VALUE).plusMinutes(1L) : this.plusMinutes(-l);
        return zonedDateTime;
    }

    public ZonedDateTime minusMonths(long l) {
        ZonedDateTime zonedDateTime = l == Long.MIN_VALUE ? this.plusMonths(Long.MAX_VALUE).plusMonths(1L) : this.plusMonths(-l);
        return zonedDateTime;
    }

    public ZonedDateTime minusNanos(long l) {
        ZonedDateTime zonedDateTime = l == Long.MIN_VALUE ? this.plusNanos(Long.MAX_VALUE).plusNanos(1L) : this.plusNanos(-l);
        return zonedDateTime;
    }

    public ZonedDateTime minusSeconds(long l) {
        ZonedDateTime zonedDateTime = l == Long.MIN_VALUE ? this.plusSeconds(Long.MAX_VALUE).plusSeconds(1L) : this.plusSeconds(-l);
        return zonedDateTime;
    }

    public ZonedDateTime minusWeeks(long l) {
        ZonedDateTime zonedDateTime = l == Long.MIN_VALUE ? this.plusWeeks(Long.MAX_VALUE).plusWeeks(1L) : this.plusWeeks(-l);
        return zonedDateTime;
    }

    public ZonedDateTime minusYears(long l) {
        ZonedDateTime zonedDateTime = l == Long.MIN_VALUE ? this.plusYears(Long.MAX_VALUE).plusYears(1L) : this.plusYears(-l);
        return zonedDateTime;
    }

    @Override
    public ZonedDateTime plus(long l, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            if (temporalUnit.isDateBased()) {
                return this.resolveLocal(this.dateTime.plus(l, temporalUnit));
            }
            return this.resolveInstant(this.dateTime.plus(l, temporalUnit));
        }
        return temporalUnit.addTo(this, l);
    }

    @Override
    public ZonedDateTime plus(TemporalAmount temporalAmount) {
        if (temporalAmount instanceof Period) {
            temporalAmount = (Period)temporalAmount;
            return this.resolveLocal(this.dateTime.plus(temporalAmount));
        }
        Objects.requireNonNull(temporalAmount, "amountToAdd");
        return (ZonedDateTime)temporalAmount.addTo(this);
    }

    public ZonedDateTime plusDays(long l) {
        return this.resolveLocal(this.dateTime.plusDays(l));
    }

    public ZonedDateTime plusHours(long l) {
        return this.resolveInstant(this.dateTime.plusHours(l));
    }

    public ZonedDateTime plusMinutes(long l) {
        return this.resolveInstant(this.dateTime.plusMinutes(l));
    }

    public ZonedDateTime plusMonths(long l) {
        return this.resolveLocal(this.dateTime.plusMonths(l));
    }

    public ZonedDateTime plusNanos(long l) {
        return this.resolveInstant(this.dateTime.plusNanos(l));
    }

    public ZonedDateTime plusSeconds(long l) {
        return this.resolveInstant(this.dateTime.plusSeconds(l));
    }

    public ZonedDateTime plusWeeks(long l) {
        return this.resolveLocal(this.dateTime.plusWeeks(l));
    }

    public ZonedDateTime plusYears(long l) {
        return this.resolveLocal(this.dateTime.plusYears(l));
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery == TemporalQueries.localDate()) {
            return (R)this.toLocalDate();
        }
        return ChronoZonedDateTime.super.query(temporalQuery);
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

    @Override
    public LocalDate toLocalDate() {
        return this.dateTime.toLocalDate();
    }

    public LocalDateTime toLocalDateTime() {
        return this.dateTime;
    }

    @Override
    public LocalTime toLocalTime() {
        return this.dateTime.toLocalTime();
    }

    public OffsetDateTime toOffsetDateTime() {
        return OffsetDateTime.of(this.dateTime, this.offset);
    }

    @Override
    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(this.dateTime.toString());
        ((StringBuilder)charSequence).append(this.offset.toString());
        String string = ((StringBuilder)charSequence).toString();
        charSequence = string;
        if (this.offset != this.zone) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append('[');
            ((StringBuilder)charSequence).append(this.zone.toString());
            ((StringBuilder)charSequence).append(']');
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    public ZonedDateTime truncatedTo(TemporalUnit temporalUnit) {
        return this.resolveLocal(this.dateTime.truncatedTo(temporalUnit));
    }

    @Override
    public long until(Temporal temporal, TemporalUnit temporalUnit) {
        temporal = ZonedDateTime.from(temporal);
        if (temporalUnit instanceof ChronoUnit) {
            temporal = ((ZonedDateTime)temporal).withZoneSameInstant(this.zone);
            if (temporalUnit.isDateBased()) {
                return this.dateTime.until(((ZonedDateTime)temporal).dateTime, temporalUnit);
            }
            return this.toOffsetDateTime().until(((ZonedDateTime)temporal).toOffsetDateTime(), temporalUnit);
        }
        return temporalUnit.between(this, temporal);
    }

    @Override
    public ZonedDateTime with(TemporalAdjuster temporalAdjuster) {
        if (temporalAdjuster instanceof LocalDate) {
            return this.resolveLocal(LocalDateTime.of((LocalDate)temporalAdjuster, this.dateTime.toLocalTime()));
        }
        if (temporalAdjuster instanceof LocalTime) {
            return this.resolveLocal(LocalDateTime.of(this.dateTime.toLocalDate(), (LocalTime)temporalAdjuster));
        }
        if (temporalAdjuster instanceof LocalDateTime) {
            return this.resolveLocal((LocalDateTime)temporalAdjuster);
        }
        if (temporalAdjuster instanceof OffsetDateTime) {
            temporalAdjuster = (OffsetDateTime)temporalAdjuster;
            return ZonedDateTime.ofLocal(((OffsetDateTime)temporalAdjuster).toLocalDateTime(), this.zone, ((OffsetDateTime)temporalAdjuster).getOffset());
        }
        if (temporalAdjuster instanceof Instant) {
            temporalAdjuster = (Instant)temporalAdjuster;
            return ZonedDateTime.create(((Instant)temporalAdjuster).getEpochSecond(), ((Instant)temporalAdjuster).getNano(), this.zone);
        }
        if (temporalAdjuster instanceof ZoneOffset) {
            return this.resolveOffset((ZoneOffset)temporalAdjuster);
        }
        return (ZonedDateTime)temporalAdjuster.adjustInto(this);
    }

    @Override
    public ZonedDateTime with(TemporalField temporalField, long l) {
        if (temporalField instanceof ChronoField) {
            ChronoField chronoField = (ChronoField)temporalField;
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
            if (n != 1) {
                if (n != 2) {
                    return this.resolveLocal(this.dateTime.with(temporalField, l));
                }
                return this.resolveOffset(ZoneOffset.ofTotalSeconds(chronoField.checkValidIntValue(l)));
            }
            return ZonedDateTime.create(l, this.getNano(), this.zone);
        }
        return temporalField.adjustInto(this, l);
    }

    public ZonedDateTime withDayOfMonth(int n) {
        return this.resolveLocal(this.dateTime.withDayOfMonth(n));
    }

    public ZonedDateTime withDayOfYear(int n) {
        return this.resolveLocal(this.dateTime.withDayOfYear(n));
    }

    public ZonedDateTime withEarlierOffsetAtOverlap() {
        Comparable<ZoneOffsetTransition> comparable = this.getZone().getRules().getTransition(this.dateTime);
        if (comparable != null && ((ZoneOffsetTransition)comparable).isOverlap() && !((ZoneOffset)(comparable = ((ZoneOffsetTransition)comparable).getOffsetBefore())).equals(this.offset)) {
            return new ZonedDateTime(this.dateTime, (ZoneOffset)comparable, this.zone);
        }
        return this;
    }

    public ZonedDateTime withFixedOffsetZone() {
        TemporalAccessor temporalAccessor;
        if (this.zone.equals(this.offset)) {
            temporalAccessor = this;
        } else {
            LocalDateTime localDateTime = this.dateTime;
            temporalAccessor = this.offset;
            temporalAccessor = new ZonedDateTime(localDateTime, (ZoneOffset)temporalAccessor, (ZoneId)((Object)temporalAccessor));
        }
        return temporalAccessor;
    }

    public ZonedDateTime withHour(int n) {
        return this.resolveLocal(this.dateTime.withHour(n));
    }

    public ZonedDateTime withLaterOffsetAtOverlap() {
        Comparable<ZoneOffsetTransition> comparable = this.getZone().getRules().getTransition(this.toLocalDateTime());
        if (comparable != null && !((ZoneOffset)(comparable = ((ZoneOffsetTransition)comparable).getOffsetAfter())).equals(this.offset)) {
            return new ZonedDateTime(this.dateTime, (ZoneOffset)comparable, this.zone);
        }
        return this;
    }

    public ZonedDateTime withMinute(int n) {
        return this.resolveLocal(this.dateTime.withMinute(n));
    }

    public ZonedDateTime withMonth(int n) {
        return this.resolveLocal(this.dateTime.withMonth(n));
    }

    public ZonedDateTime withNano(int n) {
        return this.resolveLocal(this.dateTime.withNano(n));
    }

    public ZonedDateTime withSecond(int n) {
        return this.resolveLocal(this.dateTime.withSecond(n));
    }

    public ZonedDateTime withYear(int n) {
        return this.resolveLocal(this.dateTime.withYear(n));
    }

    public ZonedDateTime withZoneSameInstant(ZoneId serializable) {
        Objects.requireNonNull(serializable, "zone");
        serializable = this.zone.equals(serializable) ? this : ZonedDateTime.create(this.dateTime.toEpochSecond(this.offset), this.dateTime.getNano(), serializable);
        return serializable;
    }

    public ZonedDateTime withZoneSameLocal(ZoneId serializable) {
        Objects.requireNonNull(serializable, "zone");
        serializable = this.zone.equals(serializable) ? this : ZonedDateTime.ofLocal(this.dateTime, serializable, this.offset);
        return serializable;
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        this.dateTime.writeExternal(dataOutput);
        this.offset.writeExternal(dataOutput);
        this.zone.write(dataOutput);
    }

}

