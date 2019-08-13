/*
 * Decompiled with CFR 0.145.
 */
package sun.util.locale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import sun.util.locale.BaseLocale;
import sun.util.locale.Extension;
import sun.util.locale.LocaleExtensions;
import sun.util.locale.LocaleUtils;
import sun.util.locale.ParseStatus;
import sun.util.locale.StringTokenIterator;

public class LanguageTag {
    private static final Map<String, String[]> GRANDFATHERED = new HashMap<String, String[]>();
    public static final String PRIVATEUSE = "x";
    public static final String PRIVUSE_VARIANT_PREFIX = "lvariant";
    public static final String SEP = "-";
    public static final String UNDETERMINED = "und";
    private List<String> extensions = Collections.emptyList();
    private List<String> extlangs = Collections.emptyList();
    private String language = "";
    private String privateuse = "";
    private String region = "";
    private String script = "";
    private List<String> variants = Collections.emptyList();

    static {
        for (String[] arrstring : new String[][]{{"art-lojban", "jbo"}, {"cel-gaulish", "xtg-x-cel-gaulish"}, {"en-GB-oed", "en-GB-x-oed"}, {"i-ami", "ami"}, {"i-bnn", "bnn"}, {"i-default", "en-x-i-default"}, {"i-enochian", "und-x-i-enochian"}, {"i-hak", "hak"}, {"i-klingon", "tlh"}, {"i-lux", "lb"}, {"i-mingo", "see-x-i-mingo"}, {"i-navajo", "nv"}, {"i-pwn", "pwn"}, {"i-tao", "tao"}, {"i-tay", "tay"}, {"i-tsu", "tsu"}, {"no-bok", "nb"}, {"no-nyn", "nn"}, {"sgn-BE-FR", "sfb"}, {"sgn-BE-NL", "vgt"}, {"sgn-CH-DE", "sgg"}, {"zh-guoyu", "cmn"}, {"zh-hakka", "hak"}, {"zh-min", "nan-x-zh-min"}, {"zh-min-nan", "nan"}, {"zh-xiang", "hsn"}}) {
            GRANDFATHERED.put(LocaleUtils.toLowerString(arrstring[0]), arrstring);
        }
    }

    private LanguageTag() {
    }

    public static String canonicalizeExtension(String string) {
        return LocaleUtils.toLowerString(string);
    }

    public static String canonicalizeExtensionSingleton(String string) {
        return LocaleUtils.toLowerString(string);
    }

    public static String canonicalizeExtensionSubtag(String string) {
        return LocaleUtils.toLowerString(string);
    }

    public static String canonicalizeExtlang(String string) {
        return LocaleUtils.toLowerString(string);
    }

    public static String canonicalizeLanguage(String string) {
        return LocaleUtils.toLowerString(string);
    }

    public static String canonicalizePrivateuse(String string) {
        return LocaleUtils.toLowerString(string);
    }

    public static String canonicalizePrivateuseSubtag(String string) {
        return LocaleUtils.toLowerString(string);
    }

    public static String canonicalizeRegion(String string) {
        return LocaleUtils.toUpperString(string);
    }

    public static String canonicalizeScript(String string) {
        return LocaleUtils.toTitleString(string);
    }

    public static String canonicalizeVariant(String string) {
        return LocaleUtils.toLowerString(string);
    }

    public static boolean isExtensionSingleton(String string) {
        int n = string.length();
        boolean bl = true;
        if (n != 1 || !LocaleUtils.isAlphaString(string) || LocaleUtils.caseIgnoreMatch(PRIVATEUSE, string)) {
            bl = false;
        }
        return bl;
    }

    public static boolean isExtensionSingletonChar(char c) {
        return LanguageTag.isExtensionSingleton(String.valueOf(c));
    }

    public static boolean isExtensionSubtag(String string) {
        int n = string.length();
        boolean bl = n >= 2 && n <= 8 && LocaleUtils.isAlphaNumericString(string);
        return bl;
    }

    public static boolean isExtlang(String string) {
        boolean bl = string.length() == 3 && LocaleUtils.isAlphaString(string);
        return bl;
    }

    public static boolean isLanguage(String string) {
        int n = string.length();
        boolean bl = n >= 2 && n <= 8 && LocaleUtils.isAlphaString(string);
        return bl;
    }

    public static boolean isPrivateusePrefix(String string) {
        int n = string.length();
        boolean bl = true;
        if (n != 1 || !LocaleUtils.caseIgnoreMatch(PRIVATEUSE, string)) {
            bl = false;
        }
        return bl;
    }

    public static boolean isPrivateusePrefixChar(char c) {
        return LocaleUtils.caseIgnoreMatch(PRIVATEUSE, String.valueOf(c));
    }

