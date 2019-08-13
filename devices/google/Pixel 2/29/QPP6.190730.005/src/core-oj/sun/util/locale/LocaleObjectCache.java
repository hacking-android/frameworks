/*
 * Decompiled with CFR 0.145.
 */
package sun.util.locale;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class LocaleObjectCache<K, V> {
    private ConcurrentMap<K, CacheEntry<K, V>> map;
    private ReferenceQueue<V> queue = new ReferenceQueue();

    public LocaleObjectCache() {
        this(16, 0.75f, 16);
    }

    public LocaleObjectCache(int n, float f, int n2) {
        this.map = new ConcurrentHashMap<K, CacheEntry<K, V>>(n, f, n2);
    }

    private void cleanStaleEntries() {
        CacheEntry cacheEntry;
        while ((cacheEntry = (CacheEntry)this.queue.poll()) != null) {
            this.map.remove(cacheEntry.getKey());
        }
    }

    protected abstract V createObject(K var1);

    public V get(K object) {
        V v = null;
        this.cleanStaleEntries();
        CacheEntry<K, Object> cacheEntry = (CacheEntry)this.map.get(object);
        if (cacheEntry != null) {
            v = (V)cacheEntry.get();
        }
        cacheEntry = v;
        if (v == null) {
            v = this.createObject(object);
            K k = this.normalizeKey(object);
            if (k != null && v != null) {
                CacheEntry<K, Object> cacheEntry2 = new CacheEntry<K, Object>(k, v, (ReferenceQueue<Object>)this.queue);
                object = this.map.putIfAbsent(k, cacheEntry2);
                if (object == null) {
                    cacheEntry = v;
                } else {
                    object = ((SoftReference)object).get();
                    cacheEntry = object;
                    if (object == null) {
                        this.map.put(k, cacheEntry2);
                        cacheEntry = v;
                    }
                }
            } else {
                return null;
            }
        }
        return (V)cacheEntry;
    }

    protected K normalizeKey(K k) {
        return k;
    }

    protected V put(K object, V object2) {
        object2 = new CacheEntry<K, V>(object, object2, this.queue);
        object = (object = (CacheEntry)this.map.put(object, (CacheEntry<K, V>)object2)) == null ? null : ((SoftReference)object).get();
        return (V)object;
    }

    private static class CacheEntry<K, V>
    extends SoftReference<V> {
        private K key;

        CacheEntry(K k, V v, ReferenceQueue<V> referenceQueue) {
            super(v, referenceQueue);
            this.key = k;
        }

        K getKey() {
            return this.key;
        }
    }

}

