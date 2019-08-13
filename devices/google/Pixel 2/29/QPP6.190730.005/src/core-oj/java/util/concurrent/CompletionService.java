/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public interface CompletionService<V> {
    public Future<V> poll();

    public Future<V> poll(long var1, TimeUnit var3) throws InterruptedException;

    public Future<V> submit(Runnable var1, V var2);

    public Future<V> submit(Callable<V> var1);

    public Future<V> take() throws InterruptedException;
}

