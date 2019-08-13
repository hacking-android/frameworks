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
import java.util.Arrays;
import libcore.util.EmptyArray;

public class SparseIntArray
implements Cloneable {
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int[] mKeys;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mSize;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int[] mValues;

    public SparseIntArray() {
        this(10);
    }

    public SparseIntArray(int n) {
        if (n == 0) {
            this.mKeys = EmptyArray.INT;
            this.mValues = EmptyArray.INT;
        } else {
            this.mKeys = ArrayUtils.newUnpaddedIntArray(n);
            this.mValues = new int[this.mKeys.length];
        }
        this.mSize = 0;
    }

    public void append(int n, int n2) {
        int n3 = this.mSize;
        if (n3 != 0 && n <= this.mKeys[n3 - 1]) {
            this.put(n, n2);
            return;
        }
        this.mKeys = GrowingArrayUtils.append(this.mKeys, this.mSize, n);
        this.mValues = GrowingArrayUtils.append(this.mValues, this.mSize, n2);
        ++this.mSize;
    }

    public void clear() {
        this.mSize = 0;
    }

    public SparseIntArray clone() {
        SparseIntArray sparseIntArray;
        SparseIntArray sparseIntArray2 = null;
        sparseIntArray2 = sparseIntArray = (SparseIntArray)super.clone();
        sparseIntArray.mKeys = (int[])this.mKeys.clone();
        sparseIntArray2 = sparseIntArray;
        try {
            sparseIntArray.mValues = (int[])this.mValues.clone();
            sparseIntArray2 = sparseIntArray;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return sparseIntArray2;
    }

    public int[] copyKeys() {
        if (this.size() == 0) {
            return null;
        }
        return Arrays.copyOf(this.mKeys, this.size());
    }

    public void delete(int n) {
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) >= 0) {
            this.removeAt(n);
        }
    }

    public int get(int n) {
        return this.get(n, 0);
    }

    public int get(int n, int n2) {
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) < 0) {
            return n2;
        }
        return this.mValues[n];
    }

    public int indexOfKey(int n) {
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
    }

    public int indexOfValue(int n) {
        for (int i = 0; i < this.mSize; ++i) {
            if (this.mValues[i] != n) continue;
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

    public void put(int n, int n2) {
        int n3 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
        if (n3 >= 0) {
            this.mValues[n3] = n2;
        } else {
            this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, n3, n);
            this.mValues = GrowingArrayUtils.insert(this.mValues, this.mSize, n3, n2);
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

    public void setValueAt(int n, int n2) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        this.mValues[n] = n2;
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

    public int valueAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        return this.mValues[n];
    }
}

