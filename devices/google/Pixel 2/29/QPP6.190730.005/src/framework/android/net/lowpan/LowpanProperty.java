/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import java.util.Map;

public abstract class LowpanProperty<T> {
    public T getFromMap(Map map) {
        return (T)map.get(this.getName());
    }

    public abstract String getName();

    public abstract Class<T> getType();

    public void putInMap(Map map, T t) {
        map.put(this.getName(), t);
    }
}

