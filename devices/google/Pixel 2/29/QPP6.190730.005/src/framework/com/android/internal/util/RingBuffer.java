/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import com.android.internal.util.Preconditions;
import java.lang.reflect.Array;
import java.util.Arrays;

public class RingBuffer<T> {
    private final T[] mBuffer;
    private long mCursor = 0L;

    public RingBuffer(Class<T> class_, int n) {
        Preconditions.checkArgumentPositive(n, "A RingBuffer cannot have 0 capacity");
        this.mBuffer = (Object[])Array.newInstance(class_, n);
    }

    private int indexOf(long l) {
        return (int)Math.abs(l % (long)this.mBuffer.length);
    }

    public void append(T t) {
        T[] arrT = this.mBuffer;
        long l = this.mCursor;
        this.mCursor = 1L + l;
        arrT[this.indexOf((long)l)] = t;
    }

    public void clear() {
        for (int i = 0; i < this.size(); ++i) {
            this.mBuffer[i] = null;
        }
        this.mCursor = 0L;
    }

    protected T createNewItem() {
        Object obj;
        try {
            obj = this.mBuffer.getClass().getComponentType().newInstance();
        }
        catch (IllegalAccessException | InstantiationException reflectiveOperationException) {
            return null;
        }
        return (T)obj;
    }

    public T getNextSlot() {
        long l = this.mCursor;
        this.mCursor = 1L + l;
        T[] arrT = this.mBuffer;
        int n = this.indexOf(l);
        if (arrT[n] == null) {
            arrT[n] = this.createNewItem();
        }
        return this.mBuffer[n];
    }

    public boolean isEmpty() {
        boolean bl = this.size() == 0;
        return bl;
    }

    public int size() {
        return (int)Math.min((long)this.mBuffer.length, this.mCursor);
    }

    public T[] toArray() {
        T[] arrT = Arrays.copyOf(this.mBuffer, this.size(), this.mBuffer.getClass());
        long l = this.mCursor - 1L;
        int n = arrT.length - 1;
        while (n >= 0) {
            arrT[n] = this.mBuffer[this.indexOf(l)];
            --n;
            --l;
        }
        return arrT;
    }
}

