/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.Extension;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class Extensions
extends ASN1Object {
    private Hashtable extensions = new Hashtable();
    private Vector ordering = new Vector();

    private Extensions(ASN1Sequence aSN1Object) {
        Object object = ((ASN1Sequence)aSN1Object).getObjects();
        while (object.hasMoreElements()) {
            aSN1Object = Extension.getInstance(object.nextElement());
            if (!this.extensions.containsKey(((Extension)aSN1Object).getExtnId())) {
                this.extensions.put(((Extension)aSN1Object).getExtnId(), aSN1Object);
                this.ordering.addElement(((Extension)aSN1Object).getExtnId());
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("repeated extension found: ");
            ((StringBuilder)object).append(((Extension)aSN1Object).getExtnId());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

    public Extensions(Extension extension) {
        this.ordering.addElement(extension.getExtnId());
        this.extensions.put(extension.getExtnId(), extension);
    }

    public Extensions(Extension[] arrextension) {
        for (int i = 0; i != arrextension.length; ++i) {
            Extension extension = arrextension[i];
            this.ordering.addElement(extension.getExtnId());
            this.extensions.put(extension.getExtnId(), extension);
        }
    }

    private ASN1ObjectIdentifier[] getExtensionOIDs(boolean bl) {
        Vector vector = new Vector();
        for (int i = 0; i != this.ordering.size(); ++i) {
            Object e = this.ordering.elementAt(i);
            if (((Extension)this.extensions.get(e)).isCritical() != bl) continue;
            vector.addElement(e);
        }
        return this.toOidArray(vector);
    }

    public static Extensions getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return Extensions.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static Extensions getInstance(Object object) {
        if (object instanceof Extensions) {
            return (Extensions)object;
        }
        if (object != null) {
            return new Extensions(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    private ASN1ObjectIdentifier[] toOidArray(Vector vector) {
        ASN1ObjectIdentifier[] arraSN1ObjectIdentifier = new ASN1ObjectIdentifier[vector.size()];
        for (int i = 0; i != arraSN1ObjectIdentifier.length; ++i) {
            arraSN1ObjectIdentifier[i] = (ASN1ObjectIdentifier)vector.elementAt(i);
        }
        return arraSN1ObjectIdentifier;
    }

    public boolean equivalent(Extensions extensions) {
        if (this.extensions.size() != extensions.extensions.size()) {
            return false;
        }
        Enumeration enumeration = this.extensions.keys();
        while (enumeration.hasMoreElements()) {
            Object k = enumeration.nextElement();
            if (this.extensions.get(k).equals(extensions.extensions.get(k))) continue;
            return false;
        }
        return true;
    }

    public ASN1ObjectIdentifier[] getCriticalExtensionOIDs() {
        return this.getExtensionOIDs(true);
    }

    public Extension getExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (Extension)this.extensions.get(aSN1ObjectIdentifier);
    }

    public ASN1ObjectIdentifier[] getExtensionOIDs() {
        return this.toOidArray(this.ordering);
    }

    public ASN1Encodable getExtensionParsedValue(ASN1ObjectIdentifier aSN1Object) {
        if ((aSN1Object = this.getExtension((ASN1ObjectIdentifier)aSN1Object)) != null) {
            return ((Extension)aSN1Object).getParsedValue();
        }
        return null;
    }

    public ASN1ObjectIdentifier[] getNonCriticalExtensionOIDs() {
        return this.getExtensionOIDs(false);
    }

    public Enumeration oids() {
        return this.ordering.elements();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Enumeration enumeration = this.ordering.elements();
        while (enumeration.hasMoreElements()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)enumeration.nextElement();
            aSN1EncodableVector.add((Extension)this.extensions.get(aSN1ObjectIdentifier));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

