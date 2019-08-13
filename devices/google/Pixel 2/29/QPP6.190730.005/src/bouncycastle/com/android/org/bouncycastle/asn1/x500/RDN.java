/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x500;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERSet;
import com.android.org.bouncycastle.asn1.x500.AttributeTypeAndValue;

public class RDN
extends ASN1Object {
    private ASN1Set values;

    public RDN(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(aSN1ObjectIdentifier);
        aSN1EncodableVector.add(aSN1Encodable);
        this.values = new DERSet(new DERSequence(aSN1EncodableVector));
    }

    private RDN(ASN1Set aSN1Set) {
        this.values = aSN1Set;
    }

    public RDN(AttributeTypeAndValue attributeTypeAndValue) {
        this.values = new DERSet(attributeTypeAndValue);
    }

    public RDN(AttributeTypeAndValue[] arrattributeTypeAndValue) {
        this.values = new DERSet(arrattributeTypeAndValue);
    }

    public static RDN getInstance(Object object) {
        if (object instanceof RDN) {
            return (RDN)object;
        }
        if (object != null) {
            return new RDN(ASN1Set.getInstance(object));
        }
        return null;
    }

    public AttributeTypeAndValue getFirst() {
        if (this.values.size() == 0) {
            return null;
        }
        return AttributeTypeAndValue.getInstance(this.values.getObjectAt(0));
    }

    public AttributeTypeAndValue[] getTypesAndValues() {
        AttributeTypeAndValue[] arrattributeTypeAndValue = new AttributeTypeAndValue[this.values.size()];
        for (int i = 0; i != arrattributeTypeAndValue.length; ++i) {
            arrattributeTypeAndValue[i] = AttributeTypeAndValue.getInstance(this.values.getObjectAt(i));
        }
        return arrattributeTypeAndValue;
    }

    public boolean isMultiValued() {
        int n = this.values.size();
        boolean bl = true;
        if (n <= 1) {
            bl = false;
        }
        return bl;
    }

    public int size() {
        return this.values.size();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.values;
    }
}

