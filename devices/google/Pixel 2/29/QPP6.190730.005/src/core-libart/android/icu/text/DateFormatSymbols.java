/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CacheBase;
import android.icu.impl.CalendarUtil;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SoftCache;
import android.icu.impl.UResource;
import android.icu.impl.Utility;
import android.icu.text.NumberingSystem;
import android.icu.text.TimeZoneNames;
import android.icu.util.Calendar;
import android.icu.util.ICUCloneNotSupportedException;
import android.icu.util.ICUException;
import android.icu.util.TimeZone;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import android.icu.util.UResourceBundleIterator;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class DateFormatSymbols
implements Serializable,
Cloneable {
    public static final int ABBREVIATED = 0;
    static final String ALTERNATE_TIME_SEPARATOR = ".";
    private static final String[][] CALENDAR_CLASSES;
    private static final String[] DAY_PERIOD_KEYS;
    static final String DEFAULT_TIME_SEPARATOR = ":";
    private static CacheBase<String, DateFormatSymbols, ULocale> DFSCACHE;
    @Deprecated
    public static final int DT_CONTEXT_COUNT = 3;
    static final int DT_LEAP_MONTH_PATTERN_FORMAT_ABBREV = 1;
    static final int DT_LEAP_MONTH_PATTERN_FORMAT_NARROW = 2;
    static final int DT_LEAP_MONTH_PATTERN_FORMAT_WIDE = 0;
    static final int DT_LEAP_MONTH_PATTERN_NUMERIC = 6;
    static final int DT_LEAP_MONTH_PATTERN_STANDALONE_ABBREV = 4;
    static final int DT_LEAP_MONTH_PATTERN_STANDALONE_NARROW = 5;
    static final int DT_LEAP_MONTH_PATTERN_STANDALONE_WIDE = 3;
    static final int DT_MONTH_PATTERN_COUNT = 7;
    @Deprecated
    public static final int DT_WIDTH_COUNT = 4;
    public static final int FORMAT = 0;
    private static final String[] LEAP_MONTH_PATTERNS_PATHS;
    public static final int NARROW = 2;
    @Deprecated
    public static final int NUMERIC = 2;
    public static final int SHORT = 3;
    public static final int STANDALONE = 1;
    public static final int WIDE = 1;
    private static final Map<String, CapitalizationContextUsage> contextUsageTypeMap;
    static final int millisPerHour = 3600000;
    static final String patternChars = "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB";
    private static final long serialVersionUID = -5987973545549424702L;
    String[] abbreviatedDayPeriods = null;
    private ULocale actualLocale;
    String[] ampms = null;
    String[] ampmsNarrow = null;
    Map<CapitalizationContextUsage, boolean[]> capitalization = null;
    String[] eraNames = null;
    String[] eras = null;
    String[] leapMonthPatterns = null;
    String localPatternChars = null;
    String[] months = null;
    String[] narrowDayPeriods = null;
    String[] narrowEras = null;
    String[] narrowMonths = null;
    String[] narrowWeekdays = null;
    String[] quarters = null;
    private ULocale requestedLocale;
    String[] shortMonths = null;
    String[] shortQuarters = null;
    String[] shortWeekdays = null;
    String[] shortYearNames = null;
    String[] shortZodiacNames = null;
    String[] shorterWeekdays = null;
    String[] standaloneAbbreviatedDayPeriods = null;
    String[] standaloneMonths = null;
    String[] standaloneNarrowDayPeriods = null;
    String[] standaloneNarrowMonths = null;
    String[] standaloneNarrowWeekdays = null;
    String[] standaloneQuarters = null;
    String[] standaloneShortMonths = null;
    String[] standaloneShortQuarters = null;
    String[] standaloneShortWeekdays = null;
    String[] standaloneShorterWeekdays = null;
    String[] standaloneWeekdays = null;
    String[] standaloneWideDayPeriods = null;
    private String timeSeparator = null;
    private ULocale validLocale;
    String[] weekdays = null;
    String[] wideDayPeriods = null;
    private String[][] zoneStrings = null;

    static {
        String[] arrstring = new String[]{"GregorianCalendar", "gregorian"};
        String[] arrstring2 = new String[]{"JapaneseCalendar", "japanese"};
        String[] arrstring3 = new String[]{"BuddhistCalendar", "buddhist"};
        String[] arrstring4 = new String[]{"PersianCalendar", "persian"};
        String[] arrstring5 = new String[]{"HebrewCalendar", "hebrew"};
        String[] arrstring6 = new String[]{"EthiopicCalendar", "ethiopic"};
        CALENDAR_CLASSES = new String[][]{arrstring, arrstring2, arrstring3, {"TaiwanCalendar", "roc"}, arrstring4, {"IslamicCalendar", "islamic"}, arrstring5, {"ChineseCalendar", "chinese"}, {"IndianCalendar", "indian"}, {"CopticCalendar", "coptic"}, arrstring6};
        contextUsageTypeMap = new HashMap<String, CapitalizationContextUsage>();
        contextUsageTypeMap.put("month-format-except-narrow", CapitalizationContextUsage.MONTH_FORMAT);
        contextUsageTypeMap.put("month-standalone-except-narrow", CapitalizationContextUsage.MONTH_STANDALONE);
        contextUsageTypeMap.put("month-narrow", CapitalizationContextUsage.MONTH_NARROW);
        contextUsageTypeMap.put("day-format-except-narrow", CapitalizationContextUsage.DAY_FORMAT);
        contextUsageTypeMap.put("day-standalone-except-narrow", CapitalizationContextUsage.DAY_STANDALONE);
        contextUsageTypeMap.put("day-narrow", CapitalizationContextUsage.DAY_NARROW);
        contextUsageTypeMap.put("era-name", CapitalizationContextUsage.ERA_WIDE);
        contextUsageTypeMap.put("era-abbr", CapitalizationContextUsage.ERA_ABBREV);
        contextUsageTypeMap.put("era-narrow", CapitalizationContextUsage.ERA_NARROW);
        contextUsageTypeMap.put("zone-long", CapitalizationContextUsage.ZONE_LONG);
        contextUsageTypeMap.put("zone-short", CapitalizationContextUsage.ZONE_SHORT);
        contextUsageTypeMap.put("metazone-long", CapitalizationContextUsage.METAZONE_LONG);
        contextUsageTypeMap.put("metazone-short", CapitalizationContextUsage.METAZONE_SHORT);
        DFSCACHE = new SoftCache<String, DateFormatSymbols, ULocale>(){

            @Override
            protected DateFormatSymbols createInstance(String string, ULocale uLocale) {
                int n;
                int n2 = string.indexOf(43) + 1;
                int n3 = n = string.indexOf(43, n2);
                if (n < 0) {
                    n3 = string.length();
                }
                return new DateFormatSymbols(uLocale, null, string.substring(n2, n3));
            }
        };
        LEAP_MONTH_PATTERNS_PATHS = new String[7];
        arrstring = LEAP_MONTH_PATTERNS_PATHS;
        arrstring[0] = "monthPatterns/format/wide";
        arrstring[1] = "monthPatterns/format/abbreviated";
        arrstring[2] = "monthPatterns/format/narrow";
        arrstring[3] = "monthPatterns/stand-alone/wide";
        arrstring[4] = "monthPatterns/stand-alone/abbreviated";
        arrstring[5] = "monthPatterns/stand-alone/narrow";
        arrstring[6] = "monthPatterns/numeric/all";
        DAY_PERIOD_KEYS = new String[]{"midnight", "noon", "morning1", "afternoon1", "evening1", "night1", "morning2", "afternoon2", "evening2", "night2"};
    }

    public DateFormatSymbols() {
        this(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public DateFormatSymbols(Calendar calendar, ULocale uLocale) {
        this.initializeData(uLocale, calendar.getType());
    }

    public DateFormatSymbols(Calendar calendar, Locale locale) {
        this.initializeData(ULocale.forLocale(locale), calendar.getType());
    }

    public DateFormatSymbols(ULocale uLocale) {
        this.initializeData(uLocale, CalendarUtil.getCalendarType(uLocale));
    }

    private DateFormatSymbols(ULocale uLocale, ICUResourceBundle iCUResourceBundle, String string) {
        this.initializeData(uLocale, iCUResourceBundle, string);
    }

    @Deprecated
    public DateFormatSymbols(ULocale uLocale, String string) {
        this.initializeData(uLocale, string);
    }

    public DateFormatSymbols(Class<? extends Calendar> object, ULocale uLocale) {
        object = object.getName();
        String string = object.substring(object.lastIndexOf(46) + 1);
        Object object2 = null;
        String[][] arrstring = CALENDAR_CLASSES;
        int n = arrstring.length;
        int n2 = 0;
        do {
            object = object2;
            if (n2 >= n) break;
            object = arrstring[n2];
            if (object[0].equals(string)) {
                object = object[1];
                break;
            }
            ++n2;
        } while (true);
        object2 = object;
        if (object == null) {
            object2 = string.replaceAll("Calendar", "").toLowerCase(Locale.ENGLISH);
        }
        this.initializeData(uLocale, (String)object2);
    }

    public DateFormatSymbols(Class<? extends Calendar> class_, Locale locale) {
        this(class_, ULocale.forLocale(locale));
    }

    public DateFormatSymbols(Locale locale) {
        this(ULocale.forLocale(locale));
    }

    public DateFormatSymbols(ResourceBundle resourceBundle, ULocale uLocale) {
        this.initializeData(uLocale, (ICUResourceBundle)resourceBundle, CalendarUtil.getCalendarType(uLocale));
    }

    public DateFormatSymbols(ResourceBundle resourceBundle, Locale locale) {
        this(resourceBundle, ULocale.forLocale(locale));
    }

    private static final boolean arrayOfArrayEquals(Object[][] arrobject, Object[][] arrobject2) {
        if (arrobject == arrobject2) {
            return true;
        }
        if (arrobject != null && arrobject2 != null) {
            if (arrobject.length != arrobject2.length) {
                return false;
            }
            boolean bl = true;
            for (int i = 0; i < arrobject.length && (bl = Utility.arrayEquals(arrobject[i], (Object)arrobject2[i])); ++i) {
            }
            return bl;
        }
        return false;
    }

    private final String[] duplicate(String[] arrstring) {
        return (String[])arrstring.clone();
    }

    private final String[][] duplicate(String[][] arrstring) {
        String[][] arrstring2 = new String[arrstring.length][];
        for (int i = 0; i < arrstring.length; ++i) {
            arrstring2[i] = this.duplicate(arrstring[i]);
        }
        return arrstring2;
    }

    public static Locale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableLocales();
    }

    public static ULocale[] getAvailableULocales() {
        return ICUResourceBundle.getAvailableULocales();
    }

    @Deprecated
    public static ResourceBundle getDateFormatBundle(Calendar calendar, ULocale uLocale) throws MissingResourceException {
        return null;
    }

    @Deprecated
    public static ResourceBundle getDateFormatBundle(Calendar calendar, Locale locale) throws MissingResourceException {
        return null;
    }

    @Deprecated
    public static ResourceBundle getDateFormatBundle(Class<? extends Calendar> class_, ULocale uLocale) throws MissingResourceException {
        return null;
    }

    @Deprecated
    public static ResourceBundle getDateFormatBundle(Class<? extends Calendar> class_, Locale locale) throws MissingResourceException {
        return null;
    }

    public static DateFormatSymbols getInstance() {
        return new DateFormatSymbols();
    }

    public static DateFormatSymbols getInstance(ULocale uLocale) {
        return new DateFormatSymbols(uLocale);
    }

    public static DateFormatSymbols getInstance(Locale locale) {
        return new DateFormatSymbols(locale);
    }

    private String[] loadDayPeriodStrings(Map<String, String> map) {
        String[] arrstring = new String[DAY_PERIOD_KEYS.length];
        if (map != null) {
            String[] arrstring2;
            for (int i = 0; i < (arrstring2 = DAY_PERIOD_KEYS).length; ++i) {
                arrstring[i] = map.get(arrstring2[i]);
            }
        }
        return arrstring;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
    }

    public Object clone() {
        try {
            DateFormatSymbols dateFormatSymbols = (DateFormatSymbols)super.clone();
            return dateFormatSymbols;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (DateFormatSymbols)object;
            if (!(Utility.arrayEquals(this.eras, (Object)((DateFormatSymbols)object).eras) && Utility.arrayEquals(this.eraNames, (Object)((DateFormatSymbols)object).eraNames) && Utility.arrayEquals(this.months, (Object)((DateFormatSymbols)object).months) && Utility.arrayEquals(this.shortMonths, (Object)((DateFormatSymbols)object).shortMonths) && Utility.arrayEquals(this.narrowMonths, (Object)((DateFormatSymbols)object).narrowMonths) && Utility.arrayEquals(this.standaloneMonths, (Object)((DateFormatSymbols)object).standaloneMonths) && Utility.arrayEquals(this.standaloneShortMonths, (Object)((DateFormatSymbols)object).standaloneShortMonths) && Utility.arrayEquals(this.standaloneNarrowMonths, (Object)((DateFormatSymbols)object).standaloneNarrowMonths) && Utility.arrayEquals(this.weekdays, (Object)((DateFormatSymbols)object).weekdays) && Utility.arrayEquals(this.shortWeekdays, (Object)((DateFormatSymbols)object).shortWeekdays) && Utility.arrayEquals(this.shorterWeekdays, (Object)((DateFormatSymbols)object).shorterWeekdays) && Utility.arrayEquals(this.narrowWeekdays, (Object)((DateFormatSymbols)object).narrowWeekdays) && Utility.arrayEquals(this.standaloneWeekdays, (Object)((DateFormatSymbols)object).standaloneWeekdays) && Utility.arrayEquals(this.standaloneShortWeekdays, (Object)((DateFormatSymbols)object).standaloneShortWeekdays) && Utility.arrayEquals(this.standaloneShorterWeekdays, (Object)((DateFormatSymbols)object).standaloneShorterWeekdays) && Utility.arrayEquals(this.standaloneNarrowWeekdays, (Object)((DateFormatSymbols)object).standaloneNarrowWeekdays) && Utility.arrayEquals(this.ampms, (Object)((DateFormatSymbols)object).ampms) && Utility.arrayEquals(this.ampmsNarrow, (Object)((DateFormatSymbols)object).ampmsNarrow) && Utility.arrayEquals(this.abbreviatedDayPeriods, (Object)((DateFormatSymbols)object).abbreviatedDayPeriods) && Utility.arrayEquals(this.wideDayPeriods, (Object)((DateFormatSymbols)object).wideDayPeriods) && Utility.arrayEquals(this.narrowDayPeriods, (Object)((DateFormatSymbols)object).narrowDayPeriods) && Utility.arrayEquals(this.standaloneAbbreviatedDayPeriods, (Object)((DateFormatSymbols)object).standaloneAbbreviatedDayPeriods) && Utility.arrayEquals(this.standaloneWideDayPeriods, (Object)((DateFormatSymbols)object).standaloneWideDayPeriods) && Utility.arrayEquals(this.standaloneNarrowDayPeriods, (Object)((DateFormatSymbols)object).standaloneNarrowDayPeriods) && Utility.arrayEquals(this.timeSeparator, (Object)((DateFormatSymbols)object).timeSeparator) && DateFormatSymbols.arrayOfArrayEquals(this.zoneStrings, ((DateFormatSymbols)object).zoneStrings) && this.requestedLocale.getDisplayName().equals(((DateFormatSymbols)object).requestedLocale.getDisplayName()) && Utility.arrayEquals(this.localPatternChars, (Object)((DateFormatSymbols)object).localPatternChars))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public String[] getAmPmStrings() {
        return this.duplicate(this.ampms);
    }

    public String[] getEraNames() {
        return this.duplicate(this.eraNames);
    }

    public String[] getEras() {
        return this.duplicate(this.eras);
    }

    @Deprecated
    public String getLeapMonthPattern(int n, int n2) {
        block1 : {
            block4 : {
                block8 : {
                    block9 : {
                        block10 : {
                            int n3;
                            block2 : {
                                block5 : {
                                    block6 : {
                                        block7 : {
                                            block3 : {
                                                if (this.leapMonthPatterns == null) break block1;
                                                n3 = -1;
                                                if (n == 0) break block2;
                                                if (n == 1) break block3;
                                                n = n != 2 ? n3 : 6;
                                                break block4;
                                            }
                                            if (n2 == 0) break block5;
                                            if (n2 == 1) break block6;
                                            if (n2 == 2) break block7;
                                            if (n2 == 3) break block5;
                                            n = n3;
                                            break block4;
                                        }
                                        n = 5;
                                        break block4;
                                    }
                                    n = 3;
                                    break block4;
                                }
                                n = 1;
                                break block4;
                            }
                            if (n2 == 0) break block8;
                            if (n2 == 1) break block9;
                            if (n2 == 2) break block10;
                            if (n2 == 3) break block8;
                            n = n3;
                            break block4;
                        }
                        n = 2;
                        break block4;
                    }
                    n = 0;
                    break block4;
                }
                n = 1;
            }
            if (n >= 0) {
                return this.leapMonthPatterns[n];
            }
            throw new IllegalArgumentException("Bad context or width argument");
        }
        return null;
    }

    public String getLocalPatternChars() {
        return this.localPatternChars;
    }

    @UnsupportedAppUsage
    public final ULocale getLocale(ULocale.Type object) {
        object = object == ULocale.ACTUAL_LOCALE ? this.actualLocale : this.validLocale;
        return object;
    }

    public String[] getMonths() {
        return this.duplicate(this.months);
    }

    public String[] getMonths(int n, int n2) {
        String[] arrstring;
        block3 : {
            block7 : {
                block8 : {
                    block9 : {
                        block1 : {
                            block4 : {
                                block5 : {
                                    block6 : {
                                        String[] arrstring2;
                                        block2 : {
                                            arrstring2 = null;
                                            arrstring = null;
                                            if (n == 0) break block1;
                                            if (n == 1) break block2;
                                            arrstring = arrstring2;
                                            break block3;
                                        }
                                        if (n2 == 0) break block4;
                                        if (n2 == 1) break block5;
                                        if (n2 == 2) break block6;
                                        if (n2 == 3) break block4;
                                        arrstring = arrstring2;
                                        break block3;
                                    }
                                    arrstring = this.standaloneNarrowMonths;
                                    break block3;
                                }
                                arrstring = this.standaloneMonths;
                                break block3;
                            }
                            arrstring = this.standaloneShortMonths;
                            break block3;
                        }
                        if (n2 == 0) break block7;
                        if (n2 == 1) break block8;
                        if (n2 == 2) break block9;
                        if (n2 == 3) break block7;
                        break block3;
                    }
                    arrstring = this.narrowMonths;
                    break block3;
                }
                arrstring = this.months;
                break block3;
            }
            arrstring = this.shortMonths;
        }
        if (arrstring != null) {
            return this.duplicate(arrstring);
        }
        throw new IllegalArgumentException("Bad context or width argument");
    }

    @Deprecated
    public String[] getNarrowEras() {
        return this.duplicate(this.narrowEras);
    }

    public String[] getQuarters(int n, int n2) {
        String[] arrstring;
        block3 : {
            block7 : {
                block8 : {
                    block9 : {
                        block1 : {
                            block4 : {
                                block5 : {
                                    block6 : {
                                        String[] arrstring2;
                                        block2 : {
                                            arrstring2 = null;
                                            arrstring = null;
                                            if (n == 0) break block1;
                                            if (n == 1) break block2;
                                            arrstring = arrstring2;
                                            break block3;
                                        }
                                        if (n2 == 0) break block4;
                                        if (n2 == 1) break block5;
                                        if (n2 == 2) break block6;
                                        if (n2 == 3) break block4;
                                        arrstring = arrstring2;
                                        break block3;
                                    }
                                    arrstring = null;
                                    break block3;
                                }
                                arrstring = this.standaloneQuarters;
                                break block3;
                            }
                            arrstring = this.standaloneShortQuarters;
                            break block3;
                        }
                        if (n2 == 0) break block7;
                        if (n2 == 1) break block8;
                        if (n2 == 2) break block9;
                        if (n2 == 3) break block7;
                        break block3;
                    }
                    arrstring = null;
                    break block3;
                }
                arrstring = this.quarters;
                break block3;
            }
            arrstring = this.shortQuarters;
        }
        if (arrstring != null) {
            return this.duplicate(arrstring);
        }
        throw new IllegalArgumentException("Bad context or width argument");
    }

    public String[] getShortMonths() {
        return this.duplicate(this.shortMonths);
    }

    public String[] getShortWeekdays() {
        return this.duplicate(this.shortWeekdays);
    }

    @Deprecated
    public String getTimeSeparatorString() {
        return this.timeSeparator;
    }

    public String[] getWeekdays() {
        return this.duplicate(this.weekdays);
    }

    public String[] getWeekdays(int n, int n2) {
        String[] arrstring = null;
        String[] arrstring2 = null;
        if (n != 0) {
            if (n != 1) {
                arrstring2 = arrstring;
            } else if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 != 2) {
                        if (n2 != 3) {
                            arrstring2 = arrstring;
                        } else {
                            arrstring2 = this.standaloneShorterWeekdays;
                            if (arrstring2 == null) {
                                arrstring2 = this.standaloneShortWeekdays;
                            }
                        }
                    } else {
                        arrstring2 = this.standaloneNarrowWeekdays;
                    }
                } else {
                    arrstring2 = this.standaloneWeekdays;
                }
            } else {
                arrstring2 = this.standaloneShortWeekdays;
            }
        } else if (n2 != 0) {
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 == 3 && (arrstring2 = this.shorterWeekdays) == null) {
                        arrstring2 = this.shortWeekdays;
                    }
                } else {
                    arrstring2 = this.narrowWeekdays;
                }
            } else {
                arrstring2 = this.weekdays;
            }
        } else {
            arrstring2 = this.shortWeekdays;
        }
        if (arrstring2 != null) {
            return this.duplicate(arrstring2);
        }
        throw new IllegalArgumentException("Bad context or width argument");
    }

    public String[] getYearNames(int n, int n2) {
        String[] arrstring = this.shortYearNames;
        if (arrstring != null) {
            return this.duplicate(arrstring);
        }
        return null;
    }

    public String[] getZodiacNames(int n, int n2) {
        String[] arrstring = this.shortZodiacNames;
        if (arrstring != null) {
            return this.duplicate(arrstring);
        }
        return null;
    }

    public String[][] getZoneStrings() {
        Object object = this.zoneStrings;
        if (object != null) {
            return this.duplicate((String[][])object);
        }
        String[] arrstring = TimeZone.getAvailableIDs();
        TimeZoneNames timeZoneNames = TimeZoneNames.getInstance(this.validLocale);
        timeZoneNames.loadAllDisplayNames();
        TimeZoneNames.NameType nameType = TimeZoneNames.NameType.LONG_STANDARD;
        TimeZoneNames.NameType nameType2 = TimeZoneNames.NameType.SHORT_STANDARD;
        TimeZoneNames.NameType nameType3 = TimeZoneNames.NameType.LONG_DAYLIGHT;
        TimeZoneNames.NameType nameType4 = TimeZoneNames.NameType.SHORT_DAYLIGHT;
        long l = System.currentTimeMillis();
        String[][] arrstring2 = new String[arrstring.length][5];
        for (int i = 0; i < arrstring.length; ++i) {
            object = TimeZone.getCanonicalID(arrstring[i]);
            if (object == null) {
                object = arrstring[i];
            }
            arrstring2[i][0] = arrstring[i];
            String[] arrstring3 = arrstring2[i];
            timeZoneNames.getDisplayNames((String)object, new TimeZoneNames.NameType[]{nameType, nameType2, nameType3, nameType4}, l, arrstring3, 1);
        }
        this.zoneStrings = arrstring2;
        return this.zoneStrings;
    }

    public int hashCode() {
        return this.requestedLocale.toString().hashCode();
    }

    void initializeData(DateFormatSymbols dateFormatSymbols) {
        this.eras = dateFormatSymbols.eras;
        this.eraNames = dateFormatSymbols.eraNames;
        this.narrowEras = dateFormatSymbols.narrowEras;
        this.months = dateFormatSymbols.months;
        this.shortMonths = dateFormatSymbols.shortMonths;
        this.narrowMonths = dateFormatSymbols.narrowMonths;
        this.standaloneMonths = dateFormatSymbols.standaloneMonths;
        this.standaloneShortMonths = dateFormatSymbols.standaloneShortMonths;
        this.standaloneNarrowMonths = dateFormatSymbols.standaloneNarrowMonths;
        this.weekdays = dateFormatSymbols.weekdays;
        this.shortWeekdays = dateFormatSymbols.shortWeekdays;
        this.shorterWeekdays = dateFormatSymbols.shorterWeekdays;
        this.narrowWeekdays = dateFormatSymbols.narrowWeekdays;
        this.standaloneWeekdays = dateFormatSymbols.standaloneWeekdays;
        this.standaloneShortWeekdays = dateFormatSymbols.standaloneShortWeekdays;
        this.standaloneShorterWeekdays = dateFormatSymbols.standaloneShorterWeekdays;
        this.standaloneNarrowWeekdays = dateFormatSymbols.standaloneNarrowWeekdays;
        this.ampms = dateFormatSymbols.ampms;
        this.ampmsNarrow = dateFormatSymbols.ampmsNarrow;
        this.timeSeparator = dateFormatSymbols.timeSeparator;
        this.shortQuarters = dateFormatSymbols.shortQuarters;
        this.quarters = dateFormatSymbols.quarters;
        this.standaloneShortQuarters = dateFormatSymbols.standaloneShortQuarters;
        this.standaloneQuarters = dateFormatSymbols.standaloneQuarters;
        this.leapMonthPatterns = dateFormatSymbols.leapMonthPatterns;
        this.shortYearNames = dateFormatSymbols.shortYearNames;
        this.shortZodiacNames = dateFormatSymbols.shortZodiacNames;
        this.abbreviatedDayPeriods = dateFormatSymbols.abbreviatedDayPeriods;
        this.wideDayPeriods = dateFormatSymbols.wideDayPeriods;
        this.narrowDayPeriods = dateFormatSymbols.narrowDayPeriods;
        this.standaloneAbbreviatedDayPeriods = dateFormatSymbols.standaloneAbbreviatedDayPeriods;
        this.standaloneWideDayPeriods = dateFormatSymbols.standaloneWideDayPeriods;
        this.standaloneNarrowDayPeriods = dateFormatSymbols.standaloneNarrowDayPeriods;
        this.zoneStrings = dateFormatSymbols.zoneStrings;
        this.localPatternChars = dateFormatSymbols.localPatternChars;
        this.capitalization = dateFormatSymbols.capitalization;
        this.actualLocale = dateFormatSymbols.actualLocale;
        this.validLocale = dateFormatSymbols.validLocale;
        this.requestedLocale = dateFormatSymbols.requestedLocale;
    }

    @Deprecated
    protected void initializeData(ULocale object, ICUResourceBundle object2, String arrstring) {
        String[] arrstring2;
        Object object3 = new CalendarDataSink();
        if (object2 == null) {
            arrstring2 = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (ULocale)object);
            object2 = arrstring;
            arrstring = arrstring2;
        } else {
            arrstring2 = object2;
            object2 = arrstring;
            arrstring = arrstring2;
        }
        while (object2 != null) {
            arrstring2 = new StringBuilder();
            arrstring2.append("calendar/");
            arrstring2.append((String)object2);
            arrstring2 = ((ICUResourceBundle)arrstring).findWithFallback(arrstring2.toString());
            if (arrstring2 == null) {
                if (!"gregorian".equals(object2)) {
                    object2 = "gregorian";
                    ((CalendarDataSink)object3).visitAllResources();
                    continue;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("The 'gregorian' calendar type wasn't found for the locale: ");
                ((StringBuilder)object2).append(((ULocale)object).getBaseName());
                throw new MissingResourceException(((StringBuilder)object2).toString(), this.getClass().getName(), "gregorian");
            }
            ((CalendarDataSink)object3).preEnumerate((String)object2);
            arrstring2.getAllItemsWithFallback("", (UResource.Sink)object3);
            if (((String)object2).equals("gregorian")) break;
            arrstring2 = ((CalendarDataSink)object3).nextCalendarType;
            object2 = arrstring2;
            if (arrstring2 != null) continue;
            object2 = "gregorian";
            ((CalendarDataSink)object3).visitAllResources();
        }
        Map<String, String[]> object42 = ((CalendarDataSink)object3).arrays;
        object3 = ((CalendarDataSink)object3).maps;
        this.eras = object42.get("eras/abbreviated");
        this.eraNames = object42.get("eras/wide");
        this.narrowEras = object42.get("eras/narrow");
        this.months = object42.get("monthNames/format/wide");
        this.shortMonths = object42.get("monthNames/format/abbreviated");
        this.narrowMonths = object42.get("monthNames/format/narrow");
        this.standaloneMonths = object42.get("monthNames/stand-alone/wide");
        this.standaloneShortMonths = object42.get("monthNames/stand-alone/abbreviated");
        this.standaloneNarrowMonths = object42.get("monthNames/stand-alone/narrow");
        object2 = object42.get("dayNames/format/wide");
        this.weekdays = new String[8];
        arrstring = this.weekdays;
        arrstring[0] = "";
        System.arraycopy(object2, 0, arrstring, 1, ((Object)object2).length);
        object2 = object42.get("dayNames/format/abbreviated");
        this.shortWeekdays = new String[8];
        arrstring = this.shortWeekdays;
        arrstring[0] = "";
        System.arraycopy(object2, 0, arrstring, 1, ((Object)object2).length);
        arrstring = object42.get("dayNames/format/short");
        this.shorterWeekdays = new String[8];
        object2 = this.shorterWeekdays;
        object2[0] = "";
        System.arraycopy(arrstring, 0, object2, 1, ((Object)arrstring).length);
        object2 = arrstring = object42.get("dayNames/format/narrow");
        if (arrstring == null) {
            object2 = arrstring = object42.get("dayNames/stand-alone/narrow");
            if (arrstring == null && (object2 = object42.get("dayNames/format/abbreviated")) == null) {
                throw new MissingResourceException("Resource not found", this.getClass().getName(), "dayNames/format/abbreviated");
            }
        }
        this.narrowWeekdays = new String[8];
        arrstring = this.narrowWeekdays;
        arrstring[0] = "";
        System.arraycopy(object2, 0, arrstring, 1, ((Object)object2).length);
        arrstring = object42.get("dayNames/stand-alone/wide");
        this.standaloneWeekdays = new String[8];
        object2 = this.standaloneWeekdays;
        object2[0] = "";
        System.arraycopy(arrstring, 0, object2, 1, ((Object)arrstring).length);
        arrstring = object42.get("dayNames/stand-alone/abbreviated");
        this.standaloneShortWeekdays = new String[8];
        object2 = this.standaloneShortWeekdays;
        object2[0] = "";
        System.arraycopy(arrstring, 0, object2, 1, ((Object)arrstring).length);
        arrstring2 = object42.get("dayNames/stand-alone/short");
        this.standaloneShorterWeekdays = new String[8];
        object2 = this.standaloneShorterWeekdays;
        object2[0] = "";
        System.arraycopy(arrstring2, 0, object2, 1, arrstring2.length);
        arrstring = object42.get("dayNames/stand-alone/narrow");
        this.standaloneNarrowWeekdays = new String[8];
        object2 = this.standaloneNarrowWeekdays;
        object2[0] = "";
        System.arraycopy(arrstring, 0, object2, 1, ((Object)arrstring).length);
        this.ampms = object42.get("AmPmMarkers");
        this.ampmsNarrow = object42.get("AmPmMarkersNarrow");
        this.quarters = object42.get("quarters/format/wide");
        this.shortQuarters = object42.get("quarters/format/abbreviated");
        this.standaloneQuarters = object42.get("quarters/stand-alone/wide");
        this.standaloneShortQuarters = object42.get("quarters/stand-alone/abbreviated");
        this.abbreviatedDayPeriods = this.loadDayPeriodStrings((Map)object3.get("dayPeriod/format/abbreviated"));
        this.wideDayPeriods = this.loadDayPeriodStrings((Map)object3.get("dayPeriod/format/wide"));
        this.narrowDayPeriods = this.loadDayPeriodStrings((Map)object3.get("dayPeriod/format/narrow"));
        this.standaloneAbbreviatedDayPeriods = this.loadDayPeriodStrings((Map)object3.get("dayPeriod/stand-alone/abbreviated"));
        this.standaloneWideDayPeriods = this.loadDayPeriodStrings((Map)object3.get("dayPeriod/stand-alone/wide"));
        this.standaloneNarrowDayPeriods = this.loadDayPeriodStrings((Map)object3.get("dayPeriod/stand-alone/narrow"));
        for (int i = 0; i < 7; ++i) {
            object2 = LEAP_MONTH_PATTERNS_PATHS[i];
            if (object2 == null || (object2 = (Map)object3.get(object2)) == null || (object2 = (String)object2.get("leap")) == null) continue;
            if (this.leapMonthPatterns == null) {
                this.leapMonthPatterns = new String[7];
            }
            this.leapMonthPatterns[i] = object2;
        }
        this.shortYearNames = object42.get("cyclicNameSets/years/format/abbreviated");
        this.shortZodiacNames = object42.get("cyclicNameSets/zodiacs/format/abbreviated");
        this.requestedLocale = object;
        object3 = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (ULocale)object);
        this.localPatternChars = patternChars;
        object2 = ((ICUResourceBundle)object3).getULocale();
        this.setLocale((ULocale)object2, (ULocale)object2);
        this.capitalization = new HashMap<CapitalizationContextUsage, boolean[]>();
        for (CapitalizationContextUsage capitalizationContextUsage : CapitalizationContextUsage.values()) {
            this.capitalization.put(capitalizationContextUsage, new boolean[]{false, false});
        }
        try {
            object2 = ((ICUResourceBundle)object3).getWithFallback("contextTransforms");
        }
        catch (MissingResourceException missingResourceException) {
            object2 = null;
        }
        if (object2 != null) {
            UResourceBundleIterator uResourceBundleIterator = ((UResourceBundle)object2).getIterator();
            while (uResourceBundleIterator.hasNext()) {
                Object object4 = uResourceBundleIterator.next();
                int[] arrn = ((UResourceBundle)object4).getIntVector();
                if (arrn.length < 2) continue;
                object4 = ((UResourceBundle)object4).getKey();
                if ((object4 = contextUsageTypeMap.get(object4)) == null) continue;
                boolean bl = arrn[0] != 0;
                boolean bl2 = arrn[1] != 0;
                this.capitalization.put((CapitalizationContextUsage)((Object)object4), new boolean[]{bl, bl2});
            }
        }
        object = (object = NumberingSystem.getInstance((ULocale)object)) == null ? "latn" : ((NumberingSystem)object).getName();
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("NumberElements/");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append("/symbols/timeSeparator");
        object = ((StringBuilder)object2).toString();
        try {
            this.setTimeSeparatorString(((ICUResourceBundle)object3).getStringWithFallback((String)object));
        }
        catch (MissingResourceException missingResourceException) {
            this.setTimeSeparatorString(DEFAULT_TIME_SEPARATOR);
        }
    }

    protected void initializeData(ULocale uLocale, String charSequence) {
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append(uLocale.getBaseName());
        charSequence2.append('+');
        charSequence2.append((String)charSequence);
        charSequence2 = charSequence2.toString();
        String string = uLocale.getKeywordValue("numbers");
        charSequence = charSequence2;
        if (string != null) {
            charSequence = charSequence2;
            if (string.length() > 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append('+');
                ((StringBuilder)charSequence).append(string);
                charSequence = ((StringBuilder)charSequence).toString();
            }
        }
        this.initializeData(DFSCACHE.getInstance((String)charSequence, uLocale));
    }

    public void setAmPmStrings(String[] arrstring) {
        this.ampms = this.duplicate(arrstring);
    }

    public void setEraNames(String[] arrstring) {
        this.eraNames = this.duplicate(arrstring);
    }

    public void setEras(String[] arrstring) {
        this.eras = this.duplicate(arrstring);
    }

    @Deprecated
    public void setLeapMonthPattern(String string, int n, int n2) {
        if (this.leapMonthPatterns != null) {
            int n3 = -1;
            n = n != 0 ? (n != 1 ? (n != 2 ? n3 : 6) : (n2 != 0 ? (n2 != 1 ? (n2 != 2 ? n3 : 5) : 3) : 1)) : (n2 != 0 ? (n2 != 1 ? (n2 != 2 ? n3 : 2) : 0) : 1);
            if (n >= 0) {
                this.leapMonthPatterns[n] = string;
            }
        }
    }

    public void setLocalPatternChars(String string) {
        this.localPatternChars = string;
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

    public void setMonths(String[] arrstring) {
        this.months = this.duplicate(arrstring);
    }

    public void setMonths(String[] arrstring, int n, int n2) {
        if (n != 0) {
            if (n == 1) {
                if (n2 != 0) {
                    if (n2 != 1) {
                        if (n2 == 2) {
                            this.standaloneNarrowMonths = this.duplicate(arrstring);
                        }
                    } else {
                        this.standaloneMonths = this.duplicate(arrstring);
                    }
                } else {
                    this.standaloneShortMonths = this.duplicate(arrstring);
                }
            }
        } else if (n2 != 0) {
            if (n2 != 1) {
                if (n2 == 2) {
                    this.narrowMonths = this.duplicate(arrstring);
                }
            } else {
                this.months = this.duplicate(arrstring);
            }
        } else {
            this.shortMonths = this.duplicate(arrstring);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setQuarters(String[] arrstring, int n, int n2) {
        if (n != 0) {
            if (n != 1) return;
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 == 2) return;
                }
                this.standaloneQuarters = this.duplicate(arrstring);
                return;
            } else {
                this.standaloneShortQuarters = this.duplicate(arrstring);
            }
            return;
        } else if (n2 != 0) {
            if (n2 != 1) {
                if (n2 == 2) return;
            }
            this.quarters = this.duplicate(arrstring);
            return;
        } else {
            this.shortQuarters = this.duplicate(arrstring);
        }
    }

    public void setShortMonths(String[] arrstring) {
        this.shortMonths = this.duplicate(arrstring);
    }

    public void setShortWeekdays(String[] arrstring) {
        this.shortWeekdays = this.duplicate(arrstring);
    }

    @Deprecated
    public void setTimeSeparatorString(String string) {
        this.timeSeparator = string;
    }

    public void setWeekdays(String[] arrstring) {
        this.weekdays = this.duplicate(arrstring);
    }

    public void setWeekdays(String[] arrstring, int n, int n2) {
        if (n != 0) {
            if (n == 1) {
                if (n2 != 0) {
                    if (n2 != 1) {
                        if (n2 != 2) {
                            if (n2 == 3) {
                                this.standaloneShorterWeekdays = this.duplicate(arrstring);
                            }
                        } else {
                            this.standaloneNarrowWeekdays = this.duplicate(arrstring);
                        }
                    } else {
                        this.standaloneWeekdays = this.duplicate(arrstring);
                    }
                } else {
                    this.standaloneShortWeekdays = this.duplicate(arrstring);
                }
            }
        } else if (n2 != 0) {
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 == 3) {
                        this.shorterWeekdays = this.duplicate(arrstring);
                    }
                } else {
                    this.narrowWeekdays = this.duplicate(arrstring);
                }
            } else {
                this.weekdays = this.duplicate(arrstring);
            }
        } else {
            this.shortWeekdays = this.duplicate(arrstring);
        }
    }

    public void setYearNames(String[] arrstring, int n, int n2) {
        if (n == 0 && n2 == 0) {
            this.shortYearNames = this.duplicate(arrstring);
        }
    }

    public void setZodiacNames(String[] arrstring, int n, int n2) {
        if (n == 0 && n2 == 0) {
            this.shortZodiacNames = this.duplicate(arrstring);
        }
    }

    public void setZoneStrings(String[][] arrstring) {
        this.zoneStrings = this.duplicate(arrstring);
    }

    private static final class CalendarDataSink
    extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final String CALENDAR_ALIAS_PREFIX = "/LOCALE/calendar/";
        List<String> aliasPathPairs = new ArrayList<String>();
        private String aliasRelativePath;
        Map<String, String[]> arrays = new TreeMap<String, String[]>();
        String currentCalendarType = null;
        Map<String, Map<String, String>> maps = new TreeMap<String, Map<String, String>>();
        String nextCalendarType = null;
        private Set<String> resourcesToVisit;

        CalendarDataSink() {
        }

        private AliasType processAliasFromValue(String charSequence, UResource.Value object) {
            if (((UResource.Value)object).getType() == 3) {
                int n;
                if (((String)(object = ((UResource.Value)object).getAliasString())).startsWith(CALENDAR_ALIAS_PREFIX) && ((String)object).length() > CALENDAR_ALIAS_PREFIX.length() && (n = ((String)object).indexOf(47, CALENDAR_ALIAS_PREFIX.length())) > CALENDAR_ALIAS_PREFIX.length()) {
                    String string = ((String)object).substring(CALENDAR_ALIAS_PREFIX.length(), n);
                    this.aliasRelativePath = ((String)object).substring(n + 1);
                    if (this.currentCalendarType.equals(string) && !((String)charSequence).equals(this.aliasRelativePath)) {
                        return AliasType.SAME_CALENDAR;
                    }
                    if (!this.currentCalendarType.equals(string) && ((String)charSequence).equals(this.aliasRelativePath)) {
                        if (string.equals("gregorian")) {
                            return AliasType.GREGORIAN;
                        }
                        charSequence = this.nextCalendarType;
                        if (charSequence == null || ((String)charSequence).equals(string)) {
                            this.nextCalendarType = string;
                            return AliasType.DIFFERENT_CALENDAR;
                        }
                    }
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Malformed 'calendar' alias. Path: ");
                ((StringBuilder)charSequence).append((String)object);
                throw new ICUException(((StringBuilder)charSequence).toString());
            }
            return AliasType.NONE;
        }

        void preEnumerate(String string) {
            this.currentCalendarType = string;
            this.nextCalendarType = null;
            this.aliasPathPairs.clear();
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        protected void processResource(String var1_1, UResource.Key var2_2, UResource.Value var3_3) {
            var4_4 = var3_3.getTable();
            var5_5 = null;
            var6_6 = 0;
            while (var4_4.getKeyAndValue(var6_6, var2_2, var3_3) != false) {
                block5 : {
                    block10 : {
                        block9 : {
                            block8 : {
                                block7 : {
                                    block6 : {
                                        block4 : {
                                            if (!var2_2.endsWith("%variant")) break block4;
                                            var7_8 = var5_5;
                                            break block5;
                                        }
                                        var7_9 = var2_2.toString();
                                        if (var3_3.getType() != 0) break block6;
                                        if (var6_6 == 0) {
                                            var5_5 = new HashMap<String, String>();
                                            this.maps.put(var1_1, var5_5);
                                        }
                                        var5_5.put(var7_9, var3_3.getString());
                                        var7_10 = var5_5;
                                        break block5;
                                    }
                                    var8_19 = new StringBuilder();
                                    var8_19.append(var1_1);
                                    var8_19.append("/");
                                    var8_19.append(var7_9);
                                    var8_19 = var8_19.toString();
                                    if (!var8_19.startsWith("cyclicNameSets") || "cyclicNameSets/years/format/abbreviated".startsWith((String)var8_19) || "cyclicNameSets/zodiacs/format/abbreviated".startsWith((String)var8_19) || "cyclicNameSets/dayParts/format/abbreviated".startsWith((String)var8_19)) break block7;
                                    var7_11 = var5_5;
                                    break block5;
                                }
                                var7_12 = var5_5;
                                if (this.arrays.containsKey(var8_19)) break block5;
                                if (!this.maps.containsKey(var8_19)) break block8;
                                var7_13 = var5_5;
                                break block5;
                            }
                            if (this.processAliasFromValue((String)var8_19, var3_3) != AliasType.SAME_CALENDAR) break block9;
                            this.aliasPathPairs.add(this.aliasRelativePath);
                            this.aliasPathPairs.add((String)var8_19);
                            var7_14 = var5_5;
                            break block5;
                        }
                        if (var3_3.getType() != 8) break block10;
                        var7_15 = var3_3.getStringArray();
                        this.arrays.put((String)var8_19, var7_15);
                        ** GOTO lbl-1000
                    }
                    if (var3_3.getType() == 2) {
                        this.processResource((String)var8_19, var2_2, var3_3);
                        var7_16 = var5_5;
                    } else lbl-1000: // 2 sources:
                    {
                        var7_18 = var5_5;
                    }
                }
                ++var6_6;
                var5_5 = var7_7;
            }
        }

        @Override
        public void put(UResource.Key charSequence, UResource.Value value, boolean bl) {
            boolean bl2;
            AliasType aliasType = null;
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, (UResource.Key)charSequence, value)) {
                Object object;
                block18 : {
                    String string;
                    block23 : {
                        block22 : {
                            block21 : {
                                block20 : {
                                    block19 : {
                                        block17 : {
                                            string = charSequence.toString();
                                            object = this.processAliasFromValue(string, value);
                                            if (object != AliasType.GREGORIAN) break block17;
                                            object = aliasType;
                                            break block18;
                                        }
                                        if (object != AliasType.DIFFERENT_CALENDAR) break block19;
                                        object = aliasType;
                                        if (aliasType == null) {
                                            object = new HashSet();
                                        }
                                        object.add(this.aliasRelativePath);
                                        break block18;
                                    }
                                    if (object != AliasType.SAME_CALENDAR) break block20;
                                    object = aliasType;
                                    if (!this.arrays.containsKey(string)) {
                                        object = aliasType;
                                        if (!this.maps.containsKey(string)) {
                                            this.aliasPathPairs.add(this.aliasRelativePath);
                                            this.aliasPathPairs.add(string);
                                            object = aliasType;
                                        }
                                    }
                                    break block18;
                                }
                                object = this.resourcesToVisit;
                                if (object == null || object.isEmpty() || this.resourcesToVisit.contains(string) || string.equals("AmPmMarkersAbbr")) break block21;
                                object = aliasType;
                                break block18;
                            }
                            if (!string.startsWith("AmPmMarkers")) break block22;
                            object = aliasType;
                            if (!string.endsWith("%variant")) {
                                object = aliasType;
                                if (!this.arrays.containsKey(string)) {
                                    object = value.getStringArray();
                                    this.arrays.put(string, (String[])object);
                                    object = aliasType;
                                }
                            }
                            break block18;
                        }
                        if (string.equals("eras") || string.equals("dayNames") || string.equals("monthNames") || string.equals("quarters") || string.equals("dayPeriod") || string.equals("monthPatterns")) break block23;
                        object = aliasType;
                        if (!string.equals("cyclicNameSets")) break block18;
                    }
                    this.processResource(string, (UResource.Key)charSequence, value);
                    object = aliasType;
                }
                ++n;
                aliasType = object;
            }
            do {
                bl2 = false;
                int n2 = 0;
                while (n2 < this.aliasPathPairs.size()) {
                    n = 0;
                    charSequence = this.aliasPathPairs.get(n2);
                    if (this.arrays.containsKey(charSequence)) {
                        this.arrays.put(this.aliasPathPairs.get(n2 + 1), this.arrays.get(charSequence));
                        n = 1;
                    } else if (this.maps.containsKey(charSequence)) {
                        this.maps.put(this.aliasPathPairs.get(n2 + 1), this.maps.get(charSequence));
                        n = 1;
                    }
                    if (n != 0) {
                        this.aliasPathPairs.remove(n2 + 1);
                        this.aliasPathPairs.remove(n2);
                        bl2 = true;
                        continue;
                    }
                    n2 += 2;
                }
            } while (bl2 && !this.aliasPathPairs.isEmpty());
            if (aliasType != null) {
                this.resourcesToVisit = aliasType;
            }
        }

        void visitAllResources() {
            this.resourcesToVisit = null;
        }

        private static enum AliasType {
            SAME_CALENDAR,
            DIFFERENT_CALENDAR,
            GREGORIAN,
            NONE;
            
        }

    }

    static enum CapitalizationContextUsage {
        OTHER,
        MONTH_FORMAT,
        MONTH_STANDALONE,
        MONTH_NARROW,
        DAY_FORMAT,
        DAY_STANDALONE,
        DAY_NARROW,
        ERA_WIDE,
        ERA_ABBREV,
        ERA_NARROW,
        ZONE_LONG,
        ZONE_SHORT,
        METAZONE_LONG,
        METAZONE_SHORT;
        
    }

}

