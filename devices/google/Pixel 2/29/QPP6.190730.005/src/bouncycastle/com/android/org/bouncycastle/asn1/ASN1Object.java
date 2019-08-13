/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DEROutputStream;
import com.android.org.bouncycastle.asn1.DLOutputStream;
import com.android.org.bouncycastle.util.Encodable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class ASN1Object
implements ASN1Encodable,
Encodable {
    protected static boolean hasEncodedTagValue(Object object, int n) {
        boolean bl;
        boolean bl2 = object instanceof byte[];
        boolean bl3 = bl = false;
        if (bl2) {
            bl3 = bl;
            if (((byte[])object)[0] == n) {
                bl3 = true;
            }
        }
        return bl3;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ASN1Encodable)) {
            return false;
        }
        object = (ASN1Encodable)object;
        return this.toASN1Primitive().equals(object.toASN1Primitive());
    }

    @Override
    public byte[] getEncoded() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new ASN1OutputStream(byteArrayOutputStream).writeObject(this);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getEncoded(String object) throws IOException {
        if (((String)object).equals("DER")) {
            object = new ByteArrayOutputStream();
            new DEROutputStream((OutputStream)object).writeObject(this);
            return ((ByteArrayOutputStream)object).toByteArray();
        }
        if (((String)object).equals("DL")) {
            object = new ByteArrayOutputStream();
            new DLOutputStream((OutputStream)object).writeObject(this);
            return ((ByteArrayOutputStream)object).toByteArray();
        }
        return this.getEncoded();
    }

    public int hashCode() {
        return this.toASN1Primitive().hashCode();
    }

    public ASN1Primitive toASN1Object() {
        return this.toASN1Primitive();
    }

    @Override
    public abstract ASN1Primitive toASN1Primitive();
}

