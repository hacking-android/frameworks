/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateImpl;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.JapaneseEra;
import java.time.chrono.Ser;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.LocalGregorianCalendar;

public final class JapaneseDate
extends ChronoLocalDateImpl<JapaneseDate>
implements ChronoLocalDate,
Serializable {
    static final LocalDate MEIJI_6_ISODATE = LocalDate.of(1873, 1, 1);
    private static final long serialVersionUID = -305327627230580483L;
    private transient JapaneseEra era;
    private final transient LocalDate isoDate;
    private transient int yearOfEra;

    JapaneseDate(LocalDate localDate) {
        if (!localDate.isBefore(MEIJI_6_ISODATE)) {
            LocalGregorianCalendar.Date date = JapaneseDate.toPrivateJapaneseDate(localDate);
            this.era = JapaneseEra.toJapaneseEra(date.getEra());
            this.yearOfEra = date.getYear();
            this.isoDate = localDate;
            return;
        }
        throw new DateTimeException("JapaneseDate before Meiji 6 is not supported");
    }

    JapaneseDate(JapaneseEra japaneseEra, int n, LocalDate localDate) {
        if (!localDate.isBefore(MEIJI_6_ISODATE)) {
            this.era = japaneseEra;
            this.yearOfEra = n;
            this.isoDate = localDate;
            return;
        }
        throw new DateTimeException("JapaneseDate before Meiji 6 is not supported");
    }

    public static JapaneseDate from(TemporalAccessor temporalAccessor) {
        return JapaneseChronology.INSTANCE.date(temporalAccessor);
    }

    public static JapaneseDate now() {
        return JapaneseDate.now(Clock.systemDefaultZone());
    }

    public static JapaneseDate now(Clock clock) {
        return new JapaneseDate(LocalDate.now(clock));
    }

    public static JapaneseDate now(ZoneId zoneId) {
        return JapaneseDate.now(Clock.system(zoneId));
    }

    public static JapaneseDate of(int n, int n2, int n3) {
        return new JapaneseDate(LocalDate.of(n, n2, n3));
    }

    public static JapaneseDate of(JapaneseEra japaneseEra, int n, int n2, int n3) {
        Objects.requireNonNull(japaneseEra, "era");
        CalendarDate calendarDate = JapaneseChronology.JCAL.newCalendarDate(null);
        ((LocalGregorianCalendar.Date)calendarDate).setEra(japaneseEra.getPrivateEra()).setDate(n, n2, n3);
        if (JapaneseChronology.JCAL.validate(calendarDate)) {
            return new JapaneseDate(japaneseEra, n, LocalDate.of(((LocalGregorianCalendar.Date)calendarDate).getNormalizedYear(), n2, n3));
        }
        throw new DateTimeException("year, month, and day not valid for Era");
    }

    static JapaneseDate ofYearDay(JapaneseEra japaneseEra, int n, int n2) {
        Objects.requireNonNull(japaneseEra, "era");
        CalendarDate calendarDate = japaneseEra.getPrivateEra().getSinceDate();
        CalendarDate calendarDate2 = JapaneseChronology.JCAL.newCalendarDate(null);
        ((LocalGregorianCalendar.Date)calendarDate2).setEra(japaneseEra.getPrivateEra());
        if (n == 1) {
            calendarDate2.setDate(n, calendarDate.getMonth(), calendarDate.getDayOfMonth() + n2 - 1);
        } else {
            calendarDate2.setDate(n, 1, n2);
        }
        JapaneseChronology.JCAL.normalize(calendarDate2);
        if (japaneseEra.getPrivateEra() == calendarDate2.getEra() && n == calendarDate2.getYear()) {
            return new JapaneseDate(japaneseEra, n, LocalDate.of(((LocalGregorianCalendar.Date)calendarDate2).getNormalizedYear(), calendarDate2.getMonth(), calendarDate2.getDayOfMonth()));
        }
        throw new DateTimeException("Invalid parameters");
    }

    static JapaneseDate readExternal(DataInput dataInput) throws IOException {
        int n = dataInput.readInt();
        byte by = dataInput.readByte();
        byte by2 = dataInput.readByte();
        return JapaneseChronology.INSTANCE.date(n, by, by2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private static LocalGregorianCalendar.Date toPrivateJapaneseDate(LocalDate localDate) {
        int n;
        CalendarDate calendarDate = JapaneseChronology.JCAL.newCalendarDate(null);
        sun.util.calendar.Era era = JapaneseEra.privateEraFrom(localDate);
        int n2 = n = localDate.getYear();
        if (era != null) {
            n2 = n - (era.getSinceDate().getYear() - 1);
        }
        ((LocalGregorianCalendar.Date)((LocalGregorianCalendar.Date)calendarDate).setEra(era)).setYear(n2).setMonth(localDate.getMonthValue()).setDayOfMonth(localDate.getDayOfMonth());
        JapaneseChronology.JCAL.normalize(calendarDate);
        return calendarDate;
    }

    private JapaneseDate with(LocalDate chronoLocalDate) {
        chronoLocalDate = chronoLocalDate.equals(this.isoDate) ? this : new JapaneseDate((LocalDate)chronoLocalDate);
        return chronoLocalDate;
    }

    private JapaneseDate withYear(int n) {
        return this.withYear((JapaneseEra)this.getEra(), n);
    }

    private JapaneseDate withYear(JapaneseEra japaneseEra, int n) {
        n = JapaneseChronology.INSTANCE.prolepticYear(japaneseEra, n);
        return this.with(this.isoDate.withYear(n));
    }

    private Object writeReplace() {
        return new Ser(4, this);
    }

    public final ChronoLocalDateTime<JapaneseDate> atTime(LocalTime localTime) {
        return super.atTime(localTime);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof JapaneseDate) {
            object = (JapaneseDate)object;
            return this.isoDate.equals(((JapaneseDate)object).isoDate);
        }
        return false;
    }

    @Override
    public JapaneseChronology getChronology() {
        return JapaneseChronology.INSTANCE;
    }

    @Override
    public JapaneseEra getEra() {
        return this.era;
    }

    @Override
    public long getLong(TemporalField object) {
        if (object instanceof ChronoField) {
            switch ((ChronoField)object) {
                default: {
                    return this.isoDate.getLong((TemporalField)object);
                }
                case ERA: {
                    return this.era.getValue();
                }
                case ALIGNED_DAY_OF_WEEK_IN_MONTH: 
                case ALIGNED_DAY_OF_WEEK_IN_YEAR: 
                case ALIGNED_WEEK_OF_MONTH: 
                case ALIGNED_WEEK_OF_YEAR: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported field: ");
                    stringBuilder.append(object);
                    throw new UnsupportedTemporalTypeException(stringBuilder.toString());
                }
                case YEAR_OF_ERA: {
                    return this.yearOfEra;
                }
                case DAY_OF_YEAR: 
            }
            object = JapaneseChronology.createCalendar();
            ((Calendar)object).set(0, this.era.getValue() + 2);
            ((Calendar)object).set(this.yearOfEra, this.isoDate.getMonthValue() - 1, this.isoDate.getDayOfMonth());
            return ((Calendar)object).get(6);
        }
        return object.getFrom(this);
    }

    @Override
    public int hashCode() {
        return ((JapaneseChronology)this.getChronology()).getId().hashCode() ^ this.isoDate.hashCode();
    }

    @Override
    public boolean isSupported(TemporalField temporalField) {
        if (temporalField != ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH && temporalField != ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR && temporalField != ChronoField.ALIGNED_WEEK_OF_MONTH && temporalField != ChronoField.ALIGNED_WEEK_OF_YEAR) {
            return super.isSupported(temporalField);
        }
        return false;
    }

    @Override
    public int lengthOfMonth() {
        return this.isoDate.lengthOfMonth();
    }

    @Override
    public int lengthOfYear() {
        Calendar calendar = JapaneseChronology.createCalendar();
        calendar.set(0, this.era.getValue() + 2);
        calendar.set(this.yearOfEra, this.isoDate.getMonthValue() - 1, this.isoDate.getDayOfMonth());
        return calendar.getActualMaximum(6);
    }

    @Override
    public JapaneseDate minus(long l, TemporalUnit temporalUnit) {
        return (JapaneseDate)super.minus(l, temporalUnit);
    }

    @Override
    public JapaneseDate minus(TemporalAmount temporalAmount) {
        return (JapaneseDate)super.minus(temporalAmount);
    }

    @Override
    JapaneseDate minusDays(long l) {
        return (JapaneseDate)super.minusDays(l);
    }

    @Override
    JapaneseDate minusMonths(long l) {
        return (JapaneseDate)super.minusMonths(l);
    }

    @Override
    JapaneseDate minusWeeks(long l) {
        return (JapaneseDate)super.minusWeeks(l);
    }

    @Override
    JapaneseDate minusYears(long l) {
        return (JapaneseDate)super.minusYears(l);
    }

    @Override
    public JapaneseDate plus(long l, TemporalUnit temporalUnit) {
        return (JapaneseDate)super.plus(l, temporalUnit);
    }

    @Override
    public JapaneseDate plus(TemporalAmount temporalAmount) {
        return (JapaneseDate)super.plus(temporalAmount);
    }

    @Override
    JapaneseDate plusDays(long l) {
        return this.with(this.isoDate.plusDays(l));
    }

    @Override
    JapaneseDate plusMonths(long l) {
        return this.with(this.isoDate.plusMonths(l));
    }

    @Override
    JapaneseDate plusWeeks(long l) {
        return this.with(this.isoDate.plusWeeks(l));
    }

    @Override
    JapaneseDate plusYears(long l) {
        return this.with(this.isoDate.plusYears(l));
    }

    @Override
    public ValueRange range(TemporalField object) {
        if (object instanceof ChronoField) {
            if (this.isSupported((TemporalField)object)) {
                int n = 1.$SwitchMap$java$time$temporal$ChronoField[((Enum)(object = (ChronoField)object)).ordinal()];
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            return ((JapaneseChronology)this.getChronology()).range((ChronoField)object);
                        }
                        object = JapaneseChronology.createCalendar();
                        ((Calendar)object).set(0, this.era.getValue() + 2);
                        ((Calendar)object).set(this.yearOfEra, this.isoDate.getMonthValue() - 1, this.isoDate.getDayOfMonth());
                        return ValueRange.of(1L, ((Calendar)object).getActualMaximum(1));
                    }
                    return ValueRange.of(1L, this.lengthOfYear());
                }
                return ValueRange.of(1L, this.lengthOfMonth());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported field: ");
            stringBuilder.append(object);
            throw new UnsupportedTemporalTypeException(stringBuilder.toString());
        }
        return object.rangeRefinedBy(this);
    }

    @Override
    public long toEpochDay() {
        return this.isoDate.toEpochDay();
    }

    @Override
    public ChronoPeriod until(ChronoLocalDate object) {
        object = this.isoDate.until((ChronoLocalDate)object);
        return this.getChronology().period(((Period)object).getYears(), ((Period)object).getMonths(), ((Period)object).getDays());
    }

    @Override
    public JapaneseDate with(TemporalAdjuster temporalAdjuster) {
        return (JapaneseDate)super.with(temporalAdjuster);
    }

    @Override
    public JapaneseDate with(TemporalField temporalField, long l) {
        block4 : {
            int n;
            block6 : {
                block7 : {
                    block8 : {
                        block5 : {
                            if (!(temporalField instanceof ChronoField)) break block4;
                            ChronoField chronoField = (ChronoField)temporalField;
                            if (this.getLong(chronoField) == l) {
                                return this;
                            }
                            int n2 = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
                            if (n2 != 3 && n2 != 8 && n2 != 9) break block5;
                            n = ((JapaneseChronology)this.getChronology()).range(chronoField).checkValidIntValue(l, chronoField);
                            n2 = 1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
                            if (n2 == 3) break block6;
                            if (n2 == 8) break block7;
                            if (n2 == 9) break block8;
                        }
                        return this.with(this.isoDate.with(temporalField, l));
                    }
                    return this.with(this.isoDate.withYear(n));
                }
                return this.withYear(JapaneseEra.of(n), this.yearOfEra);
            }
            return this.withYear(n);
        }
        return (JapaneseDate)super.with(temporalField, l);
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(this.get(ChronoField.YEAR));
        dataOutput.writeByte(this.get(ChronoField.MONTH_OF_YEAR));
        dataOutput.writeByte(this.get(ChronoField.DAY_OF_MONTH));
    }

}

