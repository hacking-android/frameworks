/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Iterator;
import java.util.SortedSet;

public interface NavigableSet<E>
extends SortedSet<E> {
    public E ceiling(E var1);

    public Iterator<E> descendingIterator();

    public NavigableSet<E> descendingSet();

    public E floor(E var1);

    public NavigableSet<E> headSet(E var1, boolean var2);

    @Override
    public SortedSet<E> headSet(E var1);

    public E higher(E var1);

    @Override
    public Iterator<E> iterator();

    public E lower(E var1);

    public E pollFirst();

    public E pollLast();

    public NavigableSet<E> subSet(E var1, boolean var2, E var3, boolean var4);

    @Override
    public SortedSet<E> subSet(E var1, E var2);

    public NavigableSet<E> tailSet(E var1, boolean var2);

    @Override
    public SortedSet<E> tailSet(E var1);
}

