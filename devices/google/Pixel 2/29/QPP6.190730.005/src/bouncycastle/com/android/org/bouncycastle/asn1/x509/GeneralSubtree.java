/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.x509.GeneralName;
import java.math.BigInteger;

public class GeneralSubtree
extends ASN1Object {
    private static final BigInteger ZERO = BigInteger.valueOf(0L);
    private GeneralName base;
    private ASN1Integer maximum;
    private ASN1Integer minimum;

    /*
     * Enabled aggressive block sorting
     */
    private GeneralSubtree(ASN1Sequence object) {
        this.base = GeneralName.getInstance(((ASN1Sequence)object).getObjectAt(0));
        int n = ((ASN1Sequence)object).size();
        if (n == 1) return;
        if (n != 2) {
            if (n != 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Bad sequence size: ");
                stringBuilder.append(((ASN1Sequence)object).size());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(((ASN1Sequence)object).getObjectAt(1));
            if (aSN1TaggedObject.getTagNo() != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Bad tag number for 'minimum': ");
                ((StringBuilder)object).append(aSN1TaggedObject.getTagNo());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.minimum = ASN1Integer.getInstance(aSN1TaggedObject, false);
            aSN1TaggedObject = ASN1TaggedObject.getInstance(((ASN1Sequence)object).getObjectAt(2));
            if (aSN1TaggedObject.getTagNo() == 1) {
                this.maximum = ASN1Integer.getInstance(aSN1TaggedObject, false);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Bad tag number for 'maximum': ");
            ((StringBuilder)object).append(aSN1TaggedObject.getTagNo());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        n = ((ASN1TaggedObject)(object = ASN1TaggedObject.getInstance(((ASN1Sequence)object).getObjectAt(1)))).getTagNo();
        if (n == 0) {
            this.minimum = ASN1Integer.getInstance((ASN1TaggedObject)object, false);
            return;
        }
        if (n == 1) {
            this.maximum = ASN1Integer.getInstance((ASN1TaggedObject)object, false);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad tag number: ");
        stringBuilder.append(((ASN1TaggedObject)object).getTagNo());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public GeneralSubtree(GeneralName generalName) {
        this(generalName, null, null);
    }

    public GeneralSubtree(GeneralName generalName, BigInteger bigInteger, BigInteger bigInteger2) {
        this.base = generalName;
        if (bigInteger2 != null) {
            this.maximum = new ASN1Integer(bigInteger2);
        }
        this.minimum = bigInteger == null ? null : new ASN1Integer(bigInteger);
    }

    public static GeneralSubtree getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return new GeneralSubtree(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static GeneralSubtree getInstance(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof GeneralSubtree) {
            return (GeneralSubtree)object;
        }
        return new GeneralSubtree(ASN1Sequence.getInstance(object));
    }

    public GeneralName getBase() {
        return this.base;
    }

    public BigInteger getMaximum() {
        ASN1Integer aSN1Integer = this.maximum;
        if (aSN1Integer == null) {
            return null;
        }
        return aSN1Integer.getValue();
    }

    public BigInteger getMinimum() {
        ASN1Integer aSN1Integer = this.minimum;
        if (aSN1Integer == null) {
            return ZERO;
        }
        return aSN1Integer.getValue();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.base);
        ASN1Integer aSN1Integer = this.minimum;
        if (aSN1Integer != null && !aSN1Integer.getValue().equals(ZERO)) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, this.minimum));
        }
        if ((aSN1Integer = this.maximum) != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, aSN1Integer));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

