/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECLookupTable;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.FixedPointPreCompInfo;
import com.android.org.bouncycastle.math.ec.PreCompCallback;
import com.android.org.bouncycastle.math.ec.PreCompInfo;
import java.math.BigInteger;

public class FixedPointUtil {
    public static final String PRECOMP_NAME = "bc_fixed_point";

    public static int getCombSize(ECCurve eCCurve) {
        BigInteger bigInteger = eCCurve.getOrder();
        int n = bigInteger == null ? eCCurve.getFieldSize() + 1 : bigInteger.bitLength();
        return n;
    }

    public static FixedPointPreCompInfo getFixedPointPreCompInfo(PreCompInfo preCompInfo) {
        preCompInfo = preCompInfo instanceof FixedPointPreCompInfo ? (FixedPointPreCompInfo)preCompInfo : null;
        return preCompInfo;
    }

    public static FixedPointPreCompInfo precompute(final ECPoint eCPoint) {
        final ECCurve eCCurve = eCPoint.getCurve();
        return (FixedPointPreCompInfo)eCCurve.precompute(eCPoint, PRECOMP_NAME, new PreCompCallback(){

            private boolean checkExisting(FixedPointPreCompInfo fixedPointPreCompInfo, int n) {
                boolean bl = fixedPointPreCompInfo != null && this.checkTable(fixedPointPreCompInfo.getLookupTable(), n);
                return bl;
            }

            private boolean checkTable(ECLookupTable eCLookupTable, int n) {
                boolean bl = eCLookupTable != null && eCLookupTable.getSize() >= n;
                return bl;
            }

            @Override
            public PreCompInfo precompute(PreCompInfo object) {
                int n;
                Object object2;
                int n2;
                int n3;
                if (this.checkExisting((FixedPointPreCompInfo)(object = object instanceof FixedPointPreCompInfo ? (FixedPointPreCompInfo)object : null), n = 1 << (n3 = (n2 = FixedPointUtil.getCombSize(eCCurve)) > 250 ? 6 : 5))) {
                    return object;
                }
                int n4 = (n2 + n3 - 1) / n3;
                ECPoint[] arreCPoint = new ECPoint[n3 + 1];
                arreCPoint[0] = eCPoint;
                for (n2 = 1; n2 < n3; ++n2) {
                    arreCPoint[n2] = arreCPoint[n2 - 1].timesPow2(n4);
                }
                arreCPoint[n3] = arreCPoint[0].subtract(arreCPoint[1]);
                eCCurve.normalizeAll(arreCPoint);
                object = new ECPoint[n];
                object[0] = arreCPoint[0];
                for (n2 = n3 - 1; n2 >= 0; --n2) {
                    int n5;
                    object2 = arreCPoint[n2];
                    for (n4 = n5 = 1 << n2; n4 < n; n4 += n5 << 1) {
                        object[n4] = object[n4 - n5].add((ECPoint)object2);
                    }
                }
                eCCurve.normalizeAll((ECPoint[])object);
                object2 = new FixedPointPreCompInfo();
                ((FixedPointPreCompInfo)object2).setLookupTable(eCCurve.createCacheSafeLookupTable((ECPoint[])object, 0, ((ECPoint[])object).length));
                ((FixedPointPreCompInfo)object2).setOffset(arreCPoint[n3]);
                ((FixedPointPreCompInfo)object2).setWidth(n3);
                return object2;
            }
        });
    }

}

