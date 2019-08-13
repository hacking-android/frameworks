/*
 * Decompiled with CFR 0.145.
 */
package sun.util.locale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import sun.util.locale.BaseLocale;
import sun.util.locale.Extension;
import sun.util.locale.LanguageTag;
import sun.util.locale.LocaleExtensions;
import sun.util.locale.LocaleSyntaxException;
import sun.util.locale.LocaleUtils;
import sun.util.locale.StringTokenIterator;
import sun.util.locale.UnicodeLocaleExtension;

public final class InternalLocaleBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final CaseInsensitiveChar PRIVATEUSE_KEY = new CaseInsensitiveChar("x");
    private Map<CaseInsensitiveChar, String> extensions;
    private String language = "";
    private String region = "";
    private String script = "";
    private Set<CaseInsensitiveString> uattributes;
    private Map<CaseInsensitiveString, String> ukeywords;
    private String variant = "";

    private int checkVariants(String object, String string) {
        object = new StringTokenIterator((String)object, string);
        while (!((StringTokenIterator)object).isDone()) {
            if (!LanguageTag.isVariant(((StringTokenIterator)object).current())) {
                return ((StringTokenIterator)object).currentStart();
            }
            ((StringTokenIterator)object).next();
        }
        return -1;
    }

    static String removePrivateuseVariant(String string) {
        boolean bl;
        StringTokenIterator stringTokenIterator = new StringTokenIterator(string, "-");
        int n = -1;
        boolean bl2 = false;
        do {
            bl = bl2;
            if (stringTokenIterator.isDone()) break;
            if (n != -1) {
                bl = true;
                break;
            }
            if (LocaleUtils.caseIgnoreMatch(stringTokenIterator.current(), "lvariant")) {
                n = stringTokenIterator.currentStart();
            }
            stringTokenIterator.next();
        } while (true);
        if (!bl) {
            return string;
        }
        string = n == 0 ? null : string.substring(0, n - 1);
        return string;
    }

    private InternalLocaleBuilder setExtensions(List<String> object, String string) {
        this.clearExtensions();
        if (!LocaleUtils.isEmpty(object)) {
            HashSet<CaseInsensitiveChar> hashSet = new HashSet<CaseInsensitiveChar>(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                String string2 = (String)object.next();
                CaseInsensitiveChar caseInsensitiveChar = new CaseInsensitiveChar(string2);
                if (!hashSet.contains(caseInsensitiveChar)) {
                    if (UnicodeLocaleExtension.isSingletonChar(caseInsensitiveChar.value())) {
                        this.setUnicodeLocaleExtension(string2.substring(2));
                    } else {
                        if (this.extensions == null) {
                            this.extensions = new HashMap<CaseInsensitiveChar, String>(4);
                        }
                        this.extensions.put(caseInsensitiveChar, string2.substring(2));
                    }
                }
                hashSet.add(caseInsensitiveChar);
            }
        }
        if (string != null && string.length() > 0) {
            if (this.extensions == null) {
                this.extensions = new HashMap<CaseInsensitiveChar, String>(1);
            }
            this.extensions.put(new CaseInsensitiveChar(string), string.substring(2));
        }
        return this;
    }

    private void setUnicodeLocaleExtension(String string) {
        Set<CaseInsensitiveString> set = this.uattributes;
        if (set != null) {
            set.clear();
        }
        if ((set = this.ukeywords) != null) {
            set.clear();
        }
        StringTokenIterator stringTokenIterator = new StringTokenIterator(string, "-");
        while (!stringTokenIterator.isDone() && UnicodeLocaleExtension.isAttribute(stringTokenIterator.current())) {
            if (this.uattributes == null) {
                this.uattributes = new HashSet<CaseInsensitiveString>(4);
            }
            this.uattributes.add(new CaseInsensitiveString(stringTokenIterator.current()));
            stringTokenIterator.next();
        }
        set = null;
        int n = -1;
        int n2 = -1;
        while (!stringTokenIterator.isDone()) {
            int n3;
            Object object;
            int n4;
            String string2 = "";
            if (set != null) {
                if (UnicodeLocaleExtension.isKey(stringTokenIterator.current())) {
                    object = n == -1 ? "" : string.substring(n, n2);
                    if (this.ukeywords == null) {
                        this.ukeywords = new HashMap<CaseInsensitiveString, String>(4);
                    }
                    this.ukeywords.put((CaseInsensitiveString)((Object)set), (String)object);
                    set = new CaseInsensitiveString(stringTokenIterator.current());
                    if (this.ukeywords.containsKey(set)) {
                        set = null;
                    }
                    n3 = -1;
                    n4 = -1;
                } else {
                    n4 = n;
                    if (n == -1) {
                        n4 = stringTokenIterator.currentStart();
                    }
                    n3 = stringTokenIterator.currentEnd();
                }
            } else {
                n4 = n;
                n3 = n2;
                if (UnicodeLocaleExtension.isKey(stringTokenIterator.current())) {
                    object = new CaseInsensitiveString(stringTokenIterator.current());
                    Map<CaseInsensitiveString, String> map = this.ukeywords;
                    set = object;
                    n4 = n;
                    n3 = n2;
                    if (map != null) {
                        set = object;
                        n4 = n;
                        n3 = n2;
                        if (map.containsKey(object)) {
                            set = null;
                            n3 = n2;
                            n4 = n;
                        }
                    }
                }
            }
            if (!stringTokenIterator.hasNext()) {
                if (set == null) break;
                string = n4 == -1 ? string2 : string.substring(n4, n3);
                if (this.ukeywords == null) {
                    this.ukeywords = new HashMap<CaseInsensitiveString, String>(4);
                }
                this.ukeywords.put((CaseInsensitiveString)((Object)set), string);
                break;
            }
            stringTokenIterator.next();
            n = n4;
            n2 = n3;
        }
    }

    public InternalLocaleBuilder addUnicodeLocaleAttribute(String string) throws LocaleSyntaxException {
        if (UnicodeLocaleExtension.isAttribute(string)) {
            if (this.uattributes == null) {
                this.uattributes = new HashSet<CaseInsensitiveString>(4);
            }
            this.uattributes.add(new CaseInsensitiveString(string));
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ill-formed Unicode locale attribute: ");
        stringBuilder.append(string);
        throw new LocaleSyntaxException(stringBuilder.toString());
    }

    public InternalLocaleBuilder clear() {
        this.language = "";
        this.script = "";
        this.region = "";
        this.variant = "";
        this.clearExtensions();
        return this;
    }

    public InternalLocaleBuilder clearExtensions() {
        Map<Object, String> map = this.extensions;
        if (map != null) {
            map.clear();
        }
        if ((map = this.uattributes) != null) {
            map.clear();
        }
        if ((map = this.ukeywords) != null) {
            map.clear();
        }
        return this;
    }

    public BaseLocale getBaseLocale() {
        String string = this.language;
        String string2 = this.script;
        String string3 = this.region;
        String string4 = this.variant;
        Object object = this.extensions;
        Object object2 = string4;
        if (object != null) {
            object = object.get(PRIVATEUSE_KEY);
            object2 = string4;
            if (object != null) {
                int n;
                object2 = new StringTokenIterator((String)object, "-");
                boolean bl = false;
                int n2 = -1;
                do {
                    n = n2;
                    if (((StringTokenIterator)object2).isDone()) break;
                    if (bl) {
                        n = ((StringTokenIterator)object2).currentStart();
                        break;
                    }
                    if (LocaleUtils.caseIgnoreMatch(((StringTokenIterator)object2).current(), "lvariant")) {
                        bl = true;
                    }
                    ((StringTokenIterator)object2).next();
                } while (true);
                object2 = string4;
                if (n != -1) {
                    object2 = new StringBuilder(string4);
                    if (((StringBuilder)object2).length() != 0) {
                        ((StringBuilder)object2).append("_");
                    }
                    ((StringBuilder)object2).append(((String)object).substring(n).replaceAll("-", "_"));
                    object2 = ((StringBuilder)object2).toString();
                }
            }
        }
        return BaseLocale.getInstance(string, string2, string3, (String)object2);
    }

    public LocaleExtensions getLocaleExtensions() {
        boolean bl = LocaleUtils.isEmpty(this.extensions);
        LocaleExtensions localeExtensions = null;
        if (bl && LocaleUtils.isEmpty(this.uattributes) && LocaleUtils.isEmpty(this.ukeywords)) {
            return null;
        }
        LocaleExtensions localeExtensions2 = new LocaleExtensions(this.extensions, this.uattributes, this.ukeywords);
        if (!localeExtensions2.isEmpty()) {
            localeExtensions = localeExtensions2;
        }
        return localeExtensions;
    }

    public InternalLocaleBuilder removeUnicodeLocaleAttribute(String string) throws LocaleSyntaxException {
        if (string != null && UnicodeLocaleExtension.isAttribute(string)) {
            Set<CaseInsensitiveString> set = this.uattributes;
            if (set != null) {
                set.remove(new CaseInsensitiveString(string));
            }
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ill-formed Unicode locale attribute: ");
        stringBuilder.append(string);
        throw new LocaleSyntaxException(stringBuilder.toString());
    }

    public InternalLocaleBuilder setExtension(char c, String map) throws LocaleSyntaxException {
        boolean bl = LanguageTag.isPrivateusePrefixChar(c);
        if (!bl && !LanguageTag.isExtensionSingletonChar(c)) {
            map = new StringBuilder();
            ((StringBuilder)((Object)map)).append("Ill-formed extension key: ");
            ((StringBuilder)((Object)map)).append(c);
            throw new LocaleSyntaxException(((StringBuilder)((Object)map)).toString());
        }
        boolean bl2 = LocaleUtils.isEmpty((String)((Object)map));
        Object object = new CaseInsensitiveChar(c);
        if (bl2) {
            if (UnicodeLocaleExtension.isSingletonChar(((CaseInsensitiveChar)object).value())) {
                map = this.uattributes;
                if (map != null) {
                    map.clear();
                }
                if ((map = this.ukeywords) != null) {
                    map.clear();
                }
            } else {
                map = this.extensions;
                if (map != null && map.containsKey(object)) {
                    this.extensions.remove(object);
                }
            }
        } else {
            String string = ((String)((Object)map)).replaceAll("_", "-");
            map = new StringTokenIterator(string, "-");
            while (!((StringTokenIterator)((Object)map)).isDone()) {
                String string2 = ((StringTokenIterator)((Object)map)).current();
                bl2 = bl ? LanguageTag.isPrivateuseSubtag(string2) : LanguageTag.isExtensionSubtag(string2);
                if (bl2) {
                    ((StringTokenIterator)((Object)map)).next();
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Ill-formed extension value: ");
                ((StringBuilder)object).append(string2);
                throw new LocaleSyntaxException(((StringBuilder)object).toString(), ((StringTokenIterator)((Object)map)).currentStart());
            }
            if (UnicodeLocaleExtension.isSingletonChar(((CaseInsensitiveChar)object).value())) {
                this.setUnicodeLocaleExtension(string);
            } else {
                if (this.extensions == null) {
                    this.extensions = new HashMap<CaseInsensitiveChar, String>(4);
                }
                this.extensions.put((CaseInsensitiveChar)object, string);
            }
        }
        return this;
    }

    public InternalLocaleBuilder setExtensions(String object) throws LocaleSyntaxException {
        CharSequence charSequence;
        Object object2;
        int n;
        if (LocaleUtils.isEmpty((String)object)) {
            this.clearExtensions();
            return this;
        }
        String string = ((String)object).replaceAll("_", "-");
        StringTokenIterator stringTokenIterator = new StringTokenIterator(string, "-");
        object = null;
        CharSequence charSequence2 = null;
        int n2 = 0;
        while (!stringTokenIterator.isDone() && LanguageTag.isExtensionSingleton((String)(object2 = stringTokenIterator.current()))) {
            String string2;
            n = stringTokenIterator.currentStart();
            charSequence = new StringBuilder((String)object2);
            stringTokenIterator.next();
            while (!stringTokenIterator.isDone() && LanguageTag.isExtensionSubtag(string2 = stringTokenIterator.current())) {
                ((StringBuilder)charSequence).append("-");
                ((StringBuilder)charSequence).append(string2);
                n2 = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
            if (n2 >= n) {
                object2 = object;
                if (object == null) {
                    object2 = new ArrayList(4);
                }
                object2.add(((StringBuilder)charSequence).toString());
                object = object2;
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Incomplete extension '");
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append("'");
            throw new LocaleSyntaxException(((StringBuilder)object).toString(), n);
        }
        object2 = charSequence2;
        if (!stringTokenIterator.isDone()) {
            charSequence = stringTokenIterator.current();
            object2 = charSequence2;
            if (LanguageTag.isPrivateusePrefix((String)charSequence)) {
                n = stringTokenIterator.currentStart();
                charSequence2 = new StringBuilder((String)charSequence);
                stringTokenIterator.next();
                while (!stringTokenIterator.isDone() && LanguageTag.isPrivateuseSubtag((String)(object2 = stringTokenIterator.current()))) {
                    ((StringBuilder)charSequence2).append("-");
                    ((StringBuilder)charSequence2).append((String)object2);
                    n2 = stringTokenIterator.currentEnd();
                    stringTokenIterator.next();
                }
                if (n2 > n) {
                    object2 = ((StringBuilder)charSequence2).toString();
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Incomplete privateuse:");
                    ((StringBuilder)object).append(string.substring(n));
                    throw new LocaleSyntaxException(((StringBuilder)object).toString(), n);
                }
            }
        }
        if (stringTokenIterator.isDone()) {
            return this.setExtensions((List<String>)object, (String)object2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Ill-formed extension subtags:");
        ((StringBuilder)object).append(string.substring(stringTokenIterator.currentStart()));
        throw new LocaleSyntaxException(((StringBuilder)object).toString(), stringTokenIterator.currentStart());
    }

    public InternalLocaleBuilder setLanguage(String string) throws LocaleSyntaxException {
        block4 : {
            block3 : {
                block2 : {
                    if (!LocaleUtils.isEmpty(string)) break block2;
                    this.language = "";
                    break block3;
                }
                if (!LanguageTag.isLanguage(string)) break block4;
                this.language = string;
            }
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ill-formed language: ");
        stringBuilder.append(string);
        throw new LocaleSyntaxException(stringBuilder.toString(), 0);
    }

    public InternalLocaleBuilder setLanguageTag(LanguageTag languageTag) {
        CharSequence charSequence;
        this.clear();
        if (!languageTag.getExtlangs().isEmpty()) {
            this.language = languageTag.getExtlangs().get(0);
        } else {
            charSequence = languageTag.getLanguage();
            if (!((String)charSequence).equals("und")) {
                this.language = charSequence;
            }
        }
        this.script = languageTag.getScript();
        this.region = languageTag.getRegion();
        List<String> list = languageTag.getVariants();
        if (!list.isEmpty()) {
            charSequence = new StringBuilder(list.get(0));
            int n = list.size();
            for (int i = 1; i < n; ++i) {
                ((StringBuilder)charSequence).append("_");
                ((StringBuilder)charSequence).append(list.get(i));
            }
            this.variant = ((StringBuilder)charSequence).toString();
        }
        this.setExtensions(languageTag.getExtensions(), languageTag.getPrivateuse());
        return this;
    }

    public InternalLocaleBuilder setLocale(BaseLocale object, LocaleExtensions localeExtensions) throws LocaleSyntaxException {
        int n;
        Object object2;
        Object object32 = ((BaseLocale)object).getLanguage();
        String string = ((BaseLocale)object).getScript();
        String string2 = ((BaseLocale)object).getRegion();
        String object42 = ((BaseLocale)object).getVariant();
        if (((String)object32).equals("ja") && string2.equals("JP") && object42.equals("JP")) {
            object = "";
            object2 = object32;
        } else if (((String)object32).equals("th") && string2.equals("TH") && object42.equals("TH")) {
            object = "";
            object2 = object32;
        } else {
            object2 = object32;
            object = object42;
            if (((String)object32).equals("no")) {
                object2 = object32;
                object = object42;
                if (string2.equals("NO")) {
                    object2 = object32;
                    object = object42;
                    if (object42.equals("NY")) {
                        object2 = "nn";
                        object = "";
                    }
                }
            }
        }
        if (((String)object2).length() > 0 && !LanguageTag.isLanguage((String)object2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Ill-formed language: ");
            ((StringBuilder)object).append((String)object2);
            throw new LocaleSyntaxException(((StringBuilder)object).toString());
        }
        if (string.length() > 0 && !LanguageTag.isScript(string)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Ill-formed script: ");
            ((StringBuilder)object).append(string);
            throw new LocaleSyntaxException(((StringBuilder)object).toString());
        }
        if (string2.length() > 0 && !LanguageTag.isRegion(string2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Ill-formed region: ");
            ((StringBuilder)object).append(string2);
            throw new LocaleSyntaxException(((StringBuilder)object).toString());
        }
        object32 = object;
        if (((String)object).length() > 0 && (n = this.checkVariants((String)(object32 = ((String)object).replaceAll("-", "_")), "_")) != -1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Ill-formed variant: ");
            ((StringBuilder)object).append((String)object32);
            throw new LocaleSyntaxException(((StringBuilder)object).toString(), n);
        }
        this.language = object2;
        this.script = string;
        this.region = string2;
        this.variant = object32;
        this.clearExtensions();
        object = localeExtensions == null ? null : localeExtensions.getKeys();
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                object32 = (Character)object.next();
                object2 = localeExtensions.getExtension((Character)object32);
                if (object2 instanceof UnicodeLocaleExtension) {
                    object2 = (UnicodeLocaleExtension)object2;
                    for (String string3 : ((UnicodeLocaleExtension)object2).getUnicodeLocaleAttributes()) {
                        if (this.uattributes == null) {
                            this.uattributes = new HashSet<CaseInsensitiveString>(4);
                        }
                        this.uattributes.add(new CaseInsensitiveString(string3));
                    }
                    for (Object object32 : ((UnicodeLocaleExtension)object2).getUnicodeLocaleKeys()) {
                        if (this.ukeywords == null) {
                            this.ukeywords = new HashMap<CaseInsensitiveString, String>(4);
                        }
                        this.ukeywords.put(new CaseInsensitiveString((String)object32), ((UnicodeLocaleExtension)object2).getUnicodeLocaleType((String)object32));
                    }
                    continue;
                }
                if (this.extensions == null) {
                    this.extensions = new HashMap<CaseInsensitiveChar, String>(4);
                }
                this.extensions.put(new CaseInsensitiveChar(((Character)object32).charValue()), ((Extension)object2).getValue());
            }
        }
        return this;
    }

    public InternalLocaleBuilder setRegion(String string) throws LocaleSyntaxException {
        block4 : {
            block3 : {
                block2 : {
                    if (!LocaleUtils.isEmpty(string)) break block2;
                    this.region = "";
                    break block3;
                }
                if (!LanguageTag.isRegion(string)) break block4;
                this.region = string;
            }
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ill-formed region: ");
        stringBuilder.append(string);
        throw new LocaleSyntaxException(stringBuilder.toString(), 0);
    }

    public InternalLocaleBuilder setScript(String string) throws LocaleSyntaxException {
        block4 : {
            block3 : {
                block2 : {
                    if (!LocaleUtils.isEmpty(string)) break block2;
                    this.script = "";
                    break block3;
                }
                if (!LanguageTag.isScript(string)) break block4;
                this.script = string;
            }
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ill-formed script: ");
        stringBuilder.append(string);
        throw new LocaleSyntaxException(stringBuilder.toString(), 0);
    }

    public InternalLocaleBuilder setUnicodeLocaleKeyword(String object, String map) throws LocaleSyntaxException {
        if (UnicodeLocaleExtension.isKey((String)object)) {
            object = new CaseInsensitiveString((String)object);
            if (map == null) {
                map = this.ukeywords;
                if (map != null) {
                    map.remove(object);
                }
            } else {
                if (((String)((Object)map)).length() != 0) {
                    StringTokenIterator stringTokenIterator = new StringTokenIterator(((String)((Object)map)).replaceAll("_", "-"), "-");
                    while (!stringTokenIterator.isDone()) {
                        if (UnicodeLocaleExtension.isTypeSubtag(stringTokenIterator.current())) {
                            stringTokenIterator.next();
                            continue;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Ill-formed Unicode locale keyword type: ");
                        ((StringBuilder)object).append((String)((Object)map));
                        throw new LocaleSyntaxException(((StringBuilder)object).toString(), stringTokenIterator.currentStart());
                    }
                }
                if (this.ukeywords == null) {
                    this.ukeywords = new HashMap<CaseInsensitiveString, String>(4);
                }
                this.ukeywords.put((CaseInsensitiveString)object, (String)((Object)map));
            }
            return this;
        }
        map = new StringBuilder();
        ((StringBuilder)((Object)map)).append("Ill-formed Unicode locale keyword key: ");
        ((StringBuilder)((Object)map)).append((String)object);
        throw new LocaleSyntaxException(((StringBuilder)((Object)map)).toString());
    }

    public InternalLocaleBuilder setVariant(String string) throws LocaleSyntaxException {
        int n;
        CharSequence charSequence;
        block4 : {
            block3 : {
                block2 : {
                    if (!LocaleUtils.isEmpty(string)) break block2;
                    this.variant = "";
                    break block3;
                }
                charSequence = string.replaceAll("-", "_");
                n = this.checkVariants((String)charSequence, "_");
                if (n != -1) break block4;
                this.variant = charSequence;
            }
            return this;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Ill-formed variant: ");
        ((StringBuilder)charSequence).append(string);
        throw new LocaleSyntaxException(((StringBuilder)charSequence).toString(), n);
    }

    static final class CaseInsensitiveChar {
        private final char ch;
        private final char lowerCh;

        CaseInsensitiveChar(char c) {
            this.ch = c;
            this.lowerCh = LocaleUtils.toLower(this.ch);
        }

        private CaseInsensitiveChar(String string) {
            this(string.charAt(0));
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof CaseInsensitiveChar)) {
                return false;
            }
            if (this.lowerCh != ((CaseInsensitiveChar)object).lowerCh) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            return this.lowerCh;
        }

        public char value() {
            return this.ch;
        }
    }

    static final class CaseInsensitiveString {
        private final String lowerStr;
        private final String str;

        CaseInsensitiveString(String string) {
            this.str = string;
            this.lowerStr = LocaleUtils.toLowerString(string);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof CaseInsensitiveString)) {
                return false;
            }
            return this.lowerStr.equals(((CaseInsensitiveString)object).lowerStr);
        }

        public int hashCode() {
            return this.lowerStr.hashCode();
        }

        public String value() {
            return this.str;
        }
    }

}

