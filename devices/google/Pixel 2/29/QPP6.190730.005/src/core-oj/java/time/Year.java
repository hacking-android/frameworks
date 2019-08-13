/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$1t2bycXU085eFZcwODXkbd0X4Bk
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
import java.time.MonthDay;
import java.time.Ser;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time._$$Lambda$1t2bycXU085eFZcwODXkbd0X4Bk;
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

public final class Year
implements Temporal,
TemporalAdjuster,
Comparable<Year>,
Serializable {
    public static final int MAX_VALUE = 999999999;
    public static final int MIN_VALUE = -999999999;
    private static final DateTimeFormatter PARSER = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).toFormatter();
    private static final long serialVersionUID = -23038383694477807L;
    private final int year;

    private Year(int n) {
        this.year = n;
    }

    public static Year from(TemporalAccessor temporalAccessor) {
        Object object;
        TemporalAccessor temporalAccessor2;
        block5 : {
            if (temporalAccessor instanceof Year) {
                return (Year)temporalAccessor;
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
                ((StringBuilder)object).append("Unable to obtain Year from TemporalAccessor: ");
                ((StringBuilder)object).append(temporalAccessor2);
                ((StringBuilder)object).append(" of type ");
                ((StringBuilder)object).append(temporalAccessor2.getClass().getName());
                throw new DateTimeException(((StringBuilder)object).toString(), dateTimeException);
            }
            object = LocalDate.from(temporalAccessor);
        }
        temporalAccessor2 = object;
        temporalAccessor = Year.of(object.get(ChronoField.YEAR));
        return temporalAccessor;
    }

    public static boolean isLeap(long l) {
        boolean bl = (3L & l) == 0L && (l % 100L != 0L || l % 400L == 0L);
        return bl;
    }

    public static Year now() {
        return Year.now(Clock.systemDefaultZone());
    }

    public static Year now(Clock clock) {
        return Year.of(LocalDate.now(clock).getYear());
    }

    public static Year now(ZoneId zoneId) {
        return Year.now(Clock.system(zoneId));
    }

    public static Year of(int n) {
        ChronoField.YEAR.checkValidValue(n);
        return new Year(n);
    }

    public static Year parse(CharSequence charSequence) {
        return Year.parse(charSequence, PARSER);
    }

    public static Year parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (Year)dateTimeFormatter.parse(charSequence, _$$Lambda$1t2bycXU085eFZcwODXkbd0X4Bk.INSTANCE);
    }

    static Year readExternal(DataInput dataInput) throws IOException {
        return Year.of(dataInput.readInt());
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser(11, this);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        if (Chronology.from(temporal).equals(IsoChronology.INSTANCE)) {
            return temporal.with(ChronoField.YEAR, this.year);
        }
        throw new DateTimeException("Adjustment only supported on ISO date-time");
    }

    public LocalDate atDay(int n) {
        return LocalDate.ofYearDay(this.year, n);
    }

    public YearMonth atMonth(int n) {
        return YearMonth.of(this.year, n);
    }

    public YearMonth atMonth(Month month) {
        return YearMonth.of(this.year, month);
    }

    public LocalDate atMonthDay(MonthDay monthDay) {
        return monthDay.atYear(this.year);
    }

    @Override
    public int compareTo(Year year) {
        return this.year - year.year;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof Year) {
            if (this.year != ((Year)object).year) {
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
                    if (n == 3) {
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
        return temporalField.getFrom(this);
    }

    public int getValue() {
        return this.year;
    }

    public int hashCode() {
        return this.year;
    }

    public boolean isAfter(Year year) {
        boolean bl = this.year > year.year;
        return bl;
    }

    public boolean isBefore(Year year) {
        boolean bl = this.year < year.year;
        return bl;
    }

    public boolean isLeap() {
        return Year.isLeap(this.year);
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
                if (temporalField != ChronoField.YEAR_OF_ERA) {
                    bl2 = temporalField == ChronoField.ERA ? bl3 : false;
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
            return bl2;
        }
        if (temporalUnit == null || !temporalUnit.isSupportedBy(this)) {
            bl2 = false;
        }
        return bl2;
    }

    public boolean isValidMonthDay(MonthDay monthDay) {
        boolean bl = monthDay != null && monthDay.isValidYear(this.year);
        return bl;
    }

    public int length() {
        int n = this.isLeap() ? 366 : 365;
        return n;
    }

    @Override
    public Year minus(long l, TemporalUnit object) {
        object = l == Long.MIN_VALUE ? this.plus(Long.MAX_VALUE, (TemporalUnit)object).plus(1L, (TemporalUnit)object) : this.plus(-l, (TemporalUnit)object);
        return object;
    }

    @Override
    public Year minus(TemporalAmount temporalAmount) {
        return (Year)temporalAmount.subtractFrom(this);
    }

    public Year minusYears(long l) {
        Year year = l == Long.MIN_VALUE ? this.plusYears(Long.MAX_VALUE).plusYears(1L) : this.plusYears(-l);
        return year;
    }

    @Override
    public Year plus(long l, TemporalUnit temporalUnit) {
        if (temporalUnit instanceof ChronoUnit) {
            int n = 1.$SwitchMap$java$time$temporal$ChronoUnit[((ChronoUnit)temporalUnit).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n == 5) {
                                return this.with(ChronoField.ERA, Math.addExact(this.getLong(ChronoField.ERA), l));
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unsupported unit: ");
                            stringBuilder.append(temporalUnit);
                            throw new UnsupportedTemporalTypeException(stringBuilder.toString());
                        }
                        return this.plusYears(Math.multiplyExact(l, 1000L));
                    }
                    return this.plusYears(Math.multiplyExact(l, 100L));
                }
                return this.plusYears(Math.multiplyExact(l, 10L));
            }
            return this.plusYears(l);
        }
        return temporalUnit.addTo(this, l);
    }

    @Override
    public Year plus(TemporalAmount temporalAmount) {
        return (Year)temporalAmount.addTo(this);
    }

    public Year plusYears(long l) {
        if (l == 0L) {
            return this;
        }
        return Year.of(ChronoField.YEAR.checkValidIntValue((long)this.year + l));
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery == TemporalQueries.chronology()) {
            return (R)IsoChronology.INSTANCE;
        }
        if (temporalQuery == TemporalQueries.precision()) {
            return (R)ChronoUnit.YEARS;
        }
        return Temporal.super.query(temporalQuery);
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        if (temporalField == ChronoField.YEAR_OF_ERA) {
            long l = this.year <= 0 ? 1000000000L : 999999999L;
            return ValueRange.of(1L, l);
        }
        return Temporal.super.range(temporalField);
    }

    public String toString() {
        return Integer.toString(this.year);
    }

    @Override
    public long until(Temporal object, TemporalUnit temporalUnit) {
        object = Year.from((TemporalAccessor)object);
        if (temporalUnit instanceof ChronoUnit) {
            long l = (long)((Year)object).year - (long)this.year;
            int n = 1.$SwitchMap$java$time$temporal$ChronoUnit[((ChronoUnit)temporalUnit).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n == 5) {
                                return ((Year)object).getLong(ChronoField.ERA) - this.getLong(ChronoField.ERA);
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unsupported unit: ");
                            ((StringBuilder)object).append(temporalUnit);
                            throw new UnsupportedTemporalTypeException(((StringBuilder)object).toString());
                        }
                        return l / 1000L;
                    }
                    return l / 100L;
                }
                return l / 10L;
            }
            return l;
        }
        return temporalUnit.between(this, (Temporal)object);
    }

    @Override
    public Year with(TemporalAdjuster temporalAdjuster) {
        return (Year)temporalAdjuster.adjustInto(this);
    }

    @Override
    public Year with(TemporalField object, long l) {
        if (object instanceof ChronoField) {
            Object object2 = (ChronoField)object;
            ((ChronoField)object2).checkValidValue(l);
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((Enum)object2).ordinal()];
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        object = this.getLong(ChronoField.ERA) == l ? this : Year.of(1 - this.year);
                        return object;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unsupported field: ");
                    ((StringBuilder)object2).append(object);
                    throw new UnsupportedTemporalTypeException(((StringBuilder)object2).toString());
                }
                return Year.of((int)l);
            }
            if (this.year < 1) {
                l = 1L - l;
            }
            return Year.of((int)l);
        }
        return object.adjustInto(this, l);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.year);
    }

}

