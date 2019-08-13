/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.pkcs.Attribute;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import java.util.Enumeration;

public class CertificationRequestInfo
extends ASN1Object {
    ASN1Set attributes = null;
    X500Name subject;
    SubjectPublicKeyInfo subjectPKInfo;
    ASN1Integer version = new ASN1Integer(0L);

    public CertificationRequestInfo(ASN1Sequence aSN1Sequence) {
        this.version = (ASN1Integer)aSN1Sequence.getObjectAt(0);
        this.subject = X500Name.getInstance(aSN1Sequence.getObjectAt(1));
        this.subjectPKInfo = SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(2));
        if (aSN1Sequence.size() > 3) {
            this.attributes = ASN1Set.getInstance((ASN1TaggedObject)aSN1Sequence.getObjectAt(3), false);
        }
        CertificationRequestInfo.validateAttributes(this.attributes);
        if (this.subject != null && this.version != null && this.subjectPKInfo != null) {
            return;
        }
        throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
    }

    public CertificationRequestInfo(X500Name x500Name, SubjectPublicKeyInfo subjectPublicKeyInfo, ASN1Set aSN1Set) {
        if (x500Name != null && subjectPublicKeyInfo != null) {
            CertificationRequestInfo.validateAttributes(aSN1Set);
            this.subject = x500Name;
            this.subjectPKInfo = subjectPublicKeyInfo;
            this.attributes = aSN1Set;
            return;
        }
        throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
    }

    public CertificationRequestInfo(X509Name x509Name, SubjectPublicKeyInfo subjectPublicKeyInfo, ASN1Set aSN1Set) {
        this(X500Name.getInstance(x509Name.toASN1Primitive()), subjectPublicKeyInfo, aSN1Set);
    }

    public static CertificationRequestInfo getInstance(Object object) {
        if (object instanceof CertificationRequestInfo) {
            return (CertificationRequestInfo)object;
        }
        if (object != null) {
            return new CertificationRequestInfo(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    private static void validateAttributes(ASN1Set object) {
        if (object == null) {
            return;
        }
        object = ((ASN1Set)object).getObjects();
        while (object.hasMoreElements()) {
            Attribute attribute = Attribute.getInstance(object.nextElement());
            if (!attribute.getAttrType().equals(PKCSObjectIdentifiers.pkcs_9_at_challengePassword) || attribute.getAttrValues().size() == 1) continue;
            throw new IllegalArgumentException("challengePassword attribute must have one value");
        }
    }

    public ASN1Set getAttributes() {
        return this.attributes;
    }

    public X500Name getSubject() {
        return this.subject;
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.subjectPKInfo;
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.version);
        aSN1EncodableVector.add(this.subject);
        aSN1EncodableVector.add(this.subjectPKInfo);
        ASN1Set aSN1Set = this.attributes;
        if (aSN1Set != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, aSN1Set));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

