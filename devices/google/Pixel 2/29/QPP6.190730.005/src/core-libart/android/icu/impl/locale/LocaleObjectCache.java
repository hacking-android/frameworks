/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

public abstract class LocaleObjectCache<K, V> {
    private ConcurrentHashMap<K, CacheEntry<K, V>> _map;
    private ReferenceQueue<V> _queue = new ReferenceQueue();

    public LocaleObjectCache() {
        this(16, 0.75f, 16);
    }

    public LocaleObjectCache(int n, float f, int n2) {
        this._map = new ConcurrentHashMap(n, f, n2);
    }

    private void cleanStaleEntries() {
        CacheEntry cacheEntry;
        while ((cacheEntry = (CacheEntry)this._queue.poll()) != null) {
            this._map.remove(cacheEntry.getKey());
        }
    }

    protected abstract V createObject(K var1);

    public V get(K object) {
        CacheEntry<K, V> cacheEntry;
        block5 : {
            CacheEntry<K, Object> cacheEntry2 = null;
            this.cleanStaleEntries();
            cacheEntry = this._map.get(object);
            if (cacheEntry != null) {
                cacheEntry2 = (CacheEntry<K, Object>)cacheEntry.get();
            }
            cacheEntry = cacheEntry2;
            if (cacheEntry2 == null) {
                K k = this.normalizeKey(object);
                object = this.createObject(k);
                if (k != null && object != null) {
                    CacheEntry<K, K> cacheEntry3 = new CacheEntry<K, K>(k, object, this._queue);
                    do {
                        cacheEntry = cacheEntry2;
                        if (cacheEntry2 != null) break block5;
                        this.cleanStaleEntries();
                        cacheEntry2 = this._map.putIfAbsent(k, cacheEntry3);
                        if (cacheEntry2 == null) {
                            cacheEntry = object;
                            break block5;
                        }
                        cacheEntry2 = cacheEntry2.get();
                    } while (true);
                }
                return null;
            }
        }
        return (V)cacheEntry;
    }

    protected K normalizeKey(K k) {
        return k;
    }

    private static class CacheEntry<K, V>
    extends SoftReference<V> {
        private K _key;

        CacheEntry(K k, V v, ReferenceQueue<V> referenceQueue) {
            super(v, referenceQueue);
            this._key = k;
        }

        K getKey() {
            return this._key;
        }
    }

}

