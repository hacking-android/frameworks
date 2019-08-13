/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import android.system.Os;
import android.system.OsConstants;
import dalvik.annotation.compat.UnsupportedAppUsage;
import dalvik.system.VMRuntime;
import java.io.Serializable;
import java.lang.ref.FinalizerReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import libcore.util.EmptyArray;

public final class Daemons {
    private static final Daemon[] DAEMONS;
    @UnsupportedAppUsage
    private static long MAX_FINALIZE_NANOS = 0L;
    private static final int NANOS_PER_MILLI = 1000000;
    private static final CountDownLatch POST_ZYGOTE_START_LATCH;
    private static final CountDownLatch PRE_ZYGOTE_START_LATCH;
    private static boolean postZygoteFork;

    static {
        MAX_FINALIZE_NANOS = 10000000000L;
        DAEMONS = new Daemon[]{HeapTaskDaemon.INSTANCE, ReferenceQueueDaemon.INSTANCE, FinalizerDaemon.INSTANCE, FinalizerWatchdogDaemon.INSTANCE};
        POST_ZYGOTE_START_LATCH = new CountDownLatch(DAEMONS.length);
        PRE_ZYGOTE_START_LATCH = new CountDownLatch(DAEMONS.length);
        postZygoteFork = false;
    }

    public static void requestGC() {
        VMRuntime.getRuntime().requestConcurrentGC();
    }

    @UnsupportedAppUsage
    public static void requestHeapTrim() {
        VMRuntime.getRuntime().requestHeapTrim();
    }

    @UnsupportedAppUsage
    public static void start() {
        Daemon[] arrdaemon = DAEMONS;
        int n = arrdaemon.length;
        for (int i = 0; i < n; ++i) {
            arrdaemon[i].start();
        }
    }

    public static void startPostZygoteFork() {
        postZygoteFork = true;
        Daemon[] arrdaemon = DAEMONS;
        int n = arrdaemon.length;
        for (int i = 0; i < n; ++i) {
            arrdaemon[i].startPostZygoteFork();
        }
    }

    @UnsupportedAppUsage
    public static void stop() {
        Daemon[] arrdaemon = DAEMONS;
        int n = arrdaemon.length;
        for (int i = 0; i < n; ++i) {
            arrdaemon[i].stop();
        }
    }

    private static void waitForDaemonStart() throws Exception {
        if (postZygoteFork) {
            POST_ZYGOTE_START_LATCH.await();
        } else {
            PRE_ZYGOTE_START_LATCH.await();
        }
    }

