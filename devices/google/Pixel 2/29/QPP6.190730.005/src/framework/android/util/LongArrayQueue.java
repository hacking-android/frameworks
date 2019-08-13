/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.util;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.util.NoSuchElementException;
import libcore.util.EmptyArray;

public class LongArrayQueue {
    private int mHead;
    private int mSize;
    private int mTail;
    private long[] mValues;

    public LongArrayQueue() {
        this(16);
    }

    public LongArrayQueue(int n) {
        this.mValues = n == 0 ? EmptyArray.LONG : ArrayUtils.newUnpaddedLongArray(n);
        this.mSize = 0;
        this.mTail = 0;
        this.mHead = 0;
    }

    private void grow() {
        int n = this.mSize;
        if (n >= this.mValues.length) {
            long[] arrl = ArrayUtils.newUnpaddedLongArray(GrowingArrayUtils.growSize(n));
            long[] arrl2 = this.mValues;
            int n2 = arrl2.length;
            n = this.mHead;
            System.arraycopy(arrl2, n, arrl, 0, n2 -= n);
            System.arraycopy(this.mValues, 0, arrl, n2, this.mHead);
            this.mValues = arrl;
            this.mHead = 0;
            this.mTail = this.mSize;
            return;
        }
        throw new IllegalStateException("Queue not full yet!");
    }

    public void addLast(long l) {
        if (this.mSize == this.mValues.length) {
            this.grow();
        }
        long[] arrl = this.mValues;
        int n = this.mTail;
        arrl[n] = l;
        this.mTail = (n + 1) % arrl.length;
        ++this.mSize;
    }

    public void clear() {
        this.mSize = 0;
        this.mTail = 0;
        this.mHead = 0;
    }

    public long get(int n) {
        if (n >= 0 && n < this.mSize) {
            int n2 = this.mHead;
            long[] arrl = this.mValues;
            return arrl[(n2 + n) % arrl.length];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Index ");
        stringBuilder.append(n);
        stringBuilder.append(" not valid for a queue of size ");
        stringBuilder.append(this.mSize);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public long peekFirst() {
        if (this.mSize != 0) {
            return this.mValues[this.mHead];
        }
        throw new NoSuchElementException("Queue is empty!");
    }

    public long peekLast() {
        if (this.mSize != 0) {
            int n;
            int n2 = n = this.mTail;
            if (n == 0) {
                n2 = this.mValues.length;
            }
            return this.mValues[n2 - 1];
        }
        throw new NoSuchElementException("Queue is empty!");
    }

    public long removeFirst() {
        int n = this.mSize;
        if (n != 0) {
            long[] arrl = this.mValues;
            int n2 = this.mHead;
            long l = arrl[n2];
            this.mHead = (n2 + 1) % arrl.length;
            this.mSize = n - 1;
            return l;
        }
        throw new NoSuchElementException("Queue is empty!");
    }

    public int size() {
        return this.mSize;
    }
}

