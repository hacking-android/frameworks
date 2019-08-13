/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.cms;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.BERSequence;
import com.android.org.bouncycastle.asn1.BERTaggedObject;
import com.android.org.bouncycastle.asn1.cms.CMSObjectIdentifiers;

public class ContentInfo
extends ASN1Object
implements CMSObjectIdentifiers {
    private ASN1Encodable content;
    private ASN1ObjectIdentifier contentType;

    public ContentInfo(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.contentType = aSN1ObjectIdentifier;
        this.content = aSN1Encodable;
    }

    public ContentInfo(ASN1Sequence aSN1Primitive) {
        if (((ASN1Sequence)aSN1Primitive).size() >= 1 && ((ASN1Sequence)aSN1Primitive).size() <= 2) {
            this.contentType = (ASN1ObjectIdentifier)((ASN1Sequence)aSN1Primitive).getObjectAt(0);
            if (((ASN1Sequence)aSN1Primitive).size() > 1) {
                if (((ASN1TaggedObject)(aSN1Primitive = (ASN1TaggedObject)((ASN1Sequence)aSN1Primitive).getObjectAt(1))).isExplicit() && ((ASN1TaggedObject)aSN1Primitive).getTagNo() == 0) {
                    this.content = ((ASN1TaggedObject)aSN1Primitive).getObject();
                } else {
                    throw new IllegalArgumentException("Bad tag for 'content'");
                }
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(((ASN1Sequence)aSN1Primitive).size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static ContentInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return ContentInfo.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static ContentInfo getInstance(Object object) {
        if (object instanceof ContentInfo) {
            return (ContentInfo)object;
        }
        if (object != null) {
            return new ContentInfo(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public ASN1Encodable getContent() {
        return this.content;
    }

    public ASN1ObjectIdentifier getContentType() {
        return this.contentType;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.contentType);
        ASN1Encodable aSN1Encodable = this.content;
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add(new BERTaggedObject(0, aSN1Encodable));
        }
        return new BERSequence(aSN1EncodableVector);
    }
}

