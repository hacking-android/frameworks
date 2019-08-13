/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.StreamUtil;
import java.io.IOException;

public class DERTaggedObject
extends ASN1TaggedObject {
    private static final byte[] ZERO_BYTES = new byte[0];

    public DERTaggedObject(int n, ASN1Encodable aSN1Encodable) {
        super(true, n, aSN1Encodable);
    }

    public DERTaggedObject(boolean bl, int n, ASN1Encodable aSN1Encodable) {
        super(bl, n, aSN1Encodable);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        if (!this.empty) {
            ASN1Primitive aSN1Primitive = this.obj.toASN1Primitive().toDERObject();
            if (this.explicit) {
                aSN1OutputStream.writeTag(160, this.tagNo);
                aSN1OutputStream.writeLength(aSN1Primitive.encodedLength());
                aSN1OutputStream.writeObject(aSN1Primitive);
            } else {
                int n = aSN1Primitive.isConstructed() ? 160 : 128;
                aSN1OutputStream.writeTag(n, this.tagNo);
                aSN1OutputStream.writeImplicitObject(aSN1Primitive);
            }
        } else {
            aSN1OutputStream.writeEncoded(160, this.tagNo, ZERO_BYTES);
        }
    }

    @Override
    int encodedLength() throws IOException {
        if (!this.empty) {
            int n = this.obj.toASN1Primitive().toDERObject().encodedLength();
            if (this.explicit) {
                return StreamUtil.calculateTagLength(this.tagNo) + StreamUtil.calculateBodyLength(n) + n;
            }
            return StreamUtil.calculateTagLength(this.tagNo) + (n - 1);
        }
        return StreamUtil.calculateTagLength(this.tagNo) + 1;
    }

    @Override
    boolean isConstructed() {
        if (!this.empty) {
            if (this.explicit) {
                return true;
            }
            return this.obj.toASN1Primitive().toDERObject().isConstructed();
        }
        return true;
    }
}

