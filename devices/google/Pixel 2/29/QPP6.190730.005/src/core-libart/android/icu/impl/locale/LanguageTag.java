/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import android.icu.impl.locale.AsciiUtil;
import android.icu.impl.locale.BaseLocale;
import android.icu.impl.locale.Extension;
import android.icu.impl.locale.LocaleExtensions;
import android.icu.impl.locale.ParseStatus;
import android.icu.impl.locale.StringTokenIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LanguageTag {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Map<AsciiUtil.CaseInsensitiveKey, String[]> GRANDFATHERED;
    private static final boolean JDKIMPL = false;
    public static final String PRIVATEUSE = "x";
    public static final String PRIVUSE_VARIANT_PREFIX = "lvariant";
    public static final String SEP = "-";
    public static String UNDETERMINED;
    private List<String> _extensions = Collections.emptyList();
    private List<String> _extlangs = Collections.emptyList();
    private String _language = "";
    private String _privateuse = "";
    private String _region = "";
    private String _script = "";
    private List<String> _variants = Collections.emptyList();

    static {
        UNDETERMINED = "und";
        GRANDFATHERED = new HashMap<AsciiUtil.CaseInsensitiveKey, String[]>();
        for (String[] arrstring : new String[][]{{"art-lojban", "jbo"}, {"cel-gaulish", "xtg-x-cel-gaulish"}, {"en-GB-oed", "en-GB-x-oed"}, {"i-ami", "ami"}, {"i-bnn", "bnn"}, {"i-default", "en-x-i-default"}, {"i-enochian", "und-x-i-enochian"}, {"i-hak", "hak"}, {"i-klingon", "tlh"}, {"i-lux", "lb"}, {"i-mingo", "see-x-i-mingo"}, {"i-navajo", "nv"}, {"i-pwn", "pwn"}, {"i-tao", "tao"}, {"i-tay", "tay"}, {"i-tsu", "tsu"}, {"no-bok", "nb"}, {"no-nyn", "nn"}, {"sgn-BE-FR", "sfb"}, {"sgn-BE-NL", "vgt"}, {"sgn-CH-DE", "sgg"}, {"zh-guoyu", "cmn"}, {"zh-hakka", "hak"}, {"zh-min", "nan-x-zh-min"}, {"zh-min-nan", "nan"}, {"zh-xiang", "hsn"}}) {
            GRANDFATHERED.put(new AsciiUtil.CaseInsensitiveKey(arrstring[0]), arrstring);
        }
    }

    private LanguageTag() {
    }

    public static String canonicalizeExtension(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizeExtensionSingleton(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizeExtensionSubtag(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizeExtlang(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizeLanguage(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizePrivateuse(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizePrivateuseSubtag(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizeRegion(String string) {
        return AsciiUtil.toUpperString(string);
    }

    public static String canonicalizeScript(String string) {
        return AsciiUtil.toTitleString(string);
    }

    public static String canonicalizeVariant(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static boolean isExtensionSingleton(String string) {
        int n = string.length();
        boolean bl = true;
        if (n != 1 || !AsciiUtil.isAlphaString(string) || AsciiUtil.caseIgnoreMatch(PRIVATEUSE, string)) {
            bl = false;
        }
        return bl;
    }

    public static boolean isExtensionSingletonChar(char c) {
        return LanguageTag.isExtensionSingleton(String.valueOf(c));
    }

    public static boolean isExtensionSubtag(String string) {
        boolean bl = string.length() >= 2 && string.length() <= 8 && AsciiUtil.isAlphaNumericString(string);
        return bl;
    }

    public static boolean isExtlang(String string) {
        boolean bl = string.length() == 3 && AsciiUtil.isAlphaString(string);
        return bl;
    }

    public static boolean isLanguage(String string) {
        boolean bl = string.length() >= 2 && string.length() <= 8 && AsciiUtil.isAlphaString(string);
        return bl;
    }

    public static boolean isPrivateusePrefix(String string) {
        int n = string.length();
        boolean bl = true;
        if (n != 1 || !AsciiUtil.caseIgnoreMatch(PRIVATEUSE, string)) {
            bl = false;
        }
        return bl;
    }

    public static boolean isPrivateusePrefixChar(char c) {
        return AsciiUtil.caseIgnoreMatch(PRIVATEUSE, String.valueOf(c));
    }

    public static boolean isPrivateuseSubtag(String string) {
        int n = string.length();
        boolean bl = true;
        if (n < 1 || string.length() > 8 || !AsciiUtil.isAlphaNumericString(string)) {
            bl = false;
        }
        return bl;
    }

    public static boolean isRegion(String string) {
        boolean bl = string.length() == 2 && AsciiUtil.isAlphaString(string) || string.length() == 3 && AsciiUtil.isNumericString(string);
        return bl;
    }

    public static boolean isScript(String string) {
        boolean bl = string.length() == 4 && AsciiUtil.isAlphaString(string);
        return bl;
    }

    public static boolean isVariant(String string) {
        int n = string.length();
        if (n >= 5 && n <= 8) {
            return AsciiUtil.isAlphaNumericString(string);
        }
        boolean bl = false;
        if (n == 4) {
            if (AsciiUtil.isNumeric(string.charAt(0)) && AsciiUtil.isAlphaNumeric(string.charAt(1)) && AsciiUtil.isAlphaNumeric(string.charAt(2)) && AsciiUtil.isAlphaNumeric(string.charAt(3))) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public static LanguageTag parse(String string, ParseStatus parseStatus) {
        if (parseStatus == null) {
            parseStatus = new ParseStatus();
        } else {
            parseStatus.reset();
        }
        boolean bl = false;
        Object object = GRANDFATHERED.get(new AsciiUtil.CaseInsensitiveKey(string));
        if (object != null) {
            object = new StringTokenIterator(object[1], SEP);
            bl = true;
        } else {
            object = new StringTokenIterator(string, SEP);
        }
        LanguageTag languageTag = new LanguageTag();
        if (languageTag.parseLanguage((StringTokenIterator)object, parseStatus)) {
            if (languageTag._language.length() <= 3) {
                languageTag.parseExtlangs((StringTokenIterator)object, parseStatus);
            }
            languageTag.parseScript((StringTokenIterator)object, parseStatus);
            languageTag.parseRegion((StringTokenIterator)object, parseStatus);
            languageTag.parseVariants((StringTokenIterator)object, parseStatus);
            languageTag.parseExtensions((StringTokenIterator)object, parseStatus);
        }
        languageTag.parsePrivateuse((StringTokenIterator)object, parseStatus);
        if (bl) {
            parseStatus._parseLength = string.length();
        } else if (!((StringTokenIterator)object).isDone() && !parseStatus.isError()) {
            string = ((StringTokenIterator)object).current();
            parseStatus._errorIndex = ((StringTokenIterator)object).currentStart();
            if (string.length() == 0) {
                parseStatus._errorMsg = "Empty subtag";
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid subtag: ");
                ((StringBuilder)object).append(string);
                parseStatus._errorMsg = ((StringBuilder)object).toString();
            }
        }
        return languageTag;
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
                    parseStatus._parseLength = ((StringTokenIterator)object).currentEnd();
                    ((StringTokenIterator)object).next();
                }
                if (parseStatus._parseLength <= n) {
                    parseStatus._errorIndex = n;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Incomplete extension '");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append("'");
                    parseStatus._errorMsg = ((StringBuilder)object).toString();
                    break;
                }
                if (this._extensions.size() == 0) {
                    this._extensions = new ArrayList<String>(4);
                }
                this._extensions.add(stringBuilder.toString());
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
                if (this._extlangs.isEmpty()) {
                    this._extlangs = new ArrayList<String>(3);
                }
                this._extlangs.add(string);
                parseStatus._parseLength = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            } while (this._extlangs.size() != 3);
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
                this._language = string;
                parseStatus._parseLength = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
            return bl;
        }
        return false;
    }

    public static LanguageTag parseLocale(BaseLocale object, LocaleExtensions localeExtensions) {
        Object object2;
        LanguageTag languageTag = new LanguageTag();
        Object object3 = ((BaseLocale)object).getLanguage();
        String string = ((BaseLocale)object).getScript();
        Object object4 = ((BaseLocale)object).getRegion();
        Object object5 = ((BaseLocale)object).getVariant();
        boolean bl = false;
        ArrayList arrayList = null;
        object = object3;
        if (((String)object3).length() > 0) {
            object = object3;
            if (LanguageTag.isLanguage((String)object3)) {
                if (((String)object3).equals("iw")) {
                    object = "he";
                } else if (((String)object3).equals("ji")) {
                    object = "yi";
                } else {
                    object = object3;
                    if (((String)object3).equals("in")) {
                        object = "id";
                    }
                }
                languageTag._language = object;
            }
        }
        boolean bl2 = bl;
        if (string.length() > 0) {
            bl2 = bl;
            if (LanguageTag.isScript(string)) {
                languageTag._script = LanguageTag.canonicalizeScript(string);
                bl2 = true;
            }
        }
        bl = bl2;
        if (((String)object4).length() > 0) {
            bl = bl2;
            if (LanguageTag.isRegion((String)object4)) {
                languageTag._region = LanguageTag.canonicalizeRegion((String)object4);
                bl = true;
            }
        }
        bl2 = bl;
        object4 = arrayList;
        if (((String)object5).length() > 0) {
            object3 = null;
            object2 = new StringTokenIterator((String)object5, "_");
            while (!((StringTokenIterator)object2).isDone() && LanguageTag.isVariant((String)(object4 = ((StringTokenIterator)object2).current()))) {
                object5 = object3;
                if (object3 == null) {
                    object5 = new ArrayList();
                }
                object5.add(LanguageTag.canonicalizeVariant((String)object4));
                ((StringTokenIterator)object2).next();
                object3 = object5;
            }
            if (object3 != null) {
                languageTag._variants = object3;
                bl = true;
            }
            bl2 = bl;
            object4 = arrayList;
            if (!((StringTokenIterator)object2).isDone()) {
                object3 = new StringBuilder();
                while (!((StringTokenIterator)object2).isDone() && LanguageTag.isPrivateuseSubtag((String)(object5 = ((StringTokenIterator)object2).current()))) {
                    if (((StringBuilder)object3).length() > 0) {
                        ((StringBuilder)object3).append(SEP);
                    }
                    ((StringBuilder)object3).append(AsciiUtil.toLowerString((String)object5));
                    ((StringTokenIterator)object2).next();
                }
                bl2 = bl;
                object4 = arrayList;
                if (((StringBuilder)object3).length() > 0) {
                    object4 = ((StringBuilder)object3).toString();
                    bl2 = bl;
                }
            }
        }
        object5 = null;
        object3 = null;
        object2 = localeExtensions.getKeys().iterator();
        arrayList = object;
        while (object2.hasNext()) {
            Character c = (Character)object2.next();
            Extension extension = localeExtensions.getExtension(c);
            if (LanguageTag.isPrivateusePrefixChar(c.charValue())) {
                object3 = extension.getValue();
                object = object5;
            } else {
                object = object5;
                if (object5 == null) {
                    object = new ArrayList();
                }
                object5 = new StringBuilder();
                ((StringBuilder)object5).append(c.toString());
                ((StringBuilder)object5).append(SEP);
                ((StringBuilder)object5).append(extension.getValue());
                object.add(((StringBuilder)object5).toString());
            }
            object5 = object;
        }
        if (object5 != null) {
            languageTag._extensions = object5;
            bl2 = true;
        }
        object = object3;
        if (object4 != null) {
            if (object3 == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("lvariant-");
                ((StringBuilder)object).append((String)object4);
                object = ((StringBuilder)object).toString();
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append((String)object3);
                ((StringBuilder)object).append(SEP);
                ((StringBuilder)object).append(PRIVUSE_VARIANT_PREFIX);
                ((StringBuilder)object).append(SEP);
                ((StringBuilder)object).append(((String)object4).replace("_", SEP));
                object = ((StringBuilder)object).toString();
            }
        }
        if (object != null) {
            languageTag._privateuse = object;
        }
        if (languageTag._language.length() == 0 && (bl2 || object == null)) {
            languageTag._language = UNDETERMINED;
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
                    parseStatus._parseLength = stringTokenIterator.currentEnd();
                    stringTokenIterator.next();
                }
                if (parseStatus._parseLength <= n) {
                    parseStatus._errorIndex = n;
                    parseStatus._errorMsg = "Incomplete privateuse";
                    bl2 = bl;
                } else {
                    this._privateuse = stringBuilder.toString();
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
                this._region = string;
                parseStatus._parseLength = stringTokenIterator.currentEnd();
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
                this._script = string;
                parseStatus._parseLength = stringTokenIterator.currentEnd();
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
                if (this._variants.isEmpty()) {
                    this._variants = new ArrayList<String>(3);
                }
                this._variants.add(string);
                parseStatus._parseLength = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
            return bl;
        }
        return false;
    }

    public List<String> getExtensions() {
        return Collections.unmodifiableList(this._extensions);
    }

    public List<String> getExtlangs() {
        return Collections.unmodifiableList(this._extlangs);
    }

    public String getLanguage() {
        return this._language;
    }

    public String getPrivateuse() {
        return this._privateuse;
    }

    public String getRegion() {
        return this._region;
    }

    public String getScript() {
        return this._script;
    }

    public List<String> getVariants() {
        return Collections.unmodifiableList(this._variants);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this._language.length() > 0) {
            stringBuilder.append(this._language);
            for (String string : this._extlangs) {
                stringBuilder.append(SEP);
                stringBuilder.append(string);
            }
            if (this._script.length() > 0) {
                stringBuilder.append(SEP);
                stringBuilder.append(this._script);
            }
            if (this._region.length() > 0) {
                stringBuilder.append(SEP);
                stringBuilder.append(this._region);
            }
            for (String string : this._variants) {
                stringBuilder.append(SEP);
                stringBuilder.append(string);
            }
            for (String string : this._extensions) {
                stringBuilder.append(SEP);
                stringBuilder.append(string);
            }
        }
        if (this._privateuse.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(SEP);
            }
            stringBuilder.append(this._privateuse);
        }
        return stringBuilder.toString();
    }
}

