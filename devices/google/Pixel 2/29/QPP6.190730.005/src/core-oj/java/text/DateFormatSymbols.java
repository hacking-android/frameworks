/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.ICU
 *  libcore.icu.LocaleData
 *  libcore.icu.TimeZoneNames
 */
package java.text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import libcore.icu.ICU;
import libcore.icu.LocaleData;
import libcore.icu.TimeZoneNames;

public class DateFormatSymbols
implements Serializable,
Cloneable {
    static final int PATTERN_AM_PM = 14;
    static final int PATTERN_DAY_OF_MONTH = 3;
    static final int PATTERN_DAY_OF_WEEK = 9;
    static final int PATTERN_DAY_OF_WEEK_IN_MONTH = 11;
    static final int PATTERN_DAY_OF_YEAR = 10;
    static final int PATTERN_DAY_PERIOD = 24;
    static final int PATTERN_ERA = 0;
    static final int PATTERN_FLEXIBLE_DAY_PERIOD = 25;
    static final int PATTERN_HOUR0 = 16;
    static final int PATTERN_HOUR1 = 15;
    static final int PATTERN_HOUR_OF_DAY0 = 5;
    static final int PATTERN_HOUR_OF_DAY1 = 4;
    static final int PATTERN_ISO_DAY_OF_WEEK = 20;
    static final int PATTERN_ISO_ZONE = 21;
    static final int PATTERN_MILLISECOND = 8;
    static final int PATTERN_MINUTE = 6;
    static final int PATTERN_MONTH = 2;
    static final int PATTERN_MONTH_STANDALONE = 22;
    static final int PATTERN_SECOND = 7;
    static final int PATTERN_STANDALONE_DAY_OF_WEEK = 23;
    static final int PATTERN_WEEK_OF_MONTH = 13;
    static final int PATTERN_WEEK_OF_YEAR = 12;
    static final int PATTERN_WEEK_YEAR = 19;
    static final int PATTERN_YEAR = 1;
    static final int PATTERN_ZONE_NAME = 17;
    static final int PATTERN_ZONE_VALUE = 18;
    private static final ConcurrentMap<Locale, SoftReference<DateFormatSymbols>> cachedInstances = new ConcurrentHashMap<Locale, SoftReference<DateFormatSymbols>>(3);
    static final int currentSerialVersion = 1;
    static final int millisPerHour = 3600000;
    static final String patternChars = "GyMdkHmsSEDFwWahKzZYuXLcbB";
    static final long serialVersionUID = -5987973545549424702L;
    String[] ampms = null;
    volatile transient int cachedHashCode = 0;
    String[] eras = null;
    transient boolean isZoneStringsSet = false;
    private transient int lastZoneIndex = 0;
    String localPatternChars = null;
    Locale locale = null;
    String[] months = null;
    private int serialVersionOnStream = 1;
    String[] shortMonths = null;
    private String[] shortStandAloneMonths;
    private String[] shortStandAloneWeekdays;
    String[] shortWeekdays = null;
    private String[] standAloneMonths;
    private String[] standAloneWeekdays;
    private String[] tinyMonths;
    private String[] tinyStandAloneMonths;
    private String[] tinyStandAloneWeekdays;
    private String[] tinyWeekdays;
    String[] weekdays = null;
    String[][] zoneStrings = null;

    public DateFormatSymbols() {
        this.initializeData(Locale.getDefault(Locale.Category.FORMAT));
    }

    public DateFormatSymbols(Locale locale) {
        this.initializeData(locale);
    }

    private void copyMembers(DateFormatSymbols dateFormatSymbols, DateFormatSymbols dateFormatSymbols2) {
        dateFormatSymbols2.locale = dateFormatSymbols.locale;
        String[] arrstring = dateFormatSymbols.eras;
        dateFormatSymbols2.eras = Arrays.copyOf(arrstring, arrstring.length);
        arrstring = dateFormatSymbols.months;
        dateFormatSymbols2.months = Arrays.copyOf(arrstring, arrstring.length);
        arrstring = dateFormatSymbols.shortMonths;
        dateFormatSymbols2.shortMonths = Arrays.copyOf(arrstring, arrstring.length);
        arrstring = dateFormatSymbols.weekdays;
        dateFormatSymbols2.weekdays = Arrays.copyOf(arrstring, arrstring.length);
        arrstring = dateFormatSymbols.shortWeekdays;
        dateFormatSymbols2.shortWeekdays = Arrays.copyOf(arrstring, arrstring.length);
        arrstring = dateFormatSymbols.ampms;
        dateFormatSymbols2.ampms = Arrays.copyOf(arrstring, arrstring.length);
        dateFormatSymbols2.zoneStrings = dateFormatSymbols.zoneStrings != null ? dateFormatSymbols.getZoneStringsImpl(true) : null;
        dateFormatSymbols2.localPatternChars = dateFormatSymbols.localPatternChars;
        dateFormatSymbols2.cachedHashCode = 0;
        dateFormatSymbols2.tinyMonths = dateFormatSymbols.tinyMonths;
        dateFormatSymbols2.tinyWeekdays = dateFormatSymbols.tinyWeekdays;
        dateFormatSymbols2.standAloneMonths = dateFormatSymbols.standAloneMonths;
        dateFormatSymbols2.shortStandAloneMonths = dateFormatSymbols.shortStandAloneMonths;
        dateFormatSymbols2.tinyStandAloneMonths = dateFormatSymbols.tinyStandAloneMonths;
        dateFormatSymbols2.standAloneWeekdays = dateFormatSymbols.standAloneWeekdays;
        dateFormatSymbols2.shortStandAloneWeekdays = dateFormatSymbols.shortStandAloneWeekdays;
        dateFormatSymbols2.tinyStandAloneWeekdays = dateFormatSymbols.tinyStandAloneWeekdays;
    }

    public static Locale[] getAvailableLocales() {
        return ICU.getAvailableLocales();
    }

    private static DateFormatSymbols getCachedInstance(Locale locale) {
        Object object;
        block5 : {
            DateFormatSymbols dateFormatSymbols;
            block4 : {
                object = (SoftReference)cachedInstances.get(locale);
                if (object == null) break block4;
                dateFormatSymbols = (DateFormatSymbols)((SoftReference)object).get();
                object = dateFormatSymbols;
                if (dateFormatSymbols != null) break block5;
            }
            dateFormatSymbols = new DateFormatSymbols(locale);
            SoftReference<DateFormatSymbols> softReference = new SoftReference<DateFormatSymbols>(dateFormatSymbols);
            SoftReference<DateFormatSymbols> softReference2 = cachedInstances.putIfAbsent(locale, softReference);
            object = dateFormatSymbols;
            if (softReference2 != null && (object = softReference2.get()) == null) {
                cachedInstances.put(locale, softReference);
                object = dateFormatSymbols;
            }
        }
        return object;
    }

    public static final DateFormatSymbols getInstance() {
        return DateFormatSymbols.getInstance(Locale.getDefault(Locale.Category.FORMAT));
    }

    public static final DateFormatSymbols getInstance(Locale locale) {
        return (DateFormatSymbols)DateFormatSymbols.getCachedInstance(locale).clone();
    }

    static final DateFormatSymbols getInstanceRef(Locale locale) {
        return DateFormatSymbols.getCachedInstance(locale);
    }

    private String[][] getZoneStringsImpl(boolean bl) {
        String[][] arrstring = this.internalZoneStrings();
        if (!bl) {
            return arrstring;
        }
        int n = arrstring.length;
        String[][] arrstring2 = new String[n][];
        for (int i = 0; i < n; ++i) {
            arrstring2[i] = Arrays.copyOf(arrstring[i], arrstring[i].length);
        }
        return arrstring2;
    }

    private void initializeData(Locale locale) {
        Object object = (SoftReference)cachedInstances.get(locale);
        if (object != null && (object = (DateFormatSymbols)((SoftReference)object).get()) != null) {
            this.copyMembers((DateFormatSymbols)object, this);
            return;
        }
        locale = LocaleData.mapInvalidAndNullLocales((Locale)locale);
        object = LocaleData.get((Locale)locale);
        this.locale = locale;
        this.eras = ((LocaleData)object).eras;
        this.months = ((LocaleData)object).longMonthNames;
        this.shortMonths = ((LocaleData)object).shortMonthNames;
        this.ampms = ((LocaleData)object).amPm;
        this.localPatternChars = patternChars;
        this.weekdays = ((LocaleData)object).longWeekdayNames;
        this.shortWeekdays = ((LocaleData)object).shortWeekdayNames;
        this.initializeSupplementaryData((LocaleData)object);
    }

    private void initializeSupplementaryData(LocaleData localeData) {
        this.tinyMonths = localeData.tinyMonthNames;
        this.tinyWeekdays = localeData.tinyWeekdayNames;
        this.standAloneMonths = localeData.longStandAloneMonthNames;
        this.shortStandAloneMonths = localeData.shortStandAloneMonthNames;
        this.tinyStandAloneMonths = localeData.tinyStandAloneMonthNames;
        this.standAloneWeekdays = localeData.longStandAloneWeekdayNames;
        this.shortStandAloneWeekdays = localeData.shortStandAloneWeekdayNames;
        this.tinyStandAloneWeekdays = localeData.tinyStandAloneWeekdayNames;
    }

    private String[][] internalZoneStrings() {
        synchronized (this) {
            if (this.zoneStrings == null) {
                this.zoneStrings = TimeZoneNames.getZoneStrings((Locale)this.locale);
            }
            String[][] arrstring = this.zoneStrings;
            return arrstring;
        }
    }

    private boolean isSubclassObject() {
        return this.getClass().getName().equals("java.text.DateFormatSymbols") ^ true;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.serialVersionOnStream < 1) {
            this.initializeSupplementaryData(LocaleData.get((Locale)this.locale));
        }
        this.serialVersionOnStream = 1;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.internalZoneStrings();
        objectOutputStream.defaultWriteObject();
    }

    public Object clone() {
        try {
            DateFormatSymbols dateFormatSymbols = (DateFormatSymbols)super.clone();
            this.copyMembers(this, dateFormatSymbols);
            return dateFormatSymbols;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            DateFormatSymbols dateFormatSymbols = (DateFormatSymbols)object;
            if (Arrays.equals(this.eras, dateFormatSymbols.eras) && Arrays.equals(this.months, dateFormatSymbols.months) && Arrays.equals(this.shortMonths, dateFormatSymbols.shortMonths) && Arrays.equals(this.tinyMonths, dateFormatSymbols.tinyMonths) && Arrays.equals(this.weekdays, dateFormatSymbols.weekdays) && Arrays.equals(this.shortWeekdays, dateFormatSymbols.shortWeekdays) && Arrays.equals(this.tinyWeekdays, dateFormatSymbols.tinyWeekdays) && Arrays.equals(this.standAloneMonths, dateFormatSymbols.standAloneMonths) && Arrays.equals(this.shortStandAloneMonths, dateFormatSymbols.shortStandAloneMonths) && Arrays.equals(this.tinyStandAloneMonths, dateFormatSymbols.tinyStandAloneMonths) && Arrays.equals(this.standAloneWeekdays, dateFormatSymbols.standAloneWeekdays) && Arrays.equals(this.shortStandAloneWeekdays, dateFormatSymbols.shortStandAloneWeekdays) && Arrays.equals(this.tinyStandAloneWeekdays, dateFormatSymbols.tinyStandAloneWeekdays) && Arrays.equals(this.ampms, dateFormatSymbols.ampms) && ((object = this.localPatternChars) != null && ((String)object).equals(dateFormatSymbols.localPatternChars) || this.localPatternChars == null && dateFormatSymbols.localPatternChars == null)) {
                if (!this.isZoneStringsSet && !dateFormatSymbols.isZoneStringsSet && Objects.equals(this.locale, dateFormatSymbols.locale)) {
                    return true;
                }
                return Arrays.deepEquals((Object[])this.getZoneStringsWrapper(), (Object[])dateFormatSymbols.getZoneStringsWrapper());
            }
            return false;
        }
        return false;
    }

    public String[] getAmPmStrings() {
        String[] arrstring = this.ampms;
        return Arrays.copyOf(arrstring, arrstring.length);
    }

    public String[] getEras() {
        String[] arrstring = this.eras;
        return Arrays.copyOf(arrstring, arrstring.length);
    }

    public String getLocalPatternChars() {
        return this.localPatternChars;
    }

    public String[] getMonths() {
        String[] arrstring = this.months;
        return Arrays.copyOf(arrstring, arrstring.length);
    }

    public String[] getShortMonths() {
        String[] arrstring = this.shortMonths;
        return Arrays.copyOf(arrstring, arrstring.length);
    }

    String[] getShortStandAloneMonths() {
        return this.shortStandAloneMonths;
    }

    String[] getShortStandAloneWeekdays() {
        return this.shortStandAloneWeekdays;
    }

    public String[] getShortWeekdays() {
        String[] arrstring = this.shortWeekdays;
        return Arrays.copyOf(arrstring, arrstring.length);
    }

    String[] getStandAloneMonths() {
        return this.standAloneMonths;
    }

    String[] getStandAloneWeekdays() {
        return this.standAloneWeekdays;
    }

    String[] getTinyMonths() {
        return this.tinyMonths;
    }

    String[] getTinyStandAloneMonths() {
        return this.tinyStandAloneMonths;
    }

    String[] getTinyStandAloneWeekdays() {
        return this.tinyStandAloneWeekdays;
    }

    String[] getTinyWeekdays() {
        return this.tinyWeekdays;
    }

    public String[] getWeekdays() {
        String[] arrstring = this.weekdays;
        return Arrays.copyOf(arrstring, arrstring.length);
    }

    final int getZoneIndex(String string) {
        int n = this.lastZoneIndex;
        String[][] arrstring = this.getZoneStringsWrapper();
        if (n < arrstring.length && string.equals(arrstring[n][0])) {
            return this.lastZoneIndex;
        }
        for (n = 0; n < arrstring.length; ++n) {
            if (!string.equals(arrstring[n][0])) continue;
            this.lastZoneIndex = n;
            return n;
        }
        return -1;
    }

    public String[][] getZoneStrings() {
        return this.getZoneStringsImpl(true);
    }

    final String[][] getZoneStringsWrapper() {
        if (this.isSubclassObject()) {
            return this.getZoneStrings();
        }
        return this.getZoneStringsImpl(false);
    }

    public int hashCode() {
        int n;
        int n2 = n = this.cachedHashCode;
        if (n == 0) {
            this.cachedHashCode = n2 = ((((((5 * 11 + Arrays.hashCode(this.eras)) * 11 + Arrays.hashCode(this.months)) * 11 + Arrays.hashCode(this.shortMonths)) * 11 + Arrays.hashCode(this.weekdays)) * 11 + Arrays.hashCode(this.shortWeekdays)) * 11 + Arrays.hashCode(this.ampms)) * 11 + Objects.hashCode(this.localPatternChars);
        }
        return n2;
    }

    public void setAmPmStrings(String[] arrstring) {
        this.ampms = Arrays.copyOf(arrstring, arrstring.length);
        this.cachedHashCode = 0;
    }

    public void setEras(String[] arrstring) {
        this.eras = Arrays.copyOf(arrstring, arrstring.length);
        this.cachedHashCode = 0;
    }

    public void setLocalPatternChars(String string) {
        this.localPatternChars = string.toString();
        this.cachedHashCode = 0;
    }

    public void setMonths(String[] arrstring) {
        this.months = Arrays.copyOf(arrstring, arrstring.length);
        this.cachedHashCode = 0;
    }

    public void setShortMonths(String[] arrstring) {
        this.shortMonths = Arrays.copyOf(arrstring, arrstring.length);
        this.cachedHashCode = 0;
    }

    public void setShortWeekdays(String[] arrstring) {
        this.shortWeekdays = Arrays.copyOf(arrstring, arrstring.length);
        this.cachedHashCode = 0;
    }

    public void setWeekdays(String[] arrstring) {
        this.weekdays = Arrays.copyOf(arrstring, arrstring.length);
        this.cachedHashCode = 0;
    }

    public void setZoneStrings(String[][] arrstring) {
        String[][] arrarrstring = new String[arrstring.length][];
        for (int i = 0; i < arrstring.length; ++i) {
            int n = arrstring[i].length;
            if (n >= 5) {
                arrarrstring[i] = Arrays.copyOf(arrstring[i], n);
                continue;
            }
            throw new IllegalArgumentException();
        }
        this.zoneStrings = arrarrstring;
        this.isZoneStringsSet = true;
    }
}

