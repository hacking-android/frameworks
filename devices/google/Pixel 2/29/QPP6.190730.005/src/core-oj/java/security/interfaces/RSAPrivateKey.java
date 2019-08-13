/*
 * Decompiled with CFR 0.145.
 */
package java.security.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.interfaces.RSAKey;

public interface RSAPrivateKey
extends PrivateKey,
RSAKey {
    public static final long serialVersionUID = 5187144804936595022L;

    public BigInteger getPrivateExponent();
}

