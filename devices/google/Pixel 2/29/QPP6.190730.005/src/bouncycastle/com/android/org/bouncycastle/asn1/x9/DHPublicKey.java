/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x9;

import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import java.math.BigInteger;

public class DHPublicKey
extends ASN1Object {
    private ASN1Integer y;

    private DHPublicKey(ASN1Integer aSN1Integer) {
        if (aSN1Integer != null) {
            this.y = aSN1Integer;
            return;
        }
        throw new IllegalArgumentException("'y' cannot be null");
    }

    public DHPublicKey(BigInteger bigInteger) {
        if (bigInteger != null) {
            this.y = new ASN1Integer(bigInteger);
            return;
        }
        throw new IllegalArgumentException("'y' cannot be null");
    }

    public static DHPublicKey getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return DHPublicKey.getInstance(ASN1Integer.getInstance(aSN1TaggedObject, bl));
    }

    public static DHPublicKey getInstance(Object object) {
        if (object != null && !(object instanceof DHPublicKey)) {
            if (object instanceof ASN1Integer) {
                return new DHPublicKey((ASN1Integer)object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid DHPublicKey: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (DHPublicKey)object;
    }

    public BigInteger getY() {
        return this.y.getPositiveValue();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.y;
    }
}

