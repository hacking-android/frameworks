/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

public interface ICUCache<K, V> {
    public static final Object NULL = new Object();
    public static final int SOFT = 0;
    public static final int WEAK = 1;

    public void clear();

    public V get(Object var1);

    public void put(K var1, V var2);
}

