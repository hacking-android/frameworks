/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.TimeZoneNames
 *  android.icu.text.TimeZoneNames$MatchInfo
 *  android.icu.text.TimeZoneNames$NameType
 *  android.icu.util.TimeZone
 *  android.icu.util.ULocale
 *  libcore.icu.LocaleData
 *  libcore.icu.TimeZoneNames
 */
package java.text;

import android.icu.text.TimeZoneNames;
import android.icu.util.ULocale;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.text.AttributedCharacterIterator;
import java.text.CalendarBuilder;
import java.text.CharacterIteratorFieldDelegate;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.DontCareFieldPosition;
import java.text.FieldPosition;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import libcore.icu.LocaleData;
import sun.util.calendar.CalendarUtils;

public class SimpleDateFormat
extends DateFormat {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Set<TimeZoneNames.NameType> DST_NAME_TYPES;
    private static final String GMT = "GMT";
    private static final int MILLIS_PER_MINUTE = 60000;
    private static final EnumSet<TimeZoneNames.NameType> NAME_TYPES;
    private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD;
    private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD;
    private static final DateFormat.Field[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID;
    private static final int[] REST_OF_STYLES;
    private static final int TAG_QUOTE_ASCII_CHAR = 100;
    private static final int TAG_QUOTE_CHARS = 101;
    private static final ConcurrentMap<Locale, NumberFormat> cachedNumberFormatData;
    static final int currentSerialVersion = 1;
    static final long serialVersionUID = 4774881970558875024L;
    private transient char[] compiledPattern;
    private Date defaultCenturyStart;
    private transient int defaultCenturyStartYear;
    private DateFormatSymbols formatData;
    private transient boolean hasFollowingMinusSign = false;
    private Locale locale;
    private transient char minusSign = (char)45;
    private transient NumberFormat originalNumberFormat;
    private transient String originalNumberPattern;
    private String pattern;
    private int serialVersionOnStream = 1;
    private transient TimeZoneNames timeZoneNames;
    transient boolean useDateFormatSymbols;
    private transient char zeroDigit;

    static {
        cachedNumberFormatData = new ConcurrentHashMap<Locale, NumberFormat>(3);
        PATTERN_INDEX_TO_CALENDAR_FIELD = new int[]{0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15, 15, 17, 1000, 15, 2, 7, 9, 9};
        PATTERN_INDEX_TO_DATE_FORMAT_FIELD = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 17, 1, 9, 17, 2, 9, 14, 14};
        PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID = new DateFormat.Field[]{DateFormat.Field.ERA, DateFormat.Field.YEAR, DateFormat.Field.MONTH, DateFormat.Field.DAY_OF_MONTH, DateFormat.Field.HOUR_OF_DAY1, DateFormat.Field.HOUR_OF_DAY0, DateFormat.Field.MINUTE, DateFormat.Field.SECOND, DateFormat.Field.MILLISECOND, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.DAY_OF_YEAR, DateFormat.Field.DAY_OF_WEEK_IN_MONTH, DateFormat.Field.WEEK_OF_YEAR, DateFormat.Field.WEEK_OF_MONTH, DateFormat.Field.AM_PM, DateFormat.Field.HOUR1, DateFormat.Field.HOUR0, DateFormat.Field.TIME_ZONE, DateFormat.Field.TIME_ZONE, DateFormat.Field.YEAR, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.TIME_ZONE, DateFormat.Field.MONTH, DateFormat.Field.DAY_OF_WEEK, DateFormat.Field.AM_PM, DateFormat.Field.AM_PM};
        NAME_TYPES = EnumSet.of(TimeZoneNames.NameType.LONG_GENERIC, new TimeZoneNames.NameType[]{TimeZoneNames.NameType.LONG_STANDARD, TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.SHORT_GENERIC, TimeZoneNames.NameType.SHORT_STANDARD, TimeZoneNames.NameType.SHORT_DAYLIGHT});
        DST_NAME_TYPES = Collections.unmodifiableSet(EnumSet.of(TimeZoneNames.NameType.LONG_DAYLIGHT, TimeZoneNames.NameType.SHORT_DAYLIGHT));
        REST_OF_STYLES = new int[]{32769, 2, 32770};
    }

    public SimpleDateFormat() {
        this(3, 3, Locale.getDefault(Locale.Category.FORMAT));
    }

    SimpleDateFormat(int n, int n2, Locale locale) {
        this(SimpleDateFormat.getDateTimeFormat(n, n2, locale), locale);
    }

    public SimpleDateFormat(String string) {
        this(string, Locale.getDefault(Locale.Category.FORMAT));
    }

    public SimpleDateFormat(String string, DateFormatSymbols dateFormatSymbols) {
        if (string != null && dateFormatSymbols != null) {
            this.pattern = string;
            this.formatData = (DateFormatSymbols)dateFormatSymbols.clone();
            this.locale = Locale.getDefault(Locale.Category.FORMAT);
            this.initializeCalendar(this.locale);
            this.initialize(this.locale);
            this.useDateFormatSymbols = true;
            return;
        }
        throw new NullPointerException();
    }

    public SimpleDateFormat(String string, Locale locale) {
        if (string != null && locale != null) {
            this.initializeCalendar(locale);
            this.pattern = string;
            this.formatData = DateFormatSymbols.getInstanceRef(locale);
            this.locale = locale;
            this.initialize(locale);
            return;
        }
        throw new NullPointerException();
    }

    private void applyPatternImpl(String string) {
        this.compiledPattern = this.compile(string);
        this.pattern = string;
    }

    private void checkNegativeNumberExpression() {
        if (this.numberFormat instanceof DecimalFormat && !this.numberFormat.equals(this.originalNumberFormat)) {
            String string = ((DecimalFormat)this.numberFormat).toPattern();
            if (!string.equals(this.originalNumberPattern)) {
                this.hasFollowingMinusSign = false;
                int n = string.indexOf(59);
                if (n > -1 && (n = string.indexOf(45, n)) > string.lastIndexOf(48) && n > string.lastIndexOf(35)) {
                    this.hasFollowingMinusSign = true;
                    this.minusSign = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getMinusSign();
                }
                this.originalNumberPattern = string;
            }
            this.originalNumberFormat = this.numberFormat;
        }
    }

    private char[] compile(String object) {
        int n = ((String)object).length();
        int n2 = 0;
        StringBuilder stringBuilder = new StringBuilder(n * 2);
        StringBuilder stringBuilder2 = null;
        int n3 = 0;
        int n4 = -1;
        for (int i = 0; i < n; ++i) {
            int n5;
            int n6;
            char c = ((String)object).charAt(i);
            if (c == '\'') {
                if (i + 1 < n && (c = ((String)object).charAt(i + 1)) == '\'') {
                    n5 = i + 1;
                    n6 = n3;
                    i = n4;
                    if (n3 != 0) {
                        SimpleDateFormat.encode(n4, n3, stringBuilder);
                        i = -1;
                        n6 = 0;
                    }
                    if (n2 != 0) {
                        stringBuilder2.append(c);
                        n3 = n6;
                        n4 = i;
                        i = n5;
                        continue;
                    }
                    stringBuilder.append((char)(c | 25600));
                    n3 = n6;
                    n4 = i;
                    i = n5;
                    continue;
                }
                if (n2 == 0) {
                    n5 = n3;
                    n6 = n4;
                    if (n3 != 0) {
                        SimpleDateFormat.encode(n4, n3, stringBuilder);
                        n6 = -1;
                        n5 = 0;
                    }
                    if (stringBuilder2 == null) {
                        stringBuilder2 = new StringBuilder(n);
                    } else {
                        stringBuilder2.setLength(0);
                    }
                    n2 = 1;
                    n3 = n5;
                    n4 = n6;
                    continue;
                }
                n2 = stringBuilder2.length();
                if (n2 == 1) {
                    c = stringBuilder2.charAt(0);
                    if (c < '') {
                        stringBuilder.append((char)(c | 25600));
                    } else {
                        stringBuilder.append('\u6501');
                        stringBuilder.append(c);
                    }
                } else {
                    SimpleDateFormat.encode(101, n2, stringBuilder);
                    stringBuilder.append(stringBuilder2);
                }
                n2 = 0;
                continue;
            }
            if (n2 != 0) {
                stringBuilder2.append(c);
                continue;
            }
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                n6 = "GyMdkHmsSEDFwWahKzZYuXLcbB".indexOf(c);
                if (n6 != -1) {
                    if (n4 != -1 && n4 != n6) {
                        SimpleDateFormat.encode(n4, n3, stringBuilder);
                        n4 = n6;
                        n3 = 1;
                        continue;
                    }
                    n4 = n6;
                    ++n3;
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Illegal pattern character '");
                ((StringBuilder)object).append(c);
                ((StringBuilder)object).append("'");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            n5 = n3;
            n6 = n4;
            if (n3 != 0) {
                SimpleDateFormat.encode(n4, n3, stringBuilder);
                n6 = -1;
                n5 = 0;
            }
            if (c < '') {
                stringBuilder.append((char)(c | 25600));
                n3 = n5;
                n4 = n6;
                continue;
            }
            for (n3 = i + 1; !(n3 >= n || (n4 = (int)((String)object).charAt(n3)) == 39 || n4 >= 97 && n4 <= 122 || n4 >= 65 && n4 <= 90); ++n3) {
            }
            stringBuilder.append((char)(n3 - i | 25856));
            while (i < n3) {
                stringBuilder.append(((String)object).charAt(i));
                ++i;
            }
            --i;
            n4 = n6;
            n3 = n5;
        }
        if (n2 == 0) {
            if (n3 != 0) {
                SimpleDateFormat.encode(n4, n3, stringBuilder);
            }
            n3 = stringBuilder.length();
            object = new char[n3];
            stringBuilder.getChars(0, n3, (char[])object, 0);
            return object;
        }
        throw new IllegalArgumentException("Unterminated quote");
    }

    private static void encode(int n, int n2, StringBuilder stringBuilder) {
        if (n == 21 && n2 >= 4) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("invalid ISO 8601 format: length=");
            stringBuilder.append(n2);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (n2 < 255) {
            stringBuilder.append((char)(n << 8 | n2));
        } else {
            stringBuilder.append((char)(255 | n << 8));
            stringBuilder.append((char)(n2 >>> 16));
            stringBuilder.append((char)(65535 & n2));
        }
    }

    private StringBuffer format(Date arrc, StringBuffer stringBuffer, Format.FieldDelegate fieldDelegate) {
        this.calendar.setTime((Date)arrc);
        boolean bl = this.useDateFormatSymbols();
        int n = 0;
        while (n < (arrc = this.compiledPattern).length) {
            int n2 = arrc[n] >>> 8;
            int n3 = n + 1;
            int n4 = arrc[n] & 255;
            if (n4 == 255) {
                n = n3 + 1;
                n4 = arrc[n3] << 16 | arrc[n];
                ++n;
            } else {
                n = n3;
            }
            if (n2 != 100) {
                if (n2 != 101) {
                    this.subFormat(n2, n4, fieldDelegate, stringBuffer, bl);
                    continue;
                }
                stringBuffer.append(this.compiledPattern, n, n4);
                n += n4;
                continue;
            }
            stringBuffer.append((char)n4);
        }
        return stringBuffer;
    }

    private String formatMonth(int n, int n2, int n3, StringBuffer stringBuffer, boolean bl, boolean bl2, int n4, int n5) {
        Object object = null;
        if (bl) {
            Object object2;
            if (n == 4) {
                object2 = this.formatData;
                object2 = bl2 ? ((DateFormatSymbols)object2).getStandAloneMonths() : ((DateFormatSymbols)object2).getMonths();
            } else if (n == 5) {
                object2 = this.formatData;
                object2 = bl2 ? ((DateFormatSymbols)object2).getTinyStandAloneMonths() : ((DateFormatSymbols)object2).getTinyMonths();
            } else if (n == 3) {
                object2 = this.formatData;
                object2 = bl2 ? ((DateFormatSymbols)object2).getShortStandAloneMonths() : ((DateFormatSymbols)object2).getShortMonths();
            } else {
                object2 = null;
            }
            if (object2 != null) {
                object = object2[n2];
            }
        } else if (n < 3) {
            object = null;
        } else {
            int n6 = n5;
            if (bl2) {
                n6 = Calendar.toStandaloneStyle(n5);
            }
            object = this.calendar.getDisplayName(n4, n6, this.locale);
        }
        if (object == null) {
            this.zeroPaddingNumber(n2 + 1, n, n3, stringBuffer);
        }
        return object;
    }

    private String formatWeekday(int n, int n2, boolean bl, boolean bl2) {
        if (bl) {
            String[] arrstring;
            if (n == 4) {
                arrstring = this.formatData;
                arrstring = bl2 ? arrstring.getStandAloneWeekdays() : arrstring.getWeekdays();
            } else if (n == 5) {
                arrstring = this.formatData;
                arrstring = bl2 ? arrstring.getTinyStandAloneWeekdays() : arrstring.getTinyWeekdays();
            } else {
                arrstring = this.formatData;
                arrstring = bl2 ? arrstring.getShortStandAloneWeekdays() : arrstring.getShortWeekdays();
            }
            return arrstring[n2];
        }
        return null;
    }

    private static String getDateTimeFormat(int n, int n2, Locale locale) {
        locale = LocaleData.get((Locale)locale);
        if (n >= 0 && n2 >= 0) {
            return MessageFormat.format("{0} {1}", locale.getDateFormat(n2), locale.getTimeFormat(n));
        }
        if (n >= 0) {
            return locale.getTimeFormat(n);
        }
        if (n2 >= 0) {
            return locale.getDateFormat(n2);
        }
        throw new IllegalArgumentException("No date or time style specified");
    }

    private Map<String, Integer> getDisplayNamesMap(int n, Locale locale) {
        Map<String, Integer> map = this.calendar.getDisplayNames(n, 1, locale);
        for (int n2 : REST_OF_STYLES) {
            Map<String, Integer> map2 = this.calendar.getDisplayNames(n, n2, locale);
            if (map2 == null) continue;
            map.putAll(map2);
        }
        return map;
    }

    private TimeZoneNames getTimeZoneNames() {
        if (this.timeZoneNames == null) {
            this.timeZoneNames = TimeZoneNames.getInstance((Locale)this.locale);
        }
        return this.timeZoneNames;
    }

    private void initialize(Locale locale) {
        this.compiledPattern = this.compile(this.pattern);
        this.numberFormat = (NumberFormat)cachedNumberFormatData.get(locale);
        if (this.numberFormat == null) {
            this.numberFormat = NumberFormat.getIntegerInstance(locale);
            this.numberFormat.setGroupingUsed(false);
            cachedNumberFormatData.putIfAbsent(locale, this.numberFormat);
        }
        this.numberFormat = (NumberFormat)this.numberFormat.clone();
        this.initializeDefaultCentury();
    }

    private void initializeCalendar(Locale locale) {
        if (this.calendar == null) {
            this.calendar = Calendar.getInstance(TimeZone.getDefault(), locale);
        }
    }

    private void initializeDefaultCentury() {
        this.calendar.setTimeInMillis(System.currentTimeMillis());
        this.calendar.add(1, -80);
        this.parseAmbiguousDatesAsAfter(this.calendar.getTime());
    }

    private boolean isDigit(char c) {
        boolean bl = c >= '0' && c <= '9';
        return bl;
    }

    private int matchString(String string, int n, int n2, Map<String, Integer> map, CalendarBuilder calendarBuilder) {
        block6 : {
            if (map == null) break block6;
            String string2 = null;
            for (String string3 : map.keySet()) {
                String string4;
                block8 : {
                    int n3;
                    block7 : {
                        n3 = string3.length();
                        if (string2 == null) break block7;
                        string4 = string2;
                        if (n3 <= string2.length()) break block8;
                    }
                    string4 = string2;
                    if (string.regionMatches(true, n, string3, 0, n3)) {
                        string4 = string3;
                    }
                }
                string2 = string4;
            }
            if (string2 != null) {
                calendarBuilder.set(n2, map.get(string2));
                return string2.length() + n;
            }
        }
        return -n;
    }

    private int matchString(String string, int n, int n2, String[] arrstring, CalendarBuilder calendarBuilder) {
        int n3 = 0;
        int n4 = arrstring.length;
        if (n2 == 7) {
            n3 = 1;
        }
        int n5 = 0;
        int n6 = -1;
        while (n3 < n4) {
            int n7 = arrstring[n3].length();
            int n8 = n6;
            int n9 = n5;
            if (n7 > n5) {
                n8 = n6;
                n9 = n5;
                if (string.regionMatches(true, n, arrstring[n3], 0, n7)) {
                    n8 = n3;
                    n9 = n7;
                }
            }
            n6 = n8;
            n5 = n9;
            if (arrstring[n3].charAt(n7 - 1) == '.') {
                n6 = n8;
                n5 = n9;
                if (n7 - 1 > n9) {
                    n6 = n8;
                    n5 = n9;
                    if (string.regionMatches(true, n, arrstring[n3], 0, n7 - 1)) {
                        n6 = n3;
                        n5 = n7 - 1;
                    }
                }
            }
            ++n3;
        }
        if (n6 >= 0) {
            calendarBuilder.set(n2, n6);
            return n + n5;
        }
        return -n;
    }

    private int matchZoneString(String string, int n, String[] arrstring) {
        for (int i = 1; i <= 4; ++i) {
            String string2 = arrstring[i];
            if (!string.regionMatches(true, n, string2, 0, string2.length())) continue;
            return i;
        }
        return -1;
    }

    private void parseAmbiguousDatesAsAfter(Date date) {
        this.defaultCenturyStart = date;
        this.calendar.setTime(date);
        this.defaultCenturyStartYear = this.calendar.get(1);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Date parseInternal(String object, ParsePosition parsePosition) {
        this.checkNegativeNumberExpression();
        n2 = parsePosition.index;
        n3 = object.length();
        arrbl = new boolean[]{false};
        calendarBuilder = new CalendarBuilder();
        n4 = 0;
        n = n2;
        block5 : do {
            block17 : {
                block18 : {
                    block15 : {
                        block16 : {
                            if (n4 >= ((char[])(object2 = this.compiledPattern)).length) break block15;
                            n5 = object2[n4] >>> 8;
                            object3 /* !! */  = n4 + 1;
                            n6 = object2[n4] & 255;
                            if (n6 == 255) {
                                n4 = object3 /* !! */  + 1;
                                n6 = object2[object3 /* !! */ ];
                                n6 = object2[n4] | n6 << 16;
                                ++n4;
                            } else {
                                n4 = object3 /* !! */ ;
                            }
                            if (n5 == 100) break block16;
                            if (n5 != 101) {
                                bl2 = false;
                                object2 = this.compiledPattern;
                                if (n4 < ((Object)object2).length) {
                                    object3 /* !! */  = (int)(object2[n4] >>> 8);
                                    bl = bl2;
                                    if (object3 /* !! */  != 100) {
                                        bl = bl2;
                                        if (object3 /* !! */  != 101) {
                                            bl = true;
                                        }
                                    }
                                    bl2 = this.hasFollowingMinusSign && (object3 /* !! */  == 100 || object3 /* !! */  == 101) && (object3 /* !! */  = object3 /* !! */  == 100 ? this.compiledPattern[n4] & 255 : this.compiledPattern[n4 + 1]) == this.minusSign;
                                } else {
                                    bl = false;
                                    bl2 = false;
                                }
                                if ((n = this.subParse((String)object, n, n5, n6, bl, arrbl, parsePosition, bl2, calendarBuilder)) >= 0) continue;
                                parsePosition.index = n2;
                                return null;
                            }
                            break block17;
                        }
                        if (n < n3 && object.charAt(n) == (char)n6) {
                            ++n;
                            continue;
                        }
                        parsePosition.index = n2;
                        parsePosition.errorIndex = n;
                        return null;
                    }
                    parsePosition.index = n;
                    object = this.calendar;
                    try {
                        object = object2 = calendarBuilder.establish((Calendar)object).getTime();
                    }
                    catch (IllegalArgumentException illegalArgumentException) {}
                    if (arrbl[0] == false) return object;
                    object = object2;
                    if (object2.before(this.defaultCenturyStart) == false) return object;
                    return calendarBuilder.addYear(100).establish(this.calendar).getTime();
                    break block18;
                    catch (IllegalArgumentException illegalArgumentException) {
                        // empty catch block
                    }
                }
                parsePosition.errorIndex = n;
                parsePosition.index = n2;
                return null;
            }
            do {
                if (n6 > 0) ** break;
                continue block5;
                if (n >= n3 || object.charAt(n) != this.compiledPattern[n4]) break block5;
                ++n;
                --n6;
                ++n4;
            } while (true);
            break;
        } while (true);
        parsePosition.index = n2;
        parsePosition.errorIndex = n;
        return null;
    }

    private int parseMonth(String string, int n, int n2, int n3, int n4, ParsePosition object, boolean bl, boolean bl2, CalendarBuilder calendarBuilder) {
        if (n <= 2) {
            calendarBuilder.set(2, n2 - 1);
            return ((ParsePosition)object).index;
        }
        if (bl) {
            object = this.formatData;
            object = bl2 ? ((DateFormatSymbols)object).getStandAloneMonths() : ((DateFormatSymbols)object).getMonths();
            n = this.matchString(string, n3, 2, (String[])object, calendarBuilder);
            if (n > 0) {
                return n;
            }
            object = this.formatData;
            object = bl2 ? ((DateFormatSymbols)object).getShortStandAloneMonths() : ((DateFormatSymbols)object).getShortMonths();
            n = n2 = this.matchString(string, n3, 2, (String[])object, calendarBuilder);
            if (n2 > 0) {
                return n;
            }
        } else {
            n = n2 = (n3 = this.matchString(string, n3, n4, this.getDisplayNamesMap(n4, this.locale), calendarBuilder));
            if (n3 > 0) {
                return n2;
            }
        }
        return n;
    }

    private int parseWeekday(String string, int n, int n2, boolean bl, boolean bl2, CalendarBuilder calendarBuilder) {
        int n3;
        if (bl) {
            Object object = this.formatData;
            object = bl2 ? ((DateFormatSymbols)object).getStandAloneWeekdays() : ((DateFormatSymbols)object).getWeekdays();
            n2 = this.matchString(string, n, 7, (String[])object, calendarBuilder);
            if (n2 > 0) {
                return n2;
            }
            object = this.formatData;
            object = bl2 ? ((DateFormatSymbols)object).getShortStandAloneWeekdays() : ((DateFormatSymbols)object).getShortWeekdays();
            n3 = this.matchString(string, n, 7, (String[])object, calendarBuilder);
            if (n3 > 0) {
                return n3;
            }
        } else {
            int[] arrn;
            int[] arrn2 = arrn = new int[2];
            arrn2[0] = 2;
            arrn2[1] = 1;
            int n4 = arrn.length;
            n3 = -1;
            for (int i = 0; i < n4; ++i) {
                int n5;
                n3 = arrn[i];
                n3 = n5 = this.matchString(string, n, n2, this.calendar.getDisplayNames(n2, n3, this.locale), calendarBuilder);
                if (n5 <= 0) continue;
                return n3;
            }
        }
        return n3;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        TimeZone timeZone;
        ((ObjectInputStream)object).defaultReadObject();
        try {
            this.compiledPattern = this.compile(this.pattern);
            if (this.serialVersionOnStream < 1) {
                this.initializeDefaultCentury();
            } else {
                this.parseAmbiguousDatesAsAfter(this.defaultCenturyStart);
            }
            this.serialVersionOnStream = 1;
        }
        catch (Exception exception) {
            throw new InvalidObjectException("invalid pattern");
        }
        TimeZone timeZone2 = this.getTimeZone();
        if (timeZone2 instanceof SimpleTimeZone && (timeZone = TimeZone.getTimeZone((String)(object = timeZone2.getID()))) != null && timeZone.hasSameRules(timeZone2) && timeZone.getID().equals(object)) {
            this.setTimeZone(timeZone);
        }
    }

    private void subFormat(int n, int n2, Format.FieldDelegate fieldDelegate, StringBuffer stringBuffer, boolean bl) {
        Object object;
        int n3;
        block54 : {
            int n4;
            block46 : {
                int n5;
                int n6;
                block47 : {
                    block48 : {
                        block49 : {
                            block50 : {
                                block51 : {
                                    block52 : {
                                        block53 : {
                                            n3 = stringBuffer.length();
                                            n4 = PATTERN_INDEX_TO_CALENDAR_FIELD[n];
                                            if (n4 == 17) {
                                                if (this.calendar.isWeekDateSupported()) {
                                                    n5 = this.calendar.getWeekYear();
                                                    n6 = n4;
                                                    n4 = n5;
                                                } else {
                                                    n6 = PATTERN_INDEX_TO_CALENDAR_FIELD[1];
                                                    n4 = this.calendar.get(n6);
                                                    n = 1;
                                                }
                                            } else if (n4 == 1000) {
                                                n5 = CalendarBuilder.toISODayOfWeek(this.calendar.get(7));
                                                n6 = n4;
                                                n4 = n5;
                                            } else {
                                                n5 = this.calendar.get(n4);
                                                n6 = n4;
                                                n4 = n5;
                                            }
                                            n5 = n2 >= 4 ? 2 : 1;
                                            object = !bl && n6 != 1000 ? this.calendar.getDisplayName(n6, n5, this.locale) : null;
                                            if (n == 0) break block46;
                                            if (n == 1) break block47;
                                            if (n == 2) break block48;
                                            if (n == 4) break block49;
                                            if (n == 8) break block50;
                                            int n7 = 0;
                                            boolean bl2 = false;
                                            if (n == 9) break block51;
                                            if (n == 14) break block52;
                                            if (n == 15) break block53;
                                            block0 : switch (n) {
                                                default: {
                                                    switch (n) {
                                                        default: {
                                                            if (object == null) {
                                                                this.zeroPaddingNumber(n4, n2, Integer.MAX_VALUE, stringBuffer);
                                                                break block0;
                                                            }
                                                            break block54;
                                                        }
                                                        case 24: 
                                                        case 25: {
                                                            object = "";
                                                            break;
                                                        }
                                                        case 23: {
                                                            if (object == null) {
                                                                object = this.formatWeekday(n2, n4, bl, true);
                                                                break;
                                                            }
                                                            break block54;
                                                        }
                                                        case 22: {
                                                            object = this.formatMonth(n2, n4, Integer.MAX_VALUE, stringBuffer, bl, true, n6, n5);
                                                            break;
                                                        }
                                                        case 21: {
                                                            n4 = this.calendar.get(15) + this.calendar.get(16);
                                                            if (n4 == 0) {
                                                                stringBuffer.append('Z');
                                                                break;
                                                            }
                                                            if ((n4 /= 60000) >= 0) {
                                                                stringBuffer.append('+');
                                                            } else {
                                                                stringBuffer.append('-');
                                                                n4 = -n4;
                                                            }
                                                            CalendarUtils.sprintf0d(stringBuffer, n4 / 60, 2);
                                                            if (n2 == 1) break;
                                                            if (n2 == 3) {
                                                                stringBuffer.append(':');
                                                            }
                                                            CalendarUtils.sprintf0d(stringBuffer, n4 % 60, 2);
                                                            break;
                                                        }
                                                    }
                                                    break block54;
                                                }
                                                case 19: {
                                                    break block47;
                                                }
                                                case 18: {
                                                    n4 = this.calendar.get(15);
                                                    n6 = this.calendar.get(16);
                                                    bl = n2 >= 4;
                                                    if (n2 == 4) {
                                                        bl2 = true;
                                                    }
                                                    stringBuffer.append(TimeZone.createGmtOffsetString(bl2, bl, n4 + n6));
                                                    break block54;
                                                }
                                                case 17: {
                                                    if (object == null) {
                                                        Object object2;
                                                        Object object3 = this.calendar.getTimeZone();
                                                        bl = this.calendar.get(16) != 0;
                                                        if (this.formatData.isZoneStringsSet) {
                                                            n2 = n2 < 4 ? n7 : 1;
                                                            object2 = libcore.icu.TimeZoneNames.getDisplayName((String[][])this.formatData.getZoneStringsWrapper(), (String)((TimeZone)object3).getID(), (boolean)bl, (int)n2);
                                                        } else {
                                                            object2 = n2 < 4 ? (bl ? TimeZoneNames.NameType.SHORT_DAYLIGHT : TimeZoneNames.NameType.SHORT_STANDARD) : (bl ? TimeZoneNames.NameType.LONG_DAYLIGHT : TimeZoneNames.NameType.LONG_STANDARD);
                                                            object3 = android.icu.util.TimeZone.getCanonicalID((String)((TimeZone)object3).getID());
                                                            object2 = this.getTimeZoneNames().getDisplayName((String)object3, object2, this.calendar.getTimeInMillis());
                                                        }
                                                        if (object2 != null) {
                                                            stringBuffer.append((String)object2);
                                                            break;
                                                        }
                                                        stringBuffer.append(TimeZone.createGmtOffsetString(true, true, this.calendar.get(15) + this.calendar.get(16)));
                                                        break;
                                                    }
                                                    break block54;
                                                }
                                            }
                                            break block54;
                                        }
                                        if (object == null) {
                                            if (n4 == 0) {
                                                this.zeroPaddingNumber(this.calendar.getLeastMaximum(10) + 1, n2, Integer.MAX_VALUE, stringBuffer);
                                            } else {
                                                this.zeroPaddingNumber(n4, n2, Integer.MAX_VALUE, stringBuffer);
                                            }
                                        }
                                        break block54;
                                    }
                                    if (bl) {
                                        object = this.formatData.getAmPmStrings()[n4];
                                    }
                                    break block54;
                                }
                                if (object == null) {
                                    object = this.formatWeekday(n2, n4, bl, false);
                                }
                                break block54;
                            }
                            if (object == null) {
                                this.zeroPaddingNumber((int)((double)n4 / 1000.0 * Math.pow(10.0, n2)), n2, n2, stringBuffer);
                            }
                            break block54;
                        }
                        if (object == null) {
                            if (n4 == 0) {
                                this.zeroPaddingNumber(this.calendar.getMaximum(11) + 1, n2, Integer.MAX_VALUE, stringBuffer);
                            } else {
                                this.zeroPaddingNumber(n4, n2, Integer.MAX_VALUE, stringBuffer);
                            }
                        }
                        break block54;
                    }
                    object = this.formatMonth(n2, n4, Integer.MAX_VALUE, stringBuffer, bl, false, n6, n5);
                    break block54;
                }
                n6 = 1;
                if (this.calendar instanceof GregorianCalendar) {
                    if (n2 != 2) {
                        this.zeroPaddingNumber(n4, n2, Integer.MAX_VALUE, stringBuffer);
                    } else {
                        this.zeroPaddingNumber(n4, 2, 2, stringBuffer);
                    }
                } else if (object == null) {
                    if (n5 == 2) {
                        n2 = n6;
                    }
                    this.zeroPaddingNumber(n4, n2, Integer.MAX_VALUE, stringBuffer);
                }
                break block54;
            }
            String string = object;
            if (bl) {
                String[] arrstring = this.formatData.getEras();
                string = object;
                if (n4 < arrstring.length) {
                    string = arrstring[n4];
                }
            }
            object = string == null ? "" : string;
        }
        if (object != null) {
            stringBuffer.append((String)object);
        }
        n2 = PATTERN_INDEX_TO_DATE_FORMAT_FIELD[n];
        object = PATTERN_INDEX_TO_DATE_FORMAT_FIELD_ID[n];
        fieldDelegate.formatted(n2, (Format.Field)object, object, n3, stringBuffer.length(), stringBuffer);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int subParse(String var1_1, int var2_5, int var3_6, int var4_7, boolean var5_8, boolean[] var6_9, ParsePosition var7_10, boolean var8_11, CalendarBuilder var9_12) {
        var10_13 = this;
        var11_14 = var1_1;
        var12_15 = var7_10;
        var13_16 = var9_12;
        var14_17 = 0;
        var15_18 = false;
        var16_19 = new ParsePosition(0);
        var16_19.index = var2_5;
        var17_20 = 19;
        if (var3_6 == 19 && !var10_13.calendar.isWeekDateSupported()) {
            var3_6 = 1;
        }
        var18_21 = SimpleDateFormat.PATTERN_INDEX_TO_CALENDAR_FIELD[var3_6];
        var19_22 = var3_6;
        var3_6 = var17_20;
        do {
            block59 : {
                block58 : {
                    block66 : {
                        block67 : {
                            block68 : {
                                block69 : {
                                    block70 : {
                                        block71 : {
                                            block72 : {
                                                block61 : {
                                                    block65 : {
                                                        block64 : {
                                                            block63 : {
                                                                block62 : {
                                                                    block60 : {
                                                                        var17_20 = var2_5;
                                                                        if (var16_19.index >= var1_1.length()) {
                                                                            var12_15.errorIndex = var17_20;
                                                                            return -1;
                                                                        }
                                                                        var20_23 = var11_14.charAt(var16_19.index);
                                                                        if (var20_23 == 32 || var20_23 == 9) break block59;
                                                                        var20_23 = var16_19.index;
                                                                        if (var19_22 == 4 || var19_22 == 15 || var19_22 == 2 && var4_7 <= 2 || var19_22 == 1) break block60;
                                                                        var3_6 = var14_17;
                                                                        if (var19_22 != 19) break block61;
                                                                    }
                                                                    if (!var5_8) break block62;
                                                                    if (var17_20 + var4_7 > var1_1.length()) break block58;
                                                                    var12_15 = var10_13.numberFormat.parse(var11_14.substring(0, var17_20 + var4_7), var16_19);
                                                                    break block63;
                                                                }
                                                                var12_15 = var10_13.numberFormat.parse(var11_14, var16_19);
                                                            }
                                                            if (var12_15 != null) break block64;
                                                            if (var19_22 != 1) break block58;
                                                            var3_6 = var14_17;
                                                            if (!(var10_13.calendar instanceof GregorianCalendar)) break block61;
                                                            break block58;
                                                        }
                                                        var3_6 = var14_17 = var12_15.intValue();
                                                        if (!var8_11) break block61;
                                                        var3_6 = var14_17;
                                                        if (var14_17 >= 0) break block61;
                                                        if (var16_19.index < var1_1.length() && var11_14.charAt(var16_19.index) != var10_13.minusSign) break block65;
                                                        var3_6 = var14_17;
                                                        if (var16_19.index != var1_1.length()) break block61;
                                                        var3_6 = var14_17;
                                                        if (var11_14.charAt(var16_19.index - 1) != var10_13.minusSign) break block61;
                                                    }
                                                    var3_6 = -var14_17;
                                                    --var16_19.index;
                                                }
                                                var15_18 = this.useDateFormatSymbols();
                                                if (var19_22 == 0) break block66;
                                                if (var19_22 == 1) break block67;
                                                if (var19_22 == 2) break block68;
                                                if (var19_22 == 4) break block69;
                                                if (var19_22 == 9) break block70;
                                                if (var19_22 == 14) break block71;
                                                if (var19_22 == 15) break block72;
                                                block3 : switch (var19_22) {
                                                    default: {
                                                        switch (var19_22) {
                                                            default: {
                                                                var2_5 = var16_19.getIndex();
                                                                if (var5_8) {
                                                                    if (var17_20 + var4_7 > var1_1.length()) break;
                                                                    var6_9 = var10_13.numberFormat.parse(var11_14.substring(0, var17_20 + var4_7), var16_19);
                                                                } else {
                                                                    var6_9 = var10_13.numberFormat.parse(var11_14, var16_19);
                                                                }
                                                                if (var6_9 == null) break block3;
                                                                var2_5 = var19_22 == 8 ? (int)(var6_9.doubleValue() / Math.pow(10.0, var16_19.getIndex() - var2_5) * 1000.0) : var6_9.intValue();
                                                                var3_6 = var2_5;
                                                                if (!var8_11) ** GOTO lbl84
                                                                var3_6 = var2_5;
                                                                if (var2_5 >= 0) ** GOTO lbl84
                                                                if (var16_19.index < var1_1.length() && var11_14.charAt(var16_19.index) != var10_13.minusSign) ** GOTO lbl82
                                                                var3_6 = var2_5;
                                                                if (var16_19.index != var1_1.length()) ** GOTO lbl84
                                                                var3_6 = var2_5;
                                                                if (var11_14.charAt(var16_19.index - 1) != var10_13.minusSign) ** GOTO lbl84
lbl82: // 2 sources:
                                                                var3_6 = -var2_5;
                                                                --var16_19.index;
lbl84: // 5 sources:
                                                                var13_16.set(var18_21, var3_6);
                                                                return var16_19.index;
                                                            }
                                                            case 23: {
                                                                var2_5 = this.parseWeekday(var1_1, var2_5, var18_21, var15_18, true, var9_12);
                                                                if (var2_5 <= 0) break block3;
                                                                return var2_5;
                                                            }
                                                            case 22: {
                                                                var2_5 = this.parseMonth(var1_1, var4_7, var3_6, var2_5, var18_21, var16_19, var15_18, true, var9_12);
                                                                if (var2_5 <= 0) break block3;
                                                                return var2_5;
                                                            }
                                                            case 21: {
                                                                if (var1_1.length() - var16_19.index <= 0) break;
                                                                var2_5 = var11_14.charAt(var16_19.index);
                                                                if (var2_5 == 90) {
                                                                    var13_16.set(15, 0).set(16, 0);
                                                                    var16_19.index = var2_5 = var16_19.index + 1;
                                                                    return var2_5;
                                                                }
                                                                if (var2_5 != 43) ** GOTO lbl106
                                                                var2_5 = 1;
                                                                ** GOTO lbl108
lbl106: // 1 sources:
                                                                if (var2_5 != 45) ** GOTO lbl115
                                                                var2_5 = -1;
lbl108: // 2 sources:
                                                                var16_19.index = var3_6 = var16_19.index + 1;
                                                                var5_8 = var4_7 == 3;
                                                                var2_5 = this.subParseNumericZone(var1_1, var3_6, var2_5, var4_7, var5_8, var9_12);
                                                                if (var2_5 > 0) {
                                                                    return var2_5;
                                                                }
                                                                var16_19.index = -var2_5;
                                                                break;
lbl115: // 1 sources:
                                                                ++var16_19.index;
                                                                break;
                                                            }
                                                        }
                                                        break;
                                                    }
                                                    case 19: {
                                                        break block67;
                                                    }
                                                    case 17: 
                                                    case 18: {
                                                        var4_7 = var11_14.charAt(var16_19.index);
                                                        var3_6 = var4_7 == 43 ? 1 : (var4_7 == 45 ? -1 : 0);
                                                        if (var3_6 != 0) ** GOTO lbl156
                                                        if (var4_7 != 71 && var4_7 != 103) ** GOTO lbl147
                                                        if (var1_1.length() - var17_20 >= "GMT".length() && var1_1.regionMatches(true, var2_5, "GMT", 0, "GMT".length())) {
                                                            var16_19.index = "GMT".length() + var17_20;
                                                            var2_5 = var3_6;
                                                            if (var1_1.length() - var16_19.index > 0) {
                                                                var4_7 = var11_14.charAt(var16_19.index);
                                                                if (var4_7 == 43) {
                                                                    var2_5 = 1;
                                                                } else {
                                                                    var2_5 = var3_6;
                                                                    if (var4_7 == 45) {
                                                                        var2_5 = -1;
                                                                    }
                                                                }
                                                            }
                                                            if (var2_5 == 0) {
                                                                var13_16.set(15, 0).set(16, 0);
                                                                return var16_19.index;
                                                            }
                                                            var16_19.index = var3_6 = var16_19.index + 1;
                                                            if ((var2_5 = this.subParseNumericZone(var1_1, var3_6, var2_5, 0, false, var9_12)) > 0) {
                                                                return var2_5;
                                                            }
                                                            var16_19.index = -var2_5;
                                                        }
lbl147: // 3 sources:
                                                        var2_5 = var16_19.index;
                                                        {
                                                            catch (IndexOutOfBoundsException var1_2) {}
                                                        }
                                                        try {
                                                            var2_5 = this.subParseZoneString(var11_14, var2_5, var13_16);
                                                            if (var2_5 > 0) {
                                                                return var2_5;
                                                            }
                                                            var16_19.index = -var2_5;
                                                            ** break;
                                                            break block58;
lbl156: // 1 sources:
                                                            var16_19.index = var2_5 = var16_19.index + 1;
                                                            if ((var2_5 = this.subParseNumericZone(var1_1, var2_5, var3_6, 0, false, var9_12)) > 0) {
                                                                return var2_5;
                                                            }
                                                            var16_19.index = -var2_5;
                                                        }
                                                        catch (IndexOutOfBoundsException var1_3) {}
                                                        break;
                                                        catch (IndexOutOfBoundsException var1_4) {
                                                            ** break;
                                                        }
lbl165: // 2 sources:
                                                        break;
                                                    }
                                                }
                                                break block58;
                                            }
                                            if (this.isLenient() || (var2_5 = var3_6) >= 1 && var2_5 <= 12) {
                                                var2_5 = var3_6 == var10_13.calendar.getLeastMaximum(10) + 1 ? 0 : var3_6;
                                                var13_16.set(10, var2_5);
                                                return var16_19.index;
                                            }
                                            break block58;
                                        }
                                        if (var15_18 != false ? (var2_5 = this.matchString(var1_1, var2_5, 9, var10_13.formatData.getAmPmStrings(), var9_12)) > 0 : (var2_5 = this.matchString(var1_1, var2_5, var18_21, var10_13.getDisplayNamesMap(var18_21, var10_13.locale), var9_12)) > 0) {
                                            return var2_5;
                                        }
                                        break block58;
                                    }
                                    if ((var2_5 = this.parseWeekday(var1_1, var2_5, var18_21, var15_18, false, var9_12)) > 0) {
                                        return var2_5;
                                    }
                                    break block58;
                                }
                                if (this.isLenient() || var3_6 >= 1 && var3_6 <= 24) {
                                    var2_5 = var3_6 == var10_13.calendar.getMaximum(11) + 1 ? 0 : var3_6;
                                    var13_16.set(11, var2_5);
                                    return var16_19.index;
                                }
                                break block58;
                            }
                            if ((var2_5 = this.parseMonth(var1_1, var4_7, var3_6, var2_5, var18_21, var16_19, var15_18, false, var9_12)) > 0) {
                                return var2_5;
                            }
                            break block58;
                        }
                        var19_22 = var18_21;
                        var7_10 = this;
                        var5_8 = true;
                        var18_21 = 1;
                        if (!(var7_10.calendar instanceof GregorianCalendar)) {
                            if (var4_7 >= 4) {
                                var18_21 = 2;
                            }
                            if ((var6_9 = var7_10.calendar.getDisplayNames(var19_22, var18_21, var7_10.locale)) != null && (var2_5 = this.matchString(var1_1, var2_5, var19_22, (Map<String, Integer>)var6_9, var9_12)) > 0) {
                                return var2_5;
                            }
                            var13_16.set(var19_22, var3_6);
                            return var16_19.index;
                        }
                        if (var4_7 <= 2 && var16_19.index - var20_23 == 2 && Character.isDigit(var1_1.charAt(var20_23)) && Character.isDigit(var1_1.charAt(var20_23 + 1))) {
                            var4_7 = var7_10.defaultCenturyStartYear;
                            var2_5 = 100;
                            var18_21 = var4_7 % 100;
                            if (var3_6 != var18_21) {
                                var5_8 = false;
                            }
                            var6_9[0] = var5_8;
                            var4_7 = var7_10.defaultCenturyStartYear / 100;
                            if (var3_6 >= var18_21) {
                                var2_5 = 0;
                            }
                            var3_6 += var4_7 * 100 + var2_5;
                        }
                        var13_16.set(var19_22, var3_6);
                        return var16_19.index;
                    }
                    if (var15_18 != false ? (var2_5 = this.matchString(var1_1, var2_5, 0, var10_13.formatData.getEras(), var9_12)) > 0 : (var2_5 = this.matchString(var1_1, var2_5, var18_21, var10_13.getDisplayNamesMap(var18_21, var10_13.locale), var9_12)) > 0) {
                        return var2_5;
                    }
                }
                var7_10.errorIndex = var16_19.index;
                return -1;
            }
            ++var16_19.index;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int subParseNumericZone(String string, int n, int n2, int c, boolean bl, CalendarBuilder calendarBuilder) {
        block14 : {
            block16 : {
                block15 : {
                    block17 : {
                        c2 = c3 = n + 1;
                        c5 = string.charAt(n);
                        c2 = c3;
                        bl2 = this.isDigit(c5);
                        if (!bl2) {
                            n = c3;
                            return 1 - n;
                        }
                        n3 = c5 - 48;
                        n = c3 + 1;
                        c5 = string.charAt(c3);
                        bl2 = this.isDigit(c5);
                        if (bl2) {
                            n3 = n3 * 10 + (c5 - 48);
                        } else {
                            --n;
                        }
                        if (n3 > 23) {
                            return 1 - n;
                        }
                        n4 = 0;
                        c3 = n;
                        if (c == '\u0001') break block14;
                        c = (char)(n + 1);
                        c2 = c;
                        try {
                            n = string.charAt(n);
                            c2 = n;
                            if (c2 != ':') ** GOTO lbl37
                            n = c + '\u0001';
                            c2 = n;
                        }
                        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                            n = c2;
                            return 1 - n;
                        }
                        c4 = c = string.charAt(c);
                        break block17;
lbl37: // 1 sources:
                        if (bl) {
                            n = c;
                            return 1 - n;
                        }
                        n = c;
                        c4 = c2;
                    }
                    c2 = n;
                    bl = this.isDigit(c4);
                    if (bl) break block15;
                    return 1 - n;
                }
                c2 = c = (char)(n + 1);
                c5 = string.charAt(n);
                c2 = c;
                bl = this.isDigit(c5);
                if (bl && (n4 = (c4 - 48) * 10 + (c5 - 48)) <= 59) break block16;
                n = c;
                return 1 - n;
            }
            c3 = c;
        }
        c2 = c3;
        calendarBuilder.set(15, 60000 * (n4 + n3 * 60) * n2).set(16, 0);
        return c3;
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return 1 - n;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            n = c2;
        }
        return 1 - n;
    }

    private int subParseZoneString(String string, int n, CalendarBuilder calendarBuilder) {
        if (this.formatData.isZoneStringsSet) {
            return this.subParseZoneStringFromSymbols(string, n, calendarBuilder);
        }
        return this.subParseZoneStringFromICU(string, n, calendarBuilder);
    }

    private int subParseZoneStringFromICU(String object, int n, CalendarBuilder calendarBuilder) {
        TimeZoneNames.MatchInfo matchInfo;
        boolean bl;
        String string = android.icu.util.TimeZone.getCanonicalID((String)this.getTimeZone().getID());
        TimeZoneNames timeZoneNames = this.getTimeZoneNames();
        Object object2 = null;
        Object object3 = null;
        Iterator iterator = timeZoneNames.find((CharSequence)object, n, NAME_TYPES).iterator();
        object = object3;
        do {
            TimeZoneNames.MatchInfo matchInfo2;
            Object object4;
            matchInfo = object2;
            object3 = object;
            if (!iterator.hasNext()) break;
            matchInfo = (TimeZoneNames.MatchInfo)iterator.next();
            if (object2 != null && object2.matchLength() >= matchInfo.matchLength()) {
                matchInfo2 = object2;
                object4 = object;
                if (object2.matchLength() == matchInfo.matchLength()) {
                    if (string.equals(matchInfo.tzID())) {
                        object3 = object;
                        break;
                    }
                    matchInfo2 = object2;
                    object4 = object;
                    if (matchInfo.mzID() != null) {
                        object3 = object;
                        if (object == null) {
                            object3 = timeZoneNames.getAvailableMetaZoneIDs(string);
                        }
                        matchInfo2 = object2;
                        object4 = object3;
                        if (object3.contains(matchInfo.mzID())) {
                            break;
                        }
                    }
                }
            } else {
                object4 = object;
                matchInfo2 = matchInfo;
            }
            object2 = matchInfo2;
            object = object4;
        } while (true);
        if (matchInfo == null) {
            return -n;
        }
        object2 = matchInfo.tzID();
        object = object2;
        if (object2 == null) {
            object = object3;
            if (object3 == null) {
                object = timeZoneNames.getAvailableMetaZoneIDs(string);
            }
            if (object.contains(matchInfo.mzID())) {
                object = string;
            } else {
                object2 = ULocale.forLocale((Locale)this.locale);
                object = object3 = object2.getCountry();
                if (((String)object3).length() == 0) {
                    object = ULocale.addLikelySubtags((ULocale)object2).getCountry();
                }
                object = timeZoneNames.getReferenceZoneID(matchInfo.mzID(), (String)object);
            }
        }
        object3 = TimeZone.getTimeZone((String)object);
        if (!string.equals(object)) {
            this.setTimeZone((TimeZone)object3);
        }
        int n2 = (bl = DST_NAME_TYPES.contains((Object)matchInfo.nameType())) ? ((TimeZone)object3).getDSTSavings() : 0;
        if (!bl || n2 != 0) {
            calendarBuilder.clear(15).set(16, n2);
        }
        return matchInfo.matchLength() + n;
    }

    private int subParseZoneStringFromSymbols(String string, int n, CalendarBuilder calendarBuilder) {
        String[] arrstring;
        int n2;
        boolean bl = false;
        boolean bl2 = false;
        TimeZone timeZone = this.getTimeZone();
        int n3 = this.formatData.getZoneIndex(timeZone.getID());
        Object object = null;
        String[][] arrstring2 = this.formatData.getZoneStringsWrapper();
        Object object2 = null;
        int n4 = 0;
        int n5 = 0;
        boolean bl3 = bl;
        Object object3 = object;
        if (n3 != -1) {
            arrstring = arrstring2[n3];
            n3 = n2 = this.matchZoneString(string, n, arrstring);
            bl3 = bl;
            object3 = object;
            object2 = arrstring;
            n4 = n3;
            if (n2 > 0) {
                bl3 = bl2;
                if (n3 <= 2) {
                    bl3 = arrstring[n3].equalsIgnoreCase(arrstring[n3 + 2]);
                }
                object3 = TimeZone.getTimeZone(arrstring[0]);
                n4 = n3;
                object2 = arrstring;
            }
        }
        bl2 = bl3;
        object = object3;
        arrstring = object2;
        n3 = n4;
        if (object3 == null) {
            n2 = this.formatData.getZoneIndex(TimeZone.getDefault().getID());
            bl2 = bl3;
            object = object3;
            arrstring = object2;
            n3 = n4;
            if (n2 != -1) {
                object2 = arrstring2[n2];
                n4 = n2 = this.matchZoneString(string, n, (String[])object2);
                bl2 = bl3;
                object = object3;
                arrstring = object2;
                n3 = n4;
                if (n2 > 0) {
                    if (n4 <= 2) {
                        bl3 = object2[n4].equalsIgnoreCase(object2[n4 + 2]);
                    }
                    object = TimeZone.getTimeZone(object2[0]);
                    n3 = n4;
                    arrstring = object2;
                    bl2 = bl3;
                }
            }
        }
        bl3 = bl2;
        object2 = object;
        object3 = arrstring;
        n4 = n3;
        if (object == null) {
            int n6 = arrstring2.length;
            n2 = 0;
            do {
                bl3 = bl2;
                object2 = object;
                object3 = arrstring;
                n4 = n3;
                if (n2 >= n6) break;
                arrstring = arrstring2[n2];
                n4 = n3 = this.matchZoneString(string, n, arrstring);
                if (n3 > 0) {
                    if (n4 <= 2) {
                        bl2 = arrstring[n4].equalsIgnoreCase(arrstring[n4 + 2]);
                    }
                    object2 = TimeZone.getTimeZone(arrstring[0]);
                    bl3 = bl2;
                    object3 = arrstring;
                    break;
                }
                ++n2;
                n3 = n4;
            } while (true);
        }
        if (object2 != null) {
            if (!object2.equals(timeZone)) {
                this.setTimeZone((TimeZone)object2);
            }
            n3 = n5;
            if (n4 >= 3) {
                n3 = object2.getDSTSavings();
            }
            if (!(bl3 || n4 >= 3 && n3 == 0)) {
                calendarBuilder.clear(15).set(16, n3);
            }
            return object3[n4].length() + n;
        }
        return -n;
    }

    private String translatePattern(String charSequence, String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        for (int i = 0; i < ((String)charSequence).length(); ++i) {
            boolean bl2;
            int n;
            block10 : {
                int n2;
                int n3;
                block12 : {
                    block11 : {
                        block9 : {
                            n3 = ((String)charSequence).charAt(i);
                            if (!bl) break block9;
                            bl2 = bl;
                            n = n3;
                            if (n3 == 39) {
                                bl2 = false;
                                n = n3;
                            }
                            break block10;
                        }
                        if (n3 != 39) break block11;
                        bl2 = true;
                        n = n3;
                        break block10;
                    }
                    if (n3 >= 97 && n3 <= 122) break block12;
                    bl2 = bl;
                    n = n3;
                    if (n3 < 65) break block10;
                    bl2 = bl;
                    n = n3;
                    if (n3 > 90) break block10;
                }
                if ((n2 = string.indexOf(n3)) >= 0) {
                    bl2 = bl;
                    n = n3;
                    if (n2 < string2.length()) {
                        n2 = string2.charAt(n2);
                        bl2 = bl;
                        n = n2;
                    }
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Illegal pattern  character '");
                    ((StringBuilder)charSequence).append((char)n3);
                    ((StringBuilder)charSequence).append("'");
                    throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                }
            }
            stringBuilder.append((char)n);
            bl = bl2;
        }
        if (!bl) {
            return stringBuilder.toString();
        }
        throw new IllegalArgumentException("Unfinished quote in pattern");
    }

    private boolean useDateFormatSymbols() {
        boolean bl = this.useDateFormatSymbols || "java.util.GregorianCalendar".equals(this.calendar.getClass().getName()) || this.locale == null;
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void zeroPaddingNumber(int n, int n2, int n3, StringBuffer stringBuffer) {
        block17 : {
            int n4;
            block16 : {
                n4 = n;
                if (this.zeroDigit != '\u0000') break block16;
                n4 = n;
                this.zeroDigit = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols().getZeroDigit();
            }
            if (n < 0) break block17;
            if (n < 100 && n2 >= 1 && n2 <= 2) {
                if (n < 10) {
                    if (n2 == 2) {
                        n4 = n;
                        stringBuffer.append(this.zeroDigit);
                    }
                    n4 = n;
                    stringBuffer.append((char)(this.zeroDigit + n));
                    return;
                }
                n4 = n;
                stringBuffer.append((char)(this.zeroDigit + n / 10));
                n4 = n;
                stringBuffer.append((char)(this.zeroDigit + n % 10));
                return;
            }
            if (n < 1000 || n >= 10000) break block17;
            if (n2 == 4) {
                n4 = n;
                stringBuffer.append((char)(this.zeroDigit + n / 1000));
                n4 = n %= 1000;
                stringBuffer.append((char)(this.zeroDigit + n / 100));
                n4 = n %= 100;
                stringBuffer.append((char)(this.zeroDigit + n / 10));
                n4 = n;
                stringBuffer.append((char)(this.zeroDigit + n % 10));
                return;
            }
            if (n2 != 2 || n3 != 2) break block17;
            n4 = n;
            try {
                this.zeroPaddingNumber(n % 100, 2, 2, stringBuffer);
                return;
            }
            catch (Exception exception) {
                n = n4;
            }
        }
        this.numberFormat.setMinimumIntegerDigits(n2);
        this.numberFormat.setMaximumIntegerDigits(n3);
        this.numberFormat.format(n, stringBuffer, DontCareFieldPosition.INSTANCE);
    }

    public void applyLocalizedPattern(String string) {
        string = this.translatePattern(string, this.formatData.getLocalPatternChars(), "GyMdkHmsSEDFwWahKzZYuXLcbB");
        this.compiledPattern = this.compile(string);
        this.pattern = string;
    }

    public void applyPattern(String string) {
        this.applyPatternImpl(string);
    }

    @Override
    public Object clone() {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat)super.clone();
        simpleDateFormat.formatData = (DateFormatSymbols)this.formatData.clone();
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
    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        fieldPosition.endIndex = 0;
        fieldPosition.beginIndex = 0;
        return this.format(date, stringBuffer, fieldPosition.getFieldDelegate());
    }

    @Override
    public AttributedCharacterIterator formatToCharacterIterator(Object object) {
        block6 : {
            CharacterIteratorFieldDelegate characterIteratorFieldDelegate;
            StringBuffer stringBuffer;
            block5 : {
                block4 : {
                    stringBuffer = new StringBuffer();
                    characterIteratorFieldDelegate = new CharacterIteratorFieldDelegate();
                    if (!(object instanceof Date)) break block4;
                    this.format((Date)object, stringBuffer, characterIteratorFieldDelegate);
                    break block5;
                }
                if (!(object instanceof Number)) break block6;
                this.format(new Date(((Number)object).longValue()), stringBuffer, characterIteratorFieldDelegate);
            }
            return characterIteratorFieldDelegate.getIterator(stringBuffer.toString());
        }
        if (object == null) {
            throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
        }
        throw new IllegalArgumentException("Cannot format given Object as a Date");
    }

    public Date get2DigitYearStart() {
        return (Date)this.defaultCenturyStart.clone();
    }

    public DateFormatSymbols getDateFormatSymbols() {
        return (DateFormatSymbols)this.formatData.clone();
    }

    @Override
    public int hashCode() {
        return this.pattern.hashCode();
    }

    @Override
    public Date parse(String object, ParsePosition parsePosition) {
        TimeZone timeZone = this.getTimeZone();
        try {
            object = this.parseInternal((String)object, parsePosition);
            return object;
        }
        finally {
            this.setTimeZone(timeZone);
        }
    }

    public void set2DigitYearStart(Date date) {
        this.parseAmbiguousDatesAsAfter(new Date(date.getTime()));
    }

    public void setDateFormatSymbols(DateFormatSymbols dateFormatSymbols) {
        this.formatData = (DateFormatSymbols)dateFormatSymbols.clone();
        this.useDateFormatSymbols = true;
    }

    public String toLocalizedPattern() {
        return this.translatePattern(this.pattern, "GyMdkHmsSEDFwWahKzZYuXLcbB", this.formatData.getLocalPatternChars());
    }

    public String toPattern() {
        return this.pattern;
    }
}

