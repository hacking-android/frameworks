/*
 * Decompiled with CFR 0.145.
 */
package libcore.icu;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import libcore.icu.LocaleData;
import libcore.util.BasicLruCache;

public final class ICU {
    @UnsupportedAppUsage
    private static final BasicLruCache<String, String> CACHED_PATTERNS = new BasicLruCache(8);
    private static final int IDX_LANGUAGE = 0;
    private static final int IDX_REGION = 2;
    private static final int IDX_SCRIPT = 1;
    private static final int IDX_VARIANT = 3;
    public static final int U_BUFFER_OVERFLOW_ERROR = 15;
    public static final int U_ILLEGAL_CHAR_FOUND = 12;
    public static final int U_INVALID_CHAR_FOUND = 10;
    public static final int U_TRUNCATED_CHAR_FOUND = 11;
    public static final int U_ZERO_ERROR = 0;
    private static Locale[] availableLocalesCache;
    private static String[] isoCountries;
    private static String[] isoLanguages;

    private ICU() {
    }

    public static boolean U_FAILURE(int n) {
        boolean bl = n > 0;
        return bl;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static native String addLikelySubtags(String var0);

    @UnsupportedAppUsage
    public static Locale addLikelySubtags(Locale locale) {
        return Locale.forLanguageTag(ICU.addLikelySubtags(locale.toLanguageTag()).replace('_', '-'));
    }

    public static Locale[] getAvailableBreakIteratorLocales() {
        return ICU.localesFromStrings(ICU.getAvailableBreakIteratorLocalesNative());
    }

    private static native String[] getAvailableBreakIteratorLocalesNative();

    public static Locale[] getAvailableCalendarLocales() {
        return ICU.localesFromStrings(ICU.getAvailableCalendarLocalesNative());
    }

    private static native String[] getAvailableCalendarLocalesNative();

    public static Locale[] getAvailableCollatorLocales() {
        return ICU.localesFromStrings(ICU.getAvailableCollatorLocalesNative());
    }

    private static native String[] getAvailableCollatorLocalesNative();

    public static native String[] getAvailableCurrencyCodes();

    public static Locale[] getAvailableDateFormatLocales() {
        return ICU.localesFromStrings(ICU.getAvailableDateFormatLocalesNative());
    }

    private static native String[] getAvailableDateFormatLocalesNative();

    public static Locale[] getAvailableDateFormatSymbolsLocales() {
        return ICU.getAvailableDateFormatLocales();
    }

    public static Locale[] getAvailableDecimalFormatSymbolsLocales() {
        return ICU.getAvailableNumberFormatLocales();
    }

    public static Locale[] getAvailableLocales() {
        if (availableLocalesCache == null) {
            availableLocalesCache = ICU.localesFromStrings(ICU.getAvailableLocalesNative());
        }
        return (Locale[])availableLocalesCache.clone();
    }

    private static native String[] getAvailableLocalesNative();

    public static Locale[] getAvailableNumberFormatLocales() {
        return ICU.localesFromStrings(ICU.getAvailableNumberFormatLocalesNative());
    }

    private static native String[] getAvailableNumberFormatLocalesNative();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static String getBestDateTimePattern(String string, Locale object) {
        String string2 = ((Locale)object).toLanguageTag();
        object = new StringBuilder();
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("\t");
        ((StringBuilder)object).append(string2);
        String string3 = ((StringBuilder)object).toString();
        BasicLruCache<String, String> basicLruCache = CACHED_PATTERNS;
        synchronized (basicLruCache) {
            String string4 = CACHED_PATTERNS.get(string3);
            object = string4;
            if (string4 == null) {
                object = ICU.getBestDateTimePatternNative(string, string2);
                CACHED_PATTERNS.put(string3, (String)object);
            }
            return object;
        }
    }

    @UnsupportedAppUsage
    private static native String getBestDateTimePatternNative(String var0, String var1);

    public static native String getCldrVersion();

    public static native String getCurrencyCode(String var0);

    private static native String getCurrencyDisplayName(String var0, String var1);

    public static String getCurrencyDisplayName(Locale locale, String string) {
        return ICU.getCurrencyDisplayName(locale.toLanguageTag(), string);
    }

    public static native int getCurrencyFractionDigits(String var0);

    public static native int getCurrencyNumericCode(String var0);

    private static native String getCurrencySymbol(String var0, String var1);

    public static String getCurrencySymbol(Locale locale, String string) {
        return ICU.getCurrencySymbol(locale.toLanguageTag(), string);
    }

    /*
     * Enabled aggressive block sorting
     */
    @UnsupportedAppUsage
    public static char[] getDateFormatOrder(String string) {
        Object object = new char[3];
        int n = 0;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        int n2 = 0;
        while (n2 < string.length()) {
            int n3;
            int n4;
            boolean bl4;
            boolean bl5;
            boolean bl6;
            char c = string.charAt(n2);
            if (c != 'd' && c != 'L' && c != 'M' && c != 'y') {
                if (c == 'G') {
                    n3 = n;
                    bl5 = bl;
                    bl4 = bl2;
                    bl6 = bl3;
                    n4 = n2;
                } else {
                    if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Bad pattern character '");
                        ((StringBuilder)object).append(c);
                        ((StringBuilder)object).append("' in ");
                        ((StringBuilder)object).append(string);
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    n3 = n;
                    bl5 = bl;
                    bl4 = bl2;
                    bl6 = bl3;
                    n4 = n2;
                    if (c == '\'') {
                        if (n2 < string.length() - 1 && string.charAt(n2 + 1) == '\'') {
                            n4 = n2 + 1;
                            n3 = n;
                            bl5 = bl;
                            bl4 = bl2;
                            bl6 = bl3;
                        } else {
                            n3 = string.indexOf(39, n2 + 1);
                            if (n3 == -1) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Bad quoting in ");
                                ((StringBuilder)object).append(string);
                                throw new IllegalArgumentException(((StringBuilder)object).toString());
                            }
                            n4 = n3 + 1;
                            n3 = n;
                            bl5 = bl;
                            bl4 = bl2;
                            bl6 = bl3;
                        }
                    }
                }
            } else if (c == 'd' && !bl) {
                object[n] = (char)100;
                bl5 = true;
                n3 = n + 1;
                bl4 = bl2;
                bl6 = bl3;
                n4 = n2;
            } else if (!(c != 'L' && c != 'M' || bl2)) {
                object[n] = (char)77;
                bl4 = true;
                n3 = n + 1;
                bl5 = bl;
                bl6 = bl3;
                n4 = n2;
            } else {
                n3 = n;
                bl5 = bl;
                bl4 = bl2;
                bl6 = bl3;
                n4 = n2;
                if (c == 'y') {
                    n3 = n;
                    bl5 = bl;
                    bl4 = bl2;
                    bl6 = bl3;
                    n4 = n2;
                    if (!bl3) {
                        object[n] = (char)121;
                        bl6 = true;
                        n3 = n + 1;
                        n4 = n2;
                        bl4 = bl2;
                        bl5 = bl;
                    }
                }
            }
            n2 = n4 + 1;
            n = n3;
            bl = bl5;
            bl2 = bl4;
            bl3 = bl6;
        }
        return object;
    }

