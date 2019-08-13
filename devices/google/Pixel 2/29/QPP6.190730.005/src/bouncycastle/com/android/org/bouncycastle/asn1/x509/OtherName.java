/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;

public class OtherName
extends ASN1Object {
    private final ASN1ObjectIdentifier typeID;
    private final ASN1Encodable value;

    public OtherName(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.typeID = aSN1ObjectIdentifier;
        this.value = aSN1Encodable;
    }

    private OtherName(ASN1Sequence aSN1Sequence) {
        this.typeID = ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
        this.value = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(1)).getObject();
    }

    public static OtherName getInstance(Object object) {
        if (object instanceof OtherName) {
            return (OtherName)object;
        }
        if (object != null) {
            return new OtherName(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public ASN1ObjectIdentifier getTypeID() {
        return this.typeID;
    }

    public ASN1Encodable getValue() {
        return this.value;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.typeID);
        aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.value));
        return new DERSequence(aSN1EncodableVector);
    }
}