    private static abstract class Daemon
    implements Runnable {
        private String name;
        private boolean postZygoteFork;
        @UnsupportedAppUsage
        private Thread thread;

        protected Daemon(String string) {
            this.name = string;
        }

        public StackTraceElement[] getStackTrace() {
            synchronized (this) {
                StackTraceElement[] arrstackTraceElement = this.thread != null ? this.thread.getStackTrace() : EmptyArray.STACK_TRACE_ELEMENT;
                return arrstackTraceElement;
            }
        }

        public void interrupt() {
            synchronized (this) {
                this.interrupt(this.thread);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void interrupt(Thread object) {
            synchronized (this) {
                Throwable throwable2;
                if (object != null) {
                    try {
                        ((Thread)object).interrupt();
                        return;
                    }
                    catch (Throwable throwable2) {}
                } else {
                    object = new IllegalStateException("not running");
                    throw object;
                }
                throw throwable2;
            }
        }

        @UnsupportedAppUsage
        protected boolean isRunning() {
            synchronized (this) {
                Thread thread = this.thread;
                boolean bl = thread != null;
                return bl;
            }
        }

        @Override
        public final void run() {
            if (this.postZygoteFork) {
                VMRuntime.getRuntime();
                VMRuntime.setSystemDaemonThreadPriority();
                POST_ZYGOTE_START_LATCH.countDown();
            } else {
                PRE_ZYGOTE_START_LATCH.countDown();
            }
            this.runInternal();
        }

        public abstract void runInternal();

        @UnsupportedAppUsage
        public void start() {
            synchronized (this) {
                this.startInternal();
                return;
            }
        }

        public void startInternal() {
            if (this.thread == null) {
                this.thread = new Thread(ThreadGroup.systemThreadGroup, this, this.name);
                this.thread.setDaemon(true);
                this.thread.setSystemDaemon(true);
                this.thread.start();
                return;
            }
            throw new IllegalStateException("already running");
        }

        public void startPostZygoteFork() {
            synchronized (this) {
                this.postZygoteFork = true;
                this.startInternal();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @UnsupportedAppUsage
        public void stop() {
            // MONITORENTER : this
            Thread thread = this.thread;
            this.thread = null;
            // MONITOREXIT : this
            if (thread == null) throw new IllegalStateException("not running");
            this.interrupt(thread);
            do {
                try {
                    thread.join();
                    return;
                }
                catch (OutOfMemoryError outOfMemoryError) {
                    continue;
                }
                catch (InterruptedException interruptedException) {
                    continue;
                }
                break;
            } while (true);
        }
    }

    private static class FinalizerDaemon
    extends Daemon {
        @UnsupportedAppUsage
        private static final FinalizerDaemon INSTANCE = new FinalizerDaemon();
        @UnsupportedAppUsage
        private Object finalizingObject = null;
        private final AtomicInteger progressCounter = new AtomicInteger(0);
        private final ReferenceQueue<Object> queue = FinalizerReference.queue;

        FinalizerDaemon() {
            super("FinalizerDaemon");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @FindBugsSuppressWarnings(value={"FI_EXPLICIT_INVOCATION"})
        private void doFinalize(FinalizerReference<?> finalizerReference) {
            FinalizerReference.remove(finalizerReference);
            Object obj = finalizerReference.get();
            finalizerReference.clear();
            try {
                obj.finalize();
                return;
            }
            catch (Throwable throwable) {
                try {
                    System.logE((String)"Uncaught exception thrown by finalizer", (Throwable)throwable);
                    return;
                }
                finally {
                    this.finalizingObject = null;
                }
            }
        }

        @Override
        public void runInternal() {
            int n = this.progressCounter.get();
            while (this.isRunning()) {
                Object object;
                int n2;
                int n3;
                block18 : {
                    AtomicInteger atomicInteger;
                    block17 : {
                        n2 = n;
                        n3 = n;
                        object = (FinalizerReference)this.queue.poll();
                        if (object == null) break block17;
                        n2 = n;
                        n3 = n;
                        this.finalizingObject = ((FinalizerReference)object).get();
                        n2 = n;
                        n3 = n++;
                        atomicInteger = this.progressCounter;
                        n2 = n;
                        n3 = n;
                        atomicInteger.lazySet(n);
                        break block18;
                    }
                    n2 = n;
                    n3 = n;
                    this.finalizingObject = null;
                    n2 = n;
                    n3 = n++;
                    object = this.progressCounter;
                    n2 = n;
                    n3 = n;
                    ((AtomicInteger)object).lazySet(n);
                    n2 = n;
                    n3 = n;
                    FinalizerWatchdogDaemon.INSTANCE.goToSleep();
                    n2 = n;
                    n3 = n;
                    object = (FinalizerReference)this.queue.remove();
                    n2 = n;
                    n3 = n;
                    this.finalizingObject = ((FinalizerReference)object).get();
                    n2 = n;
                    n3 = n++;
                    atomicInteger = this.progressCounter;
                    n2 = n;
                    n3 = n;
                    atomicInteger.set(n);
                    n2 = n;
                    n3 = n;
                    FinalizerWatchdogDaemon.INSTANCE.wakeUp();
                }
                n2 = n;
                n3 = n;
                try {
                    this.doFinalize((FinalizerReference<?>)object);
                    n2 = n;
                }
                catch (OutOfMemoryError outOfMemoryError) {
                }
                catch (InterruptedException interruptedException) {
                    n2 = n3;
                }
                n = n2;
            }
        }
    }

    private static class FinalizerWatchdogDaemon
    extends Daemon {
        @UnsupportedAppUsage
        private static final FinalizerWatchdogDaemon INSTANCE = new FinalizerWatchdogDaemon();
        private long finalizerTimeoutMs = 0L;
        private boolean needToWork = true;

        FinalizerWatchdogDaemon() {
            super("FinalizerWatchdogDaemon");
        }

        private static void finalizerTimedOut(Object object) {
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append(object.getClass().getName());
            ((StringBuilder)serializable).append(".finalize() timed out after ");
            ((StringBuilder)serializable).append(VMRuntime.getRuntime().getFinalizerTimeoutMs() / 1000L);
            ((StringBuilder)serializable).append(" seconds");
            object = ((StringBuilder)serializable).toString();
            serializable = new TimeoutException((String)object);
            ((Throwable)serializable).setStackTrace(FinalizerDaemon.INSTANCE.getStackTrace());
            try {
                Os.kill(Os.getpid(), OsConstants.SIGQUIT);
                Thread.sleep(5000L);
            }
            catch (OutOfMemoryError outOfMemoryError) {
            }
            catch (Exception exception) {
                System.logE((String)"failed to send SIGQUIT", (Throwable)exception);
            }
            if (Thread.getUncaughtExceptionPreHandler() == null && Thread.getDefaultUncaughtExceptionHandler() == null) {
                System.logE((String)object, (Throwable)serializable);
                System.exit(2);
            }
            Thread.currentThread().dispatchUncaughtException((Throwable)serializable);
        }

        private boolean getNeedToWork() {
            synchronized (this) {
                boolean bl = this.needToWork;
                return bl;
            }
        }

        private void goToSleep() {
            synchronized (this) {
                this.needToWork = false;
                return;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean sleepForMillis(long l) {
            long l2 = System.currentTimeMillis();
            long l3;
            while ((l3 = l - (System.currentTimeMillis() - l2)) > 0L) {
                try {
                    Thread.sleep(l3);
                    continue;
                }
                catch (OutOfMemoryError outOfMemoryError) {
                    if (this.isRunning()) continue;
                    return false;
                }
                catch (InterruptedException interruptedException) {
                    if (!this.isRunning()) return false;
                    continue;
                }
                break;
            }
            return true;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private boolean sleepUntilNeeded() {
            synchronized (this) {
                boolean bl;
                while (!(bl = this.needToWork)) {
                    try {
                        this.wait();
                    }
                    catch (OutOfMemoryError outOfMemoryError) {
                        return false;
                    }
                    catch (InterruptedException interruptedException) {
                        return false;
                    }
                }
                return true;
            }
        }

        private Object waitForFinalization() {
            if (this.finalizerTimeoutMs == 0L) {
                this.finalizerTimeoutMs = VMRuntime.getRuntime().getFinalizerTimeoutMs();
                MAX_FINALIZE_NANOS = this.finalizerTimeoutMs * 1000000L;
            }
            long l = FinalizerDaemon.INSTANCE.progressCounter.get();
            if (!this.sleepForMillis(this.finalizerTimeoutMs)) {
                return null;
            }
            if (this.getNeedToWork() && (long)FinalizerDaemon.INSTANCE.progressCounter.get() == l) {
                Object object = FinalizerDaemon.INSTANCE.finalizingObject;
                this.sleepForMillis(500L);
                if (this.getNeedToWork() && (long)FinalizerDaemon.INSTANCE.progressCounter.get() == l) {
                    return object;
                }
            }
            return null;
        }

        private void wakeUp() {
            synchronized (this) {
                this.needToWork = true;
                this.notify();
                return;
            }
        }

        @Override
        public void runInternal() {
            while (this.isRunning()) {
                Object object;
                if (!this.sleepUntilNeeded() || (object = this.waitForFinalization()) == null || VMRuntime.getRuntime().isDebuggerActive()) continue;
                FinalizerWatchdogDaemon.finalizerTimedOut(object);
                break;
            }
        }
    }

    private static class HeapTaskDaemon
    extends Daemon {
        private static final HeapTaskDaemon INSTANCE = new HeapTaskDaemon();

        HeapTaskDaemon() {
            super("HeapTaskDaemon");
        }

        @Override
        public void interrupt(Thread thread) {
            synchronized (this) {
                VMRuntime.getRuntime().stopHeapTaskProcessor();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void runInternal() {
            synchronized (this) {
                if (this.isRunning()) {
                    VMRuntime.getRuntime().startHeapTaskProcessor();
                }
            }
            VMRuntime.getRuntime().runHeapTasks();
        }
    }

    private static class ReferenceQueueDaemon
    extends Daemon {
        @UnsupportedAppUsage
        private static final ReferenceQueueDaemon INSTANCE = new ReferenceQueueDaemon();

        ReferenceQueueDaemon() {
            super("ReferenceQueueDaemon");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void runInternal() {
            while (this.isRunning()) {
                while (ReferenceQueue.unenqueued == null) {
                    ReferenceQueue.class.wait();
                }
                Reference reference = ReferenceQueue.unenqueued;
                ReferenceQueue.unenqueued = null;
                // MONITOREXIT : java.lang.ref.ReferenceQueue.class
                ReferenceQueue.enqueuePending((Reference)reference);
            }
        }
    }

}

