/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERSequence;
import java.math.BigInteger;

public class PBEParameter
extends ASN1Object {
    ASN1Integer iterations;
    ASN1OctetString salt;

    private PBEParameter(ASN1Sequence aSN1Sequence) {
        this.salt = (ASN1OctetString)aSN1Sequence.getObjectAt(0);
        this.iterations = (ASN1Integer)aSN1Sequence.getObjectAt(1);
    }

    public PBEParameter(byte[] arrby, int n) {
        if (arrby.length == 8) {
            this.salt = new DEROctetString(arrby);
            this.iterations = new ASN1Integer(n);
            return;
        }
        throw new IllegalArgumentException("salt length must be 8");
    }

    public static PBEParameter getInstance(Object object) {
        if (object instanceof PBEParameter) {
            return (PBEParameter)object;
        }
        if (object != null) {
            return new PBEParameter(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public BigInteger getIterationCount() {
        return this.iterations.getValue();
    }

    public byte[] getSalt() {
        return this.salt.getOctets();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.salt);
        aSN1EncodableVector.add(this.iterations);
        return new DERSequence(aSN1EncodableVector);
    }
}

