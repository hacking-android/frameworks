/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

public class DHParameterSpec
implements AlgorithmParameterSpec {
    private BigInteger g;
    private int l;
    private BigInteger p;

    public DHParameterSpec(BigInteger bigInteger, BigInteger bigInteger2) {
        this.p = bigInteger;
        this.g = bigInteger2;
        this.l = 0;
    }

    public DHParameterSpec(BigInteger bigInteger, BigInteger bigInteger2, int n) {
        this.p = bigInteger;
        this.g = bigInteger2;
        this.l = n;
    }

    public BigInteger getG() {
        return this.g;
    }

    public int getL() {
        return this.l;
    }

    public BigInteger getP() {
        return this.p;
    }
}

