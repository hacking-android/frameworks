/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.ECConstants;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.ECPointMap;
import com.android.org.bouncycastle.math.ec.WNafPreCompInfo;
import com.android.org.bouncycastle.math.ec.WNafUtil;
import com.android.org.bouncycastle.math.ec.endo.ECEndomorphism;
import com.android.org.bouncycastle.math.ec.endo.GLVEndomorphism;
import com.android.org.bouncycastle.math.field.FiniteField;
import com.android.org.bouncycastle.math.field.PolynomialExtensionField;
import java.math.BigInteger;

public class ECAlgorithms {
    public static ECPoint cleanPoint(ECCurve eCCurve, ECPoint eCPoint) {
        if (eCCurve.equals(eCPoint.getCurve())) {
            return eCCurve.decodePoint(eCPoint.getEncoded(false));
        }
        throw new IllegalArgumentException("Point must be on the same curve");
    }

    static ECPoint implCheckResult(ECPoint eCPoint) {
        if (eCPoint.isValidPartial()) {
            return eCPoint;
        }
        throw new IllegalStateException("Invalid result");
    }

    static ECPoint implShamirsTrickJsf(ECPoint eCPoint, BigInteger arrby, ECPoint eCPoint2, BigInteger bigInteger) {
        Object object = eCPoint.getCurve();
        ECPoint eCPoint3 = ((ECCurve)object).getInfinity();
        ECPoint eCPoint4 = eCPoint.add(eCPoint2);
        ECPoint eCPoint5 = eCPoint.subtract(eCPoint2);
        Object object2 = new ECPoint[]{eCPoint2, eCPoint5, eCPoint, eCPoint4};
        ((ECCurve)object).normalizeAll((ECPoint[])object2);
        eCPoint5 = object2[3].negate();
        ECPoint eCPoint6 = object2[2].negate();
        eCPoint4 = object2[1].negate();
        object = object2[0].negate();
        ECPoint eCPoint7 = object2[0];
        ECPoint eCPoint8 = object2[1];
        eCPoint2 = object2[2];
        object2 = object2[3];
        arrby = WNafUtil.generateJSF((BigInteger)arrby, bigInteger);
        eCPoint = eCPoint3;
        int n = arrby.length;
        while (--n >= 0) {
            byte by = arrby[n];
            eCPoint = eCPoint.twicePlus(new ECPoint[]{eCPoint5, eCPoint6, eCPoint4, object, eCPoint3, eCPoint7, eCPoint8, eCPoint2, object2}[(by << 24 >> 28) * 3 + 4 + (by << 28 >> 28)]);
        }
        return eCPoint;
    }

    static ECPoint implShamirsTrickWNaf(ECPoint arreCPoint, BigInteger arreCPoint2, ECPoint arreCPoint3, BigInteger arreCPoint4) {
        int n = arreCPoint2.signum();
        boolean bl = false;
        n = n < 0 ? 1 : 0;
        if (arreCPoint4.signum() < 0) {
            bl = true;
        }
        BigInteger bigInteger = arreCPoint2.abs();
        BigInteger bigInteger2 = arreCPoint4.abs();
        int n2 = Math.max(2, Math.min(16, WNafUtil.getWindowSize(bigInteger.bitLength())));
        int n3 = Math.max(2, Math.min(16, WNafUtil.getWindowSize(bigInteger2.bitLength())));
        WNafPreCompInfo wNafPreCompInfo = WNafUtil.precompute((ECPoint)arreCPoint, n2, true);
        arreCPoint4 = WNafUtil.precompute((ECPoint)arreCPoint3, n3, true);
        arreCPoint = n != 0 ? wNafPreCompInfo.getPreCompNeg() : wNafPreCompInfo.getPreComp();
        arreCPoint2 = bl ? arreCPoint4.getPreCompNeg() : arreCPoint4.getPreComp();
        arreCPoint3 = n != 0 ? wNafPreCompInfo.getPreComp() : wNafPreCompInfo.getPreCompNeg();
        arreCPoint4 = bl ? arreCPoint4.getPreComp() : arreCPoint4.getPreCompNeg();
        return ECAlgorithms.implShamirsTrickWNaf(arreCPoint, arreCPoint3, WNafUtil.generateWindowNaf(n2, bigInteger), arreCPoint2, arreCPoint4, WNafUtil.generateWindowNaf(n3, bigInteger2));
    }

