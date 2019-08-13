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

public class RSAPrivateKey
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
    private BigInteger version;

    private RSAPrivateKey(ASN1Sequence object) {
        Enumeration enumeration = ((ASN1Sequence)object).getObjects();
        object = ((ASN1Integer)enumeration.nextElement()).getValue();
        if (((BigInteger)object).intValue() != 0 && ((BigInteger)object).intValue() != 1) {
            throw new IllegalArgumentException("wrong version for RSA private key");
        }
        this.version = object;
        this.modulus = ((ASN1Integer)enumeration.nextElement()).getValue();
        this.publicExponent = ((ASN1Integer)enumeration.nextElement()).getValue();
        this.privateExponent = ((ASN1Integer)enumeration.nextElement()).getValue();
        this.prime1 = ((ASN1Integer)enumeration.nextElement()).getValue();
        this.prime2 = ((ASN1Integer)enumeration.nextElement()).getValue();
        this.exponent1 = ((ASN1Integer)enumeration.nextElement()).getValue();
        this.exponent2 = ((ASN1Integer)enumeration.nextElement()).getValue();
        this.coefficient = ((ASN1Integer)enumeration.nextElement()).getValue();
        if (enumeration.hasMoreElements()) {
            this.otherPrimeInfos = (ASN1Sequence)enumeration.nextElement();
        }
    }

    public RSAPrivateKey(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8) {
        this.version = BigInteger.valueOf(0L);
        this.modulus = bigInteger;
        this.publicExponent = bigInteger2;
        this.privateExponent = bigInteger3;
        this.prime1 = bigInteger4;
        this.prime2 = bigInteger5;
        this.exponent1 = bigInteger6;
        this.exponent2 = bigInteger7;
        this.coefficient = bigInteger8;
    }

    public static RSAPrivateKey getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return RSAPrivateKey.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static RSAPrivateKey getInstance(Object object) {
        if (object instanceof RSAPrivateKey) {
            return (RSAPrivateKey)object;
        }
        if (object != null) {
            return new RSAPrivateKey(ASN1Sequence.getInstance(object));
        }
        return null;
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

    public BigInteger getVersion() {
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

