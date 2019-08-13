/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.spec;

import com.android.org.bouncycastle.jce.spec.ECParameterSpec;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;

public class ECNamedCurveParameterSpec
extends ECParameterSpec {
    private String name;

    public ECNamedCurveParameterSpec(String string, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger) {
        super(eCCurve, eCPoint, bigInteger);
        this.name = string;
    }

    public ECNamedCurveParameterSpec(String string, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2) {
        super(eCCurve, eCPoint, bigInteger, bigInteger2);
        this.name = string;
    }

    public ECNamedCurveParameterSpec(String string, ECCurve eCCurve, ECPoint eCPoint, BigInteger bigInteger, BigInteger bigInteger2, byte[] arrby) {
        super(eCCurve, eCPoint, bigInteger, bigInteger2, arrby);
        this.name = string;
    }

    public String getName() {
        return this.name;
    }
}

