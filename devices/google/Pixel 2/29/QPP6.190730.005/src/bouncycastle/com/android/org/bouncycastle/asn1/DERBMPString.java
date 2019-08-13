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
import java.io.IOException;

public class DERBMPString
extends ASN1Primitive
implements ASN1String {
    private final char[] string;

    public DERBMPString(String string) {
        this.string = string.toCharArray();
    }

    DERBMPString(byte[] arrby) {
        char[] arrc = new char[arrby.length / 2];
        for (int i = 0; i != arrc.length; ++i) {
            arrc[i] = (char)(arrby[i * 2] << 8 | arrby[i * 2 + 1] & 255);
        }
        this.string = arrc;
    }

    DERBMPString(char[] arrc) {
        this.string = arrc;
    }

    public static DERBMPString getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof DERBMPString)) {
            return new DERBMPString(ASN1OctetString.getInstance(aSN1Primitive).getOctets());
        }
        return DERBMPString.getInstance(aSN1Primitive);
    }

    public static DERBMPString getInstance(Object object) {
        if (object != null && !(object instanceof DERBMPString)) {
            if (object instanceof byte[]) {
                try {
                    object = (DERBMPString)DERBMPString.fromByteArray((byte[])object);
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
        return (DERBMPString)object;
    }

    @Override
    protected boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERBMPString)) {
            return false;
        }
        aSN1Primitive = (DERBMPString)aSN1Primitive;
        return Arrays.areEqual(this.string, ((DERBMPString)aSN1Primitive).string);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        char[] arrc;
        aSN1OutputStream.write(30);
        aSN1OutputStream.writeLength(this.string.length * 2);
        for (int i = 0; i != (arrc = this.string).length; ++i) {
            char c = arrc[i];
            aSN1OutputStream.write((byte)(c >> 8));
            aSN1OutputStream.write((byte)c);
        }
    }

    @Override
    int encodedLength() {
        return StreamUtil.calculateBodyLength(this.string.length * 2) + 1 + this.string.length * 2;
    }

    @Override
    public String getString() {
        return new String(this.string);
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

