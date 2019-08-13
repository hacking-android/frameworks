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
import com.android.org.bouncycastle.asn1.x9.DHValidationParms;
import java.math.BigInteger;
import java.util.Enumeration;

public class DHDomainParameters
extends ASN1Object {
    private ASN1Integer g;
    private ASN1Integer j;
    private ASN1Integer p;
    private ASN1Integer q;
    private DHValidationParms validationParms;

    public DHDomainParameters(ASN1Integer aSN1Integer, ASN1Integer aSN1Integer2, ASN1Integer aSN1Integer3, ASN1Integer aSN1Integer4, DHValidationParms dHValidationParms) {
        if (aSN1Integer != null) {
            if (aSN1Integer2 != null) {
                if (aSN1Integer3 != null) {
                    this.p = aSN1Integer;
                    this.g = aSN1Integer2;
                    this.q = aSN1Integer3;
                    this.j = aSN1Integer4;
                    this.validationParms = dHValidationParms;
                    return;
                }
                throw new IllegalArgumentException("'q' cannot be null");
            }
            throw new IllegalArgumentException("'g' cannot be null");
        }
        throw new IllegalArgumentException("'p' cannot be null");
    }

    private DHDomainParameters(ASN1Sequence aSN1Encodable) {
        if (((ASN1Sequence)aSN1Encodable).size() >= 3 && ((ASN1Sequence)aSN1Encodable).size() <= 5) {
            ASN1Encodable aSN1Encodable2;
            Enumeration enumeration = ((ASN1Sequence)aSN1Encodable).getObjects();
            this.p = ASN1Integer.getInstance(enumeration.nextElement());
            this.g = ASN1Integer.getInstance(enumeration.nextElement());
            this.q = ASN1Integer.getInstance(enumeration.nextElement());
            aSN1Encodable = aSN1Encodable2 = DHDomainParameters.getNext(enumeration);
            if (aSN1Encodable2 != null) {
                aSN1Encodable = aSN1Encodable2;
                if (aSN1Encodable2 instanceof ASN1Integer) {
                    this.j = ASN1Integer.getInstance(aSN1Encodable2);
                    aSN1Encodable = DHDomainParameters.getNext(enumeration);
                }
            }
            if (aSN1Encodable != null) {
                this.validationParms = DHValidationParms.getInstance(aSN1Encodable.toASN1Primitive());
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(((ASN1Sequence)aSN1Encodable).size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public DHDomainParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, DHValidationParms dHValidationParms) {
        if (bigInteger != null) {
            if (bigInteger2 != null) {
                if (bigInteger3 != null) {
                    this.p = new ASN1Integer(bigInteger);
                    this.g = new ASN1Integer(bigInteger2);
                    this.q = new ASN1Integer(bigInteger3);
                    this.j = new ASN1Integer(bigInteger4);
                    this.validationParms = dHValidationParms;
                    return;
                }
                throw new IllegalArgumentException("'q' cannot be null");
            }
            throw new IllegalArgumentException("'g' cannot be null");
        }
        throw new IllegalArgumentException("'p' cannot be null");
    }

    public static DHDomainParameters getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return DHDomainParameters.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static DHDomainParameters getInstance(Object object) {
        if (object != null && !(object instanceof DHDomainParameters)) {
            if (object instanceof ASN1Sequence) {
                return new DHDomainParameters((ASN1Sequence)object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid DHDomainParameters: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (DHDomainParameters)object;
    }

    private static ASN1Encodable getNext(Enumeration object) {
        object = object.hasMoreElements() ? (ASN1Encodable)object.nextElement() : null;
        return object;
    }

    public ASN1Integer getG() {
        return this.g;
    }

    public ASN1Integer getJ() {
        return this.j;
    }

    public ASN1Integer getP() {
        return this.p;
    }

    public ASN1Integer getQ() {
        return this.q;
    }

    public DHValidationParms getValidationParms() {
        return this.validationParms;
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
        if ((aSN1Object = this.validationParms) != null) {
            aSN1EncodableVector.add(aSN1Object);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

