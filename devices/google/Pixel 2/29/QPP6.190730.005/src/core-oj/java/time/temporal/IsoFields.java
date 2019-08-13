/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.DateTimePatternGenerator
 *  android.icu.util.ULocale
 */
package java.time.temporal;

import android.icu.text.DateTimePatternGenerator;
import android.icu.util.ULocale;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class IsoFields {
    public static final TemporalField DAY_OF_QUARTER = Field.DAY_OF_QUARTER;
    public static final TemporalField QUARTER_OF_YEAR = Field.QUARTER_OF_YEAR;
    public static final TemporalUnit QUARTER_YEARS;
    public static final TemporalField WEEK_BASED_YEAR;
    public static final TemporalUnit WEEK_BASED_YEARS;
    public static final TemporalField WEEK_OF_WEEK_BASED_YEAR;

    static {
        WEEK_OF_WEEK_BASED_YEAR = Field.WEEK_OF_WEEK_BASED_YEAR;
        WEEK_BASED_YEAR = Field.WEEK_BASED_YEAR;
        WEEK_BASED_YEARS = Unit.WEEK_BASED_YEARS;
        QUARTER_YEARS = Unit.QUARTER_YEARS;
    }

    private IsoFields() {
        throw new AssertionError((Object)"Not instantiable");
    }

    private static enum Field implements TemporalField
    {
        DAY_OF_QUARTER{

            @Override
            public <R extends Temporal> R adjustInto(R r, long l) {
                long l2 = this.getFrom(r);
                this.range().checkValidValue(l, this);
                return (R)r.with(ChronoField.DAY_OF_YEAR, r.getLong(ChronoField.DAY_OF_YEAR) + (l - l2));
            }

            @Override
            public TemporalUnit getBaseUnit() {
                return ChronoUnit.DAYS;
            }

            @Override
            public long getFrom(TemporalAccessor arrn) {
                if (this.isSupportedBy((TemporalAccessor)arrn)) {
                    int n = arrn.get(ChronoField.DAY_OF_YEAR);
                    int n2 = arrn.get(ChronoField.MONTH_OF_YEAR);
                    long l = arrn.getLong(ChronoField.YEAR);
                    arrn = QUARTER_DAYS;
                    int n3 = (n2 - 1) / 3;
                    n2 = IsoChronology.INSTANCE.isLeapYear(l) ? 4 : 0;
                    return n - arrn[n3 + n2];
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: DayOfQuarter");
            }

            @Override
            public TemporalUnit getRangeUnit() {
                return QUARTER_YEARS;
            }

            @Override
            public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
                boolean bl = temporalAccessor.isSupported(ChronoField.DAY_OF_YEAR) && temporalAccessor.isSupported(ChronoField.MONTH_OF_YEAR) && temporalAccessor.isSupported(ChronoField.YEAR) && Field.isIso(temporalAccessor);
                return bl;
            }

            @Override
            public ValueRange range() {
                return ValueRange.of(1L, 90L, 92L);
            }

            @Override
            public ValueRange rangeRefinedBy(TemporalAccessor object) {
                if (this.isSupportedBy((TemporalAccessor)object)) {
                    long l = object.getLong(QUARTER_OF_YEAR);
                    if (l == 1L) {
                        l = object.getLong(ChronoField.YEAR);
                        object = IsoChronology.INSTANCE.isLeapYear(l) ? ValueRange.of(1L, 91L) : ValueRange.of(1L, 90L);
                        return object;
                    }
                    if (l == 2L) {
                        return ValueRange.of(1L, 91L);
                    }
                    if (l != 3L && l != 4L) {
                        return this.range();
                    }
                    return ValueRange.of(1L, 92L);
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: DayOfQuarter");
            }

            @Override
            public ChronoLocalDate resolve(Map<TemporalField, Long> map, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle) {
                Long l = map.get(ChronoField.YEAR);
                Long l2 = map.get(QUARTER_OF_YEAR);
                if (l != null && l2 != null) {
                    int n = ChronoField.YEAR.checkValidIntValue(l);
                    long l3 = map.get(DAY_OF_QUARTER);
                    Field.ensureIso(temporalAccessor);
                    if (resolverStyle == ResolverStyle.LENIENT) {
                        temporalAccessor = LocalDate.of(n, 1, 1).plusMonths(Math.multiplyExact(Math.subtractExact(l2, 1L), 3L));
                        l3 = Math.subtractExact(l3, 1L);
                    } else {
                        temporalAccessor = LocalDate.of(n, (QUARTER_OF_YEAR.range().checkValidIntValue(l2, QUARTER_OF_YEAR) - 1) * 3 + 1, 1);
                        if (l3 < 1L || l3 > 90L) {
                            if (resolverStyle == ResolverStyle.STRICT) {
                                this.rangeRefinedBy(temporalAccessor).checkValidValue(l3, this);
                            } else {
                                this.range().checkValidValue(l3, this);
                            }
                        }
                        --l3;
                    }
                    map.remove(this);
                    map.remove(ChronoField.YEAR);
                    map.remove(QUARTER_OF_YEAR);
                    return ((LocalDate)temporalAccessor).plusDays(l3);
                }
                return null;
            }

            @Override
            public String toString() {
                return "DayOfQuarter";
            }
        }
        ,
        QUARTER_OF_YEAR{

            @Override
            public <R extends Temporal> R adjustInto(R r, long l) {
                long l2 = this.getFrom(r);
                this.range().checkValidValue(l, this);
                return (R)r.with(ChronoField.MONTH_OF_YEAR, r.getLong(ChronoField.MONTH_OF_YEAR) + (l - l2) * 3L);
            }

            @Override
            public TemporalUnit getBaseUnit() {
                return QUARTER_YEARS;
            }

            @Override
            public long getFrom(TemporalAccessor temporalAccessor) {
                if (this.isSupportedBy(temporalAccessor)) {
                    return (2L + temporalAccessor.getLong(ChronoField.MONTH_OF_YEAR)) / 3L;
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: QuarterOfYear");
            }

            @Override
            public TemporalUnit getRangeUnit() {
                return ChronoUnit.YEARS;
            }

            @Override
            public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
                boolean bl = temporalAccessor.isSupported(ChronoField.MONTH_OF_YEAR) && Field.isIso(temporalAccessor);
                return bl;
            }

            @Override
            public ValueRange range() {
                return ValueRange.of(1L, 4L);
            }

            @Override
            public String toString() {
                return "QuarterOfYear";
            }
        }
        ,
        WEEK_OF_WEEK_BASED_YEAR{

            @Override
            public <R extends Temporal> R adjustInto(R r, long l) {
                this.range().checkValidValue(l, this);
                return (R)r.plus(Math.subtractExact(l, this.getFrom(r)), ChronoUnit.WEEKS);
            }

            @Override
            public TemporalUnit getBaseUnit() {
                return ChronoUnit.WEEKS;
            }

            @Override
            public String getDisplayName(Locale object) {
                Objects.requireNonNull(object, "locale");
                object = DateTimePatternGenerator.getFrozenInstance((ULocale)ULocale.forLocale((Locale)object));
                object = object.getAppendItemName(4);
                if (object == null || ((String)object).isEmpty()) {
                    object = this.toString();
                }
                return object;
            }

            @Override
            public long getFrom(TemporalAccessor temporalAccessor) {
                if (this.isSupportedBy(temporalAccessor)) {
                    return Field.getWeek(LocalDate.from(temporalAccessor));
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: WeekOfWeekBasedYear");
            }

            @Override
            public TemporalUnit getRangeUnit() {
                return WEEK_BASED_YEARS;
            }

            @Override
            public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
                boolean bl = temporalAccessor.isSupported(ChronoField.EPOCH_DAY) && Field.isIso(temporalAccessor);
                return bl;
            }

            @Override
            public ValueRange range() {
                return ValueRange.of(1L, 52L, 53L);
            }

            @Override
            public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
                if (this.isSupportedBy(temporalAccessor)) {
                    return Field.getWeekRange(LocalDate.from(temporalAccessor));
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: WeekOfWeekBasedYear");
            }

            @Override
            public ChronoLocalDate resolve(Map<TemporalField, Long> map, TemporalAccessor temporalAccessor, ResolverStyle resolverStyle) {
                Long l = map.get(WEEK_BASED_YEAR);
                Long l2 = map.get(ChronoField.DAY_OF_WEEK);
                if (l != null && l2 != null) {
                    int n = WEEK_BASED_YEAR.range().checkValidIntValue(l, WEEK_BASED_YEAR);
                    long l3 = map.get(WEEK_OF_WEEK_BASED_YEAR);
                    Field.ensureIso(temporalAccessor);
                    temporalAccessor = LocalDate.of(n, 1, 4);
                    if (resolverStyle == ResolverStyle.LENIENT) {
                        long l4 = l2;
                        if (l4 > 7L) {
                            temporalAccessor = ((LocalDate)temporalAccessor).plusWeeks((l4 - 1L) / 7L);
                            l4 = (l4 - 1L) % 7L + 1L;
                        } else if (l4 < 1L) {
                            temporalAccessor = ((LocalDate)temporalAccessor).plusWeeks(Math.subtractExact(l4, 7L) / 7L);
                            l4 = (6L + l4) % 7L + 1L;
                        }
                        temporalAccessor = ((LocalDate)temporalAccessor).plusWeeks(Math.subtractExact(l3, 1L)).with(ChronoField.DAY_OF_WEEK, l4);
                    } else {
                        n = ChronoField.DAY_OF_WEEK.checkValidIntValue(l2);
                        if (l3 < 1L || l3 > 52L) {
                            if (resolverStyle == ResolverStyle.STRICT) {
                                Field.getWeekRange((LocalDate)temporalAccessor).checkValidValue(l3, this);
                            } else {
                                this.range().checkValidValue(l3, this);
                            }
                        }
                        temporalAccessor = ((LocalDate)temporalAccessor).plusWeeks(l3 - 1L).with(ChronoField.DAY_OF_WEEK, n);
                    }
                    map.remove(this);
                    map.remove(WEEK_BASED_YEAR);
                    map.remove(ChronoField.DAY_OF_WEEK);
                    return temporalAccessor;
                }
                return null;
            }

            @Override
            public String toString() {
                return "WeekOfWeekBasedYear";
            }
        }
        ,
        WEEK_BASED_YEAR{

            @Override
            public <R extends Temporal> R adjustInto(R r, long l) {
                if (this.isSupportedBy(r)) {
                    int n;
                    int n2 = this.range().checkValidIntValue(l, WEEK_BASED_YEAR);
                    LocalDate localDate = LocalDate.from(r);
                    int n3 = localDate.get(ChronoField.DAY_OF_WEEK);
                    int n4 = n = Field.getWeek(localDate);
                    if (n == 53) {
                        n4 = n;
                        if (Field.getWeekRange(n2) == 52) {
                            n4 = 52;
                        }
                    }
                    localDate = LocalDate.of(n2, 1, 4);
                    return (R)r.with(localDate.plusDays(n3 - localDate.get(ChronoField.DAY_OF_WEEK) + (n4 - 1) * 7));
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: WeekBasedYear");
            }

            @Override
            public TemporalUnit getBaseUnit() {
                return WEEK_BASED_YEARS;
            }

            @Override
            public long getFrom(TemporalAccessor temporalAccessor) {
                if (this.isSupportedBy(temporalAccessor)) {
                    return Field.getWeekBasedYear(LocalDate.from(temporalAccessor));
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: WeekBasedYear");
            }

            @Override
            public TemporalUnit getRangeUnit() {
                return ChronoUnit.FOREVER;
            }

            @Override
            public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
                boolean bl = temporalAccessor.isSupported(ChronoField.EPOCH_DAY) && Field.isIso(temporalAccessor);
                return bl;
            }

            @Override
            public ValueRange range() {
                return ChronoField.YEAR.range();
            }

            @Override
            public String toString() {
                return "WeekBasedYear";
            }
        };
        
        private static final int[] QUARTER_DAYS;

        static {
            QUARTER_DAYS = new int[]{0, 90, 181, 273, 0, 91, 182, 274};
        }

        private static void ensureIso(TemporalAccessor temporalAccessor) {
            if (Field.isIso(temporalAccessor)) {
                return;
            }
            throw new DateTimeException("Resolve requires IsoChronology");
        }

        private static int getWeek(LocalDate localDate) {
            int n;
            int n2 = localDate.getDayOfWeek().ordinal();
            int n3 = localDate.getDayOfYear();
            int n4 = 1;
            int n5 = n3 - 1;
            n2 = 3 - n2 + n5;
            n2 = n3 = n2 - n2 / 7 * 7 - 3;
            if (n3 < -3) {
                n2 = n3 + 7;
            }
            if (n5 < n2) {
                return (int)Field.getWeekRange(localDate.withDayOfYear(180).minusYears(1L)).getMaximum();
            }
            n5 = n = (n5 - n2) / 7 + 1;
            if (n == 53) {
                n3 = n4;
                if (n2 != -3) {
                    n3 = n2 == -2 && localDate.isLeapYear() ? n4 : 0;
                }
                n5 = n;
                if (n3 == 0) {
                    n5 = 1;
                }
            }
            return n5;
        }

        private static int getWeekBasedYear(LocalDate localDate) {
            int n;
            block2 : {
                int n2;
                int n3;
                block1 : {
                    n2 = localDate.getYear();
                    n3 = localDate.getDayOfYear();
                    if (n3 > 3) break block1;
                    n = n2;
                    if (n3 - localDate.getDayOfWeek().ordinal() >= -2) break block2;
                    n = n2 - 1;
                    break block2;
                }
                n = n2;
                if (n3 < 363) break block2;
                int n4 = localDate.getDayOfWeek().ordinal();
                n = n2;
                if (n3 - 363 - localDate.isLeapYear() - n4 >= 0) {
                    n = n2 + 1;
                }
            }
            return n;
        }

        private static int getWeekRange(int n) {
            LocalDate localDate = LocalDate.of(n, 1, 1);
            if (!(localDate.getDayOfWeek() == DayOfWeek.THURSDAY || localDate.getDayOfWeek() == DayOfWeek.WEDNESDAY && localDate.isLeapYear())) {
                return 52;
            }
            return 53;
        }

        private static ValueRange getWeekRange(LocalDate localDate) {
            return ValueRange.of(1L, Field.getWeekRange(Field.getWeekBasedYear(localDate)));
        }

        private static boolean isIso(TemporalAccessor temporalAccessor) {
            return Chronology.from(temporalAccessor).equals(IsoChronology.INSTANCE);
        }

        @Override
        public boolean isDateBased() {
            return true;
        }

        @Override
        public boolean isTimeBased() {
            return false;
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
            return this.range();
        }

    }

    private static enum Unit implements TemporalUnit
    {
        WEEK_BASED_YEARS("WeekBasedYears", Duration.ofSeconds(31556952L)),
        QUARTER_YEARS("QuarterYears", Duration.ofSeconds(7889238L));
        
        private final Duration duration;
        private final String name;

        private Unit(String string2, Duration duration) {
            this.name = string2;
            this.duration = duration;
        }

        @Override
        public <R extends Temporal> R addTo(R r, long l) {
            int n = 1.$SwitchMap$java$time$temporal$IsoFields$Unit[this.ordinal()];
            if (n != 1) {
                if (n == 2) {
                    return (R)r.plus(l / 256L, ChronoUnit.YEARS).plus(l % 256L * 3L, ChronoUnit.MONTHS);
                }
                throw new IllegalStateException("Unreachable");
            }
            return (R)r.with(WEEK_BASED_YEAR, Math.addExact((long)r.get(WEEK_BASED_YEAR), l));
        }

        @Override
        public long between(Temporal temporal, Temporal temporal2) {
            if (temporal.getClass() != temporal2.getClass()) {
                return temporal.until(temporal2, this);
            }
            int n = 1.$SwitchMap$java$time$temporal$IsoFields$Unit[this.ordinal()];
            if (n != 1) {
                if (n == 2) {
                    return temporal.until(temporal2, ChronoUnit.MONTHS) / 3L;
                }
                throw new IllegalStateException("Unreachable");
            }
            return Math.subtractExact(temporal2.getLong(WEEK_BASED_YEAR), temporal.getLong(WEEK_BASED_YEAR));
        }

        @Override
        public Duration getDuration() {
            return this.duration;
        }

        @Override
        public boolean isDateBased() {
            return true;
        }

        @Override
        public boolean isDurationEstimated() {
            return true;
        }

        @Override
        public boolean isSupportedBy(Temporal temporal) {
            return temporal.isSupported(ChronoField.EPOCH_DAY);
        }

        @Override
        public boolean isTimeBased() {
            return false;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

}

