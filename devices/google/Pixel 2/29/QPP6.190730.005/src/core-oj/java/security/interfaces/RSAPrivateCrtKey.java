/*
 * Decompiled with CFR 0.145.
 */
package java.security.interfaces;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;

public interface RSAPrivateCrtKey
extends RSAPrivateKey {
    public static final long serialVersionUID = -5682214253527700368L;

    public BigInteger getCrtCoefficient();

    public BigInteger getPrimeExponentP();

    public BigInteger getPrimeExponentQ();

    public BigInteger getPrimeP();

    public BigInteger getPrimeQ();

    public BigInteger getPublicExponent();
}

