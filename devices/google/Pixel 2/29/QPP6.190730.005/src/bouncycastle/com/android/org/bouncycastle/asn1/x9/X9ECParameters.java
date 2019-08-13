/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x9;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x9.X9Curve;
import com.android.org.bouncycastle.asn1.x9.X9ECPoint;
import com.android.org.bouncycastle.asn1.x9.X9FieldID;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.field.FiniteField;
import com.android.org.bouncycastle.math.field.Polynomial;
import com.android.org.bouncycastle.math.field.PolynomialExtensionField;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

public class X9ECParameters
extends ASN1Object
implements X9ObjectIdentifiers {
    private static final BigInteger ONE = BigInteger.valueOf(1L);
    private ECCurve curve;
    private X9FieldID fieldID;
    private X9ECPoint g;
    private BigInteger h;
    private BigInteger n;
    private byte[] seed;

    private X9ECParameters(ASN1Sequence aSN1Encodable) {
        if (aSN1Encodable.getObjectAt(0) instanceof ASN1Integer && ((ASN1Integer)aSN1Encodable.getObjectAt(0)).getValue().equals(ONE)) {
            this.n = ((ASN1Integer)aSN1Encodable.getObjectAt(4)).getValue();
            if (aSN1Encodable.size() == 6) {
                this.h = ((ASN1Integer)aSN1Encodable.getObjectAt(5)).getValue();
            }
            X9Curve x9Curve = new X9Curve(X9FieldID.getInstance(aSN1Encodable.getObjectAt(1)), this.n, this.h, ASN1Sequence.getInstance(aSN1Encodable.getObjectAt(2)));
            this.curve = x9Curve.getCurve();
            this.g = (aSN1Encodable = aSN1Encodable.getObjectAt(3)) instanceof X9ECPoint ? (X9ECPoint)aSN1Encodable : new X9ECPoint(this.curve, (ASN1OctetString)aSN1Encodable);
            this.seed = x9Curve.getSeed();
            return;
        }
        throw new IllegalArgumentException("bad version in X9ECParameters");
    }

    public X9ECParameters(ECCurve eCCurve, X9ECPoint x9ECPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this(eCCurve, x9ECPoint, bigInteger, bigInteger2, null);
    }

    public X9ECParameters(ECCurve arrn, X9ECPoint x9ECPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] arrby) {
        block4 : {
            block6 : {
                block3 : {
                    block5 : {
                        block2 : {
                            this.curve = arrn;
                            this.g = x9ECPoint;
                            this.n = bigInteger;
                            this.h = bigInteger2;
                            this.seed = Arrays.clone(arrby);
                            if (!ECAlgorithms.isFpCurve((ECCurve)arrn)) break block2;
                            this.fieldID = new X9FieldID(arrn.getField().getCharacteristic());
                            break block3;
                        }
                        if (!ECAlgorithms.isF2mCurve((ECCurve)arrn)) break block4;
                        if ((arrn = ((PolynomialExtensionField)arrn.getField()).getMinimalPolynomial().getExponentsPresent()).length != 3) break block5;
                        this.fieldID = new X9FieldID(arrn[2], arrn[1]);
                        break block3;
                    }
                    if (arrn.length != 5) break block6;
                    this.fieldID = new X9FieldID(arrn[4], arrn[1], arrn[2], arrn[3]);
                }
                return;
            }
            throw new IllegalArgumentException("Only trinomial and pentomial curves are supported");
        }
        throw new IllegalArgumentException("'curve' is of an unsupported type");
    }

    public X9ECParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        this(eCCurve, eCPoint, bigInteger, null, null);
    }

    public X9ECParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        this(eCCurve, eCPoint, bigInteger, bigInteger2, null);
    }

    public X9ECParameters(ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] arrby) {
        this(eCCurve, new X9ECPoint(eCPoint), bigInteger, bigInteger2, arrby);
    }

    public static X9ECParameters getInstance(Object object) {
        if (object instanceof X9ECParameters) {
            return (X9ECParameters)object;
        }
        if (object != null) {
            return new X9ECParameters(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public X9ECPoint getBaseEntry() {
        return this.g;
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public X9Curve getCurveEntry() {
        return new X9Curve(this.curve, this.seed);
    }

    public X9FieldID getFieldIDEntry() {
        return this.fieldID;
    }

    public ECPoint getG() {
        return this.g.getPoint();
    }

    public BigInteger getH() {
        return this.h;
    }

    public BigInteger getN() {
        return this.n;
    }

    public byte[] getSeed() {
        return Arrays.clone(this.seed);
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(ONE));
        aSN1EncodableVector.add(this.fieldID);
        aSN1EncodableVector.add(new X9Curve(this.curve, this.seed));
        aSN1EncodableVector.add(this.g);
        aSN1EncodableVector.add(new ASN1Integer(this.n));
        BigInteger bigInteger = this.h;
        if (bigInteger != null) {
            aSN1EncodableVector.add(new ASN1Integer(bigInteger));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

