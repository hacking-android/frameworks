/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.math.BigInteger;
import java.security.spec.KeySpec;

public class DHPrivateKeySpec
implements KeySpec {
    private BigInteger g;
    private BigInteger p;
    private BigInteger x;

    public DHPrivateKeySpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.x = bigInteger;
        this.p = bigInteger2;
        this.g = bigInteger3;
    }

    public BigInteger getG() {
        return this.g;
    }

    public BigInteger getP() {
        return this.p;
    }

    public BigInteger getX() {
        return this.x;
    }
}

