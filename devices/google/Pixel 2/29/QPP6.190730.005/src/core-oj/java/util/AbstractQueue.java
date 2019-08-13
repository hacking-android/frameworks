/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public abstract class AbstractQueue<E>
extends AbstractCollection<E>
implements Queue<E> {
    protected AbstractQueue() {
    }

    @Override
    public boolean add(E e) {
        if (this.offer(e)) {
            return true;
        }
        throw new IllegalStateException("Queue full");
    }

    @Override
    public boolean addAll(Collection<? extends E> object) {
        if (object != null) {
            if (object != this) {
                boolean bl = false;
                object = object.iterator();
                while (object.hasNext()) {
                    if (!this.add(object.next())) continue;
                    bl = true;
                }
                return bl;
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    @Override
    public void clear() {
        while (this.poll() != null) {
        }
    }

    @Override
    public E element() {
        Object e = this.peek();
        if (e != null) {
            return e;
        }
        throw new NoSuchElementException();
    }

    @Override
    public E remove() {
        Object e = this.poll();
        if (e != null) {
            return e;
        }
        throw new NoSuchElementException();
    }
}

