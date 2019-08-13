/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.AbstractProtobufList;
import com.android.framework.protobuf.IntArrayList;
import com.android.framework.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class LongArrayList
extends AbstractProtobufList<Long>
implements Internal.LongList,
RandomAccess {
    private static final LongArrayList EMPTY_LIST = new LongArrayList();
    private long[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    LongArrayList() {
        this(new long[10], 0);
    }

    private LongArrayList(long[] arrl, int n) {
        this.array = arrl;
        this.size = n;
    }

    private void addLong(int n, long l) {
        int n2;
        this.ensureIsMutable();
        if (n >= 0 && n <= (n2 = this.size)) {
            long[] arrl = this.array;
            if (n2 < arrl.length) {
                System.arraycopy(arrl, n, arrl, n + 1, n2 - n);
            } else {
                long[] arrl2 = new long[n2 * 3 / 2 + 1];
                System.arraycopy(arrl, 0, arrl2, 0, n);
                System.arraycopy(this.array, n, arrl2, n + 1, this.size - n);
                this.array = arrl2;
            }
            this.array[n] = l;
            ++this.size;
            ++this.modCount;
            return;
        }
        throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(n));
    }

    public static LongArrayList emptyList() {
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
    public void add(int n, Long l) {
        this.addLong(n, l);
    }

    @Override
    public boolean addAll(Collection<? extends Long> longArrayList) {
        this.ensureIsMutable();
        if (longArrayList != null) {
            if (!(longArrayList instanceof LongArrayList)) {
                return super.addAll(longArrayList);
            }
            longArrayList = longArrayList;
            int n = longArrayList.size;
            if (n == 0) {
                return false;
            }
            int n2 = this.size;
            if (Integer.MAX_VALUE - n2 >= n) {
                long[] arrl = this.array;
                if ((n = n2 + n) > arrl.length) {
                    this.array = Arrays.copyOf(arrl, n);
                }
                System.arraycopy(longArrayList.array, 0, this.array, this.size, longArrayList.size);
                this.size = n;
                ++this.modCount;
                return true;
            }
            throw new OutOfMemoryError();
        }
        throw new NullPointerException();
    }

    @Override
    public void addLong(long l) {
        this.addLong(this.size, l);
    }

    @Override
    public boolean equals(Object arrl) {
        if (this == arrl) {
            return true;
        }
        if (!(arrl instanceof IntArrayList)) {
            return super.equals(arrl);
        }
        arrl = (LongArrayList)arrl;
        if (this.size != arrl.size) {
            return false;
        }
        arrl = arrl.array;
        for (int i = 0; i < this.size; ++i) {
            if (this.array[i] == arrl[i]) continue;
            return false;
        }
        return true;
    }

    @Override
    public Long get(int n) {
        return this.getLong(n);
    }

    @Override
    public long getLong(int n) {
        this.ensureIndexInRange(n);
        return this.array[n];
    }

    @Override
    public int hashCode() {
        int n = 1;
        for (int i = 0; i < this.size; ++i) {
            n = n * 31 + Internal.hashLong(this.array[i]);
        }
        return n;
    }

    @Override
    public Internal.LongList mutableCopyWithCapacity(int n) {
        if (n >= this.size) {
            return new LongArrayList(Arrays.copyOf(this.array, n), this.size);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Long remove(int n) {
        this.ensureIsMutable();
        this.ensureIndexInRange(n);
        long[] arrl = this.array;
        long l = arrl[n];
        System.arraycopy(arrl, n + 1, arrl, n, this.size - n);
        --this.size;
        ++this.modCount;
        return l;
    }

    @Override
    public boolean remove(Object arrl) {
        this.ensureIsMutable();
        for (int i = 0; i < this.size; ++i) {
            if (!arrl.equals(this.array[i])) continue;
            arrl = this.array;
            System.arraycopy(arrl, i + 1, arrl, i, this.size - i);
            --this.size;
            ++this.modCount;
            return true;
        }
        return false;
    }

    @Override
    public Long set(int n, Long l) {
        return this.setLong(n, l);
    }

    @Override
    public long setLong(int n, long l) {
        this.ensureIsMutable();
        this.ensureIndexInRange(n);
        long[] arrl = this.array;
        long l2 = arrl[n];
        arrl[n] = l;
        return l2;
    }

    @Override
    public int size() {
        return this.size;
    }
}

