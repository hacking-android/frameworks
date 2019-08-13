/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1String;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.StreamUtil;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;

public class DERUTF8String
extends ASN1Primitive
implements ASN1String {
    private final byte[] string;

    public DERUTF8String(String string) {
        this.string = Strings.toUTF8ByteArray(string);
    }

    DERUTF8String(byte[] arrby) {
        this.string = arrby;
    }

    public static DERUTF8String getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof DERUTF8String)) {
            return new DERUTF8String(ASN1OctetString.getInstance(aSN1Primitive).getOctets());
        }
        return DERUTF8String.getInstance(aSN1Primitive);
    }

    public static DERUTF8String getInstance(Object object) {
        if (object != null && !(object instanceof DERUTF8String)) {
            if (object instanceof byte[]) {
                try {
                    object = (DERUTF8String)DERUTF8String.fromByteArray((byte[])object);
                    return object;
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("encoding error in getInstance: ");
                    stringBuilder.append(exception.toString());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("illegal object in getInstance: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (DERUTF8String)object;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERUTF8String)) {
            return false;
        }
        aSN1Primitive = (DERUTF8String)aSN1Primitive;
        return Arrays.areEqual(this.string, ((DERUTF8String)aSN1Primitive).string);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(12, this.string);
    }

    @Override
    int encodedLength() throws IOException {
        return StreamUtil.calculateBodyLength(this.string.length) + 1 + this.string.length;
    }

    @Override
    public String getString() {
        return Strings.fromUTF8ByteArray(this.string);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.string);
    }

    @Override
    boolean isConstructed() {
        return false;
    }

    public String toString() {
        return this.getString();
    }
}

