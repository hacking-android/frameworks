/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent.atomic;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class AtomicMarkableReference<V> {
    private static final long PAIR;
    private static final Unsafe U;
    private volatile Pair<V> pair;

    static {
        U = Unsafe.getUnsafe();
        try {
            PAIR = U.objectFieldOffset(AtomicMarkableReference.class.getDeclaredField("pair"));
            return;
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            throw new Error(reflectiveOperationException);
        }
    }

    public AtomicMarkableReference(V v, boolean bl) {
        this.pair = Pair.of(v, bl);
    }

    private boolean casPair(Pair<V> pair, Pair<V> pair2) {
        return U.compareAndSwapObject(this, PAIR, pair, pair2);
    }

    public boolean attemptMark(V v, boolean bl) {
        Pair<V> pair = this.pair;
        bl = v == pair.reference && (bl == pair.mark || this.casPair(pair, Pair.of(v, bl)));
        return bl;
    }

    public boolean compareAndSet(V v, V v2, boolean bl, boolean bl2) {
        Pair<V> pair = this.pair;
        bl = v == pair.reference && bl == pair.mark && (v2 == pair.reference && bl2 == pair.mark || this.casPair(pair, Pair.of(v2, bl2)));
        return bl;
    }

    public V get(boolean[] arrbl) {
        Pair<V> pair = this.pair;
        arrbl[0] = pair.mark;
        return (V)pair.reference;
    }

    public V getReference() {
        return (V)this.pair.reference;
    }

    public boolean isMarked() {
        return this.pair.mark;
    }

    public void set(V v, boolean bl) {
        Pair<V> pair = this.pair;
        if (v != pair.reference || bl != pair.mark) {
            this.pair = Pair.of(v, bl);
        }
    }

    public boolean weakCompareAndSet(V v, V v2, boolean bl, boolean bl2) {
        return this.compareAndSet(v, v2, bl, bl2);
    }

    private static class Pair<T> {
        final boolean mark;
        final T reference;

        private Pair(T t, boolean bl) {
            this.reference = t;
            this.mark = bl;
        }

        static <T> Pair<T> of(T t, boolean bl) {
            return new Pair<T>(t, bl);
        }
    }

}

