/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import android.icu.impl.locale.AsciiUtil;
import android.icu.impl.locale.Extension;
import android.icu.impl.locale.InternalLocaleBuilder;
import android.icu.impl.locale.LanguageTag;
import android.icu.impl.locale.UnicodeLocaleExtension;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class LocaleExtensions {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final LocaleExtensions CALENDAR_JAPANESE;
    public static final LocaleExtensions EMPTY_EXTENSIONS;
    private static final SortedMap<Character, Extension> EMPTY_MAP;
    public static final LocaleExtensions NUMBER_THAI;
    private String _id;
    private SortedMap<Character, Extension> _map;

    static {
        EMPTY_MAP = Collections.unmodifiableSortedMap(new TreeMap());
        LocaleExtensions localeExtensions = EMPTY_EXTENSIONS = new LocaleExtensions();
        localeExtensions._id = "";
        localeExtensions._map = EMPTY_MAP;
        localeExtensions = CALENDAR_JAPANESE = new LocaleExtensions();
        localeExtensions._id = "u-ca-japanese";
        localeExtensions._map = new TreeMap<Character, Extension>();
        LocaleExtensions.CALENDAR_JAPANESE._map.put(Character.valueOf('u'), UnicodeLocaleExtension.CA_JAPANESE);
        localeExtensions = NUMBER_THAI = new LocaleExtensions();
        localeExtensions._id = "u-nu-thai";
        localeExtensions._map = new TreeMap<Character, Extension>();
        LocaleExtensions.NUMBER_THAI._map.put(Character.valueOf('u'), UnicodeLocaleExtension.NU_THAI);
    }

    private LocaleExtensions() {
    }

    /*
     * WARNING - void declaration
     */
    LocaleExtensions(Map<InternalLocaleBuilder.CaseInsensitiveChar, String> object5, Set<InternalLocaleBuilder.CaseInsensitiveString> object2, Map<InternalLocaleBuilder.CaseInsensitiveString, String> object3) {
        Object object;
        Map.Entry entry;
        Iterator iterator;
        boolean bl = true;
        boolean bl2 = object5 != null && object5.size() > 0;
        boolean bl3 = entry != null && entry.size() > 0;
        if (iterator == null || iterator.size() <= 0) {
            bl = false;
        }
        if (!(bl2 || bl3 || bl)) {
            this._map = EMPTY_MAP;
            this._id = "";
            return;
        }
        this._map = new TreeMap<Character, Extension>();
        if (bl2) {
            for (Map.Entry entry2 : object5.entrySet()) {
                void var1_6;
                char c = AsciiUtil.toLower(((InternalLocaleBuilder.CaseInsensitiveChar)entry2.getKey()).value());
                Object object4 = object = (String)entry2.getValue();
                if (LanguageTag.isPrivateusePrefixChar(c)) {
                    Object object6 = object = InternalLocaleBuilder.removePrivateuseVariant((String)object);
                    if (object == null) continue;
                }
                Extension extension = new Extension(c, AsciiUtil.toLowerString((String)var1_6));
                this._map.put(Character.valueOf(c), extension);
            }
        }
        if (bl3 || bl) {
            void var1_9;
            Object var1_8 = null;
            object = null;
            if (bl3) {
                TreeSet treeSet = new TreeSet();
                entry = entry.iterator();
                do {
                    TreeSet treeSet2 = treeSet;
                    if (!entry.hasNext()) break;
                    treeSet.add(AsciiUtil.toLowerString(((InternalLocaleBuilder.CaseInsensitiveString)entry.next()).value()));
                } while (true);
            }
            entry = object;
            if (bl) {
                object = new TreeMap();
                iterator = iterator.entrySet().iterator();
                do {
                    entry = object;
                    if (!iterator.hasNext()) break;
                    entry = iterator.next();
                    ((TreeMap)object).put(AsciiUtil.toLowerString(((InternalLocaleBuilder.CaseInsensitiveString)entry.getKey()).value()), AsciiUtil.toLowerString((String)entry.getValue()));
                } while (true);
            }
            UnicodeLocaleExtension unicodeLocaleExtension = new UnicodeLocaleExtension((SortedSet<String>)var1_9, (SortedMap<String, String>)((Object)entry));
            this._map.put(Character.valueOf('u'), unicodeLocaleExtension);
        }
        if (this._map.size() == 0) {
            this._map = EMPTY_MAP;
            this._id = "";
        } else {
            this._id = LocaleExtensions.toID(this._map);
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
        return this._id.equals(((LocaleExtensions)object)._id);
    }

    public Extension getExtension(Character c) {
        return (Extension)this._map.get(Character.valueOf(AsciiUtil.toLower(c.charValue())));
    }

    public String getExtensionValue(Character object) {
        if ((object = (Extension)this._map.get(Character.valueOf(AsciiUtil.toLower(((Character)object).charValue())))) == null) {
            return null;
        }
        return ((Extension)object).getValue();
    }

    public String getID() {
        return this._id;
    }

    public Set<Character> getKeys() {
        return Collections.unmodifiableSet(this._map.keySet());
    }

    public Set<String> getUnicodeLocaleAttributes() {
        Extension extension = (Extension)this._map.get(Character.valueOf('u'));
        if (extension == null) {
            return Collections.emptySet();
        }
        return ((UnicodeLocaleExtension)extension).getUnicodeLocaleAttributes();
    }

    public Set<String> getUnicodeLocaleKeys() {
        Extension extension = (Extension)this._map.get(Character.valueOf('u'));
        if (extension == null) {
            return Collections.emptySet();
        }
        return ((UnicodeLocaleExtension)extension).getUnicodeLocaleKeys();
    }

    public String getUnicodeLocaleType(String string) {
        Extension extension = (Extension)this._map.get(Character.valueOf('u'));
        if (extension == null) {
            return null;
        }
        return ((UnicodeLocaleExtension)extension).getUnicodeLocaleType(AsciiUtil.toLowerString(string));
    }

    public int hashCode() {
        return this._id.hashCode();
    }

    public boolean isEmpty() {
        return this._map.isEmpty();
    }

    public String toString() {
        return this._id;
    }
}

