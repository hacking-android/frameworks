/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUCache;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.SimpleCache;
import android.icu.impl.UResource;
import android.icu.text.DateIntervalFormat;
import android.icu.util.Calendar;
import android.icu.util.Freezable;
import android.icu.util.ICUCloneNotSupportedException;
import android.icu.util.ICUException;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Set;

public class DateIntervalInfo
implements Cloneable,
Freezable<DateIntervalInfo>,
Serializable {
    static final String[] CALENDAR_FIELD_TO_PATTERN_LETTER = new String[]{"G", "y", "M", "w", "W", "d", "D", "E", "F", "a", "h", "H", "m", "s", "S", "z", " ", "Y", "e", "u", "g", "A", " ", " "};
    private static String CALENDAR_KEY = "calendar";
    private static final ICUCache<String, DateIntervalInfo> DIICACHE;
    private static String EARLIEST_FIRST_PREFIX;
    private static String FALLBACK_STRING;
    private static String INTERVAL_FORMATS_KEY;
    private static String LATEST_FIRST_PREFIX;
    private static final int MINIMUM_SUPPORTED_CALENDAR_FIELD = 13;
    static final int currentSerialVersion = 1;
    private static final long serialVersionUID = 1L;
    private String fFallbackIntervalPattern;
    private boolean fFirstDateInPtnIsLaterDate = false;
    private Map<String, Map<String, PatternInfo>> fIntervalPatterns = null;
    private transient boolean fIntervalPatternsReadOnly = false;
    private volatile transient boolean frozen = false;

    static {
        INTERVAL_FORMATS_KEY = "intervalFormats";
        FALLBACK_STRING = "fallback";
        LATEST_FIRST_PREFIX = "latestFirst:";
        EARLIEST_FIRST_PREFIX = "earliestFirst:";
        DIICACHE = new SimpleCache<String, DateIntervalInfo>();
    }

    @Deprecated
    public DateIntervalInfo() {
        this.fIntervalPatterns = new HashMap<String, Map<String, PatternInfo>>();
        this.fFallbackIntervalPattern = "{0} \u2013 {1}";
    }

    public DateIntervalInfo(ULocale uLocale) {
        this.initializeData(uLocale);
    }

    public DateIntervalInfo(Locale locale) {
        this(ULocale.forLocale(locale));
    }

    private static Map<String, Map<String, PatternInfo>> cloneIntervalPatterns(Map<String, Map<String, PatternInfo>> object) {
        HashMap<String, Map<String, PatternInfo>> hashMap = new HashMap<String, Map<String, PatternInfo>>();
        for (Map.Entry entry : object.entrySet()) {
            String string = (String)entry.getKey();
            Map map = (Map)entry.getValue();
            HashMap<String, PatternInfo> object2 = new HashMap<String, PatternInfo>();
            for (Map.Entry entry2 : map.entrySet()) {
                object2.put((String)entry2.getKey(), (PatternInfo)entry2.getValue());
            }
            hashMap.put(string, object2);
        }
        return hashMap;
    }

    private Object cloneUnfrozenDII() {
        try {
            DateIntervalInfo dateIntervalInfo = (DateIntervalInfo)super.clone();
            dateIntervalInfo.fFallbackIntervalPattern = this.fFallbackIntervalPattern;
            dateIntervalInfo.fFirstDateInPtnIsLaterDate = this.fFirstDateInPtnIsLaterDate;
            if (this.fIntervalPatternsReadOnly) {
                dateIntervalInfo.fIntervalPatterns = this.fIntervalPatterns;
                dateIntervalInfo.fIntervalPatternsReadOnly = true;
            } else {
                dateIntervalInfo.fIntervalPatterns = DateIntervalInfo.cloneIntervalPatterns(this.fIntervalPatterns);
                dateIntervalInfo.fIntervalPatternsReadOnly = false;
            }
            dateIntervalInfo.frozen = false;
            return dateIntervalInfo;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException("clone is not supported", cloneNotSupportedException);
        }
    }

    @Deprecated
    public static PatternInfo genPatternInfo(String string, boolean bl) {
        int n = DateIntervalInfo.splitPatternInto2Part(string);
        String string2 = string.substring(0, n);
        String string3 = null;
        if (n < string.length()) {
            string3 = string.substring(n, string.length());
        }
        return new PatternInfo(string2, string3, bl);
    }

    private void initializeData(ULocale uLocale) {
        String string = uLocale.toString();
        DateIntervalInfo dateIntervalInfo = DIICACHE.get(string);
        if (dateIntervalInfo == null) {
            this.setup(uLocale);
            this.fIntervalPatternsReadOnly = true;
            DIICACHE.put(string, ((DateIntervalInfo)this.clone()).freeze());
        } else {
            this.initializeFromReadOnlyPatterns(dateIntervalInfo);
        }
    }

    private void initializeFromReadOnlyPatterns(DateIntervalInfo dateIntervalInfo) {
        this.fFallbackIntervalPattern = dateIntervalInfo.fFallbackIntervalPattern;
        this.fFirstDateInPtnIsLaterDate = dateIntervalInfo.fFirstDateInPtnIsLaterDate;
        this.fIntervalPatterns = dateIntervalInfo.fIntervalPatterns;
        this.fIntervalPatternsReadOnly = true;
    }

    static void parseSkeleton(String string, int[] arrn) {
        for (int i = 0; i < string.length(); ++i) {
            int n = string.charAt(i) - 65;
            arrn[n] = arrn[n] + 1;
        }
    }

    private void setIntervalPattern(String string, String string2, PatternInfo patternInfo) {
        this.fIntervalPatterns.get(string).put(string2, patternInfo);
    }

    private PatternInfo setIntervalPatternInternally(String string, String string2, String object) {
        Object object2 = this.fIntervalPatterns.get(string);
        boolean bl = false;
        Map<String, PatternInfo> map = object2;
        if (object2 == null) {
            map = new HashMap<String, PatternInfo>();
            bl = true;
        }
        boolean bl2 = this.fFirstDateInPtnIsLaterDate;
        if (((String)object).startsWith(LATEST_FIRST_PREFIX)) {
            bl2 = true;
            object2 = ((String)object).substring(LATEST_FIRST_PREFIX.length(), ((String)object).length());
        } else {
            object2 = object;
            if (((String)object).startsWith(EARLIEST_FIRST_PREFIX)) {
                bl2 = false;
                object2 = ((String)object).substring(EARLIEST_FIRST_PREFIX.length(), ((String)object).length());
            }
        }
        object = DateIntervalInfo.genPatternInfo((String)object2, bl2);
        map.put(string2, (PatternInfo)object);
        if (bl) {
            this.fIntervalPatterns.put(string, map);
        }
        return object;
    }

    private void setup(ULocale object) {
        Object object2;
        String string;
        block8 : {
            this.fIntervalPatterns = new HashMap<String, Map<String, PatternInfo>>(19);
            this.fFallbackIntervalPattern = "{0} \u2013 {1}";
            string = ((ULocale)object).getKeywordValue("calendar");
            object2 = string;
            if (string != null) break block8;
            object2 = Calendar.getKeywordValuesForLocale("calendar", (ULocale)object, true)[0];
        }
        string = object2;
        if (object2 == null) {
            string = "gregorian";
        }
        object2 = new DateIntervalSink(this);
        object = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (ULocale)object);
        Serializable serializable = new StringBuilder();
        ((StringBuilder)serializable).append(CALENDAR_KEY);
        ((StringBuilder)serializable).append("/");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append("/");
        ((StringBuilder)serializable).append(INTERVAL_FORMATS_KEY);
        ((StringBuilder)serializable).append("/");
        ((StringBuilder)serializable).append(FALLBACK_STRING);
        this.setFallbackIntervalPattern(((ICUResourceBundle)object).getStringWithFallback(((StringBuilder)serializable).toString()));
        serializable = new HashSet();
        while (string != null) {
            try {
                if (!serializable.contains(string)) {
                    serializable.add(string);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(CALENDAR_KEY);
                    stringBuilder.append("/");
                    stringBuilder.append(string);
                    ((ICUResourceBundle)object).getAllItemsWithFallback(stringBuilder.toString(), (UResource.Sink)object2);
                    string = ((DateIntervalSink)object2).getAndResetNextCalendarType();
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Loop in calendar type fallback: ");
                ((StringBuilder)object).append(string);
                object2 = new ICUException(((StringBuilder)object).toString());
                throw object2;
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
                break;
            }
        }
    }

    private static int splitPatternInto2Part(String string) {
        int n;
        int n2 = 0;
        char c = '\u0000';
        int n3 = 0;
        int[] arrn = new int[58];
        int n4 = 0;
        int n5 = 0;
        do {
            char c2;
            int n6;
            int n7;
            block14 : {
                char c3;
                block15 : {
                    block13 : {
                        n = n4;
                        if (n5 >= string.length()) break;
                        c3 = string.charAt(n5);
                        n = n3;
                        if (c3 != c) {
                            n = n3;
                            if (n3 > 0) {
                                if (arrn[c - 65] == 0) {
                                    arrn[c - 65] = 1;
                                    n = 0;
                                } else {
                                    n = 1;
                                    break;
                                }
                            }
                        }
                        if (c3 != '\'') break block13;
                        if (n5 + 1 < string.length() && string.charAt(n5 + 1) == '\'') {
                            n7 = n5 + 1;
                            n6 = n2;
                            c2 = c;
                            n3 = n;
                        } else {
                            n3 = n2 == 0 ? 1 : 0;
                            n6 = n3;
                            c2 = c;
                            n3 = n;
                            n7 = n5;
                        }
                        break block14;
                    }
                    n6 = n2;
                    c2 = c;
                    n3 = n;
                    n7 = n5;
                    if (n2 != 0) break block14;
                    if (c3 >= 'a' && c3 <= 'z') break block15;
                    n6 = n2;
                    c2 = c;
                    n3 = n;
                    n7 = n5;
                    if (c3 < 'A') break block14;
                    n6 = n2;
                    c2 = c;
                    n3 = n;
                    n7 = n5;
                    if (c3 > 'Z') break block14;
                }
                c2 = c3;
                n3 = n + 1;
                n7 = n5;
                n6 = n2;
            }
            n5 = n7 + 1;
            n2 = n6;
            c = c2;
        } while (true);
        n2 = n3;
        if (n3 > 0) {
            n2 = n3;
            if (n == 0) {
                n2 = n3;
                if (arrn[c - 65] == 0) {
                    n2 = 0;
                }
            }
        }
        return n5 - n2;
    }

    private static boolean stringNumeric(int n, int n2, char c) {
        return c == 'M' && (n <= 2 && n2 > 2 || n > 2 && n2 <= 2);
    }

    public Object clone() {
        if (this.frozen) {
            return this;
        }
        return this.cloneUnfrozenDII();
    }

    @Override
    public DateIntervalInfo cloneAsThawed() {
        return (DateIntervalInfo)this.cloneUnfrozenDII();
    }

    public boolean equals(Object object) {
        if (object instanceof DateIntervalInfo) {
            object = (DateIntervalInfo)object;
            return this.fIntervalPatterns.equals(((DateIntervalInfo)object).fIntervalPatterns);
        }
        return false;
    }

    @Override
    public DateIntervalInfo freeze() {
        this.fIntervalPatternsReadOnly = true;
        this.frozen = true;
        return this;
    }

    DateIntervalFormat.BestMatchInfo getBestSkeleton(String string) {
        int n;
        String string22 = string;
        int[] arrn = new int[58];
        int[] arrn2 = new int[58];
        boolean bl = false;
        String string3 = string22;
        if (string22.indexOf(122) != -1) {
            string3 = string22.replace('z', 'v');
            bl = true;
        }
        DateIntervalInfo.parseSkeleton(string3, arrn);
        int n2 = Integer.MAX_VALUE;
        int n3 = 0;
        for (String string22 : this.fIntervalPatterns.keySet()) {
            for (n = 0; n < arrn2.length; ++n) {
                arrn2[n] = 0;
            }
            DateIntervalInfo.parseSkeleton(string22, arrn2);
            n = 0;
            int n4 = 1;
            for (int i = 0; i < arrn.length; ++i) {
                int n5 = arrn[i];
                int n6 = arrn2[i];
                if (n5 == n6) continue;
                if (n5 == 0) {
                    n4 = -1;
                    n += 4096;
                    continue;
                }
                if (n6 == 0) {
                    n4 = -1;
                    n += 4096;
                    continue;
                }
                if (DateIntervalInfo.stringNumeric(n5, n6, (char)(i + 65))) {
                    n += 256;
                    continue;
                }
                n += Math.abs(n5 - n6);
            }
            if (n < n2) {
                n2 = n;
                string = string22;
                n3 = n4;
            }
            if (n != 0) continue;
            n3 = 0;
            break;
        }
        n = n3;
        if (bl) {
            n = n3;
            if (n3 != -1) {
                n = 2;
            }
        }
        return new DateIntervalFormat.BestMatchInfo(string, n);
    }

    public boolean getDefaultOrder() {
        return this.fFirstDateInPtnIsLaterDate;
    }

    public String getFallbackIntervalPattern() {
        return this.fFallbackIntervalPattern;
    }

    public PatternInfo getIntervalPattern(String object, int n) {
        if (n <= 13) {
            if ((object = this.fIntervalPatterns.get(object)) != null && (object = (PatternInfo)object.get(CALENDAR_FIELD_TO_PATTERN_LETTER[n])) != null) {
                return object;
            }
            return null;
        }
        throw new IllegalArgumentException("no support for field less than SECOND");
    }

    @Deprecated
    public Map<String, Set<String>> getPatterns() {
        LinkedHashMap<String, Set<String>> linkedHashMap = new LinkedHashMap<String, Set<String>>();
        for (Map.Entry<String, Map<String, PatternInfo>> entry : this.fIntervalPatterns.entrySet()) {
            linkedHashMap.put(entry.getKey(), new LinkedHashSet<String>(entry.getValue().keySet()));
        }
        return linkedHashMap;
    }

    @Deprecated
    public Map<String, Map<String, PatternInfo>> getRawPatterns() {
        LinkedHashMap<String, Map<String, PatternInfo>> linkedHashMap = new LinkedHashMap<String, Map<String, PatternInfo>>();
        for (Map.Entry<String, Map<String, PatternInfo>> entry : this.fIntervalPatterns.entrySet()) {
            linkedHashMap.put(entry.getKey(), new LinkedHashMap<String, PatternInfo>(entry.getValue()));
        }
        return linkedHashMap;
    }

    public int hashCode() {
        return this.fIntervalPatterns.hashCode();
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

    public void setFallbackIntervalPattern(String string) {
        if (!this.frozen) {
            int n = string.indexOf("{0}");
            int n2 = string.indexOf("{1}");
            if (n != -1 && n2 != -1) {
                if (n > n2) {
                    this.fFirstDateInPtnIsLaterDate = true;
                }
                this.fFallbackIntervalPattern = string;
                return;
            }
            throw new IllegalArgumentException("no pattern {0} or pattern {1} in fallbackPattern");
        }
        throw new UnsupportedOperationException("no modification is allowed after DII is frozen");
    }

    public void setIntervalPattern(String string, int n, String object) {
        if (!this.frozen) {
            if (n <= 13) {
                if (this.fIntervalPatternsReadOnly) {
                    this.fIntervalPatterns = DateIntervalInfo.cloneIntervalPatterns(this.fIntervalPatterns);
                    this.fIntervalPatternsReadOnly = false;
                }
                object = this.setIntervalPatternInternally(string, CALENDAR_FIELD_TO_PATTERN_LETTER[n], (String)object);
                if (n == 11) {
                    this.setIntervalPattern(string, CALENDAR_FIELD_TO_PATTERN_LETTER[9], (PatternInfo)object);
                    this.setIntervalPattern(string, CALENDAR_FIELD_TO_PATTERN_LETTER[10], (PatternInfo)object);
                } else if (n == 5 || n == 7) {
                    this.setIntervalPattern(string, CALENDAR_FIELD_TO_PATTERN_LETTER[5], (PatternInfo)object);
                }
                return;
            }
            throw new IllegalArgumentException("calendar field is larger than MINIMUM_SUPPORTED_CALENDAR_FIELD");
        }
        throw new UnsupportedOperationException("no modification is allowed after DII is frozen");
    }

    private static final class DateIntervalSink
    extends UResource.Sink {
        private static final String ACCEPTED_PATTERN_LETTERS = "yMdahHms";
        private static final String DATE_INTERVAL_PATH_PREFIX;
        private static final String DATE_INTERVAL_PATH_SUFFIX;
        DateIntervalInfo dateIntervalInfo;
        String nextCalendarType;

        static {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("/LOCALE/");
            stringBuilder.append(CALENDAR_KEY);
            stringBuilder.append("/");
            DATE_INTERVAL_PATH_PREFIX = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append("/");
            stringBuilder.append(INTERVAL_FORMATS_KEY);
            DATE_INTERVAL_PATH_SUFFIX = stringBuilder.toString();
        }

        public DateIntervalSink(DateIntervalInfo dateIntervalInfo) {
            this.dateIntervalInfo = dateIntervalInfo;
        }

        private String getCalendarTypeFromPath(String string) {
            if (string.startsWith(DATE_INTERVAL_PATH_PREFIX) && string.endsWith(DATE_INTERVAL_PATH_SUFFIX)) {
                return string.substring(DATE_INTERVAL_PATH_PREFIX.length(), string.length() - DATE_INTERVAL_PATH_SUFFIX.length());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Malformed 'intervalFormat' alias path: ");
            stringBuilder.append(string);
            throw new ICUException(stringBuilder.toString());
        }

        private void setIntervalPatternIfAbsent(String string, String string2, UResource.Value value) {
            Map map = (Map)this.dateIntervalInfo.fIntervalPatterns.get(string);
            if (map == null || !map.containsKey(string2)) {
                this.dateIntervalInfo.setIntervalPatternInternally(string, string2, value.toString());
            }
        }

        private CharSequence validateAndProcessPatternLetter(CharSequence charSequence) {
            if (charSequence.length() != 1) {
                return null;
            }
            char c = charSequence.charAt(0);
            if (ACCEPTED_PATTERN_LETTERS.indexOf(c) < 0) {
                return null;
            }
            if (c == CALENDAR_FIELD_TO_PATTERN_LETTER[11].charAt(0)) {
                charSequence = CALENDAR_FIELD_TO_PATTERN_LETTER[10];
            }
            return charSequence;
        }

        public String getAndResetNextCalendarType() {
            String string = this.nextCalendarType;
            this.nextCalendarType = null;
            return string;
        }

        public void processSkeletonTable(UResource.Key key, UResource.Value value) {
            String string = key.toString();
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                CharSequence charSequence;
                if (value.getType() == 0 && (charSequence = this.validateAndProcessPatternLetter(key)) != null) {
                    this.setIntervalPatternIfAbsent(string, charSequence.toString(), value);
                }
                ++n;
            }
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                if (key.contentEquals(INTERVAL_FORMATS_KEY)) {
                    if (value.getType() == 3) {
                        this.nextCalendarType = this.getCalendarTypeFromPath(value.getAliasString());
                        break;
                    }
                    if (value.getType() == 2) {
                        table = value.getTable();
                        n = 0;
                        while (table.getKeyAndValue(n, key, value)) {
                            if (value.getType() == 2) {
                                this.processSkeletonTable(key, value);
                            }
                            ++n;
                        }
                        break;
                    }
                }
                ++n;
            }
        }
    }

    public static final class PatternInfo
    implements Cloneable,
    Serializable {
        static final int currentSerialVersion = 1;
        private static final long serialVersionUID = 1L;
        private final boolean fFirstDateInPtnIsLaterDate;
        private final String fIntervalPatternFirstPart;
        private final String fIntervalPatternSecondPart;

        public PatternInfo(String string, String string2, boolean bl) {
            this.fIntervalPatternFirstPart = string;
            this.fIntervalPatternSecondPart = string2;
            this.fFirstDateInPtnIsLaterDate = bl;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof PatternInfo;
            boolean bl2 = false;
            if (bl) {
                object = (PatternInfo)object;
                if (Objects.equals(this.fIntervalPatternFirstPart, ((PatternInfo)object).fIntervalPatternFirstPart) && Objects.equals(this.fIntervalPatternSecondPart, ((PatternInfo)object).fIntervalPatternSecondPart) && this.fFirstDateInPtnIsLaterDate == ((PatternInfo)object).fFirstDateInPtnIsLaterDate) {
                    bl2 = true;
                }
                return bl2;
            }
            return false;
        }

        public boolean firstDateInPtnIsLaterDate() {
            return this.fFirstDateInPtnIsLaterDate;
        }

        public String getFirstPart() {
            return this.fIntervalPatternFirstPart;
        }

        public String getSecondPart() {
            return this.fIntervalPatternSecondPart;
        }

        public int hashCode() {
            String string = this.fIntervalPatternFirstPart;
            int n = string != null ? string.hashCode() : 0;
            string = this.fIntervalPatternSecondPart;
            int n2 = n;
            if (string != null) {
                n2 = n ^ string.hashCode();
            }
            n = n2;
            if (this.fFirstDateInPtnIsLaterDate) {
                n = n2;
            }
            return n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{first=\u00ab");
            stringBuilder.append(this.fIntervalPatternFirstPart);
            stringBuilder.append("\u00bb, second=\u00ab");
            stringBuilder.append(this.fIntervalPatternSecondPart);
            stringBuilder.append("\u00bb, reversed:");
            stringBuilder.append(this.fFirstDateInPtnIsLaterDate);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

}

