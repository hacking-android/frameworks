/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.x509.X509Extension;
import com.android.org.bouncycastle.asn1.x509.X509Extensions;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class X509ExtensionsGenerator {
    private Vector extOrdering = new Vector();
    private Hashtable extensions = new Hashtable();

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, ASN1Encodable object) {
        try {
            this.addExtension(aSN1ObjectIdentifier, bl, object.toASN1Primitive().getEncoded("DER"));
            return;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("error encoding value: ");
            ((StringBuilder)object).append(iOException);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, byte[] object) {
        if (!this.extensions.containsKey(aSN1ObjectIdentifier)) {
            this.extOrdering.addElement(aSN1ObjectIdentifier);
            this.extensions.put(aSN1ObjectIdentifier, new X509Extension(bl, (ASN1OctetString)new DEROctetString((byte[])object)));
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("extension ");
        ((StringBuilder)object).append(aSN1ObjectIdentifier);
        ((StringBuilder)object).append(" already added");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public X509Extensions generate() {
        return new X509Extensions(this.extOrdering, this.extensions);
    }

    public boolean isEmpty() {
        return this.extOrdering.isEmpty();
    }

    public void reset() {
        this.extensions = new Hashtable();
        this.extOrdering = new Vector();
    }
}

