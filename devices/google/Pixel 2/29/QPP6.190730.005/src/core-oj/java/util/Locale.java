/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.ICU
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.security.Permission;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyPermission;
import java.util.Set;
import libcore.icu.ICU;
import sun.util.locale.BaseLocale;
import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LanguageTag;
import sun.util.locale.LocaleExtensions;
import sun.util.locale.LocaleMatcher;
import sun.util.locale.LocaleObjectCache;
import sun.util.locale.LocaleSyntaxException;
import sun.util.locale.LocaleUtils;
import sun.util.locale.ParseStatus;

public final class Locale
implements Cloneable,
Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Locale CANADA;
    public static final Locale CANADA_FRENCH;
    public static final Locale CHINA;
    public static final Locale CHINESE;
    private static final int DISPLAY_COUNTRY = 1;
    private static final int DISPLAY_LANGUAGE = 0;
    private static final int DISPLAY_SCRIPT = 3;
    private static final int DISPLAY_VARIANT = 2;
    public static final Locale ENGLISH;
    public static final Locale FRANCE;
    public static final Locale FRENCH;
    public static final Locale GERMAN;
    public static final Locale GERMANY;
    public static final Locale ITALIAN;
    public static final Locale ITALY;
    public static final Locale JAPAN;
    public static final Locale JAPANESE;
    public static final Locale KOREA;
    public static final Locale KOREAN;
    private static final Cache LOCALECACHE;
    public static final Locale PRC;
    public static final char PRIVATE_USE_EXTENSION = 'x';
    public static final Locale ROOT;
    public static final Locale SIMPLIFIED_CHINESE;
    public static final Locale TAIWAN;
    public static final Locale TRADITIONAL_CHINESE;
    public static final Locale UK;
    private static final String UNDETERMINED_LANGUAGE = "und";
    public static final char UNICODE_LOCALE_EXTENSION = 'u';
    public static final Locale US;
    private static volatile Locale defaultDisplayLocale;
    private static volatile Locale defaultFormatLocale;
    private static volatile String[] isoCountries;
    private static volatile String[] isoLanguages;
    private static final ObjectStreamField[] serialPersistentFields;
    static final long serialVersionUID = 9149081749638150636L;
    private transient BaseLocale baseLocale;
    private volatile transient int hashCodeValue = 0;
    private volatile transient String languageTag;
    private transient LocaleExtensions localeExtensions;

    static {
        Locale locale;
        LOCALECACHE = new Cache();
        ENGLISH = Locale.createConstant("en", "");
        FRENCH = Locale.createConstant("fr", "");
        GERMAN = Locale.createConstant("de", "");
        ITALIAN = Locale.createConstant("it", "");
        JAPANESE = Locale.createConstant("ja", "");
        KOREAN = Locale.createConstant("ko", "");
        CHINESE = Locale.createConstant("zh", "");
        SIMPLIFIED_CHINESE = Locale.createConstant("zh", "CN");
        TRADITIONAL_CHINESE = Locale.createConstant("zh", "TW");
        FRANCE = Locale.createConstant("fr", "FR");
        GERMANY = Locale.createConstant("de", "DE");
        ITALY = Locale.createConstant("it", "IT");
        JAPAN = Locale.createConstant("ja", "JP");
        KOREA = Locale.createConstant("ko", "KR");
        CHINA = locale = SIMPLIFIED_CHINESE;
        PRC = locale;
        TAIWAN = TRADITIONAL_CHINESE;
        UK = Locale.createConstant("en", "GB");
        US = Locale.createConstant("en", "US");
        CANADA = Locale.createConstant("en", "CA");
        CANADA_FRENCH = Locale.createConstant("fr", "CA");
        ROOT = Locale.createConstant("", "");
        defaultDisplayLocale = null;
        defaultFormatLocale = null;
        serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("language", String.class), new ObjectStreamField("country", String.class), new ObjectStreamField("variant", String.class), new ObjectStreamField("hashcode", Integer.TYPE), new ObjectStreamField("script", String.class), new ObjectStreamField("extensions", String.class)};
        isoLanguages = null;
        isoCountries = null;
    }

    public Locale(String string) {
        this(string, "", "");
    }

    public Locale(String string, String string2) {
        this(string, string2, "");
    }

    public Locale(String string, String string2, String string3) {
        if (string != null && string2 != null && string3 != null) {
            this.baseLocale = BaseLocale.getInstance(Locale.convertOldISOCodes(string), "", string2, string3);
            this.localeExtensions = Locale.getCompatibilityExtensions(string, "", string2, string3);
            return;
        }
        throw new NullPointerException();
    }

    private Locale(BaseLocale baseLocale, LocaleExtensions localeExtensions) {
        this.baseLocale = baseLocale;
        this.localeExtensions = localeExtensions;
    }

    public static String adjustLanguageCode(String string) {
        String string2 = string.toLowerCase(US);
        if (string.equals("he")) {
            string2 = "iw";
        } else if (string.equals("id")) {
            string2 = "in";
        } else if (string.equals("yi")) {
            string2 = "ji";
        }
        return string2;
    }

    private static String[] composeList(MessageFormat messageFormat, String[] arrstring) {
        if (arrstring.length <= 3) {
            return arrstring;
        }
        String string = messageFormat.format(new String[]{arrstring[0], arrstring[1]});
        String[] arrstring2 = new String[arrstring.length - 1];
        System.arraycopy(arrstring, 2, arrstring2, 1, arrstring2.length - 1);
        arrstring2[0] = string;
        return Locale.composeList(messageFormat, arrstring2);
    }

    private static String convertOldISOCodes(String string) {
        if ((string = LocaleUtils.toLowerString(string).intern()) == "he") {
            return "iw";
        }
        if (string == "yi") {
            return "ji";
        }
        if (string == "id") {
            return "in";
        }
        return string;
    }

    private static Locale createConstant(String string, String string2) {
        return Locale.getInstance(BaseLocale.createInstance(string, string2), null);
    }

    public static List<Locale> filter(List<LanguageRange> list, Collection<Locale> collection) {
        return Locale.filter(list, collection, FilteringMode.AUTOSELECT_FILTERING);
    }

    public static List<Locale> filter(List<LanguageRange> list, Collection<Locale> collection, FilteringMode filteringMode) {
        return LocaleMatcher.filter(list, collection, filteringMode);
    }

    public static List<String> filterTags(List<LanguageRange> list, Collection<String> collection) {
        return Locale.filterTags(list, collection, FilteringMode.AUTOSELECT_FILTERING);
    }

    public static List<String> filterTags(List<LanguageRange> list, Collection<String> collection, FilteringMode filteringMode) {
        return LocaleMatcher.filterTags(list, collection, filteringMode);
    }

    public static Locale forLanguageTag(String object) {
        Object object2 = LanguageTag.parse((String)object, null);
        object = new InternalLocaleBuilder();
        ((InternalLocaleBuilder)object).setLanguageTag((LanguageTag)object2);
        BaseLocale baseLocale = ((InternalLocaleBuilder)object).getBaseLocale();
        object = object2 = ((InternalLocaleBuilder)object).getLocaleExtensions();
        if (object2 == null) {
            object = object2;
            if (baseLocale.getVariant().length() > 0) {
                object = Locale.getCompatibilityExtensions(baseLocale.getLanguage(), baseLocale.getScript(), baseLocale.getRegion(), baseLocale.getVariant());
            }
        }
        return Locale.getInstance(baseLocale, (LocaleExtensions)object);
    }

    private static String formatList(String[] arrobject, String charSequence, String string) {
        if (charSequence != null && string != null) {
            String[] arrstring = arrobject;
            if (arrobject.length > 3) {
                arrstring = Locale.composeList(new MessageFormat(string), (String[])arrobject);
            }
            arrobject = new Object[arrstring.length + 1];
            System.arraycopy(arrstring, 0, arrobject, 1, arrstring.length);
            arrobject[0] = new Integer(arrstring.length);
            return new MessageFormat((String)charSequence).format(arrobject);
        }
        charSequence = new StringBuilder();
        for (int i = 0; i < arrobject.length; ++i) {
            if (i > 0) {
                ((StringBuilder)charSequence).append(',');
            }
            ((StringBuilder)charSequence).append((String)arrobject[i]);
        }
        return ((StringBuilder)charSequence).toString();
    }

    public static Locale[] getAvailableLocales() {
        return ICU.getAvailableLocales();
    }

    private static LocaleExtensions getCompatibilityExtensions(String string, String string2, String string3, String string4) {
        LocaleExtensions localeExtensions;
        LocaleExtensions localeExtensions2 = null;
        if (LocaleUtils.caseIgnoreMatch(string, "ja") && string2.length() == 0 && LocaleUtils.caseIgnoreMatch(string3, "jp") && "JP".equals(string4)) {
            localeExtensions = LocaleExtensions.CALENDAR_JAPANESE;
        } else {
            localeExtensions = localeExtensions2;
            if (LocaleUtils.caseIgnoreMatch(string, "th")) {
                localeExtensions = localeExtensions2;
                if (string2.length() == 0) {
                    localeExtensions = localeExtensions2;
                    if (LocaleUtils.caseIgnoreMatch(string3, "th")) {
                        localeExtensions = localeExtensions2;
                        if ("TH".equals(string4)) {
                            localeExtensions = LocaleExtensions.NUMBER_THAI;
                        }
                    }
                }
            }
        }
        return localeExtensions;
    }

    public static Locale getDefault() {
        return NoImagePreloadHolder.defaultLocale;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Locale getDefault(Category category) {
        int n = 1.$SwitchMap$java$util$Locale$Category[category.ordinal()];
        if (n == 1) {
            if (defaultDisplayLocale != null) return defaultDisplayLocale;
            synchronized (Locale.class) {
                if (defaultDisplayLocale != null) return defaultDisplayLocale;
                defaultDisplayLocale = Locale.initDefault(category);
                return defaultDisplayLocale;
            }
        }
        if (n != 2) {
            return Locale.getDefault();
        }
        if (defaultFormatLocale != null) return defaultFormatLocale;
        synchronized (Locale.class) {
            if (defaultFormatLocale != null) return defaultFormatLocale;
            defaultFormatLocale = Locale.initDefault(category);
            return defaultFormatLocale;
        }
    }

    public static String[] getISOCountries() {
        return ICU.getISOCountries();
    }

    public static String[] getISOLanguages() {
        return ICU.getISOLanguages();
    }

    static Locale getInstance(String string, String string2, String string3) {
        return Locale.getInstance(string, "", string2, string3, null);
    }

    static Locale getInstance(String string, String string2, String string3, String string4, LocaleExtensions localeExtensions) {
        if (string != null && string2 != null && string3 != null && string4 != null) {
            LocaleExtensions localeExtensions2 = localeExtensions;
            if (localeExtensions == null) {
                localeExtensions2 = Locale.getCompatibilityExtensions(string, string2, string3, string4);
            }
            return Locale.getInstance(BaseLocale.getInstance(string, string2, string3, string4), localeExtensions2);
        }
        throw new NullPointerException();
    }

    static Locale getInstance(BaseLocale object, LocaleExtensions localeExtensions) {
        object = new LocaleKey((BaseLocale)object, localeExtensions);
        return (Locale)LOCALECACHE.get(object);
    }

    public static Locale initDefault() {
        String string;
        String string2;
        String string3;
        String string4 = System.getProperty("user.locale", "");
        if (!string4.isEmpty()) {
            return Locale.forLanguageTag(string4);
        }
        String string5 = System.getProperty("user.language", "en");
        string4 = System.getProperty("user.region");
        if (string4 != null) {
            int n = string4.indexOf(95);
            if (n >= 0) {
                string = string4.substring(0, n);
                string4 = string4.substring(n + 1);
            } else {
                string = string4;
                string4 = "";
            }
            string2 = "";
            string3 = string;
            string = string4;
        } else {
            string4 = System.getProperty("user.script", "");
            string3 = System.getProperty("user.country", "");
            string = System.getProperty("user.variant", "");
            string2 = string4;
        }
        return Locale.getInstance(string5, string2, string3, string, null);
    }

    private static Locale initDefault(Category category) {
        Locale locale = NoImagePreloadHolder.defaultLocale;
        return Locale.getInstance(System.getProperty(category.languageKey, locale.getLanguage()), System.getProperty(category.scriptKey, locale.getScript()), System.getProperty(category.countryKey, locale.getCountry()), System.getProperty(category.variantKey, locale.getVariant()), null);
    }

    private static boolean isAsciiAlphaNum(String string) {
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9') {
                continue;
            }
            return false;
        }
        return true;
    }

    private static boolean isUnM49AreaCode(String string) {
        if (string.length() != 3) {
            return false;
        }
        for (int i = 0; i < 3; ++i) {
            char c = string.charAt(i);
            if (c >= '0' && c <= '9') {
                continue;
            }
            return false;
        }
        return true;
    }

    private static boolean isUnicodeExtensionKey(String string) {
        boolean bl = string.length() == 2 && LocaleUtils.isAlphaNumericString(string);
        return bl;
    }

    private static boolean isValidBcp47Alpha(String string, int n, int n2) {
        int n3 = string.length();
        if (n3 >= n && n3 <= n2) {
            for (n = 0; n < n3; ++n) {
                n2 = string.charAt(n);
                if (n2 >= 97 && n2 <= 122 || n2 >= 65 && n2 <= 90) {
                    continue;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean isValidVariantSubtag(String string) {
        char c;
        return string.length() >= 5 && string.length() <= 8 ? Locale.isAsciiAlphaNum(string) : string.length() == 4 && (c = string.charAt(0)) >= '0' && c <= '9' && Locale.isAsciiAlphaNum(string);
    }

    public static Locale lookup(List<LanguageRange> list, Collection<Locale> collection) {
        return LocaleMatcher.lookup(list, collection);
    }

    public static String lookupTag(List<LanguageRange> list, Collection<String> collection) {
        return LocaleMatcher.lookupTag(list, collection);
    }

    private static String normalizeAndValidateLanguage(String string, boolean bl) {
        if (string != null && !string.isEmpty()) {
            CharSequence charSequence = string.toLowerCase(ROOT);
            if (!Locale.isValidBcp47Alpha((String)charSequence, 2, 3)) {
                if (!bl) {
                    return UNDETERMINED_LANGUAGE;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Invalid language: ");
                ((StringBuilder)charSequence).append(string);
                throw new IllformedLocaleException(((StringBuilder)charSequence).toString());
            }
            return charSequence;
        }
        return "";
    }

    private static String normalizeAndValidateRegion(String string, boolean bl) {
        if (string != null && !string.isEmpty()) {
            CharSequence charSequence = string.toUpperCase(ROOT);
            if (!Locale.isValidBcp47Alpha((String)charSequence, 2, 2) && !Locale.isUnM49AreaCode((String)charSequence)) {
                if (!bl) {
                    return "";
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Invalid region: ");
                ((StringBuilder)charSequence).append(string);
                throw new IllformedLocaleException(((StringBuilder)charSequence).toString());
            }
            return charSequence;
        }
        return "";
    }

    private static String normalizeAndValidateVariant(String string) {
        if (string != null && !string.isEmpty()) {
            String string2 = string.replace('-', '_');
            Object object = string2.split("_");
            int n = ((String[])object).length;
            for (int i = 0; i < n; ++i) {
                if (Locale.isValidVariantSubtag(object[i])) {
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid variant: ");
                ((StringBuilder)object).append(string);
                throw new IllformedLocaleException(((StringBuilder)object).toString());
            }
            return string2;
        }
        return "";
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        object = ((ObjectInputStream)object).readFields();
        Object object2 = (String)((ObjectInputStream.GetField)object).get("language", "");
        String string = (String)((ObjectInputStream.GetField)object).get("script", "");
        String string2 = (String)((ObjectInputStream.GetField)object).get("country", "");
        String string3 = (String)((ObjectInputStream.GetField)object).get("variant", "");
        object = (String)((ObjectInputStream.GetField)object).get("extensions", "");
        this.baseLocale = BaseLocale.getInstance(Locale.convertOldISOCodes((String)object2), string, string2, string3);
        if (object != null && ((String)object).length() > 0) {
            try {
                object2 = new InternalLocaleBuilder();
                ((InternalLocaleBuilder)object2).setExtensions((String)object);
                this.localeExtensions = ((InternalLocaleBuilder)object2).getLocaleExtensions();
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage());
            }
        } else {
            this.localeExtensions = null;
        }
    }

    private Object readResolve() throws ObjectStreamException {
        return Locale.getInstance(this.baseLocale.getLanguage(), this.baseLocale.getScript(), this.baseLocale.getRegion(), this.baseLocale.getVariant(), this.localeExtensions);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void setDefault(Category object, Locale locale) {
        synchronized (Locale.class) {
            int n;
            void var1_1;
            if (object == null) {
                object = new NullPointerException("Category cannot be NULL");
                throw object;
            }
            if (var1_1 == null) {
                object = new NullPointerException("Can't set default locale to NULL");
                throw object;
            }
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                PropertyPermission propertyPermission = new PropertyPermission("user.language", "write");
                securityManager.checkPermission(propertyPermission);
            }
            if ((n = 1.$SwitchMap$java$util$Locale$Category[((Enum)object).ordinal()]) != 1) {
                if (n == 2) {
                    defaultFormatLocale = var1_1;
                }
            } else {
                defaultDisplayLocale = var1_1;
            }
            return;
        }
    }

    public static void setDefault(Locale locale) {
        synchronized (Locale.class) {
            Locale.setDefault(Category.DISPLAY, locale);
            Locale.setDefault(Category.FORMAT, locale);
            NoImagePreloadHolder.defaultLocale = locale;
            ICU.setDefaultLocale((String)locale.toLanguageTag());
            return;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ObjectOutputStream.PutField putField = objectOutputStream.putFields();
        putField.put("language", this.baseLocale.getLanguage());
        putField.put("script", this.baseLocale.getScript());
        putField.put("country", this.baseLocale.getRegion());
        putField.put("variant", this.baseLocale.getVariant());
        Object object = this.localeExtensions;
        object = object == null ? "" : ((LocaleExtensions)object).getID();
        putField.put("extensions", object);
        putField.put("hashcode", -1);
        objectOutputStream.writeFields();
    }

    public Object clone() {
        try {
            Locale locale = (Locale)super.clone();
            return locale;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof Locale)) {
            return false;
        }
        Object object2 = ((Locale)object).baseLocale;
        if (!this.baseLocale.equals(object2)) {
            return false;
        }
        object2 = this.localeExtensions;
        if (object2 == null) {
            if (((Locale)object).localeExtensions != null) {
                bl = false;
            }
            return bl;
        }
        return ((LocaleExtensions)object2).equals(((Locale)object).localeExtensions);
    }

    BaseLocale getBaseLocale() {
        return this.baseLocale;
    }

    public String getCountry() {
        return this.baseLocale.getRegion();
    }

    public final String getDisplayCountry() {
        return this.getDisplayCountry(Locale.getDefault(Category.DISPLAY));
    }

    public String getDisplayCountry(Locale object) {
        String string = this.baseLocale.getRegion();
        if (string.isEmpty()) {
            return "";
        }
        if (Locale.normalizeAndValidateRegion(string, false).isEmpty()) {
            return string;
        }
        string = ICU.getDisplayCountry((Locale)this, (Locale)object);
        object = string;
        if (string == null) {
            object = ICU.getDisplayCountry((Locale)this, (Locale)Locale.getDefault());
        }
        return object;
    }

    public final String getDisplayLanguage() {
        return this.getDisplayLanguage(Locale.getDefault(Category.DISPLAY));
    }

    public String getDisplayLanguage(Locale object) {
        String string = this.baseLocale.getLanguage();
        if (string.isEmpty()) {
            return "";
        }
        if (UNDETERMINED_LANGUAGE.equals(Locale.normalizeAndValidateLanguage(string, false))) {
            return string;
        }
        string = ICU.getDisplayLanguage((Locale)this, (Locale)object);
        object = string;
        if (string == null) {
            object = ICU.getDisplayLanguage((Locale)this, (Locale)Locale.getDefault());
        }
        return object;
    }

    public final String getDisplayName() {
        return this.getDisplayName(Locale.getDefault(Category.DISPLAY));
    }

    public String getDisplayName(Locale object) {
        String string;
        int n = 0;
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = this.baseLocale.getLanguage();
        if (!string2.isEmpty()) {
            string = this.getDisplayLanguage((Locale)object);
            if (string.isEmpty()) {
                string = string2;
            }
            stringBuilder.append(string);
            n = 0 + 1;
        }
        string2 = this.baseLocale.getScript();
        int n2 = n;
        if (!string2.isEmpty()) {
            if (n == 1) {
                stringBuilder.append(" (");
            }
            if ((string = this.getDisplayScript((Locale)object)).isEmpty()) {
                string = string2;
            }
            stringBuilder.append(string);
            n2 = n + 1;
        }
        string = this.baseLocale.getRegion();
        n = n2;
        if (!string.isEmpty()) {
            if (n2 == 1) {
                stringBuilder.append(" (");
            } else if (n2 == 2) {
                stringBuilder.append(",");
            }
            string2 = this.getDisplayCountry((Locale)object);
            if (!string2.isEmpty()) {
                string = string2;
            }
            stringBuilder.append(string);
            n = n2 + 1;
        }
        string = this.baseLocale.getVariant();
        n2 = n;
        if (!string.isEmpty()) {
            if (n == 1) {
                stringBuilder.append(" (");
            } else if (n == 2 || n == 3) {
                stringBuilder.append(",");
            }
            object = this.getDisplayVariant((Locale)object);
            if (((String)object).isEmpty()) {
                object = string;
            }
            stringBuilder.append((String)object);
            n2 = n + 1;
        }
        if (n2 > 1) {
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    public String getDisplayScript() {
        return this.getDisplayScript(Locale.getDefault(Category.DISPLAY));
    }

    public String getDisplayScript(Locale object) {
        if (this.baseLocale.getScript().isEmpty()) {
            return "";
        }
        String string = ICU.getDisplayScript((Locale)this, (Locale)object);
        object = string;
        if (string == null) {
            object = ICU.getDisplayScript((Locale)this, (Locale)Locale.getDefault(Category.DISPLAY));
        }
        return object;
    }

    public final String getDisplayVariant() {
        return this.getDisplayVariant(Locale.getDefault(Category.DISPLAY));
    }

    public String getDisplayVariant(Locale object) {
        String string = this.baseLocale.getVariant();
        if (string.isEmpty()) {
            return "";
        }
        try {
            Locale.normalizeAndValidateVariant(string);
        }
        catch (IllformedLocaleException illformedLocaleException) {
            return string;
        }
        String string2 = ICU.getDisplayVariant((Locale)this, (Locale)object);
        object = string2;
        if (string2 == null) {
            object = ICU.getDisplayVariant((Locale)this, (Locale)Locale.getDefault());
        }
        if (((String)object).isEmpty()) {
            return string;
        }
        return object;
    }

    public String getExtension(char c) {
        if (LocaleExtensions.isValidKey(c)) {
            String string = this.hasExtensions() ? this.localeExtensions.getExtensionValue(Character.valueOf(c)) : null;
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ill-formed extension key: ");
        stringBuilder.append(c);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Set<Character> getExtensionKeys() {
        if (!this.hasExtensions()) {
            return Collections.emptySet();
        }
        return this.localeExtensions.getKeys();
    }

    public String getISO3Country() throws MissingResourceException {
        CharSequence charSequence = this.baseLocale.getRegion();
        if (((String)charSequence).length() == 3) {
            return this.baseLocale.getRegion();
        }
        if (((String)charSequence).isEmpty()) {
            return "";
        }
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("en-");
        ((StringBuilder)charSequence2).append((String)charSequence);
        charSequence2 = ICU.getISO3Country((String)((StringBuilder)charSequence2).toString());
        if (!((String)charSequence).isEmpty() && ((String)charSequence2).isEmpty()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Couldn't find 3-letter country code for ");
            ((StringBuilder)charSequence).append(this.baseLocale.getRegion());
            charSequence2 = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("FormatData_");
            ((StringBuilder)charSequence).append(this.toString());
            throw new MissingResourceException((String)charSequence2, ((StringBuilder)charSequence).toString(), "ShortCountry");
        }
        return charSequence2;
    }

    public String getISO3Language() throws MissingResourceException {
        CharSequence charSequence = this.baseLocale.getLanguage();
        if (((String)charSequence).length() == 3) {
            return charSequence;
        }
        if (((String)charSequence).isEmpty()) {
            return "";
        }
        CharSequence charSequence2 = ICU.getISO3Language((String)charSequence);
        if (!((String)charSequence).isEmpty() && ((String)charSequence2).isEmpty()) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Couldn't find 3-letter language code for ");
            ((StringBuilder)charSequence2).append((String)charSequence);
            charSequence2 = ((StringBuilder)charSequence2).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("FormatData_");
            ((StringBuilder)charSequence).append(this.toString());
            throw new MissingResourceException((String)charSequence2, ((StringBuilder)charSequence).toString(), "ShortLanguage");
        }
        return charSequence2;
    }

    public String getLanguage() {
        return this.baseLocale.getLanguage();
    }

    LocaleExtensions getLocaleExtensions() {
        return this.localeExtensions;
    }

    public String getScript() {
        return this.baseLocale.getScript();
    }

    public Set<String> getUnicodeLocaleAttributes() {
        if (!this.hasExtensions()) {
            return Collections.emptySet();
        }
        return this.localeExtensions.getUnicodeLocaleAttributes();
    }

    public Set<String> getUnicodeLocaleKeys() {
        LocaleExtensions localeExtensions = this.localeExtensions;
        if (localeExtensions == null) {
            return Collections.emptySet();
        }
        return localeExtensions.getUnicodeLocaleKeys();
    }

    public String getUnicodeLocaleType(String string) {
        if (Locale.isUnicodeExtensionKey(string)) {
            string = this.hasExtensions() ? this.localeExtensions.getUnicodeLocaleType(string) : null;
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ill-formed Unicode locale key: ");
        stringBuilder.append(string);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public String getVariant() {
        return this.baseLocale.getVariant();
    }

    public boolean hasExtensions() {
        boolean bl = this.localeExtensions != null;
        return bl;
    }

    public int hashCode() {
        int n;
        int n2 = n = this.hashCodeValue;
        if (n == 0) {
            n = this.baseLocale.hashCode();
            LocaleExtensions localeExtensions = this.localeExtensions;
            n2 = n;
            if (localeExtensions != null) {
                n2 = n ^ localeExtensions.hashCode();
            }
            this.hashCodeValue = n2;
        }
        return n2;
    }

    public Locale stripExtensions() {
        Locale locale = this.hasExtensions() ? Locale.getInstance(this.baseLocale, null) : this;
        return locale;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toLanguageTag() {
        if (this.languageTag != null) {
            return this.languageTag;
        }
        Object object = LanguageTag.parseLocale(this.baseLocale, this.localeExtensions);
        CharSequence charSequence = new StringBuilder();
        Object object22 = ((LanguageTag)object).getLanguage();
        if (((String)object22).length() > 0) {
            ((StringBuilder)charSequence).append(LanguageTag.canonicalizeLanguage((String)object22));
        }
        if (((String)(object22 = ((LanguageTag)object).getScript())).length() > 0) {
            ((StringBuilder)charSequence).append("-");
            ((StringBuilder)charSequence).append(LanguageTag.canonicalizeScript((String)object22));
        }
        if (((String)(object22 = ((LanguageTag)object).getRegion())).length() > 0) {
            ((StringBuilder)charSequence).append("-");
            ((StringBuilder)charSequence).append(LanguageTag.canonicalizeRegion((String)object22));
        }
        for (Object object22 : ((LanguageTag)object).getVariants()) {
            ((StringBuilder)charSequence).append("-");
            ((StringBuilder)charSequence).append((String)object22);
        }
        for (Object object3 : ((LanguageTag)object).getExtensions()) {
            ((StringBuilder)charSequence).append("-");
            ((StringBuilder)charSequence).append(LanguageTag.canonicalizeExtension((String)object3));
        }
        if (((String)(object = ((LanguageTag)object).getPrivateuse())).length() > 0) {
            if (((StringBuilder)charSequence).length() > 0) {
                ((StringBuilder)charSequence).append("-");
            }
            ((StringBuilder)charSequence).append("x");
            ((StringBuilder)charSequence).append("-");
            ((StringBuilder)charSequence).append((String)object);
        }
        charSequence = ((StringBuilder)charSequence).toString();
        synchronized (this) {
            if (this.languageTag == null) {
                this.languageTag = charSequence;
            }
            return this.languageTag;
        }
    }

    public final String toString() {
        int n = this.baseLocale.getLanguage().length();
        boolean bl = true;
        n = n != 0 ? 1 : 0;
        boolean bl2 = this.baseLocale.getScript().length() != 0;
        boolean bl3 = this.baseLocale.getRegion().length() != 0;
        boolean bl4 = this.baseLocale.getVariant().length() != 0;
        Object object = this.localeExtensions;
        if (object == null || ((LocaleExtensions)object).getID().length() == 0) {
            bl = false;
        }
        object = new StringBuilder(this.baseLocale.getLanguage());
        if (bl3 || n != 0 && (bl4 || bl2 || bl)) {
            ((StringBuilder)object).append('_');
            ((StringBuilder)object).append(this.baseLocale.getRegion());
        }
        if (bl4 && (n != 0 || bl3)) {
            ((StringBuilder)object).append('_');
            ((StringBuilder)object).append(this.baseLocale.getVariant());
        }
        if (bl2 && (n != 0 || bl3)) {
            ((StringBuilder)object).append("_#");
            ((StringBuilder)object).append(this.baseLocale.getScript());
        }
        if (bl && (n != 0 || bl3)) {
            ((StringBuilder)object).append('_');
            if (!bl2) {
                ((StringBuilder)object).append('#');
            }
            ((StringBuilder)object).append(this.localeExtensions.getID());
        }
        return ((StringBuilder)object).toString();
    }

    public static final class Builder {
        private final InternalLocaleBuilder localeBuilder = new InternalLocaleBuilder();

        public Builder addUnicodeLocaleAttribute(String string) {
            try {
                this.localeBuilder.addUnicodeLocaleAttribute(string);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Locale build() {
            LocaleExtensions localeExtensions;
            BaseLocale baseLocale = this.localeBuilder.getBaseLocale();
            LocaleExtensions localeExtensions2 = localeExtensions = this.localeBuilder.getLocaleExtensions();
            if (localeExtensions == null) {
                localeExtensions2 = localeExtensions;
                if (baseLocale.getVariant().length() > 0) {
                    localeExtensions2 = Locale.getCompatibilityExtensions(baseLocale.getLanguage(), baseLocale.getScript(), baseLocale.getRegion(), baseLocale.getVariant());
                }
            }
            return Locale.getInstance(baseLocale, localeExtensions2);
        }

        public Builder clear() {
            this.localeBuilder.clear();
            return this;
        }

        public Builder clearExtensions() {
            this.localeBuilder.clearExtensions();
            return this;
        }

        public Builder removeUnicodeLocaleAttribute(String string) {
            if (string != null) {
                try {
                    this.localeBuilder.removeUnicodeLocaleAttribute(string);
                    return this;
                }
                catch (LocaleSyntaxException localeSyntaxException) {
                    throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
                }
            }
            throw new NullPointerException("attribute == null");
        }

        public Builder setExtension(char c, String string) {
            try {
                this.localeBuilder.setExtension(c, string);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Builder setLanguage(String string) {
            try {
                this.localeBuilder.setLanguage(string);
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
                this.localeBuilder.setLanguageTag((LanguageTag)object);
                return this;
            }
            throw new IllformedLocaleException(parseStatus.getErrorMessage(), parseStatus.getErrorIndex());
        }

        public Builder setLocale(Locale locale) {
            try {
                this.localeBuilder.setLocale(locale.baseLocale, locale.localeExtensions);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Builder setRegion(String string) {
            try {
                this.localeBuilder.setRegion(string);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Builder setScript(String string) {
            try {
                this.localeBuilder.setScript(string);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Builder setUnicodeLocaleKeyword(String string, String string2) {
            try {
                this.localeBuilder.setUnicodeLocaleKeyword(string, string2);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }

        public Builder setVariant(String string) {
            try {
                this.localeBuilder.setVariant(string);
                return this;
            }
            catch (LocaleSyntaxException localeSyntaxException) {
                throw new IllformedLocaleException(localeSyntaxException.getMessage(), localeSyntaxException.getErrorIndex());
            }
        }
    }

    private static class Cache
    extends LocaleObjectCache<LocaleKey, Locale> {
        private Cache() {
        }

        @Override
        protected Locale createObject(LocaleKey localeKey) {
            return new Locale(localeKey.base, localeKey.exts);
        }
    }

    public static enum Category {
        DISPLAY("user.language.display", "user.script.display", "user.country.display", "user.variant.display"),
        FORMAT("user.language.format", "user.script.format", "user.country.format", "user.variant.format");
        
        final String countryKey;
        final String languageKey;
        final String scriptKey;
        final String variantKey;

        private Category(String string2, String string3, String string4, String string5) {
            this.languageKey = string2;
            this.scriptKey = string3;
            this.countryKey = string4;
            this.variantKey = string5;
        }
    }

    public static enum FilteringMode {
        AUTOSELECT_FILTERING,
        EXTENDED_FILTERING,
        IGNORE_EXTENDED_RANGES,
        MAP_EXTENDED_RANGES,
        REJECT_EXTENDED_RANGES;
        
    }

    public static final class LanguageRange {
        public static final double MAX_WEIGHT = 1.0;
        public static final double MIN_WEIGHT = 0.0;
        private volatile int hash = 0;
        private final String range;
        private final double weight;

        public LanguageRange(String string) {
            this(string, 1.0);
        }

        public LanguageRange(String charSequence, double d) {
            if (charSequence != null) {
                if (!(d < 0.0) && !(d > 1.0)) {
                    Object object;
                    boolean bl;
                    block6 : {
                        charSequence = ((String)charSequence).toLowerCase();
                        boolean bl2 = false;
                        object = ((String)charSequence).split("-");
                        if (!LanguageRange.isSubtagIllFormed(object[0], true) && !((String)charSequence).endsWith("-")) {
                            int n = 1;
                            do {
                                bl = bl2;
                                if (n >= ((String[])object).length) break block6;
                                if (LanguageRange.isSubtagIllFormed((String)object[n], false)) {
                                    bl = true;
                                    break block6;
                                }
                                ++n;
                            } while (true);
                        }
                        bl = true;
                    }
                    if (!bl) {
                        this.range = charSequence;
                        this.weight = d;
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("range=");
                    ((StringBuilder)object).append((String)charSequence);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("weight=");
                ((StringBuilder)charSequence).append(d);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            throw new NullPointerException();
        }

        private static boolean isSubtagIllFormed(String arrc, boolean bl) {
            if (!arrc.equals("") && arrc.length() <= 8) {
                if (arrc.equals("*")) {
                    return false;
                }
                arrc = arrc.toCharArray();
                if (bl) {
                    for (char c : arrc) {
                        if (c >= 'a' && c <= 'z') {
                            continue;
                        }
                        return true;
                    }
                } else {
                    for (char c : arrc) {
                        if (c >= '0' && (c <= '9' || c >= 'a') && c <= 'z') {
                            continue;
                        }
                        return true;
                    }
                }
                return false;
            }
            return true;
        }

        public static List<LanguageRange> mapEquivalents(List<LanguageRange> list, Map<String, List<String>> map) {
            return LocaleMatcher.mapEquivalents(list, map);
        }

        public static List<LanguageRange> parse(String string) {
            return LocaleMatcher.parse(string);
        }

        public static List<LanguageRange> parse(String string, Map<String, List<String>> map) {
            return LanguageRange.mapEquivalents(LanguageRange.parse(string), map);
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof LanguageRange)) {
                return false;
            }
            object = (LanguageRange)object;
            if (this.hash != ((LanguageRange)object).hash || !this.range.equals(((LanguageRange)object).range) || this.weight != ((LanguageRange)object).weight) {
                bl = false;
            }
            return bl;
        }

        public String getRange() {
            return this.range;
        }

        public double getWeight() {
            return this.weight;
        }

        public int hashCode() {
            if (this.hash == 0) {
                int n = this.range.hashCode();
                long l = Double.doubleToLongBits(this.weight);
                this.hash = (17 * 37 + n) * 37 + (int)(l >>> 32 ^ l);
            }
            return this.hash;
        }
    }

    private static final class LocaleKey {
        private final BaseLocale base;
        private final LocaleExtensions exts;
        private final int hash;

        private LocaleKey(BaseLocale object, LocaleExtensions localeExtensions) {
            this.base = object;
            this.exts = localeExtensions;
            int n = this.base.hashCode();
            object = this.exts;
            int n2 = n;
            if (object != null) {
                n2 = n ^ ((LocaleExtensions)object).hashCode();
            }
            this.hash = n2;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof LocaleKey)) {
                return false;
            }
            object = (LocaleKey)object;
            if (this.hash == ((LocaleKey)object).hash && this.base.equals(((LocaleKey)object).base)) {
                LocaleExtensions localeExtensions = this.exts;
                if (localeExtensions == null) {
                    if (((LocaleKey)object).exts != null) {
                        bl = false;
                    }
                    return bl;
                }
                return localeExtensions.equals(((LocaleKey)object).exts);
            }
            return false;
        }

        public int hashCode() {
            return this.hash;
        }
    }

    private static class NoImagePreloadHolder {
        public static volatile Locale defaultLocale = Locale.initDefault();

        private NoImagePreloadHolder() {
        }
    }

}

