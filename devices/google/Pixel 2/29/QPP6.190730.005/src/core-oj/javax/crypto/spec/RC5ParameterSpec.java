/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

public class RC5ParameterSpec
implements AlgorithmParameterSpec {
    private byte[] iv = null;
    private int rounds;
    private int version;
    private int wordSize;

    public RC5ParameterSpec(int n, int n2, int n3) {
        this.version = n;
        this.rounds = n2;
        this.wordSize = n3;
    }

    public RC5ParameterSpec(int n, int n2, int n3, byte[] arrby) {
        this(n, n2, n3, arrby, 0);
    }

    public RC5ParameterSpec(int n, int n2, int n3, byte[] arrby, int n4) {
        this.version = n;
        this.rounds = n2;
        this.wordSize = n3;
        if (arrby != null) {
            n = n3 / 8 * 2;
            if (arrby.length - n4 >= n) {
                this.iv = new byte[n];
                System.arraycopy(arrby, n4, this.iv, 0, n);
                return;
            }
            throw new IllegalArgumentException("IV too short");
        }
        throw new IllegalArgumentException("IV missing");
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof RC5ParameterSpec)) {
            return false;
        }
        object = (RC5ParameterSpec)object;
        if (this.version != ((RC5ParameterSpec)object).version || this.rounds != ((RC5ParameterSpec)object).rounds || this.wordSize != ((RC5ParameterSpec)object).wordSize || !Arrays.equals(this.iv, ((RC5ParameterSpec)object).iv)) {
            bl = false;
        }
        return bl;
    }

    public byte[] getIV() {
        Object object = this.iv;
        object = object == null ? null : (byte[])object.clone();
        return object;
    }

    public int getRounds() {
        return this.rounds;
    }

    public int getVersion() {
        return this.version;
    }

    public int getWordSize() {
        return this.wordSize;
    }

    public int hashCode() {
        int n = 0;
        int n2 = 0;
        if (this.iv != null) {
            int n3 = 1;
            do {
                byte[] arrby = this.iv;
                n = n2;
                if (n3 >= arrby.length) break;
                n2 += arrby[n3] * n3;
                ++n3;
            } while (true);
        }
        return n + (this.version + this.rounds + this.wordSize);
    }
}

