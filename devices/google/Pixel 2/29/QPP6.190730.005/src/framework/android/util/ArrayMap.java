/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.util.ContainerHelpers;
import android.util.Log;
import android.util.MapCollections;
import android.util.UtilConfig;
import com.android.internal.util.ArrayUtils;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Set;
import libcore.util.EmptyArray;

public final class ArrayMap<K, V>
implements Map<K, V> {
    private static final int BASE_SIZE = 4;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private static final int CACHE_SIZE = 10;
    private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;
    private static final boolean DEBUG = false;
    @UnsupportedAppUsage(maxTargetSdk=28)
    public static final ArrayMap EMPTY;
    @UnsupportedAppUsage(maxTargetSdk=28)
    static final int[] EMPTY_IMMUTABLE_INTS;
    private static final String TAG = "ArrayMap";
    @UnsupportedAppUsage(maxTargetSdk=28)
    static Object[] mBaseCache;
    @UnsupportedAppUsage(maxTargetSdk=28)
    static int mBaseCacheSize;
    @UnsupportedAppUsage(maxTargetSdk=28)
    static Object[] mTwiceBaseCache;
    @UnsupportedAppUsage(maxTargetSdk=28)
    static int mTwiceBaseCacheSize;
    @UnsupportedAppUsage(maxTargetSdk=28)
    Object[] mArray;
    MapCollections<K, V> mCollections;
    @UnsupportedAppUsage(maxTargetSdk=28)
    int[] mHashes;
    final boolean mIdentityHashCode;
    @UnsupportedAppUsage(maxTargetSdk=28)
    int mSize;

    static {
        EMPTY_IMMUTABLE_INTS = new int[0];
        EMPTY = new ArrayMap<K, V>(-1);
    }

    public ArrayMap() {
        this(0, false);
    }

    public ArrayMap(int n) {
        this(n, false);
    }

    public ArrayMap(int n, boolean bl) {
        this.mIdentityHashCode = bl;
        if (n < 0) {
            this.mHashes = EMPTY_IMMUTABLE_INTS;
            this.mArray = EmptyArray.OBJECT;
        } else if (n == 0) {
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
        } else {
            this.allocArrays(n);
        }
        this.mSize = 0;
    }

    public ArrayMap(ArrayMap<K, V> arrayMap) {
        this();
        if (arrayMap != null) {
            this.putAll(arrayMap);
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
        if (this.mHashes == EMPTY_IMMUTABLE_INTS) {
            throw new UnsupportedOperationException("ArrayMap is immutable");
        }
        if (n == 8) {
            synchronized (ArrayMap.class) {
                if (mTwiceBaseCache != null) {
                    Object[] arrobject = mTwiceBaseCache;
                    this.mArray = arrobject;
                    mTwiceBaseCache = (Object[])arrobject[0];
                    this.mHashes = (int[])arrobject[1];
                    arrobject[1] = null;
                    arrobject[0] = null;
                    --mTwiceBaseCacheSize;
                    return;
                }
            }
        } else if (n == 4) {
            synchronized (ArrayMap.class) {
                if (mBaseCache != null) {
                    Object[] arrobject = mBaseCache;
                    this.mArray = arrobject;
                    mBaseCache = (Object[])arrobject[0];
                    this.mHashes = (int[])arrobject[1];
                    arrobject[1] = null;
                    arrobject[0] = null;
                    --mBaseCacheSize;
                    return;
                }
            }
        }
        this.mHashes = new int[n];
        this.mArray = new Object[n << 1];
    }

    private static int binarySearchHashes(int[] arrn, int n, int n2) {
        try {
            n = ContainerHelpers.binarySearch(arrn, n, n2);
            return n;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new ConcurrentModificationException();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage(maxTargetSdk=28)
    private static void freeArrays(int[] arrn, Object[] arrobject, int n) {
        if (arrn.length == 8) {
            synchronized (ArrayMap.class) {
                if (mTwiceBaseCacheSize >= 10) return;
                arrobject[0] = mTwiceBaseCache;
                arrobject[1] = arrn;
                n = (n << 1) - 1;
                do {
                    if (n < 2) {
                        mTwiceBaseCache = arrobject;
                        ++mTwiceBaseCacheSize;
                        return;
                    }
                    arrobject[n] = null;
                    --n;
                } while (true);
            }
        }
        if (arrn.length != 4) return;
        synchronized (ArrayMap.class) {
            if (mBaseCacheSize >= 10) return;
            arrobject[0] = mBaseCache;
            arrobject[1] = arrn;
            n = (n << 1) - 1;
            do {
                if (n < 2) {
                    mBaseCache = arrobject;
                    ++mBaseCacheSize;
                    return;
                }
                arrobject[n] = null;
                --n;
            } while (true);
        }
    }

    private MapCollections<K, V> getCollection() {
        if (this.mCollections == null) {
            this.mCollections = new MapCollections<K, V>(){

                @Override
                protected void colClear() {
                    ArrayMap.this.clear();
                }

                @Override
                protected Object colGetEntry(int n, int n2) {
                    return ArrayMap.this.mArray[(n << 1) + n2];
                }

                @Override
                protected Map<K, V> colGetMap() {
                    return ArrayMap.this;
                }

                @Override
                protected int colGetSize() {
                    return ArrayMap.this.mSize;
                }

                @Override
                protected int colIndexOfKey(Object object) {
                    return ArrayMap.this.indexOfKey(object);
                }

                @Override
                protected int colIndexOfValue(Object object) {
                    return ArrayMap.this.indexOfValue(object);
                }

                @Override
                protected void colPut(K k, V v) {
                    ArrayMap.this.put(k, v);
                }

                @Override
                protected void colRemoveAt(int n) {
                    ArrayMap.this.removeAt(n);
                }

                @Override
                protected V colSetValue(int n, V v) {
                    return ArrayMap.this.setValueAt(n, v);
                }
            };
        }
        return this.mCollections;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public void append(K k, V v) {
        int n = this.mSize;
        int n2 = k == null ? 0 : (this.mIdentityHashCode ? System.identityHashCode(k) : k.hashCode());
        Object object = this.mHashes;
        if (n < ((int[])object).length) {
            if (n > 0 && object[n - 1] > n2) {
                RuntimeException runtimeException = new RuntimeException("here");
                runtimeException.fillInStackTrace();
                object = new StringBuilder();
                ((StringBuilder)object).append("New hash ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" is before end of array hash ");
                ((StringBuilder)object).append(this.mHashes[n - 1]);
                ((StringBuilder)object).append(" at index ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" key ");
                ((StringBuilder)object).append(k);
                Log.w(TAG, ((StringBuilder)object).toString(), runtimeException);
                this.put(k, v);
                return;
            }
            this.mSize = n + 1;
            this.mHashes[n] = n2;
            n2 = n << 1;
            object = this.mArray;
            object[n2] = (int)k;
            object[n2 + 1] = v;
            return;
        }
        throw new IllegalStateException("Array is full");
    }

    @Override
    public void clear() {
        if (this.mSize > 0) {
            int[] arrn = this.mHashes;
            Object[] arrobject = this.mArray;
            int n = this.mSize;
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
            this.mSize = 0;
            ArrayMap.freeArrays(arrn, arrobject, n);
        }
        if (this.mSize <= 0) {
            return;
        }
        throw new ConcurrentModificationException();
    }

    public boolean containsAll(Collection<?> collection) {
        return MapCollections.containsAllHelper(this, collection);
    }

    @Override
    public boolean containsKey(Object object) {
        boolean bl = this.indexOfKey(object) >= 0;
        return bl;
    }

    @Override
    public boolean containsValue(Object object) {
        boolean bl = this.indexOfValue(object) >= 0;
        return bl;
    }

    public void ensureCapacity(int n) {
        int n2 = this.mSize;
        if (this.mHashes.length < n) {
            int[] arrn = this.mHashes;
            Object[] arrobject = this.mArray;
            this.allocArrays(n);
            if (this.mSize > 0) {
                System.arraycopy(arrn, 0, this.mHashes, 0, n2);
                System.arraycopy(arrobject, 0, this.mArray, 0, n2 << 1);
            }
            ArrayMap.freeArrays(arrn, arrobject, n2);
        }
        if (this.mSize == n2) {
            return;
        }
        throw new ConcurrentModificationException();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.getCollection().getEntrySet();
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
        if (!(object instanceof Map)) {
            return false;
        }
        Map map = (Map)object;
        if (this.size() != map.size()) {
            return false;
        }
        int n = 0;
        try {
            do {
                boolean bl;
                if (n >= this.mSize) {
                    return true;
                }
                K k = this.keyAt(n);
                V v = this.valueAt(n);
                object = map.get(k);
                if (v == null ? object != null || !map.containsKey(k) : !(bl = v.equals(object))) {
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

    public void erase() {
        int n = this.mSize;
        if (n > 0) {
            Object[] arrobject = this.mArray;
            for (int i = 0; i < n << 1; ++i) {
                arrobject[i] = null;
            }
            this.mSize = 0;
        }
    }

    @Override
    public V get(Object object) {
        int n = this.indexOfKey(object);
        object = n >= 0 ? this.mArray[(n << 1) + 1] : null;
        return (V)object;
    }

    @Override
    public int hashCode() {
        int[] arrn = this.mHashes;
        Object[] arrobject = this.mArray;
        int n = 0;
        int n2 = 0;
        int n3 = 1;
        int n4 = this.mSize;
        while (n2 < n4) {
            Object object = arrobject[n3];
            int n5 = arrn[n2];
            int n6 = object == null ? 0 : object.hashCode();
            n += n5 ^ n6;
            ++n2;
            n3 += 2;
        }
        return n;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    int indexOf(Object object, int n) {
        int n2;
        int n3 = this.mSize;
        if (n3 == 0) {
            return -1;
        }
        int n4 = ArrayMap.binarySearchHashes(this.mHashes, n3, n);
        if (n4 < 0) {
            return n4;
        }
        if (object.equals(this.mArray[n4 << 1])) {
            return n4;
        }
        for (n2 = n4 + 1; n2 < n3 && this.mHashes[n2] == n; ++n2) {
            if (!object.equals(this.mArray[n2 << 1])) continue;
            return n2;
        }
        --n4;
        while (n4 >= 0 && this.mHashes[n4] == n) {
            if (object.equals(this.mArray[n4 << 1])) {
                return n4;
            }
            --n4;
        }
        return n2;
    }

    public int indexOfKey(Object object) {
        int n;
        if (object == null) {
            n = this.indexOfNull();
        } else {
            n = this.mIdentityHashCode ? System.identityHashCode(object) : object.hashCode();
            n = this.indexOf(object, n);
        }
        return n;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    int indexOfNull() {
        int n;
        int n2 = this.mSize;
        if (n2 == 0) {
            return -1;
        }
        int n3 = ArrayMap.binarySearchHashes(this.mHashes, n2, 0);
        if (n3 < 0) {
            return n3;
        }
        if (this.mArray[n3 << 1] == null) {
            return n3;
        }
        for (n = n3 + 1; n < n2 && this.mHashes[n] == 0; ++n) {
            if (this.mArray[n << 1] != null) continue;
            return n;
        }
        for (n2 = n3 - 1; n2 >= 0 && this.mHashes[n2] == 0; --n2) {
            if (this.mArray[n2 << 1] != null) continue;
            return n2;
        }
        return n;
    }

    public int indexOfValue(Object object) {
        int n = this.mSize * 2;
        Object[] arrobject = this.mArray;
        if (object == null) {
            for (int i = 1; i < n; i += 2) {
                if (arrobject[i] != null) continue;
                return i >> 1;
            }
        } else {
            for (int i = 1; i < n; i += 2) {
                if (!object.equals(arrobject[i])) continue;
                return i >> 1;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        boolean bl = this.mSize <= 0;
        return bl;
    }

    public K keyAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        return (K)this.mArray[n << 1];
    }

    @Override
    public Set<K> keySet() {
        return this.getCollection().getKeySet();
    }

    @Override
    public V put(K object, V v) {
        int n;
        Object[] arrobject;
        int n2;
        int n3;
        int n4 = this.mSize;
        if (object == null) {
            n3 = 0;
            n = this.indexOfNull();
        } else {
            n2 = this.mIdentityHashCode ? System.identityHashCode(object) : object.hashCode();
            n = this.indexOf(object, n2);
            n3 = n2;
        }
        if (n >= 0) {
            n2 = (n << 1) + 1;
            Object[] arrobject2 = this.mArray;
            object = arrobject2[n2];
            arrobject2[n2] = v;
            return (V)object;
        }
        if (n4 >= this.mHashes.length) {
            n2 = 4;
            if (n4 >= 8) {
                n2 = (n4 >> 1) + n4;
            } else if (n4 >= 4) {
                n2 = 8;
            }
            int[] arrn = this.mHashes;
            arrobject = this.mArray;
            this.allocArrays(n2);
            if (n4 == this.mSize) {
                int[] arrn2 = this.mHashes;
                if (arrn2.length > 0) {
                    System.arraycopy(arrn, 0, arrn2, 0, arrn.length);
                    System.arraycopy(arrobject, 0, this.mArray, 0, arrobject.length);
                }
                ArrayMap.freeArrays(arrn, arrobject, n4);
            } else {
                throw new ConcurrentModificationException();
            }
        }
        if (n < n4) {
            arrobject = this.mHashes;
            System.arraycopy(arrobject, n, arrobject, n + 1, n4 - n);
            arrobject = this.mArray;
            System.arraycopy(arrobject, n << 1, arrobject, n + 1 << 1, this.mSize - n << 1);
        }
        if (n4 == (n2 = this.mSize) && n < (arrobject = this.mHashes).length) {
            arrobject[n] = n3;
            arrobject = this.mArray;
            arrobject[n << 1] = (int)object;
            arrobject[(n << 1) + 1] = (int)v;
            this.mSize = n2 + 1;
            return null;
        }
        throw new ConcurrentModificationException();
    }

    @Override
    public void putAll(ArrayMap<? extends K, ? extends V> arrayMap) {
        int n = arrayMap.mSize;
        this.ensureCapacity(this.mSize + n);
        if (this.mSize == 0) {
            if (n > 0) {
                System.arraycopy(arrayMap.mHashes, 0, this.mHashes, 0, n);
                System.arraycopy(arrayMap.mArray, 0, this.mArray, 0, n << 1);
                this.mSize = n;
            }
        } else {
            for (int i = 0; i < n; ++i) {
                this.put(arrayMap.keyAt(i), arrayMap.valueAt(i));
            }
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> object2) {
        this.ensureCapacity(this.mSize + object2.size());
        for (Map.Entry entry : object2.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object object) {
        int n = this.indexOfKey(object);
        if (n >= 0) {
            return this.removeAt(n);
        }
        return null;
    }

    public boolean removeAll(Collection<?> collection) {
        return MapCollections.removeAllHelper(this, collection);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public V removeAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        Object object = this.mArray[(n << 1) + 1];
        int n2 = this.mSize;
        if (n2 <= 1) {
            int[] arrn = this.mHashes;
            Object[] arrobject = this.mArray;
            this.mHashes = EmptyArray.INT;
            this.mArray = EmptyArray.OBJECT;
            ArrayMap.freeArrays(arrn, arrobject, n2);
            n = 0;
        } else {
            int n3 = n2 - 1;
            int[] arrn = this.mHashes;
            int n4 = arrn.length;
            int n5 = 8;
            if (n4 > 8 && this.mSize < arrn.length / 3) {
                if (n2 > 8) {
                    n5 = n2 + (n2 >> 1);
                }
                int[] arrn2 = this.mHashes;
                Object[] arrobject = this.mArray;
                this.allocArrays(n5);
                if (n2 != this.mSize) throw new ConcurrentModificationException();
                if (n > 0) {
                    System.arraycopy(arrn2, 0, this.mHashes, 0, n);
                    System.arraycopy(arrobject, 0, this.mArray, 0, n << 1);
                }
                if (n < n3) {
                    System.arraycopy(arrn2, n + 1, this.mHashes, n, n3 - n);
                    System.arraycopy(arrobject, n + 1 << 1, this.mArray, n << 1, n3 - n << 1);
                }
            } else {
                if (n < n3) {
                    int[] arrn3 = this.mHashes;
                    System.arraycopy(arrn3, n + 1, arrn3, n, n3 - n);
                    Object[] arrobject = this.mArray;
                    System.arraycopy(arrobject, n + 1 << 1, arrobject, n << 1, n3 - n << 1);
                }
                Object[] arrobject = this.mArray;
                arrobject[n3 << 1] = null;
                arrobject[(n3 << 1) + 1] = null;
            }
            n = n3;
        }
        if (n2 != this.mSize) throw new ConcurrentModificationException();
        this.mSize = n;
        return (V)object;
    }

    public boolean retainAll(Collection<?> collection) {
        return MapCollections.retainAllHelper(this, collection);
    }

    public V setValueAt(int n, V v) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        n = (n << 1) + 1;
        Object[] arrobject = this.mArray;
        Object object = arrobject[n];
        arrobject[n] = v;
        return (V)object;
    }

    @Override
    public int size() {
        return this.mSize;
    }

    public String toString() {
        if (this.isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
        stringBuilder.append('{');
        for (int i = 0; i < this.mSize; ++i) {
            Object object;
            if (i > 0) {
                stringBuilder.append(", ");
            }
            if ((object = this.keyAt(i)) != this) {
                stringBuilder.append(object);
            } else {
                stringBuilder.append("(this Map)");
            }
            stringBuilder.append('=');
            object = this.valueAt(i);
            if (object != this) {
                stringBuilder.append(ArrayUtils.deepToString(object));
                continue;
            }
            stringBuilder.append("(this Map)");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public void validate() {
        int n = this.mSize;
        if (n <= 1) {
            return;
        }
        int n2 = this.mHashes[0];
        int n3 = 0;
        for (int i = 1; i < n; ++i) {
            int n4;
            int n5;
            block6 : {
                n4 = this.mHashes[i];
                if (n4 != n2) {
                    n5 = i;
                } else {
                    Object object;
                    Object object2 = this.mArray[i << 1];
                    int n6 = i - 1;
                    do {
                        n4 = n2;
                        n5 = n3;
                        if (n6 < n3) break block6;
                        object = this.mArray[n6 << 1];
                        if (object2 == object) break;
                        if (object2 != null && object != null && object2.equals(object)) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Duplicate key in ArrayMap: ");
                            ((StringBuilder)object).append(object2);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        --n6;
                    } while (true);
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Duplicate key in ArrayMap: ");
                    ((StringBuilder)object).append(object2);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            }
            n2 = n4;
            n3 = n5;
        }
    }

    public V valueAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        return (V)this.mArray[(n << 1) + 1];
    }

    @Override
    public Collection<V> values() {
        return this.getCollection().getValues();
    }

}

