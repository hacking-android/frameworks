/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.security.spec.AlgorithmParameterSpec;

public class DHGenParameterSpec
implements AlgorithmParameterSpec {
    private int exponentSize;
    private int primeSize;

    public DHGenParameterSpec(int n, int n2) {
        this.primeSize = n;
        this.exponentSize = n2;
    }

    public int getExponentSize() {
        return this.exponentSize;
    }

    public int getPrimeSize() {
        return this.primeSize;
    }
}

