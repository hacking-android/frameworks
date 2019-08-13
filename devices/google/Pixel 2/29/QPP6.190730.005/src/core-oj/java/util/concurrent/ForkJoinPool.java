/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.lang.reflect.Field;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import sun.misc.Unsafe;

public class ForkJoinPool
extends AbstractExecutorService {
    private static final int ABASE;
    private static final long AC_MASK = -281474976710656L;
    private static final int AC_SHIFT = 48;
    private static final long AC_UNIT = 0x1000000000000L;
    private static final long ADD_WORKER = 0x800000000000L;
    private static final int ASHIFT;
    private static final int COMMON_MAX_SPARES;
    static final int COMMON_PARALLELISM;
    private static final long CTL;
    private static final int DEFAULT_COMMON_MAX_SPARES = 256;
    static final int EVENMASK = 65534;
    static final int FIFO_QUEUE = Integer.MIN_VALUE;
    private static final long IDLE_TIMEOUT_MS = 2000L;
    static final int IS_OWNED = 1;
    static final int LIFO_QUEUE = 0;
    static final int MAX_CAP = 32767;
    static final int MODE_MASK = -65536;
    static final int POLL_LIMIT = 1023;
    private static final long RUNSTATE;
    private static final int SEED_INCREMENT = -1640531527;
    private static final int SHUTDOWN = Integer.MIN_VALUE;
    static final int SMASK = 65535;
    static final int SPARE_WORKER = 131072;
    private static final long SP_MASK = 0xFFFFFFFFL;
    static final int SQMASK = 126;
    static final int SS_SEQ = 65536;
    private static final int STARTED = 1;
    private static final int STOP = 2;
    private static final long TC_MASK = 0xFFFF00000000L;
    private static final int TC_SHIFT = 32;
    private static final long TC_UNIT = 0x100000000L;
    private static final int TERMINATED = 4;
    private static final long TIMEOUT_SLOP_MS = 20L;
    private static final Unsafe U;
    private static final long UC_MASK = -4294967296L;
    static final int UNREGISTERED = 262144;
    static final int UNSIGNALLED = Integer.MIN_VALUE;
    static final ForkJoinPool common;
    public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory;
    static final RuntimePermission modifyThreadPermission;
    private static int poolNumberSequence;
    AuxState auxState;
    final int config;
    volatile long ctl;
    final ForkJoinWorkerThreadFactory factory;
    volatile int runState;
    final Thread.UncaughtExceptionHandler ueh;
    volatile WorkQueue[] workQueues;
    final String workerNamePrefix;

    static {
        block7 : {
            int n;
            block8 : {
                U = Unsafe.getUnsafe();
                try {
                    CTL = U.objectFieldOffset(ForkJoinPool.class.getDeclaredField("ctl"));
                    RUNSTATE = U.objectFieldOffset(ForkJoinPool.class.getDeclaredField("runState"));
                    ABASE = U.arrayBaseOffset(ForkJoinTask[].class);
                    n = U.arrayIndexScale(ForkJoinTask[].class);
                    if ((n - 1 & n) != 0) break block7;
                }
                catch (ReflectiveOperationException reflectiveOperationException) {
                    throw new Error(reflectiveOperationException);
                }
                ASHIFT = 31 - Integer.numberOfLeadingZeros(n);
                int n2 = 256;
                String string = System.getProperty("java.util.concurrent.ForkJoinPool.common.maximumSpares");
                n = n2;
                if (string == null) break block8;
                try {
                    n = Integer.parseInt(string);
                }
                catch (Exception exception) {
                    n = n2;
                }
            }
            COMMON_MAX_SPARES = n;
            defaultForkJoinWorkerThreadFactory = new DefaultForkJoinWorkerThreadFactory();
            modifyThreadPermission = new RuntimePermission("modifyThread");
            common = AccessController.doPrivileged(new PrivilegedAction<ForkJoinPool>(){

                @Override
                public ForkJoinPool run() {
                    return ForkJoinPool.makeCommonPool();
                }
            });
            COMMON_PARALLELISM = Math.max(ForkJoinPool.common.config & 65535, 1);
            return;
        }
        Error error = new Error("array index scale not a power of two");
        throw error;
    }

    public ForkJoinPool() {
        this(Math.min(32767, Runtime.getRuntime().availableProcessors()), defaultForkJoinWorkerThreadFactory, null, false);
    }

    public ForkJoinPool(int n) {
        this(n, defaultForkJoinWorkerThreadFactory, null, false);
    }

    private ForkJoinPool(int n, ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, int n2, String string) {
        this.workerNamePrefix = string;
        this.factory = forkJoinWorkerThreadFactory;
        this.ueh = uncaughtExceptionHandler;
        this.config = 65535 & n | n2;
        long l = -n;
        this.ctl = l << 48 & -281474976710656L | l << 32 & 0xFFFF00000000L;
    }

    public ForkJoinPool(int n, ForkJoinWorkerThreadFactory object, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, boolean bl) {
        int n2 = ForkJoinPool.checkParallelism(n);
        ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory = ForkJoinPool.checkFactory((ForkJoinWorkerThreadFactory)object);
        n = bl ? Integer.MIN_VALUE : 0;
        object = new StringBuilder();
        ((StringBuilder)object).append("ForkJoinPool-");
        ((StringBuilder)object).append(ForkJoinPool.nextPoolId());
        ((StringBuilder)object).append("-worker-");
        this(n2, forkJoinWorkerThreadFactory, uncaughtExceptionHandler, n, ((StringBuilder)object).toString());
        ForkJoinPool.checkPermission();
    }

    private int awaitWork(WorkQueue workQueue) {
        int n;
        int n2 = n = 0;
        if (workQueue != null) {
            n2 = n;
            if (workQueue.scanState < 0) {
                long l = this.ctl;
                if ((int)(l >> 48) + (this.config & 65535) <= 0) {
                    n2 = this.timedAwaitWork(workQueue, l);
                } else if ((this.runState & 2) != 0) {
                    workQueue.qlock = -1;
                    n2 = -1;
                } else {
                    n2 = n;
                    if (workQueue.scanState < 0) {
                        workQueue.parker = Thread.currentThread();
                        if (workQueue.scanState < 0) {
                            LockSupport.park(this);
                        }
                        workQueue.parker = null;
                        if ((this.runState & 2) != 0) {
                            workQueue.qlock = -1;
                            n2 = -1;
                        } else {
                            n2 = n;
                            if (workQueue.scanState < 0) {
                                Thread.interrupted();
                                n2 = n;
                            }
                        }
                    }
                }
            }
        }
        return n2;
    }

    private static ForkJoinWorkerThreadFactory checkFactory(ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory) {
        if (forkJoinWorkerThreadFactory != null) {
            return forkJoinWorkerThreadFactory;
        }
        throw new NullPointerException();
    }

    private static int checkParallelism(int n) {
        if (n > 0 && n <= 32767) {
            return n;
        }
        throw new IllegalArgumentException();
    }

    private static void checkPermission() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(modifyThreadPermission);
        }
    }

    public static ForkJoinPool commonPool() {
        return common;
    }

    static WorkQueue commonSubmitterQueue() {
        int n;
        Object object = common;
        int n2 = ThreadLocalRandom.getProbe();
        object = object != null && (object = object.workQueues) != null && (n = ((WorkQueue[])object).length) > 0 ? object[n - 1 & n2 & 126] : null;
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean createWorker(boolean bl) {
        Object object;
        Object object2;
        block6 : {
            Object object3 = this.factory;
            Object object4 = null;
            object = null;
            object2 = null;
            if (object3 != null) {
                object = object2;
                try {
                    object = object2 = (object3 = object3.newThread(this));
                    if (object3 != null) {
                        if (bl) {
                            object = object2;
                            object4 = ((ForkJoinWorkerThread)object2).workQueue;
                            if (object4 != null) {
                                object = object2;
                                ((WorkQueue)object4).config |= 131072;
                            }
                        }
                        object = object2;
                        ((Thread)object2).start();
                        return true;
                    }
                }
                catch (Throwable throwable) {
                    break block6;
                }
            }
            object2 = object4;
        }
        this.deregisterWorker((ForkJoinWorkerThread)object, (Throwable)object2);
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private <T> ForkJoinTask<T> externalSubmit(ForkJoinTask<T> forkJoinTask) {
        if (forkJoinTask == null) throw new NullPointerException();
        Object object = Thread.currentThread();
        if (object instanceof ForkJoinWorkerThread) {
            object = (ForkJoinWorkerThread)object;
            if (((ForkJoinWorkerThread)object).pool == this && (object = ((ForkJoinWorkerThread)object).workQueue) != null) {
                ((WorkQueue)object).push(forkJoinTask);
                return forkJoinTask;
            }
        }
        this.externalPush(forkJoinTask);
        return forkJoinTask;
    }

    private WorkQueue findNonEmptyStealQueue() {
        int n;
        int n2 = ThreadLocalRandom.nextSecondarySeed();
        WorkQueue[] arrworkQueue = this.workQueues;
        if (arrworkQueue != null && (n = arrworkQueue.length) > 0) {
            int n3;
            int n4 = n - 1;
            int n5 = n3 = n2 & n4;
            int n6 = 0;
            n = 0;
            do {
                int n7;
                WorkQueue workQueue = arrworkQueue[n5];
                n2 = n;
                if (workQueue != null) {
                    n2 = workQueue.base;
                    if (n2 - workQueue.top < 0) {
                        return workQueue;
                    }
                    n2 = n + n2;
                }
                n5 = n7 = n5 + 1 & n4;
                int n8 = n6;
                n = n2;
                if (n7 == n3) {
                    if (n6 == n2) break;
                    n = 0;
                    n8 = n2;
                }
                n6 = n8;
            } while (true);
        }
        return null;
    }

    public static int getCommonPoolParallelism() {
        return COMMON_PARALLELISM;
    }

    static int getSurplusQueuedTaskCount() {
        Object object = Thread.currentThread();
        boolean bl = object instanceof ForkJoinWorkerThread;
        int n = 0;
        if (bl) {
            Object object2 = (ForkJoinWorkerThread)object;
            object = ((ForkJoinWorkerThread)object2).pool;
            int n2 = ((ForkJoinPool)object).config & 65535;
            object2 = ((ForkJoinWorkerThread)object2).workQueue;
            int n3 = ((WorkQueue)object2).top;
            int n4 = ((WorkQueue)object2).base;
            int n5 = (int)(((ForkJoinPool)object).ctl >> 48) + n2;
            if (n5 <= (n2 >>>= 1)) {
                n = n2 >>> 1;
                n = n5 > n ? 1 : (n5 > (n >>>= 1) ? 2 : (n5 > n >>> 1 ? 4 : 8));
            }
            return n3 - n4 - n;
        }
        return 0;
    }

    private void helpStealer(WorkQueue workQueue, ForkJoinTask<?> forkJoinTask) {
        if (forkJoinTask != null && workQueue != null) {
            ForkJoinTask<?> forkJoinTask2 = workQueue.currentSteal;
            int n = 0;
            block0 : while (workQueue.tryRemoveAndExec(forkJoinTask) && forkJoinTask.status >= 0) {
                int n2;
                block18 : {
                    int n3;
                    WorkQueue[] arrworkQueue = this.workQueues;
                    Object object = arrworkQueue;
                    if (arrworkQueue == null) break;
                    int n4 = n3 = ((WorkQueue[])object).length;
                    if (n3 <= 0) break;
                    int n5 = n4 - 1;
                    n3 = 0;
                    WorkQueue workQueue2 = workQueue;
                    ForkJoinTask<?> forkJoinTask3 = forkJoinTask;
                    block1 : while (forkJoinTask3.status >= 0) {
                        int n6 = workQueue2.hint;
                        int n7 = 0;
                        n2 = n3;
                        n3 = n5;
                        do {
                            WorkQueue workQueue3;
                            if ((workQueue3 = object[n5 = (n7 << 1) + (n6 | 1) & n3]) == null) continue;
                            if (workQueue3.currentSteal == forkJoinTask3) {
                                workQueue2.hint = n5;
                                n7 = n2;
                                arrworkQueue = object;
                                n5 = n3;
                                do {
                                    ForkJoinTask<?> forkJoinTask4;
                                    block19 : {
                                        if (forkJoinTask3.status < 0) {
                                            n2 = n;
                                            break block18;
                                        }
                                        n2 = workQueue3.base;
                                        n3 = n7 + n2;
                                        forkJoinTask4 = workQueue3.currentJoin;
                                        object = null;
                                        Object object2 = workQueue3.array;
                                        if (object2 != null && (n7 = ((ForkJoinTask<?>[])object2).length) > 0) {
                                            long l = n7 - 1 & n2;
                                            n7 = ASHIFT;
                                            object = (ForkJoinTask)U.getObjectVolatile(object2, l = (long)ABASE + (l << n7));
                                            if (object != null) {
                                                n7 = n2 + 1;
                                                if (n2 == workQueue3.base) {
                                                    n2 = n;
                                                    if (workQueue2.currentJoin != forkJoinTask3) break block18;
                                                    n2 = n;
                                                    if (workQueue3.currentSteal != forkJoinTask3) break block18;
                                                    if (forkJoinTask3.status < 0) {
                                                        n2 = n;
                                                        break block18;
                                                    }
                                                    if (U.compareAndSwapObject(object2, l, object, null)) {
                                                        workQueue3.base = n7;
                                                        workQueue.currentSteal = object;
                                                        n2 = workQueue.top;
                                                        do {
                                                            ((ForkJoinTask)object).doExec();
                                                            workQueue.currentSteal = forkJoinTask2;
                                                            if (forkJoinTask.status < 0) break block0;
                                                            if (workQueue.top == n2) {
                                                                n2 = n7;
                                                                break block19;
                                                            }
                                                            object2 = workQueue.pop();
                                                            object = object2;
                                                            if (object2 == null) {
                                                                n2 = n;
                                                                break block18;
                                                            }
                                                            workQueue.currentSteal = object;
                                                        } while (true);
                                                    }
                                                }
                                                n2 = n7;
                                            }
                                        }
                                    }
                                    if (object == null && n2 == workQueue3.base && n2 - workQueue3.top >= 0) {
                                        forkJoinTask3 = forkJoinTask4;
                                        if (forkJoinTask4 == null) {
                                            n2 = n;
                                            if (forkJoinTask4 == workQueue3.currentJoin) {
                                                if (n == n3) break block0;
                                                n2 = n3;
                                            }
                                            break block18;
                                        }
                                        workQueue2 = workQueue3;
                                        object = arrworkQueue;
                                        continue block1;
                                    }
                                    n7 = n3;
                                } while (true);
                            }
                            n2 += workQueue3.base;
                        } while (++n7 <= n3);
                        break block0;
                    }
                    n2 = n;
                }
                n = n2;
            }
        }
    }

    private void inactivate(WorkQueue workQueue, int n) {
        block1 : {
            long l;
            n = n + 65536 | Integer.MIN_VALUE;
            long l2 = n;
            if (workQueue == null) break block1;
            workQueue.scanState = n;
            do {
                l = this.ctl;
                workQueue.stackPred = (int)l;
            } while (!U.compareAndSwapLong(this, CTL, l, -4294967296L & l - 0x1000000000000L | l2 & 0xFFFFFFFFL));
        }
    }

    static ForkJoinPool makeCommonPool() {
        int n;
        ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory;
        String string;
        Object object;
        ForkJoinWorkerThreadFactory forkJoinWorkerThreadFactory2;
        String string2;
        Object object2;
        int n2;
        block13 : {
            int n3 = -1;
            forkJoinWorkerThreadFactory = null;
            forkJoinWorkerThreadFactory2 = null;
            object = null;
            n2 = n3;
            object2 = forkJoinWorkerThreadFactory;
            String string3 = System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
            n2 = n3;
            object2 = forkJoinWorkerThreadFactory;
            string2 = System.getProperty("java.util.concurrent.ForkJoinPool.common.threadFactory");
            n2 = n3;
            object2 = forkJoinWorkerThreadFactory;
            string = System.getProperty("java.util.concurrent.ForkJoinPool.common.exceptionHandler");
            n = n3;
            if (string3 == null) break block13;
            n2 = n3;
            object2 = forkJoinWorkerThreadFactory;
            try {
                n = Integer.parseInt(string3);
            }
            catch (Exception exception) {
                object = null;
                forkJoinWorkerThreadFactory2 = object2;
            }
        }
        if (string2 != null) {
            n2 = n;
            object2 = forkJoinWorkerThreadFactory;
            forkJoinWorkerThreadFactory2 = (ForkJoinWorkerThreadFactory)ClassLoader.getSystemClassLoader().loadClass(string2).newInstance();
        }
        object2 = object;
        if (string != null) {
            n2 = n;
            object2 = forkJoinWorkerThreadFactory2;
            object2 = object = (Thread.UncaughtExceptionHandler)ClassLoader.getSystemClassLoader().loadClass(string).newInstance();
        }
        n2 = n;
        object = object2;
        object2 = forkJoinWorkerThreadFactory2;
        if (forkJoinWorkerThreadFactory2 == null) {
            object2 = System.getSecurityManager() == null ? defaultForkJoinWorkerThreadFactory : new InnocuousForkJoinWorkerThreadFactory();
        }
        n = n2;
        if (n2 < 0) {
            n = n2 = Runtime.getRuntime().availableProcessors() - 1;
            if (n2 <= 0) {
                n = 1;
            }
        }
        n2 = n;
        if (n > 32767) {
            n2 = 32767;
        }
        return new ForkJoinPool(n2, (ForkJoinWorkerThreadFactory)object2, (Thread.UncaughtExceptionHandler)object, 0, "ForkJoinPool.commonPool-worker-");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void managedBlock(ManagedBlocker var0) throws InterruptedException {
        var1_2 = Thread.currentThread();
        if (!(var1_2 instanceof ForkJoinWorkerThread)) ** GOTO lbl-1000
        var2_3 = (ForkJoinWorkerThread)var1_2;
        var1_2 = var2_3.pool;
        if (var1_2 != null) {
            var2_3 = var2_3.workQueue;
            do {
                if (var0.isReleasable() != false) return;
            } while (!ForkJoinPool.super.tryCompensate((WorkQueue)var2_3));
        } else lbl-1000: // 2 sources:
        {
            do {
                if (var0.isReleasable() != false) return;
            } while (!var0.block());
            return;
        }
        try {
            while (!var0.isReleasable() && !(var3_4 = var0.block())) {
            }
            ForkJoinPool.U.getAndAddLong(var1_2, ForkJoinPool.CTL, 0x1000000000000L);
        }
        catch (Throwable var0_1) {
            ForkJoinPool.U.getAndAddLong(var1_2, ForkJoinPool.CTL, 0x1000000000000L);
            throw var0_1;
        }
    }

    private static final int nextPoolId() {
        synchronized (ForkJoinPool.class) {
            int n;
            poolNumberSequence = n = poolNumberSequence + 1;
            return n;
        }
    }

    static void quiesceCommonPool() {
        common.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    private int scan(WorkQueue workQueue, int n, int n2, int n3) {
        block16 : {
            int n4 = 0;
            WorkQueue[] arrworkQueue = this.workQueues;
            if (arrworkQueue != null && workQueue != null) {
                int n5;
                int n6 = n5 = arrworkQueue.length;
                if (n5 > 0) {
                    int n7 = n6 - 1;
                    int n8 = n5 = n7 & n3;
                    int n9 = 0;
                    int n10 = workQueue.scanState;
                    int n11 = n3;
                    n3 = n4;
                    do {
                        int n12;
                        ForkJoinTask<?>[] arrforkJoinTask;
                        WorkQueue workQueue2;
                        if ((workQueue2 = arrworkQueue[n8]) != null && (n12 = workQueue2.base) - workQueue2.top < 0 && (arrforkJoinTask = workQueue2.array) != null && (n4 = arrforkJoinTask.length) > 0) {
                            long l = n4 - 1 & n12;
                            int n13 = ASHIFT;
                            int n14 = ABASE;
                            n4 = n3;
                            ForkJoinTask forkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrforkJoinTask, l = (long)n14 + (l << n13));
                            if (forkJoinTask == null) {
                                n = n4;
                            } else {
                                n14 = n12 + 1;
                                if (n12 != workQueue2.base) {
                                    n = n4;
                                } else if (n10 < 0) {
                                    this.tryReactivate(workQueue, arrworkQueue, n11);
                                    n = n4;
                                } else if (!U.compareAndSwapObject(arrforkJoinTask, l, forkJoinTask, null)) {
                                    n = n4;
                                } else {
                                    workQueue2.base = n14;
                                    workQueue.currentSteal = forkJoinTask;
                                    if (n14 != workQueue2.top) {
                                        this.signalWork();
                                    }
                                    workQueue.runTask(forkJoinTask);
                                    if (++n9 <= n) continue;
                                    n = n4;
                                }
                            }
                            break block16;
                        }
                        n4 = n3;
                        if (n9 != 0) {
                            n = n4;
                            break block16;
                        }
                        if ((n8 = n8 + n2 & n7) != n5) continue;
                        if (n10 < 0) {
                            n = n10;
                            break block16;
                        }
                        if (n11 >= 0) {
                            this.inactivate(workQueue, n10);
                            n = n4;
                            break block16;
                        }
                        n11 <<= 1;
                    } while (true);
                }
            }
            n = 0;
        }
        return n;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int timedAwaitWork(WorkQueue workQueue, long l) {
        void var1_5;
        AuxState auxState;
        block13 : {
            int n;
            block12 : {
                WorkQueue[] arrworkQueue;
                int n2 = 0;
                int n3 = (int)(l >>> 32);
                n = 1;
                if ((n3 = 1 - n3) > 0) {
                    n = n3;
                }
                long l2 = (long)n * 2000L + System.currentTimeMillis();
                n = n2;
                if (this.runState < 0) {
                    n = n2 = (n3 = this.tryTerminate(false, false));
                    if (n3 <= 0) return n;
                    n = n2;
                }
                if (workQueue == null) return n;
                if (workQueue.scanState >= 0) return n;
                workQueue.parker = Thread.currentThread();
                if (workQueue.scanState < 0) {
                    LockSupport.parkUntil(this, l2);
                }
                workQueue.parker = null;
                if ((this.runState & 2) != 0) {
                    workQueue.qlock = -1;
                    return -1;
                }
                n2 = workQueue.scanState;
                if (n2 >= 0) return n;
                if (Thread.interrupted()) return n;
                if ((int)l != n2) return n;
                auxState = this.auxState;
                if (auxState == null) return n;
                if (this.ctl != l) return n;
                if (l2 - System.currentTimeMillis() > 20L) return n;
                auxState.lock();
                n2 = workQueue.config;
                n3 = n2 & 65535;
                int n4 = workQueue.stackPred;
                long l3 = n4;
                try {}
                catch (Throwable throwable) {}
                if ((this.runState & 2) == 0 && (arrworkQueue = this.workQueues) != null && n3 < arrworkQueue.length && n3 >= 0 && arrworkQueue[n3] == workQueue) {
                    Unsafe unsafe = U;
                    l2 = CTL;
                    try {
                        if (!unsafe.compareAndSwapLong(this, l2, l, -4294967296L & l - 0x100000000L | 0xFFFFFFFFL & l3)) break block12;
                        arrworkQueue[n3] = null;
                        workQueue.config = n2 | 262144;
                        n = -1;
                        workQueue.qlock = -1;
                    }
                    catch (Throwable throwable) {
                        break block13;
                    }
                }
            }
            auxState.unlock();
            return n;
            break block13;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        auxState.unlock();
        throw var1_5;
    }

    private void tryAddWorker(long l) {
        long l2 = l;
        do {
            if (this.ctl == l2 && U.compareAndSwapLong(this, CTL, l2, -281474976710656L & 0x1000000000000L + l2 | 0xFFFF00000000L & 0x100000000L + l2)) {
                this.createWorker(false);
                break;
            }
            l = l2 = this.ctl;
            if ((l2 & 0x800000000000L) == 0L) break;
            l2 = l;
        } while ((int)l == 0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean tryCompensate(WorkQueue workQueue) {
        long l = this.ctl;
        WorkQueue[] arrworkQueue = this.workQueues;
        int n = this.config & 65535;
        int n2 = (int)(l >> 48);
        int n3 = n + (short)(l >> 32);
        if (workQueue == null) return false;
        if (workQueue.qlock < 0) return false;
        if (n == 0) return false;
        if (arrworkQueue == null) return false;
        int n4 = arrworkQueue.length;
        if (n4 <= 0) return false;
        int n5 = n4 - 1;
        for (n4 = 0; n4 <= n5; ++n4) {
            WorkQueue workQueue2;
            int n6 = n4 << 1 | 1;
            if (n6 > n5 || n6 < 0 || (workQueue2 = arrworkQueue[n6]) == null || workQueue2.scanState < 0 || workQueue2.currentSteal != null) continue;
            return false;
        }
        n4 = 1;
        if (n4 == 0) return false;
        if (this.ctl != l) return false;
        n4 = (int)l;
        if (n4 != 0) {
            return this.tryRelease(l, arrworkQueue[n5 & n4], 0L);
        }
        if (n3 >= n && n + n2 > 1 && workQueue.isEmpty()) {
            return U.compareAndSwapLong(this, CTL, l, -281474976710656L & l - 0x1000000000000L | 0xFFFFFFFFFFFFL & l);
        }
        if (n3 >= 32767) throw new RejectedExecutionException("Thread limit exceeded replacing blocked worker");
        if (this == common) {
            if (n3 >= COMMON_MAX_SPARES + n) throw new RejectedExecutionException("Thread limit exceeded replacing blocked worker");
        }
        boolean bl = false;
        boolean bl2 = n3 >= n;
        if (!U.compareAndSwapLong(this, CTL, l, -281474976710656L & l | 0xFFFF00000000L & 0x100000000L + l)) return bl;
        if (!this.createWorker(bl2)) return bl;
        return true;
    }

    private void tryCreateExternalQueue(int n) {
        AuxState auxState = this.auxState;
        if (auxState != null && n >= 0) {
            boolean bl;
            WorkQueue workQueue;
            block9 : {
                WorkQueue[] arrworkQueue;
                workQueue = new WorkQueue(this, null);
                workQueue.config = n;
                workQueue.scanState = Integer.MAX_VALUE;
                workQueue.qlock = 1;
                boolean bl2 = false;
                auxState.lock();
                try {
                    arrworkQueue = this.workQueues;
                    bl = bl2;
                    if (arrworkQueue == null) break block9;
                    bl = bl2;
                }
                catch (Throwable throwable) {
                    auxState.unlock();
                    throw throwable;
                }
                if (n >= arrworkQueue.length) break block9;
                bl = bl2;
                if (arrworkQueue[n] == null) {
                    arrworkQueue[n] = workQueue;
                    bl = true;
                }
            }
            auxState.unlock();
            if (bl) {
                try {
                    workQueue.growArray();
                }
                finally {
                    workQueue.qlock = 0;
                }
            }
        }
    }

    private boolean tryDropSpare(WorkQueue workQueue) {
        block8 : {
            if (workQueue != null && workQueue.isEmpty()) {
                boolean bl;
                WorkQueue[] arrworkQueue;
                int n;
                int n2;
                do {
                    Object object = this;
                    long l = ((ForkJoinPool)object).ctl;
                    if ((short)(l >> 32) <= 0 || (n = (int)l) == 0 && (int)(l >> 48) <= 0 || (arrworkQueue = ((ForkJoinPool)object).workQueues) == null || (n2 = arrworkQueue.length) <= 0) break block8;
                    if (n == 0) {
                        bl = U.compareAndSwapLong(this, CTL, l, l - 0x100000000L & 0xFFFF00000000L | l - 0x1000000000000L & -281474976710656L | 0xFFFFFFFFL & l);
                        continue;
                    }
                    object = arrworkQueue[n2 - 1 & n];
                    if (object != null && ((WorkQueue)object).scanState == n) {
                        long l2 = 0xFFFFFFFFL & (long)((WorkQueue)object).stackPred;
                        if (workQueue != object && workQueue.scanState < 0) {
                            l2 |= 0x1000000000000L + l & -281474976710656L | 0xFFFF00000000L & l;
                            bl = false;
                        } else {
                            l2 |= l & -281474976710656L | 0xFFFF00000000L & l - 0x100000000L;
                            bl = true;
                        }
                        if (U.compareAndSwapLong(this, CTL, l, l2)) {
                            ((WorkQueue)object).scanState = Integer.MAX_VALUE & n;
                            LockSupport.unpark(((WorkQueue)object).parker);
                            continue;
                        }
                        bl = false;
                        continue;
                    }
                    bl = false;
                } while (!bl);
                n2 = workQueue.config;
                n = 65535 & n2;
                if (n >= 0 && n < arrworkQueue.length && arrworkQueue[n] == workQueue) {
                    arrworkQueue[n] = null;
                }
                workQueue.config = 262144 | n2;
                workQueue.qlock = -1;
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void tryInitialize(boolean bl) {
        if (this.runState == 0) {
            int n = this.config & 65535;
            n = n > 1 ? --n : 1;
            n |= n >>> 1;
            n |= n >>> 2;
            n |= n >>> 4;
            n |= n >>> 8;
            AuxState auxState = new AuxState();
            WorkQueue[] arrworkQueue = new WorkQueue[65535 & (n | n >>> 16) + 1 << 1];
            RuntimePermission runtimePermission = modifyThreadPermission;
            synchronized (runtimePermission) {
                if (this.runState == 0) {
                    this.workQueues = arrworkQueue;
                    this.auxState = auxState;
                    this.runState = 1;
                }
            }
        }
        if (!bl) return;
        if (this.runState >= 0) {
            return;
        }
        this.tryTerminate(false, false);
        throw new RejectedExecutionException();
    }

    private void tryReactivate(WorkQueue workQueue, WorkQueue[] object, int n) {
        block1 : {
            int n2;
            long l = this.ctl;
            int n3 = (int)l;
            if (n3 == 0 || workQueue == null || object == null || (n2 = ((WorkQueue[])object).length) <= 0 || ((n3 ^ n) & 65536) != 0 || (object = object[n2 - 1 & n3]) == null) break block1;
            long l2 = object.stackPred;
            if (workQueue.scanState < 0 && object.scanState == n3 && U.compareAndSwapLong(this, CTL, l, l2 & 0xFFFFFFFFL | -4294967296L & 0x1000000000000L + l)) {
                object.scanState = n3 & Integer.MAX_VALUE;
                LockSupport.unpark(object.parker);
            }
        }
    }

    private boolean tryRelease(long l, WorkQueue workQueue, long l2) {
        int n = (int)l;
        if (workQueue != null) {
            int n2 = workQueue.scanState;
            long l3 = workQueue.stackPred;
            if (n == n2 && U.compareAndSwapLong(this, CTL, l, l3 & 0xFFFFFFFFL | -4294967296L & l + l2)) {
                workQueue.scanState = n & Integer.MAX_VALUE;
                LockSupport.unpark(workQueue.parker);
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int tryTerminate(boolean bl, boolean bl2) {
        long l;
        Object object;
        int n;
        Object object2;
        long l2;
        long l3;
        while ((n = this.runState) >= 0) {
            if (!bl2) return 1;
            if (this == common) {
                return 1;
            }
            if (n == 0) {
                this.tryInitialize(false);
                continue;
            }
            U.compareAndSwapInt(this, RUNSTATE, n, n | Integer.MIN_VALUE);
        }
        if ((n & 2) == 0) {
            if (!bl) {
                l = 0L;
                do {
                    if ((int)((l2 = this.ctl) >> 48) + (this.config & 65535) > 0) {
                        return 0;
                    }
                    object = this.workQueues;
                    l3 = l2;
                    if (object != null) {
                        n = 0;
                        do {
                            l3 = l2;
                            if (n >= ((Object)object).length) break;
                            object2 = object[n];
                            l3 = l2;
                            if (object2 != null) {
                                int n2 = object2.base;
                                l3 = l2 + (long)n2;
                                if (object2.currentSteal != null) return 0;
                                if (n2 != object2.top) {
                                    return 0;
                                }
                            }
                            ++n;
                            l2 = l3;
                        } while (true);
                    }
                    if (l == l3) break;
                    l = l3;
                } while (true);
            }
            while (!(object2 = U).compareAndSwapInt(this, l2 = RUNSTATE, n = this.runState, n | 2)) {
            }
        }
        l3 = 0L;
        do {
            l = this.ctl;
            object2 = this.workQueues;
            l2 = l;
            if (object2 != null) {
                l2 = l;
                for (n = 0; n < ((WorkQueue[])object2).length; ++n) {
                    object = object2[n];
                    l = l2;
                    if (object != null) {
                        ((WorkQueue)object).cancelAll();
                        l = l2 += (long)((WorkQueue)object).base;
                        if (((WorkQueue)object).qlock >= 0) {
                            ((WorkQueue)object).qlock = -1;
                            object = ((WorkQueue)object).owner;
                            l = l2;
                            if (object != null) {
                                try {
                                    ((Thread)object).interrupt();
                                }
                                finally {
                                    l = l2;
                                }
                            }
                        }
                    }
                    l2 = l;
                }
            }
            if (l3 == l2) {
                if ((short)(this.ctl >>> 32) + (this.config & 65535) > 0) return -1;
                this.runState = -2147483641;
                synchronized (this) {
                    this.notifyAll();
                    return -1;
                }
            }
            l3 = l2;
        } while (true);
    }

    final int awaitJoin(WorkQueue workQueue, ForkJoinTask<?> forkJoinTask, long l) {
        int n = 0;
        int n2 = 0;
        if (workQueue != null) {
            ForkJoinTask<?> forkJoinTask2 = workQueue.currentJoin;
            if (forkJoinTask != null) {
                n2 = n = forkJoinTask.status;
                if (n >= 0) {
                    workQueue.currentJoin = forkJoinTask;
                    CountedCompleter countedCompleter = forkJoinTask instanceof CountedCompleter ? (CountedCompleter)forkJoinTask : null;
                    do {
                        long l2;
                        if (countedCompleter != null) {
                            this.helpComplete(workQueue, countedCompleter, 0);
                        } else {
                            this.helpStealer(workQueue, forkJoinTask);
                        }
                        n2 = n = forkJoinTask.status;
                        if (n < 0) break;
                        if (l == 0L) {
                            l2 = 0L;
                        } else {
                            long l3;
                            l2 = l - System.nanoTime();
                            if (l2 <= 0L) break;
                            l2 = l3 = TimeUnit.NANOSECONDS.toMillis(l2);
                            if (l3 <= 0L) {
                                l2 = 1L;
                            }
                        }
                        if (this.tryCompensate(workQueue)) {
                            forkJoinTask.internalWait(l2);
                            U.getAndAddLong(this, CTL, 0x1000000000000L);
                        }
                        n2 = n = forkJoinTask.status;
                    } while (n >= 0);
                    workQueue.currentJoin = forkJoinTask2;
                }
            }
        } else {
            n2 = n;
        }
        return n2;
    }

    public boolean awaitQuiescence(long l, TimeUnit object) {
        int n;
        Object object2 = this;
        l = ((TimeUnit)((Object)object)).toNanos(l);
        object = Thread.currentThread();
        if (object instanceof ForkJoinWorkerThread) {
            object = (ForkJoinWorkerThread)object;
            if (((ForkJoinWorkerThread)object).pool == object2) {
                ((ForkJoinPool)object2).helpQuiescePool(((ForkJoinWorkerThread)object).workQueue);
                return true;
            }
        }
        long l2 = System.nanoTime();
        int n2 = 0;
        boolean bl = true;
        while (!this.isQuiescent() && (object = this.workQueues) != null && (n = ((WorkQueue[])object).length) > 0) {
            if (!bl) {
                if (System.nanoTime() - l2 > l) {
                    return false;
                }
                Thread.yield();
            }
            boolean bl2 = false;
            int n3 = n - 1;
            int n4 = n3 + 1 << 2;
            do {
                n = n2;
                bl = bl2;
                if (n4 < 0) break;
                n = n2 + 1;
                if ((n2 &= n3) <= n3 && n2 >= 0 && (object2 = object[n2]) != null && (n2 = ((WorkQueue)object2).base) - ((WorkQueue)object2).top < 0) {
                    object = ((WorkQueue)object2).pollAt(n2);
                    if (object != null) {
                        ((ForkJoinTask)object).doExec();
                    }
                    bl = true;
                    break;
                }
                --n4;
                n2 = n;
            } while (true);
            n2 = n;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean awaitTermination(long l, TimeUnit timeUnit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        if (this == common) {
            this.awaitQuiescence(l, timeUnit);
            return false;
        }
        long l2 = timeUnit.toNanos(l);
        if (this.isTerminated()) {
            return true;
        }
        if (l2 <= 0L) {
            return false;
        }
        long l3 = System.nanoTime();
        synchronized (this) {
            l = l2;
            while (!this.isTerminated()) {
                if (l <= 0L) {
                    return false;
                }
                if ((l = TimeUnit.NANOSECONDS.toMillis(l)) <= 0L) {
                    l = 1L;
                }
                this.wait(l);
                l = l3 + l2 - System.nanoTime();
            }
            return true;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void deregisterWorker(ForkJoinWorkerThread object, Throwable throwable) {
        void var2_8;
        int n;
        long l;
        WorkQueue[] arrworkQueue;
        int n2;
        WorkQueue workQueue = null;
        if (object != null && (workQueue = object.workQueue) != null) {
            n = 65535 & workQueue.config;
            n2 = workQueue.nsteals;
            AuxState auxState = this.auxState;
            if (auxState != null) {
                auxState.lock();
                WorkQueue[] arrworkQueue2 = this.workQueues;
                if (arrworkQueue2 != null && arrworkQueue2.length > n && arrworkQueue2[n] == workQueue) {
                    arrworkQueue2[n] = null;
                }
                try {
                    auxState.stealCount += (long)n2;
                }
                finally {
                    auxState.unlock();
                }
            }
        }
        if (workQueue == null || (workQueue.config & 262144) == 0) {
            Unsafe unsafe;
            long l2;
            while (!(unsafe = U).compareAndSwapLong(this, l2 = CTL, l = this.ctl, -281474976710656L & l - 0x1000000000000L | l - 0x100000000L & 0xFFFF00000000L | 0xFFFFFFFFL & l)) {
            }
        }
        if (workQueue != null) {
            workQueue.currentSteal = null;
            workQueue.qlock = -1;
            workQueue.cancelAll();
        }
        while (this.tryTerminate(false, false) >= 0 && workQueue != null && workQueue.array != null && (arrworkQueue = this.workQueues) != null && (n2 = arrworkQueue.length) > 0) {
            l = this.ctl;
            n = (int)l;
            if (n != 0) {
                if (!this.tryRelease(l, arrworkQueue[n2 - 1 & n], 0x1000000000000L)) continue;
                break;
            }
            if (var2_8 == null || (0x800000000000L & l) == 0L) break;
            this.tryAddWorker(l);
            break;
        }
        if (var2_8 == null) {
            ForkJoinTask.helpExpungeStaleExceptions();
            return;
        }
        ForkJoinTask.rethrow((Throwable)var2_8);
    }

    protected int drainTasksTo(Collection<? super ForkJoinTask<?>> collection) {
        int n = 0;
        int n2 = 0;
        WorkQueue[] arrworkQueue = this.workQueues;
        if (arrworkQueue != null) {
            int n3 = 0;
            do {
                n = n2;
                if (n3 >= arrworkQueue.length) break;
                WorkQueue workQueue = arrworkQueue[n3];
                n = n2;
                if (workQueue != null) {
                    do {
                        ForkJoinTask<?> forkJoinTask = workQueue.poll();
                        n = n2++;
                        if (forkJoinTask == null) break;
                        collection.add(forkJoinTask);
                    } while (true);
                }
                ++n3;
                n2 = n;
            } while (true);
        }
        return n;
    }

    @Override
    public void execute(Runnable forkJoinTask) {
        if (forkJoinTask != null) {
            forkJoinTask = forkJoinTask instanceof ForkJoinTask ? (ForkJoinTask)forkJoinTask : new ForkJoinTask.RunnableExecuteAction((Runnable)((Object)forkJoinTask));
            this.externalSubmit(forkJoinTask);
            return;
        }
        throw new NullPointerException();
    }

    public void execute(ForkJoinTask<?> forkJoinTask) {
        this.externalSubmit(forkJoinTask);
    }

    final int externalHelpComplete(CountedCompleter<?> countedCompleter, int n) {
        int n2;
        int n3 = ThreadLocalRandom.getProbe();
        WorkQueue[] arrworkQueue = this.workQueues;
        n = arrworkQueue != null && (n2 = arrworkQueue.length) > 0 ? this.helpComplete(arrworkQueue[n2 - 1 & n3 & 126], countedCompleter, n) : 0;
        return n;
    }

    final void externalPush(ForkJoinTask<?> forkJoinTask) {
        int n;
        int n2 = n = ThreadLocalRandom.getProbe();
        if (n == 0) {
            ThreadLocalRandom.localInit();
            n2 = ThreadLocalRandom.getProbe();
        }
        do {
            block6 : {
                block8 : {
                    block7 : {
                        n = this.runState;
                        Object object = this.workQueues;
                        if (n <= 0 || object == null || (n = ((WorkQueue[])object).length) <= 0) break block6;
                        if ((object = object[n = n - 1 & n2 & 126]) == null) {
                            this.tryCreateExternalQueue(n);
                            continue;
                        }
                        n = ((WorkQueue)object).sharedPush(forkJoinTask);
                        if (n < 0) break block7;
                        if (n != 0) break block8;
                        this.signalWork();
                    }
                    return;
                }
                n2 = ThreadLocalRandom.advanceProbe(n2);
                continue;
            }
            this.tryInitialize(true);
        } while (true);
    }

    public int getActiveThreadCount() {
        int n;
        block0 : {
            n = (this.config & 65535) + (int)(this.ctl >> 48);
            if (n > 0) break block0;
            n = 0;
        }
        return n;
    }

    public boolean getAsyncMode() {
        boolean bl = (this.config & Integer.MIN_VALUE) != 0;
        return bl;
    }

    public ForkJoinWorkerThreadFactory getFactory() {
        return this.factory;
    }

    public int getParallelism() {
        int n = this.config & 65535;
        if (n <= 0) {
            n = 1;
        }
        return n;
    }

    public int getPoolSize() {
        return (this.config & 65535) + (short)(this.ctl >>> 32);
    }

    public int getQueuedSubmissionCount() {
        int n = 0;
        int n2 = 0;
        WorkQueue[] arrworkQueue = this.workQueues;
        if (arrworkQueue != null) {
            int n3 = 0;
            do {
                n = n2;
                if (n3 >= arrworkQueue.length) break;
                WorkQueue workQueue = arrworkQueue[n3];
                n = n2;
                if (workQueue != null) {
                    n = n2 + workQueue.queueSize();
                }
                n3 += 2;
                n2 = n;
            } while (true);
        }
        return n;
    }

    public long getQueuedTaskCount() {
        long l = 0L;
        WorkQueue[] arrworkQueue = this.workQueues;
        long l2 = l;
        if (arrworkQueue != null) {
            int n = 1;
            do {
                l2 = l;
                if (n >= arrworkQueue.length) break;
                WorkQueue workQueue = arrworkQueue[n];
                l2 = l;
                if (workQueue != null) {
                    l2 = l + (long)workQueue.queueSize();
                }
                n += 2;
                l = l2;
            } while (true);
        }
        return l2;
    }

    public int getRunningThreadCount() {
        int n = 0;
        int n2 = 0;
        WorkQueue[] arrworkQueue = this.workQueues;
        if (arrworkQueue != null) {
            int n3 = 1;
            do {
                n = n2;
                if (n3 >= arrworkQueue.length) break;
                WorkQueue workQueue = arrworkQueue[n3];
                n = n2;
                if (workQueue != null) {
                    n = n2;
                    if (workQueue.isApparentlyUnblocked()) {
                        n = n2 + 1;
                    }
                }
                n3 += 2;
                n2 = n;
            } while (true);
        }
        return n;
    }

    public long getStealCount() {
        WorkQueue[] arrworkQueue = this.auxState;
        long l = arrworkQueue == null ? 0L : arrworkQueue.stealCount;
        arrworkQueue = this.workQueues;
        long l2 = l;
        if (arrworkQueue != null) {
            int n = 1;
            do {
                l2 = l;
                if (n >= arrworkQueue.length) break;
                WorkQueue workQueue = arrworkQueue[n];
                l2 = l;
                if (workQueue != null) {
                    l2 = l + (long)workQueue.nsteals;
                }
                n += 2;
                l = l2;
            } while (true);
        }
        return l2;
    }

    public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.ueh;
    }

    public boolean hasQueuedSubmissions() {
        WorkQueue[] arrworkQueue = this.workQueues;
        if (arrworkQueue != null) {
            for (int i = 0; i < arrworkQueue.length; i += 2) {
                WorkQueue workQueue = arrworkQueue[i];
                if (workQueue == null || workQueue.isEmpty()) continue;
                return true;
            }
        }
        return false;
    }

    final int helpComplete(WorkQueue workQueue, CountedCompleter<?> countedCompleter, int n) {
        int n2;
        Object object = workQueue;
        int n3 = 0;
        WorkQueue[] arrworkQueue = this.workQueues;
        if (arrworkQueue != null && (n2 = arrworkQueue.length) > 1 && countedCompleter != null && object != null) {
            int n4;
            int n5;
            int n6 = n2 - 1;
            int n7 = n5 = ((WorkQueue)object).config;
            int n8 = n4 = n7 & n6;
            int n9 = 3;
            int n10 = 1;
            int n11 = 0;
            int n12 = 0;
            n2 = n;
            n = n12;
            do {
                int n13;
                int n14;
                int n15;
                int n16;
                int n17;
                n3 = n12 = countedCompleter.status;
                if (n12 < 0) break;
                if (n10 == 1 && (object = workQueue.popCC(countedCompleter, n5)) != null) {
                    ((ForkJoinTask)object).doExec();
                    n13 = n2;
                    if (n2 != 0 && (n13 = n2 - 1) == 0) break;
                    n = 0;
                    n12 = 0;
                    n15 = n8;
                    n17 = n7;
                    n14 = n9;
                    n16 = n10;
                } else {
                    int n18;
                    n12 = n8 | 1;
                    if (n12 >= 0 && n12 <= n6 && (object = arrworkQueue[n12]) != null) {
                        n18 = n12 = (n15 = ((WorkQueue)object).pollAndExecCC(countedCompleter));
                        n10 = n;
                        if (n15 < 0) {
                            n10 = n + n12;
                            n18 = n12;
                        }
                    } else {
                        n18 = 0;
                        n10 = n;
                    }
                    if (n18 > 0) {
                        n13 = n2;
                        if (n18 == 1) {
                            n13 = n2;
                            if (n2 != 0) {
                                n13 = n = n2 - 1;
                                if (n == 0) break;
                            }
                        }
                        n14 = n7 >>> 16 | 3;
                        n = n7 ^ n7 << 13;
                        n ^= n >>> 17;
                        n17 = n ^ n << 5;
                        n15 = n8 = n17 & n6;
                        n = 0;
                        n12 = 0;
                        n16 = n18;
                    } else {
                        int n19;
                        int n20 = n19 = n8 + n9 & n6;
                        n17 = n7;
                        n15 = n4;
                        n8 = n20;
                        n14 = n9;
                        n16 = n18;
                        n12 = n11;
                        n = n10;
                        n13 = n2;
                        if (n19 == n4) {
                            if (n11 == n10) break;
                            n = 0;
                            n12 = n10;
                            n13 = n2;
                            n16 = n18;
                            n14 = n9;
                            n8 = n20;
                            n15 = n4;
                            n17 = n7;
                        }
                    }
                }
                n7 = n17;
                n4 = n15;
                n9 = n14;
                n10 = n16;
                n11 = n12;
                n2 = n13;
            } while (true);
        }
        return n3;
    }

    final void helpQuiescePool(WorkQueue workQueue) {
        ForkJoinTask<?> forkJoinTask = workQueue.currentSteal;
        int n = workQueue.config;
        int n2 = 1;
        do {
            int n3;
            Object object;
            if (n >= 0 && (object = workQueue.pop()) != null) {
                workQueue.currentSteal = object;
                ((ForkJoinTask)object).doExec();
                workQueue.currentSteal = forkJoinTask;
                n3 = n2;
            } else {
                long l;
                object = this.findNonEmptyStealQueue();
                if (object != null) {
                    int n4 = n2;
                    if (n2 == 0) {
                        n4 = 1;
                        U.getAndAddLong(this, CTL, 0x1000000000000L);
                    }
                    object = ((WorkQueue)object).pollAt(((WorkQueue)object).base);
                    n3 = n4;
                    if (object != null) {
                        workQueue.currentSteal = object;
                        ((ForkJoinTask)object).doExec();
                        workQueue.currentSteal = forkJoinTask;
                        workQueue.nsteals = n2 = workQueue.nsteals + 1;
                        n3 = n4;
                        if (n2 < 0) {
                            workQueue.transferStealCount(this);
                            n3 = n4;
                        }
                    }
                } else if (n2 != 0) {
                    l = this.ctl;
                    if (U.compareAndSwapLong(this, CTL, l, l - 0x1000000000000L & -281474976710656L | 0xFFFFFFFFFFFFL & l)) {
                        n2 = 0;
                    }
                    n3 = n2;
                } else {
                    l = this.ctl;
                    n3 = n2;
                    if ((int)(l >> 48) + (this.config & 65535) <= 0) {
                        n3 = n2;
                        if (U.compareAndSwapLong(this, CTL, l, l + 0x1000000000000L)) {
                            return;
                        }
                    }
                }
            }
            n2 = n3;
        } while (true);
    }

    public <T> T invoke(ForkJoinTask<T> forkJoinTask) {
        if (forkJoinTask != null) {
            this.externalSubmit(forkJoinTask);
            return forkJoinTask.join();
        }
        throw new NullPointerException();
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> object) {
        int n;
        int n2;
        ArrayList<Future<T>> arrayList = new ArrayList<Future<T>>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            Callable callable = (Callable)object.next();
            ForkJoinTask.AdaptedCallable adaptedCallable = new ForkJoinTask.AdaptedCallable(callable);
            arrayList.add(adaptedCallable);
            this.externalSubmit(adaptedCallable);
        }
        try {
            n2 = arrayList.size();
        }
        catch (Throwable throwable) {
            n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                ((Future)arrayList.get(n)).cancel(false);
            }
            throw throwable;
        }
        for (n = 0; n < n2; ++n) {
            ((ForkJoinTask)arrayList.get(n)).quietlyJoin();
        }
        return arrayList;
    }

    public boolean isQuiescent() {
        boolean bl = (this.config & 65535) + (int)(this.ctl >> 48) <= 0;
        return bl;
    }

    @Override
    public boolean isShutdown() {
        boolean bl = (this.runState & Integer.MIN_VALUE) != 0;
        return bl;
    }

    @Override
    public boolean isTerminated() {
        boolean bl = (this.runState & 4) != 0;
        return bl;
    }

    public boolean isTerminating() {
        int n = this.runState;
        boolean bl = (n & 2) != 0 && (n & 4) == 0;
        return bl;
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t) {
        return new ForkJoinTask.AdaptedRunnable<T>(runnable, t);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new ForkJoinTask.AdaptedCallable<T>(callable);
    }

    final ForkJoinTask<?> nextTaskFor(WorkQueue workQueue) {
        Object object;
        do {
            if ((object = workQueue.nextLocalTask()) != null) {
                return object;
            }
            object = this.findNonEmptyStealQueue();
            if (object != null) continue;
            return null;
        } while ((object = ((WorkQueue)object).pollAt(((WorkQueue)object).base)) == null);
        return object;
    }

    protected ForkJoinTask<?> pollSubmission() {
        int n;
        ThreadLocalRandom.nextSecondarySeed();
        WorkQueue[] arrworkQueue = this.workQueues;
        if (arrworkQueue != null && (n = arrworkQueue.length) > 0) {
            for (int i = 0; i < n; ++i) {
                Object object = arrworkQueue[i << 1 & n - 1];
                if (object == null || (object = ((WorkQueue)object).poll()) == null) continue;
                return object;
            }
        }
        return null;
    }

    final WorkQueue registerWorker(ForkJoinWorkerThread forkJoinWorkerThread) {
        forkJoinWorkerThread.setDaemon(true);
        WorkQueue[] arrworkQueue = this.ueh;
        if (arrworkQueue != null) {
            forkJoinWorkerThread.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)arrworkQueue);
        }
        WorkQueue workQueue = new WorkQueue(this, forkJoinWorkerThread);
        int n = 0;
        int n2 = 0;
        int n3 = this.config;
        AuxState auxState = this.auxState;
        if (auxState != null) {
            block12 : {
                int n4;
                long l;
                auxState.lock();
                auxState.indexSeed = l = auxState.indexSeed - 1640531527L;
                int n5 = (int)l;
                arrworkQueue = this.workQueues;
                n = n2;
                if (arrworkQueue == null) break block12;
                int n6 = n4 = arrworkQueue.length;
                n = n2;
                if (n4 <= 0) break block12;
                int n7 = n2 = n6 - 1;
                n = n2 &= 1 | n5 << 1;
                WorkQueue[] arrworkQueue2 = arrworkQueue;
                if (arrworkQueue[n2] != null) {
                    int n8 = 0;
                    n4 = 2;
                    if (n6 > 4) {
                        n4 = 2 + (n6 >>> 1 & 65534);
                    }
                    int n9 = n6;
                    n = n2;
                    n2 = n8;
                    do {
                        n = n6 = (n8 = n + n4 & n7);
                        arrworkQueue2 = arrworkQueue;
                        if (arrworkQueue[n8] == null) break;
                        n2 = n8 = n2 + 1;
                        n = n6;
                        if (n8 < n9) continue;
                        n9 = n2 = n9 << 1;
                        arrworkQueue = arrworkQueue2 = Arrays.copyOf(arrworkQueue, n2);
                        this.workQueues = arrworkQueue2;
                        n7 = n9 - 1;
                        n2 = 0;
                        n = n6;
                    } while (true);
                }
                try {
                    workQueue.hint = n5;
                    workQueue.config = n | n3 & -65536;
                    workQueue.scanState = 2147418112 & n5 | n;
                    arrworkQueue2[n] = workQueue;
                }
                catch (Throwable throwable) {
                    auxState.unlock();
                    throw throwable;
                }
            }
            auxState.unlock();
        }
        forkJoinWorkerThread.setName(this.workerNamePrefix.concat(Integer.toString(n >>> 1)));
        return workQueue;
    }

    final void runWorker(WorkQueue workQueue) {
        workQueue.growArray();
        int n = (workQueue.config & 131072) != 0 ? 0 : 1023;
        long l = (long)workQueue.hint * -2685821657736338717L;
        if ((this.runState & 2) == 0) {
            if (l == 0L) {
                l = 1L;
            }
            while (n != 0 || !this.tryDropSpare(workQueue)) {
                int n2 = (int)(l >>> 48);
                l ^= l >>> 12;
                l ^= l << 25;
                if (this.scan(workQueue, n, n2 | 1, (int)(l ^= l >>> 27)) >= 0 || this.awaitWork(workQueue) >= 0) continue;
                break;
            }
        }
    }

    @Override
    public void shutdown() {
        ForkJoinPool.checkPermission();
        this.tryTerminate(false, true);
    }

    @Override
    public List<Runnable> shutdownNow() {
        ForkJoinPool.checkPermission();
        this.tryTerminate(true, true);
        return Collections.emptyList();
    }

    final void signalWork() {
        long l;
        while ((l = this.ctl) < 0L) {
            int n;
            int n2;
            int n3 = (int)l;
            if (n3 == 0) {
                if ((0x800000000000L & l) == 0L) break;
                this.tryAddWorker(l);
                break;
            }
            Object object = this.workQueues;
            if (object == null || (n2 = ((WorkQueue[])object).length) <= (n = 65535 & n3) || (object = object[n]) == null) break;
            n = object.scanState;
            long l2 = object.stackPred;
            if (n3 != n || !U.compareAndSwapLong(this, CTL, l, l2 & 0xFFFFFFFFL | -4294967296L & 0x1000000000000L + l)) continue;
            object.scanState = n3 & Integer.MAX_VALUE;
            LockSupport.unpark(object.parker);
            break;
        }
    }

    public ForkJoinTask<?> submit(Runnable forkJoinTask) {
        if (forkJoinTask != null) {
            forkJoinTask = forkJoinTask instanceof ForkJoinTask ? (ForkJoinTask)forkJoinTask : new ForkJoinTask.AdaptedRunnableAction((Runnable)((Object)forkJoinTask));
            return this.externalSubmit(forkJoinTask);
        }
        throw new NullPointerException();
    }

    public <T> ForkJoinTask<T> submit(Runnable runnable, T t) {
        return this.externalSubmit(new ForkJoinTask.AdaptedRunnable<T>(runnable, t));
    }

    public <T> ForkJoinTask<T> submit(Callable<T> callable) {
        return this.externalSubmit(new ForkJoinTask.AdaptedCallable<T>(callable));
    }

    public <T> ForkJoinTask<T> submit(ForkJoinTask<T> forkJoinTask) {
        return this.externalSubmit(forkJoinTask);
    }

    public String toString() {
        int n;
        long l = 0L;
        long l2 = 0L;
        int n2 = 0;
        int n3 = 0;
        Object object = this.auxState;
        long l3 = object == null ? 0L : ((AuxState)object).stealCount;
        long l4 = this.ctl;
        Object object2 = this.workQueues;
        if (object2 != null) {
            for (n2 = 0; n2 < ((WorkQueue[])object2).length; ++n2) {
                long l5;
                object = object2[n2];
                if (object != null) {
                    n = ((WorkQueue)object).queueSize();
                    if ((n2 & 1) == 0) {
                        l5 = l2 + (long)n;
                        n = n3;
                    } else {
                        long l6 = l + (long)n;
                        long l7 = l3 + (long)((WorkQueue)object).nsteals;
                        l = l6;
                        l5 = l2;
                        n = n3;
                        l3 = l7;
                        if (((WorkQueue)object).isApparentlyUnblocked()) {
                            n = n3 + 1;
                            l = l6;
                            l5 = l2;
                            l3 = l7;
                        }
                    }
                } else {
                    n = n3;
                    l5 = l2;
                }
                l2 = l5;
                n3 = n;
            }
        } else {
            n3 = n2;
        }
        int n4 = this.config & 65535;
        short s = (short)(l4 >>> 32);
        n2 = n = (int)(l4 >> 48) + n4;
        if (n < 0) {
            n2 = 0;
        }
        object = ((n = this.runState) & 4) != 0 ? "Terminated" : ((n & 2) != 0 ? "Terminating" : ((Integer.MIN_VALUE & n) != 0 ? "Shutting down" : "Running"));
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(Object.super.toString());
        ((StringBuilder)object2).append("[");
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(", parallelism = ");
        ((StringBuilder)object2).append(n4);
        ((StringBuilder)object2).append(", size = ");
        ((StringBuilder)object2).append(s + n4);
        ((StringBuilder)object2).append(", active = ");
        ((StringBuilder)object2).append(n2);
        ((StringBuilder)object2).append(", running = ");
        ((StringBuilder)object2).append(n3);
        ((StringBuilder)object2).append(", steals = ");
        ((StringBuilder)object2).append(l3);
        ((StringBuilder)object2).append(", tasks = ");
        ((StringBuilder)object2).append(l);
        ((StringBuilder)object2).append(", submissions = ");
        ((StringBuilder)object2).append(l2);
        ((StringBuilder)object2).append("]");
        return ((StringBuilder)object2).toString();
    }

    final boolean tryExternalUnpush(ForkJoinTask<?> forkJoinTask) {
        int n;
        int n2 = ThreadLocalRandom.getProbe();
        Object object = this.workQueues;
        boolean bl = object != null && (n = ((WorkQueue[])object).length) > 0 && (object = object[n - 1 & n2 & 126]) != null && ((WorkQueue)object).trySharedUnpush(forkJoinTask);
        return bl;
    }

    private static final class AuxState
    extends ReentrantLock {
        private static final long serialVersionUID = -6001602636862214147L;
        long indexSeed;
        volatile long stealCount;

        AuxState() {
        }
    }

    private static final class DefaultForkJoinWorkerThreadFactory
    implements ForkJoinWorkerThreadFactory {
        private DefaultForkJoinWorkerThreadFactory() {
        }

        @Override
        public final ForkJoinWorkerThread newThread(ForkJoinPool forkJoinPool) {
            return new ForkJoinWorkerThread(forkJoinPool);
        }
    }

    private static final class EmptyTask
    extends ForkJoinTask<Void> {
        private static final long serialVersionUID = -7721805057305804111L;

        EmptyTask() {
            this.status = -268435456;
        }

        @Override
        public final boolean exec() {
            return true;
        }

        @Override
        public final Void getRawResult() {
            return null;
        }

        @Override
        public final void setRawResult(Void void_) {
        }
    }

    public static interface ForkJoinWorkerThreadFactory {
        public ForkJoinWorkerThread newThread(ForkJoinPool var1);
    }

    private static final class InnocuousForkJoinWorkerThreadFactory
    implements ForkJoinWorkerThreadFactory {
        private static final AccessControlContext innocuousAcc;

        static {
            Permissions permissions = new Permissions();
            permissions.add(modifyThreadPermission);
            permissions.add(new RuntimePermission("enableContextClassLoaderOverride"));
            permissions.add(new RuntimePermission("modifyThreadGroup"));
            innocuousAcc = new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, permissions)});
        }

        private InnocuousForkJoinWorkerThreadFactory() {
        }

        @Override
        public final ForkJoinWorkerThread newThread(final ForkJoinPool forkJoinPool) {
            return AccessController.doPrivileged(new PrivilegedAction<ForkJoinWorkerThread>(){

                @Override
                public ForkJoinWorkerThread run() {
                    return new ForkJoinWorkerThread.InnocuousForkJoinWorkerThread(forkJoinPool);
                }
            }, innocuousAcc);
        }

    }

    public static interface ManagedBlocker {
        public boolean block() throws InterruptedException;

        public boolean isReleasable();
    }

    static final class WorkQueue {
        private static final int ABASE;
        private static final int ASHIFT;
        static final int INITIAL_QUEUE_CAPACITY = 8192;
        static final int MAXIMUM_QUEUE_CAPACITY = 67108864;
        private static final long QLOCK;
        private static final Unsafe U;
        ForkJoinTask<?>[] array;
        volatile int base;
        int config;
        volatile ForkJoinTask<?> currentJoin;
        volatile ForkJoinTask<?> currentSteal;
        int hint;
        int nsteals;
        final ForkJoinWorkerThread owner;
        volatile Thread parker;
        final ForkJoinPool pool;
        volatile int qlock;
        volatile int scanState;
        int stackPred;
        int top;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static {
            U = Unsafe.getUnsafe();
            try {
                QLOCK = U.objectFieldOffset(WorkQueue.class.getDeclaredField("qlock"));
                ABASE = U.arrayBaseOffset(ForkJoinTask[].class);
                int n = U.arrayIndexScale(ForkJoinTask[].class);
                if ((n - 1 & n) == 0) {
                    ASHIFT = 31 - Integer.numberOfLeadingZeros(n);
                    return;
                }
                Error error = new Error("array index scale not a power of two");
                throw error;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new Error(reflectiveOperationException);
            }
        }

        WorkQueue(ForkJoinPool forkJoinPool, ForkJoinWorkerThread forkJoinWorkerThread) {
            this.pool = forkJoinPool;
            this.owner = forkJoinWorkerThread;
            this.top = 4096;
            this.base = 4096;
        }

        private void growAndSharedPush(ForkJoinTask<?> forkJoinTask) {
            block5 : {
                this.growArray();
                int n = this.top;
                ForkJoinTask<?>[] arrforkJoinTask = this.array;
                if (arrforkJoinTask == null) break block5;
                int n2 = arrforkJoinTask.length;
                if (n2 <= 0) break block5;
                arrforkJoinTask[n2 - 1 & n] = forkJoinTask;
                this.top = n + 1;
            }
            return;
            finally {
                this.qlock = 0;
            }
        }

        final void cancelAll() {
            ForkJoinTask<?> forkJoinTask = this.currentJoin;
            if (forkJoinTask != null) {
                this.currentJoin = null;
                ForkJoinTask.cancelIgnoringExceptions(forkJoinTask);
            }
            if ((forkJoinTask = this.currentSteal) != null) {
                this.currentSteal = null;
                ForkJoinTask.cancelIgnoringExceptions(forkJoinTask);
            }
            while ((forkJoinTask = this.poll()) != null) {
                ForkJoinTask.cancelIgnoringExceptions(forkJoinTask);
            }
        }

        final int getPoolIndex() {
            return (this.config & 65535) >>> 1;
        }

        final ForkJoinTask<?>[] growArray() {
            ForkJoinTask<?>[] arrforkJoinTask = this.array;
            int n = arrforkJoinTask != null ? arrforkJoinTask.length << 1 : 8192;
            if (n >= 8192 && n <= 67108864) {
                int n2;
                int n3;
                int n4;
                ForkJoinTask[] arrforkJoinTask2 = new ForkJoinTask[n];
                this.array = arrforkJoinTask2;
                if (arrforkJoinTask != null && (n3 = arrforkJoinTask.length - 1) > 0 && (n2 = this.top) - (n4 = this.base) > 0) {
                    int n5;
                    do {
                        long l;
                        ForkJoinTask forkJoinTask;
                        if ((forkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrforkJoinTask, l = ((long)(n4 & n3) << ASHIFT) + (long)ABASE)) != null && U.compareAndSwapObject(arrforkJoinTask, l, forkJoinTask, null)) {
                            arrforkJoinTask2[n4 & n - 1] = forkJoinTask;
                        }
                        n4 = n5 = n4 + 1;
                    } while (n5 != n2);
                    U.storeFence();
                }
                return arrforkJoinTask2;
            }
            throw new RejectedExecutionException("Queue capacity exceeded");
        }

        final boolean isApparentlyUnblocked() {
            Object object;
            boolean bl = this.scanState >= 0 && (object = this.owner) != null && (object = object.getState()) != Thread.State.BLOCKED && object != Thread.State.WAITING && object != Thread.State.TIMED_WAITING;
            return bl;
        }

        final boolean isEmpty() {
            ForkJoinTask<?>[] arrforkJoinTask;
            int n = this.base;
            int n2 = this.top;
            boolean bl = (n -= n2) >= 0 || n == -1 && ((arrforkJoinTask = this.array) == null || (n = arrforkJoinTask.length) == 0 || arrforkJoinTask[n - 1 & n2 - 1] == null);
            return bl;
        }

        final void localPollAndExec() {
            int n = 0;
            do {
                int n2 = this.base;
                int n3 = this.top;
                Object object = this.array;
                if (object == null || n2 == n3 || (n3 = ((ForkJoinTask<?>[])object).length) <= 0) break;
                long l = n2 & n3 - 1;
                n3 = ASHIFT;
                long l2 = ABASE;
                object = (ForkJoinTask)U.getAndSetObject(object, (l << n3) + l2, null);
                n3 = n++;
                if (object != null) {
                    this.base = n2 + 1;
                    ((ForkJoinTask)object).doExec();
                    n3 = n;
                    if (n > 1023) break;
                }
                n = n3;
            } while (true);
        }

        final void localPopAndExec() {
            int n = 0;
            do {
                int n2 = this.base;
                int n3 = this.top;
                Object object = this.array;
                if (object == null || n2 == n3 || (n2 = ((ForkJoinTask<?>[])object).length) <= 0) break;
                long l = n2 - 1 & --n3;
                n2 = ASHIFT;
                long l2 = ABASE;
                if ((object = (ForkJoinTask)U.getAndSetObject(object, (l << n2) + l2, null)) == null) break;
                this.top = n3;
                this.currentSteal = object;
                ((ForkJoinTask)object).doExec();
            } while (++n <= 1023);
        }

        final ForkJoinTask<?> nextLocalTask() {
            ForkJoinTask<?> forkJoinTask = this.config < 0 ? this.poll() : this.pop();
            return forkJoinTask;
        }

        final ForkJoinTask<?> peek() {
            int n;
            Object object = this.array;
            if (object != null && (n = ((ForkJoinTask<?>[])object).length) > 0) {
                int n2 = this.config < 0 ? this.base : this.top - 1;
                object = object[n - 1 & n2];
            } else {
                object = null;
            }
            return object;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        final ForkJoinTask<?> poll() {
            do lbl-1000: // 4 sources:
            {
                var1_1 = this.base;
                var2_2 = this.top;
                var3_3 = this.array;
                if (var3_3 == null) return null;
                if ((var2_2 = var1_1 - var2_2) >= 0) return null;
                var4_4 = var3_3.length;
                if (var4_4 <= 0) return null;
                var5_5 = ((long)(var4_4 - 1 & var1_1) << WorkQueue.ASHIFT) + (long)WorkQueue.ABASE;
                var7_6 = (ForkJoinTask)WorkQueue.U.getObjectVolatile(var3_3, var5_5);
                if (var1_1 != this.base) ** GOTO lbl-1000
                if (var7_6 == null) continue;
                if (!WorkQueue.U.compareAndSwapObject(var3_3, var5_5, var7_6, null)) ** GOTO lbl-1000
                this.base = var1_1 + 1;
                return var7_6;
            } while (var2_2 != -1);
            return null;
        }

        final int pollAndExecCC(CountedCompleter<?> countedCompleter) {
            int n;
            block10 : {
                int n2;
                int n3 = this.base;
                n = this.top;
                ForkJoinTask<?>[] arrforkJoinTask = this.array;
                if (arrforkJoinTask != null && n3 != n && (n2 = arrforkJoinTask.length) > 0) {
                    long l = ((long)(n2 - 1 & n3) << ASHIFT) + (long)ABASE;
                    CountedCompleter<?> countedCompleter2 = (CountedCompleter<?>)U.getObjectVolatile(arrforkJoinTask, l);
                    if (countedCompleter2 == null) {
                        n = 2;
                    } else if (!(countedCompleter2 instanceof CountedCompleter)) {
                        n = -1;
                    } else {
                        CountedCompleter<?> countedCompleter3;
                        CountedCompleter countedCompleter4 = countedCompleter2;
                        countedCompleter2 = countedCompleter4;
                        do {
                            if (countedCompleter2 == countedCompleter) {
                                if (n3 == this.base && U.compareAndSwapObject(arrforkJoinTask, l, countedCompleter4, null)) {
                                    this.base = n3 + 1;
                                    countedCompleter4.doExec();
                                    n = 1;
                                } else {
                                    n = 2;
                                }
                                break block10;
                            }
                            countedCompleter2 = countedCompleter3 = countedCompleter2.completer;
                        } while (countedCompleter3 != null);
                        n = -1;
                    }
                } else {
                    n = n3 | Integer.MIN_VALUE;
                }
            }
            return n;
        }

        final ForkJoinTask<?> pollAt(int n) {
            ForkJoinTask forkJoinTask;
            long l;
            int n2;
            ForkJoinTask<?>[] arrforkJoinTask = this.array;
            if (arrforkJoinTask != null && (n2 = arrforkJoinTask.length) > 0 && (forkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrforkJoinTask, l = ((long)(n2 - 1 & n) << ASHIFT) + (long)ABASE)) != null && n == this.base && U.compareAndSwapObject(arrforkJoinTask, l, forkJoinTask, null)) {
                this.base = n + 1;
                return forkJoinTask;
            }
            return null;
        }

        final ForkJoinTask<?> pop() {
            ForkJoinTask forkJoinTask;
            long l;
            int n = this.base;
            int n2 = this.top;
            ForkJoinTask<?>[] arrforkJoinTask = this.array;
            if (arrforkJoinTask != null && n != n2 && (n = arrforkJoinTask.length) > 0 && (forkJoinTask = (ForkJoinTask)U.getObject(arrforkJoinTask, l = ((long)(n - 1 & --n2) << ASHIFT) + (long)ABASE)) != null && U.compareAndSwapObject(arrforkJoinTask, l, forkJoinTask, null)) {
                this.top = n2;
                return forkJoinTask;
            }
            return null;
        }

        final CountedCompleter<?> popCC(CountedCompleter<?> countedCompleter, int n) {
            block8 : {
                CountedCompleter<?> countedCompleter2;
                ForkJoinTask forkJoinTask;
                CountedCompleter<?> countedCompleter3;
                long l;
                int n2 = this.base;
                int n3 = this.top;
                ForkJoinTask<?>[] arrforkJoinTask = this.array;
                if (arrforkJoinTask == null || n2 == n3 || (n2 = arrforkJoinTask.length) <= 0 || !((forkJoinTask = (ForkJoinTask)U.getObjectVolatile(arrforkJoinTask, l = ((long)(n2 = n2 - 1 & n3 - 1) << ASHIFT) + (long)ABASE)) instanceof CountedCompleter)) break block8;
                CountedCompleter<?> countedCompleter4 = countedCompleter3 = (CountedCompleter<?>)forkJoinTask;
                do {
                    if (countedCompleter4 == countedCompleter) {
                        if ((n & 1) == 0) {
                            n = 0;
                            if (U.compareAndSwapInt(this, QLOCK, 0, 1)) {
                                if (this.top == n3 && this.array == arrforkJoinTask && U.compareAndSwapObject(arrforkJoinTask, l, countedCompleter3, null)) {
                                    n = 1;
                                    this.top = n3 - 1;
                                }
                                U.putOrderedInt(this, QLOCK, 0);
                                if (n != 0) {
                                    return countedCompleter3;
                                }
                            }
                        } else if (U.compareAndSwapObject(arrforkJoinTask, l, countedCompleter3, null)) {
                            this.top = n3 - 1;
                            return countedCompleter3;
                        }
                        break;
                    }
                    countedCompleter4 = countedCompleter2 = countedCompleter4.completer;
                } while (countedCompleter2 != null);
            }
            return null;
        }

        final void push(ForkJoinTask<?> object) {
            int n;
            U.storeFence();
            int n2 = this.top;
            ForkJoinTask<?>[] arrforkJoinTask = this.array;
            if (arrforkJoinTask != null && (n = arrforkJoinTask.length) > 0) {
                arrforkJoinTask[n - 1 & n2] = object;
                this.top = n2 + 1;
                object = this.pool;
                if ((n2 = this.base - n2) == 0 && object != null) {
                    U.fullFence();
                    ((ForkJoinPool)object).signalWork();
                } else if (n + n2 == 1) {
                    this.growArray();
                }
            }
        }

        final int queueSize() {
            int n = this.base - this.top;
            n = n >= 0 ? 0 : -n;
            return n;
        }

        final void runTask(ForkJoinTask<?> object) {
            if (object != null) {
                int n;
                ((ForkJoinTask)object).doExec();
                if (this.config < 0) {
                    this.localPollAndExec();
                } else {
                    this.localPopAndExec();
                }
                this.nsteals = n = this.nsteals + 1;
                object = this.owner;
                this.currentSteal = null;
                if (n < 0) {
                    this.transferStealCount(this.pool);
                }
                if (object != null) {
                    ((ForkJoinWorkerThread)object).afterTopLevelExec();
                }
            }
        }

        final int sharedPush(ForkJoinTask<?> forkJoinTask) {
            int n;
            if (U.compareAndSwapInt(this, QLOCK, 0, 1)) {
                int n2;
                int n3;
                int n4 = this.base;
                n = this.top;
                ForkJoinTask<?>[] arrforkJoinTask = this.array;
                if (arrforkJoinTask != null && (n2 = arrforkJoinTask.length) > 0 && n2 - 1 + (n3 = n4 - n) > 0) {
                    arrforkJoinTask[n2 - 1 & n] = forkJoinTask;
                    this.top = n + 1;
                    n2 = 0;
                    this.qlock = 0;
                    n = n2;
                    if (n3 < 0) {
                        n = n2;
                        if (n4 == this.base) {
                            n = n3;
                        }
                    }
                } else {
                    this.growAndSharedPush(forkJoinTask);
                    n = 0;
                }
            } else {
                n = 1;
            }
            return n;
        }

        final void transferStealCount(ForkJoinPool forkJoinPool) {
            AuxState auxState;
            if (forkJoinPool != null && (auxState = forkJoinPool.auxState) != null) {
                long l = this.nsteals;
                this.nsteals = 0;
                long l2 = l;
                if (l < 0L) {
                    l2 = Integer.MAX_VALUE;
                }
                auxState.lock();
                try {
                    auxState.stealCount += l2;
                }
                finally {
                    auxState.unlock();
                }
            }
        }

        final boolean tryRemoveAndExec(ForkJoinTask<?> forkJoinTask) {
            block9 : {
                if (forkJoinTask != null && forkJoinTask.status >= 0) {
                    block10 : {
                        block0 : do {
                            long l;
                            int n;
                            int n2;
                            ForkJoinTask<?> forkJoinTask2;
                            ForkJoinTask<?>[] arrforkJoinTask;
                            int n3 = this.base;
                            int n4 = n2 = this.top;
                            n2 = n = n3 - n2;
                            if (n >= 0 || (arrforkJoinTask = this.array) == null || (n = arrforkJoinTask.length) <= 0) break block9;
                            while ((forkJoinTask2 = (ForkJoinTask<?>)U.getObjectVolatile(arrforkJoinTask, l = (long)(((n - 1 & --n4) << ASHIFT) + ABASE))) != null) {
                                if (forkJoinTask2 == forkJoinTask) {
                                    boolean bl = false;
                                    if (n4 + 1 == this.top) {
                                        if (U.compareAndSwapObject(arrforkJoinTask, l, forkJoinTask2, null)) {
                                            this.top = n4;
                                            bl = true;
                                        }
                                    } else if (this.base == n3) {
                                        bl = U.compareAndSwapObject(arrforkJoinTask, l, forkJoinTask2, new EmptyTask());
                                    }
                                    if (!bl) continue block0;
                                    forkJoinTask2 = this.currentSteal;
                                    this.currentSteal = forkJoinTask;
                                    forkJoinTask.doExec();
                                    this.currentSteal = forkJoinTask2;
                                    continue block0;
                                }
                                if (forkJoinTask2.status < 0 && n4 + 1 == this.top) {
                                    if (!U.compareAndSwapObject(arrforkJoinTask, l, forkJoinTask2, null)) continue block0;
                                    this.top = n4;
                                    continue block0;
                                }
                                if (++n2 != 0) continue;
                                if (this.base == n3) break block10;
                            }
                        } while (forkJoinTask.status >= 0);
                        return false;
                    }
                    return false;
                }
            }
            return true;
        }

        final boolean trySharedUnpush(ForkJoinTask<?> forkJoinTask) {
            boolean bl = false;
            boolean bl2 = false;
            int n = this.top - 1;
            ForkJoinTask<?>[] arrforkJoinTask = this.array;
            boolean bl3 = bl;
            if (arrforkJoinTask != null) {
                int n2 = arrforkJoinTask.length;
                bl3 = bl;
                if (n2 > 0) {
                    long l = ((long)(n2 - 1 & n) << ASHIFT) + (long)ABASE;
                    bl3 = bl;
                    if ((ForkJoinTask)U.getObject(arrforkJoinTask, l) == forkJoinTask) {
                        bl3 = bl;
                        if (U.compareAndSwapInt(this, QLOCK, 0, 1)) {
                            bl3 = bl2;
                            if (this.top == n + 1) {
                                bl3 = bl2;
                                if (this.array == arrforkJoinTask) {
                                    bl3 = bl2;
                                    if (U.compareAndSwapObject(arrforkJoinTask, l, forkJoinTask, null)) {
                                        bl3 = true;
                                        this.top = n;
                                    }
                                }
                            }
                            U.putOrderedInt(this, QLOCK, 0);
                        }
                    }
                }
            }
            return bl3;
        }

        final boolean tryUnpush(ForkJoinTask<?> forkJoinTask) {
            int n = this.base;
            int n2 = this.top;
            ForkJoinTask<?>[] arrforkJoinTask = this.array;
            if (arrforkJoinTask != null && n != n2 && (n = arrforkJoinTask.length) > 0) {
                long l = n - 1 & --n2;
                n = ASHIFT;
                long l2 = ABASE;
                if (U.compareAndSwapObject(arrforkJoinTask, (l << n) + l2, forkJoinTask, null)) {
                    this.top = n2;
                    return true;
                }
            }
            return false;
        }
    }

}

