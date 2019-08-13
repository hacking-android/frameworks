/*
 * Decompiled with CFR 0.145.
 */
package java.security.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;

public interface RSAPublicKey
extends PublicKey,
RSAKey {
    public static final long serialVersionUID = -8727434096241101194L;

    public BigInteger getPublicExponent();
}

