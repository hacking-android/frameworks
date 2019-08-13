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

public class DERGeneralString
extends ASN1Primitive
implements ASN1String {
    private final byte[] string;

    public DERGeneralString(String string) {
        this.string = Strings.toByteArray(string);
    }

    DERGeneralString(byte[] arrby) {
        this.string = arrby;
    }

    public static DERGeneralString getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof DERGeneralString)) {
            return new DERGeneralString(((ASN1OctetString)aSN1Primitive).getOctets());
        }
        return DERGeneralString.getInstance(aSN1Primitive);
    }

    public static DERGeneralString getInstance(Object object) {
        if (object != null && !(object instanceof DERGeneralString)) {
            if (object instanceof byte[]) {
                try {
                    object = (DERGeneralString)DERGeneralString.fromByteArray((byte[])object);
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
        return (DERGeneralString)object;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERGeneralString)) {
            return false;
        }
        aSN1Primitive = (DERGeneralString)aSN1Primitive;
        return Arrays.areEqual(this.string, ((DERGeneralString)aSN1Primitive).string);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(27, this.string);
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

    public String toString() {
        return this.getString();
    }
}

