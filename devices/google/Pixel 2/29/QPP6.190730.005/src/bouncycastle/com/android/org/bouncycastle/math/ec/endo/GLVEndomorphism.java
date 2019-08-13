/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.endo;

import com.android.org.bouncycastle.math.ec.endo.ECEndomorphism;
import java.math.BigInteger;

public interface GLVEndomorphism
extends ECEndomorphism {
    public BigInteger[] decomposeScalar(BigInteger var1);
}

