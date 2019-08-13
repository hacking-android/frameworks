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
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Helpers;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class LinkedBlockingDeque<E>
extends AbstractQueue<E>
implements BlockingDeque<E>,
Serializable {
    private static final long serialVersionUID = -387911632671998426L;
    private final int capacity;
    private transient int count;
    transient Node<E> first;
    transient Node<E> last;
    final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = this.lock.newCondition();
    private final Condition notFull = this.lock.newCondition();

    public LinkedBlockingDeque() {
        this(Integer.MAX_VALUE);
    }

    public LinkedBlockingDeque(int n) {
        if (n > 0) {
            this.capacity = n;
            return;
        }
        throw new IllegalArgumentException();
    }

    public LinkedBlockingDeque(Collection<? extends E> object) {
        this(Integer.MAX_VALUE);
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            Iterator<E> iterator = object.iterator();
            while (iterator.hasNext()) {
                E e = iterator.next();
                if (e != null) {
                    object = new Node(e);
                    if (LinkedBlockingDeque.super.linkLast((Node<E>)object)) continue;
                    object = new IllegalStateException("Deque full");
                    throw object;
                }
                object = new NullPointerException();
                throw object;
            }
            return;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    private boolean linkFirst(Node<E> node) {
        if (this.count >= this.capacity) {
            return false;
        }
        Node<E> node2 = this.first;
        node.next = node2;
        this.first = node;
        if (this.last == null) {
            this.last = node;
        } else {
            node2.prev = node;
        }
        ++this.count;
        this.notEmpty.signal();
        return true;
    }

    private boolean linkLast(Node<E> node) {
        if (this.count >= this.capacity) {
            return false;
        }
        Node<E> node2 = this.last;
        node.prev = node2;
        this.last = node;
        if (this.first == null) {
            this.first = node;
        } else {
            node2.next = node;
        }
        ++this.count;
        this.notEmpty.signal();
        return true;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.count = 0;
        this.first = null;
        this.last = null;
        Object object;
        while ((object = objectInputStream.readObject()) != null) {
            this.add(object);
        }
        return;
    }

    private E unlinkFirst() {
        Node<E> node = this.first;
        if (node == null) {
            return null;
        }
        Node node2 = node.next;
        Object e = node.item;
        node.item = null;
        node.next = node;
        this.first = node2;
        if (node2 == null) {
            this.last = null;
        } else {
            node2.prev = null;
        }
        --this.count;
        this.notFull.signal();
        return e;
    }

    private E unlinkLast() {
        Node<E> node = this.last;
        if (node == null) {
            return null;
        }
        Node node2 = node.prev;
        Object e = node.item;
        node.item = null;
        node.prev = node;
        this.last = node2;
        if (node2 == null) {
            this.first = null;
        } else {
            node2.next = null;
        }
        --this.count;
        this.notFull.signal();
        return e;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            objectOutputStream.defaultWriteObject();
            Node<E> node = this.first;
            while (node != null) {
                objectOutputStream.writeObject(node.item);
                node = node.next;
            }
            objectOutputStream.writeObject(null);
            return;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public boolean add(E e) {
        this.addLast(e);
        return true;
    }

    @Override
    public void addFirst(E e) {
        if (this.offerFirst(e)) {
            return;
        }
        throw new IllegalStateException("Deque full");
    }

    @Override
    public void addLast(E e) {
        if (this.offerLast(e)) {
            return;
        }
        throw new IllegalStateException("Deque full");
    }

    @Override
    public void clear() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        Node<E> node = this.first;
        while (node != null) {
            node.item = null;
            Node node2 = node.next;
            node.prev = null;
            node.next = null;
            node = node2;
        }
        try {
            this.last = null;
            this.first = null;
            this.count = 0;
            this.notFull.signalAll();
            return;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public boolean contains(Object object) {
        Node<E> node;
        if (object == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            node = this.first;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
        while (node != null) {
            block6 : {
                boolean bl = object.equals(node.item);
                if (!bl) break block6;
                reentrantLock.unlock();
                return true;
            }
            node = node.next;
        }
        reentrantLock.unlock();
        return false;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingItr();
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
                    n2 = Math.min(n, this.count);
                }
                catch (Throwable throwable) {
                    reentrantLock.unlock();
                    throw throwable;
                }
                for (n = 0; n < n2; ++n) {
                    collection.add(this.first.item);
                    this.unlinkFirst();
                }
                reentrantLock.unlock();
                return n2;
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    @Override
    public E element() {
        return this.getFirst();
    }

    @Override
    public E getFirst() {
        E e = this.peekFirst();
        if (e != null) {
            return e;
        }
        throw new NoSuchElementException();
    }

    @Override
    public E getLast() {
        E e = this.peekLast();
        if (e != null) {
            return e;
        }
        throw new NoSuchElementException();
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public boolean offer(E e) {
        return this.offerLast(e);
    }

    @Override
    public boolean offer(E e, long l, TimeUnit timeUnit) throws InterruptedException {
        return this.offerLast(e, l, timeUnit);
    }

    @Override
    public boolean offerFirst(E object) {
        if (object != null) {
            Node<E> node = new Node<E>(object);
            object = this.lock;
            ((ReentrantLock)object).lock();
            try {
                boolean bl = this.linkFirst(node);
                return bl;
            }
            finally {
                ((ReentrantLock)object).unlock();
            }
        }
        throw new NullPointerException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean offerFirst(E object, long l, TimeUnit object2) throws InterruptedException {
        if (object == null) {
            throw new NullPointerException();
        }
        object = new Node<Object>(object);
        l = ((TimeUnit)((Object)object2)).toNanos(l);
        object2 = this.lock;
        ((ReentrantLock)object2).lockInterruptibly();
        try {
            boolean bl;
            while (!(bl = this.linkFirst((Node<E>)object))) {
                if (l <= 0L) {
                    ((ReentrantLock)object2).unlock();
                    return false;
                }
                l = this.notFull.awaitNanos(l);
            }
            ((ReentrantLock)object2).unlock();
            return true;
        }
        catch (Throwable throwable) {
            ((ReentrantLock)object2).unlock();
            throw throwable;
        }
    }

    @Override
    public boolean offerLast(E object) {
        if (object != null) {
            Node<E> node = new Node<E>(object);
            object = this.lock;
            ((ReentrantLock)object).lock();
            try {
                boolean bl = this.linkLast(node);
                return bl;
            }
            finally {
                ((ReentrantLock)object).unlock();
            }
        }
        throw new NullPointerException();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean offerLast(E object, long l, TimeUnit timeUnit) throws InterruptedException {
        if (object == null) {
            throw new NullPointerException();
        }
        Node<Object> node = new Node<Object>(object);
        l = timeUnit.toNanos(l);
        object = this.lock;
        ((ReentrantLock)object).lockInterruptibly();
        try {
            boolean bl;
            while (!(bl = this.linkLast(node))) {
                if (l <= 0L) {
                    ((ReentrantLock)object).unlock();
                    return false;
                }
                l = this.notFull.awaitNanos(l);
            }
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
        return this.peekFirst();
    }

    @Override
    public E peekFirst() {
        E e;
        ReentrantLock reentrantLock;
        block4 : {
            reentrantLock = this.lock;
            reentrantLock.lock();
            if (this.first != null) break block4;
            e = null;
        }
        e = this.first.item;
        return e;
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public E peekLast() {
        E e;
        ReentrantLock reentrantLock;
        block4 : {
            reentrantLock = this.lock;
            reentrantLock.lock();
            if (this.last != null) break block4;
            e = null;
        }
        e = this.last.item;
        return e;
        finally {
            reentrantLock.unlock();
        }
    }

    @Override
    public E poll() {
        return this.pollFirst();
    }

    @Override
    public E poll(long l, TimeUnit timeUnit) throws InterruptedException {
        return this.pollFirst(l, timeUnit);
    }

    @Override
    public E pollFirst() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            E e = this.unlinkFirst();
            return e;
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
    public E pollFirst(long l, TimeUnit object) throws InterruptedException {
        E e;
        l = ((TimeUnit)((Object)object)).toNanos(l);
        object = this.lock;
        ((ReentrantLock)object).lockInterruptibly();
        do {
            block4 : {
                e = this.unlinkFirst();
                if (e != null) break;
                if (l > 0L) break block4;
                ((ReentrantLock)object).unlock();
                return null;
            }
            l = this.notEmpty.awaitNanos(l);
            continue;
            break;
        } while (true);
        ((ReentrantLock)object).unlock();
        return e;
        catch (Throwable throwable) {
            ((ReentrantLock)object).unlock();
            throw throwable;
        }
    }

    @Override
    public E pollLast() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            E e = this.unlinkLast();
            return e;
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
    public E pollLast(long l, TimeUnit object) throws InterruptedException {
        E e;
        l = ((TimeUnit)((Object)object)).toNanos(l);
        object = this.lock;
        ((ReentrantLock)object).lockInterruptibly();
        do {
            block4 : {
                e = this.unlinkLast();
                if (e != null) break;
                if (l > 0L) break block4;
                ((ReentrantLock)object).unlock();
                return null;
            }
            l = this.notEmpty.awaitNanos(l);
            continue;
            break;
        } while (true);
        ((ReentrantLock)object).unlock();
        return e;
        catch (Throwable throwable) {
            ((ReentrantLock)object).unlock();
            throw throwable;
        }
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
    public void put(E e) throws InterruptedException {
        this.putLast(e);
    }

    @Override
    public void putFirst(E object) throws InterruptedException {
        if (object != null) {
            Node<E> node = new Node<E>(object);
            object = this.lock;
            ((ReentrantLock)object).lock();
            try {
                while (!this.linkFirst(node)) {
                    this.notFull.await();
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
    public void putLast(E object) throws InterruptedException {
        if (object != null) {
            Node<E> node = new Node<E>(object);
            object = this.lock;
            ((ReentrantLock)object).lock();
            try {
                while (!this.linkLast(node)) {
                    this.notFull.await();
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
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            int n = this.capacity;
            int n2 = this.count;
            return n - n2;
        }
        finally {
            reentrantLock.unlock();
        }
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
        Node<E> node;
        if (object == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            node = this.first;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
        while (node != null) {
            block6 : {
                if (!object.equals(node.item)) break block6;
                this.unlink(node);
                reentrantLock.unlock();
                return true;
            }
            node = node.next;
        }
        reentrantLock.unlock();
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
        Node<E> node;
        if (object == null) {
            return false;
        }
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            node = this.last;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
        while (node != null) {
            block6 : {
                if (!object.equals(node.item)) break block6;
                this.unlink(node);
                reentrantLock.unlock();
                return true;
            }
            node = node.prev;
        }
        reentrantLock.unlock();
        return false;
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
        return new LBDSpliterator(this);
    }

    @Override
    public E take() throws InterruptedException {
        return this.takeFirst();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public E takeFirst() throws InterruptedException {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            E e;
            while ((e = this.unlinkFirst()) == null) {
                this.notEmpty.await();
            }
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
    public E takeLast() throws InterruptedException {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            E e;
            while ((e = this.unlinkLast()) == null) {
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
        Node<E> node;
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        Object[] arrobject = new Object[this.count];
        int n = 0;
        try {
            node = this.first;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
        while (node != null) {
            arrobject[n] = node.item;
            node = node.next;
            ++n;
        }
        reentrantLock.unlock();
        return arrobject;
    }

    @Override
    public <T> T[] toArray(T[] object) {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        Object[] arrobject = object;
        if (((T[])object).length < this.count) {
            arrobject = (Object[])Array.newInstance(object.getClass().getComponentType(), this.count);
        }
        int n = 0;
        object = this.first;
        while (object != null) {
            arrobject[n] = object.item;
            object = object.next;
            ++n;
        }
        try {
            if (arrobject.length > n) {
                arrobject[n] = null;
            }
            reentrantLock.unlock();
            return arrobject;
        }
        catch (Throwable throwable) {
            reentrantLock.unlock();
            throw throwable;
        }
    }

    @Override
    public String toString() {
        return Helpers.collectionToString(this);
    }

    void unlink(Node<E> node) {
        Node node2 = node.prev;
        Node node3 = node.next;
        if (node2 == null) {
            this.unlinkFirst();
        } else if (node3 == null) {
            this.unlinkLast();
        } else {
            node2.next = node3;
            node3.prev = node2;
            node.item = null;
            --this.count;
            this.notFull.signal();
        }
    }

    private abstract class AbstractItr
    implements Iterator<E> {
        private Node<E> lastRet;
        Node<E> next;
        E nextItem;

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        AbstractItr() {
            ReentrantLock reentrantLock = LinkedBlockingDeque.this.lock;
            reentrantLock.lock();
            try {
                void var1_4;
                this.next = this.firstNode();
                if (this.next == null) {
                    Object var1_2 = null;
                } else {
                    Object e = this.next.item;
                }
                this.nextItem = var1_4;
                return;
            }
            finally {
                reentrantLock.unlock();
            }
        }

        private Node<E> succ(Node<E> node) {
            Node<E> node2;
            while ((node2 = this.nextNode(node)) != null) {
                if (node2.item != null) {
                    return node2;
                }
                if (node2 == node) {
                    return this.firstNode();
                }
                node = node2;
            }
            return null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void advance() {
            ReentrantLock reentrantLock = LinkedBlockingDeque.this.lock;
            reentrantLock.lock();
            try {
                this.next = this.succ(this.next);
                Object var2_2 = this.next == null ? null : this.next.item;
                this.nextItem = var2_2;
                return;
            }
            finally {
                reentrantLock.unlock();
            }
        }

        abstract Node<E> firstNode();

        @Override
        public boolean hasNext() {
            boolean bl = this.next != null;
            return bl;
        }

        @Override
        public E next() {
            Node<E> node = this.next;
            if (node != null) {
                this.lastRet = node;
                node = this.nextItem;
                this.advance();
                return (E)node;
            }
            throw new NoSuchElementException();
        }

        abstract Node<E> nextNode(Node<E> var1);

        @Override
        public void remove() {
            Node<E> node = this.lastRet;
            if (node != null) {
                this.lastRet = null;
                ReentrantLock reentrantLock = LinkedBlockingDeque.this.lock;
                reentrantLock.lock();
                try {
                    if (node.item != null) {
                        LinkedBlockingDeque.this.unlink(node);
                    }
                    return;
                }
                finally {
                    reentrantLock.unlock();
                }
            }
            throw new IllegalStateException();
        }
    }

    private class DescendingItr
    extends LinkedBlockingDeque<E> {
        private DescendingItr() {
        }

        Node<E> firstNode() {
            return LinkedBlockingDeque.this.last;
        }

        Node<E> nextNode(Node<E> node) {
            return node.prev;
        }
    }

    private class Itr
    extends LinkedBlockingDeque<E> {
        private Itr() {
        }

        Node<E> firstNode() {
            return LinkedBlockingDeque.this.first;
        }

        Node<E> nextNode(Node<E> node) {
            return node.next;
        }
    }

    static final class LBDSpliterator<E>
    implements Spliterator<E> {
        static final int MAX_BATCH = 33554432;
        int batch;
        Node<E> current;
        long est;
        boolean exhausted;
        final LinkedBlockingDeque<E> queue;

        LBDSpliterator(LinkedBlockingDeque<E> linkedBlockingDeque) {
            this.queue = linkedBlockingDeque;
            this.est = linkedBlockingDeque.size();
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
            LinkedBlockingDeque<E> linkedBlockingDeque = this.queue;
            ReentrantLock reentrantLock = linkedBlockingDeque.lock;
            if (this.exhausted) return false;
            {
                Object t = null;
                reentrantLock.lock();
                Object t2 = t;
                try {
                    if (this.current == null) {
                        this.current = linkedBlockingDeque.first;
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
                    reentrantLock.unlock();
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
                    reentrantLock.unlock();
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
                    var1_1 /* !! */  = this.queue;
                    var2_2 = this.batch;
                    var3_3 = 33554432;
                    if (var2_2 <= 0) {
                        var3_3 = 1;
                    } else if (var2_2 < 33554432) {
                        var3_3 = var2_2 + 1;
                    }
                    if (this.exhausted != false) return null;
                    var5_5 = var4_4 /* !! */  = this.current;
                    if (var4_4 /* !! */  == null) {
                        var5_5 = var4_4 /* !! */  = var1_1 /* !! */ .first;
                        if (var4_4 /* !! */  == null) return null;
                    }
                    if (var5_5.next == null) return null;
                    var6_7 = new Object[var3_3];
                    var7_8 = var1_1 /* !! */ .lock;
                    var8_9 = 0;
                    var2_2 = 0;
                    var4_4 /* !! */  = this.current;
                    var7_8.lock();
                    var9_10 = var2_2;
                    var5_5 = var4_4 /* !! */ ;
                    if (var4_4 /* !! */  != null) ** GOTO lbl30
                    try {
                        block10 : {
                            var5_5 = var4_4 /* !! */  = var1_1 /* !! */ .first;
                            var9_10 = var8_9;
                            var1_1 /* !! */  = var5_5;
                            if (var4_4 /* !! */  == null) break block10;
                            var9_10 = var2_2;
lbl30: // 2 sources:
                            do {
                                var6_7[var9_10] = var4_4 /* !! */  = var5_5.item;
                                var2_2 = var9_10;
                                if (var4_4 /* !! */  != null) {
                                    var2_2 = var9_10 + 1;
                                }
                                var4_4 /* !! */  = var5_5 = var5_5.next;
                                var9_10 = var2_2;
                                var1_1 /* !! */  = var4_4 /* !! */ ;
                                if (var5_5 == null) break block10;
                                var9_10 = var2_2;
                                var5_5 = var4_4 /* !! */ ;
                            } while (var2_2 < var3_3);
                            var1_1 /* !! */  = var4_4 /* !! */ ;
                            var9_10 = var2_2;
                        }
                        var7_8.unlock();
                        this.current = var1_1 /* !! */ ;
                        if (var1_1 /* !! */  != null) break block11;
                        this.est = 0L;
                        this.exhausted = true;
                        break block12;
                    }
                    catch (Throwable var5_6) {
                        var7_8.unlock();
                        throw var5_6;
                    }
                }
                this.est = var10_11 = this.est - (long)var9_10;
                if (var10_11 < 0L) {
                    this.est = 0L;
                }
            }
            if (var9_10 <= 0) return null;
            this.batch = var9_10;
            return Spliterators.spliterator(var6_7, 0, var9_10, 4368);
        }
    }

    static final class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(E e) {
            this.item = e;
        }
    }

}

