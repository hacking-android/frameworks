/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.locks;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public interface Lock {
    public void lock();

    public void lockInterruptibly() throws InterruptedException;

    public Condition newCondition();

    public boolean tryLock();

    public boolean tryLock(long var1, TimeUnit var3) throws InterruptedException;

    public void unlock();
}

