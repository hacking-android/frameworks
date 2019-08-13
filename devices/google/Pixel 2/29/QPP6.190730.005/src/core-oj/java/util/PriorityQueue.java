/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.function.Consumer;

public class PriorityQueue<E>
extends AbstractQueue<E>
implements Serializable {
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    private static final long serialVersionUID = -7720805057305804111L;
    private final Comparator<? super E> comparator;
    transient int modCount;
    transient Object[] queue;
    int size;

    public PriorityQueue() {
        this(11, null);
    }

    public PriorityQueue(int n) {
        this(n, null);
    }

    public PriorityQueue(int n, Comparator<? super E> comparator) {
        if (n >= 1) {
            this.queue = new Object[n];
            this.comparator = comparator;
            return;
        }
        throw new IllegalArgumentException();
    }

    public PriorityQueue(Collection<? extends E> collection) {
        if (collection instanceof SortedSet) {
            collection = (SortedSet)collection;
            this.comparator = collection.comparator();
            this.initElementsFromCollection(collection);
        } else if (collection instanceof PriorityQueue) {
            collection = (PriorityQueue)collection;
            this.comparator = ((PriorityQueue)collection).comparator();
            this.initFromPriorityQueue((PriorityQueue<? extends E>)collection);
        } else {
            this.comparator = null;
            this.initFromCollection(collection);
        }
    }

    public PriorityQueue(Comparator<? super E> comparator) {
        this(11, comparator);
    }

    public PriorityQueue(PriorityQueue<? extends E> priorityQueue) {
        this.comparator = priorityQueue.comparator();
        this.initFromPriorityQueue(priorityQueue);
    }

    public PriorityQueue(SortedSet<? extends E> sortedSet) {
        this.comparator = sortedSet.comparator();
        this.initElementsFromCollection(sortedSet);
    }

    private void grow(int n) {
        int n2 = this.queue.length;
        int n3 = n2 < 64 ? n2 + 2 : n2 >> 1;
        n3 = n2 = n3 + n2;
        if (n2 - 2147483639 > 0) {
            n3 = PriorityQueue.hugeCapacity(n);
        }
        this.queue = Arrays.copyOf(this.queue, n3);
    }

    private void heapify() {
        for (int i = (this.size >>> 1) - 1; i >= 0; --i) {
            this.siftDown(i, this.queue[i]);
        }
    }

    private static int hugeCapacity(int n) {
        if (n >= 0) {
            int n2 = 2147483639;
            n = n > 2147483639 ? Integer.MAX_VALUE : n2;
            return n;
        }
        throw new OutOfMemoryError();
    }

    private int indexOf(Object object) {
        if (object != null) {
            for (int i = 0; i < this.size; ++i) {
                if (!object.equals(this.queue[i])) continue;
                return i;
            }
        }
        return -1;
    }

    private void initElementsFromCollection(Collection<? extends E> arrobject) {
        Object[] arrobject2 = arrobject.toArray();
        arrobject = arrobject2;
        if (arrobject2.getClass() != Object[].class) {
            arrobject = Arrays.copyOf(arrobject2, arrobject2.length, Object[].class);
        }
        if (arrobject.length == 1 || this.comparator != null) {
            int n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                if (arrobject[i] != null) {
                    continue;
                }
                throw new NullPointerException();
            }
        }
        this.queue = arrobject;
        this.size = arrobject.length;
    }

    private void initFromCollection(Collection<? extends E> collection) {
        this.initElementsFromCollection(collection);
        this.heapify();
    }

    private void initFromPriorityQueue(PriorityQueue<? extends E> priorityQueue) {
        if (priorityQueue.getClass() == PriorityQueue.class) {
            this.queue = priorityQueue.toArray();
            this.size = priorityQueue.size();
        } else {
            this.initFromCollection(priorityQueue);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        objectInputStream.readInt();
        this.queue = new Object[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.queue[i] = objectInputStream.readObject();
        }
        this.heapify();
    }

    private void siftDown(int n, E e) {
        if (this.comparator != null) {
            this.siftDownUsingComparator(n, e);
        } else {
            this.siftDownComparable(n, e);
        }
    }

    private void siftDownComparable(int n, E object) {
        Comparable comparable = (Comparable)object;
        int n2 = this.size;
        int n3 = n;
        while (n3 < n2 >>> 1) {
            int n4 = (n3 << 1) + 1;
            Object[] arrobject = this.queue;
            Object object2 = arrobject[n4];
            int n5 = n4 + 1;
            n = n4;
            object = object2;
            if (n5 < this.size) {
                n = n4;
                object = object2;
                if (((Comparable)object2).compareTo(arrobject[n5]) > 0) {
                    object = this.queue;
                    n = n5;
                    object = object[n5];
                }
            }
            if (comparable.compareTo(object) <= 0) break;
            this.queue[n3] = object;
            n3 = n;
        }
        this.queue[n3] = comparable;
    }

    private void siftDownUsingComparator(int n, E e) {
        int n2 = this.size;
        int n3 = n;
        while (n3 < n2 >>> 1) {
            int n4 = (n3 << 1) + 1;
            Object[] arrobject = this.queue;
            Object[] arrobject2 = arrobject[n4];
            int n5 = n4 + 1;
            n = n4;
            Object object = arrobject2;
            if (n5 < this.size) {
                n = n4;
                object = arrobject2;
                if (this.comparator.compare(arrobject2, arrobject[n5]) > 0) {
                    object = this.queue;
                    n = n5;
                    object = object[n5];
                }
            }
            if (this.comparator.compare(e, object) <= 0) break;
            this.queue[n3] = object;
            n3 = n;
        }
        this.queue[n3] = e;
    }

    private void siftUp(int n, E e) {
        if (this.comparator != null) {
            this.siftUpUsingComparator(n, e);
        } else {
            this.siftUpComparable(n, e);
        }
    }

    private void siftUpComparable(int n, E object) {
        Object object2;
        int n2;
        object = (Comparable)object;
        while (n > 0 && object.compareTo((Object)(object2 = this.queue[n2 = n - 1 >>> 1])) < 0) {
            this.queue[n] = object2;
            n = n2;
        }
        this.queue[n] = object;
    }

    private void siftUpUsingComparator(int n, E e) {
        Object object;
        int n2;
        while (n > 0 && this.comparator.compare(e, object = this.queue[n2 = n - 1 >>> 1]) < 0) {
            this.queue[n] = object;
            n = n2;
        }
        this.queue[n] = e;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(Math.max(2, this.size + 1));
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.queue[i]);
        }
    }

    @Override
    public boolean add(E e) {
        return this.offer(e);
    }

    @Override
    public void clear() {
        ++this.modCount;
        for (int i = 0; i < this.size; ++i) {
            this.queue[i] = null;
        }
        this.size = 0;
    }

    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    @Override
    public boolean contains(Object object) {
        boolean bl = this.indexOf(object) >= 0;
        return bl;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public boolean offer(E e) {
        if (e != null) {
            ++this.modCount;
            int n = this.size;
            if (n >= this.queue.length) {
                this.grow(n + 1);
            }
            this.size = n + 1;
            if (n == 0) {
                this.queue[0] = e;
            } else {
                this.siftUp(n, e);
            }
            return true;
        }
        throw new NullPointerException();
    }

    @Override
    public E peek() {
        Object object = this.size == 0 ? null : this.queue[0];
        return (E)object;
    }

    @Override
    public E poll() {
        int n = this.size;
        if (n == 0) {
            return null;
        }
        this.size = --n;
        ++this.modCount;
        Object[] arrobject = this.queue;
        Object object = arrobject[0];
        Object object2 = arrobject[n];
        arrobject[n] = null;
        if (n != 0) {
            this.siftDown(0, object2);
        }
        return (E)object;
    }

    @Override
    public boolean remove(Object object) {
        int n = this.indexOf(object);
        if (n == -1) {
            return false;
        }
        this.removeAt(n);
        return true;
    }

    E removeAt(int n) {
        int n2;
        ++this.modCount;
        this.size = n2 = this.size - 1;
        if (n2 == n) {
            this.queue[n] = null;
        } else {
            Object[] arrobject = this.queue;
            Object object = arrobject[n2];
            arrobject[n2] = null;
            this.siftDown(n, object);
            if (this.queue[n] == object) {
                this.siftUp(n, object);
                if (this.queue[n] != object) {
                    return (E)object;
                }
            }
        }
        return null;
    }

    boolean removeEq(Object object) {
        for (int i = 0; i < this.size; ++i) {
            if (object != this.queue[i]) continue;
            this.removeAt(i);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public final Spliterator<E> spliterator() {
        return new PriorityQueueSpliterator(this, 0, -1, 0);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.queue, this.size);
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        int n = this.size;
        if (arrT.length < n) {
            return Arrays.copyOf(this.queue, n, arrT.getClass());
        }
        System.arraycopy(this.queue, 0, arrT, 0, n);
        if (arrT.length > n) {
            arrT[n] = null;
        }
        return arrT;
    }

    private final class Itr
    implements Iterator<E> {
        private int cursor;
        private int expectedModCount;
        private ArrayDeque<E> forgetMeNot;
        private int lastRet = -1;
        private E lastRetElt;

        private Itr() {
            this.expectedModCount = PriorityQueue.this.modCount;
        }

        @Override
        public boolean hasNext() {
            ArrayDeque<E> arrayDeque;
            boolean bl = this.cursor < PriorityQueue.this.size || (arrayDeque = this.forgetMeNot) != null && !arrayDeque.isEmpty();
            return bl;
        }

        @Override
        public E next() {
            if (this.expectedModCount == PriorityQueue.this.modCount) {
                if (this.cursor < PriorityQueue.this.size) {
                    Object[] arrobject = PriorityQueue.this.queue;
                    int n = this.cursor;
                    this.cursor = n + 1;
                    this.lastRet = n;
                    return (E)arrobject[n];
                }
                ArrayDeque<E> arrayDeque = this.forgetMeNot;
                if (arrayDeque != null) {
                    this.lastRet = -1;
                    this.lastRetElt = arrayDeque.poll();
                    arrayDeque = this.lastRetElt;
                    if (arrayDeque != null) {
                        return (E)arrayDeque;
                    }
                }
                throw new NoSuchElementException();
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public void remove() {
            block6 : {
                block9 : {
                    block8 : {
                        block7 : {
                            if (this.expectedModCount != PriorityQueue.this.modCount) break block6;
                            int n = this.lastRet;
                            if (n == -1) break block7;
                            Object e = PriorityQueue.this.removeAt(n);
                            this.lastRet = -1;
                            if (e == null) {
                                --this.cursor;
                            } else {
                                if (this.forgetMeNot == null) {
                                    this.forgetMeNot = new ArrayDeque();
                                }
                                this.forgetMeNot.add(e);
                            }
                            break block8;
                        }
                        E e = this.lastRetElt;
                        if (e == null) break block9;
                        PriorityQueue.this.removeEq(e);
                        this.lastRetElt = null;
                    }
                    this.expectedModCount = PriorityQueue.this.modCount;
                    return;
                }
                throw new IllegalStateException();
            }
            throw new ConcurrentModificationException();
        }
    }

    static final class PriorityQueueSpliterator<E>
    implements Spliterator<E> {
        private int expectedModCount;
        private int fence;
        private int index;
        private final PriorityQueue<E> pq;

        PriorityQueueSpliterator(PriorityQueue<E> priorityQueue, int n, int n2, int n3) {
            this.pq = priorityQueue;
            this.index = n;
            this.fence = n2;
            this.expectedModCount = n3;
        }

        private int getFence() {
            int n;
            int n2 = n = this.fence;
            if (n < 0) {
                this.expectedModCount = this.pq.modCount;
                this.fence = n2 = this.pq.size;
            }
            return n2;
        }

        @Override
        public int characteristics() {
            return 16704;
        }

        @Override
        public long estimateSize() {
            return this.getFence() - this.index;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            if (consumer != null) {
                block9 : {
                    Object[] arrobject;
                    PriorityQueue<E> priorityQueue = this.pq;
                    if (priorityQueue != null && (arrobject = priorityQueue.queue) != null) {
                        int n;
                        int n2;
                        int n3 = n = this.fence;
                        if (n < 0) {
                            n = priorityQueue.modCount;
                            n3 = priorityQueue.size;
                        } else {
                            n = this.expectedModCount;
                        }
                        if (n2 >= 0) {
                            this.index = n3;
                            if (n3 <= arrobject.length) {
                                for (int i = n2 = this.index; i < n3; ++i) {
                                    Object object = arrobject[i];
                                    if (object != null) {
                                        consumer.accept(object);
                                        continue;
                                    }
                                    break block9;
                                }
                                if (priorityQueue.modCount == n) {
                                    return;
                                }
                            }
                        }
                    }
                }
                throw new ConcurrentModificationException();
            }
            throw new NullPointerException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> consumer) {
            if (consumer != null) {
                int n = this.getFence();
                int n2 = this.index;
                if (n2 >= 0 && n2 < n) {
                    this.index = n2 + 1;
                    Object object = this.pq.queue[n2];
                    if (object != null) {
                        consumer.accept(object);
                        if (this.pq.modCount == this.expectedModCount) {
                            return true;
                        }
                        throw new ConcurrentModificationException();
                    }
                    throw new ConcurrentModificationException();
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public PriorityQueueSpliterator<E> trySplit() {
            Object object;
            int n = this.getFence();
            int n2 = this.index;
            if (n2 >= (n = n2 + n >>> 1)) {
                object = null;
            } else {
                object = this.pq;
                this.index = n;
                object = new PriorityQueueSpliterator<E>((PriorityQueue<E>)object, n2, n, this.expectedModCount);
            }
            return object;
        }
    }

}

