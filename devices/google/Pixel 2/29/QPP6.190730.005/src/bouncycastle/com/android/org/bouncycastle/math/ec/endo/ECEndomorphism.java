/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.endo;

import com.android.org.bouncycastle.math.ec.ECPointMap;

public interface ECEndomorphism {
    public ECPointMap getPointMap();

    public boolean hasEfficientPointMap();
}

