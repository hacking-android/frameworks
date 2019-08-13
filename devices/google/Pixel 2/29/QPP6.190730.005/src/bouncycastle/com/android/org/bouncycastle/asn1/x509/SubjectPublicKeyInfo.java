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
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.util.Enumeration;

public class SubjectPublicKeyInfo
extends ASN1Object {
    private AlgorithmIdentifier algId;
    private DERBitString keyData;

    public SubjectPublicKeyInfo(ASN1Sequence object) {
        if (((ASN1Sequence)object).size() == 2) {
            object = ((ASN1Sequence)object).getObjects();
            this.algId = AlgorithmIdentifier.getInstance(object.nextElement());
            this.keyData = DERBitString.getInstance(object.nextElement());
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(((ASN1Sequence)object).size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public SubjectPublicKeyInfo(AlgorithmIdentifier algorithmIdentifier, ASN1Encodable aSN1Encodable) throws IOException {
        this.keyData = new DERBitString(aSN1Encodable);
        this.algId = algorithmIdentifier;
    }

    public SubjectPublicKeyInfo(AlgorithmIdentifier algorithmIdentifier, byte[] arrby) {
        this.keyData = new DERBitString(arrby);
        this.algId = algorithmIdentifier;
    }

    public static SubjectPublicKeyInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return SubjectPublicKeyInfo.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    @UnsupportedAppUsage
    public static SubjectPublicKeyInfo getInstance(Object object) {
        if (object instanceof SubjectPublicKeyInfo) {
            return (SubjectPublicKeyInfo)object;
        }
        if (object != null) {
            return new SubjectPublicKeyInfo(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public AlgorithmIdentifier getAlgorithm() {
        return this.algId;
    }

    public AlgorithmIdentifier getAlgorithmId() {
        return this.algId;
    }

    public ASN1Primitive getPublicKey() throws IOException {
        return ASN1Primitive.fromByteArray(this.keyData.getOctets());
    }

    public DERBitString getPublicKeyData() {
        return this.keyData;
    }

    public ASN1Primitive parsePublicKey() throws IOException {
        return ASN1Primitive.fromByteArray(this.keyData.getOctets());
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.algId);
        aSN1EncodableVector.add(this.keyData);
        return new DERSequence(aSN1EncodableVector);
    }
}

