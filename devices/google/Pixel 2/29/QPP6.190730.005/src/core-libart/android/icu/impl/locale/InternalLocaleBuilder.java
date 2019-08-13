/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import android.icu.impl.locale.AsciiUtil;
import android.icu.impl.locale.BaseLocale;
import android.icu.impl.locale.Extension;
import android.icu.impl.locale.LanguageTag;
import android.icu.impl.locale.LocaleExtensions;
import android.icu.impl.locale.LocaleSyntaxException;
import android.icu.impl.locale.StringTokenIterator;
import android.icu.impl.locale.UnicodeLocaleExtension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class InternalLocaleBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean JDKIMPL = false;
    private static final CaseInsensitiveChar PRIVUSE_KEY = new CaseInsensitiveChar("x".charAt(0));
    private HashMap<CaseInsensitiveChar, String> _extensions;
    private String _language = "";
    private String _region = "";
    private String _script = "";
    private HashSet<CaseInsensitiveString> _uattributes;
    private HashMap<CaseInsensitiveString, String> _ukeywords;
    private String _variant = "";

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
            if (AsciiUtil.caseIgnoreMatch(stringTokenIterator.current(), "lvariant")) {
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
        if (object != null && object.size() > 0) {
            HashSet hashSet = new HashSet(object.size());
            object = object.iterator();
            while (object.hasNext()) {
                String string2 = (String)object.next();
                CaseInsensitiveChar caseInsensitiveChar = new CaseInsensitiveChar(string2.charAt(0));
                if (hashSet.contains(caseInsensitiveChar)) continue;
                if (UnicodeLocaleExtension.isSingletonChar(caseInsensitiveChar.value())) {
                    this.setUnicodeLocaleExtension(string2.substring(2));
                    continue;
                }
                if (this._extensions == null) {
                    this._extensions = new HashMap(4);
                }
                this._extensions.put(caseInsensitiveChar, string2.substring(2));
            }
        }
        if (string != null && string.length() > 0) {
            if (this._extensions == null) {
                this._extensions = new HashMap(1);
            }
            this._extensions.put(new CaseInsensitiveChar(string.charAt(0)), string.substring(2));
        }
        return this;
    }

    private void setUnicodeLocaleExtension(String string) {
        HashSet<CaseInsensitiveString> hashSet = this._uattributes;
        if (hashSet != null) {
            hashSet.clear();
        }
        if ((hashSet = this._ukeywords) != null) {
            ((HashMap)((Object)hashSet)).clear();
        }
        StringTokenIterator stringTokenIterator = new StringTokenIterator(string, "-");
        while (!stringTokenIterator.isDone() && UnicodeLocaleExtension.isAttribute(stringTokenIterator.current())) {
            if (this._uattributes == null) {
                this._uattributes = new HashSet(4);
            }
            this._uattributes.add(new CaseInsensitiveString(stringTokenIterator.current()));
            stringTokenIterator.next();
        }
        hashSet = null;
        int n = -1;
        int n2 = -1;
        while (!stringTokenIterator.isDone()) {
            int n3;
            Object object;
            int n4;
            String string2 = "";
            if (hashSet != null) {
                if (UnicodeLocaleExtension.isKey(stringTokenIterator.current())) {
                    object = n == -1 ? "" : string.substring(n, n2);
                    if (this._ukeywords == null) {
                        this._ukeywords = new HashMap(4);
                    }
                    this._ukeywords.put((CaseInsensitiveString)((Object)hashSet), (String)object);
                    hashSet = new CaseInsensitiveString(stringTokenIterator.current());
                    if (this._ukeywords.containsKey(hashSet)) {
                        hashSet = null;
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
                    HashMap<CaseInsensitiveString, String> hashMap = this._ukeywords;
                    hashSet = object;
                    n4 = n;
                    n3 = n2;
                    if (hashMap != null) {
                        hashSet = object;
                        n4 = n;
                        n3 = n2;
                        if (hashMap.containsKey(object)) {
                            hashSet = null;
                            n3 = n2;
                            n4 = n;
                        }
                    }
                }
            }
            if (!stringTokenIterator.hasNext()) {
                if (hashSet == null) break;
                string = n4 == -1 ? string2 : string.substring(n4, n3);
                if (this._ukeywords == null) {
                    this._ukeywords = new HashMap(4);
                }
                this._ukeywords.put((CaseInsensitiveString)((Object)hashSet), string);
                break;
            }
            stringTokenIterator.next();
            n = n4;
            n2 = n3;
        }
    }

    public InternalLocaleBuilder addUnicodeLocaleAttribute(String string) throws LocaleSyntaxException {
        if (string != null && UnicodeLocaleExtension.isAttribute(string)) {
            if (this._uattributes == null) {
                this._uattributes = new HashSet(4);
            }
            this._uattributes.add(new CaseInsensitiveString(string));
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ill-formed Unicode locale attribute: ");
        stringBuilder.append(string);
        throw new LocaleSyntaxException(stringBuilder.toString());
    }

    public InternalLocaleBuilder clear() {
        this._language = "";
        this._script = "";
        this._region = "";
        this._variant = "";
        this.clearExtensions();
        return this;
    }

    public InternalLocaleBuilder clearExtensions() {
        Cloneable cloneable = this._extensions;
        if (cloneable != null) {
            ((HashMap)cloneable).clear();
        }
        if ((cloneable = this._uattributes) != null) {
            ((HashSet)cloneable).clear();
        }
        if ((cloneable = this._ukeywords) != null) {
            ((HashMap)cloneable).clear();
        }
        return this;
    }

    public BaseLocale getBaseLocale() {
        String string = this._language;
        String string2 = this._script;
        String string3 = this._region;
        String string4 = this._variant;
        Object object = this._extensions;
        Object object2 = string4;
        if (object != null) {
            object = ((HashMap)object).get(PRIVUSE_KEY);
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
                    if (AsciiUtil.caseIgnoreMatch(((StringTokenIterator)object2).current(), "lvariant")) {
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
        Cloneable cloneable = this._extensions;
        if (cloneable != null && ((HashMap)cloneable).size() != 0 || (cloneable = this._uattributes) != null && ((HashSet)cloneable).size() != 0 || (cloneable = this._ukeywords) != null && ((HashMap)cloneable).size() != 0) {
            return new LocaleExtensions(this._extensions, this._uattributes, this._ukeywords);
        }
        return LocaleExtensions.EMPTY_EXTENSIONS;
    }

    public InternalLocaleBuilder removeUnicodeLocaleAttribute(String string) throws LocaleSyntaxException {
        if (string != null && UnicodeLocaleExtension.isAttribute(string)) {
            HashSet<CaseInsensitiveString> hashSet = this._uattributes;
            if (hashSet != null) {
                hashSet.remove(new CaseInsensitiveString(string));
            }
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ill-formed Unicode locale attribute: ");
        stringBuilder.append(string);
        throw new LocaleSyntaxException(stringBuilder.toString());
    }

    public InternalLocaleBuilder setExtension(char c, String cloneable) throws LocaleSyntaxException {
        boolean bl = LanguageTag.isPrivateusePrefixChar(c);
        if (!bl && !LanguageTag.isExtensionSingletonChar(c)) {
            cloneable = new StringBuilder();
            ((StringBuilder)((Object)cloneable)).append("Ill-formed extension key: ");
            ((StringBuilder)((Object)cloneable)).append(c);
            throw new LocaleSyntaxException(((StringBuilder)((Object)cloneable)).toString());
        }
        boolean bl2 = cloneable == null || ((String)((Object)cloneable)).length() == 0;
        Object object = new CaseInsensitiveChar(c);
        if (bl2) {
            if (UnicodeLocaleExtension.isSingletonChar(((CaseInsensitiveChar)object).value())) {
                cloneable = this._uattributes;
                if (cloneable != null) {
                    ((HashSet)cloneable).clear();
                }
                if ((cloneable = this._ukeywords) != null) {
                    cloneable.clear();
                }
            } else {
                cloneable = this._extensions;
                if (cloneable != null && cloneable.containsKey(object)) {
                    this._extensions.remove(object);
                }
            }
        } else {
            String string = ((String)((Object)cloneable)).replaceAll("_", "-");
            cloneable = new StringTokenIterator(string, "-");
            while (!((StringTokenIterator)((Object)cloneable)).isDone()) {
                String string2 = ((StringTokenIterator)((Object)cloneable)).current();
                boolean bl3 = bl ? LanguageTag.isPrivateuseSubtag(string2) : LanguageTag.isExtensionSubtag(string2);
                if (bl3) {
                    ((StringTokenIterator)((Object)cloneable)).next();
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Ill-formed extension value: ");
                ((StringBuilder)object).append(string2);
                throw new LocaleSyntaxException(((StringBuilder)object).toString(), ((StringTokenIterator)((Object)cloneable)).currentStart());
            }
            if (UnicodeLocaleExtension.isSingletonChar(((CaseInsensitiveChar)object).value())) {
                this.setUnicodeLocaleExtension(string);
            } else {
                if (this._extensions == null) {
                    this._extensions = new HashMap(4);
                }
                this._extensions.put((CaseInsensitiveChar)object, string);
            }
        }
        return this;
    }

    public InternalLocaleBuilder setExtensions(String object) throws LocaleSyntaxException {
        if (object != null && ((String)object).length() != 0) {
            CharSequence charSequence;
            int n;
            Object object2;
            String string = ((String)object).replaceAll("_", "-");
            StringTokenIterator stringTokenIterator = new StringTokenIterator(string, "-");
            object = null;
            String string2 = null;
            int n2 = 0;
            while (!stringTokenIterator.isDone() && LanguageTag.isExtensionSingleton((String)(object2 = stringTokenIterator.current()))) {
                String string3;
                n = stringTokenIterator.currentStart();
                charSequence = new StringBuilder((String)object2);
                stringTokenIterator.next();
                while (!stringTokenIterator.isDone() && LanguageTag.isExtensionSubtag(string3 = stringTokenIterator.current())) {
                    ((StringBuilder)charSequence).append("-");
                    ((StringBuilder)charSequence).append(string3);
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
            object2 = string2;
            if (!stringTokenIterator.isDone()) {
                charSequence = stringTokenIterator.current();
                object2 = string2;
                if (LanguageTag.isPrivateusePrefix((String)charSequence)) {
                    n = stringTokenIterator.currentStart();
                    object2 = new StringBuilder((String)charSequence);
                    stringTokenIterator.next();
                    while (!stringTokenIterator.isDone() && LanguageTag.isPrivateuseSubtag(string2 = stringTokenIterator.current())) {
                        ((StringBuilder)object2).append("-");
                        ((StringBuilder)object2).append(string2);
                        n2 = stringTokenIterator.currentEnd();
                        stringTokenIterator.next();
                    }
                    if (n2 > n) {
                        object2 = ((StringBuilder)object2).toString();
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
        this.clearExtensions();
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public InternalLocaleBuilder setLanguage(String string) throws LocaleSyntaxException {
        if (string != null && string.length() != 0) {
            if (LanguageTag.isLanguage(string)) {
                this._language = string;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ill-formed language: ");
            stringBuilder.append(string);
            throw new LocaleSyntaxException(stringBuilder.toString(), 0);
        }
        this._language = "";
        return this;
    }

    public InternalLocaleBuilder setLanguageTag(LanguageTag languageTag) {
        CharSequence charSequence;
        this.clear();
        if (languageTag.getExtlangs().size() > 0) {
            this._language = languageTag.getExtlangs().get(0);
        } else {
            charSequence = languageTag.getLanguage();
            if (!((String)charSequence).equals(LanguageTag.UNDETERMINED)) {
                this._language = charSequence;
            }
        }
        this._script = languageTag.getScript();
        this._region = languageTag.getRegion();
        List<String> list = languageTag.getVariants();
        if (list.size() > 0) {
            charSequence = new StringBuilder(list.get(0));
            for (int i = 1; i < list.size(); ++i) {
                ((StringBuilder)charSequence).append("_");
                ((StringBuilder)charSequence).append(list.get(i));
            }
            this._variant = ((StringBuilder)charSequence).toString();
        }
        this.setExtensions(languageTag.getExtensions(), languageTag.getPrivateuse());
        return this;
    }

    public InternalLocaleBuilder setLocale(BaseLocale object, LocaleExtensions object2) throws LocaleSyntaxException {
        int n;
        String object32 = ((BaseLocale)object).getLanguage();
        Object object33 = ((BaseLocale)object).getScript();
        Object object4 = ((BaseLocale)object).getRegion();
        object = ((BaseLocale)object).getVariant();
        if (object32.length() > 0 && !LanguageTag.isLanguage(object32)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Ill-formed language: ");
            ((StringBuilder)object).append(object32);
            throw new LocaleSyntaxException(((StringBuilder)object).toString());
        }
        if (((String)object33).length() > 0 && !LanguageTag.isScript((String)object33)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Ill-formed script: ");
            ((StringBuilder)object).append((String)object33);
            throw new LocaleSyntaxException(((StringBuilder)object).toString());
        }
        if (((String)object4).length() > 0 && !LanguageTag.isRegion((String)object4)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Ill-formed region: ");
            ((StringBuilder)object).append((String)object4);
            throw new LocaleSyntaxException(((StringBuilder)object).toString());
        }
        if (((String)object).length() > 0 && (n = this.checkVariants((String)object, "_")) != -1) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Ill-formed variant: ");
            ((StringBuilder)object2).append((String)object);
            throw new LocaleSyntaxException(((StringBuilder)object2).toString(), n);
        }
        this._language = object32;
        this._script = object33;
        this._region = object4;
        this._variant = object;
        this.clearExtensions();
        object = object2 == null ? null : ((LocaleExtensions)object2).getKeys();
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                object33 = (Character)object.next();
                object4 = ((LocaleExtensions)object2).getExtension((Character)object33);
                if (object4 instanceof UnicodeLocaleExtension) {
                    object4 = (UnicodeLocaleExtension)object4;
                    for (String string : ((UnicodeLocaleExtension)object4).getUnicodeLocaleAttributes()) {
                        if (this._uattributes == null) {
                            this._uattributes = new HashSet(4);
                        }
                        this._uattributes.add(new CaseInsensitiveString(string));
                    }
                    for (Object object33 : ((UnicodeLocaleExtension)object4).getUnicodeLocaleKeys()) {
                        if (this._ukeywords == null) {
                            this._ukeywords = new HashMap(4);
                        }
                        this._ukeywords.put(new CaseInsensitiveString((String)object33), ((UnicodeLocaleExtension)object4).getUnicodeLocaleType((String)object33));
                    }
                    continue;
                }
                if (this._extensions == null) {
                    this._extensions = new HashMap(4);
                }
                this._extensions.put(new CaseInsensitiveChar(((Character)object33).charValue()), ((Extension)object4).getValue());
            }
        }
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public InternalLocaleBuilder setRegion(String string) throws LocaleSyntaxException {
        if (string != null && string.length() != 0) {
            if (LanguageTag.isRegion(string)) {
                this._region = string;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ill-formed region: ");
            stringBuilder.append(string);
            throw new LocaleSyntaxException(stringBuilder.toString(), 0);
        }
        this._region = "";
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public InternalLocaleBuilder setScript(String string) throws LocaleSyntaxException {
        if (string != null && string.length() != 0) {
            if (LanguageTag.isScript(string)) {
                this._script = string;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Ill-formed script: ");
            stringBuilder.append(string);
            throw new LocaleSyntaxException(stringBuilder.toString(), 0);
        }
        this._script = "";
        return this;
    }

    public InternalLocaleBuilder setUnicodeLocaleKeyword(String hashMap, String charSequence) throws LocaleSyntaxException {
        if (UnicodeLocaleExtension.isKey((String)((Object)hashMap))) {
            Object object = new CaseInsensitiveString((String)((Object)hashMap));
            if (charSequence == null) {
                hashMap = this._ukeywords;
                if (hashMap != null) {
                    hashMap.remove(object);
                }
            } else {
                if (((String)charSequence).length() != 0) {
                    hashMap = new StringTokenIterator(((String)charSequence).replaceAll("_", "-"), "-");
                    while (!((StringTokenIterator)((Object)hashMap)).isDone()) {
                        if (UnicodeLocaleExtension.isTypeSubtag(((StringTokenIterator)((Object)hashMap)).current())) {
                            ((StringTokenIterator)((Object)hashMap)).next();
                            continue;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Ill-formed Unicode locale keyword type: ");
                        ((StringBuilder)object).append((String)charSequence);
                        throw new LocaleSyntaxException(((StringBuilder)object).toString(), ((StringTokenIterator)((Object)hashMap)).currentStart());
                    }
                }
                if (this._ukeywords == null) {
                    this._ukeywords = new HashMap(4);
                }
                this._ukeywords.put((CaseInsensitiveString)object, (String)charSequence);
            }
            return this;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Ill-formed Unicode locale keyword key: ");
        ((StringBuilder)charSequence).append((String)((Object)hashMap));
        throw new LocaleSyntaxException(((StringBuilder)charSequence).toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    public InternalLocaleBuilder setVariant(String string) throws LocaleSyntaxException {
        if (string != null && string.length() != 0) {
            CharSequence charSequence = string.replaceAll("-", "_");
            int n = this.checkVariants((String)charSequence, "_");
            if (n == -1) {
                this._variant = charSequence;
                return this;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Ill-formed variant: ");
            ((StringBuilder)charSequence).append(string);
            throw new LocaleSyntaxException(((StringBuilder)charSequence).toString(), n);
        }
        this._variant = "";
        return this;
    }

    static class CaseInsensitiveChar {
        private char _c;

        CaseInsensitiveChar(char c) {
            this._c = c;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof CaseInsensitiveChar)) {
                return false;
            }
            if (this._c != AsciiUtil.toLower(((CaseInsensitiveChar)object).value())) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            return AsciiUtil.toLower(this._c);
        }

        public char value() {
            return this._c;
        }
    }

    static class CaseInsensitiveString {
        private String _s;

        CaseInsensitiveString(String string) {
            this._s = string;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof CaseInsensitiveString)) {
                return false;
            }
            return AsciiUtil.caseIgnoreMatch(this._s, ((CaseInsensitiveString)object).value());
        }

        public int hashCode() {
            return AsciiUtil.toLowerString(this._s).hashCode();
        }

        public String value() {
            return this._s;
        }
    }

}

