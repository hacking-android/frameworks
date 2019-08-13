/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1BitString;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.x509.DistributionPointName;
import com.android.org.bouncycastle.asn1.x509.GeneralNames;
import com.android.org.bouncycastle.asn1.x509.ReasonFlags;
import com.android.org.bouncycastle.util.Strings;

public class DistributionPoint
extends ASN1Object {
    GeneralNames cRLIssuer;
    DistributionPointName distributionPoint;
    ReasonFlags reasons;

    public DistributionPoint(ASN1Sequence object) {
        for (int i = 0; i != ((ASN1Sequence)object).size(); ++i) {
            ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(((ASN1Sequence)object).getObjectAt(i));
            int n = aSN1TaggedObject.getTagNo();
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        this.cRLIssuer = GeneralNames.getInstance(aSN1TaggedObject, false);
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown tag encountered in structure: ");
                    ((StringBuilder)object).append(aSN1TaggedObject.getTagNo());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                this.reasons = new ReasonFlags(DERBitString.getInstance(aSN1TaggedObject, false));
                continue;
            }
            this.distributionPoint = DistributionPointName.getInstance(aSN1TaggedObject, true);
        }
    }

    public DistributionPoint(DistributionPointName distributionPointName, ReasonFlags reasonFlags, GeneralNames generalNames) {
        this.distributionPoint = distributionPointName;
        this.reasons = reasonFlags;
        this.cRLIssuer = generalNames;
    }

    private void appendObject(StringBuffer stringBuffer, String string, String string2, String string3) {
        stringBuffer.append("    ");
        stringBuffer.append(string2);
        stringBuffer.append(":");
        stringBuffer.append(string);
        stringBuffer.append("    ");
        stringBuffer.append("    ");
        stringBuffer.append(string3);
        stringBuffer.append(string);
    }

    public static DistributionPoint getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return DistributionPoint.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static DistributionPoint getInstance(Object object) {
        if (object != null && !(object instanceof DistributionPoint)) {
            if (object instanceof ASN1Sequence) {
                return new DistributionPoint((ASN1Sequence)object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid DistributionPoint: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (DistributionPoint)object;
    }

    public GeneralNames getCRLIssuer() {
        return this.cRLIssuer;
    }

    public DistributionPointName getDistributionPoint() {
        return this.distributionPoint;
    }

    public ReasonFlags getReasons() {
        return this.reasons;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1Object aSN1Object = this.distributionPoint;
        if (aSN1Object != null) {
            aSN1EncodableVector.add(new DERTaggedObject(0, aSN1Object));
        }
        if ((aSN1Object = this.reasons) != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, aSN1Object));
        }
        if ((aSN1Object = this.cRLIssuer) != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, aSN1Object));
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        String string = Strings.lineSeparator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("DistributionPoint: [");
        stringBuffer.append(string);
        ASN1Object aSN1Object = this.distributionPoint;
        if (aSN1Object != null) {
            this.appendObject(stringBuffer, string, "distributionPoint", ((DistributionPointName)aSN1Object).toString());
        }
        if ((aSN1Object = this.reasons) != null) {
            this.appendObject(stringBuffer, string, "reasons", ((ASN1BitString)aSN1Object).toString());
        }
        if ((aSN1Object = this.cRLIssuer) != null) {
            this.appendObject(stringBuffer, string, "cRLIssuer", ((GeneralNames)aSN1Object).toString());
        }
        stringBuffer.append("]");
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

