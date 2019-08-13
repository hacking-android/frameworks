/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

public class RingBufferIndices {
    private final int mCapacity;
    private int mSize;
    private int mStart;

    public RingBufferIndices(int n) {
        this.mCapacity = n;
    }

    public int add() {
        int n = this.mSize;
        int n2 = this.mCapacity;
        if (n < n2) {
            n2 = this.mSize;
            this.mSize = n + 1;
            return n2;
        }
        n = this.mStart++;
        if (this.mStart == n2) {
            this.mStart = 0;
        }
        return n;
    }

    public void clear() {
        this.mStart = 0;
        this.mSize = 0;
    }

    public int indexOf(int n) {
        int n2 = this.mStart + n;
        int n3 = this.mCapacity;
        n = n2;
        if (n2 >= n3) {
            n = n2 - n3;
        }
        return n;
    }

    public int size() {
        return this.mSize;
    }
}

