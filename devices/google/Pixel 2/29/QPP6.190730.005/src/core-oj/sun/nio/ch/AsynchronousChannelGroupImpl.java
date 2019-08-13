/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.Channel;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import sun.nio.ch.Invoker;
import sun.nio.ch.ThreadPool;
import sun.security.action.GetIntegerAction;

abstract class AsynchronousChannelGroupImpl
extends AsynchronousChannelGroup
implements Executor {
    private static final int internalThreadCount = AccessController.doPrivileged(new GetIntegerAction("sun.nio.ch.internalThreadPoolSize", 1));
    private final ThreadPool pool;
    private final AtomicBoolean shutdown = new AtomicBoolean();
    private final Object shutdownNowLock = new Object();
    private final Queue<Runnable> taskQueue;
    private volatile boolean terminateInitiated;
    private final AtomicInteger threadCount = new AtomicInteger();
    private ScheduledThreadPoolExecutor timeoutExecutor;

    AsynchronousChannelGroupImpl(AsynchronousChannelProvider asynchronousChannelProvider, ThreadPool threadPool) {
        super(asynchronousChannelProvider);
        this.pool = threadPool;
        this.taskQueue = threadPool.isFixedThreadPool() ? new ConcurrentLinkedQueue<Runnable>() : null;
        this.timeoutExecutor = (ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1, ThreadPool.defaultThreadFactory());
        this.timeoutExecutor.setRemoveOnCancelPolicy(true);
    }

    private Runnable bindToGroup(final Runnable runnable) {
        return new Runnable(){

            @Override
            public void run() {
                Invoker.bindToGroup(this);
                runnable.run();
            }
        };
    }

    private void shutdownExecutors() {
        AccessController.doPrivileged(new PrivilegedAction<Void>(){

            @Override
            public Void run() {
                AsynchronousChannelGroupImpl.this.pool.executor().shutdown();
                AsynchronousChannelGroupImpl.this.timeoutExecutor.shutdown();
                return null;
            }
        });
    }

    private void startInternalThread(final Runnable runnable) {
        AccessController.doPrivileged(new PrivilegedAction<Void>(){

            @Override
            public Void run() {
                ThreadPool.defaultThreadFactory().newThread(runnable).start();
                return null;
            }
        });
    }

    abstract Object attachForeignChannel(Channel var1, FileDescriptor var2) throws IOException;

    @Override
    public final boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.pool.executor().awaitTermination(l, timeUnit);
    }

    abstract void closeAllChannels() throws IOException;

    abstract void detachForeignChannel(Object var1);

    final void detachFromThreadPool() {
        if (!this.shutdown.getAndSet(true)) {
            if (this.isEmpty()) {
                this.shutdownHandlerTasks();
                return;
            }
            throw new AssertionError((Object)"Group not empty");
        }
        throw new AssertionError((Object)"Already shutdown");
    }

    @Override
    public final void execute(final Runnable runnable) {
        Runnable runnable2 = runnable;
        if (System.getSecurityManager() != null) {
            runnable2 = new Runnable(AccessController.getContext()){
                final /* synthetic */ AccessControlContext val$acc;
                {
                    this.val$acc = accessControlContext;
                }

                @Override
                public void run() {
                    AccessController.doPrivileged(new PrivilegedAction<Void>(){

                        @Override
                        public Void run() {
                            runnable.run();
                            return null;
                        }
                    }, this.val$acc);
                }

            };
        }
        this.executeOnPooledThread(runnable2);
    }

    abstract void executeOnHandlerTask(Runnable var1);

    final void executeOnPooledThread(Runnable runnable) {
        if (this.isFixedThreadPool()) {
            this.executeOnHandlerTask(runnable);
        } else {
            this.pool.executor().execute(this.bindToGroup(runnable));
        }
    }

    final ExecutorService executor() {
        return this.pool.executor();
    }

    final int fixedThreadCount() {
        if (this.isFixedThreadPool()) {
            return this.pool.poolSize();
        }
        return this.pool.poolSize() + internalThreadCount;
    }

    abstract boolean isEmpty();

    final boolean isFixedThreadPool() {
        return this.pool.isFixedThreadPool();
    }

    @Override
    public final boolean isShutdown() {
        return this.shutdown.get();
    }

    @Override
    public final boolean isTerminated() {
        return this.pool.executor().isTerminated();
    }

    final void offerTask(Runnable runnable) {
        this.taskQueue.offer(runnable);
    }

    final Runnable pollTask() {
        Queue<Runnable> queue = this.taskQueue;
        queue = queue == null ? null : queue.poll();
        return queue;
    }

    final Future<?> schedule(Runnable object, long l, TimeUnit timeUnit) {
        try {
            object = this.timeoutExecutor.schedule((Runnable)object, l, timeUnit);
            return object;
        }
        catch (RejectedExecutionException rejectedExecutionException) {
            if (this.terminateInitiated) {
                return null;
            }
            throw new AssertionError(rejectedExecutionException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void shutdown() {
        if (this.shutdown.getAndSet(true)) {
            return;
        }
        if (!this.isEmpty()) {
            return;
        }
        Object object = this.shutdownNowLock;
        synchronized (object) {
            if (!this.terminateInitiated) {
                this.terminateInitiated = true;
                this.shutdownHandlerTasks();
                this.shutdownExecutors();
            }
            return;
        }
    }

    abstract void shutdownHandlerTasks();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void shutdownNow() throws IOException {
        this.shutdown.set(true);
        Object object = this.shutdownNowLock;
        synchronized (object) {
            if (!this.terminateInitiated) {
                this.terminateInitiated = true;
                this.closeAllChannels();
                this.shutdownHandlerTasks();
                this.shutdownExecutors();
            }
            return;
        }
    }

    protected final void startThreads(Runnable runnable) {
        int n;
        if (!this.isFixedThreadPool()) {
            for (n = 0; n < internalThreadCount; ++n) {
                this.startInternalThread(runnable);
                this.threadCount.incrementAndGet();
            }
        }
        if (this.pool.poolSize() > 0) {
            runnable = this.bindToGroup(runnable);
            n = 0;
            do {
                try {
                    if (n >= this.pool.poolSize()) break;
                    this.pool.executor().execute(runnable);
                    this.threadCount.incrementAndGet();
                    ++n;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    // empty catch block
                    break;
                }
            } while (true);
        }
    }

    final int threadCount() {
        return this.threadCount.get();
    }

    final int threadExit(Runnable runnable, boolean bl) {
        if (bl) {
            try {
                if (Invoker.isBoundToAnyGroup()) {
                    this.pool.executor().execute(this.bindToGroup(runnable));
                } else {
                    this.startInternalThread(runnable);
                }
                int n = this.threadCount.get();
                return n;
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                // empty catch block
            }
        }
        return this.threadCount.decrementAndGet();
    }

}

