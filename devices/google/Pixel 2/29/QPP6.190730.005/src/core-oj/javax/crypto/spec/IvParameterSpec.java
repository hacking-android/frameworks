/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.security.spec.AlgorithmParameterSpec;

public class IvParameterSpec
implements AlgorithmParameterSpec {
    private byte[] iv;

    public IvParameterSpec(byte[] arrby) {
        this(arrby, 0, arrby.length);
    }

    public IvParameterSpec(byte[] arrby, int n, int n2) {
        if (arrby != null) {
            if (arrby.length - n >= n2) {
                if (n2 >= 0) {
                    this.iv = new byte[n2];
                    System.arraycopy(arrby, n, this.iv, 0, n2);
                    return;
                }
                throw new ArrayIndexOutOfBoundsException("len is negative");
            }
            throw new IllegalArgumentException("IV buffer too short for given offset/length combination");
        }
        throw new IllegalArgumentException("IV missing");
    }

    public byte[] getIV() {
        return (byte[])this.iv.clone();
    }
}

