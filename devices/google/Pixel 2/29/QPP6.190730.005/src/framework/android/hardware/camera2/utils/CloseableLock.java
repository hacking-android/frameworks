/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import android.util.Log;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CloseableLock
implements AutoCloseable {
    private static final boolean VERBOSE = false;
    private final String TAG;
    private volatile boolean mClosed = false;
    private final Condition mCondition = this.mLock.newCondition();
    private boolean mExclusive = false;
    private final ReentrantLock mLock = new ReentrantLock();
    private final ThreadLocal<Integer> mLockCount = new ThreadLocal<Integer>(){

        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    private final String mName;
    private int mSharedLocks = 0;

    public CloseableLock() {
        this.TAG = "CloseableLock";
        this.mName = "";
    }

    public CloseableLock(String string2) {
        this.TAG = "CloseableLock";
        this.mName = string2;
    }

    private void log(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CloseableLock[");
        stringBuilder.append(this.mName);
        stringBuilder.append("]");
        Log.v(stringBuilder.toString(), string2);
    }

    public ScopedLock acquireExclusiveLock() {
        int n;
        boolean bl;
        block9 : {
            block8 : {
                this.mLock.lock();
                bl = this.mClosed;
                if (!bl) break block8;
                this.mLock.unlock();
                return null;
            }
            n = this.mLockCount.get();
            if (this.mExclusive || n <= 0) break block9;
            IllegalStateException illegalStateException = new IllegalStateException("Cannot acquire exclusive lock while holding shared lock");
            throw illegalStateException;
        }
        while (n == 0) {
            if (!this.mExclusive && this.mSharedLocks <= 0) break;
            this.mCondition.awaitUninterruptibly();
            bl = this.mClosed;
            if (!bl) continue;
            this.mLock.unlock();
            return null;
        }
        try {
            this.mExclusive = true;
            n = this.mLockCount.get();
            this.mLockCount.set(n + 1);
            return new ScopedLock();
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            this.mLock.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ScopedLock acquireLock() {
        this.mLock.lock();
        boolean bl = this.mClosed;
        if (bl) {
            this.mLock.unlock();
            return null;
        }
        int n = this.mLockCount.get();
        if (this.mExclusive && n > 0) {
            IllegalStateException illegalStateException = new IllegalStateException("Cannot acquire shared lock while holding exclusive lock");
            throw illegalStateException;
        }
        while (this.mExclusive) {
            this.mCondition.awaitUninterruptibly();
            bl = this.mClosed;
            if (!bl) continue;
            this.mLock.unlock();
            return null;
        }
        ++this.mSharedLocks;
        n = this.mLockCount.get();
        this.mLockCount.set(n + 1);
        return new ScopedLock();
    }

    @Override
    public void close() {
        if (this.mClosed) {
            return;
        }
        if (this.acquireExclusiveLock() == null) {
            return;
        }
        if (this.mLockCount.get() == 1) {
            try {
                this.mLock.lock();
                this.mClosed = true;
                this.mExclusive = false;
                this.mSharedLocks = 0;
                this.mLockCount.remove();
                this.mCondition.signalAll();
                return;
            }
            finally {
                this.mLock.unlock();
            }
        }
        throw new IllegalStateException("Cannot close while one or more acquired locks are being held by this thread; release all other locks first");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void releaseLock() {
        if (this.mLockCount.get() <= 0) throw new IllegalStateException("Cannot release lock that was not acquired by this thread");
        try {
            this.mLock.lock();
            if (!this.mClosed) {
                if (!this.mExclusive) {
                    --this.mSharedLocks;
                } else if (this.mSharedLocks != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Too many shared locks ");
                    stringBuilder.append(this.mSharedLocks);
                    AssertionError assertionError = new AssertionError((Object)stringBuilder.toString());
                    throw assertionError;
                }
                int n = this.mLockCount.get() - 1;
                this.mLockCount.set(n);
                if (n == 0 && this.mExclusive) {
                    this.mExclusive = false;
                    this.mCondition.signalAll();
                    return;
                }
                if (n != 0) return;
                if (this.mSharedLocks != 0) return;
                this.mCondition.signalAll();
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Do not release after the lock has been closed");
            throw illegalStateException;
        }
        finally {
            this.mLock.unlock();
        }
    }

    public class ScopedLock
    implements AutoCloseable {
        private ScopedLock() {
        }

        @Override
        public void close() {
            CloseableLock.this.releaseLock();
        }
    }

}