    public static native String getDefaultLocale();

    public static String getDisplayCountry(Locale locale, Locale locale2) {
        return ICU.getDisplayCountryNative(locale.toLanguageTag(), locale2.toLanguageTag());
    }

    private static native String getDisplayCountryNative(String var0, String var1);

    public static String getDisplayLanguage(Locale locale, Locale locale2) {
        return ICU.getDisplayLanguageNative(locale.toLanguageTag(), locale2.toLanguageTag());
    }

    private static native String getDisplayLanguageNative(String var0, String var1);

    public static String getDisplayScript(Locale locale, Locale locale2) {
        return ICU.getDisplayScriptNative(locale.toLanguageTag(), locale2.toLanguageTag());
    }

    private static native String getDisplayScriptNative(String var0, String var1);

    public static String getDisplayVariant(Locale locale, Locale locale2) {
        return ICU.getDisplayVariantNative(locale.toLanguageTag(), locale2.toLanguageTag());
    }

    private static native String getDisplayVariantNative(String var0, String var1);

    public static native String getISO3Country(String var0);

    public static native String getISO3Language(String var0);

    public static String[] getISOCountries() {
        if (isoCountries == null) {
            isoCountries = ICU.getISOCountriesNative();
        }
        return (String[])isoCountries.clone();
    }

    private static native String[] getISOCountriesNative();

    public static String[] getISOLanguages() {
        if (isoLanguages == null) {
            isoLanguages = ICU.getISOLanguagesNative();
        }
        return (String[])isoLanguages.clone();
    }

    private static native String[] getISOLanguagesNative();

    public static native String getIcuVersion();

    @Deprecated
    @UnsupportedAppUsage
    public static native String getScript(String var0);

    public static native String getTZDataVersion();

    public static native String getUnicodeVersion();

    static native boolean initLocaleDataNative(String var0, LocaleData var1);

