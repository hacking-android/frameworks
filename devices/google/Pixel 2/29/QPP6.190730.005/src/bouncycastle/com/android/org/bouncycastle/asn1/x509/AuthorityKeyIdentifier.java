/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.x509.Extension;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.GeneralNames;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;
import java.util.Enumeration;

public class AuthorityKeyIdentifier
extends ASN1Object {
    GeneralNames certissuer;
    ASN1Integer certserno;
    ASN1OctetString keyidentifier;

    protected AuthorityKeyIdentifier(ASN1Sequence object) {
        this.keyidentifier = null;
        this.certissuer = null;
        this.certserno = null;
        object = ((ASN1Sequence)object).getObjects();
        while (object.hasMoreElements()) {
            ASN1TaggedObject aSN1TaggedObject = DERTaggedObject.getInstance(object.nextElement());
            int n = aSN1TaggedObject.getTagNo();
            if (n != 0) {
                if (n != 1) {
                    if (n == 2) {
                        this.certserno = ASN1Integer.getInstance(aSN1TaggedObject, false);
                        continue;
                    }
                    throw new IllegalArgumentException("illegal tag");
                }
                this.certissuer = GeneralNames.getInstance(aSN1TaggedObject, false);
                continue;
            }
            this.keyidentifier = ASN1OctetString.getInstance(aSN1TaggedObject, false);
        }
    }

    public AuthorityKeyIdentifier(GeneralNames generalNames, BigInteger bigInteger) {
        this((byte[])null, generalNames, bigInteger);
    }

    public AuthorityKeyIdentifier(SubjectPublicKeyInfo arrby) {
        this.keyidentifier = null;
        this.certissuer = null;
        this.certserno = null;
        Digest digest = AndroidDigestFactory.getSHA1();
        byte[] arrby2 = new byte[digest.getDigestSize()];
        arrby = arrby.getPublicKeyData().getBytes();
        digest.update(arrby, 0, arrby.length);
        digest.doFinal(arrby2, 0);
        this.keyidentifier = new DEROctetString(arrby2);
    }

    public AuthorityKeyIdentifier(SubjectPublicKeyInfo arrby, GeneralNames generalNames, BigInteger bigInteger) {
        this.keyidentifier = null;
        this.certissuer = null;
        this.certserno = null;
        Digest digest = AndroidDigestFactory.getSHA1();
        byte[] arrby2 = new byte[digest.getDigestSize()];
        arrby = arrby.getPublicKeyData().getBytes();
        digest.update(arrby, 0, arrby.length);
        digest.doFinal(arrby2, 0);
        this.keyidentifier = new DEROctetString(arrby2);
        this.certissuer = GeneralNames.getInstance(generalNames.toASN1Primitive());
        this.certserno = new ASN1Integer(bigInteger);
    }

    public AuthorityKeyIdentifier(byte[] arrby) {
        this(arrby, null, null);
    }

    public AuthorityKeyIdentifier(byte[] object, GeneralNames generalNames, BigInteger bigInteger) {
        Object var4_4 = null;
        this.keyidentifier = null;
        this.certissuer = null;
        this.certserno = null;
        object = object != null ? new DEROctetString((byte[])object) : null;
        this.keyidentifier = object;
        this.certissuer = generalNames;
        object = var4_4;
        if (bigInteger != null) {
            object = new ASN1Integer(bigInteger);
        }
        this.certserno = object;
    }

    public static AuthorityKeyIdentifier fromExtensions(Extensions extensions) {
        return AuthorityKeyIdentifier.getInstance(extensions.getExtensionParsedValue(Extension.authorityKeyIdentifier));
    }

    public static AuthorityKeyIdentifier getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return AuthorityKeyIdentifier.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static AuthorityKeyIdentifier getInstance(Object object) {
        if (object instanceof AuthorityKeyIdentifier) {
            return (AuthorityKeyIdentifier)object;
        }
        if (object != null) {
            return new AuthorityKeyIdentifier(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public GeneralNames getAuthorityCertIssuer() {
        return this.certissuer;
    }

    public BigInteger getAuthorityCertSerialNumber() {
        ASN1Integer aSN1Integer = this.certserno;
        if (aSN1Integer != null) {
            return aSN1Integer.getValue();
        }
        return null;
    }

    public byte[] getKeyIdentifier() {
        ASN1OctetString aSN1OctetString = this.keyidentifier;
        if (aSN1OctetString != null) {
            return aSN1OctetString.getOctets();
        }
        return null;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1Object aSN1Object = this.keyidentifier;
        if (aSN1Object != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, aSN1Object));
        }
        if ((aSN1Object = this.certissuer) != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, aSN1Object));
        }
        if ((aSN1Object = this.certserno) != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, aSN1Object));
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AuthorityKeyIdentifier: KeyID(");
        Object object = this.keyidentifier;
        object = object != null ? Hex.toHexString(((ASN1OctetString)object).getOctets()) : "null";
        stringBuilder.append((String)object);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

