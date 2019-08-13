/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.DateNumberFormat;
import android.icu.impl.DayPeriodRules;
import android.icu.impl.ICUCache;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.PatternProps;
import android.icu.impl.SimpleCache;
import android.icu.impl.SimpleFormatterImpl;
import android.icu.lang.UCharacter;
import android.icu.text.BreakIterator;
import android.icu.text.DateFormat;
import android.icu.text.DateFormatSymbols;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.DisplayContext;
import android.icu.text.MessageFormat;
import android.icu.text.NumberFormat;
import android.icu.text.NumberingSystem;
import android.icu.text.TimeZoneFormat;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import android.icu.util.BasicTimeZone;
import android.icu.util.Calendar;
import android.icu.util.HebrewCalendar;
import android.icu.util.Output;
import android.icu.util.TimeZone;
import android.icu.util.TimeZoneRule;
import android.icu.util.TimeZoneTransition;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.UUID;

public class SimpleDateFormat
extends DateFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int[] CALENDAR_FIELD_TO_LEVEL;
    static final UnicodeSet DATE_PATTERN_TYPE;
    private static final int DECIMAL_BUF_SIZE = 10;
    static boolean DelayedHebrewMonthCheck = false;
    private static final String FALLBACKPATTERN = "yy/MM/dd HH:mm";
    private static final int HEBREW_CAL_CUR_MILLENIUM_END_YEAR = 6000;
    private static final int HEBREW_CAL_CUR_MILLENIUM_START_YEAR = 5000;
    private static final int ISOSpecialEra = -32000;
    private static final String NUMERIC_FORMAT_CHARS = "ADdFgHhKkmrSsuWwYy";
    private static final String NUMERIC_FORMAT_CHARS2 = "ceLMQq";
    private static ICUCache<String, Object[]> PARSED_PATTERN_CACHE;
    private static final boolean[] PATTERN_CHAR_IS_SYNTAX;
    private static final int[] PATTERN_CHAR_TO_INDEX;
    private static final int[] PATTERN_CHAR_TO_LEVEL;
    private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD;
    private static final DateFormat.Field[] PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE;
    private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD;
    private static final String SUPPRESS_NEGATIVE_PREFIX = "\uab00";
    private static ULocale cachedDefaultLocale;
    private static String cachedDefaultPattern;
    static final int currentSerialVersion = 2;
    private static final int millisPerHour = 3600000;
    private static final long serialVersionUID = 4774881970558875024L;
    private transient BreakIterator capitalizationBrkIter = null;
    private transient char[] decDigits;
    private transient char[] decimalBuf;
    private transient long defaultCenturyBase;
    private Date defaultCenturyStart;
    private transient int defaultCenturyStartYear;
    private DateFormatSymbols formatData;
    private transient boolean hasMinute;
    private transient boolean hasSecond;
    private transient ULocale locale;
    private HashMap<String, NumberFormat> numberFormatters;
    private String override;
    private HashMap<Character, String> overrideMap;
    private String pattern;
    private transient Object[] patternItems;
    private int serialVersionOnStream = 2;
    private volatile TimeZoneFormat tzFormat;
    private transient boolean useFastFormat;
    private transient boolean useLocalZeroPaddingNumberFormat;

    static {
        DelayedHebrewMonthCheck = false;
        CALENDAR_FIELD_TO_LEVEL = new int[]{0, 10, 20, 20, 30, 30, 20, 30, 30, 40, 50, 50, 60, 70, 80, 0, 0, 10, 30, 10, 0, 40, 0, 0};
        PATTERN_CHAR_TO_LEVEL = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, 20, 30, 30, 0, 50, -1, -1, 50, 20, 20, -1, 0, -1, 20, -1, 80, -1, 10, 0, 30, 0, 10, 0, -1, -1, -1, -1, -1, -1, 40, -1, 30, 30, 30, -1, 0, 50, -1, -1, 50, -1, 60, -1, -1, -1, 20, 10, 70, -1, 10, 0, 20, 0, 10, 0, -1, -1, -1, -1, -1};
        PATTERN_CHAR_IS_SYNTAX = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false};
        cachedDefaultLocale = null;
        cachedDefaultPattern = null;
        PATTERN_CHAR_TO_INDEX = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 22, 36, -1, 10, 9, 11, 0, 5, -1, -1, 16, 26, 2, -1, 31, -1, 27, -1, 8, -1, 30, 29, 13, 32, 18, 23, -1, -1, -1, -1, -1, -1, 14, 35, 25, 3, 19, -1, 21, 15, -1, -1, 4, -1, 6, -1, -1, -1, 28, 34, 7, -1, 20, 24, 12, 33, 1, 17, -1, -1, -1, -1, -1};
        PATTERN_INDEX_TO_CALENDAR_FIELD = new int[]{0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15, 17, 18, 19, 20, 21, 15, 15, 18, 2, 2, 2, 15, 1, 15, 15, 15, 19, -1, -2};
        PATTERN_INDEX_TO_DATE_FORMAT_FIELD = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37};
        PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE = new DateFormat.Field[]{DateFormat.Field.ERA, DateFormat.Field.YEAR, DateFormat.Field.MONTH, DateFormat.Field.DAY_OF_MONTH, DateFormat.Field.HOUR_OF_DAY1, DateFormat.Field.HOUR_OF_DAY0, DateFormat.Field.MINUTE, DateFormat.Field.SECOND, DateFormat.Field.MILLISECOND, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.DAY_OF_YEAR, DateFormat.Field.DAY_OF_WEEK_IN_MONTH, DateFormat.Field.WEEK_OF_YEAR, DateFormat.Field.WEEK_OF_MONTH, DateFormat.Field.AM_PM, DateFormat.Field.HOUR1, DateFormat.Field.HOUR0, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR_WOY, DateFormat.Field.DOW_LOCAL, DateFormat.Field.EXTENDED_YEAR, DateFormat.Field.JULIAN_DAY, DateFormat.Field.MILLISECONDS_IN_DAY, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.MONTH, DateFormat.Field.QUARTER, DateFormat.Field.QUARTER, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.RELATED_YEAR, DateFormat.Field.AM_PM_MIDNIGHT_NOON, DateFormat.Field.FLEXIBLE_DAY_PERIOD, DateFormat.Field.TIME_SEPARATOR};
        PARSED_PATTERN_CACHE = new SimpleCache<String, Object[]>();
        DATE_PATTERN_TYPE = new UnicodeSet("[GyYuUQqMLlwWd]").freeze();
    }

    public SimpleDateFormat() {
        this(SimpleDateFormat.getDefaultPattern(), null, null, null, null, true, null);
    }

    public SimpleDateFormat(String string) {
        this(string, null, null, null, null, true, null);
    }

    public SimpleDateFormat(String string, DateFormatSymbols dateFormatSymbols) {
        this(string, (DateFormatSymbols)dateFormatSymbols.clone(), null, null, null, true, null);
    }

    private SimpleDateFormat(String string, DateFormatSymbols dateFormatSymbols, Calendar calendar, NumberFormat numberFormat, ULocale uLocale, boolean bl, String string2) {
        this.pattern = string;
        this.formatData = dateFormatSymbols;
        this.calendar = calendar;
        this.numberFormat = numberFormat;
        this.locale = uLocale;
        this.useFastFormat = bl;
        this.override = string2;
        this.initialize();
    }

    SimpleDateFormat(String string, DateFormatSymbols dateFormatSymbols, Calendar calendar, ULocale uLocale, boolean bl, String string2) {
        this(string, (DateFormatSymbols)dateFormatSymbols.clone(), (Calendar)calendar.clone(), null, uLocale, bl, string2);
    }

    @Deprecated
    public SimpleDateFormat(String string, DateFormatSymbols dateFormatSymbols, ULocale uLocale) {
        this(string, (DateFormatSymbols)dateFormatSymbols.clone(), null, null, uLocale, true, null);
    }

    public SimpleDateFormat(String string, ULocale uLocale) {
        this(string, null, null, null, uLocale, true, null);
    }

    public SimpleDateFormat(String string, String string2, ULocale uLocale) {
        this(string, null, null, null, uLocale, false, string2);
    }

    public SimpleDateFormat(String string, Locale locale) {
        this(string, null, null, null, ULocale.forLocale(locale), true, null);
    }

    private boolean allowNumericFallback(int n) {
        return n == 26 || n == 19 || n == 25 || n == 30 || n == 27 || n == 28;
        {
        }
    }

    private static int countDigits(String string, int n, int n2) {
        int n3 = 0;
        while (n < n2) {
            int n4 = string.codePointAt(n);
            int n5 = n3;
            if (UCharacter.isDigit(n4)) {
                n5 = n3 + 1;
            }
            n += UCharacter.charCount(n4);
            n3 = n5;
        }
        return n3;
    }

    private boolean diffCalFieldValue(Calendar serializable, Calendar calendar, Object[] arrobject, int n) throws IllegalArgumentException {
        if (arrobject[n] instanceof String) {
            return false;
        }
        char c = ((PatternItem)arrobject[n]).type;
        n = SimpleDateFormat.getIndexFromChar(c);
        if (n != -1) {
            return (n = PATTERN_INDEX_TO_CALENDAR_FIELD[n]) >= 0 && ((Calendar)serializable).get(n) != calendar.get(n);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Illegal pattern character '");
        ((StringBuilder)serializable).append(c);
        ((StringBuilder)serializable).append("' in \"");
        ((StringBuilder)serializable).append(this.pattern);
        ((StringBuilder)serializable).append('\"');
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    private void fastZeroPaddingNumber(StringBuffer stringBuffer, int n, int n2, int n3) {
        char[] arrc = this.decimalBuf;
        if (arrc.length < n3) {
            n3 = arrc.length;
        }
        int n4 = n3 - 1;
        int n5 = n;
        n = n4;
        do {
            this.decimalBuf[n] = this.decDigits[n5 % 10];
            if (n == 0 || (n5 /= 10) == 0) break;
            --n;
        } while (true);
        n2 -= n3 - n;
        do {
            n5 = n2;
            if (n2 <= 0) break;
            n5 = n2--;
            if (n <= 0) break;
            arrc = this.decimalBuf;
            arrc[--n] = this.decDigits[0];
        } while (true);
        while (n5 > 0) {
            stringBuffer.append(this.decDigits[0]);
            --n5;
        }
        stringBuffer.append(this.decimalBuf, n, n3 - n);
    }

    private StringBuffer format(Calendar calendar, DisplayContext displayContext, StringBuffer stringBuffer, FieldPosition fieldPosition, List<FieldPosition> list) {
        fieldPosition.setBeginIndex(0);
        fieldPosition.setEndIndex(0);
        Object[] arrobject = this.getPatternItems();
        for (int i = 0; i < arrobject.length; ++i) {
            int n;
            if (arrobject[i] instanceof String) {
                stringBuffer.append((String)arrobject[i]);
                continue;
            }
            Object object = (PatternItem)arrobject[i];
            int n2 = list != null ? stringBuffer.length() : 0;
            if (this.useFastFormat) {
                this.subFormat(stringBuffer, ((PatternItem)object).type, ((PatternItem)object).length, stringBuffer.length(), i, displayContext, fieldPosition, calendar);
            } else {
                stringBuffer.append(this.subFormat(((PatternItem)object).type, ((PatternItem)object).length, stringBuffer.length(), i, displayContext, fieldPosition, calendar));
            }
            if (list == null || (n = stringBuffer.length()) - n2 <= 0) continue;
            object = new FieldPosition(this.patternCharToDateFormatField(((PatternItem)object).type));
            ((FieldPosition)object).setBeginIndex(n2);
            ((FieldPosition)object).setEndIndex(n);
            list.add((FieldPosition)object);
        }
        return stringBuffer;
    }

    private Date getDefaultCenturyStart() {
        if (this.defaultCenturyStart == null) {
            this.initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        return this.defaultCenturyStart;
    }

    private int getDefaultCenturyStartYear() {
        if (this.defaultCenturyStart == null) {
            this.initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        return this.defaultCenturyStartYear;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String getDefaultPattern() {
        synchronized (SimpleDateFormat.class) {
            Object object = ULocale.getDefault(ULocale.Category.FORMAT);
            if (((ULocale)object).equals(cachedDefaultLocale)) return cachedDefaultPattern;
            cachedDefaultLocale = object;
            Object object2 = Calendar.getInstance(cachedDefaultLocale);
            try {
                ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", cachedDefaultLocale);
                object = new StringBuilder();
                ((StringBuilder)object).append("calendar/");
                ((StringBuilder)object).append(((Calendar)object2).getType());
                ((StringBuilder)object).append("/DateTimePatterns");
                object = object2 = iCUResourceBundle.findWithFallback(((StringBuilder)object).toString());
                if (object2 == null) {
                    object = iCUResourceBundle.findWithFallback("calendar/gregorian/DateTimePatterns");
                }
                if (object != null && ((UResourceBundle)object).getSize() >= 9) {
                    int n = 8;
                    if (((UResourceBundle)object).getSize() >= 13) {
                        n = 8 + 4;
                    }
                    cachedDefaultPattern = SimpleFormatterImpl.formatRawPattern(((UResourceBundle)object).getString(n), 2, 2, ((UResourceBundle)object).getString(3), ((UResourceBundle)object).getString(7));
                    return cachedDefaultPattern;
                } else {
                    cachedDefaultPattern = FALLBACKPATTERN;
                }
                return cachedDefaultPattern;
            }
            catch (MissingResourceException missingResourceException) {
                cachedDefaultPattern = FALLBACKPATTERN;
            }
            return cachedDefaultPattern;
        }
    }

    private static int getIndexFromChar(char c) {
        int[] arrn = PATTERN_CHAR_TO_INDEX;
        c = c < arrn.length ? (char)arrn[c & 255] : (char)-1;
        return c;
    }

    @Deprecated
    public static SimpleDateFormat getInstance(Calendar.FormatConfiguration formatConfiguration) {
        String string = formatConfiguration.getOverrideString();
        boolean bl = string != null && string.length() > 0;
        return new SimpleDateFormat(formatConfiguration.getPatternString(), formatConfiguration.getDateFormatSymbols(), formatConfiguration.getCalendar(), null, formatConfiguration.getLocale(), bl, formatConfiguration.getOverrideString());
    }

    private static int getLevelFromChar(char c) {
        int[] arrn = PATTERN_CHAR_TO_LEVEL;
        c = c < arrn.length ? (char)arrn[c & 255] : (char)-1;
        return c;
    }

    private Object[] getPatternItems() {
        Object object = this.patternItems;
        if (object != null) {
            return object;
        }
        this.patternItems = PARSED_PATTERN_CACHE.get(this.pattern);
        object = this.patternItems;
        if (object != null) {
            return object;
        }
        boolean bl = false;
        char c = '\u0000';
        StringBuilder stringBuilder = new StringBuilder();
        char c2 = '\u0000';
        int n = 1;
        object = new ArrayList();
        char c3 = '\u0000';
        char c4 = c2;
        do {
            char c5 = this.pattern.length();
            c2 = '\u0000';
            if (c3 >= c5) break;
            char c6 = this.pattern.charAt(c3);
            if (c6 == '\'') {
                if (bl) {
                    stringBuilder.append('\'');
                    bl = false;
                    c5 = c4;
                } else {
                    boolean bl2;
                    bl = bl2 = true;
                    c5 = c4;
                    if (c4 != '\u0000') {
                        object.add(new PatternItem(c4, n));
                        c5 = '\u0000';
                        bl = bl2;
                    }
                }
                if (c == '\u0000') {
                    c2 = '\u0001';
                }
                c = c2;
                c2 = c5;
            } else {
                bl = false;
                if (c != '\u0000') {
                    stringBuilder.append(c6);
                    c2 = c4;
                } else if (SimpleDateFormat.isSyntaxChar(c6)) {
                    if (c6 == c4) {
                        ++n;
                        c2 = c4;
                    } else {
                        if (c4 == '\u0000') {
                            if (stringBuilder.length() > 0) {
                                object.add(stringBuilder.toString());
                                stringBuilder.setLength(0);
                            }
                        } else {
                            object.add(new PatternItem(c4, n));
                        }
                        c2 = c6;
                        n = 1;
                    }
                } else {
                    c2 = c4;
                    if (c4 != '\u0000') {
                        object.add(new PatternItem(c4, n));
                        c2 = '\u0000';
                    }
                    stringBuilder.append(c6);
                }
            }
            ++c3;
            c4 = c2;
        } while (true);
        if (c4 == '\u0000') {
            if (stringBuilder.length() > 0) {
                object.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        } else {
            object.add(new PatternItem(c4, n));
        }
        this.patternItems = object.toArray(new Object[object.size()]);
        PARSED_PATTERN_CACHE.put(this.pattern, this.patternItems);
        return this.patternItems;
    }

    private void initLocalZeroPaddingNumberFormat() {
        if (this.numberFormat instanceof DecimalFormat) {
            String[] arrstring = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getDigitStringsLocal();
            this.useLocalZeroPaddingNumberFormat = true;
            this.decDigits = new char[10];
            for (int i = 0; i < 10; ++i) {
                if (arrstring[i].length() > 1) {
                    this.useLocalZeroPaddingNumberFormat = false;
                    break;
                }
                this.decDigits[i] = arrstring[i].charAt(0);
            }
        } else if (this.numberFormat instanceof DateNumberFormat) {
            this.decDigits = ((DateNumberFormat)this.numberFormat).getDigits();
            this.useLocalZeroPaddingNumberFormat = true;
        } else {
            this.useLocalZeroPaddingNumberFormat = false;
        }
        if (this.useLocalZeroPaddingNumberFormat) {
            this.decimalBuf = new char[10];
        }
    }

    private void initNumberFormatters(ULocale uLocale) {
        this.numberFormatters = new HashMap();
        this.overrideMap = new HashMap();
        this.processOverrideString(uLocale, this.override);
    }

    private void initialize() {
        if (this.locale == null) {
            this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        if (this.formatData == null) {
            this.formatData = new DateFormatSymbols(this.locale);
        }
        if (this.calendar == null) {
            this.calendar = Calendar.getInstance(this.locale);
        }
        if (this.numberFormat == null) {
            Object object = NumberingSystem.getInstance(this.locale);
            String string = ((NumberingSystem)object).getDescription();
            if (!((NumberingSystem)object).isAlgorithmic() && string.length() == 10) {
                object = ((NumberingSystem)object).getName();
                this.numberFormat = new DateNumberFormat(this.locale, string, (String)object);
            } else {
                this.numberFormat = NumberFormat.getInstance(this.locale);
            }
        }
        if (this.numberFormat instanceof DecimalFormat) {
            SimpleDateFormat.fixNumberFormatForDates(this.numberFormat);
        }
        this.defaultCenturyBase = System.currentTimeMillis();
        this.setLocale(this.calendar.getLocale(ULocale.VALID_LOCALE), this.calendar.getLocale(ULocale.ACTUAL_LOCALE));
        this.initLocalZeroPaddingNumberFormat();
        if (this.override != null) {
            this.initNumberFormatters(this.locale);
        }
        this.parsePattern();
    }

    private void initializeDefaultCenturyStart(long l) {
        this.defaultCenturyBase = l;
        Calendar calendar = (Calendar)this.calendar.clone();
        calendar.setTimeInMillis(l);
        calendar.add(1, -80);
        this.defaultCenturyStart = calendar.getTime();
        this.defaultCenturyStartYear = calendar.get(1);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void initializeTimeZoneFormat(boolean bl) {
        synchronized (this) {
            if (bl || this.tzFormat == null) {
                void var2_6;
                this.tzFormat = TimeZoneFormat.getInstance(this.locale);
                Object var2_2 = null;
                if (this.numberFormat instanceof DecimalFormat) {
                    String[] arrstring = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getDigitStringsLocal();
                    StringBuilder stringBuilder = new StringBuilder();
                    int n = arrstring.length;
                    for (int i = 0; i < n; ++i) {
                        stringBuilder.append(arrstring[i]);
                    }
                    String string = stringBuilder.toString();
                } else if (this.numberFormat instanceof DateNumberFormat) {
                    String string = new String(((DateNumberFormat)this.numberFormat).getDigits());
                }
                if (var2_6 != null && !this.tzFormat.getGMTOffsetDigits().equals(var2_6)) {
                    if (this.tzFormat.isFrozen()) {
                        this.tzFormat = this.tzFormat.cloneAsThawed();
                    }
                    this.tzFormat.setGMTOffsetDigits((String)var2_6);
                }
            }
            return;
        }
    }

    static boolean isFieldUnitIgnored(String string, int n) {
        int n2 = CALENDAR_FIELD_TO_LEVEL[n];
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        n = 0;
        int n6 = n4;
        do {
            int n7;
            int n8;
            n4 = string.length();
            int n9 = 0;
            if (n >= n4) break;
            int n10 = string.charAt(n);
            n4 = n5;
            if (n10 != n6) {
                n4 = n5;
                if (n5 > 0) {
                    if (n2 <= SimpleDateFormat.getLevelFromChar((char)n6)) {
                        return false;
                    }
                    n4 = 0;
                }
            }
            if (n10 == 39) {
                if (n + 1 < string.length() && string.charAt(n + 1) == '\'') {
                    n8 = n + 1;
                    n9 = n3;
                    n7 = n6;
                    n5 = n4;
                } else {
                    n5 = n9;
                    if (n3 == 0) {
                        n5 = 1;
                    }
                    n9 = n5;
                    n7 = n6;
                    n5 = n4;
                    n8 = n;
                }
            } else {
                n9 = n3;
                n7 = n6;
                n5 = n4;
                n8 = n;
                if (n3 == 0) {
                    n9 = n3;
                    n7 = n6;
                    n5 = n4;
                    n8 = n;
                    if (SimpleDateFormat.isSyntaxChar((char)n10)) {
                        n7 = n10;
                        n5 = n4 + '\u0001';
                        n8 = n;
                        n9 = n3;
                    }
                }
            }
            n = n8 + 1;
            n3 = n9;
            n6 = n7;
        } while (true);
        return n5 <= 0 || n2 > SimpleDateFormat.getLevelFromChar((char)n6);
    }

    private static final boolean isNumeric(char c, int n) {
        boolean bl = NUMERIC_FORMAT_CHARS.indexOf(c) >= 0 || n <= 2 && NUMERIC_FORMAT_CHARS2.indexOf(c) >= 0;
        return bl;
    }

    private static boolean isSyntaxChar(char c) {
        boolean[] arrbl = PATTERN_CHAR_IS_SYNTAX;
        boolean bl = c < arrbl.length ? arrbl[c & 255] : false;
        return bl;
    }

    private boolean lowerLevel(Object[] object, int n, int n2) throws IllegalArgumentException {
        if (object[n] instanceof String) {
            return false;
        }
        char c = ((PatternItem)object[n]).type;
        n = SimpleDateFormat.getLevelFromChar(c);
        if (n != -1) {
            return n >= n2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Illegal pattern character '");
        ((StringBuilder)object).append(c);
        ((StringBuilder)object).append("' in \"");
        ((StringBuilder)object).append(this.pattern);
        ((StringBuilder)object).append('\"');
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private int matchDayPeriodString(String string, int n, String[] arrstring, int n2, Output<DayPeriodRules.DayPeriod> output) {
        int n3 = 0;
        int n4 = -1;
        for (int i = 0; i < n2; ++i) {
            int n5 = n3;
            int n6 = n4;
            if (arrstring[i] != null) {
                int n7 = arrstring[i].length();
                n5 = n3;
                n6 = n4;
                if (n7 > n3) {
                    n7 = this.regionMatchesWithOptionalDot(string, n, arrstring[i], n7);
                    n5 = n3;
                    n6 = n4;
                    if (n7 >= 0) {
                        n6 = i;
                        n5 = n7;
                    }
                }
            }
            n3 = n5;
            n4 = n6;
        }
        if (n4 >= 0) {
            output.value = DayPeriodRules.DayPeriod.VALUES[n4];
            return n + n3;
        }
        return -n;
    }

    private int matchLiteral(String string, int n, Object[] object, int n2, boolean[] arrbl) {
        Object object2;
        int n3;
        String string2 = (String)object[n2];
        int n4 = string2.length();
        int n5 = string.length();
        int n6 = 0;
        int n7 = n;
        while (n6 < n4 && n7 < n5) {
            int n8;
            int n9;
            n3 = string2.charAt(n6);
            char c = string.charAt(n7);
            if (PatternProps.isWhiteSpace(n3) && PatternProps.isWhiteSpace(c)) {
                do {
                    n3 = n7;
                    if (n6 + 1 >= n4) break;
                    n3 = n7;
                    if (!PatternProps.isWhiteSpace(string2.charAt(n6 + 1))) break;
                    ++n6;
                } while (true);
                do {
                    n8 = n6;
                    n9 = n3;
                    if (n3 + 1 < n5) {
                        n8 = n6;
                        n9 = n3;
                        if (PatternProps.isWhiteSpace(string.charAt(n3 + 1))) {
                            ++n3;
                            continue;
                        }
                    }
                    break;
                } while (true);
            } else {
                n8 = n6;
                n9 = n7;
                if (n3 != c) {
                    if (c == '.' && n7 == n && n2 > 0 && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) {
                        object2 = object[n2 - 1];
                        if (!(object2 instanceof PatternItem) || ((PatternItem)object2).isNumeric) break;
                        ++n7;
                        continue;
                    }
                    if ((n3 == 32 || n3 == 46) && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) {
                        ++n6;
                        continue;
                    }
                    if (n7 == n || !this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH)) break;
                    ++n6;
                    continue;
                }
            }
            n6 = n8 + 1;
            n7 = n9 + 1;
        }
        boolean bl = n6 == n4;
        arrbl[0] = bl;
        n6 = n7;
        if (!arrbl[0]) {
            n6 = n7;
            if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) {
                n6 = n7;
                if (n2 > 0) {
                    n6 = n7;
                    if (n2 < ((Object[])object).length - 1) {
                        n6 = n7;
                        if (n < n5) {
                            object2 = object[n2 - 1];
                            object = object[n2 + 1];
                            n6 = n7;
                            if (object2 instanceof PatternItem) {
                                n6 = n7;
                                if (object instanceof PatternItem) {
                                    n2 = ((PatternItem)object2).type;
                                    n3 = ((PatternItem)object).type;
                                    n6 = n7;
                                    if (DATE_PATTERN_TYPE.contains(n2) != DATE_PATTERN_TYPE.contains(n3)) {
                                        for (n2 = n; n2 < n5 && PatternProps.isWhiteSpace(string.charAt(n2)); ++n2) {
                                        }
                                        bl = n2 > n;
                                        arrbl[0] = bl;
                                        n6 = n2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return n6;
    }

    @Deprecated
    private int matchString(String string, int n, int n2, String[] arrstring, String string2, Calendar calendar) {
        int n3 = 0;
        int n4 = arrstring.length;
        if (n2 == 7) {
            n3 = 1;
        }
        int n5 = 0;
        int n6 = -1;
        int n7 = 0;
        while (n3 < n4) {
            int n8 = arrstring[n3].length();
            int n9 = n5;
            int n10 = n6;
            int n11 = n7;
            if (n8 > n5) {
                n8 = this.regionMatchesWithOptionalDot(string, n, arrstring[n3], n8);
                n9 = n5;
                n10 = n6;
                n11 = n7;
                if (n8 >= 0) {
                    n10 = n3;
                    n9 = n8;
                    n11 = 0;
                }
            }
            n5 = n9;
            n6 = n10;
            n7 = n11;
            if (string2 != null) {
                String string3 = SimpleFormatterImpl.formatRawPattern(string2, 1, 1, arrstring[n3]);
                n8 = string3.length();
                n5 = n9;
                n6 = n10;
                n7 = n11;
                if (n8 > n9) {
                    n8 = this.regionMatchesWithOptionalDot(string, n, string3, n8);
                    n5 = n9;
                    n6 = n10;
                    n7 = n11;
                    if (n8 >= 0) {
                        n6 = n3;
                        n5 = n8;
                        n7 = 1;
                    }
                }
            }
            ++n3;
        }
        if (n6 >= 0) {
            if (n2 >= 0) {
                n3 = n6;
                if (n2 == 1) {
                    n3 = n6 + 1;
                }
                calendar.set(n2, n3);
                if (string2 != null) {
                    calendar.set(22, n7);
                }
            }
            return n + n5;
        }
        return n;
    }

    private void parseAmbiguousDatesAsAfter(Date date) {
        this.defaultCenturyStart = date;
        this.calendar.setTime(date);
        this.defaultCenturyStartYear = this.calendar.get(1);
    }

    private Number parseInt(String object, int n, ParsePosition parsePosition, boolean bl, NumberFormat object2) {
        Object object3;
        int n2 = parsePosition.getIndex();
        if (bl) {
            object = ((NumberFormat)object2).parse((String)object, parsePosition);
        } else if (object2 instanceof DecimalFormat) {
            object3 = ((DecimalFormat)object2).getNegativePrefix();
            ((DecimalFormat)object2).setNegativePrefix(SUPPRESS_NEGATIVE_PREFIX);
            object = ((NumberFormat)object2).parse((String)object, parsePosition);
            ((DecimalFormat)object2).setNegativePrefix((String)object3);
        } else {
            bl = object2 instanceof DateNumberFormat;
            if (bl) {
                ((DateNumberFormat)object2).setParsePositiveOnly(true);
            }
            object = object3 = ((NumberFormat)object2).parse((String)object, parsePosition);
            if (bl) {
                ((DateNumberFormat)object2).setParsePositiveOnly(false);
                object = object3;
            }
        }
        object2 = object;
        if (n > 0) {
            int n3 = parsePosition.getIndex() - n2;
            object2 = object;
            if (n3 > n) {
                double d = ((Number)object).doubleValue();
                n3 -= n;
                while (n3 > 0) {
                    d /= 10.0;
                    --n3;
                }
                parsePosition.setIndex(n2 + n);
                object2 = (int)d;
            }
        }
        return object2;
    }

    private Number parseInt(String string, ParsePosition parsePosition, boolean bl, NumberFormat numberFormat) {
        return this.parseInt(string, -1, parsePosition, bl, numberFormat);
    }

    private void parsePattern() {
        this.hasMinute = false;
        this.hasSecond = false;
        boolean bl = false;
        for (int i = 0; i < this.pattern.length(); ++i) {
            char c = this.pattern.charAt(i);
            boolean bl2 = bl;
            if (c == '\'') {
                bl2 = !bl;
            }
            if (!bl2) {
                if (c == 'm') {
                    this.hasMinute = true;
                }
                if (c == 's') {
                    this.hasSecond = true;
                }
            }
            bl = bl2;
        }
    }

    private void processOverrideString(ULocale uLocale, String string) {
        if (string != null && string.length() != 0) {
            int n = 0;
            boolean bl = true;
            while (bl) {
                int n2;
                Object object;
                int n3 = string.indexOf(";", n);
                if (n3 == -1) {
                    bl = false;
                    n2 = string.length();
                } else {
                    n2 = n3;
                }
                CharSequence charSequence = string.substring(n, n2);
                n = charSequence.indexOf("=");
                if (n == -1) {
                    n = 1;
                } else {
                    object = charSequence.substring(n + 1);
                    char c = charSequence.charAt(0);
                    this.overrideMap.put(Character.valueOf(c), (String)object);
                    n = 0;
                    charSequence = object;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(uLocale.getBaseName());
                ((StringBuilder)object).append("@numbers=");
                ((StringBuilder)object).append((String)charSequence);
                object = NumberFormat.createInstance(new ULocale(((StringBuilder)object).toString()), 0);
                ((NumberFormat)object).setGroupingUsed(false);
                if (n != 0) {
                    this.setNumberFormat((NumberFormat)object);
                } else {
                    this.useLocalZeroPaddingNumberFormat = false;
                }
                if (n == 0 && !this.numberFormatters.containsKey(charSequence)) {
                    this.numberFormatters.put((String)charSequence, (NumberFormat)object);
                }
                n = n3 + 1;
            }
            return;
        }
    }

    private void readObject(ObjectInputStream object2) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object2).defaultReadObject();
        int n = this.serialVersionOnStream > 1 ? ((ObjectInputStream)object2).readInt() : -1;
        if (this.serialVersionOnStream < 1) {
            this.defaultCenturyBase = System.currentTimeMillis();
        } else {
            this.parseAmbiguousDatesAsAfter(this.defaultCenturyStart);
        }
        this.serialVersionOnStream = 2;
        this.locale = this.getLocale(ULocale.VALID_LOCALE);
        if (this.locale == null) {
            this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
        }
        this.initLocalZeroPaddingNumberFormat();
        this.setContext(DisplayContext.CAPITALIZATION_NONE);
        if (n >= 0) {
            for (DisplayContext displayContext : DisplayContext.values()) {
                if (displayContext.value() != n) continue;
                this.setContext(displayContext);
                break;
            }
        }
        if (!this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_MATCH)) {
            this.setBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH, false);
        }
        this.parsePattern();
    }

    private int regionMatchesWithOptionalDot(String string, int n, String string2, int n2) {
        if (string.regionMatches(true, n, string2, 0, n2)) {
            return n2;
        }
        if (string2.length() > 0 && string2.charAt(string2.length() - 1) == '.' && string.regionMatches(true, n, string2, 0, n2 - 1)) {
            return n2 - 1;
        }
        return -1;
    }

    private static void safeAppend(String[] arrstring, int n, StringBuffer stringBuffer) {
        if (arrstring != null && n >= 0 && n < arrstring.length) {
            stringBuffer.append(arrstring[n]);
        }
    }

    private static void safeAppendWithMonthPattern(String[] arrstring, int n, StringBuffer stringBuffer, String string) {
        if (arrstring != null && n >= 0 && n < arrstring.length) {
            if (string == null) {
                stringBuffer.append(arrstring[n]);
            } else {
                stringBuffer.append(SimpleFormatterImpl.formatRawPattern(string, 1, 1, arrstring[n]));
            }
        }
    }

    private int subParse(String string, int n, char c, int n2, boolean bl, boolean bl2, boolean[] arrbl, Calendar calendar, MessageFormat messageFormat, Output<TimeZoneFormat.TimeType> output) {
        return this.subParse(string, n, c, n2, bl, bl2, arrbl, calendar, null, null, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Deprecated
    private int subParse(String var1_1, int var2_2, char var3_3, int var4_4, boolean var5_5, boolean var6_6, boolean[] var7_7, Calendar var8_8, MessageFormat var9_9, Output<TimeZoneFormat.TimeType> var10_10, Output<DayPeriodRules.DayPeriod> var11_11) {
        block147 : {
            block146 : {
                var12_57 = 0;
                var13_58 = new ParsePosition(0);
                var14_59 = SimpleDateFormat.getIndexFromChar(var3_3);
                if (var14_59 == -1) {
                    return var2_2;
                }
                var15_60 = this.getNumberFormat(var3_3);
                var16_61 = SimpleDateFormat.PATTERN_INDEX_TO_CALENDAR_FIELD[var14_59];
                if (var9_54 != null) {
                    var9_54.setFormatByArgumentIndex(0, var15_60);
                }
                var17_62 = var8_53.getType();
                var18_63 = null;
                var19_70 = null;
                var20_78 = !var17_62.equals("chinese") && !var8_53.getType().equals("dangi") ? 0 : 1;
                var21_79 = var2_2;
                do {
                    if (var21_79 >= var1_1.length()) {
                        return var21_79;
                    }
                    var2_2 = UTF16.charAt((String)var1_1, var21_79);
                    if (!UCharacter.isUWhiteSpace(var2_2) || !PatternProps.isWhiteSpace(var2_2)) break;
                    var21_79 += UTF16.getCharCount(var2_2);
                } while (true);
                var13_58.setIndex(var21_79);
                if (!(var14_59 == 4 || var14_59 == 15 || var14_59 == 2 && var4_4 <= 2 || var14_59 == 26 || var14_59 == 19 || var14_59 == 25 || var14_59 == 1 || var14_59 == 18 || var14_59 == 30 || var14_59 == 0 && var20_78 != 0 || var14_59 == 27 || var14_59 == 28 || var14_59 == 8)) {
                    var2_2 = var12_57;
                    var19_71 = var18_63;
                } else {
                    if (var9_54 != null) {
                        if (var14_59 != 2 && var14_59 != 26) {
                            var2_2 = 0;
                        } else {
                            var18_64 = var9_54.parse((String)var1_1, var13_58);
                            if (var18_64 != null && var13_58.getIndex() > var21_79 && var18_64[0] instanceof Number) {
                                var19_72 = (Number)var18_64[0];
                                var8_53.set(22, 1);
                                var2_2 = 1;
                            } else {
                                var2_2 = 0;
                                var13_58.setIndex(var21_79);
                                var8_53.set(22, 0);
                            }
                        }
                    } else {
                        var2_2 = 0;
                    }
                    if (var2_2 == 0) {
                        if (var5_5) {
                            if (var21_79 + var4_4 > var1_1.length()) {
                                return var21_79;
                            }
                            var18_66 = this.parseInt((String)var1_1, var4_4, var13_58, var6_6, var15_60);
                        } else {
                            var18_67 = this.parseInt((String)var1_1, var13_58, var6_6, var15_60);
                        }
                        var19_74 = var18_68;
                        if (var18_68 == null) {
                            var19_75 = var18_68;
                            if (!this.allowNumericFallback(var14_59)) {
                                return var21_79;
                            }
                        }
                    }
                    var2_2 = var19_76 != null ? var19_76.intValue() : var12_57;
                }
                switch (var14_59) {
                    default: {
                        if (var5_5) {
                            if (var21_79 + var4_4 <= var1_1.length()) break;
                            return -var21_79;
                        }
                        break block146;
                    }
                    case 37: {
                        var7_8 = new ArrayList<String>(3);
                        var7_8.add(this.formatData.getTimeSeparatorString());
                        if (!this.formatData.getTimeSeparatorString().equals(":")) {
                            var7_8.add(":");
                        }
                        if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH) == false) return this.matchString((String)var1_1, var21_79, -1, var7_8.toArray(new String[0]), (Calendar)var8_53);
                        if (this.formatData.getTimeSeparatorString().equals(".") != false) return this.matchString((String)var1_1, var21_79, -1, var7_8.toArray(new String[0]), (Calendar)var8_53);
                        var7_8.add(".");
                        return this.matchString((String)var1_1, var21_79, -1, var7_8.toArray(new String[0]), (Calendar)var8_53);
                    }
                    case 36: {
                        var2_2 = 0;
                        if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || var4_4 == 3) {
                            var2_2 = var20_78 = (var16_61 = this.matchDayPeriodString((String)var1_1, var21_79, this.formatData.abbreviatedDayPeriods, this.formatData.abbreviatedDayPeriods.length, (Output<DayPeriodRules.DayPeriod>)var11_56));
                            if (var16_61 > 0) {
                                return var20_78;
                            }
                        }
                        if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || var4_4 == 4) {
                            var2_2 = var20_78 = (var16_61 = this.matchDayPeriodString((String)var1_1, var21_79, this.formatData.wideDayPeriods, this.formatData.wideDayPeriods.length, (Output<DayPeriodRules.DayPeriod>)var11_56));
                            if (var16_61 > 0) {
                                return var20_78;
                            }
                        }
                        if (!this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH)) {
                            if (var4_4 != 4) return var2_2;
                        }
                        var2_2 = var4_4 = (var21_79 = this.matchDayPeriodString((String)var1_1, var21_79, this.formatData.narrowDayPeriods, this.formatData.narrowDayPeriods.length, (Output<DayPeriodRules.DayPeriod>)var11_56));
                        if (var21_79 <= 0) return var2_2;
                        return var4_4;
                    }
                    case 35: {
                        var2_2 = this.subParse((String)var1_1, var21_79, 'a', var4_4, var5_5, var6_6, var7_7 /* !! */ , (Calendar)var8_53, (MessageFormat)var9_54, (Output<TimeZoneFormat.TimeType>)var10_55, (Output<DayPeriodRules.DayPeriod>)var11_56);
                        if (var2_2 > 0) {
                            return var2_2;
                        }
                        var2_2 = 0;
                        if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || var4_4 == 3) {
                            var2_2 = var20_78 = (var16_61 = this.matchDayPeriodString((String)var1_1, var21_79, this.formatData.abbreviatedDayPeriods, 2, (Output<DayPeriodRules.DayPeriod>)var11_56));
                            if (var16_61 > 0) {
                                return var20_78;
                            }
                        }
                        if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || var4_4 == 4) {
                            var2_2 = var20_78 = (var16_61 = this.matchDayPeriodString((String)var1_1, var21_79, this.formatData.wideDayPeriods, 2, (Output<DayPeriodRules.DayPeriod>)var11_56));
                            if (var16_61 > 0) {
                                return var20_78;
                            }
                        }
                        if (!this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH)) {
                            if (var4_4 != 4) return var2_2;
                        }
                        var2_2 = var4_4 = (var21_79 = this.matchDayPeriodString((String)var1_1, var21_79, this.formatData.narrowDayPeriods, 2, (Output<DayPeriodRules.DayPeriod>)var11_56));
                        if (var21_79 <= 0) return var2_2;
                        return var4_4;
                    }
                    case 33: {
                        if (var4_4 != 1) {
                            if (var4_4 != 2) {
                                if (var4_4 != 3) {
                                    if (var4_4 != 4) {
                                        var7_9 = TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FULL;
                                    } else {
                                        var7_10 = TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL;
                                    }
                                } else {
                                    var7_11 = TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FIXED;
                                }
                            } else {
                                var7_12 = TimeZoneFormat.Style.ISO_BASIC_LOCAL_FIXED;
                            }
                        } else {
                            var7_13 = TimeZoneFormat.Style.ISO_BASIC_LOCAL_SHORT;
                        }
                        var1_1 = this.tzFormat().parse((TimeZoneFormat.Style)var7_14, (String)var1_1, var13_58, (Output<TimeZoneFormat.TimeType>)var10_55);
                        if (var1_1 == null) return var21_79;
                        var8_53.setTimeZone((TimeZone)var1_1);
                        return var13_58.getIndex();
                    }
                    case 32: {
                        if (var4_4 != 1) {
                            if (var4_4 != 2) {
                                if (var4_4 != 3) {
                                    if (var4_4 != 4) {
                                        var7_15 = TimeZoneFormat.Style.ISO_EXTENDED_FULL;
                                    } else {
                                        var7_16 = TimeZoneFormat.Style.ISO_BASIC_FULL;
                                    }
                                } else {
                                    var7_17 = TimeZoneFormat.Style.ISO_EXTENDED_FIXED;
                                }
                            } else {
                                var7_18 = TimeZoneFormat.Style.ISO_BASIC_FIXED;
                            }
                        } else {
                            var7_19 = TimeZoneFormat.Style.ISO_BASIC_SHORT;
                        }
                        var1_1 = this.tzFormat().parse((TimeZoneFormat.Style)var7_20, (String)var1_1, var13_58, (Output<TimeZoneFormat.TimeType>)var10_55);
                        if (var1_1 == null) return var21_79;
                        var8_53.setTimeZone((TimeZone)var1_1);
                        return var13_58.getIndex();
                    }
                    case 31: {
                        if (var4_4 < 4) {
                            var7_21 = TimeZoneFormat.Style.LOCALIZED_GMT_SHORT;
                        } else {
                            var7_22 = TimeZoneFormat.Style.LOCALIZED_GMT;
                        }
                        var1_1 = this.tzFormat().parse((TimeZoneFormat.Style)var7_23, (String)var1_1, var13_58, (Output<TimeZoneFormat.TimeType>)var10_55);
                        if (var1_1 == null) return var21_79;
                        var8_53.setTimeZone((TimeZone)var1_1);
                        return var13_58.getIndex();
                    }
                    case 30: {
                        if (this.formatData.shortYearNames != null && (var4_4 = this.matchString((String)var1_1, var21_79, 1, this.formatData.shortYearNames, null, (Calendar)var8_53)) > 0) {
                            return var4_4;
                        }
                        if (var19_77 == null) return var21_79;
                        if (!this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC) && this.formatData.shortYearNames != null) {
                            if (var2_2 <= this.formatData.shortYearNames.length) return var21_79;
                        }
                        var8_53.set(1, var2_2);
                        return var13_58.getIndex();
                    }
                    case 29: {
                        if (var4_4 != 1) {
                            if (var4_4 != 2) {
                                if (var4_4 != 3) {
                                    var7_24 = TimeZoneFormat.Style.GENERIC_LOCATION;
                                } else {
                                    var7_25 = TimeZoneFormat.Style.EXEMPLAR_LOCATION;
                                }
                            } else {
                                var7_26 = TimeZoneFormat.Style.ZONE_ID;
                            }
                        } else {
                            var7_27 = TimeZoneFormat.Style.ZONE_ID_SHORT;
                        }
                        var1_1 = this.tzFormat().parse((TimeZoneFormat.Style)var7_28, (String)var1_1, var13_58, (Output<TimeZoneFormat.TimeType>)var10_55);
                        if (var1_1 == null) return var21_79;
                        var8_53.setTimeZone((TimeZone)var1_1);
                        return var13_58.getIndex();
                    }
                    case 28: {
                        if (!(var4_4 <= 2 || var19_77 != null && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC))) {
                            var20_78 = 0;
                            if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || var4_4 == 4) {
                                var20_78 = var2_2 = (var16_61 = this.matchQuarterString((String)var1_1, var21_79, 2, this.formatData.standaloneQuarters, (Calendar)var8_53));
                                if (var16_61 > 0) {
                                    return var2_2;
                                }
                            }
                            if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) return this.matchQuarterString((String)var1_1, var21_79, 2, this.formatData.standaloneShortQuarters, (Calendar)var8_53);
                            if (var4_4 != 3) return var20_78;
                            return this.matchQuarterString((String)var1_1, var21_79, 2, this.formatData.standaloneShortQuarters, (Calendar)var8_53);
                        }
                        var8_53.set(2, (var2_2 - 1) * 3);
                        return var13_58.getIndex();
                    }
                    case 27: {
                        if (!(var4_4 <= 2 || var19_77 != null && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC))) {
                            var20_78 = 0;
                            if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || var4_4 == 4) {
                                var20_78 = var2_2 = (var16_61 = this.matchQuarterString((String)var1_1, var21_79, 2, this.formatData.quarters, (Calendar)var8_53));
                                if (var16_61 > 0) {
                                    return var2_2;
                                }
                            }
                            if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) != false) return this.matchQuarterString((String)var1_1, var21_79, 2, this.formatData.shortQuarters, (Calendar)var8_53);
                            if (var4_4 != 3) return var20_78;
                            return this.matchQuarterString((String)var1_1, var21_79, 2, this.formatData.shortQuarters, (Calendar)var8_53);
                        }
                        var8_53.set(2, (var2_2 - 1) * 3);
                        return var13_58.getIndex();
                    }
                    case 25: {
                        if (!(var4_4 == 1 || var19_77 != null && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC))) {
                            var2_2 = 0;
                            if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || var4_4 == 4) {
                                var2_2 = var20_78 = (var16_61 = this.matchString((String)var1_1, var21_79, 7, this.formatData.standaloneWeekdays, null, (Calendar)var8_53));
                                if (var16_61 > 0) {
                                    return var20_78;
                                }
                            }
                            if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || var4_4 == 3) {
                                var2_2 = var20_78 = (var16_61 = this.matchString((String)var1_1, var21_79, 7, this.formatData.standaloneShortWeekdays, null, (Calendar)var8_53));
                                if (var16_61 > 0) {
                                    return var20_78;
                                }
                            }
                            if (!this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH)) {
                                if (var4_4 != 6) return var2_2;
                            }
                            if (this.formatData.standaloneShorterWeekdays == null) return var2_2;
                            return this.matchString((String)var1_1, var21_79, 7, this.formatData.standaloneShorterWeekdays, null, (Calendar)var8_53);
                        }
                        var8_53.set(var16_61, var2_2);
                        return var13_58.getIndex();
                    }
                    case 24: {
                        if (var4_4 < 4) {
                            var7_29 = TimeZoneFormat.Style.GENERIC_SHORT;
                        } else {
                            var7_30 = TimeZoneFormat.Style.GENERIC_LONG;
                        }
                        var1_1 = this.tzFormat().parse((TimeZoneFormat.Style)var7_31, (String)var1_1, var13_58, (Output<TimeZoneFormat.TimeType>)var10_55);
                        if (var1_1 == null) return var21_79;
                        var8_53.setTimeZone((TimeZone)var1_1);
                        return var13_58.getIndex();
                    }
                    case 23: {
                        if (var4_4 < 4) {
                            var7_32 = TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL;
                        } else if (var4_4 == 5) {
                            var7_33 = TimeZoneFormat.Style.ISO_EXTENDED_FULL;
                        } else {
                            var7_34 = TimeZoneFormat.Style.LOCALIZED_GMT;
                        }
                        var1_1 = this.tzFormat().parse((TimeZoneFormat.Style)var7_35, (String)var1_1, var13_58, (Output<TimeZoneFormat.TimeType>)var10_55);
                        if (var1_1 == null) return var21_79;
                        var8_53.setTimeZone((TimeZone)var1_1);
                        return var13_58.getIndex();
                    }
                    case 19: {
                        if (var4_4 <= 2 || var19_77 != null && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC)) {
                            var8_53.set(var16_61, var2_2);
                            return var13_58.getIndex();
                        }
                        ** GOTO lbl263
                    }
                    case 17: {
                        if (var4_4 < 4) {
                            var7_36 = TimeZoneFormat.Style.SPECIFIC_SHORT;
                        } else {
                            var7_37 = TimeZoneFormat.Style.SPECIFIC_LONG;
                        }
                        var1_1 = this.tzFormat().parse((TimeZoneFormat.Style)var7_38, (String)var1_1, var13_58, (Output<TimeZoneFormat.TimeType>)var10_55);
                        if (var1_1 == null) return var21_79;
                        var8_53.setTimeZone((TimeZone)var1_1);
                        return var13_58.getIndex();
                    }
                    case 15: {
                        if (var2_2 == var8_53.getLeastMaximum(10) + 1) {
                            var2_2 = 0;
                        }
                        var8_53.set(10, var2_2);
                        return var13_58.getIndex();
                    }
                    case 14: {
                        if ((this.formatData.ampmsNarrow == null || var4_4 < 5 || this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH)) && (var2_2 = this.matchString((String)var1_1, var21_79, 9, this.formatData.ampms, null, (Calendar)var8_53)) > 0) {
                            return var2_2;
                        }
                        if (this.formatData.ampmsNarrow == null) return var21_79;
                        if (var4_4 < 5) {
                            if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) == false) return var21_79;
                        }
                        if ((var2_2 = this.matchString((String)var1_1, var21_79, 9, this.formatData.ampmsNarrow, null, (Calendar)var8_53)) <= 0) return var21_79;
                        return var2_2;
                    }
