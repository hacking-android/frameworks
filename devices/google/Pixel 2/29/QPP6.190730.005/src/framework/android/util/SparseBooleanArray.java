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

public class SparseBooleanArray
implements Cloneable {
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int[] mKeys;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mSize;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private boolean[] mValues;

    public SparseBooleanArray() {
        this(10);
    }

    public SparseBooleanArray(int n) {
        if (n == 0) {
            this.mKeys = EmptyArray.INT;
            this.mValues = EmptyArray.BOOLEAN;
        } else {
            this.mKeys = ArrayUtils.newUnpaddedIntArray(n);
            this.mValues = new boolean[this.mKeys.length];
        }
        this.mSize = 0;
    }

    public void append(int n, boolean bl) {
        int n2 = this.mSize;
        if (n2 != 0 && n <= this.mKeys[n2 - 1]) {
            this.put(n, bl);
            return;
        }
        this.mKeys = GrowingArrayUtils.append(this.mKeys, this.mSize, n);
        this.mValues = GrowingArrayUtils.append(this.mValues, this.mSize, bl);
        ++this.mSize;
    }

    public void clear() {
        this.mSize = 0;
    }

    public SparseBooleanArray clone() {
        SparseBooleanArray sparseBooleanArray;
        SparseBooleanArray sparseBooleanArray2 = null;
        sparseBooleanArray2 = sparseBooleanArray = (SparseBooleanArray)super.clone();
        sparseBooleanArray.mKeys = (int[])this.mKeys.clone();
        sparseBooleanArray2 = sparseBooleanArray;
        try {
            sparseBooleanArray.mValues = (boolean[])this.mValues.clone();
            sparseBooleanArray2 = sparseBooleanArray;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return sparseBooleanArray2;
    }

    public void delete(int n) {
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) >= 0) {
            Object[] arrobject = this.mKeys;
            System.arraycopy(arrobject, n + 1, arrobject, n, this.mSize - (n + 1));
            arrobject = this.mValues;
            System.arraycopy(arrobject, n + 1, arrobject, n, this.mSize - (n + 1));
            --this.mSize;
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SparseBooleanArray)) {
            return false;
        }
        object = (SparseBooleanArray)object;
        if (this.mSize != ((SparseBooleanArray)object).mSize) {
            return false;
        }
        for (int i = 0; i < this.mSize; ++i) {
            if (this.mKeys[i] != ((SparseBooleanArray)object).mKeys[i]) {
                return false;
            }
            if (this.mValues[i] == ((SparseBooleanArray)object).mValues[i]) continue;
            return false;
        }
        return true;
    }

    public boolean get(int n) {
        return this.get(n, false);
    }

    public boolean get(int n, boolean bl) {
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) < 0) {
            return bl;
        }
        return this.mValues[n];
    }

    public int hashCode() {
        int n = this.mSize;
        for (int i = 0; i < this.mSize; ++i) {
            n = n * 31 + this.mKeys[i] | this.mValues[i];
        }
        return n;
    }

    public int indexOfKey(int n) {
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
    }

    public int indexOfValue(boolean bl) {
        for (int i = 0; i < this.mSize; ++i) {
            if (this.mValues[i] != bl) continue;
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

    public void put(int n, boolean bl) {
        int n2 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
        if (n2 >= 0) {
            this.mValues[n2] = bl;
        } else {
            this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, n2, n);
            this.mValues = GrowingArrayUtils.insert(this.mValues, this.mSize, n2, bl);
            ++this.mSize;
        }
    }

    public void removeAt(int n) {
        Object[] arrobject = this.mKeys;
        System.arraycopy(arrobject, n + 1, arrobject, n, this.mSize - (n + 1));
        arrobject = this.mValues;
        System.arraycopy(arrobject, n + 1, arrobject, n, this.mSize - (n + 1));
        --this.mSize;
    }

    public void setKeyAt(int n, int n2) {
        if (n < this.mSize) {
            this.mKeys[n] = n2;
            return;
        }
        throw new ArrayIndexOutOfBoundsException(n);
    }

    public void setValueAt(int n, boolean bl) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        this.mValues[n] = bl;
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

    public boolean valueAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        return this.mValues[n];
    }
}

