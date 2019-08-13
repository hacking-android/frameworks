/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public interface TransferQueue<E>
extends BlockingQueue<E> {
    public int getWaitingConsumerCount();

    public boolean hasWaitingConsumer();

    public void transfer(E var1) throws InterruptedException;

    public boolean tryTransfer(E var1);

    public boolean tryTransfer(E var1, long var2, TimeUnit var4) throws InterruptedException;
}

