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
import java.util.TreeMap;
import java.util.TreeSet;
import sun.util.locale.Extension;
import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LanguageTag;
import sun.util.locale.LocaleUtils;
import sun.util.locale.UnicodeLocaleExtension;

public class LocaleExtensions {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final LocaleExtensions CALENDAR_JAPANESE = new LocaleExtensions("u-ca-japanese", Character.valueOf('u'), UnicodeLocaleExtension.CA_JAPANESE);
    public static final LocaleExtensions NUMBER_THAI = new LocaleExtensions("u-nu-thai", Character.valueOf('u'), UnicodeLocaleExtension.NU_THAI);
    private final Map<Character, Extension> extensionMap;
    private final String id;

    private LocaleExtensions(String string, Character c, Extension extension) {
        this.id = string;
        this.extensionMap = Collections.singletonMap(c, extension);
    }

    /*
     * WARNING - void declaration
     */
    LocaleExtensions(Map<InternalLocaleBuilder.CaseInsensitiveChar, String> object5, Set<InternalLocaleBuilder.CaseInsensitiveString> object2, Map<InternalLocaleBuilder.CaseInsensitiveString, String> object3) {
        Map.Entry entry;
        Object object;
        Iterator iterator;
        boolean bl = LocaleUtils.isEmpty(object5) ^ true;
        boolean bl2 = LocaleUtils.isEmpty(entry) ^ true;
        boolean bl3 = LocaleUtils.isEmpty(iterator) ^ true;
        if (!(bl || bl2 || bl3)) {
            this.id = "";
            this.extensionMap = Collections.emptyMap();
            return;
        }
        TreeMap<Character, Extension> treeMap = new TreeMap<Character, Extension>();
        if (bl) {
            for (Map.Entry entry2 : object5.entrySet()) {
                void var1_6;
                char c = LocaleUtils.toLower(((InternalLocaleBuilder.CaseInsensitiveChar)entry2.getKey()).value());
                Object object4 = object = (String)entry2.getValue();
                if (LanguageTag.isPrivateusePrefixChar(c)) {
                    Object object6 = object = InternalLocaleBuilder.removePrivateuseVariant((String)object);
                    if (object == null) continue;
                }
                treeMap.put(Character.valueOf(c), new Extension(c, LocaleUtils.toLowerString((String)var1_6)));
            }
        }
        if (bl2 || bl3) {
            void var1_8;
            Object var1_7 = null;
            object = null;
            if (bl2) {
                TreeSet treeSet = new TreeSet();
                entry = entry.iterator();
                do {
                    Object object7 = treeSet;
                    if (!entry.hasNext()) break;
                    treeSet.add(LocaleUtils.toLowerString(((InternalLocaleBuilder.CaseInsensitiveString)entry.next()).value()));
                } while (true);
            }
            entry = object;
            if (bl3) {
                object = new TreeMap();
                iterator = iterator.entrySet().iterator();
                do {
                    entry = object;
                    if (!iterator.hasNext()) break;
                    entry = iterator.next();
                    object.put(LocaleUtils.toLowerString(((InternalLocaleBuilder.CaseInsensitiveString)entry.getKey()).value()), LocaleUtils.toLowerString((String)entry.getValue()));
                } while (true);
            }
            treeMap.put(Character.valueOf('u'), new UnicodeLocaleExtension((SortedSet<String>)var1_8, (SortedMap<String, String>)((Object)entry)));
        }
        if (treeMap.isEmpty()) {
            this.id = "";
            this.extensionMap = Collections.emptyMap();
        } else {
            this.id = LocaleExtensions.toID(treeMap);
            this.extensionMap = treeMap;
        }
    }

    public static boolean isValidKey(char c) {
        boolean bl = LanguageTag.isExtensionSingletonChar(c) || LanguageTag.isPrivateusePrefixChar(c);
        return bl;
    }

    public static boolean isValidUnicodeLocaleKey(String string) {
        return UnicodeLocaleExtension.isKey(string);
    }

    private static String toID(SortedMap<Character, Extension> object) {
        StringBuilder stringBuilder = new StringBuilder();
        Object object2 = null;
        Iterator<Map.Entry<Character, Extension>> iterator = object.entrySet().iterator();
        object = object2;
        while (iterator.hasNext()) {
            object2 = iterator.next();
            char c = object2.getKey().charValue();
            object2 = object2.getValue();
            if (LanguageTag.isPrivateusePrefixChar(c)) {
                object = object2;
                continue;
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.append("-");
            }
            stringBuilder.append(object2);
        }
        if (object != null) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("-");
            }
            stringBuilder.append(object);
        }
        return stringBuilder.toString();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LocaleExtensions)) {
            return false;
        }
        return this.id.equals(((LocaleExtensions)object).id);
    }

    public Extension getExtension(Character c) {
        return this.extensionMap.get(Character.valueOf(LocaleUtils.toLower(c.charValue())));
    }

    public String getExtensionValue(Character object) {
        if ((object = this.extensionMap.get(Character.valueOf(LocaleUtils.toLower(((Character)object).charValue())))) == null) {
            return null;
        }
        return ((Extension)object).getValue();
    }

    public String getID() {
        return this.id;
    }

    public Set<Character> getKeys() {
        if (this.extensionMap.isEmpty()) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(this.extensionMap.keySet());
    }

    public Set<String> getUnicodeLocaleAttributes() {
        Extension extension = this.extensionMap.get(Character.valueOf('u'));
        if (extension == null) {
            return Collections.emptySet();
        }
        return ((UnicodeLocaleExtension)extension).getUnicodeLocaleAttributes();
    }

    public Set<String> getUnicodeLocaleKeys() {
        Extension extension = this.extensionMap.get(Character.valueOf('u'));
        if (extension == null) {
            return Collections.emptySet();
        }
        return ((UnicodeLocaleExtension)extension).getUnicodeLocaleKeys();
    }

    public String getUnicodeLocaleType(String string) {
        Extension extension = this.extensionMap.get(Character.valueOf('u'));
        if (extension == null) {
            return null;
        }
        return ((UnicodeLocaleExtension)extension).getUnicodeLocaleType(LocaleUtils.toLowerString(string));
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public boolean isEmpty() {
        return this.extensionMap.isEmpty();
    }

    public String toString() {
        return this.id;
    }
}

