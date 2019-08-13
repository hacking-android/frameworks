/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUCache;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.PatternTokenizer;
import android.icu.impl.SimpleCache;
import android.icu.impl.SimpleFormatterImpl;
import android.icu.impl.UResource;
import android.icu.text.DateFormat;
import android.icu.text.DecimalFormatSymbols;
import android.icu.text.SimpleDateFormat;
import android.icu.text.UnicodeSet;
import android.icu.util.Calendar;
import android.icu.util.Freezable;
import android.icu.util.ICUCloneNotSupportedException;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class DateTimePatternGenerator
implements Freezable<DateTimePatternGenerator>,
Cloneable {
    private static final DisplayWidth APPENDITEM_WIDTH;
    private static final int APPENDITEM_WIDTH_INT;
    private static final String[] CANONICAL_ITEMS;
    private static final Set<String> CANONICAL_SET;
    private static final String[] CLDR_FIELD_APPEND;
    private static final String[] CLDR_FIELD_NAME;
    private static final DisplayWidth[] CLDR_FIELD_WIDTH;
    private static final int DATE_MASK = 1023;
    public static final int DAY = 7;
    public static final int DAYPERIOD = 10;
    public static final int DAY_OF_WEEK_IN_MONTH = 9;
    public static final int DAY_OF_YEAR = 8;
    private static final boolean DEBUG = false;
    private static final int DELTA = 16;
    private static ICUCache<String, DateTimePatternGenerator> DTPNG_CACHE;
    public static final int ERA = 0;
    private static final int EXTRA_FIELD = 65536;
    private static final String[] FIELD_NAME;
    private static final int FRACTIONAL_MASK = 16384;
    public static final int FRACTIONAL_SECOND = 14;
    public static final int HOUR = 11;
    private static final String[] LAST_RESORT_ALLOWED_HOUR_FORMAT;
    static final Map<String, String[]> LOCALE_TO_ALLOWED_HOUR;
    private static final int LONG = -260;
    public static final int MATCH_ALL_FIELDS_LENGTH = 65535;
    public static final int MATCH_HOUR_FIELD_LENGTH = 2048;
    @Deprecated
    public static final int MATCH_MINUTE_FIELD_LENGTH = 4096;
    public static final int MATCH_NO_OPTIONS = 0;
    @Deprecated
    public static final int MATCH_SECOND_FIELD_LENGTH = 8192;
    public static final int MINUTE = 12;
    private static final int MISSING_FIELD = 4096;
    public static final int MONTH = 3;
    private static final int NARROW = -257;
    private static final int NONE = 0;
    private static final int NUMERIC = 256;
    public static final int QUARTER = 2;
    public static final int SECOND = 13;
    private static final int SECOND_AND_FRACTIONAL_MASK = 24576;
    private static final int SHORT = -259;
    private static final int SHORTER = -258;
    private static final int TIME_MASK = 64512;
    @Deprecated
    public static final int TYPE_LIMIT = 16;
    public static final int WEEKDAY = 6;
    public static final int WEEK_OF_MONTH = 5;
    public static final int WEEK_OF_YEAR = 4;
    public static final int YEAR = 1;
    public static final int ZONE = 15;
    private static final int[][] types;
    private transient DistanceInfo _distanceInfo = new DistanceInfo();
    private String[] allowedHourFormats;
    private String[] appendItemFormats = new String[16];
    private TreeMap<String, PatternWithSkeletonFlag> basePattern_pattern = new TreeMap();
    private Set<String> cldrAvailableFormatKeys = new HashSet<String>(20);
    private transient DateTimeMatcher current = new DateTimeMatcher();
    private String dateTimeFormat = "{1} {0}";
    private String decimal = "?";
    private char defaultHourFormatChar = (char)72;
    private String[][] fieldDisplayNames = new String[16][DisplayWidth.access$100()];
    private transient FormatParser fp = new FormatParser();
    private volatile boolean frozen = false;
    private TreeMap<DateTimeMatcher, PatternWithSkeletonFlag> skeleton2pattern = new TreeMap();

    static {
        LAST_RESORT_ALLOWED_HOUR_FORMAT = new String[]{"H"};
        int[] arrn = new HashMap();
        ((ICUResourceBundle)ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER)).getAllItemsWithFallback("timeData", new DayPeriodAllowedHoursSink((HashMap)arrn));
        LOCALE_TO_ALLOWED_HOUR = Collections.unmodifiableMap(arrn);
        APPENDITEM_WIDTH = DisplayWidth.WIDE;
        APPENDITEM_WIDTH_INT = APPENDITEM_WIDTH.ordinal();
        CLDR_FIELD_WIDTH = DisplayWidth.values();
        DTPNG_CACHE = new SimpleCache<String, DateTimePatternGenerator>();
        CLDR_FIELD_APPEND = new String[]{"Era", "Year", "Quarter", "Month", "Week", "*", "Day-Of-Week", "Day", "*", "*", "*", "Hour", "Minute", "Second", "*", "Timezone"};
        CLDR_FIELD_NAME = new String[]{"era", "year", "quarter", "month", "week", "weekOfMonth", "weekday", "day", "dayOfYear", "weekdayOfMonth", "dayperiod", "hour", "minute", "second", "*", "zone"};
        FIELD_NAME = new String[]{"Era", "Year", "Quarter", "Month", "Week_in_Year", "Week_in_Month", "Weekday", "Day", "Day_Of_Year", "Day_of_Week_in_Month", "Dayperiod", "Hour", "Minute", "Second", "Fractional_Second", "Zone"};
        CANONICAL_ITEMS = new String[]{"G", "y", "Q", "M", "w", "W", "E", "d", "D", "F", "a", "H", "m", "s", "S", "v"};
        CANONICAL_SET = new HashSet<String>(Arrays.asList(CANONICAL_ITEMS));
        arrn = new int[]{71, 0, -259, 1, 3};
        int[] arrn2 = new int[]{71, 0, -257, 5};
        int[] arrn3 = new int[]{121, 1, 256, 1, 20};
        int[] arrn4 = new int[]{117, 1, 288, 1, 20};
        int[] arrn5 = new int[]{114, 1, 304, 1, 20};
        int[] arrn6 = new int[]{85, 1, -260, 4};
        int[] arrn7 = new int[]{81, 2, 256, 1, 2};
        int[] arrn8 = new int[]{81, 2, -259, 3};
        int[] arrn9 = new int[]{113, 2, 272, 1, 2};
        int[] arrn10 = new int[]{113, 2, -276, 4};
        int[] arrn11 = new int[]{113, 2, -273, 5};
        int[] arrn12 = new int[]{77, 3, -259, 3};
        int[] arrn13 = new int[]{77, 3, -260, 4};
        int[] arrn14 = new int[]{77, 3, -257, 5};
        int[] arrn15 = new int[]{76, 3, -275, 3};
        int[] arrn16 = new int[]{76, 3, -276, 4};
        int[] arrn17 = new int[]{76, 3, -273, 5};
        int[] arrn18 = new int[]{108, 3, 272, 1, 1};
        int[] arrn19 = new int[]{119, 4, 256, 1, 2};
        int[] arrn20 = new int[]{69, 6, -259, 1, 3};
        int[] arrn21 = new int[]{69, 6, -257, 5};
        int[] arrn22 = new int[]{69, 6, -258, 6};
        int[] arrn23 = new int[]{99, 6, 288, 1, 2};
        int[] arrn24 = new int[]{99, 6, -291, 3};
        int[] arrn25 = new int[]{99, 6, -292, 4};
        int[] arrn26 = new int[]{99, 6, -289, 5};
        int[] arrn27 = new int[]{99, 6, -290, 6};
        int[] arrn28 = new int[]{101, 6, -275, 3};
        int[] arrn29 = new int[]{101, 6, -276, 4};
        int[] arrn30 = new int[]{101, 6, -273, 5};
        int[] arrn31 = new int[]{101, 6, -274, 6};
        int[] arrn32 = new int[]{100, 7, 256, 1, 2};
        int[] arrn33 = new int[]{103, 7, 272, 1, 20};
        int[] arrn34 = new int[]{68, 8, 256, 1, 3};
        int[] arrn35 = new int[]{70, 9, 256, 1};
        int[] arrn36 = new int[]{97, 10, -259, 1, 3};
        int[] arrn37 = new int[]{97, 10, -257, 5};
        int[] arrn38 = new int[]{98, 10, -276, 4};
        int[] arrn39 = new int[]{98, 10, -273, 5};
        int[] arrn40 = new int[]{66, 10, -307, 1, 3};
        int[] arrn41 = new int[]{66, 10, -305, 5};
        int[] arrn42 = new int[]{72, 11, 416, 1, 2};
        int[] arrn43 = new int[]{75, 11, 272, 1, 2};
        int[] arrn44 = new int[]{65, 13, 272, 1, 1000};
        int[] arrn45 = new int[]{118, 15, -291, 1};
        int[] arrn46 = new int[]{118, 15, -292, 4};
        int[] arrn47 = new int[]{122, 15, -260, 4};
        int[] arrn48 = new int[]{90, 15, -273, 1, 3};
        int[] arrn49 = new int[]{90, 15, -276, 4};
        int[] arrn50 = new int[]{79, 15, -275, 1};
        int[] arrn51 = new int[]{86, 15, -275, 1};
        int[] arrn52 = new int[]{86, 15, -276, 2};
        int[] arrn53 = new int[]{86, 15, -278, 4};
        int[] arrn54 = new int[]{88, 15, -273, 1};
        int[] arrn55 = new int[]{88, 15, -276, 4};
        int[] arrn56 = new int[]{120, 15, -273, 1};
        int[] arrn57 = new int[]{120, 15, -276, 4};
        types = new int[][]{arrn, {71, 0, -260, 4}, arrn2, arrn3, {89, 1, 272, 1, 20}, arrn4, arrn5, {85, 1, -259, 1, 3}, arrn6, {85, 1, -257, 5}, arrn7, arrn8, {81, 2, -260, 4}, {81, 2, -257, 5}, arrn9, {113, 2, -275, 3}, arrn10, arrn11, {77, 3, 256, 1, 2}, arrn12, arrn13, arrn14, {76, 3, 272, 1, 2}, arrn15, arrn16, arrn17, arrn18, arrn19, {87, 5, 256, 1}, arrn20, {69, 6, -260, 4}, arrn21, arrn22, arrn23, arrn24, arrn25, arrn26, arrn27, {101, 6, 272, 1, 2}, arrn28, arrn29, arrn30, arrn31, arrn32, arrn33, arrn34, arrn35, arrn36, {97, 10, -260, 4}, arrn37, {98, 10, -275, 1, 3}, arrn38, arrn39, arrn40, {66, 10, -308, 4}, arrn41, arrn42, {107, 11, 432, 1, 2}, {104, 11, 256, 1, 2}, arrn43, {109, 12, 256, 1, 2}, {115, 13, 256, 1, 2}, arrn44, {83, 14, 256, 1, 1000}, arrn45, arrn46, {122, 15, -259, 1, 3}, arrn47, arrn48, arrn49, {90, 15, -275, 5}, arrn50, {79, 15, -276, 4}, arrn51, arrn52, {86, 15, -277, 3}, arrn53, arrn54, {88, 15, -275, 2}, arrn55, arrn56, {120, 15, -275, 2}, arrn57};
    }

    protected DateTimePatternGenerator() {
    }

    private void addCLDRData(PatternInfo object, ULocale object2) {
        Object object3;
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (ULocale)object2);
        object2 = this.getCalendarTypeToUse((ULocale)object2);
        AppendItemFormatsSink appendItemFormatsSink = new AppendItemFormatsSink();
        try {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("calendar/");
            ((StringBuilder)object3).append((String)object2);
            ((StringBuilder)object3).append("/appendItems");
            iCUResourceBundle.getAllItemsWithFallback(((StringBuilder)object3).toString(), appendItemFormatsSink);
        }
        catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        object3 = new AppendItemNamesSink();
        try {
            iCUResourceBundle.getAllItemsWithFallback("fields", (UResource.Sink)object3);
        }
        catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        object3 = new AvailableFormatsSink((PatternInfo)object);
        try {
            object = new StringBuilder();
            ((StringBuilder)object).append("calendar/");
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append("/availableFormats");
            iCUResourceBundle.getAllItemsWithFallback(((StringBuilder)object).toString(), (UResource.Sink)object3);
        }
        catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
    }

    private void addCanonicalItems() {
        String[] arrstring;
        PatternInfo patternInfo = new PatternInfo();
        for (int i = 0; i < (arrstring = CANONICAL_ITEMS).length; ++i) {
            this.addPattern(String.valueOf(arrstring[i]), false, patternInfo);
        }
    }

    private void addICUPatterns(PatternInfo patternInfo, ULocale uLocale) {
        for (int i = 0; i <= 3; ++i) {
            this.addPattern(((SimpleDateFormat)DateFormat.getDateInstance(i, uLocale)).toPattern(), false, patternInfo);
            SimpleDateFormat simpleDateFormat = (SimpleDateFormat)DateFormat.getTimeInstance(i, uLocale);
            this.addPattern(simpleDateFormat.toPattern(), false, patternInfo);
            if (i != 3) continue;
            this.consumeShortTimePattern(simpleDateFormat.toPattern(), patternInfo);
        }
    }

    private String adjustFieldTypes(PatternWithMatcher patternWithMatcher, DateTimeMatcher dateTimeMatcher, EnumSet<DTPGflags> enumSet, int n) {
        this.fp.set(patternWithMatcher.pattern);
        StringBuilder stringBuilder = new StringBuilder();
        for (Object object : this.fp.getItems()) {
            block11 : {
                char c;
                int n2;
                StringBuilder stringBuilder2;
                int n3;
                int n4;
                block13 : {
                    block14 : {
                        VariableField variableField;
                        int n5;
                        block12 : {
                            block10 : {
                                if (object instanceof String) {
                                    stringBuilder.append(this.fp.quoteLiteral((String)object));
                                    continue;
                                }
                                variableField = (VariableField)object;
                                stringBuilder2 = new StringBuilder(variableField.toString());
                                n3 = variableField.getType();
                                if (!enumSet.contains((Object)DTPGflags.FIX_FRACTIONAL_SECONDS) || n3 != 13) break block10;
                                stringBuilder2.append(this.decimal);
                                dateTimeMatcher.original.appendFieldTo(14, stringBuilder2);
                                object = stringBuilder2;
                                break block11;
                            }
                            object = stringBuilder2;
                            if (dateTimeMatcher.type[n3] == 0) break block11;
                            c = dateTimeMatcher.original.getFieldChar(n3);
                            n4 = n2 = dateTimeMatcher.original.getFieldLength(n3);
                            if (c == 'E') {
                                n4 = n2;
                                if (n2 < 3) {
                                    n4 = 3;
                                }
                            }
                            n5 = n4;
                            object = patternWithMatcher.matcherWithSkeleton;
                            if (!(n3 == 11 && (n & 2048) == 0 || n3 == 12 && (n & 4096) == 0) && (n3 != 13 || (n & 8192) != 0)) break block12;
                            n2 = stringBuilder2.length();
                            break block13;
                        }
                        n2 = n5;
                        if (object == null) break block13;
                        n2 = ((DateTimeMatcher)object).original.getFieldLength(n3);
                        boolean bl = variableField.isNumeric();
                        boolean bl2 = ((DateTimeMatcher)object).fieldIsNumeric(n3);
                        if (n2 == n4 || bl && !bl2) break block14;
                        n2 = n5;
                        if (!bl2) break block13;
                        n2 = n5;
                        if (bl) break block13;
                    }
                    n2 = stringBuilder2.length();
                }
                n4 = n3 != 11 && n3 != 3 && n3 != 6 && (n3 != 1 || c == 'Y') ? (int)c : (int)stringBuilder2.charAt(0);
                int n6 = n4;
                if (n3 == 11) {
                    n6 = n4;
                    if (enumSet.contains((Object)DTPGflags.SKELETON_USES_CAP_J)) {
                        n6 = n4 = (int)this.defaultHourFormatChar;
                    }
                }
                stringBuilder2 = new StringBuilder();
                do {
                    object = stringBuilder2;
                    if (n2 <= 0) break;
                    stringBuilder2.append((char)n6);
                    --n2;
                } while (true);
            }
            stringBuilder.append((CharSequence)object);
        }
        return stringBuilder.toString();
    }

    private void checkFrozen() {
        if (!this.isFrozen()) {
            return;
        }
        throw new UnsupportedOperationException("Attempt to modify frozen object");
    }

    private void consumeShortTimePattern(String string, PatternInfo patternInfo) {
        Object object = new FormatParser();
        ((FormatParser)object).set(string);
        object = ((FormatParser)object).getItems();
        for (int i = 0; i < object.size(); ++i) {
            Object object2 = object.get(i);
            if (!(object2 instanceof VariableField) || ((VariableField)(object2 = (VariableField)object2)).getType() != 11) continue;
            this.defaultHourFormatChar = ((VariableField)object2).toString().charAt(0);
            break;
        }
        this.hackTimes(patternInfo, string);
    }

    private void fillInMissing() {
        for (int i = 0; i < 16; ++i) {
            if (this.getAppendItemFormat(i) == null) {
                this.setAppendItemFormat(i, "{0} \u251c{2}: {1}\u2524");
            }
            if (this.getFieldDisplayName(i, DisplayWidth.WIDE) == null) {
                DisplayWidth displayWidth = DisplayWidth.WIDE;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("F");
                stringBuilder.append(i);
                this.setFieldDisplayName(i, displayWidth, stringBuilder.toString());
            }
            if (this.getFieldDisplayName(i, DisplayWidth.ABBREVIATED) == null) {
                this.setFieldDisplayName(i, DisplayWidth.ABBREVIATED, this.getFieldDisplayName(i, DisplayWidth.WIDE));
            }
            if (this.getFieldDisplayName(i, DisplayWidth.NARROW) != null) continue;
            this.setFieldDisplayName(i, DisplayWidth.NARROW, this.getFieldDisplayName(i, DisplayWidth.ABBREVIATED));
        }
    }

    private void getAllowedHourFormats(ULocale arrstring) {
        String[] arrstring2 = ULocale.addLikelySubtags((ULocale)arrstring);
        String[] arrstring3 = arrstring = arrstring2.getCountry();
        if (arrstring.isEmpty()) {
            arrstring3 = "001";
        }
        arrstring = new StringBuilder();
        arrstring.append(arrstring2.getLanguage());
        arrstring.append("_");
        arrstring.append((String)arrstring3);
        arrstring = arrstring.toString();
        arrstring = arrstring2 = LOCALE_TO_ALLOWED_HOUR.get(arrstring);
        if (arrstring2 == null) {
            arrstring = arrstring3 = LOCALE_TO_ALLOWED_HOUR.get(arrstring3);
            if (arrstring3 == null) {
                arrstring = LAST_RESORT_ALLOWED_HOUR_FORMAT;
            }
        }
        this.allowedHourFormats = arrstring;
    }

    private String getAppendFormat(int n) {
        return this.appendItemFormats[n];
    }

    @Deprecated
    public static int getAppendFormatNumber(UResource.Key key) {
        String[] arrstring;
        for (int i = 0; i < (arrstring = CLDR_FIELD_APPEND).length; ++i) {
            if (!key.contentEquals(arrstring[i])) continue;
            return i;
        }
        return -1;
    }

    @Deprecated
    public static int getAppendFormatNumber(String string) {
        String[] arrstring;
        for (int i = 0; i < (arrstring = CLDR_FIELD_APPEND).length; ++i) {
            if (!arrstring[i].equals(string)) continue;
            return i;
        }
        return -1;
    }

    private String getAppendName(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'");
        stringBuilder.append(this.fieldDisplayNames[n][APPENDITEM_WIDTH_INT]);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }

    private String getBestAppending(DateTimeMatcher dateTimeMatcher, int n, DistanceInfo distanceInfo, DateTimeMatcher dateTimeMatcher2, EnumSet<DTPGflags> object, int n2) {
        EnumSet<DTPGflags> enumSet = null;
        if (n != 0) {
            PatternWithMatcher patternWithMatcher = this.getBestRaw(dateTimeMatcher, n, distanceInfo, dateTimeMatcher2);
            Object object2 = this.adjustFieldTypes(patternWithMatcher, dateTimeMatcher, (EnumSet<DTPGflags>)object, n2);
            enumSet = object;
            object = object2;
            do {
                Object object3 = dateTimeMatcher;
                object2 = this;
                if (distanceInfo.missingFieldMask != 0) {
                    if ((distanceInfo.missingFieldMask & 24576) == 16384 && (n & 24576) == 24576) {
                        patternWithMatcher.pattern = object;
                        enumSet = EnumSet.copyOf(enumSet);
                        enumSet.add(DTPGflags.FIX_FRACTIONAL_SECONDS);
                        object = DateTimePatternGenerator.super.adjustFieldTypes(patternWithMatcher, (DateTimeMatcher)object3, enumSet, n2);
                        distanceInfo.missingFieldMask &= -16385;
                        continue;
                    }
                    int n3 = distanceInfo.missingFieldMask;
                    object3 = DateTimePatternGenerator.super.adjustFieldTypes(DateTimePatternGenerator.super.getBestRaw((DateTimeMatcher)object3, distanceInfo.missingFieldMask, distanceInfo, dateTimeMatcher2), (DateTimeMatcher)object3, enumSet, n2);
                    n3 = DateTimePatternGenerator.super.getTopBitNumber(distanceInfo.missingFieldMask & n3);
                    object = SimpleFormatterImpl.formatRawPattern(DateTimePatternGenerator.super.getAppendFormat(n3), 2, 3, new CharSequence[]{object, object3, DateTimePatternGenerator.super.getAppendName(n3)});
                    continue;
                }
                break;
            } while (true);
        } else {
            object = enumSet;
        }
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private String getBestPattern(String object, DateTimeMatcher dateTimeMatcher, int n) {
        var4_6 = EnumSet.noneOf(DTPGflags.class);
        object = this.mapSkeletonMetacharacters((String)object, var4_6);
        // MONITORENTER : this
        this.current.set((String)object, this.fp, false);
        object = this.current;
        var5_7 = this._distanceInfo;
        object = this.getBestRaw((DateTimeMatcher)object, -1, (DistanceInfo)var5_7, var2_4);
        if (this._distanceInfo.missingFieldMask != 0 || this._distanceInfo.extraFieldMask != 0) ** GOTO lbl16
        var2_4 = this.current;
        object = this.adjustFieldTypes((PatternWithMatcher)object, var2_4, var4_6, (int)var3_5);
        // MONITOREXIT : this
        return object;
lbl16: // 1 sources:
        var6_8 = this.current.getFieldMask();
        var5_7 = this.getBestAppending(this.current, var6_8 & 1023, this._distanceInfo, var2_4, var4_6, (int)var3_5);
        object = this.getBestAppending(this.current, var6_8 & 64512, this._distanceInfo, var2_4, var4_6, (int)var3_5);
        // MONITOREXIT : this
        if (var5_7 == null) {
            if (object != null) return object;
            return "";
        }
        if (object != null) return SimpleFormatterImpl.formatRawPattern(this.getDateTimeFormat(), 2, 2, new CharSequence[]{object, var5_7});
        return var5_7;
        catch (Throwable throwable) {
            throw object;
            catch (Throwable throwable) {
                throw object;
            }
        }
    }

    private PatternWithMatcher getBestRaw(DateTimeMatcher dateTimeMatcher, int n, DistanceInfo distanceInfo, DateTimeMatcher dateTimeMatcher2) {
        int n2 = Integer.MAX_VALUE;
        PatternWithMatcher patternWithMatcher = new PatternWithMatcher("", null);
        DistanceInfo distanceInfo2 = new DistanceInfo();
        for (DateTimeMatcher dateTimeMatcher3 : this.skeleton2pattern.keySet()) {
            if (dateTimeMatcher3.equals(dateTimeMatcher2)) continue;
            int n3 = dateTimeMatcher.getDistance(dateTimeMatcher3, n, distanceInfo2);
            int n4 = n2;
            if (n3 < n2) {
                n4 = n3;
                PatternWithSkeletonFlag patternWithSkeletonFlag = this.skeleton2pattern.get(dateTimeMatcher3);
                patternWithMatcher.pattern = patternWithSkeletonFlag.pattern;
                patternWithMatcher.matcherWithSkeleton = patternWithSkeletonFlag.skeletonWasSpecified ? dateTimeMatcher3 : null;
                distanceInfo.setTo(distanceInfo2);
                if (n3 == 0) break;
            }
            n2 = n4;
        }
        return patternWithMatcher;
    }

    private static int getCLDRFieldAndWidthNumber(UResource.Key key) {
        for (int i = 0; i < CLDR_FIELD_NAME.length; ++i) {
            for (int j = 0; j < DisplayWidth.COUNT; ++j) {
                if (!key.contentEquals(CLDR_FIELD_NAME[i].concat(DateTimePatternGenerator.CLDR_FIELD_WIDTH[j].cldrKey()))) continue;
                return DisplayWidth.COUNT * i + j;
            }
        }
        return -1;
    }

    private String getCalendarTypeToUse(ULocale object) {
        String string;
        String string2 = string = ((ULocale)object).getKeywordValue("calendar");
        if (string == null) {
            string2 = Calendar.getKeywordValuesForLocale("calendar", (ULocale)object, true)[0];
        }
        object = string2;
        if (string2 == null) {
            object = "gregorian";
        }
        return object;
    }

    private static char getCanonicalChar(int n, char c) {
        if (c != 'h' && c != 'K') {
            Object object;
            for (c = '\u0000'; c < ((int[][])(object = types)).length; c = (char)(c + 1)) {
                if ((object = object[c])[1] != n) continue;
                return (char)object[0];
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not find field ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        return 'h';
    }

    private static int getCanonicalIndex(String arrn, boolean bl) {
        int n;
        block4 : {
            int n2;
            int n3 = arrn.length();
            int n4 = -1;
            if (n3 == 0) {
                return -1;
            }
            char c = arrn.charAt(0);
            for (n2 = 1; n2 < n3; ++n2) {
                if (arrn.charAt(n2) == c) continue;
                return -1;
            }
            n = -1;
            for (n2 = 0; n2 < (arrn = types).length; ++n2) {
                if ((arrn = arrn[n2])[0] != c) continue;
                n = n2;
                if (arrn[3] > n3 || arrn[arrn.length - 1] < n3) {
                    continue;
                }
                return n2;
            }
            if (!bl) break block4;
            n = n4;
        }
        return n;
    }

    public static DateTimePatternGenerator getEmptyInstance() {
        DateTimePatternGenerator dateTimePatternGenerator = new DateTimePatternGenerator();
        dateTimePatternGenerator.addCanonicalItems();
        dateTimePatternGenerator.fillInMissing();
        return dateTimePatternGenerator;
    }

    private static String getFilteredPattern(FormatParser formatParser, BitSet bitSet) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < formatParser.items.size(); ++i) {
            if (bitSet.get(i)) continue;
            Object e = formatParser.items.get(i);
            if (e instanceof String) {
                stringBuilder.append(formatParser.quoteLiteral(e.toString()));
                continue;
            }
            stringBuilder.append(e.toString());
        }
        return stringBuilder.toString();
    }

    @Deprecated
    public static DateTimePatternGenerator getFrozenInstance(ULocale uLocale) {
        String string = uLocale.toString();
        DateTimePatternGenerator dateTimePatternGenerator = DTPNG_CACHE.get(string);
        if (dateTimePatternGenerator != null) {
            return dateTimePatternGenerator;
        }
        dateTimePatternGenerator = new DateTimePatternGenerator();
        dateTimePatternGenerator.initData(uLocale);
        dateTimePatternGenerator.freeze();
        DTPNG_CACHE.put(string, dateTimePatternGenerator);
        return dateTimePatternGenerator;
    }

    public static DateTimePatternGenerator getInstance() {
        return DateTimePatternGenerator.getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
    }

    public static DateTimePatternGenerator getInstance(ULocale uLocale) {
        return DateTimePatternGenerator.getFrozenInstance(uLocale).cloneAsThawed();
    }

    public static DateTimePatternGenerator getInstance(Locale locale) {
        return DateTimePatternGenerator.getInstance(ULocale.forLocale(locale));
    }

    private static String getName(String object) {
        int n = DateTimePatternGenerator.getCanonicalIndex((String)object, true);
        object = FIELD_NAME;
        Object object2 = types;
        object = object[object2[n][1]];
        if (object2[n][2] < 0) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(":S");
            object = ((StringBuilder)object2).toString();
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append(":N");
            object = ((StringBuilder)object2).toString();
        }
        return object;
    }

    private TreeSet<String> getSet(String object) {
        Object object2 = this.fp.set((String)object).getItems();
        object = new TreeSet();
        object2 = object2.iterator();
        while (object2.hasNext()) {
            String string = object2.next().toString();
            if (string.startsWith("G") || string.startsWith("a")) continue;
            ((TreeSet)object).add(string);
        }
        return object;
    }

    private int getTopBitNumber(int n) {
        int n2 = 0;
        while (n != 0) {
            n >>>= 1;
            ++n2;
        }
        return n2 - 1;
    }

    private void hackTimes(PatternInfo patternInfo, String string) {
        int n;
        int n2;
        this.fp.set(string);
        Serializable serializable = new StringBuilder();
        int n3 = 0;
        for (n2 = 0; n2 < this.fp.items.size(); ++n2) {
            string = this.fp.items.get(n2);
            if (string instanceof String) {
                n = n3;
                if (n3 != 0) {
                    ((StringBuilder)serializable).append(this.fp.quoteLiteral(string.toString()));
                    n = n3;
                }
            } else {
                char c = string.toString().charAt(0);
                if (c == 'm') {
                    n = 1;
                    ((StringBuilder)serializable).append((Object)string);
                } else {
                    if (c == 's') {
                        if (n3 == 0) break;
                        ((StringBuilder)serializable).append((Object)string);
                        this.addPattern(((StringBuilder)serializable).toString(), false, patternInfo);
                        break;
                    }
                    if (n3 != 0 || c == 'z' || c == 'Z' || c == 'v') break;
                    n = n3;
                    if (c == 'V') break;
                }
            }
            n3 = n;
        }
        BitSet bitSet = new BitSet();
        serializable = new BitSet();
        for (n2 = 0; n2 < this.fp.items.size(); ++n2) {
            string = this.fp.items.get(n2);
            if (!(string instanceof VariableField)) continue;
            bitSet.set(n2);
            n = string.toString().charAt(0);
            if (n != 115 && n != 83) continue;
            ((BitSet)serializable).set(n2);
            for (n = n2 - 1; n >= 0 && !bitSet.get(n); ++n) {
                ((BitSet)serializable).set(n2);
            }
        }
        this.addPattern(DateTimePatternGenerator.getFilteredPattern(this.fp, (BitSet)serializable), false, patternInfo);
    }

    private void initData(ULocale uLocale) {
        PatternInfo patternInfo = new PatternInfo();
        this.addCanonicalItems();
        this.addICUPatterns(patternInfo, uLocale);
        this.addCLDRData(patternInfo, uLocale);
        this.setDateTimeFromCalendar(uLocale);
        this.setDecimalSymbols(uLocale);
        this.getAllowedHourFormats(uLocale);
        this.fillInMissing();
    }

    private boolean isAvailableFormatSet(String string) {
        return this.cldrAvailableFormatKeys.contains(string);
    }

    @Deprecated
    public static boolean isSingleField(String string) {
        char c = string.charAt(0);
        for (int i = 1; i < string.length(); ++i) {
            if (string.charAt(i) == c) continue;
            return false;
        }
        return true;
    }

    private String mapSkeletonMetacharacters(String string, EnumSet<DTPGflags> enumSet) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        for (int i = 0; i < string.length(); ++i) {
            char c;
            int n2;
            int n3;
            int n4;
            block13 : {
                char c2;
                block14 : {
                    int n5;
                    block12 : {
                        n4 = string.charAt(i);
                        n2 = 0;
                        if (n4 == 39) {
                            if (n == 0) {
                                n2 = 1;
                            }
                            n = n2;
                            continue;
                        }
                        if (n != 0) continue;
                        if (n4 != 106 && n4 != 67) {
                            if (n4 == 74) {
                                stringBuilder.append('H');
                                enumSet.add(DTPGflags.SKELETON_USES_CAP_J);
                                continue;
                            }
                            stringBuilder.append((char)n4);
                            continue;
                        }
                        n2 = 0;
                        while (i + 1 < string.length() && string.charAt(i + 1) == n4) {
                            ++n2;
                            ++i;
                        }
                        int n6 = (n2 & true) + 1;
                        n2 = n2 < 2 ? 1 : (n2 >> 1) + 3;
                        n5 = 97;
                        if (n4 != 106) break block12;
                        n3 = this.defaultHourFormatChar;
                        c = n3;
                        n4 = n5;
                        break block13;
                    }
                    String string2 = this.allowedHourFormats[0];
                    c2 = string2.charAt(0);
                    n3 = string2.charAt(string2.length() - 1);
                    if (n3 == 98) break block14;
                    c = c2;
                    n4 = n5;
                    if (n3 != 66) break block13;
                }
                n4 = n3;
                c = c2;
            }
            if (c == 'H' || c == 'k') {
                n2 = 0;
            }
            do {
                if (n2 <= 0) break;
                stringBuilder.append((char)n4);
                --n2;
            } while (true);
            for (n3 = n6; n3 > 0; --n3) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    private void setAvailableFormat(String string) {
        this.checkFrozen();
        this.cldrAvailableFormatKeys.add(string);
    }

    private void setDateTimeFromCalendar(ULocale uLocale) {
        this.setDateTimeFormat(Calendar.getDateTimePattern(Calendar.getInstance(uLocale), uLocale, 2));
    }

    private void setDecimalSymbols(ULocale uLocale) {
        this.setDecimal(String.valueOf(new DecimalFormatSymbols(uLocale).getDecimalSeparator()));
    }

    @Deprecated
    private void setFieldDisplayName(int n, DisplayWidth displayWidth, String string) {
        this.checkFrozen();
        if (n < 16 && n >= 0) {
            this.fieldDisplayNames[n][displayWidth.ordinal()] = string;
        }
    }

    private static String showMask(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; ++i) {
            if ((1 << i & n) == 0) continue;
            if (stringBuilder.length() != 0) {
                stringBuilder.append(" | ");
            }
            stringBuilder.append(FIELD_NAME[i]);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    public DateTimePatternGenerator addPattern(String string, boolean bl, PatternInfo patternInfo) {
        return this.addPatternWithSkeleton(string, null, bl, patternInfo);
    }

    @Deprecated
    public DateTimePatternGenerator addPatternWithSkeleton(String object, String string, boolean bl, PatternInfo patternInfo) {
        this.checkFrozen();
        boolean bl2 = false;
        DateTimeMatcher dateTimeMatcher = string == null ? new DateTimeMatcher().set((String)object, this.fp, false) : new DateTimeMatcher().set(string, this.fp, false);
        String string2 = dateTimeMatcher.getBasePattern();
        PatternWithSkeletonFlag patternWithSkeletonFlag = this.basePattern_pattern.get(string2);
        if (patternWithSkeletonFlag != null && (!patternWithSkeletonFlag.skeletonWasSpecified || string != null && !bl)) {
            patternInfo.status = 1;
            patternInfo.conflictingPattern = patternWithSkeletonFlag.pattern;
            if (!bl) {
                return this;
            }
        }
        if ((patternWithSkeletonFlag = this.skeleton2pattern.get(dateTimeMatcher)) != null) {
            patternInfo.status = 2;
            patternInfo.conflictingPattern = patternWithSkeletonFlag.pattern;
            if (!bl || string != null && patternWithSkeletonFlag.skeletonWasSpecified) {
                return this;
            }
        }
        patternInfo.status = 0;
        patternInfo.conflictingPattern = "";
        bl = bl2;
        if (string != null) {
            bl = true;
        }
        object = new PatternWithSkeletonFlag((String)object, bl);
        this.skeleton2pattern.put(dateTimeMatcher, (PatternWithSkeletonFlag)object);
        this.basePattern_pattern.put(string2, (PatternWithSkeletonFlag)object);
        return this;
    }

    public Object clone() {
        try {
            DateTimePatternGenerator dateTimePatternGenerator = (DateTimePatternGenerator)super.clone();
            dateTimePatternGenerator.skeleton2pattern = (TreeMap)this.skeleton2pattern.clone();
            dateTimePatternGenerator.basePattern_pattern = (TreeMap)this.basePattern_pattern.clone();
            dateTimePatternGenerator.appendItemFormats = (String[])this.appendItemFormats.clone();
            dateTimePatternGenerator.fieldDisplayNames = (String[][])this.fieldDisplayNames.clone();
            Object object = new DateTimeMatcher();
            dateTimePatternGenerator.current = object;
            dateTimePatternGenerator.fp = object = new FormatParser();
            dateTimePatternGenerator._distanceInfo = object = new DistanceInfo();
            dateTimePatternGenerator.frozen = false;
            return dateTimePatternGenerator;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException("Internal Error", cloneNotSupportedException);
        }
    }

    @Override
    public DateTimePatternGenerator cloneAsThawed() {
        DateTimePatternGenerator dateTimePatternGenerator = (DateTimePatternGenerator)this.clone();
        this.frozen = false;
        return dateTimePatternGenerator;
    }

    @Override
    public DateTimePatternGenerator freeze() {
        this.frozen = true;
        return this;
    }

    public String getAppendItemFormat(int n) {
        return this.appendItemFormats[n];
    }

    public String getAppendItemName(int n) {
        return this.getFieldDisplayName(n, APPENDITEM_WIDTH);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getBaseSkeleton(String string) {
        synchronized (this) {
            this.current.set(string, this.fp, false);
            return this.current.getBasePattern();
        }
    }

    public Set<String> getBaseSkeletons(Set<String> set) {
        Set<String> set2 = set;
        if (set == null) {
            set2 = new HashSet<String>();
        }
        set2.addAll(this.basePattern_pattern.keySet());
        return set2;
    }

    public String getBestPattern(String string) {
        return this.getBestPattern(string, null, 0);
    }

    public String getBestPattern(String string, int n) {
        return this.getBestPattern(string, null, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public String getCanonicalSkeletonAllowingDuplicates(String string) {
        synchronized (this) {
            this.current.set(string, this.fp, true);
            return this.current.toCanonicalString();
        }
    }

    public String getDateTimeFormat() {
        return this.dateTimeFormat;
    }

    public String getDecimal() {
        return this.decimal;
    }

    @Deprecated
    public char getDefaultHourFormatChar() {
        return this.defaultHourFormatChar;
    }

    public String getFieldDisplayName(int n, DisplayWidth displayWidth) {
        if (n < 16 && n >= 0) {
            return this.fieldDisplayNames[n][displayWidth.ordinal()];
        }
        return "";
    }

    @Deprecated
    public String getFields(String charSequence) {
        this.fp.set((String)charSequence);
        charSequence = new StringBuilder();
        for (Object object : this.fp.getItems()) {
            if (object instanceof String) {
                ((StringBuilder)charSequence).append(this.fp.quoteLiteral((String)object));
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            stringBuilder.append(DateTimePatternGenerator.getName(object.toString()));
            stringBuilder.append("}");
            ((StringBuilder)charSequence).append(stringBuilder.toString());
        }
        return ((StringBuilder)charSequence).toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public Collection<String> getRedundants(Collection<String> object) {
        synchronized (this) {
            LinkedHashSet<String> linkedHashSet = object;
            if (object == null) {
                linkedHashSet = new LinkedHashSet<String>();
            }
            Iterator<DateTimeMatcher> iterator = this.skeleton2pattern.keySet().iterator();
            while (iterator.hasNext()) {
                DateTimeMatcher dateTimeMatcher = iterator.next();
                object = this.skeleton2pattern.get((Object)dateTimeMatcher).pattern;
                if (CANONICAL_SET.contains(object) || !this.getBestPattern(dateTimeMatcher.toString(), dateTimeMatcher, 0).equals(object)) continue;
                linkedHashSet.add((String)object);
            }
            return linkedHashSet;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getSkeleton(String string) {
        synchronized (this) {
            this.current.set(string, this.fp, false);
            return this.current.toString();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public String getSkeletonAllowingDuplicates(String string) {
        synchronized (this) {
            this.current.set(string, this.fp, true);
            return this.current.toString();
        }
    }

    public Map<String, String> getSkeletons(Map<String, String> object) {
        Map<String, String> map = object;
        if (object == null) {
            map = new LinkedHashMap<String, String>();
        }
        for (DateTimeMatcher dateTimeMatcher : this.skeleton2pattern.keySet()) {
            String string = this.skeleton2pattern.get((Object)dateTimeMatcher).pattern;
            if (CANONICAL_SET.contains(string)) continue;
            map.put(dateTimeMatcher.toString(), string);
        }
        return map;
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

    public String replaceFieldTypes(String string, String string2) {
        return this.replaceFieldTypes(string, string2, 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String replaceFieldTypes(String string, String string2, int n) {
        synchronized (this) {
            PatternWithMatcher patternWithMatcher = new PatternWithMatcher(string, null);
            return this.adjustFieldTypes(patternWithMatcher, this.current.set(string2, this.fp, false), EnumSet.noneOf(DTPGflags.class), n);
        }
    }

    public void setAppendItemFormat(int n, String string) {
        this.checkFrozen();
        this.appendItemFormats[n] = string;
    }

    public void setAppendItemName(int n, String string) {
        this.setFieldDisplayName(n, APPENDITEM_WIDTH, string);
    }

    public void setDateTimeFormat(String string) {
        this.checkFrozen();
        this.dateTimeFormat = string;
    }

    public void setDecimal(String string) {
        this.checkFrozen();
        this.decimal = string;
    }

    @Deprecated
    public void setDefaultHourFormatChar(char c) {
        this.defaultHourFormatChar = c;
    }

    @Deprecated
    public boolean skeletonsAreSimilar(String iterator, String arrn) {
        if (((String)((Object)iterator)).equals(arrn)) {
            return true;
        }
        Object object = this.getSet((String)((Object)iterator));
        iterator = this.getSet((String)arrn);
        if (((TreeSet)object).size() != ((TreeSet)((Object)iterator)).size()) {
            return false;
        }
        iterator = ((TreeSet)((Object)iterator)).iterator();
        object = ((TreeSet)object).iterator();
        while (object.hasNext()) {
            int n;
            arrn = types;
            int n2 = DateTimePatternGenerator.getCanonicalIndex((String)object.next(), false);
            if (arrn[n2][1] == arrn[n = DateTimePatternGenerator.getCanonicalIndex((String)iterator.next(), false)][1]) continue;
            return false;
        }
        return true;
    }

    private class AppendItemFormatsSink
    extends UResource.Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        private AppendItemFormatsSink() {
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2 = DateTimePatternGenerator.getAppendFormatNumber(key);
                if (DateTimePatternGenerator.this.getAppendItemFormat(n2) == null) {
                    DateTimePatternGenerator.this.setAppendItemFormat(n2, value.toString());
                }
                ++n;
            }
        }
    }

    private class AppendItemNamesSink
    extends UResource.Sink {
        private AppendItemNamesSink() {
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2;
                if (value.getType() == 2 && (n2 = DateTimePatternGenerator.getCLDRFieldAndWidthNumber(key)) != -1) {
                    int n3 = n2 / DisplayWidth.COUNT;
                    DisplayWidth displayWidth = CLDR_FIELD_WIDTH[n2 % DisplayWidth.COUNT];
                    UResource.Table table2 = value.getTable();
                    n2 = 0;
                    while (table2.getKeyAndValue(n2, key, value)) {
                        if (!key.contentEquals("dn")) {
                            ++n2;
                            continue;
                        }
                        if (DateTimePatternGenerator.this.getFieldDisplayName(n3, displayWidth) != null) break;
                        DateTimePatternGenerator.this.setFieldDisplayName(n3, displayWidth, value.toString());
                        break;
                    }
                }
                ++n;
            }
        }
    }

    private class AvailableFormatsSink
    extends UResource.Sink {
        PatternInfo returnInfo;

        public AvailableFormatsSink(PatternInfo patternInfo) {
            this.returnInfo = patternInfo;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                String string = key.toString();
                if (!DateTimePatternGenerator.this.isAvailableFormatSet(string)) {
                    DateTimePatternGenerator.this.setAvailableFormat(string);
                    String string2 = value.toString();
                    DateTimePatternGenerator.this.addPatternWithSkeleton(string2, string, bl ^ true, this.returnInfo);
                }
                ++n;
            }
        }
    }

    private static enum DTPGflags {
        FIX_FRACTIONAL_SECONDS,
        SKELETON_USES_CAP_J;
        
    }

    private static class DateTimeMatcher
    implements Comparable<DateTimeMatcher> {
        private boolean addedDefaultDayPeriod = false;
        private SkeletonFields baseOriginal = new SkeletonFields();
        private SkeletonFields original = new SkeletonFields();
        private int[] type = new int[16];

        private DateTimeMatcher() {
        }

        @Override
        public int compareTo(DateTimeMatcher dateTimeMatcher) {
            int n = this.original.compareTo(dateTimeMatcher.original);
            n = n > 0 ? -1 : (n < 0 ? 1 : 0);
            return n;
        }

        public boolean equals(Object object) {
            boolean bl = this == object || object != null && object instanceof DateTimeMatcher && this.original.equals(((DateTimeMatcher)object).original);
            return bl;
        }

        void extractFrom(DateTimeMatcher dateTimeMatcher, int n) {
            int[] arrn;
            for (int i = 0; i < (arrn = this.type).length; ++i) {
                if ((1 << i & n) != 0) {
                    arrn[i] = dateTimeMatcher.type[i];
                    this.original.copyFieldFrom(dateTimeMatcher.original, i);
                    continue;
                }
                arrn[i] = 0;
                this.original.clearField(i);
            }
        }

        public boolean fieldIsNumeric(int n) {
            boolean bl = this.type[n] > 0;
            return bl;
        }

        String getBasePattern() {
            return this.baseOriginal.toString(this.addedDefaultDayPeriod);
        }

        int getDistance(DateTimeMatcher dateTimeMatcher, int n, DistanceInfo distanceInfo) {
            int n2 = 0;
            distanceInfo.clear();
            for (int i = 0; i < 16; ++i) {
                int n3;
                int n4 = (1 << i & n) == 0 ? 0 : this.type[i];
                if (n4 == (n3 = dateTimeMatcher.type[i])) continue;
                if (n4 == 0) {
                    n2 += 65536;
                    distanceInfo.addExtra(i);
                    continue;
                }
                if (n3 == 0) {
                    n2 += 4096;
                    distanceInfo.addMissing(i);
                    continue;
                }
                n2 += Math.abs(n4 - n3);
            }
            return n2;
        }

        int getFieldMask() {
            int[] arrn;
            int n = 0;
            for (int i = 0; i < (arrn = this.type).length; ++i) {
                int n2 = n;
                if (arrn[i] != 0) {
                    n2 = n | 1 << i;
                }
                n = n2;
            }
            return n;
        }

        public int hashCode() {
            return this.original.hashCode();
        }

        DateTimeMatcher set(String arrn, FormatParser object, boolean bl) {
            block6 : {
                int n;
                block7 : {
                    Arrays.fill(this.type, 0);
                    this.original.clear();
                    this.baseOriginal.clear();
                    this.addedDefaultDayPeriod = false;
                    ((FormatParser)object).set((String)arrn);
                    object = ((FormatParser)object).getItems().iterator();
                    while (object.hasNext()) {
                        int n2;
                        char c;
                        Object object2 = object.next();
                        if (!(object2 instanceof VariableField)) continue;
                        int[] arrn2 = (int[])object2;
                        object2 = arrn2.toString();
                        n = ((VariableField)arrn2).getCanonicalIndex();
                        arrn2 = types[n];
                        int n3 = arrn2[1];
                        if (!this.original.isFieldEmpty(n3)) {
                            c = this.original.getFieldChar(n3);
                            n = ((String)object2).charAt(0);
                            if (bl || c == 'r' && n == 85 || c == 'U' && n == 114) continue;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Conflicting fields:\t");
                            ((StringBuilder)object).append(c);
                            ((StringBuilder)object).append(", ");
                            ((StringBuilder)object).append((String)object2);
                            ((StringBuilder)object).append("\t in ");
                            ((StringBuilder)object).append((String)arrn);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        this.original.populate(n3, (String)object2);
                        c = (char)arrn2[0];
                        n = arrn2[3];
                        if ("GEzvQ".indexOf(c) >= 0) {
                            n = 1;
                        }
                        this.baseOriginal.populate(n3, c, n);
                        n = n2 = arrn2[2];
                        if (n2 > 0) {
                            n = n2 + ((String)object2).length();
                        }
                        this.type[n3] = n;
                    }
                    if (this.original.isFieldEmpty(11)) break block6;
                    if (this.original.getFieldChar(11) == 'h' || this.original.getFieldChar(11) == 'K') break block7;
                    if (this.original.isFieldEmpty(10)) break block6;
                    this.original.clearField(10);
                    this.baseOriginal.clearField(10);
                    this.type[10] = 0;
                    break block6;
                }
                if (this.original.isFieldEmpty(10)) {
                    for (n = 0; n < types.length; ++n) {
                        arrn = types[n];
                        if (arrn[1] != 10) continue;
                        this.original.populate(10, (char)arrn[0], arrn[3]);
                        this.baseOriginal.populate(10, (char)arrn[0], arrn[3]);
                        this.type[10] = arrn[2];
                        this.addedDefaultDayPeriod = true;
                        break;
                    }
                }
            }
            return this;
        }

        public String toCanonicalString() {
            return this.original.toCanonicalString(this.addedDefaultDayPeriod);
        }

        public String toString() {
            return this.original.toString(this.addedDefaultDayPeriod);
        }
    }

    private static class DayPeriodAllowedHoursSink
    extends UResource.Sink {
        HashMap<String, String[]> tempMap;

        private DayPeriodAllowedHoursSink(HashMap<String, String[]> hashMap) {
            this.tempMap = hashMap;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                String string = key.toString();
                UResource.Table table2 = value.getTable();
                int n2 = 0;
                while (table2.getKeyAndValue(n2, key, value)) {
                    if (key.contentEquals("allowed")) {
                        this.tempMap.put(string, value.getStringArrayOrStringAsArray());
                    }
                    ++n2;
                }
                ++n;
            }
        }
    }

    public static enum DisplayWidth {
        WIDE(""),
        ABBREVIATED("-short"),
        NARROW("-narrow");
        
        @Deprecated
        private static int COUNT;
        private final String cldrKey;

        static {
            COUNT = DisplayWidth.values().length;
        }

        private DisplayWidth(String string2) {
            this.cldrKey = string2;
        }

        private String cldrKey() {
            return this.cldrKey;
        }
    }

    private static class DistanceInfo {
        int extraFieldMask;
        int missingFieldMask;

        @UnsupportedAppUsage
        private DistanceInfo() {
        }

        void addExtra(int n) {
            this.extraFieldMask |= 1 << n;
        }

        void addMissing(int n) {
            this.missingFieldMask |= 1 << n;
        }

        void clear() {
            this.extraFieldMask = 0;
            this.missingFieldMask = 0;
        }

        void setTo(DistanceInfo distanceInfo) {
            this.missingFieldMask = distanceInfo.missingFieldMask;
            this.extraFieldMask = distanceInfo.extraFieldMask;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("missingFieldMask: ");
            stringBuilder.append(DateTimePatternGenerator.showMask(this.missingFieldMask));
            stringBuilder.append(", extraFieldMask: ");
            stringBuilder.append(DateTimePatternGenerator.showMask(this.extraFieldMask));
            return stringBuilder.toString();
        }
    }

    @Deprecated
    public static class FormatParser {
        private static final UnicodeSet QUOTING_CHARS;
        private static final UnicodeSet SYNTAX_CHARS;
        private List<Object> items = new ArrayList<Object>();
        private transient PatternTokenizer tokenizer = new PatternTokenizer().setSyntaxCharacters(SYNTAX_CHARS).setExtraQuotingCharacters(QUOTING_CHARS).setUsingQuote(true);

        static {
            SYNTAX_CHARS = new UnicodeSet("[a-zA-Z]").freeze();
            QUOTING_CHARS = new UnicodeSet("[[[:script=Latn:][:script=Cyrl:]]&[[:L:][:M:]]]").freeze();
        }

        private void addVariable(StringBuffer stringBuffer, boolean bl) {
            if (stringBuffer.length() != 0) {
                this.items.add(new VariableField(stringBuffer.toString(), bl));
                stringBuffer.setLength(0);
            }
        }

        @Deprecated
        public List<Object> getItems() {
            return this.items;
        }

        @Deprecated
        public boolean hasDateAndTimeFields() {
            int n;
            int n2 = 0;
            for (Object object : this.items) {
                n = n2;
                if (object instanceof VariableField) {
                    n = n2 | 1 << ((VariableField)object).getType();
                }
                n2 = n;
            }
            boolean bl = false;
            n = (n2 & 1023) != 0 ? 1 : 0;
            n2 = (64512 & n2) != 0 ? 1 : 0;
            boolean bl2 = bl;
            if (n != 0) {
                bl2 = bl;
                if (n2 != 0) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        @Deprecated
        public Object quoteLiteral(String string) {
            return this.tokenizer.quoteLiteral(string);
        }

        @Deprecated
        public final FormatParser set(String string) {
            return this.set(string, false);
        }

        @Deprecated
        public FormatParser set(String charSequence, boolean bl) {
            this.items.clear();
            if (((String)charSequence).length() == 0) {
                return this;
            }
            this.tokenizer.setPattern((String)charSequence);
            charSequence = new StringBuffer();
            StringBuffer stringBuffer = new StringBuffer();
            do {
                ((StringBuffer)charSequence).setLength(0);
                int n = this.tokenizer.next((StringBuffer)charSequence);
                if (n == 0) {
                    this.addVariable(stringBuffer, false);
                    return this;
                }
                if (n == 1) {
                    if (stringBuffer.length() != 0 && ((StringBuffer)charSequence).charAt(0) != stringBuffer.charAt(0)) {
                        this.addVariable(stringBuffer, false);
                    }
                    stringBuffer.append((StringBuffer)charSequence);
                    continue;
                }
                this.addVariable(stringBuffer, false);
                this.items.add(((StringBuffer)charSequence).toString());
            } while (true);
        }

        @Deprecated
        public String toString() {
            return this.toString(0, this.items.size());
        }

        @Deprecated
        public String toString(int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder();
            while (n < n2) {
                Object object = this.items.get(n);
                if (object instanceof String) {
                    object = (String)object;
                    stringBuilder.append(this.tokenizer.quoteLiteral((String)object));
                } else {
                    stringBuilder.append(this.items.get(n).toString());
                }
                ++n;
            }
            return stringBuilder.toString();
        }
    }

    public static final class PatternInfo {
        public static final int BASE_CONFLICT = 1;
        public static final int CONFLICT = 2;
        public static final int OK = 0;
        public String conflictingPattern;
        public int status;
    }

    private static class PatternWithMatcher {
        public DateTimeMatcher matcherWithSkeleton;
        public String pattern;

        public PatternWithMatcher(String string, DateTimeMatcher dateTimeMatcher) {
            this.pattern = string;
            this.matcherWithSkeleton = dateTimeMatcher;
        }
    }

    private static class PatternWithSkeletonFlag {
        public String pattern;
        public boolean skeletonWasSpecified;

        public PatternWithSkeletonFlag(String string, boolean bl) {
            this.pattern = string;
            this.skeletonWasSpecified = bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.pattern);
            stringBuilder.append(",");
            stringBuilder.append(this.skeletonWasSpecified);
            return stringBuilder.toString();
        }
    }

    private static class SkeletonFields {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final byte DEFAULT_CHAR = 0;
        private static final byte DEFAULT_LENGTH = 0;
        private byte[] chars = new byte[16];
        private byte[] lengths = new byte[16];

        private SkeletonFields() {
        }

        private StringBuilder appendFieldTo(int n, StringBuilder stringBuilder, boolean bl) {
            char c = (char)this.chars[n];
            byte by = this.lengths[n];
            char c2 = c;
            if (bl) {
                n = DateTimePatternGenerator.getCanonicalChar(n, c);
                c2 = n;
            }
            for (n = 0; n < by; ++n) {
                stringBuilder.append(c2);
            }
            return stringBuilder;
        }

        private StringBuilder appendTo(StringBuilder stringBuilder, boolean bl, boolean bl2) {
            for (int i = 0; i < 16; ++i) {
                if (bl2 && i == 10) continue;
                this.appendFieldTo(i, stringBuilder, bl);
            }
            return stringBuilder;
        }

        public StringBuilder appendFieldTo(int n, StringBuilder stringBuilder) {
            return this.appendFieldTo(n, stringBuilder, false);
        }

        public StringBuilder appendTo(StringBuilder stringBuilder) {
            return this.appendTo(stringBuilder, false, false);
        }

        public void clear() {
            Arrays.fill(this.chars, (byte)0);
            Arrays.fill(this.lengths, (byte)0);
        }

        void clearField(int n) {
            this.chars[n] = (byte)(false ? 1 : 0);
            this.lengths[n] = (byte)(false ? 1 : 0);
        }

        public int compareTo(SkeletonFields skeletonFields) {
            for (int i = 0; i < 16; ++i) {
                int n = this.chars[i] - skeletonFields.chars[i];
                if (n != 0) {
                    return n;
                }
                n = this.lengths[i] - skeletonFields.lengths[i];
                if (n == 0) continue;
                return n;
            }
            return 0;
        }

        void copyFieldFrom(SkeletonFields skeletonFields, int n) {
            this.chars[n] = skeletonFields.chars[n];
            this.lengths[n] = skeletonFields.lengths[n];
        }

        public boolean equals(Object object) {
            boolean bl = this == object || object != null && object instanceof SkeletonFields && this.compareTo((SkeletonFields)object) == 0;
            return bl;
        }

        char getFieldChar(int n) {
            return (char)this.chars[n];
        }

        int getFieldLength(int n) {
            return this.lengths[n];
        }

        public int hashCode() {
            return Arrays.hashCode(this.chars) ^ Arrays.hashCode(this.lengths);
        }

        public boolean isFieldEmpty(int n) {
            boolean bl = this.lengths[n] == 0;
            return bl;
        }

        void populate(int n, char c, int n2) {
            this.chars[n] = (byte)c;
            this.lengths[n] = (byte)n2;
        }

        void populate(int n, String string) {
            for (char c : string.toCharArray()) {
            }
            this.populate(n, string.charAt(0), string.length());
        }

        public String toCanonicalString() {
            return this.appendTo(new StringBuilder(), true, false).toString();
        }

        public String toCanonicalString(boolean bl) {
            return this.appendTo(new StringBuilder(), true, bl).toString();
        }

        public String toString() {
            return this.appendTo(new StringBuilder(), false, false).toString();
        }

        public String toString(boolean bl) {
            return this.appendTo(new StringBuilder(), false, bl).toString();
        }
    }

    @Deprecated
    public static class VariableField {
        private final int canonicalIndex;
        private final String string;

        @Deprecated
        public VariableField(String string) {
            this(string, false);
        }

        @Deprecated
        public VariableField(String string, boolean bl) {
            this.canonicalIndex = DateTimePatternGenerator.getCanonicalIndex(string, bl);
            if (this.canonicalIndex >= 0) {
                this.string = string;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal datetime field:\t");
            stringBuilder.append(string);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        @Deprecated
        public static String getCanonicalCode(int n) {
            try {
                String string = CANONICAL_ITEMS[n];
                return string;
            }
            catch (Exception exception) {
                return String.valueOf(n);
            }
        }

        private int getCanonicalIndex() {
            return this.canonicalIndex;
        }

        @Deprecated
        public int getType() {
            return types[this.canonicalIndex][1];
        }

        @Deprecated
        public boolean isNumeric() {
            boolean bl = types[this.canonicalIndex][2] > 0;
            return bl;
        }

        @Deprecated
        public String toString() {
            return this.string;
        }
    }

}

