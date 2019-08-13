/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.util.Arrays;

public class SubjectKeyIdentifier
extends ASN1Object {
    private byte[] keyidentifier;

    protected SubjectKeyIdentifier(ASN1OctetString aSN1OctetString) {
        this(aSN1OctetString.getOctets());
    }

    public SubjectKeyIdentifier(byte[] arrby) {
        this.keyidentifier = Arrays.clone(arrby);
    }

    public static SubjectKeyIdentifier fromExtensions(Extensions extensions) {
        return SubjectKeyIdentifier.getInstance(extensions.getExtensionParsedValue(Extension.subjectKeyIdentifier));
    }

    public static SubjectKeyIdentifier getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return SubjectKeyIdentifier.getInstance(ASN1OctetString.getInstance(aSN1TaggedObject, bl));
    }

    public static SubjectKeyIdentifier getInstance(Object object) {
        if (object instanceof SubjectKeyIdentifier) {
            return (SubjectKeyIdentifier)object;
        }
        if (object != null) {
            return new SubjectKeyIdentifier(ASN1OctetString.getInstance(object));
        }
        return null;
    }

    public byte[] getKeyIdentifier() {
        return Arrays.clone(this.keyidentifier);
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return new DEROctetString(this.getKeyIdentifier());
    }
}

