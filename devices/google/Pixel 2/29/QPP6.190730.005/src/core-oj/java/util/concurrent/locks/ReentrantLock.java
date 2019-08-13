/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.locks;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ReentrantLock
implements Lock,
Serializable {
    private static final long serialVersionUID = 7373984872572414699L;
    private final Sync sync;

    public ReentrantLock() {
        this.sync = new NonfairSync();
    }

    public ReentrantLock(boolean bl) {
        Sync sync = bl ? new FairSync() : new NonfairSync();
        this.sync = sync;
    }

    public int getHoldCount() {
        return this.sync.getHoldCount();
    }

    protected Thread getOwner() {
        return this.sync.getOwner();
    }

    public final int getQueueLength() {
        return this.sync.getQueueLength();
    }

    protected Collection<Thread> getQueuedThreads() {
        return this.sync.getQueuedThreads();
    }

    public int getWaitQueueLength(Condition condition) {
        if (condition != null) {
            if (condition instanceof AbstractQueuedSynchronizer.ConditionObject) {
                return this.sync.getWaitQueueLength((AbstractQueuedSynchronizer.ConditionObject)condition);
            }
            throw new IllegalArgumentException("not owner");
        }
        throw new NullPointerException();
    }

    protected Collection<Thread> getWaitingThreads(Condition condition) {
        if (condition != null) {
            if (condition instanceof AbstractQueuedSynchronizer.ConditionObject) {
                return this.sync.getWaitingThreads((AbstractQueuedSynchronizer.ConditionObject)condition);
            }
            throw new IllegalArgumentException("not owner");
        }
        throw new NullPointerException();
    }

    public final boolean hasQueuedThread(Thread thread) {
        return this.sync.isQueued(thread);
    }

    public final boolean hasQueuedThreads() {
        return this.sync.hasQueuedThreads();
    }

    public boolean hasWaiters(Condition condition) {
        if (condition != null) {
            if (condition instanceof AbstractQueuedSynchronizer.ConditionObject) {
                return this.sync.hasWaiters((AbstractQueuedSynchronizer.ConditionObject)condition);
            }
            throw new IllegalArgumentException("not owner");
        }
        throw new NullPointerException();
    }

    public final boolean isFair() {
        return this.sync instanceof FairSync;
    }

    public boolean isHeldByCurrentThread() {
        return this.sync.isHeldExclusively();
    }

    public boolean isLocked() {
        return this.sync.isLocked();
    }

    @Override
    public void lock() {
        this.sync.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        this.sync.acquireInterruptibly(1);
    }

    @Override
    public Condition newCondition() {
        return this.sync.newCondition();
    }

    public String toString() {
        CharSequence charSequence;
        Thread thread = this.sync.getOwner();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        if (thread == null) {
            charSequence = "[Unlocked]";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[Locked by thread ");
            ((StringBuilder)charSequence).append(thread.getName());
            ((StringBuilder)charSequence).append("]");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        return stringBuilder.toString();
    }

    @Override
    public boolean tryLock() {
        return this.sync.nonfairTryAcquire(1);
    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.sync.tryAcquireNanos(1, timeUnit.toNanos(l));
    }

    @Override
    public void unlock() {
        this.sync.release(1);
    }

    static final class FairSync
    extends Sync {
        private static final long serialVersionUID = -3000897897090466540L;

        FairSync() {
        }

        @Override
        final void lock() {
            this.acquire(1);
        }

        @Override
        protected final boolean tryAcquire(int n) {
            Thread thread = Thread.currentThread();
            int n2 = this.getState();
            if (n2 == 0) {
                if (!this.hasQueuedPredecessors() && this.compareAndSetState(0, n)) {
                    this.setExclusiveOwnerThread(thread);
                    return true;
                }
            } else if (thread == this.getExclusiveOwnerThread()) {
                if ((n = n2 + n) >= 0) {
                    this.setState(n);
                    return true;
                }
                throw new Error("Maximum lock count exceeded");
            }
            return false;
        }
    }

    static final class NonfairSync
    extends Sync {
        private static final long serialVersionUID = 7316153563782823691L;

        NonfairSync() {
        }

        @Override
        final void lock() {
            if (this.compareAndSetState(0, 1)) {
                this.setExclusiveOwnerThread(Thread.currentThread());
            } else {
                this.acquire(1);
            }
        }

        @Override
        protected final boolean tryAcquire(int n) {
            return this.nonfairTryAcquire(n);
        }
    }

    static abstract class Sync
    extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -5179523762034025860L;

        Sync() {
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.setState(0);
        }

        final int getHoldCount() {
            int n = this.isHeldExclusively() ? this.getState() : 0;
            return n;
        }

        final Thread getOwner() {
            Thread thread = this.getState() == 0 ? null : this.getExclusiveOwnerThread();
            return thread;
        }

        @Override
        protected final boolean isHeldExclusively() {
            boolean bl = this.getExclusiveOwnerThread() == Thread.currentThread();
            return bl;
        }

        final boolean isLocked() {
            boolean bl = this.getState() != 0;
            return bl;
        }

        abstract void lock();

        final AbstractQueuedSynchronizer.ConditionObject newCondition() {
            return new AbstractQueuedSynchronizer.ConditionObject(this);
        }

        final boolean nonfairTryAcquire(int n) {
            Thread thread = Thread.currentThread();
            int n2 = this.getState();
            if (n2 == 0) {
                if (this.compareAndSetState(0, n)) {
                    this.setExclusiveOwnerThread(thread);
                    return true;
                }
            } else if (thread == this.getExclusiveOwnerThread()) {
                if ((n = n2 + n) >= 0) {
                    this.setState(n);
                    return true;
                }
                throw new Error("Maximum lock count exceeded");
            }
            return false;
        }

        @Override
        protected final boolean tryRelease(int n) {
            n = this.getState() - n;
            if (Thread.currentThread() == this.getExclusiveOwnerThread()) {
                boolean bl = false;
                if (n == 0) {
                    bl = true;
                    this.setExclusiveOwnerThread(null);
                }
                this.setState(n);
                return bl;
            }
            throw new IllegalMonitorStateException();
        }
    }

}

