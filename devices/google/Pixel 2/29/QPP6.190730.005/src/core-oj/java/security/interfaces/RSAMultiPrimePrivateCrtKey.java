/*
 * Decompiled with CFR 0.145.
 */
package java.security.interfaces;

import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAOtherPrimeInfo;

public interface RSAMultiPrimePrivateCrtKey
extends RSAPrivateKey {
    public static final long serialVersionUID = 618058533534628008L;

    public BigInteger getCrtCoefficient();

    public RSAOtherPrimeInfo[] getOtherPrimeInfo();

    public BigInteger getPrimeExponentP();

    public BigInteger getPrimeExponentQ();

    public BigInteger getPrimeP();

    public BigInteger getPrimeQ();

    public BigInteger getPublicExponent();
}

