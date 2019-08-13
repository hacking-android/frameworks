/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.interfaces;

import com.android.org.bouncycastle.jce.interfaces.ECKey;
import java.math.BigInteger;
import java.security.PrivateKey;

public interface ECPrivateKey
extends ECKey,
PrivateKey {
    public BigInteger getD();
}

