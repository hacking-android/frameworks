/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Null;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;

public class DERNull
extends ASN1Null {
    @UnsupportedAppUsage
    public static final DERNull INSTANCE = new DERNull();
    private static final byte[] zeroBytes = new byte[0];

    protected DERNull() {
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(5, zeroBytes);
    }

    @Override
    int encodedLength() {
        return 2;
    }

    @Override
    boolean isConstructed() {
        return false;
    }
}

