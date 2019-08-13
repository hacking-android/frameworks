/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.Enumeration;
import java.util.Vector;

public class ASN1EncodableVector {
    private final Vector v = new Vector();

    @UnsupportedAppUsage
    public void add(ASN1Encodable aSN1Encodable) {
        this.v.addElement(aSN1Encodable);
    }

    public void addAll(ASN1EncodableVector object) {
        object = ((ASN1EncodableVector)object).v.elements();
        while (object.hasMoreElements()) {
            this.v.addElement(object.nextElement());
        }
    }

    public ASN1Encodable get(int n) {
        return (ASN1Encodable)this.v.elementAt(n);
    }

    public int size() {
        return this.v.size();
    }
}

