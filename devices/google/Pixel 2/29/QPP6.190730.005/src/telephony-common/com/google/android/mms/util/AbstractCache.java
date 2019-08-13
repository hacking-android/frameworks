/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.mms.util;

import java.util.HashMap;

public abstract class AbstractCache<K, V> {
    private static final boolean DEBUG = false;
    private static final boolean LOCAL_LOGV = false;
    private static final int MAX_CACHED_ITEMS = 500;
    private static final String TAG = "AbstractCache";
    private final HashMap<K, CacheEntry<V>> mCacheMap = new HashMap();

    protected AbstractCache() {
    }

    public V get(K object) {
        if (object != null && (object = this.mCacheMap.get(object)) != null) {
            ++((CacheEntry)object).hit;
            return ((CacheEntry)object).value;
        }
        return null;
    }

    public V purge(K object) {
        object = (object = this.mCacheMap.remove(object)) != null ? ((CacheEntry)object).value : null;
        return (V)object;
    }

    public void purgeAll() {
        this.mCacheMap.clear();
    }

    public boolean put(K k, V v) {
        if (this.mCacheMap.size() >= 500) {
            return false;
        }
        if (k != null) {
            CacheEntry cacheEntry = new CacheEntry();
            cacheEntry.value = v;
            this.mCacheMap.put(k, cacheEntry);
            return true;
        }
        return false;
    }

    public int size() {
        return this.mCacheMap.size();
    }

    private static class CacheEntry<V> {
        int hit;
        V value;

        private CacheEntry() {
        }
    }

}