    static ECPoint implShamirsTrickWNaf(ECPoint arreCPoint, BigInteger arreCPoint2, ECPointMap arreCPoint3, BigInteger arreCPoint4) {
        int n = arreCPoint2.signum();
        boolean bl = false;
        n = n < 0 ? 1 : 0;
        if (arreCPoint4.signum() < 0) {
            bl = true;
        }
        BigInteger bigInteger = arreCPoint2.abs();
        BigInteger bigInteger2 = arreCPoint4.abs();
        int n2 = Math.max(2, Math.min(16, WNafUtil.getWindowSize(Math.max(bigInteger.bitLength(), bigInteger2.bitLength()))));
        arreCPoint2 = WNafUtil.mapPointWithPrecomp((ECPoint)arreCPoint, n2, true, (ECPointMap)arreCPoint3);
        arreCPoint3 = WNafUtil.getWNafPreCompInfo((ECPoint)arreCPoint);
        arreCPoint4 = WNafUtil.getWNafPreCompInfo((ECPoint)arreCPoint2);
        arreCPoint = n != 0 ? arreCPoint3.getPreCompNeg() : arreCPoint3.getPreComp();
        arreCPoint2 = bl ? arreCPoint4.getPreCompNeg() : arreCPoint4.getPreComp();
        arreCPoint3 = n != 0 ? arreCPoint3.getPreComp() : arreCPoint3.getPreCompNeg();
        arreCPoint4 = bl ? arreCPoint4.getPreComp() : arreCPoint4.getPreCompNeg();
        return ECAlgorithms.implShamirsTrickWNaf(arreCPoint, arreCPoint3, WNafUtil.generateWindowNaf(n2, bigInteger), arreCPoint2, arreCPoint4, WNafUtil.generateWindowNaf(n2, bigInteger2));
    }

    private static ECPoint implShamirsTrickWNaf(ECPoint[] object, ECPoint[] arreCPoint, byte[] arrby, ECPoint[] arreCPoint2, ECPoint[] arreCPoint3, byte[] arrby2) {
        ECPoint[] arreCPoint4;
        int n = Math.max(arrby.length, arrby2.length);
        Object object2 = arreCPoint4 = object[0].getCurve().getInfinity();
        int n2 = 0;
        for (int i = n - 1; i >= 0; --i) {
            Object object3;
            byte by;
            n = i < arrby.length ? arrby[i] : 0;
            if ((n | (by = i < arrby2.length ? arrby2[i] : (byte)0)) == 0) {
                ++n2;
                continue;
            }
            Object object4 = object3 = arreCPoint4;
            if (n != 0) {
                int n3 = Math.abs(n);
                object4 = n < 0 ? arreCPoint : object;
                object4 = object3.add(object4[n3 >>> 1]);
            }
            object3 = object4;
            if (by != 0) {
                n = Math.abs(by);
                object3 = by < 0 ? arreCPoint3 : arreCPoint2;
                object3 = object4.add(object3[n >>> 1]);
            }
            object4 = object2;
            n = n2;
            if (n2 > 0) {
                object4 = object2.timesPow2(n2);
                n = 0;
            }
            object2 = object4.twicePlus((ECPoint)object3);
            n2 = n;
        }
        object = object2;
        if (n2 > 0) {
            object = object2.timesPow2(n2);
        }
        return object;
    }

