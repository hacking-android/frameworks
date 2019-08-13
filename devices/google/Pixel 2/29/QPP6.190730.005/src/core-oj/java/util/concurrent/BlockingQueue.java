/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public interface BlockingQueue<E>
extends Queue<E> {
    @Override
    public boolean add(E var1);

    @Override
    public boolean contains(Object var1);

    public int drainTo(Collection<? super E> var1);

    public int drainTo(Collection<? super E> var1, int var2);

    @Override
    public boolean offer(E var1);

    public boolean offer(E var1, long var2, TimeUnit var4) throws InterruptedException;

    public E poll(long var1, TimeUnit var3) throws InterruptedException;

    public void put(E var1) throws InterruptedException;

    public int remainingCapacity();

    @Override
    public boolean remove(Object var1);

    public E take() throws InterruptedException;
}

