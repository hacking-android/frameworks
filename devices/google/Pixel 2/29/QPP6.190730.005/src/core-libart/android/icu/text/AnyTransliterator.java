/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.lang.UScript;
import android.icu.text.CompoundTransliterator;
import android.icu.text.Replaceable;
import android.icu.text.Transliterator;
import android.icu.text.TransliteratorIDParser;
import android.icu.text.UnicodeFilter;
import android.icu.text.UnicodeSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.MissingResourceException;
import java.util.concurrent.ConcurrentHashMap;

class AnyTransliterator
extends Transliterator {
    static final String ANY = "Any";
    static final String LATIN_PIVOT = "-Latin;Latin-";
    static final String NULL_ID = "Null";
    static final char TARGET_SEP = '-';
    static final char VARIANT_SEP = '/';
    private ConcurrentHashMap<Integer, Transliterator> cache;
    private String target;
    private int targetScript;
    private Transliterator widthFix = Transliterator.getInstance("[[:dt=Nar:][:dt=Wide:]] nfkd");

    public AnyTransliterator(String string, UnicodeFilter unicodeFilter, String string2, int n, Transliterator transliterator, ConcurrentHashMap<Integer, Transliterator> concurrentHashMap) {
        super(string, unicodeFilter);
        this.targetScript = n;
        this.cache = concurrentHashMap;
        this.target = string2;
    }

    private AnyTransliterator(String charSequence, String string, String string2, int n) {
        super((String)charSequence, null);
        this.targetScript = n;
        this.cache = new ConcurrentHashMap();
        this.target = string;
        if (string2.length() > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append('/');
            ((StringBuilder)charSequence).append(string2);
            this.target = ((StringBuilder)charSequence).toString();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Transliterator getTransliterator(int n) {
        if (n != this.targetScript && n != -1) {
            Object object = n;
            Object object2 = this.cache.get(object);
            ArrayList<Transliterator> arrayList = object2;
            if (object2 != null) return arrayList;
            String string = UScript.getName(n);
            arrayList = new StringBuilder();
            ((StringBuilder)((Object)arrayList)).append(string);
            ((StringBuilder)((Object)arrayList)).append('-');
            ((StringBuilder)((Object)arrayList)).append(this.target);
            arrayList = ((StringBuilder)((Object)arrayList)).toString();
            try {
                arrayList = Transliterator.getInstance((String)((Object)arrayList), 0);
                object2 = arrayList;
            }
            catch (RuntimeException runtimeException) {}
            arrayList = object2;
            if (object2 == null) {
                arrayList = new StringBuilder();
                ((StringBuilder)((Object)arrayList)).append(string);
                ((StringBuilder)((Object)arrayList)).append(LATIN_PIVOT);
                ((StringBuilder)((Object)arrayList)).append(this.target);
                arrayList = ((StringBuilder)((Object)arrayList)).toString();
                try {
                    arrayList = Transliterator.getInstance((String)((Object)arrayList), 0);
                    object2 = arrayList;
                }
                catch (RuntimeException runtimeException) {}
                arrayList = object2;
            }
            if (arrayList == null) {
                if (this.isWide(this.targetScript)) return arrayList;
                return this.widthFix;
            }
            object2 = arrayList;
            if (!this.isWide(this.targetScript)) {
                object2 = new ArrayList<Transliterator>();
                object2.add(this.widthFix);
                object2.add(arrayList);
                object2 = new CompoundTransliterator((List<Transliterator>)object2);
            }
            object = (Transliterator)((Object)this.cache.putIfAbsent((Integer)object, (Transliterator)object2));
            arrayList = object2;
            if (object == null) return arrayList;
            return object;
        }
        if (!this.isWide(this.targetScript)) return this.widthFix;
        return null;
    }

    private boolean isWide(int n) {
        boolean bl = n == 5 || n == 17 || n == 18 || n == 20 || n == 22;
        return bl;
    }

    static void register() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        Enumeration<String> enumeration = Transliterator.getAvailableSources();
        while (enumeration.hasMoreElements()) {
            String string = enumeration.nextElement();
            if (string.equalsIgnoreCase(ANY)) continue;
            Enumeration<String> enumeration2 = Transliterator.getAvailableTargets(string);
            while (enumeration2.hasMoreElements()) {
                Object object;
                String string2 = enumeration2.nextElement();
                int n = AnyTransliterator.scriptNameToCode(string2);
                if (n == -1) continue;
                Object object2 = object = (HashSet)hashMap.get(string2);
                if (object == null) {
                    object2 = object = new HashSet();
                    hashMap.put(string2, object);
                }
                object = Transliterator.getAvailableVariants(string, string2);
                while (object.hasMoreElements()) {
                    String string3 = (String)object.nextElement();
                    if (object2.contains(string3)) continue;
                    object2.add(string3);
                    Transliterator.registerInstance(new AnyTransliterator(TransliteratorIDParser.STVtoID(ANY, string2, string3), string2, string3, n));
                    Transliterator.registerSpecialInverse(string2, NULL_ID, false);
                }
            }
        }
    }

    private static int scriptNameToCode(String arrn) {
        int n;
        block2 : {
            n = -1;
            try {
                arrn = UScript.getCode((String)arrn);
                if (arrn == null) break block2;
            }
            catch (MissingResourceException missingResourceException) {
                return -1;
            }
            n = arrn[0];
        }
        return n;
    }

    @Override
    public void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3) {
        unicodeSet = this.getFilterAsUnicodeSet(unicodeSet);
        unicodeSet2.addAll(unicodeSet);
        if (unicodeSet.size() != 0) {
            unicodeSet3.addAll(0, 1114111);
        }
    }

    @Override
    protected void handleTransliterate(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n;
        block2 : {
            int n2 = position.start;
            int n3 = position.limit;
            ScriptRunIterator scriptRunIterator = new ScriptRunIterator(replaceable, position.contextStart, position.contextLimit);
            do {
                n = n3;
                if (!scriptRunIterator.next()) break block2;
                if (scriptRunIterator.limit <= n2) continue;
                Transliterator transliterator = this.getTransliterator(scriptRunIterator.scriptCode);
                if (transliterator == null) {
                    position.start = scriptRunIterator.limit;
                    continue;
                }
                boolean bl2 = bl && scriptRunIterator.limit >= n3;
                position.start = Math.max(n2, scriptRunIterator.start);
                n = position.limit = Math.min(n3, scriptRunIterator.limit);
                transliterator.filteredTransliterate(replaceable, position, bl2);
                n = position.limit - n;
                scriptRunIterator.adjustLimit(n);
                if (scriptRunIterator.limit >= (n3 += n)) break;
            } while (true);
            n = n3;
        }
        position.limit = n;
    }

    public Transliterator safeClone() {
        UnicodeFilter unicodeFilter;
        UnicodeFilter unicodeFilter2 = unicodeFilter = this.getFilter();
        if (unicodeFilter != null) {
            unicodeFilter2 = unicodeFilter;
            if (unicodeFilter instanceof UnicodeSet) {
                unicodeFilter2 = new UnicodeSet((UnicodeSet)unicodeFilter);
            }
        }
        return new AnyTransliterator(this.getID(), unicodeFilter2, this.target, this.targetScript, this.widthFix, this.cache);
    }

    private static class ScriptRunIterator {
        public int limit;
        public int scriptCode;
        public int start;
        private Replaceable text;
        private int textLimit;
        private int textStart;

        public ScriptRunIterator(Replaceable replaceable, int n, int n2) {
            this.text = replaceable;
            this.textStart = n;
            this.textLimit = n2;
            this.limit = n;
        }

        public void adjustLimit(int n) {
            this.limit += n;
            this.textLimit += n;
        }

        public boolean next() {
            int n;
            this.scriptCode = -1;
            this.start = this.limit;
            if (this.start == this.textLimit) {
                return false;
            }
            while ((n = this.start) > this.textStart && ((n = UScript.getScript(this.text.char32At(n - 1))) == 0 || n == 1)) {
                --this.start;
            }
            while ((n = ++this.limit) < this.textLimit) {
                int n2 = UScript.getScript(this.text.char32At(n));
                if (n2 == 0 || n2 == 1) continue;
                n = this.scriptCode;
                if (n == -1) {
                    this.scriptCode = n2;
                    continue;
                }
                if (n2 != n) break;
            }
            return true;
        }
    }

}

