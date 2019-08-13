/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Boolean;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.X509Extension;
import java.math.BigInteger;

public class BasicConstraints
extends ASN1Object {
    ASN1Boolean cA = ASN1Boolean.getInstance(false);
    ASN1Integer pathLenConstraint = null;

    public BasicConstraints(int n) {
        this.cA = ASN1Boolean.getInstance(true);
        this.pathLenConstraint = new ASN1Integer(n);
    }

    private BasicConstraints(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 0) {
            this.cA = null;
            this.pathLenConstraint = null;
        } else {
            if (aSN1Sequence.getObjectAt(0) instanceof ASN1Boolean) {
                this.cA = ASN1Boolean.getInstance(aSN1Sequence.getObjectAt(0));
            } else {
                this.cA = null;
                this.pathLenConstraint = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
            }
            if (aSN1Sequence.size() > 1) {
                if (this.cA != null) {
                    this.pathLenConstraint = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1));
                } else {
                    throw new IllegalArgumentException("wrong sequence in constructor");
                }
            }
        }
    }

    public BasicConstraints(boolean bl) {
        this.cA = bl ? ASN1Boolean.getInstance(true) : null;
        this.pathLenConstraint = null;
    }

    public static BasicConstraints fromExtensions(Extensions extensions) {
        return BasicConstraints.getInstance(extensions.getExtensionParsedValue(Extension.basicConstraints));
    }

    public static BasicConstraints getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return BasicConstraints.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static BasicConstraints getInstance(Object object) {
        if (object instanceof BasicConstraints) {
            return (BasicConstraints)object;
        }
        if (object instanceof X509Extension) {
            return BasicConstraints.getInstance(X509Extension.convertValueToObject((X509Extension)object));
        }
        if (object != null) {
            return new BasicConstraints(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public BigInteger getPathLenConstraint() {
        ASN1Integer aSN1Integer = this.pathLenConstraint;
        if (aSN1Integer != null) {
            return aSN1Integer.getValue();
        }
        return null;
    }

    public boolean isCA() {
        ASN1Boolean aSN1Boolean = this.cA;
        boolean bl = aSN1Boolean != null && aSN1Boolean.isTrue();
        return bl;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1Primitive aSN1Primitive = this.cA;
        if (aSN1Primitive != null) {
            aSN1EncodableVector.add(aSN1Primitive);
        }
        if ((aSN1Primitive = this.pathLenConstraint) != null) {
            aSN1EncodableVector.add(aSN1Primitive);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        if (this.pathLenConstraint == null) {
            if (this.cA == null) {
                return "BasicConstraints: isCa(false)";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("BasicConstraints: isCa(");
            stringBuilder.append(this.isCA());
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BasicConstraints: isCa(");
        stringBuilder.append(this.isCA());
        stringBuilder.append("), pathLenConstraint = ");
        stringBuilder.append(this.pathLenConstraint.getValue());
        return stringBuilder.toString();
    }
}