    public static boolean isPrivateuseSubtag(String string) {
        int n = string.length();
        boolean bl = true;
        if (n < 1 || n > 8 || !LocaleUtils.isAlphaNumericString(string)) {
            bl = false;
        }
        return bl;
    }

    public static boolean isRegion(String string) {
        boolean bl = string.length() == 2 && LocaleUtils.isAlphaString(string) || string.length() == 3 && LocaleUtils.isNumericString(string);
        return bl;
    }

    public static boolean isScript(String string) {
        boolean bl = string.length() == 4 && LocaleUtils.isAlphaString(string);
        return bl;
    }

    public static boolean isVariant(String string) {
        int n = string.length();
        if (n >= 5 && n <= 8) {
            return LocaleUtils.isAlphaNumericString(string);
        }
        boolean bl = false;
        if (n == 4) {
            if (LocaleUtils.isNumeric(string.charAt(0)) && LocaleUtils.isAlphaNumeric(string.charAt(1)) && LocaleUtils.isAlphaNumeric(string.charAt(2)) && LocaleUtils.isAlphaNumeric(string.charAt(3))) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public static LanguageTag parse(String object, ParseStatus parseStatus) {
        if (parseStatus == null) {
            parseStatus = new ParseStatus();
        } else {
            parseStatus.reset();
        }
        Object object2 = GRANDFATHERED.get(LocaleUtils.toLowerString((String)object));
        object = object2 != null ? new StringTokenIterator(object2[1], SEP) : new StringTokenIterator((String)object, SEP);
        object2 = new LanguageTag();
        if (LanguageTag.super.parseLanguage((StringTokenIterator)object, parseStatus)) {
            LanguageTag.super.parseExtlangs((StringTokenIterator)object, parseStatus);
            LanguageTag.super.parseScript((StringTokenIterator)object, parseStatus);
            LanguageTag.super.parseRegion((StringTokenIterator)object, parseStatus);
            LanguageTag.super.parseVariants((StringTokenIterator)object, parseStatus);
            LanguageTag.super.parseExtensions((StringTokenIterator)object, parseStatus);
        }
        LanguageTag.super.parsePrivateuse((StringTokenIterator)object, parseStatus);
        if (!((StringTokenIterator)object).isDone() && !parseStatus.isError()) {
            String string = ((StringTokenIterator)object).current();
            parseStatus.errorIndex = ((StringTokenIterator)object).currentStart();
            if (string.length() == 0) {
                parseStatus.errorMsg = "Empty subtag";
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid subtag: ");
                ((StringBuilder)object).append(string);
                parseStatus.errorMsg = ((StringBuilder)object).toString();
            }
        }
        return object2;
    }

    private boolean parseExtensions(StringTokenIterator object, ParseStatus parseStatus) {
        if (!((StringTokenIterator)object).isDone() && !parseStatus.isError()) {
            String string;
            boolean bl = false;
            while (!((StringTokenIterator)object).isDone() && LanguageTag.isExtensionSingleton(string = ((StringTokenIterator)object).current())) {
                String string2;
                int n = ((StringTokenIterator)object).currentStart();
                StringBuilder stringBuilder = new StringBuilder(string);
                ((StringTokenIterator)object).next();
                while (!((StringTokenIterator)object).isDone() && LanguageTag.isExtensionSubtag(string2 = ((StringTokenIterator)object).current())) {
                    stringBuilder.append(SEP);
                    stringBuilder.append(string2);
                    parseStatus.parseLength = ((StringTokenIterator)object).currentEnd();
                    ((StringTokenIterator)object).next();
                }
                if (parseStatus.parseLength <= n) {
                    parseStatus.errorIndex = n;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Incomplete extension '");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append("'");
                    parseStatus.errorMsg = ((StringBuilder)object).toString();
                    break;
                }
                if (this.extensions.isEmpty()) {
                    this.extensions = new ArrayList<String>(4);
                }
                this.extensions.add(stringBuilder.toString());
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private boolean parseExtlangs(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (!stringTokenIterator.isDone() && !parseStatus.isError()) {
            boolean bl;
            boolean bl2 = false;
            do {
                bl = bl2;
                if (stringTokenIterator.isDone()) break;
                String string = stringTokenIterator.current();
                if (!LanguageTag.isExtlang(string)) {
                    bl = bl2;
                    break;
                }
                bl = true;
                bl2 = true;
                if (this.extlangs.isEmpty()) {
                    this.extlangs = new ArrayList<String>(3);
                }
                this.extlangs.add(string);
                parseStatus.parseLength = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            } while (this.extlangs.size() != 3);
            return bl;
        }
        return false;
    }

    private boolean parseLanguage(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (!stringTokenIterator.isDone() && !parseStatus.isError()) {
            boolean bl = false;
            String string = stringTokenIterator.current();
            if (LanguageTag.isLanguage(string)) {
                bl = true;
                this.language = string;
                parseStatus.parseLength = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
            return bl;
        }
        return false;
    }

    public static LanguageTag parseLocale(BaseLocale arrayList, LocaleExtensions localeExtensions) {
        LanguageTag languageTag = new LanguageTag();
        Object object = ((BaseLocale)((Object)arrayList)).getLanguage();
        Object object2 = ((BaseLocale)((Object)arrayList)).getScript();
        Iterator<Character> iterator = ((BaseLocale)((Object)arrayList)).getRegion();
        Object object3 = ((BaseLocale)((Object)arrayList)).getVariant();
        boolean bl = false;
        ArrayList<Object> arrayList2 = null;
        ArrayList<Object> arrayList3 = object;
        if (LanguageTag.isLanguage((String)object)) {
            if (((String)object).equals("iw")) {
                arrayList = "he";
            } else if (((String)object).equals("ji")) {
                arrayList = "yi";
            } else {
                arrayList = object;
                if (((String)object).equals("in")) {
                    arrayList = "id";
                }
            }
            languageTag.language = arrayList;
            arrayList3 = arrayList;
        }
        if (LanguageTag.isScript((String)object2)) {
            languageTag.script = LanguageTag.canonicalizeScript((String)object2);
            bl = true;
        }
        if (LanguageTag.isRegion((String)((Object)iterator))) {
            languageTag.region = LanguageTag.canonicalizeRegion((String)((Object)iterator));
            bl = true;
        }
        arrayList = object3;
        if (languageTag.language.equals("no")) {
            arrayList = object3;
            if (languageTag.region.equals("NO")) {
                arrayList = object3;
                if (((String)object3).equals("NY")) {
                    languageTag.language = "nn";
                    arrayList = "";
                }
            }
        }
        boolean bl2 = bl;
        object3 = arrayList2;
        if (((String)((Object)arrayList)).length() > 0) {
            object = null;
            iterator = new StringTokenIterator((String)((Object)arrayList), "_");
            arrayList = object;
            while (!((StringTokenIterator)((Object)iterator)).isDone() && LanguageTag.isVariant((String)(object3 = ((StringTokenIterator)((Object)iterator)).current()))) {
                object = arrayList;
                if (arrayList == null) {
                    object = new ArrayList<Object>();
                }
                object.add(object3);
                ((StringTokenIterator)((Object)iterator)).next();
                arrayList = object;
            }
            if (arrayList != null) {
                languageTag.variants = arrayList;
                bl = true;
            }
            bl2 = bl;
            object3 = arrayList2;
            if (!((StringTokenIterator)((Object)iterator)).isDone()) {
                arrayList = new StringBuilder();
                while (!((StringTokenIterator)((Object)iterator)).isDone() && LanguageTag.isPrivateuseSubtag((String)(object = ((StringTokenIterator)((Object)iterator)).current()))) {
                    if (((StringBuilder)((Object)arrayList)).length() > 0) {
                        ((StringBuilder)((Object)arrayList)).append(SEP);
                    }
                    ((StringBuilder)((Object)arrayList)).append((String)object);
                    ((StringTokenIterator)((Object)iterator)).next();
                }
                bl2 = bl;
                object3 = arrayList2;
                if (((StringBuilder)((Object)arrayList)).length() > 0) {
                    object3 = ((StringBuilder)((Object)arrayList)).toString();
                    bl2 = bl;
                }
            }
        }
        arrayList2 = null;
        arrayList = null;
        iterator = null;
        object = null;
        if (localeExtensions != null) {
            iterator = localeExtensions.getKeys().iterator();
            arrayList2 = arrayList3;
            while (iterator.hasNext()) {
                object2 = iterator.next();
                Extension extension = localeExtensions.getExtension((Character)object2);
                if (LanguageTag.isPrivateusePrefixChar(((Character)object2).charValue())) {
                    object = extension.getValue();
                    continue;
                }
                arrayList3 = arrayList;
                if (arrayList == null) {
                    arrayList3 = new ArrayList();
                }
                arrayList = new StringBuilder();
                ((StringBuilder)((Object)arrayList)).append(((Character)object2).toString());
                ((StringBuilder)((Object)arrayList)).append(SEP);
                ((StringBuilder)((Object)arrayList)).append(extension.getValue());
                arrayList3.add(((StringBuilder)((Object)arrayList)).toString());
                arrayList = arrayList3;
            }
        } else {
            object = iterator;
            arrayList = arrayList2;
        }
        if (arrayList != null) {
            languageTag.extensions = arrayList;
            bl2 = true;
        }
        arrayList = object;
        if (object3 != null) {
            if (object == null) {
                arrayList = new StringBuilder();
                ((StringBuilder)((Object)arrayList)).append("lvariant-");
                ((StringBuilder)((Object)arrayList)).append((String)object3);
                arrayList = ((StringBuilder)((Object)arrayList)).toString();
            } else {
                arrayList = new StringBuilder();
                ((StringBuilder)((Object)arrayList)).append((String)object);
                ((StringBuilder)((Object)arrayList)).append(SEP);
                ((StringBuilder)((Object)arrayList)).append(PRIVUSE_VARIANT_PREFIX);
                ((StringBuilder)((Object)arrayList)).append(SEP);
                ((StringBuilder)((Object)arrayList)).append(((String)object3).replace("_", SEP));
                arrayList = ((StringBuilder)((Object)arrayList)).toString();
            }
        }
        if (arrayList != null) {
            languageTag.privateuse = arrayList;
        }
        if (languageTag.language.length() == 0 && (bl2 || arrayList == null)) {
            languageTag.language = UNDETERMINED;
        }
        return languageTag;
    }

    private boolean parsePrivateuse(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (!stringTokenIterator.isDone() && !parseStatus.isError()) {
            boolean bl = false;
            String string = stringTokenIterator.current();
            boolean bl2 = bl;
            if (LanguageTag.isPrivateusePrefix(string)) {
                int n = stringTokenIterator.currentStart();
                StringBuilder stringBuilder = new StringBuilder(string);
                stringTokenIterator.next();
                while (!stringTokenIterator.isDone() && LanguageTag.isPrivateuseSubtag(string = stringTokenIterator.current())) {
                    stringBuilder.append(SEP);
                    stringBuilder.append(string);
                    parseStatus.parseLength = stringTokenIterator.currentEnd();
                    stringTokenIterator.next();
                }
                if (parseStatus.parseLength <= n) {
                    parseStatus.errorIndex = n;
                    parseStatus.errorMsg = "Incomplete privateuse";
                    bl2 = bl;
                } else {
                    this.privateuse = stringBuilder.toString();
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    private boolean parseRegion(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (!stringTokenIterator.isDone() && !parseStatus.isError()) {
            boolean bl = false;
            String string = stringTokenIterator.current();
            if (LanguageTag.isRegion(string)) {
                bl = true;
                this.region = string;
                parseStatus.parseLength = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
            return bl;
        }
        return false;
    }

    private boolean parseScript(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (!stringTokenIterator.isDone() && !parseStatus.isError()) {
            boolean bl = false;
            String string = stringTokenIterator.current();
            if (LanguageTag.isScript(string)) {
                bl = true;
                this.script = string;
                parseStatus.parseLength = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
            return bl;
        }
        return false;
    }

    private boolean parseVariants(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (!stringTokenIterator.isDone() && !parseStatus.isError()) {
            String string;
            boolean bl = false;
            while (!stringTokenIterator.isDone() && LanguageTag.isVariant(string = stringTokenIterator.current())) {
                bl = true;
                if (this.variants.isEmpty()) {
                    this.variants = new ArrayList<String>(3);
                }
                this.variants.add(string);
                parseStatus.parseLength = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
            return bl;
        }
        return false;
    }

    public List<String> getExtensions() {
        if (this.extensions.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.extensions);
    }

    public List<String> getExtlangs() {
        if (this.extlangs.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.extlangs);
    }

    public String getLanguage() {
        return this.language;
    }

    public String getPrivateuse() {
        return this.privateuse;
    }

    public String getRegion() {
        return this.region;
    }

    public String getScript() {
        return this.script;
    }

    public List<String> getVariants() {
        if (this.variants.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.variants);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.language.length() > 0) {
            stringBuilder.append(this.language);
            for (String string : this.extlangs) {
                stringBuilder.append(SEP);
                stringBuilder.append(string);
            }
            if (this.script.length() > 0) {
                stringBuilder.append(SEP);
                stringBuilder.append(this.script);
            }
            if (this.region.length() > 0) {
                stringBuilder.append(SEP);
                stringBuilder.append(this.region);
            }
            for (String string : this.variants) {
                stringBuilder.append(SEP);
                stringBuilder.append(string);
            }
            for (String string : this.extensions) {
                stringBuilder.append(SEP);
                stringBuilder.append(string);
            }
        }
        if (this.privateuse.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(SEP);
            }
            stringBuilder.append(this.privateuse);
        }
        return stringBuilder.toString();
    }
}

