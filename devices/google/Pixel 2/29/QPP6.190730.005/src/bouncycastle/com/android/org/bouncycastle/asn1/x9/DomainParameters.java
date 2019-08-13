/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x9;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x9.ValidationParams;
import java.math.BigInteger;
import java.util.Enumeration;

public class DomainParameters
extends ASN1Object {
    private final ASN1Integer g;
    private final ASN1Integer j;
    private final ASN1Integer p;
    private final ASN1Integer q;
    private final ValidationParams validationParams;

    private DomainParameters(ASN1Sequence aSN1Encodable) {
        if (((ASN1Sequence)aSN1Encodable).size() >= 3 && ((ASN1Sequence)aSN1Encodable).size() <= 5) {
            Enumeration enumeration = ((ASN1Sequence)aSN1Encodable).getObjects();
            this.p = ASN1Integer.getInstance(enumeration.nextElement());
            this.g = ASN1Integer.getInstance(enumeration.nextElement());
            this.q = ASN1Integer.getInstance(enumeration.nextElement());
            aSN1Encodable = DomainParameters.getNext(enumeration);
            if (aSN1Encodable != null && aSN1Encodable instanceof ASN1Integer) {
                this.j = ASN1Integer.getInstance(aSN1Encodable);
                aSN1Encodable = DomainParameters.getNext(enumeration);
            } else {
                this.j = null;
            }
            this.validationParams = aSN1Encodable != null ? ValidationParams.getInstance(aSN1Encodable.toASN1Primitive()) : null;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(((ASN1Sequence)aSN1Encodable).size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public DomainParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, ValidationParams validationParams) {
        if (bigInteger != null) {
            if (bigInteger2 != null) {
                if (bigInteger3 != null) {
                    this.p = new ASN1Integer(bigInteger);
                    this.g = new ASN1Integer(bigInteger2);
                    this.q = new ASN1Integer(bigInteger3);
                    this.j = bigInteger4 != null ? new ASN1Integer(bigInteger4) : null;
                    this.validationParams = validationParams;
                    return;
                }
                throw new IllegalArgumentException("'q' cannot be null");
            }
            throw new IllegalArgumentException("'g' cannot be null");
        }
        throw new IllegalArgumentException("'p' cannot be null");
    }

    public static DomainParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return DomainParameters.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static DomainParameters getInstance(Object object) {
        if (object instanceof DomainParameters) {
            return (DomainParameters)object;
        }
        if (object != null) {
            return new DomainParameters(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    private static ASN1Encodable getNext(Enumeration object) {
        object = object.hasMoreElements() ? (ASN1Encodable)object.nextElement() : null;
        return object;
    }

    public BigInteger getG() {
        return this.g.getPositiveValue();
    }

    public BigInteger getJ() {
        ASN1Integer aSN1Integer = this.j;
        if (aSN1Integer == null) {
            return null;
        }
        return aSN1Integer.getPositiveValue();
    }

    public BigInteger getP() {
        return this.p.getPositiveValue();
    }

    public BigInteger getQ() {
        return this.q.getPositiveValue();
    }

    public ValidationParams getValidationParams() {
        return this.validationParams;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.p);
        aSN1EncodableVector.add(this.g);
        aSN1EncodableVector.add(this.q);
        ASN1Object aSN1Object = this.j;
        if (aSN1Object != null) {
            aSN1EncodableVector.add(aSN1Object);
        }
        if ((aSN1Object = this.validationParams) != null) {
            aSN1EncodableVector.add(aSN1Object);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

