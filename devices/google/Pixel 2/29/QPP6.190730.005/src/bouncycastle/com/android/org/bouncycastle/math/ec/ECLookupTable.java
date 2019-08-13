/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.ECPoint;

public interface ECLookupTable {
    public int getSize();

    public ECPoint lookup(int var1);
}

