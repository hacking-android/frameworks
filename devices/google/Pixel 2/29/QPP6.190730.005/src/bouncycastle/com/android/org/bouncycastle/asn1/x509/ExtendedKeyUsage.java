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
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.KeyPurposeId;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class ExtendedKeyUsage
extends ASN1Object {
    ASN1Sequence seq;
    Hashtable usageTable = new Hashtable();

    private ExtendedKeyUsage(ASN1Sequence object) {
        this.seq = object;
        object = ((ASN1Sequence)object).getObjects();
        while (object.hasMoreElements()) {
            ASN1Encodable aSN1Encodable = (ASN1Encodable)object.nextElement();
            if (aSN1Encodable.toASN1Primitive() instanceof ASN1ObjectIdentifier) {
                this.usageTable.put(aSN1Encodable, aSN1Encodable);
                continue;
            }
            throw new IllegalArgumentException("Only ASN1ObjectIdentifiers allowed in ExtendedKeyUsage.");
        }
    }

    public ExtendedKeyUsage(KeyPurposeId keyPurposeId) {
        this.seq = new DERSequence(keyPurposeId);
        this.usageTable.put(keyPurposeId, keyPurposeId);
    }

    public ExtendedKeyUsage(Vector object) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        object = ((Vector)object).elements();
        while (object.hasMoreElements()) {
            KeyPurposeId keyPurposeId = KeyPurposeId.getInstance(object.nextElement());
            aSN1EncodableVector.add(keyPurposeId);
            this.usageTable.put(keyPurposeId, keyPurposeId);
        }
        this.seq = new DERSequence(aSN1EncodableVector);
    }

    public ExtendedKeyUsage(KeyPurposeId[] arrkeyPurposeId) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != arrkeyPurposeId.length; ++i) {
            aSN1EncodableVector.add(arrkeyPurposeId[i]);
            this.usageTable.put(arrkeyPurposeId[i], arrkeyPurposeId[i]);
        }
        this.seq = new DERSequence(aSN1EncodableVector);
    }

    public static ExtendedKeyUsage fromExtensions(Extensions extensions) {
        return ExtendedKeyUsage.getInstance(extensions.getExtensionParsedValue(Extension.extendedKeyUsage));
    }

    public static ExtendedKeyUsage getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return ExtendedKeyUsage.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static ExtendedKeyUsage getInstance(Object object) {
        if (object instanceof ExtendedKeyUsage) {
            return (ExtendedKeyUsage)object;
        }
        if (object != null) {
            return new ExtendedKeyUsage(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public KeyPurposeId[] getUsages() {
        KeyPurposeId[] arrkeyPurposeId = new KeyPurposeId[this.seq.size()];
        int n = 0;
        Enumeration enumeration = this.seq.getObjects();
        while (enumeration.hasMoreElements()) {
            arrkeyPurposeId[n] = KeyPurposeId.getInstance(enumeration.nextElement());
            ++n;
        }
        return arrkeyPurposeId;
    }

    public boolean hasKeyPurposeId(KeyPurposeId keyPurposeId) {
        boolean bl = this.usageTable.get(keyPurposeId) != null;
        return bl;
    }

    public int size() {
        return this.usageTable.size();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return this.seq;
    }
}

