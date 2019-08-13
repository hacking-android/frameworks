/*
 * Decompiled with CFR 0.145.
 */
package java.lang.reflect;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;

final class WeakCache<K, P, V> {
    private final ConcurrentMap<Object, ConcurrentMap<Object, Supplier<V>>> map = new ConcurrentHashMap<Object, ConcurrentMap<Object, Supplier<V>>>();
    private final ReferenceQueue<K> refQueue = new ReferenceQueue();
    private final ConcurrentMap<Supplier<V>, Boolean> reverseMap = new ConcurrentHashMap<Supplier<V>, Boolean>();
    private final BiFunction<K, P, ?> subKeyFactory;
    private final BiFunction<K, P, V> valueFactory;

    public WeakCache(BiFunction<K, P, ?> biFunction, BiFunction<K, P, V> biFunction2) {
        this.subKeyFactory = Objects.requireNonNull(biFunction);
        this.valueFactory = Objects.requireNonNull(biFunction2);
    }

    private void expungeStaleEntries() {
        CacheKey cacheKey;
        while ((cacheKey = (CacheKey)this.refQueue.poll()) != null) {
            cacheKey.expungeFrom(this.map, this.reverseMap);
        }
    }

    public boolean containsValue(V v) {
        Objects.requireNonNull(v);
        this.expungeStaleEntries();
        return this.reverseMap.containsKey(new LookupValue<V>(v));
    }

    public V get(K k, P p) {
        Object object;
        Object object2;
        Objects.requireNonNull(p);
        this.expungeStaleEntries();
        Object object3 = CacheKey.valueOf(k, this.refQueue);
        Object object4 = object = (ConcurrentHashMap)this.map.get(object3);
        if (object == null) {
            object2 = this.map;
            object4 = object = new ConcurrentHashMap();
            if ((object = (ConcurrentMap)object2.putIfAbsent((Object)object3, object)) != null) {
                object4 = object;
            }
        }
        Object obj = Objects.requireNonNull(this.subKeyFactory.apply(k, p));
        object = (Supplier)object4.get(obj);
        object2 = null;
        while (object == null || (object3 = object.get()) == null) {
            object3 = object2;
            if (object2 == null) {
                object3 = new Factory(k, p, obj, object4);
            }
            if (object == null) {
                Supplier supplier = (Supplier)((Object)object4.putIfAbsent(obj, object3));
                object = supplier;
                object2 = object3;
                if (supplier != null) continue;
                object = object3;
                object2 = object3;
                continue;
            }
            if (object4.replace(obj, object, object3)) {
                object = object3;
                object2 = object3;
                continue;
            }
            object = (Supplier)object4.get(obj);
            object2 = object3;
        }
        return (V)object3;
    }

    public int size() {
        this.expungeStaleEntries();
        return this.reverseMap.size();
    }

    private static final class CacheKey<K>
    extends WeakReference<K> {
        private static final Object NULL_KEY = new Object();
        private final int hash;

        private CacheKey(K k, ReferenceQueue<K> referenceQueue) {
            super(k, referenceQueue);
            this.hash = System.identityHashCode(k);
        }

        static <K> Object valueOf(K object, ReferenceQueue<K> referenceQueue) {
            object = object == null ? NULL_KEY : new CacheKey<K>(object, referenceQueue);
            return object;
        }

        public boolean equals(Object object) {
            Object t;
            boolean bl = object == this || object != null && object.getClass() == this.getClass() && (t = this.get()) != null && t == ((CacheKey)object).get();
            return bl;
        }

        void expungeFrom(ConcurrentMap<?, ? extends ConcurrentMap<?, ?>> object, ConcurrentMap<?, Boolean> concurrentMap) {
            if ((object = (ConcurrentMap)object.remove(this)) != null) {
                object = object.values().iterator();
                while (object.hasNext()) {
                    concurrentMap.remove(object.next());
                }
            }
        }

        public int hashCode() {
            return this.hash;
        }
    }

    private static final class CacheValue<V>
    extends WeakReference<V>
    implements Value<V> {
        private final int hash;

        CacheValue(V v) {
            super(v);
            this.hash = System.identityHashCode(v);
        }

        public boolean equals(Object object) {
            Object t;
            boolean bl = object == this || object instanceof Value && (t = this.get()) != null && t == ((Value)object).get();
            return bl;
        }

        public int hashCode() {
            return this.hash;
        }
    }

    private final class Factory
    implements Supplier<V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final K key;
        private final P parameter;
        private final Object subKey;
        private final ConcurrentMap<Object, Supplier<V>> valuesMap;

        Factory(K k, P p, Object object, ConcurrentMap<Object, Supplier<V>> concurrentMap) {
            this.key = k;
            this.parameter = p;
            this.subKey = object;
            this.valuesMap = concurrentMap;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public V get() {
            synchronized (this) {
                Object object;
                Object r;
                block8 : {
                    object = (CacheValue)this.valuesMap.get(this.subKey);
                    if (object != this) {
                        return null;
                    }
                    r = Objects.requireNonNull(WeakCache.this.valueFactory.apply(this.key, this.parameter));
                    object = new CacheValue(r);
                    if (!this.valuesMap.replace(this.subKey, this, object)) break block8;
                    WeakCache.this.reverseMap.put(object, Boolean.TRUE);
                    return (V)r;
                }
                object = new AssertionError((Object)"Should not reach here");
                throw object;
                finally {
                    if (r == null) {
                        this.valuesMap.remove(this.subKey, this);
                    }
                }
            }
        }
    }

    private static final class LookupValue<V>
    implements Value<V> {
        private final V value;

        LookupValue(V v) {
            this.value = v;
        }

        public boolean equals(Object object) {
            boolean bl = object == this || object instanceof Value && this.value == ((Value)object).get();
            return bl;
        }

        @Override
        public V get() {
            return this.value;
        }

        public int hashCode() {
            return System.identityHashCode(this.value);
        }
    }

    private static interface Value<V>
    extends Supplier<V> {
    }

}

