/*
 * Decompiled with CFR 0.145.
 */
package java.time.chrono;

import java.io.IOException;
import java.io.InputStream;
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
import java.time.chrono.HijrahDate;
import java.time.chrono.HijrahEra;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import sun.util.calendar.BaseCalendar;
import sun.util.logging.PlatformLogger;

public final class HijrahChronology
extends AbstractChronology
implements Serializable {
    public static final HijrahChronology INSTANCE;
    private static final String KEY_ID = "id";
    private static final String KEY_ISO_START = "iso-start";
    private static final String KEY_TYPE = "type";
    private static final String KEY_VERSION = "version";
    private static final String PROP_PREFIX = "calendar.hijrah.";
    private static final String PROP_TYPE_SUFFIX = ".type";
    private static final transient Properties calendarProperties;
    private static final long serialVersionUID = 3127340209035924785L;
    private final transient String calendarType;
    private transient int[] hijrahEpochMonthStartDays;
    private transient int hijrahStartEpochMonth;
    private volatile transient boolean initComplete;
    private transient int maxEpochDay;
    private transient int maxMonthLength;
    private transient int maxYearLength;
    private transient int minEpochDay;
    private transient int minMonthLength;
    private transient int minYearLength;
    private final transient String typeId;

    static {
        try {
            calendarProperties = BaseCalendar.getCalendarProperties();
        }
        catch (IOException iOException) {
            throw new InternalError("Can't initialize lib/calendars.properties", iOException);
        }
        try {
            HijrahChronology hijrahChronology;
            INSTANCE = hijrahChronology = new HijrahChronology("Hijrah-umalqura");
            AbstractChronology.registerChrono(INSTANCE, "Hijrah");
            AbstractChronology.registerChrono(INSTANCE, "islamic");
        }
        catch (DateTimeException dateTimeException) {
            PlatformLogger.getLogger("java.time.chrono").severe("Unable to initialize Hijrah calendar: Hijrah-umalqura", dateTimeException);
            throw new RuntimeException("Unable to initialize Hijrah-umalqura calendar", dateTimeException.getCause());
        }
        HijrahChronology.registerVariants();
    }

    private HijrahChronology(String charSequence) throws DateTimeException {
        if (!((String)charSequence).isEmpty()) {
            CharSequence charSequence2 = new StringBuilder();
            charSequence2.append(PROP_PREFIX);
            charSequence2.append((String)charSequence);
            charSequence2.append(PROP_TYPE_SUFFIX);
            charSequence2 = charSequence2.toString();
            String string = calendarProperties.getProperty((String)charSequence2);
            if (string != null && !string.isEmpty()) {
                this.typeId = charSequence;
                this.calendarType = string;
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("calendarType is missing or empty for: ");
            ((StringBuilder)charSequence).append((String)charSequence2);
            throw new DateTimeException(((StringBuilder)charSequence).toString());
        }
        throw new IllegalArgumentException("calendar id is empty");
    }

    private void checkCalendarInit() {
        if (!this.initComplete) {
            this.loadCalendarData();
            this.initComplete = true;
        }
    }

    private int[] createEpochMonths(int n, int n2, int n3, Map<Integer, int[]> object) {
        int n4 = 0;
        int[] arrn = new int[(n3 - n2 + 1) * 12 + 1];
        this.minMonthLength = Integer.MAX_VALUE;
        this.maxMonthLength = Integer.MIN_VALUE;
        int n5 = n2;
        int n6 = n;
        n = n5;
        n5 = n4;
        while (n <= n3) {
            int[] arrn2 = object.get(n);
            n4 = 0;
            while (n4 < 12) {
                int n7 = arrn2[n4];
                arrn[n5] = n6;
                if (n7 >= 29 && n7 <= 32) {
                    n6 += n7;
                    this.minMonthLength = Math.min(this.minMonthLength, n7);
                    this.maxMonthLength = Math.max(this.maxMonthLength, n7);
                    ++n4;
                    ++n5;
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid month length in year: ");
                ((StringBuilder)object).append(n2);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            ++n;
        }
        n = n5 + 1;
        arrn[n5] = n6;
        if (n == arrn.length) {
            return arrn;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Did not fill epochMonths exactly: ndx = ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" should be ");
        ((StringBuilder)object).append(arrn.length);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    private int epochDayToEpochMonth(int n) {
        int n2;
        n = n2 = Arrays.binarySearch(this.hijrahEpochMonthStartDays, n);
        if (n2 < 0) {
            n = -n2 - 2;
        }
        return n;
    }

    private int epochMonthLength(int n) {
        int[] arrn = this.hijrahEpochMonthStartDays;
        return arrn[n + 1] - arrn[n];
    }

    private int epochMonthToEpochDay(int n) {
        return this.hijrahEpochMonthStartDays[n];
    }

    private int epochMonthToMonth(int n) {
        return (this.hijrahStartEpochMonth + n) % 12;
    }

    private int epochMonthToYear(int n) {
        return (this.hijrahStartEpochMonth + n) / 12;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void loadCalendarData() {
        try {
            var1_1 = HijrahChronology.calendarProperties;
            var2_5 = new StringBuilder();
            var2_5.append("calendar.hijrah.");
            var2_5.append(this.typeId);
            var3_6 = var1_1.getProperty(var2_5.toString());
            var1_1 = new StringBuilder();
            var1_1.append("Resource missing for calendar: calendar.hijrah.");
            var1_1.append(this.typeId);
            Objects.requireNonNull(var3_6, var1_1.toString());
            var4_7 = HijrahChronology.readConfigProperties(var3_6);
            var5_8 = new HashMap<Integer, int[]>();
            var6_9 = Integer.MIN_VALUE;
            var7_10 /* !! */  = null;
            var1_1 = null;
            var2_5 = null;
            var8_11 = 0;
            var9_12 = var4_7.entrySet().iterator();
            var10_13 = Integer.MAX_VALUE;
            while (var9_12.hasNext()) {
                block19 : {
                    block16 : {
                        block17 : {
                            block18 : {
                                var11_14 = var9_12.next();
                                var12_15 = (int[])var11_14.getKey();
                                var13_16 = var12_15.hashCode();
                                if (var13_16 == -1117701862) break block16;
                                if (var13_16 == 3355) break block17;
                                if (var13_16 == 3575610) break block18;
                                if (var13_16 != 351608024 || !var12_15.equals("version")) ** GOTO lbl-1000
                                var13_16 = 2;
                                break block19;
                            }
                            if (!var12_15.equals("type")) ** GOTO lbl-1000
                            var13_16 = 1;
                            break block19;
                        }
                        if (!var12_15.equals("id")) ** GOTO lbl-1000
                        var13_16 = 0;
                        break block19;
                    }
                    var14_17 = var12_15.equals("iso-start");
                    if (var14_17) {
                        var13_16 = 3;
                    } else lbl-1000: // 4 sources:
                    {
                        var13_16 = -1;
                    }
                }
                if (var13_16 != 0) {
                    if (var13_16 != 1) {
                        if (var13_16 != 2) {
                            if (var13_16 != 3) {
                                try {
                                    var13_16 = Integer.valueOf((String)var12_15);
                                    var5_8.put(var13_16, this.parseMonths((String)var11_14.getValue()));
                                    var6_9 = Math.max(var6_9, var13_16);
                                    var10_13 = Math.min(var10_13, var13_16);
                                    continue;
                                }
                                catch (NumberFormatException var1_2) {
                                    var2_5 = new StringBuilder();
                                    var2_5.append("bad key: ");
                                    var2_5.append((String)var12_15);
                                    var1_3 = new IllegalArgumentException(var2_5.toString());
                                    throw var1_3;
                                }
                            }
                            var12_15 = this.parseYMD((String)var11_14.getValue());
                            var8_11 = (int)LocalDate.of(var12_15[0], var12_15[1], var12_15[2]).toEpochDay();
                            continue;
                        }
                        var2_5 = (String)var11_14.getValue();
                        continue;
                    }
                    var1_1 = (String)var11_14.getValue();
                    continue;
                }
                var7_10 /* !! */  = (String)var11_14.getValue();
            }
            if (!this.getId().equals(var7_10 /* !! */ )) ** GOTO lbl94
            if (!this.getCalendarType().equals(var1_1)) ** GOTO lbl87
            if (var2_5 != null && !var2_5.isEmpty()) {
                if (var8_11 == 0) {
                    var1_1 = new IllegalArgumentException("Configuration does not contain a ISO start date");
                    throw var1_1;
                }
            } else {
                var1_1 = new IllegalArgumentException("Configuration does not contain a version");
                throw var1_1;
lbl87: // 1 sources:
                var7_10 /* !! */  = new StringBuilder();
                var7_10 /* !! */ .append("Configuration is for a different calendar type: ");
                var7_10 /* !! */ .append((String)var1_1);
                var2_5 = new IllegalArgumentException(var7_10 /* !! */ .toString());
                throw var2_5;
lbl94: // 1 sources:
                var1_1 = new StringBuilder();
                var1_1.append("Configuration is for a different calendar: ");
                var1_1.append((String)var7_10 /* !! */ );
                var2_5 = new IllegalArgumentException(var1_1.toString());
                throw var2_5;
            }
            this.hijrahStartEpochMonth = var10_13 * 12;
            this.minEpochDay = var8_11;
            this.hijrahEpochMonthStartDays = this.createEpochMonths(this.minEpochDay, var10_13, var6_9, var5_8);
            this.maxEpochDay = this.hijrahEpochMonthStartDays[this.hijrahEpochMonthStartDays.length - 1];
            var13_16 = var10_13;
        }
        catch (Exception var1_4) {
            var2_5 = PlatformLogger.getLogger("java.time.chrono");
            var7_10 /* !! */  = new StringBuilder();
            var7_10 /* !! */ .append("Unable to initialize Hijrah calendar proxy: ");
            var7_10 /* !! */ .append(this.typeId);
            var2_5.severe(var7_10 /* !! */ .toString(), var1_4);
            var2_5 = new StringBuilder();
            var2_5.append("Unable to initialize HijrahCalendar: ");
            var2_5.append(this.typeId);
            throw new DateTimeException(var2_5.toString(), var1_4);
        }
        while (var13_16 < var6_9) {
            var10_13 = this.getYearLength(var13_16);
            this.minYearLength = Math.min(this.minYearLength, var10_13);
            this.maxYearLength = Math.max(this.maxYearLength, var10_13);
            ++var13_16;
        }
    }

    private int[] parseMonths(String arrobject) {
        Object object = new int[12];
        if ((arrobject = arrobject.split("\\s")).length == 12) {
            for (int i = 0; i < 12; ++i) {
                try {
                    object[i] = Integer.valueOf(arrobject[i]);
                    continue;
                }
                catch (NumberFormatException numberFormatException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("bad key: ");
                    stringBuilder.append((String)arrobject[i]);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("wrong number of months on line: ");
        ((StringBuilder)object).append(Arrays.toString(arrobject));
        ((StringBuilder)object).append("; count: ");
        ((StringBuilder)object).append(arrobject.length);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private int[] parseYMD(String object) {
        object = ((String)object).trim();
        try {
            if (((String)object).charAt(4) == '-' && ((String)object).charAt(7) == '-') {
                return new int[]{Integer.valueOf(((String)object).substring(0, 4)), Integer.valueOf(((String)object).substring(5, 7)), Integer.valueOf(((String)object).substring(8, 10))};
            }
            object = new IllegalArgumentException("date must be yyyy-MM-dd");
            throw object;
        }
        catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException("date must be yyyy-MM-dd", numberFormatException);
        }
    }

    private static Properties readConfigProperties(String string) throws Exception {
        Properties properties;
        block7 : {
            properties = new Properties();
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(string);
            try {
                properties.load(inputStream);
                if (inputStream == null) break block7;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    }
                    throw throwable2;
                }
            }
            inputStream.close();
        }
        return properties;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private static void registerVariants() {
        for (String string : calendarProperties.stringPropertyNames()) {
            if (!string.startsWith(PROP_PREFIX) || (string = string.substring(PROP_PREFIX.length())).indexOf(46) >= 0 || string.equals(INSTANCE.getId())) continue;
            try {
                HijrahChronology hijrahChronology = new HijrahChronology(string);
                AbstractChronology.registerChrono(hijrahChronology);
            }
            catch (DateTimeException dateTimeException) {
                PlatformLogger platformLogger = PlatformLogger.getLogger("java.time.chrono");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to initialize Hijrah calendar: ");
                stringBuilder.append(string);
                platformLogger.severe(stringBuilder.toString(), dateTimeException);
            }
        }
    }

    private int yearMonthToDayOfYear(int n, int n2) {
        n = this.yearToEpochMonth(n);
        return this.epochMonthToEpochDay(n + n2) - this.epochMonthToEpochDay(n);
    }

    private int yearToEpochMonth(int n) {
        return n * 12 - this.hijrahStartEpochMonth;
    }

    void checkValidDayOfYear(int n) {
        if (n >= 1 && n <= this.getMaximumDayOfYear()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Hijrah day of year: ");
        stringBuilder.append(n);
        throw new DateTimeException(stringBuilder.toString());
    }

    void checkValidMonth(int n) {
        if (n >= 1 && n <= 12) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Hijrah month: ");
        stringBuilder.append(n);
        throw new DateTimeException(stringBuilder.toString());
    }

    int checkValidYear(long l) {
        if (l >= (long)this.getMinimumYear() && l <= (long)this.getMaximumYear()) {
            return (int)l;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Hijrah year: ");
        stringBuilder.append(l);
        throw new DateTimeException(stringBuilder.toString());
    }

    @Override
    public HijrahDate date(int n, int n2, int n3) {
        return HijrahDate.of(this, n, n2, n3);
    }

    @Override
    public HijrahDate date(Era era, int n, int n2, int n3) {
        return this.date(this.prolepticYear(era, n), n2, n3);
    }

    @Override
    public HijrahDate date(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof HijrahDate) {
            return (HijrahDate)temporalAccessor;
        }
        return HijrahDate.ofEpochDay(this, temporalAccessor.getLong(ChronoField.EPOCH_DAY));
    }

    @Override
    public HijrahDate dateEpochDay(long l) {
        return HijrahDate.ofEpochDay(this, l);
    }

    @Override
    public HijrahDate dateNow() {
        return this.dateNow(Clock.systemDefaultZone());
    }

    @Override
    public HijrahDate dateNow(Clock clock) {
        return this.date(LocalDate.now(clock));
    }

    @Override
    public HijrahDate dateNow(ZoneId zoneId) {
        return this.dateNow(Clock.system(zoneId));
    }

    @Override
    public HijrahDate dateYearDay(int n, int n2) {
        Serializable serializable = HijrahDate.of(this, n, 1, 1);
        if (n2 <= ((HijrahDate)serializable).lengthOfYear()) {
            return ((HijrahDate)serializable).plusDays(n2 - 1);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Invalid dayOfYear: ");
        ((StringBuilder)serializable).append(n2);
        throw new DateTimeException(((StringBuilder)serializable).toString());
    }

    @Override
    public HijrahDate dateYearDay(Era era, int n, int n2) {
        return this.dateYearDay(this.prolepticYear(era, n), n2);
    }

    @Override
    public HijrahEra eraOf(int n) {
        if (n == 1) {
            return HijrahEra.AH;
        }
        throw new DateTimeException("invalid Hijrah era");
    }

    @Override
    public List<Era> eras() {
        return Arrays.asList(HijrahEra.values());
    }

    @Override
    public String getCalendarType() {
        return this.calendarType;
    }

    int getDayOfYear(int n, int n2) {
        return this.yearMonthToDayOfYear(n, n2 - 1);
    }

    long getEpochDay(int n, int n2, int n3) {
        this.checkCalendarInit();
        this.checkValidMonth(n2);
        int n4 = this.yearToEpochMonth(n) + (n2 - 1);
        if (n4 >= 0 && n4 < this.hijrahEpochMonthStartDays.length) {
            if (n3 >= 1 && n3 <= this.getMonthLength(n, n2)) {
                return this.epochMonthToEpochDay(n4) + (n3 - 1);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid Hijrah day of month: ");
            stringBuilder.append(n3);
            throw new DateTimeException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Hijrah date, year: ");
        stringBuilder.append(n);
        stringBuilder.append(", month: ");
        stringBuilder.append(n2);
        throw new DateTimeException(stringBuilder.toString());
    }

    int[] getHijrahDateInfo(int n) {
        this.checkCalendarInit();
        if (n >= this.minEpochDay && n < this.maxEpochDay) {
            int n2 = this.epochDayToEpochMonth(n);
            return new int[]{this.epochMonthToYear(n2), this.epochMonthToMonth(n2) + 1, n - this.epochMonthToEpochDay(n2) + 1};
        }
        throw new DateTimeException("Hijrah date out of range");
    }

    @Override
    public String getId() {
        return this.typeId;
    }

    int getMaximumDayOfYear() {
        return this.maxYearLength;
    }

    int getMaximumMonthLength() {
        return this.maxMonthLength;
    }

    int getMaximumYear() {
        return this.epochMonthToYear(this.hijrahEpochMonthStartDays.length - 1) - 1;
    }

    int getMinimumMonthLength() {
        return this.minMonthLength;
    }

    int getMinimumYear() {
        return this.epochMonthToYear(0);
    }

    int getMonthLength(int n, int n2) {
        int n3 = this.yearToEpochMonth(n) + (n2 - 1);
        if (n3 >= 0 && n3 < this.hijrahEpochMonthStartDays.length) {
            return this.epochMonthLength(n3);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Hijrah date, year: ");
        stringBuilder.append(n);
        stringBuilder.append(", month: ");
        stringBuilder.append(n2);
        throw new DateTimeException(stringBuilder.toString());
    }

    int getSmallestMaximumDayOfYear() {
        return this.minYearLength;
    }

    int getYearLength(int n) {
        return this.yearMonthToDayOfYear(n, 12);
    }

    @Override
    public boolean isLeapYear(long l) {
        this.checkCalendarInit();
        long l2 = this.getMinimumYear();
        boolean bl = false;
        if (l >= l2 && l <= (long)this.getMaximumYear()) {
            if (this.getYearLength((int)l) > 354) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public ChronoLocalDateTime<HijrahDate> localDateTime(TemporalAccessor temporalAccessor) {
        return super.localDateTime(temporalAccessor);
    }

    @Override
    public int prolepticYear(Era era, int n) {
        if (era instanceof HijrahEra) {
            return n;
        }
        throw new ClassCastException("Era must be HijrahEra");
    }

    @Override
    public ValueRange range(ChronoField chronoField) {
        this.checkCalendarInit();
        if (chronoField instanceof ChronoField) {
            switch (chronoField) {
                default: {
                    return chronoField.range();
                }
                case ERA: {
                    return ValueRange.of(1L, 1L);
                }
                case YEAR: 
                case YEAR_OF_ERA: {
                    return ValueRange.of(this.getMinimumYear(), this.getMaximumYear());
                }
                case ALIGNED_WEEK_OF_MONTH: {
                    return ValueRange.of(1L, 5L);
                }
                case DAY_OF_YEAR: {
                    return ValueRange.of(1L, this.getMaximumDayOfYear());
                }
                case DAY_OF_MONTH: 
            }
            return ValueRange.of(1L, 1L, this.getMinimumMonthLength(), this.getMaximumMonthLength());
        }
        return chronoField.range();
    }

    @Override
    public HijrahDate resolveDate(Map<TemporalField, Long> map, ResolverStyle resolverStyle) {
        return (HijrahDate)super.resolveDate(map, resolverStyle);
    }

    @Override
    Object writeReplace() {
        return super.writeReplace();
    }

    public ChronoZonedDateTime<HijrahDate> zonedDateTime(Instant instant, ZoneId zoneId) {
        return super.zonedDateTime(instant, zoneId);
    }

    public ChronoZonedDateTime<HijrahDate> zonedDateTime(TemporalAccessor temporalAccessor) {
        return super.zonedDateTime(temporalAccessor);
    }

}

