/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Era;
import java.time.chrono.IsoChronology;
import java.time.chrono.ThaiBuddhistDate;
import java.time.chrono.ThaiBuddhistEra;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ThaiBuddhistChronology
extends AbstractChronology
implements Serializable {
    private static final HashMap<String, String[]> ERA_FULL_NAMES;
    private static final HashMap<String, String[]> ERA_NARROW_NAMES;
    private static final HashMap<String, String[]> ERA_SHORT_NAMES;
    private static final String FALLBACK_LANGUAGE = "en";
    public static final ThaiBuddhistChronology INSTANCE;
    private static final String TARGET_LANGUAGE = "th";
    static final int YEARS_DIFFERENCE = 543;
    private static final long serialVersionUID = 2775954514031616474L;

    static {
        INSTANCE = new ThaiBuddhistChronology();
        ERA_NARROW_NAMES = new HashMap();
        ERA_SHORT_NAMES = new HashMap();
        ERA_FULL_NAMES = new HashMap();
        ERA_NARROW_NAMES.put(FALLBACK_LANGUAGE, new String[]{"BB", "BE"});
        ERA_NARROW_NAMES.put(TARGET_LANGUAGE, new String[]{"BB", "BE"});
        ERA_SHORT_NAMES.put(FALLBACK_LANGUAGE, new String[]{"B.B.", "B.E."});
        ERA_SHORT_NAMES.put(TARGET_LANGUAGE, new String[]{"\u0e1e.\u0e28.", "\u0e1b\u0e35\u0e01\u0e48\u0e2d\u0e19\u0e04\u0e23\u0e34\u0e2a\u0e15\u0e4c\u0e01\u0e32\u0e25\u0e17\u0e35\u0e48"});
        ERA_FULL_NAMES.put(FALLBACK_LANGUAGE, new String[]{"Before Buddhist", "Budhhist Era"});
        ERA_FULL_NAMES.put(TARGET_LANGUAGE, new String[]{"\u0e1e\u0e38\u0e17\u0e18\u0e28\u0e31\u0e01\u0e23\u0e32\u0e0a", "\u0e1b\u0e35\u0e01\u0e48\u0e2d\u0e19\u0e04\u0e23\u0e34\u0e2a\u0e15\u0e4c\u0e01\u0e32\u0e25\u0e17\u0e35\u0e48"});
    }

    private ThaiBuddhistChronology() {
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    @Override
    public ThaiBuddhistDate date(int n, int n2, int n3) {
        return new ThaiBuddhistDate(LocalDate.of(n - 543, n2, n3));
    }

    @Override
    public ThaiBuddhistDate date(Era era, int n, int n2, int n3) {
        return this.date(this.prolepticYear(era, n), n2, n3);
    }

    @Override
    public ThaiBuddhistDate date(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof ThaiBuddhistDate) {
            return (ThaiBuddhistDate)temporalAccessor;
        }
        return new ThaiBuddhistDate(LocalDate.from(temporalAccessor));
    }

    @Override
    public ThaiBuddhistDate dateEpochDay(long l) {
        return new ThaiBuddhistDate(LocalDate.ofEpochDay(l));
    }

    @Override
    public ThaiBuddhistDate dateNow() {
        return this.dateNow(Clock.systemDefaultZone());
    }

    @Override
    public ThaiBuddhistDate dateNow(Clock clock) {
        return this.date(LocalDate.now(clock));
    }

    @Override
    public ThaiBuddhistDate dateNow(ZoneId zoneId) {
        return this.dateNow(Clock.system(zoneId));
    }

    @Override
    public ThaiBuddhistDate dateYearDay(int n, int n2) {
        return new ThaiBuddhistDate(LocalDate.ofYearDay(n - 543, n2));
    }

    @Override
    public ThaiBuddhistDate dateYearDay(Era era, int n, int n2) {
        return this.dateYearDay(this.prolepticYear(era, n), n2);
    }

    @Override
    public ThaiBuddhistEra eraOf(int n) {
        return ThaiBuddhistEra.of(n);
    }

    @Override
    public List<Era> eras() {
        return Arrays.asList(ThaiBuddhistEra.values());
    }

    @Override
    public String getCalendarType() {
        return "buddhist";
    }

    @Override
    public String getId() {
        return "ThaiBuddhist";
    }

    @Override
    public boolean isLeapYear(long l) {
        return IsoChronology.INSTANCE.isLeapYear(l - 543L);
    }

    public ChronoLocalDateTime<ThaiBuddhistDate> localDateTime(TemporalAccessor temporalAccessor) {
        return super.localDateTime(temporalAccessor);
    }

    @Override
    public int prolepticYear(Era era, int n) {
        if (era instanceof ThaiBuddhistEra) {
            if (era != ThaiBuddhistEra.BE) {
                n = 1 - n;
            }
            return n;
        }
        throw new ClassCastException("Era must be BuddhistEra");
    }

    @Override
    public ValueRange range(ChronoField object) {
        int n = 1.$SwitchMap$java$time$temporal$ChronoField[((Enum)object).ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    return ((ChronoField)object).range();
                }
                object = ChronoField.YEAR.range();
                return ValueRange.of(((ValueRange)object).getMinimum() + 543L, ((ValueRange)object).getMaximum() + 543L);
            }
            object = ChronoField.YEAR.range();
            return ValueRange.of(1L, -(((ValueRange)object).getMinimum() + 543L) + 1L, ((ValueRange)object).getMaximum() + 543L);
        }
        object = ChronoField.PROLEPTIC_MONTH.range();
        return ValueRange.of(((ValueRange)object).getMinimum() + 6516L, ((ValueRange)object).getMaximum() + 6516L);
    }

    @Override
    public ThaiBuddhistDate resolveDate(Map<TemporalField, Long> map, ResolverStyle resolverStyle) {
        return (ThaiBuddhistDate)super.resolveDate(map, resolverStyle);
    }

    @Override
    Object writeReplace() {
        return super.writeReplace();
    }

    public ChronoZonedDateTime<ThaiBuddhistDate> zonedDateTime(Instant instant, ZoneId zoneId) {
        return super.zonedDateTime(instant, zoneId);
    }

    public ChronoZonedDateTime<ThaiBuddhistDate> zonedDateTime(TemporalAccessor temporalAccessor) {
        return super.zonedDateTime(temporalAccessor);
    }

}

