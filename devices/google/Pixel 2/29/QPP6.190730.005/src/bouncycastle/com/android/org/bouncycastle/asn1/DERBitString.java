/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1BitString;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DLBitString;
import com.android.org.bouncycastle.asn1.StreamUtil;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;

public class DERBitString
extends ASN1BitString {
    protected DERBitString(byte by, int n) {
        this(DERBitString.toByteArray(by), n);
    }

    public DERBitString(int n) {
        super(DERBitString.getBytes(n), DERBitString.getPadBits(n));
    }

    public DERBitString(ASN1Encodable aSN1Encodable) throws IOException {
        super(aSN1Encodable.toASN1Primitive().getEncoded("DER"), 0);
    }

    @UnsupportedAppUsage
    public DERBitString(byte[] arrby) {
        this(arrby, 0);
    }

    public DERBitString(byte[] arrby, int n) {
        super(arrby, n);
    }

    static DERBitString fromOctetString(byte[] arrby) {
        if (arrby.length >= 1) {
            byte by = arrby[0];
            byte[] arrby2 = new byte[arrby.length - 1];
            if (arrby2.length != 0) {
                System.arraycopy((byte[])arrby, (int)1, (byte[])arrby2, (int)0, (int)(arrby.length - 1));
            }
            return new DERBitString(arrby2, (int)by);
        }
        throw new IllegalArgumentException("truncated BIT STRING detected");
    }

    public static DERBitString getInstance(ASN1TaggedObject aSN1Primitive, boolean bl) {
        aSN1Primitive = aSN1Primitive.getObject();
        if (!bl && !(aSN1Primitive instanceof DERBitString)) {
            return DERBitString.fromOctetString(((ASN1OctetString)aSN1Primitive).getOctets());
        }
        return DERBitString.getInstance(aSN1Primitive);
    }

    public static DERBitString getInstance(Object object) {
        if (object != null && !(object instanceof DERBitString)) {
            if (object instanceof DLBitString) {
                return new DERBitString(((DLBitString)object).data, ((DLBitString)object).padBits);
            }
            if (object instanceof byte[]) {
                try {
                    object = (DERBitString)DERBitString.fromByteArray((byte[])object);
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
        return (DERBitString)object;
    }

    private static byte[] toByteArray(byte by) {
        return new byte[]{by};
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        byte[] arrby = DERBitString.derForm(this.data, this.padBits);
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

