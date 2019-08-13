/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentMap;

public interface ConcurrentNavigableMap<K, V>
extends ConcurrentMap<K, V>,
NavigableMap<K, V> {
    @Override
    public NavigableSet<K> descendingKeySet();

    @Override
    public ConcurrentNavigableMap<K, V> descendingMap();

    @Override
    public ConcurrentNavigableMap<K, V> headMap(K var1);

    @Override
    public ConcurrentNavigableMap<K, V> headMap(K var1, boolean var2);

    @Override
    public NavigableSet<K> keySet();

    @Override
    public NavigableSet<K> navigableKeySet();

    @Override
    public ConcurrentNavigableMap<K, V> subMap(K var1, K var2);

    @Override
    public ConcurrentNavigableMap<K, V> subMap(K var1, boolean var2, K var3, boolean var4);

    @Override
    public ConcurrentNavigableMap<K, V> tailMap(K var1);

    @Override
    public ConcurrentNavigableMap<K, V> tailMap(K var1, boolean var2);
}

