/*
 * Decompiled with CFR 0.145.
 */
package libcore.icu;

import android.icu.impl.ICUResourceBundle;
import android.icu.text.NumberingSystem;
import android.icu.util.UResourceBundle;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import libcore.icu.ICU;
import libcore.util.Objects;

public final class LocaleData {
    private static final HashMap<String, LocaleData> localeDataCache = new HashMap();
    public String NaN;
    public String[] amPm;
    public String currencyPattern;
    public String currencySymbol;
    public char decimalSeparator;
    public String[] eras;
    public String exponentSeparator;
    @UnsupportedAppUsage
    public Integer firstDayOfWeek;
    public String fullDateFormat;
    public String fullTimeFormat;
    public char groupingSeparator;
    public String infinity;
    public String integerPattern;
    public String internationalCurrencySymbol;
    public String longDateFormat;
    public String[] longMonthNames;
    public String[] longStandAloneMonthNames;
    @UnsupportedAppUsage
    public String[] longStandAloneWeekdayNames;
    public String longTimeFormat;
    public String[] longWeekdayNames;
    public String mediumDateFormat;
    public String mediumTimeFormat;
    @UnsupportedAppUsage
    public Integer minimalDaysInFirstWeek;
    public String minusSign;
    public char monetarySeparator;
    public String narrowAm;
    public String narrowPm;
    public String numberPattern;
    public char patternSeparator;
    public String perMill;
    public String percent;
    public String percentPattern;
    public String shortDateFormat;
    @UnsupportedAppUsage
    public String[] shortMonthNames;
    @UnsupportedAppUsage
    public String[] shortStandAloneMonthNames;
    @UnsupportedAppUsage
    public String[] shortStandAloneWeekdayNames;
    public String shortTimeFormat;
    public String[] shortWeekdayNames;
    @UnsupportedAppUsage
    public String timeFormat_Hm;
    public String timeFormat_Hms;
    @UnsupportedAppUsage
    public String timeFormat_hm;
    public String timeFormat_hms;
    public String[] tinyMonthNames;
    public String[] tinyStandAloneMonthNames;
    public String[] tinyStandAloneWeekdayNames;
    public String[] tinyWeekdayNames;
    @UnsupportedAppUsage
    public String today;
    @UnsupportedAppUsage
    public String tomorrow;
    public String yesterday;
    @UnsupportedAppUsage
    public char zeroDigit;

    static {
        LocaleData.get(Locale.ROOT);
        LocaleData.get(Locale.US);
        LocaleData.get(Locale.getDefault());
    }

    private LocaleData() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static LocaleData get(Locale cloneable) {
        LocaleData localeData;
        if (cloneable == null) {
            throw new NullPointerException("locale == null");
        }
        String string = cloneable.toLanguageTag();
        Object object = localeDataCache;
        synchronized (object) {
            localeData = localeDataCache.get(string);
            if (localeData != null) {
                return localeData;
            }
        }
        object = LocaleData.initLocaleData(cloneable);
        cloneable = localeDataCache;
        synchronized (cloneable) {
            localeData = localeDataCache.get(string);
            if (localeData != null) {
                return localeData;
            }
            localeDataCache.put(string, (LocaleData)object);
            return object;
        }
    }

    private static LocaleData initLocaleData(Locale object) {
        Object object2 = new LocaleData();
        if (ICU.initLocaleDataNative(((Locale)object).toLanguageTag(), (LocaleData)object2)) {
            LocaleData.initializePatternSeparator((LocaleData)object2, (Locale)object);
            ((LocaleData)object2).timeFormat_hm = ICU.getBestDateTimePattern("hm", (Locale)object);
            ((LocaleData)object2).timeFormat_Hm = ICU.getBestDateTimePattern("Hm", (Locale)object);
            ((LocaleData)object2).timeFormat_hms = ICU.getBestDateTimePattern("hms", (Locale)object);
            ((LocaleData)object2).timeFormat_Hms = ICU.getBestDateTimePattern("Hms", (Locale)object);
            object = ((LocaleData)object2).fullTimeFormat;
            if (object != null) {
                ((LocaleData)object2).fullTimeFormat = ((String)object).replace('v', 'z');
            }
            if ((object = ((LocaleData)object2).numberPattern) != null) {
                ((LocaleData)object2).integerPattern = ((String)object).replaceAll("\\.[#,]*", "");
            }
            return object2;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("couldn't initialize LocaleData for locale ");
        ((StringBuilder)object2).append(object);
        throw new AssertionError((Object)((StringBuilder)object2).toString());
    }

    private static void initializePatternSeparator(LocaleData localeData, Locale object) {
        block10 : {
            block9 : {
                Object object2 = NumberingSystem.getInstance((Locale)object);
                object2 = object2 != null && ((NumberingSystem)object2).getRadix() == 10 && !((NumberingSystem)object2).isAlgorithmic() ? ((NumberingSystem)object2).getName() : "latn";
                ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", (Locale)object);
                Object var4_6 = null;
                object = var4_6;
                if (!"latn".equals(object2)) {
                    try {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("NumberElements/");
                        ((StringBuilder)object).append((String)object2);
                        ((StringBuilder)object).append("/symbols/list");
                        object = iCUResourceBundle.getStringWithFallback(((StringBuilder)object).toString());
                    }
                    catch (MissingResourceException missingResourceException) {
                        object = var4_6;
                    }
                }
                object2 = object;
                if (object == null) {
                    try {
                        object2 = iCUResourceBundle.getStringWithFallback("NumberElements/latn/symbols/list");
                    }
                    catch (MissingResourceException missingResourceException) {
                        object2 = object;
                    }
                }
                if (object2 == null) break block9;
                object = object2;
                if (!((String)object2).isEmpty()) break block10;
            }
            object = ";";
        }
        localeData.patternSeparator = ((String)object).charAt(0);
    }

    @UnsupportedAppUsage
    public static Locale mapInvalidAndNullLocales(Locale locale) {
        if (locale == null) {
            return Locale.getDefault();
        }
        if ("und".equals(locale.toLanguageTag())) {
            return Locale.ROOT;
        }
        return locale;
    }

    public String getDateFormat(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        return this.shortDateFormat;
                    }
                    throw new AssertionError();
                }
                return this.mediumDateFormat;
            }
            return this.longDateFormat;
        }
        return this.fullDateFormat;
    }

    public String getTimeFormat(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        if (DateFormat.is24Hour == null) {
                            return this.shortTimeFormat;
                        }
                        String string = DateFormat.is24Hour != false ? this.timeFormat_Hm : this.timeFormat_hm;
                        return string;
                    }
                    throw new AssertionError();
                }
                if (DateFormat.is24Hour == null) {
                    return this.mediumTimeFormat;
                }
                String string = DateFormat.is24Hour != false ? this.timeFormat_Hms : this.timeFormat_hms;
                return string;
            }
            return this.longTimeFormat;
        }
        return this.fullTimeFormat;
    }

    public String toString() {
        return Objects.toString(this);
    }
}

