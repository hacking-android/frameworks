/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.AttributeCertificateInfo;

public class AttributeCertificate
extends ASN1Object {
    AttributeCertificateInfo acinfo;
    AlgorithmIdentifier signatureAlgorithm;
    DERBitString signatureValue;

    public AttributeCertificate(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 3) {
            this.acinfo = AttributeCertificateInfo.getInstance(aSN1Sequence.getObjectAt(0));
            this.signatureAlgorithm = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
            this.signatureValue = DERBitString.getInstance(aSN1Sequence.getObjectAt(2));
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(aSN1Sequence.size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public AttributeCertificate(AttributeCertificateInfo attributeCertificateInfo, AlgorithmIdentifier algorithmIdentifier, DERBitString dERBitString) {
        this.acinfo = attributeCertificateInfo;
        this.signatureAlgorithm = algorithmIdentifier;
        this.signatureValue = dERBitString;
    }

    public static AttributeCertificate getInstance(Object object) {
        if (object instanceof AttributeCertificate) {
            return (AttributeCertificate)object;
        }
        if (object != null) {
            return new AttributeCertificate(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public AttributeCertificateInfo getAcinfo() {
        return this.acinfo;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public DERBitString getSignatureValue() {
        return this.signatureValue;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.acinfo);
        aSN1EncodableVector.add(this.signatureAlgorithm);
        aSN1EncodableVector.add(this.signatureValue);
        return new DERSequence(aSN1EncodableVector);
    }
}

