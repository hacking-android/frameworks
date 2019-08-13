/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x9;

import com.android.org.bouncycastle.asn1.ASN1Choice;
import com.android.org.bouncycastle.asn1.ASN1Null;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;

public class X962Parameters
extends ASN1Object
implements ASN1Choice {
    private ASN1Primitive params = null;

    public X962Parameters(ASN1Null aSN1Null) {
        this.params = aSN1Null;
    }

    public X962Parameters(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.params = aSN1ObjectIdentifier;
    }

    public X962Parameters(ASN1Primitive aSN1Primitive) {
        this.params = aSN1Primitive;
    }

    public X962Parameters(X9ECParameters x9ECParameters) {
        this.params = x9ECParameters.toASN1Primitive();
    }

    public static X962Parameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return X962Parameters.getInstance(aSN1TaggedObject.getObject());
    }

    public static X962Parameters getInstance(Object object) {
        if (object != null && !(object instanceof X962Parameters)) {
            if (object instanceof ASN1Primitive) {
                return new X962Parameters((ASN1Primitive)object);
            }
            if (object instanceof byte[]) {
                try {
                    object = new X962Parameters(ASN1Primitive.fromByteArray((byte[])object));
                    return object;
                }
                catch (Exception exception) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("unable to parse encoded data: ");
                    ((StringBuilder)object).append(exception.getMessage());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            }
            throw new IllegalArgumentException("unknown object in getInstance()");
        }
        return (X962Parameters)object;
    }

    public ASN1Primitive getParameters() {
        return this.params;
    }

    public boolean isImplicitlyCA() {
        return this.params instanceof ASN1Null;
    }

    public boolean isNamedCurve() {
        return this.params instanceof ASN1ObjectIdentifier;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.params;
    }
}

