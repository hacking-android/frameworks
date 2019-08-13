/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Enumeration;

public abstract class Dictionary<K, V> {
    public abstract Enumeration<V> elements();

    public abstract V get(Object var1);

    public abstract boolean isEmpty();

    public abstract Enumeration<K> keys();

    public abstract V put(K var1, V var2);

    public abstract V remove(Object var1);

    public abstract int size();
}

