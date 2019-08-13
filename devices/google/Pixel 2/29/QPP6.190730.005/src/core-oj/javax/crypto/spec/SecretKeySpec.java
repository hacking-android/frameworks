/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto.spec;

import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Locale;
import javax.crypto.SecretKey;

public class SecretKeySpec
implements KeySpec,
SecretKey {
    private static final long serialVersionUID = 6577238317307289933L;
    private String algorithm;
    private byte[] key;

    public SecretKeySpec(byte[] arrby, int n, int n2, String string) {
        if (arrby != null && string != null) {
            if (arrby.length != 0) {
                if (arrby.length - n >= n2) {
                    if (n2 >= 0) {
                        this.key = new byte[n2];
                        System.arraycopy(arrby, n, this.key, 0, n2);
                        this.algorithm = string;
                        return;
                    }
                    throw new ArrayIndexOutOfBoundsException("len is negative");
                }
                throw new IllegalArgumentException("Invalid offset/length combination");
            }
            throw new IllegalArgumentException("Empty key");
        }
        throw new IllegalArgumentException("Missing argument");
    }

    public SecretKeySpec(byte[] arrby, String string) {
        if (arrby != null && string != null) {
            if (arrby.length != 0) {
                this.key = (byte[])arrby.clone();
                this.algorithm = string;
                return;
            }
            throw new IllegalArgumentException("Empty key");
        }
        throw new IllegalArgumentException("Missing argument");
    }

    public boolean equals(Object arrby) {
        if (this == arrby) {
            return true;
        }
        if (!(arrby instanceof SecretKey)) {
            return false;
        }
        String string = ((SecretKey)arrby).getAlgorithm();
        if (!(string.equalsIgnoreCase(this.algorithm) || string.equalsIgnoreCase("DESede") && this.algorithm.equalsIgnoreCase("TripleDES") || string.equalsIgnoreCase("TripleDES") && this.algorithm.equalsIgnoreCase("DESede"))) {
            return false;
        }
        arrby = ((SecretKey)arrby).getEncoded();
        return MessageDigest.isEqual(this.key, arrby);
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public byte[] getEncoded() {
        return (byte[])this.key.clone();
    }

    @Override
    public String getFormat() {
        return "RAW";
    }

    public int hashCode() {
        byte[] arrby;
        int n = 0;
        for (int i = 1; i < (arrby = this.key).length; ++i) {
            n += arrby[i] * i;
        }
        if (this.algorithm.equalsIgnoreCase("TripleDES")) {
            return "desede".hashCode() ^ n;
        }
        return this.algorithm.toLowerCase(Locale.ENGLISH).hashCode() ^ n;
    }
}

