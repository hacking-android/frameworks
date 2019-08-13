/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class LinkedList<E>
extends AbstractSequentialList<E>
implements List<E>,
Deque<E>,
Cloneable,
Serializable {
    private static final long serialVersionUID = 876323262645176354L;
    transient Node<E> first;
    transient Node<E> last;
    transient int size = 0;

    public LinkedList() {
    }

    public LinkedList(Collection<? extends E> collection) {
        this();
        this.addAll(collection);
    }

    private void checkElementIndex(int n) {
        if (this.isElementIndex(n)) {
            return;
        }
        throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
    }

    private void checkPositionIndex(int n) {
        if (this.isPositionIndex(n)) {
            return;
        }
        throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
    }

    private boolean isElementIndex(int n) {
        boolean bl = n >= 0 && n < this.size;
        return bl;
    }

    private boolean isPositionIndex(int n) {
        boolean bl = n >= 0 && n <= this.size;
        return bl;
    }

    private void linkFirst(E object) {
        Node<E> node = this.first;
        object = new Node<E>(null, object, node);
        this.first = object;
        if (node == null) {
            this.last = object;
        } else {
            node.prev = object;
        }
        ++this.size;
        ++this.modCount;
    }

    private String outOfBoundsMsg(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index: ");
        stringBuilder.append(n);
        stringBuilder.append(", Size: ");
        stringBuilder.append(this.size);
        return stringBuilder.toString();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int n = objectInputStream.readInt();
        for (int i = 0; i < n; ++i) {
            this.linkLast(objectInputStream.readObject());
        }
    }

    private LinkedList<E> superClone() {
        try {
            LinkedList linkedList = (LinkedList)Object.super.clone();
            return linkedList;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    private E unlinkFirst(Node<E> node) {
        Object e = node.item;
        Node node2 = node.next;
        node.item = null;
        node.next = null;
        this.first = node2;
        if (node2 == null) {
            this.last = null;
        } else {
            node2.prev = null;
        }
        --this.size;
        ++this.modCount;
        return e;
    }

    private E unlinkLast(Node<E> node) {
        Object e = node.item;
        Node node2 = node.prev;
        node.item = null;
        node.prev = null;
        this.last = node2;
        if (node2 == null) {
            this.first = null;
        } else {
            node2.next = null;
        }
        --this.size;
        ++this.modCount;
        return e;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        Node<E> node = this.first;
        while (node != null) {
            objectOutputStream.writeObject(node.item);
            node = node.next;
        }
    }

    @Override
    public void add(int n, E e) {
        this.checkPositionIndex(n);
        if (n == this.size) {
            this.linkLast(e);
        } else {
            this.linkBefore(e, this.node(n));
        }
    }

    @Override
    public boolean add(E e) {
        this.linkLast(e);
        return true;
    }

    @Override
    public boolean addAll(int n, Collection<? extends E> node) {
        Node<E> node2;
        this.checkPositionIndex(n);
        Object[] arrobject = node.toArray();
        int n2 = arrobject.length;
        int n3 = 0;
        if (n2 == 0) {
            return false;
        }
        if (n == this.size) {
            node2 = null;
            node = this.last;
        } else {
            node2 = this.node(n);
            node = node2.prev;
        }
        int n4 = arrobject.length;
        for (n = n3; n < n4; ++n) {
            Node<Object> node3 = new Node<Object>(node, arrobject[n], null);
            if (node == null) {
                this.first = node3;
            } else {
                node.next = node3;
            }
            node = node3;
        }
        if (node2 == null) {
            this.last = node;
        } else {
            node.next = node2;
            node2.prev = node;
        }
        this.size += n2;
        ++this.modCount;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return this.addAll(this.size, collection);
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
        Node<E> node = this.first;
        while (node != null) {
            Node node2 = node.next;
            node.item = null;
            node.next = null;
            node.prev = null;
            node = node2;
        }
        this.last = null;
        this.first = null;
        this.size = 0;
        ++this.modCount;
    }

    public Object clone() {
        LinkedList linkedList = this.superClone();
        linkedList.last = null;
        linkedList.first = null;
        linkedList.size = 0;
        linkedList.modCount = 0;
        Node<E> node = this.first;
        while (node != null) {
            linkedList.add(node.item);
            node = node.next;
        }
        return linkedList;
    }

    @Override
    public boolean contains(Object object) {
        boolean bl = this.indexOf(object) != -1;
        return bl;
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
    public E get(int n) {
        this.checkElementIndex(n);
        return this.node((int)n).item;
    }

    @Override
    public E getFirst() {
        Node<E> node = this.first;
        if (node != null) {
            return node.item;
        }
        throw new NoSuchElementException();
    }

    @Override
    public E getLast() {
        Node<E> node = this.last;
        if (node != null) {
            return node.item;
        }
        throw new NoSuchElementException();
    }

    @Override
    public int indexOf(Object node) {
        int n = 0;
        int n2 = 0;
        if (node == null) {
            node = this.first;
            while (node != null) {
                if (node.item == null) {
                    return n2;
                }
                ++n2;
                node = node.next;
            }
        } else {
            Node<E> node2 = this.first;
            n2 = n;
            while (node2 != null) {
                if (node.equals(node2.item)) {
                    return n2;
                }
                ++n2;
                node2 = node2.next;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object node) {
        int n = this.size;
        if (node == null) {
            node = this.last;
            while (node != null) {
                --n;
                if (node.item == null) {
                    return n;
                }
                node = node.prev;
            }
        } else {
            Node<E> node2 = this.last;
            while (node2 != null) {
                --n;
                if (node.equals(node2.item)) {
                    return n;
                }
                node2 = node2.prev;
            }
        }
        return -1;
    }

    void linkBefore(E object, Node<E> node) {
        Node node2 = node.prev;
        object = new Node(node2, object, node);
        node.prev = object;
        if (node2 == null) {
            this.first = object;
        } else {
            node2.next = object;
        }
        ++this.size;
        ++this.modCount;
    }

    void linkLast(E object) {
        Node<E> node = this.last;
        object = new Node<E>(node, object, null);
        this.last = object;
        if (node == null) {
            this.first = object;
        } else {
            node.next = object;
        }
        ++this.size;
        ++this.modCount;
    }

    @Override
    public ListIterator<E> listIterator(int n) {
        this.checkPositionIndex(n);
        return new ListItr(n);
    }

    Node<E> node(int n) {
        int n2 = this.size;
        if (n < n2 >> 1) {
            Node<E> node = this.first;
            for (n2 = 0; n2 < n; ++n2) {
                node = node.next;
            }
            return node;
        }
        Node<E> node = this.last;
        --n2;
        while (n2 > n) {
            node = node.prev;
            --n2;
        }
        return node;
    }

    @Override
    public boolean offer(E e) {
        return this.add(e);
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
        Node<E> node = this.first;
        node = node == null ? null : node.item;
        return (E)node;
    }

    @Override
    public E peekFirst() {
        Node<E> node = this.first;
        node = node == null ? null : node.item;
        return (E)node;
    }

    @Override
    public E peekLast() {
        Node<E> node = this.last;
        node = node == null ? null : node.item;
        return (E)node;
    }

    @Override
    public E poll() {
        Node<E> node = this.first;
        node = node == null ? null : this.unlinkFirst(node);
        return (E)node;
    }

    @Override
    public E pollFirst() {
        Node<E> node = this.first;
        node = node == null ? null : this.unlinkFirst(node);
        return (E)node;
    }

    @Override
    public E pollLast() {
        Node<E> node = this.last;
        node = node == null ? null : this.unlinkLast(node);
        return (E)node;
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
    public E remove(int n) {
        this.checkElementIndex(n);
        return this.unlink(this.node(n));
    }

    @Override
    public boolean remove(Object node) {
        if (node == null) {
            node = this.first;
            while (node != null) {
                if (node.item == null) {
                    this.unlink(node);
                    return true;
                }
                node = node.next;
            }
        } else {
            Node<E> node2 = this.first;
            while (node2 != null) {
                if (node.equals(node2.item)) {
                    this.unlink(node2);
                    return true;
                }
                node2 = node2.next;
            }
        }
        return false;
    }

    @Override
    public E removeFirst() {
        Node<E> node = this.first;
        if (node != null) {
            return this.unlinkFirst(node);
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean removeFirstOccurrence(Object object) {
        return this.remove(object);
    }

    @Override
    public E removeLast() {
        Node<E> node = this.last;
        if (node != null) {
            return this.unlinkLast(node);
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean removeLastOccurrence(Object node) {
        if (node == null) {
            node = this.last;
            while (node != null) {
                if (node.item == null) {
                    this.unlink(node);
                    return true;
                }
                node = node.prev;
            }
        } else {
            Node<E> node2 = this.last;
            while (node2 != null) {
                if (node.equals(node2.item)) {
                    this.unlink(node2);
                    return true;
                }
                node2 = node2.prev;
            }
        }
        return false;
    }

    @Override
    public E set(int n, E e) {
        this.checkElementIndex(n);
        Node<E> node = this.node(n);
        Object e2 = node.item;
        node.item = e;
        return e2;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Spliterator<E> spliterator() {
        return new LLSpliterator(this, -1, 0);
    }

    @Override
    public Object[] toArray() {
        Object[] arrobject = new Object[this.size];
        int n = 0;
        Node<E> node = this.first;
        while (node != null) {
            arrobject[n] = node.item;
            node = node.next;
            ++n;
        }
        return arrobject;
    }

    @Override
    public <T> T[] toArray(T[] object) {
        Object[] arrobject = object;
        if (((T[])object).length < this.size) {
            arrobject = (Object[])Array.newInstance(object.getClass().getComponentType(), this.size);
        }
        int n = 0;
        object = this.first;
        while (object != null) {
            arrobject[n] = object.item;
            object = object.next;
            ++n;
        }
        n = arrobject.length;
        int n2 = this.size;
        if (n > n2) {
            arrobject[n2] = null;
        }
        return arrobject;
    }

    E unlink(Node<E> node) {
        Object e = node.item;
        Node node2 = node.next;
        Node node3 = node.prev;
        if (node3 == null) {
            this.first = node2;
        } else {
            node3.next = node2;
            node.prev = null;
        }
        if (node2 == null) {
            this.last = node3;
        } else {
            node2.prev = node3;
            node.next = null;
        }
        node.item = null;
        --this.size;
        ++this.modCount;
        return e;
    }

    private class DescendingIterator
    implements Iterator<E> {
        private final LinkedList<E> itr;

        private DescendingIterator() {
            LinkedList.this = LinkedList.this;
            this.itr = new ListItr(LinkedList.this.size());
        }

        @Override
        public boolean hasNext() {
            return ((ListItr)((Object)this.itr)).hasPrevious();
        }

        @Override
        public E next() {
            return ((ListItr)((Object)this.itr)).previous();
        }

        @Override
        public void remove() {
            ((ListItr)((Object)this.itr)).remove();
        }
    }

    static final class LLSpliterator<E>
    implements Spliterator<E> {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        int batch;
        Node<E> current;
        int est;
        int expectedModCount;
        final LinkedList<E> list;

        LLSpliterator(LinkedList<E> linkedList, int n, int n2) {
            this.list = linkedList;
            this.est = n;
            this.expectedModCount = n2;
        }

        @Override
        public int characteristics() {
            return 16464;
        }

        @Override
        public long estimateSize() {
            return this.getEst();
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            if (consumer != null) {
                int n;
                int n2 = n = this.getEst();
                if (n > 0) {
                    Node<E> node;
                    Node<E> node2 = node = this.current;
                    if (node != null) {
                        this.current = null;
                        this.est = 0;
                        do {
                            node = node2.item;
                            node2 = node2.next;
                            consumer.accept(node);
                            if (node2 == null) break;
                            n2 = n = n2 - 1;
                        } while (n > 0);
                    }
                }
                if (this.list.modCount == this.expectedModCount) {
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new NullPointerException();
        }

        final int getEst() {
            int n;
            int n2 = n = this.est;
            if (n < 0) {
                LinkedList<E> linkedList = this.list;
                if (linkedList == null) {
                    this.est = 0;
                    n2 = 0;
                } else {
                    this.expectedModCount = linkedList.modCount;
                    this.current = linkedList.first;
                    this.est = n2 = linkedList.size;
                }
            }
            return n2;
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> consumer) {
            if (consumer != null) {
                Node<E> node;
                if (this.getEst() > 0 && (node = this.current) != null) {
                    --this.est;
                    Object e = node.item;
                    this.current = node.next;
                    consumer.accept(e);
                    if (this.list.modCount == this.expectedModCount) {
                        return true;
                    }
                    throw new ConcurrentModificationException();
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public Spliterator<E> trySplit() {
            int n = this.getEst();
            if (n > 1) {
                Node<E> node;
                Node<E> node2 = node = this.current;
                if (node != null) {
                    int n2;
                    int n3;
                    int n4 = n2 = this.batch + 1024;
                    if (n2 > n) {
                        n4 = n;
                    }
                    n2 = n4;
                    if (n4 > 33554432) {
                        n2 = 33554432;
                    }
                    Object[] arrobject = new Object[n2];
                    n4 = 0;
                    do {
                        n3 = n4 + 1;
                        arrobject[n4] = node2.item;
                        node2 = node = node2.next;
                        if (node == null || n3 >= n2) break;
                        n4 = n3;
                    } while (true);
                    this.current = node2;
                    this.batch = n3;
                    this.est = n - n3;
                    return Spliterators.spliterator(arrobject, 0, n3, 16);
                }
            }
            return null;
        }
    }

    private class ListItr
    implements ListIterator<E> {
        private int expectedModCount;
        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;

        ListItr(int n) {
            this.expectedModCount = LinkedList.this.modCount;
            LinkedList.this = n == ((LinkedList)LinkedList.this).size ? null : ((LinkedList)LinkedList.this).node(n);
            this.next = LinkedList.this;
            this.nextIndex = n;
        }

        @Override
        public void add(E e) {
            this.checkForComodification();
            this.lastReturned = null;
            Node<E> node = this.next;
            if (node == null) {
                LinkedList.this.linkLast(e);
            } else {
                LinkedList.this.linkBefore(e, node);
            }
            ++this.nextIndex;
            ++this.expectedModCount;
        }

        final void checkForComodification() {
            if (LinkedList.this.modCount == this.expectedModCount) {
                return;
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            while (LinkedList.this.modCount == this.expectedModCount && this.nextIndex < LinkedList.this.size) {
                consumer.accept(this.next.item);
                Node<E> node = this.next;
                this.lastReturned = node;
                this.next = node.next;
                ++this.nextIndex;
            }
            this.checkForComodification();
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.nextIndex < LinkedList.this.size;
            return bl;
        }

        @Override
        public boolean hasPrevious() {
            boolean bl = this.nextIndex > 0;
            return bl;
        }

        @Override
        public E next() {
            this.checkForComodification();
            if (this.hasNext()) {
                Node<E> node = this.next;
                this.lastReturned = node;
                this.next = node.next;
                ++this.nextIndex;
                return this.lastReturned.item;
            }
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return this.nextIndex;
        }

        @Override
        public E previous() {
            this.checkForComodification();
            if (this.hasPrevious()) {
                Node<E> node = this.next;
                node = node == null ? LinkedList.this.last : node.prev;
                this.next = node;
                this.lastReturned = node;
                --this.nextIndex;
                return this.lastReturned.item;
            }
            throw new NoSuchElementException();
        }

        @Override
        public int previousIndex() {
            return this.nextIndex - 1;
        }

        @Override
        public void remove() {
            this.checkForComodification();
            Node<E> node = this.lastReturned;
            if (node != null) {
                node = node.next;
                LinkedList.this.unlink(this.lastReturned);
                if (this.next == this.lastReturned) {
                    this.next = node;
                } else {
                    --this.nextIndex;
                }
                this.lastReturned = null;
                ++this.expectedModCount;
                return;
            }
            throw new IllegalStateException();
        }

        @Override
        public void set(E e) {
            if (this.lastReturned != null) {
                this.checkForComodification();
                this.lastReturned.item = e;
                return;
            }
            throw new IllegalStateException();
        }
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> node, E e, Node<E> node2) {
            this.item = e;
            this.next = node2;
            this.prev = node;
        }
    }

}

