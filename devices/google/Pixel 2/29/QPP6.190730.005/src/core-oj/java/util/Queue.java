/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Collection;

public interface Queue<E>
extends Collection<E> {
    @Override
    public boolean add(E var1);

    public E element();

    public boolean offer(E var1);

    public E peek();

    public E poll();

    public E remove();
}

