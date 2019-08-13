/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.util;

import android.util.ContainerHelpers;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import libcore.util.EmptyArray;

public class IntArray
implements Cloneable {
    private static final int MIN_CAPACITY_INCREMENT = 12;
    private int mSize;
    private int[] mValues;

    public IntArray() {
        this(10);
    }

    public IntArray(int n) {
        this.mValues = n == 0 ? EmptyArray.INT : ArrayUtils.newUnpaddedIntArray(n);
        this.mSize = 0;
    }

    private IntArray(int[] arrn, int n) {
        this.mValues = arrn;
        this.mSize = Preconditions.checkArgumentInRange(n, 0, arrn.length, "size");
    }

    private void ensureCapacity(int n) {
        int n2 = this.mSize;
        int n3 = n2 + n;
        if (n3 >= this.mValues.length) {
            n = n2 < 6 ? 12 : n2 >> 1;
            if ((n += n2) <= n3) {
                n = n3;
            }
            int[] arrn = ArrayUtils.newUnpaddedIntArray(n);
            System.arraycopy(this.mValues, 0, arrn, 0, n2);
            this.mValues = arrn;
        }
    }

    public static IntArray fromArray(int[] arrn, int n) {
        return IntArray.wrap(Arrays.copyOf(arrn, n));
    }

    public static IntArray wrap(int[] arrn) {
        return new IntArray(arrn, arrn.length);
    }

    public void add(int n) {
        this.add(this.mSize, n);
    }

    public void add(int n, int n2) {
        this.ensureCapacity(1);
        int n3 = this.mSize;
        int n4 = n3 - n;
        this.mSize = n3 + 1;
        ArrayUtils.checkBounds(this.mSize, n);
        if (n4 != 0) {
            int[] arrn = this.mValues;
            System.arraycopy(arrn, n, arrn, n + 1, n4);
        }
        this.mValues[n] = n2;
    }

    public void addAll(IntArray intArray) {
        int n = intArray.mSize;
        this.ensureCapacity(n);
        System.arraycopy(intArray.mValues, 0, this.mValues, this.mSize, n);
        this.mSize += n;
    }

    public int binarySearch(int n) {
        return ContainerHelpers.binarySearch(this.mValues, this.mSize, n);
    }

    public void clear() {
        this.mSize = 0;
    }

    public IntArray clone() throws CloneNotSupportedException {
        IntArray intArray = (IntArray)super.clone();
        intArray.mValues = (int[])this.mValues.clone();
        return intArray;
    }

    public int get(int n) {
        ArrayUtils.checkBounds(this.mSize, n);
        return this.mValues[n];
    }

    public int indexOf(int n) {
        int n2 = this.mSize;
        for (int i = 0; i < n2; ++i) {
            if (this.mValues[i] != n) continue;
            return i;
        }
        return -1;
    }

    public void remove(int n) {
        ArrayUtils.checkBounds(this.mSize, n);
        int[] arrn = this.mValues;
        System.arraycopy(arrn, n + 1, arrn, n, this.mSize - n - 1);
        --this.mSize;
    }

    public void resize(int n) {
        Preconditions.checkArgumentNonnegative(n);
        int[] arrn = this.mValues;
        if (n <= arrn.length) {
            Arrays.fill(arrn, n, arrn.length, 0);
        } else {
            this.ensureCapacity(n - this.mSize);
        }
        this.mSize = n;
    }

    public void set(int n, int n2) {
        ArrayUtils.checkBounds(this.mSize, n);
        this.mValues[n] = n2;
    }

    public int size() {
        return this.mSize;
    }

    public int[] toArray() {
        return Arrays.copyOf(this.mValues, this.mSize);
    }
}

