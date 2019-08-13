/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.spec;

import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.field.FiniteField;
import com.android.org.bouncycastle.math.field.Polynomial;
import com.android.org.bouncycastle.math.field.PolynomialExtensionField;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;

public class ECNamedCurveSpec
extends ECParameterSpec {
    private String name;

    public ECNamedCurveSpec(String string, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        super(ECNamedCurveSpec.convertCurve(eCCurve, null), EC5Util.convertPoint(eCPoint), bigInteger, 1);
        this.name = string;
    }

    public ECNamedCurveSpec(String string, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        super(ECNamedCurveSpec.convertCurve(eCCurve, null), EC5Util.convertPoint(eCPoint), bigInteger, bigInteger2.intValue());
        this.name = string;
    }

    public ECNamedCurveSpec(String string, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] arrby) {
        super(ECNamedCurveSpec.convertCurve(eCCurve, arrby), EC5Util.convertPoint(eCPoint), bigInteger, bigInteger2.intValue());
        this.name = string;
    }

    public ECNamedCurveSpec(String string, EllipticCurve ellipticCurve, java.security.spec.ECPoint eCPoint, BigInteger bigInteger) {
        super(ellipticCurve, eCPoint, bigInteger, 1);
        this.name = string;
    }

    public ECNamedCurveSpec(String string, EllipticCurve ellipticCurve, java.security.spec.ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        super(ellipticCurve, eCPoint, bigInteger, bigInteger2.intValue());
        this.name = string;
    }

    private static EllipticCurve convertCurve(ECCurve eCCurve, byte[] arrby) {
        return new EllipticCurve(ECNamedCurveSpec.convertField(eCCurve.getField()), eCCurve.getA().toBigInteger(), eCCurve.getB().toBigInteger(), arrby);
    }

    private static ECField convertField(FiniteField object) {
        if (ECAlgorithms.isFpField((FiniteField)object)) {
            return new ECFieldFp(object.getCharacteristic());
        }
        object = ((PolynomialExtensionField)object).getMinimalPolynomial();
        int[] arrn = object.getExponentsPresent();
        arrn = Arrays.reverse(Arrays.copyOfRange(arrn, 1, arrn.length - 1));
        return new ECFieldF2m(object.getDegree(), arrn);
    }

    public String getName() {
        return this.name;
    }
}

