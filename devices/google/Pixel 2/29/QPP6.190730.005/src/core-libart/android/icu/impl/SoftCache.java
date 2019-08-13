/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.CacheBase;
import android.icu.impl.CacheValue;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SoftCache<K, V, D>
extends CacheBase<K, V, D> {
    private ConcurrentHashMap<K, Object> map = new ConcurrentHashMap();

    @Override
    public final V getInstance(K object, D object2) {
        Object object3 = this.map.get(object);
        if (object3 != null) {
            if (!(object3 instanceof CacheValue)) {
                return (V)object3;
            }
            CacheValue cacheValue = (CacheValue)object3;
            if (cacheValue.isNull()) {
                return null;
            }
            object3 = cacheValue.get();
            if (object3 != null) {
                return (V)object3;
            }
            return cacheValue.resetIfCleared(this.createInstance(object, object2));
        }
        object3 = this.createInstance(object, object2);
        object2 = object3 != null && CacheValue.futureInstancesWillBeStrong() ? object3 : CacheValue.getInstance(object3);
        if ((object = this.map.putIfAbsent(object, object2)) == null) {
            return (V)object3;
        }
        if (!(object instanceof CacheValue)) {
            return (V)object;
        }
        return (V)((CacheValue)object).resetIfCleared(object3);
    }
}

