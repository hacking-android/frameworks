/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util;

public interface Selector<T>
extends Cloneable {
    public Object clone();

    public boolean match(T var1);
}

