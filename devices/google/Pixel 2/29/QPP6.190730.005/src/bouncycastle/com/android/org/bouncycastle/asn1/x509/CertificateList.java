/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.TBSCertList;
import com.android.org.bouncycastle.asn1.x509.Time;
import java.util.Enumeration;

public class CertificateList
extends ASN1Object {
    int hashCodeValue;
    boolean isHashCodeSet = false;
    DERBitString sig;
    AlgorithmIdentifier sigAlgId;
    TBSCertList tbsCertList;

    public CertificateList(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 3) {
            this.tbsCertList = TBSCertList.getInstance(aSN1Sequence.getObjectAt(0));
            this.sigAlgId = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
            this.sig = DERBitString.getInstance(aSN1Sequence.getObjectAt(2));
            return;
        }
        throw new IllegalArgumentException("sequence wrong size for CertificateList");
    }

    public static CertificateList getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return CertificateList.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static CertificateList getInstance(Object object) {
        if (object instanceof CertificateList) {
            return (CertificateList)object;
        }
        if (object != null) {
            return new CertificateList(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public X500Name getIssuer() {
        return this.tbsCertList.getIssuer();
    }

    public Time getNextUpdate() {
        return this.tbsCertList.getNextUpdate();
    }

    public Enumeration getRevokedCertificateEnumeration() {
        return this.tbsCertList.getRevokedCertificateEnumeration();
    }

    public TBSCertList.CRLEntry[] getRevokedCertificates() {
        return this.tbsCertList.getRevokedCertificates();
    }

    public DERBitString getSignature() {
        return this.sig;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.sigAlgId;
    }

    public TBSCertList getTBSCertList() {
        return this.tbsCertList;
    }

    public Time getThisUpdate() {
        return this.tbsCertList.getThisUpdate();
    }

    public int getVersionNumber() {
        return this.tbsCertList.getVersionNumber();
    }

    @Override
    public int hashCode() {
        if (!this.isHashCodeSet) {
            this.hashCodeValue = super.hashCode();
            this.isHashCodeSet = true;
        }
        return this.hashCodeValue;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.tbsCertList);
        aSN1EncodableVector.add(this.sigAlgId);
        aSN1EncodableVector.add(this.sig);
        return new DERSequence(aSN1EncodableVector);
    }
}

