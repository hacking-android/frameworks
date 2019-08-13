/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Iterator;

public interface ListIterator<E>
extends Iterator<E> {
    public void add(E var1);

    @Override
    public boolean hasNext();

    public boolean hasPrevious();

    @Override
    public E next();

    public int nextIndex();

    public E previous();

    public int previousIndex();

    @Override
    public void remove();

    public void set(E var1);
}

