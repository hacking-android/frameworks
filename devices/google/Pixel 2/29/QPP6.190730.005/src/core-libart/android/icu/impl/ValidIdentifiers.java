/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.StringRange;
import android.icu.impl.locale.AsciiUtil;
import android.icu.util.UResourceBundle;
import android.icu.util.UResourceBundleIterator;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ValidIdentifiers {
    public static Map<Datatype, Map<Datasubtype, ValiditySet>> getData() {
        return ValidityData.data;
    }

    public static Datasubtype isValid(Datatype object, Set<Datasubtype> object2, String string) {
        if ((object = ValidityData.data.get(object)) != null) {
            object2 = object2.iterator();
            while (object2.hasNext()) {
                Datasubtype datasubtype = (Datasubtype)((Object)object2.next());
                ValiditySet validitySet = (ValiditySet)object.get((Object)datasubtype);
                if (validitySet == null || !validitySet.contains(AsciiUtil.toLowerString(string))) continue;
                return datasubtype;
            }
        }
        return null;
    }

    public static Datasubtype isValid(Datatype object, Set<Datasubtype> object2, String string, String string2) {
        if ((object = ValidityData.data.get(object)) != null) {
            string = AsciiUtil.toLowerString(string);
            string2 = AsciiUtil.toLowerString(string2);
            Iterator<Datasubtype> iterator = object2.iterator();
            while (iterator.hasNext()) {
                Datasubtype datasubtype = iterator.next();
                object2 = (ValiditySet)object.get((Object)datasubtype);
                if (object2 == null || !((ValiditySet)object2).contains(string, string2)) continue;
                return datasubtype;
            }
        }
        return null;
    }

    public static enum Datasubtype {
        deprecated,
        private_use,
        regular,
        special,
        unknown,
        macroregion;
        
    }

    public static enum Datatype {
        currency,
        language,
        region,
        script,
        subdivision,
        unit,
        variant,
        u,
        t,
        x,
        illegal;
        
    }

    private static class ValidityData {
        static final Map<Datatype, Map<Datasubtype, ValiditySet>> data;

        static {
            EnumMap enumMap = new EnumMap(Datatype.class);
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
            UResourceBundleIterator uResourceBundleIterator = uResourceBundle.get("idValidity").getIterator();
            while (uResourceBundleIterator.hasNext()) {
                Object object = uResourceBundleIterator.next();
                Datatype datatype = Datatype.valueOf(((UResourceBundle)object).getKey());
                EnumMap<Datasubtype, ValiditySet> enumMap2 = new EnumMap<Datasubtype, ValiditySet>(Datasubtype.class);
                object = ((UResourceBundle)object).getIterator();
                while (((UResourceBundleIterator)object).hasNext()) {
                    String[] arrstring = ((UResourceBundleIterator)object).next();
                    Datasubtype datasubtype = Datasubtype.valueOf(arrstring.getKey());
                    HashSet<String> hashSet = new HashSet<String>();
                    if (arrstring.getType() == 0) {
                        ValidityData.addRange(arrstring.getString(), hashSet);
                    } else {
                        arrstring = arrstring.getStringArray();
                        int n = arrstring.length;
                        for (int i = 0; i < n; ++i) {
                            ValidityData.addRange(arrstring[i], hashSet);
                        }
                    }
                    boolean bl = datatype == Datatype.subdivision;
                    enumMap2.put(datasubtype, new ValiditySet(hashSet, bl));
                }
                enumMap.put(datatype, Collections.unmodifiableMap(enumMap2));
            }
            data = Collections.unmodifiableMap(enumMap);
        }

        private ValidityData() {
        }

        private static void addRange(String string, Set<String> set) {
            int n = (string = AsciiUtil.toLowerString(string)).indexOf(126);
            if (n < 0) {
                set.add(string);
            } else {
                StringRange.expand(string.substring(0, n), string.substring(n + 1), false, set);
            }
        }
    }

    public static class ValiditySet {
        public final Set<String> regularData;
        public final Map<String, Set<String>> subdivisionData;

        public ValiditySet(Set<String> set22, boolean bl) {
            if (bl) {
                HashSet<String> hashSet;
                HashMap hashMap = new HashMap();
                for (Set set22 : set22) {
                    int n = ((String)((Object)set22)).indexOf(45);
                    int n2 = n + 1;
                    int n3 = n;
                    if (n < 0) {
                        n2 = ((String)((Object)set22)).charAt(0) < 'A' ? 3 : 2;
                        n3 = n2;
                    }
                    String object = ((String)((Object)set22)).substring(0, n3);
                    String string = ((String)((Object)set22)).substring(n2);
                    hashSet = (Set)hashMap.get(object);
                    set22 = hashSet;
                    if (hashSet == null) {
                        hashSet = new HashSet<String>();
                        set22 = hashSet;
                        hashMap.put(object, hashSet);
                    }
                    set22.add((String)string);
                }
                this.regularData = null;
                hashSet = new HashMap();
                for (Map.Entry entry : hashMap.entrySet()) {
                    set22 = (Set)entry.getValue();
                    set22 = set22.size() == 1 ? Collections.singleton((String)set22.iterator().next()) : Collections.unmodifiableSet(set22);
                    ((HashMap)((Object)hashSet)).put((String)entry.getKey(), set22);
                }
                this.subdivisionData = Collections.unmodifiableMap(hashSet);
            } else {
                this.regularData = Collections.unmodifiableSet(set22);
                this.subdivisionData = null;
            }
        }

        public boolean contains(String string) {
            Set<String> set = this.regularData;
            if (set != null) {
                return set.contains(string);
            }
            int n = string.indexOf(45);
            return this.contains(string.substring(0, n), string.substring(n + 1));
        }

        public boolean contains(String object, String string) {
            boolean bl = (object = this.subdivisionData.get(object)) != null && object.contains(string);
            return bl;
        }

        public String toString() {
            Set<String> set = this.regularData;
            if (set != null) {
                return set.toString();
            }
            return this.subdivisionData.toString();
        }
    }

}

