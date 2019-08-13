/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1BitString;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.StreamUtil;
import java.io.IOException;

public class DLBitString
extends ASN1BitString {
    protected DLBitString(byte by, int n) {
        this(DLBitString.toByteArray(by), n);
    }

    public DLBitString(int n) {
        super(DLBitString.getBytes(n), DLBitString.getPadBits(n));
    }

    public DLBitString(ASN1Encodable aSN1Encodable) throws IOException {
        super(aSN1Encodable.toASN1Primitive().getEncoded("DER"), 0);
    }

    public DLBitString(byte[] arrby) {
        this(arrby, 0);
    }

    public DLBitString(byte[] arrby, int n) {
        super(arrby, n);
    }

    static DLBitString fromOctetString(byte[] arrby) {
        if (arrby.length >= 1) {
            byte by = arrby[0];
            byte[] arrby2 = new byte[arrby.length - 1];
            if (arrby2.length != 0) {
                System.arraycopy((byte[])arrby, (int)1, (byte[])arrby2, (int)0, (int)(arrby.length - 1));
            }
            return new DLBitString(arrby2, (int)by);
        }
        throw new IllegalArgumentException("truncated BIT STRING detected");
    }

    public static ASN1BitString getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof DLBitString)) {
            return DLBitString.fromOctetString(((ASN1OctetString)aSN1Primitive).getOctets());
        }
        return DLBitString.getInstance(aSN1Primitive);
    }

    public static ASN1BitString getInstance(Object object) {
        if (object != null && !(object instanceof DLBitString)) {
            if (object instanceof DERBitString) {
                return (DERBitString)object;
            }
            if (object instanceof byte[]) {
                try {
                    object = (ASN1BitString)DLBitString.fromByteArray((byte[])object);
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
        return (DLBitString)object;
    }

    private static byte[] toByteArray(byte by) {
        return new byte[]{by};
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        byte[] arrby = this.data;
        byte[] arrby2 = new byte[arrby.length + 1];
        arrby2[0] = (byte)this.getPadBits();
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)1, (int)(arrby2.length - 1));
        aSN1OutputStream.writeEncoded(3, arrby2);
    }

    @Override
    int encodedLength() {
        return StreamUtil.calculateBodyLength(this.data.length + 1) + 1 + this.data.length + 1;
    }

    @Override
    boolean isConstructed() {
        return false;
    }
}

