/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;

public class RSAKeyGenParameterSpec
implements AlgorithmParameterSpec {
    public static final BigInteger F0 = BigInteger.valueOf(3L);
    public static final BigInteger F4 = BigInteger.valueOf(65537L);
    private int keysize;
    private BigInteger publicExponent;

    public RSAKeyGenParameterSpec(int n, BigInteger bigInteger) {
        this.keysize = n;
        this.publicExponent = bigInteger;
    }

    public int getKeysize() {
        return this.keysize;
    }

    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }
}

