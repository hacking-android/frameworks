/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public interface SortedMap<K, V>
extends Map<K, V> {
    public Comparator<? super K> comparator();

    @Override
    public Set<Map.Entry<K, V>> entrySet();

    public K firstKey();

    public SortedMap<K, V> headMap(K var1);

    @Override
    public Set<K> keySet();

    public K lastKey();

    public SortedMap<K, V> subMap(K var1, K var2);

    public SortedMap<K, V> tailMap(K var1);

    @Override
    public Collection<V> values();
}

