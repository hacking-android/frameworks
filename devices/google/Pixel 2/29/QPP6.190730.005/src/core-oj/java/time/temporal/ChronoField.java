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
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;
import java.util.Locale;
import java.util.Objects;

public enum ChronoField implements TemporalField
{
    NANO_OF_SECOND("NanoOfSecond", ChronoUnit.NANOS, ChronoUnit.SECONDS, ValueRange.of(0L, 999999999L)),
    NANO_OF_DAY("NanoOfDay", ChronoUnit.NANOS, ChronoUnit.DAYS, ValueRange.of(0L, 86399999999999L)),
    MICRO_OF_SECOND("MicroOfSecond", ChronoUnit.MICROS, ChronoUnit.SECONDS, ValueRange.of(0L, 999999L)),
    MICRO_OF_DAY("MicroOfDay", ChronoUnit.MICROS, ChronoUnit.DAYS, ValueRange.of(0L, 86399999999L)),
    MILLI_OF_SECOND("MilliOfSecond", ChronoUnit.MILLIS, ChronoUnit.SECONDS, ValueRange.of(0L, 999L)),
    MILLI_OF_DAY("MilliOfDay", ChronoUnit.MILLIS, ChronoUnit.DAYS, ValueRange.of(0L, 86399999L)),
    SECOND_OF_MINUTE("SecondOfMinute", ChronoUnit.SECONDS, ChronoUnit.MINUTES, ValueRange.of(0L, 59L), "second"),
    SECOND_OF_DAY("SecondOfDay", ChronoUnit.SECONDS, ChronoUnit.DAYS, ValueRange.of(0L, 86399L)),
    MINUTE_OF_HOUR("MinuteOfHour", ChronoUnit.MINUTES, ChronoUnit.HOURS, ValueRange.of(0L, 59L), "minute"),
    MINUTE_OF_DAY("MinuteOfDay", ChronoUnit.MINUTES, ChronoUnit.DAYS, ValueRange.of(0L, 1439L)),
    HOUR_OF_AMPM("HourOfAmPm", ChronoUnit.HOURS, ChronoUnit.HALF_DAYS, ValueRange.of(0L, 11L)),
    CLOCK_HOUR_OF_AMPM("ClockHourOfAmPm", ChronoUnit.HOURS, ChronoUnit.HALF_DAYS, ValueRange.of(1L, 12L)),
    HOUR_OF_DAY("HourOfDay", ChronoUnit.HOURS, ChronoUnit.DAYS, ValueRange.of(0L, 23L), "hour"),
    CLOCK_HOUR_OF_DAY("ClockHourOfDay", ChronoUnit.HOURS, ChronoUnit.DAYS, ValueRange.of(1L, 24L)),
    AMPM_OF_DAY("AmPmOfDay", ChronoUnit.HALF_DAYS, ChronoUnit.DAYS, ValueRange.of(0L, 1L), "dayperiod"),
    DAY_OF_WEEK("DayOfWeek", ChronoUnit.DAYS, ChronoUnit.WEEKS, ValueRange.of(1L, 7L), "weekday"),
    ALIGNED_DAY_OF_WEEK_IN_MONTH("AlignedDayOfWeekInMonth", ChronoUnit.DAYS, ChronoUnit.WEEKS, ValueRange.of(1L, 7L)),
    ALIGNED_DAY_OF_WEEK_IN_YEAR("AlignedDayOfWeekInYear", ChronoUnit.DAYS, ChronoUnit.WEEKS, ValueRange.of(1L, 7L)),
    DAY_OF_MONTH("DayOfMonth", ChronoUnit.DAYS, ChronoUnit.MONTHS, ValueRange.of(1L, 28L, 31L), "day"),
    DAY_OF_YEAR("DayOfYear", ChronoUnit.DAYS, ChronoUnit.YEARS, ValueRange.of(1L, 365L, 366L)),
    EPOCH_DAY("EpochDay", ChronoUnit.DAYS, ChronoUnit.FOREVER, ValueRange.of(-365249999634L, 365249999634L)),
    ALIGNED_WEEK_OF_MONTH("AlignedWeekOfMonth", ChronoUnit.WEEKS, ChronoUnit.MONTHS, ValueRange.of(1L, 4L, 5L)),
    ALIGNED_WEEK_OF_YEAR("AlignedWeekOfYear", ChronoUnit.WEEKS, ChronoUnit.YEARS, ValueRange.of(1L, 53L)),
    MONTH_OF_YEAR("MonthOfYear", ChronoUnit.MONTHS, ChronoUnit.YEARS, ValueRange.of(1L, 12L), "month"),
    PROLEPTIC_MONTH("ProlepticMonth", ChronoUnit.MONTHS, ChronoUnit.FOREVER, ValueRange.of(-11999999988L, 11999999999L)),
    YEAR_OF_ERA("YearOfEra", ChronoUnit.YEARS, ChronoUnit.FOREVER, ValueRange.of(1L, 999999999L, 1000000000L)),
    YEAR("Year", ChronoUnit.YEARS, ChronoUnit.FOREVER, ValueRange.of(-999999999L, 999999999L), "year"),
    ERA("Era", ChronoUnit.ERAS, ChronoUnit.FOREVER, ValueRange.of(0L, 1L), "era"),
    INSTANT_SECONDS("InstantSeconds", ChronoUnit.SECONDS, ChronoUnit.FOREVER, ValueRange.of(Long.MIN_VALUE, Long.MAX_VALUE)),
    OFFSET_SECONDS("OffsetSeconds", ChronoUnit.SECONDS, ChronoUnit.FOREVER, ValueRange.of(-64800L, 64800L));
    
