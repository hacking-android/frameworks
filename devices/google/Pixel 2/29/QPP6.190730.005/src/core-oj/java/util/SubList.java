/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

class SubList<E>
extends AbstractList<E> {
    private final AbstractList<E> l;
    private final int offset;
    private int size;

    SubList(AbstractList<E> object, int n, int n2) {
        if (n >= 0) {
            if (n2 <= ((AbstractCollection)object).size()) {
                if (n <= n2) {
                    this.l = object;
                    this.offset = n;
                    this.size = n2 - n;
                    this.modCount = this.l.modCount;
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("fromIndex(");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(") > toIndex(");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(")");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("toIndex = ");
            ((StringBuilder)object).append(n2);
            throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("fromIndex = ");
        ((StringBuilder)object).append(n);
        throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    static /* synthetic */ int access$208(SubList subList) {
        int n = subList.size;
        subList.size = n + 1;
        return n;
    }

    static /* synthetic */ int access$210(SubList subList) {
        int n = subList.size;
        subList.size = n - 1;
        return n;
    }

    private void checkForComodification() {
        if (this.modCount == this.l.modCount) {
            return;
        }
        throw new ConcurrentModificationException();
    }

    private String outOfBoundsMsg(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index: ");
        stringBuilder.append(n);
        stringBuilder.append(", Size: ");
        stringBuilder.append(this.size);
        return stringBuilder.toString();
    }

    private void rangeCheck(int n) {
        if (n >= 0 && n < this.size) {
            return;
        }
        throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
    }

    private void rangeCheckForAdd(int n) {
        if (n >= 0 && n <= this.size) {
            return;
        }
        throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
    }

    @Override
    public void add(int n, E e) {
        this.rangeCheckForAdd(n);
        this.checkForComodification();
        this.l.add(this.offset + n, e);
        this.modCount = this.l.modCount;
        ++this.size;
    }

    @Override
    public boolean addAll(int n, Collection<? extends E> collection) {
        this.rangeCheckForAdd(n);
        int n2 = collection.size();
        if (n2 == 0) {
            return false;
        }
        this.checkForComodification();
        this.l.addAll(this.offset + n, collection);
        this.modCount = this.l.modCount;
        this.size += n2;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return this.addAll(this.size, collection);
    }

    @Override
    public E get(int n) {
        this.rangeCheck(n);
        this.checkForComodification();
        return this.l.get(this.offset + n);
    }

    @Override
    public Iterator<E> iterator() {
        return this.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(final int n) {
        this.checkForComodification();
        this.rangeCheckForAdd(n);
        return new ListIterator<E>(){
            private final ListIterator<E> i;
            {
                this.i = SubList.this.l.listIterator(n + SubList.this.offset);
            }

            @Override
            public void add(E object) {
                this.i.add(object);
                object = SubList.this;
                ((SubList)object).modCount = SubList.access$100(object).modCount;
                SubList.access$208(SubList.this);
            }

            @Override
            public boolean hasNext() {
                boolean bl = this.nextIndex() < SubList.this.size;
                return bl;
            }

            @Override
            public boolean hasPrevious() {
                boolean bl = this.previousIndex() >= 0;
                return bl;
            }

            @Override
            public E next() {
                if (this.hasNext()) {
                    return this.i.next();
                }
                throw new NoSuchElementException();
            }

            @Override
            public int nextIndex() {
                return this.i.nextIndex() - SubList.this.offset;
            }

            @Override
            public E previous() {
                if (this.hasPrevious()) {
                    return this.i.previous();
                }
                throw new NoSuchElementException();
            }

            @Override
            public int previousIndex() {
                return this.i.previousIndex() - SubList.this.offset;
            }

            @Override
            public void remove() {
                this.i.remove();
                SubList subList = SubList.this;
                subList.modCount = SubList.access$100((SubList)subList).modCount;
                SubList.access$210(SubList.this);
            }

            @Override
            public void set(E e) {
                this.i.set(e);
            }
        };
    }

    @Override
    public E remove(int n) {
        this.rangeCheck(n);
        this.checkForComodification();
        E e = this.l.remove(this.offset + n);
        this.modCount = this.l.modCount;
        --this.size;
        return e;
    }

    @Override
    protected void removeRange(int n, int n2) {
        this.checkForComodification();
        AbstractList<E> abstractList = this.l;
        int n3 = this.offset;
        abstractList.removeRange(n + n3, n3 + n2);
        this.modCount = this.l.modCount;
        this.size -= n2 - n;
    }

    @Override
    public E set(int n, E e) {
        this.rangeCheck(n);
        this.checkForComodification();
        return this.l.set(this.offset + n, e);
    }

    @Override
    public int size() {
        this.checkForComodification();
        return this.size;
    }

    @Override
    public List<E> subList(int n, int n2) {
        return new SubList<E>(this, n, n2);
    }

}

