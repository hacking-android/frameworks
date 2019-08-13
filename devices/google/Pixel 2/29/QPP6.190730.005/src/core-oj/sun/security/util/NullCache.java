/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import sun.security.util.Cache;

class NullCache<K, V>
extends Cache<K, V> {
    static final Cache<Object, Object> INSTANCE = new NullCache<Object, Object>();

    private NullCache() {
    }

    @Override
    public void accept(Cache.CacheVisitor<K, V> cacheVisitor) {
    }

    @Override
    public void clear() {
    }

    @Override
    public V get(Object object) {
        return null;
    }

    @Override
    public void put(K k, V v) {
    }

    @Override
    public void remove(Object object) {
    }

    @Override
    public void setCapacity(int n) {
    }

    @Override
    public void setTimeout(int n) {
    }

    @Override
    public int size() {
        return 0;
    }
}

