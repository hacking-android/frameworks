/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUCache;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SimpleCache<K, V>
implements ICUCache<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private volatile Reference<Map<K, V>> cacheRef = null;
    private int capacity = 16;
    private int type = 0;

    public SimpleCache() {
    }

    public SimpleCache(int n) {
        this(n, 16);
    }

    public SimpleCache(int n, int n2) {
        if (n == 1) {
            this.type = n;
        }
        if (n2 > 0) {
            this.capacity = n2;
        }
    }

    @Override
    public void clear() {
        this.cacheRef = null;
    }

    @Override
    public V get(Object object) {
        Reference<Map<K, V>> reference = this.cacheRef;
        if (reference != null && (reference = reference.get()) != null) {
            return reference.get(object);
        }
        return null;
    }

    @Override
    public void put(K k, V v) {
        Object object = this.cacheRef;
        Reference reference = null;
        if (object != null) {
            reference = ((Reference)object).get();
        }
        object = reference;
        if (reference == null) {
            object = Collections.synchronizedMap(new HashMap(this.capacity));
            reference = this.type == 1 ? new WeakReference<Object>(object) : new SoftReference<Object>(object);
            this.cacheRef = reference;
        }
        object.put(k, v);
    }
}

