/*
 * Decompiled with CFR 0.145.
 */
package android.telecom.Logging;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class TimedEvent<T> {
    public static <T> Map<T, Double> averageTimings(Collection<? extends TimedEvent<T>> iterator) {
        HashMap<T, Integer> hashMap = new HashMap<T, Integer>();
        HashMap<Object, Double> hashMap2 = new HashMap<Object, Double>();
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            TimedEvent object = (TimedEvent)((Object)iterator.next());
            if (hashMap.containsKey(object.getKey())) {
                hashMap.put(object.getKey(), (Integer)hashMap.get(object.getKey()) + 1);
                hashMap2.put(object.getKey(), (Double)hashMap2.get(object.getKey()) + (double)object.getTime());
                continue;
            }
            hashMap.put(object.getKey(), 1);
            hashMap2.put(object.getKey(), Double.valueOf(object.getTime()));
        }
        for (Map.Entry entry : hashMap2.entrySet()) {
            hashMap2.put(entry.getKey(), (Double)entry.getValue() / (double)((Integer)hashMap.get(entry.getKey())).intValue());
        }
        return hashMap2;
    }

    public abstract T getKey();

    public abstract long getTime();
}

