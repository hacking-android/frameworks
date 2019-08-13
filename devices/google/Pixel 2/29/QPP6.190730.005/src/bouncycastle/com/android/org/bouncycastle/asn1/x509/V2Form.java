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

public class V2Form
extends ASN1Object {
    IssuerSerial baseCertificateID;
    GeneralNames issuerName;
    ObjectDigestInfo objectDigestInfo;

    public V2Form(ASN1Sequence object) {
        block5 : {
            if (((ASN1Sequence)object).size() > 3) break block5;
            int n = 0;
            if (!(((ASN1Sequence)object).getObjectAt(0) instanceof ASN1TaggedObject)) {
                n = 0 + 1;
                this.issuerName = GeneralNames.getInstance(((ASN1Sequence)object).getObjectAt(0));
            }
            while (n != ((ASN1Sequence)object).size()) {
                ASN1TaggedObject aSN1TaggedObject;
                block8 : {
                    block7 : {
                        block6 : {
                            aSN1TaggedObject = ASN1TaggedObject.getInstance(((ASN1Sequence)object).getObjectAt(n));
                            if (aSN1TaggedObject.getTagNo() != 0) break block6;
                            this.baseCertificateID = IssuerSerial.getInstance(aSN1TaggedObject, false);
                            break block7;
                        }
                        if (aSN1TaggedObject.getTagNo() != 1) break block8;
                        this.objectDigestInfo = ObjectDigestInfo.getInstance(aSN1TaggedObject, false);
                    }
                    ++n;
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Bad tag number: ");
                ((StringBuilder)object).append(aSN1TaggedObject.getTagNo());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(((ASN1Sequence)object).size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public V2Form(GeneralNames generalNames) {
        this(generalNames, null, null);
    }

    public V2Form(GeneralNames generalNames, IssuerSerial issuerSerial) {
        this(generalNames, issuerSerial, null);
    }

    public V2Form(GeneralNames generalNames, IssuerSerial issuerSerial, ObjectDigestInfo objectDigestInfo) {
        this.issuerName = generalNames;
        this.baseCertificateID = issuerSerial;
        this.objectDigestInfo = objectDigestInfo;
    }

    public V2Form(GeneralNames generalNames, ObjectDigestInfo objectDigestInfo) {
        this(generalNames, null, objectDigestInfo);
    }

    public static V2Form getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return V2Form.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static V2Form getInstance(Object object) {
        if (object instanceof V2Form) {
            return (V2Form)object;
        }
        if (object != null) {
            return new V2Form(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public IssuerSerial getBaseCertificateID() {
        return this.baseCertificateID;
    }

    public GeneralNames getIssuerName() {
        return this.issuerName;
    }

    public ObjectDigestInfo getObjectDigestInfo() {
        return this.objectDigestInfo;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1Object aSN1Object = this.issuerName;
        if (aSN1Object != null) {
            aSN1EncodableVector.add(aSN1Object);
        }
        if ((aSN1Object = this.baseCertificateID) != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, aSN1Object));
        }
        if ((aSN1Object = this.objectDigestInfo) != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, aSN1Object));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

