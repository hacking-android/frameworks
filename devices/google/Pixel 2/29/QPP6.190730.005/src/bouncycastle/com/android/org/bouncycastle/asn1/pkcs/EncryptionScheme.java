/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptionScheme
extends ASN1Object {
    private AlgorithmIdentifier algId;

    public EncryptionScheme(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.algId = new AlgorithmIdentifier(aSN1ObjectIdentifier);
    }

    public EncryptionScheme(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.algId = new AlgorithmIdentifier(aSN1ObjectIdentifier, aSN1Encodable);
    }

    private EncryptionScheme(ASN1Sequence aSN1Sequence) {
        this.algId = AlgorithmIdentifier.getInstance(aSN1Sequence);
    }

    public static EncryptionScheme getInstance(Object object) {
        if (object instanceof EncryptionScheme) {
            return (EncryptionScheme)object;
        }
        if (object != null) {
            return new EncryptionScheme(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public ASN1ObjectIdentifier getAlgorithm() {
        return this.algId.getAlgorithm();
    }

    public ASN1Encodable getParameters() {
        return this.algId.getParameters();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.algId.toASN1Primitive();
    }
}

