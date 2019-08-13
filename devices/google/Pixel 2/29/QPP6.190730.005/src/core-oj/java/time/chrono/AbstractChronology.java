/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.time.chrono.-$
 *  java.time.chrono.-$$Lambda
 *  java.time.chrono.-$$Lambda$AbstractChronology
 *  java.time.chrono.-$$Lambda$AbstractChronology$5b0W7uLeaWkn0HLPDKwPXzJ7HPo
 *  java.time.chrono.-$$Lambda$AbstractChronology$j22w8kHhJoqCd56hhLQK1G0VLFw
 *  java.time.chrono.-$$Lambda$AbstractChronology$onW9aZyLFliH5Gg1qLodD_GoPfA
 */
package java.time.chrono;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.chrono.-$;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.Era;
import java.time.chrono.HijrahChronology;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseChronology;
import java.time.chrono.MinguoChronology;
import java.time.chrono.Ser;
import java.time.chrono.ThaiBuddhistChronology;
import java.time.chrono._$$Lambda$AbstractChronology$5b0W7uLeaWkn0HLPDKwPXzJ7HPo;
import java.time.chrono._$$Lambda$AbstractChronology$j22w8kHhJoqCd56hhLQK1G0VLFw;
import java.time.chrono._$$Lambda$AbstractChronology$onW9aZyLFliH5Gg1qLodD_GoPfA;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import sun.util.logging.PlatformLogger;

