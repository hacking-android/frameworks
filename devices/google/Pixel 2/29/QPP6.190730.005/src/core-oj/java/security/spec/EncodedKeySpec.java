/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.security.spec.KeySpec;

public abstract class EncodedKeySpec
implements KeySpec {
    private byte[] encodedKey;

    public EncodedKeySpec(byte[] arrby) {
        this.encodedKey = (byte[])arrby.clone();
    }

    public byte[] getEncoded() {
        return (byte[])this.encodedKey.clone();
    }

    public abstract String getFormat();
}

