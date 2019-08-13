/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.locale.AsciiUtil;
import android.icu.util.Output;
import android.icu.util.UResourceBundle;
import android.icu.util.UResourceBundleIterator;
import java.util.AbstractCollection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyTypeData {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static Map<String, Set<String>> BCP47_KEYS;
    static Set<String> DEPRECATED_KEYS;
    static Map<String, Set<String>> DEPRECATED_KEY_TYPES;
    private static final Map<String, KeyData> KEYMAP;
    private static final Object[][] KEY_DATA;
    static Map<String, ValueType> VALUE_TYPES;

    static {
        DEPRECATED_KEYS = Collections.emptySet();
        VALUE_TYPES = Collections.emptyMap();
        DEPRECATED_KEY_TYPES = Collections.emptyMap();
        KEY_DATA = new Object[0][];
        KEYMAP = new HashMap<String, KeyData>();
        KeyTypeData.initFromResourceBundle();
    }

    public static Set<String> getBcp47KeyTypes(String string) {
        return BCP47_KEYS.get(string);
    }

    public static Set<String> getBcp47Keys() {
        return BCP47_KEYS.keySet();
    }

    private static void getKeyInfo(UResourceBundle object) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        LinkedHashMap<String, ValueType> linkedHashMap = new LinkedHashMap<String, ValueType>();
        object = ((UResourceBundle)object).getIterator();
        while (((UResourceBundleIterator)object).hasNext()) {
            Object object2 = ((UResourceBundleIterator)object).next();
            KeyInfoType keyInfoType = KeyInfoType.valueOf(((UResourceBundle)object2).getKey());
            object2 = ((UResourceBundle)object2).getIterator();
            while (((UResourceBundleIterator)object2).hasNext()) {
                Object object3 = ((UResourceBundleIterator)object2).next();
                String string = ((UResourceBundle)object3).getKey();
                object3 = ((UResourceBundle)object3).getString();
                int n = 1.$SwitchMap$android$icu$impl$locale$KeyTypeData$KeyInfoType[keyInfoType.ordinal()];
                if (n != 1) {
                    if (n != 2) continue;
                    linkedHashMap.put(string, ValueType.valueOf((String)object3));
                    continue;
                }
                linkedHashSet.add(string);
            }
        }
        DEPRECATED_KEYS = Collections.unmodifiableSet(linkedHashSet);
        VALUE_TYPES = Collections.unmodifiableMap(linkedHashMap);
    }

    private static void getTypeInfo(UResourceBundle object) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        object = ((UResourceBundle)object).getIterator();
        while (((UResourceBundleIterator)object).hasNext()) {
            Object object2 = ((UResourceBundleIterator)object).next();
            TypeInfoType typeInfoType = TypeInfoType.valueOf(((UResourceBundle)object2).getKey());
            UResourceBundleIterator uResourceBundleIterator = ((UResourceBundle)object2).getIterator();
            while (uResourceBundleIterator.hasNext()) {
                Object object3 = uResourceBundleIterator.next();
                object2 = ((UResourceBundle)object3).getKey();
                LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
                object3 = ((UResourceBundle)object3).getIterator();
                while (((UResourceBundleIterator)object3).hasNext()) {
                    String string = ((UResourceBundleIterator)object3).next().getKey();
                    if (1.$SwitchMap$android$icu$impl$locale$KeyTypeData$TypeInfoType[typeInfoType.ordinal()] != 1) continue;
                    linkedHashSet.add(string);
                }
                linkedHashMap.put(object2, Collections.unmodifiableSet(linkedHashSet));
            }
        }
        DEPRECATED_KEY_TYPES = Collections.unmodifiableMap(linkedHashMap);
    }

    public static ValueType getValueType(String object) {
        block0 : {
            if ((object = VALUE_TYPES.get(object)) != null) break block0;
            object = ValueType.single;
        }
        return object;
    }

    private static void initFromResourceBundle() {
        Object object;
        ICUResourceBundle iCUResourceBundle = ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER, ICUResourceBundle.OpenType.DIRECT);
        KeyTypeData.getKeyInfo(iCUResourceBundle.get("keyInfo"));
        KeyTypeData.getTypeInfo(iCUResourceBundle.get("typeInfo"));
        UResourceBundle uResourceBundle = iCUResourceBundle.get("keyMap");
        Object object2 = iCUResourceBundle.get("typeMap");
        UResourceBundle uResourceBundle2 = null;
        Object object3 = null;
        try {
            object = iCUResourceBundle.get("typeAlias");
            uResourceBundle2 = object;
        }
        catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        try {
            object3 = object = iCUResourceBundle.get("bcpTypeAlias");
        }
        catch (MissingResourceException missingResourceException) {
            // empty catch block
        }
        UResourceBundleIterator uResourceBundleIterator = uResourceBundle.getIterator();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        while (uResourceBundleIterator.hasNext()) {
            Object object4;
            Object object5;
            Set<EnumSet<SpecialType>> set;
            Iterator iterator;
            Object object6;
            boolean bl;
            object = uResourceBundleIterator.next();
            String string = ((UResourceBundle)object).getKey();
            String string2 = ((UResourceBundle)object).getString();
            if (string2.length() == 0) {
                bl = true;
                string2 = string;
            } else {
                bl = false;
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            linkedHashMap.put(string2, Collections.unmodifiableSet(linkedHashSet));
            boolean bl2 = string.equals("timezone");
            HashMap hashMap = null;
            if (uResourceBundle2 != null) {
                object = null;
                try {
                    object = object5 = uResourceBundle2.get(string);
                }
                catch (MissingResourceException missingResourceException) {
                    // empty catch block
                }
                if (object != null) {
                    hashMap = new HashMap();
                    object = ((UResourceBundle)object).getIterator();
                    while (((UResourceBundleIterator)object).hasNext()) {
                        set = ((UResourceBundleIterator)object).next();
                        object5 = ((UResourceBundle)((Object)set)).getKey();
                        iterator = ((UResourceBundle)((Object)set)).getString();
                        if (bl2) {
                            object5 = ((String)object5).replace(':', '/');
                        }
                        object4 = (Set)hashMap.get(iterator);
                        set = object4;
                        if (object4 == null) {
                            set = new HashSet<EnumSet<SpecialType>>();
                            hashMap.put(iterator, (HashSet<EnumSet<SpecialType>>)set);
                        }
                        set.add((EnumSet<SpecialType>)object5);
                    }
                }
            }
            object4 = null;
            if (object3 != null) {
                object = null;
                try {
                    object = object5 = ((UResourceBundle)object3).get(string2);
                }
                catch (MissingResourceException missingResourceException) {
                    // empty catch block
                }
                if (object != null) {
                    object4 = new HashMap();
                    object5 = ((UResourceBundle)object).getIterator();
                    while (((UResourceBundleIterator)object5).hasNext()) {
                        set = ((UResourceBundleIterator)object5).next();
                        iterator = ((UResourceBundle)((Object)set)).getKey();
                        object6 = ((UResourceBundle)((Object)set)).getString();
                        set = (Set)object4.get(object6);
                        if (set == null) {
                            set = new HashSet();
                            object4.put(object6, set);
                        }
                        set.add((EnumSet<SpecialType>)((Object)iterator));
                    }
                }
            }
            HashMap<String, Type> hashMap2 = new HashMap<String, Type>();
            object5 = null;
            set = null;
            object = null;
            try {
                iterator = ((UResourceBundle)object2).get(string);
                object = iterator;
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            if (object != null) {
                object5 = ((UResourceBundle)object).getIterator();
                object = set;
                set = object5;
                while (((UResourceBundleIterator)((Object)set)).hasNext()) {
                    Object object7;
                    object5 = ((UResourceBundleIterator)((Object)set)).next();
                    iterator = ((UResourceBundle)object5).getKey();
                    object6 = ((UResourceBundle)object5).getString();
                    char c = ((String)((Object)iterator)).charAt(0);
                    c = '9' < c && c < 'a' && ((String)object6).length() == 0 ? (char)'\u0001' : '\u0000';
                    if (c != '\u0000') {
                        object5 = object;
                        if (object == null) {
                            object5 = EnumSet.noneOf(SpecialType.class);
                        }
                        ((AbstractCollection)object5).add((SpecialType)SpecialType.valueOf((String)((Object)iterator)));
                        linkedHashSet.add(iterator);
                        object = object5;
                        continue;
                    }
                    object5 = bl2 ? ((String)((Object)iterator)).replace(':', '/') : iterator;
                    c = '\u0000';
                    if (((String)object6).length() == 0) {
                        c = '\u0001';
                        iterator = object5;
                    } else {
                        iterator = object6;
                    }
                    linkedHashSet.add(iterator);
                    object6 = new Type((String)object5, (String)((Object)iterator));
                    hashMap2.put(AsciiUtil.toLowerString((String)object5), (Type)object6);
                    if (c == '\u0000') {
                        hashMap2.put(AsciiUtil.toLowerString((String)((Object)iterator)), (Type)object6);
                    }
                    if (hashMap != null && (object7 = (Set)hashMap.get(object5)) != null) {
                        object7 = object7.iterator();
                        while (object7.hasNext()) {
                            hashMap2.put(AsciiUtil.toLowerString((String)object7.next()), (Type)object6);
                        }
                    }
                    if (object4 == null || (object5 = (Set)object4.get(iterator)) == null) continue;
                    iterator = object5.iterator();
                    while (iterator.hasNext()) {
                        hashMap2.put(AsciiUtil.toLowerString((String)iterator.next()), (Type)object6);
                    }
                }
                object5 = object2;
                object2 = object3;
                object3 = object5;
            } else {
                object = object2;
                object2 = object3;
                object3 = object;
                object = object5;
            }
            object = new KeyData(string, string2, hashMap2, (EnumSet<SpecialType>)object);
            KEYMAP.put(AsciiUtil.toLowerString(string), (KeyData)object);
            if (!bl) {
                KEYMAP.put(AsciiUtil.toLowerString(string2), (KeyData)object);
            }
            object = object3;
            object3 = object2;
            object2 = object;
        }
        BCP47_KEYS = Collections.unmodifiableMap(linkedHashMap);
    }

    /*
     * WARNING - void declaration
     */
    private static void initFromTables() {
        Object object72 = KEY_DATA;
        int n = ((Object[][])object72).length;
        for (int i = 0; i < n; ++i) {
            Object object;
            Object object2;
            void object52;
            Object object3;
            Object[] arrobject;
            Object[] arrobject2 = object72[i];
            String string = (String)arrobject2[0];
            String string2 = (String)arrobject2[1];
            String[][] arrstring = (String[][])arrobject2[2];
            String[][] arrstring2 = (String[][])arrobject2[3];
            Object object4 = (String[][])arrobject2[4];
            boolean bl = false;
            String string3 = string2;
            if (string2 == null) {
                string3 = string;
                bl = true;
            }
            HashMap hashMap = null;
            if (arrstring2 != null) {
                hashMap = new HashMap();
                for (String[] arrstring3 : arrstring2) {
                    void var5_11;
                    object = arrstring3[0];
                    object2 = arrstring3[1];
                    Set set = (Set)hashMap.get(object2);
                    if (set == null) {
                        HashSet hashSet = new HashSet();
                        hashMap.put((String[])object2, hashSet);
                    }
                    var5_11.add(object);
                }
                Object[][] arrobject3 = object72;
            } else {
                Object[][] arrobject4 = object72;
            }
            object72 = null;
            if (object4 != null) {
                object = new HashMap();
                for (Object object72 : object4) {
                    object3 = object72[0];
                    arrobject = object72[1];
                    object72 = object2 = (Set)object.get(arrobject);
                    if (object2 == null) {
                        object72 = new HashSet();
                        object.put(arrobject, object72);
                    }
                    object72.add(object3);
                }
                object72 = object;
            }
            arrobject = new HashMap();
            int n2 = arrstring.length;
            object = null;
            for (int j = 0; j < n2; ++j) {
                Object object5;
                int n3;
                block20 : {
                    object2 = arrstring[j];
                    object4 = object2[0];
                    object3 = object2[1];
                    int n4 = 0;
                    object2 = SpecialType.values();
                    int n5 = ((String[])object2).length;
                    for (n3 = 0; n3 < n5; ++n3) {
                        object5 = object2[n3];
                        if (!((String)object4).equals(((Enum)object5).toString())) continue;
                        n3 = 1;
                        object2 = object;
                        if (object == null) {
                            object2 = new HashSet();
                        }
                        object2.add(object5);
                        object = object2;
                        break block20;
                    }
                    n3 = n4;
                }
                if (n3 != 0) continue;
                if (object3 == null) {
                    n3 = 1;
                    object2 = object4;
                } else {
                    n3 = 0;
                    object2 = object3;
                }
                object3 = new Type((String)object4, (String)object2);
                arrobject.put(AsciiUtil.toLowerString((String)object4), object3);
                if (n3 == 0) {
                    arrobject.put(AsciiUtil.toLowerString((String)object2), object3);
                }
                if ((object5 = (Set)hashMap.get(object4)) != null) {
                    object5 = object5.iterator();
                    while (object5.hasNext()) {
                        arrobject.put(AsciiUtil.toLowerString((String)object5.next()), object3);
                    }
                }
                if ((object2 = (Set)object72.get(object2)) == null) continue;
                object2 = object2.iterator();
                while (object2.hasNext()) {
                    arrobject.put(AsciiUtil.toLowerString((String)object2.next()), object3);
                }
            }
            object72 = null;
            if (object != null) {
                object72 = EnumSet.copyOf(object);
            }
            object72 = new KeyData(string, string3, (Map<String, Type>)arrobject, (EnumSet<SpecialType>)object72);
            KEYMAP.put(AsciiUtil.toLowerString(string), (KeyData)object72);
            if (!bl) {
                KEYMAP.put(AsciiUtil.toLowerString(string3), (KeyData)object72);
            }
            object72 = object52;
        }
    }

    public static boolean isDeprecated(String string) {
        return DEPRECATED_KEYS.contains(string);
    }

    public static boolean isDeprecated(String object, String string) {
        if ((object = DEPRECATED_KEY_TYPES.get(object)) == null) {
            return false;
        }
        return object.contains(string);
    }

    public static String toBcpKey(String object) {
        object = AsciiUtil.toLowerString((String)object);
        if ((object = KEYMAP.get(object)) != null) {
            return ((KeyData)object).bcpId;
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    public static String toBcpType(String string, String object3, Output<Boolean> iterator, Output<Boolean> output) {
        Object object;
        void var3_6;
        Object object2 = false;
        if (object != null) {
            ((Output)object).value = object2;
        }
        if (var3_6 != null) {
            var3_6.value = object2;
        }
        object2 = AsciiUtil.toLowerString(string);
        string = AsciiUtil.toLowerString((String)object3);
        KeyData keyData = KEYMAP.get(object2);
        if (keyData != null) {
            if (object != null) {
                ((Output)object).value = Boolean.TRUE;
            }
            if ((object = keyData.typeMap.get(string)) != null) {
                return ((Type)object).bcpId;
            }
            if (keyData.specialTypes != null) {
                for (SpecialType specialType : keyData.specialTypes) {
                    if (!specialType.handler.isWellFormed(string)) continue;
                    if (var3_6 != null) {
                        var3_6.value = true;
                    }
                    return specialType.handler.canonicalize(string);
                }
            }
        }
        return null;
    }

    public static String toLegacyKey(String object) {
        object = AsciiUtil.toLowerString((String)object);
        if ((object = KEYMAP.get(object)) != null) {
            return ((KeyData)object).legacyId;
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    public static String toLegacyType(String string, String object3, Output<Boolean> iterator, Output<Boolean> output) {
        Object object;
        void var3_6;
        Object object2 = false;
        if (object != null) {
            ((Output)object).value = object2;
        }
        if (var3_6 != null) {
            var3_6.value = object2;
        }
        object2 = AsciiUtil.toLowerString(string);
        string = AsciiUtil.toLowerString((String)object3);
        KeyData keyData = KEYMAP.get(object2);
        if (keyData != null) {
            if (object != null) {
                ((Output)object).value = Boolean.TRUE;
            }
            if ((object = keyData.typeMap.get(string)) != null) {
                return ((Type)object).legacyId;
            }
            if (keyData.specialTypes != null) {
                for (SpecialType specialType : keyData.specialTypes) {
                    if (!specialType.handler.isWellFormed(string)) continue;
                    if (var3_6 != null) {
                        var3_6.value = true;
                    }
                    return specialType.handler.canonicalize(string);
                }
            }
        }
        return null;
    }

    private static class CodepointsTypeHandler
    extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("[0-9a-fA-F]{4,6}(-[0-9a-fA-F]{4,6})*");

        private CodepointsTypeHandler() {
        }

        @Override
        boolean isWellFormed(String string) {
            return pat.matcher(string).matches();
        }
    }

    private static class KeyData {
        String bcpId;
        String legacyId;
        EnumSet<SpecialType> specialTypes;
        Map<String, Type> typeMap;

        KeyData(String string, String string2, Map<String, Type> map, EnumSet<SpecialType> enumSet) {
            this.legacyId = string;
            this.bcpId = string2;
            this.typeMap = map;
            this.specialTypes = enumSet;
        }
    }

    private static enum KeyInfoType {
        deprecated,
        valueType;
        
    }

    private static class PrivateUseKeyValueTypeHandler
    extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("[a-zA-Z0-9]{3,8}(-[a-zA-Z0-9]{3,8})*");

        private PrivateUseKeyValueTypeHandler() {
        }

        @Override
        boolean isWellFormed(String string) {
            return pat.matcher(string).matches();
        }
    }

    private static class ReorderCodeTypeHandler
    extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("[a-zA-Z]{3,8}(-[a-zA-Z]{3,8})*");

        private ReorderCodeTypeHandler() {
        }

        @Override
        boolean isWellFormed(String string) {
            return pat.matcher(string).matches();
        }
    }

    private static class RgKeyValueTypeHandler
    extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("([a-zA-Z]{2}|[0-9]{3})[zZ]{4}");

        private RgKeyValueTypeHandler() {
        }

        @Override
        boolean isWellFormed(String string) {
            return pat.matcher(string).matches();
        }
    }

    private static enum SpecialType {
        CODEPOINTS(new CodepointsTypeHandler()),
        REORDER_CODE(new ReorderCodeTypeHandler()),
        RG_KEY_VALUE(new RgKeyValueTypeHandler()),
        SUBDIVISION_CODE(new SubdivisionKeyValueTypeHandler()),
        PRIVATE_USE(new PrivateUseKeyValueTypeHandler());
        
        SpecialTypeHandler handler;

        private SpecialType(SpecialTypeHandler specialTypeHandler) {
            this.handler = specialTypeHandler;
        }
    }

    private static abstract class SpecialTypeHandler {
        private SpecialTypeHandler() {
        }

        String canonicalize(String string) {
            return AsciiUtil.toLowerString(string);
        }

        abstract boolean isWellFormed(String var1);
    }

    private static class SubdivisionKeyValueTypeHandler
    extends SpecialTypeHandler {
        private static final Pattern pat = Pattern.compile("([a-zA-Z]{2}|[0-9]{3})");

        private SubdivisionKeyValueTypeHandler() {
        }

        @Override
        boolean isWellFormed(String string) {
            return pat.matcher(string).matches();
        }
    }

    private static class Type {
        String bcpId;
        String legacyId;

        Type(String string, String string2) {
            this.legacyId = string;
            this.bcpId = string2;
        }
    }

    private static enum TypeInfoType {
        deprecated;
        
    }

    public static enum ValueType {
        single,
        multiple,
        incremental,
        any;
        
    }

}