    static ECPoint implSumOfMultiplies(ECPoint[] arreCPoint, ECPointMap eCPointMap, BigInteger[] arrbigInteger) {
        int n = arreCPoint.length;
        int n2 = n << 1;
        boolean[] arrbl = new boolean[n2];
        WNafPreCompInfo[] arrwNafPreCompInfo = new WNafPreCompInfo[n2];
        byte[][] arrarrby = new byte[n2][];
        for (n2 = 0; n2 < n; ++n2) {
            int n3 = n2 << 1;
            int n4 = n3 + 1;
            BigInteger bigInteger = arrbigInteger[n3];
            int n5 = bigInteger.signum();
            boolean bl = false;
            boolean bl2 = n5 < 0;
            arrbl[n3] = bl2;
            bigInteger = bigInteger.abs();
            Object object = arrbigInteger[n4];
            bl2 = bl;
            if (((BigInteger)object).signum() < 0) {
                bl2 = true;
            }
            arrbl[n4] = bl2;
            BigInteger bigInteger2 = ((BigInteger)object).abs();
            n5 = Math.max(2, Math.min(16, WNafUtil.getWindowSize(Math.max(bigInteger.bitLength(), bigInteger2.bitLength()))));
            ECPoint eCPoint = arreCPoint[n2];
            object = WNafUtil.mapPointWithPrecomp(eCPoint, n5, true, eCPointMap);
            arrwNafPreCompInfo[n3] = WNafUtil.getWNafPreCompInfo(eCPoint);
            arrwNafPreCompInfo[n4] = WNafUtil.getWNafPreCompInfo((ECPoint)object);
            arrarrby[n3] = WNafUtil.generateWindowNaf(n5, bigInteger);
            arrarrby[n4] = WNafUtil.generateWindowNaf(n5, bigInteger2);
        }
        return ECAlgorithms.implSumOfMultiplies(arrbl, arrwNafPreCompInfo, arrarrby);
    }

    static ECPoint implSumOfMultiplies(ECPoint[] arreCPoint, BigInteger[] arrbigInteger) {
        int n = arreCPoint.length;
        boolean[] arrbl = new boolean[n];
        WNafPreCompInfo[] arrwNafPreCompInfo = new WNafPreCompInfo[n];
        byte[][] arrarrby = new byte[n][];
        for (int i = 0; i < n; ++i) {
            BigInteger bigInteger = arrbigInteger[i];
            boolean bl = bigInteger.signum() < 0;
            arrbl[i] = bl;
            bigInteger = bigInteger.abs();
            int n2 = Math.max(2, Math.min(16, WNafUtil.getWindowSize(bigInteger.bitLength())));
            arrwNafPreCompInfo[i] = WNafUtil.precompute(arreCPoint[i], n2, true);
            arrarrby[i] = WNafUtil.generateWindowNaf(n2, bigInteger);
        }
        return ECAlgorithms.implSumOfMultiplies(arrbl, arrwNafPreCompInfo, arrarrby);
    }

    private static ECPoint implSumOfMultiplies(boolean[] object, WNafPreCompInfo[] arrwNafPreCompInfo, byte[][] arrby) {
        ECPoint eCPoint;
        int n;
        int n2 = 0;
        int n3 = arrby.length;
        for (n = 0; n < n3; ++n) {
            n2 = Math.max(n2, arrby[n].length);
        }
        ECPoint eCPoint2 = eCPoint = arrwNafPreCompInfo[0].getPreComp()[0].getCurve().getInfinity();
        n = 0;
        int n4 = n2 - 1;
        n2 = n;
        while (n4 >= 0) {
            Object object2;
            Object object3 = eCPoint;
            for (n = 0; n < n3; ++n) {
                object2 = arrby[n];
                byte by = n4 < ((byte[])object2).length ? object2[n4] : (byte)0;
                object2 = object3;
                if (by != 0) {
                    int n5 = Math.abs(by);
                    object2 = arrwNafPreCompInfo[n];
                    boolean bl = by < 0;
                    object2 = bl == object[n] ? ((WNafPreCompInfo)object2).getPreComp() : ((WNafPreCompInfo)object2).getPreCompNeg();
                    object2 = ((ECPoint)object3).add((ECPoint)object2[n5 >>> 1]);
                }
                object3 = object2;
            }
            if (object3 == eCPoint) {
                ++n2;
            } else {
                object2 = eCPoint2;
                n = n2;
                if (n2 > 0) {
                    object2 = eCPoint2.timesPow2(n2);
                    n = 0;
                }
                eCPoint2 = ((ECPoint)object2).twicePlus((ECPoint)object3);
                n2 = n;
            }
            --n4;
        }
        object = eCPoint2;
        if (n2 > 0) {
            object = eCPoint2.timesPow2(n2);
        }
        return object;
    }

