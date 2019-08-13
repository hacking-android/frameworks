/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.lang.reflect.Field;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import sun.misc.Unsafe;

public class Phaser {
    private static final long COUNTS_MASK = 0xFFFFFFFFL;
    private static final int EMPTY = 1;
    private static final int MAX_PARTIES = 65535;
    private static final int MAX_PHASE = Integer.MAX_VALUE;
    private static final int NCPU = Runtime.getRuntime().availableProcessors();
    private static final int ONE_ARRIVAL = 1;
    private static final int ONE_DEREGISTER = 65537;
    private static final int ONE_PARTY = 65536;
    private static final long PARTIES_MASK = 0xFFFF0000L;
    private static final int PARTIES_SHIFT = 16;
    private static final int PHASE_SHIFT = 32;
    static final int SPINS_PER_ARRIVAL;
    private static final long STATE;
    private static final long TERMINATION_BIT = Long.MIN_VALUE;
    private static final Unsafe U;
    private static final int UNARRIVED_MASK = 65535;
    private final AtomicReference<QNode> evenQ;
    private final AtomicReference<QNode> oddQ;
    private final Phaser parent;
    private final Phaser root;
    private volatile long state;

    static {
        int n = NCPU < 2 ? 1 : 256;
        SPINS_PER_ARRIVAL = n;
        U = Unsafe.getUnsafe();
        try {
            STATE = U.objectFieldOffset(Phaser.class.getDeclaredField("state"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public Phaser() {
        this(null, 0);
    }

    public Phaser(int n) {
        this(null, n);
    }

    public Phaser(Phaser phaser) {
        this(phaser, 0);
    }

    public Phaser(Phaser phaser, int n) {
        if (n >>> 16 == 0) {
            int n2 = 0;
            int n3 = 0;
            this.parent = phaser;
            if (phaser != null) {
                Phaser phaser2;
                this.root = phaser2 = phaser.root;
                this.evenQ = phaser2.evenQ;
                this.oddQ = phaser2.oddQ;
                if (n != 0) {
                    n3 = phaser.doRegister(1);
                }
            } else {
                this.root = this;
                this.evenQ = new AtomicReference();
                this.oddQ = new AtomicReference();
                n3 = n2;
            }
            long l = n == 0 ? 1L : (long)n3 << 32 | (long)n << 16 | (long)n;
            this.state = l;
            return;
        }
        throw new IllegalArgumentException("Illegal number of parties");
    }

    private int abortWait(int n) {
        AtomicReference<QNode> atomicReference = (n & 1) == 0 ? this.evenQ : this.oddQ;
        do {
            Thread thread;
            QNode qNode = atomicReference.get();
            n = (int)(this.root.state >>> 32);
            if (qNode == null || (thread = qNode.thread) != null && qNode.phase == n) break;
            if (!atomicReference.compareAndSet(qNode, qNode.next) || thread == null) continue;
            qNode.thread = null;
            LockSupport.unpark(thread);
        } while (true);
        return n;
    }

    private static int arrivedOf(long l) {
        int n = (int)l;
        n = n == 1 ? 0 : (n >>> 16) - (65535 & n);
        return n;
    }

    private String badArrive(long l) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempted arrival of unregistered party for ");
        stringBuilder.append(this.stateToString(l));
        return stringBuilder.toString();
    }

    private String badRegister(long l) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempt to register more than 65535 parties for ");
        stringBuilder.append(this.stateToString(l));
        return stringBuilder.toString();
    }

    private int doArrive(int n) {
        long l;
        block7 : {
            Unsafe unsafe;
            int n2;
            long l2;
            long l3;
            int n3;
            Phaser phaser = this.root;
            do {
                if ((n3 = (int)((l = phaser == this ? this.state : this.reconcileState()) >>> 32)) < 0) {
                    return n3;
                }
                n2 = (int)l;
                n2 = n2 == 1 ? 0 : 65535 & n2;
                if (n2 <= 0) break block7;
            } while (!(unsafe = U).compareAndSwapLong(this, l3 = STATE, l, l2 = l - (long)n));
            n = n3;
            if (n2 == 1) {
                l = l2 & 0xFFFF0000L;
                n = (int)l >>> 16;
                if (phaser == this) {
                    l = this.onAdvance(n3, n) ? Long.MIN_VALUE | l : (n == 0 ? 1L | l : (long)n | l);
                    l3 = n3 + 1 & Integer.MAX_VALUE;
                    U.compareAndSwapLong(this, STATE, l2, l | l3 << 32);
                    this.releaseWaiters(n3);
                    n = n3;
                } else if (n == 0) {
                    n = this.parent.doArrive(65537);
                    U.compareAndSwapLong(this, STATE, l2, l2 | 1L);
                } else {
                    n = this.parent.doArrive(1);
                }
            }
            return n;
        }
        throw new IllegalStateException(this.badArrive(l));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private int doRegister(int n) {
        int n2 = n;
        long l = (long)n2 << 16 | (long)n2;
        Phaser phaser = this.parent;
        do {
            long l2;
            long l3 = phaser == null ? this.state : this.reconcileState();
            int n3 = (int)l3;
            if (n > 65535 - (n3 >>> 16)) throw new IllegalStateException(this.badRegister(l3));
            n2 = (int)(l3 >>> 32);
            if (n2 < 0) {
                return n2;
            }
            if (n3 != 1) {
                if (phaser != null && this.reconcileState() != l3) continue;
                if ((n3 & 65535) == 0) {
                    this.root.internalAwaitAdvance(n2, null);
                    continue;
                }
                Unsafe unsafe = U;
                l2 = STATE;
                if (!unsafe.compareAndSwapLong(this, l2, l3, l3 + l)) continue;
                return n2;
            }
            if (phaser == null) {
                l2 = n2;
                if (!U.compareAndSwapLong(this, STATE, l3, l2 << 32 | l)) continue;
                return n2;
            }
            // MONITORENTER : this
            n3 = n2;
            if (this.state == l3) {
                n3 = n2;
                n = phaser.doRegister(1);
                if (n < 0) {
                    // MONITOREXIT : this
                    return n;
                }
                do {
                    n3 = n;
                    if (U.compareAndSwapLong(this, STATE, l3, (long)n << 32 | l)) {
                        n3 = n;
                        // MONITOREXIT : this
                        return n;
                    }
                    n3 = n;
                    l3 = this.state;
                    n3 = n;
                    n = (int)(this.root.state >>> 32);
                } while (true);
            }
            n3 = n2;
            // MONITOREXIT : this
        } while (true);
        catch (Throwable throwable) {
            throw throwable;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int internalAwaitAdvance(int var1_1, QNode var2_2) {
        this.releaseWaiters(var1_1 - 1);
        var3_3 = 0;
        var4_4 = Phaser.SPINS_PER_ARRIVAL;
        var5_5 = false;
        do {
            block16 : {
                block18 : {
                    block17 : {
                        block15 : {
                            var6_6 = this.state;
                            var9_8 = var8_7 = (int)(var6_6 >>> 32);
                            if (var8_7 != var1_1) break;
                            if (var2_2 != null) break block15;
                            var8_7 = (int)var6_6 & 65535;
                            var9_8 = var3_3;
                            if (var8_7 == var3_3) ** GOTO lbl-1000
                            var9_8 = var3_3 = var8_7;
                            if (var8_7 < Phaser.NCPU) {
                                var4_4 += Phaser.SPINS_PER_ARRIVAL;
                            } else lbl-1000: // 2 sources:
                            {
                                var3_3 = var9_8;
                            }
                            var10_9 = Thread.interrupted();
                            var9_8 = var4_4;
                            if (!var10_9) {
                                var4_4 = var9_8 = var4_4 - 1;
                                if (var9_8 >= 0) continue;
                            }
                            var2_2 = new QNode(this, var1_1, false, false, 0L);
                            var2_2.wasInterrupted = var10_9;
                            var4_4 = var9_8;
                            continue;
                        }
                        if (var2_2.isReleasable()) break;
                        if (var5_5) break block16;
                        var11_10 = (var1_1 & 1) == 0 ? this.evenQ : this.oddQ;
                        var2_2.next = var12_12 = var11_10.get();
                        if (var12_12 == null) break block17;
                        var10_9 = var5_5;
                        if (var12_12.phase != var1_1) break block18;
                    }
                    var10_9 = var5_5;
                    if ((int)(this.state >>> 32) == var1_1) {
                        var10_9 = var11_10.compareAndSet(var12_12, var2_2);
                    }
                }
                var5_5 = var10_9;
                continue;
            }
            try {
                ForkJoinPool.managedBlock(var2_2);
            }
            catch (InterruptedException var11_11) {
                var2_2.wasInterrupted = true;
            }
        } while (true);
        var4_4 = var9_8;
        if (var2_2 != null) {
            if (var2_2.thread != null) {
                var2_2.thread = null;
            }
            if (var2_2.wasInterrupted && !var2_2.interruptible) {
                Thread.currentThread().interrupt();
            }
            var4_4 = var9_8;
            if (var9_8 == var1_1) {
                var4_4 = var3_3 = (int)(this.state >>> 32);
                if (var3_3 == var1_1) {
                    return this.abortWait(var1_1);
                }
            }
        }
        this.releaseWaiters(var1_1);
        return var4_4;
    }

    private static int partiesOf(long l) {
        return (int)l >>> 16;
    }

    private static int phaseOf(long l) {
        return (int)(l >>> 32);
    }

    private AtomicReference<QNode> queueFor(int n) {
        AtomicReference<QNode> atomicReference = (n & 1) == 0 ? this.evenQ : this.oddQ;
        return atomicReference;
    }

    private long reconcileState() {
        long l;
        Phaser phaser = this.root;
        long l2 = l = this.state;
        if (phaser != this) {
            int n;
            l2 = l;
            while ((n = (int)(phaser.state >>> 32)) != (int)(l2 >>> 32)) {
                Unsafe unsafe = U;
                long l3 = STATE;
                long l4 = n;
                l = n < 0 ? 0xFFFFFFFFL & l2 : ((n = (int)l2 >>> 16) == 0 ? 1L : 0xFFFF0000L & l2 | (long)n);
                if (!unsafe.compareAndSwapLong(this, l3, l2, l = l4 << 32 | l)) {
                    l2 = this.state;
                    continue;
                }
                l2 = l;
                break;
            }
        }
        return l2;
    }

    private void releaseWaiters(int n) {
        QNode qNode;
        AtomicReference<QNode> atomicReference = (n & 1) == 0 ? this.evenQ : this.oddQ;
        while ((qNode = atomicReference.get()) != null && qNode.phase != (int)(this.root.state >>> 32)) {
            Thread thread;
            if (!atomicReference.compareAndSet(qNode, qNode.next) || (thread = qNode.thread) == null) continue;
            qNode.thread = null;
            LockSupport.unpark(thread);
        }
    }

    private String stateToString(long l) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("[phase = ");
        stringBuilder.append(Phaser.phaseOf(l));
        stringBuilder.append(" parties = ");
        stringBuilder.append(Phaser.partiesOf(l));
        stringBuilder.append(" arrived = ");
        stringBuilder.append(Phaser.arrivedOf(l));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static int unarrivedOf(long l) {
        int n = (int)l;
        n = n == 1 ? 0 : 65535 & n;
        return n;
    }

    public int arrive() {
        return this.doArrive(1);
    }

    public int arriveAndAwaitAdvance() {
        long l;
        block5 : {
            int n;
            long l2;
            Unsafe unsafe;
            int n2;
            long l3;
            Phaser phaser = this.root;
            do {
                if ((n = (int)((l = phaser == this ? this.state : this.reconcileState()) >>> 32)) < 0) {
                    return n;
                }
                n2 = (int)l;
                n2 = n2 == 1 ? 0 : 65535 & n2;
                if (n2 <= 0) break block5;
            } while (!(unsafe = U).compareAndSwapLong(this, l2 = STATE, l, l3 = l - 1L));
            if (n2 > 1) {
                return phaser.internalAwaitAdvance(n, null);
            }
            if (phaser != this) {
                return this.parent.arriveAndAwaitAdvance();
            }
            l = l3 & 0xFFFF0000L;
            n2 = (int)l >>> 16;
            l = this.onAdvance(n, n2) ? (l |= Long.MIN_VALUE) : (n2 == 0 ? (l |= 1L) : (l |= (long)n2));
            n2 = n + 1 & Integer.MAX_VALUE;
            l2 = n2;
            if (!U.compareAndSwapLong(this, STATE, l3, l | l2 << 32)) {
                return (int)(this.state >>> 32);
            }
            this.releaseWaiters(n);
            return n2;
        }
        throw new IllegalStateException(this.badArrive(l));
    }

    public int arriveAndDeregister() {
        return this.doArrive(65537);
    }

    public int awaitAdvance(int n) {
        Phaser phaser = this.root;
        long l = phaser == this ? this.state : this.reconcileState();
        int n2 = (int)(l >>> 32);
        if (n < 0) {
            return n;
        }
        if (n2 == n) {
            return phaser.internalAwaitAdvance(n, null);
        }
        return n2;
    }

    public int awaitAdvanceInterruptibly(int n) throws InterruptedException {
        Phaser phaser = this.root;
        long l = phaser == this ? this.state : this.reconcileState();
        int n2 = (int)(l >>> 32);
        if (n < 0) {
            return n;
        }
        int n3 = n2;
        if (n2 == n) {
            QNode qNode = new QNode(this, n, true, false, 0L);
            n3 = phaser.internalAwaitAdvance(n, qNode);
            if (qNode.wasInterrupted) {
                throw new InterruptedException();
            }
        }
        return n3;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int awaitAdvanceInterruptibly(int n, long l, TimeUnit object) throws InterruptedException, TimeoutException {
        long l2 = ((TimeUnit)((Object)object)).toNanos(l);
        Phaser phaser = this.root;
        l = phaser == this ? this.state : this.reconcileState();
        int n2 = (int)(l >>> 32);
        if (n < 0) {
            return n;
        }
        if (n2 != n) return n2;
        QNode qNode = new QNode(this, n, true, true, l2);
        n2 = phaser.internalAwaitAdvance(n, qNode);
        if (qNode.wasInterrupted) throw new InterruptedException();
        if (n2 == n) throw new TimeoutException();
        return n2;
    }

    public int bulkRegister(int n) {
        if (n >= 0) {
            if (n == 0) {
                return this.getPhase();
            }
            return this.doRegister(n);
        }
        throw new IllegalArgumentException();
    }

    public void forceTermination() {
        long l;
        Phaser phaser = this.root;
        while ((l = phaser.state) >= 0L) {
            if (!U.compareAndSwapLong(phaser, STATE, l, l | Long.MIN_VALUE)) continue;
            this.releaseWaiters(0);
            this.releaseWaiters(1);
            return;
        }
    }

    public int getArrivedParties() {
        return Phaser.arrivedOf(this.reconcileState());
    }

    public Phaser getParent() {
        return this.parent;
    }

    public final int getPhase() {
        return (int)(this.root.state >>> 32);
    }

    public int getRegisteredParties() {
        return Phaser.partiesOf(this.state);
    }

    public Phaser getRoot() {
        return this.root;
    }

    public int getUnarrivedParties() {
        return Phaser.unarrivedOf(this.reconcileState());
    }

    public boolean isTerminated() {
        boolean bl = this.root.state < 0L;
        return bl;
    }

    protected boolean onAdvance(int n, int n2) {
        boolean bl = n2 == 0;
        return bl;
    }

    public int register() {
        return this.doRegister(1);
    }

    public String toString() {
        return this.stateToString(this.reconcileState());
    }

    static final class QNode
    implements ForkJoinPool.ManagedBlocker {
        final long deadline;
        final boolean interruptible;
        long nanos;
        QNode next;
        final int phase;
        final Phaser phaser;
        volatile Thread thread;
        final boolean timed;
        boolean wasInterrupted;

        QNode(Phaser phaser, int n, boolean bl, boolean bl2, long l) {
            this.phaser = phaser;
            this.phase = n;
            this.interruptible = bl;
            this.nanos = l;
            this.timed = bl2;
            l = bl2 ? System.nanoTime() + l : 0L;
            this.deadline = l;
            this.thread = Thread.currentThread();
        }

        @Override
        public boolean block() {
            while (!this.isReleasable()) {
                if (this.timed) {
                    LockSupport.parkNanos(this, this.nanos);
                    continue;
                }
                LockSupport.park(this);
            }
            return true;
        }

        @Override
        public boolean isReleasable() {
            block7 : {
                block8 : {
                    long l;
                    if (this.thread == null) {
                        return true;
                    }
                    if (this.phaser.getPhase() != this.phase) {
                        this.thread = null;
                        return true;
                    }
                    if (Thread.interrupted()) {
                        this.wasInterrupted = true;
                    }
                    if (this.wasInterrupted && this.interruptible) {
                        this.thread = null;
                        return true;
                    }
                    if (!this.timed) break block7;
                    if (this.nanos <= 0L) break block8;
                    this.nanos = l = this.deadline - System.nanoTime();
                    if (l > 0L) break block7;
                }
                this.thread = null;
                return true;
            }
            return false;
        }
    }

}

