/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1BitString;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.util.BigIntegers;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;

public class PrivateKeyInfo
extends ASN1Object {
    private ASN1Set attributes;
    private ASN1OctetString privateKey;
    private AlgorithmIdentifier privateKeyAlgorithm;
    private ASN1BitString publicKey;
    private ASN1Integer version;

    private PrivateKeyInfo(ASN1Sequence aSN1Primitive) {
        Enumeration enumeration = ((ASN1Sequence)aSN1Primitive).getObjects();
        this.version = ASN1Integer.getInstance(enumeration.nextElement());
        int n = PrivateKeyInfo.getVersionValue(this.version);
        this.privateKeyAlgorithm = AlgorithmIdentifier.getInstance(enumeration.nextElement());
        this.privateKey = ASN1OctetString.getInstance(enumeration.nextElement());
        int n2 = -1;
        while (enumeration.hasMoreElements()) {
            aSN1Primitive = (ASN1TaggedObject)enumeration.nextElement();
            int n3 = ((ASN1TaggedObject)aSN1Primitive).getTagNo();
            if (n3 > n2) {
                n2 = n3;
                if (n3 != 0) {
                    if (n3 == 1) {
                        if (n >= 1) {
                            this.publicKey = DERBitString.getInstance((ASN1TaggedObject)aSN1Primitive, false);
                            continue;
                        }
                        throw new IllegalArgumentException("'publicKey' requires version v2(1) or later");
                    }
                    throw new IllegalArgumentException("unknown optional field in private key info");
                }
                this.attributes = ASN1Set.getInstance((ASN1TaggedObject)aSN1Primitive, false);
                continue;
            }
            throw new IllegalArgumentException("invalid optional field in private key info");
        }
    }

    public PrivateKeyInfo(AlgorithmIdentifier algorithmIdentifier, ASN1Encodable aSN1Encodable) throws IOException {
        this(algorithmIdentifier, aSN1Encodable, null, null);
    }

    public PrivateKeyInfo(AlgorithmIdentifier algorithmIdentifier, ASN1Encodable aSN1Encodable, ASN1Set aSN1Set) throws IOException {
        this(algorithmIdentifier, aSN1Encodable, aSN1Set, null);
    }

    public PrivateKeyInfo(AlgorithmIdentifier aSN1Object, ASN1Encodable aSN1Encodable, ASN1Set aSN1Set, byte[] arrby) throws IOException {
        BigInteger bigInteger = arrby != null ? BigIntegers.ONE : BigIntegers.ZERO;
        this.version = new ASN1Integer(bigInteger);
        this.privateKeyAlgorithm = aSN1Object;
        this.privateKey = new DEROctetString(aSN1Encodable);
        this.attributes = aSN1Set;
        aSN1Object = arrby == null ? null : new DERBitString(arrby);
        this.publicKey = aSN1Object;
    }

    public static PrivateKeyInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return PrivateKeyInfo.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static PrivateKeyInfo getInstance(Object object) {
        if (object instanceof PrivateKeyInfo) {
            return (PrivateKeyInfo)object;
        }
        if (object != null) {
            return new PrivateKeyInfo(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    private static int getVersionValue(ASN1Integer object) {
        if (((BigInteger)(object = ((ASN1Integer)object).getValue())).compareTo(BigIntegers.ZERO) >= 0 && ((BigInteger)object).compareTo(BigIntegers.ONE) <= 0) {
            return ((BigInteger)object).intValue();
        }
        throw new IllegalArgumentException("invalid version for private key info");
    }

    public ASN1Set getAttributes() {
        return this.attributes;
    }

    public AlgorithmIdentifier getPrivateKeyAlgorithm() {
        return this.privateKeyAlgorithm;
    }

    public ASN1BitString getPublicKeyData() {
        return this.publicKey;
    }

    public boolean hasPublicKey() {
        boolean bl = this.publicKey != null;
        return bl;
    }

    public ASN1Encodable parsePrivateKey() throws IOException {
        return ASN1Primitive.fromByteArray(this.privateKey.getOctets());
    }

    public ASN1Encodable parsePublicKey() throws IOException {
        ASN1Primitive aSN1Primitive = this.publicKey;
        aSN1Primitive = aSN1Primitive == null ? null : ASN1Primitive.fromByteArray(aSN1Primitive.getOctets());
        return aSN1Primitive;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.version);
        aSN1EncodableVector.add(this.privateKeyAlgorithm);
        aSN1EncodableVector.add(this.privateKey);
        ASN1Primitive aSN1Primitive = this.attributes;
        if (aSN1Primitive != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 0, aSN1Primitive));
        }
        if ((aSN1Primitive = this.publicKey) != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, aSN1Primitive));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

