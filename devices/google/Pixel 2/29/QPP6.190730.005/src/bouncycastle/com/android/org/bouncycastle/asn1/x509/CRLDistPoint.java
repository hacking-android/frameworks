/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.DistributionPoint;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.util.Strings;

public class CRLDistPoint
extends ASN1Object {
    ASN1Sequence seq = null;

    private CRLDistPoint(ASN1Sequence aSN1Sequence) {
        this.seq = aSN1Sequence;
    }

    public CRLDistPoint(DistributionPoint[] arrdistributionPoint) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != arrdistributionPoint.length; ++i) {
            aSN1EncodableVector.add(arrdistributionPoint[i]);
        }
        this.seq = new DERSequence(aSN1EncodableVector);
    }

    public static CRLDistPoint fromExtensions(Extensions extensions) {
        return CRLDistPoint.getInstance(extensions.getExtensionParsedValue(Extension.cRLDistributionPoints));
    }

    public static CRLDistPoint getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return CRLDistPoint.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static CRLDistPoint getInstance(Object object) {
        if (object instanceof CRLDistPoint) {
            return (CRLDistPoint)object;
        }
        if (object != null) {
            return new CRLDistPoint(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public DistributionPoint[] getDistributionPoints() {
        DistributionPoint[] arrdistributionPoint = new DistributionPoint[this.seq.size()];
        for (int i = 0; i != this.seq.size(); ++i) {
            arrdistributionPoint[i] = DistributionPoint.getInstance(this.seq.getObjectAt(i));
        }
        return arrdistributionPoint;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.seq;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = Strings.lineSeparator();
        stringBuffer.append("CRLDistPoint:");
        stringBuffer.append(string);
        DistributionPoint[] arrdistributionPoint = this.getDistributionPoints();
        for (int i = 0; i != arrdistributionPoint.length; ++i) {
            stringBuffer.append("    ");
            stringBuffer.append(arrdistributionPoint[i]);
            stringBuffer.append(string);
        }
        return stringBuffer.toString();
    }
}

