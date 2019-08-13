/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.Helpers;
import java.util.function.Consumer;
import sun.misc.Unsafe;

public class ConcurrentLinkedDeque<E>
extends AbstractCollection<E>
implements Deque<E>,
Serializable {
    private static final long HEAD;
    private static final int HOPS = 2;
    private static final Node<Object> NEXT_TERMINATOR;
    private static final Node<Object> PREV_TERMINATOR;
    private static final long TAIL;
    private static final Unsafe U;
    private static final long serialVersionUID = 876323262645176354L;
    private volatile transient Node<E> head;
    private volatile transient Node<E> tail;

    static {
        U = Unsafe.getUnsafe();
        Node<Object> node = PREV_TERMINATOR = new Node();
        node.next = node;
        NEXT_TERMINATOR = new Node();
        node = NEXT_TERMINATOR;
        node.prev = node;
        try {
            HEAD = U.objectFieldOffset(ConcurrentLinkedDeque.class.getDeclaredField("head"));
            TAIL = U.objectFieldOffset(ConcurrentLinkedDeque.class.getDeclaredField("tail"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public ConcurrentLinkedDeque() {
        Node<Object> node = new Node<Object>(null);
        this.tail = node;
        this.head = node;
    }

    public ConcurrentLinkedDeque(Collection<? extends E> object) {
        Object object2 = null;
        Object object3 = null;
        Iterator<E> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = new Node<E>(Objects.requireNonNull(iterator.next()));
            if (object2 == null) {
                object2 = object;
            } else {
                ((Node)object3).lazySetNext(object);
                ((Node)object).lazySetPrev(object3);
            }
            object3 = object;
        }
        this.initHeadTail((Node<E>)object2, (Node<E>)object3);
    }

    private boolean casHead(Node<E> node, Node<E> node2) {
        return U.compareAndSwapObject(this, HEAD, node, node2);
    }

    private boolean casTail(Node<E> node, Node<E> node2) {
        return U.compareAndSwapObject(this, TAIL, node, node2);
    }

    private void initHeadTail(Node<E> node, Node<E> node2) {
        Node<Object> node3 = node;
        Node<Object> node4 = node2;
        if (node == node2) {
            if (node == null) {
                node3 = new Node<Object>(null);
                node4 = node3;
            } else {
                node4 = new Node<Object>(null);
                node2.lazySetNext(node4);
                node4.lazySetPrev(node2);
                node3 = node;
            }
        }
        this.head = node3;
        this.tail = node4;
    }

    private void linkFirst(E object) {
        Node<E> node;
        Node<E> node2 = new Node<E>(Objects.requireNonNull(object));
        block0 : do {
            node = this.head;
            object = node;
            do {
                Node node3;
                if ((node3 = ((Node)object).prev) != null) {
                    object = node3;
                    Node node4 = node3.prev;
                    if (node4 != null) {
                        node3 = this.head;
                        object = node != node3 ? node3 : node4;
                        node = node3;
                        continue;
                    }
                }
                if (((Node)object).next == object) continue block0;
                node2.lazySetNext((Node<E>)object);
                if (((Node)object).casPrev(null, node2)) break block0;
            } while (true);
            break;
        } while (true);
        if (object != node) {
            this.casHead(node, node2);
        }
    }

    private void linkLast(E object) {
        Node<E> node;
        Node<E> node2 = new Node<E>(Objects.requireNonNull(object));
        block0 : do {
            node = this.tail;
            object = node;
            do {
                Node node3;
                if ((node3 = ((Node)object).next) != null) {
                    object = node3;
                    Node node4 = node3.next;
                    if (node4 != null) {
                        node3 = this.tail;
                        object = node != node3 ? node3 : node4;
                        node = node3;
                        continue;
                    }
                }
                if (((Node)object).prev == object) continue block0;
                node2.lazySetPrev((Node<E>)object);
                if (((Node)object).casNext(null, node2)) break block0;
            } while (true);
            break;
        } while (true);
        if (object != node) {
            this.casTail(node, node2);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Node<Object> node;
        objectInputStream.defaultReadObject();
        Node<Object> node2 = null;
        Node<Object> node3 = null;
        while ((node = objectInputStream.readObject()) != null) {
            node = new Node<Object>(node);
            if (node2 == null) {
                node2 = node;
            } else {
                node3.lazySetNext(node);
                node.lazySetPrev(node3);
            }
            node3 = node;
        }
        this.initHeadTail(node2, node3);
    }

    private E screenNullResult(E e) {
        if (e != null) {
            return e;
        }
        throw new NoSuchElementException();
    }

    private void skipDeletedPredecessors(Node<E> node) {
        block0 : do {
            Node node2;
            Node node3 = node2 = node.prev;
            do {
                Node node4;
                block14 : {
                    block13 : {
                        block12 : {
                            block11 : {
                                if (node3.item != null) break block11;
                                node4 = node3.prev;
                                if (node4 != null) break block12;
                                if (node3.next == node3) break block13;
                            }
                            if (node2 == node3 || node.casPrev(node2, node3)) {
                                return;
                            }
                            break block13;
                        }
                        if (node3 != node4) break block14;
                    }
                    if (node.item != null || node.next == null) continue block0;
                    return;
                }
                node3 = node4;
            } while (true);
            break;
        } while (true);
    }

    private void skipDeletedSuccessors(Node<E> node) {
        block0 : do {
            Node node2;
            Node node3 = node2 = node.next;
            do {
                Node node4;
                block14 : {
                    block13 : {
                        block12 : {
                            block11 : {
                                if (node3.item != null) break block11;
                                node4 = node3.next;
                                if (node4 != null) break block12;
                                if (node3.prev == node3) break block13;
                            }
                            if (node2 == node3 || node.casNext(node2, node3)) {
                                return;
                            }
                            break block13;
                        }
                        if (node3 != node4) break block14;
                    }
                    if (node.item != null || node.prev == null) continue block0;
                    return;
                }
                node3 = node4;
            } while (true);
            break;
        } while (true);
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

    private void unlinkFirst(Node<E> node, Node<E> node2) {
        Node node3;
        Node<E> node4 = null;
        Node<E> node5 = node2;
        while (node5.item == null && (node3 = node5.next) != null) {
            if (node5 == node3) {
                return;
            }
            node4 = node5;
            node5 = node3;
        }
        if (node4 != null && node5.prev != node5 && node.casNext(node2, node5)) {
            this.skipDeletedPredecessors(node5);
            if (node.prev == null && (node5.next == null || node5.item != null) && node5.prev == node) {
                this.updateHead();
                this.updateTail();
                node4.lazySetNext(node4);
                node4.lazySetPrev(this.prevTerminator());
            }
        }
    }

    private void unlinkLast(Node<E> node, Node<E> node2) {
        Node node3;
        Node<E> node4 = null;
        Node<E> node5 = node2;
        while (node5.item == null && (node3 = node5.prev) != null) {
            if (node5 == node3) {
                return;
            }
            node4 = node5;
            node5 = node3;
        }
        if (node4 != null && node5.next != node5 && node.casPrev(node2, node5)) {
            this.skipDeletedSuccessors(node5);
            if (node.next == null && (node5.prev == null || node5.item != null) && node5.next == node) {
                this.updateHead();
                this.updateTail();
                node4.lazySetPrev(node4);
                node4.lazySetNext(this.nextTerminator());
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final void updateHead() {
        block0 : do lbl-1000: // 3 sources:
        {
            var1_1 = this.head;
            if (var1_1.item != null) return;
            var3_3 = var2_2 = var1_1.prev;
            if (var2_2 == null) return;
            while ((var2_2 = var3_3.prev) != null) {
                var3_3 = var2_2;
                var2_2 = var2_2.prev;
                if (var2_2 == null) continue block0;
                if (var1_1 != this.head) ** GOTO lbl-1000
                var3_3 = var2_2;
            }
        } while (!this.casHead(var1_1, var3_3));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private final void updateTail() {
        block0 : do lbl-1000: // 3 sources:
        {
            var1_1 = this.tail;
            if (var1_1.item != null) return;
            var3_3 = var2_2 = var1_1.next;
            if (var2_2 == null) return;
            while ((var2_2 = var3_3.next) != null) {
                var3_3 = var2_2;
                var2_2 = var2_2.next;
                if (var2_2 == null) continue block0;
                if (var1_1 != this.tail) ** GOTO lbl-1000
                var3_3 = var2_2;
            }
        } while (!this.casTail(var1_1, var3_3));
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
        return this.offerLast(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> node) {
        if (node != this) {
            Node node2 = null;
            Object object = null;
            Node node3 = node.iterator();
            while (node3.hasNext()) {
                node = new Node<E>(Objects.requireNonNull(node3.next()));
                if (node2 == null) {
                    node2 = node;
                } else {
                    ((Node)object).lazySetNext(node);
                    node.lazySetPrev(object);
                }
                object = node;
            }
            if (node2 == null) {
                return false;
            }
            block1 : do {
                node = node3 = this.tail;
                do {
                    Node node4 = node.next;
                    Node node5 = node;
                    if (node4 != null) {
                        node5 = node4;
                        node = node4.next;
                        if (node != null) {
                            node5 = this.tail;
                            if (node3 != node5) {
                                node = node5;
                            }
                            node3 = node5;
                            continue;
                        }
                    }
                    if (node5.prev == node5) continue block1;
                    node2.lazySetPrev(node5);
                    node = node5;
                    if (node5.casNext(null, node2)) break block1;
                } while (true);
                break;
            } while (true);
            if (!this.casTail(node3, (Node<E>)object)) {
                node = this.tail;
                if (((Node)object).next == null) {
                    this.casTail(node, (Node<E>)object);
                }
            }
            return true;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void addFirst(E e) {
        this.linkFirst(e);
    }

    @Override
    public void addLast(E e) {
        this.linkLast(e);
    }

    @Override
    public void clear() {
        while (this.pollFirst() != null) {
        }
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

    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingItr();
    }

    @Override
    public E element() {
        return this.getFirst();
    }

    Node<E> first() {
        Node<E> node;
        Node<E> node2;
        block0 : do {
            Node node3;
            node = node2 = this.head;
            while ((node3 = node.prev) != null) {
                node = node3;
                Node node4 = node3.prev;
                if (node4 == null) continue block0;
                node3 = this.head;
                node = node2 != node3 ? node3 : node4;
                node2 = node3;
            }
        } while (node != node2 && !this.casHead(node2, node));
        return node;
    }

    @Override
    public E getFirst() {
        return this.screenNullResult(this.peekFirst());
    }

    @Override
    public E getLast() {
        return this.screenNullResult(this.peekLast());
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.peekFirst() == null;
        return bl;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    Node<E> last() {
        Node<E> node;
        Node<E> node2;
        block0 : do {
            Node node3;
            node = node2 = this.tail;
            while ((node3 = node.next) != null) {
                node = node3;
                Node node4 = node3.next;
                if (node4 == null) continue block0;
                node3 = this.tail;
                node = node2 != node3 ? node3 : node4;
                node2 = node3;
            }
        } while (node != node2 && !this.casTail(node2, node));
        return node;
    }

    Node<E> nextTerminator() {
        return NEXT_TERMINATOR;
    }

    @Override
    public boolean offer(E e) {
        return this.offerLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        this.linkFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        this.linkLast(e);
        return true;
    }

    @Override
    public E peek() {
        return this.peekFirst();
    }

    @Override
    public E peekFirst() {
        Node<E> node = this.first();
        while (node != null) {
            Object e = node.item;
            if (e != null) {
                return e;
            }
            node = this.succ(node);
        }
        return null;
    }

    @Override
    public E peekLast() {
        Node<E> node = this.last();
        while (node != null) {
            Object e = node.item;
            if (e != null) {
                return e;
            }
            node = this.pred(node);
        }
        return null;
    }

    @Override
    public E poll() {
        return this.pollFirst();
    }

    @Override
    public E pollFirst() {
        Node<Object> node = this.first();
        while (node != null) {
            Object e = node.item;
            if (e != null && node.casItem(e, null)) {
                this.unlink(node);
                return e;
            }
            node = this.succ(node);
        }
        return null;
    }

    @Override
    public E pollLast() {
        Node<Object> node = this.last();
        while (node != null) {
            Object e = node.item;
            if (e != null && node.casItem(e, null)) {
                this.unlink(node);
                return e;
            }
            node = this.pred(node);
        }
        return null;
    }

    @Override
    public E pop() {
        return this.removeFirst();
    }

    final Node<E> pred(Node<E> node) {
        Node node2 = node.prev;
        node = node == node2 ? this.last() : node2;
        return node;
    }

    Node<E> prevTerminator() {
        return PREV_TERMINATOR;
    }

    @Override
    public void push(E e) {
        this.addFirst(e);
    }

    @Override
    public E remove() {
        return this.removeFirst();
    }

    @Override
    public boolean remove(Object object) {
        return this.removeFirstOccurrence(object);
    }

    @Override
    public E removeFirst() {
        return this.screenNullResult(this.pollFirst());
    }

    @Override
    public boolean removeFirstOccurrence(Object object) {
        Objects.requireNonNull(object);
        Node<Object> node = this.first();
        while (node != null) {
            Object e = node.item;
            if (e != null && object.equals(e) && node.casItem(e, null)) {
                this.unlink(node);
                return true;
            }
            node = this.succ(node);
        }
        return false;
    }

    @Override
    public E removeLast() {
        return this.screenNullResult(this.pollLast());
    }

    @Override
    public boolean removeLastOccurrence(Object object) {
        Objects.requireNonNull(object);
        Node<Object> node = this.last();
        while (node != null) {
            Object e = node.item;
            if (e != null && object.equals(e) && node.casItem(e, null)) {
                this.unlink(node);
                return true;
            }
            node = this.pred(node);
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
        return new CLDSpliterator(this);
    }

    final Node<E> succ(Node<E> node) {
        Node node2 = node.next;
        node = node == node2 ? this.first() : node2;
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    void unlink(Node<E> node) {
        Node node2 = node.prev;
        Node node3 = node.next;
        if (node2 == null) {
            this.unlinkFirst(node, node3);
            return;
        }
        if (node3 == null) {
            this.unlinkLast(node, node2);
            return;
        }
        int n = 1;
        do {
            Node node4;
            block15 : {
                boolean bl;
                block14 : {
                    block13 : {
                        if (node2.item == null) break block13;
                        bl = false;
                        break block14;
                    }
                    node4 = node2.prev;
                    if (node4 != null) break block15;
                    if (node2.next == node2) {
                        return;
                    }
                    bl = true;
                }
                node4 = node2;
                node2 = node3;
                do {
                    block18 : {
                        boolean bl2;
                        block17 : {
                            block16 : {
                                if (node2.item == null) break block16;
                                bl2 = false;
                                break block17;
                            }
                            node3 = node2.next;
                            if (node3 != null) break block18;
                            if (node2.prev == node2) {
                                return;
                            }
                            bl2 = true;
                        }
                        if (n < 2 && bl | bl2) {
                            return;
                        }
                        this.skipDeletedSuccessors(node4);
                        this.skipDeletedPredecessors(node2);
                        if (!(bl | bl2) || node4.next != node2 || node2.prev != node4 || !(bl ? node4.prev == null : node4.item != null) || !(bl2 ? node2.next == null : node2.item != null)) return;
                        this.updateHead();
                        this.updateTail();
                        node2 = bl ? this.prevTerminator() : node;
                        node.lazySetPrev(node2);
                        node2 = bl2 ? this.nextTerminator() : node;
                        node.lazySetNext(node2);
                        return;
                    }
                    if (node2 == node3) {
                        return;
                    }
                    node2 = node3;
                    ++n;
                } while (true);
            }
            if (node2 == node4) {
                return;
            }
            node2 = node4;
            ++n;
        } while (true);
    }

    private abstract class AbstractItr
    implements Iterator<E> {
        private Node<E> lastRet;
        private E nextItem;
        private Node<E> nextNode;

        AbstractItr() {
            this.advance();
        }

        private void advance() {
            Node<E> node = this.nextNode;
            this.lastRet = node;
            node = node == null ? this.startNode() : this.nextNode(node);
            do {
                block5 : {
                    block4 : {
                        block3 : {
                            if (node != null) break block3;
                            this.nextNode = null;
                            this.nextItem = null;
                            break block4;
                        }
                        Object e = node.item;
                        if (e == null) break block5;
                        this.nextNode = node;
                        this.nextItem = e;
                    }
                    return;
                }
                node = this.nextNode(node);
            } while (true);
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.nextItem != null;
            return bl;
        }

        @Override
        public E next() {
            E e = this.nextItem;
            if (e != null) {
                this.advance();
                return e;
            }
            throw new NoSuchElementException();
        }

        abstract Node<E> nextNode(Node<E> var1);

        @Override
        public void remove() {
            Node<E> node = this.lastRet;
            if (node != null) {
                node.item = null;
                ConcurrentLinkedDeque.this.unlink(node);
                this.lastRet = null;
                return;
            }
            throw new IllegalStateException();
        }

        abstract Node<E> startNode();
    }

    static final class CLDSpliterator<E>
    implements Spliterator<E> {
        static final int MAX_BATCH = 33554432;
        int batch;
        Node<E> current;
        boolean exhausted;
        final ConcurrentLinkedDeque<E> queue;

        CLDSpliterator(ConcurrentLinkedDeque<E> concurrentLinkedDeque) {
            this.queue = concurrentLinkedDeque;
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
                    ConcurrentLinkedDeque<E> concurrentLinkedDeque;
                    block8 : {
                        if (consumer == null) break block6;
                        concurrentLinkedDeque = this.queue;
                        if (this.exhausted) break block7;
                        node = node2 = this.current;
                        if (node2 != null) break block8;
                        node = node2 = concurrentLinkedDeque.first();
                        if (node2 == null) break block7;
                    }
                    this.exhausted = true;
                    node2 = node;
                    do {
                        Object e = node2.item;
                        node = node2.next;
                        if (node2 == node) {
                            node = concurrentLinkedDeque.first();
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
                    Node<E> node2;
                    ConcurrentLinkedDeque<E> concurrentLinkedDeque;
                    Object e;
                    block9 : {
                        if (consumer == null) break block7;
                        concurrentLinkedDeque = this.queue;
                        if (this.exhausted) break block8;
                        node = node2 = this.current;
                        if (node2 != null) break block9;
                        node = node2 = concurrentLinkedDeque.first();
                        if (node2 == null) break block8;
                    }
                    do {
                        e = node.item;
                        node2 = node.next;
                        if (node == node2) {
                            node2 = concurrentLinkedDeque.first();
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
            block13 : {
                Node<E> node;
                Node<E> node2;
                int n;
                ConcurrentLinkedDeque<E> concurrentLinkedDeque;
                int n2;
                block14 : {
                    concurrentLinkedDeque = this.queue;
                    n2 = this.batch;
                    n = 33554432;
                    if (n2 <= 0) {
                        n = 1;
                    } else if (n2 < 33554432) {
                        n = n2 + 1;
                    }
                    if (this.exhausted) break block13;
                    node2 = node = this.current;
                    if (node != null) break block14;
                    node2 = node = concurrentLinkedDeque.first();
                    if (node == null) break block13;
                }
                node = node2;
                if (node2.item == null && node2 == (node = node2.next)) {
                    node = node2 = concurrentLinkedDeque.first();
                    this.current = node2;
                }
                if (node != null && node.next != null) {
                    int n3;
                    Object[] arrobject = new Object[n];
                    n2 = 0;
                    node2 = node;
                    do {
                        arrobject[n2] = node = node2.item;
                        n3 = n2;
                        if (node != null) {
                            n3 = n2 + 1;
                        }
                        if (node2 == (node = node2.next)) {
                            node = concurrentLinkedDeque.first();
                        }
                        if (node == null) break;
                        node2 = node;
                        n2 = n3;
                    } while (n3 < n);
                    this.current = node;
                    if (node == null) {
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

    private class DescendingItr
    extends ConcurrentLinkedDeque<E> {
        private DescendingItr() {
        }

        Node<E> nextNode(Node<E> node) {
            return ConcurrentLinkedDeque.this.pred(node);
        }

        Node<E> startNode() {
            return ConcurrentLinkedDeque.this.last();
        }
    }

    private class Itr
    extends ConcurrentLinkedDeque<E> {
        private Itr() {
        }

        Node<E> nextNode(Node<E> node) {
            return ConcurrentLinkedDeque.this.succ(node);
        }

        Node<E> startNode() {
            return ConcurrentLinkedDeque.this.first();
        }
    }

    static final class Node<E> {
        private static final long ITEM;
        private static final long NEXT;
        private static final long PREV;
        private static final Unsafe U;
        volatile E item;
        volatile Node<E> next;
        volatile Node<E> prev;

        static {
            U = Unsafe.getUnsafe();
            try {
                PREV = U.objectFieldOffset(Node.class.getDeclaredField("prev"));
                ITEM = U.objectFieldOffset(Node.class.getDeclaredField("item"));
                NEXT = U.objectFieldOffset(Node.class.getDeclaredField("next"));
                return;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new Error(reflectiveOperationException);
            }
        }

        Node() {
        }

        Node(E e) {
            U.putObject(this, ITEM, e);
        }

        boolean casItem(E e, E e2) {
            return U.compareAndSwapObject(this, ITEM, e, e2);
        }

        boolean casNext(Node<E> node, Node<E> node2) {
            return U.compareAndSwapObject(this, NEXT, node, node2);
        }

        boolean casPrev(Node<E> node, Node<E> node2) {
            return U.compareAndSwapObject(this, PREV, node, node2);
        }

        void lazySetNext(Node<E> node) {
            U.putOrderedObject(this, NEXT, node);
        }

        void lazySetPrev(Node<E> node) {
            U.putOrderedObject(this, PREV, node);
        }
    }

}

