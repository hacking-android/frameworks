/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x9;

import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.x9.X9IntegerConverter;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import java.math.BigInteger;

public class X9FieldElement
extends ASN1Object {
    private static X9IntegerConverter converter = new X9IntegerConverter();
    protected ECFieldElement f;

    public X9FieldElement(int n, int n2, int n3, int n4, ASN1OctetString aSN1OctetString) {
        this(new ECFieldElement.F2m(n, n2, n3, n4, new BigInteger(1, aSN1OctetString.getOctets())));
    }

    public X9FieldElement(ECFieldElement eCFieldElement) {
        this.f = eCFieldElement;
    }

    public X9FieldElement(BigInteger bigInteger, ASN1OctetString aSN1OctetString) {
        this(new ECFieldElement.Fp(bigInteger, new BigInteger(1, aSN1OctetString.getOctets())));
    }

    public ECFieldElement getValue() {
        return this.f;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        int n = converter.getByteLength(this.f);
        return new DEROctetString(converter.integerToBytes(this.f.toBigInteger(), n));
    }
}

