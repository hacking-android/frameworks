/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.org.bouncycastle.math.ec.ECConstants;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECMultiplier;
import com.android.org.bouncycastle.math.ec.PreCompCallback;
import com.android.org.bouncycastle.math.ec.PreCompInfo;
import com.android.org.bouncycastle.math.ec.ValidityPrecompInfo;
import java.math.BigInteger;
import java.util.Hashtable;

public abstract class ECPoint {
    protected static final ECFieldElement[] EMPTY_ZS = new ECFieldElement[0];
    protected ECCurve curve;
    protected Hashtable preCompTable = null;
    protected boolean withCompression;
    protected ECFieldElement x;
    protected ECFieldElement y;
    protected ECFieldElement[] zs;

    protected ECPoint(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        this(eCCurve, eCFieldElement, eCFieldElement2, ECPoint.getInitialZCoords(eCCurve));
    }

    protected ECPoint(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement) {
        this.curve = eCCurve;
        this.x = eCFieldElement;
        this.y = eCFieldElement2;
        this.zs = arreCFieldElement;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static ECFieldElement[] getInitialZCoords(ECCurve eCCurve) {
        int n = eCCurve == null ? 0 : eCCurve.getCoordinateSystem();
        if (n == 0 || n == 5) return EMPTY_ZS;
        ECFieldElement eCFieldElement = eCCurve.fromBigInteger(ECConstants.ONE);
        if (n == 1 || n == 2) return new ECFieldElement[]{eCFieldElement};
        if (n == 3) return new ECFieldElement[]{eCFieldElement, eCFieldElement, eCFieldElement};
        if (n == 4) return new ECFieldElement[]{eCFieldElement, eCCurve.getA()};
        if (n == 6) return new ECFieldElement[]{eCFieldElement};
        throw new IllegalArgumentException("unknown coordinate system");
    }

    public abstract ECPoint add(ECPoint var1);

    protected void checkNormalized() {
        if (this.isNormalized()) {
            return;
        }
        throw new IllegalStateException("point not in normal form");
    }

    protected ECPoint createScaledPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return this.getCurve().createRawPoint(this.getRawXCoord().multiply(eCFieldElement), this.getRawYCoord().multiply(eCFieldElement2), this.withCompression);
    }

    protected abstract ECPoint detach();

