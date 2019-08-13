/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ArrayList<E>
extends AbstractList<E>
implements List<E>,
RandomAccess,
Cloneable,
Serializable {
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTDATA;
    private static final int MAX_ARRAY_SIZE = 2147483639;
    private static final long serialVersionUID = 8683452581122892189L;
    transient Object[] elementData;
    private int size;

    static {
        EMPTY_ELEMENTDATA = new Object[0];
        DEFAULTCAPACITY_EMPTY_ELEMENTDATA = new Object[0];
    }

    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    public ArrayList(int n) {
        block4 : {
            block3 : {
                block2 : {
                    if (n <= 0) break block2;
                    this.elementData = new Object[n];
                    break block3;
                }
                if (n != 0) break block4;
                this.elementData = EMPTY_ELEMENTDATA;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal Capacity: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public ArrayList(Collection<? extends E> arrobject) {
        int n;
        arrobject = this.elementData = arrobject.toArray();
        this.size = n = arrobject.length;
        if (n != 0) {
            if (arrobject.getClass() != Object[].class) {
                this.elementData = Arrays.copyOf(this.elementData, this.size, Object[].class);
            }
        } else {
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean batchRemove(Collection<?> collection, boolean bl) {
        int n;
        Object[] arrobject = this.elementData;
        int n2 = 0;
        int n3 = 0;
        boolean bl2 = false;
        do {
            block9 : {
                if (n2 >= this.size) break;
                boolean bl3 = collection.contains(arrobject[n2]);
                n = n3;
                if (bl3 != bl) break block9;
                arrobject[n3] = arrobject[n2];
                n = n3 + 1;
            }
            ++n2;
            n3 = n;
        } while (true);
        int n4 = this.size;
        n = n3;
        if (n2 != n4) {
            System.arraycopy(arrobject, n2, arrobject, n3, n4 - n2);
            n = n3 + (this.size - n2);
        }
        bl = bl2;
        if (n == this.size) return bl;
        n3 = n;
        do {
            if (n3 >= this.size) {
                this.modCount += this.size - n;
                this.size = n;
                return true;
            }
            arrobject[n3] = null;
            ++n3;
        } while (true);
        catch (Throwable throwable) {
            int n5 = this.size;
            n = n3;
            if (n2 != n5) {
                System.arraycopy(arrobject, n2, arrobject, n3, n5 - n2);
                n = n3 + (this.size - n2);
            }
            if (n == this.size) throw throwable;
            n3 = n;
            do {
                if (n3 >= this.size) {
                    this.modCount += this.size - n;
                    this.size = n;
                    throw throwable;
                }
                arrobject[n3] = null;
                ++n3;
            } while (true);
        }
    }

    private void ensureCapacityInternal(int n) {
        int n2 = n;
        if (this.elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            n2 = Math.max(10, n);
        }
        this.ensureExplicitCapacity(n2);
    }

    private void ensureExplicitCapacity(int n) {
        ++this.modCount;
        if (n - this.elementData.length > 0) {
            this.grow(n);
        }
    }

    private void fastRemove(int n) {
        Object[] arrobject;
        ++this.modCount;
        int n2 = this.size - n - 1;
        if (n2 > 0) {
            arrobject = this.elementData;
            System.arraycopy(arrobject, n + 1, arrobject, n, n2);
        }
        arrobject = this.elementData;
        this.size = n = this.size - 1;
        arrobject[n] = null;
    }

    private void grow(int n) {
        int n2;
        int n3 = this.elementData.length;
        n3 = n2 = (n3 >> 1) + n3;
        if (n2 - n < 0) {
            n3 = n;
        }
        n2 = n3;
        if (n3 - 2147483639 > 0) {
            n2 = ArrayList.hugeCapacity(n);
        }
        this.elementData = Arrays.copyOf(this.elementData, n2);
    }

    private static int hugeCapacity(int n) {
        if (n >= 0) {
            int n2 = 2147483639;
            n = n > 2147483639 ? Integer.MAX_VALUE : n2;
            return n;
        }
        throw new OutOfMemoryError();
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
        this.elementData = EMPTY_ELEMENTDATA;
        objectInputStream.defaultReadObject();
        objectInputStream.readInt();
        int n = this.size;
        if (n > 0) {
            this.ensureCapacityInternal(n);
            Object[] arrobject = this.elementData;
            for (n = 0; n < this.size; ++n) {
                arrobject[n] = objectInputStream.readObject();
            }
        }
    }

    static void subListRangeCheck(int n, int n2, int n3) {
        if (n >= 0) {
            if (n2 <= n3) {
                if (n <= n2) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("fromIndex(");
                stringBuilder.append(n);
                stringBuilder.append(") > toIndex(");
                stringBuilder.append(n2);
                stringBuilder.append(")");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("toIndex = ");
            stringBuilder.append(n2);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fromIndex = ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n = this.modCount;
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeObject(this.elementData[i]);
        }
        if (this.modCount == n) {
            return;
        }
        throw new ConcurrentModificationException();
    }

    @Override
    public void add(int n, E e) {
        int n2 = this.size;
        if (n <= n2 && n >= 0) {
            this.ensureCapacityInternal(n2 + 1);
            Object[] arrobject = this.elementData;
            System.arraycopy(arrobject, n, arrobject, n + 1, this.size - n);
            this.elementData[n] = e;
            ++this.size;
            return;
        }
        throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
    }

    @Override
    public boolean add(E e) {
        this.ensureCapacityInternal(this.size + 1);
        Object[] arrobject = this.elementData;
        int n = this.size;
        this.size = n + 1;
        arrobject[n] = e;
        return true;
    }

    @Override
    public boolean addAll(int n, Collection<? extends E> arrobject) {
        if (n <= this.size && n >= 0) {
            Object[] arrobject2;
            arrobject = arrobject.toArray();
            int n2 = arrobject.length;
            this.ensureCapacityInternal(this.size + n2);
            int n3 = this.size - n;
            if (n3 > 0) {
                arrobject2 = this.elementData;
                System.arraycopy(arrobject2, n, arrobject2, n + n2, n3);
            }
            arrobject2 = this.elementData;
            boolean bl = false;
            System.arraycopy(arrobject, 0, arrobject2, n, n2);
            this.size += n2;
            if (n2 != 0) {
                bl = true;
            }
            return bl;
        }
        throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
    }

    @Override
    public boolean addAll(Collection<? extends E> arrobject) {
        arrobject = arrobject.toArray();
        int n = arrobject.length;
        this.ensureCapacityInternal(this.size + n);
        Object[] arrobject2 = this.elementData;
        int n2 = this.size;
        boolean bl = false;
        System.arraycopy(arrobject, 0, arrobject2, n2, n);
        this.size += n;
        if (n != 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public void clear() {
        ++this.modCount;
        for (int i = 0; i < this.size; ++i) {
            this.elementData[i] = null;
        }
        this.size = 0;
    }

    public Object clone() {
        try {
            ArrayList arrayList = (ArrayList)Object.super.clone();
            arrayList.elementData = Arrays.copyOf(this.elementData, this.size);
            arrayList.modCount = 0;
            return arrayList;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException);
        }
    }

    @Override
    public boolean contains(Object object) {
        boolean bl = this.indexOf(object) >= 0;
        return bl;
    }

    public void ensureCapacity(int n) {
        int n2 = this.elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA ? 0 : 10;
        if (n > n2) {
            this.ensureExplicitCapacity(n);
        }
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        int n = this.modCount;
        Object[] arrobject = this.elementData;
        int n2 = this.size;
        for (int i = 0; this.modCount == n && i < n2; ++i) {
            consumer.accept(arrobject[i]);
        }
        if (this.modCount == n) {
            return;
        }
        throw new ConcurrentModificationException();
    }

    @Override
    public E get(int n) {
        if (n < this.size) {
            return (E)this.elementData[n];
        }
        throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
    }

    @Override
    public int indexOf(Object object) {
        if (object == null) {
            for (int i = 0; i < this.size; ++i) {
                if (this.elementData[i] != null) continue;
                return i;
            }
        } else {
            for (int i = 0; i < this.size; ++i) {
                if (!object.equals(this.elementData[i])) continue;
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.size == 0;
        return bl;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public int lastIndexOf(Object object) {
        if (object == null) {
            for (int i = this.size - 1; i >= 0; --i) {
                if (this.elementData[i] != null) continue;
                return i;
            }
        } else {
            for (int i = this.size - 1; i >= 0; --i) {
                if (!object.equals(this.elementData[i])) continue;
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int n) {
        if (n >= 0 && n <= this.size) {
            return new ListItr(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index: ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    @Override
    public E remove(int n) {
        if (n < this.size) {
            ++this.modCount;
            Object[] arrobject = this.elementData;
            Object object = arrobject[n];
            int n2 = this.size - n - 1;
            if (n2 > 0) {
                System.arraycopy(arrobject, n + 1, arrobject, n, n2);
            }
            arrobject = this.elementData;
            this.size = n = this.size - 1;
            arrobject[n] = null;
            return (E)object;
        }
        throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
    }

    @Override
    public boolean remove(Object object) {
        if (object == null) {
            for (int i = 0; i < this.size; ++i) {
                if (this.elementData[i] != null) continue;
                this.fastRemove(i);
                return true;
            }
        } else {
            for (int i = 0; i < this.size; ++i) {
                if (!object.equals(this.elementData[i])) continue;
                this.fastRemove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        Objects.requireNonNull(collection);
        return this.batchRemove(collection, false);
    }

    @Override
    public boolean removeIf(Predicate<? super E> arrobject) {
        int n;
        int n2;
        Objects.requireNonNull(arrobject);
        int n3 = 0;
        BitSet bitSet = new BitSet(this.size);
        int n4 = this.modCount;
        int n5 = this.size;
        for (n = 0; this.modCount == n4 && n < n5; ++n) {
            n2 = n3;
            if (arrobject.test(this.elementData[n])) {
                bitSet.set(n);
                n2 = n3 + 1;
            }
            n3 = n2;
        }
        if (this.modCount == n4) {
            boolean bl = n3 > 0;
            if (bl) {
                n2 = n5 - n3;
                n3 = 0;
                for (n = 0; n3 < n5 && n < n2; ++n3, ++n) {
                    n3 = bitSet.nextClearBit(n3);
                    arrobject = this.elementData;
                    arrobject[n] = arrobject[n3];
                }
                for (n = n2; n < n5; ++n) {
                    this.elementData[n] = null;
                }
                this.size = n2;
                if (this.modCount == n4) {
                    ++this.modCount;
                } else {
                    throw new ConcurrentModificationException();
                }
            }
            return bl;
        }
        throw new ConcurrentModificationException();
    }

    @Override
    protected void removeRange(int n, int n2) {
        if (n2 >= n) {
            ++this.modCount;
            int n3 = this.size;
            Object[] arrobject = this.elementData;
            System.arraycopy(arrobject, n2, arrobject, n, n3 - n2);
            for (n = n2 = this.size - (n2 - n); n < this.size; ++n) {
                this.elementData[n] = null;
            }
            this.size = n2;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    @Override
    public void replaceAll(UnaryOperator<E> unaryOperator) {
        Objects.requireNonNull(unaryOperator);
        int n = this.modCount;
        int n2 = this.size;
        for (int i = 0; this.modCount == n && i < n2; ++i) {
            Object[] arrobject = this.elementData;
            arrobject[i] = unaryOperator.apply(arrobject[i]);
        }
        if (this.modCount == n) {
            ++this.modCount;
            return;
        }
        throw new ConcurrentModificationException();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        Objects.requireNonNull(collection);
        return this.batchRemove(collection, true);
    }

    @Override
    public E set(int n, E e) {
        if (n < this.size) {
            Object[] arrobject = this.elementData;
            Object object = arrobject[n];
            arrobject[n] = e;
            return (E)object;
        }
        throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void sort(Comparator<? super E> comparator) {
        int n = this.modCount;
        Arrays.sort(this.elementData, 0, this.size, comparator);
        if (this.modCount == n) {
            ++this.modCount;
            return;
        }
        throw new ConcurrentModificationException();
    }

    @Override
    public Spliterator<E> spliterator() {
        return new ArrayListSpliterator(this, 0, -1, 0);
    }

    @Override
    public List<E> subList(int n, int n2) {
        ArrayList.subListRangeCheck(n, n2, this.size);
        return new SubList(this, 0, n, n2);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.elementData, this.size);
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        int n = arrT.length;
        int n2 = this.size;
        if (n < n2) {
            return Arrays.copyOf(this.elementData, n2, arrT.getClass());
        }
        System.arraycopy(this.elementData, 0, arrT, 0, n2);
        n = arrT.length;
        n2 = this.size;
        if (n > n2) {
            arrT[n2] = null;
        }
        return arrT;
    }

    public void trimToSize() {
        ++this.modCount;
        int n = this.size;
        Object[] arrobject = this.elementData;
        if (n < arrobject.length) {
            arrobject = n == 0 ? EMPTY_ELEMENTDATA : Arrays.copyOf(arrobject, n);
            this.elementData = arrobject;
        }
    }

    static final class ArrayListSpliterator<E>
    implements Spliterator<E> {
        private int expectedModCount;
        private int fence;
        private int index;
        private final ArrayList<E> list;

        ArrayListSpliterator(ArrayList<E> arrayList, int n, int n2, int n3) {
            this.list = arrayList;
            this.index = n;
            this.fence = n2;
            this.expectedModCount = n3;
        }

        private int getFence() {
            int n;
            int n2 = n = this.fence;
            if (n < 0) {
                ArrayList<E> arrayList = this.list;
                if (arrayList == null) {
                    this.fence = 0;
                    n2 = 0;
                } else {
                    this.expectedModCount = arrayList.modCount;
                    this.fence = n2 = arrayList.size;
                }
            }
            return n2;
        }

        @Override
        public int characteristics() {
            return 16464;
        }

        @Override
        public long estimateSize() {
            return this.getFence() - this.index;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            if (consumer != null) {
                Object[] arrobject;
                ArrayList<E> arrayList = this.list;
                if (arrayList != null && (arrobject = arrayList.elementData) != null) {
                    int n;
                    int n2;
                    int n3 = n = this.fence;
                    if (n < 0) {
                        n = arrayList.modCount;
                        n3 = arrayList.size;
                    } else {
                        n = this.expectedModCount;
                    }
                    if (n2 >= 0) {
                        this.index = n3;
                        if (n3 <= arrobject.length) {
                            for (int i = n2 = this.index; i < n3; ++i) {
                                consumer.accept(arrobject[i]);
                            }
                            if (arrayList.modCount == n) {
                                return;
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
                int n = this.index;
                int n2 = this.getFence();
                if (n < n2) {
                    this.index = n + 1;
                    consumer.accept(this.list.elementData[n]);
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
        public ArrayListSpliterator<E> trySplit() {
            Object object;
            int n = this.getFence();
            int n2 = this.index;
            if (n2 >= (n = n2 + n >>> 1)) {
                object = null;
            } else {
                object = this.list;
                this.index = n;
                object = new ArrayListSpliterator<E>((ArrayList<E>)object, n2, n, this.expectedModCount);
            }
            return object;
        }
    }

    private class Itr
    implements Iterator<E> {
        int cursor;
        int expectedModCount;
        int lastRet;
        protected int limit;

        private Itr() {
            this.limit = ArrayList.this.size;
            this.lastRet = -1;
            this.expectedModCount = ArrayList.this.modCount;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            int n;
            Objects.requireNonNull(consumer);
            int n2 = ArrayList.this.size;
            if (n >= n2) {
                return;
            }
            Object[] arrobject = ArrayList.this.elementData;
            if (n < arrobject.length) {
                for (n = this.cursor; n != n2 && ArrayList.this.modCount == this.expectedModCount; ++n) {
                    consumer.accept(arrobject[n]);
                }
                this.cursor = n;
                this.lastRet = n - 1;
                if (ArrayList.this.modCount == this.expectedModCount) {
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.cursor < this.limit;
            return bl;
        }

        @Override
        public E next() {
            if (ArrayList.this.modCount == this.expectedModCount) {
                int n = this.cursor;
                if (n < this.limit) {
                    Object[] arrobject = ArrayList.this.elementData;
                    if (n < arrobject.length) {
                        this.cursor = n + 1;
                        this.lastRet = n;
                        return (E)arrobject[n];
                    }
                    throw new ConcurrentModificationException();
                }
                throw new NoSuchElementException();
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public void remove() {
            if (this.lastRet >= 0) {
                if (ArrayList.this.modCount == this.expectedModCount) {
                    try {
                        ArrayList.this.remove(this.lastRet);
                        this.cursor = this.lastRet;
                        this.lastRet = -1;
                        this.expectedModCount = ArrayList.this.modCount;
                        --this.limit;
                        return;
                    }
                    catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        throw new ConcurrentModificationException();
                    }
                }
                throw new ConcurrentModificationException();
            }
            throw new IllegalStateException();
        }
    }

    private class ListItr
    extends ArrayList<E>
    implements ListIterator<E> {
        ListItr(int n) {
            this.cursor = n;
        }

        @Override
        public void add(E e) {
            if (ArrayList.this.modCount == this.expectedModCount) {
                try {
                    int n = this.cursor;
                    ArrayList.this.add(n, e);
                    this.cursor = n + 1;
                    this.lastRet = -1;
                    this.expectedModCount = ArrayList.this.modCount;
                    ++this.limit;
                    return;
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new ConcurrentModificationException();
                }
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public boolean hasPrevious() {
            boolean bl = this.cursor != 0;
            return bl;
        }

        @Override
        public int nextIndex() {
            return this.cursor;
        }

        @Override
        public E previous() {
            if (ArrayList.this.modCount == this.expectedModCount) {
                int n = this.cursor - 1;
                if (n >= 0) {
                    Object[] arrobject = ArrayList.this.elementData;
                    if (n < arrobject.length) {
                        this.cursor = n;
                        this.lastRet = n;
                        return (E)arrobject[n];
                    }
                    throw new ConcurrentModificationException();
                }
                throw new NoSuchElementException();
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public int previousIndex() {
            return this.cursor - 1;
        }

        @Override
        public void set(E e) {
            if (this.lastRet >= 0) {
                if (ArrayList.this.modCount == this.expectedModCount) {
                    try {
                        ArrayList.this.set(this.lastRet, e);
                        return;
                    }
                    catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        throw new ConcurrentModificationException();
                    }
                }
                throw new ConcurrentModificationException();
            }
            throw new IllegalStateException();
        }
    }

    private class SubList
    extends AbstractList<E>
    implements RandomAccess {
        private final int offset;
        private final AbstractList<E> parent;
        private final int parentOffset;
        int size;

        SubList(AbstractList<E> abstractList, int n, int n2, int n3) {
            this.parent = abstractList;
            this.parentOffset = n2;
            this.offset = n + n2;
            this.size = n3 - n2;
            this.modCount = ArrayList.this.modCount;
        }

        private String outOfBoundsMsg(int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index: ");
            stringBuilder.append(n);
            stringBuilder.append(", Size: ");
            stringBuilder.append(this.size);
            return stringBuilder.toString();
        }

        @Override
        public void add(int n, E e) {
            if (n >= 0 && n <= this.size) {
                if (ArrayList.this.modCount == this.modCount) {
                    this.parent.add(this.parentOffset + n, e);
                    this.modCount = this.parent.modCount;
                    ++this.size;
                    return;
                }
                throw new ConcurrentModificationException();
            }
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
        }

        @Override
        public boolean addAll(int n, Collection<? extends E> collection) {
            if (n >= 0 && n <= this.size) {
                int n2 = collection.size();
                if (n2 == 0) {
                    return false;
                }
                if (ArrayList.this.modCount == this.modCount) {
                    this.parent.addAll(this.parentOffset + n, collection);
                    this.modCount = this.parent.modCount;
                    this.size += n2;
                    return true;
                }
                throw new ConcurrentModificationException();
            }
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            return this.addAll(this.size, collection);
        }

        @Override
        public E get(int n) {
            if (n >= 0 && n < this.size) {
                if (ArrayList.this.modCount == this.modCount) {
                    return (E)ArrayList.this.elementData[this.offset + n];
                }
                throw new ConcurrentModificationException();
            }
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
        }

        @Override
        public Iterator<E> iterator() {
            return this.listIterator();
        }

        @Override
        public ListIterator<E> listIterator(final int n) {
            if (ArrayList.this.modCount == this.modCount) {
                if (n >= 0 && n <= this.size) {
                    return new ListIterator<E>(){
                        int cursor;
                        int expectedModCount;
                        int lastRet;
                        {
                            this.cursor = n;
                            this.lastRet = -1;
                            this.expectedModCount = ArrayList.this.modCount;
                        }

                        @Override
                        public void add(E e) {
                            if (this.expectedModCount == ArrayList.this.modCount) {
                                try {
                                    int n2 = this.cursor;
                                    SubList.this.add(n2, e);
                                    this.cursor = n2 + 1;
                                    this.lastRet = -1;
                                    this.expectedModCount = ArrayList.this.modCount;
                                    return;
                                }
                                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                                    throw new ConcurrentModificationException();
                                }
                            }
                            throw new ConcurrentModificationException();
                        }

                        @Override
                        public void forEachRemaining(Consumer<? super E> consumer) {
                            int n3;
                            Objects.requireNonNull(consumer);
                            int n2 = SubList.this.size;
                            if (n3 >= n2) {
                                return;
                            }
                            Object[] arrobject = ArrayList.this.elementData;
                            if (offset + n3 < arrobject.length) {
                                for (n3 = this.cursor; n3 != n2 && SubList.this.modCount == this.expectedModCount; ++n3) {
                                    consumer.accept(arrobject[offset + n3]);
                                }
                                this.cursor = n3;
                                this.lastRet = n3;
                                if (this.expectedModCount == ArrayList.this.modCount) {
                                    return;
                                }
                                throw new ConcurrentModificationException();
                            }
                            throw new ConcurrentModificationException();
                        }

                        @Override
                        public boolean hasNext() {
                            boolean bl = this.cursor != SubList.this.size;
                            return bl;
                        }

                        @Override
                        public boolean hasPrevious() {
                            boolean bl = this.cursor != 0;
                            return bl;
                        }

                        @Override
                        public E next() {
                            if (this.expectedModCount == ArrayList.this.modCount) {
                                int n3 = this.cursor;
                                if (n3 < SubList.this.size) {
                                    int n2 = offset;
                                    Object[] arrobject = ArrayList.this.elementData;
                                    if (n2 + n3 < arrobject.length) {
                                        this.cursor = n3 + 1;
                                        this.lastRet = n3;
                                        return (E)arrobject[n2 + n3];
                                    }
                                    throw new ConcurrentModificationException();
                                }
                                throw new NoSuchElementException();
                            }
                            throw new ConcurrentModificationException();
                        }

                        @Override
                        public int nextIndex() {
                            return this.cursor;
                        }

                        @Override
                        public E previous() {
                            if (this.expectedModCount == ArrayList.this.modCount) {
                                int n3 = this.cursor - 1;
                                if (n3 >= 0) {
                                    int n2 = offset;
                                    Object[] arrobject = ArrayList.this.elementData;
                                    if (n2 + n3 < arrobject.length) {
                                        this.cursor = n3;
                                        this.lastRet = n3;
                                        return (E)arrobject[n2 + n3];
                                    }
                                    throw new ConcurrentModificationException();
                                }
                                throw new NoSuchElementException();
                            }
                            throw new ConcurrentModificationException();
                        }

                        @Override
                        public int previousIndex() {
                            return this.cursor - 1;
                        }

                        @Override
                        public void remove() {
                            if (this.lastRet >= 0) {
                                if (this.expectedModCount == ArrayList.this.modCount) {
                                    try {
                                        SubList.this.remove(this.lastRet);
                                        this.cursor = this.lastRet;
                                        this.lastRet = -1;
                                        this.expectedModCount = ArrayList.this.modCount;
                                        return;
                                    }
                                    catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                                        throw new ConcurrentModificationException();
                                    }
                                }
                                throw new ConcurrentModificationException();
                            }
                            throw new IllegalStateException();
                        }

                        @Override
                        public void set(E e) {
                            if (this.lastRet >= 0) {
                                if (this.expectedModCount == ArrayList.this.modCount) {
                                    try {
                                        ArrayList.this.set(offset + this.lastRet, e);
                                        return;
                                    }
                                    catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                                        throw new ConcurrentModificationException();
                                    }
                                }
                                throw new ConcurrentModificationException();
                            }
                            throw new IllegalStateException();
                        }
                    };
                }
                throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public E remove(int n) {
            if (n >= 0 && n < this.size) {
                if (ArrayList.this.modCount == this.modCount) {
                    E e = this.parent.remove(this.parentOffset + n);
                    this.modCount = this.parent.modCount;
                    --this.size;
                    return e;
                }
                throw new ConcurrentModificationException();
            }
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
        }

        @Override
        protected void removeRange(int n, int n2) {
            if (ArrayList.this.modCount == this.modCount) {
                AbstractList<E> abstractList = this.parent;
                int n3 = this.parentOffset;
                abstractList.removeRange(n3 + n, n3 + n2);
                this.modCount = this.parent.modCount;
                this.size -= n2 - n;
                return;
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public E set(int n, E e) {
            if (n >= 0 && n < this.size) {
                if (ArrayList.this.modCount == this.modCount) {
                    Object object = ArrayList.this.elementData[this.offset + n];
                    ArrayList.this.elementData[this.offset + n] = e;
                    return (E)object;
                }
                throw new ConcurrentModificationException();
            }
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(n));
        }

        @Override
        public int size() {
            if (ArrayList.this.modCount == this.modCount) {
                return this.size;
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public Spliterator<E> spliterator() {
            if (this.modCount == ArrayList.this.modCount) {
                ArrayList arrayList = ArrayList.this;
                int n = this.offset;
                return new ArrayListSpliterator(arrayList, n, this.size + n, this.modCount);
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public List<E> subList(int n, int n2) {
            ArrayList.subListRangeCheck(n, n2, this.size);
            return new SubList(this, this.offset, n, n2);
        }

    }

}

