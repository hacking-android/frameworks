/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.ICUResourceBundle;
import android.icu.impl.LocaleUtility;
import android.icu.impl.Utility;
import android.icu.lang.UScript;
import android.icu.text.AnyTransliterator;
import android.icu.text.CompoundTransliterator;
import android.icu.text.RuleBasedTransliterator;
import android.icu.text.Transliterator;
import android.icu.text.TransliteratorIDParser;
import android.icu.text.TransliteratorParser;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;
import android.icu.util.CaseInsensitiveString;
import android.icu.util.ULocale;
import android.icu.util.UResourceBundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

class TransliteratorRegistry {
    private static final String ANY = "Any";
    private static final boolean DEBUG = false;
    private static final char LOCALE_SEP = '_';
    private static final String NO_VARIANT = "";
    private List<CaseInsensitiveString> availableIDs = new ArrayList<CaseInsensitiveString>();
    private Map<CaseInsensitiveString, Object[]> registry = Collections.synchronizedMap(new HashMap());
    private Map<CaseInsensitiveString, Map<CaseInsensitiveString, List<CaseInsensitiveString>>> specDAG = Collections.synchronizedMap(new HashMap());

    private Object[] find(String arrstring) {
        arrstring = TransliteratorIDParser.IDtoSTV((String)arrstring);
        return this.find(arrstring[0], arrstring[1], arrstring[2]);
    }

    private Object[] find(String object, String object2, String arrobject) {
        object = new Spec((String)object);
        object2 = new Spec((String)object2);
        if (arrobject.length() != 0) {
            Object[] arrobject2 = this.findInDynamicStore((Spec)object, (Spec)object2, (String)arrobject);
            if (arrobject2 != null) {
                return arrobject2;
            }
            if ((arrobject = this.findInStaticStore((Spec)object, (Spec)object2, (String)arrobject)) != null) {
                return arrobject;
            }
        }
        block0 : do {
            ((Spec)object).reset();
            do {
                if ((arrobject = this.findInDynamicStore((Spec)object, (Spec)object2, NO_VARIANT)) != null) {
                    return arrobject;
                }
                arrobject = this.findInStaticStore((Spec)object, (Spec)object2, NO_VARIANT);
                if (arrobject != null) {
                    return arrobject;
                }
                if (!((Spec)object).hasFallback()) {
                    if (!((Spec)object2).hasFallback()) {
                        return null;
                    }
                    ((Spec)object2).next();
                    continue block0;
                }
                ((Spec)object).next();
            } while (true);
            break;
        } while (true);
    }

