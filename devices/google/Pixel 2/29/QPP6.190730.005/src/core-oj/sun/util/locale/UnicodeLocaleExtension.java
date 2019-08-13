/*
 * Decompiled with CFR 0.145.
 */
package sun.util.locale;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import sun.util.locale.Extension;
import sun.util.locale.LocaleUtils;

public class UnicodeLocaleExtension
extends Extension {
    public static final UnicodeLocaleExtension CA_JAPANESE = new UnicodeLocaleExtension("ca", "japanese");
    public static final UnicodeLocaleExtension NU_THAI = new UnicodeLocaleExtension("nu", "thai");
    public static final char SINGLETON = 'u';
    private final Set<String> attributes;
    private final Map<String, String> keywords;

    private UnicodeLocaleExtension(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("-");
        stringBuilder.append(string2);
        super('u', stringBuilder.toString());
        this.attributes = Collections.emptySet();
        this.keywords = Collections.singletonMap(string, string2);
    }

    UnicodeLocaleExtension(SortedSet<String> object, SortedMap<String, String> iterator2) {
        super('u');
        this.attributes = object != null ? object : Collections.emptySet();
        this.keywords = iterator2 != null ? iterator2 : Collections.emptyMap();
        if (!this.attributes.isEmpty() || !this.keywords.isEmpty()) {
            object = new StringBuilder();
            for (String string : this.attributes) {
                ((StringBuilder)object).append("-");
                ((StringBuilder)object).append(string);
            }
            for (Map.Entry<String, String> entry : this.keywords.entrySet()) {
                String string = entry.getKey();
                String string2 = entry.getValue();
                ((StringBuilder)object).append("-");
                ((StringBuilder)object).append(string);
                if (string2.length() <= 0) continue;
                ((StringBuilder)object).append("-");
                ((StringBuilder)object).append(string2);
            }
            this.setValue(((StringBuilder)object).substring(1));
        }
    }

    public static boolean isAttribute(String string) {
        int n = string.length();
        boolean bl = n >= 3 && n <= 8 && LocaleUtils.isAlphaNumericString(string);
        return bl;
    }

    public static boolean isKey(String string) {
        boolean bl = string.length() == 2 && LocaleUtils.isAlphaNumericString(string);
        return bl;
    }

    public static boolean isSingletonChar(char c) {
        boolean bl = 'u' == LocaleUtils.toLower(c);
        return bl;
    }

    public static boolean isTypeSubtag(String string) {
        int n = string.length();
        boolean bl = n >= 3 && n <= 8 && LocaleUtils.isAlphaNumericString(string);
        return bl;
    }

    public Set<String> getUnicodeLocaleAttributes() {
        if (this.attributes == Collections.EMPTY_SET) {
            return this.attributes;
        }
        return Collections.unmodifiableSet(this.attributes);
    }

    public Set<String> getUnicodeLocaleKeys() {
        if (this.keywords == Collections.EMPTY_MAP) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(this.keywords.keySet());
    }

    public String getUnicodeLocaleType(String string) {
        return this.keywords.get(string);
    }
}

