/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x509.Time;
import com.android.org.bouncycastle.asn1.x509.X509Extensions;
import com.android.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import java.math.BigInteger;

public class TBSCertificateStructure
extends ASN1Object
implements X509ObjectIdentifiers,
PKCSObjectIdentifiers {
    Time endDate;
    X509Extensions extensions;
    X500Name issuer;
    DERBitString issuerUniqueId;
    ASN1Sequence seq;
    ASN1Integer serialNumber;
    AlgorithmIdentifier signature;
    Time startDate;
    X500Name subject;
    SubjectPublicKeyInfo subjectPublicKeyInfo;
    DERBitString subjectUniqueId;
    ASN1Integer version;

    public TBSCertificateStructure(ASN1Sequence aSN1Sequence) {
        int n = 0;
        this.seq = aSN1Sequence;
        if (aSN1Sequence.getObjectAt(0) instanceof DERTaggedObject) {
            this.version = ASN1Integer.getInstance((ASN1TaggedObject)aSN1Sequence.getObjectAt(0), true);
        } else {
            n = -1;
            this.version = new ASN1Integer(0L);
        }
        this.serialNumber = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(n + 1));
        this.signature = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(n + 2));
        this.issuer = X500Name.getInstance(aSN1Sequence.getObjectAt(n + 3));
        ASN1Primitive aSN1Primitive = (ASN1Sequence)aSN1Sequence.getObjectAt(n + 4);
        this.startDate = Time.getInstance(((ASN1Sequence)aSN1Primitive).getObjectAt(0));
        this.endDate = Time.getInstance(((ASN1Sequence)aSN1Primitive).getObjectAt(1));
        this.subject = X500Name.getInstance(aSN1Sequence.getObjectAt(n + 5));
        this.subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(aSN1Sequence.getObjectAt(n + 6));
        for (int i = aSN1Sequence.size() - (n + 6) - 1; i > 0; --i) {
            aSN1Primitive = (DERTaggedObject)aSN1Sequence.getObjectAt(n + 6 + i);
            int n2 = ((ASN1TaggedObject)aSN1Primitive).getTagNo();
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) continue;
                    this.extensions = X509Extensions.getInstance(aSN1Primitive);
                    continue;
                }
                this.subjectUniqueId = DERBitString.getInstance((ASN1TaggedObject)aSN1Primitive, false);
                continue;
            }
            this.issuerUniqueId = DERBitString.getInstance((ASN1TaggedObject)aSN1Primitive, false);
        }
    }

    public static TBSCertificateStructure getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return TBSCertificateStructure.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static TBSCertificateStructure getInstance(Object object) {
        if (object instanceof TBSCertificateStructure) {
            return (TBSCertificateStructure)object;
        }
        if (object != null) {
            return new TBSCertificateStructure(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public Time getEndDate() {
        return this.endDate;
    }

    public X509Extensions getExtensions() {
        return this.extensions;
    }

    public X500Name getIssuer() {
        return this.issuer;
    }

    public DERBitString getIssuerUniqueId() {
        return this.issuerUniqueId;
    }

    public ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    public AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public Time getStartDate() {
        return this.startDate;
    }

    public X500Name getSubject() {
        return this.subject;
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.subjectPublicKeyInfo;
    }

    public DERBitString getSubjectUniqueId() {
        return this.subjectUniqueId;
    }

    public int getVersion() {
        return this.version.getValue().intValue() + 1;
    }

    public ASN1Integer getVersionNumber() {
        return this.version;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.seq;
    }
}

