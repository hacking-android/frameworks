/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import sun.misc.Unsafe;

public class SynchronousQueue<E>
extends AbstractQueue<E>
implements BlockingQueue<E>,
Serializable {
    static final int MAX_TIMED_SPINS;
    static final int MAX_UNTIMED_SPINS;
    static final long SPIN_FOR_TIMEOUT_THRESHOLD = 1000L;
    private static final long serialVersionUID = -3223113410248163686L;
    private ReentrantLock qlock;
    private volatile transient Transferer<E> transferer;
    private WaitQueue waitingConsumers;
    private WaitQueue waitingProducers;

    static {
        int n = Runtime.getRuntime().availableProcessors() < 2 ? 0 : 32;
        MAX_TIMED_SPINS = n;
        MAX_UNTIMED_SPINS = MAX_TIMED_SPINS * 16;
    }

    public SynchronousQueue() {
        this(false);
    }

    public SynchronousQueue(boolean bl) {
        Transferer transferer = bl ? new TransferQueue() : new TransferStack();
        this.transferer = transferer;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.transferer = this.waitingProducers instanceof FifoWaitQueue ? new TransferQueue() : new TransferStack();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (this.transferer instanceof TransferQueue) {
            this.qlock = new ReentrantLock(true);
            this.waitingProducers = new FifoWaitQueue();
            this.waitingConsumers = new FifoWaitQueue();
        } else {
            this.qlock = new ReentrantLock();
            this.waitingProducers = new LifoWaitQueue();
            this.waitingConsumers = new LifoWaitQueue();
        }
        objectOutputStream.defaultWriteObject();
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean contains(Object object) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return collection.isEmpty();
    }

    @Override
    public int drainTo(Collection<? super E> collection) {
        if (collection != null) {
            if (collection != this) {
                E e;
                int n = 0;
                while ((e = this.poll()) != null) {
                    collection.add(e);
                    ++n;
                }
                return n;
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    @Override
    public int drainTo(Collection<? super E> collection, int n) {
        if (collection != null) {
            if (collection != this) {
                int n2;
                E e;
                for (n2 = 0; n2 < n && (e = this.poll()) != null; ++n2) {
                    collection.add(e);
                }
                return n2;
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public boolean offer(E e) {
        if (e != null) {
            Transferer<E> transferer = this.transferer;
            boolean bl = true;
            if (transferer.transfer(e, true, 0L) == null) {
                bl = false;
            }
            return bl;
        }
        throw new NullPointerException();
    }

    @Override
    public boolean offer(E e, long l, TimeUnit timeUnit) throws InterruptedException {
        if (e != null) {
            if (this.transferer.transfer(e, true, timeUnit.toNanos(l)) != null) {
                return true;
            }
            if (!Thread.interrupted()) {
                return false;
            }
            throw new InterruptedException();
        }
        throw new NullPointerException();
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public E poll() {
        return this.transferer.transfer(null, true, 0L);
    }

    @Override
    public E poll(long l, TimeUnit timeUnit) throws InterruptedException {
        if ((timeUnit = this.transferer.transfer(null, true, timeUnit.toNanos(l))) == null && Thread.interrupted()) {
            throw new InterruptedException();
        }
        return (E)((Object)timeUnit);
    }

    @Override
    public void put(E e) throws InterruptedException {
        if (e != null) {
            if (this.transferer.transfer(e, false, 0L) != null) {
                return;
            }
            Thread.interrupted();
            throw new InterruptedException();
        }
        throw new NullPointerException();
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public boolean remove(Object object) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.emptySpliterator();
    }

    @Override
    public E take() throws InterruptedException {
        E e = this.transferer.transfer(null, false, 0L);
        if (e != null) {
            return e;
        }
        Thread.interrupted();
        throw new InterruptedException();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        if (arrT.length > 0) {
            arrT[0] = null;
        }
        return arrT;
    }

    @Override
    public String toString() {
        return "[]";
    }

    static class FifoWaitQueue
    extends WaitQueue {
        private static final long serialVersionUID = -3623113410248163686L;

        FifoWaitQueue() {
        }
    }

    static class LifoWaitQueue
    extends WaitQueue {
        private static final long serialVersionUID = -3633113410248163686L;

        LifoWaitQueue() {
        }
    }

    static final class TransferQueue<E>
    extends Transferer<E> {
        private static final long CLEANME;
        private static final long HEAD;
        private static final long TAIL;
        private static final Unsafe U;
        volatile transient QNode cleanMe;
        volatile transient QNode head;
        volatile transient QNode tail;

        static {
            U = Unsafe.getUnsafe();
            try {
                HEAD = U.objectFieldOffset(TransferQueue.class.getDeclaredField("head"));
                TAIL = U.objectFieldOffset(TransferQueue.class.getDeclaredField("tail"));
                CLEANME = U.objectFieldOffset(TransferQueue.class.getDeclaredField("cleanMe"));
                return;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new Error(reflectiveOperationException);
            }
        }

        TransferQueue() {
            QNode qNode;
            this.head = qNode = new QNode(null, false);
            this.tail = qNode;
        }

        void advanceHead(QNode qNode, QNode qNode2) {
            if (qNode == this.head && U.compareAndSwapObject(this, HEAD, qNode, qNode2)) {
                qNode.next = qNode;
            }
        }

        void advanceTail(QNode qNode, QNode qNode2) {
            if (this.tail == qNode) {
                U.compareAndSwapObject(this, TAIL, qNode, qNode2);
            }
        }

        Object awaitFulfill(QNode qNode, E e, boolean bl, long l) {
            long l2 = bl ? System.nanoTime() + l : 0L;
            Thread thread = Thread.currentThread();
            int n = this.head.next == qNode ? (bl ? MAX_TIMED_SPINS : MAX_UNTIMED_SPINS) : 0;
            do {
                Object object;
                int n2;
                if (thread.isInterrupted()) {
                    qNode.tryCancel(e);
                }
                if ((object = qNode.item) != e) {
                    return object;
                }
                if (bl) {
                    long l3;
                    l = l3 = l2 - System.nanoTime();
                    if (l3 <= 0L) {
                        qNode.tryCancel(e);
                        l = l3;
                        continue;
                    }
                }
                if (n > 0) {
                    n2 = n - 1;
                } else if (qNode.waiter == null) {
                    qNode.waiter = thread;
                    n2 = n;
                } else if (!bl) {
                    LockSupport.park(this);
                    n2 = n;
                } else {
                    n2 = n;
                    if (l > 1000L) {
                        LockSupport.parkNanos(this, l);
                        n2 = n;
                    }
                }
                n = n2;
            } while (true);
        }

        boolean casCleanMe(QNode qNode, QNode qNode2) {
            boolean bl = this.cleanMe == qNode && U.compareAndSwapObject(this, CLEANME, qNode, qNode2);
            return bl;
        }

        void clean(QNode qNode, QNode qNode2) {
            qNode2.waiter = null;
            while (qNode.next == qNode2) {
                QNode qNode3 = this.head;
                QNode qNode4 = qNode3.next;
                if (qNode4 != null && qNode4.isCancelled()) {
                    this.advanceHead(qNode3, qNode4);
                    continue;
                }
                qNode4 = this.tail;
                if (qNode4 == qNode3) {
                    return;
                }
                qNode3 = qNode4.next;
                if (qNode4 != this.tail) continue;
                if (qNode3 != null) {
                    this.advanceTail(qNode4, qNode3);
                    continue;
                }
                if (qNode2 != qNode4 && ((qNode3 = qNode2.next) == qNode2 || qNode.casNext(qNode2, qNode3))) {
                    return;
                }
                QNode qNode5 = this.cleanMe;
                if (qNode5 != null) {
                    qNode3 = qNode5.next;
                    if (qNode3 == null || qNode3 == qNode5 || !qNode3.isCancelled() || qNode3 != qNode4 && (qNode4 = qNode3.next) != null && qNode4 != qNode3 && qNode5.casNext(qNode3, qNode4)) {
                        this.casCleanMe(qNode5, null);
                    }
                    if (qNode5 != qNode) continue;
                    return;
                }
                if (!this.casCleanMe(null, qNode)) continue;
                return;
            }
        }

        @Override
        E transfer(E object, boolean bl, long l) {
            block10 : {
                Object object2;
                QNode qNode;
                QNode qNode2 = null;
                boolean bl2 = object != null;
                do {
                    qNode = this.tail;
                    QNode qNode3 = this.head;
                    if (qNode == null || qNode3 == null) continue;
                    if (qNode3 != qNode && qNode.isData != bl2) {
                        QNode qNode4 = qNode3.next;
                        if (qNode != this.tail || qNode4 == null || qNode3 != this.head) continue;
                        object2 = qNode4.item;
                        boolean bl3 = object2 != null;
                        if (bl2 != bl3 && object2 != qNode4 && qNode4.casItem(object2, object)) {
                            this.advanceHead(qNode3, qNode4);
                            LockSupport.unpark(qNode4.waiter);
                            if (object2 != null) {
                                object = object2;
                            }
                            return object;
                        }
                        this.advanceHead(qNode3, qNode4);
                        continue;
                    }
                    object2 = qNode.next;
                    if (qNode != this.tail) continue;
                    if (object2 != null) {
                        this.advanceTail(qNode, (QNode)object2);
                        continue;
                    }
                    if (bl && l <= 0L) {
                        return null;
                    }
                    if (qNode2 == null) {
                        qNode2 = new QNode(object, bl2);
                    }
                    if (qNode.casNext(null, qNode2)) break;
                } while (true);
                this.advanceTail(qNode, qNode2);
                object2 = this.awaitFulfill(qNode2, object, bl, l);
                if (object2 == qNode2) {
                    this.clean(qNode, qNode2);
                    return null;
                }
                if (!qNode2.isOffList()) {
                    this.advanceHead(qNode, qNode2);
                    if (object2 != null) {
                        qNode2.item = qNode2;
                    }
                    qNode2.waiter = null;
                }
                if (object2 == null) break block10;
                object = object2;
            }
            return object;
        }

        static final class QNode {
            private static final long ITEM;
            private static final long NEXT;
            private static final Unsafe U;
            final boolean isData;
            volatile Object item;
            volatile QNode next;
            volatile Thread waiter;

            static {
                U = Unsafe.getUnsafe();
                try {
                    ITEM = U.objectFieldOffset(QNode.class.getDeclaredField("item"));
                    NEXT = U.objectFieldOffset(QNode.class.getDeclaredField("next"));
                    return;
                }
                catch (ReflectiveOperationException reflectiveOperationException) {
                    throw new Error(reflectiveOperationException);
                }
            }

            QNode(Object object, boolean bl) {
                this.item = object;
                this.isData = bl;
            }

            boolean casItem(Object object, Object object2) {
                boolean bl = this.item == object && U.compareAndSwapObject(this, ITEM, object, object2);
                return bl;
            }

            boolean casNext(QNode qNode, QNode qNode2) {
                boolean bl = this.next == qNode && U.compareAndSwapObject(this, NEXT, qNode, qNode2);
                return bl;
            }

            boolean isCancelled() {
                boolean bl = this.item == this;
                return bl;
            }

            boolean isOffList() {
                boolean bl = this.next == this;
                return bl;
            }

            void tryCancel(Object object) {
                U.compareAndSwapObject(this, ITEM, object, this);
            }
        }

    }

    static final class TransferStack<E>
    extends Transferer<E> {
        static final int DATA = 1;
        static final int FULFILLING = 2;
        private static final long HEAD;
        static final int REQUEST = 0;
        private static final Unsafe U;
        volatile SNode head;

        static {
            U = Unsafe.getUnsafe();
            try {
                HEAD = U.objectFieldOffset(TransferStack.class.getDeclaredField("head"));
                return;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new Error(reflectiveOperationException);
            }
        }

        TransferStack() {
        }

        static boolean isFulfilling(int n) {
            boolean bl = (n & 2) != 0;
            return bl;
        }

        static SNode snode(SNode sNode, Object object, SNode sNode2, int n) {
            SNode sNode3 = sNode;
            if (sNode == null) {
                sNode3 = new SNode(object);
            }
            sNode3.mode = n;
            sNode3.next = sNode2;
            return sNode3;
        }

        SNode awaitFulfill(SNode sNode, boolean bl, long l) {
            long l2 = bl ? System.nanoTime() + l : 0L;
            Thread thread = Thread.currentThread();
            int n = this.shouldSpin(sNode) ? (bl ? MAX_TIMED_SPINS : MAX_UNTIMED_SPINS) : 0;
            do {
                int n2;
                SNode sNode2;
                if (thread.isInterrupted()) {
                    sNode.tryCancel();
                }
                if ((sNode2 = sNode.match) != null) {
                    return sNode2;
                }
                if (bl) {
                    long l3;
                    l = l3 = l2 - System.nanoTime();
                    if (l3 <= 0L) {
                        sNode.tryCancel();
                        l = l3;
                        continue;
                    }
                }
                if (n > 0) {
                    if (!this.shouldSpin(sNode)) {
                        n = 0;
                    }
                    n2 = --n;
                } else if (sNode.waiter == null) {
                    sNode.waiter = thread;
                    n2 = n;
                } else if (!bl) {
                    LockSupport.park(this);
                    n2 = n;
                } else {
                    n2 = n;
                    if (l > 1000L) {
                        LockSupport.parkNanos(this, l);
                        n2 = n;
                    }
                }
                n = n2;
            } while (true);
        }

        boolean casHead(SNode sNode, SNode sNode2) {
            boolean bl = sNode == this.head && U.compareAndSwapObject(this, HEAD, sNode, sNode2);
            return bl;
        }

        void clean(SNode sNode) {
            SNode sNode2;
            SNode sNode3;
            sNode.item = null;
            sNode.waiter = null;
            sNode = sNode3 = sNode.next;
            if (sNode3 != null) {
                sNode = sNode3;
                if (sNode3.isCancelled()) {
                    sNode = sNode3.next;
                }
            }
            do {
                SNode sNode4;
                sNode3 = sNode2 = (sNode4 = this.head);
                if (sNode4 == null) break;
                sNode3 = sNode2;
                if (sNode2 == sNode) break;
                sNode3 = sNode2;
                if (!sNode2.isCancelled()) break;
                this.casHead(sNode2, sNode2.next);
            } while (true);
            while (sNode3 != null && sNode3 != sNode) {
                sNode2 = sNode3.next;
                if (sNode2 != null && sNode2.isCancelled()) {
                    sNode3.casNext(sNode2, sNode2.next);
                    continue;
                }
                sNode3 = sNode2;
            }
        }

        boolean shouldSpin(SNode sNode) {
            SNode sNode2 = this.head;
            boolean bl = sNode2 == sNode || sNode2 == null || TransferStack.isFulfilling(sNode2.mode);
            return bl;
        }

        @Override
        E transfer(E object, boolean bl, long l) {
            SNode sNode;
            SNode sNode2 = null;
            int n = object == null ? 0 : 1;
            block0 : do {
                SNode sNode3;
                SNode sNode4;
                if ((sNode3 = this.head) != null && sNode3.mode != n) {
                    if (!TransferStack.isFulfilling(sNode3.mode)) {
                        if (sNode3.isCancelled()) {
                            this.casHead(sNode3, sNode3.next);
                            continue;
                        }
                        sNode2 = sNode = (sNode4 = TransferStack.snode(sNode2, object, sNode3, n | 2));
                        if (!this.casHead(sNode3, sNode4)) continue;
                        do {
                            if ((sNode4 = sNode.next) == null) {
                                this.casHead(sNode, null);
                                sNode2 = null;
                                continue block0;
                            }
                            sNode2 = sNode4.next;
                            if (sNode4.tryMatch(sNode)) {
                                this.casHead(sNode, sNode2);
                                object = n == 0 ? sNode4.item : sNode.item;
                                return object;
                            }
                            sNode.casNext(sNode4, sNode2);
                        } while (true);
                    }
                    sNode = sNode3.next;
                    if (sNode == null) {
                        this.casHead(sNode3, null);
                        continue;
                    }
                    sNode4 = sNode.next;
                    if (sNode.tryMatch(sNode3)) {
                        this.casHead(sNode3, sNode4);
                        continue;
                    }
                    sNode3.casNext(sNode, sNode4);
                    continue;
                }
                if (bl && l <= 0L) {
                    if (sNode3 != null && sNode3.isCancelled()) {
                        this.casHead(sNode3, sNode3.next);
                        continue;
                    }
                    return null;
                }
                sNode2 = sNode = (sNode4 = TransferStack.snode(sNode2, object, sNode3, n));
                if (this.casHead(sNode3, sNode4)) break;
            } while (true);
            sNode2 = this.awaitFulfill(sNode, bl, l);
            if (sNode2 == sNode) {
                this.clean(sNode);
                return null;
            }
            object = this.head;
            if (object != null && ((SNode)object).next == sNode) {
                this.casHead((SNode)object, sNode.next);
            }
            object = n == 0 ? sNode2.item : sNode.item;
            return object;
        }

        static final class SNode {
            private static final long MATCH;
            private static final long NEXT;
            private static final Unsafe U;
            Object item;
            volatile SNode match;
            int mode;
            volatile SNode next;
            volatile Thread waiter;

            static {
                U = Unsafe.getUnsafe();
                try {
                    MATCH = U.objectFieldOffset(SNode.class.getDeclaredField("match"));
                    NEXT = U.objectFieldOffset(SNode.class.getDeclaredField("next"));
                    return;
                }
                catch (ReflectiveOperationException reflectiveOperationException) {
                    throw new Error(reflectiveOperationException);
                }
            }

            SNode(Object object) {
                this.item = object;
            }

            boolean casNext(SNode sNode, SNode sNode2) {
                boolean bl = sNode == this.next && U.compareAndSwapObject(this, NEXT, sNode, sNode2);
                return bl;
            }

            boolean isCancelled() {
                boolean bl = this.match == this;
                return bl;
            }

            void tryCancel() {
                U.compareAndSwapObject(this, MATCH, null, this);
            }

            boolean tryMatch(SNode object) {
                SNode sNode = this.match;
                boolean bl = true;
                if (sNode == null && U.compareAndSwapObject(this, MATCH, null, object)) {
                    object = this.waiter;
                    if (object != null) {
                        this.waiter = null;
                        LockSupport.unpark((Thread)object);
                    }
                    return true;
                }
                if (this.match != object) {
                    bl = false;
                }
                return bl;
            }
        }

    }

    static abstract class Transferer<E> {
        Transferer() {
        }

        abstract E transfer(E var1, boolean var2, long var3);
    }

    static class WaitQueue
    implements Serializable {
        WaitQueue() {
        }
    }

}

