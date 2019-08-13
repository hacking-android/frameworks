/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.cms;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CMSAlgorithmProtection
extends ASN1Object {
    public static final int MAC = 2;
    public static final int SIGNATURE = 1;
    private final AlgorithmIdentifier digestAlgorithm;
    private final AlgorithmIdentifier macAlgorithm;
    private final AlgorithmIdentifier signatureAlgorithm;

    private CMSAlgorithmProtection(ASN1Sequence object) {
        block2 : {
            ASN1TaggedObject aSN1TaggedObject;
            block5 : {
                block4 : {
                    block3 : {
                        if (((ASN1Sequence)object).size() != 2) break block2;
                        this.digestAlgorithm = AlgorithmIdentifier.getInstance(((ASN1Sequence)object).getObjectAt(0));
                        aSN1TaggedObject = ASN1TaggedObject.getInstance(((ASN1Sequence)object).getObjectAt(1));
                        if (aSN1TaggedObject.getTagNo() != 1) break block3;
                        this.signatureAlgorithm = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
                        this.macAlgorithm = null;
                        break block4;
                    }
                    if (aSN1TaggedObject.getTagNo() != 2) break block5;
                    this.signatureAlgorithm = null;
                    this.macAlgorithm = AlgorithmIdentifier.getInstance(aSN1TaggedObject, false);
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown tag found: ");
            ((StringBuilder)object).append(aSN1TaggedObject.getTagNo());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("Sequence wrong size: One of signatureAlgorithm or macAlgorithm must be present");
    }

    public CMSAlgorithmProtection(AlgorithmIdentifier object, int n, AlgorithmIdentifier algorithmIdentifier) {
        block2 : {
            block5 : {
                block4 : {
                    block3 : {
                        if (object == null || algorithmIdentifier == null) break block2;
                        this.digestAlgorithm = object;
                        if (n != 1) break block3;
                        this.signatureAlgorithm = algorithmIdentifier;
                        this.macAlgorithm = null;
                        break block4;
                    }
                    if (n != 2) break block5;
                    this.signatureAlgorithm = null;
                    this.macAlgorithm = algorithmIdentifier;
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown type: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new NullPointerException("AlgorithmIdentifiers cannot be null");
    }

    public static CMSAlgorithmProtection getInstance(Object object) {
        if (object instanceof CMSAlgorithmProtection) {
            return (CMSAlgorithmProtection)object;
        }
        if (object != null) {
            return new CMSAlgorithmProtection(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public AlgorithmIdentifier getDigestAlgorithm() {
        return this.digestAlgorithm;
    }

    public AlgorithmIdentifier getMacAlgorithm() {
        return this.macAlgorithm;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.digestAlgorithm);
        AlgorithmIdentifier algorithmIdentifier = this.signatureAlgorithm;
        if (algorithmIdentifier != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, algorithmIdentifier));
        }
        if ((algorithmIdentifier = this.macAlgorithm) != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, algorithmIdentifier));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

