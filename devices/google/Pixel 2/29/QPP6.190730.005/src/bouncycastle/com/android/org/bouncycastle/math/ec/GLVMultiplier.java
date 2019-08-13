/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.AbstractECMultiplier;
import com.android.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.ECPointMap;
import com.android.org.bouncycastle.math.ec.endo.GLVEndomorphism;
import java.math.BigInteger;

public class GLVMultiplier
extends AbstractECMultiplier {
    protected final ECCurve curve;
    protected final GLVEndomorphism glvEndomorphism;

    public GLVMultiplier(ECCurve eCCurve, GLVEndomorphism gLVEndomorphism) {
        if (eCCurve != null && eCCurve.getOrder() != null) {
            this.curve = eCCurve;
            this.glvEndomorphism = gLVEndomorphism;
            return;
        }
        throw new IllegalArgumentException("Need curve with known group order");
    }

    @Override
    protected ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        if (this.curve.equals(eCPoint.getCurve())) {
            Object object = eCPoint.getCurve().getOrder();
            object = this.glvEndomorphism.decomposeScalar(bigInteger.mod((BigInteger)object));
            bigInteger = object[0];
            object = object[1];
            ECPointMap eCPointMap = this.glvEndomorphism.getPointMap();
            if (this.glvEndomorphism.hasEfficientPointMap()) {
                return ECAlgorithms.implShamirsTrickWNaf(eCPoint, bigInteger, eCPointMap, (BigInteger)object);
            }
            return ECAlgorithms.implShamirsTrickWNaf(eCPoint, bigInteger, eCPointMap.map(eCPoint), (BigInteger)object);
        }
        throw new IllegalStateException();
    }
}

