/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import java.io.IOException;

public abstract class ASN1Primitive
extends ASN1Object {
    ASN1Primitive() {
    }

    public static ASN1Primitive fromByteArray(byte[] object) throws IOException {
        block3 : {
            ASN1InputStream aSN1InputStream = new ASN1InputStream((byte[])object);
            try {
                object = aSN1InputStream.readObject();
                if (aSN1InputStream.available() != 0) break block3;
                return object;
            }
            catch (ClassCastException classCastException) {
                throw new IOException("cannot recognise object in stream");
            }
        }
        object = new IOException("Extra data detected in stream");
        throw object;
    }

    abstract boolean asn1Equals(ASN1Primitive var1);

    abstract void encode(ASN1OutputStream var1) throws IOException;

    abstract int encodedLength() throws IOException;

    @Override
    public final boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof ASN1Encodable) || !this.asn1Equals(((ASN1Encodable)object).toASN1Primitive())) {
            bl = false;
        }
        return bl;
    }

    @Override
    public abstract int hashCode();

    abstract boolean isConstructed();

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this;
    }

    ASN1Primitive toDERObject() {
        return this;
    }

    ASN1Primitive toDLObject() {
        return this;
    }
}

