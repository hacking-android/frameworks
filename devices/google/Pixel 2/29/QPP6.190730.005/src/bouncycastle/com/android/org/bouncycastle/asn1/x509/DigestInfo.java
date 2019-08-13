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
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.util.Arrays;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.Enumeration;

public class DigestInfo
extends ASN1Object {
    private AlgorithmIdentifier algId;
    private byte[] digest;

    public DigestInfo(ASN1Sequence object) {
        object = ((ASN1Sequence)object).getObjects();
        this.algId = AlgorithmIdentifier.getInstance(object.nextElement());
        this.digest = ASN1OctetString.getInstance(object.nextElement()).getOctets();
    }

    @UnsupportedAppUsage
    public DigestInfo(AlgorithmIdentifier algorithmIdentifier, byte[] arrby) {
        this.digest = Arrays.clone(arrby);
        this.algId = algorithmIdentifier;
    }

    public static DigestInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return DigestInfo.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static DigestInfo getInstance(Object object) {
        if (object instanceof DigestInfo) {
            return (DigestInfo)object;
        }
        if (object != null) {
            return new DigestInfo(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public AlgorithmIdentifier getAlgorithmId() {
        return this.algId;
    }

    public byte[] getDigest() {
        return Arrays.clone(this.digest);
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.algId);
        aSN1EncodableVector.add(new DEROctetString(this.digest));
        return new DERSequence(aSN1EncodableVector);
    }
}

