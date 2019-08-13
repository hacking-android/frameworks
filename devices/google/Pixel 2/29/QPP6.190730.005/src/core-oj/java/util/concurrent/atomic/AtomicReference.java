/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import sun.misc.Unsafe;

public class AtomicReference<V>
implements Serializable {
    private static final Unsafe U = Unsafe.getUnsafe();
    private static final long VALUE;
    private static final long serialVersionUID = -1848883965231344442L;
    private volatile V value;

    static {
        try {
            VALUE = U.objectFieldOffset(AtomicReference.class.getDeclaredField("value"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public AtomicReference() {
    }

    public AtomicReference(V v) {
        this.value = v;
    }

    public final V accumulateAndGet(V v, BinaryOperator<V> binaryOperator) {
        V v2;
        Object r;
        while (!this.compareAndSet(v2 = this.get(), r = binaryOperator.apply(v2, v))) {
        }
        return (V)r;
    }

    public final boolean compareAndSet(V v, V v2) {
        return U.compareAndSwapObject(this, VALUE, v, v2);
    }

    public final V get() {
        return this.value;
    }

    public final V getAndAccumulate(V v, BinaryOperator<V> binaryOperator) {
        V v2;
        while (!this.compareAndSet(v2 = this.get(), binaryOperator.apply(v2, v))) {
        }
        return v2;
    }

    public final V getAndSet(V v) {
        return (V)U.getAndSetObject(this, VALUE, v);
    }

    public final V getAndUpdate(UnaryOperator<V> unaryOperator) {
        V v;
        while (!this.compareAndSet(v = this.get(), unaryOperator.apply(v))) {
        }
        return v;
    }

    public final void lazySet(V v) {
        U.putOrderedObject(this, VALUE, v);
    }

    public final void set(V v) {
        this.value = v;
    }

    public String toString() {
        return String.valueOf(this.get());
    }

    public final V updateAndGet(UnaryOperator<V> unaryOperator) {
        V v;
        Object r;
        while (!this.compareAndSet(v = this.get(), r = unaryOperator.apply(v))) {
        }
        return (V)r;
    }

    public final boolean weakCompareAndSet(V v, V v2) {
        return U.compareAndSwapObject(this, VALUE, v, v2);
    }
}