    /*
     * WARNING - void declaration
     */
    public static Locale localeFromIcuLocaleId(String object) {
        void var2_8;
        void var3_16;
        String[] arrstring;
        int n = ((String)object).indexOf(64);
        Map object22 = Collections.EMPTY_MAP;
        Map map = Collections.EMPTY_MAP;
        Iterator<Map.Entry<K, V>> iterator = Collections.EMPTY_SET;
        if (n != -1) {
            arrstring = new HashMap<K, V>();
            HashMap<String, String> hashMap = new HashMap<String, String>();
            HashSet<String> hashSet = new HashSet<String>();
            String[] arrstring2 = ((String)object).substring(n + 1).split(";");
            int n2 = arrstring2.length;
            int n3 = 0;
            do {
                int n4;
                String[] arrstring3 = arrstring;
                HashMap<String, String> hashMap2 = hashMap;
                iterator = hashSet;
                if (n3 >= n2) break;
                String string = arrstring2[n3];
                if (string.startsWith("attribute=")) {
                    String[] arrstring4 = string.substring("attribute=".length()).split("-");
                    int n5 = arrstring4.length;
                    for (n4 = 0; n4 < n5; ++n4) {
                        hashSet.add(arrstring4[n4]);
                    }
                } else {
                    n4 = string.indexOf(61);
                    if (n4 == 1) {
                        String string2 = string.substring(2);
                        arrstring.put(Character.valueOf(string.charAt(0)), string2);
                    } else {
                        hashMap.put(string.substring(0, n4), string.substring(n4 + 1));
                    }
                }
                ++n3;
            } while (true);
        }
        arrstring = new String[]{"", "", "", ""};
        if (n == -1) {
            ICU.parseLangScriptRegionAndVariants((String)object, arrstring);
        } else {
            ICU.parseLangScriptRegionAndVariants(((String)object).substring(0, n), arrstring);
        }
        object = new Locale.Builder();
        ((Locale.Builder)object).setLanguage(arrstring[0]);
        ((Locale.Builder)object).setRegion(arrstring[2]);
        ((Locale.Builder)object).setVariant(arrstring[3]);
        ((Locale.Builder)object).setScript(arrstring[1]);
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            ((Locale.Builder)object).addUnicodeLocaleAttribute((String)iterator.next());
        }
        for (Map.Entry<K, V> entry : var3_16.entrySet()) {
            ((Locale.Builder)object).setUnicodeLocaleKeyword((String)entry.getKey(), (String)entry.getValue());
        }
        for (Map.Entry<K, V> entry : var2_8.entrySet()) {
            ((Locale.Builder)object).setExtension(((Character)entry.getKey()).charValue(), (String)entry.getValue());
        }
        return ((Locale.Builder)object).build();
    }

    public static Locale[] localesFromStrings(String[] arrstring) {
        LinkedHashSet<Locale> linkedHashSet = new LinkedHashSet<Locale>();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            linkedHashSet.add(ICU.localeFromIcuLocaleId(arrstring[i]));
        }
        return linkedHashSet.toArray(new Locale[linkedHashSet.size()]);
    }

    private static void parseLangScriptRegionAndVariants(String string, String[] arrstring) {
        int n = string.indexOf(95);
        int n2 = string.indexOf(95, n + 1);
        int n3 = string.indexOf(95, n2 + 1);
        if (n == -1) {
            arrstring[0] = string;
        } else if (n2 == -1) {
            arrstring[0] = string.substring(0, n);
            if ((string = string.substring(n + 1)).length() == 4) {
                arrstring[1] = string;
            } else if (string.length() != 2 && string.length() != 3) {
                arrstring[3] = string;
            } else {
                arrstring[2] = string;
            }
        } else if (n3 == -1) {
            arrstring[0] = string.substring(0, n);
            String string2 = string.substring(n + 1, n2);
            String string3 = string.substring(n2 + 1);
            if (string2.length() == 4) {
                arrstring[1] = string2;
                if (string3.length() != 2 && string3.length() != 3 && !string3.isEmpty()) {
                    arrstring[3] = string3;
                } else {
                    arrstring[2] = string3;
                }
            } else if (!string2.isEmpty() && string2.length() != 2 && string2.length() != 3) {
                arrstring[3] = string.substring(n + 1);
            } else {
                arrstring[2] = string2;
                arrstring[3] = string3;
            }
        } else {
            arrstring[0] = string.substring(0, n);
            String string4 = string.substring(n + 1, n2);
            if (string4.length() == 4) {
                arrstring[1] = string4;
                arrstring[2] = string.substring(n2 + 1, n3);
                arrstring[3] = string.substring(n3 + 1);
            } else {
                arrstring[2] = string4;
                arrstring[3] = string.substring(n2 + 1);
            }
        }
    }

    public static native void setDefaultLocale(String var0);

    private static native String toLowerCase(String var0, String var1);

    public static String toLowerCase(String string, Locale locale) {
        return ICU.toLowerCase(string, locale.toLanguageTag());
    }

    private static native String toUpperCase(String var0, String var1);

    public static String toUpperCase(String string, Locale locale) {
        return ICU.toUpperCase(string, locale.toLanguageTag());
    }
}

