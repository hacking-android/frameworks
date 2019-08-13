/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.locks;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import sun.misc.Unsafe;

public abstract class AbstractQueuedLongSynchronizer
extends AbstractOwnableSynchronizer
implements Serializable {
    private static final long HEAD;
    static final long SPIN_FOR_TIMEOUT_THRESHOLD = 1000L;
    private static final long STATE;
    private static final long TAIL;
    private static final Unsafe U;
    private static final long serialVersionUID = 7373984972572414692L;
    private volatile transient AbstractQueuedSynchronizer.Node head;
    private volatile long state;
    private volatile transient AbstractQueuedSynchronizer.Node tail;

    static {
        U = Unsafe.getUnsafe();
        try {
            STATE = U.objectFieldOffset(AbstractQueuedLongSynchronizer.class.getDeclaredField("state"));
            HEAD = U.objectFieldOffset(AbstractQueuedLongSynchronizer.class.getDeclaredField("head"));
            TAIL = U.objectFieldOffset(AbstractQueuedLongSynchronizer.class.getDeclaredField("tail"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    protected AbstractQueuedLongSynchronizer() {
    }

    private AbstractQueuedSynchronizer.Node addWaiter(AbstractQueuedSynchronizer.Node node) {
        AbstractQueuedSynchronizer.Node node2 = new AbstractQueuedSynchronizer.Node(node);
        do {
            if ((node = this.tail) != null) {
                U.putObject(node2, AbstractQueuedSynchronizer.Node.PREV, node);
                if (!this.compareAndSetTail(node, node2)) continue;
                node.next = node2;
                return node2;
            }
            this.initializeSyncQueue();
        } while (true);
    }

    private void cancelAcquire(AbstractQueuedSynchronizer.Node node) {
        AbstractQueuedSynchronizer.Node node2;
        if (node == null) {
            return;
        }
        node.thread = null;
        AbstractQueuedSynchronizer.Node node3 = node.prev;
        while (node3.waitStatus > 0) {
            node3 = node2 = node3.prev;
            node.prev = node2;
        }
        AbstractQueuedSynchronizer.Node node4 = node3.next;
        node.waitStatus = 1;
        if (node == this.tail && this.compareAndSetTail(node, node3)) {
            node3.compareAndSetNext(node4, null);
        } else {
            int n;
            if (node3 != this.head && ((n = node3.waitStatus) == -1 || n <= 0 && node3.compareAndSetWaitStatus(n, -1)) && node3.thread != null) {
                node2 = node.next;
                if (node2 != null && node2.waitStatus <= 0) {
                    node3.compareAndSetNext(node4, node2);
                }
            } else {
                this.unparkSuccessor(node);
            }
            node.next = node;
        }
    }

    private final boolean compareAndSetTail(AbstractQueuedSynchronizer.Node node, AbstractQueuedSynchronizer.Node node2) {
        return U.compareAndSwapObject(this, TAIL, node, node2);
    }

    private void doAcquireInterruptibly(long l) throws InterruptedException {
        AbstractQueuedSynchronizer.Node node = this.addWaiter(AbstractQueuedSynchronizer.Node.EXCLUSIVE);
        try {
            Object object;
            do {
                if ((object = node.predecessor()) != this.head || !this.tryAcquire(l)) continue;
                this.setHead(node);
                ((AbstractQueuedSynchronizer.Node)object).next = null;
                return;
            } while (!AbstractQueuedLongSynchronizer.shouldParkAfterFailedAcquire((AbstractQueuedSynchronizer.Node)object, node) || !this.parkAndCheckInterrupt());
            object = new InterruptedException();
            throw object;
        }
        catch (Throwable throwable) {
            this.cancelAcquire(node);
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean doAcquireNanos(long l, long l2) throws InterruptedException {
        if (l2 <= 0L) {
            return false;
        }
        long l3 = System.nanoTime();
        AbstractQueuedSynchronizer.Node node = this.addWaiter(AbstractQueuedSynchronizer.Node.EXCLUSIVE);
        try {
            Object object;
            do {
                if ((object = node.predecessor()) == this.head && this.tryAcquire(l)) {
                    this.setHead(node);
                    ((AbstractQueuedSynchronizer.Node)object).next = null;
                    return true;
                }
                long l4 = l3 + l2 - System.nanoTime();
                if (l4 <= 0L) {
                    this.cancelAcquire(node);
                    return false;
                }
                if (!AbstractQueuedLongSynchronizer.shouldParkAfterFailedAcquire((AbstractQueuedSynchronizer.Node)object, node) || l4 <= 1000L) continue;
                LockSupport.parkNanos(this, l4);
            } while (!Thread.interrupted());
            object = new InterruptedException();
            throw object;
        }
        catch (Throwable throwable) {
            this.cancelAcquire(node);
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void doAcquireShared(long l) {
        AbstractQueuedSynchronizer.Node node2 = this.addWaiter(AbstractQueuedSynchronizer.Node.SHARED);
        boolean bl = false;
        try {
            do {
                AbstractQueuedSynchronizer.Node node;
                long l2;
                if ((node = node2.predecessor()) == this.head && (l2 = this.tryAcquireShared(l)) >= 0L) {
                    this.setHeadAndPropagate(node2, l2);
                    node.next = null;
                    if (bl) {
                        AbstractQueuedLongSynchronizer.selfInterrupt();
                    }
                    return;
                }
                boolean bl2 = bl;
                if (AbstractQueuedLongSynchronizer.shouldParkAfterFailedAcquire(node, node2)) {
                    boolean bl3 = this.parkAndCheckInterrupt();
                    bl2 = bl;
                    if (bl3) {
                        bl2 = true;
                    }
                }
                bl = bl2;
            } while (true);
        }
        catch (Throwable throwable) {
            this.cancelAcquire(node2);
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void doAcquireSharedInterruptibly(long l) throws InterruptedException {
        AbstractQueuedSynchronizer.Node node = this.addWaiter(AbstractQueuedSynchronizer.Node.SHARED);
        try {
            Object object;
            do {
                long l2;
                if ((object = node.predecessor()) != this.head || (l2 = this.tryAcquireShared(l)) < 0L) continue;
                this.setHeadAndPropagate(node, l2);
                ((AbstractQueuedSynchronizer.Node)object).next = null;
                return;
            } while (!AbstractQueuedLongSynchronizer.shouldParkAfterFailedAcquire((AbstractQueuedSynchronizer.Node)object, node) || !this.parkAndCheckInterrupt());
            object = new InterruptedException();
            throw object;
        }
        catch (Throwable throwable) {
            this.cancelAcquire(node);
            throw throwable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean doAcquireSharedNanos(long l, long l2) throws InterruptedException {
        if (l2 <= 0L) {
            return false;
        }
        long l3 = System.nanoTime();
        AbstractQueuedSynchronizer.Node node = this.addWaiter(AbstractQueuedSynchronizer.Node.SHARED);
        try {
            Object object;
            do {
                long l4;
                if ((object = node.predecessor()) == this.head && (l4 = this.tryAcquireShared(l)) >= 0L) {
                    this.setHeadAndPropagate(node, l4);
                    ((AbstractQueuedSynchronizer.Node)object).next = null;
                    return true;
                }
                l4 = l3 + l2 - System.nanoTime();
                if (l4 <= 0L) {
                    this.cancelAcquire(node);
                    return false;
                }
                if (!AbstractQueuedLongSynchronizer.shouldParkAfterFailedAcquire((AbstractQueuedSynchronizer.Node)object, node) || l4 <= 1000L) continue;
                LockSupport.parkNanos(this, l4);
            } while (!Thread.interrupted());
            object = new InterruptedException();
            throw object;
        }
        catch (Throwable throwable) {
            this.cancelAcquire(node);
            throw throwable;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void doReleaseShared() {
        do lbl-1000: // 4 sources:
        {
            block1 : {
                if ((var1_1 = this.head) == null || var1_1 == this.tail) continue;
                var2_2 = var1_1.waitStatus;
                if (var2_2 != -1) break block1;
                if (!var1_1.compareAndSetWaitStatus(-1, 0)) ** GOTO lbl-1000
                this.unparkSuccessor(var1_1);
                continue;
            }
            if (var2_2 == 0 && !var1_1.compareAndSetWaitStatus(0, -3)) ** GOTO lbl-1000
        } while (var1_1 != this.head);
    }

    private AbstractQueuedSynchronizer.Node enq(AbstractQueuedSynchronizer.Node node) {
        do {
            AbstractQueuedSynchronizer.Node node2;
            if ((node2 = this.tail) != null) {
                U.putObject(node, AbstractQueuedSynchronizer.Node.PREV, node2);
                if (!this.compareAndSetTail(node2, node)) continue;
                node2.next = node;
                return node2;
            }
            this.initializeSyncQueue();
        } while (true);
    }

    private boolean findNodeFromTail(AbstractQueuedSynchronizer.Node node) {
        AbstractQueuedSynchronizer.Node node2 = this.tail;
        while (node2 != node) {
            if (node2 == null) {
                return false;
            }
            node2 = node2.prev;
        }
        return true;
    }

    private Thread fullGetFirstQueuedThread() {
        Object object;
        Thread thread;
        block7 : {
            block6 : {
                block5 : {
                    object = this.head;
                    if (object == null || (object = ((AbstractQueuedSynchronizer.Node)object).next) == null || ((AbstractQueuedSynchronizer.Node)object).prev != this.head) break block5;
                    thread = ((AbstractQueuedSynchronizer.Node)object).thread;
                    object = thread;
                    if (thread != null) break block6;
                }
                if ((object = this.head) == null || (object = ((AbstractQueuedSynchronizer.Node)object).next) == null || ((AbstractQueuedSynchronizer.Node)object).prev != this.head) break block7;
                thread = ((AbstractQueuedSynchronizer.Node)object).thread;
                object = thread;
                if (thread == null) break block7;
            }
            return object;
        }
        thread = null;
        object = this.tail;
        while (object != null && object != this.head) {
            Thread thread2 = ((AbstractQueuedSynchronizer.Node)object).thread;
            if (thread2 != null) {
                thread = thread2;
            }
            object = ((AbstractQueuedSynchronizer.Node)object).prev;
        }
        return thread;
    }

    private final void initializeSyncQueue() {
        Unsafe unsafe = U;
        long l = HEAD;
        AbstractQueuedSynchronizer.Node node = new AbstractQueuedSynchronizer.Node();
        if (unsafe.compareAndSwapObject(this, l, null, node)) {
            this.tail = node;
        }
    }

    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }

    static void selfInterrupt() {
        Thread.currentThread().interrupt();
    }

    private void setHead(AbstractQueuedSynchronizer.Node node) {
        this.head = node;
        node.thread = null;
        node.prev = null;
    }

    private void setHeadAndPropagate(AbstractQueuedSynchronizer.Node node, long l) {
        AbstractQueuedSynchronizer.Node node2 = this.head;
        this.setHead(node);
        if (!(l <= 0L && node2 != null && node2.waitStatus >= 0 && (node2 = this.head) != null && node2.waitStatus >= 0 || (node = node.next) != null && !node.isShared())) {
            this.doReleaseShared();
        }
    }

    private static boolean shouldParkAfterFailedAcquire(AbstractQueuedSynchronizer.Node node, AbstractQueuedSynchronizer.Node node2) {
        int n = node.waitStatus;
        if (n == -1) {
            return true;
        }
        if (n > 0) {
            AbstractQueuedSynchronizer.Node node3 = node;
            do {
                node = node3 = node3.prev;
                node2.prev = node3;
                node3 = node;
            } while (node.waitStatus > 0);
            node.next = node2;
        } else {
            node.compareAndSetWaitStatus(n, -1);
        }
        return false;
    }

    private void unparkSuccessor(AbstractQueuedSynchronizer.Node node) {
        AbstractQueuedSynchronizer.Node node2;
        block8 : {
            AbstractQueuedSynchronizer.Node node3;
            block7 : {
                int n = node.waitStatus;
                if (n < 0) {
                    node.compareAndSetWaitStatus(n, 0);
                }
                if ((node3 = node.next) == null) break block7;
                node2 = node3;
                if (node3.waitStatus <= 0) break block8;
            }
            AbstractQueuedSynchronizer.Node node4 = null;
            node3 = this.tail;
            do {
                node2 = node4;
                if (node3 == node) break;
                node2 = node4;
                if (node3 == null) break;
                if (node3.waitStatus <= 0) {
                    node4 = node3;
                }
                node3 = node3.prev;
            } while (true);
        }
        if (node2 != null) {
            LockSupport.unpark(node2.thread);
        }
    }

    public final void acquire(long l) {
        if (!this.tryAcquire(l) && this.acquireQueued(this.addWaiter(AbstractQueuedSynchronizer.Node.EXCLUSIVE), l)) {
            AbstractQueuedLongSynchronizer.selfInterrupt();
        }
    }

    public final void acquireInterruptibly(long l) throws InterruptedException {
        if (!Thread.interrupted()) {
            if (!this.tryAcquire(l)) {
                this.doAcquireInterruptibly(l);
            }
            return;
        }
        throw new InterruptedException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final boolean acquireQueued(AbstractQueuedSynchronizer.Node node, long l) {
        boolean bl2 = false;
        try {
            do {
                AbstractQueuedSynchronizer.Node node2;
                if ((node2 = node.predecessor()) == this.head && this.tryAcquire(l)) {
                    this.setHead(node);
                    node2.next = null;
                    return bl2;
                }
                boolean bl = bl2;
                if (AbstractQueuedLongSynchronizer.shouldParkAfterFailedAcquire(node2, node)) {
                    boolean bl3 = this.parkAndCheckInterrupt();
                    bl = bl2;
                    if (bl3) {
                        bl = true;
                    }
                }
                bl2 = bl;
            } while (true);
        }
        catch (Throwable throwable) {
            this.cancelAcquire(node);
            throw throwable;
        }
    }

    public final void acquireShared(long l) {
        if (this.tryAcquireShared(l) < 0L) {
            this.doAcquireShared(l);
        }
    }

    public final void acquireSharedInterruptibly(long l) throws InterruptedException {
        if (!Thread.interrupted()) {
            if (this.tryAcquireShared(l) < 0L) {
                this.doAcquireSharedInterruptibly(l);
            }
            return;
        }
        throw new InterruptedException();
    }

    final boolean apparentlyFirstQueuedIsExclusive() {
        AbstractQueuedSynchronizer.Node node = this.head;
        boolean bl = node != null && (node = node.next) != null && !node.isShared() && node.thread != null;
        return bl;
    }

    protected final boolean compareAndSetState(long l, long l2) {
        return U.compareAndSwapLong(this, STATE, l, l2);
    }

    final long fullyRelease(AbstractQueuedSynchronizer.Node node) {
        block3 : {
            try {
                long l = this.getState();
                if (!this.release(l)) break block3;
                return l;
            }
            catch (Throwable throwable) {
                node.waitStatus = 1;
                throw throwable;
            }
        }
        IllegalMonitorStateException illegalMonitorStateException = new IllegalMonitorStateException();
        throw illegalMonitorStateException;
    }

    public final Collection<Thread> getExclusiveQueuedThreads() {
        ArrayList<Thread> arrayList = new ArrayList<Thread>();
        AbstractQueuedSynchronizer.Node node = this.tail;
        while (node != null) {
            Thread thread;
            if (!node.isShared() && (thread = node.thread) != null) {
                arrayList.add(thread);
            }
            node = node.prev;
        }
        return arrayList;
    }

    public final Thread getFirstQueuedThread() {
        Thread thread = this.head == this.tail ? null : this.fullGetFirstQueuedThread();
        return thread;
    }

    public final int getQueueLength() {
        int n = 0;
        AbstractQueuedSynchronizer.Node node = this.tail;
        while (node != null) {
            int n2 = n;
            if (node.thread != null) {
                n2 = n + 1;
            }
            node = node.prev;
            n = n2;
        }
        return n;
    }

    public final Collection<Thread> getQueuedThreads() {
        ArrayList<Thread> arrayList = new ArrayList<Thread>();
        AbstractQueuedSynchronizer.Node node = this.tail;
        while (node != null) {
            Thread thread = node.thread;
            if (thread != null) {
                arrayList.add(thread);
            }
            node = node.prev;
        }
        return arrayList;
    }

    public final Collection<Thread> getSharedQueuedThreads() {
        ArrayList<Thread> arrayList = new ArrayList<Thread>();
        AbstractQueuedSynchronizer.Node node = this.tail;
        while (node != null) {
            Thread thread;
            if (node.isShared() && (thread = node.thread) != null) {
                arrayList.add(thread);
            }
            node = node.prev;
        }
        return arrayList;
    }

    protected final long getState() {
        return this.state;
    }

    public final int getWaitQueueLength(ConditionObject conditionObject) {
        if (this.owns(conditionObject)) {
            return conditionObject.getWaitQueueLength();
        }
        throw new IllegalArgumentException("Not owner");
    }

    public final Collection<Thread> getWaitingThreads(ConditionObject conditionObject) {
        if (this.owns(conditionObject)) {
            return conditionObject.getWaitingThreads();
        }
        throw new IllegalArgumentException("Not owner");
    }

    public final boolean hasContended() {
        boolean bl = this.head != null;
        return bl;
    }

    public final boolean hasQueuedPredecessors() {
        AbstractQueuedSynchronizer.Node node = this.head;
        AbstractQueuedSynchronizer.Node node2 = this.tail;
        boolean bl = node != node2 && ((node = node.next) == null || node.thread != Thread.currentThread());
        return bl;
    }

    public final boolean hasQueuedThreads() {
        boolean bl = this.head != this.tail;
        return bl;
    }

    public final boolean hasWaiters(ConditionObject conditionObject) {
        if (this.owns(conditionObject)) {
            return conditionObject.hasWaiters();
        }
        throw new IllegalArgumentException("Not owner");
    }

    protected boolean isHeldExclusively() {
        throw new UnsupportedOperationException();
    }

    final boolean isOnSyncQueue(AbstractQueuedSynchronizer.Node node) {
        if (node.waitStatus != -2 && node.prev != null) {
            if (node.next != null) {
                return true;
            }
            return this.findNodeFromTail(node);
        }
        return false;
    }

    public final boolean isQueued(Thread thread) {
        if (thread != null) {
            AbstractQueuedSynchronizer.Node node = this.tail;
            while (node != null) {
                if (node.thread == thread) {
                    return true;
                }
                node = node.prev;
            }
            return false;
        }
        throw new NullPointerException();
    }

    public final boolean owns(ConditionObject conditionObject) {
        return conditionObject.isOwnedBy(this);
    }

    public final boolean release(long l) {
        if (this.tryRelease(l)) {
            AbstractQueuedSynchronizer.Node node = this.head;
            if (node != null && node.waitStatus != 0) {
                this.unparkSuccessor(node);
            }
            return true;
        }
        return false;
    }

    public final boolean releaseShared(long l) {
        if (this.tryReleaseShared(l)) {
            this.doReleaseShared();
            return true;
        }
        return false;
    }

    protected final void setState(long l) {
        U.putLongVolatile(this, STATE, l);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Object.super.toString());
        stringBuilder.append("[State = ");
        stringBuilder.append(this.getState());
        stringBuilder.append(", ");
        String string = this.hasQueuedThreads() ? "non" : "";
        stringBuilder.append(string);
        stringBuilder.append("empty queue]");
        return stringBuilder.toString();
    }

    final boolean transferAfterCancelledWait(AbstractQueuedSynchronizer.Node node) {
        if (node.compareAndSetWaitStatus(-2, 0)) {
            this.enq(node);
            return true;
        }
        while (!this.isOnSyncQueue(node)) {
            Thread.yield();
        }
        return false;
    }

    final boolean transferForSignal(AbstractQueuedSynchronizer.Node node) {
        if (!node.compareAndSetWaitStatus(-2, 0)) {
            return false;
        }
        AbstractQueuedSynchronizer.Node node2 = this.enq(node);
        int n = node2.waitStatus;
        if (n > 0 || !node2.compareAndSetWaitStatus(n, -1)) {
            LockSupport.unpark(node.thread);
        }
        return true;
    }

    protected boolean tryAcquire(long l) {
        throw new UnsupportedOperationException();
    }

    public final boolean tryAcquireNanos(long l, long l2) throws InterruptedException {
        if (!Thread.interrupted()) {
            boolean bl = this.tryAcquire(l) || this.doAcquireNanos(l, l2);
            return bl;
        }
        throw new InterruptedException();
    }

    protected long tryAcquireShared(long l) {
        throw new UnsupportedOperationException();
    }

    public final boolean tryAcquireSharedNanos(long l, long l2) throws InterruptedException {
        if (!Thread.interrupted()) {
            boolean bl = this.tryAcquireShared(l) >= 0L || this.doAcquireSharedNanos(l, l2);
            return bl;
        }
        throw new InterruptedException();
    }

    protected boolean tryRelease(long l) {
        throw new UnsupportedOperationException();
    }

    protected boolean tryReleaseShared(long l) {
        throw new UnsupportedOperationException();
    }

    public class ConditionObject
    implements Condition,
    Serializable {
        private static final int REINTERRUPT = 1;
        private static final int THROW_IE = -1;
        private static final long serialVersionUID = 1173984872572414699L;
        private transient AbstractQueuedSynchronizer.Node firstWaiter;
        private transient AbstractQueuedSynchronizer.Node lastWaiter;

        private AbstractQueuedSynchronizer.Node addConditionWaiter() {
            AbstractQueuedSynchronizer.Node node;
            AbstractQueuedSynchronizer.Node node2 = node = this.lastWaiter;
            if (node != null) {
                node2 = node;
                if (node.waitStatus != -2) {
                    this.unlinkCancelledWaiters();
                    node2 = this.lastWaiter;
                }
            }
            node = new AbstractQueuedSynchronizer.Node(-2);
            if (node2 == null) {
                this.firstWaiter = node;
            } else {
                node2.nextWaiter = node;
            }
            this.lastWaiter = node;
            return node;
        }

        private int checkInterruptWhileWaiting(AbstractQueuedSynchronizer.Node node) {
            int n = Thread.interrupted() ? (AbstractQueuedLongSynchronizer.this.transferAfterCancelledWait(node) ? -1 : 1) : 0;
            return n;
        }

        private void doSignal(AbstractQueuedSynchronizer.Node node) {
            AbstractQueuedSynchronizer.Node node2;
            do {
                this.firstWaiter = node2 = node.nextWaiter;
                if (node2 == null) {
                    this.lastWaiter = null;
                }
                node.nextWaiter = null;
                if (AbstractQueuedLongSynchronizer.this.transferForSignal(node)) break;
                node = node2 = this.firstWaiter;
            } while (node2 != null);
        }

        private void doSignalAll(AbstractQueuedSynchronizer.Node node) {
            AbstractQueuedSynchronizer.Node node2;
            this.firstWaiter = null;
            this.lastWaiter = null;
            do {
                node2 = node.nextWaiter;
                node.nextWaiter = null;
                AbstractQueuedLongSynchronizer.this.transferForSignal(node);
            } while ((node = node2) != null);
        }

        private void reportInterruptAfterWait(int n) throws InterruptedException {
            if (n != -1) {
                if (n == 1) {
                    AbstractQueuedLongSynchronizer.selfInterrupt();
                }
                return;
            }
            throw new InterruptedException();
        }

        private void unlinkCancelledWaiters() {
            AbstractQueuedSynchronizer.Node node = this.firstWaiter;
            AbstractQueuedSynchronizer.Node node2 = null;
            while (node != null) {
                AbstractQueuedSynchronizer.Node node3;
                AbstractQueuedSynchronizer.Node node4 = node.nextWaiter;
                if (node.waitStatus != -2) {
                    node.nextWaiter = null;
                    if (node2 == null) {
                        this.firstWaiter = node4;
                    } else {
                        node2.nextWaiter = node4;
                    }
                    node3 = node2;
                    if (node4 == null) {
                        this.lastWaiter = node2;
                        node3 = node2;
                    }
                } else {
                    node3 = node;
                }
                node = node4;
                node2 = node3;
            }
        }

        @Override
        public final void await() throws InterruptedException {
            if (!Thread.interrupted()) {
                int n;
                AbstractQueuedSynchronizer.Node node = this.addConditionWaiter();
                long l = AbstractQueuedLongSynchronizer.this.fullyRelease(node);
                int n2 = 0;
                while (!AbstractQueuedLongSynchronizer.this.isOnSyncQueue(node)) {
                    int n3;
                    LockSupport.park(this);
                    n2 = n = (n3 = this.checkInterruptWhileWaiting(node));
                    if (n3 == 0) continue;
                    n2 = n;
                    break;
                }
                n = n2;
                if (AbstractQueuedLongSynchronizer.this.acquireQueued(node, l)) {
                    n = n2;
                    if (n2 != -1) {
                        n = 1;
                    }
                }
                if (node.nextWaiter != null) {
                    this.unlinkCancelledWaiters();
                }
                if (n != 0) {
                    this.reportInterruptAfterWait(n);
                }
                return;
            }
            throw new InterruptedException();
        }

        @Override
        public final boolean await(long l, TimeUnit object) throws InterruptedException {
            long l2 = object.toNanos(l);
            if (!Thread.interrupted()) {
                int n;
                boolean bl;
                long l3 = System.nanoTime();
                object = this.addConditionWaiter();
                long l4 = AbstractQueuedLongSynchronizer.this.fullyRelease((AbstractQueuedSynchronizer.Node)object);
                boolean bl2 = false;
                int n2 = 0;
                l = l2;
                do {
                    bl = bl2;
                    n = n2;
                    if (AbstractQueuedLongSynchronizer.this.isOnSyncQueue((AbstractQueuedSynchronizer.Node)object)) break;
                    if (l <= 0L) {
                        bl = AbstractQueuedLongSynchronizer.this.transferAfterCancelledWait((AbstractQueuedSynchronizer.Node)object);
                        n = n2;
                        break;
                    }
                    if (l > 1000L) {
                        LockSupport.parkNanos(this, l);
                    }
                    n = n2 = this.checkInterruptWhileWaiting((AbstractQueuedSynchronizer.Node)object);
                    if (n2 != 0) {
                        bl = bl2;
                        break;
                    }
                    l = l3 + l2 - System.nanoTime();
                    n2 = n;
                } while (true);
                n2 = n;
                if (AbstractQueuedLongSynchronizer.this.acquireQueued((AbstractQueuedSynchronizer.Node)object, l4)) {
                    n2 = n;
                    if (n != -1) {
                        n2 = 1;
                    }
                }
                if (((AbstractQueuedSynchronizer.Node)object).nextWaiter != null) {
                    this.unlinkCancelledWaiters();
                }
                if (n2 != 0) {
                    this.reportInterruptAfterWait(n2);
                }
                bl = !bl;
                return bl;
            }
            throw new InterruptedException();
        }

        @Override
        public final long awaitNanos(long l) throws InterruptedException {
            if (!Thread.interrupted()) {
                int n;
                long l2 = System.nanoTime() + l;
                AbstractQueuedSynchronizer.Node node = this.addConditionWaiter();
                long l3 = AbstractQueuedLongSynchronizer.this.fullyRelease(node);
                int n2 = 0;
                long l4 = l;
                do {
                    n = n2;
                    if (AbstractQueuedLongSynchronizer.this.isOnSyncQueue(node)) break;
                    if (l4 <= 0L) {
                        AbstractQueuedLongSynchronizer.this.transferAfterCancelledWait(node);
                        n = n2;
                        break;
                    }
                    if (l4 > 1000L) {
                        LockSupport.parkNanos(this, l4);
                    }
                    n = n2 = this.checkInterruptWhileWaiting(node);
                    if (n2 != 0) break;
                    l4 = l2 - System.nanoTime();
                    n2 = n;
                } while (true);
                n2 = n;
                if (AbstractQueuedLongSynchronizer.this.acquireQueued(node, l3)) {
                    n2 = n;
                    if (n != -1) {
                        n2 = 1;
                    }
                }
                if (node.nextWaiter != null) {
                    this.unlinkCancelledWaiters();
                }
                if (n2 != 0) {
                    this.reportInterruptAfterWait(n2);
                }
                l = (l4 = l2 - System.nanoTime()) <= l ? l4 : Long.MIN_VALUE;
                return l;
            }
            throw new InterruptedException();
        }

        @Override
        public final void awaitUninterruptibly() {
            AbstractQueuedSynchronizer.Node node = this.addConditionWaiter();
            long l = AbstractQueuedLongSynchronizer.this.fullyRelease(node);
            boolean bl = false;
            while (!AbstractQueuedLongSynchronizer.this.isOnSyncQueue(node)) {
                LockSupport.park(this);
                if (!Thread.interrupted()) continue;
                bl = true;
            }
            if (AbstractQueuedLongSynchronizer.this.acquireQueued(node, l) || bl) {
                AbstractQueuedLongSynchronizer.selfInterrupt();
            }
        }

        @Override
        public final boolean awaitUntil(Date object) throws InterruptedException {
            long l = ((Date)object).getTime();
            if (!Thread.interrupted()) {
                int n;
                int n2;
                long l2;
                boolean bl;
                block7 : {
                    int n3;
                    object = this.addConditionWaiter();
                    l2 = AbstractQueuedLongSynchronizer.this.fullyRelease((AbstractQueuedSynchronizer.Node)object);
                    boolean bl2 = false;
                    n = 0;
                    do {
                        bl = bl2;
                        n2 = n;
                        if (AbstractQueuedLongSynchronizer.this.isOnSyncQueue((AbstractQueuedSynchronizer.Node)object)) break block7;
                        if (System.currentTimeMillis() >= l) {
                            bl = AbstractQueuedLongSynchronizer.this.transferAfterCancelledWait((AbstractQueuedSynchronizer.Node)object);
                            n2 = n;
                            break block7;
                        }
                        LockSupport.parkUntil(this, l);
                        n = n2 = (n3 = this.checkInterruptWhileWaiting((AbstractQueuedSynchronizer.Node)object));
                    } while (n3 == 0);
                    bl = bl2;
                }
                n = n2;
                if (AbstractQueuedLongSynchronizer.this.acquireQueued((AbstractQueuedSynchronizer.Node)object, l2)) {
                    n = n2;
                    if (n2 != -1) {
                        n = 1;
                    }
                }
                if (((AbstractQueuedSynchronizer.Node)object).nextWaiter != null) {
                    this.unlinkCancelledWaiters();
                }
                if (n != 0) {
                    this.reportInterruptAfterWait(n);
                }
                bl = !bl;
                return bl;
            }
            throw new InterruptedException();
        }

        protected final int getWaitQueueLength() {
            if (AbstractQueuedLongSynchronizer.this.isHeldExclusively()) {
                int n = 0;
                AbstractQueuedSynchronizer.Node node = this.firstWaiter;
                while (node != null) {
                    int n2 = n;
                    if (node.waitStatus == -2) {
                        n2 = n + 1;
                    }
                    node = node.nextWaiter;
                    n = n2;
                }
                return n;
            }
            throw new IllegalMonitorStateException();
        }

        protected final Collection<Thread> getWaitingThreads() {
            if (AbstractQueuedLongSynchronizer.this.isHeldExclusively()) {
                ArrayList<Thread> arrayList = new ArrayList<Thread>();
                AbstractQueuedSynchronizer.Node node = this.firstWaiter;
                while (node != null) {
                    Thread thread;
                    if (node.waitStatus == -2 && (thread = node.thread) != null) {
                        arrayList.add(thread);
                    }
                    node = node.nextWaiter;
                }
                return arrayList;
            }
            throw new IllegalMonitorStateException();
        }

        protected final boolean hasWaiters() {
            if (AbstractQueuedLongSynchronizer.this.isHeldExclusively()) {
                AbstractQueuedSynchronizer.Node node = this.firstWaiter;
                while (node != null) {
                    if (node.waitStatus == -2) {
                        return true;
                    }
                    node = node.nextWaiter;
                }
                return false;
            }
            throw new IllegalMonitorStateException();
        }

        final boolean isOwnedBy(AbstractQueuedLongSynchronizer abstractQueuedLongSynchronizer) {
            boolean bl = abstractQueuedLongSynchronizer == AbstractQueuedLongSynchronizer.this;
            return bl;
        }

        @Override
        public final void signal() {
            if (AbstractQueuedLongSynchronizer.this.isHeldExclusively()) {
                AbstractQueuedSynchronizer.Node node = this.firstWaiter;
                if (node != null) {
                    this.doSignal(node);
                }
                return;
            }
            throw new IllegalMonitorStateException();
        }

        @Override
        public final void signalAll() {
            if (AbstractQueuedLongSynchronizer.this.isHeldExclusively()) {
                AbstractQueuedSynchronizer.Node node = this.firstWaiter;
                if (node != null) {
                    this.doSignalAll(node);
                }
                return;
            }
            throw new IllegalMonitorStateException();
        }
    }

}

