/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.CalType;
import android.icu.impl.CalendarUtil;
import android.icu.impl.ICUCache;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SimpleCache;
import android.icu.impl.SimpleFormatterImpl;
import android.icu.impl.SoftCache;
import android.icu.text.DateFormat;
import android.icu.text.DateFormatSymbols;
import android.icu.text.SimpleDateFormat;
import android.icu.util.BasicTimeZone;
import android.icu.util.BuddhistCalendar;
import android.icu.util.ChineseCalendar;
import android.icu.util.CopticCalendar;
import android.icu.util.DangiCalendar;
import android.icu.util.EthiopicCalendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.HebrewCalendar;
import android.icu.util.ICUCloneNotSupportedException;
import android.icu.util.IndianCalendar;
import android.icu.util.IslamicCalendar;
import android.icu.util.JapaneseCalendar;
import android.icu.util.PersianCalendar;
import android.icu.util.TaiwanCalendar;
import android.icu.util.TimeZone;
import android.icu.util.TimeZoneTransition;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;

public abstract class Calendar
implements Serializable,
Cloneable,
Comparable<Calendar> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int AM = 0;
    public static final int AM_PM = 9;
    public static final int APRIL = 3;
    public static final int AUGUST = 7;
    @Deprecated
    protected static final int BASE_FIELD_COUNT = 23;
    public static final int DATE = 5;
    static final int[][][] DATE_PRECEDENCE;
    public static final int DAY_OF_MONTH = 5;
    public static final int DAY_OF_WEEK = 7;
    public static final int DAY_OF_WEEK_IN_MONTH = 8;
    public static final int DAY_OF_YEAR = 6;
    public static final int DECEMBER = 11;
    private static final String[] DEFAULT_PATTERNS;
    public static final int DOW_LOCAL = 18;
    static final int[][][] DOW_PRECEDENCE;
    public static final int DST_OFFSET = 16;
    protected static final int EPOCH_JULIAN_DAY = 2440588;
    public static final int ERA = 0;
    public static final int EXTENDED_YEAR = 19;
    public static final int FEBRUARY = 1;
    private static final int FIELD_DIFF_MAX_INT = Integer.MAX_VALUE;
    private static final String[] FIELD_NAME;
    private static final int[] FIND_ZONE_TRANSITION_TIME_UNITS;
    public static final int FRIDAY = 6;
    protected static final int GREATEST_MINIMUM = 1;
    private static final int[][] GREGORIAN_MONTH_COUNT;
    public static final int HOUR = 10;
    public static final int HOUR_OF_DAY = 11;
    protected static final int INTERNALLY_SET = 1;
    public static final int IS_LEAP_MONTH = 22;
    public static final int JANUARY = 0;
    protected static final int JAN_1_1_JULIAN_DAY = 1721426;
    public static final int JULIAN_DAY = 20;
    public static final int JULY = 6;
    public static final int JUNE = 5;
    protected static final int LEAST_MAXIMUM = 2;
    private static final int[][] LIMITS;
    public static final int MARCH = 2;
    protected static final int MAXIMUM = 3;
    protected static final Date MAX_DATE;
    @Deprecated
    protected static final int MAX_FIELD_COUNT = 32;
    private static final int MAX_HOURS = 548;
    protected static final int MAX_JULIAN = 2130706432;
    protected static final long MAX_MILLIS = 183882168921600000L;
    public static final int MAY = 4;
    public static final int MILLISECOND = 14;
    public static final int MILLISECONDS_IN_DAY = 21;
    protected static final int MINIMUM = 0;
    protected static final int MINIMUM_USER_STAMP = 2;
    public static final int MINUTE = 12;
    protected static final Date MIN_DATE;
    protected static final int MIN_JULIAN = -2130706432;
    protected static final long MIN_MILLIS = -184303902528000000L;
    public static final int MONDAY = 2;
    public static final int MONTH = 2;
    public static final int NOVEMBER = 10;
    public static final int OCTOBER = 9;
    protected static final long ONE_DAY = 86400000L;
    protected static final int ONE_HOUR = 3600000;
    protected static final int ONE_MINUTE = 60000;
    protected static final int ONE_SECOND = 1000;
    protected static final long ONE_WEEK = 604800000L;
    private static final ICUCache<String, PatternData> PATTERN_CACHE;
    public static final int PM = 1;
    private static final char QUOTE = '\'';
    protected static final int RESOLVE_REMAP = 32;
    public static final int SATURDAY = 7;
    public static final int SECOND = 13;
    public static final int SEPTEMBER = 8;
    private static int STAMP_MAX = 0;
    public static final int SUNDAY = 1;
    public static final int THURSDAY = 5;
    public static final int TUESDAY = 3;
    public static final int UNDECIMBER = 12;
    protected static final int UNSET = 0;
    public static final int WALLTIME_FIRST = 1;
    public static final int WALLTIME_LAST = 0;
    public static final int WALLTIME_NEXT_VALID = 2;
    public static final int WEDNESDAY = 4;
    @Deprecated
    public static final int WEEKDAY = 0;
    @Deprecated
    public static final int WEEKEND = 1;
    @Deprecated
    public static final int WEEKEND_CEASE = 3;
    @Deprecated
    public static final int WEEKEND_ONSET = 2;
    private static final WeekDataCache WEEK_DATA_CACHE;
    public static final int WEEK_OF_MONTH = 4;
    public static final int WEEK_OF_YEAR = 3;
    public static final int YEAR = 1;
    public static final int YEAR_WOY = 17;
    public static final int ZONE_OFFSET = 15;
    private static final long serialVersionUID = 6222646104888790989L;
    private ULocale actualLocale;
    private transient boolean areAllFieldsSet;
    private transient boolean areFieldsSet;
    private transient boolean areFieldsVirtuallySet;
    private transient int[] fields;
    private int firstDayOfWeek;
    private transient int gregorianDayOfMonth;
    private transient int gregorianDayOfYear;
    private transient int gregorianMonth;
    private transient int gregorianYear;
    private transient int internalSetMask;
    private transient boolean isTimeSet;
    private boolean lenient = true;
    private int minimalDaysInFirstWeek;
    private transient int nextStamp = 2;
    private int repeatedWallTime = 0;
    private int skippedWallTime = 0;
    private transient int[] stamp;
    private long time;
    private ULocale validLocale;
    private int weekendCease;
    private int weekendCeaseMillis;
    private int weekendOnset;
    private int weekendOnsetMillis;
    private TimeZone zone;

    static {
        MIN_DATE = new Date(-184303902528000000L);
        MAX_DATE = new Date(183882168921600000L);
        STAMP_MAX = 10000;
        PATTERN_CACHE = new SimpleCache<String, PatternData>();
        DEFAULT_PATTERNS = new String[]{"HH:mm:ss z", "HH:mm:ss z", "HH:mm:ss", "HH:mm", "EEEE, yyyy MMMM dd", "yyyy MMMM d", "yyyy MMM d", "yy/MM/dd", "{1} {0}", "{1} {0}", "{1} {0}", "{1} {0}", "{1} {0}"};
        int[] arrn = new int[]{};
        int[] arrn2 = new int[]{};
        int[] arrn3 = new int[]{};
        int[] arrn4 = new int[]{};
        int[] arrn5 = new int[]{0, 0, 1, 1};
        int[] arrn6 = new int[]{0, 0, 11, 11};
        int[] arrn7 = new int[]{0, 0, 59, 59};
        int[] arrn8 = new int[]{0, 0, 59, 59};
        int[] arrn9 = new int[]{-43200000, -43200000, 43200000, 43200000};
        int[] arrn10 = new int[]{};
        int[] arrn11 = new int[]{-2130706432, -2130706432, 2130706432, 2130706432};
        int[] arrn12 = new int[]{0, 0, 86399999, 86399999};
        LIMITS = new int[][]{new int[0], arrn, new int[0], arrn2, arrn3, new int[0], arrn4, {1, 1, 7, 7}, new int[0], arrn5, arrn6, {0, 0, 23, 23}, arrn7, arrn8, {0, 0, 999, 999}, arrn9, {0, 0, 3600000, 3600000}, new int[0], {1, 1, 7, 7}, arrn10, arrn11, arrn12, {0, 0, 1, 1}};
        WEEK_DATA_CACHE = new WeekDataCache();
        arrn = new int[]{3, 7};
        arrn2 = new int[]{3, 18};
        arrn2 = new int[][]{{5}, arrn, {4, 7}, {8, 7}, arrn2, {4, 18}, {8, 18}, {6}, {37, 1}, {35, 17}};
        arrn = new int[]{8};
        DATE_PRECEDENCE = new int[][][]{arrn2, {{3}, {4}, arrn, {40, 7}, {40, 18}}};
        DOW_PRECEDENCE = new int[][][]{{{7}, {18}}};
        FIND_ZONE_TRANSITION_TIME_UNITS = new int[]{3600000, 1800000, 60000, 1000};
        arrn = new int[]{30, 30, 90, 91};
        arrn2 = new int[]{31, 31, 120, 121};
        arrn3 = new int[]{31, 31, 212, 213};
        arrn4 = new int[]{30, 30, 243, 244};
        arrn5 = new int[]{31, 31, 273, 274};
        arrn6 = new int[]{30, 30, 304, 305};
        arrn7 = new int[]{31, 31, 334, 335};
        GREGORIAN_MONTH_COUNT = new int[][]{{31, 31, 0, 0}, {28, 29, 31, 31}, {31, 31, 59, 60}, arrn, arrn2, {30, 30, 151, 152}, {31, 31, 181, 182}, arrn3, arrn4, arrn5, arrn6, arrn7};
        FIELD_NAME = new String[]{"ERA", "YEAR", "MONTH", "WEEK_OF_YEAR", "WEEK_OF_MONTH", "DAY_OF_MONTH", "DAY_OF_YEAR", "DAY_OF_WEEK", "DAY_OF_WEEK_IN_MONTH", "AM_PM", "HOUR", "HOUR_OF_DAY", "MINUTE", "SECOND", "MILLISECOND", "ZONE_OFFSET", "DST_OFFSET", "YEAR_WOY", "DOW_LOCAL", "EXTENDED_YEAR", "JULIAN_DAY", "MILLISECONDS_IN_DAY"};
    }

    protected Calendar() {
        this(TimeZone.getDefault(), ULocale.getDefault(ULocale.Category.FORMAT));
    }

    protected Calendar(TimeZone timeZone, ULocale uLocale) {
        this.zone = timeZone;
        this.setWeekData(Calendar.getRegionForCalendar(uLocale));
        this.setCalendarLocale(uLocale);
        this.initInternal();
    }

    protected Calendar(TimeZone timeZone, Locale locale) {
        this(timeZone, ULocale.forLocale(locale));
    }

    private long compare(Object object) {
        block4 : {
            long l;
            block3 : {
                block2 : {
                    if (!(object instanceof Calendar)) break block2;
                    l = ((Calendar)object).getTimeInMillis();
                    break block3;
                }
                if (!(object instanceof Date)) break block4;
                l = ((Date)object).getTime();
            }
            return this.getTimeInMillis() - l;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(object);
        stringBuilder.append("is not a Calendar or Date");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private final void computeGregorianAndDOWFields(int n) {
        int n2;
        this.computeGregorianFields(n);
        int[] arrn = this.fields;
        arrn[7] = n = Calendar.julianDayToDayOfWeek(n);
        n = n2 = n - this.getFirstDayOfWeek() + 1;
        if (n2 < 1) {
            n = n2 + 7;
        }
        this.fields[18] = n;
    }

    private final void computeWeekFields() {
        int n;
        int[] arrn = this.fields;
        int n2 = arrn[19];
        int n3 = arrn[7];
        int n4 = arrn[6];
        int n5 = n2;
        int n6 = (n3 + 7 - this.getFirstDayOfWeek()) % 7;
        int n7 = (n3 - n4 + 7001 - this.getFirstDayOfWeek()) % 7;
        int n8 = n = (n4 - 1 + n7) / 7;
        if (7 - n7 >= this.getMinimalDaysInFirstWeek()) {
            n8 = n + 1;
        }
        if (n8 == 0) {
            n = this.weekNumber(this.handleGetYearLength(n2 - 1) + n4, n3);
            n2 = n5 - 1;
        } else {
            int n9 = this.handleGetYearLength(n2);
            n2 = n5;
            n = n8;
            if (n4 >= n9 - 5) {
                n7 = n2 = (n6 + n9 - n4) % 7;
                if (n2 < 0) {
                    n7 = n2 + 7;
                }
                n2 = n5;
                n = n8;
                if (6 - n7 >= this.getMinimalDaysInFirstWeek()) {
                    n2 = n5;
                    n = n8;
                    if (n4 + 7 - n6 > n9) {
                        n = 1;
                        n2 = n5 + 1;
                    }
                }
            }
        }
        arrn = this.fields;
        arrn[3] = n;
        arrn[17] = n2;
        n8 = arrn[5];
        arrn[4] = this.weekNumber(n8, n3);
        this.fields[8] = (n8 - 1) / 7 + 1;
    }

    private static Calendar createInstance(ULocale comparable) {
        CalType calType;
        TimeZone timeZone = TimeZone.getDefault();
        CalType calType2 = calType = Calendar.getCalendarTypeForLocale((ULocale)comparable);
        if (calType == CalType.UNKNOWN) {
            calType2 = CalType.GREGORIAN;
        }
        switch (calType2) {
            default: {
                throw new IllegalArgumentException("Unknown calendar type");
            }
            case ROC: {
                comparable = new TaiwanCalendar(timeZone, (ULocale)comparable);
                break;
            }
            case PERSIAN: {
                comparable = new PersianCalendar(timeZone, (ULocale)comparable);
                break;
            }
            case JAPANESE: {
                comparable = new JapaneseCalendar(timeZone, (ULocale)comparable);
                break;
            }
            case ISLAMIC_CIVIL: 
            case ISLAMIC_UMALQURA: 
            case ISLAMIC_TBLA: 
            case ISLAMIC_RGSA: 
            case ISLAMIC: {
                comparable = new IslamicCalendar(timeZone, (ULocale)comparable);
                break;
            }
            case INDIAN: {
                comparable = new IndianCalendar(timeZone, (ULocale)comparable);
                break;
            }
            case HEBREW: {
                comparable = new HebrewCalendar(timeZone, (ULocale)comparable);
                break;
            }
            case ETHIOPIC_AMETE_ALEM: {
                comparable = new EthiopicCalendar(timeZone, (ULocale)comparable);
                ((EthiopicCalendar)comparable).setAmeteAlemEra(true);
                break;
            }
            case ETHIOPIC: {
                comparable = new EthiopicCalendar(timeZone, (ULocale)comparable);
                break;
            }
            case DANGI: {
                comparable = new DangiCalendar(timeZone, (ULocale)comparable);
                break;
            }
            case COPTIC: {
                comparable = new CopticCalendar(timeZone, (ULocale)comparable);
                break;
            }
            case CHINESE: {
                comparable = new ChineseCalendar(timeZone, (ULocale)comparable);
                break;
            }
            case BUDDHIST: {
                comparable = new BuddhistCalendar(timeZone, (ULocale)comparable);
                break;
            }
            case ISO8601: {
                comparable = new GregorianCalendar(timeZone, (ULocale)comparable);
                ((Calendar)comparable).setFirstDayOfWeek(2);
                ((Calendar)comparable).setMinimalDaysInFirstWeek(4);
                break;
            }
            case GREGORIAN: {
                comparable = new GregorianCalendar(timeZone, (ULocale)comparable);
            }
        }
        return comparable;
    }

    private static String expandOverride(String object, String string) {
        char c;
        if (string.indexOf(61) >= 0) {
            return string;
        }
        char c2 = '\u0000';
        char c3 = ' ';
        StringBuilder stringBuilder = new StringBuilder();
        object = new StringCharacterIterator((String)object);
        char c4 = c = ((StringCharacterIterator)object).first();
        while (c4 != '\uffff') {
            if (c4 == '\'') {
                c3 = c2 == '\u0000' ? (char)'\u0001' : '\u0000';
                c2 = c3;
            } else if (c2 == '\u0000' && c4 != c3) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(";");
                }
                stringBuilder.append(c4);
                stringBuilder.append("=");
                stringBuilder.append(string);
            }
            c3 = c4;
            c4 = c = ((StringCharacterIterator)object).next();
        }
        return stringBuilder.toString();
    }

    private static Long findPreviousZoneTransitionTime(TimeZone timeZone, int n, long l, long l2) {
        long l3;
        long l4;
        int n2;
        block9 : {
            l4 = 0L;
            int[] arrn = FIND_ZONE_TRANSITION_TIME_UNITS;
            int n3 = arrn.length;
            for (n2 = 0; n2 < n3; ++n2) {
                int n4 = arrn[n2];
                l3 = l / (long)n4;
                long l5 = l2 / (long)n4;
                if (l3 <= l5) continue;
                l4 = (l5 + l3 + 1L >>> 1) * (long)n4;
                n2 = 1;
                break block9;
            }
            n2 = 0;
        }
        if (n2 == 0) {
            l4 = l + l2 >>> 1;
        }
        if (n2 != 0) {
            if (l4 != l) {
                if (timeZone.getOffset(l4) != n) {
                    return Calendar.findPreviousZoneTransitionTime(timeZone, n, l, l4);
                }
                l = l4;
            }
            l3 = l4 - 1L;
            l4 = l;
            l = l3;
        } else {
            l4 = l;
            l = l + l2 >>> 1;
        }
        if (l == l2) {
            return l4;
        }
        if (timeZone.getOffset(l) != n) {
            if (n2 != 0) {
                return l4;
            }
            return Calendar.findPreviousZoneTransitionTime(timeZone, n, l4, l);
        }
        return Calendar.findPreviousZoneTransitionTime(timeZone, n, l, l2);
    }

    private static int firstIslamicStartYearFromGrego(int n) {
        int n2 = 0;
        int n3 = 0;
        if (n >= 1977) {
            n2 = (n - 1977) / 65;
            if ((n - 1977) % 65 >= 32) {
                n3 = 1;
            }
            n3 = n2 * 2 + n3;
        } else {
            int n4 = (n - 1976) / 65;
            n3 = n2;
            if (-(n - 1976) % 65 <= 32) {
                n3 = 1;
            }
            n3 = (n4 - 1) * 2 + n3;
        }
        return n - 579 + n3;
    }

    protected static final int floorDivide(int n, int n2) {
        n = n >= 0 ? (n /= n2) : (n + 1) / n2 - 1;
        return n;
    }

    protected static final int floorDivide(int n, int n2, int[] arrn) {
        if (n >= 0) {
            arrn[0] = n % n2;
            return n / n2;
        }
        int n3 = (n + 1) / n2 - 1;
        arrn[0] = n - n3 * n2;
        return n3;
    }

    protected static final int floorDivide(long l, int n, int[] arrn) {
        if (l >= 0L) {
            arrn[0] = (int)(l % (long)n);
            return (int)(l / (long)n);
        }
        int n2 = (int)((l + 1L) / (long)n - 1L);
        arrn[0] = (int)(l - (long)n2 * (long)n);
        return n2;
    }

    protected static final long floorDivide(long l, long l2) {
        l = l >= 0L ? (l /= l2) : (l + 1L) / l2 - 1L;
        return l;
    }

    private static DateFormat formatHelper(Calendar serializable, ULocale serializable2, int n, int n2) {
        block7 : {
            block8 : {
                block12 : {
                    String string;
                    String string2;
                    block10 : {
                        String string3;
                        PatternData patternData;
                        block11 : {
                            String string4;
                            block9 : {
                                String string5;
                                if (n2 < -1 || n2 > 3) break block7;
                                if (n < -1 || n > 3) break block8;
                                patternData = PatternData.make((Calendar)serializable, (ULocale)serializable2);
                                string2 = null;
                                if (n2 < 0 || n < 0) break block9;
                                string = string5 = SimpleFormatterImpl.formatRawPattern(patternData.getDateTimePattern(n), 2, 2, patternData.patterns[n2], patternData.patterns[n + 4]);
                                if (patternData.overrides != null) {
                                    string2 = patternData.overrides[n + 4];
                                    string = patternData.overrides[n2];
                                    string2 = Calendar.mergeOverrideStrings(patternData.patterns[n + 4], patternData.patterns[n2], string2, string);
                                    string = string5;
                                }
                                break block10;
                            }
                            if (n2 < 0) break block11;
                            string = string4 = patternData.patterns[n2];
                            if (patternData.overrides != null) {
                                string2 = patternData.overrides[n2];
                                string = string4;
                            }
                            break block10;
                        }
                        if (n < 0) break block12;
                        string = string3 = patternData.patterns[n + 4];
                        if (patternData.overrides != null) {
                            string2 = patternData.overrides[n + 4];
                            string = string3;
                        }
                    }
                    serializable2 = ((Calendar)serializable).handleGetDateFormat(string, string2, (ULocale)serializable2);
                    ((DateFormat)serializable2).setCalendar((Calendar)serializable);
                    return serializable2;
                }
                throw new IllegalArgumentException("No date or time style specified");
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Illegal date style ");
            ((StringBuilder)serializable).append(n);
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Illegal time style ");
        ((StringBuilder)serializable).append(n2);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    private int getActualHelper(int n, int n2, int n3) {
        block4 : {
            int n4;
            if (n2 == n3) {
                return n2;
            }
            boolean bl = true;
            int n5 = n3 > n2 ? 1 : -1;
            Calendar calendar = (Calendar)this.clone();
            calendar.complete();
            calendar.setLenient(true);
            if (n5 >= 0) {
                bl = false;
            }
            calendar.prepareGetActual(n, bl);
            calendar.set(n, n2);
            if (calendar.get(n) != n2 && n != 4 && n5 > 0) {
                return n2;
            }
            int n6 = n2;
            int n7 = n2;
            n2 = n6;
            do {
                n4 = n7 + n5;
                calendar.add(n, n5);
                if (calendar.get(n) != n4) break block4;
                n2 = n6 = n4;
                n7 = n4;
            } while (n4 != n3);
            n2 = n6;
        }
        return n2;
    }

    public static Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales();
    }

    private static CalType getCalendarTypeForLocale(ULocale object2) {
        String string = CalendarUtil.getCalendarType((ULocale)object2);
        if (string != null) {
            String string2 = string.toLowerCase(Locale.ENGLISH);
            for (CalType calType : CalType.values()) {
                if (!string2.equals(calType.getId())) continue;
                return calType;
            }
        }
        return CalType.UNKNOWN;
    }

    public static String getDateTimeFormatString(ULocale object, String string, int n, int n2) {
        block2 : {
            block3 : {
                block7 : {
                    block5 : {
                        block6 : {
                            block4 : {
                                if (n2 < -1 || n2 > 3) break block2;
                                if (n < -1 || n > 3) break block3;
                                object = PatternData.make((ULocale)object, string);
                                if (n2 < 0 || n < 0) break block4;
                                object = SimpleFormatterImpl.formatRawPattern(((PatternData)object).getDateTimePattern(n), 2, 2, ((PatternData)object).patterns[n2], ((PatternData)object).patterns[n + 4]);
                                break block5;
                            }
                            if (n2 < 0) break block6;
                            object = ((PatternData)object).patterns[n2];
                            break block5;
                        }
                        if (n < 0) break block7;
                        object = ((PatternData)object).patterns[n + 4];
                    }
                    return object;
                }
                throw new IllegalArgumentException("No date or time style specified");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal date style ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Illegal time style ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Deprecated
    public static String getDateTimePattern(Calendar calendar, ULocale uLocale, int n) {
        return PatternData.make(calendar, uLocale).getDateTimePattern(n);
    }

    private Long getImmediatePreviousZoneTransition(long l) {
        Object object = null;
        Object object2 = this.zone;
        if (object2 instanceof BasicTimeZone) {
            if ((object2 = ((BasicTimeZone)object2).getPreviousTransition(l, true)) != null) {
                object = ((TimeZoneTransition)object2).getTime();
            }
        } else {
            object = object2 = Calendar.getPreviousZoneTransitionTime((TimeZone)object2, l, 7200000L);
            if (object2 == null) {
                object = Calendar.getPreviousZoneTransitionTime(this.zone, l, 108000000L);
            }
        }
        return object;
    }

    public static Calendar getInstance() {
        return Calendar.getInstanceInternal(null, null);
    }

    public static Calendar getInstance(TimeZone timeZone) {
        return Calendar.getInstanceInternal(timeZone, null);
    }

    public static Calendar getInstance(TimeZone timeZone, ULocale uLocale) {
        return Calendar.getInstanceInternal(timeZone, uLocale);
    }

    public static Calendar getInstance(TimeZone timeZone, Locale locale) {
        return Calendar.getInstanceInternal(timeZone, ULocale.forLocale(locale));
    }

    public static Calendar getInstance(ULocale uLocale) {
        return Calendar.getInstanceInternal(null, uLocale);
    }

    public static Calendar getInstance(Locale locale) {
        return Calendar.getInstanceInternal(null, ULocale.forLocale(locale));
    }

    private static Calendar getInstanceInternal(TimeZone cloneable, ULocale serializable) {
        ULocale uLocale = serializable;
        if (serializable == null) {
            uLocale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        serializable = cloneable;
        if (cloneable == null) {
            serializable = TimeZone.getDefault();
        }
        cloneable = Calendar.createInstance(uLocale);
        ((Calendar)cloneable).setTimeZone((TimeZone)serializable);
        ((Calendar)cloneable).setTimeInMillis(System.currentTimeMillis());
        return cloneable;
    }

    /*
     * WARNING - void declaration
     */
    public static final String[] getKeywordValuesForLocale(String arrobject, ULocale serializable, boolean bl) {
        void var0_5;
        void var2_9;
        ArrayList<String> arrayList;
        String missingResourceException = ULocale.getRegionForSupplementalData((ULocale)((Object)arrayList), true);
        arrayList = new ArrayList<String>();
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("calendarPreferenceData");
        try {
            UResourceBundle uResourceBundle2 = uResourceBundle.get(missingResourceException);
        }
        catch (MissingResourceException missingResourceException2) {
            UResourceBundle uResourceBundle3 = uResourceBundle.get("001");
        }
        String[] arrstring = var0_5.getStringArray();
        if (var2_9 != false) {
            return arrstring;
        }
        for (int i = 0; i < arrstring.length; ++i) {
            arrayList.add(arrstring[i]);
        }
        for (CalType calType : CalType.values()) {
            if (arrayList.contains(calType.getId())) continue;
            arrayList.add(calType.getId());
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    private static PatternData getPatternData(ULocale object, String arrstring) {
        String[] arrstring2 = (String[])UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (ULocale)object);
        object = new StringBuilder();
        ((StringBuilder)object).append("calendar/");
        ((StringBuilder)object).append((String)arrstring);
        ((StringBuilder)object).append("/DateTimePatterns");
        arrstring = arrstring2.findWithFallback(((StringBuilder)object).toString());
        object = arrstring;
        if (arrstring == null) {
            object = arrstring2.getWithFallback("calendar/gregorian/DateTimePatterns");
        }
        int n = ((UResourceBundle)object).getSize();
        arrstring = new String[n];
        arrstring2 = new String[n];
        for (int i = 0; i < n; ++i) {
            ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)((UResourceBundle)object).get(i);
            int n2 = iCUResourceBundle.getType();
            if (n2 != 0) {
                if (n2 != 8) continue;
                arrstring[i] = iCUResourceBundle.getString(0);
                arrstring2[i] = iCUResourceBundle.getString(1);
                continue;
            }
            arrstring[i] = iCUResourceBundle.getString();
        }
        return new PatternData(arrstring, arrstring2);
    }

    private static Long getPreviousZoneTransitionTime(TimeZone timeZone, long l, long l2) {
        l2 = l - l2 - 1L;
        int n = timeZone.getOffset(l);
        if (n == timeZone.getOffset(l2)) {
            return null;
        }
        return Calendar.findPreviousZoneTransitionTime(timeZone, n, l, l2);
    }

    private static String getRegionForCalendar(ULocale object) {
        String string = ULocale.getRegionForSupplementalData((ULocale)object, true);
        object = string;
        if (string.length() == 0) {
            object = "001";
        }
        return object;
    }

    public static WeekData getWeekDataForRegion(String string) {
        return WEEK_DATA_CACHE.createInstance(string, string);
    }

    private static WeekData getWeekDataForRegionInternal(String object) {
        MissingResourceException missingResourceException2;
        block3 : {
            String string = object;
            if (object == null) {
                string = "001";
            }
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("weekData");
            try {
                object = uResourceBundle.get(string);
            }
            catch (MissingResourceException missingResourceException2) {
                if (string.equals("001")) break block3;
                object = uResourceBundle.get("001");
            }
            object = object.getIntVector();
            return new WeekData(object[0], object[1], object[2], object[3], object[4], object[5]);
        }
        throw missingResourceException2;
    }

    private static int gregoYearFromIslamicStart(int n) {
        int n2 = 0;
        int n3 = 0;
        if (n >= 1397) {
            n2 = (n - 1397) / 67;
            if ((n - 1397) % 67 >= 33) {
                n3 = 1;
            }
            n3 = n2 * 2 + n3;
        } else {
            int n4 = (n - 1396) / 67;
            n3 = n2;
            if (-(n - 1396) % 67 <= 33) {
                n3 = 1;
            }
            n3 = (n4 - 1) * 2 + n3;
        }
        return n + 579 - n3;
    }

    protected static final int gregorianMonthLength(int n, int n2) {
        return GREGORIAN_MONTH_COUNT[n2][Calendar.isGregorianLeapYear(n)];
    }

    protected static final int gregorianPreviousMonthLength(int n, int n2) {
        n = n2 > 0 ? Calendar.gregorianMonthLength(n, n2 - 1) : 31;
        return n;
    }

    private void initInternal() {
        this.fields = this.handleCreateFields();
        int[] arrn = this.fields;
        if (arrn != null && arrn.length >= 23 && arrn.length <= 32) {
            this.stamp = new int[arrn.length];
            int n = 4718695;
            for (int i = 23; i < this.fields.length; ++i) {
                n |= 1 << i;
            }
            this.internalSetMask = n;
            return;
        }
        throw new IllegalStateException("Invalid fields[]");
    }

    protected static final boolean isGregorianLeapYear(int n) {
        boolean bl = n % 4 == 0 && (n % 100 != 0 || n % 400 == 0);
        return bl;
    }

    protected static final int julianDayToDayOfWeek(int n) {
        int n2;
        n = n2 = (n + 2) % 7;
        if (n2 < 1) {
            n = n2 + 7;
        }
        return n;
    }

    protected static final long julianDayToMillis(int n) {
        return (long)(n - 2440588) * 86400000L;
    }

    private static String mergeOverrideStrings(String string, String string2, String string3, String string4) {
        if (string3 == null && string4 == null) {
            return null;
        }
        if (string3 == null) {
            return Calendar.expandOverride(string2, string4);
        }
        if (string4 == null) {
            return Calendar.expandOverride(string, string3);
        }
        if (string3.equals(string4)) {
            return string3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Calendar.expandOverride(string, string3));
        stringBuilder.append(";");
        stringBuilder.append(Calendar.expandOverride(string2, string4));
        return stringBuilder.toString();
    }

    protected static final int millisToJulianDay(long l) {
        return (int)(Calendar.floorDivide(l, 86400000L) + 2440588L);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.initInternal();
        this.isTimeSet = true;
        this.areAllFieldsSet = false;
        this.areFieldsSet = false;
        this.areFieldsVirtuallySet = true;
        this.nextStamp = 2;
    }

    private void recalculateStamp() {
        this.nextStamp = 1;
        for (int i = 0; i < this.stamp.length; ++i) {
            int n;
            int[] arrn;
            int n2 = STAMP_MAX;
            int n3 = -1;
            for (n = 0; n < (arrn = this.stamp).length; ++n) {
                int n4 = n2;
                int n5 = n3;
                if (arrn[n] > this.nextStamp) {
                    n4 = n2;
                    n5 = n3;
                    if (arrn[n] < n2) {
                        n4 = arrn[n];
                        n5 = n;
                    }
                }
                n2 = n4;
                n3 = n5;
            }
            if (n3 < 0) break;
            this.nextStamp = n = this.nextStamp + 1;
            arrn[n3] = n;
        }
        ++this.nextStamp;
    }

    private void setCalendarLocale(ULocale object) {
        Serializable serializable = object;
        if (((ULocale)object).getVariant().length() != 0 || ((ULocale)object).getKeywords() != null) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(((ULocale)object).getLanguage());
            String string = ((ULocale)object).getScript();
            if (string.length() > 0) {
                ((StringBuilder)serializable).append("_");
                ((StringBuilder)serializable).append(string);
            }
            if ((string = ((ULocale)object).getCountry()).length() > 0) {
                ((StringBuilder)serializable).append("_");
                ((StringBuilder)serializable).append(string);
            }
            if ((object = ((ULocale)object).getKeywordValue("calendar")) != null) {
                ((StringBuilder)serializable).append("@calendar=");
                ((StringBuilder)serializable).append((String)object);
            }
            serializable = new ULocale(((StringBuilder)serializable).toString());
        }
        this.setLocale((ULocale)serializable, (ULocale)serializable);
    }

    private void setWeekData(String string) {
        String string2 = string;
        if (string == null) {
            string2 = "001";
        }
        this.setWeekData((WeekData)WEEK_DATA_CACHE.getInstance(string2, string2));
    }

    private void updateTime() {
        this.computeTime();
        if (this.isLenient() || !this.areAllFieldsSet) {
            this.areFieldsSet = false;
        }
        this.isTimeSet = true;
        this.areFieldsVirtuallySet = false;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (!this.isTimeSet) {
            try {
                this.updateTime();
            }
            catch (IllegalArgumentException illegalArgumentException) {}
        }
        objectOutputStream.defaultWriteObject();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void add(int var1_1, int var2_2) {
        var3_3 = var2_2;
        if (var3_3 == 0) {
            return;
        }
        var4_4 = var3_3;
        var6_5 = 1;
        var2_2 = var3_3;
        switch (var1_1) {
            default: {
                var7_6 = new StringBuilder();
                var7_6.append("Calendar.add(");
                var7_6.append(this.fieldName(var1_1));
                var7_6.append(") not supported");
                throw new IllegalArgumentException(var7_6.toString());
            }
            case 14: 
            case 21: {
                var1_1 = 0;
                ** GOTO lbl43
            }
            case 13: {
                var4_4 *= 1000L;
                var1_1 = 0;
                ** GOTO lbl43
            }
            case 12: {
                var4_4 *= 60000L;
                var1_1 = 0;
                ** GOTO lbl43
            }
            case 10: 
            case 11: {
                var4_4 *= 3600000L;
                var1_1 = 0;
                ** GOTO lbl43
            }
            case 9: {
                var4_4 *= 43200000L;
                var1_1 = var6_5;
                ** GOTO lbl43
            }
            case 5: 
            case 6: 
            case 7: 
            case 18: 
            case 20: {
                var4_4 *= 86400000L;
                var1_1 = var6_5;
                ** GOTO lbl43
            }
            case 3: 
            case 4: 
            case 8: {
                var4_4 *= 604800000L;
                var1_1 = var6_5;
lbl43: // 7 sources:
                var3_3 = 0;
                var2_2 = 0;
                if (var1_1 != 0) {
                    var3_3 = this.get(16) + this.get(15);
                    var2_2 = this.get(21);
                }
                this.setTimeInMillis(this.getTimeInMillis() + var4_4);
                if (var1_1 == 0) return;
                var1_1 = this.get(21);
                if (var1_1 == var2_2) return;
                var4_4 = this.internalGetTimeInMillis();
                var6_5 = this.get(16) + this.get(15);
                if (var6_5 == var3_3) return;
                var8_9 = (long)(var3_3 - var6_5) % 86400000L;
                if (var8_9 != 0L) {
                    this.setTimeInMillis(var4_4 + var8_9);
                    var1_1 = this.get(21);
                }
                if (var1_1 == var2_2) return;
                var1_1 = this.skippedWallTime;
                if (var1_1 == 0) {
                    if (var8_9 >= 0L) return;
                    this.setTimeInMillis(var4_4);
                    return;
                }
                if (var1_1 == 1) {
                    if (var8_9 <= 0L) return;
                    this.setTimeInMillis(var4_4);
                    return;
                }
                if (var1_1 != 2) {
                    return;
                }
                if (var8_9 > 0L) {
                    var4_4 = this.internalGetTimeInMillis();
                }
                if ((var7_7 = this.getImmediatePreviousZoneTransition(var4_4)) != null) {
                    this.setTimeInMillis(var7_7.longValue());
                    return;
                }
                var7_7 = new StringBuilder();
                var7_7.append("Could not locate a time zone transition before ");
                var7_7.append(var4_4);
                throw new RuntimeException(var7_7.toString());
            }
            case 1: 
            case 17: {
                var2_2 = var3_3;
                if (this.get(0) != 0) ** GOTO lbl90
                var7_8 = this.getType();
                if (var7_8.equals("gregorian") || var7_8.equals("roc")) ** GOTO lbl89
                var2_2 = var3_3;
                if (!var7_8.equals("coptic")) ** GOTO lbl90
lbl89: // 2 sources:
                var2_2 = -var3_3;
            }
lbl90: // 4 sources:
            case 2: 
            case 19: {
                var10_10 = this.isLenient();
                this.setLenient(true);
                this.set(var1_1, this.get(var1_1) + var2_2);
                this.pinField(5);
                if (var10_10 != false) return;
                this.complete();
                this.setLenient(var10_10);
                return;
            }
            case 0: 
        }
        this.set(var1_1, this.get(var1_1) + var3_3);
        this.pinField(0);
    }

    public boolean after(Object object) {
        boolean bl = this.compare(object) > 0L;
        return bl;
    }

    public boolean before(Object object) {
        boolean bl = this.compare(object) < 0L;
        return bl;
    }

    public final void clear() {
        int[] arrn;
        for (int i = 0; i < (arrn = this.fields).length; ++i) {
            this.stamp[i] = 0;
            arrn[i] = 0;
        }
        this.areFieldsVirtuallySet = false;
        this.areAllFieldsSet = false;
        this.areFieldsSet = false;
        this.isTimeSet = false;
    }

    public final void clear(int n) {
        if (this.areFieldsVirtuallySet) {
            this.computeFields();
        }
        this.fields[n] = 0;
        this.stamp[n] = 0;
        this.areFieldsVirtuallySet = false;
        this.areAllFieldsSet = false;
        this.areFieldsSet = false;
        this.isTimeSet = false;
    }

    public Object clone() {
        try {
            Calendar calendar = (Calendar)super.clone();
            calendar.fields = new int[this.fields.length];
            calendar.stamp = new int[this.fields.length];
            System.arraycopy(this.fields, 0, calendar.fields, 0, this.fields.length);
            System.arraycopy(this.stamp, 0, calendar.stamp, 0, this.fields.length);
            calendar.zone = (TimeZone)this.zone.clone();
            return calendar;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    @Override
    public int compareTo(Calendar calendar) {
        long l = this.getTimeInMillis() - calendar.getTimeInMillis();
        int n = l < 0L ? -1 : (l > 0L ? 1 : 0);
        return n;
    }

    protected void complete() {
        if (!this.isTimeSet) {
            this.updateTime();
        }
        if (!this.areFieldsSet) {
            this.computeFields();
            this.areFieldsSet = true;
            this.areAllFieldsSet = true;
        }
    }

    protected void computeFields() {
        int n;
        int[] arrn = new int[2];
        this.getTimeZone().getOffset(this.time, false, arrn);
        long l = this.time + (long)arrn[0] + (long)arrn[1];
        int n2 = this.internalSetMask;
        for (n = 0; n < this.fields.length; ++n) {
            this.stamp[n] = (n2 & 1) == 0 ? 1 : 0;
            n2 >>= 1;
        }
        long l2 = Calendar.floorDivide(l, 86400000L);
        int[] arrn2 = this.fields;
        arrn2[20] = (int)l2 + 2440588;
        this.computeGregorianAndDOWFields(arrn2[20]);
        this.handleComputeFields(this.fields[20]);
        this.computeWeekFields();
        n = (int)(l - 86400000L * l2);
        arrn2 = this.fields;
        arrn2[21] = n;
        arrn2[14] = n % 1000;
        arrn2[13] = (n /= 1000) % 60;
        arrn2[12] = (n /= 60) % 60;
        arrn2[11] = n /= 60;
        arrn2[9] = n / 12;
        arrn2[10] = n % 12;
        arrn2[15] = arrn[0];
        arrn2[16] = arrn[1];
    }

    protected final void computeGregorianFields(int n) {
        int[] arrn;
        int n2;
        int n3;
        int n4;
        int n5;
        block7 : {
            block8 : {
                long l = n - 1721426;
                arrn = new int[1];
                n4 = Calendar.floorDivide(l, 146097, arrn);
                n = 0;
                n3 = Calendar.floorDivide(arrn[0], 36524, arrn);
                n2 = Calendar.floorDivide(arrn[0], 1461, arrn);
                n5 = Calendar.floorDivide(arrn[0], 365, arrn);
                n2 = n4 * 400 + n3 * 100 + n2 * 4 + n5;
                n4 = arrn[0];
                if (n3 != 4 && n5 != 4) {
                    ++n2;
                } else {
                    n4 = 365;
                }
                n5 = n;
                if ((n2 & 3) != 0) break block7;
                if (n2 % 100 != 0) break block8;
                n5 = n;
                if (n2 % 400 != 0) break block7;
            }
            n5 = 1;
        }
        n3 = 0;
        n = n5 != 0 ? 60 : 59;
        int n6 = 2;
        if (n4 >= n) {
            n = n5 != 0 ? 1 : 2;
            n3 = n;
        }
        n3 = ((n4 + n3) * 12 + 6) / 367;
        arrn = GREGORIAN_MONTH_COUNT[n3];
        n = n6;
        if (n5 != 0) {
            n = 3;
        }
        n = arrn[n];
        this.gregorianYear = n2;
        this.gregorianMonth = n3;
        this.gregorianDayOfMonth = n4 - n + 1;
        this.gregorianDayOfYear = n4 + 1;
    }

    protected int computeGregorianMonthStart(int n, int n2) {
        int n3;
        int[] arrn;
        int n4;
        block9 : {
            block10 : {
                int n5;
                block8 : {
                    block7 : {
                        n5 = 0;
                        if (n2 < 0) break block7;
                        n3 = n;
                        n4 = n2;
                        if (n2 <= 11) break block8;
                    }
                    arrn = new int[1];
                    n3 = n + Calendar.floorDivide(n2, 12, arrn);
                    n4 = arrn[0];
                }
                n = n5;
                if (n3 % 4 != 0) break block9;
                if (n3 % 100 != 0) break block10;
                n = n5;
                if (n3 % 400 != 0) break block9;
            }
            n = 1;
        }
        n2 = n3 - 1;
        n2 = n3 = n2 * 365 + Calendar.floorDivide(n2, 4) - Calendar.floorDivide(n2, 100) + Calendar.floorDivide(n2, 400) + 1721426 - 1;
        if (n4 != 0) {
            arrn = GREGORIAN_MONTH_COUNT[n4];
            n = n != 0 ? 3 : 2;
            n2 = n3 + arrn[n];
        }
        return n2;
    }

    protected int computeJulianDay() {
        int n;
        if (this.stamp[20] >= 2 && this.newestStamp(17, 19, this.newestStamp(0, 8, 0)) <= this.stamp[20]) {
            return this.internalGet(20);
        }
        int n2 = n = this.resolveFields(this.getFieldResolutionTable());
        if (n < 0) {
            n2 = 5;
        }
        return this.handleComputeJulianDay(n2);
    }

    @Deprecated
    protected int computeMillisInDay() {
        int n = 0;
        int[] arrn = this.stamp;
        int n2 = arrn[11];
        int n3 = Math.max(arrn[10], arrn[9]);
        if (n3 <= n2) {
            n3 = n2;
        }
        if (n3 != 0) {
            n = n3 == n2 ? 0 + this.internalGet(11) : 0 + this.internalGet(10) + this.internalGet(9) * 12;
        }
        return ((n * 60 + this.internalGet(12)) * 60 + this.internalGet(13)) * 1000 + this.internalGet(14);
    }

    @Deprecated
    protected long computeMillisInDayLong() {
        long l = 0L;
        int[] arrn = this.stamp;
        int n = arrn[11];
        int n2 = Math.max(arrn[10], arrn[9]);
        if (n2 <= n) {
            n2 = n;
        }
        if (n2 != 0) {
            l = n2 == n ? 0L + (long)this.internalGet(11) : 0L + (long)this.internalGet(10) + (long)(this.internalGet(9) * 12);
        }
        return ((l * 60L + (long)this.internalGet(12)) * 60L + (long)this.internalGet(13)) * 1000L + (long)this.internalGet(14);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void computeTime() {
        if (!this.isLenient()) {
            this.validateFields();
        }
        long l = Calendar.julianDayToMillis(this.computeJulianDay());
        long l2 = this.stamp[21] >= 2 && this.newestStamp(9, 14, 0) <= this.stamp[21] ? (long)this.internalGet(21) : (Math.max(Math.abs(this.internalGet(11)), Math.abs(this.internalGet(10))) > 548 ? this.computeMillisInDayLong() : (long)this.computeMillisInDay());
        Object object = this.stamp;
        if (object[15] < 2 && object[16] < 2) {
            if (this.lenient && this.skippedWallTime != 2) {
                this.time = l + l2 - (long)this.computeZoneOffset(l, l2);
                return;
            }
            int n = this.computeZoneOffset(l, l2);
            if (n == this.zone.getOffset(l2 = l + l2 - (long)n)) {
                this.time = l2;
                return;
            }
            if (!this.lenient) throw new IllegalArgumentException("The specified wall time does not exist due to time zone offset transition.");
            object = this.getImmediatePreviousZoneTransition(l2);
            if (object != null) {
                this.time = (Long)object;
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not locate a time zone transition before ");
            ((StringBuilder)object).append(l2);
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        this.time = l + l2 - (long)(this.internalGet(15) + this.internalGet(16));
    }

    @Deprecated
    protected int computeZoneOffset(long l, int n) {
        int[] arrn = new int[2];
        l += (long)n;
        TimeZone timeZone = this.zone;
        if (timeZone instanceof BasicTimeZone) {
            n = this.repeatedWallTime == 1 ? 4 : 12;
            int n2 = this.skippedWallTime == 1 ? 12 : 4;
            ((BasicTimeZone)this.zone).getOffsetFromLocal(l, n2, n, arrn);
        } else {
            long l2;
            int n3;
            timeZone.getOffset(l, true, arrn);
            n = n3 = 0;
            if (this.repeatedWallTime == 1) {
                l2 = arrn[0] + arrn[1];
                n = this.zone.getOffset(l - l2 - 21600000L);
                int n4 = arrn[0] + arrn[1] - n;
                n = n3;
                if (n4 < 0) {
                    n = 1;
                    this.zone.getOffset((long)n4 + l, true, arrn);
                }
            }
            if (n == 0 && this.skippedWallTime == 1) {
                l2 = arrn[0] + arrn[1];
                this.zone.getOffset(l - l2, false, arrn);
            }
        }
        return arrn[0] + arrn[1];
    }

    @Deprecated
    protected int computeZoneOffset(long l, long l2) {
        int[] arrn = new int[2];
        l += l2;
        TimeZone timeZone = this.zone;
        if (timeZone instanceof BasicTimeZone) {
            int n = this.repeatedWallTime == 1 ? 4 : 12;
            int n2 = this.skippedWallTime == 1 ? 12 : 4;
            ((BasicTimeZone)this.zone).getOffsetFromLocal(l, n2, n, arrn);
        } else {
            int n;
            timeZone.getOffset(l, true, arrn);
            int n3 = n = 0;
            if (this.repeatedWallTime == 1) {
                l2 = arrn[0] + arrn[1];
                n3 = this.zone.getOffset(l - l2 - 21600000L);
                int n4 = arrn[0] + arrn[1] - n3;
                n3 = n;
                if (n4 < 0) {
                    n3 = 1;
                    this.zone.getOffset((long)n4 + l, true, arrn);
                }
            }
            if (n3 == 0 && this.skippedWallTime == 1) {
                l2 = arrn[0] + arrn[1];
                this.zone.getOffset(l - l2, false, arrn);
            }
        }
        return arrn[0] + arrn[1];
    }

    public boolean equals(Object object) {
        boolean bl;
        block3 : {
            bl = false;
            if (object == null) {
                return false;
            }
            if (this == object) {
                return true;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            if (!this.isEquivalentTo((Calendar)(object = (Calendar)object)) || this.getTimeInMillis() != ((Calendar)object).getTime().getTime()) break block3;
            bl = true;
        }
        return bl;
    }

    public int fieldDifference(Date date, int n) {
        long l;
        int n2;
        block16 : {
            int n3;
            block15 : {
                long l2;
                n2 = 0;
                n3 = 0;
                int n4 = 0;
                l = this.getTimeInMillis();
                if (l < (l2 = date.getTime())) {
                    n3 = 1;
                    n2 = n4;
                    do {
                        this.setTimeInMillis(l);
                        this.add(n, n3);
                        long l3 = this.getTimeInMillis();
                        if (l3 == l2) {
                            return n3;
                        }
                        if (l3 > l2) {
                            n4 = n3;
                            n3 = n2;
                            while (n4 - n3 > 1) {
                                n2 = (n4 - n3) / 2 + n3;
                                this.setTimeInMillis(l);
                                this.add(n, n2);
                                l3 = this.getTimeInMillis();
                                if (l3 == l2) {
                                    return n2;
                                }
                                if (l3 > l2) {
                                    n4 = n2;
                                    continue;
                                }
                                n3 = n2;
                            }
                            break block15;
                        }
                        if (n3 >= Integer.MAX_VALUE) break;
                        n2 = n3;
                        n3 = n4 = n3 << 1;
                        if (n4 >= 0) continue;
                        n3 = Integer.MAX_VALUE;
                    } while (true);
                    throw new RuntimeException();
                }
                if (l > l2) {
                    n3 = -1;
                    do {
                        this.setTimeInMillis(l);
                        this.add(n, n3);
                        long l4 = this.getTimeInMillis();
                        if (l4 == l2) {
                            return n3;
                        }
                        if (l4 < l2) {
                            n4 = n3;
                            n3 = n2;
                            do {
                                n2 = n3;
                                if (n3 - n4 > 1) {
                                    n2 = (n4 - n3) / 2 + n3;
                                    this.setTimeInMillis(l);
                                    this.add(n, n2);
                                    l4 = this.getTimeInMillis();
                                    if (l4 == l2) {
                                        return n2;
                                    }
                                    if (l4 < l2) {
                                        n4 = n2;
                                        continue;
                                    }
                                    n3 = n2;
                                    continue;
                                }
                                break block16;
                                break;
                            } while (true);
                        }
                        n2 = n3;
                    } while ((n3 <<= 1) != 0);
                    throw new RuntimeException();
                }
            }
            n2 = n3;
        }
        this.setTimeInMillis(l);
        this.add(n, n2);
        return n2;
    }

    protected String fieldName(int n) {
        try {
            String string = FIELD_NAME[n];
            return string;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Field ");
            stringBuilder.append(n);
            return stringBuilder.toString();
        }
    }

    public final int get(int n) {
        this.complete();
        return this.fields[n];
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getActualMaximum(int var1_1) {
        if (var1_1 == 0) return this.getMaximum(var1_1);
        if (var1_1 == 18) return this.getMaximum(var1_1);
        if (var1_1 == 5) ** GOTO lbl15
        if (var1_1 == 6) ** GOTO lbl11
        if (var1_1 == 7) return this.getMaximum(var1_1);
        if (var1_1 == 20) return this.getMaximum(var1_1);
        if (var1_1 == 21) return this.getMaximum(var1_1);
        switch (var1_1) {
            default: {
                return this.getActualHelper(var1_1, this.getLeastMaximum(var1_1), this.getMaximum(var1_1));
            }
lbl11: // 1 sources:
            var2_2 = (Calendar)this.clone();
            var2_2.setLenient(true);
            var2_2.prepareGetActual(var1_1, false);
            return this.handleGetYearLength(var2_2.get(19));
lbl15: // 1 sources:
            var2_3 = (Calendar)this.clone();
            var2_3.setLenient(true);
            var2_3.prepareGetActual(var1_1, false);
            return this.handleGetMonthLength(var2_3.get(19), var2_3.get(2));
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: {
                return this.getMaximum(var1_1);
            }
        }
    }

    public int getActualMinimum(int n) {
        switch (n) {
            default: {
                n = this.getActualHelper(n, this.getGreatestMinimum(n), this.getMinimum(n));
                break;
            }
            case 7: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 18: 
            case 20: 
            case 21: {
                n = this.getMinimum(n);
            }
        }
        return n;
    }

    public DateFormat getDateTimeFormat(int n, int n2, ULocale uLocale) {
        return Calendar.formatHelper(this, uLocale, n, n2);
    }

    public DateFormat getDateTimeFormat(int n, int n2, Locale locale) {
        return Calendar.formatHelper(this, ULocale.forLocale(locale), n, n2);
    }

    @Deprecated
    public int getDayOfWeekType(int n) {
        int n2 = 1;
        int n3 = 1;
        int n4 = 1;
        if (n >= 1 && n <= 7) {
            int n5 = this.weekendOnset;
            int n6 = this.weekendCease;
            if (n5 == n6) {
                if (n != n5) {
                    return 0;
                }
                n = this.weekendOnsetMillis == 0 ? n4 : 2;
                return n;
            }
            if (n5 < n6 ? n < n5 || n > n6 : n > n6 && n < n5) {
                return 0;
            }
            if (n == this.weekendOnset) {
                n = this.weekendOnsetMillis == 0 ? n2 : 2;
                return n;
            }
            if (n == this.weekendCease) {
                n = this.weekendCeaseMillis >= 86400000 ? n3 : 3;
                return n;
            }
            return 1;
        }
        throw new IllegalArgumentException("Invalid day of week");
    }

    protected int getDefaultDayInMonth(int n, int n2) {
        return 1;
    }

    protected int getDefaultMonthInYear(int n) {
        return 0;
    }

    public String getDisplayName(ULocale uLocale) {
        return this.getClass().getName();
    }

    public String getDisplayName(Locale locale) {
        return this.getClass().getName();
    }

    public final int getFieldCount() {
        return this.fields.length;
    }

    protected int[][][] getFieldResolutionTable() {
        return DATE_PRECEDENCE;
    }

    public int getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    public final int getGreatestMinimum(int n) {
        return this.getLimit(n, 1);
    }

    protected final int getGregorianDayOfMonth() {
        return this.gregorianDayOfMonth;
    }

    protected final int getGregorianDayOfYear() {
        return this.gregorianDayOfYear;
    }

    protected final int getGregorianMonth() {
        return this.gregorianMonth;
    }

    protected final int getGregorianYear() {
        return this.gregorianYear;
    }

    public final int getLeastMaximum(int n) {
        return this.getLimit(n, 2);
    }

    protected int getLimit(int n, int n2) {
        switch (n) {
            default: {
                return this.handleGetLimit(n, n2);
            }
            case 7: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 18: 
            case 20: 
            case 21: 
            case 22: {
                return LIMITS[n][n2];
            }
            case 4: 
        }
        n = 1;
        if (n2 == 0) {
            if (this.getMinimalDaysInFirstWeek() != 1) {
                n = 0;
            }
        } else if (n2 == 1) {
            n = 1;
        } else {
            n = this.getMinimalDaysInFirstWeek();
            int n3 = this.handleGetLimit(5, n2);
            n = n2 == 2 ? (7 - n + n3) / 7 : (n3 + 6 + (7 - n)) / 7;
        }
        return n;
    }

    @UnsupportedAppUsage
    public final ULocale getLocale(ULocale.Type object) {
        object = object == ULocale.ACTUAL_LOCALE ? this.actualLocale : this.validLocale;
        return object;
    }

    public final int getMaximum(int n) {
        return this.getLimit(n, 3);
    }

    public int getMinimalDaysInFirstWeek() {
        return this.minimalDaysInFirstWeek;
    }

    public final int getMinimum(int n) {
        return this.getLimit(n, 0);
    }

    @Deprecated
    public final int getRelatedYear() {
        CalType calType;
        int n = this.get(19);
        CalType calType2 = CalType.GREGORIAN;
        String string = this.getType();
        CalType[] arrcalType = CalType.values();
        int n2 = arrcalType.length;
        int n3 = 0;
        do {
            calType = calType2;
            if (n3 >= n2 || string.equals((calType = arrcalType[n3]).getId())) break;
            ++n3;
        } while (true);
        switch (calType) {
            default: {
                n3 = n;
                break;
            }
            case PERSIAN: {
                n3 = n + 622;
                break;
            }
            case ISLAMIC_CIVIL: 
            case ISLAMIC_UMALQURA: 
            case ISLAMIC_TBLA: 
            case ISLAMIC_RGSA: 
            case ISLAMIC: {
                n3 = Calendar.gregoYearFromIslamicStart(n);
                break;
            }
            case INDIAN: {
                n3 = n + 79;
                break;
            }
            case HEBREW: {
                n3 = n - 3760;
                break;
            }
            case ETHIOPIC_AMETE_ALEM: {
                n3 = n - 5492;
                break;
            }
            case ETHIOPIC: {
                n3 = n + 8;
                break;
            }
            case DANGI: {
                n3 = n - 2333;
                break;
            }
            case COPTIC: {
                n3 = n + 284;
                break;
            }
            case CHINESE: {
                n3 = n - 2637;
            }
        }
        return n3;
    }

    public int getRepeatedWallTimeOption() {
        return this.repeatedWallTime;
    }

    public int getSkippedWallTimeOption() {
        return this.skippedWallTime;
    }

    protected final int getStamp(int n) {
        return this.stamp[n];
    }

    public final Date getTime() {
        return new Date(this.getTimeInMillis());
    }

    public long getTimeInMillis() {
        if (!this.isTimeSet) {
            this.updateTime();
        }
        return this.time;
    }

    public TimeZone getTimeZone() {
        return this.zone;
    }

    public String getType() {
        return "unknown";
    }

    public WeekData getWeekData() {
        return new WeekData(this.firstDayOfWeek, this.minimalDaysInFirstWeek, this.weekendOnset, this.weekendOnsetMillis, this.weekendCease, this.weekendCeaseMillis);
    }

    @Deprecated
    public int getWeekendTransition(int n) {
        if (n == this.weekendOnset) {
            return this.weekendOnsetMillis;
        }
        if (n == this.weekendCease) {
            return this.weekendCeaseMillis;
        }
        throw new IllegalArgumentException("Not weekend transition day");
    }

    protected void handleComputeFields(int n) {
        this.internalSet(2, this.getGregorianMonth());
        this.internalSet(5, this.getGregorianDayOfMonth());
        this.internalSet(6, this.getGregorianDayOfYear());
        int n2 = this.getGregorianYear();
        this.internalSet(19, n2);
        n = 1;
        int n3 = n2;
        if (n2 < 1) {
            n = 0;
            n3 = 1 - n2;
        }
        this.internalSet(0, n);
        this.internalSet(1, n3);
    }

    protected int handleComputeJulianDay(int n) {
        boolean bl = n == 5 || n == 4 || n == 8;
        int n2 = n == 3 && this.newerField(17, 1) == 17 ? this.internalGet(17) : this.handleGetExtendedYear();
        this.internalSet(19, n2);
        int n3 = bl ? this.internalGet(2, this.getDefaultMonthInYear(n2)) : 0;
        int n4 = this.handleComputeMonthStart(n2, n3, bl);
        if (n == 5) {
            if (this.isSet(5)) {
                return this.internalGet(5, this.getDefaultDayInMonth(n2, n3)) + n4;
            }
            return this.getDefaultDayInMonth(n2, n3) + n4;
        }
        if (n == 6) {
            return this.internalGet(6) + n4;
        }
        int n5 = this.getFirstDayOfWeek();
        int n6 = n3 = Calendar.julianDayToDayOfWeek(n4 + 1) - n5;
        if (n3 < 0) {
            n6 = n3 + 7;
        }
        n3 = 0;
        int n7 = this.resolveFields(DOW_PRECEDENCE);
        if (n7 != 7) {
            if (n7 == 18) {
                n3 = this.internalGet(18) - 1;
            }
        } else {
            n3 = this.internalGet(7) - n5;
        }
        n3 = n5 = n3 % 7;
        if (n5 < 0) {
            n3 = n5 + 7;
        }
        n3 = 1 - n6 + n3;
        if (n == 8) {
            n = n3;
            if (n3 < 1) {
                n = n3 + 7;
            }
            n = (n3 = this.internalGet(8, 1)) >= 0 ? (n += (n3 - 1) * 7) : (n += ((this.handleGetMonthLength(n2, this.internalGet(2, 0)) - n) / 7 + n3 + 1) * 7);
        } else {
            n2 = n3;
            if (7 - n6 < this.getMinimalDaysInFirstWeek()) {
                n2 = n3 + 7;
            }
            n = n2 + (this.internalGet(n) - 1) * 7;
        }
        return n4 + n;
    }

    protected abstract int handleComputeMonthStart(int var1, int var2, boolean var3);

    protected int[] handleCreateFields() {
        return new int[23];
    }

    protected DateFormat handleGetDateFormat(String string, ULocale uLocale) {
        return this.handleGetDateFormat(string, null, uLocale);
    }

    protected DateFormat handleGetDateFormat(String string, String string2, ULocale uLocale) {
        FormatConfiguration formatConfiguration = new FormatConfiguration();
        formatConfiguration.pattern = string;
        formatConfiguration.override = string2;
        formatConfiguration.formatData = new DateFormatSymbols(this, uLocale);
        formatConfiguration.loc = uLocale;
        formatConfiguration.cal = this;
        return SimpleDateFormat.getInstance(formatConfiguration);
    }

    protected DateFormat handleGetDateFormat(String string, String string2, Locale locale) {
        return this.handleGetDateFormat(string, string2, ULocale.forLocale(locale));
    }

    protected DateFormat handleGetDateFormat(String string, Locale locale) {
        return this.handleGetDateFormat(string, null, ULocale.forLocale(locale));
    }

    protected abstract int handleGetExtendedYear();

    protected abstract int handleGetLimit(int var1, int var2);

    protected int handleGetMonthLength(int n, int n2) {
        return this.handleComputeMonthStart(n, n2 + 1, true) - this.handleComputeMonthStart(n, n2, true);
    }

    protected int handleGetYearLength(int n) {
        return this.handleComputeMonthStart(n + 1, 0, false) - this.handleComputeMonthStart(n, 0, false);
    }

    public int hashCode() {
        return this.lenient | this.firstDayOfWeek << 1 | this.minimalDaysInFirstWeek << 4 | this.repeatedWallTime << 7 | this.skippedWallTime << 9 | this.zone.hashCode() << 11;
    }

    @Deprecated
    public boolean haveDefaultCentury() {
        return true;
    }

    protected final int internalGet(int n) {
        return this.fields[n];
    }

    protected final int internalGet(int n, int n2) {
        n = this.stamp[n] > 0 ? this.fields[n] : n2;
        return n;
    }

    protected final long internalGetTimeInMillis() {
        return this.time;
    }

    protected final void internalSet(int n, int n2) {
        if ((1 << n & this.internalSetMask) != 0) {
            this.fields[n] = n2;
            this.stamp[n] = 1;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Subclass cannot set ");
        stringBuilder.append(this.fieldName(n));
        throw new IllegalStateException(stringBuilder.toString());
    }

    public boolean isEquivalentTo(Calendar calendar) {
        boolean bl = this.getClass() == calendar.getClass() && this.isLenient() == calendar.isLenient() && this.getFirstDayOfWeek() == calendar.getFirstDayOfWeek() && this.getMinimalDaysInFirstWeek() == calendar.getMinimalDaysInFirstWeek() && this.getTimeZone().equals(calendar.getTimeZone()) && this.getRepeatedWallTimeOption() == calendar.getRepeatedWallTimeOption() && this.getSkippedWallTimeOption() == calendar.getSkippedWallTimeOption();
        return bl;
    }

    public boolean isLenient() {
        return this.lenient;
    }

    public final boolean isSet(int n) {
        boolean bl = this.areFieldsVirtuallySet || this.stamp[n] != 0;
        return bl;
    }

    public boolean isWeekend() {
        int n = this.get(7);
        int n2 = this.getDayOfWeekType(n);
        boolean bl = false;
        if (n2 != 0) {
            if (n2 != 1) {
                int n3 = this.internalGet(14) + (this.internalGet(13) + (this.internalGet(12) + this.internalGet(11) * 60) * 60) * 1000;
                n = this.getWeekendTransition(n);
                if (n2 == 2) {
                    if (n3 >= n) {
                        bl = true;
                    }
                } else if (n3 < n) {
                    bl = true;
                }
                return bl;
            }
            return true;
        }
        return false;
    }

    public boolean isWeekend(Date date) {
        this.setTime(date);
        return this.isWeekend();
    }

    protected int newerField(int n, int n2) {
        int[] arrn = this.stamp;
        if (arrn[n2] > arrn[n]) {
            return n2;
        }
        return n;
    }

    protected int newestStamp(int n, int n2, int n3) {
        int n4 = n3;
        while (n <= n2) {
            int[] arrn = this.stamp;
            n3 = n4;
            if (arrn[n] > n4) {
                n3 = arrn[n];
            }
            ++n;
            n4 = n3;
        }
        return n4;
    }

    protected void pinField(int n) {
        int n2 = this.getActualMaximum(n);
        int n3 = this.getActualMinimum(n);
        int[] arrn = this.fields;
        if (arrn[n] > n2) {
            this.set(n, n2);
        } else if (arrn[n] < n3) {
            this.set(n, n3);
        }
    }

    protected void prepareGetActual(int n, boolean bl) {
        block7 : {
            block2 : {
                block3 : {
                    int n2;
                    block4 : {
                        block5 : {
                            block6 : {
                                this.set(21, 0);
                                if (n == 1) break block2;
                                if (n == 2) break block3;
                                if (n == 3 || n == 4) break block4;
                                if (n == 8) break block5;
                                if (n == 17) break block6;
                                if (n == 19) break block2;
                                break block7;
                            }
                            this.set(3, this.getGreatestMinimum(3));
                            break block7;
                        }
                        this.set(5, 1);
                        this.set(7, this.get(7));
                        break block7;
                    }
                    int n3 = n2 = this.firstDayOfWeek;
                    if (bl) {
                        n3 = n2 = (n2 + 6) % 7;
                        if (n2 < 1) {
                            n3 = n2 + 7;
                        }
                    }
                    this.set(7, n3);
                    break block7;
                }
                this.set(5, this.getGreatestMinimum(5));
                break block7;
            }
            this.set(6, this.getGreatestMinimum(6));
        }
        this.set(n, this.getGreatestMinimum(n));
    }

    protected int resolveFields(int[][][] arrn) {
        int n;
        block11 : {
            n = -1;
            for (int i = 0; i < arrn.length && n < 0; ++i) {
                int[][] arrn2 = arrn[i];
                int n2 = 0;
                for (int j = 0; j < arrn2.length; ++j) {
                    int n3;
                    int n4;
                    block7 : {
                        int n5;
                        int n6;
                        block10 : {
                            block8 : {
                                block9 : {
                                    int[] arrn3 = arrn2[j];
                                    n5 = 0;
                                    for (n3 = arrn3[0] >= 32 ? 1 : 0; n3 < arrn3.length; ++n3) {
                                        n6 = this.stamp[arrn3[n3]];
                                        if (n6 == 0) {
                                            n4 = n;
                                            n3 = n2;
                                            break block7;
                                        }
                                        n5 = Math.max(n5, n6);
                                    }
                                    n4 = n;
                                    n3 = n2;
                                    if (n5 <= n2) break block7;
                                    n6 = arrn3[0];
                                    if (n6 < 32) break block8;
                                    n3 = n6 & 31;
                                    if (n3 != 5) break block9;
                                    arrn3 = this.stamp;
                                    n6 = n3;
                                    if (arrn3[4] >= arrn3[n3]) break block10;
                                }
                                n = n3;
                                n6 = n3;
                                break block10;
                            }
                            n = n6;
                        }
                        n4 = n;
                        n3 = n2;
                        if (n == n6) {
                            n3 = n5;
                            n4 = n;
                        }
                    }
                    n = n4;
                    n2 = n3;
                }
            }
            if (n < 32) break block11;
            n &= 31;
        }
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void roll(int var1_1, int var2_2) {
        if (var2_2 == 0) {
            return;
        }
        this.complete();
        var3_3 = 1;
        switch (var1_1) {
            default: {
                var4_4 = new StringBuilder();
                var4_4.append("Calendar.roll(");
                var4_4.append(this.fieldName(var1_1));
                var4_4.append(") not supported");
                throw new IllegalArgumentException(var4_4.toString());
            }
            case 20: {
                this.set(var1_1, this.internalGet(var1_1) + var2_2);
                return;
            }
            case 19: {
                this.set(var1_1, this.internalGet(var1_1) + var2_2);
                this.pinField(2);
                this.pinField(5);
                return;
            }
            case 10: 
            case 11: {
                var5_6 = this.getTimeInMillis();
                var3_3 = this.internalGet(var1_1);
                var7_10 = this.getMaximum(var1_1);
                var1_1 = var2_2 = (var3_3 + var2_2) % (var7_10 + 1);
                if (var2_2 < 0) {
                    var1_1 = var2_2 + (var7_10 + 1);
                }
                this.setTimeInMillis(((long)var1_1 - (long)var3_3) * 3600000L + var5_6);
                return;
            }
            case 8: {
                var8_16 = var2_2;
                var1_1 = (this.internalGet(5) - 1) / 7;
                var2_2 = (this.getActualMaximum(5) - this.internalGet(5)) / 7;
                var10_18 = this.time;
                var5_7 = var10_18 - (long)var1_1 * 604800000L;
                var12_19 = (long)(var1_1 + var2_2 + 1) * 604800000L;
                var8_16 = this.time = (var10_18 + var8_16 * 604800000L - var5_7) % var12_19;
                if (var8_16 < 0L) {
                    this.time = var8_16 + var12_19;
                }
                this.setTimeInMillis(this.time + var5_7);
                return;
            }
            case 7: 
            case 18: {
                var8_17 = var2_2;
                var7_11 = this.internalGet(var1_1);
                var2_2 = var3_3;
                if (var1_1 == 7) {
                    var2_2 = this.getFirstDayOfWeek();
                }
                var1_1 = var2_2 = var7_11 - var2_2;
                if (var2_2 < 0) {
                    var1_1 = var2_2 + 7;
                }
                var12_20 = this.time;
                var5_8 = var12_20 - (long)var1_1 * 86400000L;
                this.time = (var12_20 + var8_17 * 86400000L - var5_8) % 604800000L;
                if ((var12_20 = this.time) < 0L) {
                    this.time = var12_20 + 604800000L;
                }
                this.setTimeInMillis(this.time + var5_8);
                return;
            }
            case 6: {
                var12_21 = var2_2;
                var5_9 = this.time - (long)(this.internalGet(6) - 1) * 86400000L;
                var1_1 = this.getActualMaximum(6);
                var12_21 = this.time = (this.time + var12_21 * 86400000L - var5_9) % ((long)var1_1 * 86400000L);
                if (var12_21 < 0L) {
                    this.time = var12_21 + (long)var1_1 * 86400000L;
                }
                this.setTimeInMillis(this.time + var5_9);
                return;
            }
            case 4: {
                var1_1 = var3_3 = this.internalGet(7) - this.getFirstDayOfWeek();
                if (var3_3 < 0) {
                    var1_1 = var3_3 + 7;
                }
                var3_3 = var7_12 = (var1_1 - this.internalGet(5) + 1) % 7;
                if (var7_12 < 0) {
                    var3_3 = var7_12 + 7;
                }
                var3_3 = 7 - var3_3 < this.getMinimalDaysInFirstWeek() ? 8 - var3_3 : 1 - var3_3;
                var7_12 = this.getActualMaximum(5);
                var14_22 = var7_12 + 7 - (var7_12 - this.internalGet(5) + var1_1) % 7 - var3_3;
                var1_1 = var2_2 = (this.internalGet(5) + var2_2 * 7 - var3_3) % var14_22;
                if (var2_2 < 0) {
                    var1_1 = var2_2 + var14_22;
                }
                var1_1 = var2_2 = var1_1 + var3_3;
                if (var2_2 < 1) {
                    var1_1 = 1;
                }
                var2_2 = var1_1;
                if (var1_1 > var7_12) {
                    var2_2 = var7_12;
                }
                this.set(5, var2_2);
                return;
            }
            case 3: {
                var1_1 = var3_3 = this.internalGet(7) - this.getFirstDayOfWeek();
                if (var3_3 < 0) {
                    var1_1 = var3_3 + 7;
                }
                var3_3 = var7_13 = (var1_1 - this.internalGet(6) + 1) % 7;
                if (var7_13 < 0) {
                    var3_3 = var7_13 + 7;
                }
                var3_3 = 7 - var3_3 < this.getMinimalDaysInFirstWeek() ? 8 - var3_3 : 1 - var3_3;
                var7_13 = this.getActualMaximum(6);
                var14_23 = var7_13 + 7 - (var7_13 - this.internalGet(6) + var1_1) % 7 - var3_3;
                var1_1 = var2_2 = (this.internalGet(6) + var2_2 * 7 - var3_3) % var14_23;
                if (var2_2 < 0) {
                    var1_1 = var2_2 + var14_23;
                }
                var1_1 = var2_2 = var1_1 + var3_3;
                if (var2_2 < 1) {
                    var1_1 = 1;
                }
                var2_2 = var1_1;
                if (var1_1 > var7_13) {
                    var2_2 = var7_13;
                }
                this.set(6, var2_2);
                this.clear(2);
                return;
            }
            case 2: {
                var3_3 = this.getActualMaximum(2);
                var1_1 = var2_2 = (this.internalGet(2) + var2_2) % (var3_3 + 1);
                if (var2_2 < 0) {
                    var1_1 = var2_2 + (var3_3 + 1);
                }
                this.set(2, var1_1);
                this.pinField(5);
                return;
            }
            case 1: 
            case 17: {
                var14_24 = 0;
                var15_26 = this.get(0);
                var7_14 = var2_2;
                var3_3 = var14_24;
                if (var15_26 != 0) ** GOTO lbl133
                var4_5 = this.getType();
                if (var4_5.equals("gregorian") || var4_5.equals("roc")) ** GOTO lbl131
                var7_14 = var2_2;
                var3_3 = var14_24;
                if (!var4_5.equals("coptic")) ** GOTO lbl133
lbl131: // 2 sources:
                var7_14 = -var2_2;
                var3_3 = 1;
lbl133: // 3 sources:
                var7_14 = this.internalGet(var1_1) + var7_14;
                if (var15_26 <= 0 && var7_14 < 1) {
                    var2_2 = var7_14;
                    if (var3_3 != 0) {
                        var2_2 = 1;
                    }
                } else {
                    var3_3 = this.getActualMaximum(var1_1);
                    if (var3_3 < 32768) {
                        if (var7_14 < 1) {
                            var2_2 = var3_3 - -var7_14 % var3_3;
                        } else {
                            var2_2 = var7_14;
                            if (var7_14 > var3_3) {
                                var2_2 = (var7_14 - 1) % var3_3 + 1;
                            }
                        }
                    } else {
                        var2_2 = var7_14;
                        if (var7_14 < 1) {
                            var2_2 = 1;
                        }
                    }
                }
                this.set(var1_1, var2_2);
                this.pinField(2);
                this.pinField(5);
                return;
            }
            case 0: 
            case 5: 
            case 9: 
            case 12: 
            case 13: 
            case 14: 
            case 21: 
        }
        var7_15 = this.getActualMinimum(var1_1);
        var14_25 = this.getActualMaximum(var1_1) - var7_15 + 1;
        var2_2 = var3_3 = (this.internalGet(var1_1) + var2_2 - var7_15) % var14_25;
        if (var3_3 < 0) {
            var2_2 = var3_3 + var14_25;
        }
        this.set(var1_1, var2_2 + var7_15);
    }

    public final void roll(int n, boolean bl) {
        int n2 = bl ? 1 : -1;
        this.roll(n, n2);
    }

    public final void set(int n, int n2) {
        if (this.areFieldsVirtuallySet) {
            this.computeFields();
        }
        this.fields[n] = n2;
        if (this.nextStamp == STAMP_MAX) {
            this.recalculateStamp();
        }
        int[] arrn = this.stamp;
        n2 = this.nextStamp;
        this.nextStamp = n2 + 1;
        arrn[n] = n2;
        this.areFieldsVirtuallySet = false;
        this.areFieldsSet = false;
        this.isTimeSet = false;
    }

    public final void set(int n, int n2, int n3) {
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
    }

    public final void set(int n, int n2, int n3, int n4, int n5) {
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
    }

    public final void set(int n, int n2, int n3, int n4, int n5, int n6) {
        this.set(1, n);
        this.set(2, n2);
        this.set(5, n3);
        this.set(11, n4);
        this.set(12, n5);
        this.set(13, n6);
    }

    public void setFirstDayOfWeek(int n) {
        if (this.firstDayOfWeek != n) {
            if (n >= 1 && n <= 7) {
                this.firstDayOfWeek = n;
                this.areFieldsSet = false;
            } else {
                throw new IllegalArgumentException("Invalid day of week");
            }
        }
    }

    public void setLenient(boolean bl) {
        this.lenient = bl;
    }

    final void setLocale(ULocale uLocale, ULocale uLocale2) {
        boolean bl = true;
        boolean bl2 = uLocale == null;
        if (uLocale2 != null) {
            bl = false;
        }
        if (bl2 == bl) {
            this.validLocale = uLocale;
            this.actualLocale = uLocale2;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setMinimalDaysInFirstWeek(int n) {
        int n2;
        if (n < 1) {
            n2 = 1;
        } else {
            n2 = n;
            if (n > 7) {
                n2 = 7;
            }
        }
        if (this.minimalDaysInFirstWeek != n2) {
            this.minimalDaysInFirstWeek = n2;
            this.areFieldsSet = false;
        }
    }

    @Deprecated
    public final void setRelatedYear(int n) {
        CalType calType;
        CalType calType2 = CalType.GREGORIAN;
        String string = this.getType();
        CalType[] arrcalType = CalType.values();
        int n2 = arrcalType.length;
        int n3 = 0;
        do {
            calType = calType2;
            if (n3 >= n2 || string.equals((calType = arrcalType[n3]).getId())) break;
            ++n3;
        } while (true);
        switch (calType) {
            default: {
                break;
            }
            case PERSIAN: {
                n -= 622;
                break;
            }
            case ISLAMIC_CIVIL: 
            case ISLAMIC_UMALQURA: 
            case ISLAMIC_TBLA: 
            case ISLAMIC_RGSA: 
            case ISLAMIC: {
                n = Calendar.firstIslamicStartYearFromGrego(n);
                break;
            }
            case INDIAN: {
                n -= 79;
                break;
            }
            case HEBREW: {
                n += 3760;
                break;
            }
            case ETHIOPIC_AMETE_ALEM: {
                n += 5492;
                break;
            }
            case ETHIOPIC: {
                n -= 8;
                break;
            }
            case DANGI: {
                n += 2333;
                break;
            }
            case COPTIC: {
                n -= 284;
                break;
            }
            case CHINESE: {
                n += 2637;
            }
        }
        this.set(19, n);
    }

    public void setRepeatedWallTimeOption(int n) {
        if (n != 0 && n != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal repeated wall time option - ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.repeatedWallTime = n;
    }

    public void setSkippedWallTimeOption(int n) {
        if (n != 0 && n != 1 && n != 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal skipped wall time option - ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.skippedWallTime = n;
    }

    public final void setTime(Date date) {
        this.setTimeInMillis(date.getTime());
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setTimeInMillis(long l) {
        long l2;
        if (l > 183882168921600000L) {
            if (!this.isLenient()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("millis value greater than upper bounds for a Calendar : ");
                stringBuilder.append(l);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            l2 = 183882168921600000L;
        } else {
            l2 = l;
            if (l < -184303902528000000L) {
                if (!this.isLenient()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("millis value less than lower bounds for a Calendar : ");
                    stringBuilder.append(l);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                l2 = -184303902528000000L;
            }
        }
        this.time = l2;
        this.areAllFieldsSet = false;
        this.areFieldsSet = false;
        this.areFieldsVirtuallySet = true;
        this.isTimeSet = true;
        int n = 0;
        int[] arrn;
        while (n < (arrn = this.fields).length) {
            this.stamp[n] = 0;
            arrn[n] = 0;
            ++n;
        }
        return;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.zone = timeZone;
        this.areFieldsSet = false;
    }

    public Calendar setWeekData(WeekData weekData) {
        this.setFirstDayOfWeek(weekData.firstDayOfWeek);
        this.setMinimalDaysInFirstWeek(weekData.minimalDaysInFirstWeek);
        this.weekendOnset = weekData.weekendOnset;
        this.weekendOnsetMillis = weekData.weekendOnsetMillis;
        this.weekendCease = weekData.weekendCease;
        this.weekendCeaseMillis = weekData.weekendCeaseMillis;
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[time=");
        String string = this.isTimeSet ? String.valueOf(this.time) : "?";
        stringBuilder.append(string);
        stringBuilder.append(",areFieldsSet=");
        stringBuilder.append(this.areFieldsSet);
        stringBuilder.append(",areAllFieldsSet=");
        stringBuilder.append(this.areAllFieldsSet);
        stringBuilder.append(",lenient=");
        stringBuilder.append(this.lenient);
        stringBuilder.append(",zone=");
        stringBuilder.append(this.zone);
        stringBuilder.append(",firstDayOfWeek=");
        stringBuilder.append(this.firstDayOfWeek);
        stringBuilder.append(",minimalDaysInFirstWeek=");
        stringBuilder.append(this.minimalDaysInFirstWeek);
        stringBuilder.append(",repeatedWallTime=");
        stringBuilder.append(this.repeatedWallTime);
        stringBuilder.append(",skippedWallTime=");
        stringBuilder.append(this.skippedWallTime);
        for (int i = 0; i < this.fields.length; ++i) {
            stringBuilder.append(',');
            stringBuilder.append(this.fieldName(i));
            stringBuilder.append('=');
            string = this.isSet(i) ? String.valueOf(this.fields[i]) : "?";
            stringBuilder.append(string);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void validateField(int n) {
        if (n != 5) {
            if (n != 6) {
                if (n != 8) {
                    this.validateField(n, this.getMinimum(n), this.getMaximum(n));
                    return;
                } else {
                    if (this.internalGet(n) == 0) throw new IllegalArgumentException("DAY_OF_WEEK_IN_MONTH cannot be zero");
                    this.validateField(n, this.getMinimum(n), this.getMaximum(n));
                }
                return;
            } else {
                this.validateField(n, 1, this.handleGetYearLength(this.handleGetExtendedYear()));
            }
            return;
        } else {
            this.validateField(n, 1, this.handleGetMonthLength(this.handleGetExtendedYear(), this.internalGet(2)));
        }
    }

    protected final void validateField(int n, int n2, int n3) {
        int n4 = this.fields[n];
        if (n4 >= n2 && n4 <= n3) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.fieldName(n));
        stringBuilder.append('=');
        stringBuilder.append(n4);
        stringBuilder.append(", valid range=");
        stringBuilder.append(n2);
        stringBuilder.append("..");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    protected void validateFields() {
        for (int i = 0; i < this.fields.length; ++i) {
            if (this.stamp[i] < 2) continue;
            this.validateField(i);
        }
    }

    protected final int weekNumber(int n, int n2) {
        return this.weekNumber(n, n, n2);
    }

    protected int weekNumber(int n, int n2, int n3) {
        n2 = n3 = (n3 - this.getFirstDayOfWeek() - n2 + 1) % 7;
        if (n3 < 0) {
            n2 = n3 + 7;
        }
        n = n3 = (n + n2 - 1) / 7;
        if (7 - n2 >= this.getMinimalDaysInFirstWeek()) {
            n = n3 + 1;
        }
        return n;
    }

    @Deprecated
    public static class FormatConfiguration {
        private Calendar cal;
        private DateFormatSymbols formatData;
        private ULocale loc;
        private String override;
        private String pattern;

        private FormatConfiguration() {
        }

        @Deprecated
        public Calendar getCalendar() {
            return this.cal;
        }

        @Deprecated
        public DateFormatSymbols getDateFormatSymbols() {
            return this.formatData;
        }

        @Deprecated
        public ULocale getLocale() {
            return this.loc;
        }

        @Deprecated
        public String getOverrideString() {
            return this.override;
        }

        @Deprecated
        public String getPatternString() {
            return this.pattern;
        }
    }

    static class PatternData {
        private String[] overrides;
        private String[] patterns;

        public PatternData(String[] arrstring, String[] arrstring2) {
            this.patterns = arrstring;
            this.overrides = arrstring2;
        }

        private String getDateTimePattern(int n) {
            int n2 = 8;
            if (this.patterns.length >= 13) {
                n2 = 8 + (n + 1);
            }
            return this.patterns[n2];
        }

        private static PatternData make(Calendar calendar, ULocale uLocale) {
            return PatternData.make(uLocale, calendar.getType());
        }

        private static PatternData make(ULocale object, String string) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append(((ULocale)object).getBaseName());
            ((StringBuilder)object2).append("+");
            ((StringBuilder)object2).append(string);
            String string2 = ((StringBuilder)object2).toString();
            PatternData patternData = (PatternData)PATTERN_CACHE.get(string2);
            object2 = patternData;
            if (patternData == null) {
                try {
                    object = Calendar.getPatternData((ULocale)object, string);
                }
                catch (MissingResourceException missingResourceException) {
                    object = new PatternData(DEFAULT_PATTERNS, null);
                }
                PATTERN_CACHE.put(string2, object);
                object2 = object;
            }
            return object2;
        }
    }

    public static final class WeekData {
        public final int firstDayOfWeek;
        public final int minimalDaysInFirstWeek;
        public final int weekendCease;
        public final int weekendCeaseMillis;
        public final int weekendOnset;
        public final int weekendOnsetMillis;

        public WeekData(int n, int n2, int n3, int n4, int n5, int n6) {
            this.firstDayOfWeek = n;
            this.minimalDaysInFirstWeek = n2;
            this.weekendOnset = n3;
            this.weekendOnsetMillis = n4;
            this.weekendCease = n5;
            this.weekendCeaseMillis = n6;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof WeekData)) {
                return false;
            }
            object = (WeekData)object;
            if (this.firstDayOfWeek != ((WeekData)object).firstDayOfWeek || this.minimalDaysInFirstWeek != ((WeekData)object).minimalDaysInFirstWeek || this.weekendOnset != ((WeekData)object).weekendOnset || this.weekendOnsetMillis != ((WeekData)object).weekendOnsetMillis || this.weekendCease != ((WeekData)object).weekendCease || this.weekendCeaseMillis != ((WeekData)object).weekendCeaseMillis) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            return ((((this.firstDayOfWeek * 37 + this.minimalDaysInFirstWeek) * 37 + this.weekendOnset) * 37 + this.weekendOnsetMillis) * 37 + this.weekendCease) * 37 + this.weekendCeaseMillis;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            stringBuilder.append(this.firstDayOfWeek);
            stringBuilder.append(", ");
            stringBuilder.append(this.minimalDaysInFirstWeek);
            stringBuilder.append(", ");
            stringBuilder.append(this.weekendOnset);
            stringBuilder.append(", ");
            stringBuilder.append(this.weekendOnsetMillis);
            stringBuilder.append(", ");
            stringBuilder.append(this.weekendCease);
            stringBuilder.append(", ");
            stringBuilder.append(this.weekendCeaseMillis);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    private static class WeekDataCache
    extends SoftCache<String, WeekData, String> {
        private WeekDataCache() {
        }

        @Override
        protected WeekData createInstance(String string, String string2) {
            return Calendar.getWeekDataForRegionInternal(string);
        }
    }

}

