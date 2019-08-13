/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.-$
 *  java.time.-$$Lambda
 *  java.time.-$$Lambda$sL_1zXqh7GXCv2G9X40ozp_OBMA
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
import java.time.Year;
import java.time.ZoneId;
import java.time._$$Lambda$sL_1zXqh7GXCv2G9X40ozp_OBMA;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Objects;

public final class MonthDay
implements TemporalAccessor,
TemporalAdjuster,
Comparable<MonthDay>,
Serializable {
    private static final DateTimeFormatter PARSER = new DateTimeFormatterBuilder().appendLiteral("--").appendValue(ChronoField.MONTH_OF_YEAR, 2).appendLiteral('-').appendValue(ChronoField.DAY_OF_MONTH, 2).toFormatter();
    private static final long serialVersionUID = -939150713474957432L;
    private final int day;
    private final int month;

    private MonthDay(int n, int n2) {
        this.month = n;
        this.day = n2;
    }

    public static MonthDay from(TemporalAccessor object) {
        TemporalAccessor temporalAccessor;
        TemporalAccessor temporalAccessor2;
        block5 : {
            if (object instanceof MonthDay) {
                return (MonthDay)object;
            }
            temporalAccessor2 = object;
            temporalAccessor = object;
            try {
                if (IsoChronology.INSTANCE.equals(Chronology.from((TemporalAccessor)object))) break block5;
                temporalAccessor = object;
            }
            catch (DateTimeException dateTimeException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unable to obtain MonthDay from TemporalAccessor: ");
                ((StringBuilder)object).append(temporalAccessor);
                ((StringBuilder)object).append(" of type ");
                ((StringBuilder)object).append(temporalAccessor.getClass().getName());
                throw new DateTimeException(((StringBuilder)object).toString(), dateTimeException);
            }
            temporalAccessor2 = LocalDate.from((TemporalAccessor)object);
        }
        temporalAccessor = temporalAccessor2;
        object = MonthDay.of(temporalAccessor2.get(ChronoField.MONTH_OF_YEAR), temporalAccessor2.get(ChronoField.DAY_OF_MONTH));
        return object;
    }

    public static MonthDay now() {
        return MonthDay.now(Clock.systemDefaultZone());
    }

    public static MonthDay now(Clock object) {
        object = LocalDate.now((Clock)object);
        return MonthDay.of(((LocalDate)object).getMonth(), ((LocalDate)object).getDayOfMonth());
    }

    public static MonthDay now(ZoneId zoneId) {
        return MonthDay.now(Clock.system(zoneId));
    }

    public static MonthDay of(int n, int n2) {
        return MonthDay.of(Month.of(n), n2);
    }

    public static MonthDay of(Month month, int n) {
        Objects.requireNonNull(month, "month");
        ChronoField.DAY_OF_MONTH.checkValidValue(n);
        if (n <= month.maxLength()) {
            return new MonthDay(month.getValue(), n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal value for DayOfMonth field, value ");
        stringBuilder.append(n);
        stringBuilder.append(" is not valid for month ");
        stringBuilder.append(month.name());
        throw new DateTimeException(stringBuilder.toString());
    }

    public static MonthDay parse(CharSequence charSequence) {
        return MonthDay.parse(charSequence, PARSER);
    }

    public static MonthDay parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (MonthDay)dateTimeFormatter.parse(charSequence, _$$Lambda$sL_1zXqh7GXCv2G9X40ozp_OBMA.INSTANCE);
    }

    static MonthDay readExternal(DataInput dataInput) throws IOException {
        return MonthDay.of(dataInput.readByte(), (int)dataInput.readByte());
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser(13, this);
    }

    @Override
    public Temporal adjustInto(Temporal temporal) {
        if (Chronology.from(temporal).equals(IsoChronology.INSTANCE)) {
            temporal = temporal.with(ChronoField.MONTH_OF_YEAR, this.month);
            return temporal.with(ChronoField.DAY_OF_MONTH, Math.min(temporal.range(ChronoField.DAY_OF_MONTH).getMaximum(), (long)this.day));
        }
        throw new DateTimeException("Adjustment only supported on ISO date-time");
    }

    public LocalDate atYear(int n) {
        int n2 = this.month;
        int n3 = this.isValidYear(n) ? this.day : 28;
        return LocalDate.of(n, n2, n3);
    }

    @Override
    public int compareTo(MonthDay monthDay) {
        int n;
        int n2 = n = this.month - monthDay.month;
        if (n == 0) {
            n2 = this.day - monthDay.day;
        }
        return n2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof MonthDay) {
            object = (MonthDay)object;
            if (this.month != ((MonthDay)object).month || this.day != ((MonthDay)object).day) {
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

    public int getDayOfMonth() {
        return this.day;
    }

    @Override
    public long getLong(TemporalField temporalField) {
        if (temporalField instanceof ChronoField) {
            int n = 1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField)temporalField).ordinal()];
            if (n != 1) {
                if (n == 2) {
                    return this.month;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported field: ");
                stringBuilder.append(temporalField);
                throw new UnsupportedTemporalTypeException(stringBuilder.toString());
            }
            return this.day;
        }
        return temporalField.getFrom(this);
    }

    public Month getMonth() {
        return Month.of(this.month);
    }

    public int getMonthValue() {
        return this.month;
    }

    public int hashCode() {
        return (this.month << 6) + this.day;
    }

    public boolean isAfter(MonthDay monthDay) {
        boolean bl = this.compareTo(monthDay) > 0;
        return bl;
    }

    public boolean isBefore(MonthDay monthDay) {
        boolean bl = this.compareTo(monthDay) < 0;
        return bl;
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        boolean bl = temporalField instanceof ChronoField;
        boolean bl2 = true;
        boolean bl3 = true;
        if (bl) {
            bl2 = bl3;
            if (temporalField != ChronoField.MONTH_OF_YEAR) {
                bl2 = temporalField == ChronoField.DAY_OF_MONTH ? bl3 : false;
            }
            return bl2;
        }
        if (temporalField == null || !temporalField.isSupportedBy(this)) {
            bl2 = false;
        }
        return bl2;
    }

    public boolean isValidYear(int n) {
        n = this.day == 29 && this.month == 2 && !Year.isLeap(n) ? 1 : 0;
        return (n ^ 1) != 0;
    }

    @Override
    public <R> R query(TemporalQuery<R> temporalQuery) {
        if (temporalQuery == TemporalQueries.chronology()) {
            return (R)IsoChronology.INSTANCE;
        }
        return TemporalAccessor.super.query(temporalQuery);
    }

    @Override
    public ValueRange range(TemporalField temporalField) {
        if (temporalField == ChronoField.MONTH_OF_YEAR) {
            return temporalField.range();
        }
        if (temporalField == ChronoField.DAY_OF_MONTH) {
            return ValueRange.of(1L, this.getMonth().minLength(), this.getMonth().maxLength());
        }
        return TemporalAccessor.super.range(temporalField);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(10);
        stringBuilder.append("--");
        String string = this.month < 10 ? "0" : "";
        stringBuilder.append(string);
        stringBuilder.append(this.month);
        string = this.day < 10 ? "-0" : "-";
        stringBuilder.append(string);
        stringBuilder.append(this.day);
        return stringBuilder.toString();
    }

    public MonthDay with(Month month) {
        Objects.requireNonNull(month, "month");
        if (month.getValue() == this.month) {
            return this;
        }
        int n = Math.min(this.day, month.maxLength());
        return new MonthDay(month.getValue(), n);
    }

    public MonthDay withDayOfMonth(int n) {
        if (n == this.day) {
            return this;
        }
        return MonthDay.of(this.month, n);
    }

    public MonthDay withMonth(int n) {
        return this.with(Month.of(n));
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(this.month);
        dataOutput.writeByte(this.day);
    }

}

