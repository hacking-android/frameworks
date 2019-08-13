/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.locks;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public interface Condition {
    public void await() throws InterruptedException;

    public boolean await(long var1, TimeUnit var3) throws InterruptedException;

    public long awaitNanos(long var1) throws InterruptedException;

    public void awaitUninterruptibly();

    public boolean awaitUntil(Date var1) throws InterruptedException;

    public void signal();

    public void signalAll();
}

