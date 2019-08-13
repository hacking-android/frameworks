/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.AbstractECMultiplier;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.LongArray;
import com.android.org.bouncycastle.math.ec.WNafUtil;
import java.math.BigInteger;

public class WNafL2RMultiplier
extends AbstractECMultiplier {
    protected int getWindowSize(int n) {
        return WNafUtil.getWindowSize(n);
    }

    @Override
    protected ECPoint multiplyPositive(ECPoint object, BigInteger arreCPoint) {
        int n;
        int n2;
        int n3 = Math.max(2, Math.min(16, this.getWindowSize(arreCPoint.bitLength())));
        ECPoint[] arreCPoint2 = WNafUtil.precompute((ECPoint)object, n3, true);
        ECPoint[] arreCPoint3 = arreCPoint2.getPreComp();
        arreCPoint2 = arreCPoint2.getPreCompNeg();
        int[] arrn = WNafUtil.generateCompactWindowNaf(n3, (BigInteger)arreCPoint);
        object = ((ECPoint)object).getCurve().getInfinity();
        int n4 = arrn.length;
        if (n4 > 1) {
            n = n4 - 1;
            n4 = arrn[n];
            int n5 = n4 >> 16;
            n4 &= 65535;
            n2 = Math.abs(n5);
            object = n5 < 0 ? arreCPoint2 : arreCPoint3;
            if (n2 << 2 < 1 << n3) {
                byte by = LongArray.bitLengths[n2];
                n5 = n3 - by;
                object = ((ECPoint)object[(1 << n3 - 1) - 1 >>> 1]).add((ECPoint)object[((n2 ^ 1 << by - 1) << n5) + 1 >>> 1]);
                n4 -= n5;
            } else {
                object = object[n2 >>> 1];
            }
            object = ((ECPoint)object).timesPow2(n4);
            n4 = n;
        }
        while (n4 > 0) {
            n2 = arrn[--n4];
            n3 = n2 >> 16;
            n = Math.abs(n3);
            arreCPoint = n3 < 0 ? arreCPoint2 : arreCPoint3;
            object = ((ECPoint)object).twicePlus(arreCPoint[n >>> 1]).timesPow2(n2 & 65535);
        }
        return object;
    }
}

