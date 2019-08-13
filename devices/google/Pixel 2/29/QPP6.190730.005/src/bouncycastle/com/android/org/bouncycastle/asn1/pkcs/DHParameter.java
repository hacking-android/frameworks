/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERSequence;
import java.math.BigInteger;
import java.util.Enumeration;

public class DHParameter
extends ASN1Object {
    ASN1Integer g;
    ASN1Integer l;
    ASN1Integer p;

    private DHParameter(ASN1Sequence object) {
        object = ((ASN1Sequence)object).getObjects();
        this.p = ASN1Integer.getInstance(object.nextElement());
        this.g = ASN1Integer.getInstance(object.nextElement());
        this.l = object.hasMoreElements() ? (ASN1Integer)object.nextElement() : null;
    }

    public DHParameter(BigInteger bigInteger, BigInteger bigInteger2, int n) {
        this.p = new ASN1Integer(bigInteger);
        this.g = new ASN1Integer(bigInteger2);
        this.l = n != 0 ? new ASN1Integer(n) : null;
    }

    public static DHParameter getInstance(Object object) {
        if (object instanceof DHParameter) {
            return (DHParameter)object;
        }
        if (object != null) {
            return new DHParameter(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public BigInteger getG() {
        return this.g.getPositiveValue();
    }

    public BigInteger getL() {
        ASN1Integer aSN1Integer = this.l;
        if (aSN1Integer == null) {
            return null;
        }
        return aSN1Integer.getPositiveValue();
    }

    public BigInteger getP() {
        return this.p.getPositiveValue();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.p);
        aSN1EncodableVector.add(this.g);
        if (this.getL() != null) {
            aSN1EncodableVector.add(this.l);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

