/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class TimedEvent<T> {
    public static <T> Map<T, Double> averageTimings(Collection<? extends TimedEvent<T>> timedEvent2) {
        HashMap<T, Integer> hashMap = new HashMap<T, Integer>();
        HashMap<Object, Double> hashMap2 = new HashMap<Object, Double>();
        Iterator<Object> iterator = timedEvent2.iterator();
        while (iterator.hasNext()) {
            TimedEvent timedEvent = (TimedEvent)iterator.next();
            if (hashMap.containsKey(timedEvent.getKey())) {
                hashMap.put(timedEvent.getKey(), (Integer)hashMap.get(timedEvent.getKey()) + 1);
                hashMap2.put(timedEvent.getKey(), (Double)hashMap2.get(timedEvent.getKey()) + (double)timedEvent.getTime());
                continue;
            }
            hashMap.put(timedEvent.getKey(), 1);
            hashMap2.put(timedEvent.getKey(), Double.valueOf(timedEvent.getTime()));
        }
        for (Map.Entry entry : hashMap2.entrySet()) {
            hashMap2.put(entry.getKey(), (Double)entry.getValue() / (double)((Integer)hashMap.get(entry.getKey())).intValue());
        }
        return hashMap2;
    }

    public abstract T getKey();

    public abstract long getTime();
}