    private Object[] findInBundle(Spec object, Spec spec, String string, int n) {
        ResourceBundle resourceBundle = object.getBundle();
        if (resourceBundle == null) {
            return null;
        }
        for (int i = 0; i < 2; ++i) {
            int n2;
            int n3;
            block12 : {
                StringBuilder stringBuilder = new StringBuilder();
                if (i == 0) {
                    object = n == 0 ? "TransliterateTo" : "TransliterateFrom";
                    stringBuilder.append((String)object);
                } else {
                    stringBuilder.append("Transliterate");
                }
                stringBuilder.append(spec.get().toUpperCase(Locale.ENGLISH));
                object = resourceBundle.getStringArray(stringBuilder.toString());
                n2 = 0;
                if (string.length() == 0) break block12;
                n3 = 0;
                do {
                    n2 = n3;
                    if (n3 >= ((String[])object).length) break;
                    if (object[n3].equalsIgnoreCase(string)) {
                        n2 = n3;
                        break;
                    }
                    n3 += 2;
                    continue;
                    break;
                } while (true);
            }
            if (n2 >= ((String[])object).length) continue;
            n3 = i == 0 ? 0 : n;
            try {
                object = new LocaleEntry(object[n2 + 1], n3);
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
            return new Object[]{object};
        }
        return null;
    }

    private Object[] findInDynamicStore(Spec object, Spec spec, String string) {
        object = TransliteratorIDParser.STVtoID(((Spec)object).get(), spec.get(), string);
        return this.registry.get(new CaseInsensitiveString((String)object));
    }

    private Object[] findInStaticStore(Spec spec, Spec spec2, String string) {
        Object[] arrobject = null;
        if (spec.isLocale()) {
            arrobject = this.findInBundle(spec, spec2, string, 0);
        } else if (spec2.isLocale()) {
            arrobject = this.findInBundle(spec2, spec, string, 1);
        }
        if (arrobject != null) {
            this.registerEntry(spec.getTop(), spec2.getTop(), string, arrobject, false);
        }
        return arrobject;
    }

    private Transliterator instantiateEntry(String object, Object[] arrobject, StringBuffer stringBuffer) {
        Object object2;
        while (!((object2 = arrobject[0]) instanceof RuleBasedTransliterator.Data)) {
            if (object2 instanceof Class) {
                try {
                    object = (Transliterator)((Class)object2).newInstance();
                    return object;
                }
                catch (IllegalAccessException illegalAccessException) {
                }
                catch (InstantiationException instantiationException) {
                    // empty catch block
                }
                return null;
            }
            if (object2 instanceof AliasEntry) {
                stringBuffer.append(((AliasEntry)object2).alias);
                return null;
            }
            if (object2 instanceof Transliterator.Factory) {
                return ((Transliterator.Factory)object2).getInstance((String)object);
            }
            if (object2 instanceof CompoundRBTEntry) {
                return ((CompoundRBTEntry)object2).getInstance();
            }
            if (object2 instanceof AnyTransliterator) {
                return ((AnyTransliterator)object2).safeClone();
            }
            if (object2 instanceof RuleBasedTransliterator) {
                return ((RuleBasedTransliterator)object2).safeClone();
            }
            if (object2 instanceof CompoundTransliterator) {
                return ((CompoundTransliterator)object2).safeClone();
            }
            if (object2 instanceof Transliterator) {
                return (Transliterator)object2;
            }
            TransliteratorParser transliteratorParser = new TransliteratorParser();
            try {
                ResourceEntry resourceEntry = (ResourceEntry)object2;
                transliteratorParser.parse(resourceEntry.resource, resourceEntry.direction);
            }
            catch (ClassCastException classCastException) {
                object2 = (LocaleEntry)object2;
                transliteratorParser.parse(((LocaleEntry)object2).rule, ((LocaleEntry)object2).direction);
            }
            if (transliteratorParser.idBlockVector.size() == 0 && transliteratorParser.dataVector.size() == 0) {
                arrobject[0] = new AliasEntry("Any-Null");
                continue;
            }
            if (transliteratorParser.idBlockVector.size() == 0 && transliteratorParser.dataVector.size() == 1) {
                arrobject[0] = transliteratorParser.dataVector.get(0);
                continue;
            }
            if (transliteratorParser.idBlockVector.size() == 1 && transliteratorParser.dataVector.size() == 0) {
                if (transliteratorParser.compoundFilter != null) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append(transliteratorParser.compoundFilter.toPattern(false));
                    ((StringBuilder)object2).append(";");
                    ((StringBuilder)object2).append(transliteratorParser.idBlockVector.get(0));
                    arrobject[0] = new AliasEntry(((StringBuilder)object2).toString());
                    continue;
                }
                arrobject[0] = new AliasEntry(transliteratorParser.idBlockVector.get(0));
                continue;
            }
            arrobject[0] = new CompoundRBTEntry((String)object, transliteratorParser.idBlockVector, transliteratorParser.dataVector, transliteratorParser.compoundFilter);
        }
        return new RuleBasedTransliterator((String)object, (RuleBasedTransliterator.Data)object2, null);
    }

    private void registerEntry(String arrstring, Object object, boolean bl) {
        arrstring = TransliteratorIDParser.IDtoSTV((String)arrstring);
        this.registerEntry(TransliteratorIDParser.STVtoID(arrstring[0], arrstring[1], arrstring[2]), arrstring[0], arrstring[1], arrstring[2], object, bl);
    }

    private void registerEntry(String string, String string2, String string3, Object object, boolean bl) {
        String string4;
        String string5 = string4 = string;
        if (string4.length() == 0) {
            string5 = ANY;
        }
        this.registerEntry(TransliteratorIDParser.STVtoID(string, string2, string3), string5, string2, string3, object, bl);
    }

    private void registerEntry(String arrobject, String string, String string2, String string3, Object object, boolean bl) {
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString((String)arrobject);
        arrobject = object instanceof Object[] ? (Object[])object : new Object[]{object};
        this.registry.put(caseInsensitiveString, arrobject);
        if (bl) {
            this.registerSTV(string, string2, string3);
            if (!this.availableIDs.contains(caseInsensitiveString)) {
                this.availableIDs.add(caseInsensitiveString);
            }
        } else {
            this.removeSTV(string, string2, string3);
            this.availableIDs.remove(caseInsensitiveString);
        }
    }

