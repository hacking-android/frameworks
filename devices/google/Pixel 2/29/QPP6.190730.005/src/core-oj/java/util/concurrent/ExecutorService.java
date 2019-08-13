/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface ExecutorService
extends Executor {
    public boolean awaitTermination(long var1, TimeUnit var3) throws InterruptedException;

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> var1) throws InterruptedException;

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> var1, long var2, TimeUnit var4) throws InterruptedException;

    public <T> T invokeAny(Collection<? extends Callable<T>> var1) throws InterruptedException, ExecutionException;

    public <T> T invokeAny(Collection<? extends Callable<T>> var1, long var2, TimeUnit var4) throws InterruptedException, ExecutionException, TimeoutException;

    public boolean isShutdown();

    public boolean isTerminated();

    public void shutdown();

    public List<Runnable> shutdownNow();

    public Future<?> submit(Runnable var1);

    public <T> Future<T> submit(Runnable var1, T var2);

    public <T> Future<T> submit(Callable<T> var1);
}

