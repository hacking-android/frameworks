/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.ECPointMap;
import com.android.org.bouncycastle.math.ec.PreCompCallback;
import com.android.org.bouncycastle.math.ec.PreCompInfo;
import com.android.org.bouncycastle.math.ec.WNafPreCompInfo;
import java.math.BigInteger;

public abstract class WNafUtil {
    private static final int[] DEFAULT_WINDOW_SIZE_CUTOFFS = new int[]{13, 41, 121, 337, 897, 2305};
    private static final byte[] EMPTY_BYTES = new byte[0];
    private static final int[] EMPTY_INTS = new int[0];
    private static final ECPoint[] EMPTY_POINTS = new ECPoint[0];
    public static final String PRECOMP_NAME = "bc_wnaf";

    public static int[] generateCompactNaf(BigInteger arrn) {
        if (arrn.bitLength() >>> 16 == 0) {
            int n;
            if (arrn.signum() == 0) {
                return EMPTY_INTS;
            }
            BigInteger bigInteger = arrn.shiftLeft(1).add((BigInteger)arrn);
            int n2 = bigInteger.bitLength();
            int[] arrn2 = new int[n2 >> 1];
            bigInteger = bigInteger.xor((BigInteger)arrn);
            int n3 = 0;
            int n4 = 0;
            for (n = 1; n < n2 - 1; ++n) {
                if (!bigInteger.testBit(n)) {
                    ++n4;
                    continue;
                }
                int n5 = arrn.testBit(n) ? -1 : 1;
                arrn2[n3] = n5 << 16 | n4;
                ++n;
                n4 = 1;
                ++n3;
            }
            n = n3 + 1;
            arrn2[n3] = 65536 | n4;
            arrn = arrn2;
            if (arrn2.length > n) {
                arrn = WNafUtil.trim(arrn2, n);
            }
            return arrn;
        }
        throw new IllegalArgumentException("'k' must have bitlength < 2^16");
    }

    public static int[] generateCompactWindowNaf(int n, BigInteger object) {
        if (n == 2) {
            return WNafUtil.generateCompactNaf((BigInteger)object);
        }
        if (n >= 2 && n <= 16) {
            if (((BigInteger)object).bitLength() >>> 16 == 0) {
                if (((BigInteger)object).signum() == 0) {
                    return EMPTY_INTS;
                }
                int[] arrn = new int[((BigInteger)object).bitLength() / n + 1];
                int n2 = 1 << n;
                boolean bl = false;
                int n3 = 0;
                int n4 = 0;
                while (n4 <= ((BigInteger)object).bitLength()) {
                    int n5;
                    if (((BigInteger)object).testBit(n4) == bl) {
                        ++n4;
                        continue;
                    }
                    object = ((BigInteger)object).shiftRight(n4);
                    int n6 = n5 = ((BigInteger)object).intValue() & n2 - 1;
                    if (bl) {
                        n6 = n5 + 1;
                    }
                    bl = (n6 & n2 >>> 1) != 0;
                    n5 = n6;
                    if (bl) {
                        n5 = n6 - n2;
                    }
                    if (n3 > 0) {
                        --n4;
                    }
                    arrn[n3] = n5 << 16 | n4;
                    n4 = n;
                    ++n3;
                }
                object = arrn;
                if (arrn.length > n3) {
                    object = WNafUtil.trim(arrn, n3);
                }
                return object;
            }
            throw new IllegalArgumentException("'k' must have bitlength < 2^16");
        }
        throw new IllegalArgumentException("'width' must be in the range [2, 16]");
    }

