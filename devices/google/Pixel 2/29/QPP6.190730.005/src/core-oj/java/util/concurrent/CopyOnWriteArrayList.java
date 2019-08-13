/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.Arrays;
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
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import sun.misc.Unsafe;

public class CopyOnWriteArrayList<E>
implements List<E>,
RandomAccess,
Cloneable,
Serializable {
    private static final long LOCK;
    private static final Unsafe U;
    private static final long serialVersionUID = 8673264195747942595L;
    private volatile transient Object[] array;
    final transient Object lock = new Object();

    static {
        U = Unsafe.getUnsafe();
        try {
            LOCK = U.objectFieldOffset(CopyOnWriteArrayList.class.getDeclaredField("lock"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public CopyOnWriteArrayList() {
        this.setArray(new Object[0]);
    }

    public CopyOnWriteArrayList(Collection<? extends E> arrobject) {
        if (arrobject.getClass() == CopyOnWriteArrayList.class) {
            arrobject = ((CopyOnWriteArrayList)arrobject).getArray();
        } else {
            Object[] arrobject2 = arrobject.toArray();
            arrobject = arrobject2;
            if (arrobject2.getClass() != Object[].class) {
                arrobject = Arrays.copyOf(arrobject2, arrobject2.length, Object[].class);
            }
        }
        this.setArray(arrobject);
    }

    public CopyOnWriteArrayList(E[] arrE) {
        this.setArray(Arrays.copyOf(arrE, arrE.length, Object[].class));
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean addIfAbsent(E e, Object[] arrobject) {
        Object object = this.lock;
        synchronized (object) {
            Object[] arrobject2 = this.getArray();
            int n = arrobject2.length;
            if (arrobject != arrobject2) {
                int n2 = Math.min(arrobject.length, n);
                for (int i = 0; i < n2; ++i) {
                    if (arrobject2[i] == arrobject[i] || !Objects.equals(e, arrobject2[i])) continue;
                    return false;
                }
                if (CopyOnWriteArrayList.indexOf(e, arrobject2, n2, n) >= 0) {
                    return false;
                }
            }
            arrobject = Arrays.copyOf(arrobject2, n + 1);
            arrobject[n] = e;
            this.setArray(arrobject);
            return true;
        }
    }

    private E get(Object[] arrobject, int n) {
        return (E)arrobject[n];
    }

    private static int indexOf(Object object, Object[] arrobject, int n, int n2) {
        if (object == null) {
            while (n < n2) {
                if (arrobject[n] == null) {
                    return n;
                }
                ++n;
            }
        } else {
            while (n < n2) {
                if (object.equals(arrobject[n])) {
                    return n;
                }
                ++n;
            }
        }
        return -1;
    }

    private static int lastIndexOf(Object object, Object[] arrobject, int n) {
        if (object == null) {
            while (n >= 0) {
                if (arrobject[n] == null) {
                    return n;
                }
                --n;
            }
        } else {
            while (n >= 0) {
                if (object.equals(arrobject[n])) {
                    return n;
                }
                --n;
            }
        }
        return -1;
    }

    static String outOfBounds(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index: ");
        stringBuilder.append(n);
        stringBuilder.append(", Size: ");
        stringBuilder.append(n2);
        return stringBuilder.toString();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.resetLock();
        int n = objectInputStream.readInt();
        Object[] arrobject = new Object[n];
        for (int i = 0; i < n; ++i) {
            arrobject[i] = objectInputStream.readObject();
        }
        this.setArray(arrobject);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean remove(Object arrobject, Object[] arrobject2, int n) {
        Object object = this.lock;
        synchronized (object) {
            int n2;
            Object[] arrobject3;
            int n3;
            block13 : {
                arrobject3 = this.getArray();
                n2 = arrobject3.length;
                n3 = n;
                if (arrobject2 != arrobject3) {
                    int n4 = Math.min(n, n2);
                    for (n3 = 0; n3 < n4; ++n3) {
                        if (arrobject3[n3] == arrobject2[n3] || !Objects.equals(arrobject, arrobject3[n3])) {
                            continue;
                        }
                        break block13;
                    }
                    if (n >= n2) {
                        return false;
                    }
                    if (arrobject3[n] == arrobject) {
                        n3 = n;
                    } else {
                        n3 = n = CopyOnWriteArrayList.indexOf(arrobject, arrobject3, n, n2);
                        if (n < 0) {
                            return false;
                        }
                    }
                }
            }
            arrobject = new Object[n2 - 1];
            System.arraycopy(arrobject3, 0, arrobject, 0, n3);
            System.arraycopy(arrobject3, n3 + 1, arrobject, n3, n2 - n3 - 1);
            this.setArray(arrobject);
            return true;
        }
    }

    private void resetLock() {
        U.putObjectVolatile(this, LOCK, new Object());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Object[] arrobject = this.getArray();
        objectOutputStream.writeInt(arrobject.length);
        int n = arrobject.length;
        for (int i = 0; i < n; ++i) {
            objectOutputStream.writeObject(arrobject[i]);
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void add(int n, E object) {
        Object object2 = this.lock;
        synchronized (object2) {
            Object[] arrobject = this.getArray();
            int n2 = arrobject.length;
            if (n <= n2 && n >= 0) {
                Object[] arrobject2;
                int n3 = n2 - n;
                if (n3 == 0) {
                    arrobject2 = Arrays.copyOf(arrobject, n2 + 1);
                } else {
                    arrobject2 = new Object[n2 + 1];
                    System.arraycopy(arrobject, 0, arrobject2, 0, n);
                    System.arraycopy(arrobject, n, arrobject2, n + 1, n3);
                }
                arrobject2[n] = object;
                this.setArray(arrobject2);
                return;
            }
            object = new Object(CopyOnWriteArrayList.outOfBounds(n, n2));
            throw object;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean add(E e) {
        Object object = this.lock;
        synchronized (object) {
            Object[] arrobject = this.getArray();
            int n = arrobject.length;
            arrobject = Arrays.copyOf(arrobject, n + 1);
            arrobject[n] = e;
            this.setArray(arrobject);
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean addAll(int n, Collection<? extends E> arrobject) {
        Object[] arrobject2 = arrobject.toArray();
        Object object = this.lock;
        synchronized (object) {
            Object[] arrobject3 = this.getArray();
            int n2 = arrobject3.length;
            if (n <= n2 && n >= 0) {
                if (arrobject2.length == 0) {
                    return false;
                }
                int n3 = n2 - n;
                if (n3 == 0) {
                    arrobject = Arrays.copyOf(arrobject3, arrobject2.length + n2);
                } else {
                    arrobject = new Object[arrobject2.length + n2];
                    System.arraycopy(arrobject3, 0, arrobject, 0, n);
                    System.arraycopy(arrobject3, n, arrobject, arrobject2.length + n, n3);
                }
                System.arraycopy(arrobject2, 0, arrobject, n, arrobject2.length);
                this.setArray(arrobject);
                return true;
            }
            arrobject = new IndexOutOfBoundsException(CopyOnWriteArrayList.outOfBounds(n, n2));
            throw arrobject;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean addAll(Collection<? extends E> arrobject) {
        if ((arrobject = arrobject.getClass() == CopyOnWriteArrayList.class ? ((CopyOnWriteArrayList)arrobject).getArray() : arrobject.toArray()).length == 0) {
            return false;
        }
        Object object = this.lock;
        synchronized (object) {
            Object[] arrobject2 = this.getArray();
            int n = arrobject2.length;
            if (n == 0 && arrobject.getClass() == Object[].class) {
                this.setArray(arrobject);
            } else {
                arrobject2 = Arrays.copyOf(arrobject2, arrobject.length + n);
                System.arraycopy(arrobject, 0, arrobject2, n, arrobject.length);
                this.setArray(arrobject2);
            }
            return true;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int addAllAbsent(Collection<? extends E> object) {
        Object[] arrobject = object.toArray();
        if (arrobject.length == 0) {
            return 0;
        }
        object = this.lock;
        synchronized (object) {
            Object[] arrobject2 = this.getArray();
            int n = arrobject2.length;
            int n2 = 0;
            for (int i = 0; i < arrobject.length; ++i) {
                Object object2 = arrobject[i];
                int n3 = n2;
                if (CopyOnWriteArrayList.indexOf(object2, arrobject2, 0, n) < 0) {
                    n3 = n2;
                    if (CopyOnWriteArrayList.indexOf(object2, arrobject, 0, n2) < 0) {
                        arrobject[n2] = object2;
                        n3 = n2 + 1;
                    }
                }
                n2 = n3;
            }
            if (n2 > 0) {
                arrobject2 = Arrays.copyOf(arrobject2, n + n2);
                System.arraycopy(arrobject, 0, arrobject2, n, n2);
                this.setArray(arrobject2);
            }
            return n2;
        }
    }

    public boolean addIfAbsent(E e) {
        Object[] arrobject = this.getArray();
        int n = arrobject.length;
        boolean bl = false;
        if (CopyOnWriteArrayList.indexOf(e, arrobject, 0, n) < 0) {
            bl = this.addIfAbsent(e, arrobject);
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void clear() {
        Object object = this.lock;
        synchronized (object) {
            this.setArray(new Object[0]);
            return;
        }
    }

    public Object clone() {
        try {
            CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList)super.clone();
            copyOnWriteArrayList.resetLock();
            return copyOnWriteArrayList;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
    }

    @Override
    public boolean contains(Object object) {
        Object[] arrobject = this.getArray();
        int n = arrobject.length;
        boolean bl = false;
        if (CopyOnWriteArrayList.indexOf(object, arrobject, 0, n) >= 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean containsAll(Collection<?> object) {
        Object[] arrobject = this.getArray();
        int n = arrobject.length;
        object = object.iterator();
        while (object.hasNext()) {
            if (CopyOnWriteArrayList.indexOf(object.next(), arrobject, 0, n) >= 0) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object arrobject) {
        if (arrobject == this) {
            return true;
        }
        if (!(arrobject instanceof List)) {
            return false;
        }
        Iterator iterator = ((List)arrobject).iterator();
        arrobject = this.getArray();
        int n = arrobject.length;
        for (int i = 0; i < n; ++i) {
            if (iterator.hasNext() && Objects.equals(arrobject[i], iterator.next())) {
                continue;
            }
            return false;
        }
        return !iterator.hasNext();
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        if (consumer != null) {
            Object[] arrobject = this.getArray();
            int n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                consumer.accept(arrobject[i]);
            }
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public E get(int n) {
        return this.get(this.getArray(), n);
    }

    final Object[] getArray() {
        return this.array;
    }

    @Override
    public int hashCode() {
        Object[] arrobject = this.getArray();
        int n = arrobject.length;
        int n2 = 1;
        for (int i = 0; i < n; ++i) {
            Object object = arrobject[i];
            int n3 = object == null ? 0 : object.hashCode();
            n2 = n2 * 31 + n3;
        }
        return n2;
    }

    @Override
    public int indexOf(Object object) {
        Object[] arrobject = this.getArray();
        return CopyOnWriteArrayList.indexOf(object, arrobject, 0, arrobject.length);
    }

    public int indexOf(E e, int n) {
        Object[] arrobject = this.getArray();
        return CopyOnWriteArrayList.indexOf(e, arrobject, n, arrobject.length);
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.size() == 0;
        return bl;
    }

    @Override
    public Iterator<E> iterator() {
        return new COWIterator(this.getArray(), 0);
    }

    @Override
    public int lastIndexOf(Object object) {
        Object[] arrobject = this.getArray();
        return CopyOnWriteArrayList.lastIndexOf(object, arrobject, arrobject.length - 1);
    }

    public int lastIndexOf(E e, int n) {
        return CopyOnWriteArrayList.lastIndexOf(e, this.getArray(), n);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new COWIterator(this.getArray(), 0);
    }

    @Override
    public ListIterator<E> listIterator(int n) {
        Object[] arrobject = this.getArray();
        int n2 = arrobject.length;
        if (n >= 0 && n <= n2) {
            return new COWIterator(arrobject, n);
        }
        throw new IndexOutOfBoundsException(CopyOnWriteArrayList.outOfBounds(n, n2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public E remove(int n) {
        Object object = this.lock;
        synchronized (object) {
            Object[] arrobject = this.getArray();
            int n2 = arrobject.length;
            E e = this.get(arrobject, n);
            int n3 = n2 - n - 1;
            if (n3 == 0) {
                this.setArray(Arrays.copyOf(arrobject, n2 - 1));
            } else {
                Object[] arrobject2 = new Object[n2 - 1];
                System.arraycopy(arrobject, 0, arrobject2, 0, n);
                System.arraycopy(arrobject, n + 1, arrobject2, n, n3);
                this.setArray(arrobject2);
            }
            return e;
        }
    }

    @Override
    public boolean remove(Object object) {
        Object[] arrobject = this.getArray();
        int n = arrobject.length;
        boolean bl = false;
        if ((n = CopyOnWriteArrayList.indexOf(object, arrobject, 0, n)) >= 0) {
            bl = this.remove(object, arrobject, n);
        }
        return bl;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
        Object object = this.lock;
        synchronized (object) {
            Object[] arrobject = this.getArray();
            int n = arrobject.length;
            if (n != 0) {
                int n2 = 0;
                Object[] arrobject2 = new Object[n];
                for (int i = 0; i < n; ++i) {
                    Object object2 = arrobject[i];
                    int n3 = n2;
                    if (!collection.contains(object2)) {
                        arrobject2[n2] = object2;
                        n3 = n2 + 1;
                    }
                    n2 = n3;
                }
                if (n2 != n) {
                    this.setArray(Arrays.copyOf(arrobject2, n2));
                    return true;
                }
            }
            return false;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean removeIf(Predicate<? super E> arrobject) {
        if (arrobject == null) {
            throw new NullPointerException();
        }
        Object object = this.lock;
        synchronized (object) {
            Object[] arrobject2 = this.getArray();
            int n = arrobject2.length;
            int n2 = 0;
            while (n2 < n) {
                if (arrobject.test(arrobject2[n2])) {
                    int n3 = n2;
                    Object[] arrobject3 = new Object[n - 1];
                    System.arraycopy(arrobject2, 0, arrobject3, 0, n3);
                    for (int i = n2 + 1; i < n; ++i) {
                        Object object2 = arrobject2[i];
                        n2 = n3;
                        if (!arrobject.test(object2)) {
                            arrobject3[n3] = object2;
                            n2 = n3 + 1;
                        }
                        n3 = n2;
                    }
                    arrobject = n3 == n - 1 ? arrobject3 : Arrays.copyOf(arrobject3, n3);
                    this.setArray(arrobject);
                    return true;
                }
                ++n2;
            }
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void removeRange(int n, int n2) {
        Object object = this.lock;
        synchronized (object) {
            Object object2 = this.getArray();
            int n3 = ((Object[])object2).length;
            if (n >= 0 && n2 <= n3 && n2 >= n) {
                int n4 = n3 - (n2 - n);
                if ((n3 -= n2) == 0) {
                    this.setArray(Arrays.copyOf(object2, n4));
                } else {
                    Object[] arrobject = new Object[n4];
                    System.arraycopy(object2, 0, arrobject, 0, n);
                    System.arraycopy(object2, n2, arrobject, n, n3);
                    this.setArray(arrobject);
                }
                return;
            }
            object2 = new IndexOutOfBoundsException();
            throw object2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void replaceAll(UnaryOperator<E> unaryOperator) {
        if (unaryOperator == null) {
            throw new NullPointerException();
        }
        Object object = this.lock;
        synchronized (object) {
            Object[] arrobject = this.getArray();
            int n = arrobject.length;
            Object[] arrobject2 = Arrays.copyOf(arrobject, n);
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.setArray(arrobject2);
                    return;
                }
                arrobject2[n2] = unaryOperator.apply(arrobject[n2]);
                ++n2;
            } while (true);
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
        Object object = this.lock;
        synchronized (object) {
            Object[] arrobject = this.getArray();
            int n = arrobject.length;
            if (n != 0) {
                int n2 = 0;
                Object[] arrobject2 = new Object[n];
                for (int i = 0; i < n; ++i) {
                    Object object2 = arrobject[i];
                    int n3 = n2;
                    if (collection.contains(object2)) {
                        arrobject2[n2] = object2;
                        n3 = n2 + 1;
                    }
                    n2 = n3;
                }
                if (n2 != n) {
                    this.setArray(Arrays.copyOf(arrobject2, n2));
                    return true;
                }
            }
            return false;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public E set(int n, E e) {
        Object object = this.lock;
        synchronized (object) {
            Object[] arrobject = this.getArray();
            E e2 = this.get(arrobject, n);
            if (e2 != e) {
                arrobject = Arrays.copyOf(arrobject, arrobject.length);
                arrobject[n] = e;
                this.setArray(arrobject);
            } else {
                this.setArray(arrobject);
            }
            return e2;
        }
    }

    final void setArray(Object[] arrobject) {
        this.array = arrobject;
    }

    @Override
    public int size() {
        return this.getArray().length;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void sort(Comparator<? super E> comparator) {
        Object object = this.lock;
        synchronized (object) {
            Object[] arrobject = this.getArray();
            arrobject = Arrays.copyOf(arrobject, arrobject.length);
            Arrays.sort(arrobject, comparator);
            this.setArray(arrobject);
            return;
        }
    }

    @Override
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.getArray(), 1040);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<E> subList(int n, int n2) {
        Object object = this.lock;
        synchronized (object) {
            int n3 = this.getArray().length;
            if (n >= 0 && n2 <= n3 && n <= n2) {
                return new COWSubList(this, n, n2);
            }
            IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException();
            throw indexOutOfBoundsException;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] arrobject = this.getArray();
        return Arrays.copyOf(arrobject, arrobject.length);
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        Object[] arrobject = this.getArray();
        int n = arrobject.length;
        if (arrT.length < n) {
            return Arrays.copyOf(arrobject, n, arrT.getClass());
        }
        System.arraycopy(arrobject, 0, arrT, 0, n);
        if (arrT.length > n) {
            arrT[n] = null;
        }
        return arrT;
    }

    public String toString() {
        return Arrays.toString(this.getArray());
    }

    static final class COWIterator<E>
    implements ListIterator<E> {
        private int cursor;
        private final Object[] snapshot;

        COWIterator(Object[] arrobject, int n) {
            this.cursor = n;
            this.snapshot = arrobject;
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            int n = this.snapshot.length;
            for (int i = this.cursor; i < n; ++i) {
                consumer.accept(this.snapshot[i]);
            }
            this.cursor = n;
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.cursor < this.snapshot.length;
            return bl;
        }

        @Override
        public boolean hasPrevious() {
            boolean bl = this.cursor > 0;
            return bl;
        }

        @Override
        public E next() {
            if (this.hasNext()) {
                Object[] arrobject = this.snapshot;
                int n = this.cursor;
                this.cursor = n + 1;
                return (E)arrobject[n];
            }
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return this.cursor;
        }

        @Override
        public E previous() {
            if (this.hasPrevious()) {
                int n;
                Object[] arrobject = this.snapshot;
                this.cursor = n = this.cursor - 1;
                return (E)arrobject[n];
            }
            throw new NoSuchElementException();
        }

        @Override
        public int previousIndex() {
            return this.cursor - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }
    }

    private static class COWSubList<E>
    extends AbstractList<E>
    implements RandomAccess {
        private Object[] expectedArray;
        private final CopyOnWriteArrayList<E> l;
        private final int offset;
        private int size;

        COWSubList(CopyOnWriteArrayList<E> copyOnWriteArrayList, int n, int n2) {
            this.l = copyOnWriteArrayList;
            this.expectedArray = this.l.getArray();
            this.offset = n;
            this.size = n2 - n;
        }

        private void checkForComodification() {
            if (this.l.getArray() == this.expectedArray) {
                return;
            }
            throw new ConcurrentModificationException();
        }

        private void rangeCheck(int n) {
            if (n >= 0 && n < this.size) {
                return;
            }
            throw new IndexOutOfBoundsException(CopyOnWriteArrayList.outOfBounds(n, this.size));
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void add(int n, E object) {
            Object object2 = this.l.lock;
            synchronized (object2) {
                this.checkForComodification();
                if (n >= 0 && n <= this.size) {
                    this.l.add(this.offset + n, object);
                    this.expectedArray = this.l.getArray();
                    ++this.size;
                    return;
                }
                object = new Object(CopyOnWriteArrayList.outOfBounds(n, this.size));
                throw object;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void clear() {
            Object object = this.l.lock;
            synchronized (object) {
                this.checkForComodification();
                this.l.removeRange(this.offset, this.offset + this.size);
                this.expectedArray = this.l.getArray();
                this.size = 0;
                return;
            }
        }

        @Override
        public void forEach(Consumer<? super E> consumer) {
            if (consumer != null) {
                int n = this.offset + this.size;
                Object[] arrobject = this.expectedArray;
                if (this.l.getArray() == arrobject) {
                    int n2;
                    if (n2 >= 0 && n <= arrobject.length) {
                        for (n2 = this.offset; n2 < n; ++n2) {
                            consumer.accept(arrobject[n2]);
                        }
                        return;
                    }
                    throw new IndexOutOfBoundsException();
                }
                throw new ConcurrentModificationException();
            }
            throw new NullPointerException();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E get(int n) {
            Object object = this.l.lock;
            synchronized (object) {
                this.rangeCheck(n);
                this.checkForComodification();
                E e = this.l.get(this.offset + n);
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public Iterator<E> iterator() {
            Object object = this.l.lock;
            synchronized (object) {
                this.checkForComodification();
                return new COWSubListIterator<E>(this.l, 0, this.offset, this.size);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public ListIterator<E> listIterator(int n) {
            Object object = this.l.lock;
            synchronized (object) {
                this.checkForComodification();
                if (n >= 0 && n <= this.size) {
                    return new COWSubListIterator<E>(this.l, n, this.offset, this.size);
                }
                IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException(CopyOnWriteArrayList.outOfBounds(n, this.size));
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
            Object object = this.l.lock;
            synchronized (object) {
                this.rangeCheck(n);
                this.checkForComodification();
                E e = this.l.remove(this.offset + n);
                this.expectedArray = this.l.getArray();
                --this.size;
                return e;
            }
        }

        @Override
        public boolean remove(Object object) {
            int n = this.indexOf(object);
            if (n == -1) {
                return false;
            }
            this.remove(n);
            return true;
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean removeAll(Collection<?> arrobject) {
            if (arrobject == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            Object object = this.l.lock;
            synchronized (object) {
                int n = this.size;
                boolean bl2 = bl;
                if (n > 0) {
                    int n2 = this.offset;
                    int n3 = this.offset + n;
                    Object[] arrobject2 = this.expectedArray;
                    if (this.l.getArray() != arrobject2) {
                        arrobject = new ConcurrentModificationException();
                        throw arrobject;
                    }
                    int n4 = arrobject2.length;
                    if (n2 >= 0 && n3 <= n4) {
                        int n5 = 0;
                        Object object2 = new Object[n];
                        for (int i = n2; i < n3; ++i) {
                            Object object3 = arrobject2[i];
                            int n6 = n5;
                            if (!arrobject.contains(object3)) {
                                object2[n5] = object3;
                                n6 = n5 + 1;
                            }
                            n5 = n6;
                        }
                        bl2 = bl;
                        if (n5 != n) {
                            arrobject = new Object[n4 - n + n5];
                            System.arraycopy(arrobject2, 0, arrobject, 0, n2);
                            System.arraycopy(object2, 0, arrobject, n2, n5);
                            System.arraycopy(arrobject2, n3, arrobject, n2 + n5, n4 - n3);
                            this.size = n5;
                            bl2 = true;
                            object2 = this.l;
                            this.expectedArray = arrobject;
                            ((CopyOnWriteArrayList)object2).setArray(arrobject);
                        }
                    } else {
                        arrobject = new IndexOutOfBoundsException();
                        throw arrobject;
                    }
                }
                return bl2;
            }
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean removeIf(Predicate<? super E> arrobject) {
            if (arrobject == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            Object object = this.l.lock;
            synchronized (object) {
                int n = this.size;
                boolean bl2 = bl;
                if (n > 0) {
                    int n2 = this.offset;
                    int n3 = this.offset + n;
                    Object object2 = this.expectedArray;
                    if (this.l.getArray() != object2) {
                        arrobject = new ConcurrentModificationException();
                        throw arrobject;
                    }
                    int n4 = ((Object[])object2).length;
                    if (n2 >= 0 && n3 <= n4) {
                        int n5 = 0;
                        Object[] arrobject2 = new Object[n];
                        for (int i = n2; i < n3; ++i) {
                            Object object3 = object2[i];
                            int n6 = n5;
                            if (!arrobject.test(object3)) {
                                arrobject2[n5] = object3;
                                n6 = n5 + 1;
                            }
                            n5 = n6;
                        }
                        bl2 = bl;
                        if (n5 != n) {
                            arrobject = new Object[n4 - n + n5];
                            System.arraycopy(object2, 0, arrobject, 0, n2);
                            System.arraycopy(arrobject2, 0, arrobject, n2, n5);
                            System.arraycopy(object2, n3, arrobject, n2 + n5, n4 - n3);
                            this.size = n5;
                            bl2 = true;
                            object2 = this.l;
                            this.expectedArray = arrobject;
                            ((CopyOnWriteArrayList)object2).setArray(arrobject);
                        }
                    } else {
                        arrobject = new IndexOutOfBoundsException();
                        throw arrobject;
                    }
                }
                return bl2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void replaceAll(UnaryOperator<E> copyOnWriteArrayList) {
            if (copyOnWriteArrayList == null) {
                throw new NullPointerException();
            }
            Object object = this.l.lock;
            synchronized (object) {
                int n = this.offset;
                int n2 = this.offset + this.size;
                Object[] arrobject = this.expectedArray;
                if (this.l.getArray() != arrobject) {
                    copyOnWriteArrayList = new CopyOnWriteArrayList<E>();
                    throw copyOnWriteArrayList;
                }
                int n3 = arrobject.length;
                if (n >= 0 && n2 <= n3) {
                    Object[] arrobject2 = Arrays.copyOf(arrobject, n3);
                    do {
                        if (n >= n2) {
                            copyOnWriteArrayList = this.l;
                            this.expectedArray = arrobject2;
                            copyOnWriteArrayList.setArray(arrobject2);
                            return;
                        }
                        arrobject2[n] = copyOnWriteArrayList.apply(arrobject[n]);
                        ++n;
                    } while (true);
                }
                copyOnWriteArrayList = new CopyOnWriteArrayList<E>();
                throw copyOnWriteArrayList;
            }
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean retainAll(Collection<?> arrobject) {
            if (arrobject == null) {
                throw new NullPointerException();
            }
            boolean bl = false;
            Object object = this.l.lock;
            synchronized (object) {
                int n = this.size;
                boolean bl2 = bl;
                if (n > 0) {
                    int n2 = this.offset;
                    int n3 = this.offset + n;
                    Object object2 = this.expectedArray;
                    if (this.l.getArray() != object2) {
                        arrobject = new ConcurrentModificationException();
                        throw arrobject;
                    }
                    int n4 = ((Object[])object2).length;
                    if (n2 >= 0 && n3 <= n4) {
                        int n5 = 0;
                        Object[] arrobject2 = new Object[n];
                        for (int i = n2; i < n3; ++i) {
                            Object object3 = object2[i];
                            int n6 = n5;
                            if (arrobject.contains(object3)) {
                                arrobject2[n5] = object3;
                                n6 = n5 + 1;
                            }
                            n5 = n6;
                        }
                        bl2 = bl;
                        if (n5 != n) {
                            arrobject = new Object[n4 - n + n5];
                            System.arraycopy(object2, 0, arrobject, 0, n2);
                            System.arraycopy(arrobject2, 0, arrobject, n2, n5);
                            System.arraycopy(object2, n3, arrobject, n2 + n5, n4 - n3);
                            this.size = n5;
                            bl2 = true;
                            object2 = this.l;
                            this.expectedArray = arrobject;
                            ((CopyOnWriteArrayList)object2).setArray(arrobject);
                        }
                    } else {
                        arrobject = new IndexOutOfBoundsException();
                        throw arrobject;
                    }
                }
                return bl2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public E set(int n, E e) {
            Object object = this.l.lock;
            synchronized (object) {
                this.rangeCheck(n);
                this.checkForComodification();
                e = this.l.set(this.offset + n, e);
                this.expectedArray = this.l.getArray();
                return e;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public int size() {
            Object object = this.l.lock;
            synchronized (object) {
                this.checkForComodification();
                return this.size;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void sort(Comparator<? super E> copyOnWriteArrayList) {
            Object object = this.l.lock;
            synchronized (object) {
                int n = this.offset;
                int n2 = this.offset + this.size;
                Object[] arrobject = this.expectedArray;
                if (this.l.getArray() != arrobject) {
                    copyOnWriteArrayList = new CopyOnWriteArrayList<E>();
                    throw copyOnWriteArrayList;
                }
                int n3 = arrobject.length;
                if (n >= 0 && n2 <= n3) {
                    arrobject = Arrays.copyOf(arrobject, n3);
                    Arrays.sort(arrobject, n, n2, copyOnWriteArrayList);
                    copyOnWriteArrayList = this.l;
                    this.expectedArray = arrobject;
                    copyOnWriteArrayList.setArray(arrobject);
                    return;
                }
                copyOnWriteArrayList = new CopyOnWriteArrayList<E>();
                throw copyOnWriteArrayList;
            }
        }

        @Override
        public Spliterator<E> spliterator() {
            int n = this.offset;
            int n2 = this.offset + this.size;
            Object[] arrobject = this.expectedArray;
            if (this.l.getArray() == arrobject) {
                if (n >= 0 && n2 <= arrobject.length) {
                    return Spliterators.spliterator(arrobject, n, n2, 1040);
                }
                throw new IndexOutOfBoundsException();
            }
            throw new ConcurrentModificationException();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public List<E> subList(int n, int n2) {
            Object object = this.l.lock;
            synchronized (object) {
                this.checkForComodification();
                if (n >= 0 && n2 <= this.size && n <= n2) {
                    return new COWSubList<E>(this.l, this.offset + n, this.offset + n2);
                }
                IndexOutOfBoundsException indexOutOfBoundsException = new IndexOutOfBoundsException();
                throw indexOutOfBoundsException;
            }
        }
    }

    private static class COWSubListIterator<E>
    implements ListIterator<E> {
        private final ListIterator<E> it;
        private final int offset;
        private final int size;

        COWSubListIterator(List<E> list, int n, int n2, int n3) {
            this.offset = n2;
            this.size = n3;
            this.it = list.listIterator(n + n2);
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            while (this.nextIndex() < this.size) {
                consumer.accept(this.it.next());
            }
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.nextIndex() < this.size;
            return bl;
        }

        @Override
        public boolean hasPrevious() {
            boolean bl = this.previousIndex() >= 0;
            return bl;
        }

        @Override
        public E next() {
            if (this.hasNext()) {
                return this.it.next();
            }
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return this.it.nextIndex() - this.offset;
        }

        @Override
        public E previous() {
            if (this.hasPrevious()) {
                return this.it.previous();
            }
            throw new NoSuchElementException();
        }

        @Override
        public int previousIndex() {
            return this.it.previousIndex() - this.offset;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }
    }

}

