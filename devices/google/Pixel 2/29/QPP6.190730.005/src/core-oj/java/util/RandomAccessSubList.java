/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;
import java.util.SubList;

class RandomAccessSubList<E>
extends SubList<E>
implements RandomAccess {
    RandomAccessSubList(AbstractList<E> abstractList, int n, int n2) {
        super(abstractList, n, n2);
    }

    @Override
    public List<E> subList(int n, int n2) {
        return new RandomAccessSubList<E>(this, n, n2);
    }
}

