/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.AbstractProtobufList;
import com.android.framework.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class IntArrayList
extends AbstractProtobufList<Integer>
implements Internal.IntList,
RandomAccess {
    private static final IntArrayList EMPTY_LIST = new IntArrayList();
    private int[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    IntArrayList() {
        this(new int[10], 0);
    }

    private IntArrayList(int[] arrn, int n) {
        this.array = arrn;
        this.size = n;
    }

    private void addInt(int n, int n2) {
        int n3;
        this.ensureIsMutable();
        if (n >= 0 && n <= (n3 = this.size)) {
            int[] arrn = this.array;
            if (n3 < arrn.length) {
                System.arraycopy(arrn, n, arrn, n + 1, n3 - n);
            } else {
                int[] arrn2 = new int[n3 * 3 / 2 + 1];
                System.arraycopy(arrn, 0, arrn2, 0, n);
                System.arraycopy(this.array, n, arrn2, n + 1, this.size - n);
                this.array = arrn2;
            }
            this.array[n] = n2;
            ++this.size;
            ++this.modCount;
            return;
        }
        throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(n));
    }

    public static IntArrayList emptyList() {
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
    public void add(int n, Integer n2) {
        this.addInt(n, n2);
    }

    @Override
    public boolean addAll(Collection<? extends Integer> arrn) {
        this.ensureIsMutable();
        if (arrn != null) {
            if (!(arrn instanceof IntArrayList)) {
                return super.addAll(arrn);
            }
            IntArrayList intArrayList = (IntArrayList)arrn;
            int n = intArrayList.size;
            if (n == 0) {
                return false;
            }
            int n2 = this.size;
            if (Integer.MAX_VALUE - n2 >= n) {
                arrn = this.array;
                if ((n2 += n) > arrn.length) {
                    this.array = Arrays.copyOf(arrn, n2);
                }
                System.arraycopy(intArrayList.array, 0, this.array, this.size, intArrayList.size);
                this.size = n2;
                ++this.modCount;
                return true;
            }
            throw new OutOfMemoryError();
        }
        throw new NullPointerException();
    }

    @Override
    public void addInt(int n) {
        this.addInt(this.size, n);
    }

    @Override
    public boolean equals(Object arrn) {
        if (this == arrn) {
            return true;
        }
        if (!(arrn instanceof IntArrayList)) {
            return super.equals(arrn);
        }
        arrn = (IntArrayList)arrn;
        if (this.size != arrn.size) {
            return false;
        }
        arrn = arrn.array;
        for (int i = 0; i < this.size; ++i) {
            if (this.array[i] == arrn[i]) continue;
            return false;
        }
        return true;
    }

    @Override
    public Integer get(int n) {
        return this.getInt(n);
    }

    @Override
    public int getInt(int n) {
        this.ensureIndexInRange(n);
        return this.array[n];
    }

    @Override
    public int hashCode() {
        int n = 1;
        for (int i = 0; i < this.size; ++i) {
            n = n * 31 + this.array[i];
        }
        return n;
    }

    @Override
    public Internal.IntList mutableCopyWithCapacity(int n) {
        if (n >= this.size) {
            return new IntArrayList(Arrays.copyOf(this.array, n), this.size);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Integer remove(int n) {
        this.ensureIsMutable();
        this.ensureIndexInRange(n);
        int[] arrn = this.array;
        int n2 = arrn[n];
        System.arraycopy(arrn, n + 1, arrn, n, this.size - n);
        --this.size;
        ++this.modCount;
        return n2;
    }

    @Override
    public boolean remove(Object arrn) {
        this.ensureIsMutable();
        for (int i = 0; i < this.size; ++i) {
            if (!arrn.equals(this.array[i])) continue;
            arrn = this.array;
            System.arraycopy(arrn, i + 1, arrn, i, this.size - i);
            --this.size;
            ++this.modCount;
            return true;
        }
        return false;
    }

    @Override
    public Integer set(int n, Integer n2) {
        return this.setInt(n, n2);
    }

    @Override
    public int setInt(int n, int n2) {
        this.ensureIsMutable();
        this.ensureIndexInRange(n);
        int[] arrn = this.array;
        int n3 = arrn[n];
        arrn[n] = n2;
        return n3;
    }

    @Override
    public int size() {
        return this.size;
    }
}

