/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ArrayDeque<E>
extends AbstractCollection<E>
implements Deque<E>,
Cloneable,
Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int MIN_INITIAL_CAPACITY = 8;
    private static final long serialVersionUID = 2340985798034038923L;
    transient Object[] elements;
    transient int head;
    transient int tail;

    public ArrayDeque() {
        this.elements = new Object[16];
    }

    public ArrayDeque(int n) {
        this.allocateElements(n);
    }

    public ArrayDeque(Collection<? extends E> collection) {
        this.allocateElements(collection.size());
        this.addAll(collection);
    }

    private void allocateElements(int n) {
        int n2 = 8;
        if (n >= 8) {
            n |= n >>> 1;
            n |= n >>> 2;
            n |= n >>> 4;
            n |= n >>> 8;
            n2 = n = (n | n >>> 16) + 1;
            if (n < 0) {
                n2 = n >>> 1;
            }
        }
        this.elements = new Object[n2];
    }

    private void checkInvariants() {
    }

    private void doubleCapacity() {
        int n = this.head;
        Object[] arrobject = this.elements;
        int n2 = arrobject.length;
        int n3 = n2 - n;
        int n4 = n2 << 1;
        if (n4 >= 0) {
            Object[] arrobject2 = new Object[n4];
            System.arraycopy(arrobject, n, arrobject2, 0, n3);
            System.arraycopy(this.elements, 0, arrobject2, n3, n);
            Arrays.fill(this.elements, null);
            this.elements = arrobject2;
            this.head = 0;
            this.tail = n2;
            return;
        }
        throw new IllegalStateException("Sorry, deque too big");
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int n = objectInputStream.readInt();
        this.allocateElements(n);
        this.head = 0;
        this.tail = n;
        for (int i = 0; i < n; ++i) {
            this.elements[i] = objectInputStream.readObject();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size());
        int n = this.elements.length;
        int n2 = this.head;
        while (n2 != this.tail) {
            objectOutputStream.writeObject(this.elements[n2]);
            n2 = n2 + 1 & n - 1;
        }
    }

    @Override
    public boolean add(E e) {
        this.addLast(e);
        return true;
    }

    @Override
    public void addFirst(E e) {
        if (e != null) {
            int n;
            Object[] arrobject = this.elements;
            this.head = n = this.head - 1 & arrobject.length - 1;
            arrobject[n] = e;
            if (this.head == this.tail) {
                this.doubleCapacity();
            }
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public void addLast(E e) {
        if (e != null) {
            Object[] arrobject = this.elements;
            int n = this.tail;
            arrobject[n] = e;
            this.tail = n = arrobject.length - 1 & n + 1;
            if (n == this.head) {
                this.doubleCapacity();
            }
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public void clear() {
        int n = this.head;
        int n2 = this.tail;
        if (n != n2) {
            int n3;
            this.tail = 0;
            this.head = 0;
            int n4 = this.elements.length;
            do {
                this.elements[n] = null;
                n = n3 = n + 1 & n4 - 1;
            } while (n3 != n2);
        }
    }

    public ArrayDeque<E> clone() {
        try {
            ArrayDeque arrayDeque = (ArrayDeque)Object.super.clone();
            arrayDeque.elements = Arrays.copyOf(this.elements, this.elements.length);
            return arrayDeque;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean contains(Object object) {
        if (object != null) {
            Object object2;
            int n = this.elements.length;
            int n2 = this.head;
            while ((object2 = this.elements[n2]) != null) {
                if (object.equals(object2)) {
                    return true;
                }
                n2 = n2 + 1 & n - 1;
            }
        }
        return false;
    }

    boolean delete(int n) {
        this.checkInvariants();
        Object[] arrobject = this.elements;
        int n2 = arrobject.length - 1;
        int n3 = this.head;
        int n4 = this.tail;
        int n5 = n - n3 & n2;
        int n6 = n4 - n & n2;
        if (n5 < (n4 - n3 & n2)) {
            if (n5 < n6) {
                if (n3 <= n) {
                    System.arraycopy(arrobject, n3, arrobject, n3 + 1, n5);
                } else {
                    System.arraycopy(arrobject, 0, arrobject, 1, n);
                    arrobject[0] = arrobject[n2];
                    System.arraycopy(arrobject, n3, arrobject, n3 + 1, n2 - n3);
                }
                arrobject[n3] = null;
                this.head = n3 + 1 & n2;
                return false;
            }
            if (n < n4) {
                System.arraycopy(arrobject, n + 1, arrobject, n, n6);
                this.tail = n4 - 1;
            } else {
                System.arraycopy(arrobject, n + 1, arrobject, n, n2 - n);
                arrobject[n2] = arrobject[0];
                System.arraycopy(arrobject, 1, arrobject, 0, n4);
                this.tail = n4 - 1 & n2;
            }
            return true;
        }
        throw new ConcurrentModificationException();
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }

    @Override
    public E element() {
        return this.getFirst();
    }

    @Override
    public E getFirst() {
        Object object = this.elements[this.head];
        if (object != null) {
            return (E)object;
        }
        throw new NoSuchElementException();
    }

    @Override
    public E getLast() {
        Object object = this.elements;
        if ((object = object[this.tail - 1 & ((Object[])object).length - 1]) != null) {
            return (E)object;
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.head == this.tail;
        return bl;
    }

    @Override
    public Iterator<E> iterator() {
        return new DeqIterator();
    }

    @Override
    public boolean offer(E e) {
        return this.offerLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        this.addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        this.addLast(e);
        return true;
    }

    @Override
    public E peek() {
        return this.peekFirst();
    }

    @Override
    public E peekFirst() {
        return (E)this.elements[this.head];
    }

    @Override
    public E peekLast() {
        Object[] arrobject = this.elements;
        return (E)arrobject[this.tail - 1 & arrobject.length - 1];
    }

    @Override
    public E poll() {
        return this.pollFirst();
    }

    @Override
    public E pollFirst() {
        Object[] arrobject = this.elements;
        int n = this.head;
        Object object = arrobject[n];
        if (object != null) {
            arrobject[n] = null;
            this.head = n + 1 & arrobject.length - 1;
        }
        return (E)object;
    }

    @Override
    public E pollLast() {
        Object[] arrobject = this.elements;
        int n = this.tail - 1 & arrobject.length - 1;
        Object object = arrobject[n];
        if (object != null) {
            arrobject[n] = null;
            this.tail = n;
        }
        return (E)object;
    }

    @Override
    public E pop() {
        return this.removeFirst();
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
        E e = this.pollFirst();
        if (e != null) {
            return e;
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean removeFirstOccurrence(Object object) {
        if (object != null) {
            Object object2;
            int n = this.elements.length;
            int n2 = this.head;
            while ((object2 = this.elements[n2]) != null) {
                if (object.equals(object2)) {
                    this.delete(n2);
                    return true;
                }
                n2 = n2 + 1 & n - 1;
            }
        }
        return false;
    }

    @Override
    public E removeLast() {
        E e = this.pollLast();
        if (e != null) {
            return e;
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean removeLastOccurrence(Object object) {
        if (object != null) {
            Object object2;
            int n = this.elements.length - 1;
            int n2 = this.tail - 1 & n;
            while ((object2 = this.elements[n2]) != null) {
                if (object.equals(object2)) {
                    this.delete(n2);
                    return true;
                }
                n2 = n2 - 1 & n;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.tail - this.head & this.elements.length - 1;
    }

    @Override
    public Spliterator<E> spliterator() {
        return new DeqSpliterator(this, -1, -1);
    }

    @Override
    public Object[] toArray() {
        int n = this.tail;
        int n2 = this.head;
        boolean bl = n < n2;
        int n3 = bl ? this.elements.length + n : n;
        Object[] arrobject = Arrays.copyOfRange(this.elements, n2, n3);
        if (bl) {
            Object[] arrobject2 = this.elements;
            System.arraycopy(arrobject2, 0, arrobject, arrobject2.length - n2, n);
        }
        return arrobject;
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        T[] arrT2;
        int n = this.tail;
        int n2 = this.head;
        boolean bl = n < n2;
        int n3 = bl ? this.elements.length : 0;
        int n4 = n - n2 + n3;
        n3 = bl ? n : 0;
        n3 = n4 - n3;
        int n5 = arrT.length;
        if (n4 > n5) {
            arrT2 = Arrays.copyOfRange(this.elements, n2, n2 + n4, arrT.getClass());
        } else {
            System.arraycopy(this.elements, n2, arrT, 0, n3);
            arrT2 = arrT;
            if (n4 < n5) {
                arrT[n4] = null;
                arrT2 = arrT;
            }
        }
        if (bl) {
            System.arraycopy(this.elements, 0, arrT2, n3, n);
        }
        return arrT2;
    }

    private class DeqIterator
    implements Iterator<E> {
        private int cursor;
        private int fence;
        private int lastRet;

        private DeqIterator() {
            this.cursor = ArrayDeque.this.head;
            this.fence = ArrayDeque.this.tail;
            this.lastRet = -1;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            Object[] arrobject = ArrayDeque.this.elements;
            int n = arrobject.length;
            int n2 = this.fence;
            int n3 = this.cursor;
            this.cursor = n2;
            while (n3 != n2) {
                Object object = arrobject[n3];
                n3 = n3 + 1 & n - 1;
                if (object != null) {
                    consumer.accept(object);
                    continue;
                }
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.cursor != this.fence;
            return bl;
        }

        @Override
        public E next() {
            if (this.cursor != this.fence) {
                Object object = ArrayDeque.this.elements[this.cursor];
                if (ArrayDeque.this.tail == this.fence && object != null) {
                    int n;
                    this.lastRet = n = this.cursor;
                    this.cursor = n + 1 & ArrayDeque.this.elements.length - 1;
                    return (E)object;
                }
                throw new ConcurrentModificationException();
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            int n = this.lastRet;
            if (n >= 0) {
                if (ArrayDeque.this.delete(n)) {
                    this.cursor = this.cursor - 1 & ArrayDeque.this.elements.length - 1;
                    this.fence = ArrayDeque.this.tail;
                }
                this.lastRet = -1;
                return;
            }
            throw new IllegalStateException();
        }
    }

    static final class DeqSpliterator<E>
    implements Spliterator<E> {
        private final ArrayDeque<E> deq;
        private int fence;
        private int index;

        DeqSpliterator(ArrayDeque<E> arrayDeque, int n, int n2) {
            this.deq = arrayDeque;
            this.index = n;
            this.fence = n2;
        }

        private int getFence() {
            int n;
            int n2 = n = this.fence;
            if (n < 0) {
                this.fence = n2 = this.deq.tail;
                this.index = this.deq.head;
            }
            return n2;
        }

        @Override
        public int characteristics() {
            return 16720;
        }

        @Override
        public long estimateSize() {
            int n;
            int n2 = n = this.getFence() - this.index;
            if (n < 0) {
                n2 = n + this.deq.elements.length;
            }
            return n2;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            if (consumer != null) {
                Object[] arrobject = this.deq.elements;
                int n = arrobject.length;
                int n2 = this.getFence();
                int n3 = this.index;
                this.index = n2;
                while (n3 != n2) {
                    Object object = arrobject[n3];
                    n3 = n3 + 1 & n - 1;
                    if (object != null) {
                        consumer.accept(object);
                        continue;
                    }
                    throw new ConcurrentModificationException();
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> consumer) {
            if (consumer != null) {
                Object object = this.deq.elements;
                int n = ((Object[])object).length;
                int n2 = this.index;
                int n3 = this.getFence();
                if (n2 != n3) {
                    object = object[n2];
                    this.index = n2 + 1 & n - 1;
                    if (object != null) {
                        consumer.accept(object);
                        return true;
                    }
                    throw new ConcurrentModificationException();
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public DeqSpliterator<E> trySplit() {
            int n = this.getFence();
            int n2 = this.index;
            int n3 = this.deq.elements.length;
            if (n2 != n && (n2 + 1 & n3 - 1) != n) {
                int n4 = n;
                if (n2 > n) {
                    n4 = n + n3;
                }
                n4 = n2 + n4 >>> 1 & n3 - 1;
                ArrayDeque<E> arrayDeque = this.deq;
                this.index = n4;
                return new DeqSpliterator<E>(arrayDeque, n2, n4);
            }
            return null;
        }
    }

    private class DescendingIterator
    implements Iterator<E> {
        private int cursor;
        private int fence;
        private int lastRet;

        private DescendingIterator() {
            this.cursor = ArrayDeque.this.tail;
            this.fence = ArrayDeque.this.head;
            this.lastRet = -1;
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.cursor != this.fence;
            return bl;
        }

        @Override
        public E next() {
            int n = this.cursor;
            if (n != this.fence) {
                this.cursor = n - 1 & ArrayDeque.this.elements.length - 1;
                Object object = ArrayDeque.this.elements[this.cursor];
                if (ArrayDeque.this.head == this.fence && object != null) {
                    this.lastRet = this.cursor;
                    return (E)object;
                }
                throw new ConcurrentModificationException();
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            int n = this.lastRet;
            if (n >= 0) {
                if (!ArrayDeque.this.delete(n)) {
                    this.cursor = this.cursor + 1 & ArrayDeque.this.elements.length - 1;
                    this.fence = ArrayDeque.this.head;
                }
                this.lastRet = -1;
                return;
            }
            throw new IllegalStateException();
        }
    }

}

