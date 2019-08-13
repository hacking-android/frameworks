/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Helpers;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayBlockingQueue<E>
extends AbstractQueue<E>
implements BlockingQueue<E>,
Serializable {
    private static final long serialVersionUID = -817911632652898426L;
    int count;
    final Object[] items;
    transient ArrayBlockingQueue<E> itrs;
    final ReentrantLock lock;
    private final Condition notEmpty;
    private final Condition notFull;
    int putIndex;
    int takeIndex;

    public ArrayBlockingQueue(int n) {
        this(n, false);
    }

    public ArrayBlockingQueue(int n, boolean bl) {
        if (n > 0) {
            this.items = new Object[n];
            this.lock = new ReentrantLock(bl);
            this.notEmpty = this.lock.newCondition();
            this.notFull = this.lock.newCondition();
            return;
        }
        throw new IllegalArgumentException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ArrayBlockingQueue(int n, boolean bl, Collection<? extends E> object) {
        this(n, bl);
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        int n2 = 0;
        try {
            block10 : {
                try {
                    object = object.iterator();
                }
                catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                    // empty catch block
                    break block10;
                }
                while (object.hasNext()) {
                    Object e = object.next();
                    Object[] arrobject = this.items;
                    try {
                        arrobject[n2] = Objects.requireNonNull(e);
                        ++n2;
                    }
                    catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        break block10;
                    }
                }
                this.count = n2;
                if (n2 == n) {
                    n2 = 0;
                }
                this.putIndex = n2;
                reentrantLock.unlock();
                return;
            }
            object = new IllegalArgumentException();
            throw object;
        }
        catch (Throwable throwable) {}
        reentrantLock.unlock();
        throw throwable;
    }

    private E dequeue() {
        Object object = this.items;
        int n = this.takeIndex;
        Object object2 = object[n];
        object[n] = null;
        this.takeIndex = ++n;
        if (n == ((Object[])object).length) {
            this.takeIndex = 0;
        }
        --this.count;
        object = this.itrs;
        if (object != null) {
            ((Itrs)object).elementDequeued();
        }
        this.notFull.signal();
        return (E)object2;
    }

    private void enqueue(E e) {
        Object[] arrobject = this.items;
        int n = this.putIndex;
        arrobject[n] = e;
        this.putIndex = ++n;
        if (n == arrobject.length) {
            this.putIndex = 0;
        }
        ++this.count;
        this.notEmpty.signal();
    }

    @Override
    public boolean add(E e) {
        return super.add(e);
    }

    @Override
    public void clear() {
        ReentrantLock reentrantLock;
        block10 : {
            int n;
            block11 : {
                reentrantLock = this.lock;
                reentrantLock.lock();
                int n2 = this.count;
                if (n2 <= 0) break block10;
                Object[] arrobject = this.items;
                int n3 = this.putIndex;
                int n4 = this.takeIndex;
                do {
                    arrobject[n4] = null;
                    n = ++n4;
                    if (n4 == arrobject.length) {
                        n = 0;
                    }
                    n4 = n;
                    if (n != n3) continue;
                    break;
                } while (true);
                this.takeIndex = n3;
                this.count = 0;
                n = n2;
                try {
                    if (this.itrs == null) break block11;
                    ((Itrs)((Object)this.itrs)).queueIsEmpty();
                    n = n2;
                }
                catch (Throwable throwable) {
                    reentrantLock.unlock();
                    throw throwable;
                }
            }
            while (n > 0) {
                if (!reentrantLock.hasWaiters(this.notFull)) break;
                this.notFull.signal();
                --n;
            }
        }
        reentrantLock.unlock();
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean contains(Object object) {
        if (object == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            if (this.count > 0) {
                int n;
                Object[] arrobject = this.items;
                int n2 = this.putIndex;
                int n3 = this.takeIndex;
                do {
                    boolean bl;
                    if (bl = object.equals(arrobject[n3])) {
                        reentrantLock.unlock();
                        return true;
                    }
                    int n4 = arrobject.length;
                    n = ++n3;
                    if (n3 == n4) {
                        n = 0;
                    }
                    n3 = n;
                } while (n != n2);
            }
            return false;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    final int dec(int n) {
        block0 : {
            if (n != 0) break block0;
            n = this.items.length;
        }
        return n - 1;
    }

    @Override
    public int drainTo(Collection<? super E> collection) {
        return this.drainTo(collection, Integer.MAX_VALUE);
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int drainTo(Collection<? super E> collection, int n) {
        int n2;
        Objects.requireNonNull(collection);
        if (collection == this) throw new IllegalArgumentException();
        if (n <= 0) {
            return 0;
        }
        Object[] arrobject = this.items;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        int n3 = Math.min(n, this.count);
        int n4 = this.takeIndex;
        for (n = 0; n < n3; ++n) {
            int n5;
            n2 = n4;
            collection.add(arrobject[n4]);
            arrobject[n4] = null;
            n2 = n5 = n4 + 1;
            try {
                int n6 = arrobject.length;
                n4 = n5;
                if (n5 != n6) continue;
                n4 = 0;
            }
            catch (Throwable throwable) {
                if (n > 0) {
                    block22 : {
                        this.count -= n;
                        this.takeIndex = n2;
                        n4 = n;
                        if (this.itrs == null) break block22;
                        if (this.count == 0) {
                            ((Itrs)((Object)this.itrs)).queueIsEmpty();
                            n4 = n;
                            break block22;
                        }
                        n4 = n;
                        if (n <= n2) break block22;
                        ((Itrs)((Object)this.itrs)).takeIndexWrapped();
                        n4 = n;
                    }
                    while (n4 > 0) {
                        if (!reentrantLock.hasWaiters(this.notFull)) break;
                        this.notFull.signal();
                        --n4;
                    }
                }
                throw throwable;
            }
            continue;
        }
        if (n > 0) {
            block23 : {
                this.count -= n;
                this.takeIndex = n4;
                n2 = n;
                if (this.itrs == null) break block23;
                if (this.count == 0) {
                    ((Itrs)((Object)this.itrs)).queueIsEmpty();
                    n2 = n;
                    break block23;
                }
                n2 = n;
                if (n <= n4) break block23;
                ((Itrs)((Object)this.itrs)).takeIndexWrapped();
                n2 = n;
            }
            while (n2 > 0) {
                if (!reentrantLock.hasWaiters(this.notFull)) break;
                this.notFull.signal();
                --n2;
            }
        }
        reentrantLock.unlock();
        return n3;
        {
            catch (Throwable throwable) {
                reentrantLock.unlock();
                throw throwable;
            }
        }
    }

    final E itemAt(int n) {
        return (E)this.items[n];
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public boolean offer(E e) {
        ReentrantLock reentrantLock;
        block4 : {
            Objects.requireNonNull(e);
            reentrantLock = this.lock;
            reentrantLock.lock();
            int n = this.count;
            int n2 = this.items.length;
            if (n != n2) break block4;
            reentrantLock.unlock();
            return false;
        }
        try {
            this.enqueue(e);
            return true;
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
    public boolean offer(E e, long l, TimeUnit object) throws InterruptedException {
        Objects.requireNonNull(e);
        l = ((TimeUnit)((Object)object)).toNanos(l);
        object = this.lock;
        ((ReentrantLock)object).lockInterruptibly();
        try {
            int n;
            int n2;
            while ((n = this.count) == (n2 = this.items.length)) {
                if (l <= 0L) {
                    ((ReentrantLock)object).unlock();
                    return false;
                }
                l = this.notFull.awaitNanos(l);
            }
            this.enqueue(e);
            ((ReentrantLock)object).unlock();
            return true;
        }
        catch (Throwable throwable) {
            ((ReentrantLock)object).unlock();
            throw throwable;
        }
    }

    @Override
    public E peek() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            E e = this.itemAt(this.takeIndex);
            return e;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public E poll() {
        E e;
        ReentrantLock reentrantLock;
        block4 : {
            reentrantLock = this.lock;
            reentrantLock.lock();
            if (this.count != 0) break block4;
            e = null;
        }
        e = this.dequeue();
        return e;
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public E poll(long var1_1, TimeUnit var3_2) throws InterruptedException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[UNCONDITIONALDOLOOP]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void put(E e) throws InterruptedException {
        Objects.requireNonNull(e);
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lockInterruptibly();
        try {
            while (this.count == this.items.length) {
                this.notFull.await();
            }
            this.enqueue(e);
            return;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public int remainingCapacity() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int n = this.items.length;
            int n2 = this.count;
            return n - n2;
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
    public boolean remove(Object object) {
        if (object == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            if (this.count > 0) {
                int n;
                Object[] arrobject = this.items;
                int n2 = this.putIndex;
                int n3 = this.takeIndex;
                do {
                    if (object.equals(arrobject[n3])) {
                        this.removeAt(n3);
                        reentrantLock.unlock();
                        return true;
                    }
                    int n4 = arrobject.length;
                    n = ++n3;
                    if (n3 == n4) {
                        n = 0;
                    }
                    n3 = n;
                } while (n != n2);
            }
            return false;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void removeAt(int n) {
        Object object = this.items;
        int n2 = this.takeIndex;
        if (n == n2) {
            object[n2] = null;
            this.takeIndex = n = n2 + 1;
            if (n == ((Object[])object).length) {
                this.takeIndex = 0;
            }
            --this.count;
            object = this.itrs;
            if (object != null) {
                ((Itrs)object).elementDequeued();
            }
        } else {
            n2 = n;
            int n3 = this.putIndex;
            do {
                int n4;
                int n5 = n2;
                n2 = n4 = n5 + 1;
                if (n4 == ((Object[])object).length) {
                    n2 = 0;
                }
                if (n2 == n3) {
                    object[n5] = null;
                    this.putIndex = n5;
                    --this.count;
                    object = this.itrs;
                    if (object == null) break;
                    ((Itrs)object).removedAt(n);
                    break;
                }
                object[n5] = object[n2];
            } while (true);
        }
        this.notFull.signal();
    }

    @Override
    public int size() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int n = this.count;
            return n;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this, 4368);
    }

    @Override
    public E take() throws InterruptedException {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lockInterruptibly();
        try {
            while (this.count == 0) {
                this.notEmpty.await();
            }
            E e = this.dequeue();
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
            Object[] arrobject = this.items;
            int n = this.takeIndex + this.count;
            Object[] arrobject2 = Arrays.copyOfRange(arrobject, this.takeIndex, n);
            if (n != this.putIndex) {
                System.arraycopy(arrobject, 0, arrobject2, arrobject.length - this.takeIndex, this.putIndex);
            }
            return arrobject2;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        int n;
        T[] arrT2;
        ReentrantLock reentrantLock;
        Object[] arrobject;
        int n2;
        block7 : {
            reentrantLock = this.lock;
            reentrantLock.lock();
            arrobject = this.items;
            n2 = this.count;
            n = Math.min(arrobject.length - this.takeIndex, n2);
            if (arrT.length < n2) {
                arrT2 = Arrays.copyOfRange(arrobject, this.takeIndex, this.takeIndex + n2, arrT.getClass());
                break block7;
            }
            System.arraycopy(arrobject, this.takeIndex, arrT, 0, n);
            arrT2 = arrT;
            if (arrT.length <= n2) break block7;
            arrT[n2] = null;
            arrT2 = arrT;
        }
        if (n < n2) {
            System.arraycopy(arrobject, 0, arrT2, n, this.putIndex);
        }
        return arrT2;
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public String toString() {
        return Helpers.collectionToString(this);
    }

    private class Itr
    implements Iterator<E> {
        private static final int DETACHED = -3;
        private static final int NONE = -1;
        private static final int REMOVED = -2;
        private int cursor;
        private E lastItem;
        private int lastRet = -1;
        private int nextIndex;
        private E nextItem;
        private int prevCycles;
        private int prevTakeIndex;

        Itr() {
            ReentrantLock reentrantLock = ArrayBlockingQueue.this.lock;
            reentrantLock.lock();
            try {
                if (ArrayBlockingQueue.this.count == 0) {
                    this.cursor = -1;
                    this.nextIndex = -1;
                    this.prevTakeIndex = -3;
                } else {
                    int n;
                    this.prevTakeIndex = n = ArrayBlockingQueue.this.takeIndex;
                    this.nextIndex = n;
                    this.nextItem = ArrayBlockingQueue.this.itemAt(n);
                    this.cursor = this.incCursor(n);
                    if (ArrayBlockingQueue.this.itrs == null) {
                        Itrs itrs = new Itrs(ArrayBlockingQueue.this, this);
                        ArrayBlockingQueue.this.itrs = itrs;
                    } else {
                        ArrayBlockingQueue.this.itrs.register(this);
                        ((Itrs)((Object)ArrayBlockingQueue.this.itrs)).doSomeSweeping(false);
                    }
                    this.prevCycles = ((Itrs)ArrayBlockingQueue.this.itrs).cycles;
                }
                return;
            }
            finally {
                reentrantLock.unlock();
            }
        }

        private void detach() {
            if (this.prevTakeIndex >= 0) {
                this.prevTakeIndex = -3;
                ((Itrs)((Object)ArrayBlockingQueue.this.itrs)).doSomeSweeping(true);
            }
        }

        private int distance(int n, int n2, int n3) {
            n = n2 = n - n2;
            if (n2 < 0) {
                n = n2 + n3;
            }
            return n;
        }

        private int incCursor(int n) {
            int n2;
            n = n2 = n + 1;
            if (n2 == ArrayBlockingQueue.this.items.length) {
                n = 0;
            }
            n2 = n;
            if (n == ArrayBlockingQueue.this.putIndex) {
                n2 = -1;
            }
            return n2;
        }

        private void incorporateDequeues() {
            int n = ((Itrs)ArrayBlockingQueue.this.itrs).cycles;
            int n2 = ArrayBlockingQueue.this.takeIndex;
            int n3 = this.prevCycles;
            int n4 = this.prevTakeIndex;
            if (n != n3 || n2 != n4) {
                int n5 = ArrayBlockingQueue.this.items.length;
                long l = (n - n3) * n5 + (n2 - n4);
                if (this.invalidated(this.lastRet, n4, l, n5)) {
                    this.lastRet = -2;
                }
                if (this.invalidated(this.nextIndex, n4, l, n5)) {
                    this.nextIndex = -2;
                }
                if (this.invalidated(this.cursor, n4, l, n5)) {
                    this.cursor = n2;
                }
                if (this.cursor < 0 && this.nextIndex < 0 && this.lastRet < 0) {
                    this.detach();
                } else {
                    this.prevCycles = n;
                    this.prevTakeIndex = n2;
                }
            }
        }

        private boolean invalidated(int n, int n2, long l, int n3) {
            boolean bl = false;
            if (n < 0) {
                return false;
            }
            n = n2 = n - n2;
            if (n2 < 0) {
                n = n2 + n3;
            }
            if (l > (long)n) {
                bl = true;
            }
            return bl;
        }

        private void noNext() {
            ReentrantLock reentrantLock = ArrayBlockingQueue.this.lock;
            reentrantLock.lock();
            try {
                if (!this.isDetached()) {
                    this.incorporateDequeues();
                    if (this.lastRet >= 0) {
                        this.lastItem = ArrayBlockingQueue.this.itemAt(this.lastRet);
                        this.detach();
                    }
                }
                return;
            }
            finally {
                reentrantLock.unlock();
            }
        }

        @Override
        public boolean hasNext() {
            if (this.nextItem != null) {
                return true;
            }
            this.noNext();
            return false;
        }

        boolean isDetached() {
            boolean bl = this.prevTakeIndex < 0;
            return bl;
        }

        @Override
        public E next() {
            E e = this.nextItem;
            if (e != null) {
                ReentrantLock reentrantLock = ArrayBlockingQueue.this.lock;
                reentrantLock.lock();
                try {
                    if (!this.isDetached()) {
                        this.incorporateDequeues();
                    }
                    this.lastRet = this.nextIndex;
                    int n = this.cursor;
                    if (n >= 0) {
                        ArrayBlockingQueue arrayBlockingQueue = ArrayBlockingQueue.this;
                        this.nextIndex = n;
                        this.nextItem = arrayBlockingQueue.itemAt(n);
                        this.cursor = this.incCursor(n);
                    } else {
                        this.nextIndex = -1;
                        this.nextItem = null;
                    }
                    return e;
                }
                finally {
                    reentrantLock.unlock();
                }
            }
            throw new NoSuchElementException();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void remove() {
            ReentrantLock reentrantLock = ArrayBlockingQueue.this.lock;
            reentrantLock.lock();
            try {
                block14 : {
                    block13 : {
                        int n;
                        block12 : {
                            if (!this.isDetached()) {
                                this.incorporateDequeues();
                            }
                            n = this.lastRet;
                            this.lastRet = -1;
                            if (n < 0) break block12;
                            if (!this.isDetached()) {
                                ArrayBlockingQueue.this.removeAt(n);
                            } else {
                                E e = this.lastItem;
                                this.lastItem = null;
                                if (ArrayBlockingQueue.this.itemAt(n) == e) {
                                    ArrayBlockingQueue.this.removeAt(n);
                                }
                            }
                            break block13;
                        }
                        if (n == -1) break block14;
                    }
                    if (this.cursor >= 0) return;
                    if (this.nextIndex >= 0) return;
                    this.detach();
                    return;
                }
                IllegalStateException illegalStateException = new IllegalStateException();
                throw illegalStateException;
            }
            finally {
                reentrantLock.unlock();
            }
        }

        boolean removedAt(int n) {
            if (this.isDetached()) {
                return true;
            }
            int n2 = ArrayBlockingQueue.this.takeIndex;
            int n3 = this.prevTakeIndex;
            int n4 = ArrayBlockingQueue.this.items.length;
            int n5 = ((Itrs)ArrayBlockingQueue.this.itrs).cycles;
            int n6 = this.prevCycles;
            n2 = n < n2 ? 1 : 0;
            int n7 = (n5 - n6 + n2) * n4 + (n - n3);
            n = n2 = this.cursor;
            if (n2 >= 0) {
                n5 = this.distance(n2, n3, n4);
                if (n5 == n7) {
                    n = n2;
                    if (n2 == ArrayBlockingQueue.this.putIndex) {
                        n = -1;
                        this.cursor = -1;
                    }
                } else {
                    n = n2;
                    if (n5 > n7) {
                        n = n2 = ArrayBlockingQueue.this.dec(n2);
                        this.cursor = n2;
                    }
                }
            }
            n2 = n5 = this.lastRet;
            if (n5 >= 0) {
                n6 = this.distance(n5, n3, n4);
                if (n6 == n7) {
                    n2 = -2;
                    this.lastRet = -2;
                } else {
                    n2 = n5;
                    if (n6 > n7) {
                        n2 = n5 = ArrayBlockingQueue.this.dec(n5);
                        this.lastRet = n5;
                    }
                }
            }
            n5 = n6 = this.nextIndex;
            if (n6 >= 0) {
                if ((n3 = this.distance(n6, n3, n4)) == n7) {
                    n5 = -2;
                    this.nextIndex = -2;
                } else {
                    n5 = n6;
                    if (n3 > n7) {
                        n5 = n6 = ArrayBlockingQueue.this.dec(n6);
                        this.nextIndex = n6;
                    }
                }
            }
            if (n < 0 && n5 < 0 && n2 < 0) {
                this.prevTakeIndex = -3;
                return true;
            }
            return false;
        }

        void shutdown() {
            this.cursor = -1;
            if (this.nextIndex >= 0) {
                this.nextIndex = -2;
            }
            if (this.lastRet >= 0) {
                this.lastRet = -2;
                this.lastItem = null;
            }
            this.prevTakeIndex = -3;
        }

        boolean takeIndexWrapped() {
            if (this.isDetached()) {
                return true;
            }
            if (((Itrs)ArrayBlockingQueue.this.itrs).cycles - this.prevCycles > 1) {
                this.shutdown();
                return true;
            }
            return false;
        }
    }

    class Itrs {
        private static final int LONG_SWEEP_PROBES = 16;
        private static final int SHORT_SWEEP_PROBES = 4;
        int cycles;
        private ArrayBlockingQueue<E> head;
        private ArrayBlockingQueue<E> sweeper;

        Itrs(ArrayBlockingQueue<E> arrayBlockingQueue) {
            this.register(arrayBlockingQueue);
        }

        void doSomeSweeping(boolean bl) {
            boolean bl2;
            ArrayBlockingQueue arrayBlockingQueue;
            Object var6_6;
            int n = bl ? 16 : 4;
            ArrayBlockingQueue arrayBlockingQueue2 = this.sweeper;
            if (arrayBlockingQueue2 == null) {
                arrayBlockingQueue2 = null;
                arrayBlockingQueue = this.head;
                bl2 = true;
            } else {
                arrayBlockingQueue = ((Node)arrayBlockingQueue2).next;
                bl2 = false;
            }
            do {
                var6_6 = null;
                if (n <= 0) break;
                ArrayBlockingQueue<E> arrayBlockingQueue3 = arrayBlockingQueue2;
                ArrayBlockingQueue arrayBlockingQueue4 = arrayBlockingQueue;
                boolean bl3 = bl2;
                if (arrayBlockingQueue == null) {
                    if (bl2) break;
                    arrayBlockingQueue3 = null;
                    arrayBlockingQueue4 = this.head;
                    bl3 = true;
                }
                arrayBlockingQueue2 = (Itr)((Reference)((Object)arrayBlockingQueue4)).get();
                arrayBlockingQueue = ((Node)arrayBlockingQueue4).next;
                if (arrayBlockingQueue2 != null && !((Itr)((Object)arrayBlockingQueue2)).isDetached()) {
                    arrayBlockingQueue2 = arrayBlockingQueue4;
                } else {
                    n = 16;
                    ((Reference)((Object)arrayBlockingQueue4)).clear();
                    ((Node)arrayBlockingQueue4).next = null;
                    if (arrayBlockingQueue3 == null) {
                        this.head = arrayBlockingQueue;
                        arrayBlockingQueue2 = arrayBlockingQueue3;
                        if (arrayBlockingQueue == null) {
                            this$0.itrs = null;
                            return;
                        }
                    } else {
                        ((Node)arrayBlockingQueue3).next = arrayBlockingQueue;
                        arrayBlockingQueue2 = arrayBlockingQueue3;
                    }
                }
                --n;
                bl2 = bl3;
            } while (true);
            if (arrayBlockingQueue == null) {
                arrayBlockingQueue2 = var6_6;
            }
            this.sweeper = arrayBlockingQueue2;
        }

        void elementDequeued() {
            if (this$0.count == 0) {
                this.queueIsEmpty();
            } else if (this$0.takeIndex == 0) {
                this.takeIndexWrapped();
            }
        }

        void queueIsEmpty() {
            ArrayBlockingQueue<E> arrayBlockingQueue = this.head;
            while (arrayBlockingQueue != null) {
                Itr itr = (Itr)((Reference)((Object)arrayBlockingQueue)).get();
                if (itr != null) {
                    ((Reference)((Object)arrayBlockingQueue)).clear();
                    itr.shutdown();
                }
                arrayBlockingQueue = ((Node)arrayBlockingQueue).next;
            }
            this.head = null;
            this$0.itrs = null;
        }

        void register(ArrayBlockingQueue<E> arrayBlockingQueue) {
            this.head = new Node(this, arrayBlockingQueue, this.head);
        }

        void removedAt(int n) {
            ArrayBlockingQueue<E> arrayBlockingQueue = null;
            ArrayBlockingQueue<E> arrayBlockingQueue2 = this.head;
            while (arrayBlockingQueue2 != null) {
                Itr itr = (Itr)((Reference)((Object)arrayBlockingQueue2)).get();
                ArrayBlockingQueue arrayBlockingQueue3 = ((Node)arrayBlockingQueue2).next;
                if (itr != null && !itr.removedAt(n)) {
                    arrayBlockingQueue = arrayBlockingQueue2;
                } else {
                    ((Reference)((Object)arrayBlockingQueue2)).clear();
                    ((Node)arrayBlockingQueue2).next = null;
                    if (arrayBlockingQueue == null) {
                        this.head = arrayBlockingQueue3;
                    } else {
                        ((Node)arrayBlockingQueue).next = arrayBlockingQueue3;
                    }
                }
                arrayBlockingQueue2 = arrayBlockingQueue3;
            }
            if (this.head == null) {
                this$0.itrs = null;
            }
        }

        void takeIndexWrapped() {
            ++this.cycles;
            ArrayBlockingQueue<E> arrayBlockingQueue = null;
            ArrayBlockingQueue<E> arrayBlockingQueue2 = this.head;
            while (arrayBlockingQueue2 != null) {
                Itr itr = (Itr)((Reference)((Object)arrayBlockingQueue2)).get();
                ArrayBlockingQueue arrayBlockingQueue3 = ((Node)arrayBlockingQueue2).next;
                if (itr != null && !itr.takeIndexWrapped()) {
                    arrayBlockingQueue = arrayBlockingQueue2;
                } else {
                    ((Reference)((Object)arrayBlockingQueue2)).clear();
                    ((Node)arrayBlockingQueue2).next = null;
                    if (arrayBlockingQueue == null) {
                        this.head = arrayBlockingQueue3;
                    } else {
                        ((Node)arrayBlockingQueue).next = arrayBlockingQueue3;
                    }
                }
                arrayBlockingQueue2 = arrayBlockingQueue3;
            }
            if (this.head == null) {
                this$0.itrs = null;
            }
        }

        private class Node
        extends WeakReference<ArrayBlockingQueue<E>> {
            ArrayBlockingQueue<E> next;

            Node(ArrayBlockingQueue<E> arrayBlockingQueue, ArrayBlockingQueue<E> arrayBlockingQueue2) {
                super(arrayBlockingQueue);
                this.next = arrayBlockingQueue2;
            }
        }

    }

}

