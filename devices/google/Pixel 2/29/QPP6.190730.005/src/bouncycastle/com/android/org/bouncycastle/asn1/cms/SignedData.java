/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.cms;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.BERSequence;
import com.android.org.bouncycastle.asn1.BERSet;
import com.android.org.bouncycastle.asn1.BERTaggedObject;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.cms.ContentInfo;
import com.android.org.bouncycastle.asn1.cms.SignerInfo;
import java.math.BigInteger;
import java.util.Enumeration;

public class SignedData
extends ASN1Object {
    private static final ASN1Integer VERSION_1 = new ASN1Integer(1L);
    private static final ASN1Integer VERSION_3 = new ASN1Integer(3L);
    private static final ASN1Integer VERSION_4 = new ASN1Integer(4L);
    private static final ASN1Integer VERSION_5 = new ASN1Integer(5L);
    private ASN1Set certificates;
    private boolean certsBer;
    private ContentInfo contentInfo;
    private ASN1Set crls;
    private boolean crlsBer;
    private ASN1Set digestAlgorithms;
    private ASN1Set signerInfos;
    private ASN1Integer version;

    private SignedData(ASN1Sequence aSN1Primitive) {
        Object object = ((ASN1Sequence)aSN1Primitive).getObjects();
        this.version = ASN1Integer.getInstance(object.nextElement());
        this.digestAlgorithms = (ASN1Set)object.nextElement();
        this.contentInfo = ContentInfo.getInstance(object.nextElement());
        while (object.hasMoreElements()) {
            aSN1Primitive = (ASN1Primitive)object.nextElement();
            if (aSN1Primitive instanceof ASN1TaggedObject) {
                int n = ((ASN1TaggedObject)(aSN1Primitive = (ASN1TaggedObject)aSN1Primitive)).getTagNo();
                if (n != 0) {
                    if (n == 1) {
                        this.crlsBer = aSN1Primitive instanceof BERTaggedObject;
                        this.crls = ASN1Set.getInstance((ASN1TaggedObject)aSN1Primitive, false);
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("unknown tag value ");
                    ((StringBuilder)object).append(((ASN1TaggedObject)aSN1Primitive).getTagNo());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                this.certsBer = aSN1Primitive instanceof BERTaggedObject;
                this.certificates = ASN1Set.getInstance((ASN1TaggedObject)aSN1Primitive, false);
                continue;
            }
            this.signerInfos = (ASN1Set)aSN1Primitive;
        }
    }

    public SignedData(ASN1Set aSN1Set, ContentInfo contentInfo, ASN1Set aSN1Set2, ASN1Set aSN1Set3, ASN1Set aSN1Set4) {
        this.version = this.calculateVersion(contentInfo.getContentType(), aSN1Set2, aSN1Set3, aSN1Set4);
        this.digestAlgorithms = aSN1Set;
        this.contentInfo = contentInfo;
        this.certificates = aSN1Set2;
        this.crls = aSN1Set3;
        this.signerInfos = aSN1Set4;
        this.crlsBer = aSN1Set3 instanceof BERSet;
        this.certsBer = aSN1Set2 instanceof BERSet;
    }

    private ASN1Integer calculateVersion(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Set object, ASN1Set aSN1Set, ASN1Set aSN1Set2) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        boolean bl7 = false;
        boolean bl8 = false;
        if (object != null) {
            object = ((ASN1Set)object).getObjects();
            do {
                bl = bl2;
                bl5 = bl6;
                bl7 = bl8;
                if (!object.hasMoreElements()) break;
                Object object2 = object.nextElement();
                bl5 = bl2;
                bl7 = bl6;
                bl = bl8;
                if (object2 instanceof ASN1TaggedObject) {
                    if (((ASN1TaggedObject)(object2 = ASN1TaggedObject.getInstance(object2))).getTagNo() == 1) {
                        bl7 = true;
                        bl5 = bl2;
                        bl = bl8;
                    } else if (((ASN1TaggedObject)object2).getTagNo() == 2) {
                        bl = true;
                        bl5 = bl2;
                        bl7 = bl6;
                    } else {
                        bl5 = bl2;
                        bl7 = bl6;
                        bl = bl8;
                        if (((ASN1TaggedObject)object2).getTagNo() == 3) {
                            bl5 = true;
                            bl = bl8;
                            bl7 = bl6;
                        }
                    }
                }
                bl2 = bl5;
                bl6 = bl7;
                bl8 = bl;
            } while (true);
        }
        if (bl) {
            return new ASN1Integer(5L);
        }
        bl6 = bl3;
        if (aSN1Set != null) {
            object = aSN1Set.getObjects();
            bl8 = bl4;
            do {
                bl6 = bl8;
                if (!object.hasMoreElements()) break;
                if (!(object.nextElement() instanceof ASN1TaggedObject)) continue;
                bl8 = true;
            } while (true);
        }
        if (bl6) {
            return VERSION_5;
        }
        if (bl7) {
            return VERSION_4;
        }
        if (bl5) {
            return VERSION_3;
        }
        if (this.checkForVersion3(aSN1Set2)) {
            return VERSION_3;
        }
        if (!CMSObjectIdentifiers.data.equals(aSN1ObjectIdentifier)) {
            return VERSION_3;
        }
        return VERSION_1;
    }

    private boolean checkForVersion3(ASN1Set object) {
        object = ((ASN1Set)object).getObjects();
        while (object.hasMoreElements()) {
            if (SignerInfo.getInstance(object.nextElement()).getVersion().getValue().intValue() != 3) continue;
            return true;
        }
        return false;
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

    public ASN1Set getDigestAlgorithms() {
        return this.digestAlgorithms;
    }

    public ContentInfo getEncapContentInfo() {
        return this.contentInfo;
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
            if (this.certsBer) {
                aSN1EncodableVector.add(new BERTaggedObject(false, 0, aSN1Set));
            } else {
                aSN1EncodableVector.add(new DERTaggedObject(false, 0, aSN1Set));
            }
        }
        if ((aSN1Set = this.crls) != null) {
            if (this.crlsBer) {
                aSN1EncodableVector.add(new BERTaggedObject(false, 1, aSN1Set));
            } else {
                aSN1EncodableVector.add(new DERTaggedObject(false, 1, aSN1Set));
            }
        }
        aSN1EncodableVector.add(this.signerInfos);
        return new BERSequence(aSN1EncodableVector);
    }
}

