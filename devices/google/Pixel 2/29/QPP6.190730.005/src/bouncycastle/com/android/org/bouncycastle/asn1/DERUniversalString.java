/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1ParsingException;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1String;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.StreamUtil;
import com.android.org.bouncycastle.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DERUniversalString
extends ASN1Primitive
implements ASN1String {
    private static final char[] table = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final byte[] string;

    public DERUniversalString(byte[] arrby) {
        this.string = Arrays.clone(arrby);
    }

    public static DERUniversalString getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof DERUniversalString)) {
            return new DERUniversalString(((ASN1OctetString)aSN1Primitive).getOctets());
        }
        return DERUniversalString.getInstance(aSN1Primitive);
    }

    public static DERUniversalString getInstance(Object object) {
        if (object != null && !(object instanceof DERUniversalString)) {
            if (object instanceof byte[]) {
                try {
                    object = (DERUniversalString)DERUniversalString.fromByteArray((byte[])object);
                    return object;
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("encoding error getInstance: ");
                    stringBuilder.append(exception.toString());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("illegal object in getInstance: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (DERUniversalString)object;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof DERUniversalString)) {
            return false;
        }
        return Arrays.areEqual(this.string, ((DERUniversalString)aSN1Primitive).string);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.writeEncoded(28, this.getOctets());
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
        StringBuffer stringBuffer = new StringBuffer("#");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrby = new ASN1OutputStream(byteArrayOutputStream);
        try {
            arrby.writeObject(this);
            arrby = byteArrayOutputStream.toByteArray();
        }
        catch (IOException iOException) {
            throw new ASN1ParsingException("internal error encoding UniversalString");
        }
        for (int i = 0; i != arrby.length; ++i) {
            stringBuffer.append(table[arrby[i] >>> 4 & 15]);
            stringBuffer.append(table[arrby[i] & 15]);
        }
        return stringBuffer.toString();
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

