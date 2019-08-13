/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import sun.misc.Unsafe;

public class AtomicInteger
extends Number
implements Serializable {
    private static final Unsafe U = Unsafe.getUnsafe();
    private static final long VALUE;
    private static final long serialVersionUID = 6214790243416807050L;
    private volatile int value;

    static {
        try {
            VALUE = U.objectFieldOffset(AtomicInteger.class.getDeclaredField("value"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public AtomicInteger() {
    }

    public AtomicInteger(int n) {
        this.value = n;
    }

    public final int accumulateAndGet(int n, IntBinaryOperator intBinaryOperator) {
        int n2;
        int n3;
        while (!this.compareAndSet(n3 = this.get(), n2 = intBinaryOperator.applyAsInt(n3, n))) {
        }
        return n2;
    }

    public final int addAndGet(int n) {
        return U.getAndAddInt(this, VALUE, n) + n;
    }

    public final boolean compareAndSet(int n, int n2) {
        return U.compareAndSwapInt(this, VALUE, n, n2);
    }

    public final int decrementAndGet() {
        return U.getAndAddInt(this, VALUE, -1) - 1;
    }

    @Override
    public double doubleValue() {
        return this.get();
    }

    @Override
    public float floatValue() {
        return this.get();
    }

    public final int get() {
        return this.value;
    }

    public final int getAndAccumulate(int n, IntBinaryOperator intBinaryOperator) {
        int n2;
        while (!this.compareAndSet(n2 = this.get(), intBinaryOperator.applyAsInt(n2, n))) {
        }
        return n2;
    }

    public final int getAndAdd(int n) {
        return U.getAndAddInt(this, VALUE, n);
    }

    public final int getAndDecrement() {
        return U.getAndAddInt(this, VALUE, -1);
    }

    public final int getAndIncrement() {
        return U.getAndAddInt(this, VALUE, 1);
    }

    public final int getAndSet(int n) {
        return U.getAndSetInt(this, VALUE, n);
    }

    public final int getAndUpdate(IntUnaryOperator intUnaryOperator) {
        int n;
        while (!this.compareAndSet(n = this.get(), intUnaryOperator.applyAsInt(n))) {
        }
        return n;
    }

    public final int incrementAndGet() {
        return U.getAndAddInt(this, VALUE, 1) + 1;
    }

    @Override
    public int intValue() {
        return this.get();
    }

    public final void lazySet(int n) {
        U.putOrderedInt(this, VALUE, n);
    }

    @Override
    public long longValue() {
        return this.get();
    }

    public final void set(int n) {
        this.value = n;
    }

    public String toString() {
        return Integer.toString(this.get());
    }

    public final int updateAndGet(IntUnaryOperator intUnaryOperator) {
        int n;
        int n2;
        while (!this.compareAndSet(n2 = this.get(), n = intUnaryOperator.applyAsInt(n2))) {
        }
        return n;
    }

    public final boolean weakCompareAndSet(int n, int n2) {
        return U.compareAndSwapInt(this, VALUE, n, n2);
    }
}

