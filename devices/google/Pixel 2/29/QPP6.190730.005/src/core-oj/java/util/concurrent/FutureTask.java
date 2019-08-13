/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.lang.reflect.Field;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;
import sun.misc.Unsafe;

public class FutureTask<V>
implements RunnableFuture<V> {
    private static final int CANCELLED = 4;
    private static final int COMPLETING = 1;
    private static final int EXCEPTIONAL = 3;
    private static final int INTERRUPTED = 6;
    private static final int INTERRUPTING = 5;
    private static final int NEW = 0;
    private static final int NORMAL = 2;
    private static final long RUNNER;
    private static final long STATE;
    private static final Unsafe U;
    private static final long WAITERS;
    private Callable<V> callable;
    private Object outcome;
    private volatile Thread runner;
    private volatile int state;
    private volatile WaitNode waiters;

    static {
        U = Unsafe.getUnsafe();
        try {
            STATE = U.objectFieldOffset(FutureTask.class.getDeclaredField("state"));
            RUNNER = U.objectFieldOffset(FutureTask.class.getDeclaredField("runner"));
            WAITERS = U.objectFieldOffset(FutureTask.class.getDeclaredField("waiters"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public FutureTask(Runnable runnable, V v) {
        this.callable = Executors.callable(runnable, v);
        this.state = 0;
    }

    public FutureTask(Callable<V> callable) {
        if (callable != null) {
            this.callable = callable;
            this.state = 0;
            return;
        }
        throw new NullPointerException();
    }

    private int awaitDone(boolean bl, long l) throws InterruptedException {
        long l2 = 0L;
        WaitNode waitNode = null;
        boolean bl2 = false;
        do {
            long l3;
            int n;
            if ((n = this.state) > 1) {
                if (waitNode != null) {
                    waitNode.thread = null;
                }
                return n;
            }
            if (n == 1) {
                Thread.yield();
                continue;
            }
            if (Thread.interrupted()) break;
            if (waitNode == null) {
                if (bl && l <= 0L) {
                    return n;
                }
                waitNode = new WaitNode();
                continue;
            }
            if (!bl2) {
                WaitNode waitNode2;
                Unsafe unsafe = U;
                l3 = WAITERS;
                waitNode.next = waitNode2 = this.waiters;
                bl2 = unsafe.compareAndSwapObject(this, l3, waitNode2, waitNode);
                continue;
            }
            if (bl) {
                if (l2 == 0L) {
                    l2 = l3 = System.nanoTime();
                    if (l3 == 0L) {
                        l2 = 1L;
                    }
                    l3 = l;
                } else {
                    l3 = System.nanoTime() - l2;
                    if (l3 >= l) {
                        this.removeWaiter(waitNode);
                        return this.state;
                    }
                    l3 = l - l3;
                }
                if (this.state >= 1) continue;
                LockSupport.parkNanos(this, l3);
                continue;
            }
            LockSupport.park(this);
        } while (true);
        this.removeWaiter(waitNode);
        throw new InterruptedException();
    }

    private void finishCompletion() {
        block3 : {
            Object object;
            Object object2;
            do {
                object2 = object = this.waiters;
                if (object == null) break block3;
            } while (!U.compareAndSwapObject(this, WAITERS, object2, null));
            do {
                if ((object = ((WaitNode)object2).thread) != null) {
                    ((WaitNode)object2).thread = null;
                    LockSupport.unpark((Thread)object);
                }
                if ((object = ((WaitNode)object2).next) == null) break;
                ((WaitNode)object2).next = null;
                object2 = object;
            } while (true);
        }
        this.done();
        this.callable = null;
    }

    private void handlePossibleCancellationInterrupt(int n) {
        if (n == 5) {
            while (this.state == 5) {
                Thread.yield();
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void removeWaiter(WaitNode var1_1) {
        if (var1_1 == null) return;
        var1_1.thread = null;
        block0 : do {
            var2_2 = null;
            var1_1 = this.waiters;
            while (var1_1 != null) {
                var3_3 = var1_1.next;
                if (var1_1.thread != null) {
                    var4_4 = var1_1;
                } else if (var2_2 != null) {
                    var2_2.next = var3_3;
                    var4_4 = var2_2;
                    if (var2_2.thread == null) {
                        continue block0;
                    }
                } else {
                    var4_4 = var2_2;
                    if (FutureTask.U.compareAndSwapObject(this, FutureTask.WAITERS, var1_1, var3_3)) ** break;
                    continue block0;
                }
                var1_1 = var3_3;
                var2_2 = var4_4;
            }
            return;
            break;
        } while (true);
    }

    private V report(int n) throws ExecutionException {
        Object object = this.outcome;
        if (n == 2) {
            return (V)object;
        }
        if (n >= 4) {
            throw new CancellationException();
        }
        throw new ExecutionException((Throwable)object);
    }

    @Override
    public boolean cancel(boolean bl) {
        int n;
        Object object;
        long l;
        if (this.state == 0 && ((Unsafe)(object = U)).compareAndSwapInt(this, l = STATE, 0, n = bl ? 5 : 4)) {
            if (bl) {
                block9 : {
                    try {
                        object = this.runner;
                        if (object == null) break block9;
                    }
                    catch (Throwable throwable) {
                        U.putOrderedInt(this, STATE, 6);
                        throw throwable;
                    }
                    ((Thread)object).interrupt();
                }
                try {
                    U.putOrderedInt(this, STATE, 6);
                }
                finally {
                    this.finishCompletion();
                }
            }
            return true;
        }
        return false;
    }

    protected void done() {
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        int n;
        int n2 = n = this.state;
        if (n <= 1) {
            n2 = this.awaitDone(false, 0L);
        }
        return this.report(n2);
    }

    @Override
    public V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        if (timeUnit != null) {
            int n;
            int n2 = n = this.state;
            if (n <= 1) {
                n2 = n = this.awaitDone(true, timeUnit.toNanos(l));
                if (n <= 1) {
                    throw new TimeoutException();
                }
            }
            return this.report(n2);
        }
        throw new NullPointerException();
    }

    @Override
    public boolean isCancelled() {
        boolean bl = this.state >= 4;
        return bl;
    }

    @Override
    public boolean isDone() {
        boolean bl = this.state != 0;
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        if (this.state != 0) return;
        if (!FutureTask.U.compareAndSwapObject(this, FutureTask.RUNNER, null, Thread.currentThread())) {
            return;
        }
        callable /* !! */  = this.callable;
        if (callable /* !! */  == null) return;
        n = this.state;
        if (n != 0) return;
        {
            catch (Throwable throwable) {
                throw throwable;
            }
        }
        try {
            callable /* !! */  = callable /* !! */ .call();
            n = 1;
            ** GOTO lbl20
        }
        catch (Throwable throwable) {
            try {
                this.setException(throwable);
                callable /* !! */  = null;
                n = 0;
lbl20: // 2 sources:
                if (n == 0) return;
                this.set(callable /* !! */ );
                return;
            }
            finally {
                this.runner = null;
                n = this.state;
                if (n >= 5) {
                    this.handlePossibleCancellationInterrupt(n);
                }
            }
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected boolean runAndReset() {
        int n2;
        int n;
        boolean bl;
        block7 : {
            n = this.state;
            bl = false;
            if (n != 0) return false;
            if (!U.compareAndSwapObject(this, RUNNER, null, Thread.currentThread())) {
                return false;
            }
            n2 = 0;
            int n3 = this.state;
            Callable<V> callable = this.callable;
            n = n2;
            if (callable == null) break block7;
            n = n2;
            if (n3 != 0) break block7;
            {
                catch (Throwable throwable) {
                    this.runner = null;
                    n = this.state;
                    if (n < 5) throw throwable;
                    this.handlePossibleCancellationInterrupt(n);
                    throw throwable;
                }
            }
            try {
                callable.call();
                n = 1;
            }
            catch (Throwable throwable) {
                this.setException(throwable);
                n = n2;
            }
        }
        this.runner = null;
        n2 = this.state;
        if (n2 >= 5) {
            this.handlePossibleCancellationInterrupt(n2);
        }
        boolean bl2 = bl;
        if (n == 0) return bl2;
        bl2 = bl;
        if (n2 != 0) return bl2;
        return true;
    }

    protected void set(V v) {
        if (U.compareAndSwapInt(this, STATE, 0, 1)) {
            this.outcome = v;
            U.putOrderedInt(this, STATE, 2);
            this.finishCompletion();
        }
    }

    protected void setException(Throwable throwable) {
        if (U.compareAndSwapInt(this, STATE, 0, 1)) {
            this.outcome = throwable;
            U.putOrderedInt(this, STATE, 3);
            this.finishCompletion();
        }
    }

    static final class WaitNode {
        volatile WaitNode next;
        volatile Thread thread = Thread.currentThread();

        WaitNode() {
        }
    }

}