    private void registerSTV(String map, String arrayList, String string) {
        Object object = new CaseInsensitiveString((String)((Object)map));
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString((String)((Object)arrayList));
        CaseInsensitiveString caseInsensitiveString2 = new CaseInsensitiveString(string);
        arrayList = this.specDAG.get(object);
        map = arrayList;
        if (arrayList == null) {
            map = Collections.synchronizedMap(new HashMap());
            this.specDAG.put((CaseInsensitiveString)object, map);
        }
        object = (List)map.get(caseInsensitiveString);
        arrayList = object;
        if (object == null) {
            arrayList = new ArrayList<CaseInsensitiveString>();
            map.put(caseInsensitiveString, arrayList);
        }
        if (!arrayList.contains(caseInsensitiveString2)) {
            if (string.length() > 0) {
                arrayList.add(caseInsensitiveString2);
            } else {
                arrayList.add(0, caseInsensitiveString2);
            }
        }
    }

    private void removeSTV(String object, String object2, String map) {
        object = new CaseInsensitiveString((String)object);
        object2 = new CaseInsensitiveString((String)object2);
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString((String)((Object)map));
        map = this.specDAG.get(object);
        if (map == null) {
            return;
        }
        List list = (List)map.get(object2);
        if (list == null) {
            return;
        }
        list.remove(caseInsensitiveString);
        if (list.size() == 0) {
            map.remove(object2);
            if (map.size() == 0) {
                this.specDAG.remove(object);
            }
        }
    }

    public Transliterator get(String object, StringBuffer stringBuffer) {
        Object[] arrobject = this.find((String)object);
        object = arrobject == null ? null : this.instantiateEntry((String)object, arrobject, stringBuffer);
        return object;
    }

    public Enumeration<String> getAvailableIDs() {
        return new IDEnumeration(Collections.enumeration(this.availableIDs));
    }

    public Enumeration<String> getAvailableSources() {
        return new IDEnumeration(Collections.enumeration(this.specDAG.keySet()));
    }

    public Enumeration<String> getAvailableTargets(String map) {
        map = new CaseInsensitiveString((String)((Object)map));
        if ((map = this.specDAG.get(map)) == null) {
            return new IDEnumeration(null);
        }
        return new IDEnumeration(Collections.enumeration(map.keySet()));
    }

    public Enumeration<String> getAvailableVariants(String object, String map) {
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString((String)object);
        object = new CaseInsensitiveString((String)((Object)map));
        map = this.specDAG.get(caseInsensitiveString);
        if (map == null) {
            return new IDEnumeration(null);
        }
        if ((object = (List)map.get(object)) == null) {
            return new IDEnumeration(null);
        }
        return new IDEnumeration(Collections.enumeration(object));
    }

    public void put(String string, Transliterator.Factory factory, boolean bl) {
        this.registerEntry(string, factory, bl);
    }

    public void put(String string, Transliterator transliterator, boolean bl) {
        this.registerEntry(string, transliterator, bl);
    }

    public void put(String string, Class<? extends Transliterator> class_, boolean bl) {
        this.registerEntry(string, class_, bl);
    }

    public void put(String string, String string2, int n, boolean bl) {
        this.registerEntry(string, new ResourceEntry(string2, n), bl);
    }

    public void put(String string, String string2, boolean bl) {
        this.registerEntry(string, new AliasEntry(string2), bl);
    }

    public void remove(String string) {
        String[] arrstring = TransliteratorIDParser.IDtoSTV(string);
        string = TransliteratorIDParser.STVtoID(arrstring[0], arrstring[1], arrstring[2]);
        this.registry.remove(new CaseInsensitiveString(string));
        this.removeSTV(arrstring[0], arrstring[1], arrstring[2]);
        this.availableIDs.remove(new CaseInsensitiveString(string));
    }

    static class AliasEntry {
        public String alias;

        public AliasEntry(String string) {
            this.alias = string;
        }
    }

    static class CompoundRBTEntry {
        private String ID;
        private UnicodeSet compoundFilter;
        private List<RuleBasedTransliterator.Data> dataVector;
        private List<String> idBlockVector;

        public CompoundRBTEntry(String string, List<String> list, List<RuleBasedTransliterator.Data> list2, UnicodeSet unicodeSet) {
            this.ID = string;
            this.idBlockVector = list;
            this.dataVector = list2;
            this.compoundFilter = unicodeSet;
        }

