/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.math.BigInteger;
import java.security.spec.KeySpec;

public class DHPublicKeySpec
implements KeySpec {
    private BigInteger g;
    private BigInteger p;
    private BigInteger y;

    public DHPublicKeySpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.y = bigInteger;
        this.p = bigInteger2;
        this.g = bigInteger3;
    }

    public BigInteger getG() {
        return this.g;
    }

    public BigInteger getP() {
        return this.p;
    }

    public BigInteger getY() {
        return this.y;
    }
}

