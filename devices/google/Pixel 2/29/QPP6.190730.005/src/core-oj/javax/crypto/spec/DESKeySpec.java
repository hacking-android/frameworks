/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.security.InvalidKeyException;
import java.security.spec.KeySpec;

public class DESKeySpec
implements KeySpec {
    public static final int DES_KEY_LEN = 8;
    private static final byte[][] WEAK_KEYS;
    private byte[] key;

    static {
        byte[] arrby = new byte[]{1, 1, 1, 1, 1, 1, 1, 1};
        byte[] arrby2 = new byte[]{31, 31, 31, 31, 14, 14, 14, 14};
        byte[] arrby3 = new byte[]{31, -32, 31, -32, 14, -15, 14, -15};
        byte[] arrby4 = new byte[]{1, -32, 1, -32, 1, -15, 1, -15};
        byte[] arrby5 = new byte[]{31, -2, 31, -2, 14, -2, 14, -2};
        byte[] arrby6 = new byte[]{1, 31, 1, 31, 1, 14, 1, 14};
        byte[] arrby7 = new byte[]{-2, 1, -2, 1, -2, 1, -2, 1};
        byte[] arrby8 = new byte[]{-32, 1, -32, 1, -15, 1, -15, 1};
        byte[] arrby9 = new byte[]{31, 1, 31, 1, 14, 1, 14, 1};
        byte[] arrby10 = new byte[]{-2, -32, -2, -32, -2, -15, -2, -15};
        WEAK_KEYS = new byte[][]{arrby, {-2, -2, -2, -2, -2, -2, -2, -2}, arrby2, {-32, -32, -32, -32, -15, -15, -15, -15}, {1, -2, 1, -2, 1, -2, 1, -2}, arrby3, arrby4, arrby5, arrby6, {-32, -2, -32, -2, -15, -2, -15, -2}, arrby7, {-32, 31, -32, 31, -15, 14, -15, 14}, arrby8, {-2, 31, -2, 31, -2, 14, -2, 14}, arrby9, arrby10};
    }

    public DESKeySpec(byte[] arrby) throws InvalidKeyException {
        this(arrby, 0);
    }

    public DESKeySpec(byte[] arrby, int n) throws InvalidKeyException {
        if (arrby.length - n >= 8) {
            this.key = new byte[8];
            System.arraycopy(arrby, n, this.key, 0, 8);
            return;
        }
        throw new InvalidKeyException("Wrong key size");
    }

    public static boolean isParityAdjusted(byte[] arrby, int n) throws InvalidKeyException {
        if (arrby != null) {
            if (arrby.length - n >= 8) {
                int n2 = 0;
                while (n2 < 8) {
                    if ((Integer.bitCount(arrby[n] & 255) & 1) == 0) {
                        return false;
                    }
                    ++n2;
                    ++n;
                }
                return true;
            }
            throw new InvalidKeyException("Wrong key size");
        }
        throw new InvalidKeyException("null key");
    }

    public static boolean isWeak(byte[] arrby, int n) throws InvalidKeyException {
        if (arrby != null) {
            if (arrby.length - n >= 8) {
                for (int i = 0; i < WEAK_KEYS.length; ++i) {
                    boolean bl = true;
                    for (int j = 0; j < 8 && bl; ++j) {
                        if (WEAK_KEYS[i][j] == arrby[j + n]) continue;
                        bl = false;
                    }
                    if (!bl) continue;
                    return bl;
                }
                return false;
            }
            throw new InvalidKeyException("Wrong key size");
        }
        throw new InvalidKeyException("null key");
    }

    public byte[] getKey() {
        return (byte[])this.key.clone();
    }
}

