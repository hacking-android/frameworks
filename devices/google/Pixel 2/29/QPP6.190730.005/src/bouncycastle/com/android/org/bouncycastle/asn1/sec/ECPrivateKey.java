/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.sec;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.util.Enumeration;

public class ECPrivateKey
extends ASN1Object {
    private ASN1Sequence seq;

    public ECPrivateKey(int n, BigInteger object) {
        byte[] arrby = BigIntegers.asUnsignedByteArray((n + 7) / 8, (BigInteger)object);
        object = new ASN1EncodableVector();
        ((ASN1EncodableVector)object).add(new ASN1Integer(1L));
        ((ASN1EncodableVector)object).add(new DEROctetString(arrby));
        this.seq = new DERSequence((ASN1EncodableVector)object);
    }

    public ECPrivateKey(int n, BigInteger bigInteger, ASN1Encodable aSN1Encodable) {
        this(n, bigInteger, null, aSN1Encodable);
    }

    public ECPrivateKey(int n, BigInteger arrby, DERBitString dERBitString, ASN1Encodable aSN1Encodable) {
        arrby = BigIntegers.asUnsignedByteArray((n + 7) / 8, (BigInteger)arrby);
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(1L));
        aSN1EncodableVector.add(new DEROctetString(arrby));
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, aSN1Encodable));
        }
        if (dERBitString != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 1, dERBitString));
        }
        this.seq = new DERSequence(aSN1EncodableVector);
    }

    private ECPrivateKey(ASN1Sequence aSN1Sequence) {
        this.seq = aSN1Sequence;
    }

    public ECPrivateKey(BigInteger bigInteger) {
        this(bigInteger.bitLength(), bigInteger);
    }

    public ECPrivateKey(BigInteger bigInteger, ASN1Encodable aSN1Encodable) {
        this(bigInteger, null, aSN1Encodable);
    }

    public ECPrivateKey(BigInteger bigInteger, DERBitString dERBitString, ASN1Encodable aSN1Encodable) {
        this(bigInteger.bitLength(), bigInteger, dERBitString, aSN1Encodable);
    }

    public static ECPrivateKey getInstance(Object object) {
        if (object instanceof ECPrivateKey) {
            return (ECPrivateKey)object;
        }
        if (object != null) {
            return new ECPrivateKey(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    private ASN1Primitive getObjectInTag(int n) {
        Enumeration enumeration = this.seq.getObjects();
        while (enumeration.hasMoreElements()) {
            ASN1Encodable aSN1Encodable = (ASN1Encodable)enumeration.nextElement();
            if (!(aSN1Encodable instanceof ASN1TaggedObject) || ((ASN1TaggedObject)(aSN1Encodable = (ASN1TaggedObject)aSN1Encodable)).getTagNo() != n) continue;
            return ((ASN1TaggedObject)aSN1Encodable).getObject().toASN1Primitive();
        }
        return null;
    }

    public BigInteger getKey() {
        return new BigInteger(1, ((ASN1OctetString)this.seq.getObjectAt(1)).getOctets());
    }

    public ASN1Primitive getParameters() {
        return this.getObjectInTag(0);
    }

    public DERBitString getPublicKey() {
        return (DERBitString)this.getObjectInTag(1);
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.seq;
    }
}

