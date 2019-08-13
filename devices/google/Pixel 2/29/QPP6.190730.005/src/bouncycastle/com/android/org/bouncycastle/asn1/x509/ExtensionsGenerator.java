/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class ExtensionsGenerator {
    private Vector extOrdering = new Vector();
    private Hashtable extensions = new Hashtable();

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, ASN1Encodable aSN1Encodable) throws IOException {
        this.addExtension(aSN1ObjectIdentifier, bl, aSN1Encodable.toASN1Primitive().getEncoded("DER"));
    }

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, byte[] object) {
        if (!this.extensions.containsKey(aSN1ObjectIdentifier)) {
            this.extOrdering.addElement(aSN1ObjectIdentifier);
            this.extensions.put(aSN1ObjectIdentifier, new Extension(aSN1ObjectIdentifier, bl, (ASN1OctetString)new DEROctetString((byte[])object)));
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("extension ");
        ((StringBuilder)object).append(aSN1ObjectIdentifier);
        ((StringBuilder)object).append(" already added");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public void addExtension(Extension extension) {
        if (!this.extensions.containsKey(extension.getExtnId())) {
            this.extOrdering.addElement(extension.getExtnId());
            this.extensions.put(extension.getExtnId(), extension);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("extension ");
        stringBuilder.append(extension.getExtnId());
        stringBuilder.append(" already added");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Extensions generate() {
        Extension[] arrextension = new Extension[this.extOrdering.size()];
        for (int i = 0; i != this.extOrdering.size(); ++i) {
            arrextension[i] = (Extension)this.extensions.get(this.extOrdering.elementAt(i));
        }
        return new Extensions(arrextension);
    }

    public boolean isEmpty() {
        return this.extOrdering.isEmpty();
    }

    public void reset() {
        this.extensions = new Hashtable();
        this.extOrdering = new Vector();
    }
}

