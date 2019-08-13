/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x9;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERSequence;
import java.math.BigInteger;

public class ValidationParams
extends ASN1Object {
    private ASN1Integer pgenCounter;
    private DERBitString seed;

    private ValidationParams(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 2) {
            this.seed = DERBitString.getInstance(aSN1Sequence.getObjectAt(0));
            this.pgenCounter = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(aSN1Sequence.size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public ValidationParams(DERBitString dERBitString, ASN1Integer aSN1Integer) {
        if (dERBitString != null) {
            if (aSN1Integer != null) {
                this.seed = dERBitString;
                this.pgenCounter = aSN1Integer;
                return;
            }
            throw new IllegalArgumentException("'pgenCounter' cannot be null");
        }
        throw new IllegalArgumentException("'seed' cannot be null");
    }

    public ValidationParams(byte[] arrby, int n) {
        if (arrby != null) {
            this.seed = new DERBitString(arrby);
            this.pgenCounter = new ASN1Integer(n);
            return;
        }
        throw new IllegalArgumentException("'seed' cannot be null");
    }

    public static ValidationParams getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return ValidationParams.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static ValidationParams getInstance(Object object) {
        if (object instanceof ValidationParams) {
            return (ValidationParams)object;
        }
        if (object != null) {
            return new ValidationParams(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public BigInteger getPgenCounter() {
        return this.pgenCounter.getPositiveValue();
    }

    public byte[] getSeed() {
        return this.seed.getBytes();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.seed);
        aSN1EncodableVector.add(this.pgenCounter);
        return new DERSequence(aSN1EncodableVector);
    }
}