lbl263: // 2 sources:
                    case 9: {
                        var20_78 = var21_79;
                        var21_79 = 0;
                        if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || var4_4 == 4) {
                            var21_79 = var2_2 = (var16_61 = this.matchString((String)var1_1, var20_78, 7, this.formatData.weekdays, null, (Calendar)var8_53));
                            if (var16_61 > 0) {
                                return var2_2;
                            }
                        }
                        if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || var4_4 == 3) {
                            var21_79 = var2_2 = (var16_61 = this.matchString((String)var1_1, var20_78, 7, this.formatData.shortWeekdays, null, (Calendar)var8_53));
                            if (var16_61 > 0) {
                                return var2_2;
                            }
                        }
                        if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH)) ** GOTO lbl277
                        var2_2 = var21_79;
                        if (var4_4 != 6) ** GOTO lbl282
lbl277: // 2 sources:
                        var2_2 = var21_79;
                        if (this.formatData.shorterWeekdays != null) {
                            var2_2 = var21_79 = (var16_61 = this.matchString((String)var1_1, var20_78, 7, this.formatData.shorterWeekdays, null, (Calendar)var8_53));
                            if (var16_61 > 0) {
                                return var21_79;
                            }
                        }
lbl282: // 5 sources:
                        if (!this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH)) {
                            var21_79 = var2_2;
                            if (var4_4 != 5) return var21_79;
                        }
                        var21_79 = var2_2;
                        if (this.formatData.narrowWeekdays == null) return var21_79;
                        var21_79 = var2_2 = (var4_4 = this.matchString((String)var1_1, var20_78, 7, this.formatData.narrowWeekdays, null, (Calendar)var8_53));
                        if (var4_4 <= 0) return var21_79;
                        return var2_2;
                    }
                    case 8: {
                        var4_4 = SimpleDateFormat.countDigits((String)var1_1, var21_79, var13_58.getIndex());
                        if (var4_4 < 3) {
                            do {
                                var21_79 = var2_2;
                                if (var4_4 < 3) {
                                    var2_2 *= 10;
                                    ++var4_4;
                                    continue;
                                }
                                break;
                            } while (true);
                        } else {
                            var21_79 = 1;
                            while (var4_4 > 3) {
                                var21_79 *= 10;
                                --var4_4;
                            }
                            var21_79 = var2_2 / var21_79;
                        }
                        var8_53.set(14, var21_79);
                        return var13_58.getIndex();
                    }
                    case 4: {
                        if (var2_2 == var8_53.getMaximum(11) + 1) {
                            var2_2 = 0;
                        }
                        var8_53.set(11, var2_2);
                        return var13_58.getIndex();
                    }
                    case 2: 
                    case 26: {
                        if (!(var4_4 <= 2 || var19_77 != null && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC))) {
                            var20_78 = this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7 ? 1 : 0;
                            var2_2 = 0;
                            if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || var4_4 == 4) {
                                if (var14_59 == 2) {
                                    var9_54 = this.formatData.months;
                                    if (var20_78 != 0) {
                                        var7_39 = this.formatData.leapMonthPatterns[0];
                                    } else {
                                        var7_40 = null;
                                    }
                                    var2_2 = this.matchString((String)var1_1, var21_79, 2, var9_54, (String)var7_41, (Calendar)var8_53);
                                } else {
                                    var9_54 = this.formatData.standaloneMonths;
                                    if (var20_78 != 0) {
                                        var7_42 = this.formatData.leapMonthPatterns[3];
                                    } else {
                                        var7_43 = null;
                                    }
                                    var2_2 = this.matchString((String)var1_1, var21_79, 2, var9_54, (String)var7_44, (Calendar)var8_53);
                                }
                                var2_2 = var16_61 = var2_2;
                                if (var16_61 > 0) {
                                    return var16_61;
                                }
                            }
                            if (!this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH)) {
                                if (var4_4 != 3) return var2_2;
                            }
                            if (var14_59 == 2) {
                                var9_54 = this.formatData.shortMonths;
                                if (var20_78 != 0) {
                                    var7_47 = this.formatData.leapMonthPatterns[1];
                                    return this.matchString((String)var1_1, var21_79, 2, var9_54, (String)var7_49, (Calendar)var8_53);
                                } else {
                                    var7_48 = null;
                                }
                                return this.matchString((String)var1_1, var21_79, 2, var9_54, (String)var7_49, (Calendar)var8_53);
                            }
                            var9_54 = this.formatData.standaloneShortMonths;
                            if (var20_78 != 0) {
                                var7_50 = this.formatData.leapMonthPatterns[4];
                                return this.matchString((String)var1_1, var21_79, 2, var9_54, (String)var7_52, (Calendar)var8_53);
                            } else {
                                var7_51 = null;
                            }
                            return this.matchString((String)var1_1, var21_79, 2, var9_54, (String)var7_52, (Calendar)var8_53);
                        }
                        var8_53.set(2, var2_2 - 1);
                        if (var8_53.getType().equals("hebrew") == false) return var13_58.getIndex();
                        if (var2_2 < 6) return var13_58.getIndex();
                        if (var8_53.isSet(1)) {
                            if (HebrewCalendar.isLeapYear(var8_53.get(1)) != false) return var13_58.getIndex();
                            var8_53.set(2, var2_2);
                            return var13_58.getIndex();
                        }
                        SimpleDateFormat.DelayedHebrewMonthCheck = true;
                        return var13_58.getIndex();
                    }
                    case 1: 
                    case 18: {
                        var9_54 = this.override;
                        if (var9_54 != null && (var9_54.compareTo("hebr") == 0 || this.override.indexOf("y=hebr") >= 0) && var2_2 < 1000) {
                            var2_2 += 5000;
                        } else if (var4_4 == 2 && SimpleDateFormat.countDigits((String)var1_1, var21_79, var13_58.getIndex()) == 2 && var8_53.haveDefaultCentury()) {
                            var21_79 = this.getDefaultCenturyStartYear();
                            var4_4 = 100;
                            var20_78 = var21_79 % 100;
                            var5_5 = var2_2 == var20_78;
                            var7_7 /* !! */ [0] = var5_5;
                            var21_79 = this.getDefaultCenturyStartYear() / 100;
                            if (var2_2 >= var20_78) {
                                var4_4 = 0;
                            }
                            var2_2 += var21_79 * 100 + var4_4;
                        }
                        var8_53.set(var16_61, var2_2);
                        if (SimpleDateFormat.DelayedHebrewMonthCheck == false) return var13_58.getIndex();
                        if (!HebrewCalendar.isLeapYear(var2_2)) {
                            var8_53.add(2, 1);
                        }
                        SimpleDateFormat.DelayedHebrewMonthCheck = false;
                        return var13_58.getIndex();
                    }
                    case 0: {
                        if (var20_78 != 0) {
                            var8_53.set(0, var2_2);
                            return var13_58.getIndex();
                        }
                        var2_2 = var4_4 == 5 ? this.matchString((String)var1_1, var21_79, 0, this.formatData.narrowEras, null, (Calendar)var8_53) : (var4_4 == 4 ? this.matchString((String)var1_1, var21_79, 0, this.formatData.eraNames, null, (Calendar)var8_53) : this.matchString((String)var1_1, var21_79, 0, this.formatData.eras, null, (Calendar)var8_53));
                        var4_4 = var2_2;
                        if (var2_2 != var21_79) return var4_4;
                        return -32000;
                    }
                }
                var1_1 = this.parseInt((String)var1_1, var4_4, var13_58, var6_6, var15_60);
                break block147;
            }
            var1_1 = this.parseInt((String)var1_1, var13_58, var6_6, var15_60);
        }
        if (var1_1 == null) return var21_79;
        if (var14_59 != 34) {
            var8_53.set(var16_61, var1_1.intValue());
            return var13_58.getIndex();
        }
        var8_53.setRelatedYear(var1_1.intValue());
        return var13_58.getIndex();
    }

    private String translatePattern(String string, String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder();
        char c = '\u0000';
        for (int i = 0; i < string.length(); ++i) {
            char c2;
            char c3;
            char c4 = string.charAt(i);
            if (c != '\u0000') {
                c3 = c;
                c2 = c4;
                if (c4 == '\'') {
                    c3 = '\u0000';
                    c2 = c4;
                }
            } else if (c4 == '\'') {
                c3 = '\u0001';
                c2 = c4;
            } else {
                c3 = c;
                c2 = c4;
                if (SimpleDateFormat.isSyntaxChar(c4)) {
                    int n = string2.indexOf(c4);
                    c3 = c;
                    c2 = c4;
                    if (n != -1) {
                        c2 = c3 = string3.charAt(n);
                        c3 = c;
                    }
                }
            }
            stringBuilder.append(c2);
            c = c3;
        }
        if (c == '\u0000') {
            return stringBuilder.toString();
        }
        throw new IllegalArgumentException("Unfinished quote in pattern");
    }

    private TimeZoneFormat tzFormat() {
        if (this.tzFormat == null) {
            this.initializeTimeZoneFormat(false);
        }
        return this.tzFormat;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (this.defaultCenturyStart == null) {
            this.initializeDefaultCenturyStart(this.defaultCenturyBase);
        }
        this.initializeTimeZoneFormat(false);
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.getContext(DisplayContext.Type.CAPITALIZATION).value());
    }

    public void applyLocalizedPattern(String string) {
        this.pattern = this.translatePattern(string, this.formatData.localPatternChars, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB");
        this.setLocale(null, null);
    }

    public void applyPattern(String string) {
        this.pattern = string;
        this.parsePattern();
        this.setLocale(null, null);
        this.patternItems = null;
    }

    @Override
    public Object clone() {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat)super.clone();
        simpleDateFormat.formatData = (DateFormatSymbols)this.formatData.clone();
        if (this.decimalBuf != null) {
            simpleDateFormat.decimalBuf = new char[10];
        }
        return simpleDateFormat;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = super.equals(object);
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (SimpleDateFormat)object;
            if (!this.pattern.equals(((SimpleDateFormat)object).pattern) || !this.formatData.equals(((SimpleDateFormat)object).formatData)) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    public StringBuffer format(Calendar serializable, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        TimeZone timeZone;
        TimeZone timeZone2 = timeZone = null;
        Calendar calendar = serializable;
        if (serializable != this.calendar) {
            timeZone2 = timeZone;
            calendar = serializable;
            if (!((Calendar)serializable).getType().equals(this.calendar.getType())) {
                this.calendar.setTimeInMillis(((Calendar)serializable).getTimeInMillis());
                timeZone2 = this.calendar.getTimeZone();
                this.calendar.setTimeZone(((Calendar)serializable).getTimeZone());
                calendar = this.calendar;
            }
        }
        serializable = this.format(calendar, this.getContext(DisplayContext.Type.CAPITALIZATION), stringBuffer, fieldPosition, null);
        if (timeZone2 != null) {
            this.calendar.setTimeZone(timeZone2);
        }
        return serializable;
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        block6 : {
            Cloneable cloneable;
            block4 : {
                block5 : {
                    block3 : {
                        cloneable = this.calendar;
                        if (!(object instanceof Calendar)) break block3;
                        object = (Calendar)object;
                        break block4;
                    }
                    if (!(object instanceof Date)) break block5;
                    this.calendar.setTime((Date)object);
                    object = cloneable;
                    break block4;
                }
                if (!(object instanceof Number)) break block6;
                this.calendar.setTimeInMillis(((Number)object).longValue());
                object = cloneable;
            }
            Object object2 = new StringBuffer();
            FieldPosition fieldPosition = new FieldPosition(0);
            cloneable = new ArrayList();
            this.format((Calendar)object, this.getContext(DisplayContext.Type.CAPITALIZATION), (StringBuffer)object2, fieldPosition, (List<FieldPosition>)((Object)cloneable));
            object2 = new AttributedString(((StringBuffer)object2).toString());
            for (int i = 0; i < cloneable.size(); ++i) {
                fieldPosition = (FieldPosition)cloneable.get(i);
                object = fieldPosition.getFieldAttribute();
                ((AttributedString)object2).addAttribute((AttributedCharacterIterator.Attribute)object, object, fieldPosition.getBeginIndex(), fieldPosition.getEndIndex());
            }
            return ((AttributedString)object2).getIterator();
        }
        throw new IllegalArgumentException("Cannot format given Object as a Date");
    }

    public Date get2DigitYearStart() {
        return this.getDefaultCenturyStart();
    }

    public DateFormatSymbols getDateFormatSymbols() {
        return (DateFormatSymbols)this.formatData.clone();
    }

    ULocale getLocale() {
        return this.locale;
    }

    public NumberFormat getNumberFormat(char c) {
        Object object = Character.valueOf(c);
        HashMap<Character, String> hashMap = this.overrideMap;
        if (hashMap != null && hashMap.containsKey(object)) {
            object = this.overrideMap.get(object).toString();
            return this.numberFormatters.get(object);
        }
        return this.numberFormat;
    }

    protected DateFormatSymbols getSymbols() {
        return this.formatData;
    }

    public TimeZoneFormat getTimeZoneFormat() {
        return this.tzFormat().freeze();
    }

    @Override
    public int hashCode() {
        return this.pattern.hashCode();
    }

    /*
     * Exception decompiling
     */
    @Deprecated
    public final StringBuffer intervalFormatByAlgorithm(Calendar var1_1, Calendar var2_7, StringBuffer var3_8, FieldPosition var4_9) throws IllegalArgumentException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [9[UNCONDITIONALDOLOOP]], but top level block is 0[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    boolean isFieldUnitIgnored(int n) {
        return SimpleDateFormat.isFieldUnitIgnored(this.pattern, n);
    }

    protected int matchQuarterString(String string, int n, int n2, String[] arrstring, Calendar calendar) {
        int n3 = arrstring.length;
        int n4 = 0;
        int n5 = -1;
        for (int i = 0; i < n3; ++i) {
            int n6 = arrstring[i].length();
            int n7 = n4;
            int n8 = n5;
            if (n6 > n4) {
                n6 = this.regionMatchesWithOptionalDot(string, n, arrstring[i], n6);
                n7 = n4;
                n8 = n5;
                if (n6 >= 0) {
                    n8 = i;
                    n7 = n6;
                }
            }
            n4 = n7;
            n5 = n8;
        }
        if (n5 >= 0) {
            calendar.set(n2, n5 * 3);
            return n + n4;
        }
        return -n;
    }

    protected int matchString(String string, int n, int n2, String[] arrstring, Calendar calendar) {
        return this.matchString(string, n, n2, arrstring, null, calendar);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void parse(String var1_1, Calendar var2_8, ParsePosition var3_9) {
        block62 : {
            block69 : {
                block59 : {
                    block60 : {
                        block58 : {
                            block57 : {
                                block68 : {
                                    if (var2_8 != this.calendar && !var2_8.getType().equals(this.calendar.getType())) {
                                        this.calendar.setTimeInMillis(var2_8.getTimeInMillis());
                                        var4_10 = this.calendar.getTimeZone();
                                        this.calendar.setTimeZone(var2_8.getTimeZone());
                                        var5_11 = this.calendar;
                                        var6_12 = var2_8;
                                        var2_8 = var4_10;
                                        var4_10 = var5_11;
                                    } else {
                                        var5_11 = null;
                                        var6_12 = null;
                                        var4_10 = var2_8;
                                        var2_8 = var5_11;
                                    }
                                    var7_13 = var3_9.getIndex();
                                    if (var7_13 < 0) {
                                        var3_9.setErrorIndex(0);
                                        return;
                                    }
                                    var8_14 = var7_13;
                                    var9_15 = new Output<Object>(null);
                                    var5_11 = new Output<TimeZoneFormat.TimeType>(TimeZoneFormat.TimeType.UNKNOWN);
                                    var10_16 = new boolean[]{false};
                                    var11_17 = -1;
                                    var12_18 /* !! */  = this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7 ? new MessageFormat(this.formatData.leapMonthPatterns[6], this.locale) : null;
                                    var13_19 = this.getPatternItems();
                                    var14_20 = 0;
                                    var15_21 = 0;
                                    var16_22 = 0;
                                    while (var16_22 < var13_19.length) {
                                        block65 : {
                                            block63 : {
                                                block66 : {
                                                    block67 : {
                                                        block56 : {
                                                            block64 : {
                                                                if (!(var13_19[var16_22] instanceof PatternItem)) break block63;
                                                                var17_23 = (PatternItem)var13_19[var16_22];
                                                                if (var17_23.isNumeric && var11_17 == -1 && var16_22 + 1 < var13_19.length && var13_19[var16_22 + 1] instanceof PatternItem && ((PatternItem)var13_19[var16_22 + 1]).isNumeric) {
                                                                    var14_20 = var17_23.length;
                                                                    var15_21 = var7_13;
                                                                    var11_17 = var16_22;
                                                                }
                                                                if (var11_17 == -1) break block64;
                                                                var18_24 = var17_23.length;
                                                                if (var11_17 == var16_22) {
                                                                    var18_24 = var14_20;
                                                                }
                                                                if ((var7_13 = this.subParse((String)var1_1, var7_13, var19_25 = var17_23.type, var18_24, true, false, var10_16, (Calendar)var4_10, var12_18 /* !! */ , (Output<TimeZoneFormat.TimeType>)(var17_23 = var5_11))) < 0) {
                                                                    if (--var14_20 == 0) {
                                                                        var3_9.setIndex(var8_14);
                                                                        var3_9.setErrorIndex(var7_13);
                                                                        if (var2_8 == null) return;
                                                                        this.calendar.setTimeZone((TimeZone)var2_8);
                                                                        return;
                                                                    }
                                                                    var16_22 = var11_17;
                                                                    var7_13 = var15_21;
                                                                    var5_11 = var17_23;
                                                                    continue;
                                                                }
                                                                break block65;
                                                            }
                                                            if (var17_23.type == 'l') break block65;
                                                            var18_24 = -1;
                                                            var19_25 = var17_23.type;
                                                            var11_17 = var17_23.length;
                                                            var17_23 = var2_8;
                                                            if ((var11_17 = this.subParse((String)var1_1, var7_13, var19_25, var11_17, false, true, var10_16, (Calendar)var4_10, var12_18 /* !! */ , (Output<TimeZoneFormat.TimeType>)var5_11, (Output<DayPeriodRules.DayPeriod>)var9_15)) >= 0) break block66;
                                                            if (var11_17 != -32000) {
                                                                var3_9.setIndex(var8_14);
                                                                var3_9.setErrorIndex(var7_13);
                                                                if (var17_23 == null) return;
                                                                this.calendar.setTimeZone((TimeZone)var17_23);
                                                                return;
                                                            }
                                                            var20_26 /* !! */  = var13_19;
                                                            if (var16_22 + 1 >= var20_26 /* !! */ .length) break block67;
                                                            try {
                                                                var21_27 = (String)var20_26 /* !! */ [var16_22 + 1];
                                                                var17_23 = var21_27;
                                                                if (var21_27 != null) break block56;
                                                            }
                                                            catch (ClassCastException var1_2) {
                                                                var3_9.setIndex(var8_14);
                                                                var3_9.setErrorIndex(var7_13);
                                                                if (var17_23 == null) return;
                                                                this.calendar.setTimeZone((TimeZone)var17_23);
                                                                return;
                                                            }
                                                            var17_23 = (String)var20_26 /* !! */ [var16_22 + 1];
                                                        }
                                                        var22_28 = var17_23.length();
                                                        for (var11_17 = 0; var11_17 < var22_28 && PatternProps.isWhiteSpace(var17_23.charAt(var11_17)); ++var11_17) {
                                                        }
                                                        if (var11_17 == var22_28) {
                                                            ++var16_22;
                                                        }
                                                        var11_17 = var18_24;
                                                        break block65;
                                                    }
                                                    var11_17 = var18_24;
                                                    break block65;
                                                }
                                                var7_13 = var11_17;
                                                var11_17 = var18_24;
                                                break block65;
                                            }
                                            var17_23 = var2_8;
                                            var20_26 /* !! */  = new boolean[1];
                                            var7_13 = this.matchLiteral((String)var1_1, var7_13, var13_19, var16_22, var20_26 /* !! */ );
                                            if (!var20_26 /* !! */ [0]) {
                                                var3_9.setIndex(var8_14);
                                                var3_9.setErrorIndex(var7_13);
                                                if (var17_23 == null) return;
                                                this.calendar.setTimeZone((TimeZone)var17_23);
                                                return;
                                            }
                                            var11_17 = -1;
                                        }
                                        ++var16_22;
                                    }
                                    var11_17 = var7_13 < var1_1.length() && var1_1.charAt(var7_13) == '.' && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE) != false && var13_19.length != 0 && (var1_1 = var13_19[var13_19.length - 1]) instanceof PatternItem != false && ((PatternItem)var1_1).isNumeric == false ? var7_13 + 1 : var7_13;
                                    if (var9_15.value != null) {
                                        var13_19 = DayPeriodRules.getInstance(this.getLocale());
                                        var1_1 = var4_10;
                                        if (!var1_1.isSet(10) && !var1_1.isSet(11)) {
                                            var23_29 = var13_19.getMidPointForDayPeriod((DayPeriodRules.DayPeriod)var9_15.value);
                                            var16_22 = var23_29 - (double)(var7_13 = (int)var23_29) > 0.0 ? 30 : 0;
                                            var1_1.set(11, var7_13);
                                            var1_1.set(12, var16_22);
                                        } else {
                                            if (var1_1.isSet(11)) {
                                                var16_22 = var1_1.get(11);
                                            } else {
                                                var16_22 = var7_13 = var1_1.get(10);
                                                if (var7_13 == 0) {
                                                    var16_22 = 12;
                                                }
                                            }
                                            if (var16_22 != 0 && (13 > var16_22 || var16_22 > 23)) {
                                                var7_13 = var16_22;
                                                if (var16_22 == 12) {
                                                    var7_13 = 0;
                                                }
                                                if (-6.0 <= (var23_29 = (double)var7_13 + (double)var1_1.get(12) / 60.0 - var13_19.getMidPointForDayPeriod((DayPeriodRules.DayPeriod)var9_15.value)) && var23_29 < 6.0) {
                                                    var1_1.set(9, 0);
                                                } else {
                                                    var1_1.set(9, 1);
                                                }
                                            } else {
                                                var1_1.set(11, var16_22);
                                            }
                                        }
                                    }
                                    var13_19 = var4_10;
                                    var3_9.setIndex(var11_17);
                                    var4_10 = (TimeZoneFormat.TimeType)var5_11.value;
                                    var25_30 = var10_16[0];
                                    if (var25_30) break block68;
                                    try {
                                        var1_1 = TimeZoneFormat.TimeType.UNKNOWN;
                                        if (var4_10 == var1_1) ** GOTO lbl206
                                    }
                                    catch (IllegalArgumentException var1_3) {}
                                }
                                if (var25_30 = var10_16[0]) {
                                    if (!((Calendar)var13_19.clone()).getTime().before(this.getDefaultCenturyStart())) break block57;
                                    var13_19.set(1, this.getDefaultCenturyStartYear() + 100);
                                }
                            }
                            var1_1 = TimeZoneFormat.TimeType.UNKNOWN;
                            if (var4_10 == var1_1) ** GOTO lbl206
                            var5_11 = (Calendar)var13_19.clone();
                            var9_15 = var5_11.getTimeZone();
                            var1_1 = null;
                            var25_30 = var9_15 instanceof BasicTimeZone;
                            if (!var25_30) break block58;
                            var1_1 = (BasicTimeZone)var9_15;
                        }
                        var5_11.set(15, 0);
                        var5_11.set(16, 0);
                        var26_31 = var5_11.getTimeInMillis();
                        var5_11 = new int[2];
                        if (var1_1 == null) break block59;
                        if (var4_10 != TimeZoneFormat.TimeType.STANDARD) break block60;
                        var1_1.getOffsetFromLocal(var26_31, 1, 1, var5_11);
                        ** GOTO lbl185
                        break block62;
                    }
                    var1_1.getOffsetFromLocal(var26_31, 3, 3, var5_11);
                    ** GOTO lbl185
                }
                var28_32 = var26_31;
                var9_15.getOffset(var28_32, true, var5_11);
                var12_18 /* !! */  = TimeZoneFormat.TimeType.STANDARD;
                if (var4_10 == var12_18 /* !! */  && (var16_22 = var5_11[1]) != 0) break block69;
                ** try [egrp 10[TRYBLOCK] [12 : 1322->1330)] { 
lbl181: // 1 sources:
                if (var4_10 != TimeZoneFormat.TimeType.DAYLIGHT || (var16_22 = var5_11[1]) != 0) ** GOTO lbl185
            }
            try {
                block71 : {
                    block61 : {
                        block70 : {
                            var9_15.getOffset(var28_32 - 86400000L, true, var5_11);
lbl185: // 4 sources:
                            var16_22 = var5_11[1];
                            if (var4_10 != TimeZoneFormat.TimeType.STANDARD) break block70;
                            if (var5_11[1] != 0) {
                                var16_22 = 0;
                            }
                            ** GOTO lbl198
                        }
                        if (var5_11[1] != 0) ** GOTO lbl198
                        if (var1_1 != null) break block71;
                        var7_13 = var9_15.getDSTSavings();
lbl194: // 2 sources:
                        do {
                            var16_22 = var7_13;
                            if (var7_13 == 0) {
                                var16_22 = 3600000;
                            }
lbl198: // 5 sources:
                            var13_19.set(15, var5_11[0]);
                            var13_19.set(16, var16_22);
                            break block61;
                            break;
                        } while (true);
lbl202: // 2 sources:
                        catch (IllegalArgumentException var1_5) {}
                        break block62;
                        catch (IllegalArgumentException var1_6) {}
                        break block62;
                    }
                    if (var6_12 != null) {
                        var6_12.setTimeZone(var13_19.getTimeZone());
                        var6_12.setTimeInMillis(var13_19.getTimeInMillis());
                    }
                    if (var2_8 == null) return;
                    this.calendar.setTimeZone((TimeZone)var2_8);
                    return;
                    catch (IllegalArgumentException var1_7) {
                        // empty catch block
                    }
                    break block62;
                }
                var28_32 = var26_31 += (long)var5_11[0];
                var16_22 = 0;
                var7_13 = 0;
                do {
                    if ((var9_15 = var1_1.getPreviousTransition(var28_32, true)) == null) {
                        var30_34 = var26_31;
                        var32_33 = var28_32;
                        var28_32 = var30_34;
                        break;
                    }
                    var32_33 = var9_15.getTime() - 1L;
                    var16_22 = var9_15.getFrom().getDSTSavings();
                    if (var16_22 != 0) {
                        var28_32 = var26_31;
                        break;
                    }
                    var28_32 = var32_33;
                } while (true);
                while ((var4_10 = var1_1.getNextTransition(var28_32, false)) != null) {
                    var30_34 = var4_10.getTime();
                    var15_21 = var4_10.getTo().getDSTSavings();
                    var28_32 = var30_34;
                    var7_13 = var15_21;
                    if (var15_21 == 0) continue;
                    var7_13 = var15_21;
                    var28_32 = var30_34;
                    break;
                }
                if (var9_15 != null && var4_10 != null) {
                    if (var26_31 - var32_33 > var28_32 - var26_31) {
                        var16_22 = var7_13;
                    }
                } else if (var9_15 == null || var16_22 == 0) {
                    var16_22 = var4_10 != null && var7_13 != 0 ? var7_13 : var1_1.getDSTSavings();
                }
                var7_13 = var16_22;
                ** continue;
            }
            catch (IllegalArgumentException var1_4) {}
        }
        var3_9.setErrorIndex(var11_17);
        var3_9.setIndex(var8_14);
        if (var2_8 == null) return;
        this.calendar.setTimeZone((TimeZone)var2_8);
    }

    protected DateFormat.Field patternCharToDateFormatField(char c) {
        int n = SimpleDateFormat.getIndexFromChar(c);
        if (n != -1) {
            return PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[n];
        }
        return null;
    }

    public void set2DigitYearStart(Date date) {
        this.parseAmbiguousDatesAsAfter(date);
    }

    @Override
    public void setContext(DisplayContext displayContext) {
        super.setContext(displayContext);
        if (this.capitalizationBrkIter == null && (displayContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || displayContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || displayContext == DisplayContext.CAPITALIZATION_FOR_STANDALONE)) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
        }
    }

    public void setDateFormatSymbols(DateFormatSymbols dateFormatSymbols) {
        this.formatData = (DateFormatSymbols)dateFormatSymbols.clone();
    }

    @Override
    public void setNumberFormat(NumberFormat numberFormat) {
        super.setNumberFormat(numberFormat);
        this.initLocalZeroPaddingNumberFormat();
        this.initializeTimeZoneFormat(true);
        if (this.numberFormatters != null) {
            this.numberFormatters = null;
        }
        if (this.overrideMap != null) {
            this.overrideMap = null;
        }
    }

    public void setNumberFormat(String charSequence, NumberFormat numberFormat) {
        numberFormat.setGroupingUsed(false);
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append("$");
        charSequence2.append(UUID.randomUUID().toString());
        charSequence2 = charSequence2.toString();
        if (this.numberFormatters == null) {
            this.numberFormatters = new HashMap();
        }
        if (this.overrideMap == null) {
            this.overrideMap = new HashMap();
        }
        for (int i = 0; i < ((String)charSequence).length(); ++i) {
            char c = ((String)charSequence).charAt(i);
            if ("GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB".indexOf(c) != -1) {
                this.overrideMap.put(Character.valueOf(c), (String)charSequence2);
                this.numberFormatters.put((String)charSequence2, numberFormat);
                continue;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Illegal field character '");
            ((StringBuilder)charSequence).append(c);
            ((StringBuilder)charSequence).append("' in setNumberFormat.");
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        this.useLocalZeroPaddingNumberFormat = false;
    }

    public void setTimeZoneFormat(TimeZoneFormat timeZoneFormat) {
        this.tzFormat = timeZoneFormat.isFrozen() ? timeZoneFormat : timeZoneFormat.cloneAsThawed().freeze();
    }

    @Deprecated
    protected String subFormat(char c, int n, int n2, int n3, DisplayContext displayContext, FieldPosition fieldPosition, Calendar calendar) {
        StringBuffer stringBuffer = new StringBuffer();
        this.subFormat(stringBuffer, c, n, n2, n3, displayContext, fieldPosition, calendar);
        return stringBuffer.toString();
    }

    protected String subFormat(char c, int n, int n2, FieldPosition fieldPosition, DateFormatSymbols dateFormatSymbols, Calendar calendar) throws IllegalArgumentException {
        return this.subFormat(c, n, n2, 0, DisplayContext.CAPITALIZATION_NONE, fieldPosition, calendar);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Deprecated
    protected void subFormat(StringBuffer var1_1, char var2_2, int var3_3, int var4_4, int var5_5, DisplayContext var6_6, FieldPosition var7_7, Calendar var8_8) {
        var9_99 = var1_1.length();
        var10_100 = var8_10.getTimeZone();
        var11_101 = var8_10.getTimeInMillis();
        var13_102 = SimpleDateFormat.getIndexFromChar(var2_2);
        if (var13_102 == -1) {
            if (var2_2 == 'l') {
                return;
            }
            var1_1 = new StringBuilder();
            var1_1.append("Illegal pattern character '");
            var1_1.append(var2_2);
            var1_1.append("' in \"");
            var1_1.append(this.pattern);
            var1_1.append('\"');
            throw new IllegalArgumentException(var1_1.toString());
        }
        var14_103 = SimpleDateFormat.PATTERN_INDEX_TO_CALENDAR_FIELD[var13_102];
        var14_103 = var14_103 >= 0 ? (var13_102 != 34 ? var8_10.get(var14_103) : var8_10.getRelatedYear()) : 0;
        var15_104 = this.getNumberFormat(var2_2);
        var16_105 = DateFormatSymbols.CapitalizationContextUsage.OTHER;
        switch (var13_102) {
            default: {
                this.zeroPaddingNumber(var15_104, (StringBuffer)var1_1, var14_103, var3_3, Integer.MAX_VALUE);
                ** break;
            }
            case 37: {
                var1_1.append(this.formatData.getTimeSeparatorString());
                ** break;
            }
            case 36: {
                var17_106 = DayPeriodRules.getInstance(this.getLocale());
                if (var17_106 == null) {
                    this.subFormat((StringBuffer)var1_1, 'a', var3_3, var4_4, var5_5, (DisplayContext)var6_6, (FieldPosition)var7_9, (Calendar)var8_10);
                    ** break;
                }
                var18_108 = var8_10.get(11);
                var14_103 = 0;
                var19_111 = 0;
                if (this.hasMinute) {
                    var14_103 = var8_10.get(12);
                }
                if (this.hasSecond) {
                    var19_111 = var8_10.get(13);
                }
                if (var18_108 == 0 && var14_103 == 0 && var19_111 == 0 && var17_106.hasMidnight()) {
                    var20_112 = DayPeriodRules.DayPeriod.MIDNIGHT;
                } else if (var18_108 == 12 && var14_103 == 0 && var19_111 == 0 && var17_106.hasNoon()) {
                    var20_113 = DayPeriodRules.DayPeriod.NOON;
                } else {
                    var20_114 = var17_106.getDayPeriodForHour(var18_108);
                }
                var21_121 = null;
                var10_100 = var21_121;
                if (var20_115 != DayPeriodRules.DayPeriod.AM) {
                    var10_100 = var21_121;
                    if (var20_115 != DayPeriodRules.DayPeriod.PM) {
                        var10_100 = var21_121;
                        if (var20_115 != DayPeriodRules.DayPeriod.MIDNIGHT) {
                            var14_103 = var20_115.ordinal();
                            var10_100 = var3_3 <= 3 ? this.formatData.abbreviatedDayPeriods[var14_103] : (var3_3 != 4 && var3_3 <= 5 ? this.formatData.narrowDayPeriods[var14_103] : this.formatData.wideDayPeriods[var14_103]);
                        }
                    }
                }
                if (var10_100 == null && (var20_115 == DayPeriodRules.DayPeriod.MIDNIGHT || var20_115 == DayPeriodRules.DayPeriod.NOON)) {
                    var10_100 = var17_106.getDayPeriodForHour(var18_108);
                    var14_103 = var10_100.ordinal();
                    if (var3_3 <= 3) {
                        var21_121 = this.formatData.abbreviatedDayPeriods[var14_103];
                        var20_116 = var10_100;
                        var10_100 = var21_121;
                    } else if (var3_3 != 4 && var3_3 <= 5) {
                        var21_121 = this.formatData.narrowDayPeriods[var14_103];
                        var20_117 = var10_100;
                        var10_100 = var21_121;
                    } else {
                        var21_121 = this.formatData.wideDayPeriods[var14_103];
                        var20_118 = var10_100;
                        var10_100 = var21_121;
                    }
                }
                if (var20_119 != DayPeriodRules.DayPeriod.AM && var20_119 != DayPeriodRules.DayPeriod.PM && var10_100 != null) {
                    var1_1.append((String)var10_100);
                    ** break;
                }
                this.subFormat((StringBuffer)var1_1, 'a', var3_3, var4_4, var5_5, (DisplayContext)var6_6, (FieldPosition)var7_9, (Calendar)var8_10);
                ** break;
            }
            case 35: {
                if (!(var8_10.get(11) != 12 || this.hasMinute && var8_10.get(12) != 0 || this.hasSecond && var8_10.get(13) != 0)) {
                    var14_103 = var8_10.get(9);
                    var10_100 = var3_3 <= 3 ? this.formatData.abbreviatedDayPeriods[var14_103] : (var3_3 != 4 && var3_3 <= 5 ? this.formatData.narrowDayPeriods[var14_103] : this.formatData.wideDayPeriods[var14_103]);
                } else {
                    var10_100 = null;
                }
                if (var10_100 == null) {
                    this.subFormat((StringBuffer)var1_1, 'a', var3_3, var4_4, var5_5, (DisplayContext)var6_6, (FieldPosition)var7_9, (Calendar)var8_10);
                } else {
                    var1_1.append((String)var10_100);
                }
                var8_11 /* !! */  = var16_105;
                var3_3 = var13_102;
                var13_102 = 2;
                break;
            }
            case 33: {
                if (var3_3 == 1) {
                    var8_12 = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_SHORT, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 2) {
                    var8_13 = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FIXED, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 3) {
                    var8_14 = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FIXED, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 4) {
                    var8_15 = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 5) {
                    var8_16 = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FULL, (TimeZone)var10_100, var11_101);
                } else {
                    var8_17 = null;
                }
                var1_1.append((String)var8_18);
                var14_103 = 2;
                var8_19 /* !! */  = var16_105;
                var3_3 = var13_102;
                var13_102 = var14_103;
                break;
            }
            case 32: {
                if (var3_3 == 1) {
                    var8_20 = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_SHORT, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 2) {
                    var8_21 = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_FIXED, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 3) {
                    var8_22 = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FIXED, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 4) {
                    var8_23 = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_FULL, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 5) {
                    var8_24 = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FULL, (TimeZone)var10_100, var11_101);
                } else {
                    var8_25 = null;
                }
                var1_1.append((String)var8_26);
                var14_103 = 2;
                var8_27 /* !! */  = var16_105;
                var3_3 = var13_102;
                var13_102 = var14_103;
                break;
            }
            case 31: {
                if (var3_3 == 1) {
                    var8_28 = this.tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT_SHORT, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 4) {
                    var8_29 = this.tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT, (TimeZone)var10_100, var11_101);
                } else {
                    var8_30 = null;
                }
                var1_1.append((String)var8_31);
                var14_103 = 2;
                var8_32 /* !! */  = var16_105;
                var3_3 = var13_102;
                var13_102 = var14_103;
                break;
            }
            case 30: {
                var19_111 = var14_103;
                if (this.formatData.shortYearNames != null && var19_111 <= this.formatData.shortYearNames.length) {
                    SimpleDateFormat.safeAppend(this.formatData.shortYearNames, var19_111 - 1, (StringBuffer)var1_1);
                    ** break;
                }
                ** GOTO lbl435
            }
            case 29: {
                if (var3_3 == 1) {
                    var8_33 = this.tzFormat().format(TimeZoneFormat.Style.ZONE_ID_SHORT, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 2) {
                    var8_34 = this.tzFormat().format(TimeZoneFormat.Style.ZONE_ID, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 3) {
                    var8_35 = this.tzFormat().format(TimeZoneFormat.Style.EXEMPLAR_LOCATION, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 4) {
                    var8_36 = this.tzFormat().format(TimeZoneFormat.Style.GENERIC_LOCATION, (TimeZone)var10_100, var11_101);
                    var16_105 = DateFormatSymbols.CapitalizationContextUsage.ZONE_LONG;
                } else {
                    var8_37 = null;
                }
                var1_1.append((String)var8_38);
                var14_103 = 2;
                var8_39 /* !! */  = var16_105;
                var3_3 = var13_102;
                var13_102 = var14_103;
                break;
            }
            case 28: {
                if (var3_3 >= 4) {
                    SimpleDateFormat.safeAppend(this.formatData.standaloneQuarters, var14_103 / 3, (StringBuffer)var1_1);
                    ** break;
                }
                if (var3_3 == 3) {
                    SimpleDateFormat.safeAppend(this.formatData.standaloneShortQuarters, var14_103 / 3, (StringBuffer)var1_1);
                    ** break;
                }
                this.zeroPaddingNumber(var15_104, (StringBuffer)var1_1, var14_103 / 3 + 1, var3_3, Integer.MAX_VALUE);
                ** break;
            }
            case 27: {
                if (var3_3 >= 4) {
                    SimpleDateFormat.safeAppend(this.formatData.quarters, var14_103 / 3, (StringBuffer)var1_1);
                    ** break;
                }
                if (var3_3 == 3) {
                    SimpleDateFormat.safeAppend(this.formatData.shortQuarters, var14_103 / 3, (StringBuffer)var1_1);
                    ** break;
                }
                this.zeroPaddingNumber(var15_104, (StringBuffer)var1_1, var14_103 / 3 + 1, var3_3, Integer.MAX_VALUE);
                ** break;
            }
            case 25: {
                var19_111 = 2;
                if (var3_3 < 3) {
                    this.zeroPaddingNumber(var15_104, (StringBuffer)var1_1, var14_103, 1, Integer.MAX_VALUE);
                    ** break;
                }
                var14_103 = var8_10.get(7);
                if (var3_3 == 5) {
                    SimpleDateFormat.safeAppend(this.formatData.standaloneNarrowWeekdays, var14_103, (StringBuffer)var1_1);
                    var8_40 = DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW;
                    var3_3 = var13_102;
                    var13_102 = var19_111;
                    break;
                }
                if (var3_3 == 4) {
                    SimpleDateFormat.safeAppend(this.formatData.standaloneWeekdays, var14_103, (StringBuffer)var1_1);
                    var8_41 = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                    var3_3 = var13_102;
                    var13_102 = var19_111;
                    break;
                }
                if (var3_3 == 6 && this.formatData.standaloneShorterWeekdays != null) {
                    SimpleDateFormat.safeAppend(this.formatData.standaloneShorterWeekdays, var14_103, (StringBuffer)var1_1);
                    var8_42 = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                    var3_3 = var13_102;
                    var13_102 = var19_111;
                    break;
                }
                SimpleDateFormat.safeAppend(this.formatData.standaloneShortWeekdays, var14_103, (StringBuffer)var1_1);
                var8_43 = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                var3_3 = var13_102;
                var13_102 = var19_111;
                break;
            }
            case 24: {
                var14_103 = 2;
                if (var3_3 == 1) {
                    var8_44 = this.tzFormat().format(TimeZoneFormat.Style.GENERIC_SHORT, (TimeZone)var10_100, var11_101);
                    var16_105 = DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT;
                } else if (var3_3 == 4) {
                    var8_45 = this.tzFormat().format(TimeZoneFormat.Style.GENERIC_LONG, (TimeZone)var10_100, var11_101);
                    var16_105 = DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG;
                } else {
                    var8_46 = null;
                }
                var1_1.append((String)var8_47);
                var8_48 /* !! */  = var16_105;
                var3_3 = var13_102;
                var13_102 = var14_103;
                break;
            }
            case 23: {
                var14_103 = 2;
                if (var3_3 < 4) {
                    var8_49 = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL, (TimeZone)var10_100, var11_101);
                } else if (var3_3 == 5) {
                    var8_50 = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FULL, (TimeZone)var10_100, var11_101);
                } else {
                    var8_51 = this.tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT, (TimeZone)var10_100, var11_101);
                }
                var1_1.append((String)var8_52);
                var8_53 /* !! */  = var16_105;
                var3_3 = var13_102;
                var13_102 = var14_103;
                break;
            }
            case 19: {
                if (var3_3 < 3) {
                    this.zeroPaddingNumber(var15_104, (StringBuffer)var1_1, var14_103, var3_3, Integer.MAX_VALUE);
                    ** break;
                }
                var14_103 = var8_10.get(7);
                ** GOTO lbl298
            }
            case 17: {
                var14_103 = 2;
                if (var3_3 < 4) {
                    var16_105 = this.tzFormat().format(TimeZoneFormat.Style.SPECIFIC_SHORT, (TimeZone)var10_100, var11_101);
                    var8_54 = DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT;
                } else {
                    var16_105 = this.tzFormat().format(TimeZoneFormat.Style.SPECIFIC_LONG, (TimeZone)var10_100, var11_101);
                    var8_55 = DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG;
                }
                var1_1.append((String)var16_105);
                var3_3 = var13_102;
                var13_102 = var14_103;
                break;
            }
            case 15: {
                if (var14_103 == 0) {
                    this.zeroPaddingNumber(var15_104, (StringBuffer)var1_1, var8_10.getLeastMaximum(10) + 1, var3_3, Integer.MAX_VALUE);
                    ** break;
                }
                this.zeroPaddingNumber(var15_104, (StringBuffer)var1_1, var14_103, var3_3, Integer.MAX_VALUE);
                ** break;
            }
            case 14: {
                if (var3_3 >= 5 && this.formatData.ampmsNarrow != null) {
                    SimpleDateFormat.safeAppend(this.formatData.ampmsNarrow, var14_103, (StringBuffer)var1_1);
                    ** break;
                }
                SimpleDateFormat.safeAppend(this.formatData.ampms, var14_103, (StringBuffer)var1_1);
                ** break;
            }
lbl298: // 2 sources:
            case 9: {
                var19_111 = 2;
                if (var3_3 == 5) {
                    SimpleDateFormat.safeAppend(this.formatData.narrowWeekdays, var14_103, (StringBuffer)var1_1);
                    var8_57 = DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW;
                    var3_3 = var13_102;
                    var13_102 = var19_111;
                    break;
                }
                if (var3_3 == 4) {
                    SimpleDateFormat.safeAppend(this.formatData.weekdays, var14_103, (StringBuffer)var1_1);
                    var8_58 = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
                    var3_3 = var13_102;
                    var13_102 = var19_111;
                    break;
                }
                if (var3_3 == 6 && this.formatData.shorterWeekdays != null) {
                    SimpleDateFormat.safeAppend(this.formatData.shorterWeekdays, var14_103, (StringBuffer)var1_1);
                    var8_59 = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
                    var3_3 = var13_102;
                    var13_102 = var19_111;
                    break;
                }
                SimpleDateFormat.safeAppend(this.formatData.shortWeekdays, var14_103, (StringBuffer)var1_1);
                var8_60 = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
                var3_3 = var13_102;
                var13_102 = var19_111;
                break;
            }
            case 8: {
                var19_111 = 2;
                this.numberFormat.setMinimumIntegerDigits(Math.min(3, var3_3));
                this.numberFormat.setMaximumIntegerDigits(Integer.MAX_VALUE);
                if (var3_3 == 1) {
                    var14_103 /= 100;
                } else if (var3_3 == 2) {
                    var14_103 /= 10;
                }
                var8_61 = new FieldPosition(-1);
                this.numberFormat.format(var14_103, (StringBuffer)var1_1, var8_61);
                if (var3_3 > 3) {
                    this.numberFormat.setMinimumIntegerDigits(var3_3 - 3);
                    this.numberFormat.format(0L, (StringBuffer)var1_1, var8_61);
                }
                var8_62 /* !! */  = var16_105;
                var3_3 = var13_102;
                var13_102 = var19_111;
                break;
            }
            case 4: {
                if (var14_103 == 0) {
                    this.zeroPaddingNumber(var15_104, (StringBuffer)var1_1, var8_10.getMaximum(11) + 1, var3_3, Integer.MAX_VALUE);
                    ** break;
                }
                this.zeroPaddingNumber(var15_104, (StringBuffer)var1_1, var14_103, var3_3, Integer.MAX_VALUE);
                ** break;
            }
            case 2: 
            case 26: {
                var19_111 = var14_103;
                var22_123 = 2;
                var14_103 = var19_111;
                if (var8_10.getType().equals("hebrew")) {
                    var23_124 = HebrewCalendar.isLeapYear(var8_10.get(1));
                    var14_103 = var23_124 != false && var19_111 == 6 && var3_3 >= 3 ? 13 : var19_111;
                    if (!var23_124 && var14_103 >= 6 && var3_3 < 3) {
                        --var14_103;
                    }
                }
                var19_111 = this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7 ? var8_10.get(22) : 0;
                var20_120 = null;
                var21_122 = null;
                var17_107 = null;
                var24_125 = null;
                var25_126 = null;
                var8_63 = null;
                var10_100 = null;
                if (var3_3 == 5) {
                    if (var13_102 == 2) {
                        var16_105 = this.formatData.narrowMonths;
                        var8_64 = var10_100;
                        if (var19_111 != 0) {
                            var8_65 = this.formatData.leapMonthPatterns[2];
                        }
                        SimpleDateFormat.safeAppendWithMonthPattern(var16_105, var14_103, (StringBuffer)var1_1, (String)var8_66);
                    } else {
                        var16_105 = this.formatData.standaloneNarrowMonths;
                        var8_67 = var20_120;
                        if (var19_111 != 0) {
                            var8_68 = this.formatData.leapMonthPatterns[5];
                        }
                        SimpleDateFormat.safeAppendWithMonthPattern(var16_105, var14_103, (StringBuffer)var1_1, (String)var8_69);
                    }
                    var8_71 = DateFormatSymbols.CapitalizationContextUsage.MONTH_NARROW;
                    var3_3 = var13_102;
                    var13_102 = var22_123;
                    break;
                }
                if (var3_3 == 4) {
                    if (var13_102 == 2) {
                        var16_105 = this.formatData.months;
                        var8_72 = var21_122;
                        if (var19_111 != 0) {
                            var8_73 = this.formatData.leapMonthPatterns[0];
                        }
                        SimpleDateFormat.safeAppendWithMonthPattern(var16_105, var14_103, (StringBuffer)var1_1, (String)var8_74);
                        var8_75 = DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT;
                        var3_3 = var13_102;
                        var13_102 = var22_123;
                        break;
                    }
                    var16_105 = this.formatData.standaloneMonths;
                    if (var19_111 != 0) {
                        var8_76 = this.formatData.leapMonthPatterns[3];
                    } else {
                        var8_77 = var17_107;
                    }
                    SimpleDateFormat.safeAppendWithMonthPattern(var16_105, var14_103, (StringBuffer)var1_1, (String)var8_78);
                    var8_79 = DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE;
                    var3_3 = var13_102;
                    var13_102 = var22_123;
                    break;
                }
                if (var3_3 == 3) {
                    if (var13_102 == 2) {
                        var16_105 = this.formatData.shortMonths;
                        var8_80 = var24_125;
                        if (var19_111 != 0) {
                            var8_81 = this.formatData.leapMonthPatterns[1];
                        }
                        SimpleDateFormat.safeAppendWithMonthPattern(var16_105, var14_103, (StringBuffer)var1_1, (String)var8_82);
                        var8_83 = DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT;
                        var3_3 = var13_102;
                        var13_102 = var22_123;
                        break;
                    }
                    var16_105 = this.formatData.standaloneShortMonths;
                    var8_84 = var25_126;
                    if (var19_111 != 0) {
                        var8_85 = this.formatData.leapMonthPatterns[4];
                    }
                    SimpleDateFormat.safeAppendWithMonthPattern(var16_105, var14_103, (StringBuffer)var1_1, (String)var8_86);
                    var8_87 = DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE;
                    var3_3 = var13_102;
                    var13_102 = var22_123;
                    break;
                }
                var10_100 = new StringBuffer();
                var18_109 = var13_102;
                this.zeroPaddingNumber(var15_104, (StringBuffer)var10_100, var14_103 + 1, var3_3, Integer.MAX_VALUE);
                var10_100 = var10_100.toString();
                if (var19_111 != 0) {
                    var8_88 = this.formatData.leapMonthPatterns[6];
                }
                SimpleDateFormat.safeAppendWithMonthPattern(new String[]{var10_100}, 0, (StringBuffer)var1_1, (String)var8_89);
                var8_90 = var16_105;
                var13_102 = var22_123;
                var3_3 = var18_109;
                break;
            }
lbl435: // 2 sources:
            case 1: 
            case 18: {
                var19_111 = var14_103;
                var8_91 = this.override;
                var14_103 = var19_111;
                if (var8_91 == null) ** GOTO lbl448
                if (var8_91.compareTo("hebr") == 0) ** GOTO lbl443
                var14_103 = var19_111;
                if (this.override.indexOf("y=hebr") < 0) ** GOTO lbl448
lbl443: // 2 sources:
                var14_103 = var19_111;
                if (var19_111 > 5000) {
                    var14_103 = var19_111;
                    if (var19_111 < 6000) {
                        var14_103 = var19_111 - 5000;
                    }
                }
lbl448: // 7 sources:
                if (var3_3 == 2) {
                    this.zeroPaddingNumber(var15_104, (StringBuffer)var1_1, var14_103, 2, 2);
                    ** break;
                }
                this.zeroPaddingNumber(var15_104, (StringBuffer)var1_1, var14_103, var3_3, Integer.MAX_VALUE);
                ** break;
            }
            case 0: {
                var19_111 = var13_102;
                var18_110 = 2;
                if (!var8_10.getType().equals("chinese") && !var8_10.getType().equals("dangi")) {
                    if (var3_3 == 5) {
                        SimpleDateFormat.safeAppend(this.formatData.narrowEras, var14_103, (StringBuffer)var1_1);
                        var8_92 = DateFormatSymbols.CapitalizationContextUsage.ERA_NARROW;
                        var13_102 = var18_110;
                        var3_3 = var19_111;
                        break;
                    }
                    if (var3_3 == 4) {
                        SimpleDateFormat.safeAppend(this.formatData.eraNames, var14_103, (StringBuffer)var1_1);
                        var8_93 = DateFormatSymbols.CapitalizationContextUsage.ERA_WIDE;
                        var13_102 = var18_110;
                        var3_3 = var19_111;
                        break;
                    }
                    SimpleDateFormat.safeAppend(this.formatData.eras, var14_103, (StringBuffer)var1_1);
                    var8_94 = DateFormatSymbols.CapitalizationContextUsage.ERA_ABBREV;
                    var13_102 = var18_110;
                    var3_3 = var19_111;
                    break;
                }
                this.zeroPaddingNumber(var15_104, (StringBuffer)var1_1, var14_103, 1, 9);
lbl475: // 23 sources:
                var3_3 = var13_102;
                var13_102 = 2;
                var8_96 = var16_105;
            }
        }
        var14_103 = var9_99;
        if (var5_5 == 0 && var6_6 != null && UCharacter.isLowerCase(var1_1.codePointAt(var5_5 = var14_103))) {
            var23_124 = false;
            var19_111 = 1.$SwitchMap$android$icu$text$DisplayContext[var6_6.ordinal()];
            if (var19_111 != 1) {
                if ((var19_111 == var13_102 || var19_111 == 3) && this.formatData.capitalization != null) {
                    var8_98 = this.formatData.capitalization.get(var8_97);
                    var23_124 = var6_6 == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU ? var8_98[0] : var8_98[1];
                }
            } else {
                var23_124 = true;
            }
            if (var23_124) {
                if (this.capitalizationBrkIter == null) {
                    this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
                }
                var6_7 = var1_1.substring(var5_5);
                var6_8 = UCharacter.toTitleCase(this.locale, var6_7, this.capitalizationBrkIter, 768);
                var1_1.replace(var5_5, var1_1.length(), var6_8);
            }
        }
        if (var7_9.getBeginIndex() != var7_9.getEndIndex()) return;
        if (var7_9.getField() == SimpleDateFormat.PATTERN_INDEX_TO_DATE_FORMAT_FIELD[var3_3]) {
            var7_9.setBeginIndex(var4_4);
            var7_9.setEndIndex(var1_1.length() + var4_4 - var14_103);
            return;
        }
        if (var7_9.getFieldAttribute() != SimpleDateFormat.PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[var3_3]) return;
        var7_9.setBeginIndex(var4_4);
        var7_9.setEndIndex(var1_1.length() + var4_4 - var14_103);
    }

    protected int subParse(String string, int n, char c, int n2, boolean bl, boolean bl2, boolean[] arrbl, Calendar calendar) {
        return this.subParse(string, n, c, n2, bl, bl2, arrbl, calendar, null, null);
    }

    public String toLocalizedPattern() {
        return this.translatePattern(this.pattern, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB", this.formatData.localPatternChars);
    }

    public String toPattern() {
        return this.pattern;
    }

    protected String zeroPaddingNumber(long l, int n, int n2) {
        this.numberFormat.setMinimumIntegerDigits(n);
        this.numberFormat.setMaximumIntegerDigits(n2);
        return this.numberFormat.format(l);
    }

    @Deprecated
    protected void zeroPaddingNumber(NumberFormat numberFormat, StringBuffer stringBuffer, int n, int n2, int n3) {
        if (this.useLocalZeroPaddingNumberFormat && n >= 0) {
            this.fastZeroPaddingNumber(stringBuffer, n, n2, n3);
        } else {
            numberFormat.setMinimumIntegerDigits(n2);
            numberFormat.setMaximumIntegerDigits(n3);
            numberFormat.format(n, stringBuffer, new FieldPosition(-1));
        }
    }

    private static enum ContextValue {
        UNKNOWN,
        CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE,
        CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE,
        CAPITALIZATION_FOR_UI_LIST_OR_MENU,
        CAPITALIZATION_FOR_STANDALONE;
        
    }

    private static class PatternItem {
        final boolean isNumeric;
        final int length;
        final char type;

        PatternItem(char c, int n) {
            this.type = c;
            this.length = n;
            this.isNumeric = SimpleDateFormat.isNumeric(c, n);
        }
    }

}

