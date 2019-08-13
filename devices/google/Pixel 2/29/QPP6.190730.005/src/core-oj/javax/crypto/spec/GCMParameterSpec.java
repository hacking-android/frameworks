/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.security.spec.AlgorithmParameterSpec;

public class GCMParameterSpec
implements AlgorithmParameterSpec {
    private byte[] iv;
    private int tLen;

    public GCMParameterSpec(int n, byte[] arrby) {
        if (arrby != null) {
            this.init(n, arrby, 0, arrby.length);
            return;
        }
        throw new IllegalArgumentException("src array is null");
    }

    public GCMParameterSpec(int n, byte[] arrby, int n2, int n3) {
        this.init(n, arrby, n2, n3);
    }

    private void init(int n, byte[] arrby, int n2, int n3) {
        if (n >= 0) {
            this.tLen = n;
            if (arrby != null && n3 >= 0 && n2 >= 0 && n3 + n2 <= arrby.length) {
                this.iv = new byte[n3];
                System.arraycopy(arrby, n2, this.iv, 0, n3);
                return;
            }
            throw new IllegalArgumentException("Invalid buffer arguments");
        }
        throw new IllegalArgumentException("Length argument is negative");
    }

    public byte[] getIV() {
        return (byte[])this.iv.clone();
    }

    public int getTLen() {
        return this.tLen;
    }
}

