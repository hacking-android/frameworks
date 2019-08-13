/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.util;

import android.util.ContainerHelpers;
import android.util.UtilConfig;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import libcore.util.EmptyArray;

public class LongSparseArray<E>
implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage = false;
    private long[] mKeys;
    private int mSize;
    private Object[] mValues;

    public LongSparseArray() {
        this(10);
    }

    public LongSparseArray(int n) {
        if (n == 0) {
            this.mKeys = EmptyArray.LONG;
            this.mValues = EmptyArray.OBJECT;
        } else {
            this.mKeys = ArrayUtils.newUnpaddedLongArray(n);
            this.mValues = ArrayUtils.newUnpaddedObjectArray(n);
        }
        this.mSize = 0;
    }

    private void gc() {
        int n = this.mSize;
        int n2 = 0;
        long[] arrl = this.mKeys;
        Object[] arrobject = this.mValues;
        for (int i = 0; i < n; ++i) {
            Object object = arrobject[i];
            int n3 = n2;
            if (object != DELETED) {
                if (i != n2) {
                    arrl[n2] = arrl[i];
                    arrobject[n2] = object;
                    arrobject[i] = null;
                }
                n3 = n2 + 1;
            }
            n2 = n3;
        }
        this.mGarbage = false;
        this.mSize = n2;
    }

    public void append(long l, E e) {
        int n = this.mSize;
        if (n != 0 && l <= this.mKeys[n - 1]) {
            this.put(l, e);
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            this.gc();
        }
        this.mKeys = GrowingArrayUtils.append(this.mKeys, this.mSize, l);
        this.mValues = GrowingArrayUtils.append(this.mValues, this.mSize, e);
        ++this.mSize;
    }

    public void clear() {
        int n = this.mSize;
        Object[] arrobject = this.mValues;
        for (int i = 0; i < n; ++i) {
            arrobject[i] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    public LongSparseArray<E> clone() {
        LongSparseArray longSparseArray;
        LongSparseArray longSparseArray2 = null;
        longSparseArray2 = longSparseArray = (LongSparseArray)super.clone();
        longSparseArray.mKeys = (long[])this.mKeys.clone();
        longSparseArray2 = longSparseArray;
        try {
            longSparseArray.mValues = (Object[])this.mValues.clone();
            longSparseArray2 = longSparseArray;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return longSparseArray2;
    }

    public void delete(long l) {
        Object[] arrobject;
        Object object;
        Object object2;
        int n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
        if (n >= 0 && (object = (arrobject = this.mValues)[n]) != (object2 = DELETED)) {
            arrobject[n] = object2;
            this.mGarbage = true;
        }
    }

    public E get(long l) {
        return this.get(l, null);
    }

    public E get(long l, E e) {
        Object[] arrobject;
        int n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
        if (n >= 0 && (arrobject = this.mValues)[n] != DELETED) {
            return (E)arrobject[n];
        }
        return e;
    }

    public int indexOfKey(long l) {
        if (this.mGarbage) {
            this.gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
    }

    public int indexOfValue(E e) {
        if (this.mGarbage) {
            this.gc();
        }
        for (int i = 0; i < this.mSize; ++i) {
            if (this.mValues[i] != e) continue;
            return i;
        }
        return -1;
    }

    public int indexOfValueByValue(E e) {
        if (this.mGarbage) {
            this.gc();
        }
        for (int i = 0; i < this.mSize; ++i) {
            if (!(e == null ? this.mValues[i] == null : e.equals(this.mValues[i]))) continue;
            return i;
        }
        return -1;
    }

    public long keyAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        if (this.mGarbage) {
            this.gc();
        }
        return this.mKeys[n];
    }

    public void put(long l, E e) {
        int n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
        if (n >= 0) {
            this.mValues[n] = e;
        } else {
            Object[] arrobject;
            int n2 = n;
            if (n2 < this.mSize && (arrobject = this.mValues)[n2] == DELETED) {
                this.mKeys[n2] = l;
                arrobject[n2] = e;
                return;
            }
            n = n2;
            if (this.mGarbage) {
                n = n2;
                if (this.mSize >= this.mKeys.length) {
                    this.gc();
                    n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
                }
            }
            this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, n, l);
            this.mValues = GrowingArrayUtils.insert(this.mValues, this.mSize, n, e);
            ++this.mSize;
        }
    }

    public void remove(long l) {
        this.delete(l);
    }

    public void removeAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        Object[] arrobject = this.mValues;
        Object object = arrobject[n];
        Object object2 = DELETED;
        if (object != object2) {
            arrobject[n] = object2;
            this.mGarbage = true;
        }
    }

    public void setValueAt(int n, E e) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        if (this.mGarbage) {
            this.gc();
        }
        this.mValues[n] = e;
    }

    public int size() {
        if (this.mGarbage) {
            this.gc();
        }
        return this.mSize;
    }

    public String toString() {
        if (this.size() <= 0) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
        stringBuilder.append('{');
        for (int i = 0; i < this.mSize; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.keyAt(i));
            stringBuilder.append('=');
            E e = this.valueAt(i);
            if (e != this) {
                stringBuilder.append(e);
                continue;
            }
            stringBuilder.append("(this Map)");
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public E valueAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        if (this.mGarbage) {
            this.gc();
        }
        return (E)this.mValues[n];
    }
}

