/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class AtomicStampedReference<V> {
    private static final long PAIR;
    private static final Unsafe U;
    private volatile Pair<V> pair;

    static {
        U = Unsafe.getUnsafe();
        try {
            PAIR = U.objectFieldOffset(AtomicStampedReference.class.getDeclaredField("pair"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public AtomicStampedReference(V v, int n) {
        this.pair = Pair.of(v, n);
    }

    private boolean casPair(Pair<V> pair, Pair<V> pair2) {
        return U.compareAndSwapObject(this, PAIR, pair, pair2);
    }

    public boolean attemptStamp(V v, int n) {
        Pair<V> pair = this.pair;
        boolean bl = v == pair.reference && (n == pair.stamp || this.casPair(pair, Pair.of(v, n)));
        return bl;
    }

    public boolean compareAndSet(V v, V v2, int n, int n2) {
        Pair<V> pair = this.pair;
        boolean bl = v == pair.reference && n == pair.stamp && (v2 == pair.reference && n2 == pair.stamp || this.casPair(pair, Pair.of(v2, n2)));
        return bl;
    }

    public V get(int[] arrn) {
        Pair<V> pair = this.pair;
        arrn[0] = pair.stamp;
        return (V)pair.reference;
    }

    public V getReference() {
        return (V)this.pair.reference;
    }

    public int getStamp() {
        return this.pair.stamp;
    }

    public void set(V v, int n) {
        Pair<V> pair = this.pair;
        if (v != pair.reference || n != pair.stamp) {
            this.pair = Pair.of(v, n);
        }
    }

    public boolean weakCompareAndSet(V v, V v2, int n, int n2) {
        return this.compareAndSet(v, v2, n, n2);
    }

    private static class Pair<T> {
        final T reference;
        final int stamp;

        private Pair(T t, int n) {
            this.reference = t;
            this.stamp = n;
        }

        static <T> Pair<T> of(T t, int n) {
            return new Pair<T>(t, n);
        }
    }

}

