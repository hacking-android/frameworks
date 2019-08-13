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
import java.util.Objects;
import java.util.Queue;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.Helpers;
import java.util.function.Consumer;
import sun.misc.Unsafe;

public class ConcurrentLinkedQueue<E>
extends AbstractQueue<E>
implements Queue<E>,
Serializable {
    private static final long HEAD;
    private static final long ITEM;
    private static final long NEXT;
    private static final long TAIL;
    private static final Unsafe U;
    private static final long serialVersionUID = 196745693267521676L;
    volatile transient Node<E> head;
    private volatile transient Node<E> tail;

    static {
        U = Unsafe.getUnsafe();
        try {
            HEAD = U.objectFieldOffset(ConcurrentLinkedQueue.class.getDeclaredField("head"));
            TAIL = U.objectFieldOffset(ConcurrentLinkedQueue.class.getDeclaredField("tail"));
            ITEM = U.objectFieldOffset(Node.class.getDeclaredField("item"));
            NEXT = U.objectFieldOffset(Node.class.getDeclaredField("next"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public ConcurrentLinkedQueue() {
        Node<Object> node = ConcurrentLinkedQueue.newNode(null);
        this.tail = node;
        this.head = node;
    }

    public ConcurrentLinkedQueue(Collection<? extends E> object) {
        Node<Object> node = null;
        Node<Object> node2 = null;
        Iterator<E> iterator = object.iterator();
        object = node;
        while (iterator.hasNext()) {
            node = ConcurrentLinkedQueue.newNode(Objects.requireNonNull(iterator.next()));
            if (object == null) {
                object = node;
            } else {
                ConcurrentLinkedQueue.lazySetNext(node2, node);
            }
            node2 = node;
        }
        node = object;
        if (object == null) {
            node2 = node = ConcurrentLinkedQueue.newNode(null);
        }
        this.head = node;
        this.tail = node2;
    }

    private boolean casHead(Node<E> node, Node<E> node2) {
        return U.compareAndSwapObject(this, HEAD, node, node2);
    }

    static <E> boolean casItem(Node<E> node, E e, E e2) {
        return U.compareAndSwapObject(node, ITEM, e, e2);
    }

    static <E> boolean casNext(Node<E> node, Node<E> node2, Node<E> node3) {
        return U.compareAndSwapObject(node, NEXT, node2, node3);
    }

    private boolean casTail(Node<E> node, Node<E> node2) {
        return U.compareAndSwapObject(this, TAIL, node, node2);
    }

    static <E> void lazySetNext(Node<E> node, Node<E> node2) {
        U.putOrderedObject(node, NEXT, node2);
    }

    static <E> Node<E> newNode(E e) {
        Node node = new Node();
        U.putObject(node, ITEM, e);
        return node;
    }

    private void readObject(ObjectInputStream node) throws IOException, ClassNotFoundException {
        Node<Object> node2;
        ((ObjectInputStream)((Object)node)).defaultReadObject();
        Node<Object> node3 = null;
        Node<Object> node4 = null;
        while ((node2 = ((ObjectInputStream)((Object)node)).readObject()) != null) {
            node2 = ConcurrentLinkedQueue.newNode(node2);
            if (node3 == null) {
                node3 = node2;
            } else {
                ConcurrentLinkedQueue.lazySetNext(node4, node2);
            }
            node4 = node2;
        }
        node = node3;
        if (node3 == null) {
            node4 = node = ConcurrentLinkedQueue.newNode(null);
        }
        this.head = node;
        this.tail = node4;
    }

    private Object[] toArrayInternal(Object[] arrobject) {
        int n;
        Object object = arrobject;
        block0 : do {
            n = 0;
            Object object2 = this.first();
            while (object2 != null) {
                Object e = object2.item;
                Object[] arrobject2 = object;
                int n2 = n;
                if (e != null) {
                    if (object == null) {
                        arrobject2 = new Object[4];
                    } else {
                        arrobject2 = object;
                        if (n == ((Object[])object).length) {
                            arrobject2 = Arrays.copyOf(object, (n + 4) * 2);
                        }
                    }
                    arrobject2[n] = e;
                    n2 = n + 1;
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

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Node<E> node = this.first();
        while (node != null) {
            Object e = node.item;
            if (e != null) {
                objectOutputStream.writeObject(e);
            }
            node = this.succ(node);
        }
        objectOutputStream.writeObject(null);
    }

    @Override
    public boolean add(E e) {
        return this.offer(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> node) {
        block8 : {
            if (node == this) break block8;
            Node<E> node2 = null;
            Node<E> node3 = null;
            Node<E> node4 = node.iterator();
            while (node4.hasNext()) {
                node = ConcurrentLinkedQueue.newNode(Objects.requireNonNull(node4.next()));
                if (node2 == null) {
                    node2 = node;
                } else {
                    ConcurrentLinkedQueue.lazySetNext(node3, node);
                }
                node3 = node;
            }
            if (node2 == null) {
                return false;
            }
            Node<E> node5 = node4 = this.tail;
            do {
                Node<E> node6;
                block10 : {
                    block14 : {
                        Node node7;
                        block13 : {
                            block12 : {
                                block11 : {
                                    block9 : {
                                        if ((node7 = node5.next) != null) break block9;
                                        node = node4;
                                        node6 = node5;
                                        if (ConcurrentLinkedQueue.casNext(node5, null, node2)) {
                                            if (!this.casTail(node4, node3)) {
                                                node = this.tail;
                                                if (node3.next == null) {
                                                    this.casTail(node, node3);
                                                }
                                            }
                                            return true;
                                        }
                                        break block10;
                                    }
                                    if (node5 != node7) break block11;
                                    node6 = this.tail;
                                    node = node4 != node6 ? node6 : this.head;
                                    node4 = node;
                                    node = node6;
                                    node6 = node4;
                                    break block10;
                                }
                                if (node5 == node4) break block12;
                                node6 = node = (node5 = this.tail);
                                if (node4 == node5) break block13;
                                node4 = node;
                                break block14;
                            }
                            node6 = node4;
                        }
                        node4 = node6;
                        node = node7;
                    }
                    node6 = node;
                    node = node4;
                }
                node4 = node;
                node5 = node6;
            } while (true);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean contains(Object object) {
        if (object != null) {
            Node<E> node = this.first();
            while (node != null) {
                Object e = node.item;
                if (e != null && object.equals(e)) {
                    return true;
                }
                node = this.succ(node);
            }
        }
        return false;
    }

    Node<E> first() {
        Node<E> node;
        Node<E> node2;
        boolean bl;
        block0 : do {
            Node node3;
            node2 = node = this.head;
            while (!(bl = node2.item != null) && (node3 = node2.next) != null) {
                if (node2 == node3) continue block0;
                node2 = node3;
            }
            break;
        } while (true);
        this.updateHead(node, node2);
        if (!bl) {
            node2 = null;
        }
        return node2;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.first() == null;
        return bl;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public boolean offer(E object) {
        Node<E> node;
        Node<E> node2 = ConcurrentLinkedQueue.newNode(Objects.requireNonNull(object));
        Node<E> node3 = node = this.tail;
        do {
            Node<E> node4;
            block4 : {
                block8 : {
                    Node node5;
                    block7 : {
                        block6 : {
                            block5 : {
                                block3 : {
                                    if ((node5 = node3.next) != null) break block3;
                                    object = node;
                                    node4 = node3;
                                    if (ConcurrentLinkedQueue.casNext(node3, null, node2)) {
                                        if (node3 != node) {
                                            this.casTail(node, node2);
                                        }
                                        return true;
                                    }
                                    break block4;
                                }
                                if (node3 != node5) break block5;
                                node4 = this.tail;
                                object = node != node4 ? node4 : this.head;
                                node = object;
                                object = node4;
                                node4 = node;
                                break block4;
                            }
                            if (node3 == node) break block6;
                            node3 = this.tail;
                            object = node3;
                            node4 = object;
                            if (node == node3) break block7;
                            node = object;
                            break block8;
                        }
                        node4 = node;
                    }
                    node = node4;
                    object = node5;
                }
                node4 = object;
                object = node;
            }
            node = object;
            node3 = node4;
        } while (true);
    }

    @Override
    public E peek() {
        Object e;
        Node<E> node;
        Node<E> node2;
        block0 : do {
            Node node3;
            node2 = node = this.head;
            while ((e = node2.item) == null && (node3 = node2.next) != null) {
                if (node2 == node3) continue block0;
                node2 = node3;
            }
            break;
        } while (true);
        this.updateHead(node, node2);
        return e;
    }

    @Override
    public E poll() {
        block0 : do {
            Node<E> node;
            Node<E> node2 = node = this.head;
            do {
                Object e;
                Node node3;
                if ((e = node2.item) != null && ConcurrentLinkedQueue.casItem(node2, e, null)) {
                    if (node2 != node) {
                        node3 = node2.next;
                        if (node3 != null) {
                            node2 = node3;
                        }
                        this.updateHead(node, node2);
                    }
                    return e;
                }
                node3 = node2.next;
                if (node3 == null) {
                    this.updateHead(node, node2);
                    return null;
                }
                if (node2 == node3) continue block0;
                node2 = node3;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean remove(Object var1_1) {
        if (var1_1 == null) return false;
        var2_2 = null;
        var3_8 = this.first();
        while (var3_9 != null) {
            var4_11 = false;
            var5_13 = var3_9.item;
            if (var5_13 == null) ** GOTO lbl12
            if (!var1_1.equals(var5_13)) {
                var2_4 = this.succ((Node<E>)var3_9);
            } else {
                var4_11 = ConcurrentLinkedQueue.casItem(var3_9, var5_13, null);
lbl12: // 2 sources:
                var5_14 = this.succ((Node<E>)var3_9);
                if (var2_3 != null && var5_14 != null) {
                    ConcurrentLinkedQueue.casNext(var2_3, var3_9, var5_14);
                }
                var2_5 = var5_14;
                if (var4_11) {
                    return true;
                }
            }
            var5_12 = var2_6;
            var2_7 = var3_9;
            var3_10 = var5_12;
        }
        return false;
    }

    @Override
    public int size() {
        int n;
        block0 : do {
            int n2 = 0;
            Node<E> node = this.first();
            do {
                Node node2;
                n = n2;
                if (node == null) break block0;
                n = n2++;
                if (node.item != null) {
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

    @Override
    public Spliterator<E> spliterator() {
        return new CLQSpliterator(this);
    }

    final Node<E> succ(Node<E> node) {
        Node node2 = node.next;
        node = node == node2 ? this.head : node2;
        return node;
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
            String[] arrstring2 = this.first();
            object = arrstring;
            while (arrstring2 != null) {
                Object e = arrstring2.item;
                arrstring = object;
                int n3 = n2;
                int n4 = n;
                if (e != null) {
                    if (object == null) {
                        arrstring = new String[4];
                    } else {
                        arrstring = object;
                        if (n == ((String[])object).length) {
                            arrstring = Arrays.copyOf(object, n * 2);
                        }
                    }
                    arrstring[n] = object = e.toString();
                    n3 = n2 + ((String)object).length();
                    n4 = n + 1;
                }
                if (arrstring2 == (object = arrstring2.next)) continue block0;
                arrstring2 = object;
                object = arrstring;
                n2 = n3;
                n = n4;
            }
            break;
        } while (true);
        if (n == 0) {
            return "[]";
        }
        return Helpers.toString(object, n, n2);
    }

    final void updateHead(Node<E> node, Node<E> node2) {
        if (node != node2 && this.casHead(node, node2)) {
            ConcurrentLinkedQueue.lazySetNext(node, node);
        }
    }

    static final class CLQSpliterator<E>
    implements Spliterator<E> {
        static final int MAX_BATCH = 33554432;
        int batch;
        Node<E> current;
        boolean exhausted;
        final ConcurrentLinkedQueue<E> queue;

        CLQSpliterator(ConcurrentLinkedQueue<E> concurrentLinkedQueue) {
            this.queue = concurrentLinkedQueue;
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
                    Node<E> node;
                    Node<E> node2;
                    ConcurrentLinkedQueue<E> concurrentLinkedQueue;
                    block8 : {
                        if (consumer == null) break block6;
                        concurrentLinkedQueue = this.queue;
                        if (this.exhausted) break block7;
                        node = node2 = this.current;
                        if (node2 != null) break block8;
                        node = node2 = concurrentLinkedQueue.first();
                        if (node2 == null) break block7;
                    }
                    this.exhausted = true;
                    node2 = node;
                    do {
                        Object e = node2.item;
                        node = node2.next;
                        if (node2 == node) {
                            node = concurrentLinkedQueue.first();
                        }
                        if (e != null) {
                            consumer.accept(e);
                        }
                        node2 = node;
                    } while (node != null);
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> consumer) {
            block7 : {
                block8 : {
                    Node<E> node;
                    Object e;
                    Node<E> node2;
                    ConcurrentLinkedQueue<E> concurrentLinkedQueue;
                    block9 : {
                        if (consumer == null) break block7;
                        concurrentLinkedQueue = this.queue;
                        if (this.exhausted) break block8;
                        node = node2 = this.current;
                        if (node2 != null) break block9;
                        node = node2 = concurrentLinkedQueue.first();
                        if (node2 == null) break block8;
                    }
                    do {
                        e = node.item;
                        node2 = node.next;
                        if (node == node2) {
                            node2 = concurrentLinkedQueue.first();
                        }
                        if (e != null) break;
                        node = node2;
                    } while (node2 != null);
                    this.current = node2;
                    if (node2 == null) {
                        this.exhausted = true;
                    }
                    if (e != null) {
                        consumer.accept(e);
                        return true;
                    }
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public Spliterator<E> trySplit() {
            block12 : {
                ConcurrentLinkedQueue<E> concurrentLinkedQueue;
                int n;
                Node<E> node;
                Node<E> node2;
                int n2;
                block13 : {
                    concurrentLinkedQueue = this.queue;
                    n2 = this.batch;
                    n = 33554432;
                    if (n2 <= 0) {
                        n = 1;
                    } else if (n2 < 33554432) {
                        n = n2 + 1;
                    }
                    if (this.exhausted) break block12;
                    node2 = node = this.current;
                    if (node != null) break block13;
                    node2 = node = concurrentLinkedQueue.first();
                    if (node == null) break block12;
                }
                if (node2.next != null) {
                    int n3;
                    Object[] arrobject = new Object[n];
                    n2 = 0;
                    node = node2;
                    do {
                        arrobject[n2] = node2 = node.item;
                        n3 = n2;
                        if (node2 != null) {
                            n3 = n2 + 1;
                        }
                        if (node == (node2 = node.next)) {
                            node2 = concurrentLinkedQueue.first();
                        }
                        if (node2 == null) break;
                        node = node2;
                        n2 = n3;
                    } while (n3 < n);
                    this.current = node2;
                    if (node2 == null) {
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

    private class Itr
    implements Iterator<E> {
        private Node<E> lastRet;
        private E nextItem;
        private Node<E> nextNode;

        Itr() {
            block0 : do {
                Node node;
                Node node2 = node = ConcurrentLinkedQueue.this.head;
                do {
                    Object object;
                    block6 : {
                        block5 : {
                            block4 : {
                                if ((object = node2.item) == null) break block4;
                                this.nextNode = node2;
                                this.nextItem = object;
                                break block5;
                            }
                            object = node2.next;
                            if (object != null) break block6;
                        }
                        ConcurrentLinkedQueue.this.updateHead(node, node2);
                        return;
                    }
                    if (node2 == object) continue block0;
                    node2 = object;
                } while (true);
                break;
            } while (true);
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.nextItem != null;
            return bl;
        }

        @Override
        public E next() {
            Node<E> node = this.nextNode;
            if (node != null) {
                this.lastRet = node;
                Object var2_2 = null;
                Node<E> node2 = ConcurrentLinkedQueue.this.succ(node);
                while (node2 != null) {
                    Object object = node2.item;
                    var2_2 = object;
                    if (object != null) break;
                    object = ConcurrentLinkedQueue.this.succ(node2);
                    if (object != null) {
                        ConcurrentLinkedQueue.casNext(node, node2, object);
                    }
                    node2 = object;
                }
                this.nextNode = node2;
                node2 = this.nextItem;
                this.nextItem = var2_2;
                return (E)node2;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            Node<E> node = this.lastRet;
            if (node != null) {
                node.item = null;
                this.lastRet = null;
                return;
            }
            throw new IllegalStateException();
        }
    }

    private static class Node<E> {
        volatile E item;
        volatile Node<E> next;

        private Node() {
        }
    }

}

