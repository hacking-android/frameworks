/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import android.icu.util.ULocale;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalePriorityList
implements Iterable<ULocale> {
    private static final double D0 = 0.0;
    private static final Double D1 = 1.0;
    private static final Pattern languageSplitter = Pattern.compile("\\s*,\\s*");
    private static Comparator<Double> myDescendingDouble;
    private static final Pattern weightSplitter;
    private final Map<ULocale, Double> languagesAndWeights;

    static {
        weightSplitter = Pattern.compile("\\s*(\\S*)\\s*;\\s*q\\s*=\\s*(\\S*)");
        myDescendingDouble = new Comparator<Double>(){

            @Override
            public int compare(Double d, Double d2) {
                int n = d.compareTo(d2);
                n = n > 0 ? -1 : (n < 0 ? 1 : 0);
                return n;
            }
        };
    }

    private LocalePriorityList(Map<ULocale, Double> map) {
        this.languagesAndWeights = map;
    }

    public static Builder add(LocalePriorityList localePriorityList) {
        return new Builder().add(localePriorityList);
    }

    public static Builder add(ULocale uLocale, double d) {
        return new Builder().add(uLocale, d);
    }

    public static Builder add(String string) {
        return new Builder().add(string);
    }

    public static Builder add(ULocale ... arruLocale) {
        return new Builder().add(arruLocale);
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        try {
            object = (LocalePriorityList)object;
            boolean bl = this.languagesAndWeights.equals(((LocalePriorityList)object).languagesAndWeights);
            return bl;
        }
        catch (RuntimeException runtimeException) {
            return false;
        }
    }

    public Double getWeight(ULocale uLocale) {
        return this.languagesAndWeights.get(uLocale);
    }

    public int hashCode() {
        return this.languagesAndWeights.hashCode();
    }

    @Override
    public Iterator<ULocale> iterator() {
        return this.languagesAndWeights.keySet().iterator();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ULocale uLocale : this.languagesAndWeights.keySet()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(uLocale);
            double d = this.languagesAndWeights.get(uLocale);
            if (d == D1) continue;
            stringBuilder.append(";q=");
            stringBuilder.append(d);
        }
        return stringBuilder.toString();
    }

    public static class Builder {
        private final Map<ULocale, Double> languageToWeight = new LinkedHashMap<ULocale, Double>();

        private Builder() {
        }

        public Builder add(LocalePriorityList localePriorityList) {
            for (ULocale uLocale : localePriorityList.languagesAndWeights.keySet()) {
                this.add(uLocale, (Double)localePriorityList.languagesAndWeights.get(uLocale));
            }
            return this;
        }

        public Builder add(ULocale uLocale) {
            return this.add(uLocale, D1);
        }

        public Builder add(ULocale uLocale, double d) {
            if (this.languageToWeight.containsKey(uLocale)) {
                this.languageToWeight.remove(uLocale);
            }
            if (d <= 0.0) {
                return this;
            }
            double d2 = d;
            if (d > D1) {
                d2 = D1;
            }
            this.languageToWeight.put(uLocale, d2);
            return this;
        }

        public Builder add(String object) {
            object = languageSplitter.split(((String)object).trim());
            Matcher matcher = weightSplitter.matcher("");
            int n = ((String[])object).length;
            for (int i = 0; i < n; ++i) {
                Object object2 = object[i];
                if (matcher.reset((CharSequence)object2).matches()) {
                    object2 = new ULocale(matcher.group(1));
                    double d = Double.parseDouble(matcher.group(2));
                    if (d >= 0.0 && d <= D1) {
                        this.add((ULocale)object2, d);
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Illegal weight, must be 0..1: ");
                    ((StringBuilder)object).append(d);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                if (((String)object2).length() == 0) continue;
                this.add(new ULocale((String)object2));
            }
            return this;
        }

        public Builder add(ULocale ... arruLocale) {
            int n = arruLocale.length;
            for (int i = 0; i < n; ++i) {
                this.add(arruLocale[i], D1);
            }
            return this;
        }

        public LocalePriorityList build() {
            return this.build(false);
        }

        /*
         * WARNING - void declaration
         */
        public LocalePriorityList build(boolean bl) {
            LinkedHashSet linkedHashSet;
            TreeMap treeMap = new TreeMap(myDescendingDouble);
            for (ULocale object : this.languageToWeight.keySet()) {
                void var7_9;
                Comparable<Double> comparable = this.languageToWeight.get(object);
                linkedHashSet = (Set)treeMap.get(comparable);
                Set set = linkedHashSet;
                if (linkedHashSet == null) {
                    LinkedHashSet linkedHashSet2 = linkedHashSet = new LinkedHashSet();
                    treeMap.put(comparable, linkedHashSet);
                }
                var7_9.add(object);
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry entry : treeMap.entrySet()) {
                linkedHashSet = (Double)entry.getKey();
                for (Comparable<Double> comparable : (Set)entry.getValue()) {
                    void var7_17;
                    if (bl) {
                        LinkedHashSet linkedHashSet3 = linkedHashSet;
                    } else {
                        Double d = D1;
                    }
                    linkedHashMap.put(comparable, var7_17);
                }
            }
            return new LocalePriorityList(Collections.unmodifiableMap(linkedHashMap));
        }
    }

}

