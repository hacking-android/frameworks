/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DelayQueue<E extends Delayed>
extends AbstractQueue<E>
implements BlockingQueue<E> {
    private final Condition available = this.lock.newCondition();
    private Thread leader;
    private final transient ReentrantLock lock = new ReentrantLock();
    private final PriorityQueue<E> q = new PriorityQueue();

    public DelayQueue() {
    }

    public DelayQueue(Collection<? extends E> collection) {
        this.addAll(collection);
    }

    private E peekExpired() {
        Delayed delayed = (Delayed)this.q.peek();
        if (delayed == null || delayed.getDelay(TimeUnit.NANOSECONDS) > 0L) {
            delayed = null;
        }
        return (E)delayed;
    }

    @Override
    public boolean add(E e) {
        return this.offer(e);
    }

    @Override
    public void clear() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            this.q.clear();
            return;
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
    public int drainTo(Collection<? super E> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
        if (collection == this) {
            throw new IllegalArgumentException();
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        int n = 0;
        try {
            E e;
            while ((e = this.peekExpired()) != null) {
                collection.add(e);
                this.q.poll();
                ++n;
            }
            reentrantLock.unlock();
            return n;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
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
                for (n2 = 0; n2 < n; ++n2) {
                    E e;
                    try {
                        e = this.peekExpired();
                        if (e == null) break;
                    }
                    catch (Throwable throwable) {
                        reentrantLock.unlock();
                        throw throwable;
                    }
                    collection.add(e);
                    this.q.poll();
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
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            this.q.offer(e);
            if (this.q.peek() == e) {
                this.leader = null;
                this.available.signal();
            }
            return true;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public boolean offer(E e, long l, TimeUnit timeUnit) {
        return this.offer(e);
    }

    @Override
    public E peek() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Delayed delayed = (Delayed)this.q.peek();
            return (E)delayed;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public E poll() {
        ReentrantLock reentrantLock;
        Delayed delayed;
        block4 : {
            block3 : {
                reentrantLock = this.lock;
                reentrantLock.lock();
                try {
                    delayed = (Delayed)this.q.peek();
                    if (delayed == null) break block3;
                }
                catch (Throwable throwable) {
                    reentrantLock.unlock();
                    throw throwable;
                }
                if (delayed.getDelay(TimeUnit.NANOSECONDS) > 0L) break block3;
                delayed = (Delayed)this.q.poll();
                break block4;
            }
            delayed = null;
        }
        reentrantLock.unlock();
        return (E)delayed;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public E poll(long var1_1, TimeUnit var3_2) throws InterruptedException {
        var1_1 = var3_2.toNanos(var1_1);
        var3_2 = this.lock;
        var3_2.lockInterruptibly();
        do {
            var4_3 = (Delayed)this.q.peek();
            if (var4_3 != null) ** GOTO lbl22
            if (var1_1 > 0L) break block13;
            if (this.leader == null && this.q.peek() != null) {
                this.available.signal();
            }
            var3_2.unlock();
            break;
        } while (true);
        catch (Throwable var4_4) {
            if (this.leader == null && this.q.peek() != null) {
                this.available.signal();
            }
            var3_2.unlock();
            throw var4_4;
        }
        {
            block14 : {
                block13 : {
                    return null;
                }
                var1_1 = this.available.awaitNanos(var1_1);
                continue;
lbl22: // 1 sources:
                var5_5 = var4_3.getDelay(TimeUnit.NANOSECONDS);
                if (var5_5 > 0L) break block14;
                var4_3 = (Delayed)this.q.poll();
                if (this.leader == null && this.q.peek() != null) {
                    this.available.signal();
                }
                var3_2.unlock();
                return (E)var4_3;
            }
            if (var1_1 <= 0L) {
                if (this.leader == null && this.q.peek() != null) {
                    this.available.signal();
                }
                var3_2.unlock();
                return null;
            }
            if (var1_1 < var5_5) ** GOTO lbl51
            if (this.leader != null) ** GOTO lbl51
            this.leader = var4_3 = Thread.currentThread();
            var7_6 = this.available.awaitNanos(var5_5);
            {
                catch (Throwable var9_7) {
                    if (this.leader != var4_3) throw var9_7;
                    this.leader = null;
                    throw var9_7;
                }
            }
            var1_1 = var7_6 = var1_1 - (var5_5 - var7_6);
            if (this.leader != var4_3) continue;
            this.leader = null;
            var1_1 = var7_6;
            continue;
lbl51: // 2 sources:
            var1_1 = this.available.awaitNanos(var1_1);
            continue;
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
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            boolean bl = this.q.remove(object);
            return bl;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    void removeEQ(Object object) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Iterator<E> iterator = this.q.iterator();
            while (iterator.hasNext()) {
                if (object != iterator.next()) continue;
                iterator.remove();
                break;
            }
            return;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public int size() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int n = this.q.size();
            return n;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public E take() throws InterruptedException {
        Object object;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lockInterruptibly();
        do {
            long l;
            block11 : {
                while ((object = (Delayed)this.q.peek()) == null) {
                    this.available.await();
                }
                l = object.getDelay(TimeUnit.NANOSECONDS);
                if (l > 0L) break block11;
                object = (Delayed)this.q.poll();
                if (this.leader == null && this.q.peek() != null) {
                    this.available.signal();
                }
                reentrantLock.unlock();
                return (E)object;
            }
            if (this.leader != null) {
                this.available.await();
                continue;
            }
            this.leader = object = Thread.currentThread();
            this.available.awaitNanos(l);
            break;
        } while (true);
        {
            catch (Throwable throwable) {
                if (this.leader != object) throw throwable;
                this.leader = null;
                throw throwable;
            }
        }
        {
            if (this.leader != object) continue;
            this.leader = null;
            continue;
        }
        catch (Throwable throwable) {
            if (this.leader == null && this.q.peek() != null) {
                this.available.signal();
            }
            reentrantLock.unlock();
            throw throwable;
        }
    }

    @Override
    public Object[] toArray() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Object[] arrobject = this.q.toArray();
            return arrobject;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            arrT = this.q.toArray(arrT);
            return arrT;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    private class Itr
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
                return (E)((Delayed)arrobject[n]);
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            int n = this.lastRet;
            if (n >= 0) {
                DelayQueue.this.removeEQ(this.array[n]);
                this.lastRet = -1;
                return;
            }
            throw new IllegalStateException();
        }
    }

}

