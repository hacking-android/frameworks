/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.util.ContainerHelpers;
import android.util.UtilConfig;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import libcore.util.EmptyArray;

public class SparseArray<E>
implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage = false;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int[] mKeys;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mSize;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private Object[] mValues;

    public SparseArray() {
        this(10);
    }

    public SparseArray(int n) {
        if (n == 0) {
            this.mKeys = EmptyArray.INT;
            this.mValues = EmptyArray.OBJECT;
        } else {
            this.mValues = ArrayUtils.newUnpaddedObjectArray(n);
            this.mKeys = new int[this.mValues.length];
        }
        this.mSize = 0;
    }

    private void gc() {
        int n = this.mSize;
        int n2 = 0;
        int[] arrn = this.mKeys;
        Object[] arrobject = this.mValues;
        for (int i = 0; i < n; ++i) {
            Object object = arrobject[i];
            int n3 = n2;
            if (object != DELETED) {
                if (i != n2) {
                    arrn[n2] = arrn[i];
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

    public void append(int n, E e) {
        int n2 = this.mSize;
        if (n2 != 0 && n <= this.mKeys[n2 - 1]) {
            this.put(n, e);
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            this.gc();
        }
        this.mKeys = GrowingArrayUtils.append(this.mKeys, this.mSize, n);
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

    public SparseArray<E> clone() {
        SparseArray sparseArray;
        SparseArray sparseArray2 = null;
        sparseArray2 = sparseArray = (SparseArray)super.clone();
        sparseArray.mKeys = (int[])this.mKeys.clone();
        sparseArray2 = sparseArray;
        try {
            sparseArray.mValues = (Object[])this.mValues.clone();
            sparseArray2 = sparseArray;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return sparseArray2;
    }

    public void delete(int n) {
        Object object;
        Object[] arrobject;
        Object object2;
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) >= 0 && (object2 = (arrobject = this.mValues)[n]) != (object = DELETED)) {
            arrobject[n] = object;
            this.mGarbage = true;
        }
    }

    public E get(int n) {
        return this.get(n, null);
    }

    public E get(int n, E e) {
        Object[] arrobject;
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) >= 0 && (arrobject = this.mValues)[n] != DELETED) {
            return (E)arrobject[n];
        }
        return e;
    }

    public int indexOfKey(int n) {
        if (this.mGarbage) {
            this.gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
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

    public int keyAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        if (this.mGarbage) {
            this.gc();
        }
        return this.mKeys[n];
    }

    public void put(int n, E e) {
        int n2 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
        if (n2 >= 0) {
            this.mValues[n2] = e;
        } else {
            Object[] arrobject;
            int n3 = n2;
            if (n3 < this.mSize && (arrobject = this.mValues)[n3] == DELETED) {
                this.mKeys[n3] = n;
                arrobject[n3] = e;
                return;
            }
            n2 = n3;
            if (this.mGarbage) {
                n2 = n3;
                if (this.mSize >= this.mKeys.length) {
                    this.gc();
                    n2 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
                }
            }
            this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, n2, n);
            this.mValues = GrowingArrayUtils.insert(this.mValues, this.mSize, n2, e);
            ++this.mSize;
        }
    }

    public void remove(int n) {
        this.delete(n);
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

    public void removeAtRange(int n, int n2) {
        n2 = Math.min(this.mSize, n + n2);
        while (n < n2) {
            this.removeAt(n);
            ++n;
        }
    }

    public E removeReturnOld(int n) {
        Object object;
        Object[] arrobject;
        Object object2;
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) >= 0 && (object2 = (arrobject = this.mValues)[n]) != (object = DELETED)) {
            object2 = arrobject[n];
            arrobject[n] = object;
            this.mGarbage = true;
            return (E)object2;
        }
        return null;
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

