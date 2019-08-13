/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import java.math.BigInteger;
import java.util.Enumeration;

public class RSAPublicKeyStructure
extends ASN1Object {
    private BigInteger modulus;
    private BigInteger publicExponent;

    public RSAPublicKeyStructure(ASN1Sequence object) {
        if (((ASN1Sequence)object).size() == 2) {
            object = ((ASN1Sequence)object).getObjects();
            this.modulus = ASN1Integer.getInstance(object.nextElement()).getPositiveValue();
            this.publicExponent = ASN1Integer.getInstance(object.nextElement()).getPositiveValue();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(((ASN1Sequence)object).size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public RSAPublicKeyStructure(BigInteger bigInteger, BigInteger bigInteger2) {
        this.modulus = bigInteger;
        this.publicExponent = bigInteger2;
    }

    public static RSAPublicKeyStructure getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return RSAPublicKeyStructure.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static RSAPublicKeyStructure getInstance(Object object) {
        if (object != null && !(object instanceof RSAPublicKeyStructure)) {
            if (object instanceof ASN1Sequence) {
                return new RSAPublicKeyStructure((ASN1Sequence)object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid RSAPublicKeyStructure: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (RSAPublicKeyStructure)object;
    }

    public BigInteger getModulus() {
        return this.modulus;
    }

    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(this.getModulus()));
        aSN1EncodableVector.add(new ASN1Integer(this.getPublicExponent()));
        return new DERSequence(aSN1EncodableVector);
    }
}

