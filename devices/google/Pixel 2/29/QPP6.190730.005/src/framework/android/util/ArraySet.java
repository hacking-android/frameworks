/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.util.ContainerHelpers;
import android.util.MapCollections;
import android.util.Slog;
import android.util.UtilConfig;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import libcore.util.EmptyArray;

public final class ArraySet<E>
implements Collection<E>,
Set<E> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArraySet";
    static Object[] sBaseCache;
    static int sBaseCacheSize;
    static Object[] sTwiceBaseCache;
    static int sTwiceBaseCacheSize;
    @UnsupportedAppUsage(maxTargetSdk=28)
    Object[] mArray;
    MapCollections<E, E> mCollections;
    @UnsupportedAppUsage(maxTargetSdk=28)
    int[] mHashes;
    final boolean mIdentityHashCode;
    @UnsupportedAppUsage(maxTargetSdk=28)
    int mSize;

    public ArraySet() {
        this(0, false);
    }

    public ArraySet(int n) {
        this(n, false);
    }

    public ArraySet(int n, boolean bl) {
        this.mIdentityHashCode = bl;
        if (n == 0) {
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
        } else {
            this.allocArrays(n);
        }
        this.mSize = 0;
    }

    public ArraySet(ArraySet<E> arraySet) {
        this();
        if (arraySet != null) {
            this.addAll(arraySet);
        }
    }

    public ArraySet(Collection<? extends E> collection) {
        this();
        if (collection != null) {
            this.addAll(collection);
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage(maxTargetSdk=28)
    private void allocArrays(int n) {
        if (n == 8) {
            synchronized (ArraySet.class) {
                if (sTwiceBaseCache != null) {
                    Object[] arrobject = sTwiceBaseCache;
                    try {
                        this.mArray = arrobject;
                        sTwiceBaseCache = (Object[])arrobject[0];
                        this.mHashes = (int[])arrobject[1];
                        arrobject[1] = null;
                        arrobject[0] = null;
                        --sTwiceBaseCacheSize;
                    }
                    catch (ClassCastException classCastException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Found corrupt ArraySet cache: [0]=");
                        stringBuilder.append(arrobject[0]);
                        stringBuilder.append(" [1]=");
                        stringBuilder.append(arrobject[1]);
                        Slog.wtf(TAG, stringBuilder.toString());
                        sTwiceBaseCache = null;
                        sTwiceBaseCacheSize = 0;
                    }
                    return;
                }
            }
        } else if (n == 4) {
            synchronized (ArraySet.class) {
                if (sBaseCache != null) {
                    Object[] arrobject = sBaseCache;
                    try {
                        this.mArray = arrobject;
                        sBaseCache = (Object[])arrobject[0];
                        this.mHashes = (int[])arrobject[1];
                        arrobject[1] = null;
                        arrobject[0] = null;
                        --sBaseCacheSize;
                    }
                    catch (ClassCastException classCastException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Found corrupt ArraySet cache: [0]=");
                        stringBuilder.append(arrobject[0]);
                        stringBuilder.append(" [1]=");
                        stringBuilder.append(arrobject[1]);
                        Slog.wtf(TAG, stringBuilder.toString());
                        sBaseCache = null;
                        sBaseCacheSize = 0;
                    }
                    return;
                }
            }
        }
        this.mHashes = new int[n];
        this.mArray = new Object[n];
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage(maxTargetSdk=28)
    private static void freeArrays(int[] arrn, Object[] arrobject, int n) {
        if (arrn.length == 8) {
            synchronized (ArraySet.class) {
                if (sTwiceBaseCacheSize >= 10) return;
                arrobject[0] = sTwiceBaseCache;
                arrobject[1] = arrn;
                --n;
                do {
                    if (n < 2) {
                        sTwiceBaseCache = arrobject;
                        ++sTwiceBaseCacheSize;
                        return;
                    }
                    arrobject[n] = null;
                    --n;
                } while (true);
            }
        }
        if (arrn.length != 4) return;
        synchronized (ArraySet.class) {
            if (sBaseCacheSize >= 10) return;
            arrobject[0] = sBaseCache;
            arrobject[1] = arrn;
            --n;
            do {
                if (n < 2) {
                    sBaseCache = arrobject;
                    ++sBaseCacheSize;
                    return;
                }
                arrobject[n] = null;
                --n;
            } while (true);
        }
    }

    private MapCollections<E, E> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new MapCollections<E, E>(){

                @Override
                protected void colClear() {
                    ArraySet.this.clear();
                }

                @Override
                protected Object colGetEntry(int n, int n2) {
                    return ArraySet.this.mArray[n];
                }

                @Override
                protected Map<E, E> colGetMap() {
                    throw new UnsupportedOperationException("not a map");
                }

                @Override
                protected int colGetSize() {
                    return ArraySet.this.mSize;
                }

                @Override
                protected int colIndexOfKey(Object object) {
                    return ArraySet.this.indexOf(object);
                }

                @Override
                protected int colIndexOfValue(Object object) {
                    return ArraySet.this.indexOf(object);
                }

                @Override
                protected void colPut(E e, E e2) {
                    ArraySet.this.add(e);
                }

                @Override
                protected void colRemoveAt(int n) {
                    ArraySet.this.removeAt(n);
                }

                @Override
                protected E colSetValue(int n, E e) {
                    throw new UnsupportedOperationException("not a map");
                }
            };
        }
        return this.mCollections;
    }

    private int getNewShrunkenSize() {
        int n = this.mSize;
        int n2 = 8;
        if (n > 8) {
            n2 = (n >> 1) + n;
        }
        return n2;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    private int indexOf(Object object, int n) {
        int n2;
        int n3 = this.mSize;
        if (n3 == 0) {
            return -1;
        }
        int n4 = ContainerHelpers.binarySearch(this.mHashes, n3, n);
        if (n4 < 0) {
            return n4;
        }
        if (object.equals(this.mArray[n4])) {
            return n4;
        }
        for (n2 = n4 + 1; n2 < n3 && this.mHashes[n2] == n; ++n2) {
            if (!object.equals(this.mArray[n2])) continue;
            return n2;
        }
        for (n3 = n4 - 1; n3 >= 0 && this.mHashes[n3] == n; --n3) {
            if (!object.equals(this.mArray[n3])) continue;
            return n3;
        }
        return n2;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    private int indexOfNull() {
        int n;
        int n2 = this.mSize;
        if (n2 == 0) {
            return -1;
        }
        int n3 = ContainerHelpers.binarySearch(this.mHashes, n2, 0);
        if (n3 < 0) {
            return n3;
        }
        if (this.mArray[n3] == null) {
            return n3;
        }
        for (n = n3 + 1; n < n2 && this.mHashes[n] == 0; ++n) {
            if (this.mArray[n] != null) continue;
            return n;
        }
        --n3;
        while (n3 >= 0 && this.mHashes[n3] == 0) {
            if (this.mArray[n3] == null) {
                return n3;
            }
            --n3;
        }
        return n;
    }

    private boolean shouldShrink() {
        int[] arrn = this.mHashes;
        boolean bl = arrn.length > 8 && this.mSize < arrn.length / 3;
        return bl;
    }

    @Override
    public boolean add(E e) {
        Object[] arrobject;
        int n;
        int n2;
        int n3;
        if (e == null) {
            n = 0;
            n2 = this.indexOfNull();
        } else {
            n3 = this.mIdentityHashCode ? System.identityHashCode(e) : e.hashCode();
            n2 = this.indexOf(e, n3);
            n = n3;
        }
        if (n2 >= 0) {
            return false;
        }
        int n4 = this.mSize;
        if (n4 >= this.mHashes.length) {
            n3 = 4;
            if (n4 >= 8) {
                n3 = (n4 >> 1) + n4;
            } else if (n4 >= 4) {
                n3 = 8;
            }
            int[] arrn = this.mHashes;
            Object[] arrobject2 = this.mArray;
            this.allocArrays(n3);
            arrobject = this.mHashes;
            if (arrobject.length > 0) {
                System.arraycopy(arrn, 0, arrobject, 0, arrn.length);
                System.arraycopy(arrobject2, 0, this.mArray, 0, arrobject2.length);
            }
            ArraySet.freeArrays(arrn, arrobject2, this.mSize);
        }
        if (n2 < (n3 = this.mSize)) {
            arrobject = this.mHashes;
            System.arraycopy(arrobject, n2, arrobject, n2 + 1, n3 - n2);
            arrobject = this.mArray;
            System.arraycopy(arrobject, n2, arrobject, n2 + 1, this.mSize - n2);
        }
        this.mHashes[n2] = n;
        this.mArray[n2] = e;
        ++this.mSize;
        return true;
    }

    public void addAll(ArraySet<? extends E> arraySet) {
        int n = arraySet.mSize;
        this.ensureCapacity(this.mSize + n);
        if (this.mSize == 0) {
            if (n > 0) {
                System.arraycopy(arraySet.mHashes, 0, this.mHashes, 0, n);
                System.arraycopy(arraySet.mArray, 0, this.mArray, 0, n);
                this.mSize = n;
            }
        } else {
            for (int i = 0; i < n; ++i) {
                this.add(arraySet.valueAt(i));
            }
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> object) {
        this.ensureCapacity(this.mSize + object.size());
        boolean bl = false;
        object = object.iterator();
        while (object.hasNext()) {
            bl |= this.add(object.next());
        }
        return bl;
    }

    public void append(E e) {
        int n = this.mSize;
        int n2 = e == null ? 0 : (this.mIdentityHashCode ? System.identityHashCode(e) : e.hashCode());
        int[] arrn = this.mHashes;
        if (n < arrn.length) {
            if (n > 0 && arrn[n - 1] > n2) {
                this.add(e);
                return;
            }
            this.mSize = n + 1;
            this.mHashes[n] = n2;
            this.mArray[n] = e;
            return;
        }
        throw new IllegalStateException("Array is full");
    }

    @Override
    public void clear() {
        int n = this.mSize;
        if (n != 0) {
            ArraySet.freeArrays(this.mHashes, this.mArray, n);
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
            this.mSize = 0;
        }
    }

    @Override
    public boolean contains(Object object) {
        boolean bl = this.indexOf(object) >= 0;
        return bl;
    }

    @Override
    public boolean containsAll(Collection<?> object) {
        object = object.iterator();
        while (object.hasNext()) {
            if (this.contains(object.next())) continue;
            return false;
        }
        return true;
    }

    public void ensureCapacity(int n) {
        if (this.mHashes.length < n) {
            int[] arrn = this.mHashes;
            Object[] arrobject = this.mArray;
            this.allocArrays(n);
            n = this.mSize;
            if (n > 0) {
                System.arraycopy(arrn, 0, this.mHashes, 0, n);
                System.arraycopy(arrobject, 0, this.mArray, 0, this.mSize);
            }
            ArraySet.freeArrays(arrn, arrobject, this.mSize);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Set)) {
            return false;
        }
        object = (Set)object;
        if (this.size() != object.size()) {
            return false;
        }
        int n = 0;
        try {
            do {
                if (n >= this.mSize) {
                    return true;
                }
                boolean bl = object.contains(this.valueAt(n));
                if (!bl) {
                    return false;
                }
                ++n;
            } while (true);
        }
        catch (ClassCastException classCastException) {
            return false;
        }
        catch (NullPointerException nullPointerException) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int[] arrn = this.mHashes;
        int n = 0;
        int n2 = this.mSize;
        for (int i = 0; i < n2; ++i) {
            n += arrn[i];
        }
        return n;
    }

    public int indexOf(Object object) {
        int n;
        if (object == null) {
            n = this.indexOfNull();
        } else {
            n = this.mIdentityHashCode ? System.identityHashCode(object) : object.hashCode();
            n = this.indexOf(object, n);
        }
        return n;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.mSize <= 0;
        return bl;
    }

    @Override
    public Iterator<E> iterator() {
        return this.getCollection().getKeySet().iterator();
    }

    @Override
    public boolean remove(Object object) {
        int n = this.indexOf(object);
        if (n >= 0) {
            this.removeAt(n);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(ArraySet<? extends E> arraySet) {
        int n = arraySet.mSize;
        int n2 = this.mSize;
        for (int i = 0; i < n; ++i) {
            this.remove(arraySet.valueAt(i));
        }
        boolean bl = n2 != this.mSize;
        return bl;
    }

    @Override
    public boolean removeAll(Collection<?> object) {
        boolean bl = false;
        object = object.iterator();
        while (object.hasNext()) {
            bl |= this.remove(object.next());
        }
        return bl;
    }

    public E removeAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        Object object = this.mArray[n];
        if (this.mSize <= 1) {
            this.clear();
        } else if (this.shouldShrink()) {
            int n2 = this.getNewShrunkenSize();
            int[] arrn = this.mHashes;
            Object[] arrobject = this.mArray;
            this.allocArrays(n2);
            --this.mSize;
            if (n > 0) {
                System.arraycopy(arrn, 0, this.mHashes, 0, n);
                System.arraycopy(arrobject, 0, this.mArray, 0, n);
            }
            if (n < (n2 = this.mSize)) {
                System.arraycopy(arrn, n + 1, this.mHashes, n, n2 - n);
                System.arraycopy(arrobject, n + 1, this.mArray, n, this.mSize - n);
            }
        } else {
            --this.mSize;
            int n3 = this.mSize;
            if (n < n3) {
                Object[] arrobject = this.mHashes;
                System.arraycopy(arrobject, n + 1, arrobject, n, n3 - n);
                arrobject = this.mArray;
                System.arraycopy(arrobject, n + 1, arrobject, n, this.mSize - n);
            }
            this.mArray[this.mSize] = null;
        }
        return (E)object;
    }

    @Override
    public boolean removeIf(Predicate<? super E> arrobject) {
        int n;
        Object[] arrobject2;
        int n2;
        if (this.mSize == 0) {
            return false;
        }
        int n3 = 0;
        int n4 = 0;
        for (n2 = 0; n2 < (n = this.mSize); ++n2) {
            if (arrobject.test(this.mArray[n2])) {
                ++n4;
                continue;
            }
            if (n3 != n2) {
                arrobject2 = this.mArray;
                arrobject2[n3] = arrobject2[n2];
                arrobject2 = this.mHashes;
                arrobject2[n3] = arrobject2[n2];
            }
            ++n3;
        }
        if (n4 == 0) {
            return false;
        }
        if (n4 == n) {
            this.clear();
            return true;
        }
        this.mSize = n - n4;
        if (this.shouldShrink()) {
            n2 = this.getNewShrunkenSize();
            arrobject2 = this.mHashes;
            arrobject = this.mArray;
            this.allocArrays(n2);
            System.arraycopy(arrobject2, 0, this.mHashes, 0, this.mSize);
            System.arraycopy(arrobject, 0, this.mArray, 0, this.mSize);
        } else {
            for (n2 = this.mSize; n2 < (arrobject = this.mArray).length; ++n2) {
                arrobject[n2] = null;
            }
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean bl = false;
        for (int i = this.mSize - 1; i >= 0; --i) {
            if (collection.contains(this.mArray[i])) continue;
            this.removeAt(i);
            bl = true;
        }
        return bl;
    }

    @Override
    public int size() {
        return this.mSize;
    }

    @Override
    public Object[] toArray() {
        int n = this.mSize;
        Object[] arrobject = new Object[n];
        System.arraycopy(this.mArray, 0, arrobject, 0, n);
        return arrobject;
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        Object[] arrobject = arrT;
        if (arrT.length < this.mSize) {
            arrobject = (Object[])Array.newInstance(arrT.getClass().getComponentType(), this.mSize);
        }
        System.arraycopy(this.mArray, 0, arrobject, 0, this.mSize);
        int n = arrobject.length;
        int n2 = this.mSize;
        if (n > n2) {
            arrobject[n2] = null;
        }
        return arrobject;
    }

    public String toString() {
        if (this.isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.mSize * 14);
        stringBuilder.append('{');
        for (int i = 0; i < this.mSize; ++i) {
            E e;
            if (i > 0) {
                stringBuilder.append(", ");
            }
            if ((e = this.valueAt(i)) != this) {
                stringBuilder.append(e);
                continue;
            }
            stringBuilder.append("(this Set)");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public E valueAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        return this.valueAtUnchecked(n);
    }

    public E valueAtUnchecked(int n) {
        return (E)this.mArray[n];
    }

}

