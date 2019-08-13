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

public class DERGraphicString
extends ASN1Primitive
implements ASN1String {
    private final byte[] string;

    public DERGraphicString(byte[] arrby) {
        this.string = Arrays.clone(arrby);
    }

    public static DERGraphicString getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof DERGraphicString)) {
            return new DERGraphicString(((ASN1OctetString)aSN1Primitive).getOctets());
        }
        return DERGraphicString.getInstance(aSN1Primitive);
    }

    public static DERGraphicString getInstance(Object object) {
        if (object != null && !(object instanceof DERGraphicString)) {
            if (object instanceof byte[]) {
                try {
                    object = (DERGraphicString)DERGraphicString.fromByteArray((byte[])object);
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
        return (DERGraphicString)object;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERGraphicString)) {
            return false;
        }
        aSN1Primitive = (DERGraphicString)aSN1Primitive;
        return Arrays.areEqual(this.string, ((DERGraphicString)aSN1Primitive).string);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(25, this.string);
    }

    @Override
    int encodedLength() {
        return StreamUtil.calculateBodyLength(this.string.length) + 1 + this.string.length;
    }

    public byte[] getOctets() {
        return Arrays.clone(this.string);
    }

    @Override
    public String getString() {
        return Strings.fromByteArray(this.string);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.string);
    }

    @Override
    boolean isConstructed() {
        return false;
    }
}

