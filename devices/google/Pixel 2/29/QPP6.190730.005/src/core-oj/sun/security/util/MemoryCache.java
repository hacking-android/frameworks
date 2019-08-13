/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import sun.security.util.Cache;

class MemoryCache<K, V>
extends Cache<K, V> {
    private static final boolean DEBUG = false;
    private static final float LOAD_FACTOR = 0.75f;
    private final Map<K, CacheEntry<K, V>> cacheMap;
    private long lifetime;
    private int maxSize;
    private final ReferenceQueue<V> queue;

    public MemoryCache(boolean bl, int n) {
        this(bl, n, 0);
    }

    public MemoryCache(boolean bl, int n, int n2) {
        this.maxSize = n;
        this.lifetime = n2 * 1000;
        this.queue = bl ? new ReferenceQueue() : null;
        this.cacheMap = new LinkedHashMap<K, CacheEntry<K, V>>((int)((float)n / 0.75f) + 1, 0.75f, true);
    }

    private void emptyQueue() {
        if (this.queue == null) {
            return;
        }
        this.cacheMap.size();
        CacheEntry cacheEntry;
        while ((cacheEntry = (CacheEntry)((Object)this.queue.poll())) != null) {
            CacheEntry<K, V> cacheEntry2;
            Object k = cacheEntry.getKey();
            if (k == null || (cacheEntry2 = this.cacheMap.remove(k)) == null || cacheEntry == cacheEntry2) continue;
            this.cacheMap.put(k, cacheEntry2);
        }
        return;
    }

    private void expungeExpiredEntries() {
        this.emptyQueue();
        if (this.lifetime == 0L) {
            return;
        }
        int n = 0;
        long l = System.currentTimeMillis();
        Iterator<CacheEntry<K, V>> iterator = this.cacheMap.values().iterator();
        while (iterator.hasNext()) {
            int n2 = n;
            if (!iterator.next().isValid(l)) {
                iterator.remove();
                n2 = n + 1;
            }
            n = n2;
        }
    }

    private Map<K, V> getCachedEntries() {
        HashMap<K, V> hashMap = new HashMap<K, V>(this.cacheMap.size());
        for (CacheEntry<K, V> cacheEntry : this.cacheMap.values()) {
            hashMap.put(cacheEntry.getKey(), cacheEntry.getValue());
        }
        return hashMap;
    }

    @Override
    public void accept(Cache.CacheVisitor<K, V> cacheVisitor) {
        synchronized (this) {
            this.expungeExpiredEntries();
            cacheVisitor.visit(this.getCachedEntries());
            return;
        }
    }

    @Override
    public void clear() {
        synchronized (this) {
            if (this.queue != null) {
                Iterator<CacheEntry<K, V>> iterator = this.cacheMap.values().iterator();
                while (iterator.hasNext()) {
                    iterator.next().invalidate();
                }
                while (this.queue.poll() != null) {
                }
            }
            this.cacheMap.clear();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public V get(Object object) {
        synchronized (this) {
            this.emptyQueue();
            CacheEntry<K, V> cacheEntry = this.cacheMap.get(object);
            if (cacheEntry == null) {
                return null;
            }
            long l = this.lifetime;
            long l2 = 0L;
            if (l != 0L) {
                l2 = System.currentTimeMillis();
            }
            if (!cacheEntry.isValid(l2)) {
                this.cacheMap.remove(object);
                return null;
            }
            object = cacheEntry.getValue();
            return (V)object;
        }
    }

    protected CacheEntry<K, V> newEntry(K k, V v, long l, ReferenceQueue<V> referenceQueue) {
        if (referenceQueue != null) {
            return new SoftCacheEntry<K, V>(k, v, l, referenceQueue);
        }
        return new HardCacheEntry<K, V>(k, v, l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void put(K object, V object2) {
        synchronized (this) {
            long l;
            Object object3;
            this.emptyQueue();
            if (this.lifetime == 0L) {
                l = 0L;
            } else {
                l = System.currentTimeMillis();
                l = this.lifetime + l;
            }
            object3 = this.newEntry(object, object3, l, this.queue);
            object = this.cacheMap.put(object, (CacheEntry<Object, CacheEntry<Object, void>>)object3);
            if (object != null) {
                object.invalidate();
                return;
            }
            if (this.maxSize > 0 && this.cacheMap.size() > this.maxSize) {
                this.expungeExpiredEntries();
                if (this.cacheMap.size() > this.maxSize) {
                    object3 = this.cacheMap.values().iterator();
                    object = (CacheEntry)object3.next();
                    object3.remove();
                    object.invalidate();
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void remove(Object cacheEntry) {
        synchronized (this) {
            this.emptyQueue();
            cacheEntry = this.cacheMap.remove(cacheEntry);
            if (cacheEntry != null) {
                cacheEntry.invalidate();
            }
            return;
        }
    }

    @Override
    public void setCapacity(int n) {
        synchronized (this) {
            block8 : {
                this.expungeExpiredEntries();
                if (n <= 0) break block8;
                if (this.cacheMap.size() <= n) break block8;
                Iterator<CacheEntry<K, V>> iterator = this.cacheMap.values().iterator();
                for (int i = this.cacheMap.size() - n; i > 0; --i) {
                    CacheEntry<K, V> cacheEntry = iterator.next();
                    iterator.remove();
                    cacheEntry.invalidate();
                }
            }
            if (n <= 0) {
                n = 0;
            }
            this.maxSize = n;
            return;
        }
    }

    @Override
    public void setTimeout(int n) {
        synchronized (this) {
            this.emptyQueue();
            long l = n > 0 ? (long)n * 1000L : 0L;
            this.lifetime = l;
            return;
        }
    }

    @Override
    public int size() {
        synchronized (this) {
            this.expungeExpiredEntries();
            int n = this.cacheMap.size();
            return n;
        }
    }

    private static interface CacheEntry<K, V> {
        public K getKey();

        public V getValue();

        public void invalidate();

        public boolean isValid(long var1);
    }

    private static class HardCacheEntry<K, V>
    implements CacheEntry<K, V> {
        private long expirationTime;
        private K key;
        private V value;

        HardCacheEntry(K k, V v, long l) {
            this.key = k;
            this.value = v;
            this.expirationTime = l;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public void invalidate() {
            this.key = null;
            this.value = null;
            this.expirationTime = -1L;
        }

        @Override
        public boolean isValid(long l) {
            boolean bl = l <= this.expirationTime;
            if (!bl) {
                this.invalidate();
            }
            return bl;
        }
    }

    private static class SoftCacheEntry<K, V>
    extends SoftReference<V>
    implements CacheEntry<K, V> {
        private long expirationTime;
        private K key;

        SoftCacheEntry(K k, V v, long l, ReferenceQueue<V> referenceQueue) {
            super(v, referenceQueue);
            this.key = k;
            this.expirationTime = l;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return (V)this.get();
        }

        @Override
        public void invalidate() {
            this.clear();
            this.key = null;
            this.expirationTime = -1L;
        }

        @Override
        public boolean isValid(long l) {
            boolean bl = l <= this.expirationTime && this.get() != null;
            if (!bl) {
                this.invalidate();
            }
            return bl;
        }
    }

}

