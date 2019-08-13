/*
 * Decompiled with CFR 0.145.
 */
package java.security.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.interfaces.ECKey;

public interface ECPrivateKey
extends PrivateKey,
ECKey {
    public static final long serialVersionUID = -7896394956925609184L;

    public BigInteger getS();
}

