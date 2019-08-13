/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CopyOnWriteArraySet<E>
extends AbstractSet<E>
implements Serializable {
    private static final long serialVersionUID = 5457747651344034263L;
    private final CopyOnWriteArrayList<E> al;

    public CopyOnWriteArraySet() {
        this.al = new CopyOnWriteArrayList();
    }

    public CopyOnWriteArraySet(Collection<? extends E> collection) {
        if (collection.getClass() == CopyOnWriteArraySet.class) {
            this.al = new CopyOnWriteArrayList<E>(((CopyOnWriteArraySet)collection).al);
        } else {
            this.al = new CopyOnWriteArrayList();
            this.al.addAllAbsent(collection);
        }
    }

    private static int compareSets(Object[] arrobject, Set<?> object) {
        int n;
        int n2;
        int n3;
        block4 : {
            n3 = arrobject.length;
            boolean[] arrbl = new boolean[n3];
            n = 0;
            object = object.iterator();
            block0 : do {
                boolean bl = object.hasNext();
                n2 = 1;
                if (!bl) break block4;
                Object e = object.next();
                for (n2 = n; n2 < n3; ++n2) {
                    if (arrbl[n2] || !Objects.equals(e, arrobject[n2])) continue;
                    arrbl[n2] = true;
                    if (n2 != n) continue block0;
                    do {
                        n = n2 = n + 1;
                        if (n2 >= n3) continue block0;
                        n = n2;
                    } while (arrbl[n2]);
                    n = n2;
                    continue block0;
                }
                break;
            } while (true);
            return -1;
        }
        if (n == n3) {
            n2 = 0;
        }
        return n2;
    }

    @Override
    public boolean add(E e) {
        return this.al.addIfAbsent(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean bl = this.al.addAllAbsent(collection) > 0;
        return bl;
    }

    @Override
    public void clear() {
        this.al.clear();
    }

    @Override
    public boolean contains(Object object) {
        return this.al.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        boolean bl = collection instanceof Set ? CopyOnWriteArraySet.compareSets(this.al.getArray(), (Set)collection) >= 0 : this.al.containsAll(collection);
        return bl;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object == this || object instanceof Set && CopyOnWriteArraySet.compareSets(this.al.getArray(), (Set)object) == 0;
        return bl;
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        this.al.forEach(consumer);
    }

    @Override
    public boolean isEmpty() {
        return this.al.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return this.al.iterator();
    }

    @Override
    public boolean remove(Object object) {
        return this.al.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.al.removeAll(collection);
    }

    @Override
    public boolean removeIf(Predicate<? super E> predicate) {
        return this.al.removeIf(predicate);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.al.retainAll(collection);
    }

    @Override
    public int size() {
        return this.al.size();
    }

    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.al.getArray(), 1025);
    }

    @Override
    public Object[] toArray() {
        return this.al.toArray();
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        return this.al.toArray(arrT);
    }
}

