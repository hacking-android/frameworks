/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

public interface Set<E>
extends Collection<E> {
    @Override
    public boolean add(E var1);

    @Override
    public boolean addAll(Collection<? extends E> var1);

    @Override
    public void clear();

    @Override
    public boolean contains(Object var1);

    @Override
    public boolean containsAll(Collection<?> var1);

    @Override
    public boolean equals(Object var1);

    @Override
    public int hashCode();

    @Override
    public boolean isEmpty();

    @Override
    public Iterator<E> iterator();

    @Override
    public boolean remove(Object var1);

    @Override
    public boolean removeAll(Collection<?> var1);

    @Override
    public boolean retainAll(Collection<?> var1);

    @Override
    public int size();

    @Override
    default public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 1);
    }

    @Override
    public Object[] toArray();

    @Override
    public <T> T[] toArray(T[] var1);
}

