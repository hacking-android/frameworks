/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.cms;

import com.android.org.bouncycastle.asn1.ASN1Choice;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.cms.IssuerAndSerialNumber;

public class SignerIdentifier
extends ASN1Object
implements ASN1Choice {
    private ASN1Encodable id;

    public SignerIdentifier(ASN1OctetString aSN1OctetString) {
        this.id = new DERTaggedObject(false, 0, aSN1OctetString);
    }

    public SignerIdentifier(ASN1Primitive aSN1Primitive) {
        this.id = aSN1Primitive;
    }

    public SignerIdentifier(IssuerAndSerialNumber issuerAndSerialNumber) {
        this.id = issuerAndSerialNumber;
    }

    public static SignerIdentifier getInstance(Object object) {
        if (object != null && !(object instanceof SignerIdentifier)) {
            if (object instanceof IssuerAndSerialNumber) {
                return new SignerIdentifier((IssuerAndSerialNumber)object);
            }
            if (object instanceof ASN1OctetString) {
                return new SignerIdentifier((ASN1OctetString)object);
            }
            if (object instanceof ASN1Primitive) {
                return new SignerIdentifier((ASN1Primitive)object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal object in SignerIdentifier: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (SignerIdentifier)object;
    }

    public ASN1Encodable getId() {
        ASN1Encodable aSN1Encodable = this.id;
        if (aSN1Encodable instanceof ASN1TaggedObject) {
            return ASN1OctetString.getInstance((ASN1TaggedObject)aSN1Encodable, false);
        }
        return aSN1Encodable;
    }

    public boolean isTagged() {
        return this.id instanceof ASN1TaggedObject;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.id.toASN1Primitive();
    }
}

