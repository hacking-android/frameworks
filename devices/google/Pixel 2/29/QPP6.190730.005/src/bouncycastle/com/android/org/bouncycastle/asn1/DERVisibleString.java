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

public class DERVisibleString
extends ASN1Primitive
implements ASN1String {
    private final byte[] string;

    public DERVisibleString(String string) {
        this.string = Strings.toByteArray(string);
    }

    DERVisibleString(byte[] arrby) {
        this.string = arrby;
    }

    public static DERVisibleString getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof DERVisibleString)) {
            return new DERVisibleString(ASN1OctetString.getInstance(aSN1Primitive).getOctets());
        }
        return DERVisibleString.getInstance(aSN1Primitive);
    }

    public static DERVisibleString getInstance(Object object) {
        if (object != null && !(object instanceof DERVisibleString)) {
            if (object instanceof byte[]) {
                try {
                    object = (DERVisibleString)DERVisibleString.fromByteArray((byte[])object);
                    return object;
                }
                catch (Exception exception) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("encoding error in getInstance: ");
                    ((StringBuilder)object).append(exception.toString());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("illegal object in getInstance: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (DERVisibleString)object;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERVisibleString)) {
            return false;
        }
        return Arrays.areEqual(this.string, ((DERVisibleString)aSN1Primitive).string);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(26, this.string);
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

