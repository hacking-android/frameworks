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
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Helpers;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import sun.misc.Unsafe;

public class PriorityBlockingQueue<E>
extends AbstractQueue<E>
implements BlockingQueue<E>,
Serializable {
    private static final long ALLOCATIONSPINLOCK;
    private static final int DEFAULT_INITIAL_CAPACITY = 11;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    private static final Unsafe U;
    private static final long serialVersionUID = 5595510919245408276L;
    private volatile transient int allocationSpinLock;
    private transient Comparator<? super E> comparator;
    private final ReentrantLock lock;
    private final Condition notEmpty;
    private PriorityQueue<E> q;
    private transient Object[] queue;
    private transient int size;

    static {
        U = Unsafe.getUnsafe();
        try {
            ALLOCATIONSPINLOCK = U.objectFieldOffset(PriorityBlockingQueue.class.getDeclaredField("allocationSpinLock"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public PriorityBlockingQueue() {
        this(11, null);
    }

    public PriorityBlockingQueue(int n) {
        this(n, null);
    }

    public PriorityBlockingQueue(int n, Comparator<? super E> comparator) {
        if (n >= 1) {
            this.lock = new ReentrantLock();
            this.notEmpty = this.lock.newCondition();
            this.comparator = comparator;
            this.queue = new Object[n];
            return;
        }
        throw new IllegalArgumentException();
    }

    public PriorityBlockingQueue(Collection<? extends E> arrobject) {
        int n;
        Object[] arrobject2;
        this.lock = new ReentrantLock();
        this.notEmpty = this.lock.newCondition();
        int n2 = 1;
        int n3 = 1;
        if (arrobject instanceof SortedSet) {
            this.comparator = ((SortedSet)arrobject).comparator();
            n = 0;
        } else {
            n = n2;
            if (arrobject instanceof PriorityBlockingQueue) {
                arrobject2 = arrobject;
                this.comparator = arrobject2.comparator();
                int n4 = 0;
                n = n2;
                n3 = n4;
                if (arrobject2.getClass() == PriorityBlockingQueue.class) {
                    n = 0;
                    n3 = n4;
                }
            }
        }
        arrobject2 = arrobject.toArray();
        n2 = arrobject2.length;
        arrobject = arrobject2;
        if (arrobject2.getClass() != Object[].class) {
            arrobject = Arrays.copyOf(arrobject2, n2, Object[].class);
        }
        if (n3 != 0 && (n2 == 1 || this.comparator != null)) {
            for (n3 = 0; n3 < n2; ++n3) {
                if (arrobject[n3] != null) {
                    continue;
                }
                throw new NullPointerException();
            }
        }
        this.queue = arrobject;
        this.size = n2;
        if (n != 0) {
            this.heapify();
        }
    }

    private E dequeue() {
        int n = this.size - 1;
        if (n < 0) {
            return null;
        }
        Object[] arrobject = this.queue;
        Object object = arrobject[0];
        Object object2 = arrobject[n];
        arrobject[n] = null;
        Comparator<? super E> comparator = this.comparator;
        if (comparator == null) {
            PriorityBlockingQueue.siftDownComparable(0, object2, arrobject, n);
        } else {
            PriorityBlockingQueue.siftDownUsingComparator(0, object2, arrobject, n, comparator);
        }
        this.size = n;
        return (E)object;
    }

    private void heapify() {
        int n;
        Object[] arrobject = this.queue;
        int n2 = this.size;
        Comparator<? super E> comparator = this.comparator;
        if (comparator == null) {
            for (n = (n2 >>> 1) - 1; n >= 0; --n) {
                PriorityBlockingQueue.siftDownComparable(n, arrobject[n], arrobject, n2);
            }
        } else {
            while (n >= 0) {
                PriorityBlockingQueue.siftDownUsingComparator(n, arrobject[n], arrobject, n2, comparator);
                --n;
            }
        }
    }

    private int indexOf(Object object) {
        if (object != null) {
            Object[] arrobject = this.queue;
            int n = this.size;
            for (int i = 0; i < n; ++i) {
                if (!object.equals(arrobject[i])) continue;
                return i;
            }
        }
        return -1;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            objectInputStream.defaultReadObject();
            this.queue = new Object[this.q.size()];
            this.comparator = this.q.comparator();
            this.addAll(this.q);
            return;
        }
        finally {
            this.q = null;
        }
    }

    private void removeAt(int n) {
        Object[] arrobject = this.queue;
        int n2 = this.size - 1;
        if (n2 == n) {
            arrobject[n] = null;
        } else {
            Object object = arrobject[n2];
            arrobject[n2] = null;
            Comparator<? super E> comparator = this.comparator;
            if (comparator == null) {
                PriorityBlockingQueue.siftDownComparable(n, object, arrobject, n2);
            } else {
                PriorityBlockingQueue.siftDownUsingComparator(n, object, arrobject, n2, comparator);
            }
            if (arrobject[n] == object) {
                if (comparator == null) {
                    PriorityBlockingQueue.siftUpComparable(n, object, arrobject);
                } else {
                    PriorityBlockingQueue.siftUpUsingComparator(n, object, arrobject, comparator);
                }
            }
        }
        this.size = n2;
    }

    private static <T> void siftDownComparable(int n, T object, Object[] arrobject, int n2) {
        if (n2 > 0) {
            Comparable comparable = (Comparable)object;
            int n3 = n;
            while (n3 < n2 >>> 1) {
                int n4 = (n3 << 1) + 1;
                Object object2 = arrobject[n4];
                int n5 = n4 + 1;
                n = n4;
                object = object2;
                if (n5 < n2) {
                    n = n4;
                    object = object2;
                    if (((Comparable)object2).compareTo(arrobject[n5]) > 0) {
                        n = n5;
                        object = arrobject[n5];
                    }
                }
                if (comparable.compareTo(object) <= 0) break;
                arrobject[n3] = object;
                n3 = n;
            }
            arrobject[n3] = comparable;
        }
    }

    private static <T> void siftDownUsingComparator(int n, T t, Object[] arrobject, int n2, Comparator<? super T> comparator) {
        if (n2 > 0) {
            int n3 = n;
            while (n3 < n2 >>> 1) {
                int n4 = (n3 << 1) + 1;
                Object object = arrobject[n4];
                int n5 = n4 + 1;
                n = n4;
                Object object2 = object;
                if (n5 < n2) {
                    n = n4;
                    object2 = object;
                    if (comparator.compare(object, arrobject[n5]) > 0) {
                        n = n5;
                        object2 = arrobject[n5];
                    }
                }
                if (comparator.compare(t, object2) <= 0) break;
                arrobject[n3] = object2;
                n3 = n;
            }
            arrobject[n3] = t;
        }
    }

    private static <T> void siftUpComparable(int n, T object, Object[] arrobject) {
        Object object2;
        int n2;
        object = (Comparable)object;
        while (n > 0 && object.compareTo((Object)(object2 = arrobject[n2 = n - 1 >>> 1])) < 0) {
            arrobject[n] = object2;
            n = n2;
        }
        arrobject[n] = object;
    }

    private static <T> void siftUpUsingComparator(int n, T t, Object[] arrobject, Comparator<? super T> comparator) {
        int n2;
        Object object;
        while (n > 0 && comparator.compare(t, object = arrobject[n2 = n - 1 >>> 1]) < 0) {
            arrobject[n] = object;
            n = n2;
        }
        arrobject[n] = t;
    }

    private void tryGrow(Object[] object, int n) {
        Object[] arrobject;
        block10 : {
            int n2;
            this.lock.unlock();
            Object[] arrobject2 = null;
            Object[] arrobject3 = null;
            arrobject = arrobject2;
            if (this.allocationSpinLock != 0) break block10;
            arrobject = arrobject2;
            if (!U.compareAndSwapInt(this, ALLOCATIONSPINLOCK, 0, 1)) break block10;
            int n3 = n < 64 ? n + 2 : n >> 1;
            n3 = n2 = n3 + n;
            if (n2 - 2147483639 > 0) {
                n3 = n + 1;
                if (n3 >= 0 && n3 <= 2147483639) {
                    n3 = 2147483639;
                } else {
                    object = new OutOfMemoryError();
                    throw object;
                }
            }
            arrobject = arrobject3;
            if (n3 <= n) break block10;
            arrobject = arrobject3;
            try {
                if (this.queue == object) {
                    arrobject = new Object[n3];
                }
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
                this.allocationSpinLock = 0;
            }
        }
        if (arrobject == null) {
            Thread.yield();
        }
        this.lock.lock();
        if (arrobject != null && this.queue == object) {
            this.queue = arrobject;
            System.arraycopy(object, 0, arrobject, 0, n);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.lock.lock();
        try {
            PriorityQueue<? super E> priorityQueue = new PriorityQueue<E>(Math.max(this.size, 1), this.comparator);
            this.q = priorityQueue;
            this.q.addAll(this);
            objectOutputStream.defaultWriteObject();
            return;
        }
        finally {
            this.q = null;
            this.lock.unlock();
        }
    }

    @Override
    public boolean add(E e) {
        return this.offer(e);
    }

    @Override
    public void clear() {
        int n;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Object[] arrobject = this.queue;
            n = this.size;
            this.size = 0;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
        for (int i = 0; i < n; ++i) {
            arrobject[i] = null;
        }
        reentrantLock.unlock();
        return;
    }

    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    @Override
    public boolean contains(Object object) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int n = this.indexOf(object);
            boolean bl = n != -1;
            reentrantLock.unlock();
            return bl;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
    }

    @Override
    public int drainTo(Collection<? super E> collection) {
        return this.drainTo(collection, Integer.MAX_VALUE);
    }

    @Override
    public int drainTo(Collection<? super E> collection, int n) {
        if (collection != null) {
            if (collection != this) {
                int n2;
                if (n <= 0) {
                    return 0;
                }
                ReentrantLock reentrantLock = this.lock;
                reentrantLock.lock();
                try {
                    n2 = Math.min(this.size, n);
                }
                catch (Throwable throwable) {
                    reentrantLock.unlock();
                    throw throwable;
                }
                for (n = 0; n < n2; ++n) {
                    collection.add(this.queue[0]);
                    this.dequeue();
                }
                reentrantLock.unlock();
                return n2;
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr(this.toArray());
    }

    @Override
    public boolean offer(E e) {
        if (e != null) {
            int n;
            Object[] arrobject;
            int n2;
            ReentrantLock reentrantLock = this.lock;
            reentrantLock.lock();
            while ((n2 = this.size) >= (n = (arrobject = this.queue).length)) {
                this.tryGrow(arrobject, n);
            }
            try {
                Comparator<? super E> comparator = this.comparator;
                if (comparator == null) {
                    PriorityBlockingQueue.siftUpComparable(n2, e, arrobject);
                } else {
                    PriorityBlockingQueue.siftUpUsingComparator(n2, e, arrobject, comparator);
                }
                this.size = n2 + 1;
                this.notEmpty.signal();
                return true;
            }
            finally {
                reentrantLock.unlock();
            }
        }
        throw new NullPointerException();
    }

    @Override
    public boolean offer(E e, long l, TimeUnit timeUnit) {
        return this.offer(e);
    }

    @Override
    public E peek() {
        ReentrantLock reentrantLock;
        Object object;
        block4 : {
            reentrantLock = this.lock;
            reentrantLock.lock();
            if (this.size != 0) break block4;
            object = null;
        }
        object = this.queue[0];
        return (E)object;
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public E poll() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            E e = this.dequeue();
            return e;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public E poll(long l, TimeUnit object) throws InterruptedException {
        l = ((TimeUnit)((Object)object)).toNanos(l);
        object = this.lock;
        ((ReentrantLock)object).lockInterruptibly();
        try {
            E e;
            while ((e = this.dequeue()) == null && l > 0L) {
                l = this.notEmpty.awaitNanos(l);
            }
            return e;
        }
        finally {
            ((ReentrantLock)object).unlock();
        }
    }

    @Override
    public void put(E e) {
        this.offer(e);
    }

    @Override
    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean remove(Object object) {
        ReentrantLock reentrantLock;
        int n;
        block4 : {
            reentrantLock = this.lock;
            reentrantLock.lock();
            n = this.indexOf(object);
            if (n != -1) break block4;
            reentrantLock.unlock();
            return false;
        }
        try {
            this.removeAt(n);
            return true;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    void removeEQ(Object object) {
        int n;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        Object[] arrobject = this.queue;
        try {
            n = this.size;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            reentrantLock.unlock();
        }
        for (int i = 0; i < n; ++i) {
            if (object != arrobject[i]) continue;
            this.removeAt(i);
            break;
        }
        return;
    }

    @Override
    public int size() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int n = this.size;
            return n;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public Spliterator<E> spliterator() {
        return new PBQSpliterator(this, null, 0, -1);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public E take() throws InterruptedException {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lockInterruptibly();
        try {
            E e;
            while ((e = this.dequeue()) == null) {
                this.notEmpty.await();
            }
            return e;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public Object[] toArray() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Object[] arrobject = Arrays.copyOf(this.queue, this.size);
            return arrobject;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        ReentrantLock reentrantLock;
        int n;
        block4 : {
            reentrantLock = this.lock;
            reentrantLock.lock();
            try {
                n = this.size;
                if (arrT.length >= n) break block4;
                arrT = Arrays.copyOf(this.queue, this.size, arrT.getClass());
                reentrantLock.unlock();
                return arrT;
            }
            catch (Throwable throwable) {
                reentrantLock.unlock();
                throw throwable;
            }
        }
        System.arraycopy(this.queue, 0, arrT, 0, n);
        if (arrT.length > n) {
            arrT[n] = null;
        }
        reentrantLock.unlock();
        return arrT;
    }

    @Override
    public String toString() {
        return Helpers.collectionToString(this);
    }

    final class Itr
    implements Iterator<E> {
        final Object[] array;
        int cursor;
        int lastRet = -1;

        Itr(Object[] arrobject) {
            this.array = arrobject;
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.cursor < this.array.length;
            return bl;
        }

        @Override
        public E next() {
            int n = this.cursor;
            Object[] arrobject = this.array;
            if (n < arrobject.length) {
                this.lastRet = n;
                this.cursor = n + 1;
                return (E)arrobject[n];
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            int n = this.lastRet;
            if (n >= 0) {
                PriorityBlockingQueue.this.removeEQ(this.array[n]);
                this.lastRet = -1;
                return;
            }
            throw new IllegalStateException();
        }
    }

    static final class PBQSpliterator<E>
    implements Spliterator<E> {
        Object[] array;
        int fence;
        int index;
        final PriorityBlockingQueue<E> queue;

        PBQSpliterator(PriorityBlockingQueue<E> priorityBlockingQueue, Object[] arrobject, int n, int n2) {
            this.queue = priorityBlockingQueue;
            this.array = arrobject;
            this.index = n;
            this.fence = n2;
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
                int n;
                Object[] arrobject;
                Object[] arrobject2 = arrobject = this.array;
                if (arrobject == null) {
                    arrobject2 = arrobject = this.queue.toArray();
                    this.fence = arrobject.length;
                }
                if ((n = this.fence) <= arrobject2.length) {
                    int n2;
                    int n3 = n2 = this.index;
                    if (n2 >= 0) {
                        this.index = n;
                        if (n3 < n) {
                            do {
                                consumer.accept(arrobject2[n3]);
                                n3 = n2 = n3 + 1;
                            } while (n2 < n);
                        }
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        final int getFence() {
            int n;
            int n2 = n = this.fence;
            if (n < 0) {
                Object[] arrobject = this.queue.toArray();
                this.array = arrobject;
                this.fence = n2 = arrobject.length;
            }
            return n2;
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> consumer) {
            if (consumer != null) {
                int n;
                int n2 = this.getFence();
                if (n2 > (n = this.index) && n >= 0) {
                    Object[] arrobject = this.array;
                    this.index = n + 1;
                    consumer.accept(arrobject[n]);
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public PBQSpliterator<E> trySplit() {
            Object object;
            int n = this.getFence();
            int n2 = this.index;
            if (n2 >= (n = n2 + n >>> 1)) {
                object = null;
            } else {
                object = this.queue;
                Object[] arrobject = this.array;
                this.index = n;
                object = new PBQSpliterator<E>((PriorityBlockingQueue<E>)object, arrobject, n2, n);
            }
            return object;
        }
    }

}

