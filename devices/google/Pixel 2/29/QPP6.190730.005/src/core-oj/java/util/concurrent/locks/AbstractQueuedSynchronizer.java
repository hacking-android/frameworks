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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import sun.misc.Unsafe;

public abstract class AbstractQueuedSynchronizer
extends AbstractOwnableSynchronizer
implements Serializable {
    private static final long HEAD;
    static final long SPIN_FOR_TIMEOUT_THRESHOLD = 1000L;
    private static final long STATE;
    private static final long TAIL;
    private static final Unsafe U;
    private static final long serialVersionUID = 7373984972572414691L;
    private volatile transient Node head;
    private volatile int state;
    private volatile transient Node tail;

    static {
        U = Unsafe.getUnsafe();
        try {
            STATE = U.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("state"));
            HEAD = U.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("head"));
            TAIL = U.objectFieldOffset(AbstractQueuedSynchronizer.class.getDeclaredField("tail"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    protected AbstractQueuedSynchronizer() {
    }

    private Node addWaiter(Node node) {
        Node node2 = new Node(node);
        do {
            if ((node = this.tail) != null) {
                U.putObject(node2, Node.PREV, node);
                if (!this.compareAndSetTail(node, node2)) continue;
                node.next = node2;
                return node2;
            }
            this.initializeSyncQueue();
        } while (true);
    }

    private void cancelAcquire(Node node) {
        Node node2;
        if (node == null) {
            return;
        }
        node.thread = null;
        Node node3 = node.prev;
        while (node3.waitStatus > 0) {
            node3 = node2 = node3.prev;
            node.prev = node2;
        }
        node2 = node3.next;
        node.waitStatus = 1;
        if (node == this.tail && this.compareAndSetTail(node, node3)) {
            node3.compareAndSetNext(node2, null);
        } else {
            int n;
            if (node3 != this.head && ((n = node3.waitStatus) == -1 || n <= 0 && node3.compareAndSetWaitStatus(n, -1)) && node3.thread != null) {
                Node node4 = node.next;
                if (node4 != null && node4.waitStatus <= 0) {
                    node3.compareAndSetNext(node2, node4);
                }
            } else {
                this.unparkSuccessor(node);
            }
            node.next = node;
        }
    }

    private final boolean compareAndSetTail(Node node, Node node2) {
        return U.compareAndSwapObject(this, TAIL, node, node2);
    }

    private void doAcquireInterruptibly(int n) throws InterruptedException {
        Node node = this.addWaiter(Node.EXCLUSIVE);
        try {
            Object object;
            do {
                if ((object = node.predecessor()) != this.head || !this.tryAcquire(n)) continue;
                this.setHead(node);
                ((Node)object).next = null;
                return;
            } while (!AbstractQueuedSynchronizer.shouldParkAfterFailedAcquire((Node)object, node) || !this.parkAndCheckInterrupt());
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
    private boolean doAcquireNanos(int n, long l) throws InterruptedException {
        if (l <= 0L) {
            return false;
        }
        long l2 = System.nanoTime();
        Node node = this.addWaiter(Node.EXCLUSIVE);
        try {
            Object object;
            do {
                if ((object = node.predecessor()) == this.head && this.tryAcquire(n)) {
                    this.setHead(node);
                    ((Node)object).next = null;
                    return true;
                }
                long l3 = l2 + l - System.nanoTime();
                if (l3 <= 0L) {
                    this.cancelAcquire(node);
                    return false;
                }
                if (!AbstractQueuedSynchronizer.shouldParkAfterFailedAcquire((Node)object, node) || l3 <= 1000L) continue;
                LockSupport.parkNanos(this, l3);
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
    private void doAcquireShared(int n) {
        Node node2 = this.addWaiter(Node.SHARED);
        int n3 = 0;
        try {
            do {
                int n2;
                Node node;
                if ((node = node2.predecessor()) == this.head && (n2 = this.tryAcquireShared(n)) >= 0) {
                    this.setHeadAndPropagate(node2, n2);
                    node.next = null;
                    if (n3 != 0) {
                        AbstractQueuedSynchronizer.selfInterrupt();
                    }
                    return;
                }
                n2 = n3;
                if (AbstractQueuedSynchronizer.shouldParkAfterFailedAcquire(node, node2)) {
                    boolean bl = this.parkAndCheckInterrupt();
                    n2 = n3;
                    if (bl) {
                        n2 = 1;
                    }
                }
                n3 = n2;
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
    private void doAcquireSharedInterruptibly(int n) throws InterruptedException {
        Node node = this.addWaiter(Node.SHARED);
        try {
            Object object;
            do {
                int n2;
                if ((object = node.predecessor()) != this.head || (n2 = this.tryAcquireShared(n)) < 0) continue;
                this.setHeadAndPropagate(node, n2);
                ((Node)object).next = null;
                return;
            } while (!AbstractQueuedSynchronizer.shouldParkAfterFailedAcquire((Node)object, node) || !this.parkAndCheckInterrupt());
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
    private boolean doAcquireSharedNanos(int n, long l) throws InterruptedException {
        if (l <= 0L) {
            return false;
        }
        long l2 = System.nanoTime();
        Node node = this.addWaiter(Node.SHARED);
        try {
            Object object;
            do {
                int n2;
                if ((object = node.predecessor()) == this.head && (n2 = this.tryAcquireShared(n)) >= 0) {
                    this.setHeadAndPropagate(node, n2);
                    ((Node)object).next = null;
                    return true;
                }
                long l3 = l2 + l - System.nanoTime();
                if (l3 <= 0L) {
                    this.cancelAcquire(node);
                    return false;
                }
                if (!AbstractQueuedSynchronizer.shouldParkAfterFailedAcquire((Node)object, node) || l3 <= 1000L) continue;
                LockSupport.parkNanos(this, l3);
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

    private Node enq(Node node) {
        do {
            Node node2;
            if ((node2 = this.tail) != null) {
                U.putObject(node, Node.PREV, node2);
                if (!this.compareAndSetTail(node2, node)) continue;
                node2.next = node;
                return node2;
            }
            this.initializeSyncQueue();
        } while (true);
    }

    private boolean findNodeFromTail(Node node) {
        Node node2 = this.tail;
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
                    if (object == null || (object = ((Node)object).next) == null || ((Node)object).prev != this.head) break block5;
                    thread = ((Node)object).thread;
                    object = thread;
                    if (thread != null) break block6;
                }
                if ((object = this.head) == null || (object = ((Node)object).next) == null || ((Node)object).prev != this.head) break block7;
                thread = ((Node)object).thread;
                object = thread;
                if (thread == null) break block7;
            }
            return object;
        }
        thread = null;
        object = this.tail;
        while (object != null && object != this.head) {
            Thread thread2 = ((Node)object).thread;
            if (thread2 != null) {
                thread = thread2;
            }
            object = ((Node)object).prev;
        }
        return thread;
    }

    private final void initializeSyncQueue() {
        Unsafe unsafe = U;
        long l = HEAD;
        Node node = new Node();
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

    private void setHead(Node node) {
        this.head = node;
        node.thread = null;
        node.prev = null;
    }

    private void setHeadAndPropagate(Node node, int n) {
        Node node2 = this.head;
        this.setHead(node);
        if (!(n <= 0 && node2 != null && node2.waitStatus >= 0 && (node2 = this.head) != null && node2.waitStatus >= 0 || (node = node.next) != null && !node.isShared())) {
            this.doReleaseShared();
        }
    }

    private static boolean shouldParkAfterFailedAcquire(Node node, Node node2) {
        int n = node.waitStatus;
        if (n == -1) {
            return true;
        }
        if (n > 0) {
            Node node3 = node;
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

    private void unparkSuccessor(Node node) {
        Node node2;
        block8 : {
            Node node3;
            block7 : {
                int n = node.waitStatus;
                if (n < 0) {
                    node.compareAndSetWaitStatus(n, 0);
                }
                if ((node3 = node.next) == null) break block7;
                node2 = node3;
                if (node3.waitStatus <= 0) break block8;
            }
            Node node4 = null;
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

    public final void acquire(int n) {
        if (!this.tryAcquire(n) && this.acquireQueued(this.addWaiter(Node.EXCLUSIVE), n)) {
            AbstractQueuedSynchronizer.selfInterrupt();
        }
    }

    public final void acquireInterruptibly(int n) throws InterruptedException {
        if (!Thread.interrupted()) {
            if (!this.tryAcquire(n)) {
                this.doAcquireInterruptibly(n);
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
    final boolean acquireQueued(Node node, int n) {
        boolean bl2 = false;
        try {
            do {
                Node node2;
                if ((node2 = node.predecessor()) == this.head && this.tryAcquire(n)) {
                    this.setHead(node);
                    node2.next = null;
                    return bl2;
                }
                boolean bl = bl2;
                if (AbstractQueuedSynchronizer.shouldParkAfterFailedAcquire(node2, node)) {
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

    public final void acquireShared(int n) {
        if (this.tryAcquireShared(n) < 0) {
            this.doAcquireShared(n);
        }
    }

    public final void acquireSharedInterruptibly(int n) throws InterruptedException {
        if (!Thread.interrupted()) {
            if (this.tryAcquireShared(n) < 0) {
                this.doAcquireSharedInterruptibly(n);
            }
            return;
        }
        throw new InterruptedException();
    }

    final boolean apparentlyFirstQueuedIsExclusive() {
        Node node = this.head;
        boolean bl = node != null && (node = node.next) != null && !node.isShared() && node.thread != null;
        return bl;
    }

    protected final boolean compareAndSetState(int n, int n2) {
        return U.compareAndSwapInt(this, STATE, n, n2);
    }

    final int fullyRelease(Node node) {
        block3 : {
            try {
                int n = this.getState();
                if (!this.release(n)) break block3;
                return n;
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
        Node node = this.tail;
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
        Node node = this.tail;
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
        Node node = this.tail;
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
        Node node = this.tail;
        while (node != null) {
            Thread thread;
            if (node.isShared() && (thread = node.thread) != null) {
                arrayList.add(thread);
            }
            node = node.prev;
        }
        return arrayList;
    }

    protected final int getState() {
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
        Node node = this.head;
        Node node2 = this.tail;
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

    final boolean isOnSyncQueue(Node node) {
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
            Node node = this.tail;
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

    public final boolean release(int n) {
        if (this.tryRelease(n)) {
            Node node = this.head;
            if (node != null && node.waitStatus != 0) {
                this.unparkSuccessor(node);
            }
            return true;
        }
        return false;
    }

    public final boolean releaseShared(int n) {
        if (this.tryReleaseShared(n)) {
            this.doReleaseShared();
            return true;
        }
        return false;
    }

    protected final void setState(int n) {
        this.state = n;
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

    final boolean transferAfterCancelledWait(Node node) {
        if (node.compareAndSetWaitStatus(-2, 0)) {
            this.enq(node);
            return true;
        }
        while (!this.isOnSyncQueue(node)) {
            Thread.yield();
        }
        return false;
    }

    final boolean transferForSignal(Node node) {
        if (!node.compareAndSetWaitStatus(-2, 0)) {
            return false;
        }
        Node node2 = this.enq(node);
        int n = node2.waitStatus;
        if (n > 0 || !node2.compareAndSetWaitStatus(n, -1)) {
            LockSupport.unpark(node.thread);
        }
        return true;
    }

    protected boolean tryAcquire(int n) {
        throw new UnsupportedOperationException();
    }

    public final boolean tryAcquireNanos(int n, long l) throws InterruptedException {
        if (!Thread.interrupted()) {
            boolean bl = this.tryAcquire(n) || this.doAcquireNanos(n, l);
            return bl;
        }
        throw new InterruptedException();
    }

    protected int tryAcquireShared(int n) {
        throw new UnsupportedOperationException();
    }

    public final boolean tryAcquireSharedNanos(int n, long l) throws InterruptedException {
        if (!Thread.interrupted()) {
            boolean bl = this.tryAcquireShared(n) >= 0 || this.doAcquireSharedNanos(n, l);
            return bl;
        }
        throw new InterruptedException();
    }

    protected boolean tryRelease(int n) {
        throw new UnsupportedOperationException();
    }

    protected boolean tryReleaseShared(int n) {
        throw new UnsupportedOperationException();
    }

    public class ConditionObject
    implements Condition,
    Serializable {
        private static final int REINTERRUPT = 1;
        private static final int THROW_IE = -1;
        private static final long serialVersionUID = 1173984872572414699L;
        private transient Node firstWaiter;
        private transient Node lastWaiter;

        private Node addConditionWaiter() {
            Node node;
            Node node2 = node = this.lastWaiter;
            if (node != null) {
                node2 = node;
                if (node.waitStatus != -2) {
                    this.unlinkCancelledWaiters();
                    node2 = this.lastWaiter;
                }
            }
            node = new Node(-2);
            if (node2 == null) {
                this.firstWaiter = node;
            } else {
                node2.nextWaiter = node;
            }
            this.lastWaiter = node;
            return node;
        }

        private int checkInterruptWhileWaiting(Node node) {
            int n = Thread.interrupted() ? (AbstractQueuedSynchronizer.this.transferAfterCancelledWait(node) ? -1 : 1) : 0;
            return n;
        }

        private void doSignal(Node node) {
            Node node2;
            do {
                this.firstWaiter = node2 = node.nextWaiter;
                if (node2 == null) {
                    this.lastWaiter = null;
                }
                node.nextWaiter = null;
                if (AbstractQueuedSynchronizer.this.transferForSignal(node)) break;
                node = node2 = this.firstWaiter;
            } while (node2 != null);
        }

        private void doSignalAll(Node node) {
            Node node2;
            this.firstWaiter = null;
            this.lastWaiter = null;
            do {
                node2 = node.nextWaiter;
                node.nextWaiter = null;
                AbstractQueuedSynchronizer.this.transferForSignal(node);
            } while ((node = node2) != null);
        }

        private void reportInterruptAfterWait(int n) throws InterruptedException {
            if (n != -1) {
                if (n == 1) {
                    AbstractQueuedSynchronizer.selfInterrupt();
                }
                return;
            }
            throw new InterruptedException();
        }

        private void unlinkCancelledWaiters() {
            Node node = this.firstWaiter;
            Node node2 = null;
            while (node != null) {
                Node node3;
                Node node4 = node.nextWaiter;
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
                Node node = this.addConditionWaiter();
                int n2 = AbstractQueuedSynchronizer.this.fullyRelease(node);
                int n3 = 0;
                while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(node)) {
                    int n4;
                    LockSupport.park(this);
                    n3 = n = (n4 = this.checkInterruptWhileWaiting(node));
                    if (n4 == 0) continue;
                    n3 = n;
                    break;
                }
                n = n3;
                if (AbstractQueuedSynchronizer.this.acquireQueued(node, n2)) {
                    n = n3;
                    if (n3 != -1) {
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
                int n2 = AbstractQueuedSynchronizer.this.fullyRelease((Node)object);
                boolean bl2 = false;
                int n3 = 0;
                l = l2;
                do {
                    bl = bl2;
                    n = n3;
                    if (AbstractQueuedSynchronizer.this.isOnSyncQueue((Node)object)) break;
                    if (l <= 0L) {
                        bl = AbstractQueuedSynchronizer.this.transferAfterCancelledWait((Node)object);
                        n = n3;
                        break;
                    }
                    if (l > 1000L) {
                        LockSupport.parkNanos(this, l);
                    }
                    n = n3 = this.checkInterruptWhileWaiting((Node)object);
                    if (n3 != 0) {
                        bl = bl2;
                        break;
                    }
                    l = l3 + l2 - System.nanoTime();
                    n3 = n;
                } while (true);
                n3 = n;
                if (AbstractQueuedSynchronizer.this.acquireQueued((Node)object, n2)) {
                    n3 = n;
                    if (n != -1) {
                        n3 = 1;
                    }
                }
                if (((Node)object).nextWaiter != null) {
                    this.unlinkCancelledWaiters();
                }
                if (n3 != 0) {
                    this.reportInterruptAfterWait(n3);
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
                Node node = this.addConditionWaiter();
                int n2 = AbstractQueuedSynchronizer.this.fullyRelease(node);
                int n3 = 0;
                long l3 = l;
                do {
                    n = n3;
                    if (AbstractQueuedSynchronizer.this.isOnSyncQueue(node)) break;
                    if (l3 <= 0L) {
                        AbstractQueuedSynchronizer.this.transferAfterCancelledWait(node);
                        n = n3;
                        break;
                    }
                    if (l3 > 1000L) {
                        LockSupport.parkNanos(this, l3);
                    }
                    n = n3 = this.checkInterruptWhileWaiting(node);
                    if (n3 != 0) break;
                    l3 = l2 - System.nanoTime();
                    n3 = n;
                } while (true);
                n3 = n;
                if (AbstractQueuedSynchronizer.this.acquireQueued(node, n2)) {
                    n3 = n;
                    if (n != -1) {
                        n3 = 1;
                    }
                }
                if (node.nextWaiter != null) {
                    this.unlinkCancelledWaiters();
                }
                if (n3 != 0) {
                    this.reportInterruptAfterWait(n3);
                }
                l = (l3 = l2 - System.nanoTime()) <= l ? l3 : Long.MIN_VALUE;
                return l;
            }
            throw new InterruptedException();
        }

        @Override
        public final void awaitUninterruptibly() {
            Node node = this.addConditionWaiter();
            int n = AbstractQueuedSynchronizer.this.fullyRelease(node);
            boolean bl = false;
            while (!AbstractQueuedSynchronizer.this.isOnSyncQueue(node)) {
                LockSupport.park(this);
                if (!Thread.interrupted()) continue;
                bl = true;
            }
            if (AbstractQueuedSynchronizer.this.acquireQueued(node, n) || bl) {
                AbstractQueuedSynchronizer.selfInterrupt();
            }
        }

        @Override
        public final boolean awaitUntil(Date object) throws InterruptedException {
            long l = ((Date)object).getTime();
            if (!Thread.interrupted()) {
                int n;
                int n2;
                int n3;
                boolean bl;
                block7 : {
                    int n4;
                    object = this.addConditionWaiter();
                    n = AbstractQueuedSynchronizer.this.fullyRelease((Node)object);
                    boolean bl2 = false;
                    n3 = 0;
                    do {
                        bl = bl2;
                        n2 = n3;
                        if (AbstractQueuedSynchronizer.this.isOnSyncQueue((Node)object)) break block7;
                        if (System.currentTimeMillis() >= l) {
                            bl = AbstractQueuedSynchronizer.this.transferAfterCancelledWait((Node)object);
                            n2 = n3;
                            break block7;
                        }
                        LockSupport.parkUntil(this, l);
                        n3 = n2 = (n4 = this.checkInterruptWhileWaiting((Node)object));
                    } while (n4 == 0);
                    bl = bl2;
                }
                n3 = n2;
                if (AbstractQueuedSynchronizer.this.acquireQueued((Node)object, n)) {
                    n3 = n2;
                    if (n2 != -1) {
                        n3 = 1;
                    }
                }
                if (((Node)object).nextWaiter != null) {
                    this.unlinkCancelledWaiters();
                }
                if (n3 != 0) {
                    this.reportInterruptAfterWait(n3);
                }
                bl = !bl;
                return bl;
            }
            throw new InterruptedException();
        }

        protected final int getWaitQueueLength() {
            if (AbstractQueuedSynchronizer.this.isHeldExclusively()) {
                int n = 0;
                Node node = this.firstWaiter;
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
            if (AbstractQueuedSynchronizer.this.isHeldExclusively()) {
                ArrayList<Thread> arrayList = new ArrayList<Thread>();
                Node node = this.firstWaiter;
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
            if (AbstractQueuedSynchronizer.this.isHeldExclusively()) {
                Node node = this.firstWaiter;
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

        final boolean isOwnedBy(AbstractQueuedSynchronizer abstractQueuedSynchronizer) {
            boolean bl = abstractQueuedSynchronizer == AbstractQueuedSynchronizer.this;
            return bl;
        }

        @Override
        public final void signal() {
            if (AbstractQueuedSynchronizer.this.isHeldExclusively()) {
                Node node = this.firstWaiter;
                if (node != null) {
                    this.doSignal(node);
                }
                return;
            }
            throw new IllegalMonitorStateException();
        }

        @Override
        public final void signalAll() {
            if (AbstractQueuedSynchronizer.this.isHeldExclusively()) {
                Node node = this.firstWaiter;
                if (node != null) {
                    this.doSignalAll(node);
                }
                return;
            }
            throw new IllegalMonitorStateException();
        }
    }

    static final class Node {
        static final int CANCELLED = 1;
        static final int CONDITION = -2;
        static final Node EXCLUSIVE;
        private static final long NEXT;
        static final long PREV;
        static final int PROPAGATE = -3;
        static final Node SHARED;
        static final int SIGNAL = -1;
        private static final long THREAD;
        private static final Unsafe U;
        private static final long WAITSTATUS;
        volatile Node next;
        Node nextWaiter;
        volatile Node prev;
        volatile Thread thread;
        volatile int waitStatus;

        static {
            SHARED = new Node();
            EXCLUSIVE = null;
            U = Unsafe.getUnsafe();
            try {
                NEXT = U.objectFieldOffset(Node.class.getDeclaredField("next"));
                PREV = U.objectFieldOffset(Node.class.getDeclaredField("prev"));
                THREAD = U.objectFieldOffset(Node.class.getDeclaredField("thread"));
                WAITSTATUS = U.objectFieldOffset(Node.class.getDeclaredField("waitStatus"));
                return;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new Error(reflectiveOperationException);
            }
        }

        Node() {
        }

        Node(int n) {
            U.putInt(this, WAITSTATUS, n);
            U.putObject(this, THREAD, Thread.currentThread());
        }

        Node(Node node) {
            this.nextWaiter = node;
            U.putObject(this, THREAD, Thread.currentThread());
        }

        final boolean compareAndSetNext(Node node, Node node2) {
            return U.compareAndSwapObject(this, NEXT, node, node2);
        }

        final boolean compareAndSetWaitStatus(int n, int n2) {
            return U.compareAndSwapInt(this, WAITSTATUS, n, n2);
        }

        final boolean isShared() {
            boolean bl = this.nextWaiter == SHARED;
            return bl;
        }

        final Node predecessor() throws NullPointerException {
            Node node = this.prev;
            if (node != null) {
                return node;
            }
            throw new NullPointerException();
        }
    }

}

