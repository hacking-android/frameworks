/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;
import sun.misc.Unsafe;

public class AtomicLong
extends Number
implements Serializable {
    private static final Unsafe U = Unsafe.getUnsafe();
    private static final long VALUE;
    static final boolean VM_SUPPORTS_LONG_CAS;
    private static final long serialVersionUID = 1927816293512124184L;
    private volatile long value;

    static {
        VM_SUPPORTS_LONG_CAS = AtomicLong.VMSupportsCS8();
        try {
            VALUE = U.objectFieldOffset(AtomicLong.class.getDeclaredField("value"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public AtomicLong() {
    }

    public AtomicLong(long l) {
        this.value = l;
    }

    private static native boolean VMSupportsCS8();

    public final long accumulateAndGet(long l, LongBinaryOperator longBinaryOperator) {
        long l2;
        long l3;
        while (!this.compareAndSet(l3 = this.get(), l2 = longBinaryOperator.applyAsLong(l3, l))) {
        }
        return l2;
    }

    public final long addAndGet(long l) {
        return U.getAndAddLong(this, VALUE, l) + l;
    }

    public final boolean compareAndSet(long l, long l2) {
        return U.compareAndSwapLong(this, VALUE, l, l2);
    }

    public final long decrementAndGet() {
        return U.getAndAddLong(this, VALUE, -1L) - 1L;
    }

    @Override
    public double doubleValue() {
        return this.get();
    }

    @Override
    public float floatValue() {
        return this.get();
    }

    public final long get() {
        return this.value;
    }

    public final long getAndAccumulate(long l, LongBinaryOperator longBinaryOperator) {
        long l2;
        while (!this.compareAndSet(l2 = this.get(), longBinaryOperator.applyAsLong(l2, l))) {
        }
        return l2;
    }

    public final long getAndAdd(long l) {
        return U.getAndAddLong(this, VALUE, l);
    }

    public final long getAndDecrement() {
        return U.getAndAddLong(this, VALUE, -1L);
    }

    public final long getAndIncrement() {
        return U.getAndAddLong(this, VALUE, 1L);
    }

    public final long getAndSet(long l) {
        return U.getAndSetLong(this, VALUE, l);
    }

    public final long getAndUpdate(LongUnaryOperator longUnaryOperator) {
        long l;
        while (!this.compareAndSet(l = this.get(), longUnaryOperator.applyAsLong(l))) {
        }
        return l;
    }

    public final long incrementAndGet() {
        return U.getAndAddLong(this, VALUE, 1L) + 1L;
    }

    @Override
    public int intValue() {
        return (int)this.get();
    }

    public final void lazySet(long l) {
        U.putOrderedLong(this, VALUE, l);
    }

    @Override
    public long longValue() {
        return this.get();
    }

    public final void set(long l) {
        U.putLongVolatile(this, VALUE, l);
    }

    public String toString() {
        return Long.toString(this.get());
    }

    public final long updateAndGet(LongUnaryOperator longUnaryOperator) {
        long l;
        long l2;
        while (!this.compareAndSet(l = this.get(), l2 = longUnaryOperator.applyAsLong(l))) {
        }
        return l2;
    }

    public final boolean weakCompareAndSet(long l, long l2) {
        return U.compareAndSwapLong(this, VALUE, l, l2);
    }
}

