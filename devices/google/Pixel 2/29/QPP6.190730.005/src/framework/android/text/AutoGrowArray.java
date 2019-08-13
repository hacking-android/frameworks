/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.text;

import com.android.internal.util.ArrayUtils;
import libcore.util.EmptyArray;

public final class AutoGrowArray {
    private static final int MAX_CAPACITY_TO_BE_KEPT = 10000;
    private static final int MIN_CAPACITY_INCREMENT = 12;

    private static int computeNewCapacity(int n, int n2) {
        int n3 = n < 6 ? 12 : n >> 1;
        if ((n = n3 + n) <= n2) {
            n = n2;
        }
        return n;
    }

    public static class ByteArray {
        private int mSize;
        private byte[] mValues;

        public ByteArray() {
            this(10);
        }

        public ByteArray(int n) {
            this.mValues = n == 0 ? EmptyArray.BYTE : ArrayUtils.newUnpaddedByteArray(n);
            this.mSize = 0;
        }

        private void ensureCapacity(int n) {
            int n2 = this.mSize;
            if ((n = n2 + n) >= this.mValues.length) {
                byte[] arrby = ArrayUtils.newUnpaddedByteArray(AutoGrowArray.computeNewCapacity(n2, n));
                System.arraycopy(this.mValues, 0, arrby, 0, this.mSize);
                this.mValues = arrby;
            }
        }

        public void append(byte by) {
            this.ensureCapacity(1);
            byte[] arrby = this.mValues;
            int n = this.mSize;
            this.mSize = n + 1;
            arrby[n] = by;
        }

        public void clear() {
            this.mSize = 0;
        }

        public void clearWithReleasingLargeArray() {
            this.clear();
            if (this.mValues.length > 10000) {
                this.mValues = EmptyArray.BYTE;
            }
        }

        public byte get(int n) {
            return this.mValues[n];
        }

        public byte[] getRawArray() {
            return this.mValues;
        }

        public void resize(int n) {
            if (n > this.mValues.length) {
                this.ensureCapacity(n - this.mSize);
            }
            this.mSize = n;
        }

        public void set(int n, byte by) {
            this.mValues[n] = by;
        }

        public int size() {
            return this.mSize;
        }
    }

    public static class FloatArray {
        private int mSize;
        private float[] mValues;

        public FloatArray() {
            this(10);
        }

        public FloatArray(int n) {
            this.mValues = n == 0 ? EmptyArray.FLOAT : ArrayUtils.newUnpaddedFloatArray(n);
            this.mSize = 0;
        }

        private void ensureCapacity(int n) {
            int n2 = this.mSize;
            if ((n = n2 + n) >= this.mValues.length) {
                float[] arrf = ArrayUtils.newUnpaddedFloatArray(AutoGrowArray.computeNewCapacity(n2, n));
                System.arraycopy(this.mValues, 0, arrf, 0, this.mSize);
                this.mValues = arrf;
            }
        }

        public void append(float f) {
            this.ensureCapacity(1);
            float[] arrf = this.mValues;
            int n = this.mSize;
            this.mSize = n + 1;
            arrf[n] = f;
        }

        public void clear() {
            this.mSize = 0;
        }

        public void clearWithReleasingLargeArray() {
            this.clear();
            if (this.mValues.length > 10000) {
                this.mValues = EmptyArray.FLOAT;
            }
        }

        public float get(int n) {
            return this.mValues[n];
        }

        public float[] getRawArray() {
            return this.mValues;
        }

        public void resize(int n) {
            if (n > this.mValues.length) {
                this.ensureCapacity(n - this.mSize);
            }
            this.mSize = n;
        }

        public void set(int n, float f) {
            this.mValues[n] = f;
        }

        public int size() {
            return this.mSize;
        }
    }

    public static class IntArray {
        private int mSize;
        private int[] mValues;

        public IntArray() {
            this(10);
        }

        public IntArray(int n) {
            this.mValues = n == 0 ? EmptyArray.INT : ArrayUtils.newUnpaddedIntArray(n);
            this.mSize = 0;
        }

        private void ensureCapacity(int n) {
            int n2 = this.mSize;
            if ((n = n2 + n) >= this.mValues.length) {
                int[] arrn = ArrayUtils.newUnpaddedIntArray(AutoGrowArray.computeNewCapacity(n2, n));
                System.arraycopy(this.mValues, 0, arrn, 0, this.mSize);
                this.mValues = arrn;
            }
        }

        public void append(int n) {
            this.ensureCapacity(1);
            int[] arrn = this.mValues;
            int n2 = this.mSize;
            this.mSize = n2 + 1;
            arrn[n2] = n;
        }

        public void clear() {
            this.mSize = 0;
        }

        public void clearWithReleasingLargeArray() {
            this.clear();
            if (this.mValues.length > 10000) {
                this.mValues = EmptyArray.INT;
            }
        }

        public int get(int n) {
            return this.mValues[n];
        }

        public int[] getRawArray() {
            return this.mValues;
        }

        public void resize(int n) {
            if (n > this.mValues.length) {
                this.ensureCapacity(n - this.mSize);
            }
            this.mSize = n;
        }

        public void set(int n, int n2) {
            this.mValues[n] = n2;
        }

        public int size() {
            return this.mSize;
        }
    }

}

