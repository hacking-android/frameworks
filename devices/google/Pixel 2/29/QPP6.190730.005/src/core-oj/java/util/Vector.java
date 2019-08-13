/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
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

public class Vector<E>
extends AbstractList<E>
implements List<E>,
RandomAccess,
Cloneable,
Serializable {
    private static final int MAX_ARRAY_SIZE = 2147483639;
    private static final long serialVersionUID = -2767605614048989439L;
    protected int capacityIncrement;
    protected int elementCount;
    protected Object[] elementData;

    public Vector() {
        this(10);
    }

    public Vector(int n) {
        this(n, 0);
    }

    public Vector(int n, int n2) {
        if (n >= 0) {
            this.elementData = new Object[n];
            this.capacityIncrement = n2;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal Capacity: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Vector(Collection<? extends E> arrobject) {
        arrobject = this.elementData = arrobject.toArray();
        this.elementCount = arrobject.length;
        if (arrobject.getClass() != Object[].class) {
            this.elementData = Arrays.copyOf(this.elementData, this.elementCount, Object[].class);
        }
    }

    private void ensureCapacityHelper(int n) {
        if (n - this.elementData.length > 0) {
            this.grow(n);
        }
    }

    private void grow(int n) {
        int n2 = this.elementData.length;
        int n3 = this.capacityIncrement;
        if (n3 <= 0) {
            n3 = n2;
        }
        n3 = n2 = n3 + n2;
        if (n2 - n < 0) {
            n3 = n;
        }
        n2 = n3;
        if (n3 - 2147483639 > 0) {
            n2 = Vector.hugeCapacity(n);
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        Object[] arrobject;
        ObjectOutputStream.PutField putField = objectOutputStream.putFields();
        synchronized (this) {
            putField.put("capacityIncrement", this.capacityIncrement);
            putField.put("elementCount", this.elementCount);
            arrobject = (Object[])this.elementData.clone();
        }
        putField.put("elementData", arrobject);
        objectOutputStream.writeFields();
    }

    @Override
    public void add(int n, E e) {
        this.insertElementAt(e, n);
    }

    @Override
    public boolean add(E e) {
        synchronized (this) {
            ++this.modCount;
            this.ensureCapacityHelper(this.elementCount + 1);
            Object[] arrobject = this.elementData;
            int n = this.elementCount;
            this.elementCount = n + 1;
            arrobject[n] = e;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean addAll(int n, Collection<? extends E> object) {
        synchronized (this) {
            int n2 = this.modCount;
            boolean bl = true;
            this.modCount = n2 + 1;
            if (n >= 0 && n <= this.elementCount) {
                object = object.toArray();
                n2 = ((Object)object).length;
                this.ensureCapacityHelper(this.elementCount + n2);
                int n3 = this.elementCount - n;
                if (n3 > 0) {
                    System.arraycopy(this.elementData, n, this.elementData, n + n2, n3);
                }
                System.arraycopy(object, 0, this.elementData, n, n2);
                this.elementCount += n2;
                if (n2 == 0) return false;
                return bl;
            }
            object = new ArrayIndexOutOfBoundsException(n);
            throw object;
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> arrobject) {
        synchronized (this) {
            boolean bl;
            block4 : {
                int n = this.modCount;
                bl = true;
                this.modCount = n + 1;
                arrobject = arrobject.toArray();
                n = arrobject.length;
                this.ensureCapacityHelper(this.elementCount + n);
                System.arraycopy(arrobject, 0, this.elementData, this.elementCount, n);
                this.elementCount += n;
                if (n != 0) break block4;
                bl = false;
            }
            return bl;
        }
    }

    public void addElement(E e) {
        synchronized (this) {
            ++this.modCount;
            this.ensureCapacityHelper(this.elementCount + 1);
            Object[] arrobject = this.elementData;
            int n = this.elementCount;
            this.elementCount = n + 1;
            arrobject[n] = e;
            return;
        }
    }

    public int capacity() {
        synchronized (this) {
            int n = this.elementData.length;
            return n;
        }
    }

    @Override
    public void clear() {
        this.removeAllElements();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object clone() {
        synchronized (this) {
            try {
                try {
                    Vector vector = (Vector)Object.super.clone();
                    vector.elementData = Arrays.copyOf(this.elementData, this.elementCount);
                    vector.modCount = 0;
                    return vector;
                }
                catch (CloneNotSupportedException cloneNotSupportedException) {
                    InternalError internalError = new InternalError(cloneNotSupportedException);
                    throw internalError;
                }
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    @Override
    public boolean contains(Object object) {
        boolean bl = false;
        if (this.indexOf(object, 0) >= 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        synchronized (this) {
            boolean bl = super.containsAll(collection);
            return bl;
        }
    }

    public void copyInto(Object[] arrobject) {
        synchronized (this) {
            System.arraycopy(this.elementData, 0, arrobject, 0, this.elementCount);
            return;
        }
    }

    public E elementAt(int n) {
        synchronized (this) {
            block4 : {
                if (n >= this.elementCount) break block4;
                E e = this.elementData(n);
                return e;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(n);
            stringBuilder.append(" >= ");
            stringBuilder.append(this.elementCount);
            ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException = new ArrayIndexOutOfBoundsException(stringBuilder.toString());
            throw arrayIndexOutOfBoundsException;
        }
    }

    E elementData(int n) {
        return (E)this.elementData[n];
    }

    public Enumeration<E> elements() {
        return new Enumeration<E>(){
            int count = 0;

            @Override
            public boolean hasMoreElements() {
                boolean bl = this.count < Vector.this.elementCount;
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public E nextElement() {
                Vector vector = Vector.this;
                synchronized (vector) {
                    if (this.count < Vector.this.elementCount) {
                        Vector vector2 = Vector.this;
                        int n = this.count;
                        this.count = n + 1;
                        vector2 = vector2.elementData(n);
                        return (E)vector2;
                    }
                    throw new NoSuchElementException("Vector Enumeration");
                }
            }
        };
    }

    public void ensureCapacity(int n) {
        synchronized (this) {
            if (n > 0) {
                ++this.modCount;
                this.ensureCapacityHelper(n);
            }
            return;
        }
    }

    @Override
    public boolean equals(Object object) {
        synchronized (this) {
            boolean bl = super.equals(object);
            return bl;
        }
    }

    public E firstElement() {
        synchronized (this) {
            block4 : {
                if (this.elementCount == 0) break block4;
                E e = this.elementData(0);
                return e;
            }
            NoSuchElementException noSuchElementException = new NoSuchElementException();
            throw noSuchElementException;
        }
    }

    @Override
    public void forEach(Consumer<? super E> object) {
        synchronized (this) {
            block8 : {
                Objects.requireNonNull(object);
                int n = this.modCount;
                Object[] arrobject = this.elementData;
                int n2 = this.elementCount;
                int n3 = 0;
                do {
                    if (this.modCount != n || n3 >= n2) break;
                    object.accept(arrobject[n3]);
                    ++n3;
                } while (true);
                n3 = this.modCount;
                if (n3 != n) break block8;
                return;
            }
            object = new ConcurrentModificationException();
            throw object;
        }
    }

    @Override
    public E get(int n) {
        synchronized (this) {
            block4 : {
                if (n >= this.elementCount) break block4;
                E e = this.elementData(n);
                return e;
            }
            ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException = new ArrayIndexOutOfBoundsException(n);
            throw arrayIndexOutOfBoundsException;
        }
    }

    @Override
    public int hashCode() {
        synchronized (this) {
            int n = super.hashCode();
            return n;
        }
    }

    @Override
    public int indexOf(Object object) {
        return this.indexOf(object, 0);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int indexOf(Object object, int n) {
        synchronized (this) {
            void var2_2;
            if (object == null) {
                while (++var2_2 < this.elementCount) {
                    object = this.elementData[var2_2];
                    if (object != null) continue;
                    return (int)var2_2;
                }
            } else {
                while (++var2_2 < this.elementCount) {
                    boolean bl = object.equals(this.elementData[var2_2]);
                    if (!bl) continue;
                    return (int)var2_2;
                }
            }
            return -1;
        }
    }

    public void insertElementAt(E object, int n) {
        synchronized (this) {
            ++this.modCount;
            if (n <= this.elementCount) {
                this.ensureCapacityHelper(this.elementCount + 1);
                System.arraycopy(this.elementData, n, this.elementData, n + 1, this.elementCount - n);
                this.elementData[n] = object;
                ++this.elementCount;
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" > ");
            ((StringBuilder)object).append(this.elementCount);
            ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException = new ArrayIndexOutOfBoundsException(((StringBuilder)object).toString());
            throw arrayIndexOutOfBoundsException;
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (this) {
            int n = this.elementCount;
            boolean bl = n == 0;
            return bl;
        }
    }

    @Override
    public Iterator<E> iterator() {
        synchronized (this) {
            Itr itr = new Itr();
            return itr;
        }
    }

    public E lastElement() {
        synchronized (this) {
            block4 : {
                if (this.elementCount == 0) break block4;
                E e = this.elementData(this.elementCount - 1);
                return e;
            }
            NoSuchElementException noSuchElementException = new NoSuchElementException();
            throw noSuchElementException;
        }
    }

    @Override
    public int lastIndexOf(Object object) {
        synchronized (this) {
            int n = this.lastIndexOf(object, this.elementCount - 1);
            return n;
        }
    }

    public int lastIndexOf(Object object, int n) {
        synchronized (this) {
            block8 : {
                block12 : {
                    block9 : {
                        if (n >= this.elementCount) break block8;
                        if (object != null) break block9;
                        while (n >= 0) {
                            block10 : {
                                object = this.elementData[n];
                                if (object != null) break block10;
                                return n;
                            }
                            --n;
                        }
                        break block12;
                    }
                    while (n >= 0) {
                        block11 : {
                            boolean bl = object.equals(this.elementData[n]);
                            if (!bl) break block11;
                            return n;
                        }
                        --n;
                    }
                }
                return -1;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" >= ");
            ((StringBuilder)object).append(this.elementCount);
            IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException(((StringBuilder)object).toString());
            throw indexOutOfBoundsException;
        }
    }

    @Override
    public ListIterator<E> listIterator() {
        synchronized (this) {
            ListItr listItr = new ListItr(0);
            return listItr;
        }
    }

    @Override
    public ListIterator<E> listIterator(int n) {
        synchronized (this) {
            block5 : {
                if (n >= 0) {
                    if (n > this.elementCount) break block5;
                    ListItr listItr = new ListItr(n);
                    return listItr;
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index: ");
            stringBuilder.append(n);
            IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException(stringBuilder.toString());
            throw indexOutOfBoundsException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public E remove(int n) {
        synchronized (this) {
            ++this.modCount;
            if (n >= this.elementCount) {
                ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException = new ArrayIndexOutOfBoundsException(n);
                throw arrayIndexOutOfBoundsException;
            }
            E e = this.elementData(n);
            int n2 = this.elementCount - n - 1;
            if (n2 > 0) {
                System.arraycopy(this.elementData, n + 1, this.elementData, n, n2);
            }
            Object[] arrobject = this.elementData;
            this.elementCount = n = this.elementCount - 1;
            arrobject[n] = null;
            return e;
        }
    }

    @Override
    public boolean remove(Object object) {
        return this.removeElement(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        synchronized (this) {
            boolean bl = super.removeAll(collection);
            return bl;
        }
    }

    public void removeAllElements() {
        synchronized (this) {
            ++this.modCount;
            int n = 0;
            do {
                if (n >= this.elementCount) break;
                this.elementData[n] = null;
                ++n;
            } while (true);
            this.elementCount = 0;
            return;
        }
    }

    public boolean removeElement(Object object) {
        synchronized (this) {
            block4 : {
                ++this.modCount;
                int n = this.indexOf(object);
                if (n < 0) break block4;
                this.removeElementAt(n);
                return true;
            }
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeElementAt(int n) {
        synchronized (this) {
            ++this.modCount;
            if (n >= this.elementCount) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(n);
                stringBuilder.append(" >= ");
                stringBuilder.append(this.elementCount);
                ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException = new ArrayIndexOutOfBoundsException(stringBuilder.toString());
                throw arrayIndexOutOfBoundsException;
            }
            if (n < 0) {
                ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException = new ArrayIndexOutOfBoundsException(n);
                throw arrayIndexOutOfBoundsException;
            }
            int n2 = this.elementCount - n - 1;
            if (n2 > 0) {
                System.arraycopy(this.elementData, n + 1, this.elementData, n, n2);
            }
            --this.elementCount;
            this.elementData[this.elementCount] = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean removeIf(Predicate<? super E> object) {
        synchronized (this) {
            int n;
            int n2;
            Objects.requireNonNull(object);
            int n3 = 0;
            int n4 = this.elementCount;
            BitSet bitSet = new BitSet(n4);
            int n5 = this.modCount;
            boolean bl = false;
            for (n2 = 0; this.modCount == n5 && n2 < n4; ++n2) {
                n = n3;
                if (object.test(this.elementData[n2])) {
                    bitSet.set(n2);
                    n = n3 + 1;
                }
                n3 = n;
            }
            if (this.modCount != n5) {
                object = new ConcurrentModificationException();
                throw object;
            }
            if (n3 > 0) {
                bl = true;
            }
            if (bl) {
                n3 = n4 - n3;
                n = 0;
                for (n2 = 0; n < n4 && n2 < n3; ++n, ++n2) {
                    n = bitSet.nextClearBit(n);
                    this.elementData[n2] = this.elementData[n];
                }
                for (n2 = n3; n2 < n4; ++n2) {
                    this.elementData[n2] = null;
                }
                this.elementCount = n3;
                if (this.modCount != n5) {
                    object = new ConcurrentModificationException();
                    throw object;
                }
                ++this.modCount;
            }
            return bl;
        }
    }

    @Override
    protected void removeRange(int n, int n2) {
        synchronized (this) {
            ++this.modCount;
            int n3 = this.elementCount;
            System.arraycopy(this.elementData, n2, this.elementData, n, n3 - n2);
            n3 = this.elementCount;
            while (this.elementCount != n3 - (n2 - n)) {
                int n4;
                Object[] arrobject = this.elementData;
                this.elementCount = n4 = this.elementCount - 1;
                arrobject[n4] = null;
            }
            return;
        }
    }

    @Override
    public void replaceAll(UnaryOperator<E> object) {
        synchronized (this) {
            Objects.requireNonNull(object);
            int n = this.modCount;
            int n2 = this.elementCount;
            int n3 = 0;
            do {
                if (this.modCount != n || n3 >= n2) break;
                this.elementData[n3] = object.apply(this.elementData[n3]);
                ++n3;
            } while (true);
            if (this.modCount == n) {
                ++this.modCount;
                return;
            }
            object = new ConcurrentModificationException();
            throw object;
        }
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        synchronized (this) {
            boolean bl = super.retainAll(collection);
            return bl;
        }
    }

    @Override
    public E set(int n, E object) {
        synchronized (this) {
            block4 : {
                if (n >= this.elementCount) break block4;
                E e = this.elementData(n);
                this.elementData[n] = object;
                return e;
            }
            object = new Object(n);
            throw object;
        }
    }

    public void setElementAt(E object, int n) {
        synchronized (this) {
            if (n < this.elementCount) {
                this.elementData[n] = object;
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" >= ");
            ((StringBuilder)object).append(this.elementCount);
            ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException = new ArrayIndexOutOfBoundsException(((StringBuilder)object).toString());
            throw arrayIndexOutOfBoundsException;
        }
    }

    public void setSize(int n) {
        synchronized (this) {
            block7 : {
                ++this.modCount;
                if (n > this.elementCount) {
                    this.ensureCapacityHelper(n);
                    break block7;
                }
                int n2 = n;
                do {
                    if (n2 >= this.elementCount) break;
                    this.elementData[n2] = null;
                    ++n2;
                } while (true);
            }
            this.elementCount = n;
            return;
        }
    }

    @Override
    public int size() {
        synchronized (this) {
            int n = this.elementCount;
            return n;
        }
    }

    @Override
    public void sort(Comparator<? super E> object) {
        synchronized (this) {
            int n = this.modCount;
            Arrays.sort(this.elementData, 0, this.elementCount, object);
            if (this.modCount == n) {
                ++this.modCount;
                return;
            }
            object = new ConcurrentModificationException();
            throw object;
        }
    }

    @Override
    public Spliterator<E> spliterator() {
        return new VectorSpliterator(this, null, 0, -1, 0);
    }

    @Override
    public List<E> subList(int n, int n2) {
        synchronized (this) {
            List list = Collections.synchronizedList(super.subList(n, n2), this);
            return list;
        }
    }

    @Override
    public Object[] toArray() {
        synchronized (this) {
            Object[] arrobject = Arrays.copyOf(this.elementData, this.elementCount);
            return arrobject;
        }
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        synchronized (this) {
            block5 : {
                if (arrT.length >= this.elementCount) break block5;
                arrT = Arrays.copyOf(this.elementData, this.elementCount, arrT.getClass());
                return arrT;
            }
            System.arraycopy(this.elementData, 0, arrT, 0, this.elementCount);
            if (arrT.length > this.elementCount) {
                arrT[this.elementCount] = null;
            }
            return arrT;
        }
    }

    @Override
    public String toString() {
        synchronized (this) {
            String string = super.toString();
            return string;
        }
    }

    public void trimToSize() {
        synchronized (this) {
            ++this.modCount;
            int n = this.elementData.length;
            if (this.elementCount < n) {
                this.elementData = Arrays.copyOf(this.elementData, this.elementCount);
            }
            return;
        }
    }

    private class Itr
    implements Iterator<E> {
        int cursor;
        int expectedModCount;
        int lastRet;
        protected int limit;

        private Itr() {
            this.limit = Vector.this.elementCount;
            this.lastRet = -1;
            this.expectedModCount = Vector.this.modCount;
        }

        final void checkForComodification() {
            if (Vector.this.modCount == this.expectedModCount) {
                return;
            }
            throw new ConcurrentModificationException();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void forEachRemaining(Consumer<? super E> object) {
            Objects.requireNonNull(object);
            Vector vector = Vector.this;
            synchronized (vector) {
                int n;
                int n2 = this.limit;
                if (n >= n2) {
                    return;
                }
                Object[] arrobject = Vector.this.elementData;
                if (n >= arrobject.length) {
                    object = new ConcurrentModificationException();
                    throw object;
                }
                for (n = this.cursor; n != n2 && Vector.this.modCount == this.expectedModCount; ++n) {
                    object.accept(arrobject[n]);
                }
                this.cursor = n;
                this.lastRet = n - 1;
                this.checkForComodification();
                return;
            }
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.cursor < this.limit;
            return bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E next() {
            Vector vector = Vector.this;
            synchronized (vector) {
                this.checkForComodification();
                int n = this.cursor;
                if (n < this.limit) {
                    this.cursor = n + 1;
                    Vector vector2 = Vector.this;
                    this.lastRet = n;
                    vector2 = vector2.elementData(n);
                    return (E)vector2;
                }
                NoSuchElementException noSuchElementException = new NoSuchElementException();
                throw noSuchElementException;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void remove() {
            if (this.lastRet != -1) {
                Vector vector = Vector.this;
                synchronized (vector) {
                    this.checkForComodification();
                    Vector.this.remove(this.lastRet);
                    this.expectedModCount = Vector.this.modCount;
                    --this.limit;
                }
                this.cursor = this.lastRet;
                this.lastRet = -1;
                return;
            }
            throw new IllegalStateException();
        }
    }

    final class ListItr
    extends Vector<E>
    implements ListIterator<E> {
        ListItr(int n) {
            this.cursor = n;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void add(E e) {
            int n = this.cursor;
            Vector vector = Vector.this;
            synchronized (vector) {
                this.checkForComodification();
                Vector.this.add(n, e);
                this.expectedModCount = Vector.this.modCount;
                ++this.limit;
            }
            this.cursor = n + 1;
            this.lastRet = -1;
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

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E previous() {
            Vector vector = Vector.this;
            synchronized (vector) {
                this.checkForComodification();
                int n = this.cursor - 1;
                if (n >= 0) {
                    this.cursor = n;
                    Vector vector2 = Vector.this;
                    this.lastRet = n;
                    vector2 = vector2.elementData(n);
                    return (E)vector2;
                }
                NoSuchElementException noSuchElementException = new NoSuchElementException();
                throw noSuchElementException;
            }
        }

        @Override
        public int previousIndex() {
            return this.cursor - 1;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void set(E e) {
            if (this.lastRet != -1) {
                Vector vector = Vector.this;
                synchronized (vector) {
                    this.checkForComodification();
                    Vector.this.set(this.lastRet, e);
                    return;
                }
            }
            throw new IllegalStateException();
        }
    }

    static final class VectorSpliterator<E>
    implements Spliterator<E> {
        private Object[] array;
        private int expectedModCount;
        private int fence;
        private int index;
        private final Vector<E> list;

        VectorSpliterator(Vector<E> vector, Object[] arrobject, int n, int n2, int n3) {
            this.list = vector;
            this.array = arrobject;
            this.index = n;
            this.fence = n2;
            this.expectedModCount = n3;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private int getFence() {
            int n;
            int n2 = n = this.fence;
            if (n >= 0) return n2;
            Vector<E> vector = this.list;
            synchronized (vector) {
                this.array = this.list.elementData;
                this.expectedModCount = this.list.modCount;
                this.fence = n2 = this.list.elementCount;
                return n2;
            }
        }

        @Override
        public int characteristics() {
            return 16464;
        }

        @Override
        public long estimateSize() {
            return this.getFence() - this.index;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            if (consumer == null) {
                throw new NullPointerException();
            }
            Vector<E> vector = this.list;
            if (vector != null) {
                int n;
                Object[] arrobject;
                int n2 = n = this.fence;
                if (n < 0) {
                    synchronized (vector) {
                        this.expectedModCount = vector.modCount;
                        this.array = arrobject = vector.elementData;
                        this.fence = n2 = vector.elementCount;
                    }
                } else {
                    arrobject = this.array;
                }
                if (arrobject != null) {
                    int n3;
                    if (n3 >= 0) {
                        this.index = n2;
                        if (n2 <= arrobject.length) {
                            for (n = n3 = this.index; n < n2; ++n) {
                                consumer.accept(arrobject[n]);
                            }
                            if (vector.modCount == this.expectedModCount) {
                                return;
                            }
                        }
                    }
                }
            }
            throw new ConcurrentModificationException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> consumer) {
            if (consumer != null) {
                int n;
                int n2 = this.getFence();
                if (n2 > (n = this.index)) {
                    this.index = n + 1;
                    consumer.accept(this.array[n]);
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
            Object object;
            int n = this.getFence();
            int n2 = this.index;
            if (n2 >= (n = n2 + n >>> 1)) {
                object = null;
            } else {
                Vector<E> vector = this.list;
                object = this.array;
                this.index = n;
                object = new VectorSpliterator<E>(vector, (Object[])object, n2, n, this.expectedModCount);
            }
            return object;
        }
    }

}

