/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.ECConstants;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.SimpleBigDecimal;
import com.android.org.bouncycastle.math.ec.ZTauElement;
import java.math.BigInteger;

class Tnaf {
    private static final BigInteger MINUS_ONE = ECConstants.ONE.negate();
    private static final BigInteger MINUS_THREE;
    private static final BigInteger MINUS_TWO;
    public static final byte POW_2_WIDTH = 16;
    public static final byte WIDTH = 4;
    public static final ZTauElement[] alpha0;
    public static final byte[][] alpha0Tnaf;
    public static final ZTauElement[] alpha1;
    public static final byte[][] alpha1Tnaf;

    static {
        MINUS_TWO = ECConstants.TWO.negate();
        MINUS_THREE = ECConstants.THREE.negate();
        byte[] arrby = new ZTauElement(ECConstants.ONE, ECConstants.ZERO);
        byte[] arrby2 = new ZTauElement(MINUS_THREE, MINUS_ONE);
        byte[] arrby3 = MINUS_ONE;
        alpha0 = new ZTauElement[]{null, arrby, null, arrby2, null, new ZTauElement((BigInteger)arrby3, (BigInteger)arrby3), null, new ZTauElement(ECConstants.ONE, MINUS_ONE), null};
        arrby = new byte[]{1, 0, 1};
        arrby2 = new byte[]{-1, 0, 0, 1};
        alpha0Tnaf = new byte[][]{null, {1}, null, {-1, 0, 1}, null, arrby, null, arrby2};
        alpha1 = new ZTauElement[]{null, new ZTauElement(ECConstants.ONE, ECConstants.ZERO), null, new ZTauElement(MINUS_THREE, ECConstants.ONE), null, new ZTauElement(MINUS_ONE, ECConstants.ONE), null, new ZTauElement(ECConstants.ONE, ECConstants.ONE), null};
        arrby = new byte[]{1};
        arrby2 = new byte[]{-1, 0, 1};
        arrby3 = new byte[]{-1, 0, 0, -1};
        alpha1Tnaf = new byte[][]{null, arrby, null, arrby2, null, {1, 0, 1}, null, arrby3};
    }

    Tnaf() {
    }

    public static SimpleBigDecimal approximateDivisionByN(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, byte by, int n, int n2) {
        int n3 = (n + 5) / 2 + n2;
        bigInteger = bigInteger2.multiply(bigInteger.shiftRight(n - n3 - 2 + by));
        bigInteger3 = bigInteger.add(bigInteger3.multiply(bigInteger.shiftRight(n)));
        bigInteger = bigInteger2 = bigInteger3.shiftRight(n3 - n2);
        if (bigInteger3.testBit(n3 - n2 - 1)) {
            bigInteger = bigInteger2.add(ECConstants.ONE);
        }
        return new SimpleBigDecimal(bigInteger, n2);
    }

