/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.io.Serializable;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import sun.misc.Unsafe;

public class AtomicIntegerArray
implements Serializable {
    private static final int ABASE;
    private static final int ASHIFT;
    private static final Unsafe U;
    private static final long serialVersionUID = 2862133569453604235L;
    private final int[] array;

    static {
        U = Unsafe.getUnsafe();
        ABASE = U.arrayBaseOffset(int[].class);
        int n = U.arrayIndexScale(int[].class);
        if ((n - 1 & n) == 0) {
            ASHIFT = 31 - Integer.numberOfLeadingZeros(n);
            return;
        }
        throw new Error("array index scale not a power of two");
    }

    public AtomicIntegerArray(int n) {
        this.array = new int[n];
    }

    public AtomicIntegerArray(int[] arrn) {
        this.array = (int[])arrn.clone();
    }

    private static long byteOffset(int n) {
        return ((long)n << ASHIFT) + (long)ABASE;
    }

    private long checkedByteOffset(int n) {
        if (n >= 0 && n < this.array.length) {
            return AtomicIntegerArray.byteOffset(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("index ");
        stringBuilder.append(n);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    private boolean compareAndSetRaw(long l, int n, int n2) {
        return U.compareAndSwapInt(this.array, l, n, n2);
    }

    private int getRaw(long l) {
        return U.getIntVolatile(this.array, l);
    }

    public final int accumulateAndGet(int n, int n2, IntBinaryOperator intBinaryOperator) {
        int n3;
        long l = this.checkedByteOffset(n);
        while (!this.compareAndSetRaw(l, n = this.getRaw(l), n3 = intBinaryOperator.applyAsInt(n, n2))) {
        }
        return n3;
    }

    public final int addAndGet(int n, int n2) {
        return this.getAndAdd(n, n2) + n2;
    }

    public final boolean compareAndSet(int n, int n2, int n3) {
        return this.compareAndSetRaw(this.checkedByteOffset(n), n2, n3);
    }

    public final int decrementAndGet(int n) {
        return this.getAndAdd(n, -1) - 1;
    }

    public final int get(int n) {
        return this.getRaw(this.checkedByteOffset(n));
    }

    public final int getAndAccumulate(int n, int n2, IntBinaryOperator intBinaryOperator) {
        long l = this.checkedByteOffset(n);
        while (!this.compareAndSetRaw(l, n = this.getRaw(l), intBinaryOperator.applyAsInt(n, n2))) {
        }
        return n;
    }

    public final int getAndAdd(int n, int n2) {
        return U.getAndAddInt(this.array, this.checkedByteOffset(n), n2);
    }

    public final int getAndDecrement(int n) {
        return this.getAndAdd(n, -1);
    }

    public final int getAndIncrement(int n) {
        return this.getAndAdd(n, 1);
    }

    public final int getAndSet(int n, int n2) {
        return U.getAndSetInt(this.array, this.checkedByteOffset(n), n2);
    }

    public final int getAndUpdate(int n, IntUnaryOperator intUnaryOperator) {
        long l = this.checkedByteOffset(n);
        while (!this.compareAndSetRaw(l, n = this.getRaw(l), intUnaryOperator.applyAsInt(n))) {
        }
        return n;
    }

    public final int incrementAndGet(int n) {
        return this.getAndAdd(n, 1) + 1;
    }

    public final void lazySet(int n, int n2) {
        U.putOrderedInt(this.array, this.checkedByteOffset(n), n2);
    }

    public final int length() {
        return this.array.length;
    }

    public final void set(int n, int n2) {
        U.putIntVolatile(this.array, this.checkedByteOffset(n), n2);
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
            stringBuilder.append(this.getRaw(AtomicIntegerArray.byteOffset(n2)));
            if (n2 == n) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            stringBuilder.append(',');
            stringBuilder.append(' ');
            ++n2;
        } while (true);
    }

    public final int updateAndGet(int n, IntUnaryOperator intUnaryOperator) {
        int n2;
        long l = this.checkedByteOffset(n);
        while (!this.compareAndSetRaw(l, n2 = this.getRaw(l), n = intUnaryOperator.applyAsInt(n2))) {
        }
        return n;
    }

    public final boolean weakCompareAndSet(int n, int n2, int n3) {
        return this.compareAndSet(n, n2, n3);
    }
}

