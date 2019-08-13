/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.security.InvalidKeyException;
import java.security.spec.KeySpec;
import javax.crypto.spec.DESKeySpec;

public class DESedeKeySpec
implements KeySpec {
    public static final int DES_EDE_KEY_LEN = 24;
    private byte[] key;

    public DESedeKeySpec(byte[] arrby) throws InvalidKeyException {
        this(arrby, 0);
    }

    public DESedeKeySpec(byte[] arrby, int n) throws InvalidKeyException {
        if (arrby.length - n >= 24) {
            this.key = new byte[24];
            System.arraycopy(arrby, n, this.key, 0, 24);
            return;
        }
        throw new InvalidKeyException("Wrong key size");
    }

    public static boolean isParityAdjusted(byte[] arrby, int n) throws InvalidKeyException {
        if (arrby.length - n >= 24) {
            return DESKeySpec.isParityAdjusted(arrby, n) && DESKeySpec.isParityAdjusted(arrby, n + 8) && DESKeySpec.isParityAdjusted(arrby, n + 16);
            {
            }
        }
        throw new InvalidKeyException("Wrong key size");
    }

    public byte[] getKey() {
        return (byte[])this.key.clone();
    }
}

