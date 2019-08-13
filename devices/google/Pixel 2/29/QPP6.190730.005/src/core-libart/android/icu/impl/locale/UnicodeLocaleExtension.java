/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import android.icu.impl.locale.AsciiUtil;
import android.icu.impl.locale.Extension;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class UnicodeLocaleExtension
extends Extension {
    public static final UnicodeLocaleExtension CA_JAPANESE;
    private static final SortedMap<String, String> EMPTY_SORTED_MAP;
    private static final SortedSet<String> EMPTY_SORTED_SET;
    public static final UnicodeLocaleExtension NU_THAI;
    public static final char SINGLETON = 'u';
    private SortedSet<String> _attributes = EMPTY_SORTED_SET;
    private SortedMap<String, String> _keywords = EMPTY_SORTED_MAP;

    static {
        EMPTY_SORTED_SET = new TreeSet<String>();
        EMPTY_SORTED_MAP = new TreeMap<String, String>();
        CA_JAPANESE = new UnicodeLocaleExtension();
        UnicodeLocaleExtension.CA_JAPANESE._keywords = new TreeMap<String, String>();
        UnicodeLocaleExtension.CA_JAPANESE._keywords.put("ca", "japanese");
        UnicodeLocaleExtension.CA_JAPANESE._value = "ca-japanese";
        NU_THAI = new UnicodeLocaleExtension();
        UnicodeLocaleExtension.NU_THAI._keywords = new TreeMap<String, String>();
        UnicodeLocaleExtension.NU_THAI._keywords.put("nu", "thai");
        UnicodeLocaleExtension.NU_THAI._value = "nu-thai";
    }

    private UnicodeLocaleExtension() {
        super('u');
    }

    UnicodeLocaleExtension(SortedSet<String> object, SortedMap<String, String> object2) {
        this();
        if (object != null && object.size() > 0) {
            this._attributes = object;
        }
        if (object2 != null && object2.size() > 0) {
            this._keywords = object2;
        }
        if (this._attributes.size() > 0 || this._keywords.size() > 0) {
            object = new StringBuilder();
            for (String object3 : this._attributes) {
                ((StringBuilder)object).append("-");
                ((StringBuilder)object).append(object3);
            }
            for (Map.Entry<String, String> entry : this._keywords.entrySet()) {
                object2 = entry.getKey();
                String string = entry.getValue();
                ((StringBuilder)object).append("-");
                ((StringBuilder)object).append((String)object2);
                if (string.length() <= 0) continue;
                ((StringBuilder)object).append("-");
                ((StringBuilder)object).append(string);
            }
            this._value = ((StringBuilder)object).substring(1);
        }
    }

    public static boolean isAttribute(String string) {
        boolean bl = string.length() >= 3 && string.length() <= 8 && AsciiUtil.isAlphaNumericString(string);
        return bl;
    }

    public static boolean isKey(String string) {
        boolean bl = string.length() == 2 && AsciiUtil.isAlphaNumericString(string);
        return bl;
    }

    public static boolean isSingletonChar(char c) {
        boolean bl = 'u' == AsciiUtil.toLower(c);
        return bl;
    }

    public static boolean isType(String string) {
        int n = 0;
        do {
            int n2;
            String string2 = (n2 = string.indexOf("-", n)) < 0 ? string.substring(n) : string.substring(n, n2);
            boolean bl = UnicodeLocaleExtension.isTypeSubtag(string2);
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            if (n2 < 0) {
                bl = bl2;
                if (true) {
                    bl = bl2;
                    if (n < string.length()) {
                        bl = true;
                    }
                }
                return bl;
            }
            n = n2 + 1;
        } while (true);
    }

    public static boolean isTypeSubtag(String string) {
        boolean bl = string.length() >= 3 && string.length() <= 8 && AsciiUtil.isAlphaNumericString(string);
        return bl;
    }

    public Set<String> getUnicodeLocaleAttributes() {
        return Collections.unmodifiableSet(this._attributes);
    }

    public Set<String> getUnicodeLocaleKeys() {
        return Collections.unmodifiableSet(this._keywords.keySet());
    }

    public String getUnicodeLocaleType(String string) {
        return (String)this._keywords.get(string);
    }
}

