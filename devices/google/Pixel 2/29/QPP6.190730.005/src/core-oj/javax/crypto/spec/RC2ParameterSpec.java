/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

public class RC2ParameterSpec
implements AlgorithmParameterSpec {
    private int effectiveKeyBits;
    private byte[] iv = null;

    public RC2ParameterSpec(int n) {
        this.effectiveKeyBits = n;
    }

    public RC2ParameterSpec(int n, byte[] arrby) {
        this(n, arrby, 0);
    }

    public RC2ParameterSpec(int n, byte[] arrby, int n2) {
        this.effectiveKeyBits = n;
        if (arrby != null) {
            if (arrby.length - n2 >= 8) {
                this.iv = new byte[8];
                System.arraycopy(arrby, n2, this.iv, 0, 8);
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
        if (!(object instanceof RC2ParameterSpec)) {
            return false;
        }
        object = (RC2ParameterSpec)object;
        if (this.effectiveKeyBits != ((RC2ParameterSpec)object).effectiveKeyBits || !Arrays.equals(this.iv, ((RC2ParameterSpec)object).iv)) {
            bl = false;
        }
        return bl;
    }

    public int getEffectiveKeyBits() {
        return this.effectiveKeyBits;
    }

    public byte[] getIV() {
        Object object = this.iv;
        object = object == null ? null : (byte[])object.clone();
        return object;
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
        return this.effectiveKeyBits + n;
    }
}