        public Transliterator getInstance() {
            Object object;
            Cloneable cloneable = new ArrayList<Transliterator>();
            int n = 1;
            int n2 = Math.max(this.idBlockVector.size(), this.dataVector.size());
            for (int i = 0; i < n2; ++i) {
                if (i < this.idBlockVector.size() && ((String)(object = this.idBlockVector.get(i))).length() > 0) {
                    cloneable.add((Transliterator)Transliterator.getInstance((String)object));
                }
                int n3 = n;
                if (i < this.dataVector.size()) {
                    object = this.dataVector.get(i);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("%Pass");
                    stringBuilder.append(n);
                    cloneable.add((Transliterator)new RuleBasedTransliterator(stringBuilder.toString(), (RuleBasedTransliterator.Data)object, null));
                    n3 = n + 1;
                }
                n = n3;
            }
            object = new CompoundTransliterator((List<Transliterator>)((Object)cloneable), n - 1);
            ((Transliterator)object).setID(this.ID);
            cloneable = this.compoundFilter;
            if (cloneable != null) {
                ((Transliterator)object).setFilter((UnicodeFilter)((Object)cloneable));
            }
            return object;
        }
    }

    private static class IDEnumeration
    implements Enumeration<String> {
        Enumeration<CaseInsensitiveString> en;

        public IDEnumeration(Enumeration<CaseInsensitiveString> enumeration) {
            this.en = enumeration;
        }

        @Override
        public boolean hasMoreElements() {
            Enumeration<CaseInsensitiveString> enumeration = this.en;
            boolean bl = enumeration != null && enumeration.hasMoreElements();
            return bl;
        }

        @Override
        public String nextElement() {
            return this.en.nextElement().getString();
        }
    }

    static class LocaleEntry {
        public int direction;
        public String rule;

        public LocaleEntry(String string, int n) {
            this.rule = string;
            this.direction = n;
        }
    }

    static class ResourceEntry {
        public int direction;
        public String resource;

        public ResourceEntry(String string, int n) {
            this.resource = string;
            this.direction = n;
        }
    }

    static class Spec {
        private boolean isNextLocale;
        private boolean isSpecLocale;
        private String nextSpec;
        private ICUResourceBundle res;
        private String scriptName;
        private String spec;
        private String top;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Spec(String arrn) {
            this.top = arrn;
            this.spec = null;
            this.scriptName = null;
            try {
                int n = UScript.getCodeFromName(this.top);
                arrn = UScript.getCode(this.top);
                if (arrn != null) {
                    this.scriptName = UScript.getName(arrn[0]);
                    if (this.scriptName.equalsIgnoreCase(this.top)) {
                        this.scriptName = null;
                    }
                }
                this.isSpecLocale = false;
                this.res = null;
                if (n == -1) {
                    this.res = (ICUResourceBundle)UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/translit", LocaleUtility.getLocaleFromName(this.top));
                    if (this.res != null && LocaleUtility.isFallbackOf(this.res.getULocale().toString(), this.top)) {
                        this.isSpecLocale = true;
                    }
                }
            }
            catch (MissingResourceException missingResourceException) {
                this.scriptName = null;
            }
            this.reset();
        }

        private void setupNext() {
            this.isNextLocale = false;
            if (this.isSpecLocale) {
                this.nextSpec = this.spec;
                int n = this.nextSpec.lastIndexOf(95);
                if (n > 0) {
                    this.nextSpec = this.spec.substring(0, n);
                    this.isNextLocale = true;
                } else {
                    this.nextSpec = this.scriptName;
                }
            } else {
                this.nextSpec = !Utility.sameObjects(this.nextSpec, this.scriptName) ? this.scriptName : null;
            }
        }

        public String get() {
            return this.spec;
        }

        public ResourceBundle getBundle() {
            ICUResourceBundle iCUResourceBundle = this.res;
            if (iCUResourceBundle != null && iCUResourceBundle.getULocale().toString().equals(this.spec)) {
                return this.res;
            }
            return null;
        }

        public String getTop() {
            return this.top;
        }

        public boolean hasFallback() {
            boolean bl = this.nextSpec != null;
            return bl;
        }

        public boolean isLocale() {
            return this.isSpecLocale;
        }

        public String next() {
            this.spec = this.nextSpec;
            this.isSpecLocale = this.isNextLocale;
            this.setupNext();
            return this.spec;
        }

        public void reset() {
            if (!Utility.sameObjects(this.spec, this.top)) {
                this.spec = this.top;
                boolean bl = this.res != null;
                this.isSpecLocale = bl;
                this.setupNext();
            }
        }
    }

}

