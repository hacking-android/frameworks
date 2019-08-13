/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1ApplicationSpecific;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1ParsingException;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DERApplicationSpecific
extends ASN1ApplicationSpecific {
    public DERApplicationSpecific(int n, ASN1Encodable aSN1Encodable) throws IOException {
        this(true, n, aSN1Encodable);
    }

    public DERApplicationSpecific(int n, ASN1EncodableVector aSN1EncodableVector) {
        super(true, n, DERApplicationSpecific.getEncodedVector(aSN1EncodableVector));
    }

    public DERApplicationSpecific(int n, byte[] arrby) {
        this(false, n, arrby);
    }

    public DERApplicationSpecific(boolean bl, int n, ASN1Encodable aSN1Encodable) throws IOException {
        boolean bl2 = bl || aSN1Encodable.toASN1Primitive().isConstructed();
        super(bl2, n, DERApplicationSpecific.getEncoding(bl, aSN1Encodable));
    }

    DERApplicationSpecific(boolean bl, int n, byte[] arrby) {
        super(bl, n, arrby);
    }

    private static byte[] getEncodedVector(ASN1EncodableVector aSN1EncodableVector) {
        Object object = new ByteArrayOutputStream();
        for (int i = 0; i != aSN1EncodableVector.size(); ++i) {
            try {
                ((OutputStream)object).write(((ASN1Object)aSN1EncodableVector.get(i)).getEncoded("DER"));
                continue;
            }
            catch (IOException iOException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("malformed object: ");
                ((StringBuilder)object).append(iOException);
                throw new ASN1ParsingException(((StringBuilder)object).toString(), iOException);
            }
        }
        return ((ByteArrayOutputStream)object).toByteArray();
    }

    private static byte[] getEncoding(boolean bl, ASN1Encodable arrby) throws IOException {
        byte[] arrby2 = arrby.toASN1Primitive().getEncoded("DER");
        if (bl) {
            return arrby2;
        }
        int n = DERApplicationSpecific.getLengthOfHeader(arrby2);
        arrby = new byte[arrby2.length - n];
        System.arraycopy((byte[])arrby2, (int)n, (byte[])arrby, (int)0, (int)arrby.length);
        return arrby;
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        int n = 64;
        if (this.isConstructed) {
            n = 64 | 32;
        }
        aSN1OutputStream.writeEncoded(n, this.tag, this.octets);
    }
}

