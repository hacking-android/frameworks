/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.BERSequence;
import com.android.org.bouncycastle.asn1.DLSequence;
import com.android.org.bouncycastle.asn1.pkcs.ContentInfo;

public class AuthenticatedSafe
extends ASN1Object {
    private ContentInfo[] info;
    private boolean isBer = true;

    private AuthenticatedSafe(ASN1Sequence aSN1Sequence) {
        ContentInfo[] arrcontentInfo;
        this.info = new ContentInfo[aSN1Sequence.size()];
        for (int i = 0; i != (arrcontentInfo = this.info).length; ++i) {
            arrcontentInfo[i] = ContentInfo.getInstance(aSN1Sequence.getObjectAt(i));
        }
        this.isBer = aSN1Sequence instanceof BERSequence;
    }

    public AuthenticatedSafe(ContentInfo[] arrcontentInfo) {
        this.info = this.copy(arrcontentInfo);
    }

    private ContentInfo[] copy(ContentInfo[] arrcontentInfo) {
        ContentInfo[] arrcontentInfo2 = new ContentInfo[arrcontentInfo.length];
        System.arraycopy(arrcontentInfo, 0, arrcontentInfo2, 0, arrcontentInfo2.length);
        return arrcontentInfo2;
    }

    public static AuthenticatedSafe getInstance(Object object) {
        if (object instanceof AuthenticatedSafe) {
            return (AuthenticatedSafe)object;
        }
        if (object != null) {
            return new AuthenticatedSafe(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public ContentInfo[] getContentInfo() {
        return this.copy(this.info);
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ContentInfo[] arrcontentInfo;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != (arrcontentInfo = this.info).length; ++i) {
            aSN1EncodableVector.add(arrcontentInfo[i]);
        }
        if (this.isBer) {
            return new BERSequence(aSN1EncodableVector);
        }
        return new DLSequence(aSN1EncodableVector);
    }
}

