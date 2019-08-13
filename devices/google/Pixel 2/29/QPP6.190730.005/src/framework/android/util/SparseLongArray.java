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

public class SparseLongArray
implements Cloneable {
    private int[] mKeys;
    private int mSize;
    private long[] mValues;

    public SparseLongArray() {
        this(10);
    }

    public SparseLongArray(int n) {
        if (n == 0) {
            this.mKeys = EmptyArray.INT;
            this.mValues = EmptyArray.LONG;
        } else {
            this.mValues = ArrayUtils.newUnpaddedLongArray(n);
            this.mKeys = new int[this.mValues.length];
        }
        this.mSize = 0;
    }

    public void append(int n, long l) {
        int n2 = this.mSize;
        if (n2 != 0 && n <= this.mKeys[n2 - 1]) {
            this.put(n, l);
            return;
        }
        this.mKeys = GrowingArrayUtils.append(this.mKeys, this.mSize, n);
        this.mValues = GrowingArrayUtils.append(this.mValues, this.mSize, l);
        ++this.mSize;
    }

    public void clear() {
        this.mSize = 0;
    }

    public SparseLongArray clone() {
        SparseLongArray sparseLongArray;
        SparseLongArray sparseLongArray2 = null;
        sparseLongArray2 = sparseLongArray = (SparseLongArray)super.clone();
        sparseLongArray.mKeys = (int[])this.mKeys.clone();
        sparseLongArray2 = sparseLongArray;
        try {
            sparseLongArray.mValues = (long[])this.mValues.clone();
            sparseLongArray2 = sparseLongArray;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return sparseLongArray2;
    }

    public void delete(int n) {
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) >= 0) {
            this.removeAt(n);
        }
    }

    public long get(int n) {
        return this.get(n, 0L);
    }

    public long get(int n, long l) {
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) < 0) {
            return l;
        }
        return this.mValues[n];
    }

    public int indexOfKey(int n) {
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
    }

    public int indexOfValue(long l) {
        for (int i = 0; i < this.mSize; ++i) {
            if (this.mValues[i] != l) continue;
            return i;
        }
        return -1;
    }

    public int keyAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        return this.mKeys[n];
    }

    public void put(int n, long l) {
        int n2 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
        if (n2 >= 0) {
            this.mValues[n2] = l;
        } else {
            this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, n2, n);
            this.mValues = GrowingArrayUtils.insert(this.mValues, this.mSize, n2, l);
            ++this.mSize;
        }
    }

    public void removeAt(int n) {
        int[] arrn = this.mKeys;
        System.arraycopy(arrn, n + 1, arrn, n, this.mSize - (n + 1));
        arrn = this.mValues;
        System.arraycopy(arrn, n + 1, arrn, n, this.mSize - (n + 1));
        --this.mSize;
    }

    public void removeAtRange(int n, int n2) {
        n2 = Math.min(n2, this.mSize - n);
        int[] arrn = this.mKeys;
        System.arraycopy(arrn, n + n2, arrn, n, this.mSize - (n + n2));
        arrn = this.mValues;
        System.arraycopy(arrn, n + n2, arrn, n, this.mSize - (n + n2));
        this.mSize -= n2;
    }

    public int size() {
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
            stringBuilder.append(this.valueAt(i));
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public long valueAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        return this.mValues[n];
    }
}

