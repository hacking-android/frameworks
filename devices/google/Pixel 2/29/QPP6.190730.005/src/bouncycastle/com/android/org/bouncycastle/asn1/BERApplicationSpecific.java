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

public class BERApplicationSpecific
extends ASN1ApplicationSpecific {
    public BERApplicationSpecific(int n, ASN1Encodable aSN1Encodable) throws IOException {
        this(true, n, aSN1Encodable);
    }

    public BERApplicationSpecific(int n, ASN1EncodableVector aSN1EncodableVector) {
        super(true, n, BERApplicationSpecific.getEncodedVector(aSN1EncodableVector));
    }

    public BERApplicationSpecific(boolean bl, int n, ASN1Encodable aSN1Encodable) throws IOException {
        boolean bl2 = bl || aSN1Encodable.toASN1Primitive().isConstructed();
        super(bl2, n, BERApplicationSpecific.getEncoding(bl, aSN1Encodable));
    }

    BERApplicationSpecific(boolean bl, int n, byte[] arrby) {
        super(bl, n, arrby);
    }

    private static byte[] getEncodedVector(ASN1EncodableVector aSN1EncodableVector) {
        Object object = new ByteArrayOutputStream();
        for (int i = 0; i != aSN1EncodableVector.size(); ++i) {
            try {
                ((OutputStream)object).write(((ASN1Object)aSN1EncodableVector.get(i)).getEncoded("BER"));
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
        arrby = arrby.toASN1Primitive().getEncoded("BER");
        if (bl) {
            return arrby;
        }
        int n = BERApplicationSpecific.getLengthOfHeader(arrby);
        byte[] arrby2 = new byte[arrby.length - n];
        System.arraycopy((byte[])arrby, (int)n, (byte[])arrby2, (int)0, (int)arrby2.length);
        return arrby2;
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        int n = 64;
        if (this.isConstructed) {
            n = 64 | 32;
        }
        aSN1OutputStream.writeTag(n, this.tag);
        aSN1OutputStream.write(128);
        aSN1OutputStream.write(this.octets);
        aSN1OutputStream.write(0);
        aSN1OutputStream.write(0);
    }
}

