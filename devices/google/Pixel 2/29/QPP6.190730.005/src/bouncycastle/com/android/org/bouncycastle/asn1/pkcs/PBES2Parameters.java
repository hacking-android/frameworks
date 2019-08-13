/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.pkcs;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.pkcs.EncryptionScheme;
import com.android.org.bouncycastle.asn1.pkcs.KeyDerivationFunc;
import com.android.org.bouncycastle.asn1.pkcs.PBKDF2Params;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import java.util.Enumeration;

public class PBES2Parameters
extends ASN1Object
implements PKCSObjectIdentifiers {
    private KeyDerivationFunc func;
    private EncryptionScheme scheme;

    private PBES2Parameters(ASN1Sequence object) {
        object = ((ASN1Sequence)object).getObjects();
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(((ASN1Encodable)object.nextElement()).toASN1Primitive());
        this.func = aSN1Sequence.getObjectAt(0).equals(id_PBKDF2) ? new KeyDerivationFunc(id_PBKDF2, PBKDF2Params.getInstance(aSN1Sequence.getObjectAt(1))) : KeyDerivationFunc.getInstance(aSN1Sequence);
        this.scheme = EncryptionScheme.getInstance(object.nextElement());
    }

    public PBES2Parameters(KeyDerivationFunc keyDerivationFunc, EncryptionScheme encryptionScheme) {
        this.func = keyDerivationFunc;
        this.scheme = encryptionScheme;
    }

    public static PBES2Parameters getInstance(Object object) {
        if (object instanceof PBES2Parameters) {
            return (PBES2Parameters)object;
        }
        if (object != null) {
            return new PBES2Parameters(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public EncryptionScheme getEncryptionScheme() {
        return this.scheme;
    }

    public KeyDerivationFunc getKeyDerivationFunc() {
        return this.func;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.func);
        aSN1EncodableVector.add(this.scheme);
        return new DERSequence(aSN1EncodableVector);
    }
}

