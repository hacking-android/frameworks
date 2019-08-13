/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class AbstractSequentialList<E>
extends AbstractList<E> {
    protected AbstractSequentialList() {
    }

    @Override
    public void add(int n, E e) {
        try {
            this.listIterator(n).add(e);
            return;
        }
        catch (NoSuchElementException noSuchElementException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index: ");
            stringBuilder.append(n);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
    }

    @Override
    public boolean addAll(int n, Collection<? extends E> object) {
        boolean bl = false;
        try {
            ListIterator listIterator = this.listIterator(n);
            object = object.iterator();
            while (object.hasNext()) {
                listIterator.add(object.next());
                bl = true;
            }
            return bl;
        }
        catch (NoSuchElementException noSuchElementException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index: ");
            stringBuilder.append(n);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
    }

    @Override
    public E get(int n) {
        E e;
        try {
            e = this.listIterator(n).next();
        }
        catch (NoSuchElementException noSuchElementException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index: ");
            stringBuilder.append(n);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        return e;
    }

    @Override
    public Iterator<E> iterator() {
        return this.listIterator();
    }

    @Override
    public abstract ListIterator<E> listIterator(int var1);

    @Override
    public E remove(int n) {
        E e;
        try {
            ListIterator<E> listIterator = this.listIterator(n);
            e = listIterator.next();
            listIterator.remove();
        }
        catch (NoSuchElementException noSuchElementException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index: ");
            stringBuilder.append(n);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        return e;
    }

    @Override
    public E set(int n, E e) {
        E e2;
        try {
            ListIterator<E> listIterator = this.listIterator(n);
            e2 = listIterator.next();
            listIterator.set(e);
        }
        catch (NoSuchElementException noSuchElementException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index: ");
            stringBuilder.append(n);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        return e2;
    }
}

