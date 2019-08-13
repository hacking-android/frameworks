/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Map;
import java.util.NavigableSet;
import java.util.SortedMap;

public interface NavigableMap<K, V>
extends SortedMap<K, V> {
    public Map.Entry<K, V> ceilingEntry(K var1);

    public K ceilingKey(K var1);

    public NavigableSet<K> descendingKeySet();

    public NavigableMap<K, V> descendingMap();

    public Map.Entry<K, V> firstEntry();

    public Map.Entry<K, V> floorEntry(K var1);

    public K floorKey(K var1);

    public NavigableMap<K, V> headMap(K var1, boolean var2);

    @Override
    public SortedMap<K, V> headMap(K var1);

    public Map.Entry<K, V> higherEntry(K var1);

    public K higherKey(K var1);

    public Map.Entry<K, V> lastEntry();

    public Map.Entry<K, V> lowerEntry(K var1);

    public K lowerKey(K var1);

    public NavigableSet<K> navigableKeySet();

    public Map.Entry<K, V> pollFirstEntry();

    public Map.Entry<K, V> pollLastEntry();

    public NavigableMap<K, V> subMap(K var1, boolean var2, K var3, boolean var4);

    @Override
    public SortedMap<K, V> subMap(K var1, K var2);

    public NavigableMap<K, V> tailMap(K var1, boolean var2);

    @Override
    public SortedMap<K, V> tailMap(K var1);
}

