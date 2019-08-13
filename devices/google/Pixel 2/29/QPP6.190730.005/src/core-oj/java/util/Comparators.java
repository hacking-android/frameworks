/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

class Comparators {
    private Comparators() {
        throw new AssertionError((Object)"no instances");
    }

    static enum NaturalOrderComparator implements Comparator<Comparable<Object>>
    {
        INSTANCE;
        

        @Override
        public int compare(Comparable<Object> comparable, Comparable<Object> comparable2) {
            return comparable.compareTo(comparable2);
        }

        @Override
        public Comparator<Comparable<Object>> reversed() {
            return Comparator.reverseOrder();
        }
    }

    static final class NullComparator<T>
    implements Comparator<T>,
    Serializable {
        private static final long serialVersionUID = -7569533591570686392L;
        private final boolean nullFirst;
        private final Comparator<T> real;

        NullComparator(boolean bl, Comparator<? super T> comparator) {
            this.nullFirst = bl;
            this.real = comparator;
        }

        @Override
        public int compare(T t, T t2) {
            int n = 1;
            int n2 = 1;
            int n3 = 0;
            if (t == null) {
                if (t2 == null) {
                    n2 = 0;
                } else if (this.nullFirst) {
                    n2 = -1;
                }
                return n2;
            }
            if (t2 == null) {
                n2 = this.nullFirst ? n : -1;
                return n2;
            }
            Comparator<T> comparator = this.real;
            n2 = comparator == null ? n3 : comparator.compare(t, t2);
            return n2;
        }

        @Override
        public Comparator<T> reversed() {
            boolean bl = this.nullFirst;
            Comparator<T> comparator = this.real;
            comparator = comparator == null ? null : comparator.reversed();
            return new NullComparator<T>(bl ^ true, comparator);
        }

        @Override
        public Comparator<T> thenComparing(Comparator<? super T> comparator) {
            Objects.requireNonNull(comparator);
            boolean bl = this.nullFirst;
            Comparator<? super T> comparator2 = this.real;
            if (comparator2 != null) {
                comparator = comparator2.thenComparing(comparator);
            }
            return new NullComparator<T>(bl, comparator);
        }
    }

}

