/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.AbstractProtobufList;
import com.android.framework.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class BooleanArrayList
extends AbstractProtobufList<Boolean>
implements Internal.BooleanList,
RandomAccess {
    private static final BooleanArrayList EMPTY_LIST = new BooleanArrayList();
    private boolean[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    BooleanArrayList() {
        this(new boolean[10], 0);
    }

    private BooleanArrayList(boolean[] arrbl, int n) {
        this.array = arrbl;
        this.size = n;
    }

    private void addBoolean(int n, boolean bl) {
        int n2;
        this.ensureIsMutable();
        if (n >= 0 && n <= (n2 = this.size)) {
            boolean[] arrbl = this.array;
            if (n2 < arrbl.length) {
                System.arraycopy(arrbl, n, arrbl, n + 1, n2 - n);
            } else {
                boolean[] arrbl2 = new boolean[n2 * 3 / 2 + 1];
                System.arraycopy(arrbl, 0, arrbl2, 0, n);
                System.arraycopy(this.array, n, arrbl2, n + 1, this.size - n);
                this.array = arrbl2;
            }
            this.array[n] = bl;
            ++this.size;
            ++this.modCount;
            return;
        }
        throw new IndexOutOfBoundsException(this.makeOutOfBoundsExceptionMessage(n));
    }

    public static BooleanArrayList emptyList() {
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
    public void add(int n, Boolean bl) {
        this.addBoolean(n, bl);
    }

    @Override
    public boolean addAll(Collection<? extends Boolean> arrbl) {
        this.ensureIsMutable();
        if (arrbl != null) {
            if (!(arrbl instanceof BooleanArrayList)) {
                return super.addAll(arrbl);
            }
            BooleanArrayList booleanArrayList = (BooleanArrayList)arrbl;
            int n = booleanArrayList.size;
            if (n == 0) {
                return false;
            }
            int n2 = this.size;
            if (Integer.MAX_VALUE - n2 >= n) {
                arrbl = this.array;
                if ((n2 += n) > arrbl.length) {
                    this.array = Arrays.copyOf(arrbl, n2);
                }
                System.arraycopy(booleanArrayList.array, 0, this.array, this.size, booleanArrayList.size);
                this.size = n2;
                ++this.modCount;
                return true;
            }
            throw new OutOfMemoryError();
        }
        throw new NullPointerException();
    }

    @Override
    public void addBoolean(boolean bl) {
        this.addBoolean(this.size, bl);
    }

    @Override
    public boolean equals(Object arrbl) {
        if (this == arrbl) {
            return true;
        }
        if (!(arrbl instanceof BooleanArrayList)) {
            return super.equals(arrbl);
        }
        arrbl = (BooleanArrayList)arrbl;
        if (this.size != arrbl.size) {
            return false;
        }
        arrbl = arrbl.array;
        for (int i = 0; i < this.size; ++i) {
            if (this.array[i] == arrbl[i]) continue;
            return false;
        }
        return true;
    }

    @Override
    public Boolean get(int n) {
        return this.getBoolean(n);
    }

    @Override
    public boolean getBoolean(int n) {
        this.ensureIndexInRange(n);
        return this.array[n];
    }

    @Override
    public int hashCode() {
        int n = 1;
        for (int i = 0; i < this.size; ++i) {
            n = n * 31 + Internal.hashBoolean(this.array[i]);
        }
        return n;
    }

    @Override
    public Internal.BooleanList mutableCopyWithCapacity(int n) {
        if (n >= this.size) {
            return new BooleanArrayList(Arrays.copyOf(this.array, n), this.size);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Boolean remove(int n) {
        this.ensureIsMutable();
        this.ensureIndexInRange(n);
        boolean[] arrbl = this.array;
        boolean bl = arrbl[n];
        System.arraycopy(arrbl, n + 1, arrbl, n, this.size - n);
        --this.size;
        ++this.modCount;
        return bl;
    }

    @Override
    public boolean remove(Object arrbl) {
        this.ensureIsMutable();
        for (int i = 0; i < this.size; ++i) {
            if (!arrbl.equals(this.array[i])) continue;
            arrbl = this.array;
            System.arraycopy(arrbl, i + 1, arrbl, i, this.size - i);
            --this.size;
            ++this.modCount;
            return true;
        }
        return false;
    }

    @Override
    public Boolean set(int n, Boolean bl) {
        return this.setBoolean(n, bl);
    }

    @Override
    public boolean setBoolean(int n, boolean bl) {
        this.ensureIsMutable();
        this.ensureIndexInRange(n);
        boolean[] arrbl = this.array;
        boolean bl2 = arrbl[n];
        arrbl[n] = bl;
        return bl2;
    }

    @Override
    public int size() {
        return this.size;
    }
}

