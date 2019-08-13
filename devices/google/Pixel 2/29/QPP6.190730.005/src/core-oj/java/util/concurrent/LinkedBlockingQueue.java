/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Helpers;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class LinkedBlockingQueue<E>
extends AbstractQueue<E>
implements BlockingQueue<E>,
Serializable {
    private static final long serialVersionUID = -6903933977591709194L;
    private final int capacity;
    private final AtomicInteger count = new AtomicInteger();
    transient Node<E> head;
    private transient Node<E> last;
    private final Condition notEmpty = this.takeLock.newCondition();
    private final Condition notFull = this.putLock.newCondition();
    private final ReentrantLock putLock = new ReentrantLock();
    private final ReentrantLock takeLock = new ReentrantLock();

    public LinkedBlockingQueue() {
        this(Integer.MAX_VALUE);
    }

    public LinkedBlockingQueue(int n) {
        if (n > 0) {
            this.capacity = n;
            Node<Object> node = new Node<Object>(null);
            this.head = node;
            this.last = node;
            return;
        }
        throw new IllegalArgumentException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public LinkedBlockingQueue(Collection<? extends E> node) {
        this(Integer.MAX_VALUE);
        ReentrantLock reentrantLock = this.putLock;
        reentrantLock.lock();
        int n = 0;
        Iterator iterator = node.iterator();
        while (iterator.hasNext()) {
            Object e = iterator.next();
            if (e != null) {
                if (n != this.capacity) {
                    node = new Node(e);
                    LinkedBlockingQueue.super.enqueue(node);
                    ++n;
                    continue;
                }
                super("Queue full");
                throw node;
            }
            node = new Node();
            throw node;
        }
        this.count.set(n);
        return;
    }

    private E dequeue() {
        Node<E> node = this.head;
        Node node2 = node.next;
        node.next = node;
        this.head = node2;
        node = node2.item;
        node2.item = null;
        return (E)node;
    }

    private void enqueue(Node<E> node) {
        this.last.next = node;
        this.last = node;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.count.set(0);
        Object object = new Node<Object>(null);
        this.head = object;
        this.last = object;
        while ((object = objectInputStream.readObject()) != null) {
            this.add(object);
        }
        return;
    }

    private void signalNotEmpty() {
        ReentrantLock reentrantLock = this.takeLock;
        reentrantLock.lock();
        try {
            this.notEmpty.signal();
            return;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    private void signalNotFull() {
        ReentrantLock reentrantLock = this.putLock;
        reentrantLock.lock();
        try {
            this.notFull.signal();
            return;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.fullyLock();
        try {
            objectOutputStream.defaultWriteObject();
            Node node = this.head.next;
            while (node != null) {
                objectOutputStream.writeObject(node.item);
                node = node.next;
            }
            objectOutputStream.writeObject(null);
            return;
        }
        finally {
            this.fullyUnlock();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void clear() {
        this.fullyLock();
        try {
            Node node;
            Node<E> node2 = this.head;
            while ((node = node2.next) != null) {
                node2.next = node2;
                node.item = null;
                node2 = node;
            }
            this.head = this.last;
            if (this.count.getAndSet(0) != this.capacity) return;
            this.notFull.signal();
            return;
        }
        finally {
            this.fullyUnlock();
        }
    }

    @Override
    public boolean contains(Object object) {
        Node node;
        if (object == null) {
            return false;
        }
        this.fullyLock();
        try {
            node = this.head.next;
        }
        catch (Throwable throwable) {
            this.fullyUnlock();
            throw throwable;
        }
        while (node != null) {
            block6 : {
                boolean bl = object.equals(node.item);
                if (!bl) break block6;
                this.fullyUnlock();
                return true;
            }
            node = node.next;
        }
        this.fullyUnlock();
        return false;
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
        ReentrantLock reentrantLock;
        int n2;
        int n3;
        block16 : {
            int n4;
            if (collection == null) throw new NullPointerException();
            if (collection == this) throw new IllegalArgumentException();
            int n5 = 0;
            int n6 = 0;
            if (n <= 0) {
                return 0;
            }
            int n7 = 0;
            int n8 = 0;
            int n9 = 0;
            reentrantLock = this.takeLock;
            reentrantLock.lock();
            n3 = n8;
            n2 = Math.min(n, this.count.get());
            n3 = n8;
            Node<E> node = this.head;
            for (n4 = 0; n4 < n2; ++n4) {
                try {
                    Node node2 = node.next;
                    collection.add(node2.item);
                    node2.item = null;
                    node.next = node;
                    node = node2;
                }
                catch (Throwable throwable) {
                    block15 : {
                        n = n9;
                        if (n4 > 0) {
                            n3 = n8;
                            this.head = node;
                            n = n6;
                            n3 = n8;
                            if (this.count.getAndAdd(-n4) != this.capacity) break block15;
                            n = 1;
                        }
                    }
                    n3 = n;
                    throw throwable;
                }
                continue;
            }
            n = n7;
            if (n4 > 0) {
                n3 = n8;
                this.head = node;
                n3 = n8;
                n4 = this.count.getAndAdd(-n4);
                n3 = n8;
                n8 = this.capacity;
                n = n5;
                if (n4 != n8) break block16;
                n = 1;
            }
        }
        reentrantLock.unlock();
        if (n == 0) return n2;
        this.signalNotFull();
        return n2;
        {
            catch (Throwable throwable) {
                reentrantLock.unlock();
                if (n3 == 0) throw throwable;
                this.signalNotFull();
                throw throwable;
            }
        }
    }

    void fullyLock() {
        this.putLock.lock();
        this.takeLock.lock();
    }

    void fullyUnlock() {
        this.takeLock.unlock();
        this.putLock.unlock();
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public boolean offer(E object) {
        if (object != null) {
            boolean bl;
            int n;
            block7 : {
                AtomicInteger atomicInteger = this.count;
                int n2 = atomicInteger.get();
                n = this.capacity;
                bl = false;
                if (n2 == n) {
                    return false;
                }
                n = -1;
                Node<E> node = new Node<E>(object);
                object = this.putLock;
                ((ReentrantLock)object).lock();
                try {
                    if (atomicInteger.get() >= this.capacity) break block7;
                    this.enqueue(node);
                    n = n2 = atomicInteger.getAndIncrement();
                }
                catch (Throwable throwable) {
                    ((ReentrantLock)object).unlock();
                    throw throwable;
                }
                if (n2 + 1 >= this.capacity) break block7;
                this.notFull.signal();
                n = n2;
            }
            ((ReentrantLock)object).unlock();
            if (n == 0) {
                this.signalNotEmpty();
            }
            if (n >= 0) {
                bl = true;
            }
            return bl;
        }
        throw new NullPointerException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean offer(E e, long l, TimeUnit object) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        l = ((TimeUnit)((Object)object)).toNanos(l);
        object = this.putLock;
        AtomicInteger atomicInteger = this.count;
        ((ReentrantLock)object).lockInterruptibly();
        try {
            int n;
            int n2;
            while ((n = atomicInteger.get()) == (n2 = this.capacity)) {
                if (l <= 0L) {
                    ((ReentrantLock)object).unlock();
                    return false;
                }
                l = this.notFull.awaitNanos(l);
            }
            Node<E> node = new Node<E>(e);
            this.enqueue(node);
            n2 = atomicInteger.getAndIncrement();
            if (n2 + 1 < this.capacity) {
                this.notFull.signal();
            }
            ((ReentrantLock)object).unlock();
            if (n2 == 0) {
                this.signalNotEmpty();
            }
            return true;
        }
        catch (Throwable throwable) {
            ((ReentrantLock)object).unlock();
            throw throwable;
        }
    }

    @Override
    public E peek() {
        int n = this.count.get();
        E e = null;
        if (n == 0) {
            return null;
        }
        ReentrantLock reentrantLock = this.takeLock;
        reentrantLock.lock();
        try {
            if (this.count.get() > 0) {
                e = this.head.next.item;
            }
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
        int n;
        block6 : {
            int n2;
            E e2;
            AtomicInteger atomicInteger = this.count;
            if (atomicInteger.get() == 0) {
                return null;
            }
            e = null;
            n = -1;
            reentrantLock = this.takeLock;
            reentrantLock.lock();
            try {
                if (atomicInteger.get() <= 0) break block6;
                e2 = this.dequeue();
                n2 = atomicInteger.getAndDecrement();
            }
            catch (Throwable throwable) {
                reentrantLock.unlock();
                throw throwable;
            }
            e = e2;
            n = n2;
            if (n2 > 1) {
                this.notEmpty.signal();
                n = n2;
                e = e2;
            }
        }
        reentrantLock.unlock();
        if (n == this.capacity) {
            this.signalNotFull();
        }
        return e;
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
    public void put(E object) throws InterruptedException {
        if (object != null) {
            Node<E> node = new Node<E>(object);
            object = this.putLock;
            AtomicInteger atomicInteger = this.count;
            ((ReentrantLock)object).lockInterruptibly();
            try {
                while (atomicInteger.get() == this.capacity) {
                    this.notFull.await();
                }
                this.enqueue(node);
                int n = atomicInteger.getAndIncrement();
                if (n + 1 < this.capacity) {
                    this.notFull.signal();
                }
                if (n == 0) {
                    this.signalNotEmpty();
                }
                return;
            }
            finally {
                ((ReentrantLock)object).unlock();
            }
        }
        throw new NullPointerException();
    }

    @Override
    public int remainingCapacity() {
        return this.capacity - this.count.get();
    }

    @Override
    public boolean remove(Object object) {
        Node<E> node;
        Node node2;
        if (object == null) {
            return false;
        }
        this.fullyLock();
        try {
            node = this.head;
            node2 = node.next;
        }
        catch (Throwable throwable) {
            this.fullyUnlock();
            throw throwable;
        }
        while (node2 != null) {
            if (object.equals(node2.item)) {
                this.unlink(node2, node);
                this.fullyUnlock();
                return true;
            }
            node = node2;
            node2 = node2.next;
        }
        this.fullyUnlock();
        return false;
    }

    @Override
    public int size() {
        return this.count.get();
    }

    @Override
    public Spliterator<E> spliterator() {
        return new LBQSpliterator(this);
    }

    @Override
    public E take() throws InterruptedException {
        ReentrantLock reentrantLock;
        E e;
        int n;
        block6 : {
            AtomicInteger atomicInteger = this.count;
            reentrantLock = this.takeLock;
            reentrantLock.lockInterruptibly();
            while (atomicInteger.get() == 0) {
                this.notEmpty.await();
            }
            e = this.dequeue();
            n = atomicInteger.getAndDecrement();
            if (n <= 1) break block6;
            this.notEmpty.signal();
        }
        if (n == this.capacity) {
            this.signalNotFull();
        }
        return e;
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public Object[] toArray() {
        Node node;
        this.fullyLock();
        Object[] arrobject = new Object[this.count.get()];
        int n = 0;
        try {
            node = this.head.next;
        }
        catch (Throwable throwable) {
            this.fullyUnlock();
            throw throwable;
        }
        while (node != null) {
            arrobject[n] = node.item;
            node = node.next;
            ++n;
        }
        this.fullyUnlock();
        return arrobject;
    }

    @Override
    public <T> T[] toArray(T[] object) {
        this.fullyLock();
        int n = this.count.get();
        Object[] arrobject = object;
        if (((T[])object).length < n) {
            arrobject = (Object[])Array.newInstance(object.getClass().getComponentType(), n);
        }
        n = 0;
        object = this.head.next;
        while (object != null) {
            arrobject[n] = object.item;
            object = object.next;
            ++n;
        }
        try {
            if (arrobject.length > n) {
                arrobject[n] = null;
            }
            this.fullyUnlock();
            return arrobject;
        }
        catch (Throwable throwable) {
            this.fullyUnlock();
            throw throwable;
        }
    }

    @Override
    public String toString() {
        return Helpers.collectionToString(this);
    }

    void unlink(Node<E> node, Node<E> node2) {
        node.item = null;
        node2.next = node.next;
        if (this.last == node) {
            this.last = node2;
        }
        if (this.count.getAndDecrement() == this.capacity) {
            this.notFull.signal();
        }
    }

    private class Itr
    implements Iterator<E> {
        private Node<E> current;
        private E currentElement;
        private Node<E> lastRet;

        Itr() {
            LinkedBlockingQueue.this.fullyLock();
            try {
                this.current = LinkedBlockingQueue.this.head.next;
                if (this.current != null) {
                    this.currentElement = this.current.item;
                }
                return;
            }
            finally {
                LinkedBlockingQueue.this.fullyUnlock();
            }
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.current != null;
            return bl;
        }

        @Override
        public E next() {
            block9 : {
                Node node;
                LinkedBlockingQueue.this.fullyLock();
                if (this.current == null) break block9;
                this.lastRet = this.current;
                Node<E> node2 = null;
                Node<E> node3 = this.current;
                do {
                    Node node4;
                    node = node4 = node3.next;
                    if (node4 != node3) break block10;
                    break;
                } while (true);
                {
                    block10 : {
                        node = LinkedBlockingQueue.this.head.next;
                    }
                    if (node == null) break;
                    node2 = node3 = node.item;
                    if (node3 != null) break;
                    node3 = node;
                    continue;
                }
                this.current = node;
                node = this.currentElement;
                this.currentElement = node2;
                return (E)node;
            }
            try {
                NoSuchElementException noSuchElementException = new NoSuchElementException();
                throw noSuchElementException;
            }
            finally {
                LinkedBlockingQueue.this.fullyUnlock();
            }
        }

        @Override
        public void remove() {
            if (this.lastRet != null) {
                Node<E> node;
                Node node2;
                Node node3;
                LinkedBlockingQueue.this.fullyLock();
                try {
                    node = this.lastRet;
                    this.lastRet = null;
                    node2 = LinkedBlockingQueue.this.head;
                    node3 = node2.next;
                }
                catch (Throwable throwable) {
                    throw throwable;
                }
                finally {
                    LinkedBlockingQueue.this.fullyUnlock();
                }
                while (node3 != null) {
                    if (node3 == node) {
                        LinkedBlockingQueue.this.unlink(node3, node2);
                        break;
                    }
                    node2 = node3;
                    node3 = node3.next;
                }
                return;
            }
            throw new IllegalStateException();
        }
    }

    static final class LBQSpliterator<E>
    implements Spliterator<E> {
        static final int MAX_BATCH = 33554432;
        int batch;
        Node<E> current;
        long est;
        boolean exhausted;
        final LinkedBlockingQueue<E> queue;

        LBQSpliterator(LinkedBlockingQueue<E> linkedBlockingQueue) {
            this.queue = linkedBlockingQueue;
            this.est = linkedBlockingQueue.size();
        }

        @Override
        public int characteristics() {
            return 4368;
        }

        @Override
        public long estimateSize() {
            return this.est;
        }

        /*
         * Exception decompiling
         */
        @Override
        public void forEachRemaining(Consumer<? super E> var1_1) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[DOLOOP]], but top level block is 5[SIMPLE_IF_TAKEN]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean tryAdvance(Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            LinkedBlockingQueue<E> linkedBlockingQueue = this.queue;
            if (this.exhausted) return false;
            {
                Object t = null;
                linkedBlockingQueue.fullyLock();
                Object t2 = t;
                try {
                    if (this.current == null) {
                        this.current = linkedBlockingQueue.head.next;
                        t2 = t;
                    }
                    while (this.current != null) {
                        t = this.current.item;
                        this.current = this.current.next;
                        t2 = t;
                        if (t == null) continue;
                        t2 = t;
                        break;
                    }
                    linkedBlockingQueue.fullyUnlock();
                    if (this.current == null) {
                        this.exhausted = true;
                    }
                    if (t2 == null) return false;
                    {
                        consumer.accept(t2);
                        return true;
                    }
                }
                catch (Throwable throwable) {
                    linkedBlockingQueue.fullyUnlock();
                    throw throwable;
                }
            }
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public Spliterator<E> trySplit() {
            block12 : {
                block11 : {
                    var1_1 = this.queue;
                    var2_2 = this.batch;
                    var3_3 = 33554432;
                    if (var2_2 <= 0) {
                        var3_3 = 1;
                    } else if (var2_2 < 33554432) {
                        var3_3 = var2_2 + 1;
                    }
                    if (this.exhausted != false) return null;
                    var5_5 /* !! */  = var4_4 = this.current;
                    if (var4_4 == null) {
                        var5_5 /* !! */  = var4_4 = var1_1.head.next;
                        if (var4_4 == null) return null;
                    }
                    if (var5_5 /* !! */ .next == null) return null;
                    var6_7 = new Object[var3_3];
                    var7_8 = 0;
                    var2_2 = 0;
                    var5_5 /* !! */  = this.current;
                    var1_1.fullyLock();
                    var8_9 = var2_2;
                    var4_4 = var5_5 /* !! */ ;
                    if (var5_5 /* !! */  != null) ** GOTO lbl29
                    try {
                        block10 : {
                            var4_4 = var5_5 /* !! */  = var1_1.head.next;
                            var8_9 = var7_8;
                            var9_10 = var4_4;
                            if (var5_5 /* !! */  == null) break block10;
                            var8_9 = var2_2;
lbl29: // 2 sources:
                            do {
                                var6_7[var8_9] = var5_5 /* !! */  = var4_4.item;
                                var2_2 = var8_9;
                                if (var5_5 /* !! */  != null) {
                                    var2_2 = var8_9 + 1;
                                }
                                var5_5 /* !! */  = var4_4 = var4_4.next;
                                var8_9 = var2_2;
                                var9_10 = var5_5 /* !! */ ;
                                if (var4_4 == null) break block10;
                                var8_9 = var2_2;
                                var4_4 = var5_5 /* !! */ ;
                            } while (var2_2 < var3_3);
                            var9_10 = var5_5 /* !! */ ;
                            var8_9 = var2_2;
                        }
                        var1_1.fullyUnlock();
                        this.current = var9_10;
                        if (var9_10 != null) break block11;
                        this.est = 0L;
                        this.exhausted = true;
                        break block12;
                    }
                    catch (Throwable var5_6) {
                        var1_1.fullyUnlock();
                        throw var5_6;
                    }
                }
                this.est = var10_11 = this.est - (long)var8_9;
                if (var10_11 < 0L) {
                    this.est = 0L;
                }
            }
            if (var8_9 <= 0) return null;
            this.batch = var8_9;
            return Spliterators.spliterator(var6_7, 0, var8_9, 4368);
        }
    }

    static class Node<E> {
        E item;
        Node<E> next;

        Node(E e) {
            this.item = e;
        }
    }

}

