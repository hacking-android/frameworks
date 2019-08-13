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
import com.android.org.bouncycastle.asn1.x509.GeneralSubtree;
import java.util.Enumeration;

public class NameConstraints
extends ASN1Object {
    private GeneralSubtree[] excluded;
    private GeneralSubtree[] permitted;

    private NameConstraints(ASN1Sequence aSN1Primitive) {
        Object object = ((ASN1Sequence)aSN1Primitive).getObjects();
        while (object.hasMoreElements()) {
            aSN1Primitive = ASN1TaggedObject.getInstance(object.nextElement());
            int n = ((ASN1TaggedObject)aSN1Primitive).getTagNo();
            if (n != 0) {
                if (n == 1) {
                    this.excluded = this.createArray(ASN1Sequence.getInstance((ASN1TaggedObject)aSN1Primitive, false));
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown tag encountered: ");
                ((StringBuilder)object).append(((ASN1TaggedObject)aSN1Primitive).getTagNo());
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.permitted = this.createArray(ASN1Sequence.getInstance((ASN1TaggedObject)aSN1Primitive, false));
        }
    }

    public NameConstraints(GeneralSubtree[] arrgeneralSubtree, GeneralSubtree[] arrgeneralSubtree2) {
        this.permitted = NameConstraints.cloneSubtree(arrgeneralSubtree);
        this.excluded = NameConstraints.cloneSubtree(arrgeneralSubtree2);
    }

    private static GeneralSubtree[] cloneSubtree(GeneralSubtree[] arrgeneralSubtree) {
        if (arrgeneralSubtree != null) {
            GeneralSubtree[] arrgeneralSubtree2 = new GeneralSubtree[arrgeneralSubtree.length];
            System.arraycopy(arrgeneralSubtree, 0, arrgeneralSubtree2, 0, arrgeneralSubtree2.length);
            return arrgeneralSubtree2;
        }
        return null;
    }

    private GeneralSubtree[] createArray(ASN1Sequence aSN1Sequence) {
        GeneralSubtree[] arrgeneralSubtree = new GeneralSubtree[aSN1Sequence.size()];
        for (int i = 0; i != arrgeneralSubtree.length; ++i) {
            arrgeneralSubtree[i] = GeneralSubtree.getInstance(aSN1Sequence.getObjectAt(i));
        }
        return arrgeneralSubtree;
    }

    public static NameConstraints getInstance(Object object) {
        if (object instanceof NameConstraints) {
            return (NameConstraints)object;
        }
        if (object != null) {
            return new NameConstraints(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public GeneralSubtree[] getExcludedSubtrees() {
        return NameConstraints.cloneSubtree(this.excluded);
    }

    public GeneralSubtree[] getPermittedSubtrees() {
        return NameConstraints.cloneSubtree(this.permitted);
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1Encodable[] arraSN1Encodable = this.permitted;
        if (arraSN1Encodable != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, new DERSequence(arraSN1Encodable)));
        }
        if ((arraSN1Encodable = this.excluded) != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, new DERSequence(arraSN1Encodable)));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

