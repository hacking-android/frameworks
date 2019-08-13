/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.LinkedHashMap;
import java.util.Map;

public class BasicLruCache<K, V> {
    @UnsupportedAppUsage
    private final LinkedHashMap<K, V> map;
    private final int maxSize;

    @UnsupportedAppUsage
    public BasicLruCache(int n) {
        if (n > 0) {
            this.maxSize = n;
            this.map = new LinkedHashMap(0, 0.75f, true);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    private void trimToSize(int n) {
        while (this.map.size() > n) {
            Map.Entry entry = this.map.eldest();
            Object k = entry.getKey();
            entry = entry.getValue();
            this.map.remove(k);
            this.entryEvicted(k, entry);
        }
    }

    protected V create(K k) {
        return null;
    }

    protected void entryEvicted(K k, V v) {
    }

    @UnsupportedAppUsage
    public final void evictAll() {
        synchronized (this) {
            this.trimToSize(0);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public final V get(K k) {
        V v;
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            v = this.map.get(k);
            if (v != null) {
                return v;
            }
        }
        v = this.create(k);
        synchronized (this) {
            if (v != null) {
                this.map.put(k, v);
                this.trimToSize(this.maxSize);
            }
            return v;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public final V put(K object, V v) {
        synchronized (this) {
            void var2_5;
            if (object == null) {
                NullPointerException nullPointerException = new NullPointerException("key == null");
                throw nullPointerException;
            }
            if (var2_5 != null) {
                void var1_2 = this.map.put(object, var2_5);
                this.trimToSize(this.maxSize);
                return var1_2;
            }
            NullPointerException nullPointerException = new NullPointerException("value == null");
            throw nullPointerException;
        }
    }

    public final Map<K, V> snapshot() {
        synchronized (this) {
            LinkedHashMap<K, V> linkedHashMap = new LinkedHashMap<K, V>(this.map);
            return linkedHashMap;
        }
    }
}

