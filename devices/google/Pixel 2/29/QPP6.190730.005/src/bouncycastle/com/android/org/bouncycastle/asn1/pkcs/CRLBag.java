/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;

public class CRLBag
extends ASN1Object {
    private ASN1ObjectIdentifier crlId;
    private ASN1Encodable crlValue;

    public CRLBag(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.crlId = aSN1ObjectIdentifier;
        this.crlValue = aSN1Encodable;
    }

    private CRLBag(ASN1Sequence aSN1Sequence) {
        this.crlId = (ASN1ObjectIdentifier)aSN1Sequence.getObjectAt(0);
        this.crlValue = ((ASN1TaggedObject)aSN1Sequence.getObjectAt(1)).getObject();
    }

    public static CRLBag getInstance(Object object) {
        if (object instanceof CRLBag) {
            return (CRLBag)object;
        }
        if (object != null) {
            return new CRLBag(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public ASN1ObjectIdentifier getCrlId() {
        return this.crlId;
    }

    public ASN1Encodable getCrlValue() {
        return this.crlValue;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.crlId);
        aSN1EncodableVector.add(new DERTaggedObject(0, this.crlValue));
        return new DERSequence(aSN1EncodableVector);
    }
}

