/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.security.spec.AlgorithmParameterSpec;

public class PBEParameterSpec
implements AlgorithmParameterSpec {
    private int iterationCount;
    private AlgorithmParameterSpec paramSpec = null;
    private byte[] salt;

    public PBEParameterSpec(byte[] arrby, int n) {
        this.salt = (byte[])arrby.clone();
        this.iterationCount = n;
    }

    public PBEParameterSpec(byte[] arrby, int n, AlgorithmParameterSpec algorithmParameterSpec) {
        this.salt = (byte[])arrby.clone();
        this.iterationCount = n;
        this.paramSpec = algorithmParameterSpec;
    }

    public int getIterationCount() {
        return this.iterationCount;
    }

    public AlgorithmParameterSpec getParameterSpec() {
        return this.paramSpec;
    }

    public byte[] getSalt() {
        return (byte[])this.salt.clone();
    }
}

