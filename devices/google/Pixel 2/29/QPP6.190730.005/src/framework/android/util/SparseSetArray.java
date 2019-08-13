/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.util.ArraySet;
import android.util.SparseArray;

public class SparseSetArray<T> {
    private final SparseArray<ArraySet<T>> mData = new SparseArray();

    public boolean add(int n, T t) {
        ArraySet<T> arraySet = this.mData.get(n);
        ArraySet<Object> arraySet2 = arraySet;
        if (arraySet == null) {
            arraySet2 = new ArraySet();
            this.mData.put(n, arraySet2);
        }
        if (arraySet2.contains(t)) {
            return true;
        }
        arraySet2.add(t);
        return false;
    }

    public void clear() {
        this.mData.clear();
    }

    public boolean contains(int n, T t) {
        ArraySet<T> arraySet = this.mData.get(n);
        if (arraySet == null) {
            return false;
        }
        return arraySet.contains(t);
    }

    public ArraySet<T> get(int n) {
        return this.mData.get(n);
    }

    public int keyAt(int n) {
        return this.mData.keyAt(n);
    }

    public void remove(int n) {
        this.mData.remove(n);
    }

    public boolean remove(int n, T t) {
        ArraySet<T> arraySet = this.mData.get(n);
        if (arraySet == null) {
            return false;
        }
        boolean bl = arraySet.remove(t);
        if (arraySet.size() == 0) {
            this.mData.remove(n);
        }
        return bl;
    }

    public int size() {
        return this.mData.size();
    }

    public int sizeAt(int n) {
        ArraySet<T> arraySet = this.mData.valueAt(n);
        if (arraySet == null) {
            return 0;
        }
        return arraySet.size();
    }

    public T valueAt(int n, int n2) {
        return this.mData.valueAt(n).valueAt(n2);
    }
}

