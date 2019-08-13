/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.AbstractECMultiplier;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.PreCompCallback;
import com.android.org.bouncycastle.math.ec.PreCompInfo;
import com.android.org.bouncycastle.math.ec.Tnaf;
import com.android.org.bouncycastle.math.ec.WTauNafPreCompInfo;
import com.android.org.bouncycastle.math.ec.ZTauElement;
import java.math.BigInteger;

public class WTauNafMultiplier
extends AbstractECMultiplier {
    static final String PRECOMP_NAME = "bc_wtnaf";

    private static ECPoint.AbstractF2m multiplyFromWTnaf(ECPoint.AbstractF2m object, byte[] object2) {
        int n;
        Object object3 = (ECCurve.AbstractF2m)((ECPoint)object).getCurve();
        ECPoint.AbstractF2m[] arrabstractF2m = ((WTauNafPreCompInfo)((ECCurve)object3).precompute((ECPoint)object, PRECOMP_NAME, new PreCompCallback((ECPoint.AbstractF2m)object, ((ECCurve)object3).getA().toBigInteger().byteValue()){
            final /* synthetic */ byte val$a;
            final /* synthetic */ ECPoint.AbstractF2m val$p;
            {
                this.val$p = abstractF2m;
                this.val$a = by;
            }

            @Override
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                if (preCompInfo instanceof WTauNafPreCompInfo) {
                    return preCompInfo;
                }
                preCompInfo = new WTauNafPreCompInfo();
                ((WTauNafPreCompInfo)preCompInfo).setPreComp(Tnaf.getPreComp(this.val$p, this.val$a));
                return preCompInfo;
            }
        })).getPreComp();
        ECPoint.AbstractF2m[] arrabstractF2m2 = new ECPoint.AbstractF2m[arrabstractF2m.length];
        for (n = 0; n < arrabstractF2m.length; ++n) {
            arrabstractF2m2[n] = (ECPoint.AbstractF2m)arrabstractF2m[n].negate();
        }
        object = (ECPoint.AbstractF2m)((ECPoint)object).getCurve().getInfinity();
        n = 0;
        for (int i = ((byte[])object2).length - 1; i >= 0; --i) {
            int n2 = n + 1;
            byte by = object2[i];
            object3 = object;
            n = n2;
            if (by != 0) {
                object3 = ((ECPoint.AbstractF2m)object).tauPow(n2);
                n = 0;
                object = by > 0 ? arrabstractF2m[by >>> 1] : arrabstractF2m2[-by >>> 1];
                object3 = (ECPoint.AbstractF2m)((ECPoint)object3).add((ECPoint)object);
            }
            object = object3;
        }
        object2 = object;
        if (n > 0) {
            object2 = ((ECPoint.AbstractF2m)object).tauPow(n);
        }
        return object2;
    }

    private ECPoint.AbstractF2m multiplyWTnaf(ECPoint.AbstractF2m abstractF2m, ZTauElement zTauElement, byte by, byte by2) {
        ZTauElement[] arrzTauElement = by == 0 ? Tnaf.alpha0 : Tnaf.alpha1;
        BigInteger bigInteger = Tnaf.getTw(by2, 4);
        return WTauNafMultiplier.multiplyFromWTnaf(abstractF2m, Tnaf.tauAdicWNaf(by2, zTauElement, (byte)4, BigInteger.valueOf(16L), bigInteger, arrzTauElement));
    }

    @Override
    protected ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        if (eCPoint instanceof ECPoint.AbstractF2m) {
            eCPoint = (ECPoint.AbstractF2m)eCPoint;
            ECCurve.AbstractF2m abstractF2m = (ECCurve.AbstractF2m)eCPoint.getCurve();
            int n = abstractF2m.getFieldSize();
            byte by = abstractF2m.getA().toBigInteger().byteValue();
            byte by2 = Tnaf.getMu(by);
            return this.multiplyWTnaf((ECPoint.AbstractF2m)eCPoint, Tnaf.partModReduction(bigInteger, n, by, abstractF2m.getSi(), by2, (byte)10), by, by2);
        }
        throw new IllegalArgumentException("Only ECPoint.AbstractF2m can be used in WTauNafMultiplier");
    }

}

