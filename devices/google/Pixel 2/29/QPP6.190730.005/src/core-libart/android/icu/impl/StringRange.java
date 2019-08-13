/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Relation;
import android.icu.lang.CharSequences;
import android.icu.util.ICUException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class StringRange {
    public static final Comparator<int[]> COMPARE_INT_ARRAYS = new Comparator<int[]>(){

        @Override
        public int compare(int[] arrn, int[] arrn2) {
            int n = Math.min(arrn.length, arrn2.length);
            for (int i = 0; i < n; ++i) {
                int n2 = arrn[i] - arrn2[i];
                if (n2 == 0) continue;
                return n2;
            }
            return arrn.length - arrn2.length;
        }
    };
    private static final boolean DEBUG = false;

    private static void add(int n, int n2, int[] arrn, int[] arrn2, StringBuilder stringBuilder, Collection<String> collection) {
        int n3 = arrn[n + n2];
        int n4 = arrn2[n];
        if (n3 <= n4) {
            int n5 = arrn2.length;
            boolean bl = true;
            if (n != n5 - 1) {
                bl = false;
            }
            n5 = stringBuilder.length();
            while (n3 <= n4) {
                stringBuilder.appendCodePoint(n3);
                if (bl) {
                    collection.add(stringBuilder.toString());
                } else {
                    StringRange.add(n + 1, n2, arrn, arrn2, stringBuilder, collection);
                }
                stringBuilder.setLength(n5);
                ++n3;
            }
            return;
        }
        throw new ICUException("Range must have x\u1d62 \u2264 y\u1d62 for each index i");
    }

    private static LinkedList<Ranges> compact(int n, Set<Ranges> object) {
        LinkedList<Ranges> linkedList = new LinkedList<Ranges>((Collection<Ranges>)object);
        --n;
        while (n >= 0) {
            Object object2 = null;
            Iterator iterator = linkedList.iterator();
            while (iterator.hasNext()) {
                object = (Ranges)iterator.next();
                if (object2 != null && ((Ranges)object2).merge(n, (Ranges)object)) {
                    iterator.remove();
                    object = object2;
                }
                object2 = object;
            }
            --n;
        }
        return linkedList;
    }

    public static void compact(Set<String> set, Adder adder, boolean bl) {
        StringRange.compact(set, adder, bl, false);
    }

    /*
     * WARNING - void declaration
     */
    public static void compact(Set<String> object, Adder adder, boolean bl, boolean bl2) {
        if (!bl2) {
            void iterator2;
            Object string = null;
            String string2 = null;
            int n = 0;
            int n2 = 0;
            Iterator iterator = object.iterator();
            object = string2;
            do {
                bl2 = iterator.hasNext();
                Object var9_16 = null;
                if (!bl2) break;
                string2 = (String)iterator.next();
                if (iterator2 != null) {
                    int n3;
                    if (string2.regionMatches(0, (String)iterator2, 0, n2) && (n3 = string2.codePointAt(n2)) == n + 1 && string2.length() == Character.charCount(n3) + n2) {
                        object = string2;
                        n = n3;
                        continue;
                    }
                    if (object == null) {
                        object = var9_16;
                    } else if (bl) {
                        object = ((String)object).substring(n2, ((String)object).length());
                    }
                    adder.add((String)iterator2, (String)object);
                }
                String string3 = string2;
                object = null;
                n = string2.codePointBefore(string2.length());
                n2 = string2.length() - Character.charCount(n);
            } while (true);
            if (object == null) {
                object = null;
            } else if (bl) {
                object = ((String)object).substring(n2, ((String)object).length());
            }
            adder.add((String)iterator2, (String)object);
        } else {
            Relation<Integer, Ranges> relation = Relation.of(new TreeMap(), TreeSet.class);
            object = object.iterator();
            while (object.hasNext()) {
                Ranges ranges = new Ranges((String)object.next());
                relation.put(ranges.size(), ranges);
            }
            for (Map.Entry entry : relation.keyValuesSet()) {
                for (Ranges ranges : StringRange.compact((Integer)entry.getKey(), (Set)entry.getValue())) {
                    adder.add(ranges.start(), ranges.end(bl));
                }
            }
        }
    }

    public static Collection<String> expand(String arrn, String arrn2, boolean bl, Collection<String> collection) {
        if (arrn != null && arrn2 != null) {
            arrn = CharSequences.codePoints((CharSequence)arrn);
            arrn2 = CharSequences.codePoints((CharSequence)arrn2);
            int n = arrn.length - arrn2.length;
            if (bl && n != 0) {
                throw new ICUException("Range must have equal-length strings");
            }
            if (n >= 0) {
                if (arrn2.length != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < n; ++i) {
                        stringBuilder.appendCodePoint(arrn[i]);
                    }
                    StringRange.add(0, n, arrn, arrn2, stringBuilder, collection);
                    return collection;
                }
                throw new ICUException("Range must have end-length > 0");
            }
            throw new ICUException("Range must have start-length \u2265 end-length");
        }
        throw new ICUException("Range must have 2 valid strings");
    }

    public static interface Adder {
        public void add(String var1, String var2);
    }

    static final class Range
    implements Comparable<Range> {
        int max;
        int min;

        public Range(int n, int n2) {
            this.min = n;
            this.max = n2;
        }

        @Override
        public int compareTo(Range range) {
            int n = this.min - range.min;
            if (n != 0) {
                return n;
            }
            return this.max - range.max;
        }

        public boolean equals(Object object) {
            boolean bl = this == object || object != null && object instanceof Range && this.compareTo((Range)object) == 0;
            return bl;
        }

        public int hashCode() {
            return this.min * 37 + this.max;
        }

        public String toString() {
            CharSequence charSequence = new StringBuilder().appendCodePoint(this.min);
            if (this.min == this.max) {
                charSequence = ((StringBuilder)charSequence).toString();
            } else {
                ((StringBuilder)charSequence).append('~');
                charSequence = ((StringBuilder)charSequence).appendCodePoint(this.max).toString();
            }
            return charSequence;
        }
    }

    static final class Ranges
    implements Comparable<Ranges> {
        private final Range[] ranges;

        public Ranges(String arrn) {
            arrn = CharSequences.codePoints((CharSequence)arrn);
            this.ranges = new Range[arrn.length];
            for (int i = 0; i < arrn.length; ++i) {
                this.ranges[i] = new Range(arrn[i], arrn[i]);
            }
        }

        @Override
        public int compareTo(Ranges ranges) {
            Range[] arrrange;
            int n = this.ranges.length - ranges.ranges.length;
            if (n != 0) {
                return n;
            }
            for (n = 0; n < (arrrange = this.ranges).length; ++n) {
                int n2 = arrrange[n].compareTo(ranges.ranges[n]);
                if (n2 == 0) continue;
                return n2;
            }
            return 0;
        }

        public String end(boolean bl) {
            Range[] arrrange;
            int n = this.firstDifference();
            if (n == this.ranges.length) {
                return null;
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (!bl) {
                n = 0;
            }
            while (n < (arrrange = this.ranges).length) {
                stringBuilder.appendCodePoint(arrrange[n].max);
                ++n;
            }
            return stringBuilder.toString();
        }

        public int firstDifference() {
            Range[] arrrange;
            for (int i = 0; i < (arrrange = this.ranges).length; ++i) {
                if (arrrange[i].min == this.ranges[i].max) continue;
                return i;
            }
            return arrrange.length;
        }

        public boolean merge(int n, Ranges ranges) {
            for (int i = this.ranges.length - 1; i >= 0; --i) {
                if (!(i == n ? this.ranges[i].max != ranges.ranges[i].min - 1 : !this.ranges[i].equals(ranges.ranges[i]))) continue;
                return false;
            }
            this.ranges[n].max = ranges.ranges[n].max;
            return true;
        }

        public Integer size() {
            return this.ranges.length;
        }

        public String start() {
            Range[] arrrange;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < (arrrange = this.ranges).length; ++i) {
                stringBuilder.appendCodePoint(arrrange[i].min);
            }
            return stringBuilder.toString();
        }

        public String toString() {
            String string = this.start();
            String string2 = this.end(false);
            if (string2 != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append("~");
                stringBuilder.append(string2);
                string = stringBuilder.toString();
            }
            return string;
        }
    }

}

