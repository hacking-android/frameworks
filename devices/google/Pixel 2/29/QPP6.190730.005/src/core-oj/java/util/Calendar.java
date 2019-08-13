/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.LocaleData
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.AccessControlContext;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.ProtectionDomain;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.JapaneseImperialCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import libcore.icu.LocaleData;
import sun.util.locale.provider.CalendarDataUtility;

public abstract class Calendar
implements Serializable,
Cloneable,
Comparable<Calendar> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int ALL_FIELDS = 131071;
    public static final int ALL_STYLES = 0;
    public static final int AM = 0;
    public static final int AM_PM = 9;
    static final int AM_PM_MASK = 512;
    public static final int APRIL = 3;
    public static final int AUGUST = 7;
    private static final int COMPUTED = 1;
    public static final int DATE = 5;
    static final int DATE_MASK = 32;
    public static final int DAY_OF_MONTH = 5;
    static final int DAY_OF_MONTH_MASK = 32;
    public static final int DAY_OF_WEEK = 7;
    public static final int DAY_OF_WEEK_IN_MONTH = 8;
    static final int DAY_OF_WEEK_IN_MONTH_MASK = 256;
    static final int DAY_OF_WEEK_MASK = 128;
    public static final int DAY_OF_YEAR = 6;
    static final int DAY_OF_YEAR_MASK = 64;
    public static final int DECEMBER = 11;
    public static final int DST_OFFSET = 16;
    static final int DST_OFFSET_MASK = 65536;
    public static final int ERA = 0;
    static final int ERA_MASK = 1;
    public static final int FEBRUARY = 1;
    public static final int FIELD_COUNT = 17;
    private static final String[] FIELD_NAME;
    public static final int FRIDAY = 6;
    public static final int HOUR = 10;
    static final int HOUR_MASK = 1024;
    public static final int HOUR_OF_DAY = 11;
    static final int HOUR_OF_DAY_MASK = 2048;
    public static final int JANUARY = 0;
    public static final int JULY = 6;
    public static final int JUNE = 5;
    public static final int LONG = 2;
    public static final int LONG_FORMAT = 2;
    public static final int LONG_STANDALONE = 32770;
    public static final int MARCH = 2;
    public static final int MAY = 4;
    public static final int MILLISECOND = 14;
    static final int MILLISECOND_MASK = 16384;
    private static final int MINIMUM_USER_STAMP = 2;
    public static final int MINUTE = 12;
    static final int MINUTE_MASK = 4096;
    public static final int MONDAY = 2;
    public static final int MONTH = 2;
    static final int MONTH_MASK = 4;
    public static final int NARROW_FORMAT = 4;
    public static final int NARROW_STANDALONE = 32772;
    public static final int NOVEMBER = 10;
    public static final int OCTOBER = 9;
    public static final int PM = 1;
    public static final int SATURDAY = 7;
    public static final int SECOND = 13;
    static final int SECOND_MASK = 8192;
    public static final int SEPTEMBER = 8;
    public static final int SHORT = 1;
    public static final int SHORT_FORMAT = 1;
    public static final int SHORT_STANDALONE = 32769;
    static final int STANDALONE_MASK = 32768;
    public static final int SUNDAY = 1;
    public static final int THURSDAY = 5;
    public static final int TUESDAY = 3;
    public static final int UNDECIMBER = 12;
    private static final int UNSET = 0;
    public static final int WEDNESDAY = 4;
    public static final int WEEK_OF_MONTH = 4;
    static final int WEEK_OF_MONTH_MASK = 16;
    public static final int WEEK_OF_YEAR = 3;
    static final int WEEK_OF_YEAR_MASK = 8;
    public static final int YEAR = 1;
    static final int YEAR_MASK = 2;
    public static final int ZONE_OFFSET = 15;
    static final int ZONE_OFFSET_MASK = 32768;
    private static final ConcurrentMap<Locale, int[]> cachedLocaleData;
    static final int currentSerialVersion = 1;
    static final long serialVersionUID = -1807547505821590642L;
    transient boolean areAllFieldsSet;
    protected boolean areFieldsSet;
    protected int[] fields;
    private int firstDayOfWeek;
    protected boolean[] isSet;
    protected boolean isTimeSet;
    private boolean lenient = true;
    private int minimalDaysInFirstWeek;
    private int nextStamp = 2;
    private int serialVersionOnStream = 1;
    private transient boolean sharedZone = false;
    private transient int[] stamp;
    protected long time;
    private TimeZone zone;

    static {
        cachedLocaleData = new ConcurrentHashMap<Locale, int[]>(3);
        FIELD_NAME = new String[]{"ERA", "YEAR", "MONTH", "WEEK_OF_YEAR", "WEEK_OF_MONTH", "DAY_OF_MONTH", "DAY_OF_YEAR", "DAY_OF_WEEK", "DAY_OF_WEEK_IN_MONTH", "AM_PM", "HOUR", "HOUR_OF_DAY", "MINUTE", "SECOND", "MILLISECOND", "ZONE_OFFSET", "DST_OFFSET"};
    }

    protected Calendar() {
        this(TimeZone.getDefaultRef(), Locale.getDefault(Locale.Category.FORMAT));
        this.sharedZone = true;
    }

    protected Calendar(TimeZone timeZone, Locale locale) {
        Locale locale2 = locale;
        if (locale == null) {
            locale2 = Locale.getDefault();
        }
        this.fields = new int[17];
        this.isSet = new boolean[17];
        this.stamp = new int[17];
        this.zone = timeZone;
        this.setWeekCountData(locale2);
    }

    private void adjustStamp() {
        int n;
        int n2 = 2;
        int n3 = 2;
        do {
            int n4;
            int[] arrn;
            n = Integer.MAX_VALUE;
            for (n4 = 0; n4 < (arrn = this.stamp).length; ++n4) {
                int n5 = arrn[n4];
                int n6 = n;
                if (n5 >= n3) {
                    n6 = n;
                    if (n > n5) {
                        n6 = n5;
                    }
                }
                n = n2;
                if (n2 < n5) {
                    n = n5;
                }
                n2 = n;
                n = n6;
            }
            if (n2 != n && n == Integer.MAX_VALUE) break;
            for (n4 = 0; n4 < (arrn = this.stamp).length; ++n4) {
                if (arrn[n4] != n) continue;
                arrn[n4] = n3;
            }
            ++n3;
        } while (n != n2);
        this.nextStamp = n3;
    }

    private static int aggregateStamp(int n, int n2) {
        if (n != 0 && n2 != 0) {
            if (n <= n2) {
                n = n2;
            }
            return n;
        }
        return 0;
    }

    private static void appendValue(StringBuilder stringBuilder, String string, boolean bl, long l) {
        stringBuilder.append(string);
        stringBuilder.append('=');
        if (bl) {
            stringBuilder.append(l);
        } else {
            stringBuilder.append('?');
        }
    }

    @Override
    private int compareTo(long l) {
        long l2 = Calendar.getMillisOf(this);
        int n = l2 > l ? 1 : (l2 == l ? 0 : -1);
        return n;
    }

    private static Calendar createCalendar(TimeZone timeZone, Locale locale) {
        return new GregorianCalendar(timeZone, locale);
    }

    public static Set<String> getAvailableCalendarTypes() {
        return AvailableCalendarTypes.SET;
    }

    public static Locale[] getAvailableLocales() {
        synchronized (Calendar.class) {
            Locale[] arrlocale = DateFormat.getAvailableLocales();
            return arrlocale;
        }
    }

    private Map<String, Integer> getDisplayNamesImpl(int n, int n2, Locale cloneable) {
        String[] arrstring = this.getFieldStrings(n, n2, DateFormatSymbols.getInstance((Locale)cloneable));
        if (arrstring != null) {
            cloneable = new HashMap();
            for (n = 0; n < arrstring.length; ++n) {
                if (arrstring[n].length() == 0) continue;
                cloneable.put(arrstring[n], n);
            }
            return cloneable;
        }
        return null;
    }

    static String getFieldName(int n) {
        return FIELD_NAME[n];
    }

    private String[] getFieldStrings(int n, int n2, DateFormatSymbols object) {
        if ((n2 = this.getBaseStyle(n2)) == 4) {
            return null;
        }
        Object var4_4 = null;
        object = n != 0 ? (n != 2 ? (n != 7 ? (n != 9 ? var4_4 : object.getAmPmStrings()) : (n2 == 2 ? object.getWeekdays() : object.getShortWeekdays())) : (n2 == 2 ? object.getMonths() : object.getShortMonths())) : object.getEras();
        return object;
    }

    public static Calendar getInstance() {
        return Calendar.createCalendar(TimeZone.getDefault(), Locale.getDefault(Locale.Category.FORMAT));
    }

    public static Calendar getInstance(Locale locale) {
        return Calendar.createCalendar(TimeZone.getDefault(), locale);
    }

    public static Calendar getInstance(TimeZone timeZone) {
        return Calendar.createCalendar(timeZone, Locale.getDefault(Locale.Category.FORMAT));
    }

    public static Calendar getInstance(TimeZone timeZone, Locale locale) {
        return Calendar.createCalendar(timeZone, locale);
    }

    public static Calendar getJapaneseImperialInstance(TimeZone timeZone, Locale locale) {
        return new JapaneseImperialCalendar(timeZone, locale);
    }

    private static long getMillisOf(Calendar calendar) {
        if (calendar.isTimeSet) {
            return calendar.time;
        }
        calendar = (Calendar)calendar.clone();
        calendar.setLenient(true);
        return calendar.getTimeInMillis();
    }

    private void invalidateWeekFields() {
        int[] arrn;
        int n;
        Object object = this.stamp;
        if (object[4] != 1 && object[3] != 1) {
            return;
        }
        object = (Calendar)this.clone();
        ((Calendar)object).setLenient(true);
        ((Calendar)object).clear(4);
        ((Calendar)object).clear(3);
        if (this.stamp[4] == 1 && (arrn = this.fields)[4] != (n = ((Calendar)object).get(4))) {
            arrn[4] = n;
        }
        if (this.stamp[3] == 1) {
            n = ((Calendar)object).get(3);
            object = this.fields;
            if (object[3] != n) {
                object[3] = n;
            }
        }
    }

    static boolean isFieldSet(int n, int n2) {
        boolean bl = true;
        if ((1 << n2 & n) == 0) {
            bl = false;
        }
        return bl;
    }

    private boolean isNarrowFormatStyle(int n) {
        boolean bl = n == 4;
        return bl;
    }

    private boolean isNarrowStyle(int n) {
        boolean bl = n == 4 || n == 32772;
        return bl;
    }

    private boolean isStandaloneStyle(int n) {
        boolean bl = (32768 & n) != 0;
        return bl;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        this.stamp = new int[17];
        int n = this.serialVersionOnStream;
        if (n >= 2) {
            this.isTimeSet = true;
            if (this.fields == null) {
                this.fields = new int[17];
            }
            if (this.isSet == null) {
                this.isSet = new boolean[17];
            }
        } else if (n >= 0) {
            for (n = 0; n < 17; ++n) {
                this.stamp[n] = this.isSet[n];
            }
        }
        this.serialVersionOnStream = 1;
        object = this.zone;
        if (object instanceof SimpleTimeZone) {
            String string = ((TimeZone)object).getID();
            object = TimeZone.getTimeZone(string);
            if (object != null && ((TimeZone)object).hasSameRules(this.zone) && ((TimeZone)object).getID().equals(string)) {
                this.zone = object;
            }
        }
    }

    private void setWeekCountData(Locale locale) {
        LocaleData localeData;
        LocaleData localeData2 = localeData = (LocaleData)cachedLocaleData.get(locale);
        if (localeData == null) {
            localeData2 = new int[2];
            localeData = LocaleData.get((Locale)locale);
            localeData2[0] = (LocaleData)localeData.firstDayOfWeek;
            localeData2[1] = (LocaleData)localeData.minimalDaysInFirstWeek;
            cachedLocaleData.putIfAbsent(locale, (int[])localeData2);
        }
        this.firstDayOfWeek = localeData2[0];
        this.minimalDaysInFirstWeek = (int)localeData2[1];
    }

    public static int toStandaloneStyle(int n) {
        return 32768 | n;
    }

    private void updateTime() {
        this.computeTime();
        this.isTimeSet = true;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        synchronized (this) {
            block6 : {
                boolean bl = this.isTimeSet;
                if (bl) break block6;
                try {
                    this.updateTime();
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    // empty catch block
                }
            }
            objectOutputStream.defaultWriteObject();
            return;
        }
    }

    public abstract void add(int var1, int var2);

    public boolean after(Object object) {
        boolean bl = object instanceof Calendar && this.compareTo((Calendar)object) > 0;
        return bl;
    }

    public boolean before(Object object) {
        boolean bl = object instanceof Calendar && this.compareTo((Calendar)object) < 0;
        return bl;
    }

    boolean checkDisplayNameParams(int n, int n2, int n3, int n4, Locale locale, int n5) {
        n2 = this.getBaseStyle(n2);
        if (n >= 0 && n < this.fields.length && n2 >= n3 && n2 <= n4) {
            if (n2 != 3) {
                if (locale != null) {
                    return Calendar.isFieldSet(n5, n);
                }
                throw new NullPointerException();
            }
            throw new IllegalArgumentException();
        }
        throw new IllegalArgumentException();
    }

    public final void clear() {
        int[] arrn;
        for (int i = 0; i < (arrn = this.fields).length; ++i) {
            int[] arrn2 = this.stamp;
            arrn[i] = 0;
            arrn2[i] = 0;
            this.isSet[i] = false;
        }
        this.areFieldsSet = false;
        this.areAllFieldsSet = false;
        this.isTimeSet = false;
    }

    public final void clear(int n) {
        this.fields[n] = 0;
        this.stamp[n] = 0;
        this.isSet[n] = false;
        this.areFieldsSet = false;
        this.areAllFieldsSet = false;
        this.isTimeSet = false;
    }

    public Object clone() {
        Calendar calendar = (Calendar)super.clone();
        calendar.fields = new int[17];
        calendar.isSet = new boolean[17];
        calendar.stamp = new int[17];
        for (int i = 0; i < 17; ++i) {
            calendar.fields[i] = this.fields[i];
            calendar.stamp[i] = this.stamp[i];
            calendar.isSet[i] = this.isSet[i];
            continue;
        }
        try {
            calendar.zone = (TimeZone)this.zone.clone();
            return calendar;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    @Override
    public int compareTo(Calendar calendar) {
        return this.compareTo(Calendar.getMillisOf(calendar));
    }

    protected void complete() {
        if (!this.isTimeSet) {
            this.updateTime();
        }
        if (!this.areFieldsSet || !this.areAllFieldsSet) {
            this.computeFields();
            this.areFieldsSet = true;
            this.areAllFieldsSet = true;
        }
    }

    protected abstract void computeFields();

    protected abstract void computeTime();

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        try {
            boolean bl2;
            object = (Calendar)object;
            if (this.compareTo(Calendar.getMillisOf((Calendar)object)) != 0 || this.lenient != ((Calendar)object).lenient || this.firstDayOfWeek != ((Calendar)object).firstDayOfWeek || this.minimalDaysInFirstWeek != ((Calendar)object).minimalDaysInFirstWeek || !(bl2 = this.zone.equals(((Calendar)object).zone))) {
                bl = false;
            }
            return bl;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public int get(int n) {
        this.complete();
        return this.internalGet(n);
    }

    public int getActualMaximum(int n) {
        int n2;
        block3 : {
            int n3;
            int n4;
            int n5;
            int n6 = this.getLeastMaximum(n);
            if (n6 == (n3 = this.getMaximum(n))) {
                return n6;
            }
            Calendar calendar = (Calendar)this.clone();
            calendar.setLenient(true);
            if (n == 3 || n == 4) {
                calendar.set(7, this.firstDayOfWeek);
            }
            n2 = n6;
            do {
                calendar.set(n, n6);
                if (calendar.get(n) != n6) break block3;
                n4 = n6;
                n6 = n5 = n6 + 1;
                n2 = n4;
            } while (n5 <= n3);
            n2 = n4;
        }
        return n2;
    }

    public int getActualMinimum(int n) {
        int n2;
        block2 : {
            int n3;
            int n4;
            int n5;
            int n6 = this.getGreatestMinimum(n);
            if (n6 == (n3 = this.getMinimum(n))) {
                return n6;
            }
            Calendar calendar = (Calendar)this.clone();
            calendar.setLenient(true);
            n2 = n6;
            do {
                calendar.set(n, n6);
                if (calendar.get(n) != n6) break block2;
                n4 = n6;
                n6 = n5 = n6 - 1;
                n2 = n4;
            } while (n5 >= n3);
            n2 = n4;
        }
        return n2;
    }

    int getBaseStyle(int n) {
        return -32769 & n;
    }

    public String getCalendarType() {
        return this.getClass().getName();
    }

    public String getDisplayName(int n, int n2, Locale arrstring) {
        String string;
        int n3 = n2;
        if (n2 == 0) {
            n3 = 1;
        }
        if (!this.checkDisplayNameParams(n, n3, 1, 4, (Locale)arrstring, 645)) {
            return null;
        }
        String string2 = this.getCalendarType();
        n2 = this.get(n);
        if (!this.isStandaloneStyle(n3) && !this.isNarrowFormatStyle(n3)) {
            if ((arrstring = this.getFieldStrings(n, n3, DateFormatSymbols.getInstance((Locale)arrstring))) != null && n2 < arrstring.length) {
                return arrstring[n2];
            }
            return null;
        }
        String string3 = string = CalendarDataUtility.retrieveFieldValueName(string2, n, n2, n3, (Locale)arrstring);
        if (string == null) {
            if (this.isNarrowFormatStyle(n3)) {
                string3 = CalendarDataUtility.retrieveFieldValueName(string2, n, n2, Calendar.toStandaloneStyle(n3), (Locale)arrstring);
            } else {
                string3 = string;
                if (this.isStandaloneStyle(n3)) {
                    string3 = CalendarDataUtility.retrieveFieldValueName(string2, n, n2, this.getBaseStyle(n3), (Locale)arrstring);
                }
            }
        }
        return string3;
    }

    public Map<String, Integer> getDisplayNames(int n, int n2, Locale locale) {
        Map<String, Integer> map;
        if (!this.checkDisplayNameParams(n, n2, 0, 4, locale, 645)) {
            return null;
        }
        this.complete();
        String string = this.getCalendarType();
        if (n2 != 0 && !this.isStandaloneStyle(n2) && !this.isNarrowFormatStyle(n2)) {
            return this.getDisplayNamesImpl(n, n2, locale);
        }
        Map<String, Integer> map2 = map = CalendarDataUtility.retrieveFieldValueNames(string, n, n2, locale);
        if (map == null) {
            if (this.isNarrowFormatStyle(n2)) {
                map2 = CalendarDataUtility.retrieveFieldValueNames(string, n, Calendar.toStandaloneStyle(n2), locale);
            } else {
                map2 = map;
                if (n2 != 0) {
                    map2 = CalendarDataUtility.retrieveFieldValueNames(string, n, this.getBaseStyle(n2), locale);
                }
            }
        }
        return map2;
    }

    public int getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    public abstract int getGreatestMinimum(int var1);

    public abstract int getLeastMaximum(int var1);

    public abstract int getMaximum(int var1);

    public int getMinimalDaysInFirstWeek() {
        return this.minimalDaysInFirstWeek;
    }

    public abstract int getMinimum(int var1);

    final int getSetStateFields() {
        int n = 0;
        for (int i = 0; i < this.fields.length; ++i) {
            int n2 = n;
            if (this.stamp[i] != 0) {
                n2 = n | 1 << i;
            }
            n = n2;
        }
        return n;
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
        if (this.sharedZone) {
            this.zone = (TimeZone)this.zone.clone();
            this.sharedZone = false;
        }
        return this.zone;
    }

    public int getWeekYear() {
        throw new UnsupportedOperationException();
    }

    public int getWeeksInWeekYear() {
        throw new UnsupportedOperationException();
    }

    TimeZone getZone() {
        return this.zone;
    }

    public int hashCode() {
        int n = this.lenient;
        int n2 = this.firstDayOfWeek;
        int n3 = this.minimalDaysInFirstWeek;
        int n4 = this.zone.hashCode();
        long l = Calendar.getMillisOf(this);
        return (int)l ^ (int)(l >> 32) ^ (n | n2 << 1 | n3 << 4 | n4 << 7);
    }

    protected final int internalGet(int n) {
        return this.fields[n];
    }

    final void internalSet(int n, int n2) {
        this.fields[n] = n2;
    }

    final boolean isExternallySet(int n) {
        boolean bl = this.stamp[n] >= 2;
        return bl;
    }

    final boolean isFullyNormalized() {
        boolean bl = this.areFieldsSet && this.areAllFieldsSet;
        return bl;
    }

    public boolean isLenient() {
        return this.lenient;
    }

    final boolean isPartiallyNormalized() {
        boolean bl = this.areFieldsSet && !this.areAllFieldsSet;
        return bl;
    }

    public final boolean isSet(int n) {
        boolean bl = this.stamp[n] != 0;
        return bl;
    }

    public boolean isWeekDateSupported() {
        return false;
    }

    public void roll(int n, int n2) {
        do {
            if (n2 <= 0) break;
            this.roll(n, true);
        } while (true);
        for (int i = --n2; i < 0; ++i) {
            this.roll(n, false);
        }
    }

    public abstract void roll(int var1, boolean var2);

    final int selectFields() {
        int n;
        int n2 = 2;
        if (this.stamp[0] != 0) {
            n2 = 2 | 1;
        }
        int[] arrn = this.stamp;
        int n3 = arrn[7];
        int n4 = arrn[2];
        int n5 = arrn[5];
        int n6 = Calendar.aggregateStamp(arrn[4], n3);
        int n7 = Calendar.aggregateStamp(this.stamp[8], n3);
        arrn = this.stamp;
        int n8 = arrn[6];
        int n9 = Calendar.aggregateStamp(arrn[3], n3);
        int n10 = n = n5;
        if (n6 > n) {
            n10 = n6;
        }
        n = n10;
        if (n7 > n10) {
            n = n7;
        }
        n10 = n;
        if (n8 > n) {
            n10 = n8;
        }
        n = n10;
        if (n9 > n10) {
            n = n9;
        }
        n9 = n5;
        n10 = n;
        if (n == 0) {
            arrn = this.stamp;
            int n11 = arrn[4];
            n = Math.max(arrn[8], n3);
            n10 = this.stamp[3];
            int n12 = Math.max(Math.max(n11, n), n10);
            n6 = n11;
            n9 = n5;
            n7 = n;
            n10 = n12;
            if (n12 == 0) {
                n9 = n4;
                n10 = n4;
                n7 = n;
                n6 = n11;
            }
        }
        if (!(n10 == n9 || n10 == n6 && (arrn = this.stamp)[4] >= arrn[3] || n10 == n7 && (arrn = this.stamp)[8] >= arrn[3])) {
            if (n10 == n8) {
                n2 |= 64;
            } else {
                n10 = n2;
                if (n3 != 0) {
                    n10 = n2 | 128;
                }
                n2 = n10 | 8;
            }
        } else {
            n2 |= 4;
            if (n10 == n9) {
                n2 |= 32;
            } else {
                n = n2;
                if (n3 != 0) {
                    n = n2 | 128;
                }
                if (n6 == n7) {
                    arrn = this.stamp;
                    n2 = arrn[4] >= arrn[8] ? n | 16 : n | 256;
                } else if (n10 == n6) {
                    n2 = n | 16;
                } else {
                    n2 = n;
                    if (this.stamp[8] != 0) {
                        n2 = n | 256;
                    }
                }
            }
        }
        arrn = this.stamp;
        n4 = arrn[11];
        n10 = Calendar.aggregateStamp(arrn[10], arrn[9]);
        if (n10 <= n4) {
            n10 = n4;
        }
        n = n10;
        if (n10 == 0) {
            arrn = this.stamp;
            n = Math.max(arrn[10], arrn[9]);
        }
        n10 = n2;
        if (n != 0) {
            if (n == n4) {
                n10 = n2 | 2048;
            } else {
                n10 = n2 |= 1024;
                if (this.stamp[9] != 0) {
                    n10 = n2 | 512;
                }
            }
        }
        n = n10;
        if (this.stamp[12] != 0) {
            n = n10 | 4096;
        }
        n2 = n;
        if (this.stamp[13] != 0) {
            n2 = n | 8192;
        }
        n10 = n2;
        if (this.stamp[14] != 0) {
            n10 = n2 | 16384;
        }
        n2 = n10;
        if (this.stamp[15] >= 2) {
            n2 = n10 | 32768;
        }
        n10 = n2;
        if (this.stamp[16] >= 2) {
            n10 = n2 | 65536;
        }
        return n10;
    }

    public void set(int n, int n2) {
        if (this.areFieldsSet && !this.areAllFieldsSet) {
            this.computeFields();
        }
        this.internalSet(n, n2);
        this.isTimeSet = false;
        this.areFieldsSet = false;
        this.isSet[n] = true;
        int[] arrn = this.stamp;
        n2 = this.nextStamp;
        this.nextStamp = n2 + 1;
        arrn[n] = n2;
        if (this.nextStamp == Integer.MAX_VALUE) {
            this.adjustStamp();
        }
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

    final void setFieldsComputed(int n) {
        if (n == 131071) {
            for (n = 0; n < this.fields.length; ++n) {
                this.stamp[n] = 1;
                this.isSet[n] = true;
            }
            this.areAllFieldsSet = true;
            this.areFieldsSet = true;
        } else {
            for (int i = 0; i < this.fields.length; ++i) {
                if ((n & 1) == 1) {
                    this.stamp[i] = 1;
                    this.isSet[i] = true;
                } else if (this.areAllFieldsSet && !this.isSet[i]) {
                    this.areAllFieldsSet = false;
                }
                n >>>= 1;
            }
        }
    }

    final void setFieldsNormalized(int n) {
        if (n != 131071) {
            int[] arrn;
            int n2 = 0;
            int n3 = n;
            for (n = n2; n < (arrn = this.fields).length; ++n) {
                if ((n3 & 1) == 0) {
                    int[] arrn2 = this.stamp;
                    arrn[n] = 0;
                    arrn2[n] = 0;
                    this.isSet[n] = false;
                }
                n3 >>= 1;
            }
        }
        this.areFieldsSet = true;
        this.areAllFieldsSet = false;
    }

    public void setFirstDayOfWeek(int n) {
        if (this.firstDayOfWeek == n) {
            return;
        }
        this.firstDayOfWeek = n;
        this.invalidateWeekFields();
    }

    public void setLenient(boolean bl) {
        this.lenient = bl;
    }

    public void setMinimalDaysInFirstWeek(int n) {
        if (this.minimalDaysInFirstWeek == n) {
            return;
        }
        this.minimalDaysInFirstWeek = n;
        this.invalidateWeekFields();
    }

    public final void setTime(Date date) {
        this.setTimeInMillis(date.getTime());
    }

    public void setTimeInMillis(long l) {
        if (this.time == l && this.isTimeSet && this.areFieldsSet && this.areAllFieldsSet) {
            return;
        }
        this.time = l;
        this.isTimeSet = true;
        this.areFieldsSet = false;
        this.computeFields();
        this.areFieldsSet = true;
        this.areAllFieldsSet = true;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.zone = timeZone;
        this.sharedZone = false;
        this.areFieldsSet = false;
        this.areAllFieldsSet = false;
    }

    final void setUnnormalized() {
        this.areAllFieldsSet = false;
        this.areFieldsSet = false;
    }

    public void setWeekDate(int n, int n2, int n3) {
        throw new UnsupportedOperationException();
    }

    void setZoneShared(boolean bl) {
        this.sharedZone = bl;
    }

    public final Instant toInstant() {
        return Instant.ofEpochMilli(this.getTimeInMillis());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(800);
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append('[');
        Calendar.appendValue(stringBuilder, "time", this.isTimeSet, this.time);
        stringBuilder.append(",areFieldsSet=");
        stringBuilder.append(this.areFieldsSet);
        stringBuilder.append(",areAllFieldsSet=");
        stringBuilder.append(this.areAllFieldsSet);
        stringBuilder.append(",lenient=");
        stringBuilder.append(this.lenient);
        stringBuilder.append(",zone=");
        stringBuilder.append(this.zone);
        Calendar.appendValue(stringBuilder, ",firstDayOfWeek", true, this.firstDayOfWeek);
        Calendar.appendValue(stringBuilder, ",minimalDaysInFirstWeek", true, this.minimalDaysInFirstWeek);
        for (int i = 0; i < 17; ++i) {
            stringBuilder.append(',');
            Calendar.appendValue(stringBuilder, FIELD_NAME[i], this.isSet(i), this.fields[i]);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    private static class AvailableCalendarTypes {
        private static final Set<String> SET;

        static {
            HashSet<String> hashSet = new HashSet<String>(3);
            hashSet.add("gregory");
            SET = Collections.unmodifiableSet(hashSet);
        }

        private AvailableCalendarTypes() {
        }
    }

    public static class Builder {
        private static final int NFIELDS = 18;
        private static final int WEEK_YEAR = 17;
        private int[] fields;
        private int firstDayOfWeek;
        private long instant;
        private boolean lenient = true;
        private Locale locale;
        private int maxFieldIndex;
        private int minimalDaysInFirstWeek;
        private int nextStamp;
        private String type;
        private TimeZone zone;

        private void allocateFields() {
            if (this.fields == null) {
                this.fields = new int[36];
                this.nextStamp = 2;
                this.maxFieldIndex = -1;
            }
        }

        private void internalSet(int n, int n2) {
            int[] arrn = this.fields;
            int n3 = this.nextStamp;
            this.nextStamp = n3 + 1;
            arrn[n] = n3;
            if (this.nextStamp >= 0) {
                arrn[n + 18] = n2;
                if (n > this.maxFieldIndex && n < 17) {
                    this.maxFieldIndex = n;
                }
                return;
            }
            throw new IllegalStateException("stamp counter overflow");
        }

        private boolean isInstantSet() {
            int n = this.nextStamp;
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            return bl;
        }

        private boolean isSet(int n) {
            int[] arrn = this.fields;
            boolean bl = arrn != null && arrn[n] > 0;
            return bl;
        }

        private boolean isValidWeekParameter(int n) {
            boolean bl = n > 0 && n <= 7;
            return bl;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Calendar build() {
            if (this.locale == null) {
                this.locale = Locale.getDefault();
            }
            if (this.zone == null) {
                this.zone = TimeZone.getDefault();
            }
            if (this.type == null) {
                this.type = this.locale.getUnicodeLocaleType("ca");
            }
            if (this.type == null) {
                this.type = "gregory";
            }
            Object object = this.type;
            int n = -1;
            int n2 = ((String)object).hashCode();
            int n3 = 0;
            int n4 = 1;
            if (n2 != 283776265) {
                if (n2 == 2095190916 && ((String)object).equals("iso8601")) {
                    n = 1;
                }
            } else if (((String)object).equals("gregory")) {
                n = 0;
            }
            if (n != 0) {
                if (n != 1) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("unknown calendar type: ");
                    ((StringBuilder)object).append(this.type);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                object = new GregorianCalendar(this.zone, this.locale, true);
                ((GregorianCalendar)object).setGregorianChange(new Date(Long.MIN_VALUE));
                this.setWeekDefinition(2, 4);
            } else {
                object = new GregorianCalendar(this.zone, this.locale, true);
            }
            ((Calendar)object).setLenient(this.lenient);
            n = this.firstDayOfWeek;
            if (n != 0) {
                ((Calendar)object).setFirstDayOfWeek(n);
                ((Calendar)object).setMinimalDaysInFirstWeek(this.minimalDaysInFirstWeek);
            }
            if (this.isInstantSet()) {
                ((Calendar)object).setTimeInMillis(this.instant);
                ((Calendar)object).complete();
                return object;
            }
            if (this.fields == null) return object;
            {
                int[] arrn;
                n = n3;
                if (this.isSet(17)) {
                    arrn = this.fields;
                    n = n3;
                    if (arrn[17] > arrn[1]) {
                        n = 1;
                    }
                }
                if (n != 0 && !((Calendar)object).isWeekDateSupported()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("week date is unsupported by ");
                    ((StringBuilder)object).append(this.type);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                n3 = 2;
                do {
                    if (n3 < this.nextStamp) {
                    } else {
                        if (n != 0) {
                            n = n4;
                            if (this.isSet(3)) {
                                n = this.fields[21];
                            }
                            n3 = this.isSet(7) ? this.fields[25] : ((Calendar)object).getFirstDayOfWeek();
                            ((Calendar)object).setWeekDate(this.fields[35], n, n3);
                        }
                        ((Calendar)object).complete();
                        return object;
                    }
                    for (n2 = 0; n2 <= this.maxFieldIndex; ++n2) {
                        arrn = this.fields;
                        if (arrn[n2] != n3) continue;
                        ((Calendar)object).set(n2, arrn[n2 + 18]);
                        break;
                    }
                    ++n3;
                } while (true);
            }
        }

        public Builder set(int n, int n2) {
            if (n >= 0 && n < 17) {
                if (!this.isInstantSet()) {
                    this.allocateFields();
                    this.internalSet(n, n2);
                    return this;
                }
                throw new IllegalStateException("instant has been set");
            }
            throw new IllegalArgumentException("field is invalid");
        }

        public Builder setCalendarType(String charSequence) {
            block7 : {
                block6 : {
                    String string;
                    block5 : {
                        string = charSequence;
                        if (((String)charSequence).equals("gregorian")) {
                            string = "gregory";
                        }
                        if (!Calendar.getAvailableCalendarTypes().contains(string) && !string.equals("iso8601")) {
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("unknown calendar type: ");
                            ((StringBuilder)charSequence).append(string);
                            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                        }
                        charSequence = this.type;
                        if (charSequence != null) break block5;
                        this.type = string;
                        break block6;
                    }
                    if (!((String)charSequence).equals(string)) break block7;
                }
                return this;
            }
            throw new IllegalStateException("calendar type override");
        }

        public Builder setDate(int n, int n2, int n3) {
            return this.setFields(1, n, 2, n2, 5, n3);
        }

        public Builder setFields(int ... arrn) {
            int n = arrn.length;
            if (n % 2 == 0) {
                if (!this.isInstantSet()) {
                    if (this.nextStamp + n / 2 >= 0) {
                        this.allocateFields();
                        int n2 = 0;
                        while (n2 < n) {
                            int n3 = n2 + 1;
                            if ((n2 = arrn[n2]) >= 0 && n2 < 17) {
                                this.internalSet(n2, arrn[n3]);
                                n2 = n3 + 1;
                                continue;
                            }
                            throw new IllegalArgumentException("field is invalid");
                        }
                        return this;
                    }
                    throw new IllegalStateException("stamp counter overflow");
                }
                throw new IllegalStateException("instant has been set");
            }
            throw new IllegalArgumentException();
        }

        public Builder setInstant(long l) {
            if (this.fields == null) {
                this.instant = l;
                this.nextStamp = 1;
                return this;
            }
            throw new IllegalStateException();
        }

        public Builder setInstant(Date date) {
            return this.setInstant(date.getTime());
        }

        public Builder setLenient(boolean bl) {
            this.lenient = bl;
            return this;
        }

        public Builder setLocale(Locale locale) {
            if (locale != null) {
                this.locale = locale;
                return this;
            }
            throw new NullPointerException();
        }

        public Builder setTimeOfDay(int n, int n2, int n3) {
            return this.setTimeOfDay(n, n2, n3, 0);
        }

        public Builder setTimeOfDay(int n, int n2, int n3, int n4) {
            return this.setFields(11, n, 12, n2, 13, n3, 14, n4);
        }

        public Builder setTimeZone(TimeZone timeZone) {
            if (timeZone != null) {
                this.zone = timeZone;
                return this;
            }
            throw new NullPointerException();
        }

        public Builder setWeekDate(int n, int n2, int n3) {
            this.allocateFields();
            this.internalSet(17, n);
            this.internalSet(3, n2);
            this.internalSet(7, n3);
            return this;
        }

        public Builder setWeekDefinition(int n, int n2) {
            if (this.isValidWeekParameter(n) && this.isValidWeekParameter(n2)) {
                this.firstDayOfWeek = n;
                this.minimalDaysInFirstWeek = n2;
                return this;
            }
            throw new IllegalArgumentException();
        }
    }

    private static class CalendarAccessControlContext {
        private static final AccessControlContext INSTANCE;

        static {
            RuntimePermission runtimePermission = new RuntimePermission("accessClassInPackage.sun.util.calendar");
            PermissionCollection permissionCollection = runtimePermission.newPermissionCollection();
            permissionCollection.add(runtimePermission);
            INSTANCE = new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, permissionCollection)});
        }

        private CalendarAccessControlContext() {
        }
    }

}

