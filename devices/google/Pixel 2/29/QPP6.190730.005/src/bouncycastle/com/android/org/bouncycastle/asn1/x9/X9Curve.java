/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x9;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x9.X9FieldElement;
import com.android.org.bouncycastle.asn1.x9.X9FieldID;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

public class X9Curve
extends ASN1Object
implements X9ObjectIdentifiers {
    private ECCurve curve;
    private ASN1ObjectIdentifier fieldIdentifier;
    private byte[] seed;

    public X9Curve(X9FieldID aSN1Object, BigInteger bigInteger, BigInteger bigInteger2, ASN1Sequence aSN1Sequence) {
        block6 : {
            block9 : {
                block5 : {
                    int n;
                    int n2;
                    int n3;
                    int n4;
                    block8 : {
                        ASN1ObjectIdentifier aSN1ObjectIdentifier;
                        block7 : {
                            block4 : {
                                this.fieldIdentifier = null;
                                this.fieldIdentifier = ((X9FieldID)aSN1Object).getIdentifier();
                                if (!this.fieldIdentifier.equals(prime_field)) break block4;
                                this.curve = new ECCurve.Fp(((ASN1Integer)((X9FieldID)aSN1Object).getParameters()).getValue(), new BigInteger(1, ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets()), new BigInteger(1, ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets()), bigInteger, bigInteger2);
                                break block5;
                            }
                            if (!this.fieldIdentifier.equals(characteristic_two_field)) break block6;
                            aSN1Object = ASN1Sequence.getInstance(((X9FieldID)aSN1Object).getParameters());
                            n4 = ((ASN1Integer)((ASN1Sequence)aSN1Object).getObjectAt(0)).getValue().intValue();
                            aSN1ObjectIdentifier = (ASN1ObjectIdentifier)((ASN1Sequence)aSN1Object).getObjectAt(1);
                            if (!aSN1ObjectIdentifier.equals(tpBasis)) break block7;
                            n3 = ASN1Integer.getInstance(((ASN1Sequence)aSN1Object).getObjectAt(2)).getValue().intValue();
                            n = 0;
                            n2 = 0;
                            break block8;
                        }
                        if (!aSN1ObjectIdentifier.equals(ppBasis)) break block9;
                        aSN1Object = ASN1Sequence.getInstance(((ASN1Sequence)aSN1Object).getObjectAt(2));
                        n3 = ASN1Integer.getInstance(((ASN1Sequence)aSN1Object).getObjectAt(0)).getValue().intValue();
                        n = ASN1Integer.getInstance(((ASN1Sequence)aSN1Object).getObjectAt(1)).getValue().intValue();
                        n2 = ASN1Integer.getInstance(((ASN1Sequence)aSN1Object).getObjectAt(2)).getValue().intValue();
                    }
                    this.curve = new ECCurve.F2m(n4, n3, n, n2, new BigInteger(1, ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(0)).getOctets()), new BigInteger(1, ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1)).getOctets()), bigInteger, bigInteger2);
                }
                if (aSN1Sequence.size() == 3) {
                    this.seed = Arrays.clone(((DERBitString)aSN1Sequence.getObjectAt(2)).getBytes());
                }
                return;
            }
            throw new IllegalArgumentException("This type of EC basis is not implemented");
        }
        throw new IllegalArgumentException("This type of ECCurve is not implemented");
    }

    public X9Curve(ECCurve eCCurve) {
        this(eCCurve, null);
    }

    public X9Curve(ECCurve eCCurve, byte[] arrby) {
        this.fieldIdentifier = null;
        this.curve = eCCurve;
        this.seed = Arrays.clone(arrby);
        this.setFieldIdentifier();
    }

    private void setFieldIdentifier() {
        block4 : {
            block3 : {
                block2 : {
                    if (!ECAlgorithms.isFpCurve(this.curve)) break block2;
                    this.fieldIdentifier = prime_field;
                    break block3;
                }
                if (!ECAlgorithms.isF2mCurve(this.curve)) break block4;
                this.fieldIdentifier = characteristic_two_field;
            }
            return;
        }
        throw new IllegalArgumentException("This type of ECCurve is not implemented");
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public byte[] getSeed() {
        return Arrays.clone(this.seed);
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.fieldIdentifier.equals(prime_field)) {
            aSN1EncodableVector.add(new X9FieldElement(this.curve.getA()).toASN1Primitive());
            aSN1EncodableVector.add(new X9FieldElement(this.curve.getB()).toASN1Primitive());
        } else if (this.fieldIdentifier.equals(characteristic_two_field)) {
            aSN1EncodableVector.add(new X9FieldElement(this.curve.getA()).toASN1Primitive());
            aSN1EncodableVector.add(new X9FieldElement(this.curve.getB()).toASN1Primitive());
        }
        byte[] arrby = this.seed;
        if (arrby != null) {
            aSN1EncodableVector.add(new DERBitString(arrby));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

