/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import java.util.Arrays;

public final class Pin {
    public final byte[] digest;
    public final String digestAlgorithm;
    private final int mHashCode;

    public Pin(String string2, byte[] arrby) {
        this.digestAlgorithm = string2;
        this.digest = arrby;
        this.mHashCode = Arrays.hashCode(arrby) ^ string2.hashCode();
    }

    public static int getDigestLength(String string2) {
        if ("SHA-256".equalsIgnoreCase(string2)) {
            return 32;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported digest algorithm: ");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static boolean isSupportedDigestAlgorithm(String string2) {
        return "SHA-256".equalsIgnoreCase(string2);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Pin)) {
            return false;
        }
        if (((Pin)(object = (Pin)object)).hashCode() != this.mHashCode) {
            return false;
        }
        if (!Arrays.equals(this.digest, ((Pin)object).digest)) {
            return false;
        }
        return this.digestAlgorithm.equals(((Pin)object).digestAlgorithm);
    }

    public int hashCode() {
        return this.mHashCode;
    }
}

