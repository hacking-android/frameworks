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
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.x509.GeneralNames;
import com.android.org.bouncycastle.asn1.x509.IssuerSerial;
import com.android.org.bouncycastle.asn1.x509.ObjectDigestInfo;

public class Holder
extends ASN1Object {
    public static final int V1_CERTIFICATE_HOLDER = 0;
    public static final int V2_CERTIFICATE_HOLDER = 1;
    IssuerSerial baseCertificateID;
    GeneralNames entityName;
    ObjectDigestInfo objectDigestInfo;
    private int version = 1;

    private Holder(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() <= 3) {
            for (int i = 0; i != aSN1Sequence.size(); ++i) {
                ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i));
                int n = aSN1TaggedObject.getTagNo();
                if (n != 0) {
                    if (n != 1) {
                        if (n == 2) {
                            this.objectDigestInfo = ObjectDigestInfo.getInstance(aSN1TaggedObject, false);
                            continue;
                        }
                        throw new IllegalArgumentException("unknown tag in Holder");
                    }
                    this.entityName = GeneralNames.getInstance(aSN1TaggedObject, false);
                    continue;
                }
                this.baseCertificateID = IssuerSerial.getInstance(aSN1TaggedObject, false);
            }
            this.version = 1;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(aSN1Sequence.size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Holder(ASN1TaggedObject aSN1TaggedObject) {
        int n = aSN1TaggedObject.getTagNo();
        if (n != 0) {
            if (n != 1) throw new IllegalArgumentException("unknown tag in Holder");
            this.entityName = GeneralNames.getInstance(aSN1TaggedObject, true);
        } else {
            this.baseCertificateID = IssuerSerial.getInstance(aSN1TaggedObject, true);
        }
        this.version = 0;
    }

    public Holder(GeneralNames generalNames) {
        this(generalNames, 1);
    }

    public Holder(GeneralNames generalNames, int n) {
        this.entityName = generalNames;
        this.version = n;
    }

    public Holder(IssuerSerial issuerSerial) {
        this(issuerSerial, 1);
    }

    public Holder(IssuerSerial issuerSerial, int n) {
        this.baseCertificateID = issuerSerial;
        this.version = n;
    }

    public Holder(ObjectDigestInfo objectDigestInfo) {
        this.objectDigestInfo = objectDigestInfo;
    }

    public static Holder getInstance(Object object) {
        if (object instanceof Holder) {
            return (Holder)object;
        }
        if (object instanceof ASN1TaggedObject) {
            return new Holder(ASN1TaggedObject.getInstance(object));
        }
        if (object != null) {
            return new Holder(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public IssuerSerial getBaseCertificateID() {
        return this.baseCertificateID;
    }

    public GeneralNames getEntityName() {
        return this.entityName;
    }

    public ObjectDigestInfo getObjectDigestInfo() {
        return this.objectDigestInfo;
    }

    public int getVersion() {
        return this.version;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        if (this.version == 1) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            ASN1Object aSN1Object = this.baseCertificateID;
            if (aSN1Object != null) {
                aSN1EncodableVector.add(new DERTaggedObject(false, 0, aSN1Object));
            }
            if ((aSN1Object = this.entityName) != null) {
                aSN1EncodableVector.add(new DERTaggedObject(false, 1, aSN1Object));
            }
            if ((aSN1Object = this.objectDigestInfo) != null) {
                aSN1EncodableVector.add(new DERTaggedObject(false, 2, aSN1Object));
            }
            return new DERSequence(aSN1EncodableVector);
        }
        GeneralNames generalNames = this.entityName;
        if (generalNames != null) {
            return new DERTaggedObject(true, 1, generalNames);
        }
        return new DERTaggedObject(true, 0, this.baseCertificateID);
    }
}

