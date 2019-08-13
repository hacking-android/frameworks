/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Exception;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.BEROctetString;
import com.android.org.bouncycastle.asn1.BERSequence;
import com.android.org.bouncycastle.asn1.StreamUtil;
import java.io.IOException;
import java.util.Enumeration;

public class BERTaggedObject
extends ASN1TaggedObject {
    public BERTaggedObject(int n) {
        super(false, n, new BERSequence());
    }

    public BERTaggedObject(int n, ASN1Encodable aSN1Encodable) {
        super(true, n, aSN1Encodable);
    }

    public BERTaggedObject(boolean bl, int n, ASN1Encodable aSN1Encodable) {
        super(bl, n, aSN1Encodable);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    void encode(ASN1OutputStream object) throws IOException {
        ((ASN1OutputStream)object).writeTag(160, this.tagNo);
        ((ASN1OutputStream)object).write(128);
        if (!this.empty) {
            if (!this.explicit) {
                Enumeration enumeration;
                if (this.obj instanceof ASN1OctetString) {
                    enumeration = this.obj instanceof BEROctetString ? ((BEROctetString)this.obj).getObjects() : new BEROctetString(((ASN1OctetString)this.obj).getOctets()).getObjects();
                } else if (this.obj instanceof ASN1Sequence) {
                    enumeration = ((ASN1Sequence)this.obj).getObjects();
                } else {
                    if (!(this.obj instanceof ASN1Set)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("not implemented: ");
                        ((StringBuilder)object).append(this.obj.getClass().getName());
                        throw new ASN1Exception(((StringBuilder)object).toString());
                    }
                    enumeration = ((ASN1Set)this.obj).getObjects();
                }
                while (enumeration.hasMoreElements()) {
                    ((ASN1OutputStream)object).writeObject((ASN1Encodable)enumeration.nextElement());
                }
            } else {
                ((ASN1OutputStream)object).writeObject(this.obj);
            }
        }
        ((ASN1OutputStream)object).write(0);
        ((ASN1OutputStream)object).write(0);
    }

    @Override
    int encodedLength() throws IOException {
        if (!this.empty) {
            int n = this.obj.toASN1Primitive().encodedLength();
            if (this.explicit) {
                return StreamUtil.calculateTagLength(this.tagNo) + StreamUtil.calculateBodyLength(n) + n;
            }
            return StreamUtil.calculateTagLength(this.tagNo) + (n - 1);
        }
        return StreamUtil.calculateTagLength(this.tagNo) + 1;
    }

    @Override
    boolean isConstructed() {
        if (!this.empty) {
            if (this.explicit) {
                return true;
            }
            return this.obj.toASN1Primitive().toDERObject().isConstructed();
        }
        return true;
    }
}

