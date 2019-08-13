/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.StreamUtil;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.util.Enumeration;

public class DERSet
extends ASN1Set {
    private int bodyLength = -1;

    public DERSet() {
    }

    public DERSet(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
    }

    @UnsupportedAppUsage
    public DERSet(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector, true);
    }

    DERSet(ASN1EncodableVector aSN1EncodableVector, boolean bl) {
        super(aSN1EncodableVector, bl);
    }

    public DERSet(ASN1Encodable[] arraSN1Encodable) {
        super(arraSN1Encodable, true);
    }

    private int getBodyLength() throws IOException {
        if (this.bodyLength < 0) {
            int n = 0;
            Enumeration enumeration = this.getObjects();
            while (enumeration.hasMoreElements()) {
                n += ((ASN1Encodable)enumeration.nextElement()).toASN1Primitive().toDERObject().encodedLength();
            }
            this.bodyLength = n;
        }
        return this.bodyLength;
    }

    @Override
    void encode(ASN1OutputStream object) throws IOException {
        ASN1OutputStream aSN1OutputStream = ((ASN1OutputStream)object).getDERSubStream();
        int n = this.getBodyLength();
        ((ASN1OutputStream)object).write(49);
        ((ASN1OutputStream)object).writeLength(n);
        object = this.getObjects();
        while (object.hasMoreElements()) {
            aSN1OutputStream.writeObject((ASN1Encodable)object.nextElement());
        }
    }

    @Override
    int encodedLength() throws IOException {
        int n = this.getBodyLength();
        return StreamUtil.calculateBodyLength(n) + 1 + n;
    }
}

