/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OctetStringParser;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.BEROctetString;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Strings;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class ASN1OctetString
extends ASN1Primitive
implements ASN1OctetStringParser {
    byte[] string;

    public ASN1OctetString(byte[] arrby) {
        if (arrby != null) {
            this.string = arrby;
            return;
        }
        throw new NullPointerException("string cannot be null");
    }

    public static ASN1OctetString getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof ASN1OctetString)) {
            return BEROctetString.fromSequence(ASN1Sequence.getInstance(aSN1Primitive));
        }
        return ASN1OctetString.getInstance(aSN1Primitive);
    }

    public static ASN1OctetString getInstance(Object object) {
        if (object != null && !(object instanceof ASN1OctetString)) {
            Object object2;
            if (object instanceof byte[]) {
                try {
                    object = ASN1OctetString.getInstance(ASN1Primitive.fromByteArray((byte[])object));
                    return object;
                }
                catch (IOException iOException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("failed to construct OCTET STRING from byte[]: ");
                    ((StringBuilder)object).append(iOException.getMessage());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            }
            if (object instanceof ASN1Encodable && (object2 = ((ASN1Encodable)object).toASN1Primitive()) instanceof ASN1OctetString) {
                return (ASN1OctetString)object2;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("illegal object in getInstance: ");
            ((StringBuilder)object2).append(object.getClass().getName());
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        return (ASN1OctetString)object;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1OctetString)) {
            return false;
        }
        aSN1Primitive = (ASN1OctetString)aSN1Primitive;
        return Arrays.areEqual(this.string, ((ASN1OctetString)aSN1Primitive).string);
    }

    @Override
    abstract void encode(ASN1OutputStream var1) throws IOException;

    @Override
    public ASN1Primitive getLoadedObject() {
        return this.toASN1Primitive();
    }

    @Override
    public InputStream getOctetStream() {
        return new ByteArrayInputStream(this.string);
    }

    public byte[] getOctets() {
        return this.string;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.getOctets());
    }

    public ASN1OctetStringParser parser() {
        return this;
    }

    @Override
    ASN1Primitive toDERObject() {
        return new DEROctetString(this.string);
    }

    @Override
    ASN1Primitive toDLObject() {
        return new DEROctetString(this.string);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#");
        stringBuilder.append(Strings.fromByteArray(Hex.encode(this.string)));
        return stringBuilder.toString();
    }
}

