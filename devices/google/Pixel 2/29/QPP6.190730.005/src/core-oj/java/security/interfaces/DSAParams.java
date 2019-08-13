/*
 * Decompiled with CFR 0.145.
 */
package java.security.interfaces;

import java.math.BigInteger;

public interface DSAParams {
    public BigInteger getG();

    public BigInteger getP();

    public BigInteger getQ();
}

