/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.util.Comparator;
import java.util.Iterator;

public class IterableComparator<T>
implements Comparator<Iterable<T>> {
    private static final IterableComparator NOCOMPARATOR = new IterableComparator<T>();
    private final Comparator<T> comparator;
    private final int shorterFirst;

    public IterableComparator() {
        this(null, true);
    }

    public IterableComparator(Comparator<T> comparator) {
        this(comparator, true);
    }

    public IterableComparator(Comparator<T> comparator, boolean bl) {
        this.comparator = comparator;
        int n = bl ? 1 : -1;
        this.shorterFirst = n;
    }

    public static <T> int compareIterables(Iterable<T> iterable, Iterable<T> iterable2) {
        return NOCOMPARATOR.compare(iterable, iterable2);
    }

    @Override
    public int compare(Iterable<T> object, Iterable<T> object2) {
        Comparator comparator;
        Object e;
        Object e2;
        int n = 0;
        int n2 = 0;
        if (object == null) {
            if (object2 != null) {
                n2 = -this.shorterFirst;
            }
            return n2;
        }
        if (object2 == null) {
            return this.shorterFirst;
        }
        object = object.iterator();
        object2 = object2.iterator();
        do {
            if (!object.hasNext()) {
                n2 = n;
                if (object2.hasNext()) {
                    n2 = -this.shorterFirst;
                }
                return n2;
            }
            if (!object2.hasNext()) {
                return this.shorterFirst;
            }
            e2 = object.next();
            e = object2.next();
        } while ((n2 = (comparator = this.comparator) != null ? comparator.compare(e2, e) : ((Comparable)e2).compareTo(e)) == 0);
        return n2;
    }
}

