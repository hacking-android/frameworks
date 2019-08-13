/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import java.math.BigInteger;
import java.util.Enumeration;

public class RSAPrivateKeyStructure
extends ASN1Object {
    private BigInteger coefficient;
    private BigInteger exponent1;
    private BigInteger exponent2;
    private BigInteger modulus;
    private ASN1Sequence otherPrimeInfos = null;
    private BigInteger prime1;
    private BigInteger prime2;
    private BigInteger privateExponent;
    private BigInteger publicExponent;
    private int version;

    public RSAPrivateKeyStructure(ASN1Sequence object) {
        object = ((ASN1Sequence)object).getObjects();
        BigInteger bigInteger = ((ASN1Integer)object.nextElement()).getValue();
        if (bigInteger.intValue() != 0 && bigInteger.intValue() != 1) {
            throw new IllegalArgumentException("wrong version for RSA private key");
        }
        this.version = bigInteger.intValue();
        this.modulus = ((ASN1Integer)object.nextElement()).getValue();
        this.publicExponent = ((ASN1Integer)object.nextElement()).getValue();
        this.privateExponent = ((ASN1Integer)object.nextElement()).getValue();
        this.prime1 = ((ASN1Integer)object.nextElement()).getValue();
        this.prime2 = ((ASN1Integer)object.nextElement()).getValue();
        this.exponent1 = ((ASN1Integer)object.nextElement()).getValue();
        this.exponent2 = ((ASN1Integer)object.nextElement()).getValue();
        this.coefficient = ((ASN1Integer)object.nextElement()).getValue();
        if (object.hasMoreElements()) {
            this.otherPrimeInfos = (ASN1Sequence)object.nextElement();
        }
    }

    public RSAPrivateKeyStructure(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8) {
        this.version = 0;
        this.modulus = bigInteger;
        this.publicExponent = bigInteger2;
        this.privateExponent = bigInteger3;
        this.prime1 = bigInteger4;
        this.prime2 = bigInteger5;
        this.exponent1 = bigInteger6;
        this.exponent2 = bigInteger7;
        this.coefficient = bigInteger8;
    }

    public static RSAPrivateKeyStructure getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return RSAPrivateKeyStructure.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static RSAPrivateKeyStructure getInstance(Object object) {
        if (object instanceof RSAPrivateKeyStructure) {
            return (RSAPrivateKeyStructure)object;
        }
        if (object instanceof ASN1Sequence) {
            return new RSAPrivateKeyStructure((ASN1Sequence)object);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unknown object in factory: ");
        stringBuilder.append(object.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public BigInteger getCoefficient() {
        return this.coefficient;
    }

    public BigInteger getExponent1() {
        return this.exponent1;
    }

    public BigInteger getExponent2() {
        return this.exponent2;
    }

    public BigInteger getModulus() {
        return this.modulus;
    }

    public BigInteger getPrime1() {
        return this.prime1;
    }

    public BigInteger getPrime2() {
        return this.prime2;
    }

    public BigInteger getPrivateExponent() {
        return this.privateExponent;
    }

    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }

    public int getVersion() {
        return this.version;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(this.version));
        aSN1EncodableVector.add(new ASN1Integer(this.getModulus()));
        aSN1EncodableVector.add(new ASN1Integer(this.getPublicExponent()));
        aSN1EncodableVector.add(new ASN1Integer(this.getPrivateExponent()));
        aSN1EncodableVector.add(new ASN1Integer(this.getPrime1()));
        aSN1EncodableVector.add(new ASN1Integer(this.getPrime2()));
        aSN1EncodableVector.add(new ASN1Integer(this.getExponent1()));
        aSN1EncodableVector.add(new ASN1Integer(this.getExponent2()));
        aSN1EncodableVector.add(new ASN1Integer(this.getCoefficient()));
        ASN1Sequence aSN1Sequence = this.otherPrimeInfos;
        if (aSN1Sequence != null) {
            aSN1EncodableVector.add(aSN1Sequence);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

