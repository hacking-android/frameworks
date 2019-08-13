/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.AbstractECMultiplier;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECLookupTable;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.FixedPointPreCompInfo;
import com.android.org.bouncycastle.math.ec.FixedPointUtil;
import com.android.org.bouncycastle.math.raw.Nat;
import java.math.BigInteger;

public class FixedPointCombMultiplier
extends AbstractECMultiplier {
    @Override
    protected ECPoint multiplyPositive(ECPoint eCPoint, BigInteger arrn) {
        ECCurve eCCurve = eCPoint.getCurve();
        int n = FixedPointUtil.getCombSize(eCCurve);
        if (arrn.bitLength() <= n) {
            FixedPointPreCompInfo fixedPointPreCompInfo = FixedPointUtil.precompute(eCPoint);
            ECLookupTable eCLookupTable = fixedPointPreCompInfo.getLookupTable();
            int n2 = fixedPointPreCompInfo.getWidth();
            int n3 = (n + n2 - 1) / n2;
            eCPoint = eCCurve.getInfinity();
            int n4 = n3 * n2;
            arrn = Nat.fromBigInteger(n4, (BigInteger)arrn);
            for (n2 = 0; n2 < n3; ++n2) {
                int n5 = 0;
                for (n = n4 - 1 - n2; n >= 0; n -= n3) {
                    int n6 = arrn[n >>> 5] >>> (n & 31);
                    n5 = (n5 ^ n6 >>> 1) << 1 ^ n6;
                }
                eCPoint = eCPoint.twicePlus(eCLookupTable.lookup(n5));
            }
            return eCPoint.add(fixedPointPreCompInfo.getOffset());
        }
        throw new IllegalStateException("fixed-point comb doesn't support scalars larger than the curve order");
    }
}

