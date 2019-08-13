/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import java.math.BigInteger;

public class CRLNumber
extends ASN1Object {
    private BigInteger number;

    public CRLNumber(BigInteger bigInteger) {
        this.number = bigInteger;
    }

    public static CRLNumber getInstance(Object object) {
        if (object instanceof CRLNumber) {
            return (CRLNumber)object;
        }
        if (object != null) {
            return new CRLNumber(ASN1Integer.getInstance(object).getValue());
        }
        return null;
    }

    public BigInteger getCRLNumber() {
        return this.number;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return new ASN1Integer(this.number);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CRLNumber: ");
        stringBuilder.append(this.getCRLNumber());
        return stringBuilder.toString();
    }
}

