/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import libcore.util.EmptyArray;

public class LongArray
implements Cloneable {
    private static final int MIN_CAPACITY_INCREMENT = 12;
    private int mSize;
    private long[] mValues;

    @UnsupportedAppUsage
    public LongArray() {
        this(10);
    }

    public LongArray(int n) {
        this.mValues = n == 0 ? EmptyArray.LONG : ArrayUtils.newUnpaddedLongArray(n);
        this.mSize = 0;
    }

    private LongArray(long[] arrl, int n) {
        this.mValues = arrl;
        this.mSize = Preconditions.checkArgumentInRange(n, 0, arrl.length, "size");
    }

    public static boolean elementsEqual(LongArray longArray, LongArray longArray2) {
        boolean bl = true;
        if (longArray != null && longArray2 != null) {
            if (longArray.mSize != longArray2.mSize) {
                return false;
            }
            for (int i = 0; i < longArray.mSize; ++i) {
                if (longArray.get(i) == longArray2.get(i)) continue;
                return false;
            }
            return true;
        }
        if (longArray != longArray2) {
            bl = false;
        }
        return bl;
    }

    private void ensureCapacity(int n) {
        int n2 = this.mSize;
        int n3 = n2 + n;
        if (n3 >= this.mValues.length) {
            n = n2 < 6 ? 12 : n2 >> 1;
            if ((n += n2) <= n3) {
                n = n3;
            }
            long[] arrl = ArrayUtils.newUnpaddedLongArray(n);
            System.arraycopy(this.mValues, 0, arrl, 0, n2);
            this.mValues = arrl;
        }
    }

    public static LongArray fromArray(long[] arrl, int n) {
        return LongArray.wrap(Arrays.copyOf(arrl, n));
    }

    public static LongArray wrap(long[] arrl) {
        return new LongArray(arrl, arrl.length);
    }

    @UnsupportedAppUsage
    public void add(int n, long l) {
        this.ensureCapacity(1);
        int n2 = this.mSize;
        int n3 = n2 - n;
        this.mSize = n2 + 1;
        ArrayUtils.checkBounds(this.mSize, n);
        if (n3 != 0) {
            long[] arrl = this.mValues;
            System.arraycopy(arrl, n, arrl, n + 1, n3);
        }
        this.mValues[n] = l;
    }

    public void add(long l) {
        this.add(this.mSize, l);
    }

    public void addAll(LongArray longArray) {
        int n = longArray.mSize;
        this.ensureCapacity(n);
        System.arraycopy(longArray.mValues, 0, this.mValues, this.mSize, n);
        this.mSize += n;
    }

    public void clear() {
        this.mSize = 0;
    }

    public LongArray clone() {
        LongArray longArray;
        LongArray longArray2 = null;
        longArray2 = longArray = (LongArray)super.clone();
        try {
            longArray.mValues = (long[])this.mValues.clone();
            longArray2 = longArray;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return longArray2;
    }

    @UnsupportedAppUsage
    public long get(int n) {
        ArrayUtils.checkBounds(this.mSize, n);
        return this.mValues[n];
    }

    public int indexOf(long l) {
        int n = this.mSize;
        for (int i = 0; i < n; ++i) {
            if (this.mValues[i] != l) continue;
            return i;
        }
        return -1;
    }

    public void remove(int n) {
        ArrayUtils.checkBounds(this.mSize, n);
        long[] arrl = this.mValues;
        System.arraycopy(arrl, n + 1, arrl, n, this.mSize - n - 1);
        --this.mSize;
    }

    public void resize(int n) {
        Preconditions.checkArgumentNonnegative(n);
        long[] arrl = this.mValues;
        if (n <= arrl.length) {
            Arrays.fill(arrl, n, arrl.length, 0L);
        } else {
            this.ensureCapacity(n - this.mSize);
        }
        this.mSize = n;
    }

    public void set(int n, long l) {
        ArrayUtils.checkBounds(this.mSize, n);
        this.mValues[n] = l;
    }

    @UnsupportedAppUsage
    public int size() {
        return this.mSize;
    }

    public long[] toArray() {
        return Arrays.copyOf(this.mValues, this.mSize);
    }
}

