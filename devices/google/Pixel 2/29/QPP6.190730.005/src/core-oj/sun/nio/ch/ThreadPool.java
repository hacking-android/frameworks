/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  sun.nio.ch.-$
 *  sun.nio.ch.-$$Lambda
 *  sun.nio.ch.-$$Lambda$ThreadPool
 *  sun.nio.ch.-$$Lambda$ThreadPool$N88rfRTSpCtnK5fgJO-WA6OwVQM
 */
package sun.nio.ch;

import java.security.AccessController;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import sun.nio.ch.-$;
import sun.nio.ch._$$Lambda$ThreadPool$N88rfRTSpCtnK5fgJO_WA6OwVQM;
import sun.security.action.GetPropertyAction;

public class ThreadPool {
    private static final String DEFAULT_THREAD_POOL_INITIAL_SIZE = "java.nio.channels.DefaultThreadPool.initialSize";
    private static final String DEFAULT_THREAD_POOL_THREAD_FACTORY = "java.nio.channels.DefaultThreadPool.threadFactory";
    private final ExecutorService executor;
    private final boolean isFixed;
    private final int poolSize;

    private ThreadPool(ExecutorService executorService, boolean bl, int n) {
        this.executor = executorService;
        this.isFixed = bl;
        this.poolSize = n;
    }

    static ThreadPool create(int n, ThreadFactory threadFactory) {
        if (n > 0) {
            return new ThreadPool(Executors.newFixedThreadPool(n, threadFactory), true, n);
        }
        throw new IllegalArgumentException("'nThreads' must be > 0");
    }

    static ThreadPool createDefault() {
        int n;
        ThreadFactory threadFactory;
        int n2 = n = ThreadPool.getDefaultThreadPoolInitialSize();
        if (n < 0) {
            n2 = Runtime.getRuntime().availableProcessors();
        }
        ThreadFactory threadFactory2 = threadFactory = ThreadPool.getDefaultThreadPoolThreadFactory();
        if (threadFactory == null) {
            threadFactory2 = ThreadPool.defaultThreadFactory();
        }
        return new ThreadPool(Executors.newCachedThreadPool(threadFactory2), false, n2);
    }

    static ThreadFactory defaultThreadFactory() {
        return _$$Lambda$ThreadPool$N88rfRTSpCtnK5fgJO_WA6OwVQM.INSTANCE;
    }

    static ThreadPool getDefault() {
        return DefaultThreadPoolHolder.defaultThreadPool;
    }

    private static int getDefaultThreadPoolInitialSize() {
        String string = AccessController.doPrivileged(new GetPropertyAction(DEFAULT_THREAD_POOL_INITIAL_SIZE));
        if (string != null) {
            try {
                int n = Integer.parseInt(string);
                return n;
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Value of property 'java.nio.channels.DefaultThreadPool.initialSize' is invalid: ");
                stringBuilder.append(numberFormatException);
                throw new Error(stringBuilder.toString());
            }
        }
        return -1;
    }

    private static ThreadFactory getDefaultThreadPoolThreadFactory() {
        Object object = AccessController.doPrivileged(new GetPropertyAction(DEFAULT_THREAD_POOL_THREAD_FACTORY));
        if (object != null) {
            try {
                object = (ThreadFactory)Class.forName((String)object, true, ClassLoader.getSystemClassLoader()).newInstance();
                return object;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new Error(illegalAccessException);
            }
            catch (InstantiationException instantiationException) {
                throw new Error(instantiationException);
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new Error(classNotFoundException);
            }
        }
        return null;
    }

    static /* synthetic */ Thread lambda$defaultThreadFactory$0(Runnable runnable) {
        runnable = new Thread(runnable);
        ((Thread)runnable).setDaemon(true);
        return runnable;
    }

    public static ThreadPool wrap(ExecutorService executorService, int n) {
        if (executorService != null) {
            int n2;
            if (executorService instanceof ThreadPoolExecutor) {
                n2 = n;
                if (((ThreadPoolExecutor)executorService).getMaximumPoolSize() == Integer.MAX_VALUE) {
                    n2 = n < 0 ? Runtime.getRuntime().availableProcessors() : 0;
                }
            } else {
                n2 = n;
                if (n < 0) {
                    n2 = 0;
                }
            }
            return new ThreadPool(executorService, false, n2);
        }
        throw new NullPointerException("'executor' is null");
    }

    ExecutorService executor() {
        return this.executor;
    }

    boolean isFixedThreadPool() {
        return this.isFixed;
    }

    int poolSize() {
        return this.poolSize;
    }

    private static class DefaultThreadPoolHolder {
        static final ThreadPool defaultThreadPool = ThreadPool.createDefault();

        private DefaultThreadPoolHolder() {
        }
    }

}

