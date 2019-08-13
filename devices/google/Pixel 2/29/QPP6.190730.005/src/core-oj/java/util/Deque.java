/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Iterator;
import java.util.Queue;

public interface Deque<E>
extends Queue<E> {
    @Override
    public boolean add(E var1);

    public void addFirst(E var1);

    public void addLast(E var1);

    @Override
    public boolean contains(Object var1);

    public Iterator<E> descendingIterator();

    @Override
    public E element();

    public E getFirst();

    public E getLast();

    @Override
    public Iterator<E> iterator();

    @Override
    public boolean offer(E var1);

    public boolean offerFirst(E var1);

    public boolean offerLast(E var1);

    @Override
    public E peek();

    public E peekFirst();

    public E peekLast();

    @Override
    public E poll();

    public E pollFirst();

    public E pollLast();

    public E pop();

    public void push(E var1);

    @Override
    public E remove();

    @Override
    public boolean remove(Object var1);

    public E removeFirst();

    public boolean removeFirstOccurrence(Object var1);

    public E removeLast();

    public boolean removeLastOccurrence(Object var1);

    @Override
    public int size();
}