    public static byte[] generateJSF(BigInteger arrby, BigInteger object) {
        byte[] arrby2 = new byte[Math.max(arrby.bitLength(), object.bitLength()) + 1];
        Object object2 = arrby;
        arrby = object;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        byte[] arrby3 = object2;
        do {
            int n5;
            int n6;
            if (!(n2 | n3) && arrby3.bitLength() <= n4 && arrby.bitLength() <= n4) {
                arrby = arrby2;
                if (arrby2.length > n) {
                    arrby = WNafUtil.trim(arrby2, n);
                }
                return arrby;
            }
            int n7 = (arrby3.intValue() >>> n4) + n2 & 7;
            int n8 = (arrby.intValue() >>> n4) + n3 & 7;
            int n9 = n6 = n7 & 1;
            if (n6 != 0) {
                n9 = n6 -= n7 & 2;
                if (n7 + n6 == 4) {
                    n9 = n6;
                    if ((n8 & 3) == 2) {
                        n9 = -n6;
                    }
                }
            }
            n6 = n5 = n8 & 1;
            if (n5 != 0) {
                n6 = n5 -= n8 & 2;
                if (n8 + n5 == 4) {
                    n6 = n5;
                    if ((n7 & 3) == 2) {
                        n6 = -n5;
                    }
                }
            }
            n5 = n2;
            if (n2 << 1 == n9 + 1) {
                n5 = n2 ^ 1;
            }
            n8 = n3;
            if (n3 << 1 == n6 + 1) {
                n8 = n3 ^ 1;
            }
            n3 = n4 + 1;
            object2 = arrby3;
            object = arrby;
            n4 = n3;
            if (n3 == 30) {
                n4 = 0;
                object2 = arrby3.shiftRight(30);
                object = arrby.shiftRight(30);
            }
            arrby2[n] = (byte)(n9 << 4 | n6 & 15);
            ++n;
            arrby3 = object2;
            arrby = object;
            n2 = n5;
            n3 = n8;
        } while (true);
    }

