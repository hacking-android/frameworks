/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.RandomAccessSubList;
import java.util.SubList;

public abstract class AbstractList<E>
extends AbstractCollection<E>
implements List<E> {
    protected transient int modCount = 0;

    protected AbstractList() {
    }

    private String outOfBoundsMsg(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index: ");
        stringBuilder.append(n);
        stringBuilder.append(", Size: ");
        stringBuilder.append(this.size());
        return stringBuilder.toString();
    }

    private void rangeCheckForAdd(int n) {
        if (n >= 0 && n <= this.size()) {
            return;
        }
        throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
    }

    @Override
    public void add(int n, E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        this.add(this.size(), e);
        return true;
    }

    @Override
    public boolean addAll(int n, Collection<? extends E> object) {
        this.rangeCheckForAdd(n);
        boolean bl = false;
        object = object.iterator();
        while (object.hasNext()) {
            this.add(n, object.next());
            bl = true;
            ++n;
        }
        return bl;
    }

    @Override
    public void clear() {
        this.removeRange(0, this.size());
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof List)) {
            return false;
        }
        ListIterator<E> listIterator = this.listIterator();
        ListIterator listIterator2 = ((List)object).listIterator();
        while (listIterator.hasNext() && listIterator2.hasNext()) {
            object = listIterator.next();
            Object e = listIterator2.next();
            if (object != null ? object.equals(e) : e == null) continue;
            return false;
        }
        if (listIterator.hasNext() || listIterator2.hasNext()) {
            bl = false;
        }
        return bl;
    }

    @Override
    public abstract E get(int var1);

    @Override
    public int hashCode() {
        int n = 1;
        for (E e : this) {
            int n2 = e == null ? 0 : e.hashCode();
            n = n * 31 + n2;
        }
        return n;
    }

    @Override
    public int indexOf(Object object) {
        ListIterator<E> listIterator = this.listIterator();
        if (object == null) {
            while (listIterator.hasNext()) {
                if (listIterator.next() != null) continue;
                return listIterator.previousIndex();
            }
        } else {
            while (listIterator.hasNext()) {
                if (!object.equals(listIterator.next())) continue;
                return listIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public int lastIndexOf(Object object) {
        ListIterator<E> listIterator = this.listIterator(this.size());
        if (object == null) {
            while (listIterator.hasPrevious()) {
                if (listIterator.previous() != null) continue;
                return listIterator.nextIndex();
            }
        } else {
            while (listIterator.hasPrevious()) {
                if (!object.equals(listIterator.previous())) continue;
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return this.listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int n) {
        this.rangeCheckForAdd(n);
        return new ListItr(n);
    }

    @Override
    public E remove(int n) {
        throw new UnsupportedOperationException();
    }

    protected void removeRange(int n, int n2) {
        ListIterator<E> listIterator = this.listIterator(n);
        for (int i = 0; i < n2 - n; ++i) {
            listIterator.next();
            listIterator.remove();
        }
    }

    @Override
    public E set(int n, E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int n, int n2) {
        SubList subList = this instanceof RandomAccess ? new RandomAccessSubList<E>(this, n, n2) : new SubList<E>(this, n, n2);
        return subList;
    }

    private class Itr
    implements Iterator<E> {
        int cursor = 0;
        int expectedModCount;
        int lastRet = -1;

        private Itr() {
            this.expectedModCount = AbstractList.this.modCount;
        }

        final void checkForComodification() {
            if (AbstractList.this.modCount == this.expectedModCount) {
                return;
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.cursor != AbstractList.this.size();
            return bl;
        }

        @Override
        public E next() {
            Object e;
            this.checkForComodification();
            try {
                int n = this.cursor;
                e = AbstractList.this.get(n);
                this.lastRet = n;
                this.cursor = n + 1;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                this.checkForComodification();
                throw new NoSuchElementException();
            }
            return e;
        }

        @Override
        public void remove() {
            if (this.lastRet >= 0) {
                this.checkForComodification();
                try {
                    AbstractList.this.remove(this.lastRet);
                    if (this.lastRet < this.cursor) {
                        --this.cursor;
                    }
                    this.lastRet = -1;
                    this.expectedModCount = AbstractList.this.modCount;
                    return;
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new ConcurrentModificationException();
                }
            }
            throw new IllegalStateException();
        }
    }

    private class ListItr
    extends AbstractList<E>
    implements ListIterator<E> {
        ListItr(int n) {
            this.cursor = n;
        }

        @Override
        public void add(E e) {
            this.checkForComodification();
            try {
                int n = this.cursor;
                AbstractList.this.add(n, e);
                this.lastRet = -1;
                this.cursor = n + 1;
                this.expectedModCount = AbstractList.this.modCount;
                return;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasPrevious() {
            boolean bl = this.cursor != 0;
            return bl;
        }

        @Override
        public int nextIndex() {
            return this.cursor;
        }

        @Override
        public E previous() {
            Object e;
            this.checkForComodification();
            try {
                int n = this.cursor - 1;
                e = AbstractList.this.get(n);
                this.cursor = n;
                this.lastRet = n;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                this.checkForComodification();
                throw new NoSuchElementException();
            }
            return e;
        }

        @Override
        public int previousIndex() {
            return this.cursor - 1;
        }

        @Override
        public void set(E e) {
            if (this.lastRet >= 0) {
                this.checkForComodification();
                try {
                    AbstractList.this.set(this.lastRet, e);
                    this.expectedModCount = AbstractList.this.modCount;
                    return;
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new ConcurrentModificationException();
                }
            }
            throw new IllegalStateException();
        }
    }

}

