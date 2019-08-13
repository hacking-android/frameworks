/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.UnaryOperator;

public interface List<E>
extends Collection<E> {
    public void add(int var1, E var2);

    @Override
    public boolean add(E var1);

    public boolean addAll(int var1, Collection<? extends E> var2);

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

    public E get(int var1);

    @Override
    public int hashCode();

    public int indexOf(Object var1);

    @Override
    public boolean isEmpty();

    @Override
    public Iterator<E> iterator();

    public int lastIndexOf(Object var1);

    public ListIterator<E> listIterator();

    public ListIterator<E> listIterator(int var1);

    public E remove(int var1);

    @Override
    public boolean remove(Object var1);

    @Override
    public boolean removeAll(Collection<?> var1);

    default public void replaceAll(UnaryOperator<E> unaryOperator) {
        Objects.requireNonNull(unaryOperator);
        ListIterator<R> listIterator = this.listIterator();
        while (listIterator.hasNext()) {
            listIterator.set(unaryOperator.apply(listIterator.next()));
        }
    }

    @Override
    public boolean retainAll(Collection<?> var1);

    public E set(int var1, E var2);

    @Override
    public int size();

    default public void sort(Comparator<? super E> object) {
        Object[] arrobject = this.toArray();
        Arrays.sort(arrobject, object);
        object = this.listIterator();
        for (Object object2 : arrobject) {
            object.next();
            object.set(object2);
        }
    }

    @Override
    default public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 16);
    }

    public List<E> subList(int var1, int var2);

    @Override
    public Object[] toArray();

    @Override
    public <T> T[] toArray(T[] var1);
}

