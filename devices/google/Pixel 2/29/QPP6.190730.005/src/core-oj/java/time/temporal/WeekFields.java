/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.DateTimePatternGenerator
 *  android.icu.util.Calendar
 *  android.icu.util.Calendar$WeekData
 *  android.icu.util.ULocale
 */
package java.time.temporal;

import android.icu.text.DateTimePatternGenerator;
import android.icu.util.Calendar;
import android.icu.util.ULocale;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class WeekFields
implements Serializable {
    private static final ConcurrentMap<String, WeekFields> CACHE = new ConcurrentHashMap<String, WeekFields>(4, 0.75f, 2);
    public static final WeekFields ISO = new WeekFields(DayOfWeek.MONDAY, 4);
    public static final WeekFields SUNDAY_START = WeekFields.of(DayOfWeek.SUNDAY, 1);
    public static final TemporalUnit WEEK_BASED_YEARS = IsoFields.WEEK_BASED_YEARS;
    private static final long serialVersionUID = -1177360819670808121L;
    private final transient TemporalField dayOfWeek = ComputedDayOfField.ofDayOfWeekField(this);
    private final DayOfWeek firstDayOfWeek;
    private final int minimalDays;
    private final transient TemporalField weekBasedYear = ComputedDayOfField.ofWeekBasedYearField(this);
    private final transient TemporalField weekOfMonth = ComputedDayOfField.ofWeekOfMonthField(this);
    private final transient TemporalField weekOfWeekBasedYear = ComputedDayOfField.ofWeekOfWeekBasedYearField(this);
    private final transient TemporalField weekOfYear = ComputedDayOfField.ofWeekOfYearField(this);

    private WeekFields(DayOfWeek dayOfWeek, int n) {
        Objects.requireNonNull(dayOfWeek, "firstDayOfWeek");
        if (n >= 1 && n <= 7) {
            this.firstDayOfWeek = dayOfWeek;
            this.minimalDays = n;
            return;
        }
        throw new IllegalArgumentException("Minimal number of days is invalid");
    }

    public static WeekFields of(DayOfWeek object, int n) {
        Serializable serializable = new StringBuilder();
        serializable.append(((Enum)object).toString());
        serializable.append(n);
        String string = serializable.toString();
        WeekFields weekFields = (WeekFields)CACHE.get(string);
        serializable = weekFields;
        if (weekFields == null) {
            object = new WeekFields((DayOfWeek)object, n);
            CACHE.putIfAbsent(string, (WeekFields)object);
            serializable = (WeekFields)CACHE.get(string);
        }
        return serializable;
    }

    public static WeekFields of(Locale locale) {
        Objects.requireNonNull(locale, "locale");
        locale = Calendar.getWeekDataForRegion((String)ULocale.getRegionForSupplementalData((ULocale)ULocale.forLocale((Locale)locale), (boolean)true));
        return WeekFields.of(DayOfWeek.SUNDAY.plus(((Calendar.WeekData)locale).firstDayOfWeek - 1), ((Calendar.WeekData)locale).minimalDaysInFirstWeek);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException, InvalidObjectException {
        objectInputStream.defaultReadObject();
        if (this.firstDayOfWeek != null) {
            int n = this.minimalDays;
            if (n >= 1 && n <= 7) {
                return;
            }
            throw new InvalidObjectException("Minimal number of days is invalid");
        }
        throw new InvalidObjectException("firstDayOfWeek is null");
    }

    private Object readResolve() throws InvalidObjectException {
        try {
            WeekFields weekFields = WeekFields.of(this.firstDayOfWeek, this.minimalDays);
            return weekFields;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid serialized WeekFields: ");
            stringBuilder.append(illegalArgumentException.getMessage());
            throw new InvalidObjectException(stringBuilder.toString());
        }
    }

    public TemporalField dayOfWeek() {
        return this.dayOfWeek;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof WeekFields) {
            if (this.hashCode() != object.hashCode()) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public DayOfWeek getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    public int getMinimalDaysInFirstWeek() {
        return this.minimalDays;
    }

    public int hashCode() {
        return this.firstDayOfWeek.ordinal() * 7 + this.minimalDays;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WeekFields[");
        stringBuilder.append(this.firstDayOfWeek);
        stringBuilder.append(',');
        stringBuilder.append(this.minimalDays);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public TemporalField weekBasedYear() {
        return this.weekBasedYear;
    }

    public TemporalField weekOfMonth() {
        return this.weekOfMonth;
    }

    public TemporalField weekOfWeekBasedYear() {
        return this.weekOfWeekBasedYear;
    }

    public TemporalField weekOfYear() {
        return this.weekOfYear;
    }

    static class ComputedDayOfField
    implements TemporalField {
        private static final ValueRange DAY_OF_WEEK_RANGE = ValueRange.of(1L, 7L);
        private static final ValueRange WEEK_OF_MONTH_RANGE = ValueRange.of(0L, 1L, 4L, 6L);
        private static final ValueRange WEEK_OF_WEEK_BASED_YEAR_RANGE;
        private static final ValueRange WEEK_OF_YEAR_RANGE;
        private final TemporalUnit baseUnit;
        private final String name;
        private final ValueRange range;
        private final TemporalUnit rangeUnit;
        private final WeekFields weekDef;

        static {
            WEEK_OF_YEAR_RANGE = ValueRange.of(0L, 1L, 52L, 54L);
            WEEK_OF_WEEK_BASED_YEAR_RANGE = ValueRange.of(1L, 52L, 53L);
        }

        private ComputedDayOfField(String string, WeekFields weekFields, TemporalUnit temporalUnit, TemporalUnit temporalUnit2, ValueRange valueRange) {
            this.name = string;
            this.weekDef = weekFields;
            this.baseUnit = temporalUnit;
            this.rangeUnit = temporalUnit2;
            this.range = valueRange;
        }

        private int computeWeek(int n, int n2) {
            return (n + 7 + (n2 - 1)) / 7;
        }

        private int localizedDayOfWeek(int n) {
            return Math.floorMod(n - this.weekDef.getFirstDayOfWeek().getValue(), 7) + 1;
        }

        private int localizedDayOfWeek(TemporalAccessor temporalAccessor) {
            int n = this.weekDef.getFirstDayOfWeek().getValue();
            return Math.floorMod(temporalAccessor.get(ChronoField.DAY_OF_WEEK) - n, 7) + 1;
        }

        private int localizedWeekBasedYear(TemporalAccessor temporalAccessor) {
            int n = this.localizedDayOfWeek(temporalAccessor);
            int n2 = temporalAccessor.get(ChronoField.YEAR);
            int n3 = temporalAccessor.get(ChronoField.DAY_OF_YEAR);
            n = this.startOfWeekOffset(n3, n);
            int n4 = this.computeWeek(n, n3);
            if (n4 == 0) {
                return n2 - 1;
            }
            n3 = (int)temporalAccessor.range(ChronoField.DAY_OF_YEAR).getMaximum();
            if (n4 >= this.computeWeek(n, this.weekDef.getMinimalDaysInFirstWeek() + n3)) {
                return n2 + 1;
            }
            return n2;
        }

        private long localizedWeekOfMonth(TemporalAccessor temporalAccessor) {
            int n = this.localizedDayOfWeek(temporalAccessor);
            int n2 = temporalAccessor.get(ChronoField.DAY_OF_MONTH);
            return this.computeWeek(this.startOfWeekOffset(n2, n), n2);
        }

        private int localizedWeekOfWeekBasedYear(TemporalAccessor temporalAccessor) {
            int n = this.localizedDayOfWeek(temporalAccessor);
            int n2 = temporalAccessor.get(ChronoField.DAY_OF_YEAR);
            int n3 = this.startOfWeekOffset(n2, n);
            n = this.computeWeek(n3, n2);
            if (n == 0) {
                return this.localizedWeekOfWeekBasedYear(Chronology.from(temporalAccessor).date(temporalAccessor).minus(n2, ChronoUnit.DAYS));
            }
            n2 = n;
            if (n > 50) {
                n2 = (int)temporalAccessor.range(ChronoField.DAY_OF_YEAR).getMaximum();
                n3 = this.computeWeek(n3, this.weekDef.getMinimalDaysInFirstWeek() + n2);
                n2 = n;
                if (n >= n3) {
                    n2 = n - n3 + 1;
                }
            }
            return n2;
        }

        private long localizedWeekOfYear(TemporalAccessor temporalAccessor) {
            int n = this.localizedDayOfWeek(temporalAccessor);
            int n2 = temporalAccessor.get(ChronoField.DAY_OF_YEAR);
            return this.computeWeek(this.startOfWeekOffset(n2, n), n2);
        }

        static ComputedDayOfField ofDayOfWeekField(WeekFields weekFields) {
            return new ComputedDayOfField("DayOfWeek", weekFields, ChronoUnit.DAYS, ChronoUnit.WEEKS, DAY_OF_WEEK_RANGE);
        }

        private ChronoLocalDate ofWeekBasedYear(Chronology comparable, int n, int n2, int n3) {
            comparable = comparable.date(n, 1, 1);
            n = this.startOfWeekOffset(1, this.localizedDayOfWeek((TemporalAccessor)((Object)comparable)));
            int n4 = comparable.lengthOfYear();
            n2 = Math.min(n2, this.computeWeek(n, this.weekDef.getMinimalDaysInFirstWeek() + n4) - 1);
            return comparable.plus((long)(-n + (n3 - 1) + (n2 - 1) * 7), (TemporalUnit)ChronoUnit.DAYS);
        }

        static ComputedDayOfField ofWeekBasedYearField(WeekFields weekFields) {
            return new ComputedDayOfField("WeekBasedYear", weekFields, IsoFields.WEEK_BASED_YEARS, ChronoUnit.FOREVER, ChronoField.YEAR.range());
        }

        static ComputedDayOfField ofWeekOfMonthField(WeekFields weekFields) {
            return new ComputedDayOfField("WeekOfMonth", weekFields, ChronoUnit.WEEKS, ChronoUnit.MONTHS, WEEK_OF_MONTH_RANGE);
        }

        static ComputedDayOfField ofWeekOfWeekBasedYearField(WeekFields weekFields) {
            return new ComputedDayOfField("WeekOfWeekBasedYear", weekFields, ChronoUnit.WEEKS, IsoFields.WEEK_BASED_YEARS, WEEK_OF_WEEK_BASED_YEAR_RANGE);
        }

        static ComputedDayOfField ofWeekOfYearField(WeekFields weekFields) {
            return new ComputedDayOfField("WeekOfYear", weekFields, ChronoUnit.WEEKS, ChronoUnit.YEARS, WEEK_OF_YEAR_RANGE);
        }

        private ValueRange rangeByWeek(TemporalAccessor object, TemporalField temporalField) {
            int n = this.localizedDayOfWeek((TemporalAccessor)object);
            n = this.startOfWeekOffset(object.get(temporalField), n);
            object = object.range(temporalField);
            return ValueRange.of(this.computeWeek(n, (int)((ValueRange)object).getMinimum()), this.computeWeek(n, (int)((ValueRange)object).getMaximum()));
        }

        private ValueRange rangeWeekOfWeekBasedYear(TemporalAccessor temporalAccessor) {
            if (!temporalAccessor.isSupported(ChronoField.DAY_OF_YEAR)) {
                return WEEK_OF_YEAR_RANGE;
            }
            int n = this.localizedDayOfWeek(temporalAccessor);
            int n2 = temporalAccessor.get(ChronoField.DAY_OF_YEAR);
            int n3 = this.startOfWeekOffset(n2, n);
            int n4 = this.computeWeek(n3, n2);
            if (n4 == 0) {
                return this.rangeWeekOfWeekBasedYear(Chronology.from(temporalAccessor).date(temporalAccessor).minus(n2 + 7, ChronoUnit.DAYS));
            }
            n = (int)temporalAccessor.range(ChronoField.DAY_OF_YEAR).getMaximum();
            if (n4 >= (n3 = this.computeWeek(n3, this.weekDef.getMinimalDaysInFirstWeek() + n))) {
                return this.rangeWeekOfWeekBasedYear(Chronology.from(temporalAccessor).date(temporalAccessor).plus(n - n2 + 1 + 7, ChronoUnit.DAYS));
            }
            return ValueRange.of(1L, n3 - 1);
        }

        private ChronoLocalDate resolveWBY(Map<TemporalField, Long> map, Chronology comparable, int n, ResolverStyle resolverStyle) {
            int n2 = this.weekDef.weekBasedYear.range().checkValidIntValue(map.get(this.weekDef.weekBasedYear), this.weekDef.weekBasedYear);
            if (resolverStyle == ResolverStyle.LENIENT) {
                comparable = this.ofWeekBasedYear((Chronology)comparable, n2, 1, n).plus(Math.subtractExact(map.get(this.weekDef.weekOfWeekBasedYear), 1L), ChronoUnit.WEEKS);
            } else {
                comparable = this.ofWeekBasedYear((Chronology)comparable, n2, this.weekDef.weekOfWeekBasedYear.range().checkValidIntValue(map.get(this.weekDef.weekOfWeekBasedYear), this.weekDef.weekOfWeekBasedYear), n);
                if (resolverStyle == ResolverStyle.STRICT && this.localizedWeekBasedYear((TemporalAccessor)((Object)comparable)) != n2) {
                    throw new DateTimeException("Strict mode rejected resolved date as it is in a different week-based-year");
                }
            }
            map.remove(this);
            map.remove(this.weekDef.weekBasedYear);
            map.remove(this.weekDef.weekOfWeekBasedYear);
            map.remove(ChronoField.DAY_OF_WEEK);
            return comparable;
        }

        private ChronoLocalDate resolveWoM(Map<TemporalField, Long> map, Chronology comparable, int n, long l, long l2, int n2, ResolverStyle resolverStyle) {
            if (resolverStyle == ResolverStyle.LENIENT) {
                comparable = comparable.date(n, 1, 1).plus(Math.subtractExact(l, 1L), ChronoUnit.MONTHS);
                l = Math.subtractExact(l2, this.localizedWeekOfMonth((TemporalAccessor)((Object)comparable)));
                n = this.localizedDayOfWeek((TemporalAccessor)((Object)comparable));
                comparable = comparable.plus(Math.addExact(Math.multiplyExact(l, 7L), (long)(n2 - n)), (TemporalUnit)ChronoUnit.DAYS);
            } else {
                comparable = comparable.date(n, ChronoField.MONTH_OF_YEAR.checkValidIntValue(l), 1);
                comparable = comparable.plus((long)((int)((long)this.range.checkValidIntValue(l2, this) - this.localizedWeekOfMonth((TemporalAccessor)((Object)comparable))) * 7 + (n2 - this.localizedDayOfWeek((TemporalAccessor)((Object)comparable)))), (TemporalUnit)ChronoUnit.DAYS);
                if (resolverStyle == ResolverStyle.STRICT && comparable.getLong((TemporalField)ChronoField.MONTH_OF_YEAR) != l) {
                    throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
                }
            }
            map.remove(this);
            map.remove(ChronoField.YEAR);
            map.remove(ChronoField.MONTH_OF_YEAR);
            map.remove(ChronoField.DAY_OF_WEEK);
            return comparable;
        }

        private ChronoLocalDate resolveWoY(Map<TemporalField, Long> map, Chronology comparable, int n, long l, int n2, ResolverStyle resolverStyle) {
            comparable = comparable.date(n, 1, 1);
            if (resolverStyle == ResolverStyle.LENIENT) {
                l = Math.subtractExact(l, this.localizedWeekOfYear((TemporalAccessor)((Object)comparable)));
                n = this.localizedDayOfWeek((TemporalAccessor)((Object)comparable));
                comparable = comparable.plus(Math.addExact(Math.multiplyExact(l, 7L), (long)(n2 - n)), (TemporalUnit)ChronoUnit.DAYS);
            } else {
                ChronoLocalDate chronoLocalDate = comparable.plus((long)((int)((long)this.range.checkValidIntValue(l, this) - this.localizedWeekOfYear((TemporalAccessor)((Object)comparable))) * 7 + (n2 - this.localizedDayOfWeek((TemporalAccessor)((Object)comparable)))), (TemporalUnit)ChronoUnit.DAYS);
                comparable = chronoLocalDate;
                if (resolverStyle == ResolverStyle.STRICT) {
                    if (chronoLocalDate.getLong(ChronoField.YEAR) == (long)n) {
                        comparable = chronoLocalDate;
                    } else {
                        throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
                    }
                }
            }
            map.remove(this);
            map.remove(ChronoField.YEAR);
            map.remove(ChronoField.DAY_OF_WEEK);
            return comparable;
        }

        private int startOfWeekOffset(int n, int n2) {
            n2 = Math.floorMod(n - n2, 7);
            n = -n2;
            if (n2 + 1 > this.weekDef.getMinimalDaysInFirstWeek()) {
                n = 7 - n2;
            }
            return n;
        }

        @Override
        public <R extends Temporal> R adjustInto(R r, long l) {
            int n;
            int n2 = this.range.checkValidIntValue(l, this);
            if (n2 == (n = r.get(this))) {
                return r;
            }
            if (this.rangeUnit == ChronoUnit.FOREVER) {
                n2 = r.get(this.weekDef.dayOfWeek);
                n = r.get(this.weekDef.weekOfWeekBasedYear);
                return (R)this.ofWeekBasedYear(Chronology.from(r), (int)l, n, n2);
            }
            return (R)r.plus(n2 - n, this.baseUnit);
        }

        @Override
        public TemporalUnit getBaseUnit() {
            return this.baseUnit;
        }

        @Override
        public String getDisplayName(Locale object) {
            Objects.requireNonNull(object, "locale");
            if (this.rangeUnit == ChronoUnit.YEARS) {
                if ((object = DateTimePatternGenerator.getFrozenInstance((ULocale)ULocale.forLocale((Locale)object)).getAppendItemName(4)) == null || ((String)object).isEmpty()) {
                    object = this.name;
                }
                return object;
            }
            return this.name;
        }

        @Override
        public long getFrom(TemporalAccessor object) {
            if (this.rangeUnit == ChronoUnit.WEEKS) {
                return this.localizedDayOfWeek((TemporalAccessor)object);
            }
            if (this.rangeUnit == ChronoUnit.MONTHS) {
                return this.localizedWeekOfMonth((TemporalAccessor)object);
            }
            if (this.rangeUnit == ChronoUnit.YEARS) {
                return this.localizedWeekOfYear((TemporalAccessor)object);
            }
            if (this.rangeUnit == WEEK_BASED_YEARS) {
                return this.localizedWeekOfWeekBasedYear((TemporalAccessor)object);
            }
            if (this.rangeUnit == ChronoUnit.FOREVER) {
                return this.localizedWeekBasedYear((TemporalAccessor)object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unreachable, rangeUnit: ");
            ((StringBuilder)object).append(this.rangeUnit);
            ((StringBuilder)object).append(", this: ");
            ((StringBuilder)object).append(this);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }

        @Override
        public TemporalUnit getRangeUnit() {
            return this.rangeUnit;
        }

        @Override
        public boolean isDateBased() {
            return true;
        }

        @Override
        public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
            if (temporalAccessor.isSupported(ChronoField.DAY_OF_WEEK)) {
                if (this.rangeUnit == ChronoUnit.WEEKS) {
                    return true;
                }
                if (this.rangeUnit == ChronoUnit.MONTHS) {
                    return temporalAccessor.isSupported(ChronoField.DAY_OF_MONTH);
                }
                if (this.rangeUnit == ChronoUnit.YEARS) {
                    return temporalAccessor.isSupported(ChronoField.DAY_OF_YEAR);
                }
                if (this.rangeUnit == WEEK_BASED_YEARS) {
                    return temporalAccessor.isSupported(ChronoField.DAY_OF_YEAR);
                }
                if (this.rangeUnit == ChronoUnit.FOREVER) {
                    return temporalAccessor.isSupported(ChronoField.YEAR);
                }
            }
            return false;
        }

        @Override
        public boolean isTimeBased() {
            return false;
        }

        @Override
        public ValueRange range() {
            return this.range;
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor object) {
            if (this.rangeUnit == ChronoUnit.WEEKS) {
                return this.range;
            }
            if (this.rangeUnit == ChronoUnit.MONTHS) {
                return this.rangeByWeek((TemporalAccessor)object, ChronoField.DAY_OF_MONTH);
            }
            if (this.rangeUnit == ChronoUnit.YEARS) {
                return this.rangeByWeek((TemporalAccessor)object, ChronoField.DAY_OF_YEAR);
            }
            if (this.rangeUnit == WEEK_BASED_YEARS) {
                return this.rangeWeekOfWeekBasedYear((TemporalAccessor)object);
            }
            if (this.rangeUnit == ChronoUnit.FOREVER) {
                return ChronoField.YEAR.range();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unreachable, rangeUnit: ");
            ((StringBuilder)object).append(this.rangeUnit);
            ((StringBuilder)object).append(", this: ");
            ((StringBuilder)object).append(this);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }

        @Override
        public ChronoLocalDate resolve(Map<TemporalField, Long> map, TemporalAccessor object, ResolverStyle resolverStyle) {
            long l = map.get(this);
            int n = Math.toIntExact(l);
            if (this.rangeUnit == ChronoUnit.WEEKS) {
                n = this.range.checkValidIntValue(l, this);
                l = Math.floorMod(this.weekDef.getFirstDayOfWeek().getValue() - 1 + (n - 1), 7) + 1;
                map.remove(this);
                map.put(ChronoField.DAY_OF_WEEK, l);
                return null;
            }
            if (!map.containsKey(ChronoField.DAY_OF_WEEK)) {
                return null;
            }
            int n2 = this.localizedDayOfWeek(ChronoField.DAY_OF_WEEK.checkValidIntValue(map.get(ChronoField.DAY_OF_WEEK)));
            object = Chronology.from((TemporalAccessor)object);
            if (map.containsKey(ChronoField.YEAR)) {
                int n3 = ChronoField.YEAR.checkValidIntValue(map.get(ChronoField.YEAR));
                if (this.rangeUnit == ChronoUnit.MONTHS && map.containsKey(ChronoField.MONTH_OF_YEAR)) {
                    return this.resolveWoM(map, (Chronology)object, n3, map.get(ChronoField.MONTH_OF_YEAR), n, n2, resolverStyle);
                }
                if (this.rangeUnit == ChronoUnit.YEARS) {
                    return this.resolveWoY(map, (Chronology)object, n3, n, n2, resolverStyle);
                }
            } else if (this.rangeUnit == WEEK_BASED_YEARS || this.rangeUnit == ChronoUnit.FOREVER) {
                if (map.containsKey(this.weekDef.weekBasedYear) && map.containsKey(this.weekDef.weekOfWeekBasedYear)) {
                    return this.resolveWBY(map, (Chronology)object, n2, resolverStyle);
                }
            }
            return null;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.name);
            stringBuilder.append("[");
            stringBuilder.append(this.weekDef.toString());
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

}

