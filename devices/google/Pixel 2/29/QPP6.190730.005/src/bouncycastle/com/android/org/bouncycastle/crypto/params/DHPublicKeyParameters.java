/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.params.DHKeyParameters;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import java.math.BigInteger;

public class DHPublicKeyParameters
extends DHKeyParameters {
    private static final BigInteger ONE = BigInteger.valueOf(1L);
    private static final BigInteger TWO = BigInteger.valueOf(2L);
    private BigInteger y;

    public DHPublicKeyParameters(BigInteger bigInteger, DHParameters dHParameters) {
        super(false, dHParameters);
        this.y = this.validate(bigInteger, dHParameters);
    }

    private BigInteger validate(BigInteger bigInteger, DHParameters dHParameters) {
        if (bigInteger != null) {
            if (bigInteger.compareTo(TWO) >= 0 && bigInteger.compareTo(dHParameters.getP().subtract(TWO)) <= 0) {
                if (dHParameters.getQ() != null) {
                    if (ONE.equals(bigInteger.modPow(dHParameters.getQ(), dHParameters.getP()))) {
                        return bigInteger;
                    }
                    throw new IllegalArgumentException("Y value does not appear to be in correct group");
                }
                return bigInteger;
            }
            throw new IllegalArgumentException("invalid DH public key");
        }
        throw new NullPointerException("y value cannot be null");
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof DHPublicKeyParameters;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        bl = bl2;
        if (((DHPublicKeyParameters)object).getY().equals(this.y)) {
            bl = bl2;
            if (super.equals(object)) {
                bl = true;
            }
        }
        return bl;
    }

    public BigInteger getY() {
        return this.y;
    }

    @Override
    public int hashCode() {
        return this.y.hashCode() ^ super.hashCode();
    }
}

