/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.security.PublicKey;
import java.util.Arrays;

final class X509PublicKey
implements PublicKey {
    private static final long serialVersionUID = -8610156854731664298L;
    private final String algorithm;
    private final byte[] encoded;

    @UnsupportedAppUsage
    X509PublicKey(String string, byte[] arrby) {
        this.algorithm = string;
        this.encoded = arrby;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (X509PublicKey)object;
        String string = this.algorithm;
        if (string == null ? ((X509PublicKey)object).algorithm != null : !string.equals(((X509PublicKey)object).algorithm)) {
            return false;
        }
        return Arrays.equals(this.encoded, ((X509PublicKey)object).encoded);
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public byte[] getEncoded() {
        return this.encoded;
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    public int hashCode() {
        String string = this.algorithm;
        int n = string == null ? 0 : string.hashCode();
        return (1 * 31 + n) * 31 + Arrays.hashCode(this.encoded);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("X509PublicKey [algorithm=");
        stringBuilder.append(this.algorithm);
        stringBuilder.append(", encoded=");
        stringBuilder.append(Arrays.toString(this.encoded));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

