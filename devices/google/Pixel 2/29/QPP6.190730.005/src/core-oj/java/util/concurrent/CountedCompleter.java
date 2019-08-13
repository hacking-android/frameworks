/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.lang.reflect.Field;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ForkJoinWorkerThread;
import sun.misc.Unsafe;

public abstract class CountedCompleter<T>
extends ForkJoinTask<T> {
    private static final long PENDING;
    private static final Unsafe U;
    private static final long serialVersionUID = 5232453752276485070L;
    final CountedCompleter<?> completer;
    volatile int pending;

    static {
        U = Unsafe.getUnsafe();
        try {
            PENDING = U.objectFieldOffset(CountedCompleter.class.getDeclaredField("pending"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    protected CountedCompleter() {
        this.completer = null;
    }

    protected CountedCompleter(CountedCompleter<?> countedCompleter) {
        this.completer = countedCompleter;
    }

    protected CountedCompleter(CountedCompleter<?> countedCompleter, int n) {
        this.completer = countedCompleter;
        this.pending = n;
    }

    public final void addToPendingCount(int n) {
        U.getAndAddInt(this, PENDING, n);
    }

    public final boolean compareAndSetPendingCount(int n, int n2) {
        return U.compareAndSwapInt(this, PENDING, n, n2);
    }

    @Override
    public void complete(T object) {
        this.setRawResult(object);
        this.onCompletion(this);
        this.quietlyComplete();
        object = this.completer;
        if (object != null) {
            ((CountedCompleter)object).tryComplete();
        }
    }

    public abstract void compute();

    public final int decrementPendingCountUnlessZero() {
        int n;
        while ((n = this.pending) != 0 && !U.compareAndSwapInt(this, PENDING, n, n - 1)) {
        }
        return n;
    }

    @Override
    protected final boolean exec() {
        this.compute();
        return false;
    }

    public final CountedCompleter<?> firstComplete() {
        int n;
        do {
            if ((n = this.pending) != 0) continue;
            return this;
        } while (!U.compareAndSwapInt(this, PENDING, n, n - 1));
        return null;
    }

    public final CountedCompleter<?> getCompleter() {
        return this.completer;
    }

    public final int getPendingCount() {
        return this.pending;
    }

    @Override
    public T getRawResult() {
        return null;
    }

    public final CountedCompleter<?> getRoot() {
        CountedCompleter<?> countedCompleter;
        CountedCompleter<?> countedCompleter2 = this;
        while ((countedCompleter = countedCompleter2.completer) != null) {
            countedCompleter2 = countedCompleter;
        }
        return countedCompleter2;
    }

    public final void helpComplete(int n) {
        if (n > 0 && this.status >= 0) {
            Thread thread = Thread.currentThread();
            if (thread instanceof ForkJoinWorkerThread) {
                thread = (ForkJoinWorkerThread)thread;
                ((ForkJoinWorkerThread)thread).pool.helpComplete(((ForkJoinWorkerThread)thread).workQueue, this, n);
            } else {
                ForkJoinPool.common.externalHelpComplete(this, n);
            }
        }
    }

    @Override
    void internalPropagateException(Throwable throwable) {
        CountedCompleter<?> countedCompleter;
        CountedCompleter<?> countedCompleter2 = countedCompleter = this;
        while (countedCompleter.onExceptionalCompletion(throwable, countedCompleter2)) {
            CountedCompleter<?> countedCompleter3;
            countedCompleter2 = countedCompleter;
            countedCompleter = countedCompleter3 = countedCompleter.completer;
            if (countedCompleter3 != null && countedCompleter.status >= 0 && countedCompleter.recordExceptionalCompletion(throwable) == Integer.MIN_VALUE) continue;
        }
    }

    public final CountedCompleter<?> nextComplete() {
        CountedCompleter<?> countedCompleter = this.completer;
        if (countedCompleter != null) {
            return countedCompleter.firstComplete();
        }
        this.quietlyComplete();
        return null;
    }

    public void onCompletion(CountedCompleter<?> countedCompleter) {
    }

    public boolean onExceptionalCompletion(Throwable throwable, CountedCompleter<?> countedCompleter) {
        return true;
    }

    public final void propagateCompletion() {
        CountedCompleter<?> countedCompleter = this;
        do {
            CountedCompleter<?> countedCompleter2 = countedCompleter;
            int n = countedCompleter2.pending;
            if (n == 0) {
                CountedCompleter<?> countedCompleter3;
                countedCompleter = countedCompleter3 = countedCompleter2.completer;
                if (countedCompleter3 != null) continue;
                countedCompleter2.quietlyComplete();
                return;
            }
            if (U.compareAndSwapInt(countedCompleter2, PENDING, n, n - 1)) {
                return;
            }
            countedCompleter = countedCompleter2;
        } while (true);
    }

    public final void quietlyCompleteRoot() {
        CountedCompleter<?> countedCompleter = this;
        do {
            CountedCompleter<?> countedCompleter2;
            if ((countedCompleter2 = countedCompleter.completer) == null) {
                countedCompleter.quietlyComplete();
                return;
            }
            countedCompleter = countedCompleter2;
        } while (true);
    }

    public final void setPendingCount(int n) {
        this.pending = n;
    }

    @Override
    protected void setRawResult(T t) {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public final void tryComplete() {
        var2_2 = var1_1 = this;
        do lbl-1000: // 3 sources:
        {
            if ((var3_3 = var1_1.pending) != 0) continue;
            var1_1.onCompletion(var2_2);
            var4_4 = var1_1;
            var1_1 = var5_5 = var1_1.completer;
            var2_2 = var4_4;
            if (var5_5 != null) ** GOTO lbl-1000
            var4_4.quietlyComplete();
            return;
        } while (!CountedCompleter.U.compareAndSwapInt(var1_1, CountedCompleter.PENDING, var3_3, var3_3 - 1));
    }
}

