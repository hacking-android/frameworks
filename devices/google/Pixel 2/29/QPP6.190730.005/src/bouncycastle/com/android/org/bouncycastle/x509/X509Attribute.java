/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.DERSet;
import com.android.org.bouncycastle.asn1.x509.Attribute;

public class X509Attribute
extends ASN1Object {
    Attribute attr;

    X509Attribute(ASN1Encodable aSN1Encodable) {
        this.attr = Attribute.getInstance(aSN1Encodable);
    }

    public X509Attribute(String string, ASN1Encodable aSN1Encodable) {
        this.attr = new Attribute(new ASN1ObjectIdentifier(string), new DERSet(aSN1Encodable));
    }

    public X509Attribute(String string, ASN1EncodableVector aSN1EncodableVector) {
        this.attr = new Attribute(new ASN1ObjectIdentifier(string), new DERSet(aSN1EncodableVector));
    }

    public String getOID() {
        return this.attr.getAttrType().getId();
    }

    public ASN1Encodable[] getValues() {
        ASN1Set aSN1Set = this.attr.getAttrValues();
        ASN1Encodable[] arraSN1Encodable = new ASN1Encodable[aSN1Set.size()];
        for (int i = 0; i != aSN1Set.size(); ++i) {
            arraSN1Encodable[i] = aSN1Set.getObjectAt(i);
        }
        return arraSN1Encodable;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.attr.toASN1Primitive();
    }
}

