/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class CountDownLatch {
    private final Sync sync;

    public CountDownLatch(int n) {
        if (n >= 0) {
            this.sync = new Sync(n);
            return;
        }
        throw new IllegalArgumentException("count < 0");
    }

    public void await() throws InterruptedException {
        this.sync.acquireSharedInterruptibly(1);
    }

    public boolean await(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.sync.tryAcquireSharedNanos(1, timeUnit.toNanos(l));
    }

    public void countDown() {
        this.sync.releaseShared(1);
    }

    public long getCount() {
        return this.sync.getCount();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("[Count = ");
        stringBuilder.append(this.sync.getCount());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static final class Sync
    extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 4982264981922014374L;

        Sync(int n) {
            this.setState(n);
        }

        int getCount() {
            return this.getState();
        }

        @Override
        protected int tryAcquireShared(int n) {
            n = this.getState() == 0 ? 1 : -1;
            return n;
        }

        @Override
        protected boolean tryReleaseShared(int n) {
            int n2;
            boolean bl;
            do {
                n = this.getState();
                bl = false;
                if (n != 0) continue;
                return false;
            } while (!this.compareAndSetState(n, n2 = n - 1));
            if (n2 == 0) {
                bl = true;
            }
            return bl;
        }
    }

}

