/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.ReachabilitySensitive
 */
package java.util.concurrent;

import dalvik.annotation.optimization.ReachabilitySensitive;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class Executors {
    private Executors() {
    }

    public static Callable<Object> callable(Runnable runnable) {
        if (runnable != null) {
            return new RunnableAdapter<Object>(runnable, null);
        }
        throw new NullPointerException();
    }

    public static <T> Callable<T> callable(Runnable runnable, T t) {
        if (runnable != null) {
            return new RunnableAdapter<T>(runnable, t);
        }
        throw new NullPointerException();
    }

    public static Callable<Object> callable(final PrivilegedAction<?> privilegedAction) {
        if (privilegedAction != null) {
            return new Callable<Object>(){

                @Override
                public Object call() {
                    return privilegedAction.run();
                }
            };
        }
        throw new NullPointerException();
    }

    public static Callable<Object> callable(final PrivilegedExceptionAction<?> privilegedExceptionAction) {
        if (privilegedExceptionAction != null) {
            return new Callable<Object>(){

                @Override
                public Object call() throws Exception {
                    return privilegedExceptionAction.run();
                }
            };
        }
        throw new NullPointerException();
    }

    public static ThreadFactory defaultThreadFactory() {
        return new DefaultThreadFactory();
    }

    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }

    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
    }

    public static ExecutorService newFixedThreadPool(int n) {
        return new ThreadPoolExecutor(n, n, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    public static ExecutorService newFixedThreadPool(int n, ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(n, n, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
    }

    public static ScheduledExecutorService newScheduledThreadPool(int n) {
        return new ScheduledThreadPoolExecutor(n);
    }

    public static ScheduledExecutorService newScheduledThreadPool(int n, ThreadFactory threadFactory) {
        return new ScheduledThreadPoolExecutor(n, threadFactory);
    }

    public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()));
    }

    public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory));
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
        return new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1));
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
        return new DelegatedScheduledExecutorService(new ScheduledThreadPoolExecutor(1, threadFactory));
    }

    public static ExecutorService newWorkStealingPool() {
        return new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
    }

    public static ExecutorService newWorkStealingPool(int n) {
        return new ForkJoinPool(n, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
    }

    public static <T> Callable<T> privilegedCallable(Callable<T> callable) {
        if (callable != null) {
            return new PrivilegedCallable<T>(callable);
        }
        throw new NullPointerException();
    }

    public static <T> Callable<T> privilegedCallableUsingCurrentClassLoader(Callable<T> callable) {
        if (callable != null) {
            return new PrivilegedCallableUsingCurrentClassLoader<T>(callable);
        }
        throw new NullPointerException();
    }

    public static ThreadFactory privilegedThreadFactory() {
        return new PrivilegedThreadFactory();
    }

    public static ExecutorService unconfigurableExecutorService(ExecutorService executorService) {
        if (executorService != null) {
            return new DelegatedExecutorService(executorService);
        }
        throw new NullPointerException();
    }

    public static ScheduledExecutorService unconfigurableScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        if (scheduledExecutorService != null) {
            return new DelegatedScheduledExecutorService(scheduledExecutorService);
        }
        throw new NullPointerException();
    }

    private static class DefaultThreadFactory
    implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final String namePrefix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        DefaultThreadFactory() {
            Object object = System.getSecurityManager();
            object = object != null ? ((SecurityManager)object).getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.group = object;
            object = new StringBuilder();
            ((StringBuilder)object).append("pool-");
            ((StringBuilder)object).append(poolNumber.getAndIncrement());
            ((StringBuilder)object).append("-thread-");
            this.namePrefix = ((StringBuilder)object).toString();
        }

        @Override
        public Thread newThread(Runnable runnable) {
            ThreadGroup threadGroup = this.group;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.namePrefix);
            stringBuilder.append(this.threadNumber.getAndIncrement());
            runnable = new Thread(threadGroup, runnable, stringBuilder.toString(), 0L);
            if (((Thread)runnable).isDaemon()) {
                ((Thread)runnable).setDaemon(false);
            }
            if (((Thread)runnable).getPriority() != 5) {
                ((Thread)runnable).setPriority(5);
            }
            return runnable;
        }
    }

    private static class DelegatedExecutorService
    extends AbstractExecutorService {
        @ReachabilitySensitive
        private final ExecutorService e;

        DelegatedExecutorService(ExecutorService executorService) {
            this.e = executorService;
        }

        @Override
        public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
            return this.e.awaitTermination(l, timeUnit);
        }

        @Override
        public void execute(Runnable runnable) {
            this.e.execute(runnable);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) throws InterruptedException {
            return this.e.invokeAll(collection);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException {
            return this.e.invokeAll(collection, l, timeUnit);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
            return this.e.invokeAny(collection);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> collection, long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.e.invokeAny(collection, l, timeUnit);
        }

        @Override
        public boolean isShutdown() {
            return this.e.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return this.e.isTerminated();
        }

        @Override
        public void shutdown() {
            this.e.shutdown();
        }

        @Override
        public List<Runnable> shutdownNow() {
            return this.e.shutdownNow();
        }

        @Override
        public Future<?> submit(Runnable runnable) {
            return this.e.submit(runnable);
        }

        @Override
        public <T> Future<T> submit(Runnable runnable, T t) {
            return this.e.submit(runnable, t);
        }

        @Override
        public <T> Future<T> submit(Callable<T> callable) {
            return this.e.submit(callable);
        }
    }

    private static class DelegatedScheduledExecutorService
    extends DelegatedExecutorService
    implements ScheduledExecutorService {
        private final ScheduledExecutorService e;

        DelegatedScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
            super(scheduledExecutorService);
            this.e = scheduledExecutorService;
        }

        @Override
        public ScheduledFuture<?> schedule(Runnable runnable, long l, TimeUnit timeUnit) {
            return this.e.schedule(runnable, l, timeUnit);
        }

        @Override
        public <V> ScheduledFuture<V> schedule(Callable<V> callable, long l, TimeUnit timeUnit) {
            return this.e.schedule(callable, l, timeUnit);
        }

        @Override
        public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
            return this.e.scheduleAtFixedRate(runnable, l, l2, timeUnit);
        }

        @Override
        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
            return this.e.scheduleWithFixedDelay(runnable, l, l2, timeUnit);
        }
    }

    private static class FinalizableDelegatedExecutorService
    extends DelegatedExecutorService {
        FinalizableDelegatedExecutorService(ExecutorService executorService) {
            super(executorService);
        }

        protected void finalize() {
            super.shutdown();
        }
    }

    private static final class PrivilegedCallable<T>
    implements Callable<T> {
        final AccessControlContext acc;
        final Callable<T> task;

        PrivilegedCallable(Callable<T> callable) {
            this.task = callable;
            this.acc = AccessController.getContext();
        }

        @Override
        public T call() throws Exception {
            1 var1_1;
            try {
                var1_1 = new PrivilegedExceptionAction<T>(){

                    @Override
                    public T run() throws Exception {
                        return task.call();
                    }
                };
                var1_1 = AccessController.doPrivileged(var1_1, this.acc);
            }
            catch (PrivilegedActionException privilegedActionException) {
                throw privilegedActionException.getException();
            }
            return (T)var1_1;
        }

    }

    private static final class PrivilegedCallableUsingCurrentClassLoader<T>
    implements Callable<T> {
        final AccessControlContext acc;
        final ClassLoader ccl;
        final Callable<T> task;

        PrivilegedCallableUsingCurrentClassLoader(Callable<T> callable) {
            this.task = callable;
            this.acc = AccessController.getContext();
            this.ccl = Thread.currentThread().getContextClassLoader();
        }

        @Override
        public T call() throws Exception {
            1 var1_1;
            try {
                var1_1 = new PrivilegedExceptionAction<T>(){

                    @Override
                    public T run() throws Exception {
                        Thread thread = Thread.currentThread();
                        ClassLoader classLoader = thread.getContextClassLoader();
                        if (ccl == classLoader) {
                            return task.call();
                        }
                        thread.setContextClassLoader(ccl);
                        try {
                            Object t = task.call();
                            return t;
                        }
                        finally {
                            thread.setContextClassLoader(classLoader);
                        }
                    }
                };
                var1_1 = AccessController.doPrivileged(var1_1, this.acc);
            }
            catch (PrivilegedActionException privilegedActionException) {
                throw privilegedActionException.getException();
            }
            return (T)var1_1;
        }

    }

    private static class PrivilegedThreadFactory
    extends DefaultThreadFactory {
        final AccessControlContext acc = AccessController.getContext();
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();

        PrivilegedThreadFactory() {
        }

        @Override
        public Thread newThread(final Runnable runnable) {
            return super.newThread(new Runnable(){

                @Override
                public void run() {
                    AccessController.doPrivileged(new PrivilegedAction<Void>(){

                        @Override
                        public Void run() {
                            Thread.currentThread().setContextClassLoader(ccl);
                            runnable.run();
                            return null;
                        }
                    }, acc);
                }

            });
        }

    }

    private static final class RunnableAdapter<T>
    implements Callable<T> {
        private final T result;
        private final Runnable task;

        RunnableAdapter(Runnable runnable, T t) {
            this.task = runnable;
            this.result = t;
        }

        @Override
        public T call() {
            this.task.run();
            return this.result;
        }
    }

}

