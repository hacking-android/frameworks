/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import dalvik.annotation.compat.UnsupportedAppUsage;

public class AlgorithmIdentifier
extends ASN1Object {
    private ASN1ObjectIdentifier algorithm;
    private ASN1Encodable parameters;

    @UnsupportedAppUsage
    public AlgorithmIdentifier(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.algorithm = aSN1ObjectIdentifier;
    }

    @UnsupportedAppUsage
    public AlgorithmIdentifier(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.algorithm = aSN1ObjectIdentifier;
        this.parameters = aSN1Encodable;
    }

    private AlgorithmIdentifier(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() >= 1 && aSN1Sequence.size() <= 2) {
            this.algorithm = ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
            this.parameters = aSN1Sequence.size() == 2 ? aSN1Sequence.getObjectAt(1) : null;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(aSN1Sequence.size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static AlgorithmIdentifier getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return AlgorithmIdentifier.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static AlgorithmIdentifier getInstance(Object object) {
        if (object instanceof AlgorithmIdentifier) {
            return (AlgorithmIdentifier)object;
        }
        if (object != null) {
            return new AlgorithmIdentifier(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public ASN1ObjectIdentifier getAlgorithm() {
        return this.algorithm;
    }

    public ASN1Encodable getParameters() {
        return this.parameters;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.algorithm);
        ASN1Encodable aSN1Encodable = this.parameters;
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(aSN1Encodable);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

