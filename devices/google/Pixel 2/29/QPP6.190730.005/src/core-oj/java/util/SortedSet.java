/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;

public interface SortedSet<E>
extends Set<E> {
    public Comparator<? super E> comparator();

    public E first();

    public SortedSet<E> headSet(E var1);

    public E last();

    @Override
    default public Spliterator<E> spliterator() {
        return new Spliterators.IteratorSpliterator<E>((Collection)this, 21){

            @Override
            public Comparator<? super E> getComparator() {
                return SortedSet.this.comparator();
            }
        };
    }

    public SortedSet<E> subSet(E var1, E var2);

    public SortedSet<E> tailSet(E var1);

}

