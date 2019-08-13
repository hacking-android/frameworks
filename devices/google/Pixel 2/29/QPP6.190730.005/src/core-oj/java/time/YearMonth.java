/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$102LK-VjqD_Dw4HKR2kUw-BMsRk
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
import java.time.LocalDate;
import java.time.Month;
import java.time.Ser;
import java.time.ZoneId;
import java.time._$$Lambda$102LK_VjqD_Dw4HKR2kUw_BMsRk;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
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

public final class YearMonth
implements Temporal,
TemporalAdjuster,
Comparable<YearMonth>,
Serializable {
    private static final DateTimeFormatter PARSER = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('-').appendValue(ChronoField.MONTH_OF_YEAR, 2).toFormatter();
    private static final long serialVersionUID = 4183400860270640070L;
    private final int month;
    private final int year;

    private YearMonth(int n, int n2) {
        this.year = n;
        this.month = n2;
    }

    public static YearMonth from(TemporalAccessor temporalAccessor) {
        Object object;
        TemporalAccessor temporalAccessor2;
        block5 : {
            if (temporalAccessor instanceof YearMonth) {
                return (YearMonth)temporalAccessor;
            }
            Objects.requireNonNull(temporalAccessor, "temporal");
            object = temporalAccessor;
            temporalAccessor2 = temporalAccessor;
            try {
                if (IsoChronology.INSTANCE.equals(Chronology.from(temporalAccessor))) break block5;
                temporalAccessor2 = temporalAccessor;
            }
            catch (DateTimeException dateTimeException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to obtain YearMonth from TemporalAccessor: ");
                ((StringBuilder)object).append(temporalAccessor2);
                ((StringBuilder)object).append(" of type ");
                ((StringBuilder)object).append(temporalAccessor2.getClass().getName());
                throw new DateTimeException(((StringBuilder)object).toString(), dateTimeException);
            }
            object = LocalDate.from(temporalAccessor);
        }
        temporalAccessor2 = object;
        temporalAccessor = YearMonth.of(object.get(ChronoField.YEAR), object.get(ChronoField.MONTH_OF_YEAR));
        return temporalAccessor;
    }

    private long getProlepticMonth() {
        return (long)this.year * 12L + (long)this.month - 1L;
    }

    public static YearMonth now() {
        return YearMonth.now(Clock.systemDefaultZone());
    }

    public static YearMonth now(Clock object) {
        object = LocalDate.now((Clock)object);
        return YearMonth.of(((LocalDate)object).getYear(), ((LocalDate)object).getMonth());
    }

    public static YearMonth now(ZoneId zoneId) {
        return YearMonth.now(Clock.system(zoneId));
    }

    public static YearMonth of(int n, int n2) {
        ChronoField.YEAR.checkValidValue(n);
        ChronoField.MONTH_OF_YEAR.checkValidValue(n2);
        return new YearMonth(n, n2);
    }

    public static YearMonth of(int n, Month month) {
        Objects.requireNonNull(month, "month");
        return YearMonth.of(n, month.getValue());
    }

    public static YearMonth parse(CharSequence charSequence) {
        return YearMonth.parse(charSequence, PARSER);
    }

    public static YearMonth parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (YearMonth)dateTimeFormatter.parse(charSequence, _$$Lambda$102LK_VjqD_Dw4HKR2kUw_BMsRk.INSTANCE);
    }

    static YearMonth readExternal(DataInput dataInput) throws IOException {
        return YearMonth.of(dataInput.readInt(), dataInput.readByte());
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private YearMonth with(int n, int n2) {
        if (this.year == n && this.month == n2) {
            return this;
        }
        return new YearMonth(n, n2);
    }

    private Object writeReplace() {
        return new Ser(12, this);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        if (Chronology.from(temporal).equals(IsoChronology.INSTANCE)) {
            return temporal.with(ChronoField.PROLEPTIC_MONTH, this.getProlepticMonth());
        }
        throw new DateTimeException("Adjustment only supported on ISO date-time");
    }

    public LocalDate atDay(int n) {
        return LocalDate.of(this.year, this.month, n);
    }

    public LocalDate atEndOfMonth() {
        return LocalDate.of(this.year, this.month, this.lengthOfMonth());
    }

    @Override
    public int compareTo(YearMonth yearMonth) {
        int n;
        int n2 = n = this.year - yearMonth.year;
        if (n == 0) {
            n2 = this.month - yearMonth.month;
        }
        return n2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof YearMonth) {
            object = (YearMonth)object;
            if (this.year != ((YearMonth)object).year || this.month != ((YearMonth)object).month) {
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
        return this.range(temporalField).checkValidIntValue(this.getLong(temporalField), temporalField);
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)temporalField).ordinal()];
            int n2 = 1;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n == 5) {
                                if (this.year < 1) {
                                    n2 = 0;
                                }
                                return n2;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unsupported field: ");
                            stringBuilder.append(temporalField);
                            throw new UnsupportedTemporalTypeException(stringBuilder.toString());
                        }
                        return this.year;
                    }
                    n2 = n = this.year;
                    if (n < 1) {
                        n2 = 1 - n;
                    }
                    return n2;
                }
                return this.getProlepticMonth();
            }
            return this.month;
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

    public int hashCode() {
        return this.year ^ this.month << 27;
    }

    public boolean isAfter(YearMonth yearMonth) {
        boolean bl = this.compareTo(yearMonth) > 0;
        return bl;
    }

    public boolean isBefore(YearMonth yearMonth) {
        boolean bl = this.compareTo(yearMonth) < 0;
        return bl;
    }

    public boolean isLeapYear() {
        return IsoChronology.INSTANCE.isLeapYear(this.year);
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        boolean bl = temporalField instanceof ChronoField;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            bl2 = bl3;
            if (temporalField != ChronoField.YEAR) {
                bl2 = bl3;
                if (temporalField != ChronoField.MONTH_OF_YEAR) {
                    bl2 = bl3;
                    if (temporalField != ChronoField.PROLEPTIC_MONTH) {
                        bl2 = bl3;
                        if (temporalField != ChronoField.YEAR_OF_ERA) {
                            bl2 = temporalField == ChronoField.ERA ? bl3 : false;
                        }
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
            if (temporalUnit != ChronoUnit.MONTHS) {
                bl2 = bl3;
                if (temporalUnit != ChronoUnit.YEARS) {
                    bl2 = bl3;
                    if (temporalUnit != ChronoUnit.DECADES) {
                        bl2 = bl3;
                        if (temporalUnit != ChronoUnit.CENTURIES) {
                            bl2 = bl3;
                            if (temporalUnit != ChronoUnit.MILLENNIA) {
                                bl2 = temporalUnit == ChronoUnit.ERAS ? bl3 : false;
                            }
                        }
                    }
                }
            }
            return bl2;
        }
        if (temporalUnit == null || !temporalUnit.isSupportedBy(this)) {
            bl2 = false;
        }
        return bl2;
    }

    public boolean isValidDay(int n) {
        boolean bl = true;
        if (n < 1 || n > this.lengthOfMonth()) {
            bl = false;
        }
        return bl;
    }

    public int lengthOfMonth() {
        return this.getMonth().length(this.isLeapYear());
    }

    public int lengthOfYear() {
        int n = this.isLeapYear() ? 366 : 365;
        return n;
    }

    @Override
    public YearMonth minus(long l, TemporalUnit object) {
        object = l == Long.MIN_VALUE ? this.plus(Long.MAX_VALUE, (TemporalUnit)object).plus(1L, (TemporalUnit)object) : this.plus(-l, (TemporalUnit)object);
        return object;
    }

    @Override
    public YearMonth minus(TemporalAmount temporalAmount) {
        return (YearMonth)temporalAmount.subtractFrom(this);
    }

    public YearMonth minusMonths(long l) {
        YearMonth yearMonth = l == Long.MIN_VALUE ? this.plusMonths(Long.MAX_VALUE).plusMonths(1L) : this.plusMonths(-l);
        return yearMonth;
    }

    public YearMonth minusYears(long l) {
        YearMonth yearMonth = l == Long.MIN_VALUE ? this.plusYears(Long.MAX_VALUE).plusYears(1L) : this.plusYears(-l);
        return yearMonth;
    }

    @Override
    public YearMonth plus(long l, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            switch ((ChronoUnit)temporalUnit) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported unit: ");
                    stringBuilder.append(temporalUnit);
                    throw new UnsupportedTemporalTypeException(stringBuilder.toString());
                }
                case ERAS: {
                    return this.with(ChronoField.ERA, Math.addExact(this.getLong(ChronoField.ERA), l));
                }
                case MILLENNIA: {
                    return this.plusYears(Math.multiplyExact(l, 1000L));
                }
                case CENTURIES: {
                    return this.plusYears(Math.multiplyExact(l, 100L));
                }
                case DECADES: {
                    return this.plusYears(Math.multiplyExact(l, 10L));
                }
                case YEARS: {
                    return this.plusYears(l);
                }
                case MONTHS: 
            }
            return this.plusMonths(l);
        }
        return temporalUnit.addTo(this, l);
    }

    @Override
    public YearMonth plus(TemporalAmount temporalAmount) {
        return (YearMonth)temporalAmount.addTo(this);
    }

    public YearMonth plusMonths(long l) {
        if (l == 0L) {
            return this;
        }
        l = (long)this.year * 12L + (long)(this.month - 1) + l;
        return this.with(ChronoField.YEAR.checkValidIntValue(Math.floorDiv(l, 12L)), (int)Math.floorMod(l, 12L) + 1);
    }

    public YearMonth plusYears(long l) {
        if (l == 0L) {
            return this;
        }
        return this.with(ChronoField.YEAR.checkValidIntValue((long)this.year + l), this.month);
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery == TemporalQueries.chronology()) {
            return (R)IsoChronology.INSTANCE;
        }
        if (temporalQuery == TemporalQueries.precision()) {
            return (R)ChronoUnit.MONTHS;
        }
        return Temporal.super.query(temporalQuery);
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        if (temporalField == ChronoField.YEAR_OF_ERA) {
            long l = this.getYear() <= 0 ? 1000000000L : 999999999L;
            return ValueRange.of(1L, l);
        }
        return Temporal.super.range(temporalField);
    }

    public String toString() {
        int n = Math.abs(this.year);
        StringBuilder stringBuilder = new StringBuilder(9);
        if (n < 1000) {
            n = this.year;
            if (n < 0) {
                stringBuilder.append(n - 10000);
                stringBuilder.deleteCharAt(1);
            } else {
                stringBuilder.append(n + 10000);
                stringBuilder.deleteCharAt(0);
            }
        } else {
            stringBuilder.append(this.year);
        }
        String string = this.month < 10 ? "-0" : "-";
        stringBuilder.append(string);
        stringBuilder.append(this.month);
        return stringBuilder.toString();
    }

    @Override
    public long until(Temporal object, TemporalUnit temporalUnit) {
        object = YearMonth.from((TemporalAccessor)object);
        if (temporalUnit instanceof ChronoUnit) {
            long l = YearMonth.super.getProlepticMonth() - this.getProlepticMonth();
            switch ((ChronoUnit)temporalUnit) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unsupported unit: ");
                    ((StringBuilder)object).append(temporalUnit);
                    throw new UnsupportedTemporalTypeException(((StringBuilder)object).toString());
                }
                case ERAS: {
                    return ((YearMonth)object).getLong(ChronoField.ERA) - this.getLong(ChronoField.ERA);
                }
                case MILLENNIA: {
                    return l / 12000L;
                }
                case CENTURIES: {
                    return l / 1200L;
                }
                case DECADES: {
                    return l / 120L;
                }
                case YEARS: {
                    return l / 12L;
                }
                case MONTHS: 
            }
            return l;
        }
        return temporalUnit.between(this, (Temporal)object);
    }

    @Override
    public YearMonth with(TemporalAdjuster temporalAdjuster) {
        return (YearMonth)temporalAdjuster.adjustInto(this);
    }

    @Override
    public YearMonth with(TemporalField object, long l) {
        if (object instanceof ChronoField) {
            Object object2 = (ChronoField)object;
            ((ChronoField)object2).checkValidValue(l);
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((Enum)object2).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n == 5) {
                                object = this.getLong(ChronoField.ERA) == l ? this : this.withYear(1 - this.year);
                                return object;
                            }
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Unsupported field: ");
                            ((StringBuilder)object2).append(object);
                            throw new UnsupportedTemporalTypeException(((StringBuilder)object2).toString());
                        }
                        return this.withYear((int)l);
                    }
                    if (this.year < 1) {
                        l = 1L - l;
                    }
                    return this.withYear((int)l);
                }
                return this.plusMonths(l - this.getProlepticMonth());
            }
            return this.withMonth((int)l);
        }
        return object.adjustInto(this, l);
    }

    public YearMonth withMonth(int n) {
        ChronoField.MONTH_OF_YEAR.checkValidValue(n);
        return this.with(this.year, n);
    }

    public YearMonth withYear(int n) {
        ChronoField.YEAR.checkValidValue(n);
        return this.with(n, this.month);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.year);
        dataOutput.writeByte(this.month);
    }

}

