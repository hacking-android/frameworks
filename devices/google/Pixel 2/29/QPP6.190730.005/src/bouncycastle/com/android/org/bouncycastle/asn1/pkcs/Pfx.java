/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.BERSequence;
import com.android.org.bouncycastle.asn1.pkcs.ContentInfo;
import com.android.org.bouncycastle.asn1.pkcs.MacData;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import java.math.BigInteger;

public class Pfx
extends ASN1Object
implements PKCSObjectIdentifiers {
    private ContentInfo contentInfo;
    private MacData macData = null;

    private Pfx(ASN1Sequence aSN1Sequence) {
        if (ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0)).getValue().intValue() == 3) {
            this.contentInfo = ContentInfo.getInstance(aSN1Sequence.getObjectAt(1));
            if (aSN1Sequence.size() == 3) {
                this.macData = MacData.getInstance(aSN1Sequence.getObjectAt(2));
            }
            return;
        }
        throw new IllegalArgumentException("wrong version for PFX PDU");
    }

    public Pfx(ContentInfo contentInfo, MacData macData) {
        this.contentInfo = contentInfo;
        this.macData = macData;
    }

    public static Pfx getInstance(Object object) {
        if (object instanceof Pfx) {
            return (Pfx)object;
        }
        if (object != null) {
            return new Pfx(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public ContentInfo getAuthSafe() {
        return this.contentInfo;
    }

    public MacData getMacData() {
        return this.macData;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(3L));
        aSN1EncodableVector.add(this.contentInfo);
        MacData macData = this.macData;
        if (macData != null) {
            aSN1EncodableVector.add(macData);
        }
        return new BERSequence(aSN1EncodableVector);
    }
}

