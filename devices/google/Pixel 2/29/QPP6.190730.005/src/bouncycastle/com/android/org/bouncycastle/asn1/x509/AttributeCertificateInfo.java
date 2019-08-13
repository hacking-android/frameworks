/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.AttCertIssuer;
import com.android.org.bouncycastle.asn1.x509.AttCertValidityPeriod;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.Holder;
import java.math.BigInteger;

public class AttributeCertificateInfo
extends ASN1Object {
    private AttCertValidityPeriod attrCertValidityPeriod;
    private ASN1Sequence attributes;
    private Extensions extensions;
    private Holder holder;
    private AttCertIssuer issuer;
    private DERBitString issuerUniqueID;
    private ASN1Integer serialNumber;
    private AlgorithmIdentifier signature;
    private ASN1Integer version;

    private AttributeCertificateInfo(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() >= 6 && aSN1Sequence.size() <= 9) {
            int n;
            if (aSN1Sequence.getObjectAt(0) instanceof ASN1Integer) {
                this.version = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
                n = 1;
            } else {
                this.version = new ASN1Integer(0L);
                n = 0;
            }
            this.holder = Holder.getInstance(aSN1Sequence.getObjectAt(n));
            this.issuer = AttCertIssuer.getInstance(aSN1Sequence.getObjectAt(n + 1));
            this.signature = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(n + 2));
            this.serialNumber = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(n + 3));
            this.attrCertValidityPeriod = AttCertValidityPeriod.getInstance(aSN1Sequence.getObjectAt(n + 4));
            this.attributes = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(n + 5));
            n += 6;
            while (n < aSN1Sequence.size()) {
                ASN1Encodable aSN1Encodable = aSN1Sequence.getObjectAt(n);
                if (aSN1Encodable instanceof DERBitString) {
                    this.issuerUniqueID = DERBitString.getInstance(aSN1Sequence.getObjectAt(n));
                } else if (aSN1Encodable instanceof ASN1Sequence || aSN1Encodable instanceof Extensions) {
                    this.extensions = Extensions.getInstance(aSN1Sequence.getObjectAt(n));
                }
                ++n;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(aSN1Sequence.size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static AttributeCertificateInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return AttributeCertificateInfo.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static AttributeCertificateInfo getInstance(Object object) {
        if (object instanceof AttributeCertificateInfo) {
            return (AttributeCertificateInfo)object;
        }
        if (object != null) {
            return new AttributeCertificateInfo(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public AttCertValidityPeriod getAttrCertValidityPeriod() {
        return this.attrCertValidityPeriod;
    }

    public ASN1Sequence getAttributes() {
        return this.attributes;
    }

    public Extensions getExtensions() {
        return this.extensions;
    }

    public Holder getHolder() {
        return this.holder;
    }

    public AttCertIssuer getIssuer() {
        return this.issuer;
    }

    public DERBitString getIssuerUniqueID() {
        return this.issuerUniqueID;
    }

    public ASN1Integer getSerialNumber() {
        return this.serialNumber;
    }

    public AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.version.getValue().intValue() != 0) {
            aSN1EncodableVector.add(this.version);
        }
        aSN1EncodableVector.add(this.holder);
        aSN1EncodableVector.add(this.issuer);
        aSN1EncodableVector.add(this.signature);
        aSN1EncodableVector.add(this.serialNumber);
        aSN1EncodableVector.add(this.attrCertValidityPeriod);
        aSN1EncodableVector.add(this.attributes);
        ASN1Object aSN1Object = this.issuerUniqueID;
        if (aSN1Object != null) {
            aSN1EncodableVector.add(aSN1Object);
        }
        if ((aSN1Object = this.extensions) != null) {
            aSN1EncodableVector.add(aSN1Object);
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

