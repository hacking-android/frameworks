/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public interface ScheduledExecutorService
extends ExecutorService {
    public ScheduledFuture<?> schedule(Runnable var1, long var2, TimeUnit var4);

    public <V> ScheduledFuture<V> schedule(Callable<V> var1, long var2, TimeUnit var4);

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable var1, long var2, long var4, TimeUnit var6);

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable var1, long var2, long var4, TimeUnit var6);
}

