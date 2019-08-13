/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.util.ICUException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

public abstract class CacheValue<V> {
    private static final CacheValue NULL_VALUE;
    private static volatile Strength strength;

    static {
        strength = Strength.SOFT;
        NULL_VALUE = new NullValue();
    }

    public static boolean futureInstancesWillBeStrong() {
        boolean bl = strength == Strength.STRONG;
        return bl;
    }

    public static <V> CacheValue<V> getInstance(V object) {
        if (object == null) {
            return NULL_VALUE;
        }
        object = strength == Strength.STRONG ? new StrongValue<V>(object) : new SoftValue<V>(object);
        return object;
    }

    public static void setStrength(Strength strength) {
        CacheValue.strength = strength;
    }

    public abstract V get();

    public boolean isNull() {
        return false;
    }

    public abstract V resetIfCleared(V var1);

    private static final class NullValue<V>
    extends CacheValue<V> {
        private NullValue() {
        }

        @Override
        public V get() {
            return null;
        }

        @Override
        public boolean isNull() {
            return true;
        }

        @Override
        public V resetIfCleared(V v) {
            if (v == null) {
                return null;
            }
            throw new ICUException("resetting a null value to a non-null value");
        }
    }

    private static final class SoftValue<V>
    extends CacheValue<V> {
        private volatile Reference<V> ref;

        SoftValue(V v) {
            this.ref = new SoftReference<V>(v);
        }

        @Override
        public V get() {
            return this.ref.get();
        }

        @Override
        public V resetIfCleared(V v) {
            synchronized (this) {
                Object object;
                block4 : {
                    object = this.ref.get();
                    if (object != null) break block4;
                    object = new Object(v);
                    this.ref = object;
                    return v;
                }
                return object;
            }
        }
    }

    public static enum Strength {
        STRONG,
        SOFT;
        
    }

    private static final class StrongValue<V>
    extends CacheValue<V> {
        private V value;

        StrongValue(V v) {
            this.value = v;
        }

        @Override
        public V get() {
            return this.value;
        }

        @Override
        public V resetIfCleared(V v) {
            return this.value;
        }
    }

}

