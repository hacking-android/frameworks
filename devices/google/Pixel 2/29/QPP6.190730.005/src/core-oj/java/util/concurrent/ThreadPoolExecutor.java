/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.ReachabilitySensitive
 */
package java.util.concurrent;

import dalvik.annotation.optimization.ReachabilitySensitive;
import java.io.Serializable;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutor
extends AbstractExecutorService {
    private static final int CAPACITY = 536870911;
    private static final int COUNT_BITS = 29;
    private static final boolean ONLY_ONE = true;
    private static final int RUNNING = -536870912;
    private static final int SHUTDOWN = 0;
    private static final int STOP = 536870912;
    private static final int TERMINATED = 1610612736;
    private static final int TIDYING = 1073741824;
    private static final RejectedExecutionHandler defaultHandler = new AbortPolicy();
    private static final RuntimePermission shutdownPerm = new RuntimePermission("modifyThread");
    private volatile boolean allowCoreThreadTimeOut;
    private long completedTaskCount;
    private volatile int corePoolSize;
    @ReachabilitySensitive
    private final AtomicInteger ctl = new AtomicInteger(ThreadPoolExecutor.ctlOf(-536870912, 0));
    private volatile RejectedExecutionHandler handler;
    private volatile long keepAliveTime;
    private int largestPoolSize;
    private final ReentrantLock mainLock = new ReentrantLock();
    private volatile int maximumPoolSize;
    private final Condition termination = this.mainLock.newCondition();
    private volatile ThreadFactory threadFactory;
    private final BlockingQueue<Runnable> workQueue;
    @ReachabilitySensitive
    private final HashSet<Worker> workers = new HashSet();

    public ThreadPoolExecutor(int n, int n2, long l, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        this(n, n2, l, timeUnit, blockingQueue, Executors.defaultThreadFactory(), defaultHandler);
    }

    public ThreadPoolExecutor(int n, int n2, long l, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, RejectedExecutionHandler rejectedExecutionHandler) {
        this(n, n2, l, timeUnit, blockingQueue, Executors.defaultThreadFactory(), rejectedExecutionHandler);
    }

    public ThreadPoolExecutor(int n, int n2, long l, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, ThreadFactory threadFactory) {
        this(n, n2, l, timeUnit, blockingQueue, threadFactory, defaultHandler);
    }

    public ThreadPoolExecutor(int n, int n2, long l, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue, ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        if (n >= 0 && n2 > 0 && n2 >= n && l >= 0L) {
            if (blockingQueue != null && threadFactory != null && rejectedExecutionHandler != null) {
                this.corePoolSize = n;
                this.maximumPoolSize = n2;
                this.workQueue = blockingQueue;
                this.keepAliveTime = timeUnit.toNanos(l);
                this.threadFactory = threadFactory;
                this.handler = rejectedExecutionHandler;
                return;
            }
            throw new NullPointerException();
        }
        throw new IllegalArgumentException();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean addWorker(Runnable object, boolean bl) {
        do {
            n2 = this.ctl.get();
            n4 = ThreadPoolExecutor.runStateOf(n2);
            n = n2;
            if (n4 >= 0) {
                if (n4 != 0) return false;
                if (object != null) return false;
                n = n2;
                if (this.workQueue.isEmpty()) {
                    return false;
                }
            }
            do {
                block15 : {
                    block17 : {
                        block16 : {
                            if ((n3 = ThreadPoolExecutor.workerCountOf(n)) >= 536870911) return false;
                            n2 = bl != false ? this.corePoolSize : this.maximumPoolSize;
                            if (n3 >= n2) {
                                return false;
                            }
                            if (!this.compareAndIncrementWorkerCount(n)) continue;
                            bl2 = false;
                            n2 = 0;
                            worker2 = worker = null;
                            worker2 = worker;
                            serializable = new Worker((Runnable)object);
                            worker2 = worker = serializable;
                            thread = worker.thread;
                            bl = bl2;
                            if (thread == null) ** GOTO lbl70
                            worker2 = worker;
                            serializable = this.mainLock;
                            worker2 = worker;
                            serializable.lock();
                            {
                                catch (Throwable throwable) {
                                    if (false != false) throw throwable;
                                    this.addWorkerFailed(worker2);
                                    throw throwable;
                                }
                            }
                            n4 = ThreadPoolExecutor.runStateOf(this.ctl.get());
                            if (n4 < 0) break block16;
                            n = n2;
                            if (n4 != 0) break block17;
                            n = n2;
                            if (object != null) break block17;
                        }
                        if (thread.isAlive()) break block15;
                        this.workers.add(worker);
                        n = this.workers.size();
                        if (n > this.largestPoolSize) {
                            this.largestPoolSize = n;
                        }
                        n = 1;
                    }
                    worker2 = worker;
                    serializable.unlock();
                    bl = bl2;
                    if (n != 0) {
                        worker2 = worker;
                        thread.start();
                        bl = true;
                    }
                    ** GOTO lbl70
                }
                try {
                    object = new IllegalThreadStateException();
                    throw object;
                }
                catch (Throwable throwable) {
                    worker2 = worker;
                    serializable.unlock();
                    worker2 = worker;
                    throw throwable;
lbl70: // 2 sources:
                    if (bl != false) return bl;
                    this.addWorkerFailed(worker);
                    return bl;
                }
            } while (ThreadPoolExecutor.runStateOf(n = this.ctl.get()) == n4);
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void addWorkerFailed(Worker var1_1) {
        var2_3 = this.mainLock;
        var2_3.lock();
        if (var1_1 == null) ** GOTO lbl7
        try {
            this.workers.remove(var1_1);
lbl7: // 2 sources:
            this.decrementWorkerCount();
            this.tryTerminate();
            return;
        }
        finally {
            var2_3.unlock();
        }
    }

    private void advanceRunState(int n) {
        int n2;
        while (!ThreadPoolExecutor.runStateAtLeast(n2 = this.ctl.get(), n) && !this.ctl.compareAndSet(n2, ThreadPoolExecutor.ctlOf(n, ThreadPoolExecutor.workerCountOf(n2)))) {
        }
    }

    private void checkShutdownAccess() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(shutdownPerm);
            ReentrantLock reentrantLock = this.mainLock;
            reentrantLock.lock();
            try {
                Iterator<Worker> iterator = this.workers.iterator();
                while (iterator.hasNext()) {
                    securityManager.checkAccess(iterator.next().thread);
                }
            }
            finally {
                reentrantLock.unlock();
            }
        }
    }

    private boolean compareAndDecrementWorkerCount(int n) {
        return this.ctl.compareAndSet(n, n - 1);
    }

    private boolean compareAndIncrementWorkerCount(int n) {
        return this.ctl.compareAndSet(n, n + 1);
    }

    private static int ctlOf(int n, int n2) {
        return n | n2;
    }

    private void decrementWorkerCount() {
        while (!this.compareAndDecrementWorkerCount(this.ctl.get())) {
        }
    }

    private List<Runnable> drainQueue() {
        BlockingQueue<Runnable> blockingQueue = this.workQueue;
        ArrayList<Runnable> arrayList = new ArrayList<Runnable>();
        blockingQueue.drainTo(arrayList);
        if (!blockingQueue.isEmpty()) {
            for (Runnable runnable : blockingQueue.toArray(new Runnable[0])) {
                if (!blockingQueue.remove(runnable)) continue;
                arrayList.add(runnable);
            }
        }
        return arrayList;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private Runnable getTask() {
        var1_1 = false;
        do {
            block5 : {
                if ((var3_3 = ThreadPoolExecutor.runStateOf(var2_2 = this.ctl.get())) >= 0 && (var3_3 >= 536870912 || this.workQueue.isEmpty())) {
                    this.decrementWorkerCount();
                    return null;
                }
                var4_4 = ThreadPoolExecutor.workerCountOf(var2_2);
                var3_3 = !this.allowCoreThreadTimeOut && var4_4 <= this.corePoolSize ? 0 : 1;
                if ((var4_4 > this.maximumPoolSize || var3_3 != 0 && var1_1) && (var4_4 > 1 || this.workQueue.isEmpty())) break block5;
                if (var3_3 == 0) ** GOTO lbl13
                try {
                    block6 : {
                        var5_5 = this.workQueue.poll(this.keepAliveTime, TimeUnit.NANOSECONDS);
                        break block6;
lbl13: // 1 sources:
                        var5_5 = this.workQueue.take();
                    }
                    if (var5_5 != null) {
                        return var5_5;
                    }
                    var1_1 = true;
                }
                catch (InterruptedException var5_6) {
                    var1_1 = false;
                }
                continue;
            }
            if (this.compareAndDecrementWorkerCount(var2_2)) return null;
        } while (true);
    }

    private void interruptIdleWorkers() {
        this.interruptIdleWorkers(false);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void interruptIdleWorkers(boolean bl) {
        Thread thread;
        ReentrantLock reentrantLock = this.mainLock;
        reentrantLock.lock();
        try {
            for (Worker worker : this.workers) {
                boolean bl2;
                thread = worker.thread;
                if (thread.isInterrupted() || !(bl2 = worker.tryLock())) break block7;
            }
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
        {
            block7 : {
                Worker worker;
                thread.interrupt();
                worker.unlock();
                break block7;
                catch (Throwable throwable) {
                    worker.unlock();
                    throw throwable;
                }
                catch (SecurityException securityException) {
                    worker.unlock();
                }
            }
            if (!bl) continue;
        }
        reentrantLock.unlock();
    }

    private void interruptWorkers() {
        ReentrantLock reentrantLock = this.mainLock;
        reentrantLock.lock();
        try {
            Iterator<Worker> iterator = this.workers.iterator();
            while (iterator.hasNext()) {
                iterator.next().interruptIfStarted();
            }
            return;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    private static boolean isRunning(int n) {
        boolean bl = n < 0;
        return bl;
    }

    private void processWorkerExit(Worker worker, boolean bl) {
        if (bl) {
            this.decrementWorkerCount();
        }
        ReentrantLock reentrantLock = this.mainLock;
        reentrantLock.lock();
        this.completedTaskCount += worker.completedTasks;
        this.workers.remove(worker);
        this.tryTerminate();
        int n = this.ctl.get();
        if (ThreadPoolExecutor.runStateLessThan(n, 536870912)) {
            if (!bl) {
                int n2 = this.allowCoreThreadTimeOut ? 0 : this.corePoolSize;
                int n3 = n2;
                if (n2 == 0) {
                    n3 = n2;
                    if (!this.workQueue.isEmpty()) {
                        n3 = 1;
                    }
                }
                if (ThreadPoolExecutor.workerCountOf(n) >= n3) {
                    return;
                }
            }
            this.addWorker(null, false);
        }
        return;
        finally {
            reentrantLock.unlock();
        }
    }

    private static boolean runStateAtLeast(int n, int n2) {
        boolean bl = n >= n2;
        return bl;
    }

    private static boolean runStateLessThan(int n, int n2) {
        boolean bl = n < n2;
        return bl;
    }

    private static int runStateOf(int n) {
        return -536870912 & n;
    }

    private static int workerCountOf(int n) {
        return 536870911 & n;
    }

    protected void afterExecute(Runnable runnable, Throwable throwable) {
    }

    public void allowCoreThreadTimeOut(boolean bl) {
        if (bl && this.keepAliveTime <= 0L) {
            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
        }
        if (bl != this.allowCoreThreadTimeOut) {
            this.allowCoreThreadTimeOut = bl;
            if (bl) {
                this.interruptIdleWorkers();
            }
        }
    }

    public boolean allowsCoreThreadTimeOut() {
        return this.allowCoreThreadTimeOut;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean awaitTermination(long l, TimeUnit object) throws InterruptedException {
        l = ((TimeUnit)((Object)object)).toNanos(l);
        object = this.mainLock;
        ((ReentrantLock)object).lock();
        try {
            boolean bl;
            while (!(bl = ThreadPoolExecutor.runStateAtLeast(this.ctl.get(), 1610612736))) {
                if (l <= 0L) {
                    ((ReentrantLock)object).unlock();
                    return false;
                }
                l = this.termination.awaitNanos(l);
            }
            ((ReentrantLock)object).unlock();
            return true;
        }
        catch (Throwable throwable) {
            ((ReentrantLock)object).unlock();
            throw throwable;
        }
    }

    protected void beforeExecute(Thread thread, Runnable runnable) {
    }

    void ensurePrestart() {
        int n = ThreadPoolExecutor.workerCountOf(this.ctl.get());
        if (n < this.corePoolSize) {
            this.addWorker(null, true);
        } else if (n == 0) {
            this.addWorker(null, false);
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (runnable != null) {
            int n;
            int n2 = n = this.ctl.get();
            if (ThreadPoolExecutor.workerCountOf(n) < this.corePoolSize) {
                if (this.addWorker(runnable, true)) {
                    return;
                }
                n2 = this.ctl.get();
            }
            if (ThreadPoolExecutor.isRunning(n2) && this.workQueue.offer(runnable)) {
                n2 = this.ctl.get();
                if (!ThreadPoolExecutor.isRunning(n2) && this.remove(runnable)) {
                    this.reject(runnable);
                } else if (ThreadPoolExecutor.workerCountOf(n2) == 0) {
                    this.addWorker(null, false);
                }
            } else if (!this.addWorker(runnable, false)) {
                this.reject(runnable);
            }
            return;
        }
        throw new NullPointerException();
    }

    protected void finalize() {
        this.shutdown();
    }

    public int getActiveCount() {
        int n;
        ReentrantLock reentrantLock = this.mainLock;
        reentrantLock.lock();
        int n2 = 0;
        try {
            Iterator<Worker> iterator = this.workers.iterator();
            while (iterator.hasNext()) {
                boolean bl = iterator.next().isLocked();
                n = n2;
                if (!bl) break block3;
            }
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
        {
            block3 : {
                n = n2 + 1;
            }
            n2 = n;
            continue;
        }
        reentrantLock.unlock();
        return n2;
    }

    public long getCompletedTaskCount() {
        long l;
        long l2;
        ReentrantLock reentrantLock = this.mainLock;
        reentrantLock.lock();
        try {
            l = this.completedTaskCount;
            Iterator<Worker> iterator = this.workers.iterator();
            while (iterator.hasNext()) {
                l2 = iterator.next().completedTasks;
            }
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
        {
            l += l2;
            continue;
        }
        reentrantLock.unlock();
        return l;
    }

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public long getKeepAliveTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.keepAliveTime, TimeUnit.NANOSECONDS);
    }

    public int getLargestPoolSize() {
        ReentrantLock reentrantLock = this.mainLock;
        reentrantLock.lock();
        try {
            int n = this.largestPoolSize;
            return n;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    public int getMaximumPoolSize() {
        return this.maximumPoolSize;
    }

    public int getPoolSize() {
        ReentrantLock reentrantLock;
        int n;
        block4 : {
            reentrantLock = this.mainLock;
            reentrantLock.lock();
            if (!ThreadPoolExecutor.runStateAtLeast(this.ctl.get(), 1073741824)) break block4;
            n = 0;
        }
        n = this.workers.size();
        return n;
        finally {
            reentrantLock.unlock();
        }
    }

    public BlockingQueue<Runnable> getQueue() {
        return this.workQueue;
    }

    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return this.handler;
    }

    public long getTaskCount() {
        long l;
        long l2;
        ReentrantLock reentrantLock = this.mainLock;
        reentrantLock.lock();
        try {
            l = this.completedTaskCount;
            for (Worker worker : this.workers) {
                l = l2 = l + worker.completedTasks;
            }
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
        {
            Worker worker;
            if (!worker.isLocked()) continue;
            l = l2 + 1L;
            continue;
        }
        int n = this.workQueue.size();
        l2 = n;
        reentrantLock.unlock();
        return l2 + l;
    }

    public ThreadFactory getThreadFactory() {
        return this.threadFactory;
    }

    final boolean isRunningOrShutdown(boolean bl) {
        int n = ThreadPoolExecutor.runStateOf(this.ctl.get());
        bl = n == -536870912 || n == 0 && bl;
        return bl;
    }

    @Override
    public boolean isShutdown() {
        return ThreadPoolExecutor.isRunning(this.ctl.get()) ^ true;
    }

    @Override
    public boolean isTerminated() {
        return ThreadPoolExecutor.runStateAtLeast(this.ctl.get(), 1610612736);
    }

    public boolean isTerminating() {
        int n = this.ctl.get();
        boolean bl = !ThreadPoolExecutor.isRunning(n) && ThreadPoolExecutor.runStateLessThan(n, 1610612736);
        return bl;
    }

    void onShutdown() {
    }

    public int prestartAllCoreThreads() {
        int n = 0;
        while (this.addWorker(null, true)) {
            ++n;
        }
        return n;
    }

    public boolean prestartCoreThread() {
        int n = ThreadPoolExecutor.workerCountOf(this.ctl.get());
        int n2 = this.corePoolSize;
        boolean bl = true;
        if (n >= n2 || !this.addWorker(null, true)) {
            bl = false;
        }
        return bl;
    }

    public void purge() {
        BlockingQueue<Runnable> blockingQueue = this.workQueue;
        try {
            Iterator iterator = blockingQueue.iterator();
            while (iterator.hasNext()) {
                Runnable runnable = (Runnable)iterator.next();
                if (!(runnable instanceof Future) || !((Future)((Object)runnable)).isCancelled()) continue;
                iterator.remove();
            }
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            for (Object object : blockingQueue.toArray()) {
                if (!(object instanceof Future) || !((Future)object).isCancelled()) continue;
                blockingQueue.remove(object);
            }
        }
        this.tryTerminate();
    }

    final void reject(Runnable runnable) {
        this.handler.rejectedExecution(runnable, this);
    }

    public boolean remove(Runnable runnable) {
        boolean bl = this.workQueue.remove(runnable);
        this.tryTerminate();
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    final void runWorker(Worker var1_1) {
        var2_2 = Thread.currentThread();
        var3_6 = var1_1.firstTask;
        var1_1.firstTask = null;
        var1_1.unlock();
        do {
            block17 : {
                var4_9 = var3_6;
                if (var3_6 != null) ** GOTO lbl14
                var3_6 = this.getTask();
                var4_9 = var3_6;
                if (var3_6 == null) {
                    this.processWorkerExit(var1_1, false);
                    return;
                }
lbl14: // 3 sources:
                var1_1.lock();
                if (!ThreadPoolExecutor.runStateAtLeast(this.ctl.get(), 536870912) && (!Thread.interrupted() || !ThreadPoolExecutor.runStateAtLeast(this.ctl.get(), 536870912)) || var2_2.isInterrupted()) break block17;
                var2_2.interrupt();
            }
            this.beforeExecute((Thread)var2_2, var4_9);
            var4_9.run();
            this.afterExecute(var4_9, null);
            var3_6 = null;
            ++var1_1.completedTasks;
            var1_1.unlock();
            continue;
            break;
        } while (true);
        catch (Throwable var5_10) {
            var3_6 = var2_2 = var5_10;
            try {
                var3_6 = var2_2;
                var6_11 = new Error(var5_10);
                var3_6 = var2_2;
                throw var6_11;
                catch (Error var2_3) {
                    var3_6 = var2_3;
                    throw var2_3;
                }
                catch (RuntimeException var2_4) {
                    var3_6 = var2_4;
                    throw var2_4;
                }
            }
            catch (Throwable var2_5) {
                try {
                    this.afterExecute(var4_9, (Throwable)var3_6);
                    throw var2_5;
                }
                catch (Throwable var3_7) {
                    try {
                        ++var1_1.completedTasks;
                        var1_1.unlock();
                        throw var3_7;
                    }
                    catch (Throwable var3_8) {
                        this.processWorkerExit(var1_1, true);
                        throw var3_8;
                    }
                }
            }
        }
    }

    public void setCorePoolSize(int n) {
        if (n >= 0) {
            int n2 = n - this.corePoolSize;
            this.corePoolSize = n;
            if (ThreadPoolExecutor.workerCountOf(this.ctl.get()) > n) {
                this.interruptIdleWorkers();
            } else if (n2 > 0) {
                for (n = java.lang.Math.min((int)n2, (int)this.workQueue.size()); n > 0 && this.addWorker(null, true) && !this.workQueue.isEmpty(); --n) {
                }
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setKeepAliveTime(long l, TimeUnit timeUnit) {
        if (l >= 0L) {
            if (l == 0L && this.allowsCoreThreadTimeOut()) {
                throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
            }
            l = timeUnit.toNanos(l);
            long l2 = this.keepAliveTime;
            this.keepAliveTime = l;
            if (l - l2 < 0L) {
                this.interruptIdleWorkers();
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setMaximumPoolSize(int n) {
        if (n > 0 && n >= this.corePoolSize) {
            this.maximumPoolSize = n;
            if (ThreadPoolExecutor.workerCountOf(this.ctl.get()) > n) {
                this.interruptIdleWorkers();
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        if (rejectedExecutionHandler != null) {
            this.handler = rejectedExecutionHandler;
            return;
        }
        throw new NullPointerException();
    }

    public void setThreadFactory(ThreadFactory threadFactory) {
        if (threadFactory != null) {
            this.threadFactory = threadFactory;
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public void shutdown() {
        ReentrantLock reentrantLock = this.mainLock;
        reentrantLock.lock();
        try {
            this.checkShutdownAccess();
            this.advanceRunState(0);
            this.interruptIdleWorkers();
            this.onShutdown();
            this.tryTerminate();
            return;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public List<Runnable> shutdownNow() {
        ReentrantLock reentrantLock = this.mainLock;
        reentrantLock.lock();
        try {
            this.checkShutdownAccess();
            this.advanceRunState(536870912);
            this.interruptWorkers();
            List<Runnable> list = this.drainQueue();
            this.tryTerminate();
            return list;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    protected void terminated() {
    }

    public String toString() {
        int n;
        int n2;
        Object object = this.mainLock;
        ((ReentrantLock)object).lock();
        long l = this.completedTaskCount;
        int n3 = 0;
        try {
            n2 = this.workers.size();
            for (Worker worker : this.workers) {
                l += worker.completedTasks;
                boolean bl = worker.isLocked();
                n = n3;
                if (!bl) break block4;
            }
        }
        catch (Throwable throwable) {
            ((ReentrantLock)object).unlock();
            throw throwable;
        }
        {
            block4 : {
                n = n3 + 1;
            }
            n3 = n;
            continue;
        }
        ((ReentrantLock)object).unlock();
        n = this.ctl.get();
        object = ThreadPoolExecutor.runStateLessThan(n, 0) ? "Running" : (ThreadPoolExecutor.runStateAtLeast(n, 1610612736) ? "Terminated" : "Shutting down");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Object.super.toString());
        stringBuilder.append("[");
        stringBuilder.append((String)object);
        stringBuilder.append(", pool size = ");
        stringBuilder.append(n2);
        stringBuilder.append(", active threads = ");
        stringBuilder.append(n3);
        stringBuilder.append(", queued tasks = ");
        stringBuilder.append(this.workQueue.size());
        stringBuilder.append(", completed tasks = ");
        stringBuilder.append(l);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    final void tryTerminate() {
        while (ThreadPoolExecutor.isRunning(n = this.ctl.get()) == false) {
            block10 : {
                if (ThreadPoolExecutor.runStateAtLeast(n, 1073741824) != false) return;
                if (ThreadPoolExecutor.runStateOf(n) == 0 && !this.workQueue.isEmpty()) {
                    return;
                }
                if (ThreadPoolExecutor.workerCountOf(n) != 0) {
                    this.interruptIdleWorkers(true);
                    return;
                }
                reentrantLock = this.mainLock;
                reentrantLock.lock();
                bl = this.ctl.compareAndSet(n, ThreadPoolExecutor.ctlOf(1073741824, 0));
                if (!bl) break block10;
                this.terminated();
                reentrantLock.unlock();
                return;
            }
            reentrantLock.unlock();
        }
        return;
        {
            catch (Throwable throwable) {
                reentrantLock.unlock();
                throw throwable;
            }
        }
        finally {
            ** try [egrp 2[TRYBLOCK] [2 : 91->113)] { 
lbl26: // 1 sources:
            this.ctl.set(ThreadPoolExecutor.ctlOf(1610612736, 0));
            this.termination.signalAll();
        }
    }

    public static class AbortPolicy
    implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Task ");
            stringBuilder.append(runnable.toString());
            stringBuilder.append(" rejected from ");
            stringBuilder.append(threadPoolExecutor.toString());
            throw new RejectedExecutionException(stringBuilder.toString());
        }
    }

    public static class CallerRunsPolicy
    implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            if (!threadPoolExecutor.isShutdown()) {
                runnable.run();
            }
        }
    }

    public static class DiscardOldestPolicy
    implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            if (!threadPoolExecutor.isShutdown()) {
                threadPoolExecutor.getQueue().poll();
                threadPoolExecutor.execute(runnable);
            }
        }
    }

    public static class DiscardPolicy
    implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        }
    }

    private final class Worker
    extends AbstractQueuedSynchronizer
    implements Runnable {
        private static final long serialVersionUID = 6138294804551838833L;
        volatile long completedTasks;
        Runnable firstTask;
        final Thread thread;

        Worker(Runnable runnable) {
            this.setState(-1);
            this.firstTask = runnable;
            this.thread = ThreadPoolExecutor.this.getThreadFactory().newThread(this);
        }

        void interruptIfStarted() {
            Thread thread;
            if (this.getState() >= 0 && (thread = this.thread) != null && !thread.isInterrupted()) {
                try {
                    thread.interrupt();
                }
                catch (SecurityException securityException) {
                    // empty catch block
                }
            }
        }

        @Override
        protected boolean isHeldExclusively() {
            boolean bl = this.getState() != 0;
            return bl;
        }

        public boolean isLocked() {
            return this.isHeldExclusively();
        }

        public void lock() {
            this.acquire(1);
        }

        @Override
        public void run() {
            ThreadPoolExecutor.this.runWorker(this);
        }

        @Override
        protected boolean tryAcquire(int n) {
            if (this.compareAndSetState(0, 1)) {
                this.setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        public boolean tryLock() {
            return this.tryAcquire(1);
        }

        @Override
        protected boolean tryRelease(int n) {
            this.setExclusiveOwnerThread(null);
            this.setState(0);
            return true;
        }

        public void unlock() {
            this.release(1);
        }
    }

}

