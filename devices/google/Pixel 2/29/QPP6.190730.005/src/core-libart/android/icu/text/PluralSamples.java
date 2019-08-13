/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.PluralRules;
import android.icu.util.Output;
import java.io.PrintStream;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Deprecated
public class PluralSamples {
    private static final int LIMIT_FRACTION_SAMPLES = 3;
    private static final int[] TENS = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000};
    private final Set<PluralRules.FixedDecimal> _fractionSamples;
    private final Map<String, Set<PluralRules.FixedDecimal>> _keyFractionSamplesMap;
    @Deprecated
    public final Map<String, Boolean> _keyLimitedMap;
    private final Map<String, List<Double>> _keySamplesMap;
    private PluralRules pluralRules;

    /*
     * WARNING - void declaration
     */
    @Deprecated
    public PluralSamples(PluralRules object5) {
        TreeSet<PluralRules.FixedDecimal> treeSet;
        int n;
        Set<String> set;
        Object object;
        HashMap<String, List<Double>> hashMap;
        HashMap<String, Set<PluralRules.FixedDecimal>> hashMap2;
        block15 : {
            int n2;
            this.pluralRules = object5;
            set = ((PluralRules)object5).getKeywords();
            HashMap<String, Boolean> hashMap3 = new HashMap<String, Boolean>();
            for (String string : set) {
                hashMap3.put(string, ((PluralRules)object5).isLimited(string));
            }
            this._keyLimitedMap = hashMap3;
            hashMap = new HashMap<String, List<Double>>();
            n = set.size();
            for (n2 = 0; n > 0 && n2 < 128; ++n2) {
                n = this.addSimpleSamples((PluralRules)object5, 3, hashMap, n, (double)n2 / 2.0);
            }
            n = this.addSimpleSamples((PluralRules)object5, 3, hashMap, n, 1000000.0);
            hashMap2 = new HashMap<String, Set<PluralRules.FixedDecimal>>();
            treeSet = new TreeSet<PluralRules.FixedDecimal>();
            HashMap<String, Set<PluralRules.FixedDecimal>> hashMap4 = new HashMap<String, Set<PluralRules.FixedDecimal>>();
            object = treeSet.iterator();
            while (object.hasNext()) {
                PluralRules.FixedDecimal fixedDecimal = (PluralRules.FixedDecimal)((Object)object.next());
                this.addRelation(hashMap4, ((PluralRules)object5).select(fixedDecimal), fixedDecimal);
            }
            if (hashMap4.size() != set.size()) {
                for (n2 = 1; n2 < 1000; ++n2) {
                    if (!this.addIfNotPresent(n2, treeSet, hashMap4)) {
                        continue;
                    }
                    break block15;
                }
                for (n2 = 10; n2 < 1000; ++n2) {
                    if (!this.addIfNotPresent((double)n2 / 10.0, treeSet, hashMap4)) {
                        continue;
                    }
                    break block15;
                }
                object = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to find sample for each keyword: ");
                stringBuilder.append(hashMap4);
                stringBuilder.append("\n\t");
                stringBuilder.append(object5);
                stringBuilder.append("\n\t");
                stringBuilder.append(treeSet);
                ((PrintStream)object).println(stringBuilder.toString());
            }
        }
        treeSet.add(new PluralRules.FixedDecimal(0L));
        treeSet.add(new PluralRules.FixedDecimal(1L));
        treeSet.add(new PluralRules.FixedDecimal(2L));
        treeSet.add(new PluralRules.FixedDecimal(0.1, 1));
        treeSet.add(new PluralRules.FixedDecimal(1.99, 2));
        treeSet.addAll(this.fractions(treeSet));
        for (PluralRules.FixedDecimal fixedDecimal : treeSet) {
            void var3_13;
            String string = ((PluralRules)object5).select(fixedDecimal);
            Object object2 = object = (Set)hashMap2.get(string);
            if (object == null) {
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                hashMap2.put(string, linkedHashSet);
            }
            var3_13.add(fixedDecimal);
        }
        if (n > 0) {
            for (String string : set) {
                if (!hashMap.containsKey(string)) {
                    hashMap.put(string, Collections.emptyList());
                }
                if (hashMap2.containsKey(string)) continue;
                hashMap2.put(string, Collections.emptySet());
            }
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            hashMap.put((String)entry.getKey(), Collections.unmodifiableList((List)entry.getValue()));
        }
        for (Map.Entry entry : hashMap2.entrySet()) {
            hashMap2.put((String)entry.getKey(), Collections.unmodifiableSet((Set)entry.getValue()));
        }
        this._keySamplesMap = hashMap;
        this._keyFractionSamplesMap = hashMap2;
        this._fractionSamples = Collections.unmodifiableSet(treeSet);
    }

    private boolean addIfNotPresent(double d, Set<PluralRules.FixedDecimal> set, Map<String, Set<PluralRules.FixedDecimal>> map) {
        PluralRules.FixedDecimal fixedDecimal = new PluralRules.FixedDecimal(d);
        String string = this.pluralRules.select(fixedDecimal);
        if (!map.containsKey(string) || string.equals("other")) {
            this.addRelation(map, string, fixedDecimal);
            set.add(fixedDecimal);
            if (string.equals("other") && map.get("other").size() > 1) {
                return true;
            }
        }
        return false;
    }

    private void addRelation(Map<String, Set<PluralRules.FixedDecimal>> map, String string, PluralRules.FixedDecimal fixedDecimal) {
        Set<PluralRules.FixedDecimal> set;
        Set<PluralRules.FixedDecimal> set2 = set = map.get(string);
        if (set == null) {
            set2 = set = new HashSet<PluralRules.FixedDecimal>();
            map.put(string, set);
        }
        set2.add(fixedDecimal);
    }

    private int addSimpleSamples(PluralRules list, int n, Map<String, List<Double>> map, int n2, double d) {
        String string = ((PluralRules)((Object)list)).select(d);
        boolean bl = this._keyLimitedMap.get(string);
        List<Double> list2 = map.get(string);
        if (list2 == null) {
            list = new ArrayList<Double>(n);
            map.put(string, list);
        } else {
            list = list2;
            if (!bl) {
                list = list2;
                if (list2.size() == n) {
                    return n2;
                }
            }
        }
        list.add(d);
        int n3 = n2;
        if (!bl) {
            n3 = n2;
            if (list.size() == n) {
                n3 = n2 - 1;
            }
        }
        return n3;
    }

    private Set<PluralRules.FixedDecimal> fractions(Set<PluralRules.FixedDecimal> object) {
        HashSet<PluralRules.FixedDecimal> hashSet = new HashSet<PluralRules.FixedDecimal>();
        Object object2 = new HashSet<Integer>();
        object = object.iterator();
        while (object.hasNext()) {
            object2.add((int)((PluralRules.FixedDecimal)object.next()).integerValue);
        }
        object = new ArrayList(object2);
        HashSet<String> hashSet2 = new HashSet<String>();
        int n = 0;
        do {
            AbstractCollection abstractCollection = this;
            if (n >= object.size()) break;
            Integer n2 = (Integer)object.get(n);
            CharSequence charSequence = ((PluralSamples)abstractCollection).pluralRules.select(n2.intValue());
            if (hashSet2.contains(charSequence)) {
                abstractCollection = object;
                object = object2;
                object2 = abstractCollection;
            } else {
                hashSet2.add((String)charSequence);
                hashSet.add(new PluralRules.FixedDecimal(n2.intValue(), 1));
                hashSet.add(new PluralRules.FixedDecimal(n2.intValue(), 2));
                abstractCollection = PluralSamples.super.getDifferentCategory((List<Integer>)object, (String)charSequence);
                if ((Integer)((Object)abstractCollection) >= TENS[2]) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(n2);
                    ((StringBuilder)charSequence).append(".");
                    ((StringBuilder)charSequence).append(abstractCollection);
                    hashSet.add(new PluralRules.FixedDecimal(((StringBuilder)charSequence).toString()));
                    abstractCollection = object;
                    object = object2;
                    object2 = abstractCollection;
                } else {
                    for (int i = 1; i < 3; ++i) {
                        for (int j = 1; j <= i; ++j) {
                            if ((Integer)((Object)abstractCollection) >= TENS[j]) continue;
                            hashSet.add(new PluralRules.FixedDecimal((double)n2.intValue() + (double)((Integer)((Object)abstractCollection)).intValue() / (double)TENS[j], i));
                        }
                    }
                    abstractCollection = object2;
                    object2 = object;
                    object = abstractCollection;
                }
            }
            ++n;
            abstractCollection = object2;
            object2 = object;
            object = abstractCollection;
        } while (true);
        return hashSet;
    }

    private Integer getDifferentCategory(List<Integer> list, String string) {
        for (int i = list.size() - 1; i >= 0; --i) {
            Integer n = list.get(i);
            if (this.pluralRules.select(n.intValue()).equals(string)) continue;
            return n;
        }
        return 37;
    }

    Collection<Double> getAllKeywordValues(String string) {
        if (!this.pluralRules.getKeywords().contains(string)) {
            return Collections.emptyList();
        }
        Collection collection = this.getKeySamplesMap().get(string);
        if (collection.size() > 2 && !this._keyLimitedMap.get(string).booleanValue()) {
            return null;
        }
        return collection;
    }

    Set<PluralRules.FixedDecimal> getFractionSamples() {
        return this._fractionSamples;
    }

    Map<String, Set<PluralRules.FixedDecimal>> getKeyFractionSamplesMap() {
        return this._keyFractionSamplesMap;
    }

    Map<String, List<Double>> getKeySamplesMap() {
        return this._keySamplesMap;
    }

    @Deprecated
    public PluralRules.KeywordStatus getStatus(String object, int n, Set<Double> set, Output<Double> output) {
        if (output != null) {
            output.value = null;
        }
        if (!this.pluralRules.getKeywords().contains(object)) {
            return PluralRules.KeywordStatus.INVALID;
        }
        Collection<Double> collection = this.pluralRules.getAllKeywordValues((String)object);
        if (collection == null) {
            return PluralRules.KeywordStatus.UNBOUNDED;
        }
        int n2 = collection.size();
        object = set;
        if (set == null) {
            object = Collections.emptySet();
        }
        if (n2 > object.size()) {
            if (n2 == 1) {
                if (output != null) {
                    output.value = collection.iterator().next();
                }
                return PluralRules.KeywordStatus.UNIQUE;
            }
            return PluralRules.KeywordStatus.BOUNDED;
        }
        set = new HashSet<Double>(collection);
        object = object.iterator();
        while (object.hasNext()) {
            ((HashSet)set).remove((Double)object.next() - (double)n);
        }
        if (((HashSet)set).size() == 0) {
            return PluralRules.KeywordStatus.SUPPRESSED;
        }
        if (output != null && ((HashSet)set).size() == 1) {
            output.value = ((HashSet)set).iterator().next();
        }
        object = n2 == 1 ? PluralRules.KeywordStatus.UNIQUE : PluralRules.KeywordStatus.BOUNDED;
        return object;
    }
}

