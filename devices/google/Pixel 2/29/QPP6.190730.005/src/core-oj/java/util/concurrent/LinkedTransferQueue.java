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
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.Helpers;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;
import sun.misc.Unsafe;

public class LinkedTransferQueue<E>
extends AbstractQueue<E>
implements TransferQueue<E>,
Serializable {
    private static final int ASYNC = 1;
    private static final int CHAINED_SPINS = 64;
    private static final int FRONT_SPINS = 128;
    private static final long HEAD;
    private static final boolean MP;
    private static final int NOW = 0;
    private static final long SWEEPVOTES;
    static final int SWEEP_THRESHOLD = 32;
    private static final int SYNC = 2;
    private static final long TAIL;
    private static final int TIMED = 3;
    private static final Unsafe U;
    private static final long serialVersionUID = -3223113410248163686L;
    volatile transient Node head;
    private volatile transient int sweepVotes;
    private volatile transient Node tail;

    static {
        int n = Runtime.getRuntime().availableProcessors();
        boolean bl = true;
        if (n <= 1) {
            bl = false;
        }
        MP = bl;
        U = Unsafe.getUnsafe();
        try {
            HEAD = U.objectFieldOffset(LinkedTransferQueue.class.getDeclaredField("head"));
            TAIL = U.objectFieldOffset(LinkedTransferQueue.class.getDeclaredField("tail"));
            SWEEPVOTES = U.objectFieldOffset(LinkedTransferQueue.class.getDeclaredField("sweepVotes"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public LinkedTransferQueue() {
    }

    public LinkedTransferQueue(Collection<? extends E> collection) {
        this();
        this.addAll(collection);
    }

    private E awaitMatch(Node node, Node node2, E e, boolean bl, long l) {
        long l2 = bl ? System.nanoTime() + l : 0L;
        Thread thread = Thread.currentThread();
        int n = -1;
        Object object = null;
        long l3 = l;
        do {
            int n2;
            Object object2;
            if ((object2 = node.item) != e) {
                node.forgetContents();
                return (E)object2;
            }
            if (!(thread.isInterrupted() || bl && l3 <= 0L)) {
                if (n < 0) {
                    int n3;
                    n2 = n = (n3 = LinkedTransferQueue.spinsFor(node2, node.isData));
                    object2 = object;
                    l = l3;
                    if (n3 > 0) {
                        object2 = ThreadLocalRandom.current();
                        n2 = n;
                        l = l3;
                    }
                } else if (n > 0) {
                    n2 = --n;
                    object2 = object;
                    l = l3;
                    if (((ThreadLocalRandom)object).nextInt(64) == 0) {
                        Thread.yield();
                        n2 = n;
                        object2 = object;
                        l = l3;
                    }
                } else if (node.waiter == null) {
                    node.waiter = thread;
                    n2 = n;
                    object2 = object;
                    l = l3;
                } else if (bl) {
                    l3 = l2 - System.nanoTime();
                    n2 = n;
                    object2 = object;
                    l = l3;
                    if (l3 > 0L) {
                        LockSupport.parkNanos(this, l3);
                        n2 = n;
                        object2 = object;
                        l = l3;
                    }
                } else {
                    LockSupport.park(this);
                    n2 = n;
                    object2 = object;
                    l = l3;
                }
            } else {
                this.unsplice(node2, node);
                n2 = n;
                object2 = object;
                l = l3;
                if (node.casItem(e, node)) {
                    return e;
                }
            }
            n = n2;
            object = object2;
            l3 = l;
        } while (true);
    }

    private boolean casHead(Node node, Node node2) {
        return U.compareAndSwapObject(this, HEAD, node, node2);
    }

    private boolean casSweepVotes(int n, int n2) {
        return U.compareAndSwapInt(this, SWEEPVOTES, n, n2);
    }

    private boolean casTail(Node node, Node node2) {
        return U.compareAndSwapObject(this, TAIL, node, node2);
    }

    private int countOfMode(boolean bl) {
        int n;
        block0 : do {
            int n2 = 0;
            Node node = this.head;
            do {
                Node node2;
                n = n2;
                if (node == null) break block0;
                n = n2++;
                if (!node.isMatched()) {
                    if (node.isData != bl) {
                        return 0;
                    }
                    n = n2;
                    if (n2 == Integer.MAX_VALUE) {
                        n = n2;
                        break block0;
                    }
                }
                if (node == (node2 = node.next)) continue block0;
                node = node2;
                n2 = n;
            } while (true);
            break;
        } while (true);
        return n;
    }

    private boolean findAndRemove(Object object) {
        if (object != null) {
            Object object2 = null;
            Node node = this.head;
            while (node != null) {
                Node node2;
                Object object3 = node.item;
                if (node.isData) {
                    if (object3 != null && object3 != node && object.equals(object3) && node.tryMatchData()) {
                        this.unsplice((Node)object2, node);
                        return true;
                    }
                } else if (object3 == null) break;
                object3 = node;
                node = node2 = node.next;
                object2 = object3;
                if (node2 != object3) continue;
                object2 = null;
                node = this.head;
            }
        }
        return false;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Object object;
        while ((object = objectInputStream.readObject()) != null) {
            this.offer(object);
        }
        return;
    }

    private static int spinsFor(Node node, boolean bl) {
        if (MP && node != null) {
            if (node.isData != bl) {
                return 192;
            }
            if (node.isMatched()) {
                return 128;
            }
            if (node.waiter == null) {
                return 64;
            }
        }
        return 0;
    }

    private void sweep() {
        Node node;
        Node node2 = this.head;
        while (node2 != null && (node = node2.next) != null) {
            if (!node.isMatched()) {
                node2 = node;
                continue;
            }
            Node node3 = node.next;
            if (node3 == null) break;
            if (node == node3) {
                node2 = this.head;
                continue;
            }
            node2.casNext(node, node3);
        }
    }

    private Object[] toArrayInternal(Object[] arrobject) {
        int n;
        Object object = arrobject;
        block0 : do {
            n = 0;
            Object object2 = this.head;
            while (object2 != null) {
                Object[] arrobject2;
                int n2;
                Object object3 = object2.item;
                if (object2.isData) {
                    arrobject2 = object;
                    n2 = n;
                    if (object3 != null) {
                        arrobject2 = object;
                        n2 = n;
                        if (object3 != object2) {
                            if (object == null) {
                                arrobject2 = new Object[4];
                            } else {
                                arrobject2 = object;
                                if (n == ((Object[])object).length) {
                                    arrobject2 = Arrays.copyOf(object, (n + 4) * 2);
                                }
                            }
                            arrobject2[n] = object3;
                            n2 = n + 1;
                        }
                    }
                } else {
                    arrobject2 = object;
                    n2 = n;
                    if (object3 == null) break block0;
                }
                if (object2 == (object = object2.next)) {
                    object = arrobject2;
                    continue block0;
                }
                object2 = object;
                object = arrobject2;
                n = n2;
            }
            break;
        } while (true);
        if (object == null) {
            return new Object[0];
        }
        if (arrobject != null && n <= arrobject.length) {
            if (arrobject != object) {
                System.arraycopy(object, 0, arrobject, 0, n);
            }
            if (n < arrobject.length) {
                arrobject[n] = null;
            }
            return arrobject;
        }
        if (n != ((Object[])object).length) {
            object = Arrays.copyOf(object, n);
        }
        return object;
    }

    private Node tryAppend(Node node, boolean bl) {
        Node node2;
        Node node3;
        Node node4 = node3 = this.tail;
        do {
            Node node5;
            Object var5_5 = null;
            node2 = node4;
            if (node4 == null) {
                node2 = node4 = (node5 = this.head);
                if (node5 == null) {
                    if (!this.casHead(null, node)) continue;
                    return node;
                }
            }
            if (node2.cannotPrecede(bl)) {
                return null;
            }
            Node node6 = node2.next;
            if (node6 != null) {
                if (node2 != node3 && node3 != (node4 = this.tail)) {
                    node5 = node4;
                } else {
                    node5 = node3;
                    node4 = var5_5;
                    if (node2 != node6) {
                        node4 = node6;
                        node5 = node3;
                    }
                }
                node3 = node5;
                continue;
            }
            if (node2.casNext(null, node)) break;
            node4 = node2.next;
        } while (true);
        if (node2 != node3) {
            node4 = node;
            node = node3;
            while (this.tail != node || !this.casTail(node, node4)) {
                node = node3 = this.tail;
                if (node3 == null || (node3 = node.next) == null) break;
                node3 = node4 = node3.next;
                if (node4 == null || node3 == node) break;
                node4 = node3;
            }
        }
        return node2;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            objectOutputStream.writeObject(iterator.next());
        }
        objectOutputStream.writeObject(null);
    }

    private E xfer(E object, boolean bl, int n, long l) {
        block11 : {
            boolean bl2;
            Object object2;
            if (bl && object == null) {
                throw new NullPointerException();
            }
            Node node = null;
            do {
                Object object3;
                object2 = object3 = this.head;
                do {
                    boolean bl3;
                    bl2 = true;
                    if (object2 == null) break;
                    boolean bl4 = ((Node)object2).isData;
                    Object object4 = ((Node)object2).item;
                    if (object4 != object2 && (bl3 = object4 != null) == bl4) {
                        if (bl4 == bl) break;
                        if (((Node)object2).casItem(object4, object)) {
                            object = object2;
                            while (object != object3) {
                                node = ((Node)object).next;
                                if (this.head == object3) {
                                    if (node != null) {
                                        object = node;
                                    }
                                    if (this.casHead((Node)object3, (Node)object)) {
                                        ((Node)object3).forgetNext();
                                        break;
                                    }
                                }
                                object3 = object = this.head;
                                if (object == null) break;
                                node = ((Node)object3).next;
                                object = node;
                                if (node != null && ((Node)object).isMatched()) continue;
                                break;
                            }
                            LockSupport.unpark(((Node)object2).waiter);
                            return (E)object4;
                        }
                    }
                    if (object2 != (object4 = ((Node)object2).next)) {
                        object2 = object4;
                        continue;
                    }
                    object3 = object2 = this.head;
                } while (true);
                if (n == 0) break block11;
                object2 = node == null ? new Node(object, bl) : node;
                node = this.tryAppend((Node)object2, bl);
                if (node != null) break;
                node = object2;
            } while (true);
            if (n != 1) {
                bl = n == 3 ? bl2 : false;
                return this.awaitMatch((Node)object2, node, object, bl, l);
            }
        }
        return (E)object;
    }

    @Override
    public boolean add(E e) {
        this.xfer(e, true, 1, 0L);
        return true;
    }

    @Override
    public boolean contains(Object object) {
        if (object != null) {
            Node node = this.head;
            while (node != null) {
                Object object2 = node.item;
                if (node.isData) {
                    if (object2 != null && object2 != node && object.equals(object2)) {
                        return true;
                    }
                } else if (object2 == null) break;
                node = this.succ(node);
            }
        }
        return false;
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

    final Node firstDataNode() {
        block0 : do {
            Object object = this.head;
            while (object != null) {
                Object object2 = ((Node)object).item;
                if (((Node)object).isData) {
                    if (object2 != null && object2 != object) {
                        return object;
                    }
                } else if (object2 == null) break block0;
                if (object == (object2 = ((Node)object).next)) continue block0;
                object = object2;
            }
            break;
        } while (true);
        return null;
    }

    @Override
    public int getWaitingConsumerCount() {
        return this.countOfMode(false);
    }

    @Override
    public boolean hasWaitingConsumer() {
        block0 : do {
            Object object = this.head;
            while (object != null) {
                Object object2 = ((Node)object).item;
                if (((Node)object).isData) {
                    if (object2 != null && object2 != object) {
                        break block0;
                    }
                } else if (object2 == null) {
                    return true;
                }
                if (object == (object2 = ((Node)object).next)) continue block0;
                object = object2;
            }
            break;
        } while (true);
        return false;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.firstDataNode() == null;
        return bl;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public boolean offer(E e) {
        this.xfer(e, true, 1, 0L);
        return true;
    }

    @Override
    public boolean offer(E e, long l, TimeUnit timeUnit) {
        this.xfer(e, true, 1, 0L);
        return true;
    }

    @Override
    public E peek() {
        block0 : do {
            Object object = this.head;
            while (object != null) {
                Object object2 = ((Node)object).item;
                if (((Node)object).isData) {
                    if (object2 != null && object2 != object) {
                        return (E)object2;
                    }
                } else if (object2 == null) break block0;
                if (object == (object2 = ((Node)object).next)) continue block0;
                object = object2;
            }
            break;
        } while (true);
        return null;
    }

    @Override
    public E poll() {
        return this.xfer(null, false, 0, 0L);
    }

    @Override
    public E poll(long l, TimeUnit timeUnit) throws InterruptedException {
        if ((timeUnit = this.xfer(null, false, 3, timeUnit.toNanos(l))) == null && Thread.interrupted()) {
            throw new InterruptedException();
        }
        return (E)((Object)timeUnit);
    }

    @Override
    public void put(E e) {
        this.xfer(e, true, 1, 0L);
    }

    @Override
    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean remove(Object object) {
        return this.findAndRemove(object);
    }

    @Override
    public int size() {
        return this.countOfMode(true);
    }

    @Override
    public Spliterator<E> spliterator() {
        return new LTQSpliterator();
    }

    final Node succ(Node node) {
        Node node2 = node.next;
        node = node == node2 ? this.head : node2;
        return node;
    }

    @Override
    public E take() throws InterruptedException {
        E e = this.xfer(null, false, 2, 0L);
        if (e != null) {
            return e;
        }
        Thread.interrupted();
        throw new InterruptedException();
    }

    @Override
    public Object[] toArray() {
        return this.toArrayInternal(null);
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        if (arrT != null) {
            return this.toArrayInternal(arrT);
        }
        throw new NullPointerException();
    }

    @Override
    public String toString() {
        int n;
        Object object;
        int n2;
        String[] arrstring = null;
        block0 : do {
            n2 = 0;
            n = 0;
            Node node = this.head;
            object = arrstring;
            while (node != null) {
                int n3;
                int n4;
                Object object2 = node.item;
                if (node.isData) {
                    arrstring = object;
                    n4 = n2;
                    n3 = n;
                    if (object2 != null) {
                        arrstring = object;
                        n4 = n2;
                        n3 = n;
                        if (object2 != node) {
                            if (object == null) {
                                arrstring = new String[4];
                            } else {
                                arrstring = object;
                                if (n == ((String[])object).length) {
                                    arrstring = Arrays.copyOf(object, n * 2);
                                }
                            }
                            arrstring[n] = object = object2.toString();
                            n4 = n2 + ((String)object).length();
                            n3 = n + 1;
                        }
                    }
                } else {
                    arrstring = object;
                    n4 = n2;
                    n3 = n;
                    if (object2 == null) break block0;
                }
                if (node == (object = node.next)) continue block0;
                node = object;
                object = arrstring;
                n2 = n4;
                n = n3;
            }
            break;
        } while (true);
        if (n == 0) {
            return "[]";
        }
        return Helpers.toString(object, n, n2);
    }

    @Override
    public void transfer(E e) throws InterruptedException {
        if (this.xfer(e, true, 2, 0L) == null) {
            return;
        }
        Thread.interrupted();
        throw new InterruptedException();
    }

    @Override
    public boolean tryTransfer(E e) {
        boolean bl = this.xfer(e, true, 0, 0L) == null;
        return bl;
    }

    @Override
    public boolean tryTransfer(E e, long l, TimeUnit timeUnit) throws InterruptedException {
        if (this.xfer(e, true, 3, timeUnit.toNanos(l)) == null) {
            return true;
        }
        if (!Thread.interrupted()) {
            return false;
        }
        throw new InterruptedException();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    final void unsplice(Node var1_1, Node var2_2) {
        block6 : {
            var2_2.waiter = null;
            if (var1_1 == null) return;
            if (var1_1 == var2_2) return;
            if (var1_1.next != var2_2) return;
            var3_3 = var2_2.next;
            if (var3_3 != null) {
                if (var3_3 == var2_2) return;
                if (var1_1.casNext(var2_2, var3_3) == false) return;
                if (var1_1.isMatched() == false) return;
            }
            while ((var4_4 = this.head) != var1_1) {
                if (var4_4 == var2_2) return;
                if (var4_4 == null) {
                    return;
                }
                if (var4_4.isMatched()) {
                    var3_3 = var4_4.next;
                    if (var3_3 == null) {
                        return;
                    }
                    if (var3_3 == var4_4 || !this.casHead(var4_4, var3_3)) continue;
                    var4_4.forgetNext();
                    continue;
                }
                break block6;
            }
            return;
        }
        if (var1_1.next == var1_1) return;
        if (var2_2.next == var2_2) return;
        do lbl-1000: // 3 sources:
        {
            if ((var5_5 = this.sweepVotes) >= 32) continue;
            if (!this.casSweepVotes(var5_5, var5_5 + 1)) ** GOTO lbl-1000
            return;
        } while (!this.casSweepVotes(var5_5, 0));
        this.sweep();
    }

    final class Itr
    implements Iterator<E> {
        private Node lastPred;
        private Node lastRet;
        private E nextItem;
        private Node nextNode;

        Itr() {
            this.advance(null);
        }

        private void advance(Node node) {
            Object object;
            Node node2 = this.lastRet;
            if (node2 != null && !node2.isMatched()) {
                this.lastPred = node2;
            } else {
                Node node3 = this.lastPred;
                if (node3 != null && !node3.isMatched()) {
                    while ((object = node3.next) != null && object != node3 && ((Node)object).isMatched() && (node2 = ((Node)object).next) != null && node2 != object) {
                        node3.casNext((Node)object, node2);
                    }
                } else {
                    this.lastPred = null;
                }
            }
            this.lastRet = node;
            do {
                block17 : {
                    block14 : {
                        block16 : {
                            block15 : {
                                if ((node2 = node == null ? LinkedTransferQueue.this.head : node.next) == null) break block14;
                                if (node2 == node) {
                                    node = null;
                                    continue;
                                }
                                object = node2.item;
                                if (!node2.isData) break block15;
                                if (object != null && object != node2) {
                                    this.nextItem = object;
                                    this.nextNode = node2;
                                    return;
                                }
                                break block16;
                            }
                            if (object == null) break block14;
                        }
                        if (node == null) {
                            node = node2;
                            continue;
                        }
                        object = node2.next;
                        if (object != null) break block17;
                    }
                    this.nextNode = null;
                    this.nextItem = null;
                    return;
                }
                if (node2 == object) {
                    node = null;
                    continue;
                }
                node.casNext(node2, (Node)object);
            } while (true);
        }

        @Override
        public final boolean hasNext() {
            boolean bl = this.nextNode != null;
            return bl;
        }

        @Override
        public final E next() {
            Node node = this.nextNode;
            if (node != null) {
                E e = this.nextItem;
                this.advance(node);
                return e;
            }
            throw new NoSuchElementException();
        }

        @Override
        public final void remove() {
            Node node = this.lastRet;
            if (node != null) {
                this.lastRet = null;
                if (node.tryMatchData()) {
                    LinkedTransferQueue.this.unsplice(this.lastPred, node);
                }
                return;
            }
            throw new IllegalStateException();
        }
    }

    final class LTQSpliterator<E>
    implements Spliterator<E> {
        static final int MAX_BATCH = 33554432;
        int batch;
        Node current;
        boolean exhausted;

        LTQSpliterator() {
        }

        @Override
        public int characteristics() {
            return 4368;
        }

        @Override
        public long estimateSize() {
            return Long.MAX_VALUE;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            block6 : {
                block7 : {
                    Object object;
                    Object object2;
                    block8 : {
                        if (consumer == null) break block6;
                        if (this.exhausted) break block7;
                        object2 = object = this.current;
                        if (object != null) break block8;
                        object2 = object = LinkedTransferQueue.this.firstDataNode();
                        if (object == null) break block7;
                    }
                    this.exhausted = true;
                    object = object2;
                    do {
                        if ((object2 = ((Node)object).item) != null && object2 != object) {
                            consumer.accept(object2);
                        }
                        if (object == (object2 = ((Node)object).next)) {
                            object2 = LinkedTransferQueue.this.firstDataNode();
                        }
                        if (object2 == null) break;
                        object = object2;
                    } while (((Node)object2).isData);
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> consumer) {
            block8 : {
                block9 : {
                    Object object;
                    Object object2;
                    Object object3;
                    block10 : {
                        if (consumer == null) break block8;
                        if (this.exhausted) break block9;
                        object3 = object2 = this.current;
                        if (object2 != null) break block10;
                        object3 = object2 = LinkedTransferQueue.this.firstDataNode();
                        if (object2 == null) break block9;
                    }
                    do {
                        object = object2 = ((Node)object3).item;
                        if (object2 == object3) {
                            object = null;
                        }
                        if (object3 == (object2 = ((Node)object3).next)) {
                            object2 = LinkedTransferQueue.this.firstDataNode();
                        }
                        if (object != null || object2 == null) break;
                        object3 = object2;
                    } while (((Node)object2).isData);
                    this.current = object2;
                    if (object2 == null) {
                        this.exhausted = true;
                    }
                    if (object != null) {
                        consumer.accept(object);
                        return true;
                    }
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public Spliterator<E> trySplit() {
            block13 : {
                Object object;
                int n;
                Object object2;
                int n2;
                block14 : {
                    n = this.batch;
                    n2 = 33554432;
                    if (n <= 0) {
                        n2 = 1;
                    } else if (n < 33554432) {
                        n2 = n + 1;
                    }
                    if (this.exhausted) break block13;
                    object = object2 = this.current;
                    if (object2 != null) break block14;
                    object = object2 = LinkedTransferQueue.this.firstDataNode();
                    if (object2 == null) break block13;
                }
                if (((Node)object).next != null) {
                    int n3;
                    Object[] arrobject = new Object[n2];
                    n = 0;
                    object2 = object;
                    do {
                        object = ((Node)object2).item;
                        n3 = n;
                        if (object != object2) {
                            arrobject[n] = object;
                            n3 = n;
                            if (object != null) {
                                n3 = n + 1;
                            }
                        }
                        if (object2 == (object = ((Node)object2).next)) {
                            object = LinkedTransferQueue.this.firstDataNode();
                        }
                        if (object == null || n3 >= n2) break;
                        object2 = object;
                        n = n3;
                    } while (((Node)object).isData);
                    this.current = object;
                    if (object == null) {
                        this.exhausted = true;
                    }
                    if (n3 > 0) {
                        this.batch = n3;
                        return Spliterators.spliterator(arrobject, 0, n3, 4368);
                    }
                }
            }
            return null;
        }
    }

    static final class Node {
        private static final long ITEM;
        private static final long NEXT;
        private static final Unsafe U;
        private static final long WAITER;
        private static final long serialVersionUID = -3375979862319811754L;
        final boolean isData;
        volatile Object item;
        volatile Node next;
        volatile Thread waiter;

        static {
            U = Unsafe.getUnsafe();
            try {
                ITEM = U.objectFieldOffset(Node.class.getDeclaredField("item"));
                NEXT = U.objectFieldOffset(Node.class.getDeclaredField("next"));
                WAITER = U.objectFieldOffset(Node.class.getDeclaredField("waiter"));
                return;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new Error(reflectiveOperationException);
            }
        }

        Node(Object object, boolean bl) {
            U.putObject(this, ITEM, object);
            this.isData = bl;
        }

        final boolean cannotPrecede(boolean bl) {
            Object object;
            boolean bl2 = this.isData;
            boolean bl3 = true;
            bl = bl2 != bl && (object = this.item) != this && (bl = object != null) == bl2 ? bl3 : false;
            return bl;
        }

        final boolean casItem(Object object, Object object2) {
            return U.compareAndSwapObject(this, ITEM, object, object2);
        }

        final boolean casNext(Node node, Node node2) {
            return U.compareAndSwapObject(this, NEXT, node, node2);
        }

        final void forgetContents() {
            U.putObject(this, ITEM, this);
            U.putObject(this, WAITER, null);
        }

        final void forgetNext() {
            U.putObject(this, NEXT, this);
        }

        final boolean isMatched() {
            boolean bl;
            Object object = this.item;
            boolean bl2 = false;
            if (object == this || (bl = object == null) == this.isData) {
                bl2 = true;
            }
            return bl2;
        }

        final boolean isUnmatchedRequest() {
            boolean bl = !this.isData && this.item == null;
            return bl;
        }

        final boolean tryMatchData() {
            Object object = this.item;
            if (object != null && object != this && this.casItem(object, null)) {
                LockSupport.unpark(this.waiter);
                return true;
            }
            return false;
        }
    }

}

