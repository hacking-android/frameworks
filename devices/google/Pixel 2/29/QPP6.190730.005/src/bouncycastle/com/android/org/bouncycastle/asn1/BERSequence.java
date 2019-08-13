/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import java.io.IOException;
import java.util.Enumeration;

public class BERSequence
extends ASN1Sequence {
    public BERSequence() {
    }

    public BERSequence(ASN1Encodable aSN1Encodable) {
        super(aSN1Encodable);
    }

    public BERSequence(ASN1EncodableVector aSN1EncodableVector) {
        super(aSN1EncodableVector);
    }

    public BERSequence(ASN1Encodable[] arraSN1Encodable) {
        super(arraSN1Encodable);
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        aSN1OutputStream.write(48);
        aSN1OutputStream.write(128);
        Enumeration enumeration = this.getObjects();
        while (enumeration.hasMoreElements()) {
            aSN1OutputStream.writeObject((ASN1Encodable)enumeration.nextElement());
        }
        aSN1OutputStream.write(0);
        aSN1OutputStream.write(0);
    }

    @Override
    int encodedLength() throws IOException {
        int n = 0;
        Enumeration enumeration = this.getObjects();
        while (enumeration.hasMoreElements()) {
            n += ((ASN1Encodable)enumeration.nextElement()).toASN1Primitive().encodedLength();
        }
        return n + 2 + 2;
    }
}

