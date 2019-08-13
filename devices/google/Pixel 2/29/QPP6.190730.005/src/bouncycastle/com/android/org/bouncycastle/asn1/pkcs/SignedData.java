/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.BERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.pkcs.ContentInfo;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import java.util.Enumeration;

public class SignedData
extends ASN1Object
implements PKCSObjectIdentifiers {
    private ASN1Set certificates;
    private ContentInfo contentInfo;
    private ASN1Set crls;
    private ASN1Set digestAlgorithms;
    private ASN1Set signerInfos;
    private ASN1Integer version;

    public SignedData(ASN1Integer aSN1Integer, ASN1Set aSN1Set, ContentInfo contentInfo, ASN1Set aSN1Set2, ASN1Set aSN1Set3, ASN1Set aSN1Set4) {
        this.version = aSN1Integer;
        this.digestAlgorithms = aSN1Set;
        this.contentInfo = contentInfo;
        this.certificates = aSN1Set2;
        this.crls = aSN1Set3;
        this.signerInfos = aSN1Set4;
    }

    public SignedData(ASN1Sequence object) {
        object = ((ASN1Sequence)object).getObjects();
        this.version = (ASN1Integer)object.nextElement();
        this.digestAlgorithms = (ASN1Set)object.nextElement();
        this.contentInfo = ContentInfo.getInstance(object.nextElement());
        while (object.hasMoreElements()) {
            ASN1Primitive aSN1Primitive = (ASN1Primitive)object.nextElement();
            if (aSN1Primitive instanceof ASN1TaggedObject) {
                int n = ((ASN1TaggedObject)(aSN1Primitive = (ASN1TaggedObject)aSN1Primitive)).getTagNo();
                if (n != 0) {
                    if (n == 1) {
                        this.crls = ASN1Set.getInstance((ASN1TaggedObject)aSN1Primitive, false);
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("unknown tag value ");
                    ((StringBuilder)object).append(((ASN1TaggedObject)aSN1Primitive).getTagNo());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                this.certificates = ASN1Set.getInstance((ASN1TaggedObject)aSN1Primitive, false);
                continue;
            }
            this.signerInfos = (ASN1Set)aSN1Primitive;
        }
    }

    public static SignedData getInstance(Object object) {
        if (object instanceof SignedData) {
            return (SignedData)object;
        }
        if (object != null) {
            return new SignedData(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public ASN1Set getCRLs() {
        return this.crls;
    }

    public ASN1Set getCertificates() {
        return this.certificates;
    }

    public ContentInfo getContentInfo() {
        return this.contentInfo;
    }

    public ASN1Set getDigestAlgorithms() {
        return this.digestAlgorithms;
    }

    public ASN1Set getSignerInfos() {
        return this.signerInfos;
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.version);
        aSN1EncodableVector.add(this.digestAlgorithms);
        aSN1EncodableVector.add(this.contentInfo);
        ASN1Set aSN1Set = this.certificates;
        if (aSN1Set != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, aSN1Set));
        }
        if ((aSN1Set = this.crls) != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, aSN1Set));
        }
        aSN1EncodableVector.add(this.signerInfos);
        return new BERSequence(aSN1EncodableVector);
    }
}

