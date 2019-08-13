/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.signers;

import com.android.org.bouncycastle.crypto.signers.DSAKCalculator;
import com.android.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomDSAKCalculator
implements DSAKCalculator {
    private static final BigInteger ZERO = BigInteger.valueOf(0L);
    private BigInteger q;
    private SecureRandom random;

    @Override
    public void init(BigInteger bigInteger, BigInteger bigInteger2, byte[] arrby) {
        throw new IllegalStateException("Operation not supported");
    }

    @Override
    public void init(BigInteger bigInteger, SecureRandom secureRandom) {
        this.q = bigInteger;
        this.random = secureRandom;
    }

    @Override
    public boolean isDeterministic() {
        return false;
    }

    @Override
    public BigInteger nextK() {
        BigInteger bigInteger;
        int n = this.q.bitLength();
        while ((bigInteger = BigIntegers.createRandomBigInteger(n, this.random)).equals(ZERO) || bigInteger.compareTo(this.q) >= 0) {
        }
        return bigInteger;
    }
}

