/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

public class ExecutorCompletionService<V>
implements CompletionService<V> {
    private final AbstractExecutorService aes;
    private final BlockingQueue<Future<V>> completionQueue;
    private final Executor executor;

    public ExecutorCompletionService(Executor executor) {
        if (executor != null) {
            this.executor = executor;
            executor = executor instanceof AbstractExecutorService ? (AbstractExecutorService)executor : null;
            this.aes = executor;
            this.completionQueue = new LinkedBlockingQueue<Future<V>>();
            return;
        }
        throw new NullPointerException();
    }

    public ExecutorCompletionService(Executor executor, BlockingQueue<Future<V>> blockingQueue) {
        if (executor != null && blockingQueue != null) {
            this.executor = executor;
            executor = executor instanceof AbstractExecutorService ? (AbstractExecutorService)executor : null;
            this.aes = executor;
            this.completionQueue = blockingQueue;
            return;
        }
        throw new NullPointerException();
    }

    private RunnableFuture<V> newTaskFor(Runnable runnable, V v) {
        AbstractExecutorService abstractExecutorService = this.aes;
        if (abstractExecutorService == null) {
            return new FutureTask<V>(runnable, v);
        }
        return abstractExecutorService.newTaskFor(runnable, v);
    }

    private RunnableFuture<V> newTaskFor(Callable<V> callable) {
        AbstractExecutorService abstractExecutorService = this.aes;
        if (abstractExecutorService == null) {
            return new FutureTask<V>(callable);
        }
        return abstractExecutorService.newTaskFor(callable);
    }

    @Override
    public Future<V> poll() {
        return (Future)this.completionQueue.poll();
    }

    @Override
    public Future<V> poll(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.completionQueue.poll(l, timeUnit);
    }

    @Override
    public Future<V> submit(Runnable runnableFuture, V v) {
        if (runnableFuture != null) {
            runnableFuture = this.newTaskFor(runnableFuture, v);
            this.executor.execute(new QueueingFuture<V>(runnableFuture, this.completionQueue));
            return runnableFuture;
        }
        throw new NullPointerException();
    }

    @Override
    public Future<V> submit(Callable<V> object) {
        if (object != null) {
            object = this.newTaskFor((Callable<V>)object);
            this.executor.execute(new QueueingFuture<V>((RunnableFuture<V>)object, this.completionQueue));
            return object;
        }
        throw new NullPointerException();
    }

    @Override
    public Future<V> take() throws InterruptedException {
        return this.completionQueue.take();
    }

    private static class QueueingFuture<V>
    extends FutureTask<Void> {
        private final BlockingQueue<Future<V>> completionQueue;
        private final Future<V> task;

        QueueingFuture(RunnableFuture<V> runnableFuture, BlockingQueue<Future<V>> blockingQueue) {
            super(runnableFuture, null);
            this.task = runnableFuture;
            this.completionQueue = blockingQueue;
        }

        @Override
        protected void done() {
            this.completionQueue.add(this.task);
        }
    }

}

