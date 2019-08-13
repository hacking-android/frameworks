/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$Bq8PKq1YWr8nyVk9SSfRYKrOu4A
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Ser;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time._$$Lambda$Bq8PKq1YWr8nyVk9SSfRYKrOu4A;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
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
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;
import java.util.Objects;

public final class LocalDate
implements Temporal,
TemporalAdjuster,
ChronoLocalDate,
Serializable {
    static final long DAYS_0000_TO_1970 = 719528L;
    private static final int DAYS_PER_CYCLE = 146097;
    public static final LocalDate MAX;
    public static final LocalDate MIN;
    private static final long serialVersionUID = 2942565459149668126L;
    private final short day;
    private final short month;
    private final int year;

    static {
        MIN = LocalDate.of(-999999999, 1, 1);
        MAX = LocalDate.of(999999999, 12, 31);
    }

    private LocalDate(int n, int n2, int n3) {
        this.year = n;
        this.month = (short)n2;
        this.day = (short)n3;
    }

    private static LocalDate create(int n, int n2, int n3) {
        int n4 = 28;
        if (n3 > 28) {
            int n5 = 31;
            if (n2 != 2) {
                if (n2 == 4 || n2 == 6 || n2 == 9 || n2 == 11) {
                    n5 = 30;
                }
            } else {
                n5 = n4;
                if (IsoChronology.INSTANCE.isLeapYear(n)) {
                    n5 = 29;
                }
            }
            if (n3 > n5) {
                if (n3 == 29) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid date 'February 29' as '");
                    stringBuilder.append(n);
                    stringBuilder.append("' is not a leap year");
                    throw new DateTimeException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid date '");
                stringBuilder.append(Month.of(n2).name());
                stringBuilder.append(" ");
                stringBuilder.append(n3);
                stringBuilder.append("'");
                throw new DateTimeException(stringBuilder.toString());
            }
        }
        return new LocalDate(n, n2, n3);
    }

    public static LocalDate from(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        Serializable serializable = temporalAccessor.query(TemporalQueries.localDate());
        if (serializable != null) {
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unable to obtain LocalDate from TemporalAccessor: ");
        ((StringBuilder)serializable).append(temporalAccessor);
        ((StringBuilder)serializable).append(" of type ");
        ((StringBuilder)serializable).append(temporalAccessor.getClass().getName());
        throw new DateTimeException(((StringBuilder)serializable).toString());
    }

    private int get0(TemporalField temporalField) {
        int n = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)temporalField).ordinal()];
        int n2 = 1;
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported field: ");
                stringBuilder.append(temporalField);
                throw new UnsupportedTemporalTypeException(stringBuilder.toString());
            }
            case 13: {
                if (this.year < 1) {
                    n2 = 0;
                }
                return n2;
            }
            case 12: {
                return this.year;
            }
            case 11: {
                throw new UnsupportedTemporalTypeException("Invalid field 'ProlepticMonth' for get() method, use getLong() instead");
            }
            case 10: {
                return this.month;
            }
            case 9: {
                return (this.getDayOfYear() - 1) / 7 + 1;
            }
            case 8: {
                throw new UnsupportedTemporalTypeException("Invalid field 'EpochDay' for get() method, use getLong() instead");
            }
            case 7: {
                return (this.getDayOfYear() - 1) % 7 + 1;
            }
            case 6: {
                return (this.day - 1) % 7 + 1;
            }
            case 5: {
                return this.getDayOfWeek().getValue();
            }
            case 4: {
                n2 = this.year;
                if (n2 < 1) {
                    n2 = 1 - n2;
                }
                return n2;
            }
            case 3: {
                return (this.day - 1) / 7 + 1;
            }
            case 2: {
                return this.getDayOfYear();
            }
            case 1: 
        }
        return this.day;
    }

    private long getProlepticMonth() {
        return (long)this.year * 12L + (long)this.month - 1L;
    }

    private long monthsUntil(LocalDate localDate) {
        long l = this.getProlepticMonth();
        long l2 = this.getDayOfMonth();
        return (localDate.getProlepticMonth() * 32L + (long)localDate.getDayOfMonth() - (l * 32L + l2)) / 32L;
    }

    public static LocalDate now() {
        return LocalDate.now(Clock.systemDefaultZone());
    }

    public static LocalDate now(Clock object) {
        Objects.requireNonNull(object, "clock");
        Instant instant = ((Clock)object).instant();
        object = ((Clock)object).getZone().getRules().getOffset(instant);
        return LocalDate.ofEpochDay(Math.floorDiv(instant.getEpochSecond() + (long)((ZoneOffset)object).getTotalSeconds(), 86400L));
    }

    public static LocalDate now(ZoneId zoneId) {
        return LocalDate.now(Clock.system(zoneId));
    }

    public static LocalDate of(int n, int n2, int n3) {
        ChronoField.YEAR.checkValidValue(n);
        ChronoField.MONTH_OF_YEAR.checkValidValue(n2);
        ChronoField.DAY_OF_MONTH.checkValidValue(n3);
        return LocalDate.create(n, n2, n3);
    }

    public static LocalDate of(int n, Month month, int n2) {
        ChronoField.YEAR.checkValidValue(n);
        Objects.requireNonNull(month, "month");
        ChronoField.DAY_OF_MONTH.checkValidValue(n2);
        return LocalDate.create(n, month.getValue(), n2);
    }

    public static LocalDate ofEpochDay(long l) {
        long l2 = l + 719528L - 60L;
        l = 0L;
        long l3 = l2;
        if (l2 < 0L) {
            l3 = (l2 + 1L) / 146097L - 1L;
            l = l3 * 400L;
            l3 = l2 + -l3 * 146097L;
        }
        long l4 = (l3 * 400L + 591L) / 146097L;
        long l5 = l3 - (l4 * 365L + l4 / 4L - l4 / 100L + l4 / 400L);
        l2 = l4;
        long l6 = l5;
        if (l5 < 0L) {
            l2 = l4 - 1L;
            l6 = l3 - (365L * l2 + l2 / 4L - l2 / 100L + l2 / 400L);
        }
        int n = (int)l6;
        int n2 = (n * 5 + 2) / 153;
        int n3 = (n2 * 306 + 5) / 10;
        l3 = n2 / 10;
        return new LocalDate(ChronoField.YEAR.checkValidIntValue(l2 + l + l3), (n2 + 2) % 12 + 1, n - n3 + 1);
    }

    public static LocalDate ofYearDay(int n, int n2) {
        Month month;
        ChronoField.YEAR.checkValidValue(n);
        ChronoField.DAY_OF_YEAR.checkValidValue(n2);
        boolean bl = IsoChronology.INSTANCE.isLeapYear(n);
        if (n2 == 366 && !bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid date 'DayOfYear 366' as '");
            stringBuilder.append(n);
            stringBuilder.append("' is not a leap year");
            throw new DateTimeException(stringBuilder.toString());
        }
        Month month2 = month = Month.of((n2 - 1) / 31 + 1);
        if (n2 > month.firstDayOfYear(bl) + month.length(bl) - 1) {
            month2 = month.plus(1L);
        }
        int n3 = month2.firstDayOfYear(bl);
        return new LocalDate(n, month2.getValue(), n2 - n3 + 1);
    }

    public static LocalDate parse(CharSequence charSequence) {
        return LocalDate.parse(charSequence, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static LocalDate parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (LocalDate)dateTimeFormatter.parse(charSequence, _$$Lambda$Bq8PKq1YWr8nyVk9SSfRYKrOu4A.INSTANCE);
    }

    static LocalDate readExternal(DataInput dataInput) throws IOException {
        return LocalDate.of(dataInput.readInt(), dataInput.readByte(), (int)dataInput.readByte());
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private static LocalDate resolvePreviousValid(int n, int n2, int n3) {
        if (n2 != 2) {
            if (n2 == 4 || n2 == 6 || n2 == 9 || n2 == 11) {
                n3 = Math.min(n3, 30);
            }
        } else {
            int n4 = IsoChronology.INSTANCE.isLeapYear(n) ? 29 : 28;
            n3 = Math.min(n3, n4);
        }
        return new LocalDate(n, n2, n3);
    }

    private Object writeReplace() {
        return new Ser(3, this);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        return ChronoLocalDate.super.adjustInto(temporal);
    }

    public LocalDateTime atStartOfDay() {
        return LocalDateTime.of(this, LocalTime.MIDNIGHT);
    }

    public ZonedDateTime atStartOfDay(ZoneId zoneId) {
        LocalDateTime localDateTime;
        Objects.requireNonNull(zoneId, "zone");
        LocalDateTime localDateTime2 = localDateTime = this.atTime(LocalTime.MIDNIGHT);
        if (!(zoneId instanceof ZoneOffset)) {
            ZoneOffsetTransition zoneOffsetTransition = zoneId.getRules().getTransition(localDateTime);
            localDateTime2 = localDateTime;
            if (zoneOffsetTransition != null) {
                localDateTime2 = localDateTime;
                if (zoneOffsetTransition.isGap()) {
                    localDateTime2 = zoneOffsetTransition.getDateTimeAfter();
                }
            }
        }
        return ZonedDateTime.of(localDateTime2, zoneId);
    }

    public LocalDateTime atTime(int n, int n2) {
        return this.atTime(LocalTime.of(n, n2));
    }

    public LocalDateTime atTime(int n, int n2, int n3) {
        return this.atTime(LocalTime.of(n, n2, n3));
    }

    public LocalDateTime atTime(int n, int n2, int n3, int n4) {
        return this.atTime(LocalTime.of(n, n2, n3, n4));
    }

    public LocalDateTime atTime(LocalTime localTime) {
        return LocalDateTime.of(this, localTime);
    }

    public OffsetDateTime atTime(OffsetTime offsetTime) {
        return OffsetDateTime.of(LocalDateTime.of(this, offsetTime.toLocalTime()), offsetTime.getOffset());
    }

    @Override
    public int compareTo(ChronoLocalDate chronoLocalDate) {
        if (chronoLocalDate instanceof LocalDate) {
            return this.compareTo0((LocalDate)chronoLocalDate);
        }
        return ChronoLocalDate.super.compareTo(chronoLocalDate);
    }

    int compareTo0(LocalDate localDate) {
        int n;
        int n2 = n = this.year - localDate.year;
        if (n == 0) {
            n2 = n = this.month - localDate.month;
            if (n == 0) {
                n2 = this.day - localDate.day;
            }
        }
        return n2;
    }

    long daysUntil(LocalDate localDate) {
        return localDate.toEpochDay() - this.toEpochDay();
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof LocalDate) {
            if (this.compareTo0((LocalDate)object) != 0) {
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
            return this.get0(temporalField);
        }
        return ChronoLocalDate.super.get(temporalField);
    }

    @Override
    public IsoChronology getChronology() {
        return IsoChronology.INSTANCE;
    }

    public int getDayOfMonth() {
        return this.day;
    }

    public DayOfWeek getDayOfWeek() {
        return DayOfWeek.of((int)Math.floorMod(this.toEpochDay() + 3L, 7L) + 1);
    }

    public int getDayOfYear() {
        return this.getMonth().firstDayOfYear(this.isLeapYear()) + this.day - 1;
    }

    @Override
    public Era getEra() {
        return ChronoLocalDate.super.getEra();
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            if (temporalField == ChronoField.EPOCH_DAY) {
                return this.toEpochDay();
            }
            if (temporalField == ChronoField.PROLEPTIC_MONTH) {
                return this.getProlepticMonth();
            }
            return this.get0(temporalField);
        }
        return temporalField.getFrom(this);
    }

    public Month getMonth() {
        return Month.of(this.month);
    }

    public int getMonthValue() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    @Override
    public int hashCode() {
        int n = this.year;
        return n & -2048 ^ (n << 11) + (this.month << 6) + this.day;
    }

    @Override
    public boolean isAfter(ChronoLocalDate chronoLocalDate) {
        if (chronoLocalDate instanceof LocalDate) {
            boolean bl = this.compareTo0((LocalDate)chronoLocalDate) > 0;
            return bl;
        }
        return ChronoLocalDate.super.isAfter(chronoLocalDate);
    }

    @Override
    public boolean isBefore(ChronoLocalDate chronoLocalDate) {
        if (chronoLocalDate instanceof LocalDate) {
            boolean bl = this.compareTo0((LocalDate)chronoLocalDate) < 0;
            return bl;
        }
        return ChronoLocalDate.super.isBefore(chronoLocalDate);
    }

    @Override
    public boolean isEqual(ChronoLocalDate chronoLocalDate) {
        if (chronoLocalDate instanceof LocalDate) {
            boolean bl = this.compareTo0((LocalDate)chronoLocalDate) == 0;
            return bl;
        }
        return ChronoLocalDate.super.isEqual(chronoLocalDate);
    }

    @Override
    public boolean isLeapYear() {
        return IsoChronology.INSTANCE.isLeapYear(this.year);
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        return ChronoLocalDate.super.isSupported(temporalField);
    }

    @Override
    public boolean isSupported(TemporalUnit temporalUnit) {
        return ChronoLocalDate.super.isSupported(temporalUnit);
    }

    @Override
    public int lengthOfMonth() {
        int n = this.month;
        if (n != 2) {
            if (n != 4 && n != 6 && n != 9 && n != 11) {
                return 31;
            }
            return 30;
        }
        n = this.isLeapYear() ? 29 : 28;
        return n;
    }

    @Override
    public int lengthOfYear() {
        int n = this.isLeapYear() ? 366 : 365;
        return n;
    }

    @Override
    public LocalDate minus(long l, TemporalUnit object) {
        object = l == Long.MIN_VALUE ? this.plus(Long.MAX_VALUE, (TemporalUnit)object).plus(1L, (TemporalUnit)object) : this.plus(-l, (TemporalUnit)object);
        return object;
    }

    @Override
    public LocalDate minus(TemporalAmount temporalAmount) {
        if (temporalAmount instanceof Period) {
            temporalAmount = (Period)temporalAmount;
            return this.minusMonths(((Period)temporalAmount).toTotalMonths()).minusDays(((Period)temporalAmount).getDays());
        }
        Objects.requireNonNull(temporalAmount, "amountToSubtract");
        return (LocalDate)temporalAmount.subtractFrom(this);
    }

    public LocalDate minusDays(long l) {
        LocalDate localDate = l == Long.MIN_VALUE ? this.plusDays(Long.MAX_VALUE).plusDays(1L) : this.plusDays(-l);
        return localDate;
    }

    public LocalDate minusMonths(long l) {
        LocalDate localDate = l == Long.MIN_VALUE ? this.plusMonths(Long.MAX_VALUE).plusMonths(1L) : this.plusMonths(-l);
        return localDate;
    }

    public LocalDate minusWeeks(long l) {
        LocalDate localDate = l == Long.MIN_VALUE ? this.plusWeeks(Long.MAX_VALUE).plusWeeks(1L) : this.plusWeeks(-l);
        return localDate;
    }

    public LocalDate minusYears(long l) {
        LocalDate localDate = l == Long.MIN_VALUE ? this.plusYears(Long.MAX_VALUE).plusYears(1L) : this.plusYears(-l);
        return localDate;
    }

    @Override
    public LocalDate plus(long l, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            Object object = (ChronoUnit)temporalUnit;
            switch (1.$SwitchMap$java$time$temporal$ChronoUnit[((Enum)object).ordinal()]) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unsupported unit: ");
                    ((StringBuilder)object).append(temporalUnit);
                    throw new UnsupportedTemporalTypeException(((StringBuilder)object).toString());
                }
                case 8: {
                    return this.with(ChronoField.ERA, Math.addExact(this.getLong(ChronoField.ERA), l));
                }
                case 7: {
                    return this.plusYears(Math.multiplyExact(l, 1000L));
                }
                case 6: {
                    return this.plusYears(Math.multiplyExact(l, 100L));
                }
                case 5: {
                    return this.plusYears(Math.multiplyExact(l, 10L));
                }
                case 4: {
                    return this.plusYears(l);
                }
                case 3: {
                    return this.plusMonths(l);
                }
                case 2: {
                    return this.plusWeeks(l);
                }
                case 1: 
            }
            return this.plusDays(l);
        }
        return temporalUnit.addTo(this, l);
    }

    @Override
    public LocalDate plus(TemporalAmount temporalAmount) {
        if (temporalAmount instanceof Period) {
            temporalAmount = (Period)temporalAmount;
            return this.plusMonths(((Period)temporalAmount).toTotalMonths()).plusDays(((Period)temporalAmount).getDays());
        }
        Objects.requireNonNull(temporalAmount, "amountToAdd");
        return (LocalDate)temporalAmount.addTo(this);
    }

    public LocalDate plusDays(long l) {
        if (l == 0L) {
            return this;
        }
        return LocalDate.ofEpochDay(Math.addExact(this.toEpochDay(), l));
    }

    public LocalDate plusMonths(long l) {
        if (l == 0L) {
            return this;
        }
        l = (long)this.year * 12L + (long)(this.month - 1) + l;
        return LocalDate.resolvePreviousValid(ChronoField.YEAR.checkValidIntValue(Math.floorDiv(l, 12L)), (int)Math.floorMod(l, 12L) + 1, this.day);
    }

    public LocalDate plusWeeks(long l) {
        return this.plusDays(Math.multiplyExact(l, 7L));
    }

    public LocalDate plusYears(long l) {
        if (l == 0L) {
            return this;
        }
        return LocalDate.resolvePreviousValid(ChronoField.YEAR.checkValidIntValue((long)this.year + l), this.month, this.day);
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery == TemporalQueries.localDate()) {
            return (R)this;
        }
        return ChronoLocalDate.super.query(temporalQuery);
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            Object object = (ChronoField)temporalField;
            if (((ChronoField)object).isDateBased()) {
                int n = 1.$SwitchMap$java$time$temporal$ChronoField[((Enum)object).ordinal()];
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) {
                                return temporalField.range();
                            }
                            long l = this.getYear() <= 0 ? 1000000000L : 999999999L;
                            return ValueRange.of(1L, l);
                        }
                        long l = this.getMonth() == Month.FEBRUARY && !this.isLeapYear() ? 4L : 5L;
                        return ValueRange.of(1L, l);
                    }
                    return ValueRange.of(1L, this.lengthOfYear());
                }
                return ValueRange.of(1L, this.lengthOfMonth());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported field: ");
            ((StringBuilder)object).append(temporalField);
            throw new UnsupportedTemporalTypeException(((StringBuilder)object).toString());
        }
        return temporalField.rangeRefinedBy(this);
    }

    @Override
    public long toEpochDay() {
        long l = this.year;
        long l2 = this.month;
        long l3 = 0L + 365L * l;
        l3 = l >= 0L ? (l3 += (3L + l) / 4L - (99L + l) / 100L + (399L + l) / 400L) : (l3 -= l / -4L - l / -100L + l / -400L);
        l = l3 + (367L * l2 - 362L) / 12L + (long)(this.day - 1);
        l3 = l--;
        if (l2 > 2L) {
            l3 = l;
            if (!this.isLeapYear()) {
                l3 = l - 1L;
            }
        }
        return l3 - 719528L;
    }

    @Override
    public String toString() {
        int n = this.year;
        short s = this.month;
        short s2 = this.day;
        int n2 = Math.abs(n);
        StringBuilder stringBuilder = new StringBuilder(10);
        if (n2 < 1000) {
            if (n < 0) {
                stringBuilder.append(n - 10000);
                stringBuilder.deleteCharAt(1);
            } else {
                stringBuilder.append(n + 10000);
                stringBuilder.deleteCharAt(0);
            }
        } else {
            if (n > 9999) {
                stringBuilder.append('+');
            }
            stringBuilder.append(n);
        }
        String string = "-0";
        String string2 = s < 10 ? "-0" : "-";
        stringBuilder.append(string2);
        stringBuilder.append(s);
        string2 = s2 < 10 ? string : "-";
        stringBuilder.append(string2);
        stringBuilder.append(s2);
        return stringBuilder.toString();
    }

    @Override
    public long until(Temporal object, TemporalUnit temporalUnit) {
        object = LocalDate.from((TemporalAccessor)object);
        if (temporalUnit instanceof ChronoUnit) {
            switch ((ChronoUnit)temporalUnit) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unsupported unit: ");
                    ((StringBuilder)object).append(temporalUnit);
                    throw new UnsupportedTemporalTypeException(((StringBuilder)object).toString());
                }
                case ERAS: {
                    return ((LocalDate)object).getLong(ChronoField.ERA) - this.getLong(ChronoField.ERA);
                }
                case MILLENNIA: {
                    return this.monthsUntil((LocalDate)object) / 12000L;
                }
                case CENTURIES: {
                    return this.monthsUntil((LocalDate)object) / 1200L;
                }
                case DECADES: {
                    return this.monthsUntil((LocalDate)object) / 120L;
                }
                case YEARS: {
                    return this.monthsUntil((LocalDate)object) / 12L;
                }
                case MONTHS: {
                    return this.monthsUntil((LocalDate)object);
                }
                case WEEKS: {
                    return this.daysUntil((LocalDate)object) / 7L;
                }
                case DAYS: 
            }
            return this.daysUntil((LocalDate)object);
        }
        return temporalUnit.between(this, (Temporal)object);
    }

    @Override
    public Period until(ChronoLocalDate chronoLocalDate) {
        int n;
        long l;
        LocalDate localDate = LocalDate.from(chronoLocalDate);
        long l2 = localDate.getProlepticMonth() - this.getProlepticMonth();
        int n2 = localDate.day - this.day;
        if (l2 > 0L && n2 < 0) {
            l = l2 - 1L;
            chronoLocalDate = this.plusMonths(l);
            n = (int)(localDate.toEpochDay() - ((LocalDate)chronoLocalDate).toEpochDay());
        } else {
            l = l2;
            n = n2;
            if (l2 < 0L) {
                l = l2;
                n = n2;
                if (n2 > 0) {
                    l = l2 + 1L;
                    n = n2 - localDate.lengthOfMonth();
                }
            }
        }
        l2 = l / 12L;
        n2 = (int)(l % 12L);
        return Period.of(Math.toIntExact(l2), n2, n);
    }

    @Override
    public LocalDate with(TemporalAdjuster temporalAdjuster) {
        if (temporalAdjuster instanceof LocalDate) {
            return (LocalDate)temporalAdjuster;
        }
        return (LocalDate)temporalAdjuster.adjustInto(this);
    }

    @Override
    public LocalDate with(TemporalField object, long l) {
        if (object instanceof ChronoField) {
            Object object2 = (ChronoField)object;
            ((ChronoField)object2).checkValidValue(l);
            switch (1.$SwitchMap$java$time$temporal$ChronoField[((Enum)object2).ordinal()]) {
                default: {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unsupported field: ");
                    ((StringBuilder)object2).append(object);
                    throw new UnsupportedTemporalTypeException(((StringBuilder)object2).toString());
                }
                case 13: {
                    object = this.getLong(ChronoField.ERA) == l ? this : this.withYear(1 - this.year);
                    return object;
                }
                case 12: {
                    return this.withYear((int)l);
                }
                case 11: {
                    return this.plusMonths(l - this.getProlepticMonth());
                }
                case 10: {
                    return this.withMonth((int)l);
                }
                case 9: {
                    return this.plusWeeks(l - this.getLong(ChronoField.ALIGNED_WEEK_OF_YEAR));
                }
                case 8: {
                    return LocalDate.ofEpochDay(l);
                }
                case 7: {
                    return this.plusDays(l - this.getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR));
                }
                case 6: {
                    return this.plusDays(l - this.getLong(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH));
                }
                case 5: {
                    return this.plusDays(l - (long)this.getDayOfWeek().getValue());
                }
                case 4: {
                    if (this.year < 1) {
                        l = 1L - l;
                    }
                    return this.withYear((int)l);
                }
                case 3: {
                    return this.plusWeeks(l - this.getLong(ChronoField.ALIGNED_WEEK_OF_MONTH));
                }
                case 2: {
                    return this.withDayOfYear((int)l);
                }
                case 1: 
            }
            return this.withDayOfMonth((int)l);
        }
        return object.adjustInto(this, l);
    }

    public LocalDate withDayOfMonth(int n) {
        if (this.day == n) {
            return this;
        }
        return LocalDate.of(this.year, this.month, n);
    }

    public LocalDate withDayOfYear(int n) {
        if (this.getDayOfYear() == n) {
            return this;
        }
        return LocalDate.ofYearDay(this.year, n);
    }

    public LocalDate withMonth(int n) {
        if (this.month == n) {
            return this;
        }
        ChronoField.MONTH_OF_YEAR.checkValidValue(n);
        return LocalDate.resolvePreviousValid(this.year, n, this.day);
    }

    public LocalDate withYear(int n) {
        if (this.year == n) {
            return this;
        }
        ChronoField.YEAR.checkValidValue(n);
        return LocalDate.resolvePreviousValid(n, this.month, this.day);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.year);
        dataOutput.writeByte(this.month);
        dataOutput.writeByte(this.day);
    }

}

