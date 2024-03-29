/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    @UnsupportedAppUsage
    private final LinkedHashMap<K, V> map;
    private int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(int n) {
        if (n > 0) {
            this.maxSize = n;
            this.map = new LinkedHashMap(0, 0.75f, true);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    private int safeSizeOf(K k, V v) {
        int n = this.sizeOf(k, v);
        if (n >= 0) {
            return n;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Negative size: ");
        stringBuilder.append(k);
        stringBuilder.append("=");
        stringBuilder.append(v);
        throw new IllegalStateException(stringBuilder.toString());
    }

    protected V create(K k) {
        return null;
    }

    public final int createCount() {
        synchronized (this) {
            int n = this.createCount;
            return n;
        }
    }

    protected void entryRemoved(boolean bl, K k, V v, V v2) {
    }

    public final void evictAll() {
        this.trimToSize(-1);
    }

    public final int evictionCount() {
        synchronized (this) {
            int n = this.evictionCount;
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final V get(K k) {
        if (k == null) throw new NullPointerException("key == null");
        // MONITORENTER : this
        V v = this.map.get(k);
        if (v != null) {
            ++this.hitCount;
            // MONITOREXIT : this
            return v;
        }
        ++this.missCount;
        // MONITOREXIT : this
        V v2 = this.create(k);
        if (v2 == null) {
            return null;
        }
        // MONITORENTER : this
        ++this.createCount;
        v = this.map.put(k, v2);
        if (v != null) {
            this.map.put(k, v);
        } else {
            this.size += this.safeSizeOf(k, v2);
        }
        // MONITOREXIT : this
        if (v != null) {
            this.entryRemoved(false, k, v2, v);
            return v;
        }
        this.trimToSize(this.maxSize);
        return v2;
    }

    public final int hitCount() {
        synchronized (this) {
            int n = this.hitCount;
            return n;
        }
    }

    public final int maxSize() {
        synchronized (this) {
            int n = this.maxSize;
            return n;
        }
    }

    public final int missCount() {
        synchronized (this) {
            int n = this.missCount;
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final V put(K k, V v) {
        if (k == null) throw new NullPointerException("key == null || value == null");
        if (v == null) throw new NullPointerException("key == null || value == null");
        // MONITORENTER : this
        ++this.putCount;
        this.size += this.safeSizeOf(k, v);
        V v2 = this.map.put(k, v);
        if (v2 != null) {
            this.size -= this.safeSizeOf(k, v2);
        }
        // MONITOREXIT : this
        if (v2 != null) {
            this.entryRemoved(false, k, v2, v);
        }
        this.trimToSize(this.maxSize);
        return v2;
    }

    public final int putCount() {
        synchronized (this) {
            int n = this.putCount;
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public final V remove(K k) {
        if (k == null) throw new NullPointerException("key == null");
        // MONITORENTER : this
        Object v = this.map.remove(k);
        if (v != null) {
            this.size -= this.safeSizeOf(k, v);
        }
        // MONITOREXIT : this
        if (v == null) return v;
        this.entryRemoved(false, k, v, null);
        return v;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void resize(int n) {
        if (n > 0) {
            synchronized (this) {
                this.maxSize = n;
            }
            this.trimToSize(n);
            return;
        }
        throw new IllegalArgumentException("maxSize <= 0");
    }

    public final int size() {
        synchronized (this) {
            int n = this.size;
            return n;
        }
    }

    protected int sizeOf(K k, V v) {
        return 1;
    }

    public final Map<K, V> snapshot() {
        synchronized (this) {
            LinkedHashMap<K, V> linkedHashMap = new LinkedHashMap<K, V>(this.map);
            return linkedHashMap;
        }
    }

    public final String toString() {
        synchronized (this) {
            int n;
            block6 : {
                block5 : {
                    n = this.hitCount + this.missCount;
                    if (n == 0) break block5;
                    n = this.hitCount * 100 / n;
                    break block6;
                }
                n = 0;
            }
            String string2 = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", this.maxSize, this.hitCount, this.missCount, n);
            return string2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void trimToSize(int n) {
        IllegalStateException illegalStateException;
        Object object;
        do {
            synchronized (this) {
                if (this.size < 0 || this.map.isEmpty() && this.size != 0) break;
                if (this.size <= n) {
                    return;
                }
                object = this.map.eldest();
                if (object == null) {
                    return;
                }
                illegalStateException = object.getKey();
                object = object.getValue();
                this.map.remove(illegalStateException);
                this.size -= this.safeSizeOf(illegalStateException, object);
                ++this.evictionCount;
            }
            this.entryRemoved(true, illegalStateException, object, null);
        } while (true);
        {
            object = new StringBuilder();
            ((StringBuilder)object).append(this.getClass().getName());
            ((StringBuilder)object).append(".sizeOf() is reporting inconsistent results!");
            illegalStateException = new IllegalStateException(((StringBuilder)object).toString());
            throw illegalStateException;
        }
    }
}

