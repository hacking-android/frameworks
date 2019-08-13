/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

public abstract class CacheBase<K, V, D> {
    protected abstract V createInstance(K var1, D var2);

    public abstract V getInstance(K var1, D var2);
}