    public boolean equals(ECPoint eCPoint) {
        boolean bl;
        block14 : {
            block15 : {
                boolean bl2 = false;
                boolean bl3 = false;
                if (eCPoint == null) {
                    return false;
                }
                ECCurve eCCurve = this.getCurve();
                ECCurve eCCurve2 = eCPoint.getCurve();
                boolean bl4 = eCCurve == null;
                boolean bl5 = eCCurve2 == null;
                boolean bl6 = this.isInfinity();
                boolean bl7 = eCPoint.isInfinity();
                if (!bl6 && !bl7) {
                    ECPoint[] arreCPoint = this;
                    ECPoint eCPoint2 = eCPoint;
                    if (bl4 && bl5) {
                        eCPoint = arreCPoint;
                    } else if (bl4) {
                        eCPoint2 = eCPoint2.normalize();
                        eCPoint = arreCPoint;
                    } else if (bl5) {
                        eCPoint = arreCPoint.normalize();
                    } else {
                        if (!eCCurve.equals(eCCurve2)) {
                            return false;
                        }
                        arreCPoint = new ECPoint[]{this, eCCurve.importPoint(eCPoint2)};
                        eCCurve.normalizeAll(arreCPoint);
                        eCPoint = arreCPoint[0];
                        eCPoint2 = arreCPoint[1];
                    }
                    boolean bl8 = bl3;
                    if (eCPoint.getXCoord().equals(eCPoint2.getXCoord())) {
                        bl8 = bl3;
                        if (eCPoint.getYCoord().equals(eCPoint2.getYCoord())) {
                            bl8 = true;
                        }
                    }
                    return bl8;
                }
                bl = bl2;
                if (!bl6) break block14;
                bl = bl2;
                if (!bl7) break block14;
                if (bl4 || bl5) break block15;
                bl = bl2;
                if (!eCCurve.equals(eCCurve2)) break block14;
            }
            bl = true;
        }
        return bl;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ECPoint)) {
            return false;
        }
        return this.equals((ECPoint)object);
    }

    public ECFieldElement getAffineXCoord() {
        this.checkNormalized();
        return this.getXCoord();
    }

    public ECFieldElement getAffineYCoord() {
        this.checkNormalized();
        return this.getYCoord();
    }

    protected abstract boolean getCompressionYTilde();

    public ECCurve getCurve() {
        return this.curve;
    }

    protected int getCurveCoordinateSystem() {
        ECCurve eCCurve = this.curve;
        int n = eCCurve == null ? 0 : eCCurve.getCoordinateSystem();
        return n;
    }

    public final ECPoint getDetachedPoint() {
        return this.normalize().detach();
    }

    public byte[] getEncoded() {
        return this.getEncoded(this.withCompression);
    }

    public byte[] getEncoded(boolean bl) {
        if (this.isInfinity()) {
            return new byte[1];
        }
        byte[] arrby = this.normalize();
        byte[] arrby2 = arrby.getXCoord().getEncoded();
        if (bl) {
            byte[] arrby3 = new byte[arrby2.length + 1];
            int n = arrby.getCompressionYTilde() ? 3 : 2;
            arrby3[0] = (byte)n;
            System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby3, (int)1, (int)arrby2.length);
            return arrby3;
        }
        byte[] arrby4 = arrby.getYCoord().getEncoded();
        arrby = new byte[arrby2.length + arrby4.length + 1];
        arrby[0] = (byte)4;
        System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)1, (int)arrby2.length);
        System.arraycopy((byte[])arrby4, (int)0, (byte[])arrby, (int)(arrby2.length + 1), (int)arrby4.length);
        return arrby;
    }

    public final ECFieldElement getRawXCoord() {
        return this.x;
    }

    public final ECFieldElement getRawYCoord() {
        return this.y;
    }

    protected final ECFieldElement[] getRawZCoords() {
        return this.zs;
    }

    public ECFieldElement getXCoord() {
        return this.x;
    }

    public ECFieldElement getYCoord() {
        return this.y;
    }

    public ECFieldElement getZCoord(int n) {
        Object object;
        object = n >= 0 && n < ((ECFieldElement[])(object = this.zs)).length ? object[n] : null;
        return object;
    }

    public ECFieldElement[] getZCoords() {
        ECFieldElement[] arreCFieldElement = this.zs;
        int n = arreCFieldElement.length;
        if (n == 0) {
            return EMPTY_ZS;
        }
        ECFieldElement[] arreCFieldElement2 = new ECFieldElement[n];
        System.arraycopy(arreCFieldElement, 0, arreCFieldElement2, 0, n);
        return arreCFieldElement2;
    }

    public int hashCode() {
        Object object = this.getCurve();
        int n = object == null ? 0 : ((ECCurve)object).hashCode();
        int n2 = n;
        if (!this.isInfinity()) {
            object = this.normalize();
            n2 = n ^ ((ECPoint)object).getXCoord().hashCode() * 17 ^ ((ECPoint)object).getYCoord().hashCode() * 257;
        }
        return n2;
    }

    boolean implIsValid(final boolean bl, final boolean bl2) {
        if (this.isInfinity()) {
            return true;
        }
        return true ^ ((ValidityPrecompInfo)this.getCurve().precompute(this, "bc_validity", new PreCompCallback(){

            @Override
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                preCompInfo = preCompInfo instanceof ValidityPrecompInfo ? (ValidityPrecompInfo)preCompInfo : null;
                PreCompInfo preCompInfo2 = preCompInfo;
                if (preCompInfo == null) {
                    preCompInfo2 = new ValidityPrecompInfo();
                }
                if (((ValidityPrecompInfo)preCompInfo2).hasFailed()) {
                    return preCompInfo2;
                }
                if (!((ValidityPrecompInfo)preCompInfo2).hasCurveEquationPassed()) {
                    if (!bl && !ECPoint.this.satisfiesCurveEquation()) {
                        ((ValidityPrecompInfo)preCompInfo2).reportFailed();
                        return preCompInfo2;
                    }
                    ((ValidityPrecompInfo)preCompInfo2).reportCurveEquationPassed();
                }
                if (bl2 && !((ValidityPrecompInfo)preCompInfo2).hasOrderPassed()) {
                    if (!ECPoint.this.satisfiesOrder()) {
                        ((ValidityPrecompInfo)preCompInfo2).reportFailed();
                        return preCompInfo2;
                    }
                    ((ValidityPrecompInfo)preCompInfo2).reportOrderPassed();
                }
                return preCompInfo2;
            }
        })).hasFailed();
    }

    public boolean isCompressed() {
        return this.withCompression;
    }

    public boolean isInfinity() {
        boolean bl;
        block3 : {
            block2 : {
                ECFieldElement[] arreCFieldElement = this.x;
                boolean bl2 = false;
                if (arreCFieldElement == null || this.y == null) break block2;
                arreCFieldElement = this.zs;
                bl = bl2;
                if (arreCFieldElement.length <= 0) break block3;
                bl = bl2;
                if (!arreCFieldElement[0].isZero()) break block3;
            }
            bl = true;
        }
        return bl;
    }

    public boolean isNormalized() {
        int n = this.getCurveCoordinateSystem();
        boolean bl = false;
        if (n == 0 || n == 5 || this.isInfinity() || this.zs[0].isOne()) {
            bl = true;
        }
        return bl;
    }

    public boolean isValid() {
        return this.implIsValid(false, true);
    }

    boolean isValidPartial() {
        return this.implIsValid(false, false);
    }

    public ECPoint multiply(BigInteger bigInteger) {
        return this.getCurve().getMultiplier().multiply(this, bigInteger);
    }

    public abstract ECPoint negate();

    public ECPoint normalize() {
        if (this.isInfinity()) {
            return this;
        }
        int n = this.getCurveCoordinateSystem();
        if (n != 0 && n != 5) {
            ECFieldElement eCFieldElement = this.getZCoord(0);
            if (eCFieldElement.isOne()) {
                return this;
            }
            return this.normalize(eCFieldElement.invert());
        }
        return this;
    }

    ECPoint normalize(ECFieldElement eCFieldElement) {
        int n = this.getCurveCoordinateSystem();
        if (n != 1) {
            if (n != 2 && n != 3 && n != 4) {
                if (n != 6) {
                    throw new IllegalStateException("not a projective coordinate system");
                }
            } else {
                ECFieldElement eCFieldElement2 = eCFieldElement.square();
                return this.createScaledPoint(eCFieldElement2, eCFieldElement2.multiply(eCFieldElement));
            }
        }
        return this.createScaledPoint(eCFieldElement, eCFieldElement);
    }

    protected abstract boolean satisfiesCurveEquation();

    protected boolean satisfiesOrder() {
        boolean bl = ECConstants.ONE.equals(this.curve.getCofactor());
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        BigInteger bigInteger = this.curve.getOrder();
        bl = bl2;
        if (bigInteger != null) {
            bl = ECAlgorithms.referenceMultiply(this, bigInteger).isInfinity() ? bl2 : false;
        }
        return bl;
    }

    public ECPoint scaleX(ECFieldElement object) {
        object = this.isInfinity() ? this : this.getCurve().createRawPoint(this.getRawXCoord().multiply((ECFieldElement)object), this.getRawYCoord(), this.getRawZCoords(), this.withCompression);
        return object;
    }

    public ECPoint scaleY(ECFieldElement object) {
        object = this.isInfinity() ? this : this.getCurve().createRawPoint(this.getRawXCoord(), this.getRawYCoord().multiply((ECFieldElement)object), this.getRawZCoords(), this.withCompression);
        return object;
    }

    public abstract ECPoint subtract(ECPoint var1);

    public ECPoint threeTimes() {
        return this.twicePlus(this);
    }

    public ECPoint timesPow2(int n) {
        if (n >= 0) {
            ECPoint eCPoint = this;
            while (--n >= 0) {
                eCPoint = eCPoint.twice();
            }
            return eCPoint;
        }
        throw new IllegalArgumentException("'e' cannot be negative");
    }

    public String toString() {
        if (this.isInfinity()) {
            return "INF";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('(');
        stringBuffer.append(this.getRawXCoord());
        stringBuffer.append(',');
        stringBuffer.append(this.getRawYCoord());
        for (int i = 0; i < this.zs.length; ++i) {
            stringBuffer.append(',');
            stringBuffer.append(this.zs[i]);
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    public abstract ECPoint twice();

    public ECPoint twicePlus(ECPoint eCPoint) {
        return this.twice().add(eCPoint);
    }

    public static abstract class AbstractF2m
    extends ECPoint {
        protected AbstractF2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        protected AbstractF2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement) {
            super(eCCurve, eCFieldElement, eCFieldElement2, arreCFieldElement);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        protected boolean satisfiesCurveEquation() {
            ECFieldElement eCFieldElement;
            Object object;
            Object object2 = this.getCurve();
            ECFieldElement eCFieldElement2 = this.x;
            ECFieldElement eCFieldElement3 = ((ECCurve)object2).getA();
            Object object3 = ((ECCurve)object2).getB();
            int n = ((ECCurve)object2).getCoordinateSystem();
            if (n == 6) {
                ECFieldElement eCFieldElement4 = this.zs[0];
                boolean bl = eCFieldElement4.isOne();
                if (eCFieldElement2.isZero()) {
                    ECFieldElement eCFieldElement5 = this.y.square();
                    object3 = object2 = object3;
                    if (bl) return eCFieldElement5.equals(object3);
                    object3 = ((ECFieldElement)object2).multiply(eCFieldElement4.square());
                    return eCFieldElement5.equals(object3);
                }
                object2 = this.y;
                ECFieldElement eCFieldElement6 = eCFieldElement2.square();
                if (bl) {
                    object2 = ((ECFieldElement)object2).square().add((ECFieldElement)object2).add(eCFieldElement3);
                    object3 = eCFieldElement6.square().add((ECFieldElement)object3);
                    return ((ECFieldElement)object2).multiply(eCFieldElement6).equals(object3);
                } else {
                    eCFieldElement2 = eCFieldElement4.square();
                    ECFieldElement eCFieldElement7 = eCFieldElement2.square();
                    object2 = ((ECFieldElement)object2).add(eCFieldElement4).multiplyPlusProduct((ECFieldElement)object2, eCFieldElement3, eCFieldElement2);
                    object3 = eCFieldElement6.squarePlusProduct((ECFieldElement)object3, eCFieldElement7);
                }
                return ((ECFieldElement)object2).multiply(eCFieldElement6).equals(object3);
            }
            object2 = this.y;
            ECFieldElement eCFieldElement8 = ((ECFieldElement)object2).add(eCFieldElement2).multiply((ECFieldElement)object2);
            if (n != 0) {
                if (n != 1) throw new IllegalStateException("unsupported coordinate system");
                ECFieldElement eCFieldElement9 = this.zs[0];
                object2 = eCFieldElement3;
                object = object3;
                eCFieldElement = eCFieldElement8;
                if (eCFieldElement9.isOne()) return eCFieldElement.equals(eCFieldElement2.add((ECFieldElement)object2).multiply(eCFieldElement2.square()).add((ECFieldElement)object));
                object = eCFieldElement9.multiply(eCFieldElement9.square());
                eCFieldElement = eCFieldElement8.multiply(eCFieldElement9);
                object2 = eCFieldElement3.multiply(eCFieldElement9);
                object = ((ECFieldElement)object3).multiply((ECFieldElement)object);
                return eCFieldElement.equals(eCFieldElement2.add((ECFieldElement)object2).multiply(eCFieldElement2.square()).add((ECFieldElement)object));
            } else {
                eCFieldElement = eCFieldElement8;
                object = object3;
                object2 = eCFieldElement3;
            }
            return eCFieldElement.equals(eCFieldElement2.add((ECFieldElement)object2).multiply(eCFieldElement2.square()).add((ECFieldElement)object));
        }

        @Override
        protected boolean satisfiesOrder() {
            Object object = this.curve.getCofactor();
            boolean bl = ECConstants.TWO.equals(object);
            boolean bl2 = true;
            boolean bl3 = true;
            if (bl) {
                if (((ECFieldElement.AbstractF2m)this.normalize().getAffineXCoord().add(this.curve.getA())).trace() != 0) {
                    bl3 = false;
                }
                return bl3;
            }
            if (ECConstants.FOUR.equals(object)) {
                Object object2 = this.normalize();
                object = ((ECPoint)object2).getAffineXCoord();
                ECFieldElement eCFieldElement = ((ECCurve.AbstractF2m)this.curve).solveQuadraticEquation(((ECFieldElement)object).add(this.curve.getA()));
                if (eCFieldElement == null) {
                    return false;
                }
                object2 = ((ECFieldElement)object).multiply(eCFieldElement).add(((ECPoint)object2).getAffineYCoord()).add(this.curve.getA());
                bl3 = ((ECFieldElement.AbstractF2m)object2).trace() != 0 && ((ECFieldElement.AbstractF2m)((ECFieldElement)object2).add((ECFieldElement)object)).trace() != 0 ? false : bl2;
                return bl3;
            }
            return super.satisfiesOrder();
        }

        @Override
        public ECPoint scaleX(ECFieldElement object) {
            if (this.isInfinity()) {
                return this;
            }
            int n = this.getCurveCoordinateSystem();
            if (n != 5) {
                if (n != 6) {
                    return super.scaleX((ECFieldElement)object);
                }
                ECFieldElement eCFieldElement = this.getRawXCoord();
                ECFieldElement eCFieldElement2 = this.getRawYCoord();
                ECFieldElement eCFieldElement3 = this.getRawZCoords()[0];
                ECFieldElement eCFieldElement4 = eCFieldElement.multiply(((ECFieldElement)object).square());
                eCFieldElement = eCFieldElement2.add(eCFieldElement).add(eCFieldElement4);
                eCFieldElement3 = eCFieldElement3.multiply((ECFieldElement)object);
                object = this.getCurve();
                boolean bl = this.withCompression;
                return ((ECCurve)object).createRawPoint(eCFieldElement4, eCFieldElement, new ECFieldElement[]{eCFieldElement3}, bl);
            }
            ECFieldElement eCFieldElement = this.getRawXCoord();
            ECFieldElement eCFieldElement5 = this.getRawYCoord();
            ECFieldElement eCFieldElement6 = eCFieldElement.multiply((ECFieldElement)object);
            object = eCFieldElement5.add(eCFieldElement).divide((ECFieldElement)object).add(eCFieldElement6);
            return this.getCurve().createRawPoint(eCFieldElement, (ECFieldElement)object, this.getRawZCoords(), this.withCompression);
        }

        @Override
        public ECPoint scaleY(ECFieldElement eCFieldElement) {
            if (this.isInfinity()) {
                return this;
            }
            int n = this.getCurveCoordinateSystem();
            if (n != 5 && n != 6) {
                return super.scaleY(eCFieldElement);
            }
            ECFieldElement eCFieldElement2 = this.getRawXCoord();
            eCFieldElement = this.getRawYCoord().add(eCFieldElement2).multiply(eCFieldElement).add(eCFieldElement2);
            return this.getCurve().createRawPoint(eCFieldElement2, eCFieldElement, this.getRawZCoords(), this.withCompression);
        }

        @Override
        public ECPoint subtract(ECPoint eCPoint) {
            if (eCPoint.isInfinity()) {
                return this;
            }
            return this.add(eCPoint.negate());
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public AbstractF2m tau() {
            ECFieldElement eCFieldElement;
            ECCurve eCCurve;
            block4 : {
                block5 : {
                    if (this.isInfinity()) {
                        return this;
                    }
                    eCCurve = this.getCurve();
                    int n = eCCurve.getCoordinateSystem();
                    eCFieldElement = this.x;
                    if (n == 0) break block4;
                    if (n == 1) break block5;
                    if (n == 5) break block4;
                    if (n != 6) throw new IllegalStateException("unsupported coordinate system");
                }
                ECFieldElement eCFieldElement2 = this.y;
                ECFieldElement eCFieldElement3 = this.zs[0];
                eCFieldElement = eCFieldElement.square();
                eCFieldElement2 = eCFieldElement2.square();
                eCFieldElement3 = eCFieldElement3.square();
                boolean bl = this.withCompression;
                return (AbstractF2m)eCCurve.createRawPoint(eCFieldElement, eCFieldElement2, new ECFieldElement[]{eCFieldElement3}, bl);
            }
            ECFieldElement eCFieldElement4 = this.y;
            return (AbstractF2m)eCCurve.createRawPoint(eCFieldElement.square(), eCFieldElement4.square(), this.withCompression);
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        public AbstractF2m tauPow(int n) {
            ECCurve eCCurve;
            ECFieldElement eCFieldElement;
            block4 : {
                block5 : {
                    if (this.isInfinity()) {
                        return this;
                    }
                    eCCurve = this.getCurve();
                    int n2 = eCCurve.getCoordinateSystem();
                    eCFieldElement = this.x;
                    if (n2 == 0) break block4;
                    if (n2 == 1) break block5;
                    if (n2 == 5) break block4;
                    if (n2 != 6) throw new IllegalStateException("unsupported coordinate system");
                }
                ECFieldElement eCFieldElement2 = this.y;
                ECFieldElement eCFieldElement3 = this.zs[0];
                eCFieldElement = eCFieldElement.squarePow(n);
                eCFieldElement2 = eCFieldElement2.squarePow(n);
                eCFieldElement3 = eCFieldElement3.squarePow(n);
                boolean bl = this.withCompression;
                return (AbstractF2m)eCCurve.createRawPoint(eCFieldElement, eCFieldElement2, new ECFieldElement[]{eCFieldElement3}, bl);
            }
            ECFieldElement eCFieldElement4 = this.y;
            return (AbstractF2m)eCCurve.createRawPoint(eCFieldElement.squarePow(n), eCFieldElement4.squarePow(n), this.withCompression);
        }
    }

    public static abstract class AbstractFp
    extends ECPoint {
        protected AbstractFp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        protected AbstractFp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement) {
            super(eCCurve, eCFieldElement, eCFieldElement2, arreCFieldElement);
        }

        @Override
        protected boolean getCompressionYTilde() {
            return this.getAffineYCoord().testBitZero();
        }

        @Override
        protected boolean satisfiesCurveEquation() {
            ECFieldElement eCFieldElement;
            ECFieldElement eCFieldElement2;
            ECFieldElement eCFieldElement3 = this.x;
            ECFieldElement eCFieldElement4 = this.y;
            ECFieldElement eCFieldElement5 = this.curve.getA();
            ECFieldElement eCFieldElement6 = this.curve.getB();
            ECFieldElement eCFieldElement7 = eCFieldElement4.square();
            int n = this.getCurveCoordinateSystem();
            if (n != 0) {
                if (n != 1) {
                    if (n != 2 && n != 3 && n != 4) {
                        throw new IllegalStateException("unsupported coordinate system");
                    }
                    ECFieldElement eCFieldElement8 = this.zs[0];
                    eCFieldElement4 = eCFieldElement5;
                    eCFieldElement = eCFieldElement6;
                    eCFieldElement2 = eCFieldElement7;
                    if (!eCFieldElement8.isOne()) {
                        eCFieldElement = eCFieldElement8.square();
                        eCFieldElement4 = eCFieldElement.square();
                        eCFieldElement = eCFieldElement.multiply(eCFieldElement4);
                        eCFieldElement4 = eCFieldElement5.multiply(eCFieldElement4);
                        eCFieldElement = eCFieldElement6.multiply(eCFieldElement);
                        eCFieldElement2 = eCFieldElement7;
                    }
                } else {
                    ECFieldElement eCFieldElement9 = this.zs[0];
                    eCFieldElement4 = eCFieldElement5;
                    eCFieldElement = eCFieldElement6;
                    eCFieldElement2 = eCFieldElement7;
                    if (!eCFieldElement9.isOne()) {
                        eCFieldElement4 = eCFieldElement9.square();
                        eCFieldElement = eCFieldElement9.multiply(eCFieldElement4);
                        eCFieldElement2 = eCFieldElement7.multiply(eCFieldElement9);
                        eCFieldElement4 = eCFieldElement5.multiply(eCFieldElement4);
                        eCFieldElement = eCFieldElement6.multiply(eCFieldElement);
                    }
                }
            } else {
                eCFieldElement2 = eCFieldElement7;
                eCFieldElement = eCFieldElement6;
                eCFieldElement4 = eCFieldElement5;
            }
            return eCFieldElement2.equals(eCFieldElement3.square().add(eCFieldElement4).multiply(eCFieldElement3).add(eCFieldElement));
        }

        @Override
        public ECPoint subtract(ECPoint eCPoint) {
            if (eCPoint.isInfinity()) {
                return this;
            }
            return this.add(eCPoint.negate());
        }
    }

    public static class F2m
    extends AbstractF2m {
        public F2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
            boolean bl2 = true;
            boolean bl3 = eCFieldElement == null;
            if (eCFieldElement2 != null) {
                bl2 = false;
            }
            if (bl3 == bl2) {
                if (eCFieldElement != null) {
                    ECFieldElement.F2m.checkFieldElements(this.x, this.y);
                    if (eCCurve != null) {
                        ECFieldElement.F2m.checkFieldElements(this.x, this.curve.getA());
                    }
                }
                this.withCompression = bl;
                return;
            }
            throw new IllegalArgumentException("Exactly one of the field elements is null");
        }

        F2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
            super(eCCurve, eCFieldElement, eCFieldElement2, arreCFieldElement);
            this.withCompression = bl;
        }

        @Override
        public ECPoint add(ECPoint object) {
            if (this.isInfinity()) {
                return object;
            }
            if (((ECPoint)object).isInfinity()) {
                return this;
            }
            ECCurve eCCurve = this.getCurve();
            int n = eCCurve.getCoordinateSystem();
            Object object2 = this.x;
            ECFieldElement eCFieldElement = ((ECPoint)object).x;
            if (n != 0) {
                if (n != 1) {
                    if (n == 6) {
                        ECFieldElement eCFieldElement2;
                        if (((ECFieldElement)object2).isZero()) {
                            if (eCFieldElement.isZero()) {
                                return eCCurve.getInfinity();
                            }
                            return ((ECPoint)object).add(this);
                        }
                        ECFieldElement eCFieldElement3 = this.y;
                        ECFieldElement eCFieldElement4 = this.zs[0];
                        ECFieldElement eCFieldElement5 = ((ECPoint)object).y;
                        ECFieldElement eCFieldElement6 = ((ECPoint)object).zs[0];
                        boolean bl = eCFieldElement4.isOne();
                        if (!bl) {
                            object = eCFieldElement.multiply(eCFieldElement4);
                            eCFieldElement2 = eCFieldElement5.multiply(eCFieldElement4);
                        } else {
                            object = eCFieldElement;
                            eCFieldElement2 = eCFieldElement5;
                        }
                        boolean bl2 = eCFieldElement6.isOne();
                        ECFieldElement eCFieldElement7 = object2;
                        ECFieldElement eCFieldElement8 = eCFieldElement3;
                        object2 = eCFieldElement7;
                        ECFieldElement eCFieldElement9 = eCFieldElement8;
                        if (!bl2) {
                            object2 = eCFieldElement7.multiply(eCFieldElement6);
                            eCFieldElement9 = eCFieldElement8.multiply(eCFieldElement6);
                        }
                        eCFieldElement2 = eCFieldElement9.add(eCFieldElement2);
                        eCFieldElement9 = ((ECFieldElement)object2).add((ECFieldElement)object);
                        if (eCFieldElement9.isZero()) {
                            if (eCFieldElement2.isZero()) {
                                return this.twice();
                            }
                            return eCCurve.getInfinity();
                        }
                        if (eCFieldElement.isZero()) {
                            object2 = this.normalize();
                            object = ((ECPoint)object2).getXCoord();
                            eCFieldElement2 = ((ECPoint)object2).getYCoord();
                            eCFieldElement9 = eCFieldElement2.add(eCFieldElement5).divide((ECFieldElement)object);
                            object2 = eCFieldElement9.square().add(eCFieldElement9).add((ECFieldElement)object).add(eCCurve.getA());
                            if (((ECFieldElement)object2).isZero()) {
                                return new F2m(eCCurve, (ECFieldElement)object2, eCCurve.getB().sqrt(), this.withCompression);
                            }
                            eCFieldElement2 = eCFieldElement9.multiply(((ECFieldElement)object).add((ECFieldElement)object2)).add((ECFieldElement)object2).add(eCFieldElement2).divide((ECFieldElement)object2).add((ECFieldElement)object2);
                            object = eCCurve.fromBigInteger(ECConstants.ONE);
                        } else {
                            eCFieldElement9 = eCFieldElement9.square();
                            object2 = eCFieldElement2.multiply((ECFieldElement)object2);
                            eCFieldElement8 = eCFieldElement2.multiply((ECFieldElement)object);
                            if (((ECFieldElement)(object2 = ((ECFieldElement)object2).multiply(eCFieldElement8))).isZero()) {
                                return new F2m(eCCurve, (ECFieldElement)object2, eCCurve.getB().sqrt(), this.withCompression);
                            }
                            eCFieldElement2 = eCFieldElement2.multiply(eCFieldElement9);
                            object = eCFieldElement2;
                            if (!bl2) {
                                object = eCFieldElement2.multiply(eCFieldElement6);
                            }
                            eCFieldElement2 = eCFieldElement8.add(eCFieldElement9).squarePlusProduct((ECFieldElement)object, eCFieldElement3.add(eCFieldElement4));
                            if (!bl) {
                                object = ((ECFieldElement)object).multiply(eCFieldElement4);
                            }
                        }
                        bl2 = this.withCompression;
                        return new F2m(eCCurve, (ECFieldElement)object2, eCFieldElement2, new ECFieldElement[]{object}, bl2);
                    }
                    throw new IllegalStateException("unsupported coordinate system");
                }
                ECFieldElement eCFieldElement10 = this.y;
                ECFieldElement eCFieldElement11 = this.zs[0];
                ECFieldElement eCFieldElement12 = ((ECPoint)object).y;
                ECFieldElement eCFieldElement13 = ((ECPoint)object).zs[0];
                boolean bl = eCFieldElement13.isOne();
                eCFieldElement12 = eCFieldElement11.multiply(eCFieldElement12);
                object = bl ? eCFieldElement10 : eCFieldElement10.multiply(eCFieldElement13);
                ECFieldElement eCFieldElement14 = eCFieldElement12.add((ECFieldElement)object);
                eCFieldElement12 = eCFieldElement11.multiply(eCFieldElement);
                ECFieldElement eCFieldElement15 = eCFieldElement12.add((ECFieldElement)(object = bl ? object2 : ((ECFieldElement)object2).multiply(eCFieldElement13)));
                if (eCFieldElement15.isZero()) {
                    if (eCFieldElement14.isZero()) {
                        return this.twice();
                    }
                    return eCCurve.getInfinity();
                }
                eCFieldElement12 = eCFieldElement15.square();
                eCFieldElement = eCFieldElement12.multiply(eCFieldElement15);
                object = bl ? eCFieldElement11 : eCFieldElement11.multiply(eCFieldElement13);
                ECFieldElement eCFieldElement16 = eCFieldElement14.add(eCFieldElement15);
                ECFieldElement eCFieldElement17 = eCFieldElement16.multiplyPlusProduct(eCFieldElement14, eCFieldElement12, eCCurve.getA()).multiply((ECFieldElement)object).add(eCFieldElement);
                ECFieldElement eCFieldElement18 = eCFieldElement15.multiply(eCFieldElement17);
                eCFieldElement11 = bl ? eCFieldElement12 : eCFieldElement12.multiply(eCFieldElement13);
                object2 = eCFieldElement14.multiplyPlusProduct((ECFieldElement)object2, eCFieldElement15, eCFieldElement10).multiplyPlusProduct(eCFieldElement11, eCFieldElement16, eCFieldElement17);
                object = eCFieldElement.multiply((ECFieldElement)object);
                bl = this.withCompression;
                return new F2m(eCCurve, eCFieldElement18, (ECFieldElement)object2, new ECFieldElement[]{object}, bl);
            }
            ECFieldElement eCFieldElement19 = this.y;
            ECFieldElement eCFieldElement20 = ((ECPoint)object).y;
            object = ((ECFieldElement)object2).add(eCFieldElement);
            eCFieldElement20 = eCFieldElement19.add(eCFieldElement20);
            if (((ECFieldElement)object).isZero()) {
                if (eCFieldElement20.isZero()) {
                    return this.twice();
                }
                return eCCurve.getInfinity();
            }
            eCFieldElement20 = eCFieldElement20.divide((ECFieldElement)object);
            object = eCFieldElement20.square().add(eCFieldElement20).add((ECFieldElement)object).add(eCCurve.getA());
            return new F2m(eCCurve, (ECFieldElement)object, eCFieldElement20.multiply(((ECFieldElement)object2).add((ECFieldElement)object)).add((ECFieldElement)object).add(eCFieldElement19), this.withCompression);
        }

        @Override
        protected ECPoint detach() {
            return new F2m(null, this.getAffineXCoord(), this.getAffineYCoord(), false);
        }

        @Override
        protected boolean getCompressionYTilde() {
            ECFieldElement eCFieldElement = this.getRawXCoord();
            boolean bl = eCFieldElement.isZero();
            boolean bl2 = false;
            if (bl) {
                return false;
            }
            ECFieldElement eCFieldElement2 = this.getRawYCoord();
            int n = this.getCurveCoordinateSystem();
            if (n != 5 && n != 6) {
                return eCFieldElement2.divide(eCFieldElement).testBitZero();
            }
            if (eCFieldElement2.testBitZero() != eCFieldElement.testBitZero()) {
                bl2 = true;
            }
            return bl2;
        }

        @Override
        public ECFieldElement getYCoord() {
            int n = this.getCurveCoordinateSystem();
            if (n != 5 && n != 6) {
                return this.y;
            }
            ECFieldElement eCFieldElement = this.x;
            ECFieldElement eCFieldElement2 = this.y;
            if (!this.isInfinity() && !eCFieldElement.isZero()) {
                eCFieldElement = eCFieldElement2 = eCFieldElement2.add(eCFieldElement).multiply(eCFieldElement);
                if (6 == n) {
                    ECFieldElement eCFieldElement3 = this.zs[0];
                    eCFieldElement = eCFieldElement2;
                    if (!eCFieldElement3.isOne()) {
                        eCFieldElement = eCFieldElement2.divide(eCFieldElement3);
                    }
                }
                return eCFieldElement;
            }
            return eCFieldElement2;
        }

        @Override
        public ECPoint negate() {
            if (this.isInfinity()) {
                return this;
            }
            ECFieldElement eCFieldElement = this.x;
            if (eCFieldElement.isZero()) {
                return this;
            }
            int n = this.getCurveCoordinateSystem();
            if (n != 0) {
                if (n != 1) {
                    if (n != 5) {
                        if (n == 6) {
                            ECFieldElement eCFieldElement2 = this.y;
                            ECFieldElement eCFieldElement3 = this.zs[0];
                            ECCurve eCCurve = this.curve;
                            eCFieldElement2 = eCFieldElement2.add(eCFieldElement3);
                            boolean bl = this.withCompression;
                            return new F2m(eCCurve, eCFieldElement, eCFieldElement2, new ECFieldElement[]{eCFieldElement3}, bl);
                        }
                        throw new IllegalStateException("unsupported coordinate system");
                    }
                    ECFieldElement eCFieldElement4 = this.y;
                    return new F2m(this.curve, eCFieldElement, eCFieldElement4.addOne(), this.withCompression);
                }
                ECFieldElement eCFieldElement5 = this.y;
                ECFieldElement eCFieldElement6 = this.zs[0];
                ECCurve eCCurve = this.curve;
                eCFieldElement5 = eCFieldElement5.add(eCFieldElement);
                boolean bl = this.withCompression;
                return new F2m(eCCurve, eCFieldElement, eCFieldElement5, new ECFieldElement[]{eCFieldElement6}, bl);
            }
            ECFieldElement eCFieldElement7 = this.y;
            return new F2m(this.curve, eCFieldElement, eCFieldElement7.add(eCFieldElement), this.withCompression);
        }

        @Override
        public ECPoint twice() {
            if (this.isInfinity()) {
                return this;
            }
            ECCurve eCCurve = this.getCurve();
            ECFieldElement eCFieldElement = this.x;
            if (eCFieldElement.isZero()) {
                return eCCurve.getInfinity();
            }
            int n = eCCurve.getCoordinateSystem();
            if (n != 0) {
                if (n != 1) {
                    if (n == 6) {
                        ECFieldElement eCFieldElement2 = this.y;
                        ECFieldElement eCFieldElement3 = this.zs[0];
                        boolean bl = eCFieldElement3.isOne();
                        ECFieldElement eCFieldElement4 = bl ? eCFieldElement2 : eCFieldElement2.multiply(eCFieldElement3);
                        ECFieldElement eCFieldElement5 = bl ? eCFieldElement3 : eCFieldElement3.square();
                        ECFieldElement eCFieldElement6 = eCCurve.getA();
                        ECFieldElement eCFieldElement7 = bl ? eCFieldElement6 : eCFieldElement6.multiply(eCFieldElement5);
                        ECFieldElement eCFieldElement8 = eCFieldElement2.square().add(eCFieldElement4).add(eCFieldElement7);
                        if (eCFieldElement8.isZero()) {
                            return new F2m(eCCurve, eCFieldElement8, eCCurve.getB().sqrt(), this.withCompression);
                        }
                        ECFieldElement eCFieldElement9 = eCFieldElement8.square();
                        ECFieldElement eCFieldElement10 = bl ? eCFieldElement8 : eCFieldElement8.multiply(eCFieldElement5);
                        ECFieldElement eCFieldElement11 = eCCurve.getB();
                        if (eCFieldElement11.bitLength() < eCCurve.getFieldSize() >> 1) {
                            eCFieldElement4 = eCFieldElement2.add(eCFieldElement).square();
                            eCFieldElement = eCFieldElement11.isOne() ? eCFieldElement7.add(eCFieldElement5).square() : eCFieldElement7.squarePlusProduct(eCFieldElement11, eCFieldElement5.square());
                            eCFieldElement = eCFieldElement4.add(eCFieldElement8).add(eCFieldElement5).multiply(eCFieldElement4).add(eCFieldElement).add(eCFieldElement9);
                            if (eCFieldElement6.isZero()) {
                                eCFieldElement = eCFieldElement.add(eCFieldElement10);
                            } else if (!eCFieldElement6.isOne()) {
                                eCFieldElement = eCFieldElement.add(eCFieldElement6.addOne().multiply(eCFieldElement10));
                            }
                        } else {
                            if (!bl) {
                                eCFieldElement = eCFieldElement.multiply(eCFieldElement3);
                            }
                            eCFieldElement = eCFieldElement.squarePlusProduct(eCFieldElement8, eCFieldElement4).add(eCFieldElement9).add(eCFieldElement10);
                        }
                        bl = this.withCompression;
                        return new F2m(eCCurve, eCFieldElement9, eCFieldElement, new ECFieldElement[]{eCFieldElement10}, bl);
                    }
                    throw new IllegalStateException("unsupported coordinate system");
                }
                ECFieldElement eCFieldElement12 = this.y;
                ECFieldElement eCFieldElement13 = this.zs[0];
                boolean bl = eCFieldElement13.isOne();
                ECFieldElement eCFieldElement14 = bl ? eCFieldElement : eCFieldElement.multiply(eCFieldElement13);
                if (!bl) {
                    eCFieldElement12 = eCFieldElement12.multiply(eCFieldElement13);
                }
                eCFieldElement13 = eCFieldElement.square();
                eCFieldElement = eCFieldElement13.add(eCFieldElement12);
                eCFieldElement12 = eCFieldElement14.square();
                ECFieldElement eCFieldElement15 = eCFieldElement.add(eCFieldElement14);
                ECFieldElement eCFieldElement16 = eCFieldElement15.multiplyPlusProduct(eCFieldElement, eCFieldElement12, eCCurve.getA());
                eCFieldElement = eCFieldElement14.multiply(eCFieldElement16);
                eCFieldElement13 = eCFieldElement13.square().multiplyPlusProduct(eCFieldElement14, eCFieldElement16, eCFieldElement15);
                eCFieldElement14 = eCFieldElement14.multiply(eCFieldElement12);
                bl = this.withCompression;
                return new F2m(eCCurve, eCFieldElement, eCFieldElement13, new ECFieldElement[]{eCFieldElement14}, bl);
            }
            ECFieldElement eCFieldElement17 = this.y.divide(eCFieldElement).add(eCFieldElement);
            ECFieldElement eCFieldElement18 = eCFieldElement17.square().add(eCFieldElement17).add(eCCurve.getA());
            return new F2m(eCCurve, eCFieldElement18, eCFieldElement.squarePlusProduct(eCFieldElement18, eCFieldElement17.addOne()), this.withCompression);
        }

        @Override
        public ECPoint twicePlus(ECPoint object) {
            if (this.isInfinity()) {
                return object;
            }
            if (((ECPoint)object).isInfinity()) {
                return this.twice();
            }
            ECCurve eCCurve = this.getCurve();
            ECFieldElement eCFieldElement = this.x;
            if (eCFieldElement.isZero()) {
                return object;
            }
            if (eCCurve.getCoordinateSystem() != 6) {
                return this.twice().add((ECPoint)object);
            }
            ECFieldElement eCFieldElement2 = ((ECPoint)object).x;
            ECFieldElement eCFieldElement3 = ((ECPoint)object).zs[0];
            if (!eCFieldElement2.isZero() && eCFieldElement3.isOne()) {
                eCFieldElement3 = this.y;
                ECFieldElement eCFieldElement4 = this.zs[0];
                ECFieldElement eCFieldElement5 = ((ECPoint)object).y;
                ECFieldElement eCFieldElement6 = eCFieldElement.square();
                ECFieldElement eCFieldElement7 = eCFieldElement3.square();
                eCFieldElement = eCFieldElement4.square();
                eCFieldElement3 = eCFieldElement3.multiply(eCFieldElement4);
                eCFieldElement3 = eCCurve.getA().multiply(eCFieldElement).add(eCFieldElement7).add(eCFieldElement3);
                eCFieldElement5 = eCFieldElement5.addOne();
                eCFieldElement6 = eCCurve.getA().add(eCFieldElement5).multiply(eCFieldElement).add(eCFieldElement7).multiplyPlusProduct(eCFieldElement3, eCFieldElement6, eCFieldElement);
                eCFieldElement7 = eCFieldElement2.multiply(eCFieldElement);
                eCFieldElement2 = eCFieldElement7.add(eCFieldElement3).square();
                if (eCFieldElement2.isZero()) {
                    if (eCFieldElement6.isZero()) {
                        return ((ECPoint)object).twice();
                    }
                    return eCCurve.getInfinity();
                }
                if (eCFieldElement6.isZero()) {
                    return new F2m(eCCurve, eCFieldElement6, eCCurve.getB().sqrt(), this.withCompression);
                }
                object = eCFieldElement6.square().multiply(eCFieldElement7);
                eCFieldElement = eCFieldElement6.multiply(eCFieldElement2).multiply(eCFieldElement);
                eCFieldElement2 = eCFieldElement6.add(eCFieldElement2).square().multiplyPlusProduct(eCFieldElement3, eCFieldElement5, eCFieldElement);
                boolean bl = this.withCompression;
                return new F2m(eCCurve, (ECFieldElement)object, eCFieldElement2, new ECFieldElement[]{eCFieldElement}, bl);
            }
            return this.twice().add((ECPoint)object);
        }
    }

    public static class Fp
    extends AbstractFp {
        public Fp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
            boolean bl2 = true;
            boolean bl3 = eCFieldElement == null;
            if (eCFieldElement2 != null) {
                bl2 = false;
            }
            if (bl3 == bl2) {
                this.withCompression = bl;
                return;
            }
            throw new IllegalArgumentException("Exactly one of the field elements is null");
        }

        Fp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
            super(eCCurve, eCFieldElement, eCFieldElement2, arreCFieldElement);
            this.withCompression = bl;
        }

        @Override
        public ECPoint add(ECPoint object) {
            if (this.isInfinity()) {
                return object;
            }
            if (object.isInfinity()) {
                return this;
            }
            if (this == object) {
                return this.twice();
            }
            ECCurve eCCurve = this.getCurve();
            int n = eCCurve.getCoordinateSystem();
            Object object2 = this.x;
            ECFieldElement eCFieldElement = this.y;
            ECFieldElement eCFieldElement2 = object.x;
            ECFieldElement eCFieldElement3 = object.y;
            if (n != 0) {
                if (n != 1) {
                    if (n != 2 && n != 4) {
                        throw new IllegalStateException("unsupported coordinate system");
                    }
                    ECFieldElement eCFieldElement4 = this.zs[0];
                    ECFieldElement eCFieldElement5 = object.zs[0];
                    boolean bl = eCFieldElement4.isOne();
                    if (!bl && eCFieldElement4.equals(eCFieldElement5)) {
                        ECFieldElement eCFieldElement6 = object2.subtract(eCFieldElement2);
                        eCFieldElement3 = eCFieldElement.subtract(eCFieldElement3);
                        if (eCFieldElement6.isZero()) {
                            if (eCFieldElement3.isZero()) {
                                return this.twice();
                            }
                            return eCCurve.getInfinity();
                        }
                        object = eCFieldElement6.square();
                        object2 = object2.multiply((ECFieldElement)object);
                        object = eCFieldElement2.multiply((ECFieldElement)object);
                        eCFieldElement = object2.subtract((ECFieldElement)object).multiply(eCFieldElement);
                        object = eCFieldElement3.square().subtract((ECFieldElement)object2).subtract((ECFieldElement)object);
                        eCFieldElement = object2.subtract((ECFieldElement)object).multiply(eCFieldElement3).subtract(eCFieldElement);
                        object2 = eCFieldElement6.multiply(eCFieldElement4);
                        eCFieldElement2 = null;
                    } else {
                        ECFieldElement eCFieldElement7;
                        if (bl) {
                            eCFieldElement7 = eCFieldElement4;
                            object = eCFieldElement3;
                            eCFieldElement3 = eCFieldElement7;
                        } else {
                            eCFieldElement7 = eCFieldElement4.square();
                            eCFieldElement2 = eCFieldElement7.multiply(eCFieldElement2);
                            object = eCFieldElement7.multiply(eCFieldElement4).multiply(eCFieldElement3);
                            eCFieldElement3 = eCFieldElement7;
                        }
                        boolean bl2 = eCFieldElement5.isOne();
                        if (bl2) {
                            eCFieldElement3 = eCFieldElement5;
                            eCFieldElement7 = object2;
                            object2 = eCFieldElement;
                            eCFieldElement = eCFieldElement7;
                        } else {
                            eCFieldElement3 = eCFieldElement5.square();
                            eCFieldElement7 = eCFieldElement3.multiply((ECFieldElement)object2);
                            object2 = eCFieldElement3.multiply(eCFieldElement5).multiply(eCFieldElement);
                            eCFieldElement = eCFieldElement7;
                        }
                        eCFieldElement7 = eCFieldElement.subtract(eCFieldElement2);
                        object = object2.subtract((ECFieldElement)object);
                        if (eCFieldElement7.isZero()) {
                            if (object.isZero()) {
                                return this.twice();
                            }
                            return eCCurve.getInfinity();
                        }
                        eCFieldElement3 = eCFieldElement7.square();
                        eCFieldElement2 = eCFieldElement3.multiply(eCFieldElement7);
                        ECFieldElement eCFieldElement8 = eCFieldElement3.multiply(eCFieldElement);
                        eCFieldElement = object.square().add(eCFieldElement2).subtract(this.two(eCFieldElement8));
                        eCFieldElement2 = eCFieldElement8.subtract(eCFieldElement).multiplyMinusProduct((ECFieldElement)object, eCFieldElement2, (ECFieldElement)object2);
                        object2 = !bl ? eCFieldElement7.multiply(eCFieldElement4) : eCFieldElement7;
                        object = object2;
                        if (!bl2) {
                            object = object2.multiply(eCFieldElement5);
                        }
                        if (object == eCFieldElement7) {
                            object2 = object;
                            object = eCFieldElement;
                            eCFieldElement = eCFieldElement2;
                            eCFieldElement2 = eCFieldElement3;
                        } else {
                            object2 = object;
                            object = eCFieldElement;
                            eCFieldElement = eCFieldElement2;
                            eCFieldElement2 = null;
                        }
                    }
                    object2 = n == 4 ? new ECFieldElement[]{object2, this.calculateJacobianModifiedW((ECFieldElement)object2, eCFieldElement2)} : new ECFieldElement[]{object2};
                    return new Fp(eCCurve, (ECFieldElement)object, eCFieldElement, (ECFieldElement[])object2, this.withCompression);
                }
                ECFieldElement eCFieldElement9 = this.zs[0];
                ECFieldElement eCFieldElement10 = object.zs[0];
                boolean bl = eCFieldElement9.isOne();
                boolean bl3 = eCFieldElement10.isOne();
                object = bl ? eCFieldElement3 : eCFieldElement3.multiply(eCFieldElement9);
                if (!bl3) {
                    eCFieldElement = eCFieldElement.multiply(eCFieldElement10);
                }
                eCFieldElement3 = object.subtract(eCFieldElement);
                object = bl ? eCFieldElement2 : eCFieldElement2.multiply(eCFieldElement9);
                if (!bl3) {
                    object2 = object2.multiply(eCFieldElement10);
                }
                eCFieldElement2 = object.subtract((ECFieldElement)object2);
                if (eCFieldElement2.isZero()) {
                    if (eCFieldElement3.isZero()) {
                        return this.twice();
                    }
                    return eCCurve.getInfinity();
                }
                object = bl ? eCFieldElement10 : (bl3 ? eCFieldElement9 : eCFieldElement9.multiply(eCFieldElement10));
                eCFieldElement10 = eCFieldElement2.square();
                eCFieldElement9 = eCFieldElement10.multiply(eCFieldElement2);
                ECFieldElement eCFieldElement11 = eCFieldElement10.multiply((ECFieldElement)object2);
                eCFieldElement10 = eCFieldElement3.square().multiply((ECFieldElement)object).subtract(eCFieldElement9).subtract(this.two(eCFieldElement11));
                object2 = eCFieldElement2.multiply(eCFieldElement10);
                eCFieldElement = eCFieldElement11.subtract(eCFieldElement10).multiplyMinusProduct(eCFieldElement3, eCFieldElement, eCFieldElement9);
                object = eCFieldElement9.multiply((ECFieldElement)object);
                bl3 = this.withCompression;
                return new Fp(eCCurve, (ECFieldElement)object2, eCFieldElement, new ECFieldElement[]{object}, bl3);
            }
            object = eCFieldElement2.subtract((ECFieldElement)object2);
            eCFieldElement3 = eCFieldElement3.subtract(eCFieldElement);
            if (object.isZero()) {
                if (eCFieldElement3.isZero()) {
                    return this.twice();
                }
                return eCCurve.getInfinity();
            }
            object = eCFieldElement3.divide((ECFieldElement)object);
            eCFieldElement2 = object.square().subtract((ECFieldElement)object2).subtract(eCFieldElement2);
            return new Fp(eCCurve, eCFieldElement2, object.multiply(object2.subtract(eCFieldElement2)).subtract(eCFieldElement), this.withCompression);
        }

        protected ECFieldElement calculateJacobianModifiedW(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            ECFieldElement eCFieldElement3 = this.getCurve().getA();
            if (!eCFieldElement3.isZero() && !eCFieldElement.isOne()) {
                ECFieldElement eCFieldElement4 = eCFieldElement2;
                if (eCFieldElement2 == null) {
                    eCFieldElement4 = eCFieldElement.square();
                }
                eCFieldElement2 = eCFieldElement4.square();
                eCFieldElement = eCFieldElement3.negate();
                eCFieldElement = eCFieldElement.bitLength() < eCFieldElement3.bitLength() ? eCFieldElement2.multiply(eCFieldElement).negate() : eCFieldElement2.multiply(eCFieldElement3);
                return eCFieldElement;
            }
            return eCFieldElement3;
        }

        @Override
        protected ECPoint detach() {
            return new Fp(null, this.getAffineXCoord(), this.getAffineYCoord(), false);
        }

        protected ECFieldElement doubleProductFromSquares(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3, ECFieldElement eCFieldElement4) {
            return eCFieldElement.add(eCFieldElement2).square().subtract(eCFieldElement3).subtract(eCFieldElement4);
        }

        protected ECFieldElement eight(ECFieldElement eCFieldElement) {
            return this.four(this.two(eCFieldElement));
        }

        protected ECFieldElement four(ECFieldElement eCFieldElement) {
            return this.two(this.two(eCFieldElement));
        }

        protected ECFieldElement getJacobianModifiedW() {
            ECFieldElement eCFieldElement;
            ECFieldElement eCFieldElement2 = eCFieldElement = this.zs[1];
            if (eCFieldElement == null) {
                ECFieldElement[] arreCFieldElement = this.zs;
                eCFieldElement2 = eCFieldElement = this.calculateJacobianModifiedW(this.zs[0], null);
                arreCFieldElement[1] = eCFieldElement;
            }
            return eCFieldElement2;
        }

        @Override
        public ECFieldElement getZCoord(int n) {
            if (n == 1 && 4 == this.getCurveCoordinateSystem()) {
                return this.getJacobianModifiedW();
            }
            return super.getZCoord(n);
        }

        @Override
        public ECPoint negate() {
            if (this.isInfinity()) {
                return this;
            }
            ECCurve eCCurve = this.getCurve();
            if (eCCurve.getCoordinateSystem() != 0) {
                return new Fp(eCCurve, this.x, this.y.negate(), this.zs, this.withCompression);
            }
            return new Fp(eCCurve, this.x, this.y.negate(), this.withCompression);
        }

        protected ECFieldElement three(ECFieldElement eCFieldElement) {
            return this.two(eCFieldElement).add(eCFieldElement);
        }

        @Override
        public ECPoint threeTimes() {
            if (this.isInfinity()) {
                return this;
            }
            ECFieldElement eCFieldElement = this.y;
            if (eCFieldElement.isZero()) {
                return this;
            }
            ECCurve eCCurve = this.getCurve();
            int n = eCCurve.getCoordinateSystem();
            if (n != 0) {
                if (n != 4) {
                    return this.twice().add(this);
                }
                return this.twiceJacobianModified(false).add(this);
            }
            ECFieldElement eCFieldElement2 = this.x;
            ECFieldElement eCFieldElement3 = this.two(eCFieldElement);
            ECFieldElement eCFieldElement4 = eCFieldElement3.square();
            ECFieldElement eCFieldElement5 = this.three(eCFieldElement2.square()).add(this.getCurve().getA());
            ECFieldElement eCFieldElement6 = eCFieldElement5.square();
            eCFieldElement6 = this.three(eCFieldElement2).multiply(eCFieldElement4).subtract(eCFieldElement6);
            if (eCFieldElement6.isZero()) {
                return this.getCurve().getInfinity();
            }
            eCFieldElement3 = eCFieldElement6.multiply(eCFieldElement3).invert();
            eCFieldElement5 = eCFieldElement6.multiply(eCFieldElement3).multiply(eCFieldElement5);
            eCFieldElement4 = eCFieldElement4.square().multiply(eCFieldElement3).subtract(eCFieldElement5);
            eCFieldElement5 = eCFieldElement4.subtract(eCFieldElement5).multiply(eCFieldElement5.add(eCFieldElement4)).add(eCFieldElement2);
            return new Fp(eCCurve, eCFieldElement5, eCFieldElement2.subtract(eCFieldElement5).multiply(eCFieldElement4).subtract(eCFieldElement), this.withCompression);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public ECPoint timesPow2(int n) {
            int n2 = n;
            if (n2 < 0) throw new IllegalArgumentException("'e' cannot be negative");
            if (n2 == 0 || this.isInfinity()) return this;
            if (n2 == 1) {
                return this.twice();
            }
            ECCurve eCCurve = this.getCurve();
            ECFieldElement eCFieldElement = this.y;
            if (eCFieldElement.isZero()) {
                return eCCurve.getInfinity();
            }
            int n3 = eCCurve.getCoordinateSystem();
            ECFieldElement eCFieldElement2 = eCCurve.getA();
            ECFieldElement eCFieldElement3 = this.x;
            ECFieldElement eCFieldElement4 = this.zs.length < 1 ? eCCurve.fromBigInteger(ECConstants.ONE) : this.zs[0];
            ECFieldElement eCFieldElement5 = eCFieldElement;
            ECFieldElement eCFieldElement6 = eCFieldElement2;
            ECFieldElement eCFieldElement7 = eCFieldElement3;
            if (!eCFieldElement4.isOne()) {
                eCFieldElement5 = eCFieldElement;
                eCFieldElement6 = eCFieldElement2;
                eCFieldElement7 = eCFieldElement3;
                if (n3 != 0) {
                    if (n3 != 1) {
                        if (n3 != 2) {
                            if (n3 != 4) throw new IllegalStateException("unsupported coordinate system");
                            eCFieldElement6 = this.getJacobianModifiedW();
                            eCFieldElement5 = eCFieldElement;
                            eCFieldElement7 = eCFieldElement3;
                        } else {
                            eCFieldElement6 = this.calculateJacobianModifiedW(eCFieldElement4, null);
                            eCFieldElement5 = eCFieldElement;
                            eCFieldElement7 = eCFieldElement3;
                        }
                    } else {
                        eCFieldElement6 = eCFieldElement4.square();
                        eCFieldElement7 = eCFieldElement3.multiply(eCFieldElement4);
                        eCFieldElement5 = eCFieldElement.multiply(eCFieldElement6);
                        eCFieldElement6 = this.calculateJacobianModifiedW(eCFieldElement4, eCFieldElement6);
                    }
                }
            }
            eCFieldElement3 = eCFieldElement6;
            eCFieldElement6 = eCFieldElement7;
            eCFieldElement = eCFieldElement4;
            eCFieldElement4 = eCFieldElement3;
            eCFieldElement7 = eCFieldElement5;
            eCFieldElement5 = eCFieldElement6;
            eCFieldElement6 = eCFieldElement;
            for (n2 = 0; n2 < n; ++n2) {
                if (eCFieldElement7.isZero()) {
                    return eCCurve.getInfinity();
                }
                eCFieldElement3 = this.three(eCFieldElement5.square());
                eCFieldElement = this.two(eCFieldElement7);
                eCFieldElement7 = eCFieldElement.multiply(eCFieldElement7);
                eCFieldElement2 = this.two(eCFieldElement5.multiply(eCFieldElement7));
                ECFieldElement eCFieldElement8 = this.two(eCFieldElement7.square());
                if (!eCFieldElement4.isZero()) {
                    eCFieldElement7 = eCFieldElement3.add(eCFieldElement4);
                    eCFieldElement4 = this.two(eCFieldElement8.multiply(eCFieldElement4));
                } else {
                    eCFieldElement7 = eCFieldElement3;
                }
                eCFieldElement5 = eCFieldElement7.square().subtract(this.two(eCFieldElement2));
                eCFieldElement7 = eCFieldElement7.multiply(eCFieldElement2.subtract(eCFieldElement5)).subtract(eCFieldElement8);
                eCFieldElement6 = eCFieldElement6.isOne() ? eCFieldElement : eCFieldElement.multiply(eCFieldElement6);
            }
            if (n3 != 0) {
                if (n3 != 1) {
                    if (n3 != 2) {
                        if (n3 != 4) throw new IllegalStateException("unsupported coordinate system");
                        boolean bl = this.withCompression;
                        return new Fp(eCCurve, eCFieldElement5, eCFieldElement7, new ECFieldElement[]{eCFieldElement6, eCFieldElement4}, bl);
                    }
                    boolean bl = this.withCompression;
                    return new Fp(eCCurve, eCFieldElement5, eCFieldElement7, new ECFieldElement[]{eCFieldElement6}, bl);
                }
                eCFieldElement4 = eCFieldElement5.multiply(eCFieldElement6);
                eCFieldElement6 = eCFieldElement6.multiply(eCFieldElement6.square());
                boolean bl = this.withCompression;
                return new Fp(eCCurve, eCFieldElement4, eCFieldElement7, new ECFieldElement[]{eCFieldElement6}, bl);
            }
            eCFieldElement4 = eCFieldElement6.invert();
            eCFieldElement6 = eCFieldElement4.square();
            eCFieldElement4 = eCFieldElement6.multiply(eCFieldElement4);
            return new Fp(eCCurve, eCFieldElement5.multiply(eCFieldElement6), eCFieldElement7.multiply(eCFieldElement4), this.withCompression);
        }

        @Override
        public ECPoint twice() {
            if (this.isInfinity()) {
                return this;
            }
            ECCurve eCCurve = this.getCurve();
            ECFieldElement eCFieldElement = this.y;
            if (eCFieldElement.isZero()) {
                return eCCurve.getInfinity();
            }
            int n = eCCurve.getCoordinateSystem();
            ECFieldElement eCFieldElement2 = this.x;
            if (n != 0) {
                ECFieldElement eCFieldElement3;
                if (n != 1) {
                    ECFieldElement eCFieldElement4;
                    if (n != 2) {
                        if (n == 4) {
                            return this.twiceJacobianModified(true);
                        }
                        throw new IllegalStateException("unsupported coordinate system");
                    }
                    ECFieldElement eCFieldElement5 = this.zs[0];
                    boolean bl = eCFieldElement5.isOne();
                    ECFieldElement eCFieldElement6 = eCFieldElement.square();
                    ECFieldElement eCFieldElement7 = eCFieldElement6.square();
                    ECFieldElement eCFieldElement8 = eCCurve.getA();
                    ECFieldElement eCFieldElement9 = eCFieldElement8.negate();
                    if (eCFieldElement9.toBigInteger().equals(BigInteger.valueOf(3L))) {
                        eCFieldElement4 = bl ? eCFieldElement5 : eCFieldElement5.square();
                        eCFieldElement4 = this.three(eCFieldElement2.add(eCFieldElement4).multiply(eCFieldElement2.subtract(eCFieldElement4)));
                        eCFieldElement6 = this.four(eCFieldElement6.multiply(eCFieldElement2));
                    } else {
                        eCFieldElement4 = this.three(eCFieldElement2.square());
                        if (bl) {
                            eCFieldElement4 = eCFieldElement4.add(eCFieldElement8);
                        } else if (!eCFieldElement8.isZero()) {
                            ECFieldElement eCFieldElement10 = eCFieldElement5.square().square();
                            eCFieldElement4 = eCFieldElement9.bitLength() < eCFieldElement8.bitLength() ? eCFieldElement4.subtract(eCFieldElement10.multiply(eCFieldElement9)) : eCFieldElement4.add(eCFieldElement10.multiply(eCFieldElement8));
                        }
                        eCFieldElement6 = this.four(eCFieldElement2.multiply(eCFieldElement6));
                    }
                    eCFieldElement2 = eCFieldElement4.square().subtract(this.two(eCFieldElement6));
                    eCFieldElement6 = eCFieldElement6.subtract(eCFieldElement2).multiply(eCFieldElement4).subtract(this.eight(eCFieldElement7));
                    eCFieldElement4 = this.two(eCFieldElement);
                    if (!bl) {
                        eCFieldElement4 = eCFieldElement4.multiply(eCFieldElement5);
                    }
                    bl = this.withCompression;
                    return new Fp(eCCurve, eCFieldElement2, eCFieldElement6, new ECFieldElement[]{eCFieldElement4}, bl);
                }
                ECFieldElement eCFieldElement11 = this.zs[0];
                boolean bl = eCFieldElement11.isOne();
                ECFieldElement eCFieldElement12 = eCFieldElement3 = eCCurve.getA();
                if (!eCFieldElement3.isZero()) {
                    eCFieldElement12 = eCFieldElement3;
                    if (!bl) {
                        eCFieldElement12 = eCFieldElement3.multiply(eCFieldElement11.square());
                    }
                }
                ECFieldElement eCFieldElement13 = eCFieldElement12.add(this.three(eCFieldElement2.square()));
                eCFieldElement12 = bl ? eCFieldElement : eCFieldElement.multiply(eCFieldElement11);
                eCFieldElement3 = bl ? eCFieldElement.square() : eCFieldElement12.multiply(eCFieldElement);
                ECFieldElement eCFieldElement14 = this.four(eCFieldElement2.multiply(eCFieldElement3));
                eCFieldElement11 = eCFieldElement13.square().subtract(this.two(eCFieldElement14));
                eCFieldElement2 = this.two(eCFieldElement12);
                eCFieldElement = eCFieldElement11.multiply(eCFieldElement2);
                eCFieldElement3 = this.two(eCFieldElement3);
                eCFieldElement13 = eCFieldElement14.subtract(eCFieldElement11).multiply(eCFieldElement13).subtract(this.two(eCFieldElement3.square()));
                eCFieldElement3 = bl ? this.two(eCFieldElement3) : eCFieldElement2.square();
                eCFieldElement12 = this.two(eCFieldElement3).multiply(eCFieldElement12);
                bl = this.withCompression;
                return new Fp(eCCurve, eCFieldElement, eCFieldElement13, new ECFieldElement[]{eCFieldElement12}, bl);
            }
            ECFieldElement eCFieldElement15 = this.three(eCFieldElement2.square()).add(this.getCurve().getA()).divide(this.two(eCFieldElement));
            ECFieldElement eCFieldElement16 = eCFieldElement15.square().subtract(this.two(eCFieldElement2));
            return new Fp(eCCurve, eCFieldElement16, eCFieldElement15.multiply(eCFieldElement2.subtract(eCFieldElement16)).subtract(eCFieldElement), this.withCompression);
        }

        protected Fp twiceJacobianModified(boolean bl) {
            ECFieldElement eCFieldElement = this.x;
            ECFieldElement eCFieldElement2 = this.y;
            Object object = this.zs[0];
            ECFieldElement eCFieldElement3 = this.getJacobianModifiedW();
            ECFieldElement eCFieldElement4 = this.three(eCFieldElement.square()).add(eCFieldElement3);
            ECFieldElement eCFieldElement5 = this.two(eCFieldElement2);
            eCFieldElement2 = eCFieldElement5.multiply(eCFieldElement2);
            ECFieldElement eCFieldElement6 = this.two(eCFieldElement.multiply(eCFieldElement2));
            eCFieldElement = eCFieldElement4.square().subtract(this.two(eCFieldElement6));
            eCFieldElement2 = this.two(eCFieldElement2.square());
            eCFieldElement4 = eCFieldElement4.multiply(eCFieldElement6.subtract(eCFieldElement)).subtract(eCFieldElement2);
            eCFieldElement3 = bl ? this.two(eCFieldElement2.multiply(eCFieldElement3)) : null;
            if (!((ECFieldElement)object).isOne()) {
                eCFieldElement5 = eCFieldElement5.multiply((ECFieldElement)object);
            }
            object = this.getCurve();
            bl = this.withCompression;
            return new Fp((ECCurve)object, eCFieldElement, eCFieldElement4, new ECFieldElement[]{eCFieldElement5, eCFieldElement3}, bl);
        }

        @Override
        public ECPoint twicePlus(ECPoint object) {
            if (this == object) {
                return this.threeTimes();
            }
            if (this.isInfinity()) {
                return object;
            }
            if (((ECPoint)object).isInfinity()) {
                return this.twice();
            }
            ECFieldElement eCFieldElement = this.y;
            if (eCFieldElement.isZero()) {
                return object;
            }
            ECCurve eCCurve = this.getCurve();
            int n = eCCurve.getCoordinateSystem();
            if (n != 0) {
                if (n != 4) {
                    return this.twice().add((ECPoint)object);
                }
                return this.twiceJacobianModified(false).add((ECPoint)object);
            }
            ECFieldElement eCFieldElement2 = this.x;
            ECFieldElement eCFieldElement3 = ((ECPoint)object).x;
            ECFieldElement eCFieldElement4 = ((ECPoint)object).y;
            object = eCFieldElement3.subtract(eCFieldElement2);
            ECFieldElement eCFieldElement5 = eCFieldElement4.subtract(eCFieldElement);
            if (((ECFieldElement)object).isZero()) {
                if (eCFieldElement5.isZero()) {
                    return this.threeTimes();
                }
                return this;
            }
            eCFieldElement4 = ((ECFieldElement)object).square();
            ECFieldElement eCFieldElement6 = eCFieldElement5.square();
            ECFieldElement eCFieldElement7 = eCFieldElement4.multiply(this.two(eCFieldElement2).add(eCFieldElement3)).subtract(eCFieldElement6);
            if (eCFieldElement7.isZero()) {
                return eCCurve.getInfinity();
            }
            eCFieldElement6 = eCFieldElement7.multiply((ECFieldElement)object).invert();
            eCFieldElement5 = eCFieldElement7.multiply(eCFieldElement6).multiply(eCFieldElement5);
            object = this.two(eCFieldElement).multiply(eCFieldElement4).multiply((ECFieldElement)object).multiply(eCFieldElement6).subtract(eCFieldElement5);
            eCFieldElement3 = ((ECFieldElement)object).subtract(eCFieldElement5).multiply(eCFieldElement5.add((ECFieldElement)object)).add(eCFieldElement3);
            return new Fp(eCCurve, eCFieldElement3, eCFieldElement2.subtract(eCFieldElement3).multiply((ECFieldElement)object).subtract(eCFieldElement), this.withCompression);
        }

        protected ECFieldElement two(ECFieldElement eCFieldElement) {
            return eCFieldElement.add(eCFieldElement);
        }
    }

}

