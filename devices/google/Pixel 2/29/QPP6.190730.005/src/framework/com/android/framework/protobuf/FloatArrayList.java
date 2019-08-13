/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.AbstractProtobufList;
import com.android.framework.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class FloatArrayList
extends AbstractProtobufList<Float>
implements Internal.FloatList,
RandomAccess {
    private static final FloatArrayList EMPTY_LIST = new FloatArrayList();
    private float[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    FloatArrayList() {
        this(new float[10], 0);
    }

    private FloatArrayList(float[] arrf, int n) {
        this.array = arrf;
        this.size = n;
    }

    private void addFloat(int n, float f) {
        int n2;
        this.ensureIsMutable();
        if (n >= 0 && n <= (n2 = this.size)) {
            float[] arrf = this.array;
            if (n2 < arrf.length) {
                System.arraycopy(arrf, n, arrf, n + 1, n2 - n);
            } else {
                float[] arrf2 = new float[n2 * 3 / 2 + 1];
                System.arraycopy(arrf, 0, arrf2, 0, n);
                System.arraycopy(this.array, n, arrf2, n + 1, this.size - n);
                this.array = arrf2;
            }
            this.array[n] = f;
            ++this.size;
            ++this.modCount;
            return;
        }
        throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(n));
    }

    public static FloatArrayList emptyList() {
        return EMPTY_LIST;
    }

    private void ensureIndexInRange(int n) {
        if (n >= 0 && n < this.size) {
            return;
        }
        throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(n));
    }

    private String makeOutOfBoundsExceptionMessage(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index:");
        stringBuilder.append(n);
        stringBuilder.append(", Size:");
        stringBuilder.append(this.size);
        return stringBuilder.toString();
    }

    @Override
    public void add(int n, Float f) {
        this.addFloat(n, f.floatValue());
    }

    @Override
    public boolean addAll(Collection<? extends Float> floatArrayList) {
        this.ensureIsMutable();
        if (floatArrayList != null) {
            if (!(floatArrayList instanceof FloatArrayList)) {
                return super.addAll(floatArrayList);
            }
            floatArrayList = floatArrayList;
            int n = floatArrayList.size;
            if (n == 0) {
                return false;
            }
            int n2 = this.size;
            if (Integer.MAX_VALUE - n2 >= n) {
                float[] arrf = this.array;
                if ((n2 += n) > arrf.length) {
                    this.array = Arrays.copyOf(arrf, n2);
                }
                System.arraycopy(floatArrayList.array, 0, this.array, this.size, floatArrayList.size);
                this.size = n2;
                ++this.modCount;
                return true;
            }
            throw new OutOfMemoryError();
        }
        throw new NullPointerException();
    }

    @Override
    public void addFloat(float f) {
        this.addFloat(this.size, f);
    }

    @Override
    public boolean equals(Object arrf) {
        if (this == arrf) {
            return true;
        }
        if (!(arrf instanceof FloatArrayList)) {
            return super.equals(arrf);
        }
        arrf = (FloatArrayList)arrf;
        if (this.size != arrf.size) {
            return false;
        }
        arrf = arrf.array;
        for (int i = 0; i < this.size; ++i) {
            if (this.array[i] == arrf[i]) continue;
            return false;
        }
        return true;
    }

    @Override
    public Float get(int n) {
        return Float.valueOf(this.getFloat(n));
    }

    @Override
    public float getFloat(int n) {
        this.ensureIndexInRange(n);
        return this.array[n];
    }

    @Override
    public int hashCode() {
        int n = 1;
        for (int i = 0; i < this.size; ++i) {
            n = n * 31 + Float.floatToIntBits(this.array[i]);
        }
        return n;
    }

    @Override
    public Internal.FloatList mutableCopyWithCapacity(int n) {
        if (n >= this.size) {
            return new FloatArrayList(Arrays.copyOf(this.array, n), this.size);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Float remove(int n) {
        this.ensureIsMutable();
        this.ensureIndexInRange(n);
        float[] arrf = this.array;
        float f = arrf[n];
        System.arraycopy(arrf, n + 1, arrf, n, this.size - n);
        --this.size;
        ++this.modCount;
        return Float.valueOf(f);
    }

    @Override
    public boolean remove(Object arrf) {
        this.ensureIsMutable();
        for (int i = 0; i < this.size; ++i) {
            if (!arrf.equals(Float.valueOf(this.array[i]))) continue;
            arrf = this.array;
            System.arraycopy(arrf, i + 1, arrf, i, this.size - i);
            --this.size;
            ++this.modCount;
            return true;
        }
        return false;
    }

    @Override
    public Float set(int n, Float f) {
        return Float.valueOf(this.setFloat(n, f.floatValue()));
    }

    @Override
    public float setFloat(int n, float f) {
        this.ensureIsMutable();
        this.ensureIndexInRange(n);
        float[] arrf = this.array;
        float f2 = arrf[n];
        arrf[n] = f;
        return f2;
    }

    @Override
    public int size() {
        return this.size;
    }
}

