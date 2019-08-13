/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ScheduledThreadPoolExecutor
extends ThreadPoolExecutor
implements ScheduledExecutorService {
    private static final long DEFAULT_KEEPALIVE_MILLIS = 10L;
    private static final AtomicLong sequencer = new AtomicLong();
    private volatile boolean continueExistingPeriodicTasksAfterShutdown;
    private volatile boolean executeExistingDelayedTasksAfterShutdown = true;
    volatile boolean removeOnCancel;

    public ScheduledThreadPoolExecutor(int n) {
        super(n, Integer.MAX_VALUE, 10L, TimeUnit.MILLISECONDS, new DelayedWorkQueue());
    }

    public ScheduledThreadPoolExecutor(int n, RejectedExecutionHandler rejectedExecutionHandler) {
        super(n, Integer.MAX_VALUE, 10L, TimeUnit.MILLISECONDS, (BlockingQueue<Runnable>)new DelayedWorkQueue(), rejectedExecutionHandler);
    }

    public ScheduledThreadPoolExecutor(int n, ThreadFactory threadFactory) {
        super(n, Integer.MAX_VALUE, 10L, TimeUnit.MILLISECONDS, (BlockingQueue<Runnable>)new DelayedWorkQueue(), threadFactory);
    }

    public ScheduledThreadPoolExecutor(int n, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        super(n, Integer.MAX_VALUE, 10L, TimeUnit.MILLISECONDS, new DelayedWorkQueue(), threadFactory, rejectedExecutionHandler);
    }

    private void delayedExecute(RunnableScheduledFuture<?> runnableScheduledFuture) {
        if (this.isShutdown()) {
            this.reject(runnableScheduledFuture);
        } else {
            super.getQueue().add(runnableScheduledFuture);
            if (this.isShutdown() && !this.canRunInCurrentRunState(runnableScheduledFuture.isPeriodic()) && this.remove(runnableScheduledFuture)) {
                runnableScheduledFuture.cancel(false);
            } else {
                this.ensurePrestart();
            }
        }
    }

    private long overflowFree(long l) {
        Delayed delayed = (Delayed)super.getQueue().peek();
        long l2 = l;
        if (delayed != null) {
            long l3 = delayed.getDelay(TimeUnit.NANOSECONDS);
            l2 = l;
            if (l3 < 0L) {
                l2 = l;
                if (l - l3 < 0L) {
                    l2 = l3 + Long.MAX_VALUE;
                }
            }
        }
        return l2;
    }

    private long triggerTime(long l, TimeUnit timeUnit) {
        block0 : {
            long l2 = 0L;
            if (l >= 0L) break block0;
            l = l2;
        }
        return this.triggerTime(timeUnit.toNanos(l));
    }

    boolean canRunInCurrentRunState(boolean bl) {
        bl = bl ? this.continueExistingPeriodicTasksAfterShutdown : this.executeExistingDelayedTasksAfterShutdown;
        return this.isRunningOrShutdown(bl);
    }

    protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable, RunnableScheduledFuture<V> runnableScheduledFuture) {
        return runnableScheduledFuture;
    }

    protected <V> RunnableScheduledFuture<V> decorateTask(Callable<V> callable, RunnableScheduledFuture<V> runnableScheduledFuture) {
        return runnableScheduledFuture;
    }

    @Override
    public void execute(Runnable runnable) {
        this.schedule(runnable, 0L, TimeUnit.NANOSECONDS);
    }

    public boolean getContinueExistingPeriodicTasksAfterShutdownPolicy() {
        return this.continueExistingPeriodicTasksAfterShutdown;
    }

    public boolean getExecuteExistingDelayedTasksAfterShutdownPolicy() {
        return this.executeExistingDelayedTasksAfterShutdown;
    }

    @Override
    public BlockingQueue<Runnable> getQueue() {
        return super.getQueue();
    }

    public boolean getRemoveOnCancelPolicy() {
        return this.removeOnCancel;
    }

    @Override
    void onShutdown() {
        BlockingQueue<Runnable> blockingQueue = super.getQueue();
        boolean bl = this.getExecuteExistingDelayedTasksAfterShutdownPolicy();
        boolean bl2 = this.getContinueExistingPeriodicTasksAfterShutdownPolicy();
        if (!bl && !bl2) {
            for (Object object : blockingQueue.toArray()) {
                if (!(object instanceof RunnableScheduledFuture)) continue;
                ((RunnableScheduledFuture)object).cancel(false);
            }
            blockingQueue.clear();
        } else {
            for (Object object : blockingQueue.toArray()) {
                if (!(object instanceof RunnableScheduledFuture) || !(!(object = (RunnableScheduledFuture)object).isPeriodic() ? !bl : !bl2) && !object.isCancelled() || !blockingQueue.remove(object)) continue;
                object.cancel(false);
            }
        }
        this.tryTerminate();
    }

    void reExecutePeriodic(RunnableScheduledFuture<?> runnableScheduledFuture) {
        if (this.canRunInCurrentRunState(true)) {
            super.getQueue().add(runnableScheduledFuture);
            if (!this.canRunInCurrentRunState(true) && this.remove(runnableScheduledFuture)) {
                runnableScheduledFuture.cancel(false);
            } else {
                this.ensurePrestart();
            }
        }
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable runnableScheduledFuture, long l, TimeUnit timeUnit) {
        if (runnableScheduledFuture != null && timeUnit != null) {
            runnableScheduledFuture = this.decorateTask(runnableScheduledFuture, new ScheduledFutureTask<Object>(runnableScheduledFuture, null, this.triggerTime(l, timeUnit), sequencer.getAndIncrement()));
            this.delayedExecute(runnableScheduledFuture);
            return runnableScheduledFuture;
        }
        throw new NullPointerException();
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> object, long l, TimeUnit timeUnit) {
        if (object != null && timeUnit != null) {
            object = this.decorateTask((Callable<V>)object, (RunnableScheduledFuture<V>)new ScheduledFutureTask<V>((Callable<V>)object, this.triggerTime(l, timeUnit), sequencer.getAndIncrement()));
            this.delayedExecute((RunnableScheduledFuture<?>)object);
            return object;
        }
        throw new NullPointerException();
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnableScheduledFuture, long l, long l2, TimeUnit object) {
        if (runnableScheduledFuture != null && object != null) {
            if (l2 > 0L) {
                object = new ScheduledFutureTask<Object>(runnableScheduledFuture, null, this.triggerTime(l, (TimeUnit)((Object)object)), object.toNanos(l2), sequencer.getAndIncrement());
                runnableScheduledFuture = this.decorateTask(runnableScheduledFuture, (RunnableScheduledFuture<V>)object);
                ((ScheduledFutureTask)object).outerTask = runnableScheduledFuture;
                this.delayedExecute(runnableScheduledFuture);
                return runnableScheduledFuture;
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnableScheduledFuture, long l, long l2, TimeUnit object) {
        if (runnableScheduledFuture != null && object != null) {
            if (l2 > 0L) {
                object = new ScheduledFutureTask<Object>(runnableScheduledFuture, null, this.triggerTime(l, (TimeUnit)((Object)object)), -object.toNanos(l2), sequencer.getAndIncrement());
                runnableScheduledFuture = this.decorateTask(runnableScheduledFuture, (RunnableScheduledFuture<V>)object);
                ((ScheduledFutureTask)object).outerTask = runnableScheduledFuture;
                this.delayedExecute(runnableScheduledFuture);
                return runnableScheduledFuture;
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    public void setContinueExistingPeriodicTasksAfterShutdownPolicy(boolean bl) {
        this.continueExistingPeriodicTasksAfterShutdown = bl;
        if (!bl && this.isShutdown()) {
            this.onShutdown();
        }
    }

    public void setExecuteExistingDelayedTasksAfterShutdownPolicy(boolean bl) {
        this.executeExistingDelayedTasksAfterShutdown = bl;
        if (!bl && this.isShutdown()) {
            this.onShutdown();
        }
    }

    public void setRemoveOnCancelPolicy(boolean bl) {
        this.removeOnCancel = bl;
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return super.shutdownNow();
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        return this.schedule(runnable, 0L, TimeUnit.NANOSECONDS);
    }

    @Override
    public <T> Future<T> submit(Runnable runnable, T t) {
        return this.schedule(Executors.callable(runnable, t), 0L, TimeUnit.NANOSECONDS);
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        return this.schedule(callable, 0L, TimeUnit.NANOSECONDS);
    }

    long triggerTime(long l) {
        long l2 = System.nanoTime();
        if (l >= 0x3FFFFFFFFFFFFFFFL) {
            l = this.overflowFree(l);
        }
        return l2 + l;
    }

    static class DelayedWorkQueue
    extends AbstractQueue<Runnable>
    implements BlockingQueue<Runnable> {
        private static final int INITIAL_CAPACITY = 16;
        private final Condition available = this.lock.newCondition();
        private Thread leader;
        private final ReentrantLock lock = new ReentrantLock();
        private RunnableScheduledFuture<?>[] queue = new RunnableScheduledFuture[16];
        private int size;

        DelayedWorkQueue() {
        }

        private RunnableScheduledFuture<?> finishPoll(RunnableScheduledFuture<?> runnableScheduledFuture) {
            int n;
            this.size = n = this.size - 1;
            RunnableScheduledFuture<?>[] arrrunnableScheduledFuture = this.queue;
            RunnableScheduledFuture<?> runnableScheduledFuture2 = arrrunnableScheduledFuture[n];
            arrrunnableScheduledFuture[n] = null;
            if (n != 0) {
                this.siftDown(0, runnableScheduledFuture2);
            }
            this.setIndex(runnableScheduledFuture, -1);
            return runnableScheduledFuture;
        }

        private void grow() {
            int n;
            int n2 = this.queue.length;
            n2 = n = (n2 >> 1) + n2;
            if (n < 0) {
                n2 = Integer.MAX_VALUE;
            }
            this.queue = Arrays.copyOf(this.queue, n2);
        }

        private int indexOf(Object object) {
            if (object != null) {
                if (object instanceof ScheduledFutureTask) {
                    int n = ((ScheduledFutureTask)object).heapIndex;
                    if (n >= 0 && n < this.size && this.queue[n] == object) {
                        return n;
                    }
                } else {
                    for (int i = 0; i < this.size; ++i) {
                        if (!object.equals(this.queue[i])) continue;
                        return i;
                    }
                }
            }
            return -1;
        }

        private RunnableScheduledFuture<?> peekExpired() {
            RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[0];
            if (runnableScheduledFuture == null || runnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS) > 0L) {
                runnableScheduledFuture = null;
            }
            return runnableScheduledFuture;
        }

        private void setIndex(RunnableScheduledFuture<?> runnableScheduledFuture, int n) {
            if (runnableScheduledFuture instanceof ScheduledFutureTask) {
                ((ScheduledFutureTask)runnableScheduledFuture).heapIndex = n;
            }
        }

        private void siftDown(int n, RunnableScheduledFuture<?> runnableScheduledFuture) {
            int n2 = this.size;
            int n3 = n;
            while (n3 < n2 >>> 1) {
                int n4 = (n3 << 1) + 1;
                RunnableScheduledFuture<?>[] arrrunnableScheduledFuture = this.queue;
                RunnableScheduledFuture<?>[] arrrunnableScheduledFuture2 = arrrunnableScheduledFuture[n4];
                int n5 = n4 + 1;
                n = n4;
                Object object = arrrunnableScheduledFuture2;
                if (n5 < this.size) {
                    n = n4;
                    object = arrrunnableScheduledFuture2;
                    if (arrrunnableScheduledFuture2.compareTo(arrrunnableScheduledFuture[n5]) > 0) {
                        object = this.queue;
                        n = n5;
                        object = object[n5];
                    }
                }
                if (runnableScheduledFuture.compareTo(object) <= 0) break;
                this.queue[n3] = object;
                this.setIndex((RunnableScheduledFuture<?>)object, n3);
                n3 = n;
            }
            this.queue[n3] = runnableScheduledFuture;
            this.setIndex(runnableScheduledFuture, n3);
        }

        private void siftUp(int n, RunnableScheduledFuture<?> runnableScheduledFuture) {
            int n2;
            RunnableScheduledFuture<?> runnableScheduledFuture2;
            while (n > 0 && runnableScheduledFuture.compareTo(runnableScheduledFuture2 = this.queue[n2 = n - 1 >>> 1]) < 0) {
                this.queue[n] = runnableScheduledFuture2;
                this.setIndex(runnableScheduledFuture2, n);
                n = n2;
            }
            this.queue[n] = runnableScheduledFuture;
            this.setIndex(runnableScheduledFuture, n);
        }

        @Override
        public boolean add(Runnable runnable) {
            return this.offer(runnable);
        }

        @Override
        public void clear() {
            ReentrantLock reentrantLock = this.lock;
            reentrantLock.lock();
            int n = 0;
            do {
                block6 : {
                    if (n >= this.size) break;
                    RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[n];
                    if (runnableScheduledFuture == null) break block6;
                    this.queue[n] = null;
                    this.setIndex(runnableScheduledFuture, -1);
                }
                ++n;
            } while (true);
            try {
                this.size = 0;
                return;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
                reentrantLock.unlock();
            }
        }

        @Override
        public boolean contains(Object object) {
            ReentrantLock reentrantLock = this.lock;
            reentrantLock.lock();
            try {
                int n = this.indexOf(object);
                boolean bl = n != -1;
                reentrantLock.unlock();
                return bl;
            }
            catch (Throwable throwable) {
                reentrantLock.unlock();
                throw throwable;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int drainTo(Collection<? super Runnable> collection) {
            if (collection == null) {
                throw new NullPointerException();
            }
            if (collection == this) {
                throw new IllegalArgumentException();
            }
            ReentrantLock reentrantLock = this.lock;
            reentrantLock.lock();
            int n = 0;
            try {
                RunnableScheduledFuture<?> runnableScheduledFuture;
                while ((runnableScheduledFuture = this.peekExpired()) != null) {
                    collection.add(runnableScheduledFuture);
                    this.finishPoll(runnableScheduledFuture);
                    ++n;
                }
                reentrantLock.unlock();
                return n;
            }
            catch (Throwable throwable) {
                reentrantLock.unlock();
                throw throwable;
            }
        }

        @Override
        public int drainTo(Collection<? super Runnable> collection, int n) {
            if (collection != null) {
                if (collection != this) {
                    int n2;
                    if (n <= 0) {
                        return 0;
                    }
                    ReentrantLock reentrantLock = this.lock;
                    reentrantLock.lock();
                    for (n2 = 0; n2 < n; ++n2) {
                        RunnableScheduledFuture<?> runnableScheduledFuture;
                        try {
                            runnableScheduledFuture = this.peekExpired();
                            if (runnableScheduledFuture == null) break;
                        }
                        catch (Throwable throwable) {
                            reentrantLock.unlock();
                            throw throwable;
                        }
                        collection.add(runnableScheduledFuture);
                        this.finishPoll(runnableScheduledFuture);
                    }
                    reentrantLock.unlock();
                    return n2;
                }
                throw new IllegalArgumentException();
            }
            throw new NullPointerException();
        }

        @Override
        public boolean isEmpty() {
            boolean bl = this.size() == 0;
            return bl;
        }

        @Override
        public Iterator<Runnable> iterator() {
            return new Itr(Arrays.copyOf(this.queue, this.size));
        }

        @Override
        public boolean offer(Runnable object) {
            if (object != null) {
                RunnableScheduledFuture runnableScheduledFuture = (RunnableScheduledFuture)object;
                object = this.lock;
                ((ReentrantLock)object).lock();
                try {
                    int n = this.size;
                    if (n >= this.queue.length) {
                        this.grow();
                    }
                    this.size = n + 1;
                    if (n == 0) {
                        this.queue[0] = runnableScheduledFuture;
                        this.setIndex(runnableScheduledFuture, 0);
                    } else {
                        this.siftUp(n, runnableScheduledFuture);
                    }
                    if (this.queue[0] == runnableScheduledFuture) {
                        this.leader = null;
                        this.available.signal();
                    }
                    return true;
                }
                finally {
                    ((ReentrantLock)object).unlock();
                }
            }
            throw new NullPointerException();
        }

        @Override
        public boolean offer(Runnable runnable, long l, TimeUnit timeUnit) {
            return this.offer(runnable);
        }

        @Override
        public RunnableScheduledFuture<?> peek() {
            ReentrantLock reentrantLock = this.lock;
            reentrantLock.lock();
            try {
                RunnableScheduledFuture<?> runnableScheduledFuture = this.queue[0];
                return runnableScheduledFuture;
            }
            finally {
                reentrantLock.unlock();
            }
        }

        @Override
        public RunnableScheduledFuture<?> poll() {
            ReentrantLock reentrantLock;
            RunnableScheduledFuture<?> runnableScheduledFuture;
            block4 : {
                block3 : {
                    reentrantLock = this.lock;
                    reentrantLock.lock();
                    try {
                        runnableScheduledFuture = this.queue[0];
                        if (runnableScheduledFuture == null) break block3;
                    }
                    catch (Throwable throwable) {
                        reentrantLock.unlock();
                        throw throwable;
                    }
                    if (runnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS) > 0L) break block3;
                    runnableScheduledFuture = this.finishPoll(runnableScheduledFuture);
                    break block4;
                }
                runnableScheduledFuture = null;
            }
            reentrantLock.unlock();
            return runnableScheduledFuture;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public RunnableScheduledFuture<?> poll(long var1_1, TimeUnit var3_2) throws InterruptedException {
            var1_1 = var3_2.toNanos(var1_1);
            var3_2 = this.lock;
            var3_2.lockInterruptibly();
            do {
                var4_3 /* !! */  = this.queue[0];
                if (var4_3 /* !! */  != null) ** GOTO lbl22
                if (var1_1 > 0L) break block13;
                break;
            } while (true);
            catch (Throwable var4_4) {
                if (this.leader == null && this.queue[0] != null) {
                    this.available.signal();
                }
                var3_2.unlock();
                throw var4_4;
            }
            {
                block14 : {
                    block13 : {
                        if (this.leader == null && this.queue[0] != null) {
                            this.available.signal();
                        }
                        var3_2.unlock();
                        return null;
                    }
                    var1_1 = this.available.awaitNanos(var1_1);
                    continue;
lbl22: // 1 sources:
                    var5_5 = var4_3 /* !! */ .getDelay(TimeUnit.NANOSECONDS);
                    if (var5_5 > 0L) break block14;
                    var4_3 /* !! */  = this.finishPoll(var4_3 /* !! */ );
                    if (this.leader == null && this.queue[0] != null) {
                        this.available.signal();
                    }
                    var3_2.unlock();
                    return var4_3 /* !! */ ;
                }
                if (var1_1 <= 0L) {
                    if (this.leader == null && this.queue[0] != null) {
                        this.available.signal();
                    }
                    var3_2.unlock();
                    return null;
                }
                if (var1_1 < var5_5) ** GOTO lbl52
                if (this.leader != null) ** GOTO lbl52
                var4_3 /* !! */  = Thread.currentThread();
                this.leader = var4_3 /* !! */ ;
                var7_6 = this.available.awaitNanos(var5_5);
                {
                    catch (Throwable var9_7) {
                        if (this.leader != var4_3 /* !! */ ) throw var9_7;
                        this.leader = null;
                        throw var9_7;
                    }
                }
                var1_1 = var7_6 = var1_1 - (var5_5 - var7_6);
                if (this.leader != var4_3 /* !! */ ) continue;
                this.leader = null;
                var1_1 = var7_6;
                continue;
lbl52: // 2 sources:
                var1_1 = this.available.awaitNanos(var1_1);
                continue;
            }
        }

        @Override
        public void put(Runnable runnable) {
            this.offer(runnable);
        }

        @Override
        public int remainingCapacity() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean remove(Object runnableScheduledFuture) {
            ReentrantLock reentrantLock;
            block6 : {
                int n;
                int n2;
                block5 : {
                    reentrantLock = this.lock;
                    reentrantLock.lock();
                    n = this.indexOf(runnableScheduledFuture);
                    if (n >= 0) break block5;
                    reentrantLock.unlock();
                    return false;
                }
                this.setIndex(this.queue[n], -1);
                this.size = n2 = this.size - 1;
                runnableScheduledFuture = this.queue[n2];
                this.queue[n2] = null;
                if (n2 == n) break block6;
                this.siftDown(n, runnableScheduledFuture);
                if (this.queue[n] != runnableScheduledFuture) break block6;
                this.siftUp(n, runnableScheduledFuture);
            }
            return true;
            finally {
                reentrantLock.unlock();
            }
        }

        @Override
        public int size() {
            ReentrantLock reentrantLock = this.lock;
            reentrantLock.lock();
            try {
                int n = this.size;
                return n;
            }
            finally {
                reentrantLock.unlock();
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public RunnableScheduledFuture<?> take() throws InterruptedException {
            Thread thread;
            ReentrantLock reentrantLock = this.lock;
            reentrantLock.lockInterruptibly();
            do {
                long l;
                block11 : {
                    RunnableScheduledFuture<?> runnableScheduledFuture;
                    while ((runnableScheduledFuture = this.queue[0]) == null) {
                        this.available.await();
                    }
                    l = runnableScheduledFuture.getDelay(TimeUnit.NANOSECONDS);
                    if (l > 0L) break block11;
                    runnableScheduledFuture = this.finishPoll(runnableScheduledFuture);
                    if (this.leader == null && this.queue[0] != null) {
                        this.available.signal();
                    }
                    reentrantLock.unlock();
                    return runnableScheduledFuture;
                }
                if (this.leader != null) {
                    this.available.await();
                    continue;
                }
                this.leader = thread = Thread.currentThread();
                this.available.awaitNanos(l);
                break;
            } while (true);
            {
                catch (Throwable throwable) {
                    if (this.leader != thread) throw throwable;
                    this.leader = null;
                    throw throwable;
                }
            }
            {
                if (this.leader != thread) continue;
                this.leader = null;
                continue;
            }
            catch (Throwable throwable) {
                if (this.leader == null && this.queue[0] != null) {
                    this.available.signal();
                }
                reentrantLock.unlock();
                throw throwable;
            }
        }

        @Override
        public Object[] toArray() {
            ReentrantLock reentrantLock = this.lock;
            reentrantLock.lock();
            try {
                Object[] arrobject = Arrays.copyOf(this.queue, this.size, Object[].class);
                return arrobject;
            }
            finally {
                reentrantLock.unlock();
            }
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            ReentrantLock reentrantLock = this.lock;
            reentrantLock.lock();
            try {
                if (arrT.length < this.size) {
                    arrT = Arrays.copyOf(this.queue, this.size, arrT.getClass());
                    return arrT;
                }
                System.arraycopy(this.queue, 0, arrT, 0, this.size);
                if (arrT.length > this.size) {
                    arrT[this.size] = null;
                }
                return arrT;
            }
            finally {
                reentrantLock.unlock();
            }
        }

        private class Itr
        implements Iterator<Runnable> {
            final RunnableScheduledFuture<?>[] array;
            int cursor;
            int lastRet = -1;

            Itr(RunnableScheduledFuture<?>[] arrrunnableScheduledFuture) {
                this.array = arrrunnableScheduledFuture;
            }

            @Override
            public boolean hasNext() {
                boolean bl = this.cursor < this.array.length;
                return bl;
            }

            @Override
            public Runnable next() {
                int n = this.cursor;
                RunnableScheduledFuture<?>[] arrrunnableScheduledFuture = this.array;
                if (n < arrrunnableScheduledFuture.length) {
                    this.lastRet = n;
                    this.cursor = n + 1;
                    return arrrunnableScheduledFuture[n];
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                int n = this.lastRet;
                if (n >= 0) {
                    DelayedWorkQueue.this.remove(this.array[n]);
                    this.lastRet = -1;
                    return;
                }
                throw new IllegalStateException();
            }
        }

    }

    private class ScheduledFutureTask<V>
    extends FutureTask<V>
    implements RunnableScheduledFuture<V> {
        int heapIndex;
        RunnableScheduledFuture<V> outerTask;
        private final long period;
        private final long sequenceNumber;
        private volatile long time;

        ScheduledFutureTask(Runnable runnable, V v, long l, long l2) {
            super(runnable, v);
            this.outerTask = this;
            this.time = l;
            this.period = 0L;
            this.sequenceNumber = l2;
        }

        ScheduledFutureTask(Runnable runnable, V v, long l, long l2, long l3) {
            super(runnable, v);
            this.outerTask = this;
            this.time = l;
            this.period = l2;
            this.sequenceNumber = l3;
        }

        ScheduledFutureTask(Callable<V> callable, long l, long l2) {
            super(callable);
            this.outerTask = this;
            this.time = l;
            this.period = 0L;
            this.sequenceNumber = l2;
        }

        private void setNextRunTime() {
            long l = this.period;
            this.time = l > 0L ? (this.time += l) : ScheduledThreadPoolExecutor.this.triggerTime(-l);
        }

        @Override
        public boolean cancel(boolean bl) {
            if ((bl = super.cancel(bl)) && ScheduledThreadPoolExecutor.this.removeOnCancel && this.heapIndex >= 0) {
                ScheduledThreadPoolExecutor.this.remove(this);
            }
            return bl;
        }

        @Override
        public int compareTo(Delayed delayed) {
            int n = 0;
            if (delayed == this) {
                return 0;
            }
            if (delayed instanceof ScheduledFutureTask) {
                delayed = (ScheduledFutureTask)delayed;
                long l = this.time - ((ScheduledFutureTask)delayed).time;
                if (l < 0L) {
                    return -1;
                }
                if (l > 0L) {
                    return 1;
                }
                if (this.sequenceNumber < ((ScheduledFutureTask)delayed).sequenceNumber) {
                    return -1;
                }
                return 1;
            }
            long l = this.getDelay(TimeUnit.NANOSECONDS) - delayed.getDelay(TimeUnit.NANOSECONDS);
            if (l < 0L) {
                n = -1;
            } else if (l > 0L) {
                n = 1;
            }
            return n;
        }

        @Override
        public long getDelay(TimeUnit timeUnit) {
            return timeUnit.convert(this.time - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        @Override
        public boolean isPeriodic() {
            boolean bl = this.period != 0L;
            return bl;
        }

        @Override
        public void run() {
            boolean bl = this.isPeriodic();
            if (!ScheduledThreadPoolExecutor.this.canRunInCurrentRunState(bl)) {
                this.cancel(false);
            } else if (!bl) {
                super.run();
            } else if (super.runAndReset()) {
                this.setNextRunTime();
                ScheduledThreadPoolExecutor.this.reExecutePeriodic(this.outerTask);
            }
        }
    }

}

