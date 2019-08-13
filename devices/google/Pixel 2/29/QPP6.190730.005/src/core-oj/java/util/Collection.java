/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Collection<E>
extends Iterable<E> {
    public boolean add(E var1);

    public boolean addAll(Collection<? extends E> var1);

    public void clear();

    public boolean contains(Object var1);

    public boolean containsAll(Collection<?> var1);

    public boolean equals(Object var1);

    public int hashCode();

    public boolean isEmpty();

    @Override
    public Iterator<E> iterator();

    default public Stream<E> parallelStream() {
        return StreamSupport.stream(this.spliterator(), true);
    }

    public boolean remove(Object var1);

    public boolean removeAll(Collection<?> var1);

    default public boolean removeIf(Predicate<? super E> predicate) {
        Objects.requireNonNull(predicate);
        boolean bl = false;
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (!predicate.test(iterator.next())) continue;
            iterator.remove();
            bl = true;
        }
        return bl;
    }

    public boolean retainAll(Collection<?> var1);

    public int size();

    @Override
    default public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 0);
    }

    default public Stream<E> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Object[] toArray();

    public <T> T[] toArray(T[] var1);
}

