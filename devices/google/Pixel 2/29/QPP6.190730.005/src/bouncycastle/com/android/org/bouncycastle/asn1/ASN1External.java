/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERExternal;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import java.io.IOException;

public abstract class ASN1External
extends ASN1Primitive {
    protected ASN1Primitive dataValueDescriptor;
    protected ASN1ObjectIdentifier directReference;
    protected int encoding;
    protected ASN1Primitive externalContent;
    protected ASN1Integer indirectReference;

    public ASN1External(ASN1EncodableVector object) {
        ASN1Primitive aSN1Primitive;
        int n = 0;
        ASN1Primitive aSN1Primitive2 = aSN1Primitive = this.getObjFromVector((ASN1EncodableVector)object, 0);
        if (aSN1Primitive instanceof ASN1ObjectIdentifier) {
            this.directReference = (ASN1ObjectIdentifier)aSN1Primitive;
            n = 0 + 1;
            aSN1Primitive2 = this.getObjFromVector((ASN1EncodableVector)object, n);
        }
        int n2 = n;
        aSN1Primitive = aSN1Primitive2;
        if (aSN1Primitive2 instanceof ASN1Integer) {
            this.indirectReference = (ASN1Integer)aSN1Primitive2;
            n2 = n + 1;
            aSN1Primitive = this.getObjFromVector((ASN1EncodableVector)object, n2);
        }
        n = n2;
        aSN1Primitive2 = aSN1Primitive;
        if (!(aSN1Primitive instanceof ASN1TaggedObject)) {
            this.dataValueDescriptor = aSN1Primitive;
            n = n2 + 1;
            aSN1Primitive2 = this.getObjFromVector((ASN1EncodableVector)object, n);
        }
        if (((ASN1EncodableVector)object).size() == n + 1) {
            if (aSN1Primitive2 instanceof ASN1TaggedObject) {
                object = (ASN1TaggedObject)aSN1Primitive2;
                this.setEncoding(((ASN1TaggedObject)object).getTagNo());
                this.externalContent = ((ASN1TaggedObject)object).getObject();
                return;
            }
            throw new IllegalArgumentException("No tagged object found in vector. Structure doesn't seem to be of type External");
        }
        throw new IllegalArgumentException("input vector too large");
    }

    public ASN1External(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Integer aSN1Integer, ASN1Primitive aSN1Primitive, int n, ASN1Primitive aSN1Primitive2) {
        this.setDirectReference(aSN1ObjectIdentifier);
        this.setIndirectReference(aSN1Integer);
        this.setDataValueDescriptor(aSN1Primitive);
        this.setEncoding(n);
        this.setExternalContent(aSN1Primitive2.toASN1Primitive());
    }

    public ASN1External(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Integer aSN1Integer, ASN1Primitive aSN1Primitive, DERTaggedObject dERTaggedObject) {
        this(aSN1ObjectIdentifier, aSN1Integer, aSN1Primitive, dERTaggedObject.getTagNo(), dERTaggedObject.toASN1Primitive());
    }

    private ASN1Primitive getObjFromVector(ASN1EncodableVector aSN1EncodableVector, int n) {
        if (aSN1EncodableVector.size() > n) {
            return aSN1EncodableVector.get(n).toASN1Primitive();
        }
        throw new IllegalArgumentException("too few objects in input vector");
    }

    private void setDataValueDescriptor(ASN1Primitive aSN1Primitive) {
        this.dataValueDescriptor = aSN1Primitive;
    }

    private void setDirectReference(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.directReference = aSN1ObjectIdentifier;
    }

    private void setEncoding(int n) {
        if (n >= 0 && n <= 2) {
            this.encoding = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid encoding value: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void setExternalContent(ASN1Primitive aSN1Primitive) {
        this.externalContent = aSN1Primitive;
    }

    private void setIndirectReference(ASN1Integer aSN1Integer) {
        this.indirectReference = aSN1Integer;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        ASN1Primitive aSN1Primitive2;
        if (!(aSN1Primitive instanceof ASN1External)) {
            return false;
        }
        if (this == aSN1Primitive) {
            return true;
        }
        aSN1Primitive = (ASN1External)aSN1Primitive;
        ASN1Primitive aSN1Primitive3 = this.directReference;
        if (!(aSN1Primitive3 == null || (aSN1Primitive2 = ((ASN1External)aSN1Primitive).directReference) != null && aSN1Primitive2.equals(aSN1Primitive3))) {
            return false;
        }
        aSN1Primitive3 = this.indirectReference;
        if (!(aSN1Primitive3 == null || (aSN1Primitive2 = ((ASN1External)aSN1Primitive).indirectReference) != null && aSN1Primitive2.equals(aSN1Primitive3))) {
            return false;
        }
        aSN1Primitive2 = this.dataValueDescriptor;
        if (!(aSN1Primitive2 == null || (aSN1Primitive3 = ((ASN1External)aSN1Primitive).dataValueDescriptor) != null && aSN1Primitive3.equals(aSN1Primitive2))) {
            return false;
        }
        return this.externalContent.equals(((ASN1External)aSN1Primitive).externalContent);
    }

    @Override
    int encodedLength() throws IOException {
        return this.getEncoded().length;
    }

    public ASN1Primitive getDataValueDescriptor() {
        return this.dataValueDescriptor;
    }

    public ASN1ObjectIdentifier getDirectReference() {
        return this.directReference;
    }

    public int getEncoding() {
        return this.encoding;
    }

    public ASN1Primitive getExternalContent() {
        return this.externalContent;
    }

    public ASN1Integer getIndirectReference() {
        return this.indirectReference;
    }

    @Override
    public int hashCode() {
        int n = 0;
        ASN1Primitive aSN1Primitive = this.directReference;
        if (aSN1Primitive != null) {
            n = ((ASN1ObjectIdentifier)aSN1Primitive).hashCode();
        }
        aSN1Primitive = this.indirectReference;
        int n2 = n;
        if (aSN1Primitive != null) {
            n2 = n ^ ((ASN1Integer)aSN1Primitive).hashCode();
        }
        aSN1Primitive = this.dataValueDescriptor;
        n = n2;
        if (aSN1Primitive != null) {
            n = n2 ^ aSN1Primitive.hashCode();
        }
        return n ^ this.externalContent.hashCode();
    }

    @Override
    boolean isConstructed() {
        return true;
    }

    @Override
    ASN1Primitive toDERObject() {
        if (this instanceof DERExternal) {
            return this;
        }
        return new DERExternal(this.directReference, this.indirectReference, this.dataValueDescriptor, this.encoding, this.externalContent);
    }
}

