/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.locale.XCldrStub;
import android.icu.util.ICUException;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class XLikelySubtags {
    private static final XLikelySubtags DEFAULT = new XLikelySubtags();
    final Map<String, Map<String, Map<String, LSR>>> langTable;

    public XLikelySubtags() {
        this(XLikelySubtags.getDefaultRawData(), true);
    }

    public XLikelySubtags(Map<String, String> map, boolean bl) {
        this.langTable = this.init(map, bl);
    }

    public static final XLikelySubtags getDefault() {
        return DEFAULT;
    }

    private static Map<String, String> getDefaultRawData() {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "likelySubtags");
        Enumeration<String> enumeration = uResourceBundle.getKeys();
        while (enumeration.hasMoreElements()) {
            String string = enumeration.nextElement();
            treeMap.put(string, uResourceBundle.getString(string));
        }
        return treeMap;
    }

    private Map<String, Map<String, Map<String, LSR>>> init(Map<String, String> object, boolean bl) {
        Object object2;
        Map map = (Map)Maker.TREEMAP.make();
        HashMap<LSR, LSR> object32 = new HashMap<LSR, LSR>();
        for (Map.Entry entry : object.entrySet()) {
            object2 = LSR.from((String)entry.getKey());
            object = ((LSR)object2).language;
            String string = ((LSR)object2).script;
            object2 = ((LSR)object2).region;
            LSR lSR = LSR.from((String)entry.getValue());
            String string2 = lSR.language;
            String string3 = lSR.script;
            String string4 = lSR.region;
            this.set(map, (String)object, string, (String)object2, string2, string3, string4, (Map<LSR, LSR>)object32);
            Object object3 = LSR.LANGUAGE_ALIASES.getAliases((String)object);
            Set<String> set = LSR.REGION_ALIASES.getAliases((String)object2);
            object3 = object3.iterator();
            while (object3.hasNext()) {
                String string5 = (String)object3.next();
                for (String string6 : set) {
                    if (string5.equals(object) && string6.equals(object2)) continue;
                    this.set(map, string5, string, string6, string2, string3, string4, object32);
                }
            }
        }
        this.set(map, "und", "Latn", "", "en", "Latn", "US", object32);
        object = ((Map)((Map)map.get("und")).get("")).entrySet().iterator();
        while (object.hasNext()) {
            object2 = (LSR)object.next().getValue();
            this.set(map, "und", ((LSR)object2).script, ((LSR)object2).region, (LSR)object2);
        }
        if (map.containsKey("und")) {
            object2 = map.entrySet().iterator();
            while (object2.hasNext()) {
                Map.Entry entry = object2.next();
                object = (String)entry.getKey();
                Map map2 = (Map)entry.getValue();
                if (map2.containsKey("")) {
                    for (Map.Entry entry2 : map2.entrySet()) {
                        String string = (String)entry2.getKey();
                        if (((Map)entry2.getValue()).containsKey("")) continue;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("failure: ");
                        ((StringBuilder)object2).append((String)object);
                        ((StringBuilder)object2).append("-");
                        ((StringBuilder)object2).append(string);
                        throw new IllegalArgumentException(((StringBuilder)object2).toString());
                    }
                    continue;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("failure: ");
                ((StringBuilder)object2).append((String)object);
                throw new IllegalArgumentException(((StringBuilder)object2).toString());
            }
            return map;
        }
        throw new IllegalArgumentException("failure: base");
    }

    private LSR minimizeSubtags(String string, String string2, String object, ULocale.Minimize minimize) {
        object = this.maximize(string, string2, (String)object);
        LSR lSR = this.langTable.get(((LSR)object).language).get("").get("");
        boolean bl = false;
        if (((LSR)object).script.equals(lSR.script)) {
            if (((LSR)object).region.equals(lSR.region)) {
                return ((LSR)object).replace(null, "", "");
            }
            if (minimize == ULocale.Minimize.FAVOR_REGION) {
                return ((LSR)object).replace(null, "", null);
            }
            bl = true;
        }
        if (this.maximize(string, string2, "").equals(object)) {
            return ((LSR)object).replace(null, null, "");
        }
        if (bl) {
            return ((LSR)object).replace(null, "", null);
        }
        return object;
    }

    private void set(Map<String, Map<String, Map<String, LSR>>> map, String string, String string2, String string3, LSR lSR) {
        map = Maker.TREEMAP.getSubtable(map, string);
        Maker.TREEMAP.getSubtable(map, string2).put(string3, (Map<String, LSR>)((Object)lSR));
    }

    private void set(Map<String, Map<String, Map<String, LSR>>> map, String string, String string2, String string3, String object, String object2, String object3, Map<LSR, LSR> map2) {
        object3 = new LSR((String)object, (String)object2, (String)object3);
        object = object2 = map2.get(object3);
        if (object2 == null) {
            map2.put((LSR)object3, (LSR)object3);
            object = object3;
        }
        this.set(map, string, string2, string3, (LSR)object);
    }

    private static StringBuilder show(Map<?, ?> object, String string, StringBuilder stringBuilder) {
        CharSequence charSequence = string.isEmpty() ? "" : "\t";
        for (Map.Entry<Object, Object> entry : object.entrySet()) {
            object = entry.getKey().toString();
            entry = entry.getValue();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append((String)charSequence);
            if (((String)object).isEmpty()) {
                object = "\u2205";
            }
            stringBuilder2.append((String)object);
            stringBuilder.append(stringBuilder2.toString());
            if (entry instanceof Map) {
                object = (Map)((Object)entry);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append("\t");
                XLikelySubtags.show(object, ((StringBuilder)charSequence).toString(), stringBuilder);
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("\t");
                ((StringBuilder)object).append(Objects.toString(entry));
                stringBuilder.append(((StringBuilder)object).toString());
                stringBuilder.append("\n");
            }
            charSequence = string;
        }
        return stringBuilder;
    }

    public LSR maximize(LSR lSR) {
        return this.maximize(lSR.language, lSR.script, lSR.region);
    }

    public LSR maximize(ULocale uLocale) {
        return this.maximize(uLocale.getLanguage(), uLocale.getScript(), uLocale.getCountry());
    }

    public LSR maximize(String string) {
        return this.maximize(ULocale.forLanguageTag(string));
    }

    public LSR maximize(String string, String object, String object2) {
        int n;
        Map map;
        Object object3;
        int n2 = 0;
        Map<String, Map<String, LSR>> map2 = this.langTable.get(string);
        if (map2 == null) {
            n2 = 0 | 4;
            object3 = this.langTable.get("und");
        } else {
            object3 = map2;
            if (!string.equals("und")) {
                n2 = 0 | 4;
                object3 = map2;
            }
        }
        map2 = object;
        if (((String)object).equals("Zzzz")) {
            map2 = "";
        }
        if ((map = (Map)object3.get(map2)) == null) {
            n = n2 | 2;
            object = (Map)object3.get("");
        } else {
            n = n2;
            object = map;
            if (!((String)((Object)map2)).isEmpty()) {
                n = n2 | 2;
                object = map;
            }
        }
        object3 = object2;
        if (((String)object2).equals("ZZ")) {
            object3 = "";
        }
        if ((object2 = (LSR)object.get(object3)) == null) {
            n2 = n | 1;
            object = object2 = (LSR)object.get("");
            if (object2 == null) {
                return null;
            }
        } else {
            n2 = n;
            object = object2;
            if (!((String)object3).isEmpty()) {
                n2 = n | 1;
                object = object2;
            }
        }
        switch (n2) {
            default: {
                return object;
            }
            case 7: {
                return ((LSR)object).replace(string, (String)((Object)map2), (String)object3);
            }
            case 6: {
                return ((LSR)object).replace(string, (String)((Object)map2), null);
            }
            case 5: {
                return ((LSR)object).replace(string, null, (String)object3);
            }
            case 4: {
                return ((LSR)object).replace(string, null, null);
            }
            case 3: {
                return ((LSR)object).replace(null, (String)((Object)map2), (String)object3);
            }
            case 2: {
                return ((LSR)object).replace(null, (String)((Object)map2), null);
            }
            case 1: 
        }
        return ((LSR)object).replace(null, null, (String)object3);
    }

    public String toString() {
        return XLikelySubtags.show(this.langTable, "", new StringBuilder()).toString();
    }

    public static class Aliases {
        final XCldrStub.Multimap<String, String> toAliases;
        final Map<String, String> toCanonical;

        public Aliases(String string) {
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "metadata", ICUResourceBundle.ICU_DATA_CLASS_LOADER).get("alias").get(string);
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            for (int i = 0; i < uResourceBundle.getSize(); ++i) {
                Object object = uResourceBundle.get(i);
                String string2 = ((UResourceBundle)object).getKey();
                if (string2.contains("_") || ((UResourceBundle)object).get("reason").getString().equals("overlong")) continue;
                int n = ((String)(object = ((UResourceBundle)object).get("replacement").getString())).indexOf(32);
                if (n >= 0) {
                    object = ((String)object).substring(0, n);
                }
                if (((String)object).contains("_")) continue;
                hashMap.put(string2, object);
            }
            if (string.equals("language")) {
                hashMap.put("mo", "ro");
            }
            this.toCanonical = Collections.unmodifiableMap(hashMap);
            this.toAliases = XCldrStub.Multimaps.invertFrom(hashMap, XCldrStub.HashMultimap.create());
        }

        public Set<String> getAliases(String set) {
            Set<String> set2 = this.toAliases.get((String)((Object)set));
            set = set2 == null ? Collections.singleton(set) : set2;
            return set;
        }

        public String getCanonical(String string) {
            String string2 = this.toCanonical.get(string);
            if (string2 != null) {
                string = string2;
            }
            return string;
        }
    }

    public static class LSR {
        public static Aliases LANGUAGE_ALIASES = new Aliases("language");
        public static Aliases REGION_ALIASES = new Aliases("territory");
        private static final HashMap<ULocale, LSR> pseudoReplacements = new HashMap(11);
        public final String language;
        public final String region;
        public final String script;

        static {
            String[][] arrarrstring = new String[][]{{"x-bork", "x1", "", ""}, {"x-elmer", "x2", "", ""}, {"x-hacker", "x3", "", ""}, {"x-piglatin", "x4", "", ""}, {"x-pirate", "x5", "", ""}, {"en-XA", "x6", "", ""}, {"en-PSACCENT", "x6", "", ""}, {"ar-XB", "x7", "", ""}, {"ar-PSBIDI", "x7", "", ""}, {"en-XC", "x8", "en", ""}, {"en-PSCRACK", "x8", "en", ""}};
            for (int i = 0; i < arrarrstring.length; ++i) {
                pseudoReplacements.put(new ULocale(arrarrstring[i][0]), new LSR(arrarrstring[i][1], arrarrstring[i][2], arrarrstring[i][3]));
            }
        }

        public LSR(String string, String string2, String string3) {
            this.language = string;
            this.script = string2;
            this.region = string3;
        }

        public static LSR from(ULocale uLocale) {
            Object object = pseudoReplacements.get(uLocale);
            if (object != null) {
                return object;
            }
            if ("PSCRACK".equals(uLocale.getVariant())) {
                object = new StringBuilder();
                ((StringBuilder)object).append(uLocale.getLanguage());
                ((StringBuilder)object).append(uLocale.getScript());
                ((StringBuilder)object).append(uLocale.getCountry());
                return new LSR("x8", ((StringBuilder)object).toString(), "");
            }
            return new LSR(uLocale.getLanguage(), uLocale.getScript(), uLocale.getCountry());
        }

        static LSR from(String object) {
            Object object2 = ((String)object).split("[-_]");
            if (((String[])object2).length >= 1 && ((String[])object2).length <= 3) {
                String string = object2[0].toLowerCase();
                object = ((String[])object2).length < 2 ? "" : object2[1];
                object2 = ((String[])object2).length < 3 ? "" : object2[2];
                object = ((String)object).length() < 4 ? new LSR(string, "", (String)object) : new LSR(string, (String)object, (String)object2);
                return object;
            }
            throw new ICUException("too many subtags");
        }

        public static LSR from(String string, String string2, String string3) {
            return new LSR(string, string2, string3);
        }

        public static LSR fromMaximalized(ULocale uLocale) {
            Object object = pseudoReplacements.get(uLocale);
            if (object != null) {
                return object;
            }
            if ("PSCRACK".equals(uLocale.getVariant())) {
                object = new StringBuilder();
                ((StringBuilder)object).append(uLocale.getLanguage());
                ((StringBuilder)object).append(uLocale.getScript());
                ((StringBuilder)object).append(uLocale.getCountry());
                return new LSR("x8", ((StringBuilder)object).toString(), "");
            }
            return LSR.fromMaximalized(uLocale.getLanguage(), uLocale.getScript(), uLocale.getCountry());
        }

        public static LSR fromMaximalized(String string, String string2, String string3) {
            string = LANGUAGE_ALIASES.getCanonical(string);
            string3 = REGION_ALIASES.getCanonical(string3);
            return DEFAULT.maximize(string, string2, string3);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null) return false;
            if (object.getClass() != this.getClass()) return false;
            String string = this.language;
            object = (LSR)object;
            if (!string.equals(((LSR)object).language)) return false;
            if (!this.script.equals(((LSR)object).script)) return false;
            if (!this.region.equals(((LSR)object).region)) return false;
            return true;
        }

        public int hashCode() {
            return Objects.hash(this.language, this.script, this.region);
        }

        public LSR replace(String string, String string2, String string3) {
            if (string == null && string2 == null && string3 == null) {
                return this;
            }
            if (string == null) {
                string = this.language;
            }
            if (string2 == null) {
                string2 = this.script;
            }
            if (string3 == null) {
                string3 = this.region;
            }
            return new LSR(string, string2, string3);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(this.language);
            if (!this.script.isEmpty()) {
                stringBuilder.append('-');
                stringBuilder.append(this.script);
            }
            if (!this.region.isEmpty()) {
                stringBuilder.append('-');
                stringBuilder.append(this.region);
            }
            return stringBuilder.toString();
        }
    }

    static abstract class Maker {
        static final Maker HASHMAP = new Maker(){

            public Map<Object, Object> make() {
                return new HashMap<Object, Object>();
            }
        };
        static final Maker TREEMAP = new Maker(){

            public Map<Object, Object> make() {
                return new TreeMap<Object, Object>();
            }
        };

        Maker() {
        }

        public <K, V> V getSubtable(Map<K, V> map, K k) {
            V v;
            V v2 = v = map.get(k);
            if (v == null) {
                v2 = v = this.make();
                map.put(k, v);
            }
            return v2;
        }

        abstract <V> V make();

    }

}

