/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public abstract class AbstractCollection<E>
implements Collection<E> {
    private static final int MAX_ARRAY_SIZE = 2147483639;

    protected AbstractCollection() {
    }

    private static <T> T[] finishToArray(T[] arrT, Iterator<?> iterator) {
        int n = arrT.length;
        while (iterator.hasNext()) {
            int n2 = arrT.length;
            T[] arrT2 = arrT;
            if (n == n2) {
                int n3;
                int n4 = n3 = (n2 >> 1) + n2 + 1;
                if (n3 - 2147483639 > 0) {
                    n4 = AbstractCollection.hugeCapacity(n2 + 1);
                }
                arrT2 = Arrays.copyOf(arrT, n4);
            }
            arrT2[n] = iterator.next();
            ++n;
            arrT = arrT2;
        }
        if (n != arrT.length) {
            arrT = Arrays.copyOf(arrT, n);
        }
        return arrT;
    }

    private static int hugeCapacity(int n) {
        if (n >= 0) {
            int n2 = 2147483639;
            n = n > 2147483639 ? Integer.MAX_VALUE : n2;
            return n;
        }
        throw new OutOfMemoryError("Required array size too large");
    }

    @Override
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> object) {
        boolean bl = false;
        object = object.iterator();
        while (object.hasNext()) {
            if (!this.add(object.next())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public void clear() {
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    @Override
    public boolean contains(Object object) {
        Iterator<E> iterator = this.iterator();
        if (object == null) {
            while (iterator.hasNext()) {
                if (iterator.next() != null) continue;
                return true;
            }
        } else {
            while (iterator.hasNext()) {
                if (!object.equals(iterator.next())) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> object) {
        object = object.iterator();
        while (object.hasNext()) {
            if (this.contains(object.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.size() == 0;
        return bl;
    }

    @Override
    public abstract Iterator<E> iterator();

    @Override
    public boolean remove(Object object) {
        Iterator<E> iterator = this.iterator();
        if (object == null) {
            while (iterator.hasNext()) {
                if (iterator.next() != null) continue;
                iterator.remove();
                return true;
            }
        } else {
            while (iterator.hasNext()) {
                if (!object.equals(iterator.next())) continue;
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        Objects.requireNonNull(collection);
        boolean bl = false;
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (!collection.contains(iterator.next())) continue;
            iterator.remove();
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        Objects.requireNonNull(collection);
        boolean bl = false;
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (collection.contains(iterator.next())) continue;
            iterator.remove();
            bl = true;
        }
        return bl;
    }

    @Override
    public abstract int size();

    @Override
    public Object[] toArray() {
        Object[] arrobject;
        block2 : {
            arrobject = new Object[this.size()];
            Iterator<E> iterator = this.iterator();
            for (int i = 0; i < arrobject.length; ++i) {
                if (!iterator.hasNext()) {
                    return Arrays.copyOf(arrobject, i);
                }
                arrobject[i] = iterator.next();
            }
            if (!iterator.hasNext()) break block2;
            arrobject = AbstractCollection.finishToArray(arrobject, iterator);
        }
        return arrobject;
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        int n = this.size();
        Object[] arrobject = arrT.length >= n ? arrT : (Object[])Array.newInstance(arrT.getClass().getComponentType(), n);
        Iterator<E> iterator = this.iterator();
        for (n = 0; n < arrobject.length; ++n) {
            if (!iterator.hasNext()) {
                if (arrT == arrobject) {
                    arrobject[n] = null;
                } else {
                    if (arrT.length < n) {
                        return Arrays.copyOf(arrobject, n);
                    }
                    System.arraycopy(arrobject, 0, arrT, 0, n);
                    if (arrT.length > n) {
                        arrT[n] = null;
                    }
                }
                return arrT;
            }
            arrobject[n] = iterator.next();
        }
        arrT = iterator.hasNext() ? AbstractCollection.finishToArray(arrobject, iterator) : arrobject;
        return arrT;
    }

    public String toString() {
        Iterator<E> iterator = this.iterator();
        if (!iterator.hasNext()) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        do {
            Object object;
            if ((object = iterator.next()) == this) {
                object = "(this Collection)";
            }
            stringBuilder.append(object);
            if (!iterator.hasNext()) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(',');
            stringBuilder.append(' ');
        } while (true);
    }
}

