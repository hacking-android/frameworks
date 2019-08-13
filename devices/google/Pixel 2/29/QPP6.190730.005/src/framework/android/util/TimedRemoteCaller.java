/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.os.SystemClock;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.annotations.GuardedBy;
import java.util.concurrent.TimeoutException;

public abstract class TimedRemoteCaller<T> {
    public static final long DEFAULT_CALL_TIMEOUT_MILLIS = 5000L;
    @GuardedBy(value={"mLock"})
    private final SparseIntArray mAwaitedCalls = new SparseIntArray(1);
    private final long mCallTimeoutMillis;
    private final Object mLock = new Object();
    @GuardedBy(value={"mLock"})
    private final SparseArray<T> mReceivedCalls = new SparseArray(1);
    @GuardedBy(value={"mLock"})
    private int mSequenceCounter;

    public TimedRemoteCaller(long l) {
        this.mCallTimeoutMillis = l;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final T getResultTimed(int n) throws TimeoutException {
        long l = SystemClock.uptimeMillis();
        do {
            T t;
            try {
                Object object = this.mLock;
                synchronized (object) {
                    if (this.mReceivedCalls.indexOfKey(n) < 0) break block7;
                    t = this.mReceivedCalls.removeReturnOld(n);
                }
            }
            catch (InterruptedException interruptedException) {
                continue;
            }
            {
                block7 : {
                    return t;
                }
                long l2 = SystemClock.uptimeMillis();
                if ((l2 = this.mCallTimeoutMillis - (l2 - l)) <= 0L) {
                    this.mAwaitedCalls.delete(n);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No response for sequence: ");
                    stringBuilder.append(n);
                    TimeoutException timeoutException = new TimeoutException(stringBuilder.toString());
                    throw timeoutException;
                }
                this.mLock.wait(l2);
                continue;
            }
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final int onBeforeRemoteCall() {
        Object object = this.mLock;
        synchronized (object) {
            int n;
            do {
                n = this.mSequenceCounter;
                this.mSequenceCounter = n + 1;
            } while (this.mAwaitedCalls.get(n) != 0);
            this.mAwaitedCalls.put(n, 1);
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void onRemoteMethodResult(T t, int n) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mAwaitedCalls.get(n) == 0) return;
            boolean bl = true;
            if (!bl) return;
            this.mAwaitedCalls.delete(n);
            this.mReceivedCalls.put(n, t);
            this.mLock.notifyAll();
            return;
        }
    }
}

