/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.util.Arrays;

public abstract class SNIServerName {
    private static final char[] HEXES = "0123456789ABCDEF".toCharArray();
    private final byte[] encoded;
    private final int type;

    protected SNIServerName(int n, byte[] arrby) {
        if (n >= 0) {
            if (n <= 255) {
                this.type = n;
                if (arrby != null) {
                    this.encoded = (byte[])arrby.clone();
                    return;
                }
                throw new NullPointerException("Server name encoded value cannot be null");
            }
            throw new IllegalArgumentException("Server name type cannot be greater than 255");
        }
        throw new IllegalArgumentException("Server name type cannot be less than zero");
    }

    private static String toHexString(byte[] arrby) {
        if (arrby.length == 0) {
            return "(empty)";
        }
        StringBuilder stringBuilder = new StringBuilder(arrby.length * 3 - 1);
        boolean bl = true;
        for (int n : arrby) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(':');
            }
            stringBuilder.append(HEXES[(n &= 255) >>> 4]);
            stringBuilder.append(HEXES[n & 15]);
        }
        return stringBuilder.toString();
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (SNIServerName)object;
        if (this.type != ((SNIServerName)object).type || !Arrays.equals(this.encoded, ((SNIServerName)object).encoded)) {
            bl = false;
        }
        return bl;
    }

    public final byte[] getEncoded() {
        return (byte[])this.encoded.clone();
    }

    public final int getType() {
        return this.type;
    }

    public int hashCode() {
        return (17 * 31 + this.type) * 31 + Arrays.hashCode(this.encoded);
    }

    public String toString() {
        if (this.type == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("type=host_name (0), value=");
            stringBuilder.append(SNIServerName.toHexString(this.encoded));
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("type=(");
        stringBuilder.append(this.type);
        stringBuilder.append("), value=");
        stringBuilder.append(SNIServerName.toHexString(this.encoded));
        return stringBuilder.toString();
    }
}

