/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseDate;
import java.time.chrono.JapaneseEra;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarSystem;
import sun.util.calendar.LocalGregorianCalendar;

public final class JapaneseChronology
extends AbstractChronology
implements Serializable {
    public static final JapaneseChronology INSTANCE;
    static final LocalGregorianCalendar JCAL;
    private static final Locale LOCALE;
    private static final long serialVersionUID = 459996390165777884L;

    static {
        JCAL = (LocalGregorianCalendar)CalendarSystem.forName("japanese");
        LOCALE = Locale.forLanguageTag("ja-JP-u-ca-japanese");
        INSTANCE = new JapaneseChronology();
    }

    private JapaneseChronology() {
    }

    static Calendar createCalendar() {
        return Calendar.getJapaneseImperialInstance(TimeZone.getDefault(), LOCALE);
    }

    private int prolepticYearLenient(JapaneseEra japaneseEra, int n) {
        return japaneseEra.getPrivateEra().getSinceDate().getYear() + n - 1;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private ChronoLocalDate resolveYD(JapaneseEra japaneseEra, int n, Map<TemporalField, Long> map, ResolverStyle resolverStyle) {
        map.remove(ChronoField.ERA);
        map.remove(ChronoField.YEAR_OF_ERA);
        if (resolverStyle == ResolverStyle.LENIENT) {
            n = this.prolepticYearLenient(japaneseEra, n);
            long l = Math.subtractExact(map.remove(ChronoField.DAY_OF_YEAR), 1L);
            return ((JapaneseDate)this.dateYearDay(n, 1)).plus(l, ChronoUnit.DAYS);
        }
        return this.dateYearDay(japaneseEra, n, this.range(ChronoField.DAY_OF_YEAR).checkValidIntValue(map.remove(ChronoField.DAY_OF_YEAR), ChronoField.DAY_OF_YEAR));
    }

    private ChronoLocalDate resolveYMD(JapaneseEra serializable, int n, Map<TemporalField, Long> object, ResolverStyle resolverStyle) {
        object.remove(ChronoField.ERA);
        object.remove(ChronoField.YEAR_OF_ERA);
        if (resolverStyle == ResolverStyle.LENIENT) {
            n = this.prolepticYearLenient((JapaneseEra)serializable, n);
            long l = Math.subtractExact(object.remove(ChronoField.MONTH_OF_YEAR), 1L);
            long l2 = Math.subtractExact(object.remove(ChronoField.DAY_OF_MONTH), 1L);
            return ((JapaneseDate)((JapaneseDate)this.date(n, 1, 1)).plus(l, ChronoUnit.MONTHS)).plus(l2, ChronoUnit.DAYS);
        }
        int n2 = this.range(ChronoField.MONTH_OF_YEAR).checkValidIntValue(object.remove(ChronoField.MONTH_OF_YEAR), ChronoField.MONTH_OF_YEAR);
        int n3 = this.range(ChronoField.DAY_OF_MONTH).checkValidIntValue(object.remove(ChronoField.DAY_OF_MONTH), ChronoField.DAY_OF_MONTH);
        if (resolverStyle == ResolverStyle.SMART) {
            if (n >= 1) {
                int n4 = this.prolepticYearLenient((JapaneseEra)serializable, n);
                try {
                    object = this.date(n4, n2, n3);
                }
                catch (DateTimeException dateTimeException) {
                    object = ((JapaneseDate)this.date(n4, n2, 1)).with(TemporalAdjusters.lastDayOfMonth());
                }
                if (((JapaneseDate)object).getEra() != serializable && object.get(ChronoField.YEAR_OF_ERA) > 1 && n > 1) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid YearOfEra for Era: ");
                    ((StringBuilder)object).append(serializable);
                    ((StringBuilder)object).append(" ");
                    ((StringBuilder)object).append(n);
                    throw new DateTimeException(((StringBuilder)object).toString());
                }
                return object;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Invalid YearOfEra: ");
            ((StringBuilder)serializable).append(n);
            throw new DateTimeException(((StringBuilder)serializable).toString());
        }
        return this.date((Era)((Object)serializable), n, n2, n3);
    }

    @Override
    public JapaneseDate date(int n, int n2, int n3) {
        return new JapaneseDate(LocalDate.of(n, n2, n3));
    }

    @Override
    public JapaneseDate date(Era era, int n, int n2, int n3) {
        if (era instanceof JapaneseEra) {
            return JapaneseDate.of((JapaneseEra)era, n, n2, n3);
        }
        throw new ClassCastException("Era must be JapaneseEra");
    }

    @Override
    public JapaneseDate date(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof JapaneseDate) {
            return (JapaneseDate)temporalAccessor;
        }
        return new JapaneseDate(LocalDate.from(temporalAccessor));
    }

    @Override
    public JapaneseDate dateEpochDay(long l) {
        return new JapaneseDate(LocalDate.ofEpochDay(l));
    }

    @Override
    public JapaneseDate dateNow() {
        return this.dateNow(Clock.systemDefaultZone());
    }

    @Override
    public JapaneseDate dateNow(Clock clock) {
        return this.date(LocalDate.now(clock));
    }

    @Override
    public JapaneseDate dateNow(ZoneId zoneId) {
        return this.dateNow(Clock.system(zoneId));
    }

    @Override
    public JapaneseDate dateYearDay(int n, int n2) {
        return new JapaneseDate(LocalDate.ofYearDay(n, n2));
    }

    @Override
    public JapaneseDate dateYearDay(Era era, int n, int n2) {
        return JapaneseDate.ofYearDay((JapaneseEra)era, n, n2);
    }

    @Override
    public JapaneseEra eraOf(int n) {
        return JapaneseEra.of(n);
    }

    @Override
    public List<Era> eras() {
        return Arrays.asList(JapaneseEra.values());
    }

    @Override
    public String getCalendarType() {
        return "japanese";
    }

    JapaneseEra getCurrentEra() {
        JapaneseEra[] arrjapaneseEra = JapaneseEra.values();
        return arrjapaneseEra[arrjapaneseEra.length - 1];
    }

    @Override
    public String getId() {
        return "Japanese";
    }

    @Override
    public boolean isLeapYear(long l) {
        return IsoChronology.INSTANCE.isLeapYear(l);
    }

    public ChronoLocalDateTime<JapaneseDate> localDateTime(TemporalAccessor temporalAccessor) {
        return super.localDateTime(temporalAccessor);
    }

    @Override
    public int prolepticYear(Era object, int n) {
        if (object instanceof JapaneseEra) {
            JapaneseEra japaneseEra = (JapaneseEra)object;
            int n2 = japaneseEra.getPrivateEra().getSinceDate().getYear() + n - 1;
            if (n == 1) {
                return n2;
            }
            if (n2 >= -999999999 && n2 <= 999999999) {
                object = JCAL.newCalendarDate(null);
                ((LocalGregorianCalendar.Date)object).setEra(japaneseEra.getPrivateEra()).setDate(n, 1, 1);
                if (JCAL.validate((CalendarDate)object)) {
                    return n2;
                }
            }
            throw new DateTimeException("Invalid yearOfEra value");
        }
        throw new ClassCastException("Era must be JapaneseEra");
    }

    @Override
    public ValueRange range(ChronoField object) {
        switch (1.$SwitchMap$java$time$temporal$ChronoField[((Enum)object).ordinal()]) {
            default: {
                return ((ChronoField)object).range();
            }
            case 8: {
                return ValueRange.of(JapaneseEra.MEIJI.getValue(), this.getCurrentEra().getValue());
            }
            case 7: {
                return ValueRange.of(JapaneseDate.MEIJI_6_ISODATE.getYear(), 999999999L);
            }
            case 6: {
                object = JapaneseChronology.createCalendar();
                return ValueRange.of(((Calendar)object).getMinimum(6), ((Calendar)object).getGreatestMinimum(6), ((Calendar)object).getLeastMaximum(6), ((Calendar)object).getMaximum(6));
            }
            case 5: {
                object = JapaneseChronology.createCalendar();
                int n = this.getCurrentEra().getPrivateEra().getSinceDate().getYear();
                return ValueRange.of(1L, ((Calendar)object).getGreatestMinimum(1), ((Calendar)object).getLeastMaximum(1) + 1, 999999999 - n);
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported field: ");
        stringBuilder.append(object);
        throw new UnsupportedTemporalTypeException(stringBuilder.toString());
    }

    @Override
    public JapaneseDate resolveDate(Map<TemporalField, Long> map, ResolverStyle resolverStyle) {
        return (JapaneseDate)super.resolveDate(map, resolverStyle);
    }

    @Override
    ChronoLocalDate resolveYearOfEra(Map<TemporalField, Long> map, ResolverStyle resolverStyle) {
        Object object = map.get(ChronoField.ERA);
        Era era = null;
        if (object != null) {
            era = this.eraOf(this.range(ChronoField.ERA).checkValidIntValue((Long)object, ChronoField.ERA));
        }
        Long l = map.get(ChronoField.YEAR_OF_ERA);
        int n = 0;
        if (l != null) {
            n = this.range(ChronoField.YEAR_OF_ERA).checkValidIntValue(l, ChronoField.YEAR_OF_ERA);
        }
        object = era;
        if (era == null) {
            object = era;
            if (l != null) {
                object = era;
                if (!map.containsKey(ChronoField.YEAR)) {
                    object = era;
                    if (resolverStyle != ResolverStyle.STRICT) {
                        object = JapaneseEra.values()[JapaneseEra.values().length - 1];
                    }
                }
            }
        }
        if (l != null && object != null) {
            if (map.containsKey(ChronoField.MONTH_OF_YEAR) && map.containsKey(ChronoField.DAY_OF_MONTH)) {
                return this.resolveYMD((JapaneseEra)object, n, map, resolverStyle);
            }
            if (map.containsKey(ChronoField.DAY_OF_YEAR)) {
                return this.resolveYD((JapaneseEra)object, n, map, resolverStyle);
            }
        }
        return null;
    }

    @Override
    Object writeReplace() {
        return super.writeReplace();
    }

    public ChronoZonedDateTime<JapaneseDate> zonedDateTime(Instant instant, ZoneId zoneId) {
        return super.zonedDateTime(instant, zoneId);
    }

    public ChronoZonedDateTime<JapaneseDate> zonedDateTime(TemporalAccessor temporalAccessor) {
        return super.zonedDateTime(temporalAccessor);
    }

}

