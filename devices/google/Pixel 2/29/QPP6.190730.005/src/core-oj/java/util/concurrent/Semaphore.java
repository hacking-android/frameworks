/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Semaphore
implements Serializable {
    private static final long serialVersionUID = -3222578661600680210L;
    private final Sync sync;

    public Semaphore(int n) {
        this.sync = new NonfairSync(n);
    }

    public Semaphore(int n, boolean bl) {
        Sync sync = bl ? new FairSync(n) : new NonfairSync(n);
        this.sync = sync;
    }

    public void acquire() throws InterruptedException {
        this.sync.acquireSharedInterruptibly(1);
    }

    public void acquire(int n) throws InterruptedException {
        if (n >= 0) {
            this.sync.acquireSharedInterruptibly(n);
            return;
        }
        throw new IllegalArgumentException();
    }

    public void acquireUninterruptibly() {
        this.sync.acquireShared(1);
    }

    public void acquireUninterruptibly(int n) {
        if (n >= 0) {
            this.sync.acquireShared(n);
            return;
        }
        throw new IllegalArgumentException();
    }

    public int availablePermits() {
        return this.sync.getPermits();
    }

    public int drainPermits() {
        return this.sync.drainPermits();
    }

    public final int getQueueLength() {
        return this.sync.getQueueLength();
    }

    protected Collection<Thread> getQueuedThreads() {
        return this.sync.getQueuedThreads();
    }

    public final boolean hasQueuedThreads() {
        return this.sync.hasQueuedThreads();
    }

    public boolean isFair() {
        return this.sync instanceof FairSync;
    }

    protected void reducePermits(int n) {
        if (n >= 0) {
            this.sync.reducePermits(n);
            return;
        }
        throw new IllegalArgumentException();
    }

    public void release() {
        this.sync.releaseShared(1);
    }

    public void release(int n) {
        if (n >= 0) {
            this.sync.releaseShared(n);
            return;
        }
        throw new IllegalArgumentException();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("[Permits = ");
        stringBuilder.append(this.sync.getPermits());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public boolean tryAcquire() {
        Sync sync = this.sync;
        boolean bl = true;
        if (sync.nonfairTryAcquireShared(1) < 0) {
            bl = false;
        }
        return bl;
    }

    public boolean tryAcquire(int n) {
        if (n >= 0) {
            boolean bl = this.sync.nonfairTryAcquireShared(n) >= 0;
            return bl;
        }
        throw new IllegalArgumentException();
    }

    public boolean tryAcquire(int n, long l, TimeUnit timeUnit) throws InterruptedException {
        if (n >= 0) {
            return this.sync.tryAcquireSharedNanos(n, timeUnit.toNanos(l));
        }
        throw new IllegalArgumentException();
    }

    public boolean tryAcquire(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.sync.tryAcquireSharedNanos(1, timeUnit.toNanos(l));
    }

    static final class FairSync
    extends Sync {
        private static final long serialVersionUID = 2014338818796000944L;

        FairSync(int n) {
            super(n);
        }

        @Override
        protected int tryAcquireShared(int n) {
            int n2;
            int n3;
            do {
                if (!this.hasQueuedPredecessors()) continue;
                return -1;
            } while ((n2 = (n3 = this.getState()) - n) >= 0 && !this.compareAndSetState(n3, n2));
            return n2;
        }
    }

    static final class NonfairSync
    extends Sync {
        private static final long serialVersionUID = -2694183684443567898L;

        NonfairSync(int n) {
            super(n);
        }

        @Override
        protected int tryAcquireShared(int n) {
            return this.nonfairTryAcquireShared(n);
        }
    }

    static abstract class Sync
    extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 1192457210091910933L;

        Sync(int n) {
            this.setState(n);
        }

        final int drainPermits() {
            int n;
            while ((n = this.getState()) != 0 && !this.compareAndSetState(n, 0)) {
            }
            return n;
        }

        final int getPermits() {
            return this.getState();
        }

        final int nonfairTryAcquireShared(int n) {
            int n2;
            int n3;
            while ((n2 = (n3 = this.getState()) - n) >= 0 && !this.compareAndSetState(n3, n2)) {
            }
            return n2;
        }

        final void reducePermits(int n) {
            int n2;
            int n3;
            while ((n2 = (n3 = this.getState()) - n) <= n3) {
                if (!this.compareAndSetState(n3, n2)) continue;
                return;
            }
            throw new Error("Permit count underflow");
        }

        @Override
        protected final boolean tryReleaseShared(int n) {
            int n2;
            int n3;
            while ((n2 = (n3 = this.getState()) + n) >= n3) {
                if (!this.compareAndSetState(n3, n2)) continue;
                return true;
            }
            throw new Error("Maximum permit count exceeded");
        }
    }

}

