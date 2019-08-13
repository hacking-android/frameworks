/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1BitString;
import com.android.org.bouncycastle.asn1.ASN1Boolean;
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
import com.android.org.bouncycastle.asn1.x509.ReasonFlags;
import com.android.org.bouncycastle.util.Strings;

public class IssuingDistributionPoint
extends ASN1Object {
    private DistributionPointName distributionPoint;
    private boolean indirectCRL;
    private boolean onlyContainsAttributeCerts;
    private boolean onlyContainsCACerts;
    private boolean onlyContainsUserCerts;
    private ReasonFlags onlySomeReasons;
    private ASN1Sequence seq;

    private IssuingDistributionPoint(ASN1Sequence aSN1Sequence) {
        this.seq = aSN1Sequence;
        for (int i = 0; i != aSN1Sequence.size(); ++i) {
            ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
            int n = aSN1TaggedObject.getTagNo();
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) {
                                if (n == 5) {
                                    this.onlyContainsAttributeCerts = ASN1Boolean.getInstance(aSN1TaggedObject, false).isTrue();
                                    continue;
                                }
                                throw new IllegalArgumentException("unknown tag in IssuingDistributionPoint");
                            }
                            this.indirectCRL = ASN1Boolean.getInstance(aSN1TaggedObject, false).isTrue();
                            continue;
                        }
                        this.onlySomeReasons = new ReasonFlags(ReasonFlags.getInstance(aSN1TaggedObject, false));
                        continue;
                    }
                    this.onlyContainsCACerts = ASN1Boolean.getInstance(aSN1TaggedObject, false).isTrue();
                    continue;
                }
                this.onlyContainsUserCerts = ASN1Boolean.getInstance(aSN1TaggedObject, false).isTrue();
                continue;
            }
            this.distributionPoint = DistributionPointName.getInstance(aSN1TaggedObject, true);
        }
    }

    public IssuingDistributionPoint(DistributionPointName distributionPointName, boolean bl, boolean bl2) {
        this(distributionPointName, false, false, null, bl, bl2);
    }

    public IssuingDistributionPoint(DistributionPointName distributionPointName, boolean bl, boolean bl2, ReasonFlags reasonFlags, boolean bl3, boolean bl4) {
        this.distributionPoint = distributionPointName;
        this.indirectCRL = bl3;
        this.onlyContainsAttributeCerts = bl4;
        this.onlyContainsCACerts = bl2;
        this.onlyContainsUserCerts = bl;
        this.onlySomeReasons = reasonFlags;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (distributionPointName != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, distributionPointName));
        }
        if (bl) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, ASN1Boolean.getInstance(true)));
        }
        if (bl2) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, ASN1Boolean.getInstance(true)));
        }
        if (reasonFlags != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 3, reasonFlags));
        }
        if (bl3) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 4, ASN1Boolean.getInstance(true)));
        }
        if (bl4) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 5, ASN1Boolean.getInstance(true)));
        }
        this.seq = new DERSequence(aSN1EncodableVector);
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

    private String booleanToString(boolean bl) {
        String string = bl ? "true" : "false";
        return string;
    }

    public static IssuingDistributionPoint getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return IssuingDistributionPoint.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static IssuingDistributionPoint getInstance(Object object) {
        if (object instanceof IssuingDistributionPoint) {
            return (IssuingDistributionPoint)object;
        }
        if (object != null) {
            return new IssuingDistributionPoint(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public DistributionPointName getDistributionPoint() {
        return this.distributionPoint;
    }

    public ReasonFlags getOnlySomeReasons() {
        return this.onlySomeReasons;
    }

    public boolean isIndirectCRL() {
        return this.indirectCRL;
    }

    public boolean onlyContainsAttributeCerts() {
        return this.onlyContainsAttributeCerts;
    }

    public boolean onlyContainsCACerts() {
        return this.onlyContainsCACerts;
    }

    public boolean onlyContainsUserCerts() {
        return this.onlyContainsUserCerts;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.seq;
    }

    public String toString() {
        boolean bl;
        String string = Strings.lineSeparator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("IssuingDistributionPoint: [");
        stringBuffer.append(string);
        ASN1Object aSN1Object = this.distributionPoint;
        if (aSN1Object != null) {
            this.appendObject(stringBuffer, string, "distributionPoint", ((DistributionPointName)aSN1Object).toString());
        }
        if (bl = this.onlyContainsUserCerts) {
            this.appendObject(stringBuffer, string, "onlyContainsUserCerts", this.booleanToString(bl));
        }
        if (bl = this.onlyContainsCACerts) {
            this.appendObject(stringBuffer, string, "onlyContainsCACerts", this.booleanToString(bl));
        }
        if ((aSN1Object = this.onlySomeReasons) != null) {
            this.appendObject(stringBuffer, string, "onlySomeReasons", ((ASN1BitString)aSN1Object).toString());
        }
        if (bl = this.onlyContainsAttributeCerts) {
            this.appendObject(stringBuffer, string, "onlyContainsAttributeCerts", this.booleanToString(bl));
        }
        if (bl = this.indirectCRL) {
            this.appendObject(stringBuffer, string, "indirectCRL", this.booleanToString(bl));
        }
        stringBuffer.append("]");
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

