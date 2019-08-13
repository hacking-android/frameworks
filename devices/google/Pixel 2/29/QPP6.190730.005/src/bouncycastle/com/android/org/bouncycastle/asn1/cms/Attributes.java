/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.cms;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DLSet;
import com.android.org.bouncycastle.asn1.cms.Attribute;

public class Attributes
extends ASN1Object {
    private ASN1Set attributes;

    public Attributes(ASN1EncodableVector aSN1EncodableVector) {
        this.attributes = new DLSet(aSN1EncodableVector);
    }

    private Attributes(ASN1Set aSN1Set) {
        this.attributes = aSN1Set;
    }

    public static Attributes getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return Attributes.getInstance(ASN1Set.getInstance(aSN1TaggedObject, bl));
    }

    public static Attributes getInstance(Object object) {
        if (object instanceof Attributes) {
            return (Attributes)object;
        }
        if (object != null) {
            return new Attributes(ASN1Set.getInstance(object));
        }
        return null;
    }

    public Attribute[] getAttributes() {
        Attribute[] arrattribute = new Attribute[this.attributes.size()];
        for (int i = 0; i != arrattribute.length; ++i) {
            arrattribute[i] = Attribute.getInstance(this.attributes.getObjectAt(i));
        }
        return arrattribute;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.attributes;
    }
}