    private final TemporalUnit baseUnit;
    private final String displayNameKey;
    private final String name;
    private final ValueRange range;
    private final TemporalUnit rangeUnit;

    private ChronoField(String string2, TemporalUnit temporalUnit, TemporalUnit temporalUnit2, ValueRange valueRange) {
        this.name = string2;
        this.baseUnit = temporalUnit;
        this.rangeUnit = temporalUnit2;
        this.range = valueRange;
        this.displayNameKey = null;
    }

    private ChronoField(String string2, TemporalUnit temporalUnit, TemporalUnit temporalUnit2, ValueRange valueRange, String string3) {
        this.name = string2;
        this.baseUnit = temporalUnit;
        this.rangeUnit = temporalUnit2;
        this.range = valueRange;
        this.displayNameKey = string3;
    }

    private static int getIcuFieldNumber(ChronoField chronoField) {
        switch (chronoField) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected ChronoField ");
                stringBuilder.append(chronoField.name());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case ERA: {
                return 0;
            }
            case YEAR: {
                return 1;
            }
            case MONTH_OF_YEAR: {
                return 3;
            }
            case DAY_OF_MONTH: {
                return 7;
            }
            case DAY_OF_WEEK: {
                return 6;
            }
            case AMPM_OF_DAY: {
                return 10;
            }
            case HOUR_OF_DAY: {
                return 11;
            }
            case MINUTE_OF_HOUR: {
                return 12;
            }
            case SECOND_OF_MINUTE: 
        }
        return 13;
    }

    @Override
    public <R extends Temporal> R adjustInto(R r, long l) {
        return (R)r.with(this, l);
    }

    public int checkValidIntValue(long l) {
        return this.range().checkValidIntValue(l, this);
    }

    public long checkValidValue(long l) {
        return this.range().checkValidValue(l, this);
    }

    @Override
    public TemporalUnit getBaseUnit() {
        return this.baseUnit;
    }

    @Override
    public String getDisplayName(Locale object) {
        Objects.requireNonNull(object, "locale");
        if (this.displayNameKey == null) {
            return this.name;
        }
        if ((object = DateTimePatternGenerator.getFrozenInstance((ULocale)ULocale.forLocale((Locale)object)).getAppendItemName(ChronoField.getIcuFieldNumber(this))) == null || ((String)object).isEmpty()) {
            object = this.name;
        }
        return object;
    }

    @Override
    public long getFrom(TemporalAccessor temporalAccessor) {
        return temporalAccessor.getLong(this);
    }

    @Override
    public TemporalUnit getRangeUnit() {
        return this.rangeUnit;
    }

    @Override
    public boolean isDateBased() {
        boolean bl = this.ordinal() >= DAY_OF_WEEK.ordinal() && this.ordinal() <= ERA.ordinal();
        return bl;
    }

    @Override
    public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
        return temporalAccessor.isSupported(this);
    }

    @Override
    public boolean isTimeBased() {
        boolean bl = this.ordinal() < DAY_OF_WEEK.ordinal();
        return bl;
    }

    @Override
    public ValueRange range() {
        return this.range;
    }

    @Override
    public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
        return temporalAccessor.range(this);
    }

    @Override
    public String toString() {
        return this.name;
    }

}