    static ECPoint implSumOfMultipliesGLV(ECPoint[] arreCPoint, BigInteger[] object, GLVEndomorphism object2) {
        Object[] arrobject;
        int n;
        int n2;
        Object object3 = arreCPoint[0].getCurve().getOrder();
        int n3 = arreCPoint.length;
        BigInteger[] arrbigInteger = new BigInteger[n3 << 1];
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            arrobject = object2.decomposeScalar(object[n2].mod((BigInteger)object3));
            n = n4 + 1;
            arrbigInteger[n4] = arrobject[0];
            n4 = n + 1;
            arrbigInteger[n] = arrobject[1];
        }
        object = object2.getPointMap();
        if (object2.hasEfficientPointMap()) {
            return ECAlgorithms.implSumOfMultiplies(arreCPoint, (ECPointMap)object, arrbigInteger);
        }
        arrobject = new ECPoint[n3 << 1];
        n2 = 0;
        for (n4 = 0; n4 < n3; ++n4) {
            object2 = arreCPoint[n4];
            object3 = object.map((ECPoint)object2);
            n = n2 + 1;
            arrobject[n2] = object2;
            n2 = n + 1;
            arrobject[n] = object3;
        }
        return ECAlgorithms.implSumOfMultiplies((ECPoint[])arrobject, arrbigInteger);
    }

    public static ECPoint importPoint(ECCurve eCCurve, ECPoint eCPoint) {
        if (eCCurve.equals(eCPoint.getCurve())) {
            return eCCurve.importPoint(eCPoint);
        }
        throw new IllegalArgumentException("Point must be on the same curve");
    }

    public static boolean isF2mCurve(ECCurve eCCurve) {
        return ECAlgorithms.isF2mField(eCCurve.getField());
    }

    public static boolean isF2mField(FiniteField finiteField) {
        int n = finiteField.getDimension();
        boolean bl = true;
        if (n <= 1 || !finiteField.getCharacteristic().equals(ECConstants.TWO) || !(finiteField instanceof PolynomialExtensionField)) {
            bl = false;
        }
        return bl;
    }

    public static boolean isFpCurve(ECCurve eCCurve) {
        return ECAlgorithms.isFpField(eCCurve.getField());
    }

    public static boolean isFpField(FiniteField finiteField) {
        int n = finiteField.getDimension();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public static void montgomeryTrick(ECFieldElement[] arreCFieldElement, int n, int n2) {
        ECAlgorithms.montgomeryTrick(arreCFieldElement, n, n2, null);
    }

    public static void montgomeryTrick(ECFieldElement[] arreCFieldElement, int n, int n2, ECFieldElement eCFieldElement) {
        ECFieldElement[] arreCFieldElement2 = new ECFieldElement[n2];
        arreCFieldElement2[0] = arreCFieldElement[n];
        int n3 = 0;
        while (++n3 < n2) {
            arreCFieldElement2[n3] = arreCFieldElement2[n3 - 1].multiply(arreCFieldElement[n + n3]);
        }
        n2 = n3 - 1;
        if (eCFieldElement != null) {
            arreCFieldElement2[n2] = arreCFieldElement2[n2].multiply(eCFieldElement);
        }
        eCFieldElement = arreCFieldElement2[n2].invert();
        while (n2 > 0) {
            n3 = n2 - 1;
            ECFieldElement eCFieldElement2 = arreCFieldElement[n2 += n];
            arreCFieldElement[n2] = arreCFieldElement2[n3].multiply(eCFieldElement);
            eCFieldElement = eCFieldElement.multiply(eCFieldElement2);
            n2 = n3;
        }
        arreCFieldElement[n] = eCFieldElement;
    }

    public static ECPoint referenceMultiply(ECPoint eCPoint, BigInteger bigInteger) {
        BigInteger bigInteger2 = bigInteger.abs();
        ECPoint eCPoint2 = eCPoint.getCurve().getInfinity();
        int n = bigInteger2.bitLength();
        ECPoint eCPoint3 = eCPoint2;
        if (n > 0) {
            eCPoint3 = eCPoint2;
            if (bigInteger2.testBit(0)) {
                eCPoint3 = eCPoint;
            }
            int n2 = 1;
            eCPoint2 = eCPoint;
            eCPoint = eCPoint3;
            do {
                eCPoint3 = eCPoint;
                if (n2 >= n) break;
                eCPoint2 = eCPoint2.twice();
                eCPoint3 = eCPoint;
                if (bigInteger2.testBit(n2)) {
                    eCPoint3 = eCPoint.add(eCPoint2);
                }
                ++n2;
                eCPoint = eCPoint3;
            } while (true);
        }
        eCPoint = bigInteger.signum() < 0 ? eCPoint3.negate() : eCPoint3;
        return eCPoint;
    }

    public static ECPoint shamirsTrick(ECPoint eCPoint, BigInteger bigInteger, ECPoint eCPoint2, BigInteger bigInteger2) {
        return ECAlgorithms.implCheckResult(ECAlgorithms.implShamirsTrickJsf(eCPoint, bigInteger, ECAlgorithms.importPoint(eCPoint.getCurve(), eCPoint2), bigInteger2));
    }

    public static ECPoint sumOfMultiplies(ECPoint[] object, BigInteger[] arrbigInteger) {
        if (object != null && arrbigInteger != null && ((ECPoint[])object).length == arrbigInteger.length && ((ECPoint[])object).length >= 1) {
            int n = ((ECPoint[])object).length;
            if (n != 1) {
                if (n != 2) {
                    ECPoint eCPoint = object[0];
                    ECCurve eCCurve = eCPoint.getCurve();
                    ECPoint[] arreCPoint = new ECPoint[n];
                    arreCPoint[0] = eCPoint;
                    for (int i = 1; i < n; ++i) {
                        arreCPoint[i] = ECAlgorithms.importPoint(eCCurve, object[i]);
                    }
                    object = eCCurve.getEndomorphism();
                    if (object instanceof GLVEndomorphism) {
                        return ECAlgorithms.implCheckResult(ECAlgorithms.implSumOfMultipliesGLV(arreCPoint, arrbigInteger, (GLVEndomorphism)object));
                    }
                    return ECAlgorithms.implCheckResult(ECAlgorithms.implSumOfMultiplies(arreCPoint, arrbigInteger));
                }
                return ECAlgorithms.sumOfTwoMultiplies(object[0], arrbigInteger[0], object[1], arrbigInteger[1]);
            }
            return object[0].multiply(arrbigInteger[0]);
        }
        throw new IllegalArgumentException("point and scalar arrays should be non-null, and of equal, non-zero, length");
    }

    public static ECPoint sumOfTwoMultiplies(ECPoint eCPoint, BigInteger bigInteger, ECPoint eCPoint2, BigInteger bigInteger2) {
        Object object = eCPoint.getCurve();
        eCPoint2 = ECAlgorithms.importPoint((ECCurve)object, eCPoint2);
        if (object instanceof ECCurve.AbstractF2m && ((ECCurve.AbstractF2m)object).isKoblitz()) {
            return ECAlgorithms.implCheckResult(eCPoint.multiply(bigInteger).add(eCPoint2.multiply(bigInteger2)));
        }
        if ((object = ((ECCurve)object).getEndomorphism()) instanceof GLVEndomorphism) {
            object = (GLVEndomorphism)object;
            return ECAlgorithms.implCheckResult(ECAlgorithms.implSumOfMultipliesGLV(new ECPoint[]{eCPoint, eCPoint2}, new BigInteger[]{bigInteger, bigInteger2}, (GLVEndomorphism)object));
        }
        return ECAlgorithms.implCheckResult(ECAlgorithms.implShamirsTrickWNaf(eCPoint, bigInteger, eCPoint2, bigInteger2));
    }

    public static ECPoint validatePoint(ECPoint eCPoint) {
        if (eCPoint.isValid()) {
            return eCPoint;
        }
        throw new IllegalStateException("Invalid point");
    }
}

