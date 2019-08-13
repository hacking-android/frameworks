/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.util.ArrayMap;
import android.util.SparseArray;

public class ProcessMap<E> {
    final ArrayMap<String, SparseArray<E>> mMap = new ArrayMap();

    public E get(String object, int n) {
        if ((object = this.mMap.get(object)) == null) {
            return null;
        }
        return ((SparseArray)object).get(n);
    }

    public ArrayMap<String, SparseArray<E>> getMap() {
        return this.mMap;
    }

    public E put(String string2, int n, E e) {
        SparseArray<E> sparseArray;
        SparseArray<E> sparseArray2 = sparseArray = this.mMap.get(string2);
        if (sparseArray == null) {
            sparseArray2 = new SparseArray(2);
            this.mMap.put(string2, sparseArray2);
        }
        sparseArray2.put(n, e);
        return e;
    }

    public E remove(String string2, int n) {
        SparseArray<E> sparseArray = this.mMap.get(string2);
        if (sparseArray != null) {
            E e = sparseArray.removeReturnOld(n);
            if (sparseArray.size() == 0) {
                this.mMap.remove(string2);
            }
            return e;
        }
        return null;
    }

    public int size() {
        return this.mMap.size();
    }
}

