/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.interfaces;

import com.android.org.bouncycastle.jce.interfaces.ECKey;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.security.PublicKey;

public interface ECPublicKey
extends ECKey,
PublicKey {
    public ECPoint getQ();
}

