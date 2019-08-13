/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import java.util.Enumeration;

public class EncryptedPrivateKeyInfo
extends ASN1Object {
    private AlgorithmIdentifier algId;
    private ASN1OctetString data;

    private EncryptedPrivateKeyInfo(ASN1Sequence object) {
        object = ((ASN1Sequence)object).getObjects();
        this.algId = AlgorithmIdentifier.getInstance(object.nextElement());
        this.data = ASN1OctetString.getInstance(object.nextElement());
    }

    public EncryptedPrivateKeyInfo(AlgorithmIdentifier algorithmIdentifier, byte[] arrby) {
        this.algId = algorithmIdentifier;
        this.data = new DEROctetString(arrby);
    }

    public static EncryptedPrivateKeyInfo getInstance(Object object) {
        if (object instanceof EncryptedPrivateKeyInfo) {
            return (EncryptedPrivateKeyInfo)object;
        }
        if (object != null) {
            return new EncryptedPrivateKeyInfo(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public byte[] getEncryptedData() {
        return this.data.getOctets();
    }

    public AlgorithmIdentifier getEncryptionAlgorithm() {
        return this.algId;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.algId);
        aSN1EncodableVector.add(this.data);
        return new DERSequence(aSN1EncodableVector);
    }
}

