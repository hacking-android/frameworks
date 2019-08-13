/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetRelation {
    public static final int A = 6;
    public static final int ADDALL = 7;
    public static final int ANY = 7;
    public static final int A_AND_B = 2;
    public static final int A_NOT_B = 4;
    public static final int B = 3;
    public static final int B_NOT_A = 1;
    public static final int B_REMOVEALL = 1;
    public static final int COMPLEMENTALL = 5;
    public static final int CONTAINS = 6;
    public static final int DISJOINT = 5;
    public static final int EQUALS = 2;
    public static final int ISCONTAINED = 3;
    public static final int NONE = 0;
    public static final int NO_A = 1;
    public static final int NO_B = 4;
    public static final int REMOVEALL = 4;
    public static final int RETAINALL = 2;

    public static <T> SortedSet<? extends T> doOperation(SortedSet<T> object, int n, SortedSet<T> sortedSet) {
        switch (n) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Relation ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" out of range");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            case 7: {
                object.addAll(sortedSet);
                return object;
            }
            case 6: {
                return object;
            }
            case 5: {
                TreeSet<T> treeSet = new TreeSet<T>(sortedSet);
                treeSet.removeAll((Collection<?>)object);
                object.removeAll(sortedSet);
                object.addAll(treeSet);
                return object;
            }
            case 4: {
                object.removeAll(sortedSet);
                return object;
            }
            case 3: {
                object.clear();
                object.addAll(sortedSet);
                return object;
            }
            case 2: {
                object.retainAll(sortedSet);
                return object;
            }
            case 1: {
                sortedSet = new TreeSet<T>(sortedSet);
                ((AbstractSet)((Object)sortedSet)).removeAll((Collection<?>)object);
                object.clear();
                object.addAll(sortedSet);
                return object;
            }
            case 0: 
        }
        object.clear();
        return object;
    }

    public static <T> boolean hasRelation(SortedSet<T> object, int n, SortedSet<T> sortedSet) {
        if (n >= 0 && n <= 7) {
            boolean bl = (n & 4) != 0;
            boolean bl2 = (n & 2) != 0;
            boolean bl3 = (n & 1) != 0;
            if (n != 2 ? (n != 3 ? n == 6 && object.size() < sortedSet.size() : object.size() > sortedSet.size()) : object.size() != sortedSet.size()) {
                return false;
            }
            if (object.size() == 0) {
                if (sortedSet.size() == 0) {
                    return true;
                }
                return bl3;
            }
            if (sortedSet.size() == 0) {
                return bl;
            }
            Iterator iterator = object.iterator();
            Iterator iterator2 = sortedSet.iterator();
            object = iterator.next();
            sortedSet = iterator2.next();
            do {
                if ((n = ((Comparable)object).compareTo(sortedSet)) == 0) {
                    if (!bl2) {
                        return false;
                    }
                    if (!iterator.hasNext()) {
                        if (!iterator2.hasNext()) {
                            return true;
                        }
                        return bl3;
                    }
                    if (!iterator2.hasNext()) {
                        return bl;
                    }
                    object = iterator.next();
                    sortedSet = iterator2.next();
                    continue;
                }
                if (n < 0) {
                    if (!bl) {
                        return false;
                    }
                    if (!iterator.hasNext()) {
                        return bl3;
                    }
                    object = iterator.next();
                    continue;
                }
                if (!bl3) {
                    return false;
                }
                if (!iterator2.hasNext()) {
                    return bl;
                }
                sortedSet = iterator2.next();
            } while (true);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Relation ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" out of range");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }
}

