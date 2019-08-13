/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.io.Serializable;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;
import sun.misc.Unsafe;

public class AtomicLongArray
implements Serializable {
    private static final int ABASE;
    private static final int ASHIFT;
    private static final Unsafe U;
    private static final long serialVersionUID = -2308431214976778248L;
    private final long[] array;

    static {
        U = Unsafe.getUnsafe();
        ABASE = U.arrayBaseOffset(long[].class);
        int n = U.arrayIndexScale(long[].class);
        if ((n - 1 & n) == 0) {
            ASHIFT = 31 - Integer.numberOfLeadingZeros(n);
            return;
        }
        throw new Error("array index scale not a power of two");
    }

    public AtomicLongArray(int n) {
        this.array = new long[n];
    }

    public AtomicLongArray(long[] arrl) {
        this.array = (long[])arrl.clone();
    }

    private static long byteOffset(int n) {
        return ((long)n << ASHIFT) + (long)ABASE;
    }

    private long checkedByteOffset(int n) {
        if (n >= 0 && n < this.array.length) {
            return AtomicLongArray.byteOffset(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("index ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    private boolean compareAndSetRaw(long l, long l2, long l3) {
        return U.compareAndSwapLong(this.array, l, l2, l3);
    }

    private long getRaw(long l) {
        return U.getLongVolatile(this.array, l);
    }

    public final long accumulateAndGet(int n, long l, LongBinaryOperator longBinaryOperator) {
        long l2;
        long l3;
        long l4 = this.checkedByteOffset(n);
        while (!this.compareAndSetRaw(l4, l3 = this.getRaw(l4), l2 = longBinaryOperator.applyAsLong(l3, l))) {
        }
        return l2;
    }

    public long addAndGet(int n, long l) {
        return this.getAndAdd(n, l) + l;
    }

    public final boolean compareAndSet(int n, long l, long l2) {
        return this.compareAndSetRaw(this.checkedByteOffset(n), l, l2);
    }

    public final long decrementAndGet(int n) {
        return this.getAndAdd(n, -1L) - 1L;
    }

    public final long get(int n) {
        return this.getRaw(this.checkedByteOffset(n));
    }

    public final long getAndAccumulate(int n, long l, LongBinaryOperator longBinaryOperator) {
        long l2;
        long l3 = this.checkedByteOffset(n);
        while (!this.compareAndSetRaw(l3, l2 = this.getRaw(l3), longBinaryOperator.applyAsLong(l2, l))) {
        }
        return l2;
    }

    public final long getAndAdd(int n, long l) {
        return U.getAndAddLong(this.array, this.checkedByteOffset(n), l);
    }

    public final long getAndDecrement(int n) {
        return this.getAndAdd(n, -1L);
    }

    public final long getAndIncrement(int n) {
        return this.getAndAdd(n, 1L);
    }

    public final long getAndSet(int n, long l) {
        return U.getAndSetLong(this.array, this.checkedByteOffset(n), l);
    }

    public final long getAndUpdate(int n, LongUnaryOperator longUnaryOperator) {
        long l;
        long l2 = this.checkedByteOffset(n);
        while (!this.compareAndSetRaw(l2, l = this.getRaw(l2), longUnaryOperator.applyAsLong(l))) {
        }
        return l;
    }

    public final long incrementAndGet(int n) {
        return this.getAndAdd(n, 1L) + 1L;
    }

    public final void lazySet(int n, long l) {
        U.putOrderedLong(this.array, this.checkedByteOffset(n), l);
    }

    public final int length() {
        return this.array.length;
    }

    public final void set(int n, long l) {
        U.putLongVolatile(this.array, this.checkedByteOffset(n), l);
    }

    public String toString() {
        int n = this.array.length - 1;
        if (n == -1) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        int n2 = 0;
        do {
            stringBuilder.append(this.getRaw(AtomicLongArray.byteOffset(n2)));
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(',');
            stringBuilder.append(' ');
            ++n2;
        } while (true);
    }

    public final long updateAndGet(int n, LongUnaryOperator longUnaryOperator) {
        long l;
        long l2;
        long l3 = this.checkedByteOffset(n);
        while (!this.compareAndSetRaw(l3, l = this.getRaw(l3), l2 = longUnaryOperator.applyAsLong(l))) {
        }
        return l2;
    }

    public final boolean weakCompareAndSet(int n, long l, long l2) {
        return this.compareAndSet(n, l, l2);
    }
}

