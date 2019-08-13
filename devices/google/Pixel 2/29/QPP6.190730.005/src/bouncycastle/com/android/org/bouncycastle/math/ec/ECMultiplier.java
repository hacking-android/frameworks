/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;

public interface ECMultiplier {
    public ECPoint multiply(ECPoint var1, BigInteger var2);
}

