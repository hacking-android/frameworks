/*
 * Decompiled with CFR 0.145.
 */
package java.security.spec;

import java.security.spec.EncodedKeySpec;

public class PKCS8EncodedKeySpec
extends EncodedKeySpec {
    public PKCS8EncodedKeySpec(byte[] arrby) {
        super(arrby);
    }

    @Override
    public byte[] getEncoded() {
        return super.getEncoded();
    }

    @Override
    public final String getFormat() {
        return "PKCS#8";
    }
}

