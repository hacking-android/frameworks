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

public class DSAParameter
extends ASN1Object {
    ASN1Integer g;
    ASN1Integer p;
    ASN1Integer q;

    private DSAParameter(ASN1Sequence object) {
        if (((ASN1Sequence)object).size() == 3) {
            object = ((ASN1Sequence)object).getObjects();
            this.p = ASN1Integer.getInstance(object.nextElement());
            this.q = ASN1Integer.getInstance(object.nextElement());
            this.g = ASN1Integer.getInstance(object.nextElement());
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(((ASN1Sequence)object).size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public DSAParameter(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.p = new ASN1Integer(bigInteger);
        this.q = new ASN1Integer(bigInteger2);
        this.g = new ASN1Integer(bigInteger3);
    }

    public static DSAParameter getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return DSAParameter.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static DSAParameter getInstance(Object object) {
        if (object instanceof DSAParameter) {
            return (DSAParameter)object;
        }
        if (object != null) {
            return new DSAParameter(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public BigInteger getG() {
        return this.g.getPositiveValue();
    }

    public BigInteger getP() {
        return this.p.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.q.getPositiveValue();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.p);
        aSN1EncodableVector.add(this.q);
        aSN1EncodableVector.add(this.g);
        return new DERSequence(aSN1EncodableVector);
    }
}

