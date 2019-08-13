/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import java.util.AbstractSet;
import java.util.Iterator;

public final class FastImmutableArraySet<T>
extends AbstractSet<T> {
    T[] mContents;
    FastIterator<T> mIterator;

    public FastImmutableArraySet(T[] arrT) {
        this.mContents = arrT;
    }

    @Override
    public Iterator<T> iterator() {
        FastIterator<T> fastIterator = this.mIterator;
        if (fastIterator == null) {
            this.mIterator = fastIterator = new FastIterator<T>(this.mContents);
        } else {
            fastIterator.mIndex = 0;
        }
        return fastIterator;
    }

    @Override
    public int size() {
        return this.mContents.length;
    }

    private static final class FastIterator<T>
    implements Iterator<T> {
        private final T[] mContents;
        int mIndex;

        public FastIterator(T[] arrT) {
            this.mContents = arrT;
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.mIndex != this.mContents.length;
            return bl;
        }

        @Override
        public T next() {
            T[] arrT = this.mContents;
            int n = this.mIndex;
            this.mIndex = n + 1;
            return arrT[n];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}

