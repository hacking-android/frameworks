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

public class LongSparseLongArray
implements Cloneable {
    @UnsupportedAppUsage(maxTargetSdk=28)
    private long[] mKeys;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mSize;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private long[] mValues;

    public LongSparseLongArray() {
        this(10);
    }

    public LongSparseLongArray(int n) {
        if (n == 0) {
            this.mKeys = EmptyArray.LONG;
            this.mValues = EmptyArray.LONG;
        } else {
            this.mKeys = ArrayUtils.newUnpaddedLongArray(n);
            this.mValues = new long[this.mKeys.length];
        }
        this.mSize = 0;
    }

    public void append(long l, long l2) {
        int n = this.mSize;
        if (n != 0 && l <= this.mKeys[n - 1]) {
            this.put(l, l2);
            return;
        }
        this.mKeys = GrowingArrayUtils.append(this.mKeys, this.mSize, l);
        this.mValues = GrowingArrayUtils.append(this.mValues, this.mSize, l2);
        ++this.mSize;
    }

    public void clear() {
        this.mSize = 0;
    }

    public LongSparseLongArray clone() {
        LongSparseLongArray longSparseLongArray;
        LongSparseLongArray longSparseLongArray2 = null;
        longSparseLongArray2 = longSparseLongArray = (LongSparseLongArray)super.clone();
        longSparseLongArray.mKeys = (long[])this.mKeys.clone();
        longSparseLongArray2 = longSparseLongArray;
        try {
            longSparseLongArray.mValues = (long[])this.mValues.clone();
            longSparseLongArray2 = longSparseLongArray;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return longSparseLongArray2;
    }

    public void delete(long l) {
        int n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
        if (n >= 0) {
            this.removeAt(n);
        }
    }

    public long get(long l) {
        return this.get(l, 0L);
    }

    public long get(long l, long l2) {
        int n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
        if (n < 0) {
            return l2;
        }
        return this.mValues[n];
    }

    public int indexOfKey(long l) {
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
    }

    public int indexOfValue(long l) {
        for (int i = 0; i < this.mSize; ++i) {
            if (this.mValues[i] != l) continue;
            return i;
        }
        return -1;
    }

    public long keyAt(int n) {
        if (n >= this.mSize && UtilConfig.sThrowExceptionForUpperArrayOutOfBounds) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        return this.mKeys[n];
    }

    public void put(long l, long l2) {
        int n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
        if (n >= 0) {
            this.mValues[n] = l2;
        } else {
            this.mKeys = GrowingArrayUtils.insert(this.mKeys, this.mSize, n, l);
            this.mValues = GrowingArrayUtils.insert(this.mValues, this.mSize, n, l2);
            ++this.mSize;
        }
    }

    public void removeAt(int n) {
        long[] arrl = this.mKeys;
        System.arraycopy(arrl, n + 1, arrl, n, this.mSize - (n + 1));
        arrl = this.mValues;
        System.arraycopy(arrl, n + 1, arrl, n, this.mSize - (n + 1));
        --this.mSize;
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