    public static byte[] generateNaf(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return EMPTY_BYTES;
        }
        BigInteger bigInteger2 = bigInteger.shiftLeft(1).add(bigInteger);
        int n = bigInteger2.bitLength() - 1;
        byte[] arrby = new byte[n];
        bigInteger2 = bigInteger2.xor(bigInteger);
        int n2 = 1;
        while (n2 < n) {
            int n3 = n2;
            if (bigInteger2.testBit(n2)) {
                n3 = bigInteger.testBit(n2) ? -1 : 1;
                arrby[n2 - 1] = (byte)n3;
                n3 = n2 + 1;
            }
            n2 = n3 + 1;
        }
        arrby[n - 1] = (byte)(true ? 1 : 0);
        return arrby;
    }

    public static byte[] generateWindowNaf(int n, BigInteger object) {
        if (n == 2) {
            return WNafUtil.generateNaf((BigInteger)object);
        }
        if (n >= 2 && n <= 8) {
            if (((BigInteger)object).signum() == 0) {
                return EMPTY_BYTES;
            }
            byte[] arrby = new byte[((BigInteger)object).bitLength() + 1];
            int n2 = 1 << n;
            boolean bl = false;
            int n3 = 0;
            int n4 = 0;
            while (n4 <= ((BigInteger)object).bitLength()) {
                int n5;
                if (((BigInteger)object).testBit(n4) == bl) {
                    ++n4;
                    continue;
                }
                object = ((BigInteger)object).shiftRight(n4);
                int n6 = n5 = ((BigInteger)object).intValue() & n2 - 1;
                if (bl) {
                    n6 = n5 + 1;
                }
                bl = (n6 & n2 >>> 1) != 0;
                n5 = n6;
                if (bl) {
                    n5 = n6 - n2;
                }
                if (n3 > 0) {
                    --n4;
                }
                n6 = n3 + n4;
                arrby[n6] = (byte)n5;
                n4 = n;
                n3 = n6 + 1;
            }
            object = arrby;
            if (arrby.length > n3) {
                object = WNafUtil.trim(arrby, n3);
            }
            return object;
        }
        throw new IllegalArgumentException("'width' must be in the range [2, 8]");
    }

    public static int getNafWeight(BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return 0;
        }
        return bigInteger.shiftLeft(1).add(bigInteger).xor(bigInteger).bitCount();
    }

    public static WNafPreCompInfo getWNafPreCompInfo(ECPoint eCPoint) {
        return WNafUtil.getWNafPreCompInfo(eCPoint.getCurve().getPreCompInfo(eCPoint, PRECOMP_NAME));
    }

    public static WNafPreCompInfo getWNafPreCompInfo(PreCompInfo preCompInfo) {
        preCompInfo = preCompInfo instanceof WNafPreCompInfo ? (WNafPreCompInfo)preCompInfo : null;
        return preCompInfo;
    }

    public static int getWindowSize(int n) {
        return WNafUtil.getWindowSize(n, DEFAULT_WINDOW_SIZE_CUTOFFS);
    }

    public static int getWindowSize(int n, int[] arrn) {
        int n2;
        for (n2 = 0; n2 < arrn.length && n >= arrn[n2]; ++n2) {
        }
        return n2 + 2;
    }

    public static ECPoint mapPointWithPrecomp(ECPoint eCPoint, int n, final boolean bl, final ECPointMap eCPointMap) {
        ECCurve eCCurve = eCPoint.getCurve();
        final WNafPreCompInfo wNafPreCompInfo = WNafUtil.precompute(eCPoint, n, bl);
        eCPoint = eCPointMap.map(eCPoint);
        eCCurve.precompute(eCPoint, PRECOMP_NAME, new PreCompCallback(){

            @Override
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                int n;
                preCompInfo = new WNafPreCompInfo();
                ECPoint[] arreCPoint = wNafPreCompInfo.getTwice();
                if (arreCPoint != null) {
                    ((WNafPreCompInfo)preCompInfo).setTwice(eCPointMap.map((ECPoint)arreCPoint));
                }
                ECPoint[] arreCPoint2 = wNafPreCompInfo.getPreComp();
                arreCPoint = new ECPoint[arreCPoint2.length];
                for (n = 0; n < arreCPoint2.length; ++n) {
                    arreCPoint[n] = eCPointMap.map(arreCPoint2[n]);
                }
                ((WNafPreCompInfo)preCompInfo).setPreComp(arreCPoint);
                if (bl) {
                    arreCPoint2 = new ECPoint[arreCPoint.length];
                    for (n = 0; n < arreCPoint2.length; ++n) {
                        arreCPoint2[n] = arreCPoint[n].negate();
                    }
                    ((WNafPreCompInfo)preCompInfo).setPreCompNeg(arreCPoint2);
                }
                return preCompInfo;
            }
        });
        return eCPoint;
    }

    public static WNafPreCompInfo precompute(final ECPoint eCPoint, final int n, final boolean bl) {
        final ECCurve eCCurve = eCPoint.getCurve();
        return (WNafPreCompInfo)eCCurve.precompute(eCPoint, PRECOMP_NAME, new PreCompCallback(){

            private boolean checkExisting(WNafPreCompInfo wNafPreCompInfo, int n2, boolean bl2) {
                bl2 = wNafPreCompInfo != null && this.checkTable(wNafPreCompInfo.getPreComp(), n2) && (!bl2 || this.checkTable(wNafPreCompInfo.getPreCompNeg(), n2));
                return bl2;
            }

            private boolean checkTable(ECPoint[] arreCPoint, int n2) {
                boolean bl2 = arreCPoint != null && arreCPoint.length >= n2;
                return bl2;
            }

            @Override
            public PreCompInfo precompute(PreCompInfo object) {
                int n5;
                int n2;
                Object object2 = object instanceof WNafPreCompInfo ? (ECPoint[])object : null;
                if (this.checkExisting((WNafPreCompInfo)object2, n2 = 1 << Math.max(0, n - 2), bl)) {
                    return object2;
                }
                Object object3 = null;
                ECPoint[] arreCPoint = null;
                object = null;
                if (object2 != null) {
                    object3 = ((WNafPreCompInfo)object2).getPreComp();
                    arreCPoint = ((WNafPreCompInfo)object2).getPreCompNeg();
                    object = ((WNafPreCompInfo)object2).getTwice();
                }
                int n3 = 0;
                if (object3 == null) {
                    object3 = EMPTY_POINTS;
                } else {
                    n3 = ((ECPoint[])object3).length;
                }
                object2 = object3;
                Object object4 = object;
                if (n3 < n2) {
                    ECPoint[] arreCPoint2 = WNafUtil.resizeTable(object3, n2);
                    if (n2 == 1) {
                        arreCPoint2[0] = eCPoint.normalize();
                        object2 = arreCPoint2;
                        object4 = object;
                    } else {
                        int n4;
                        n5 = n4 = n3;
                        if (n4 == 0) {
                            arreCPoint2[0] = eCPoint;
                            n5 = 1;
                        }
                        Object object5 = null;
                        Object var12_12 = null;
                        if (n2 == 2) {
                            arreCPoint2[1] = eCPoint.threeTimes();
                            object4 = object;
                        } else {
                            object4 = object;
                            Object object6 = arreCPoint2[n5 - 1];
                            object3 = object4;
                            n4 = n5;
                            object2 = var12_12;
                            Object object7 = object6;
                            if (object4 == null) {
                                object5 = arreCPoint2[0].twice();
                                object4 = object5;
                                object3 = object5;
                                object = object4;
                                n4 = n5;
                                object2 = var12_12;
                                object7 = object6;
                                if (!((ECPoint)object4).isInfinity()) {
                                    object3 = object5;
                                    object = object4;
                                    n4 = n5;
                                    object2 = var12_12;
                                    object7 = object6;
                                    if (ECAlgorithms.isFpCurve(eCCurve)) {
                                        object3 = object5;
                                        object = object4;
                                        n4 = n5;
                                        object2 = var12_12;
                                        object7 = object6;
                                        if (eCCurve.getFieldSize() >= 64) {
                                            n4 = eCCurve.getCoordinateSystem();
                                            if (n4 != 2 && n4 != 3 && n4 != 4) {
                                                object3 = object5;
                                                object = object4;
                                                n4 = n5;
                                                object2 = var12_12;
                                                object7 = object6;
                                            } else {
                                                object2 = ((ECPoint)object4).getZCoord(0);
                                                object3 = eCCurve.createPoint(((ECPoint)object4).getXCoord().toBigInteger(), ((ECPoint)object4).getYCoord().toBigInteger());
                                                object7 = ((ECFieldElement)object2).square();
                                                object = ((ECFieldElement)object7).multiply((ECFieldElement)object2);
                                                object7 = ((ECPoint)object6).scaleX((ECFieldElement)object7).scaleY((ECFieldElement)object);
                                                if (n3 == 0) {
                                                    arreCPoint2[0] = object7;
                                                }
                                                n4 = n5;
                                                object = object4;
                                            }
                                        }
                                    }
                                }
                            }
                            do {
                                object4 = object;
                                object5 = object2;
                                if (n4 >= n2) break;
                                object7 = object4 = ((ECPoint)object7).add((ECPoint)object3);
                                arreCPoint2[n4] = object4;
                                ++n4;
                            } while (true);
                        }
                        eCCurve.normalizeAll(arreCPoint2, n3, n2 - n3, (ECFieldElement)object5);
                        object2 = arreCPoint2;
                    }
                }
                object3 = arreCPoint;
                if (bl) {
                    if (arreCPoint == null) {
                        n3 = 0;
                        object = new ECPoint[n2];
                    } else {
                        n3 = n5 = arreCPoint.length;
                        object = arreCPoint;
                        if (n5 < n2) {
                            object = WNafUtil.resizeTable(arreCPoint, n2);
                            n3 = n5;
                        }
                    }
                    do {
                        object3 = object;
                        if (n3 >= n2) break;
                        object[n3] = object2[n3].negate();
                        ++n3;
                    } while (true);
                }
                object = new WNafPreCompInfo();
                object.setPreComp((ECPoint[])object2);
                object.setPreCompNeg((ECPoint[])object3);
                object.setTwice((ECPoint)object4);
                return object;
            }
        });
    }

    private static ECPoint[] resizeTable(ECPoint[] arreCPoint, int n) {
        ECPoint[] arreCPoint2 = new ECPoint[n];
        System.arraycopy(arreCPoint, 0, arreCPoint2, 0, arreCPoint.length);
        return arreCPoint2;
    }

    private static byte[] trim(byte[] arrby, int n) {
        byte[] arrby2 = new byte[n];
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)0, (int)arrby2.length);
        return arrby2;
    }

    private static int[] trim(int[] arrn, int n) {
        int[] arrn2 = new int[n];
        System.arraycopy(arrn, 0, arrn2, 0, arrn2.length);
        return arrn2;
    }

}