    public static BigInteger[] getLucas(byte by, int n, boolean bl) {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        if (by != 1 && by != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        if (bl) {
            bigInteger2 = ECConstants.TWO;
            bigInteger = BigInteger.valueOf(by);
        } else {
            bigInteger2 = ECConstants.ZERO;
            bigInteger = ECConstants.ONE;
        }
        for (int i = 1; i < n; ++i) {
            BigInteger bigInteger3 = by == 1 ? bigInteger : bigInteger.negate();
            bigInteger3 = bigInteger3.subtract(bigInteger2.shiftLeft(1));
            bigInteger2 = bigInteger;
            bigInteger = bigInteger3;
        }
        return new BigInteger[]{bigInteger2, bigInteger};
    }

    public static byte getMu(int n) {
        n = n == 0 ? -1 : 1;
        return (byte)n;
    }

    public static byte getMu(ECCurve.AbstractF2m abstractF2m) {
        if (abstractF2m.isKoblitz()) {
            if (abstractF2m.getA().isZero()) {
                return -1;
            }
            return 1;
        }
        throw new IllegalArgumentException("No Koblitz curve (ABC), TNAF multiplication not possible");
    }

    public static byte getMu(ECFieldElement eCFieldElement) {
        int n = eCFieldElement.isZero() ? -1 : 1;
        return (byte)n;
    }

    public static ECPoint.AbstractF2m[] getPreComp(ECPoint.AbstractF2m abstractF2m, byte by) {
        byte[][] arrby = by == 0 ? alpha0Tnaf : alpha1Tnaf;
        ECPoint[] arreCPoint = new ECPoint.AbstractF2m[arrby.length + 1 >>> 1];
        arreCPoint[0] = abstractF2m;
        int n = arrby.length;
        for (by = (byte)3; by < n; by = (byte)(by + 2)) {
            arreCPoint[by >>> 1] = Tnaf.multiplyFromTnaf(abstractF2m, arrby[by]);
        }
        abstractF2m.getCurve().normalizeAll(arreCPoint);
        return arreCPoint;
    }

    protected static int getShiftsForCofactor(BigInteger bigInteger) {
        if (bigInteger != null) {
            if (bigInteger.equals(ECConstants.TWO)) {
                return 1;
            }
            if (bigInteger.equals(ECConstants.FOUR)) {
                return 2;
            }
        }
        throw new IllegalArgumentException("h (Cofactor) must be 2 or 4");
    }

    public static BigInteger[] getSi(int n, int n2, BigInteger arrbigInteger) {
        byte by = Tnaf.getMu(n2);
        int n3 = Tnaf.getShiftsForCofactor((BigInteger)arrbigInteger);
        arrbigInteger = Tnaf.getLucas(by, n + 3 - n2, false);
        if (by == 1) {
            arrbigInteger[0] = arrbigInteger[0].negate();
            arrbigInteger[1] = arrbigInteger[1].negate();
        }
        return new BigInteger[]{ECConstants.ONE.add(arrbigInteger[1]).shiftRight(n3), ECConstants.ONE.add(arrbigInteger[0]).shiftRight(n3).negate()};
    }

    public static BigInteger[] getSi(ECCurve.AbstractF2m arrbigInteger) {
        if (arrbigInteger.isKoblitz()) {
            int n = arrbigInteger.getFieldSize();
            int n2 = arrbigInteger.getA().toBigInteger().intValue();
            byte by = Tnaf.getMu(n2);
            int n3 = Tnaf.getShiftsForCofactor(arrbigInteger.getCofactor());
            arrbigInteger = Tnaf.getLucas(by, n + 3 - n2, false);
            if (by == 1) {
                arrbigInteger[0] = arrbigInteger[0].negate();
                arrbigInteger[1] = arrbigInteger[1].negate();
            }
            return new BigInteger[]{ECConstants.ONE.add(arrbigInteger[1]).shiftRight(n3), ECConstants.ONE.add(arrbigInteger[0]).shiftRight(n3).negate()};
        }
        throw new IllegalArgumentException("si is defined for Koblitz curves only");
    }

    public static BigInteger getTw(byte by, int n) {
        if (n == 4) {
            if (by == 1) {
                return BigInteger.valueOf(6L);
            }
            return BigInteger.valueOf(10L);
        }
        BigInteger[] arrbigInteger = Tnaf.getLucas(by, n, false);
        BigInteger bigInteger = ECConstants.ZERO.setBit(n);
        BigInteger bigInteger2 = arrbigInteger[1].modInverse(bigInteger);
        return ECConstants.TWO.multiply(arrbigInteger[0]).multiply(bigInteger2).mod(bigInteger);
    }

    public static ECPoint.AbstractF2m multiplyFromTnaf(ECPoint.AbstractF2m abstractF2m, byte[] arrby) {
        ECPoint.AbstractF2m abstractF2m2 = (ECPoint.AbstractF2m)abstractF2m.getCurve().getInfinity();
        ECPoint.AbstractF2m abstractF2m3 = (ECPoint.AbstractF2m)abstractF2m.negate();
        int n = 0;
        for (int i = arrby.length - 1; i >= 0; --i) {
            int n2 = n + 1;
            byte by = arrby[i];
            ECPoint.AbstractF2m abstractF2m4 = abstractF2m2;
            n = n2;
            if (by != 0) {
                abstractF2m4 = abstractF2m2.tauPow(n2);
                n = 0;
                abstractF2m2 = by > 0 ? abstractF2m : abstractF2m3;
                abstractF2m4 = (ECPoint.AbstractF2m)abstractF2m4.add(abstractF2m2);
            }
            abstractF2m2 = abstractF2m4;
        }
        abstractF2m = abstractF2m2;
        if (n > 0) {
            abstractF2m = abstractF2m2.tauPow(n);
        }
        return abstractF2m;
    }

    public static ECPoint.AbstractF2m multiplyRTnaf(ECPoint.AbstractF2m abstractF2m, BigInteger bigInteger) {
        BigInteger[] arrbigInteger = (BigInteger[])abstractF2m.getCurve();
        int n = arrbigInteger.getFieldSize();
        int n2 = arrbigInteger.getA().toBigInteger().intValue();
        byte by = Tnaf.getMu(n2);
        arrbigInteger = arrbigInteger.getSi();
        return Tnaf.multiplyTnaf(abstractF2m, Tnaf.partModReduction(bigInteger, n, (byte)n2, arrbigInteger, by, (byte)10));
    }

    public static ECPoint.AbstractF2m multiplyTnaf(ECPoint.AbstractF2m abstractF2m, ZTauElement zTauElement) {
        return Tnaf.multiplyFromTnaf(abstractF2m, Tnaf.tauAdicNaf(Tnaf.getMu(((ECCurve.AbstractF2m)abstractF2m.getCurve()).getA()), zTauElement));
    }

    public static SimpleBigDecimal norm(byte by, SimpleBigDecimal simpleBigDecimal, SimpleBigDecimal simpleBigDecimal2) {
        block4 : {
            block3 : {
                SimpleBigDecimal simpleBigDecimal3;
                block2 : {
                    simpleBigDecimal3 = simpleBigDecimal.multiply(simpleBigDecimal);
                    simpleBigDecimal = simpleBigDecimal.multiply(simpleBigDecimal2);
                    simpleBigDecimal2 = simpleBigDecimal2.multiply(simpleBigDecimal2).shiftLeft(1);
                    if (by != 1) break block2;
                    simpleBigDecimal = simpleBigDecimal3.add(simpleBigDecimal).add(simpleBigDecimal2);
                    break block3;
                }
                if (by != -1) break block4;
                simpleBigDecimal = simpleBigDecimal3.subtract(simpleBigDecimal).add(simpleBigDecimal2);
            }
            return simpleBigDecimal;
        }
        throw new IllegalArgumentException("mu must be 1 or -1");
    }

    public static BigInteger norm(byte by, ZTauElement object) {
        block4 : {
            block3 : {
                BigInteger bigInteger;
                BigInteger bigInteger2;
                block2 : {
                    bigInteger2 = ((ZTauElement)object).u.multiply(((ZTauElement)object).u);
                    bigInteger = ((ZTauElement)object).u.multiply(((ZTauElement)object).v);
                    object = ((ZTauElement)object).v.multiply(((ZTauElement)object).v).shiftLeft(1);
                    if (by != 1) break block2;
                    object = bigInteger2.add(bigInteger).add((BigInteger)object);
                    break block3;
                }
                if (by != -1) break block4;
                object = bigInteger2.subtract(bigInteger).add((BigInteger)object);
            }
            return object;
        }
        throw new IllegalArgumentException("mu must be 1 or -1");
    }

    public static ZTauElement partModReduction(BigInteger bigInteger, int n, byte by, BigInteger[] arrbigInteger, byte by2, byte by3) {
        BigInteger bigInteger2 = by2 == 1 ? arrbigInteger[0].add(arrbigInteger[1]) : arrbigInteger[0].subtract(arrbigInteger[1]);
        Object object = Tnaf.getLucas(by2, n, true)[1];
        object = Tnaf.round(Tnaf.approximateDivisionByN(bigInteger, arrbigInteger[0], (BigInteger)object, by, n, by3), Tnaf.approximateDivisionByN(bigInteger, arrbigInteger[1], (BigInteger)object, by, n, by3), by2);
        return new ZTauElement(bigInteger.subtract(bigInteger2.multiply(((ZTauElement)object).u)).subtract(BigInteger.valueOf(2L).multiply(arrbigInteger[1]).multiply(((ZTauElement)object).v)), arrbigInteger[1].multiply(((ZTauElement)object).u).subtract(arrbigInteger[0].multiply(((ZTauElement)object).v)));
    }

    public static ZTauElement round(SimpleBigDecimal simpleBigDecimal, SimpleBigDecimal simpleBigDecimal2, byte by) {
        int n = simpleBigDecimal.getScale();
        if (simpleBigDecimal2.getScale() == n) {
            int n2;
            if (by != 1 && by != -1) {
                throw new IllegalArgumentException("mu must be 1 or -1");
            }
            BigInteger bigInteger = simpleBigDecimal.round();
            BigInteger bigInteger2 = simpleBigDecimal2.round();
            SimpleBigDecimal simpleBigDecimal3 = simpleBigDecimal.subtract(bigInteger);
            simpleBigDecimal2 = simpleBigDecimal2.subtract(bigInteger2);
            simpleBigDecimal = simpleBigDecimal3.add(simpleBigDecimal3);
            simpleBigDecimal = by == 1 ? simpleBigDecimal.add(simpleBigDecimal2) : simpleBigDecimal.subtract(simpleBigDecimal2);
            SimpleBigDecimal simpleBigDecimal4 = simpleBigDecimal2.add(simpleBigDecimal2).add(simpleBigDecimal2);
            simpleBigDecimal2 = simpleBigDecimal4.add(simpleBigDecimal2);
            if (by == 1) {
                simpleBigDecimal4 = simpleBigDecimal3.subtract(simpleBigDecimal4);
                simpleBigDecimal2 = simpleBigDecimal3.add(simpleBigDecimal2);
            } else {
                simpleBigDecimal4 = simpleBigDecimal3.add(simpleBigDecimal4);
                simpleBigDecimal2 = simpleBigDecimal3.subtract(simpleBigDecimal2);
            }
            int n3 = 0;
            n = 0;
            if (simpleBigDecimal.compareTo(ECConstants.ONE) >= 0) {
                if (simpleBigDecimal4.compareTo(MINUS_ONE) < 0) {
                    n = by;
                    n2 = n3;
                } else {
                    n2 = 1;
                }
            } else {
                n2 = n3;
                if (simpleBigDecimal2.compareTo(ECConstants.TWO) >= 0) {
                    n = by;
                    n2 = n3;
                }
            }
            if (simpleBigDecimal.compareTo(MINUS_ONE) < 0) {
                if (simpleBigDecimal4.compareTo(ECConstants.ONE) >= 0) {
                    n = -by;
                    n3 = n2;
                } else {
                    n3 = -1;
                }
            } else {
                n3 = n2;
                if (simpleBigDecimal2.compareTo(MINUS_TWO) < 0) {
                    n = -by;
                    n3 = n2;
                }
            }
            return new ZTauElement(bigInteger.add(BigInteger.valueOf(n3)), bigInteger2.add(BigInteger.valueOf(n)));
        }
        throw new IllegalArgumentException("lambda0 and lambda1 do not have same scale");
    }

    public static ECPoint.AbstractF2m tau(ECPoint.AbstractF2m abstractF2m) {
        return abstractF2m.tau();
    }

    public static byte[] tauAdicNaf(byte by, ZTauElement object) {
        if (by != 1 && by != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        int n = Tnaf.norm(by, (ZTauElement)object).bitLength();
        n = n > 30 ? (n += 4) : 34;
        byte[] arrby = new byte[n];
        n = 0;
        int n2 = 0;
        Object object2 = ((ZTauElement)object).u;
        BigInteger bigInteger = ((ZTauElement)object).v;
        object = object2;
        do {
            if (((BigInteger)object).equals(ECConstants.ZERO) && bigInteger.equals(ECConstants.ZERO)) {
                n = n2 + 1;
                object = new byte[n];
                System.arraycopy((byte[])arrby, (int)0, (byte[])object, (int)0, (int)n);
                return object;
            }
            if (((BigInteger)object).testBit(0)) {
                arrby[n] = (byte)ECConstants.TWO.subtract(((BigInteger)object).subtract(bigInteger.shiftLeft(1)).mod(ECConstants.FOUR)).intValue();
                object = arrby[n] == 1 ? ((BigInteger)object).clearBit(0) : ((BigInteger)object).add(ECConstants.ONE);
                n2 = n;
                object2 = object;
            } else {
                arrby[n] = (byte)(false ? 1 : 0);
                object2 = object;
            }
            object = object2.shiftRight(1);
            object = by == 1 ? bigInteger.add((BigInteger)object) : bigInteger.subtract((BigInteger)object);
            bigInteger = object2.shiftRight(1).negate();
            ++n;
        } while (true);
    }

    public static byte[] tauAdicWNaf(byte by, ZTauElement object, byte by2, BigInteger bigInteger, BigInteger bigInteger2, ZTauElement[] arrzTauElement) {
        if (by != 1 && by != -1) {
            throw new IllegalArgumentException("mu must be 1 or -1");
        }
        int n = Tnaf.norm(by, (ZTauElement)object).bitLength();
        by2 = n > 30 ? (byte)(n + 4 + by2) : (byte)(by2 + 34);
        byte[] arrby = new byte[by2];
        BigInteger bigInteger3 = bigInteger.shiftRight(1);
        BigInteger bigInteger4 = ((ZTauElement)object).u;
        object = ((ZTauElement)object).v;
        n = 0;
        while (!bigInteger4.equals(ECConstants.ZERO) || !((BigInteger)object).equals(ECConstants.ZERO)) {
            BigInteger bigInteger5;
            if (bigInteger4.testBit(0)) {
                bigInteger5 = bigInteger4.add(((BigInteger)object).multiply(bigInteger2)).mod(bigInteger);
                by2 = bigInteger5.compareTo(bigInteger3) >= 0 ? (byte)bigInteger5.subtract(bigInteger).intValue() : (byte)bigInteger5.intValue();
                arrby[n] = by2;
                boolean bl = true;
                byte by3 = by2;
                if (by2 < 0) {
                    bl = false;
                    by3 = -by2;
                }
                if (bl) {
                    bigInteger4 = bigInteger4.subtract(arrzTauElement[by3].u);
                    object = ((BigInteger)object).subtract(arrzTauElement[by3].v);
                } else {
                    bigInteger4 = bigInteger4.add(arrzTauElement[by3].u);
                    object = ((BigInteger)object).add(arrzTauElement[by3].v);
                }
                bigInteger5 = bigInteger4;
            } else {
                arrby[n] = (byte)(false ? 1 : 0);
                bigInteger5 = bigInteger4;
            }
            bigInteger4 = by == 1 ? ((BigInteger)object).add(bigInteger5.shiftRight(1)) : ((BigInteger)object).subtract(bigInteger5.shiftRight(1));
            object = bigInteger5.shiftRight(1).negate();
            ++n;
        }
        return arrby;
    }
}

