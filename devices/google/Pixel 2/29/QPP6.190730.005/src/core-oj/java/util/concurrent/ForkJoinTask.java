/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import sun.misc.Unsafe;

public abstract class ForkJoinTask<V>
implements Future<V>,
Serializable {
    static final int CANCELLED = -1073741824;
    static final int DONE_MASK = -268435456;
    static final int EXCEPTIONAL = Integer.MIN_VALUE;
    private static final int EXCEPTION_MAP_CAPACITY = 32;
    static final int NORMAL = -268435456;
    static final int SIGNAL = 65536;
    static final int SMASK = 65535;
    private static final long STATUS;
    private static final Unsafe U;
    private static final ExceptionNode[] exceptionTable;
    private static final ReentrantLock exceptionTableLock;
    private static final ReferenceQueue<Object> exceptionTableRefQueue;
    private static final long serialVersionUID = -7721805057305804111L;
    volatile int status;

    static {
        U = Unsafe.getUnsafe();
        exceptionTableLock = new ReentrantLock();
        exceptionTableRefQueue = new ReferenceQueue();
        exceptionTable = new ExceptionNode[32];
        try {
            STATUS = U.objectFieldOffset(ForkJoinTask.class.getDeclaredField("status"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public static ForkJoinTask<?> adapt(Runnable runnable) {
        return new AdaptedRunnableAction(runnable);
    }

    public static <T> ForkJoinTask<T> adapt(Runnable runnable, T t) {
        return new AdaptedRunnable<T>(runnable, t);
    }

    public static <T> ForkJoinTask<T> adapt(Callable<? extends T> callable) {
        return new AdaptedCallable<T>(callable);
    }

    static final void cancelIgnoringExceptions(ForkJoinTask<?> forkJoinTask) {
        if (forkJoinTask != null && forkJoinTask.status >= 0) {
            try {
                forkJoinTask.cancel(false);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    private void clearExceptionalCompletion() {
        ExceptionNode[] arrexceptionNode;
        int n = System.identityHashCode(this);
        ReentrantLock reentrantLock = exceptionTableLock;
        reentrantLock.lock();
        try {
            arrexceptionNode = exceptionTable;
            n = arrexceptionNode.length - 1 & n;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            reentrantLock.unlock();
        }
        ExceptionNode exceptionNode = arrexceptionNode[n];
        ExceptionNode exceptionNode2 = null;
        while (exceptionNode != null) {
            ExceptionNode exceptionNode3;
            block7 : {
                block8 : {
                    exceptionNode3 = exceptionNode.next;
                    if (exceptionNode.get() != this) break block7;
                    if (exceptionNode2 != null) break block8;
                    arrexceptionNode[n] = exceptionNode3;
                    break;
                }
                exceptionNode2.next = exceptionNode3;
                break;
            }
            exceptionNode2 = exceptionNode;
            exceptionNode = exceptionNode3;
        }
        {
            ForkJoinTask.expungeStaleExceptions();
            this.status = 0;
            return;
        }
    }

    private int doInvoke() {
        int n = this.doExec();
        if (n >= 0) {
            Thread thread = Thread.currentThread();
            if (thread instanceof ForkJoinWorkerThread) {
                thread = (ForkJoinWorkerThread)thread;
                n = ((ForkJoinWorkerThread)thread).pool.awaitJoin(((ForkJoinWorkerThread)thread).workQueue, this, 0L);
            } else {
                n = this.externalAwaitDone();
            }
        }
        return n;
    }

    private int doJoin() {
        int n = this.status;
        if (n >= 0) {
            Object object = Thread.currentThread();
            if (object instanceof ForkJoinWorkerThread) {
                ForkJoinWorkerThread forkJoinWorkerThread = (ForkJoinWorkerThread)object;
                object = forkJoinWorkerThread.workQueue;
                if (!((ForkJoinPool.WorkQueue)object).tryUnpush(this) || (n = this.doExec()) >= 0) {
                    n = forkJoinWorkerThread.pool.awaitJoin((ForkJoinPool.WorkQueue)object, this, 0L);
                }
            } else {
                n = this.externalAwaitDone();
            }
        }
        return n;
    }

    private static void expungeStaleExceptions() {
        Reference<Object> reference;
        block0 : while ((reference = exceptionTableRefQueue.poll()) != null) {
            if (!(reference instanceof ExceptionNode)) continue;
            int n = ((ExceptionNode)reference).hashCode;
            ExceptionNode[] arrexceptionNode = exceptionTable;
            n = arrexceptionNode.length - 1 & n;
            ExceptionNode exceptionNode = arrexceptionNode[n];
            ExceptionNode exceptionNode2 = null;
            while (exceptionNode != null) {
                ExceptionNode exceptionNode3 = exceptionNode.next;
                if (exceptionNode == reference) {
                    if (exceptionNode2 == null) {
                        arrexceptionNode[n] = exceptionNode3;
                        continue block0;
                    }
                    exceptionNode2.next = exceptionNode3;
                    continue block0;
                }
                exceptionNode2 = exceptionNode;
                exceptionNode = exceptionNode3;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int externalAwaitDone() {
        int n;
        int n2;
        int n3;
        boolean bl = this instanceof CountedCompleter;
        int n4 = 0;
        if (bl) {
            n4 = ForkJoinPool.common.externalHelpComplete((CountedCompleter)this, 0);
        } else if (ForkJoinPool.common.tryExternalUnpush(this)) {
            n4 = this.doExec();
        }
        n4 = n2 = n4;
        if (n2 < 0) return n4;
        n4 = n2 = (n = this.status);
        if (n < 0) return n4;
        n4 = 0;
        int n5 = n2;
        do {
            n = n4;
            if (U.compareAndSwapInt(this, STATUS, n5, n5 | 65536)) {
                synchronized (this) {
                    n2 = this.status;
                    if (n2 >= 0) {
                        try {
                            this.wait(0L);
                        }
                        catch (InterruptedException interruptedException) {
                            n4 = 1;
                        }
                    } else {
                        this.notifyAll();
                    }
                }
                n = n4;
            }
            n5 = n2 = (n3 = this.status);
            n4 = n;
        } while (n3 >= 0);
        n4 = n2;
        if (n == 0) return n4;
        Thread.currentThread().interrupt();
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int externalInterruptibleAwaitDone() throws InterruptedException {
        int n;
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        int n2 = n = this.status;
        if (n >= 0) {
            boolean bl = this instanceof CountedCompleter;
            n = 0;
            if (bl) {
                n = ForkJoinPool.common.externalHelpComplete((CountedCompleter)this, 0);
            } else if (ForkJoinPool.common.tryExternalUnpush(this)) {
                n = this.doExec();
            }
            n2 = n;
            if (n >= 0) {
                do {
                    int n3;
                    n2 = n = (n3 = this.status);
                    if (n3 < 0) break;
                    if (!U.compareAndSwapInt(this, STATUS, n, n | 65536)) continue;
                    synchronized (this) {
                        if (this.status >= 0) {
                            this.wait(0L);
                        } else {
                            this.notifyAll();
                        }
                    }
                } while (true);
            }
        }
        return n2;
    }

    public static ForkJoinPool getPool() {
        Object object = Thread.currentThread();
        object = object instanceof ForkJoinWorkerThread ? ((ForkJoinWorkerThread)object).pool : null;
        return object;
    }

    public static int getQueuedTaskCount() {
        Object object = Thread.currentThread();
        object = object instanceof ForkJoinWorkerThread ? ((ForkJoinWorkerThread)object).workQueue : ForkJoinPool.commonSubmitterQueue();
        int n = object == null ? 0 : ((ForkJoinPool.WorkQueue)object).queueSize();
        return n;
    }

    public static int getSurplusQueuedTaskCount() {
        return ForkJoinPool.getSurplusQueuedTaskCount();
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Throwable getThrowableException() {
        Throwable throwable;
        int n = System.identityHashCode(this);
        Object object = exceptionTableLock;
        ((ReentrantLock)object).lock();
        ForkJoinTask.expungeStaleExceptions();
        Object object2 = exceptionTable;
        object2 = object2[((ExceptionNode[])object2).length - 1 & n];
        while (object2 != null) {
            if (((Reference)object2).get() == this) break;
            object2 = ((ExceptionNode)object2).next;
        }
        ((ReentrantLock)object).unlock();
        if (object2 == null || (throwable = ((ExceptionNode)object2).ex) == null) return null;
        if (((ExceptionNode)object2).thrower == Thread.currentThread().getId()) return throwable;
        Constructor<?>[] arrconstructor = throwable.getClass().getConstructors();
        int n2 = arrconstructor.length;
        object = null;
        for (n = 0; n < n2; ++n) {
            block13 : {
                Constructor<?> constructor = arrconstructor[n];
                Class<?>[] arrclass = constructor.getParameterTypes();
                if (arrclass.length == 0) {
                    object2 = constructor;
                    break block13;
                }
                object2 = object;
                if (arrclass.length != 1) break block13;
                object2 = object;
                if (arrclass[0] == Throwable.class) {
                    return (Throwable)constructor.newInstance(throwable);
                }
            }
            object = object2;
        }
        if (object == null) return throwable;
        try {
            object2 = (Throwable)((Constructor)object).newInstance(new Object[0]);
            ((Throwable)object2).initCause(throwable);
            return object2;
        }
        catch (Exception exception) {
            // empty catch block
        }
        return throwable;
        catch (Throwable throwable2) {
            ((ReentrantLock)object).unlock();
            throw throwable2;
        }
    }

    static final void helpExpungeStaleExceptions() {
        ReentrantLock reentrantLock = exceptionTableLock;
        if (reentrantLock.tryLock()) {
            try {
                ForkJoinTask.expungeStaleExceptions();
            }
            finally {
                reentrantLock.unlock();
            }
        }
    }

    public static void helpQuiesce() {
        Thread thread = Thread.currentThread();
        if (thread instanceof ForkJoinWorkerThread) {
            thread = (ForkJoinWorkerThread)thread;
            ((ForkJoinWorkerThread)thread).pool.helpQuiescePool(((ForkJoinWorkerThread)thread).workQueue);
        } else {
            ForkJoinPool.quiesceCommonPool();
        }
    }

    public static boolean inForkJoinPool() {
        return Thread.currentThread() instanceof ForkJoinWorkerThread;
    }

    public static <T extends ForkJoinTask<?>> Collection<T> invokeAll(Collection<T> collection) {
        if (collection instanceof RandomAccess && collection instanceof List) {
            int n;
            ForkJoinTask forkJoinTask;
            int n2;
            Throwable throwable;
            List list = (List)collection;
            NullPointerException nullPointerException = null;
            for (n = n2 = list.size() - 1; n >= 0; --n) {
                forkJoinTask = (ForkJoinTask)list.get(n);
                if (forkJoinTask == null) {
                    throwable = nullPointerException;
                    if (nullPointerException == null) {
                        throwable = new NullPointerException();
                    }
                } else if (n != 0) {
                    forkJoinTask.fork();
                    throwable = nullPointerException;
                } else {
                    throwable = nullPointerException;
                    if (forkJoinTask.doInvoke() < -268435456) {
                        throwable = nullPointerException;
                        if (nullPointerException == null) {
                            throwable = forkJoinTask.getException();
                        }
                    }
                }
                nullPointerException = throwable;
            }
            for (n = 1; n <= n2; ++n) {
                forkJoinTask = (ForkJoinTask)list.get(n);
                throwable = nullPointerException;
                if (forkJoinTask != null) {
                    if (nullPointerException != null) {
                        forkJoinTask.cancel(false);
                        throwable = nullPointerException;
                    } else {
                        throwable = nullPointerException;
                        if (forkJoinTask.doJoin() < -268435456) {
                            throwable = forkJoinTask.getException();
                        }
                    }
                }
                nullPointerException = throwable;
            }
            if (nullPointerException != null) {
                ForkJoinTask.rethrow(nullPointerException);
            }
            return collection;
        }
        ForkJoinTask.invokeAll(collection.toArray(new ForkJoinTask[collection.size()]));
        return collection;
    }

    public static void invokeAll(ForkJoinTask<?> forkJoinTask, ForkJoinTask<?> forkJoinTask2) {
        forkJoinTask2.fork();
        int n = ForkJoinTask.super.doInvoke() & -268435456;
        if (n != -268435456) {
            ForkJoinTask.super.reportException(n);
        }
        if ((n = ForkJoinTask.super.doJoin() & -268435456) != -268435456) {
            ForkJoinTask.super.reportException(n);
        }
    }

    public static void invokeAll(ForkJoinTask<?> ... arrforkJoinTask) {
        int n;
        Throwable throwable;
        ForkJoinTask<?> forkJoinTask;
        int n2;
        NullPointerException nullPointerException = null;
        for (n = n2 = arrforkJoinTask.length - 1; n >= 0; --n) {
            forkJoinTask = arrforkJoinTask[n];
            if (forkJoinTask == null) {
                throwable = nullPointerException;
                if (nullPointerException == null) {
                    throwable = new NullPointerException();
                }
            } else if (n != 0) {
                forkJoinTask.fork();
                throwable = nullPointerException;
            } else {
                throwable = nullPointerException;
                if (ForkJoinTask.super.doInvoke() < -268435456) {
                    throwable = nullPointerException;
                    if (nullPointerException == null) {
                        throwable = forkJoinTask.getException();
                    }
                }
            }
            nullPointerException = throwable;
        }
        for (n = 1; n <= n2; ++n) {
            forkJoinTask = arrforkJoinTask[n];
            throwable = nullPointerException;
            if (forkJoinTask != null) {
                if (nullPointerException != null) {
                    forkJoinTask.cancel(false);
                    throwable = nullPointerException;
                } else {
                    throwable = nullPointerException;
                    if (ForkJoinTask.super.doJoin() < -268435456) {
                        throwable = forkJoinTask.getException();
                    }
                }
            }
            nullPointerException = throwable;
        }
        if (nullPointerException != null) {
            ForkJoinTask.rethrow(nullPointerException);
        }
    }

    protected static ForkJoinTask<?> peekNextLocalTask() {
        Object object = Thread.currentThread();
        object = object instanceof ForkJoinWorkerThread ? ((ForkJoinWorkerThread)object).workQueue : ForkJoinPool.commonSubmitterQueue();
        object = object == null ? null : ((ForkJoinPool.WorkQueue)object).peek();
        return object;
    }

    protected static ForkJoinTask<?> pollNextLocalTask() {
        Object object = Thread.currentThread();
        object = object instanceof ForkJoinWorkerThread ? ((ForkJoinWorkerThread)object).workQueue.nextLocalTask() : null;
        return object;
    }

    protected static ForkJoinTask<?> pollSubmission() {
        Object object = Thread.currentThread();
        object = object instanceof ForkJoinWorkerThread ? ((ForkJoinWorkerThread)object).pool.pollSubmission() : null;
        return object;
    }

    protected static ForkJoinTask<?> pollTask() {
        Object object = Thread.currentThread();
        if (object instanceof ForkJoinWorkerThread) {
            object = (ForkJoinWorkerThread)object;
            object = ((ForkJoinWorkerThread)object).pool.nextTaskFor(((ForkJoinWorkerThread)object).workQueue);
        } else {
            object = null;
        }
        return object;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        object = ((ObjectInputStream)object).readObject();
        if (object != null) {
            this.setExceptionalCompletion((Throwable)object);
        }
    }

    private void reportException(int n) {
        if (n != -1073741824) {
            if (n == Integer.MIN_VALUE) {
                ForkJoinTask.rethrow(this.getThrowableException());
            }
            return;
        }
        throw new CancellationException();
    }

    static void rethrow(Throwable throwable) {
        ForkJoinTask.uncheckedThrow(throwable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int setCompletion(int n) {
        int n2;
        do {
            if ((n2 = this.status) >= 0) continue;
            return n2;
        } while (!U.compareAndSwapInt(this, STATUS, n2, n2 | n));
        if (n2 >>> 16 != 0) {
            synchronized (this) {
                this.notifyAll();
            }
        }
        return n;
    }

    private int setExceptionalCompletion(Throwable throwable) {
        int n = this.recordExceptionalCompletion(throwable);
        if ((-268435456 & n) == Integer.MIN_VALUE) {
            this.internalPropagateException(throwable);
        }
        return n;
    }

    static <T extends Throwable> void uncheckedThrow(Throwable throwable) throws Throwable {
        if (throwable != null) {
            throw throwable;
        }
        throw new Error("Unknown Exception");
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.getException());
    }

    @Override
    public boolean cancel(boolean bl) {
        bl = (this.setCompletion(-1073741824) & -268435456) == -1073741824;
        return bl;
    }

    public final boolean compareAndSetForkJoinTaskTag(short s, short s2) {
        int n;
        do {
            if ((short)(n = this.status) == s) continue;
            return false;
        } while (!U.compareAndSwapInt(this, STATUS, n, -65536 & n | 65535 & s2));
        return true;
    }

    public void complete(V v) {
        try {
            this.setRawResult(v);
            this.setCompletion(-268435456);
        }
        catch (Throwable throwable) {
            this.setExceptionalCompletion(throwable);
            return;
        }
    }

    public void completeExceptionally(Throwable throwable) {
        if (!(throwable instanceof RuntimeException) && !(throwable instanceof Error)) {
            throwable = new RuntimeException(throwable);
        }
        this.setExceptionalCompletion(throwable);
    }

    final int doExec() {
        int n;
        int n2;
        int n3 = n2 = (n = this.status);
        if (n >= 0) {
            try {
                boolean bl = this.exec();
                n3 = n2;
                if (bl) {
                    n3 = this.setCompletion(-268435456);
                }
            }
            catch (Throwable throwable) {
                return this.setExceptionalCompletion(throwable);
            }
        }
        return n3;
    }

    protected abstract boolean exec();

    public final ForkJoinTask<V> fork() {
        Thread thread = Thread.currentThread();
        if (thread instanceof ForkJoinWorkerThread) {
            ((ForkJoinWorkerThread)thread).workQueue.push(this);
        } else {
            ForkJoinPool.common.externalPush(this);
        }
        return this;
    }

    @Override
    public final V get() throws InterruptedException, ExecutionException {
        int n = Thread.currentThread() instanceof ForkJoinWorkerThread ? this.doJoin() : this.externalInterruptibleAwaitDone();
        if ((n = -268435456 & n) != -1073741824) {
            if (n != Integer.MIN_VALUE) {
                return this.getRawResult();
            }
            throw new ExecutionException(this.getThrowableException());
        }
        throw new CancellationException();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final V get(long var1_1, TimeUnit var3_2) throws InterruptedException, ExecutionException, TimeoutException {
        block10 : {
            block12 : {
                block11 : {
                    var1_1 = var3_2.toNanos(var1_1);
                    if (Thread.interrupted() != false) throw new InterruptedException();
                    var6_8 = var5_7 = (var4_6 = this.status);
                    if (var4_6 < 0) break block10;
                    var6_8 = var5_7;
                    if (var1_1 <= 0L) break block10;
                    var1_1 = System.nanoTime() + var1_1;
                    if (var1_1 == 0L) {
                        var1_1 = 1L;
                    }
                    if (!((var3_3 = Thread.currentThread()) instanceof ForkJoinWorkerThread)) break block11;
                    var3_4 = (ForkJoinWorkerThread)var3_3;
                    var6_8 = var3_4.pool.awaitJoin(var3_4.workQueue, this, var1_1);
                    break block10;
                }
                var7_9 = this instanceof CountedCompleter;
                var6_8 = 0;
                if (var7_9) {
                    var6_8 = ForkJoinPool.common.externalHelpComplete((CountedCompleter)this, 0);
                } else if (ForkJoinPool.common.tryExternalUnpush(this)) {
                    var6_8 = this.doExec();
                }
                var5_7 = var6_8;
                if (var6_8 >= 0) break block12;
                var6_8 = var5_7;
                break block10;
            }
            while ((var6_8 = this.status) >= 0 && (var8_10 = var1_1 - System.nanoTime()) > 0L) {
                block13 : {
                    if ((var8_10 = TimeUnit.NANOSECONDS.toMillis(var8_10)) <= 0L || !ForkJoinTask.U.compareAndSwapInt(this, ForkJoinTask.STATUS, var6_8, var6_8 | 65536)) continue;
                    // MONITORENTER : this
                    var6_8 = this.status;
                    if (var6_8 < 0) ** GOTO lbl35
                    this.wait(var8_10);
                    break block13;
lbl35: // 1 sources:
                    this.notifyAll();
                }
                // MONITOREXIT : this
            }
        }
        var5_7 = var6_8;
        if (var6_8 >= 0) {
            var5_7 = this.status;
        }
        if ((var6_8 = var5_7 & -268435456) == -268435456) return this.getRawResult();
        if (var6_8 == -1073741824) throw new CancellationException();
        if (var6_8 == Integer.MIN_VALUE) throw new ExecutionException(this.getThrowableException());
        throw new TimeoutException();
    }

    public final Throwable getException() {
        int n = this.status & -268435456;
        Throwable throwable = n >= -268435456 ? null : (n == -1073741824 ? new CancellationException() : this.getThrowableException());
        return throwable;
    }

    public final short getForkJoinTaskTag() {
        return (short)this.status;
    }

    public abstract V getRawResult();

    void internalPropagateException(Throwable throwable) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void internalWait(long l) {
        int n = this.status;
        if (n < 0) return;
        if (!U.compareAndSwapInt(this, STATUS, n, n | 65536)) return;
        synchronized (this) {
            n = this.status;
            if (n >= 0) {
                try {
                    this.wait(l);
                }
                catch (InterruptedException interruptedException) {}
            } else {
                this.notifyAll();
            }
            return;
        }
    }

    public final V invoke() {
        int n = this.doInvoke() & -268435456;
        if (n != -268435456) {
            this.reportException(n);
        }
        return this.getRawResult();
    }

    @Override
    public final boolean isCancelled() {
        boolean bl = (this.status & -268435456) == -1073741824;
        return bl;
    }

    public final boolean isCompletedAbnormally() {
        boolean bl = this.status < -268435456;
        return bl;
    }

    public final boolean isCompletedNormally() {
        boolean bl = (this.status & -268435456) == -268435456;
        return bl;
    }

    @Override
    public final boolean isDone() {
        boolean bl = this.status < 0;
        return bl;
    }

    public final V join() {
        int n = this.doJoin() & -268435456;
        if (n != -268435456) {
            this.reportException(n);
        }
        return this.getRawResult();
    }

    public final void quietlyComplete() {
        this.setCompletion(-268435456);
    }

    public final void quietlyInvoke() {
        this.doInvoke();
    }

    public final void quietlyJoin() {
        this.doJoin();
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final int recordExceptionalCompletion(Throwable throwable) {
        int n;
        int n2 = n = this.status;
        if (n < 0) return n2;
        n2 = System.identityHashCode(this);
        ReentrantLock reentrantLock = exceptionTableLock;
        reentrantLock.lock();
        try {
            ForkJoinTask.expungeStaleExceptions();
            ExceptionNode[] arrexceptionNode = exceptionTable;
            n2 = arrexceptionNode.length - 1 & n2;
            ExceptionNode exceptionNode = arrexceptionNode[n2];
            do {
                block8 : {
                    block7 : {
                        block6 : {
                            if (exceptionNode != null) break block6;
                            arrexceptionNode[n2] = new ExceptionNode(this, throwable, arrexceptionNode[n2], exceptionTableRefQueue);
                            break block7;
                        }
                        T t = exceptionNode.get();
                        if (t != this) break block8;
                    }
                    reentrantLock.unlock();
                    return this.setCompletion(Integer.MIN_VALUE);
                }
                exceptionNode = exceptionNode.next;
            } while (true);
        }
        catch (Throwable throwable2) {
            reentrantLock.unlock();
            throw throwable2;
        }
    }

    public void reinitialize() {
        if ((this.status & -268435456) == Integer.MIN_VALUE) {
            this.clearExceptionalCompletion();
        } else {
            this.status = 0;
        }
    }

    public final short setForkJoinTaskTag(short s) {
        long l;
        Unsafe unsafe;
        int n;
        while (!(unsafe = U).compareAndSwapInt(this, l = STATUS, n = this.status, 65535 & s | -65536 & n)) {
        }
        return (short)n;
    }

    protected abstract void setRawResult(V var1);

    public boolean tryUnfork() {
        Thread thread = Thread.currentThread();
        boolean bl = thread instanceof ForkJoinWorkerThread ? ((ForkJoinWorkerThread)thread).workQueue.tryUnpush(this) : ForkJoinPool.common.tryExternalUnpush(this);
        return bl;
    }

    static final class AdaptedCallable<T>
    extends ForkJoinTask<T>
    implements RunnableFuture<T> {
        private static final long serialVersionUID = 2838392045355241008L;
        final Callable<? extends T> callable;
        T result;

        AdaptedCallable(Callable<? extends T> callable) {
            if (callable != null) {
                this.callable = callable;
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public final boolean exec() {
            try {
                this.result = this.callable.call();
                return true;
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
        }

        @Override
        public final T getRawResult() {
            return this.result;
        }

        @Override
        public final void run() {
            this.invoke();
        }

        @Override
        public final void setRawResult(T t) {
            this.result = t;
        }
    }

    static final class AdaptedRunnable<T>
    extends ForkJoinTask<T>
    implements RunnableFuture<T> {
        private static final long serialVersionUID = 5232453952276885070L;
        T result;
        final Runnable runnable;

        AdaptedRunnable(Runnable runnable, T t) {
            if (runnable != null) {
                this.runnable = runnable;
                this.result = t;
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public final boolean exec() {
            this.runnable.run();
            return true;
        }

        @Override
        public final T getRawResult() {
            return this.result;
        }

        @Override
        public final void run() {
            this.invoke();
        }

        @Override
        public final void setRawResult(T t) {
            this.result = t;
        }
    }

    static final class AdaptedRunnableAction
    extends ForkJoinTask<Void>
    implements RunnableFuture<Void> {
        private static final long serialVersionUID = 5232453952276885070L;
        final Runnable runnable;

        AdaptedRunnableAction(Runnable runnable) {
            if (runnable != null) {
                this.runnable = runnable;
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public final boolean exec() {
            this.runnable.run();
            return true;
        }

        @Override
        public final Void getRawResult() {
            return null;
        }

        @Override
        public final void run() {
            this.invoke();
        }

        @Override
        public final void setRawResult(Void void_) {
        }
    }

    static final class ExceptionNode
    extends WeakReference<ForkJoinTask<?>> {
        final Throwable ex;
        final int hashCode;
        ExceptionNode next;
        final long thrower;

        ExceptionNode(ForkJoinTask<?> forkJoinTask, Throwable throwable, ExceptionNode exceptionNode, ReferenceQueue<Object> referenceQueue) {
            super(forkJoinTask, referenceQueue);
            this.ex = throwable;
            this.next = exceptionNode;
            this.thrower = Thread.currentThread().getId();
            this.hashCode = System.identityHashCode(forkJoinTask);
        }
    }

    static final class RunnableExecuteAction
    extends ForkJoinTask<Void> {
        private static final long serialVersionUID = 5232453952276885070L;
        final Runnable runnable;

        RunnableExecuteAction(Runnable runnable) {
            if (runnable != null) {
                this.runnable = runnable;
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public final boolean exec() {
            this.runnable.run();
            return true;
        }

        @Override
        public final Void getRawResult() {
            return null;
        }

        @Override
        void internalPropagateException(Throwable throwable) {
            RunnableExecuteAction.rethrow(throwable);
        }

        @Override
        public final void setRawResult(Void void_) {
        }
    }

}

