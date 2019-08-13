/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface Future<V> {
    public boolean cancel(boolean var1);

    public V get() throws InterruptedException, ExecutionException;

    public V get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException;

    public boolean isCancelled();

    public boolean isDone();
}

