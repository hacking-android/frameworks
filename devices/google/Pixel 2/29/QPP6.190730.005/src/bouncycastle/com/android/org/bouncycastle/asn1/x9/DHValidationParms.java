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

public class DHValidationParms
extends ASN1Object {
    private ASN1Integer pgenCounter;
    private DERBitString seed;

    private DHValidationParms(ASN1Sequence aSN1Sequence) {
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

    public DHValidationParms(DERBitString dERBitString, ASN1Integer aSN1Integer) {
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

    public static DHValidationParms getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return DHValidationParms.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static DHValidationParms getInstance(Object object) {
        if (object instanceof DHValidationParms) {
            return (DHValidationParms)object;
        }
        if (object != null) {
            return new DHValidationParms(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public ASN1Integer getPgenCounter() {
        return this.pgenCounter;
    }

    public DERBitString getSeed() {
        return this.seed;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.seed);
        aSN1EncodableVector.add(this.pgenCounter);
        return new DERSequence(aSN1EncodableVector);
    }
}

