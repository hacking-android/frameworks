/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.impl.CacheBase;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.ICUResourceTableAccess;
import android.icu.impl.LocaleIDParser;
import android.icu.impl.LocaleIDs;
import android.icu.impl.LocaleUtility;
import android.icu.impl.SoftCache;
import android.icu.impl.locale.AsciiUtil;
import android.icu.impl.locale.BaseLocale;
import android.icu.impl.locale.Extension;
import android.icu.impl.locale.InternalLocaleBuilder;
import android.icu.impl.locale.KeyTypeData;
import android.icu.impl.locale.LanguageTag;
import android.icu.impl.locale.LocaleExtensions;
import android.icu.impl.locale.LocaleSyntaxException;
import android.icu.impl.locale.ParseStatus;
import android.icu.impl.locale.UnicodeLocaleExtension;
import android.icu.lang.UScript;
import android.icu.text.LocaleDisplayNames;
import android.icu.util.IllformedLocaleException;
import android.icu.util.UResourceBundle;
import java.io.Serializable;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public final class ULocale
implements Serializable,
Comparable<ULocale> {
    public static Type ACTUAL_LOCALE;
    private static final SoftCache<Locale, ULocale, Void> CACHE;
    public static final ULocale CANADA;
    public static final ULocale CANADA_FRENCH;
    private static String[][] CANONICALIZE_MAP;
    public static final ULocale CHINA;
    public static final ULocale CHINESE;
    private static final Locale EMPTY_LOCALE;
    private static final String EMPTY_STRING = "";
    public static final ULocale ENGLISH;
    public static final ULocale FRANCE;
    public static final ULocale FRENCH;
    public static final ULocale GERMAN;
    public static final ULocale GERMANY;
    public static final ULocale ITALIAN;
    public static final ULocale ITALY;
    public static final ULocale JAPAN;
    public static final ULocale JAPANESE;
    public static final ULocale KOREA;
    public static final ULocale KOREAN;
    private static final String LANG_DIR_STRING = "root-en-es-pt-zh-ja-ko-de-fr-it-ar+he+fa+ru-nl-pl-th-tr-";
    private static final String LOCALE_ATTRIBUTE_KEY = "attribute";
    public static final ULocale PRC;
    public static final char PRIVATE_USE_EXTENSION = 'x';
    public static final ULocale ROOT;
    public static final ULocale SIMPLIFIED_CHINESE;
    public static final ULocale TAIWAN;
    public static final ULocale TRADITIONAL_CHINESE;
    public static final ULocale UK;
    private static final String UNDEFINED_LANGUAGE = "und";
    private static final String UNDEFINED_REGION = "ZZ";
    private static final String UNDEFINED_SCRIPT = "Zzzz";
    private static final char UNDERSCORE = '_';
    public static final char UNICODE_LOCALE_EXTENSION = 'u';
    public static final ULocale US;
    public static Type VALID_LOCALE;
    private static Locale[] defaultCategoryLocales;
    private static ULocale[] defaultCategoryULocales;
    private static Locale defaultLocale;
    private static ULocale defaultULocale;
    private static CacheBase<String, String, Void> nameCache;
    private static final long serialVersionUID = 3715177670352309217L;
    private static String[][] variantsToKeywords;
    private volatile transient BaseLocale baseLocale;
    private volatile transient LocaleExtensions extensions;
    private volatile transient Locale locale;
    private String localeID;

    static {
        nameCache = new SoftCache<String, String, Void>(){

            @Override
            protected String createInstance(String string, Void void_) {
                return new LocaleIDParser(string).getName();
            }
        };
        ENGLISH = new ULocale("en", Locale.ENGLISH);
        FRENCH = new ULocale("fr", Locale.FRENCH);
        GERMAN = new ULocale("de", Locale.GERMAN);
        ITALIAN = new ULocale("it", Locale.ITALIAN);
        JAPANESE = new ULocale("ja", Locale.JAPANESE);
        KOREAN = new ULocale("ko", Locale.KOREAN);
        CHINESE = new ULocale("zh", Locale.CHINESE);
        SIMPLIFIED_CHINESE = new ULocale("zh_Hans");
        TRADITIONAL_CHINESE = new ULocale("zh_Hant");
        FRANCE = new ULocale("fr_FR", Locale.FRANCE);
        GERMANY = new ULocale("de_DE", Locale.GERMANY);
        ITALY = new ULocale("it_IT", Locale.ITALY);
        JAPAN = new ULocale("ja_JP", Locale.JAPAN);
        KOREA = new ULocale("ko_KR", Locale.KOREA);
        PRC = CHINA = new ULocale("zh_Hans_CN");
        TAIWAN = new ULocale("zh_Hant_TW");
        UK = new ULocale("en_GB", Locale.UK);
        US = new ULocale("en_US", Locale.US);
        CANADA = new ULocale("en_CA", Locale.CANADA);
        CANADA_FRENCH = new ULocale("fr_CA", Locale.CANADA_FRENCH);
        EMPTY_LOCALE = new Locale(EMPTY_STRING, EMPTY_STRING);
        ROOT = new ULocale(EMPTY_STRING, EMPTY_LOCALE);
        CACHE = new SoftCache<Locale, ULocale, Void>(){

            @Override
            protected ULocale createInstance(Locale locale, Void void_) {
                return JDKLocaleHelper.toULocale(locale);
            }
        };
        int n = 0;
        String[] object2 = new String[]{"es_ES_PREEURO", "es_ES", "currency", "ESP"};
        String[] arrstring = new String[]{"uz_UZ_CYRILLIC", "uz_Cyrl_UZ", null, null};
        CANONICALIZE_MAP = new String[][]{{"C", "en_US_POSIX", null, null}, {"art_LOJBAN", "jbo", null, null}, {"az_AZ_CYRL", "az_Cyrl_AZ", null, null}, {"az_AZ_LATN", "az_Latn_AZ", null, null}, {"ca_ES_PREEURO", "ca_ES", "currency", "ESP"}, {"cel_GAULISH", "cel__GAULISH", null, null}, {"de_1901", "de__1901", null, null}, {"de_1906", "de__1906", null, null}, {"de__PHONEBOOK", "de", "collation", "phonebook"}, {"de_AT_PREEURO", "de_AT", "currency", "ATS"}, {"de_DE_PREEURO", "de_DE", "currency", "DEM"}, {"de_LU_PREEURO", "de_LU", "currency", "EUR"}, {"el_GR_PREEURO", "el_GR", "currency", "GRD"}, {"en_BOONT", "en__BOONT", null, null}, {"en_SCOUSE", "en__SCOUSE", null, null}, {"en_BE_PREEURO", "en_BE", "currency", "BEF"}, {"en_IE_PREEURO", "en_IE", "currency", "IEP"}, {"es__TRADITIONAL", "es", "collation", "traditional"}, object2, {"eu_ES_PREEURO", "eu_ES", "currency", "ESP"}, {"fi_FI_PREEURO", "fi_FI", "currency", "FIM"}, {"fr_BE_PREEURO", "fr_BE", "currency", "BEF"}, {"fr_FR_PREEURO", "fr_FR", "currency", "FRF"}, {"fr_LU_PREEURO", "fr_LU", "currency", "LUF"}, {"ga_IE_PREEURO", "ga_IE", "currency", "IEP"}, {"gl_ES_PREEURO", "gl_ES", "currency", "ESP"}, {"hi__DIRECT", "hi", "collation", "direct"}, {"it_IT_PREEURO", "it_IT", "currency", "ITL"}, {"ja_JP_TRADITIONAL", "ja_JP", "calendar", "japanese"}, {"nl_BE_PREEURO", "nl_BE", "currency", "BEF"}, {"nl_NL_PREEURO", "nl_NL", "currency", "NLG"}, {"pt_PT_PREEURO", "pt_PT", "currency", "PTE"}, {"sl_ROZAJ", "sl__ROZAJ", null, null}, {"sr_SP_CYRL", "sr_Cyrl_RS", null, null}, {"sr_SP_LATN", "sr_Latn_RS", null, null}, {"sr_YU_CYRILLIC", "sr_Cyrl_RS", null, null}, {"th_TH_TRADITIONAL", "th_TH", "calendar", "buddhist"}, arrstring, {"uz_UZ_CYRL", "uz_Cyrl_UZ", null, null}, {"uz_UZ_LATN", "uz_Latn_UZ", null, null}, {"zh_CHS", "zh_Hans", null, null}, {"zh_CHT", "zh_Hant", null, null}, {"zh_GAN", "zh__GAN", null, null}, {"zh_GUOYU", "zh", null, null}, {"zh_HAKKA", "zh__HAKKA", null, null}, {"zh_MIN", "zh__MIN", null, null}, {"zh_MIN_NAN", "zh__MINNAN", null, null}, {"zh_WUU", "zh__WUU", null, null}, {"zh_XIANG", "zh__XIANG", null, null}, {"zh_YUE", "zh__YUE", null, null}};
        variantsToKeywords = new String[][]{{"EURO", "currency", "EUR"}, {"PINYIN", "collation", "pinyin"}, {"STROKE", "collation", "stroke"}};
        defaultLocale = Locale.getDefault();
        defaultCategoryLocales = new Locale[Category.values().length];
        defaultCategoryULocales = new ULocale[Category.values().length];
        defaultULocale = ULocale.forLocale(defaultLocale);
        if (JDKLocaleHelper.hasLocaleCategories()) {
            for (Category category : Category.values()) {
                int n2 = category.ordinal();
                ULocale.defaultCategoryLocales[n2] = JDKLocaleHelper.getDefault(category);
                ULocale.defaultCategoryULocales[n2] = ULocale.forLocale(defaultCategoryLocales[n2]);
            }
        } else {
            Category[] arrcategory = Category.values();
            int n3 = arrcategory.length;
            for (int i = n; i < n3; ++i) {
                n = arrcategory[i].ordinal();
                ULocale.defaultCategoryLocales[n] = defaultLocale;
                ULocale.defaultCategoryULocales[n] = defaultULocale;
            }
        }
        ACTUAL_LOCALE = new Type();
        VALID_LOCALE = new Type();
    }

    public ULocale(String string) {
        this.localeID = ULocale.getName(string);
    }

    public ULocale(String string, String string2) {
        this(string, string2, null);
    }

    public ULocale(String string, String string2, String string3) {
        this.localeID = ULocale.getName(ULocale.lscvToID(string, string2, string3, EMPTY_STRING));
    }

    private ULocale(String string, Locale locale) {
        this.localeID = string;
        this.locale = locale;
    }

    private ULocale(Locale locale) {
        this.localeID = ULocale.getName(ULocale.forLocale(locale).toString());
        this.locale = locale;
    }

    public static ULocale acceptLanguage(String arruLocale, ULocale[] arruLocale2, boolean[] arrbl) {
        if (arruLocale != null) {
            try {
                arruLocale = ULocale.parseAcceptLanguage((String)arruLocale, true);
            }
            catch (ParseException parseException) {
                arruLocale = null;
            }
            if (arruLocale == null) {
                return null;
            }
            return ULocale.acceptLanguage(arruLocale, arruLocale2, arrbl);
        }
        throw new NullPointerException();
    }

    public static ULocale acceptLanguage(String string, boolean[] arrbl) {
        return ULocale.acceptLanguage(string, ULocale.getAvailableLocales(), arrbl);
    }

    public static ULocale acceptLanguage(ULocale[] arruLocale, ULocale[] arruLocale2, boolean[] arrbl) {
        if (arrbl != null) {
            arrbl[0] = true;
        }
        for (int i = 0; i < arruLocale.length; ++i) {
            Serializable serializable;
            Serializable serializable2 = arruLocale[i];
            boolean[] arrbl2 = arrbl;
            do {
                for (int j = 0; j < arruLocale2.length; ++j) {
                    if (arruLocale2[j].equals(serializable2)) {
                        if (arrbl2 != null) {
                            arrbl2[0] = false;
                        }
                        return arruLocale2[j];
                    }
                    if (((ULocale)serializable2).getScript().length() != 0 || arruLocale2[j].getScript().length() <= 0 || !arruLocale2[j].getLanguage().equals(((ULocale)serializable2).getLanguage()) || !arruLocale2[j].getCountry().equals(((ULocale)serializable2).getCountry()) || !arruLocale2[j].getVariant().equals(((ULocale)serializable2).getVariant()) || ULocale.minimizeSubtags(arruLocale2[j]).getScript().length() != 0) continue;
                    if (arrbl2 != null) {
                        arrbl2[0] = false;
                    }
                    return serializable2;
                }
                serializable = LocaleUtility.fallback(((ULocale)serializable2).toLocale());
                serializable = serializable != null ? new ULocale((Locale)serializable) : null;
                arrbl2 = null;
                serializable2 = serializable;
            } while (serializable != null);
        }
        return null;
    }

    public static ULocale acceptLanguage(ULocale[] arruLocale, boolean[] arrbl) {
        return ULocale.acceptLanguage(arruLocale, ULocale.getAvailableLocales(), arrbl);
    }

    public static ULocale addLikelySubtags(ULocale uLocale) {
        String[] arrstring = new String[3];
        String string = null;
        int n = ULocale.parseTagString(uLocale.localeID, arrstring);
        if (n < uLocale.localeID.length()) {
            string = uLocale.localeID.substring(n);
        }
        if ((string = ULocale.createLikelySubtagsString(arrstring[0], arrstring[1], arrstring[2], string)) != null) {
            uLocale = new ULocale(string);
        }
        return uLocale;
    }

    private static void appendTag(String string, StringBuilder stringBuilder) {
        if (stringBuilder.length() != 0) {
            stringBuilder.append('_');
        }
        stringBuilder.append(string);
    }

    private BaseLocale base() {
        if (this.baseLocale == null) {
            String string = EMPTY_STRING;
            Object object = EMPTY_STRING;
            String string2 = EMPTY_STRING;
            String string3 = EMPTY_STRING;
            if (!this.equals(ROOT)) {
                object = new LocaleIDParser(this.localeID);
                string = ((LocaleIDParser)object).getLanguage();
                string3 = ((LocaleIDParser)object).getScript();
                string2 = ((LocaleIDParser)object).getCountry();
                object = ((LocaleIDParser)object).getVariant();
            }
            this.baseLocale = BaseLocale.getInstance(string, string3, string2, (String)object);
        }
        return this.baseLocale;
    }

    public static String canonicalize(String charSequence) {
        boolean bl;
        LocaleIDParser localeIDParser = new LocaleIDParser((String)charSequence, true);
        Object object = localeIDParser.getBaseName();
        boolean bl2 = false;
        if (((String)charSequence).equals(EMPTY_STRING)) {
            return EMPTY_STRING;
        }
        int n = 0;
        do {
            String[][] arrstring = variantsToKeywords;
            charSequence = object;
            bl = bl2;
            if (n >= arrstring.length) break;
            arrstring = arrstring[n];
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("_");
            ((StringBuilder)charSequence).append((String)arrstring[0]);
            int n2 = ((String)object).lastIndexOf(((StringBuilder)charSequence).toString());
            if (n2 > -1) {
                bl = true;
                object = ((String)object).substring(0, n2);
                charSequence = object;
                if (((String)object).endsWith("_")) {
                    charSequence = ((String)object).substring(0, n2 - 1);
                }
                localeIDParser.setBaseName((String)charSequence);
                localeIDParser.defaultKeywordValue((String)arrstring[1], (String)arrstring[2]);
                break;
            }
            ++n;
        } while (true);
        n = 0;
        do {
            object = CANONICALIZE_MAP;
            bl2 = bl;
            if (n >= ((String[][])object).length) break;
            if (object[n][0].equals(charSequence)) {
                bl = true;
                charSequence = CANONICALIZE_MAP[n];
                localeIDParser.setBaseName((String)charSequence[1]);
                bl2 = bl;
                if (charSequence[2] == null) break;
                localeIDParser.defaultKeywordValue((String)charSequence[2], (String)charSequence[3]);
                bl2 = bl;
                break;
            }
            ++n;
        } while (true);
        if (!bl2 && localeIDParser.getLanguage().equals("nb") && localeIDParser.getVariant().equals("NY")) {
            localeIDParser.setBaseName(ULocale.lscvToID("nn", localeIDParser.getScript(), localeIDParser.getCountry(), null));
        }
        return localeIDParser.getName();
    }

    public static ULocale createCanonical(String string) {
        return new ULocale(ULocale.canonicalize(string), (Locale)null);
    }

    private static String createLikelySubtagsString(String string, String string2, String string3, String string4) {
        String string5;
        if (!ULocale.isEmptyString(string2) && !ULocale.isEmptyString(string3) && (string5 = ULocale.lookupLikelySubtags(ULocale.createTagString(string, string2, string3, null))) != null) {
            return ULocale.createTagString(null, null, null, string4, string5);
        }
        if (!ULocale.isEmptyString(string2) && (string5 = ULocale.lookupLikelySubtags(ULocale.createTagString(string, string2, null, null))) != null) {
            return ULocale.createTagString(null, null, string3, string4, string5);
        }
        if (!ULocale.isEmptyString(string3) && (string5 = ULocale.lookupLikelySubtags(ULocale.createTagString(string, null, string3, null))) != null) {
            return ULocale.createTagString(null, string2, null, string4, string5);
        }
        if ((string = ULocale.lookupLikelySubtags(ULocale.createTagString(string, null, null, null))) != null) {
            return ULocale.createTagString(null, string2, string3, string4, string);
        }
        return null;
    }

    static String createTagString(String string, String string2, String string3, String string4) {
        return ULocale.createTagString(string, string2, string3, string4, null);
    }

    private static String createTagString(String object, String object2, String string, String string2, String string3) {
        int n;
        Object object3 = null;
        int n2 = 0;
        StringBuilder stringBuilder = new StringBuilder();
        if (!ULocale.isEmptyString((String)object)) {
            ULocale.appendTag((String)object, stringBuilder);
            object = object3;
        } else {
            boolean bl = ULocale.isEmptyString(string3);
            object = UNDEFINED_LANGUAGE;
            if (bl) {
                ULocale.appendTag(UNDEFINED_LANGUAGE, stringBuilder);
                object = object3;
            } else {
                object3 = new LocaleIDParser(string3);
                String string4 = ((LocaleIDParser)object3).getLanguage();
                if (!ULocale.isEmptyString(string4)) {
                    object = string4;
                }
                ULocale.appendTag((String)object, stringBuilder);
                object = object3;
            }
        }
        if (!ULocale.isEmptyString((String)object2)) {
            ULocale.appendTag((String)object2, stringBuilder);
            object2 = object;
        } else {
            object2 = object;
            if (!ULocale.isEmptyString(string3)) {
                object3 = object;
                if (object == null) {
                    object3 = new LocaleIDParser(string3);
                }
                object = ((LocaleIDParser)object3).getScript();
                object2 = object3;
                if (!ULocale.isEmptyString((String)object)) {
                    ULocale.appendTag((String)object, stringBuilder);
                    object2 = object3;
                }
            }
        }
        if (!ULocale.isEmptyString(string)) {
            ULocale.appendTag(string, stringBuilder);
            n = 1;
        } else {
            n = n2;
            if (!ULocale.isEmptyString(string3)) {
                object = object2;
                if (object2 == null) {
                    object = new LocaleIDParser(string3);
                }
                object = ((LocaleIDParser)object).getCountry();
                n = n2;
                if (!ULocale.isEmptyString((String)object)) {
                    ULocale.appendTag((String)object, stringBuilder);
                    n = 1;
                }
            }
        }
        if (string2 != null && string2.length() > 1) {
            n2 = 0;
            if (string2.charAt(0) == '_') {
                if (string2.charAt(1) == '_') {
                    n2 = 2;
                }
            } else {
                n2 = 1;
            }
            if (n != 0) {
                if (n2 == 2) {
                    stringBuilder.append(string2.substring(1));
                } else {
                    stringBuilder.append(string2);
                }
            } else {
                if (n2 == 1) {
                    stringBuilder.append('_');
                }
                stringBuilder.append(string2);
            }
        }
        return stringBuilder.toString();
    }

    private LocaleExtensions extensions() {
        if (this.extensions == null) {
            Iterator<String> iterator = this.getKeywords();
            if (iterator == null) {
                this.extensions = LocaleExtensions.EMPTY_EXTENSIONS;
            } else {
                InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
                while (iterator.hasNext()) {
                    String string22 = iterator.next();
                    boolean bl = string22.equals(LOCALE_ATTRIBUTE_KEY);
                    if (bl) {
                        for (String string22 : this.getKeywordValue(string22).split("[-_]")) {
                            try {
                                internalLocaleBuilder.addUnicodeLocaleAttribute(string22);
                            }
                            catch (LocaleSyntaxException localeSyntaxException) {
                                // empty catch block
                            }
                        }
                        continue;
                    }
                    if (string22.length() >= 2) {
                        String localeSyntaxException = ULocale.toUnicodeLocaleKey(string22);
                        string22 = ULocale.toUnicodeLocaleType(string22, this.getKeywordValue(string22));
                        if (localeSyntaxException == null || string22 == null) continue;
                        try {
                            internalLocaleBuilder.setUnicodeLocaleKeyword(localeSyntaxException, string22);
                        }
                        catch (LocaleSyntaxException localeSyntaxException2) {}
                        continue;
                    }
                    if (string22.length() != 1 || string22.charAt(0) == 'u') continue;
                    try {
                        internalLocaleBuilder.setExtension(string22.charAt(0), this.getKeywordValue(string22).replace("_", "-"));
                    }
                    catch (LocaleSyntaxException localeSyntaxException) {
                    }
                }
                this.extensions = internalLocaleBuilder.getLocaleExtensions();
            }
        }
        return this.extensions;
    }

    public static ULocale forLanguageTag(String object) {
        object = LanguageTag.parse((String)object, null);
        InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
        internalLocaleBuilder.setLanguageTag((LanguageTag)object);
        return ULocale.getInstance(internalLocaleBuilder.getBaseLocale(), internalLocaleBuilder.getLocaleExtensions());
    }

    public static ULocale forLocale(Locale locale) {
        if (locale == null) {
            return null;
        }
        return CACHE.getInstance(locale, null);
    }

    public static ULocale[] getAvailableLocales() {
        return ICUResourceBundle.getAvailableULocales();
    }

    public static String getBaseName(String string) {
        if (string.indexOf(64) == -1) {
            return string;
        }
        return new LocaleIDParser(string).getBaseName();
    }

    public static String getCountry(String string) {
        return new LocaleIDParser(string).getCountry();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ULocale getDefault() {
        synchronized (ULocale.class) {
            if (defaultULocale == null) {
                return ROOT;
            }
            Serializable serializable = Locale.getDefault();
            if (defaultLocale.equals(serializable)) return defaultULocale;
            defaultLocale = serializable;
            defaultULocale = ULocale.forLocale(serializable);
            if (JDKLocaleHelper.hasLocaleCategories()) return defaultULocale;
            Category[] arrcategory = Category.values();
            int n = arrcategory.length;
            int n2 = 0;
            while (n2 < n) {
                int n3 = arrcategory[n2].ordinal();
                ULocale.defaultCategoryLocales[n3] = serializable;
                ULocale.defaultCategoryULocales[n3] = ULocale.forLocale(serializable);
                ++n2;
            }
            return defaultULocale;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ULocale getDefault(Category object) {
        synchronized (ULocale.class) {
            int n = object.ordinal();
            if (defaultCategoryULocales[n] == null) {
                return ROOT;
            }
            if (!JDKLocaleHelper.hasLocaleCategories()) {
                object = Locale.getDefault();
                if (defaultLocale.equals(object)) return defaultCategoryULocales[n];
                defaultLocale = object;
                defaultULocale = ULocale.forLocale((Locale)object);
                Category[] arrcategory = Category.values();
                int n2 = arrcategory.length;
                int n3 = 0;
                while (n3 < n2) {
                    int n4 = arrcategory[n3].ordinal();
                    ULocale.defaultCategoryLocales[n4] = object;
                    ULocale.defaultCategoryULocales[n4] = ULocale.forLocale((Locale)object);
                    ++n3;
                }
                return defaultCategoryULocales[n];
            }
            if (defaultCategoryLocales[n].equals(object = JDKLocaleHelper.getDefault(object))) return defaultCategoryULocales[n];
            ULocale.defaultCategoryLocales[n] = object;
            ULocale.defaultCategoryULocales[n] = ULocale.forLocale((Locale)object);
            return defaultCategoryULocales[n];
        }
    }

    public static String getDisplayCountry(String string, ULocale uLocale) {
        return ULocale.getDisplayCountryInternal(new ULocale(string), uLocale);
    }

    public static String getDisplayCountry(String string, String string2) {
        return ULocale.getDisplayCountryInternal(new ULocale(string), new ULocale(string2));
    }

    private static String getDisplayCountryInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).regionDisplayName(uLocale.getCountry());
    }

    public static String getDisplayKeyword(String string) {
        return ULocale.getDisplayKeywordInternal(string, ULocale.getDefault(Category.DISPLAY));
    }

    public static String getDisplayKeyword(String string, ULocale uLocale) {
        return ULocale.getDisplayKeywordInternal(string, uLocale);
    }

    public static String getDisplayKeyword(String string, String string2) {
        return ULocale.getDisplayKeywordInternal(string, new ULocale(string2));
    }

    private static String getDisplayKeywordInternal(String string, ULocale uLocale) {
        return LocaleDisplayNames.getInstance(uLocale).keyDisplayName(string);
    }

    public static String getDisplayKeywordValue(String string, String string2, ULocale uLocale) {
        return ULocale.getDisplayKeywordValueInternal(new ULocale(string), string2, uLocale);
    }

    public static String getDisplayKeywordValue(String string, String string2, String string3) {
        return ULocale.getDisplayKeywordValueInternal(new ULocale(string), string2, new ULocale(string3));
    }

    private static String getDisplayKeywordValueInternal(ULocale object, String string, ULocale uLocale) {
        string = AsciiUtil.toLowerString(string.trim());
        object = ((ULocale)object).getKeywordValue(string);
        return LocaleDisplayNames.getInstance(uLocale).keyValueDisplayName(string, (String)object);
    }

    public static String getDisplayLanguage(String string, ULocale uLocale) {
        return ULocale.getDisplayLanguageInternal(new ULocale(string), uLocale, false);
    }

    public static String getDisplayLanguage(String string, String string2) {
        return ULocale.getDisplayLanguageInternal(new ULocale(string), new ULocale(string2), false);
    }

    private static String getDisplayLanguageInternal(ULocale object, ULocale uLocale, boolean bl) {
        object = bl ? ((ULocale)object).getBaseName() : ((ULocale)object).getLanguage();
        return LocaleDisplayNames.getInstance(uLocale).languageDisplayName((String)object);
    }

    public static String getDisplayLanguageWithDialect(String string, ULocale uLocale) {
        return ULocale.getDisplayLanguageInternal(new ULocale(string), uLocale, true);
    }

    public static String getDisplayLanguageWithDialect(String string, String string2) {
        return ULocale.getDisplayLanguageInternal(new ULocale(string), new ULocale(string2), true);
    }

    public static String getDisplayName(String string, ULocale uLocale) {
        return ULocale.getDisplayNameInternal(new ULocale(string), uLocale);
    }

    public static String getDisplayName(String string, String string2) {
        return ULocale.getDisplayNameInternal(new ULocale(string), new ULocale(string2));
    }

    private static String getDisplayNameInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).localeDisplayName(uLocale);
    }

    public static String getDisplayNameWithDialect(String string, ULocale uLocale) {
        return ULocale.getDisplayNameWithDialectInternal(new ULocale(string), uLocale);
    }

    public static String getDisplayNameWithDialect(String string, String string2) {
        return ULocale.getDisplayNameWithDialectInternal(new ULocale(string), new ULocale(string2));
    }

    private static String getDisplayNameWithDialectInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2, LocaleDisplayNames.DialectHandling.DIALECT_NAMES).localeDisplayName(uLocale);
    }

    public static String getDisplayScript(String string, ULocale uLocale) {
        return ULocale.getDisplayScriptInternal(new ULocale(string), uLocale);
    }

    public static String getDisplayScript(String string, String string2) {
        return ULocale.getDisplayScriptInternal(new ULocale(string), new ULocale(string2));
    }

    @Deprecated
    public static String getDisplayScriptInContext(String string, ULocale uLocale) {
        return ULocale.getDisplayScriptInContextInternal(new ULocale(string), uLocale);
    }

    @Deprecated
    public static String getDisplayScriptInContext(String string, String string2) {
        return ULocale.getDisplayScriptInContextInternal(new ULocale(string), new ULocale(string2));
    }

    private static String getDisplayScriptInContextInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).scriptDisplayNameInContext(uLocale.getScript());
    }

    private static String getDisplayScriptInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).scriptDisplayName(uLocale.getScript());
    }

    public static String getDisplayVariant(String string, ULocale uLocale) {
        return ULocale.getDisplayVariantInternal(new ULocale(string), uLocale);
    }

    public static String getDisplayVariant(String string, String string2) {
        return ULocale.getDisplayVariantInternal(new ULocale(string), new ULocale(string2));
    }

    private static String getDisplayVariantInternal(ULocale uLocale, ULocale uLocale2) {
        return LocaleDisplayNames.getInstance(uLocale2).variantDisplayName(uLocale.getVariant());
    }

    public static String getFallback(String string) {
        return ULocale.getFallbackString(ULocale.getName(string));
    }

    private static String getFallbackString(String string) {
        int n;
        int n2;
        int n3 = n = string.indexOf(64);
        if (n == -1) {
            n3 = string.length();
        }
        if ((n = string.lastIndexOf(95, n3)) == -1) {
            n2 = 0;
        } else {
            do {
                n2 = --n;
                if (n <= 0) break;
                if (string.charAt(n - 1) == '_') continue;
                n2 = n;
                break;
            } while (true);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string.substring(0, n2));
        stringBuilder.append(string.substring(n3));
        return stringBuilder.toString();
    }

    public static String getISO3Country(String string) {
        return LocaleIDs.getISO3Country(ULocale.getCountry(string));
    }

    public static String getISO3Language(String string) {
        return LocaleIDs.getISO3Language(ULocale.getLanguage(string));
    }

    public static String[] getISOCountries() {
        return LocaleIDs.getISOCountries();
    }

    public static String[] getISOLanguages() {
        return LocaleIDs.getISOLanguages();
    }

    private static ULocale getInstance(BaseLocale object, LocaleExtensions iterator) {
        Object object2 = ULocale.lscvToID(((BaseLocale)object).getLanguage(), ((BaseLocale)object).getScript(), ((BaseLocale)object).getRegion(), ((BaseLocale)object).getVariant());
        Object object3 = ((LocaleExtensions)((Object)iterator)).getKeys();
        if (!object3.isEmpty()) {
            TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
            Iterator<Character> iterator2 = object3.iterator();
            while (iterator2.hasNext()) {
                object3 = iterator2.next();
                Object object4 = ((LocaleExtensions)((Object)iterator)).getExtension((Character)object3);
                if (object4 instanceof UnicodeLocaleExtension) {
                    object4 = (UnicodeLocaleExtension)object4;
                    for (String string : ((UnicodeLocaleExtension)object4).getUnicodeLocaleKeys()) {
                        object3 = ((UnicodeLocaleExtension)object4).getUnicodeLocaleType(string);
                        String string2 = ULocale.toLegacyKey(string);
                        if (((String)object3).length() == 0) {
                            object3 = "yes";
                        }
                        object3 = ULocale.toLegacyType(string, (String)object3);
                        if (string2.equals("va") && ((String)object3).equals("posix") && ((BaseLocale)object).getVariant().length() == 0) {
                            object3 = new StringBuilder();
                            ((StringBuilder)object3).append((String)object2);
                            ((StringBuilder)object3).append("_POSIX");
                            object2 = ((StringBuilder)object3).toString();
                            continue;
                        }
                        treeMap.put(string2, object3);
                    }
                    if ((object4 = ((UnicodeLocaleExtension)object4).getUnicodeLocaleAttributes()).size() <= 0) continue;
                    object3 = new StringBuilder();
                    object4 = object4.iterator();
                    while (object4.hasNext()) {
                        String string = (String)object4.next();
                        if (((StringBuilder)object3).length() > 0) {
                            ((StringBuilder)object3).append('-');
                        }
                        ((StringBuilder)object3).append(string);
                    }
                    treeMap.put(LOCALE_ATTRIBUTE_KEY, ((StringBuilder)object3).toString());
                    continue;
                }
                treeMap.put(String.valueOf(object3), ((Extension)object4).getValue());
            }
            object = object2;
            if (!treeMap.isEmpty()) {
                object = new StringBuilder((String)object2);
                ((StringBuilder)object).append("@");
                iterator = treeMap.entrySet();
                boolean bl = false;
                iterator = iterator.iterator();
                while (iterator.hasNext()) {
                    object2 = (Map.Entry)iterator.next();
                    if (bl) {
                        ((StringBuilder)object).append(";");
                    } else {
                        bl = true;
                    }
                    ((StringBuilder)object).append((String)object2.getKey());
                    ((StringBuilder)object).append("=");
                    ((StringBuilder)object).append((String)object2.getValue());
                }
                object = ((StringBuilder)object).toString();
            }
        } else {
            object = object2;
        }
        return new ULocale((String)object);
    }

    public static String getKeywordValue(String string, String string2) {
        return new LocaleIDParser(string).getKeywordValue(string2);
    }

    public static Iterator<String> getKeywords(String string) {
        return new LocaleIDParser(string).getKeywords();
    }

    public static String getLanguage(String string) {
        return new LocaleIDParser(string).getLanguage();
    }

    public static String getName(String string) {
        String string2;
        if (string != null && !string.contains("@") && ULocale.getShortestSubtagLength(string) == 1) {
            String string3;
            string2 = string3 = ULocale.forLanguageTag(string).getName();
            if (string3.length() == 0) {
                string2 = string;
            }
        } else {
            string2 = string;
        }
        return nameCache.getInstance(string2, null);
    }

    @Deprecated
    public static String getRegionForSupplementalData(ULocale uLocale, boolean bl) {
        String string;
        String string2 = uLocale.getKeywordValue("rg");
        if (string2 != null && string2.length() == 6 && (string2 = AsciiUtil.toUpperString(string2)).endsWith("ZZZZ")) {
            return string2.substring(0, 2);
        }
        string2 = string = uLocale.getCountry();
        if (string.length() == 0) {
            string2 = string;
            if (bl) {
                string2 = ULocale.addLikelySubtags(uLocale).getCountry();
            }
        }
        return string2;
    }

    public static String getScript(String string) {
        return new LocaleIDParser(string).getScript();
    }

    private static int getShortestSubtagLength(String string) {
        int n;
        int n2 = n = string.length();
        int n3 = 1;
        int n4 = 0;
        for (int i = 0; i < n; ++i) {
            int n5;
            if (string.charAt(i) != '_' && string.charAt(i) != '-') {
                n5 = n3;
                if (n3 != 0) {
                    n5 = 0;
                    n4 = 0;
                }
                ++n4;
                n3 = n5;
                continue;
            }
            n5 = n2;
            if (n4 != 0) {
                n5 = n2;
                if (n4 < n2) {
                    n5 = n4;
                }
            }
            n3 = 1;
            n2 = n5;
        }
        return n2;
    }

    public static String getVariant(String string) {
        return new LocaleIDParser(string).getVariant();
    }

    private static boolean isEmptyString(String string) {
        boolean bl = string == null || string.length() == 0;
        return bl;
    }

    private static String lookupLikelySubtags(String string) {
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "likelySubtags");
        try {
            string = uResourceBundle.getString(string);
            return string;
        }
        catch (MissingResourceException missingResourceException) {
            return null;
        }
    }

    private static String lscvToID(String string, String string2, String string3, String string4) {
        StringBuilder stringBuilder = new StringBuilder();
        if (string != null && string.length() > 0) {
            stringBuilder.append(string);
        }
        if (string2 != null && string2.length() > 0) {
            stringBuilder.append('_');
            stringBuilder.append(string2);
        }
        if (string3 != null && string3.length() > 0) {
            stringBuilder.append('_');
            stringBuilder.append(string3);
        }
        if (string4 != null && string4.length() > 0) {
            if (string3 == null || string3.length() == 0) {
                stringBuilder.append('_');
            }
            stringBuilder.append('_');
            stringBuilder.append(string4);
        }
        return stringBuilder.toString();
    }

    public static ULocale minimizeSubtags(ULocale uLocale) {
        return ULocale.minimizeSubtags(uLocale, Minimize.FAVOR_REGION);
    }

    @Deprecated
    public static ULocale minimizeSubtags(ULocale uLocale, Minimize minimize) {
        String string;
        Object object = new String[3];
        int n = ULocale.parseTagString(uLocale.localeID, object);
        String string2 = object[0];
        String string3 = object[1];
        String string4 = object[2];
        object = null;
        if (n < uLocale.localeID.length()) {
            object = uLocale.localeID.substring(n);
        }
        if (ULocale.isEmptyString(string = ULocale.createLikelySubtagsString(string2, string3, string4, null))) {
            return uLocale;
        }
        if (ULocale.createLikelySubtagsString(string2, null, null, null).equals(string)) {
            return new ULocale(ULocale.createTagString(string2, null, null, (String)object));
        }
        if (minimize == Minimize.FAVOR_REGION) {
            if (string4.length() != 0 && ULocale.createLikelySubtagsString(string2, null, string4, null).equals(string)) {
                return new ULocale(ULocale.createTagString(string2, null, string4, (String)object));
            }
            if (string3.length() != 0 && ULocale.createLikelySubtagsString(string2, string3, null, null).equals(string)) {
                return new ULocale(ULocale.createTagString(string2, string3, null, (String)object));
            }
        } else {
            if (string3.length() != 0 && ULocale.createLikelySubtagsString(string2, string3, null, null).equals(string)) {
                return new ULocale(ULocale.createTagString(string2, string3, null, (String)object));
            }
            if (string4.length() != 0 && ULocale.createLikelySubtagsString(string2, null, string4, null).equals(string)) {
                return new ULocale(ULocale.createTagString(string2, null, string4, (String)object));
            }
        }
        return uLocale;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static ULocale[] parseAcceptLanguage(String var0, boolean var1_2) throws ParseException {
        var2_3 = new TreeMap<1ULocaleAcceptLanguageQ, ULocale>();
        var3_4 = new StringBuilder();
        var4_5 = new StringBuilder();
        var5_6 = 0;
        var6_7 = new StringBuilder();
        var6_7.append(var0);
        var6_7.append(",");
        var6_7 = var6_7.toString();
        var7_8 = 0;
        var8_9 = false;
        var9_10 = 0;
        do {
            block81 : {
                if (var9_10 >= var6_7.length()) {
                    if (var5_6 != 0) throw new ParseException("Invalid AcceptlLanguage", var9_10);
                    return var2_3.values().toArray(new ULocale[var2_3.size()]);
                }
                var10_11 = 0;
                var11_12 = var6_7.charAt(var9_10);
                switch (var5_6) {
                    default: {
                        break;
                    }
                    case 10: {
                        if (var11_12 == ',') {
                            var12_13 = 1;
                            var10_11 = var7_8;
                            var7_8 = var5_6;
                            var5_6 = var10_11;
                            var10_11 = var12_13;
                        } else {
                            if (var11_12 == ' ' || var11_12 == '\t') break;
                            var5_6 = var7_8;
                            var7_8 = -1;
                        }
                        break block81;
                    }
                    case 9: {
                        if ('0' > var11_12 || var11_12 > '9') ** GOTO lbl44
                        if (var8_9 && var11_12 != '0') {
                            var5_6 = var7_8;
                            var7_8 = -1;
                        } else {
                            var4_5.append(var11_12);
                            break;
lbl44: // 1 sources:
                            if (var11_12 == ',') {
                                var12_13 = 1;
                                var10_11 = var7_8;
                                var7_8 = var5_6;
                                var5_6 = var10_11;
                                var10_11 = var12_13;
                            } else if (var11_12 != ' ' && var11_12 != '\t') {
                                var5_6 = var7_8;
                                var7_8 = -1;
                            } else {
                                var5_6 = var7_8;
                                var7_8 = 10;
                            }
                        }
                        break block81;
                    }
                    case 8: {
                        if ('0' <= var11_12 && var11_12 <= '9') {
                            if (var8_9 && var11_12 != '0' && !var1_2) {
                                var5_6 = var7_8;
                                var7_8 = -1;
                            } else {
                                var4_5.append(var11_12);
                                var5_6 = var7_8;
                                var7_8 = 9;
                            }
                        } else {
                            var5_6 = var7_8;
                            var7_8 = -1;
                        }
                        break block81;
                    }
                    case 7: {
                        if (var11_12 == '.') {
                            var4_5.append(var11_12);
                            var5_6 = var7_8;
                            var7_8 = 8;
                        } else if (var11_12 == ',') {
                            var12_13 = 1;
                            var10_11 = var7_8;
                            var7_8 = var5_6;
                            var5_6 = var10_11;
                            var10_11 = var12_13;
                        } else if (var11_12 != ' ' && var11_12 != '\t') {
                            var5_6 = var7_8;
                            var7_8 = -1;
                        } else {
                            var5_6 = var7_8;
                            var7_8 = 10;
                        }
                        break block81;
                    }
                    case 6: {
                        if (var11_12 == '0') {
                            var4_5.append(var11_12);
                            var8_9 = false;
                            var5_6 = var7_8;
                            var7_8 = 7;
                        } else if (var11_12 == '1') {
                            var4_5.append(var11_12);
                            var5_6 = var7_8;
                            var7_8 = 7;
                        } else if (var11_12 == '.') {
                            if (var1_2) {
                                var4_5.append(var11_12);
                                var5_6 = var7_8;
                                var7_8 = 8;
                            } else {
                                var5_6 = var7_8;
                                var7_8 = -1;
                            }
                        } else {
                            if (var11_12 == ' ' || var11_12 == '\t') break;
                            var5_6 = var7_8;
                            var7_8 = -1;
                        }
                        break block81;
                    }
                    case 5: {
                        if (var11_12 == '=') {
                            var5_6 = var7_8;
                            var7_8 = 6;
                        } else {
                            if (var11_12 == ' ' || var11_12 == '\t') break;
                            var5_6 = var7_8;
                            var7_8 = -1;
                        }
                        break block81;
                    }
                    case 4: {
                        if (var11_12 == 'q') {
                            var5_6 = var7_8;
                            var7_8 = 5;
                        } else {
                            if (var11_12 == ' ' || var11_12 == '\t') break;
                            var5_6 = var7_8;
                            var7_8 = -1;
                        }
                        break block81;
                    }
                    case 3: {
                        if (var11_12 == ',') {
                            var12_13 = 1;
                            var10_11 = var7_8;
                            var7_8 = var5_6;
                            var5_6 = var10_11;
                            var10_11 = var12_13;
                        } else if (var11_12 == ';') {
                            var5_6 = var7_8;
                            var7_8 = 4;
                        } else {
                            if (var11_12 == ' ' || var11_12 == '\t') break;
                            var5_6 = var7_8;
                            var7_8 = -1;
                        }
                        break block81;
                    }
                    case 2: {
                        if (var11_12 == ',') {
                            var12_13 = 1;
                            var10_11 = var7_8;
                            var7_8 = var5_6;
                            var5_6 = var10_11;
                            var10_11 = var12_13;
                        } else if (var11_12 != ' ' && var11_12 != '\t') {
                            if (var11_12 == ';') {
                                var5_6 = var7_8;
                                var7_8 = 4;
                            } else {
                                var5_6 = var7_8;
                                var7_8 = -1;
                            }
                        } else {
                            var5_6 = var7_8;
                            var7_8 = 3;
                        }
                        break block81;
                    }
                    case 1: {
                        if ('A' <= var11_12 && var11_12 <= 'Z' || 'a' <= var11_12 && var11_12 <= 'z') {
                            var3_4.append(var11_12);
                            break;
                        }
                        if (var11_12 == '-') {
                            var3_4.append(var11_12);
                            var12_13 = 1;
                            var7_8 = var5_6;
                            var5_6 = var12_13;
                        } else if (var11_12 == '_') {
                            if (var1_2) {
                                var3_4.append(var11_12);
                                var12_13 = 1;
                                var7_8 = var5_6;
                                var5_6 = var12_13;
                            } else {
                                var5_6 = var7_8;
                                var7_8 = -1;
                            }
                        } else if ('0' <= var11_12 && var11_12 <= '9') {
                            if (var7_8 != 0) {
                                var3_4.append(var11_12);
                                break;
                            }
                            var5_6 = var7_8;
                            var7_8 = -1;
                        } else if (var11_12 == ',') {
                            var12_13 = 1;
                            var10_11 = var7_8;
                            var7_8 = var5_6;
                            var5_6 = var10_11;
                            var10_11 = var12_13;
                        } else if (var11_12 != ' ' && var11_12 != '\t') {
                            if (var11_12 == ';') {
                                var5_6 = var7_8;
                                var7_8 = 4;
                            } else {
                                var5_6 = var7_8;
                                var7_8 = -1;
                            }
                        } else {
                            var5_6 = var7_8;
                            var7_8 = 3;
                        }
                        break block81;
                    }
                    case 0: {
                        if ('A' <= var11_12 && var11_12 <= 'Z' || 'a' <= var11_12 && var11_12 <= 'z') {
                            var3_4.append(var11_12);
                            var5_6 = 0;
                            var7_8 = 1;
                        } else if (var11_12 == '*') {
                            var3_4.append(var11_12);
                            var5_6 = var7_8;
                            var7_8 = 2;
                        } else {
                            if (var11_12 == ' ' || var11_12 == '\t') break;
                            var5_6 = var7_8;
                            var7_8 = -1;
                        }
                        break block81;
                    }
                }
                var12_13 = var7_8;
                var7_8 = var5_6;
                var5_6 = var12_13;
            }
            if (var7_8 == -1) throw new ParseException("Invalid Accept-Language", var9_10);
            if (var10_11 != 0) {
                var13_14 = 1.0;
                if (var4_5.length() != 0) {
                    try {
                        var15_15 = Double.parseDouble(var4_5.toString());
                    }
                    catch (NumberFormatException var0_1) {
                        var15_15 = 1.0;
                    }
                    var13_14 = var15_15;
                    if (var15_15 > 1.0) {
                        var13_14 = 1.0;
                    }
                }
                if (var3_4.charAt(0) != '*') {
                    var2_3.put(new 1ULocaleAcceptLanguageQ(var13_14, var2_3.size()), new ULocale(ULocale.canonicalize(var3_4.toString())));
                }
                var3_4.setLength(0);
                var4_5.setLength(0);
                var7_8 = 0;
            }
            ++var9_10;
            var10_11 = var5_6;
            var5_6 = var7_8;
            var7_8 = var10_11;
        } while (true);
    }

    private static int parseTagString(String string, String[] object) {
        int n;
        block2 : {
            LocaleIDParser localeIDParser = new LocaleIDParser(string);
            String string2 = localeIDParser.getLanguage();
            String string3 = localeIDParser.getScript();
            String string4 = localeIDParser.getCountry();
            object[0] = ULocale.isEmptyString(string2) ? UNDEFINED_LANGUAGE : string2;
            object[1] = string3.equals(UNDEFINED_SCRIPT) ? EMPTY_STRING : string3;
            object[2] = string4.equals(UNDEFINED_REGION) ? EMPTY_STRING : string4;
            object = localeIDParser.getVariant();
            if (!ULocale.isEmptyString((String)object)) {
                int n2 = string.indexOf((String)object);
                if (n2 > 0) {
                    --n2;
                }
                return n2;
            }
            n = string.indexOf(64);
            if (n != -1) break block2;
            n = string.length();
        }
        return n;
    }

    public static void setDefault(Category category, ULocale uLocale) {
        synchronized (ULocale.class) {
            Locale locale = uLocale.toLocale();
            int n = category.ordinal();
            ULocale.defaultCategoryULocales[n] = uLocale;
            ULocale.defaultCategoryLocales[n] = locale;
            JDKLocaleHelper.setDefault(category, locale);
            return;
        }
    }

    public static void setDefault(ULocale uLocale) {
        synchronized (ULocale.class) {
            defaultLocale = uLocale.toLocale();
            Locale.setDefault(defaultLocale);
            defaultULocale = uLocale;
            Category[] arrcategory = Category.values();
            int n = arrcategory.length;
            for (int i = 0; i < n; ++i) {
                ULocale.setDefault(arrcategory[i], uLocale);
            }
            return;
        }
    }

    public static String setKeywordValue(String object, String string, String string2) {
        object = new LocaleIDParser((String)object);
        ((LocaleIDParser)object).setKeywordValue(string, string2);
        return ((LocaleIDParser)object).getName();
    }

    public static String toLegacyKey(String string) {
        String string2;
        String string3 = string2 = KeyTypeData.toLegacyKey(string);
        if (string2 == null) {
            string3 = string2;
            if (string.matches("[0-9a-zA-Z]+")) {
                string3 = AsciiUtil.toLowerString(string);
            }
        }
        return string3;
    }

    public static String toLegacyType(String string, String string2) {
        String string3;
        string = string3 = KeyTypeData.toLegacyType(string, string2, null, null);
        if (string3 == null) {
            string = string3;
            if (string2.matches("[0-9a-zA-Z]+([_/\\-][0-9a-zA-Z]+)*")) {
                string = AsciiUtil.toLowerString(string2);
            }
        }
        return string;
    }

    public static String toUnicodeLocaleKey(String string) {
        String string2;
        String string3 = string2 = KeyTypeData.toBcpKey(string);
        if (string2 == null) {
            string3 = string2;
            if (UnicodeLocaleExtension.isKey(string)) {
                string3 = AsciiUtil.toLowerString(string);
            }
        }
        return string3;
    }

    public static String toUnicodeLocaleType(String string, String string2) {
        String string3;
        string = string3 = KeyTypeData.toBcpType(string, string2, null, null);
        if (string3 == null) {
            string = string3;
            if (UnicodeLocaleExtension.isType(string2)) {
                string = AsciiUtil.toLowerString(string2);
            }
        }
        return string;
    }

    public Object clone() {
        return this;
    }

    @Override
    public int compareTo(ULocale uLocale) {
        int n;
        int n2 = 0;
        if (this == uLocale) {
            return 0;
        }
        int n3 = n = this.getLanguage().compareTo(uLocale.getLanguage());
        if (n == 0) {
            n3 = n = this.getScript().compareTo(uLocale.getScript());
            if (n == 0) {
                n3 = n = this.getCountry().compareTo(uLocale.getCountry());
                if (n == 0) {
                    n3 = n = this.getVariant().compareTo(uLocale.getVariant());
                    if (n == 0) {
                        Iterator<String> iterator = this.getKeywords();
                        Iterator<String> iterator2 = uLocale.getKeywords();
                        if (iterator == null) {
                            n3 = iterator2 == null ? 0 : -1;
                        } else {
                            n3 = n;
                            if (iterator2 == null) {
                                n3 = 1;
                            } else {
                                do {
                                    n = n3;
                                    if (n3 != 0) break;
                                    n = n3;
                                    if (!iterator.hasNext()) break;
                                    if (!iterator2.hasNext()) {
                                        n = 1;
                                        break;
                                    }
                                    String string = iterator.next();
                                    String string2 = iterator2.next();
                                    n3 = n = string.compareTo(string2);
                                    if (n != 0) continue;
                                    string = this.getKeywordValue(string);
                                    string2 = uLocale.getKeywordValue(string2);
                                    if (string == null) {
                                        if (string2 == null) {
                                            n3 = 0;
                                            continue;
                                        }
                                        n3 = -1;
                                        continue;
                                    }
                                    if (string2 == null) {
                                        n3 = 1;
                                        continue;
                                    }
                                    n3 = string.compareTo(string2);
                                } while (true);
                                n3 = n;
                                if (n == 0) {
                                    n3 = n;
                                    if (iterator2.hasNext()) {
                                        n3 = -1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (n3 < 0) {
            n = -1;
        } else {
            n = n2;
            if (n3 > 0) {
                n = 1;
            }
        }
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof ULocale) {
            return this.localeID.equals(((ULocale)object).localeID);
        }
        return false;
    }

    public String getBaseName() {
        return ULocale.getBaseName(this.localeID);
    }

    public String getCharacterOrientation() {
        return ICUResourceTableAccess.getTableString("android/icu/impl/data/icudt63b", this, "layout", "characters", "characters");
    }

    public String getCountry() {
        return this.base().getRegion();
    }

    public String getDisplayCountry() {
        return ULocale.getDisplayCountryInternal(this, ULocale.getDefault(Category.DISPLAY));
    }

    public String getDisplayCountry(ULocale uLocale) {
        return ULocale.getDisplayCountryInternal(this, uLocale);
    }

    public String getDisplayKeywordValue(String string) {
        return ULocale.getDisplayKeywordValueInternal(this, string, ULocale.getDefault(Category.DISPLAY));
    }

    public String getDisplayKeywordValue(String string, ULocale uLocale) {
        return ULocale.getDisplayKeywordValueInternal(this, string, uLocale);
    }

    public String getDisplayLanguage() {
        return ULocale.getDisplayLanguageInternal(this, ULocale.getDefault(Category.DISPLAY), false);
    }

    public String getDisplayLanguage(ULocale uLocale) {
        return ULocale.getDisplayLanguageInternal(this, uLocale, false);
    }

    public String getDisplayLanguageWithDialect() {
        return ULocale.getDisplayLanguageInternal(this, ULocale.getDefault(Category.DISPLAY), true);
    }

    public String getDisplayLanguageWithDialect(ULocale uLocale) {
        return ULocale.getDisplayLanguageInternal(this, uLocale, true);
    }

    public String getDisplayName() {
        return ULocale.getDisplayNameInternal(this, ULocale.getDefault(Category.DISPLAY));
    }

    public String getDisplayName(ULocale uLocale) {
        return ULocale.getDisplayNameInternal(this, uLocale);
    }

    public String getDisplayNameWithDialect() {
        return ULocale.getDisplayNameWithDialectInternal(this, ULocale.getDefault(Category.DISPLAY));
    }

    public String getDisplayNameWithDialect(ULocale uLocale) {
        return ULocale.getDisplayNameWithDialectInternal(this, uLocale);
    }

    public String getDisplayScript() {
        return ULocale.getDisplayScriptInternal(this, ULocale.getDefault(Category.DISPLAY));
    }

    public String getDisplayScript(ULocale uLocale) {
        return ULocale.getDisplayScriptInternal(this, uLocale);
    }

    @Deprecated
    public String getDisplayScriptInContext() {
        return ULocale.getDisplayScriptInContextInternal(this, ULocale.getDefault(Category.DISPLAY));
    }

    @Deprecated
    public String getDisplayScriptInContext(ULocale uLocale) {
        return ULocale.getDisplayScriptInContextInternal(this, uLocale);
    }

    public String getDisplayVariant() {
        return ULocale.getDisplayVariantInternal(this, ULocale.getDefault(Category.DISPLAY));
    }

    public String getDisplayVariant(ULocale uLocale) {
        return ULocale.getDisplayVariantInternal(this, uLocale);
    }

    public String getExtension(char c) {
        if (LocaleExtensions.isValidKey(c)) {
            return this.extensions().getExtensionValue(Character.valueOf(c));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid extension key: ");
        stringBuilder.append(c);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Set<Character> getExtensionKeys() {
        return this.extensions().getKeys();
    }

    public ULocale getFallback() {
        if (this.localeID.length() != 0 && this.localeID.charAt(0) != '@') {
            return new ULocale(ULocale.getFallbackString(this.localeID), (Locale)null);
        }
        return null;
    }

    public String getISO3Country() {
        return ULocale.getISO3Country(this.localeID);
    }

    public String getISO3Language() {
        return ULocale.getISO3Language(this.localeID);
    }

    public String getKeywordValue(String string) {
        return ULocale.getKeywordValue(this.localeID, string);
    }

    public Iterator<String> getKeywords() {
        return ULocale.getKeywords(this.localeID);
    }

    public String getLanguage() {
        return this.base().getLanguage();
    }

    public String getLineOrientation() {
        return ICUResourceTableAccess.getTableString("android/icu/impl/data/icudt63b", this, "layout", "lines", "lines");
    }

    public String getName() {
        return this.localeID;
    }

    public String getScript() {
        return this.base().getScript();
    }

    public Set<String> getUnicodeLocaleAttributes() {
        return this.extensions().getUnicodeLocaleAttributes();
    }

    public Set<String> getUnicodeLocaleKeys() {
        return this.extensions().getUnicodeLocaleKeys();
    }

    public String getUnicodeLocaleType(String string) {
        if (LocaleExtensions.isValidUnicodeLocaleKey(string)) {
            return this.extensions().getUnicodeLocaleType(string);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid Unicode locale key: ");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public String getVariant() {
        return this.base().getVariant();
    }

    public int hashCode() {
        return this.localeID.hashCode();
    }

    public boolean isRightToLeft() {
        String string;
        String string2 = string = this.getScript();
        if (string.length() == 0) {
            string2 = this.getLanguage();
            if (string2.length() == 0) {
                return false;
            }
            int n = LANG_DIR_STRING.indexOf(string2);
            if (n >= 0) {
                n = LANG_DIR_STRING.charAt(string2.length() + n);
                if (n != 43) {
                    if (n == 45) {
                        return false;
                    }
                } else {
                    return true;
                }
            }
            string2 = string = ULocale.addLikelySubtags(this).getScript();
            if (string.length() == 0) {
                return false;
            }
        }
        return UScript.isRightToLeft(UScript.getCodeFromName(string2));
    }

    public ULocale setKeywordValue(String string, String string2) {
        return new ULocale(ULocale.setKeywordValue(this.localeID, string, string2), (Locale)null);
    }

    public String toLanguageTag() {
        String string;
        String string2;
        BaseLocale object5 = this.base();
        LocaleExtensions localeExtensions = this.extensions();
        Object object = object5;
        Object object2 = localeExtensions;
        if (object5.getVariant().equalsIgnoreCase("POSIX")) {
            BaseLocale baseLocale = BaseLocale.getInstance(object5.getLanguage(), object5.getScript(), object5.getRegion(), EMPTY_STRING);
            object = baseLocale;
            object2 = localeExtensions;
            if (localeExtensions.getUnicodeLocaleType("va") == null) {
                object = new InternalLocaleBuilder();
                try {
                    ((InternalLocaleBuilder)object).setLocale(BaseLocale.ROOT, localeExtensions);
                    ((InternalLocaleBuilder)object).setUnicodeLocaleKeyword("va", "posix");
                    object2 = ((InternalLocaleBuilder)object).getLocaleExtensions();
                    object = baseLocale;
                }
                catch (LocaleSyntaxException localeSyntaxException) {
                    throw new RuntimeException(localeSyntaxException);
                }
            }
        }
        object2 = LanguageTag.parseLocale((BaseLocale)object, (LocaleExtensions)object2);
        object = new StringBuilder();
        String string3 = ((LanguageTag)object2).getLanguage();
        if (string3.length() > 0) {
            ((StringBuilder)object).append(LanguageTag.canonicalizeLanguage(string3));
        }
        if ((string2 = ((LanguageTag)object2).getScript()).length() > 0) {
            ((StringBuilder)object).append("-");
            ((StringBuilder)object).append(LanguageTag.canonicalizeScript(string2));
        }
        if ((string = ((LanguageTag)object2).getRegion()).length() > 0) {
            ((StringBuilder)object).append("-");
            ((StringBuilder)object).append(LanguageTag.canonicalizeRegion(string));
        }
        for (String string4 : ((LanguageTag)object2).getVariants()) {
            ((StringBuilder)object).append("-");
            ((StringBuilder)object).append(LanguageTag.canonicalizeVariant(string4));
        }
        for (String string5 : ((LanguageTag)object2).getExtensions()) {
            ((StringBuilder)object).append("-");
            ((StringBuilder)object).append(LanguageTag.canonicalizeExtension(string5));
        }
        if (((String)(object2 = ((LanguageTag)object2).getPrivateuse())).length() > 0) {
            if (((StringBuilder)object).length() > 0) {
                ((StringBuilder)object).append("-");
            }
            ((StringBuilder)object).append("x");
            ((StringBuilder)object).append("-");
            ((StringBuilder)object).append(LanguageTag.canonicalizePrivateuse((String)object2));
        }
        return ((StringBuilder)object).toString();
    }

    public Locale toLocale() {
        if (this.locale == null) {
            this.locale = JDKLocaleHelper.toLocale(this);
        }
        return this.locale;
    }

    public String toString() {
        return this.localeID;
    }

    class 1ULocaleAcceptLanguageQ
    implements Comparable<1ULocaleAcceptLanguageQ> {
        private double q;
        private double serial;

        public 1ULocaleAcceptLanguageQ(double d, int n) {
            this.q = d;
            this.serial = n;
        }

        @Override
        public int compareTo(1ULocaleAcceptLanguageQ uLocaleAcceptLanguageQ) {
            double d = this.q;
            double d2 = uLocaleAcceptLanguageQ.q;
            if (d > d2) {
                return -1;
            }
            if (d < d2) {
                return 1;
            }
            d = this.serial;
            d2 = uLocaleAcceptLanguageQ.serial;
            if (d < d2) {
                return -1;
            }
            return d > d2;
        }
    }

    public static final class Builder {
        private final InternalLocaleBuilder _locbld = new InternalLocaleBuilder();

        public Builder addUnicodeLocaleAttribute(String string) {
            try {
                this._locbld.addUnicodeLocaleAttribute(string);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public ULocale build() {
            return ULocale.getInstance(this._locbld.getBaseLocale(), this._locbld.getLocaleExtensions());
        }

        public Builder clear() {
            this._locbld.clear();
            return this;
        }

        public Builder clearExtensions() {
            this._locbld.clearExtensions();
            return this;
        }

        public Builder removeUnicodeLocaleAttribute(String string) {
            try {
                this._locbld.removeUnicodeLocaleAttribute(string);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Builder setExtension(char c, String string) {
            try {
                this._locbld.setExtension(c, string);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Builder setLanguage(String string) {
            try {
                this._locbld.setLanguage(string);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Builder setLanguageTag(String object) {
            ParseStatus parseStatus = new ParseStatus();
            object = LanguageTag.parse((String)object, parseStatus);
            if (!parseStatus.isError()) {
                this._locbld.setLanguageTag((LanguageTag)object);
                return this;
            }
            throw new IllformedLocaleException(parseStatus.getErrorMessage(), parseStatus.getErrorIndex());
        }

        public Builder setLocale(ULocale uLocale) {
            try {
                this._locbld.setLocale(uLocale.base(), uLocale.extensions());
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Builder setRegion(String string) {
            try {
                this._locbld.setRegion(string);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Builder setScript(String string) {
            try {
                this._locbld.setScript(string);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Builder setUnicodeLocaleKeyword(String string, String string2) {
            try {
                this._locbld.setUnicodeLocaleKeyword(string, string2);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Builder setVariant(String string) {
            try {
                this._locbld.setVariant(string);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }
    }

    public static enum Category {
        DISPLAY,
        FORMAT;
        
    }

    private static final class JDKLocaleHelper {
        private static Object eDISPLAY;
        private static Object eFORMAT;
        private static boolean hasLocaleCategories;
        private static Method mGetDefault;
        private static Method mSetDefault;

        static {
            block14 : {
                Object object;
                int n = 0;
                hasLocaleCategories = false;
                GenericDeclaration genericDeclaration = null;
                Object object2 = Locale.class.getDeclaredClasses();
                int n2 = ((Class<?>[])object2).length;
                int n3 = 0;
                do {
                    object = genericDeclaration;
                    if (n3 >= n2) break;
                    object = object2[n3];
                    if (((Class)object).getName().equals("java.util.Locale$Category")) break;
                    ++n3;
                    continue;
                    break;
                } while (true);
                if (object == null) break block14;
                mGetDefault = Locale.class.getDeclaredMethod("getDefault", new Class[]{object});
                mSetDefault = Locale.class.getDeclaredMethod("setDefault", new Class[]{object, Locale.class});
                genericDeclaration = ((Class)object).getMethod("name", null);
                ?[] arrobj = ((Class)object).getEnumConstants();
                n2 = arrobj.length;
                for (n3 = n; n3 < n2; ++n3) {
                    object2 = arrobj[n3];
                    object = (String)((Method)genericDeclaration).invoke(object2, null);
                    if (((String)object).equals("DISPLAY")) {
                        eDISPLAY = object2;
                        continue;
                    }
                    if (!((String)object).equals("FORMAT")) continue;
                    eFORMAT = object2;
                    continue;
                }
                try {
                    if (eDISPLAY != null && eFORMAT != null) {
                        hasLocaleCategories = true;
                    }
                }
                catch (SecurityException securityException) {
                }
                catch (InvocationTargetException invocationTargetException) {
                }
                catch (IllegalAccessException illegalAccessException) {
                }
                catch (IllegalArgumentException illegalArgumentException) {
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    // empty catch block
                }
            }
        }

        private JDKLocaleHelper() {
        }

        public static Locale getDefault(Category object) {
            if (hasLocaleCategories) {
                Object var1_4 = null;
                int n = 3.$SwitchMap$android$icu$util$ULocale$Category[object.ordinal()];
                object = n != 1 ? (n != 2 ? var1_4 : eFORMAT) : eDISPLAY;
                if (object != null) {
                    try {
                        object = (Locale)mGetDefault.invoke(null, object);
                        return object;
                    }
                    catch (IllegalAccessException illegalAccessException) {
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                    }
                    catch (InvocationTargetException invocationTargetException) {
                        // empty catch block
                    }
                }
            }
            return Locale.getDefault();
        }

        public static boolean hasLocaleCategories() {
            return hasLocaleCategories;
        }

        public static void setDefault(Category object, Locale locale) {
            if (hasLocaleCategories) {
                Object var2_5 = null;
                int n = 3.$SwitchMap$android$icu$util$ULocale$Category[object.ordinal()];
                object = n != 1 ? (n != 2 ? var2_5 : eFORMAT) : eDISPLAY;
                if (object != null) {
                    try {
                        mSetDefault.invoke(null, object, locale);
                    }
                    catch (IllegalAccessException illegalAccessException) {
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                    }
                    catch (InvocationTargetException invocationTargetException) {
                        // empty catch block
                    }
                }
            }
        }

        public static Locale toLocale(ULocale uLocale) {
            Locale locale = null;
            Object object = uLocale.getName();
            if (uLocale.getScript().length() > 0 || ((String)object).contains("@")) {
                locale = Locale.forLanguageTag(AsciiUtil.toUpperString(uLocale.toLanguageTag()));
            }
            object = locale;
            if (locale == null) {
                object = new Locale(uLocale.getLanguage(), uLocale.getCountry(), uLocale.getVariant());
            }
            return object;
        }

        /*
         * WARNING - void declaration
         */
        public static ULocale toULocale(Locale locale) {
            void var7_16;
            void var7_22;
            String string = locale.getLanguage();
            String string2 = locale.getCountry();
            Object object = locale.getVariant();
            Object object2 = null;
            TreeMap<String, Object> treeMap = null;
            Object object3 = null;
            Object object42 = null;
            String string3 = locale.getScript();
            Object object4 = locale.getExtensionKeys();
            Object object5 = object;
            if (!object4.isEmpty()) {
                object4 = object4.iterator();
                do {
                    void var7_8;
                    object5 = object;
                    object2 = treeMap;
                    object3 = var7_8;
                    if (!object4.hasNext()) break;
                    Character c = (Character)object4.next();
                    if (c.charValue() == 'u') {
                        void var7_9;
                        object5 = locale.getUnicodeLocaleAttributes();
                        object3 = treeMap;
                        if (!object5.isEmpty()) {
                            treeMap = new TreeSet();
                            object5 = object5.iterator();
                            do {
                                object3 = treeMap;
                                if (!object5.hasNext()) break;
                                treeMap.add((String)object5.next());
                            } while (true);
                        }
                        for (String string4 : locale.getUnicodeLocaleKeys()) {
                            object5 = locale.getUnicodeLocaleType(string4);
                            if (object5 == null) continue;
                            if (string4.equals("va")) {
                                if (((String)object).length() == 0) {
                                    object = object5;
                                    continue;
                                }
                                treeMap = new StringBuilder();
                                ((StringBuilder)((Object)treeMap)).append((String)object5);
                                ((StringBuilder)((Object)treeMap)).append("_");
                                ((StringBuilder)((Object)treeMap)).append((String)object);
                                object = ((StringBuilder)((Object)treeMap)).toString();
                                continue;
                            }
                            treeMap = var7_9;
                            if (var7_9 == null) {
                                treeMap = new TreeMap<String, Object>();
                            }
                            treeMap.put(string4, object5);
                            TreeMap<String, Object> treeMap2 = treeMap;
                        }
                        object5 = object;
                        object2 = object3;
                        object3 = var7_9;
                    } else {
                        String string4;
                        string4 = locale.getExtension(c.charValue());
                        object5 = object;
                        object2 = treeMap;
                        object3 = var7_8;
                        if (string4 != null) {
                            object3 = var7_8;
                            if (var7_8 == null) {
                                object3 = new TreeMap();
                            }
                            object3.put(String.valueOf(c), string4);
                            object2 = treeMap;
                            object5 = object;
                        }
                    }
                    object = object5;
                    treeMap = object2;
                    TreeSet treeSet = object3;
                } while (true);
            }
            object = string;
            String string5 = object5;
            if (string.equals("no")) {
                object = string;
                String string6 = object5;
                if (string2.equals("NO")) {
                    object = string;
                    CharSequence charSequence = object5;
                    if (((String)object5).equals("NY")) {
                        object = "nn";
                        String string7 = ULocale.EMPTY_STRING;
                    }
                }
            }
            object5 = new StringBuilder((String)object);
            if (string3.length() > 0) {
                ((StringBuilder)object5).append('_');
                ((StringBuilder)object5).append(string3);
            }
            if (string2.length() > 0) {
                ((StringBuilder)object5).append('_');
                ((StringBuilder)object5).append(string2);
            }
            if (var7_16.length() > 0) {
                if (string2.length() == 0) {
                    ((StringBuilder)object5).append('_');
                }
                ((StringBuilder)object5).append('_');
                ((StringBuilder)object5).append((String)var7_16);
            }
            TreeSet treeSet = object3;
            if (object2 != null) {
                void var7_21;
                object = new StringBuilder();
                Iterator iterator = object2.iterator();
                while (iterator.hasNext()) {
                    treeMap = (String)iterator.next();
                    if (((StringBuilder)object).length() != 0) {
                        ((StringBuilder)object).append('-');
                    }
                    ((StringBuilder)object).append((String)((Object)treeMap));
                }
                TreeSet treeSet2 = object3;
                if (object3 == null) {
                    TreeMap treeMap3 = new TreeMap();
                }
                var7_21.put(ULocale.LOCALE_ATTRIBUTE_KEY, ((StringBuilder)object).toString());
            }
            if (var7_22 != null) {
                ((StringBuilder)object5).append('@');
                boolean bl = false;
                for (Map.Entry entry : var7_22.entrySet()) {
                    void var7_27;
                    treeMap = (String)entry.getKey();
                    object = (String)entry.getValue();
                    object3 = treeMap;
                    Object object6 = object;
                    if (((String)((Object)treeMap)).length() != 1) {
                        object3 = ULocale.toLegacyKey(treeMap);
                        if (((String)object).length() == 0) {
                            object = "yes";
                        }
                        String string8 = ULocale.toLegacyType((String)object3, (String)object);
                    }
                    if (bl) {
                        ((StringBuilder)object5).append(';');
                    } else {
                        bl = true;
                    }
                    ((StringBuilder)object5).append((String)object3);
                    ((StringBuilder)object5).append('=');
                    ((StringBuilder)object5).append((String)var7_27);
                }
            }
            return new ULocale(ULocale.getName(((StringBuilder)object5).toString()), locale);
        }
    }

    @Deprecated
    public static enum Minimize {
        FAVOR_SCRIPT,
        FAVOR_REGION;
        
    }

    public static final class Type {
        private Type() {
        }
    }

}

