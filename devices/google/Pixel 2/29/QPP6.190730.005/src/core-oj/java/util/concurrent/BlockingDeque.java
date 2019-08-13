/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public interface BlockingDeque<E>
extends BlockingQueue<E>,
Deque<E> {
    @Override
    public boolean add(E var1);

    @Override
    public void addFirst(E var1);

    @Override
    public void addLast(E var1);

    @Override
    public boolean contains(Object var1);

    @Override
    public E element();

    @Override
    public Iterator<E> iterator();

    @Override
    public boolean offer(E var1);

    @Override
    public boolean offer(E var1, long var2, TimeUnit var4) throws InterruptedException;

    @Override
    public boolean offerFirst(E var1);

    public boolean offerFirst(E var1, long var2, TimeUnit var4) throws InterruptedException;

    @Override
    public boolean offerLast(E var1);

    public boolean offerLast(E var1, long var2, TimeUnit var4) throws InterruptedException;

    @Override
    public E peek();

    @Override
    public E poll();

    @Override
    public E poll(long var1, TimeUnit var3) throws InterruptedException;

    public E pollFirst(long var1, TimeUnit var3) throws InterruptedException;

    public E pollLast(long var1, TimeUnit var3) throws InterruptedException;

    @Override
    public void push(E var1);

    @Override
    public void put(E var1) throws InterruptedException;

    public void putFirst(E var1) throws InterruptedException;

    public void putLast(E var1) throws InterruptedException;

    @Override
    public E remove();

    @Override
    public boolean remove(Object var1);

    @Override
    public boolean removeFirstOccurrence(Object var1);

    @Override
    public boolean removeLastOccurrence(Object var1);

    @Override
    public int size();

    @Override
    public E take() throws InterruptedException;

    public E takeFirst() throws InterruptedException;

    public E takeLast() throws InterruptedException;
}

