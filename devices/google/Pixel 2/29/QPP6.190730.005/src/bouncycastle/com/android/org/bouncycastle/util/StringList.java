/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util;

import com.android.org.bouncycastle.util.Iterable;

public interface StringList
extends Iterable<String> {
    public boolean add(String var1);

    public String get(int var1);

    public int size();

    public String[] toStringArray();

    public String[] toStringArray(int var1, int var2);
}

