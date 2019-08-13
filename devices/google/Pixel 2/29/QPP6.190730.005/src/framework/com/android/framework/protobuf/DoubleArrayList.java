/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.AbstractProtobufList;
import com.android.framework.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class DoubleArrayList
extends AbstractProtobufList<Double>
implements Internal.DoubleList,
RandomAccess {
    private static final DoubleArrayList EMPTY_LIST = new DoubleArrayList();
    private double[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    DoubleArrayList() {
        this(new double[10], 0);
    }

    private DoubleArrayList(double[] arrd, int n) {
        this.array = arrd;
        this.size = n;
    }

    private void addDouble(int n, double d) {
        int n2;
        this.ensureIsMutable();
        if (n >= 0 && n <= (n2 = this.size)) {
            double[] arrd = this.array;
            if (n2 < arrd.length) {
                System.arraycopy(arrd, n, arrd, n + 1, n2 - n);
            } else {
                double[] arrd2 = new double[n2 * 3 / 2 + 1];
                System.arraycopy(arrd, 0, arrd2, 0, n);
                System.arraycopy(this.array, n, arrd2, n + 1, this.size - n);
                this.array = arrd2;
            }
            this.array[n] = d;
            ++this.size;
            ++this.modCount;
            return;
        }
        throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(n));
    }

    public static DoubleArrayList emptyList() {
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
    public void add(int n, Double d) {
        this.addDouble(n, d);
    }

    @Override
    public boolean addAll(Collection<? extends Double> doubleArrayList) {
        this.ensureIsMutable();
        if (doubleArrayList != null) {
            if (!(doubleArrayList instanceof DoubleArrayList)) {
                return super.addAll(doubleArrayList);
            }
            doubleArrayList = doubleArrayList;
            int n = doubleArrayList.size;
            if (n == 0) {
                return false;
            }
            int n2 = this.size;
            if (Integer.MAX_VALUE - n2 >= n) {
                double[] arrd = this.array;
                if ((n2 += n) > arrd.length) {
                    this.array = Arrays.copyOf(arrd, n2);
                }
                System.arraycopy(doubleArrayList.array, 0, this.array, this.size, doubleArrayList.size);
                this.size = n2;
                ++this.modCount;
                return true;
            }
            throw new OutOfMemoryError();
        }
        throw new NullPointerException();
    }

    @Override
    public void addDouble(double d) {
        this.addDouble(this.size, d);
    }

    @Override
    public boolean equals(Object arrd) {
        if (this == arrd) {
            return true;
        }
        if (!(arrd instanceof DoubleArrayList)) {
            return super.equals(arrd);
        }
        arrd = (DoubleArrayList)arrd;
        if (this.size != arrd.size) {
            return false;
        }
        arrd = arrd.array;
        for (int i = 0; i < this.size; ++i) {
            if (this.array[i] == arrd[i]) continue;
            return false;
        }
        return true;
    }

    @Override
    public Double get(int n) {
        return this.getDouble(n);
    }

    @Override
    public double getDouble(int n) {
        this.ensureIndexInRange(n);
        return this.array[n];
    }

    @Override
    public int hashCode() {
        int n = 1;
        for (int i = 0; i < this.size; ++i) {
            n = n * 31 + Internal.hashLong(Double.doubleToLongBits(this.array[i]));
        }
        return n;
    }

    @Override
    public Internal.DoubleList mutableCopyWithCapacity(int n) {
        if (n >= this.size) {
            return new DoubleArrayList(Arrays.copyOf(this.array, n), this.size);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Double remove(int n) {
        this.ensureIsMutable();
        this.ensureIndexInRange(n);
        double[] arrd = this.array;
        double d = arrd[n];
        System.arraycopy(arrd, n + 1, arrd, n, this.size - n);
        --this.size;
        ++this.modCount;
        return d;
    }

    @Override
    public boolean remove(Object arrd) {
        this.ensureIsMutable();
        for (int i = 0; i < this.size; ++i) {
            if (!arrd.equals(this.array[i])) continue;
            arrd = this.array;
            System.arraycopy(arrd, i + 1, arrd, i, this.size - i);
            --this.size;
            ++this.modCount;
            return true;
        }
        return false;
    }

    @Override
    public Double set(int n, Double d) {
        return this.setDouble(n, d);
    }

    @Override
    public double setDouble(int n, double d) {
        this.ensureIsMutable();
        this.ensureIndexInRange(n);
        double[] arrd = this.array;
        double d2 = arrd[n];
        arrd[n] = d;
        return d2;
    }

    @Override
    public int size() {
        return this.size;
    }
}

