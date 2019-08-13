/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1UTCTime;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x509.TBSCertificate;
import com.android.org.bouncycastle.asn1.x509.Time;
import com.android.org.bouncycastle.asn1.x509.X509Extensions;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import dalvik.annotation.compat.UnsupportedAppUsage;

public class V3TBSCertificateGenerator {
    private boolean altNamePresentAndCritical;
    Time endDate;
    Extensions extensions;
    X500Name issuer;
    private DERBitString issuerUniqueID;
    ASN1Integer serialNumber;
    AlgorithmIdentifier signature;
    Time startDate;
    X500Name subject;
    SubjectPublicKeyInfo subjectPublicKeyInfo;
    private DERBitString subjectUniqueID;
    DERTaggedObject version = new DERTaggedObject(true, 0, new ASN1Integer(2L));

    @UnsupportedAppUsage
    public TBSCertificate generateTBSCertificate() {
        if (this.serialNumber != null && this.signature != null && this.issuer != null && this.startDate != null && this.endDate != null && (this.subject != null || this.altNamePresentAndCritical) && this.subjectPublicKeyInfo != null) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            aSN1EncodableVector.add(this.version);
            aSN1EncodableVector.add(this.serialNumber);
            aSN1EncodableVector.add(this.signature);
            aSN1EncodableVector.add(this.issuer);
            Object object = new ASN1EncodableVector();
            ((ASN1EncodableVector)object).add(this.startDate);
            ((ASN1EncodableVector)object).add(this.endDate);
            aSN1EncodableVector.add(new DERSequence((ASN1EncodableVector)object));
            object = this.subject;
            if (object != null) {
                aSN1EncodableVector.add((ASN1Encodable)object);
            } else {
                aSN1EncodableVector.add(new DERSequence());
            }
            aSN1EncodableVector.add(this.subjectPublicKeyInfo);
            object = this.issuerUniqueID;
            if (object != null) {
                aSN1EncodableVector.add(new DERTaggedObject(false, 1, (ASN1Encodable)object));
            }
            if ((object = this.subjectUniqueID) != null) {
                aSN1EncodableVector.add(new DERTaggedObject(false, 2, (ASN1Encodable)object));
            }
            if ((object = this.extensions) != null) {
                aSN1EncodableVector.add(new DERTaggedObject(true, 3, (ASN1Encodable)object));
            }
            return TBSCertificate.getInstance(new DERSequence(aSN1EncodableVector));
        }
        throw new IllegalStateException("not all mandatory fields set in V3 TBScertificate generator");
    }

    public void setEndDate(ASN1UTCTime aSN1UTCTime) {
        this.endDate = new Time(aSN1UTCTime);
    }

    @UnsupportedAppUsage
    public void setEndDate(Time time) {
        this.endDate = time;
    }

    public void setExtensions(Extensions aSN1Object) {
        this.extensions = aSN1Object;
        if (aSN1Object != null && (aSN1Object = ((Extensions)aSN1Object).getExtension(Extension.subjectAlternativeName)) != null && ((Extension)aSN1Object).isCritical()) {
            this.altNamePresentAndCritical = true;
        }
    }

    public void setExtensions(X509Extensions x509Extensions) {
        this.setExtensions(Extensions.getInstance(x509Extensions));
    }

    public void setIssuer(X500Name x500Name) {
        this.issuer = x500Name;
    }

    @UnsupportedAppUsage
    public void setIssuer(X509Name x509Name) {
        this.issuer = X500Name.getInstance(x509Name);
    }

    public void setIssuerUniqueID(DERBitString dERBitString) {
        this.issuerUniqueID = dERBitString;
    }

    @UnsupportedAppUsage
    public void setSerialNumber(ASN1Integer aSN1Integer) {
        this.serialNumber = aSN1Integer;
    }

    @UnsupportedAppUsage
    public void setSignature(AlgorithmIdentifier algorithmIdentifier) {
        this.signature = algorithmIdentifier;
    }

    public void setStartDate(ASN1UTCTime aSN1UTCTime) {
        this.startDate = new Time(aSN1UTCTime);
    }

    @UnsupportedAppUsage
    public void setStartDate(Time time) {
        this.startDate = time;
    }

    public void setSubject(X500Name x500Name) {
        this.subject = x500Name;
    }

    @UnsupportedAppUsage
    public void setSubject(X509Name x509Name) {
        this.subject = X500Name.getInstance(x509Name.toASN1Primitive());
    }

    @UnsupportedAppUsage
    public void setSubjectPublicKeyInfo(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.subjectPublicKeyInfo = subjectPublicKeyInfo;
    }

    public void setSubjectUniqueID(DERBitString dERBitString) {
        this.subjectUniqueID = dERBitString;
    }
}

