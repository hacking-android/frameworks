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
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x509.Time;
import java.math.BigInteger;

public class TBSCertificate
extends ASN1Object {
    Time endDate;
    Extensions extensions;
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

    /*
     * Enabled aggressive block sorting
     */
    private TBSCertificate(ASN1Sequence object) {
        int n;
        int n2 = 0;
        this.seq = object;
        if (((ASN1Sequence)object).getObjectAt(0) instanceof ASN1TaggedObject) {
            this.version = ASN1Integer.getInstance((ASN1TaggedObject)((ASN1Sequence)object).getObjectAt(0), true);
        } else {
            n2 = -1;
            this.version = new ASN1Integer(0L);
        }
        int n3 = 0;
        boolean bl = false;
        if (this.version.getValue().equals(BigInteger.valueOf(0L))) {
            n3 = 1;
        } else if (this.version.getValue().equals(BigInteger.valueOf(1L))) {
            bl = true;
        } else if (!this.version.getValue().equals(BigInteger.valueOf(2L))) {
            throw new IllegalArgumentException("version number not recognised");
        }
        this.serialNumber = ASN1Integer.getInstance(((ASN1Sequence)object).getObjectAt(n2 + 1));
        this.signature = AlgorithmIdentifier.getInstance(((ASN1Sequence)object).getObjectAt(n2 + 2));
        this.issuer = X500Name.getInstance(((ASN1Sequence)object).getObjectAt(n2 + 3));
        ASN1Primitive aSN1Primitive = (ASN1Sequence)((ASN1Sequence)object).getObjectAt(n2 + 4);
        this.startDate = Time.getInstance(((ASN1Sequence)aSN1Primitive).getObjectAt(0));
        this.endDate = Time.getInstance(((ASN1Sequence)aSN1Primitive).getObjectAt(1));
        this.subject = X500Name.getInstance(((ASN1Sequence)object).getObjectAt(n2 + 5));
        this.subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(((ASN1Sequence)object).getObjectAt(n2 + 6));
        int n4 = n = ((ASN1Sequence)object).size() - (n2 + 6) - 1;
        if (n != 0) {
            if (n3 != 0) {
                throw new IllegalArgumentException("version 1 certificate contains extra data");
            }
            n4 = n;
        }
        while (n4 > 0) {
            aSN1Primitive = (ASN1TaggedObject)((ASN1Sequence)object).getObjectAt(n2 + 6 + n4);
            n3 = ((ASN1TaggedObject)aSN1Primitive).getTagNo();
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 != 3) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unknown tag encountered in structure: ");
                        ((StringBuilder)object).append(((ASN1TaggedObject)aSN1Primitive).getTagNo());
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    if (bl) {
                        throw new IllegalArgumentException("version 2 certificate cannot contain extensions");
                    }
                    this.extensions = Extensions.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)aSN1Primitive, true));
                } else {
                    this.subjectUniqueId = DERBitString.getInstance((ASN1TaggedObject)aSN1Primitive, false);
                }
            } else {
                this.issuerUniqueId = DERBitString.getInstance((ASN1TaggedObject)aSN1Primitive, false);
            }
            --n4;
        }
        return;
    }

    public static TBSCertificate getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return TBSCertificate.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static TBSCertificate getInstance(Object object) {
        if (object instanceof TBSCertificate) {
            return (TBSCertificate)object;
        }
        if (object != null) {
            return new TBSCertificate(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public Time getEndDate() {
        return this.endDate;
    }

    public Extensions getExtensions() {
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

    public ASN1Integer getVersion() {
        return this.version;
    }

    public int getVersionNumber() {
        return this.version.getValue().intValue() + 1;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.seq;
    }
}

