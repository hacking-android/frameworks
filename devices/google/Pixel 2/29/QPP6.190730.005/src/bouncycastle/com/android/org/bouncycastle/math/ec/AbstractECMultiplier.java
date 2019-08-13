/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECMultiplier;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;

public abstract class AbstractECMultiplier
implements ECMultiplier {
    protected ECPoint checkResult(ECPoint eCPoint) {
        return ECAlgorithms.implCheckResult(eCPoint);
    }

    @Override
    public ECPoint multiply(ECPoint eCPoint, BigInteger bigInteger) {
        int n = bigInteger.signum();
        if (n != 0 && !eCPoint.isInfinity()) {
            eCPoint = this.multiplyPositive(eCPoint, bigInteger.abs());
            if (n <= 0) {
                eCPoint = eCPoint.negate();
            }
            return this.checkResult(eCPoint);
        }
        return eCPoint.getCurve().getInfinity();
    }

    protected abstract ECPoint multiplyPositive(ECPoint var1, BigInteger var2);
}

