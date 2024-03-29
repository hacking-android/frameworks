/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import java.io.IOException;
import java.io.OutputStream;

public class DLOutputStream
extends ASN1OutputStream {
    public DLOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void writeObject(ASN1Encodable aSN1Encodable) throws IOException {
        if (aSN1Encodable != null) {
            aSN1Encodable.toASN1Primitive().toDLObject().encode(this);
            return;
        }
        throw new IOException("null object detected");
    }
}