public abstract class AbstractChronology
implements Chronology {
    private static final ConcurrentHashMap<String, Chronology> CHRONOS_BY_ID;
    private static final ConcurrentHashMap<String, Chronology> CHRONOS_BY_TYPE;
    static final Comparator<ChronoLocalDate> DATE_ORDER;
    static final Comparator<ChronoLocalDateTime<? extends ChronoLocalDate>> DATE_TIME_ORDER;
    static final Comparator<ChronoZonedDateTime<?>> INSTANT_ORDER;

    static {
        DATE_ORDER = (Comparator)((Object)((Serializable)_$$Lambda$AbstractChronology$j22w8kHhJoqCd56hhLQK1G0VLFw.INSTANCE));
        DATE_TIME_ORDER = (Comparator)((Object)((Serializable)_$$Lambda$AbstractChronology$onW9aZyLFliH5Gg1qLodD_GoPfA.INSTANCE));
        INSTANT_ORDER = (Comparator)((Object)((Serializable)_$$Lambda$AbstractChronology$5b0W7uLeaWkn0HLPDKwPXzJ7HPo.INSTANCE));
        CHRONOS_BY_ID = new ConcurrentHashMap();
        CHRONOS_BY_TYPE = new ConcurrentHashMap();
    }

    protected AbstractChronology() {
    }

    static Set<Chronology> getAvailableChronologies() {
        AbstractChronology.initCache();
        HashSet<Chronology> hashSet = new HashSet<Chronology>(CHRONOS_BY_ID.values());
        Iterator<Chronology> iterator = ServiceLoader.load(Chronology.class).iterator();
        while (iterator.hasNext()) {
            hashSet.add(iterator.next());
        }
        return hashSet;
    }

    private static boolean initCache() {
        if (CHRONOS_BY_ID.get("ISO") == null) {
            AbstractChronology.registerChrono(HijrahChronology.INSTANCE);
            AbstractChronology.registerChrono(JapaneseChronology.INSTANCE);
            AbstractChronology.registerChrono(MinguoChronology.INSTANCE);
            AbstractChronology.registerChrono(ThaiBuddhistChronology.INSTANCE);
            for (AbstractChronology abstractChronology : ServiceLoader.load(AbstractChronology.class, null)) {
                String string = abstractChronology.getId();
                if (!string.equals("ISO") && AbstractChronology.registerChrono(abstractChronology) == null) continue;
                PlatformLogger platformLogger = PlatformLogger.getLogger("java.time.chrono");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Ignoring duplicate Chronology, from ServiceLoader configuration ");
                stringBuilder.append(string);
                platformLogger.warning(stringBuilder.toString());
            }
            AbstractChronology.registerChrono(IsoChronology.INSTANCE);
            return true;
        }
        return false;
    }

    static /* synthetic */ int lambda$static$2241c452$1(ChronoZonedDateTime chronoZonedDateTime, ChronoZonedDateTime chronoZonedDateTime2) {
        int n;
        int n2 = n = Long.compare(chronoZonedDateTime.toEpochSecond(), chronoZonedDateTime2.toEpochSecond());
        if (n == 0) {
            n2 = Long.compare(chronoZonedDateTime.toLocalTime().getNano(), chronoZonedDateTime2.toLocalTime().getNano());
        }
        return n2;
    }

    static /* synthetic */ int lambda$static$7f2d2d5b$1(ChronoLocalDate chronoLocalDate, ChronoLocalDate chronoLocalDate2) {
        return Long.compare(chronoLocalDate.toEpochDay(), chronoLocalDate2.toEpochDay());
    }

    static /* synthetic */ int lambda$static$b5a61975$1(ChronoLocalDateTime chronoLocalDateTime, ChronoLocalDateTime chronoLocalDateTime2) {
        int n;
        int n2 = n = Long.compare(chronoLocalDateTime.toLocalDate().toEpochDay(), chronoLocalDateTime2.toLocalDate().toEpochDay());
        if (n == 0) {
            n2 = Long.compare(chronoLocalDateTime.toLocalTime().toNanoOfDay(), chronoLocalDateTime2.toLocalTime().toNanoOfDay());
        }
        return n2;
    }

    static Chronology of(String string) {
        Objects.requireNonNull(string, "id");
        do {
            Chronology object2;
            if ((object2 = AbstractChronology.of0(string)) == null) continue;
            return object2;
        } while (AbstractChronology.initCache());
        for (Chronology chronology : ServiceLoader.load(Chronology.class)) {
            if (!string.equals(chronology.getId()) && !string.equals(chronology.getCalendarType())) continue;
            return chronology;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown chronology: ");
        stringBuilder.append(string);
        throw new DateTimeException(stringBuilder.toString());
    }

    private static Chronology of0(String string) {
        Chronology chronology;
        Chronology chronology2 = chronology = CHRONOS_BY_ID.get(string);
        if (chronology == null) {
            chronology2 = CHRONOS_BY_TYPE.get(string);
        }
        return chronology2;
    }

    static Chronology ofLocale(Locale object) {
        Objects.requireNonNull(object, "locale");
        object = ((Locale)object).getUnicodeLocaleType("ca");
        if (object != null && !"iso".equals(object) && !"iso8601".equals(object)) {
            Object object2;
            do {
                if ((object2 = CHRONOS_BY_TYPE.get(object)) == null) continue;
                return object2;
            } while (AbstractChronology.initCache());
            for (Chronology chronology : ServiceLoader.load(Chronology.class)) {
                if (!((String)object).equals(chronology.getCalendarType())) continue;
                return chronology;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unknown calendar system: ");
            ((StringBuilder)object2).append((String)object);
            throw new DateTimeException(((StringBuilder)object2).toString());
        }
        return IsoChronology.INSTANCE;
    }

    static Chronology readExternal(DataInput dataInput) throws IOException {
        return Chronology.of(dataInput.readUTF());
    }

    private void readObject(ObjectInputStream objectInputStream) throws ObjectStreamException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    static Chronology registerChrono(Chronology chronology) {
        return AbstractChronology.registerChrono(chronology, chronology.getId());
    }

    static Chronology registerChrono(Chronology chronology, String object) {
        String string;
        if ((object = CHRONOS_BY_ID.putIfAbsent((String)object, chronology)) == null && (string = chronology.getCalendarType()) != null) {
            CHRONOS_BY_TYPE.putIfAbsent(string, chronology);
        }
        return object;
    }

    void addFieldValue(Map<TemporalField, Long> object, ChronoField chronoField, long l) {
        Long l2 = object.get(chronoField);
        if (l2 != null && l2 != l) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Conflict found: ");
            ((StringBuilder)object).append(chronoField);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(l2);
            ((StringBuilder)object).append(" differs from ");
            ((StringBuilder)object).append(chronoField);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append(l);
            throw new DateTimeException(((StringBuilder)object).toString());
        }
        object.put((TemporalField)chronoField, l);
    }

    @Override
    public int compareTo(Chronology chronology) {
        return this.getId().compareTo(chronology.getId());
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof AbstractChronology) {
            if (this.compareTo((AbstractChronology)object) != 0) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode() ^ this.getId().hashCode();
    }

    ChronoLocalDate resolveAligned(ChronoLocalDate chronoLocalDate, long l, long l2, long l3) {
        ChronoLocalDate chronoLocalDate2 = chronoLocalDate.plus(l, ChronoUnit.MONTHS).plus(l2, ChronoUnit.WEEKS);
        if (l3 > 7L) {
            chronoLocalDate = chronoLocalDate2.plus((l3 - 1L) / 7L, ChronoUnit.WEEKS);
            l = (l3 - 1L) % 7L + 1L;
        } else {
            chronoLocalDate = chronoLocalDate2;
            l = l3;
            if (l3 < 1L) {
                chronoLocalDate = chronoLocalDate2.plus(Math.subtractExact(l3, 7L) / 7L, ChronoUnit.WEEKS);
                l = (6L + l3) % 7L + 1L;
            }
        }
        return chronoLocalDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.of((int)l)));
    }

    @Override
    public ChronoLocalDate resolveDate(Map<TemporalField, Long> map, ResolverStyle resolverStyle) {
        if (map.containsKey(ChronoField.EPOCH_DAY)) {
            return this.dateEpochDay(map.remove(ChronoField.EPOCH_DAY));
        }
        this.resolveProlepticMonth(map, resolverStyle);
        ChronoLocalDate chronoLocalDate = this.resolveYearOfEra(map, resolverStyle);
        if (chronoLocalDate != null) {
            return chronoLocalDate;
        }
        if (map.containsKey(ChronoField.YEAR)) {
            if (map.containsKey(ChronoField.MONTH_OF_YEAR)) {
                if (map.containsKey(ChronoField.DAY_OF_MONTH)) {
                    return this.resolveYMD(map, resolverStyle);
                }
                if (map.containsKey(ChronoField.ALIGNED_WEEK_OF_MONTH)) {
                    if (map.containsKey(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH)) {
                        return this.resolveYMAA(map, resolverStyle);
                    }
                    if (map.containsKey(ChronoField.DAY_OF_WEEK)) {
                        return this.resolveYMAD(map, resolverStyle);
                    }
                }
            }
            if (map.containsKey(ChronoField.DAY_OF_YEAR)) {
                return this.resolveYD(map, resolverStyle);
            }
            if (map.containsKey(ChronoField.ALIGNED_WEEK_OF_YEAR)) {
                if (map.containsKey(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR)) {
                    return this.resolveYAA(map, resolverStyle);
                }
                if (map.containsKey(ChronoField.DAY_OF_WEEK)) {
                    return this.resolveYAD(map, resolverStyle);
                }
            }
        }
        return null;
    }

    void resolveProlepticMonth(Map<TemporalField, Long> map, ResolverStyle object) {
        Long l = map.remove(ChronoField.PROLEPTIC_MONTH);
        if (l != null) {
            if (object != ResolverStyle.LENIENT) {
                ChronoField.PROLEPTIC_MONTH.checkValidValue(l);
            }
            object = this.dateNow().with(ChronoField.DAY_OF_MONTH, 1L).with(ChronoField.PROLEPTIC_MONTH, l);
            this.addFieldValue(map, ChronoField.MONTH_OF_YEAR, object.get(ChronoField.MONTH_OF_YEAR));
            this.addFieldValue(map, ChronoField.YEAR, object.get(ChronoField.YEAR));
        }
    }

    ChronoLocalDate resolveYAA(Map<TemporalField, Long> object, ResolverStyle resolverStyle) {
        int n = this.range(ChronoField.YEAR).checkValidIntValue(object.remove(ChronoField.YEAR), ChronoField.YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long l = Math.subtractExact(object.remove(ChronoField.ALIGNED_WEEK_OF_YEAR), 1L);
            long l2 = Math.subtractExact(object.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR), 1L);
            return this.dateYearDay(n, 1).plus(l, ChronoUnit.WEEKS).plus(l2, ChronoUnit.DAYS);
        }
        int n2 = this.range(ChronoField.ALIGNED_WEEK_OF_YEAR).checkValidIntValue(object.remove(ChronoField.ALIGNED_WEEK_OF_YEAR), ChronoField.ALIGNED_WEEK_OF_YEAR);
        int n3 = this.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR).checkValidIntValue(object.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR), ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR);
        object = this.dateYearDay(n, 1).plus((n2 - 1) * 7 + (n3 - 1), ChronoUnit.DAYS);
        if (resolverStyle == ResolverStyle.STRICT && object.get(ChronoField.YEAR) != n) {
            throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
        }
        return object;
    }

    ChronoLocalDate resolveYAD(Map<TemporalField, Long> object, ResolverStyle resolverStyle) {
        int n = this.range(ChronoField.YEAR).checkValidIntValue(object.remove(ChronoField.YEAR), ChronoField.YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long l = Math.subtractExact(object.remove(ChronoField.ALIGNED_WEEK_OF_YEAR), 1L);
            long l2 = Math.subtractExact(object.remove(ChronoField.DAY_OF_WEEK), 1L);
            return this.resolveAligned(this.dateYearDay(n, 1), 0L, l, l2);
        }
        int n2 = this.range(ChronoField.ALIGNED_WEEK_OF_YEAR).checkValidIntValue(object.remove(ChronoField.ALIGNED_WEEK_OF_YEAR), ChronoField.ALIGNED_WEEK_OF_YEAR);
        int n3 = this.range(ChronoField.DAY_OF_WEEK).checkValidIntValue(object.remove(ChronoField.DAY_OF_WEEK), ChronoField.DAY_OF_WEEK);
        object = this.dateYearDay(n, 1).plus((n2 - 1) * 7, ChronoUnit.DAYS).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(n3)));
        if (resolverStyle == ResolverStyle.STRICT && object.get(ChronoField.YEAR) != n) {
            throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
        }
        return object;
    }

    ChronoLocalDate resolveYD(Map<TemporalField, Long> map, ResolverStyle resolverStyle) {
        int n = this.range(ChronoField.YEAR).checkValidIntValue(map.remove(ChronoField.YEAR), ChronoField.YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long l = Math.subtractExact(map.remove(ChronoField.DAY_OF_YEAR), 1L);
            return this.dateYearDay(n, 1).plus(l, ChronoUnit.DAYS);
        }
        return this.dateYearDay(n, this.range(ChronoField.DAY_OF_YEAR).checkValidIntValue(map.remove(ChronoField.DAY_OF_YEAR), ChronoField.DAY_OF_YEAR));
    }

    ChronoLocalDate resolveYMAA(Map<TemporalField, Long> object, ResolverStyle resolverStyle) {
        int n = this.range(ChronoField.YEAR).checkValidIntValue(object.remove(ChronoField.YEAR), ChronoField.YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long l = Math.subtractExact(object.remove(ChronoField.MONTH_OF_YEAR), 1L);
            long l2 = Math.subtractExact(object.remove(ChronoField.ALIGNED_WEEK_OF_MONTH), 1L);
            long l3 = Math.subtractExact(object.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH), 1L);
            return this.date(n, 1, 1).plus(l, ChronoUnit.MONTHS).plus(l2, ChronoUnit.WEEKS).plus(l3, ChronoUnit.DAYS);
        }
        int n2 = this.range(ChronoField.MONTH_OF_YEAR).checkValidIntValue(object.remove(ChronoField.MONTH_OF_YEAR), ChronoField.MONTH_OF_YEAR);
        int n3 = this.range(ChronoField.ALIGNED_WEEK_OF_MONTH).checkValidIntValue(object.remove(ChronoField.ALIGNED_WEEK_OF_MONTH), ChronoField.ALIGNED_WEEK_OF_MONTH);
        int n4 = this.range(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH).checkValidIntValue(object.remove(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH), ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH);
        object = this.date(n, n2, 1).plus((n3 - 1) * 7 + (n4 - 1), ChronoUnit.DAYS);
        if (resolverStyle == ResolverStyle.STRICT && object.get(ChronoField.MONTH_OF_YEAR) != n2) {
            throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
        }
        return object;
    }

    ChronoLocalDate resolveYMAD(Map<TemporalField, Long> object, ResolverStyle resolverStyle) {
        int n = this.range(ChronoField.YEAR).checkValidIntValue(object.remove(ChronoField.YEAR), ChronoField.YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long l = Math.subtractExact(object.remove(ChronoField.MONTH_OF_YEAR), 1L);
            long l2 = Math.subtractExact(object.remove(ChronoField.ALIGNED_WEEK_OF_MONTH), 1L);
            long l3 = Math.subtractExact(object.remove(ChronoField.DAY_OF_WEEK), 1L);
            return this.resolveAligned(this.date(n, 1, 1), l, l2, l3);
        }
        int n2 = this.range(ChronoField.MONTH_OF_YEAR).checkValidIntValue(object.remove(ChronoField.MONTH_OF_YEAR), ChronoField.MONTH_OF_YEAR);
        int n3 = this.range(ChronoField.ALIGNED_WEEK_OF_MONTH).checkValidIntValue(object.remove(ChronoField.ALIGNED_WEEK_OF_MONTH), ChronoField.ALIGNED_WEEK_OF_MONTH);
        int n4 = this.range(ChronoField.DAY_OF_WEEK).checkValidIntValue(object.remove(ChronoField.DAY_OF_WEEK), ChronoField.DAY_OF_WEEK);
        object = this.date(n, n2, 1).plus((n3 - 1) * 7, ChronoUnit.DAYS).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(n4)));
        if (resolverStyle == ResolverStyle.STRICT && object.get(ChronoField.MONTH_OF_YEAR) != n2) {
            throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
        }
        return object;
    }

    ChronoLocalDate resolveYMD(Map<TemporalField, Long> object, ResolverStyle resolverStyle) {
        int n = this.range(ChronoField.YEAR).checkValidIntValue(object.remove(ChronoField.YEAR), ChronoField.YEAR);
        if (resolverStyle == ResolverStyle.LENIENT) {
            long l = Math.subtractExact(object.remove(ChronoField.MONTH_OF_YEAR), 1L);
            long l2 = Math.subtractExact(object.remove(ChronoField.DAY_OF_MONTH), 1L);
            return this.date(n, 1, 1).plus(l, ChronoUnit.MONTHS).plus(l2, ChronoUnit.DAYS);
        }
        int n2 = this.range(ChronoField.MONTH_OF_YEAR).checkValidIntValue(object.remove(ChronoField.MONTH_OF_YEAR), ChronoField.MONTH_OF_YEAR);
        int n3 = this.range(ChronoField.DAY_OF_MONTH).checkValidIntValue(object.remove(ChronoField.DAY_OF_MONTH), ChronoField.DAY_OF_MONTH);
        if (resolverStyle == ResolverStyle.SMART) {
            try {
                object = this.date(n, n2, n3);
                return object;
            }
            catch (DateTimeException dateTimeException) {
                return this.date(n, n2, 1).with(TemporalAdjusters.lastDayOfMonth());
            }
        }
        return this.date(n, n2, n3);
    }

    ChronoLocalDate resolveYearOfEra(Map<TemporalField, Long> map, ResolverStyle object) {
        block9 : {
            block8 : {
                Long l = map.remove(ChronoField.YEAR_OF_ERA);
                if (l == null) break block8;
                Long l2 = map.remove(ChronoField.ERA);
                int n = object != ResolverStyle.LENIENT ? this.range(ChronoField.YEAR_OF_ERA).checkValidIntValue(l, ChronoField.YEAR_OF_ERA) : Math.toIntExact(l);
                if (l2 != null) {
                    object = this.eraOf(this.range(ChronoField.ERA).checkValidIntValue(l2, ChronoField.ERA));
                    this.addFieldValue(map, ChronoField.YEAR, this.prolepticYear((Era)object, n));
                } else if (map.containsKey(ChronoField.YEAR)) {
                    object = this.dateYearDay(this.range(ChronoField.YEAR).checkValidIntValue(map.get(ChronoField.YEAR), ChronoField.YEAR), 1);
                    this.addFieldValue(map, ChronoField.YEAR, this.prolepticYear(object.getEra(), n));
                } else if (object == ResolverStyle.STRICT) {
                    map.put(ChronoField.YEAR_OF_ERA, l);
                } else {
                    object = this.eras();
                    if (object.isEmpty()) {
                        this.addFieldValue(map, ChronoField.YEAR, n);
                    } else {
                        object = (Era)object.get(object.size() - 1);
                        this.addFieldValue(map, ChronoField.YEAR, this.prolepticYear((Era)object, n));
                    }
                }
                break block9;
            }
            if (!map.containsKey(ChronoField.ERA)) break block9;
            this.range(ChronoField.ERA).checkValidValue(map.get(ChronoField.ERA), ChronoField.ERA);
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getId();
    }

    void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.getId());
    }

    Object writeReplace() {
        return new Ser(1, this);
    }
}

