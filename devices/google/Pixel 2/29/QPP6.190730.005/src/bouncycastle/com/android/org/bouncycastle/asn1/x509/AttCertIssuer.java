/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Choice;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.x509.GeneralNames;
import com.android.org.bouncycastle.asn1.x509.V2Form;

public class AttCertIssuer
extends ASN1Object
implements ASN1Choice {
    ASN1Primitive choiceObj;
    ASN1Encodable obj;

    public AttCertIssuer(GeneralNames generalNames) {
        this.obj = generalNames;
        this.choiceObj = this.obj.toASN1Primitive();
    }

    public AttCertIssuer(V2Form v2Form) {
        this.obj = v2Form;
        this.choiceObj = new DERTaggedObject(false, 0, this.obj);
    }

    public static AttCertIssuer getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return AttCertIssuer.getInstance(aSN1TaggedObject.getObject());
    }

    public static AttCertIssuer getInstance(Object object) {
        if (object != null && !(object instanceof AttCertIssuer)) {
            if (object instanceof V2Form) {
                return new AttCertIssuer(V2Form.getInstance(object));
            }
            if (object instanceof GeneralNames) {
                return new AttCertIssuer((GeneralNames)object);
            }
            if (object instanceof ASN1TaggedObject) {
                return new AttCertIssuer(V2Form.getInstance((ASN1TaggedObject)object, false));
            }
            if (object instanceof ASN1Sequence) {
                return new AttCertIssuer(GeneralNames.getInstance(object));
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unknown object in factory: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (AttCertIssuer)object;
    }

    public ASN1Encodable getIssuer() {
        return this.obj;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.choiceObj;
    }
}

