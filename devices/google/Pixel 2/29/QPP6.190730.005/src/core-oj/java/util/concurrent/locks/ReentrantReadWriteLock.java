/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.locks;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import sun.misc.Unsafe;

public class ReentrantReadWriteLock
implements ReadWriteLock,
Serializable {
    private static final long TID;
    private static final Unsafe U;
    private static final long serialVersionUID = -6992448646407690164L;
    private final ReadLock readerLock;
    final Sync sync;
    private final WriteLock writerLock;

    static {
        U = Unsafe.getUnsafe();
        try {
            TID = U.objectFieldOffset(Thread.class.getDeclaredField("tid"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public ReentrantReadWriteLock() {
        this(false);
    }

    public ReentrantReadWriteLock(boolean bl) {
        Sync sync = bl ? new FairSync() : new NonfairSync();
        this.sync = sync;
        this.readerLock = new ReadLock(this);
        this.writerLock = new WriteLock(this);
    }

    static final long getThreadId(Thread thread) {
        return U.getLongVolatile(thread, TID);
    }

    protected Thread getOwner() {
        return this.sync.getOwner();
    }

    public final int getQueueLength() {
        return this.sync.getQueueLength();
    }

    protected Collection<Thread> getQueuedReaderThreads() {
        return this.sync.getSharedQueuedThreads();
    }

    protected Collection<Thread> getQueuedThreads() {
        return this.sync.getQueuedThreads();
    }

    protected Collection<Thread> getQueuedWriterThreads() {
        return this.sync.getExclusiveQueuedThreads();
    }

    public int getReadHoldCount() {
        return this.sync.getReadHoldCount();
    }

    public int getReadLockCount() {
        return this.sync.getReadLockCount();
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

    public int getWriteHoldCount() {
        return this.sync.getWriteHoldCount();
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

    public boolean isWriteLocked() {
        return this.sync.isWriteLocked();
    }

    public boolean isWriteLockedByCurrentThread() {
        return this.sync.isHeldExclusively();
    }

    @Override
    public ReadLock readLock() {
        return this.readerLock;
    }

    public String toString() {
        int n = this.sync.getCount();
        int n2 = Sync.exclusiveCount(n);
        n = Sync.sharedCount(n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("[Write locks = ");
        stringBuilder.append(n2);
        stringBuilder.append(", Read locks = ");
        stringBuilder.append(n);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public WriteLock writeLock() {
        return this.writerLock;
    }

    static final class FairSync
    extends Sync {
        private static final long serialVersionUID = -2274990926593161451L;

        FairSync() {
        }

        @Override
        final boolean readerShouldBlock() {
            return this.hasQueuedPredecessors();
        }

        @Override
        final boolean writerShouldBlock() {
            return this.hasQueuedPredecessors();
        }
    }

    static final class NonfairSync
    extends Sync {
        private static final long serialVersionUID = -8159625535654395037L;

        NonfairSync() {
        }

        @Override
        final boolean readerShouldBlock() {
            return this.apparentlyFirstQueuedIsExclusive();
        }

        @Override
        final boolean writerShouldBlock() {
            return false;
        }
    }

    public static class ReadLock
    implements Lock,
    Serializable {
        private static final long serialVersionUID = -5992448646407690164L;
        private final Sync sync;

        protected ReadLock(ReentrantReadWriteLock reentrantReadWriteLock) {
            this.sync = reentrantReadWriteLock.sync;
        }

        @Override
        public void lock() {
            this.sync.acquireShared(1);
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            this.sync.acquireSharedInterruptibly(1);
        }

        @Override
        public Condition newCondition() {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            int n = this.sync.getReadLockCount();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            stringBuilder.append("[Read locks = ");
            stringBuilder.append(n);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public boolean tryLock() {
            return this.sync.tryReadLock();
        }

        @Override
        public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
            return this.sync.tryAcquireSharedNanos(1, timeUnit.toNanos(l));
        }

        @Override
        public void unlock() {
            this.sync.releaseShared(1);
        }
    }

    static abstract class Sync
    extends AbstractQueuedSynchronizer {
        static final int EXCLUSIVE_MASK = 65535;
        static final int MAX_COUNT = 65535;
        static final int SHARED_SHIFT = 16;
        static final int SHARED_UNIT = 65536;
        private static final long serialVersionUID = 6317671515068378041L;
        private transient HoldCounter cachedHoldCounter;
        private transient Thread firstReader;
        private transient int firstReaderHoldCount;
        private transient ThreadLocalHoldCounter readHolds = new ThreadLocalHoldCounter();

        Sync() {
            this.setState(this.getState());
        }

        static int exclusiveCount(int n) {
            return 65535 & n;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.readHolds = new ThreadLocalHoldCounter();
            this.setState(0);
        }

        static int sharedCount(int n) {
            return n >>> 16;
        }

        private IllegalMonitorStateException unmatchedUnlockException() {
            return new IllegalMonitorStateException("attempt to unlock read lock, not locked by current thread");
        }

        final int fullTryAcquireShared(Thread object) {
            HoldCounter holdCounter = null;
            do {
                HoldCounter holdCounter2;
                int n;
                HoldCounter holdCounter3;
                block18 : {
                    block20 : {
                        block21 : {
                            block19 : {
                                block17 : {
                                    if (Sync.exclusiveCount(n = this.getState()) == 0) break block17;
                                    holdCounter2 = holdCounter;
                                    if (this.getExclusiveOwnerThread() != object) {
                                        return -1;
                                    }
                                    break block18;
                                }
                                holdCounter2 = holdCounter;
                                if (!this.readerShouldBlock()) break block18;
                                if (this.firstReader != object) break block19;
                                holdCounter2 = holdCounter;
                                break block18;
                            }
                            holdCounter3 = holdCounter;
                            if (holdCounter != null) break block20;
                            holdCounter2 = this.cachedHoldCounter;
                            if (holdCounter2 == null) break block21;
                            holdCounter3 = holdCounter2;
                            if (holdCounter2.tid == ReentrantReadWriteLock.getThreadId((Thread)object)) break block20;
                        }
                        holdCounter3 = holdCounter2 = (HoldCounter)this.readHolds.get();
                        if (holdCounter2.count == 0) {
                            this.readHolds.remove();
                            holdCounter3 = holdCounter2;
                        }
                    }
                    holdCounter2 = holdCounter3;
                    if (holdCounter3.count == 0) {
                        return -1;
                    }
                }
                if (Sync.sharedCount(n) == 65535) break;
                if (this.compareAndSetState(n, 65536 + n)) {
                    if (Sync.sharedCount(n) == 0) {
                        this.firstReader = object;
                        this.firstReaderHoldCount = 1;
                    } else if (this.firstReader == object) {
                        ++this.firstReaderHoldCount;
                    } else {
                        holdCounter3 = holdCounter2;
                        if (holdCounter2 == null) {
                            holdCounter3 = this.cachedHoldCounter;
                        }
                        if (holdCounter3 != null && holdCounter3.tid == ReentrantReadWriteLock.getThreadId((Thread)object)) {
                            object = holdCounter3;
                            if (holdCounter3.count == 0) {
                                this.readHolds.set(holdCounter3);
                                object = holdCounter3;
                            }
                        } else {
                            object = (HoldCounter)this.readHolds.get();
                        }
                        ++((HoldCounter)object).count;
                        this.cachedHoldCounter = object;
                    }
                    return 1;
                }
                holdCounter = holdCounter2;
            } while (true);
            throw new Error("Maximum lock count exceeded");
        }

        final int getCount() {
            return this.getState();
        }

        final Thread getOwner() {
            Thread thread = Sync.exclusiveCount(this.getState()) == 0 ? null : this.getExclusiveOwnerThread();
            return thread;
        }

        final int getReadHoldCount() {
            if (this.getReadLockCount() == 0) {
                return 0;
            }
            Thread thread = Thread.currentThread();
            if (this.firstReader == thread) {
                return this.firstReaderHoldCount;
            }
            HoldCounter holdCounter = this.cachedHoldCounter;
            if (holdCounter != null && holdCounter.tid == ReentrantReadWriteLock.getThreadId(thread)) {
                return holdCounter.count;
            }
            int n = ((HoldCounter)this.readHolds.get()).count;
            if (n == 0) {
                this.readHolds.remove();
            }
            return n;
        }

        final int getReadLockCount() {
            return Sync.sharedCount(this.getState());
        }

        final int getWriteHoldCount() {
            int n = this.isHeldExclusively() ? Sync.exclusiveCount(this.getState()) : 0;
            return n;
        }

        @Override
        protected final boolean isHeldExclusively() {
            boolean bl = this.getExclusiveOwnerThread() == Thread.currentThread();
            return bl;
        }

        final boolean isWriteLocked() {
            boolean bl = Sync.exclusiveCount(this.getState()) != 0;
            return bl;
        }

        final AbstractQueuedSynchronizer.ConditionObject newCondition() {
            return new AbstractQueuedSynchronizer.ConditionObject(this);
        }

        abstract boolean readerShouldBlock();

        @Override
        protected final boolean tryAcquire(int n) {
            Thread thread = Thread.currentThread();
            int n2 = this.getState();
            int n3 = Sync.exclusiveCount(n2);
            if (n2 != 0) {
                if (n3 != 0 && thread == this.getExclusiveOwnerThread()) {
                    if (Sync.exclusiveCount(n) + n3 <= 65535) {
                        this.setState(n2 + n);
                        return true;
                    }
                    throw new Error("Maximum lock count exceeded");
                }
                return false;
            }
            if (!this.writerShouldBlock() && this.compareAndSetState(n2, n2 + n)) {
                this.setExclusiveOwnerThread(thread);
                return true;
            }
            return false;
        }

        @Override
        protected final int tryAcquireShared(int n) {
            Object object = Thread.currentThread();
            n = this.getState();
            if (Sync.exclusiveCount(n) != 0 && this.getExclusiveOwnerThread() != object) {
                return -1;
            }
            int n2 = Sync.sharedCount(n);
            if (!this.readerShouldBlock() && n2 < 65535 && this.compareAndSetState(n, 65536 + n)) {
                if (n2 == 0) {
                    this.firstReader = object;
                    this.firstReaderHoldCount = 1;
                } else if (this.firstReader == object) {
                    ++this.firstReaderHoldCount;
                } else {
                    HoldCounter holdCounter = this.cachedHoldCounter;
                    if (holdCounter != null && holdCounter.tid == ReentrantReadWriteLock.getThreadId((Thread)object)) {
                        object = holdCounter;
                        if (holdCounter.count == 0) {
                            this.readHolds.set(holdCounter);
                            object = holdCounter;
                        }
                    } else {
                        holdCounter = (HoldCounter)this.readHolds.get();
                        object = holdCounter;
                        this.cachedHoldCounter = holdCounter;
                    }
                    ++((HoldCounter)object).count;
                }
                return 1;
            }
            return this.fullTryAcquireShared((Thread)object);
        }

        final boolean tryReadLock() {
            block9 : {
                int n;
                int n2;
                Object object = Thread.currentThread();
                do {
                    if (Sync.exclusiveCount(n2 = this.getState()) != 0 && this.getExclusiveOwnerThread() != object) {
                        return false;
                    }
                    n = Sync.sharedCount(n2);
                    if (n == 65535) break block9;
                } while (!this.compareAndSetState(n2, 65536 + n2));
                if (n == 0) {
                    this.firstReader = object;
                    this.firstReaderHoldCount = 1;
                } else if (this.firstReader == object) {
                    ++this.firstReaderHoldCount;
                } else {
                    HoldCounter holdCounter = this.cachedHoldCounter;
                    if (holdCounter != null && holdCounter.tid == ReentrantReadWriteLock.getThreadId((Thread)object)) {
                        object = holdCounter;
                        if (holdCounter.count == 0) {
                            this.readHolds.set(holdCounter);
                            object = holdCounter;
                        }
                    } else {
                        holdCounter = (HoldCounter)this.readHolds.get();
                        object = holdCounter;
                        this.cachedHoldCounter = holdCounter;
                    }
                    ++((HoldCounter)object).count;
                }
                return true;
            }
            throw new Error("Maximum lock count exceeded");
        }

        @Override
        protected final boolean tryRelease(int n) {
            if (this.isHeldExclusively()) {
                n = this.getState() - n;
                boolean bl = Sync.exclusiveCount(n) == 0;
                if (bl) {
                    this.setExclusiveOwnerThread(null);
                }
                this.setState(n);
                return bl;
            }
            throw new IllegalMonitorStateException();
        }

        @Override
        protected final boolean tryReleaseShared(int n) {
            int n2;
            boolean bl;
            block10 : {
                Object object;
                block12 : {
                    block11 : {
                        Thread thread;
                        block9 : {
                            thread = Thread.currentThread();
                            object = this.firstReader;
                            bl = true;
                            if (object != thread) break block9;
                            n = this.firstReaderHoldCount;
                            if (n == 1) {
                                this.firstReader = null;
                            } else {
                                this.firstReaderHoldCount = n - 1;
                            }
                            break block10;
                        }
                        HoldCounter holdCounter = this.cachedHoldCounter;
                        if (holdCounter == null) break block11;
                        object = holdCounter;
                        if (holdCounter.tid == ReentrantReadWriteLock.getThreadId(thread)) break block12;
                    }
                    object = (HoldCounter)this.readHolds.get();
                }
                if ((n = ((HoldCounter)object).count) <= 1) {
                    this.readHolds.remove();
                    if (n <= 0) {
                        throw this.unmatchedUnlockException();
                    }
                }
                --((HoldCounter)object).count;
            }
            while (!this.compareAndSetState(n2 = this.getState(), n = n2 - 65536)) {
            }
            if (n != 0) {
                bl = false;
            }
            return bl;
        }

        final boolean tryWriteLock() {
            Thread thread = Thread.currentThread();
            int n = this.getState();
            if (n != 0) {
                int n2 = Sync.exclusiveCount(n);
                if (n2 != 0 && thread == this.getExclusiveOwnerThread()) {
                    if (n2 == 65535) {
                        throw new Error("Maximum lock count exceeded");
                    }
                } else {
                    return false;
                }
            }
            if (!this.compareAndSetState(n, n + 1)) {
                return false;
            }
            this.setExclusiveOwnerThread(thread);
            return true;
        }

        abstract boolean writerShouldBlock();

        static final class HoldCounter {
            int count;
            final long tid = ReentrantReadWriteLock.getThreadId(Thread.currentThread());

            HoldCounter() {
            }
        }

        static final class ThreadLocalHoldCounter
        extends ThreadLocal<HoldCounter> {
            ThreadLocalHoldCounter() {
            }

            @Override
            public HoldCounter initialValue() {
                return new HoldCounter();
            }
        }

    }

    public static class WriteLock
    implements Lock,
    Serializable {
        private static final long serialVersionUID = -4992448646407690164L;
        private final Sync sync;

        protected WriteLock(ReentrantReadWriteLock reentrantReadWriteLock) {
            this.sync = reentrantReadWriteLock.sync;
        }

        public int getHoldCount() {
            return this.sync.getWriteHoldCount();
        }

        public boolean isHeldByCurrentThread() {
            return this.sync.isHeldExclusively();
        }

        @Override
        public void lock() {
            this.sync.acquire(1);
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
            Object object = this.sync.getOwner();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            if (object == null) {
                object = "[Unlocked]";
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("[Locked by thread ");
                stringBuilder2.append(((Thread)object).getName());
                stringBuilder2.append("]");
                object = stringBuilder2.toString();
            }
            stringBuilder.append((String)object);
            return stringBuilder.toString();
        }

        @Override
        public boolean tryLock() {
            return this.sync.tryWriteLock();
        }

        @Override
        public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
            return this.sync.tryAcquireNanos(1, timeUnit.toNanos(l));
        }

        @Override
        public void unlock() {
            this.sync.release(1);
        }
    }

}

