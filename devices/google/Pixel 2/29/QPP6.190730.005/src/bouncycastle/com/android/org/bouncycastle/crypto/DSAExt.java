/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.DSA;
import java.math.BigInteger;

public interface DSAExt
extends DSA {
    public BigInteger getOrder();
}

