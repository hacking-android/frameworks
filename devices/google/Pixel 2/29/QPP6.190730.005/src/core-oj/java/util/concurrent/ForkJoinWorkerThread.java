/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.lang.reflect.Field;
import java.security.AccessControlContext;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.ProtectionDomain;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import sun.misc.Unsafe;

public class ForkJoinWorkerThread
extends Thread {
    private static final long INHERITABLETHREADLOCALS;
    private static final long INHERITEDACCESSCONTROLCONTEXT;
    private static final long THREADLOCALS;
    private static final Unsafe U;
    final ForkJoinPool pool;
    final ForkJoinPool.WorkQueue workQueue;

    static {
        U = Unsafe.getUnsafe();
        try {
            THREADLOCALS = U.objectFieldOffset(Thread.class.getDeclaredField("threadLocals"));
            INHERITABLETHREADLOCALS = U.objectFieldOffset(Thread.class.getDeclaredField("inheritableThreadLocals"));
            INHERITEDACCESSCONTROLCONTEXT = U.objectFieldOffset(Thread.class.getDeclaredField("inheritedAccessControlContext"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    protected ForkJoinWorkerThread(ForkJoinPool forkJoinPool) {
        super("aForkJoinWorkerThread");
        this.pool = forkJoinPool;
        this.workQueue = forkJoinPool.registerWorker(this);
    }

    ForkJoinWorkerThread(ForkJoinPool forkJoinPool, ThreadGroup threadGroup, AccessControlContext accessControlContext) {
        super(threadGroup, null, "aForkJoinWorkerThread");
        U.putOrderedObject(this, INHERITEDACCESSCONTROLCONTEXT, accessControlContext);
        this.eraseThreadLocals();
        this.pool = forkJoinPool;
        this.workQueue = forkJoinPool.registerWorker(this);
    }

    void afterTopLevelExec() {
    }

    final void eraseThreadLocals() {
        U.putObject(this, THREADLOCALS, null);
        U.putObject(this, INHERITABLETHREADLOCALS, null);
    }

    public ForkJoinPool getPool() {
        return this.pool;
    }

    public int getPoolIndex() {
        return this.workQueue.getPoolIndex();
    }

    protected void onStart() {
    }

    protected void onTermination(Throwable throwable) {
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        Throwable throwable;
        block7 : {
            if (this.workQueue.array != null) return;
            throwable = null;
            this.onStart();
            this.pool.runWorker(this.workQueue);
            try {
                this.onTermination(null);
            }
            catch (Throwable throwable2) {
                if (!false) {
                    throwable = throwable2;
                }
                break block7;
            }
            catch (Throwable throwable3) {
                try {
                    this.onTermination(throwable3);
                }
                catch (Throwable throwable4) {}
            }
        }
        this.pool.deregisterWorker(this, throwable);
    }

    static final class InnocuousForkJoinWorkerThread
    extends ForkJoinWorkerThread {
        private static final AccessControlContext INNOCUOUS_ACC;
        private static final ThreadGroup innocuousThreadGroup;

        static {
            innocuousThreadGroup = InnocuousForkJoinWorkerThread.createThreadGroup();
            INNOCUOUS_ACC = new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, null)});
        }

        InnocuousForkJoinWorkerThread(ForkJoinPool forkJoinPool) {
            super(forkJoinPool, innocuousThreadGroup, INNOCUOUS_ACC);
        }

        private static ThreadGroup createThreadGroup() {
            long l;
            ThreadGroup threadGroup;
            Unsafe unsafe;
            try {
                unsafe = Unsafe.getUnsafe();
                long l2 = unsafe.objectFieldOffset(Thread.class.getDeclaredField("group"));
                l = unsafe.objectFieldOffset(ThreadGroup.class.getDeclaredField("parent"));
                threadGroup = (ThreadGroup)unsafe.getObject(Thread.currentThread(), l2);
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new Error(reflectiveOperationException);
            }
            while (threadGroup != null) {
                ThreadGroup threadGroup2;
                block5 : {
                    threadGroup2 = (ThreadGroup)unsafe.getObject(threadGroup, l);
                    if (threadGroup2 != null) break block5;
                    threadGroup = new ThreadGroup(threadGroup, "InnocuousForkJoinWorkerThreadGroup");
                    return threadGroup;
                }
                threadGroup = threadGroup2;
            }
            throw new Error("Cannot create ThreadGroup");
        }

        @Override
        void afterTopLevelExec() {
            this.eraseThreadLocals();
        }

        @Override
        public ClassLoader getContextClassLoader() {
            return ClassLoader.getSystemClassLoader();
        }

        @Override
        public void setContextClassLoader(ClassLoader classLoader) {
            throw new SecurityException("setContextClassLoader");
        }

        @Override
        public void setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        }
    }

}

