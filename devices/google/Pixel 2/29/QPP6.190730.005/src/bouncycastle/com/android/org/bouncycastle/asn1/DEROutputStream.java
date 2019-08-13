/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.io.OutputStream;

public class DEROutputStream
extends ASN1OutputStream {
    @UnsupportedAppUsage
    public DEROutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    ASN1OutputStream getDERSubStream() {
        return this;
    }

    @Override
    ASN1OutputStream getDLSubStream() {
        return this;
    }

    @Override
    public void writeObject(ASN1Encodable aSN1Encodable) throws IOException {
        if (aSN1Encodable != null) {
            aSN1Encodable.toASN1Primitive().toDERObject().encode(this);
            return;
        }
        throw new IOException("null object detected");
    }
}

