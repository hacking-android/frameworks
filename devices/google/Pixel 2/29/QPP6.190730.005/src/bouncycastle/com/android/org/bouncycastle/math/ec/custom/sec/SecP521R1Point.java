/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP521R1Field;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP521R1FieldElement;
import com.android.org.bouncycastle.math.raw.Nat;

public class SecP521R1Point
extends ECPoint.AbstractFp {
    public SecP521R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        this(eCCurve, eCFieldElement, eCFieldElement2, false);
    }

    public SecP521R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
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

    SecP521R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
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
        Object object2 = (int[])this.x;
        int[] arrn = (int[])this.y;
        int[] arrn2 = (int[])object.getXCoord();
        Object object3 = (int[])object.getYCoord();
        SecP521R1FieldElement secP521R1FieldElement = (SecP521R1FieldElement)this.zs[0];
        SecP521R1FieldElement secP521R1FieldElement2 = (SecP521R1FieldElement)object.getZCoord(0);
        int[] arrn3 = Nat.create(17);
        int[] arrn4 = Nat.create(17);
        int[] arrn5 = Nat.create(17);
        int[] arrn6 = Nat.create(17);
        boolean bl = secP521R1FieldElement.isOne();
        if (bl) {
            object = arrn2.x;
            object3 = object3.x;
        } else {
            SecP521R1Field.square(secP521R1FieldElement.x, arrn5);
            object = arrn4;
            SecP521R1Field.multiply(arrn5, arrn2.x, object);
            SecP521R1Field.multiply(arrn5, secP521R1FieldElement.x, arrn5);
            SecP521R1Field.multiply(arrn5, object3.x, arrn5);
            object3 = arrn5;
        }
        boolean bl2 = secP521R1FieldElement2.isOne();
        if (bl2) {
            object2 = object2.x;
            arrn2 = arrn.x;
        } else {
            arrn2 = arrn6;
            SecP521R1Field.square(secP521R1FieldElement2.x, arrn2);
            SecP521R1Field.multiply(arrn2, object2.x, arrn3);
            SecP521R1Field.multiply(arrn2, secP521R1FieldElement2.x, arrn2);
            SecP521R1Field.multiply(arrn2, arrn.x, arrn2);
            object2 = arrn3;
        }
        arrn = Nat.create(17);
        SecP521R1Field.subtract(object2, object, arrn);
        SecP521R1Field.subtract(arrn2, object3, arrn4);
        if (Nat.isZero(17, arrn)) {
            if (Nat.isZero(17, arrn4)) {
                return this.twice();
            }
            return eCCurve.getInfinity();
        }
        SecP521R1Field.square(arrn, arrn5);
        object = Nat.create(17);
        SecP521R1Field.multiply(arrn5, arrn, object);
        SecP521R1Field.multiply(arrn5, object2, arrn5);
        SecP521R1Field.multiply(arrn2, object, arrn3);
        object3 = new SecP521R1FieldElement(arrn6);
        SecP521R1Field.square(arrn4, object3.x);
        SecP521R1Field.add(object3.x, object, object3.x);
        SecP521R1Field.subtract(object3.x, arrn5, object3.x);
        SecP521R1Field.subtract(object3.x, arrn5, object3.x);
        object = new SecP521R1FieldElement((int[])object);
        SecP521R1Field.subtract(arrn5, object3.x, object.x);
        SecP521R1Field.multiply(object.x, arrn4, arrn4);
        SecP521R1Field.subtract(arrn4, arrn3, object.x);
        object2 = new SecP521R1FieldElement(arrn);
        if (!bl) {
            SecP521R1Field.multiply(object2.x, secP521R1FieldElement.x, object2.x);
        }
        if (!bl2) {
            SecP521R1Field.multiply(object2.x, secP521R1FieldElement2.x, object2.x);
        }
        bl = this.withCompression;
        return new SecP521R1Point(eCCurve, (ECFieldElement)object3, (ECFieldElement)object, new ECFieldElement[]{object2}, bl);
    }

    @Override
    protected ECPoint detach() {
        return new SecP521R1Point(null, this.getAffineXCoord(), this.getAffineYCoord());
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

    @Override
    public ECPoint negate() {
        if (this.isInfinity()) {
            return this;
        }
        return new SecP521R1Point(this.curve, this.x, this.y.negate(), this.zs, this.withCompression);
    }

    protected ECFieldElement three(ECFieldElement eCFieldElement) {
        return this.two(eCFieldElement).add(eCFieldElement);
    }

    @Override
    public ECPoint threeTimes() {
        if (!this.isInfinity() && !this.y.isZero()) {
            return this.twice().add(this);
        }
        return this;
    }

    @Override
    public ECPoint twice() {
        if (this.isInfinity()) {
            return this;
        }
        ECCurve eCCurve = this.getCurve();
        SecP521R1FieldElement secP521R1FieldElement = (SecP521R1FieldElement)this.y;
        if (secP521R1FieldElement.isZero()) {
            return eCCurve.getInfinity();
        }
        SecP521R1FieldElement secP521R1FieldElement2 = (SecP521R1FieldElement)this.x;
        SecP521R1FieldElement secP521R1FieldElement3 = (SecP521R1FieldElement)this.zs[0];
        int[] arrn = Nat.create(17);
        Object object = Nat.create(17);
        int[] arrn2 = Nat.create(17);
        SecP521R1Field.square(secP521R1FieldElement.x, arrn2);
        Object object2 = Nat.create(17);
        SecP521R1Field.square(arrn2, object2);
        boolean bl = secP521R1FieldElement3.isOne();
        Object object3 = secP521R1FieldElement3.x;
        if (!bl) {
            SecP521R1Field.square(secP521R1FieldElement3.x, object);
            object3 = object;
        }
        SecP521R1Field.subtract(secP521R1FieldElement2.x, object3, arrn);
        SecP521R1Field.add(secP521R1FieldElement2.x, object3, object);
        SecP521R1Field.multiply(object, arrn, object);
        Nat.addBothTo(17, object, object, object);
        SecP521R1Field.reduce23(object);
        SecP521R1Field.multiply(arrn2, secP521R1FieldElement2.x, arrn2);
        Nat.shiftUpBits(17, arrn2, 2, 0);
        SecP521R1Field.reduce23(arrn2);
        Nat.shiftUpBits(17, object2, 3, 0, arrn);
        SecP521R1Field.reduce23(arrn);
        object2 = new SecP521R1FieldElement((int[])object2);
        SecP521R1Field.square(object, object2.x);
        SecP521R1Field.subtract(object2.x, arrn2, object2.x);
        SecP521R1Field.subtract(object2.x, arrn2, object2.x);
        object3 = new SecP521R1FieldElement(arrn2);
        SecP521R1Field.subtract(arrn2, object2.x, object3.x);
        SecP521R1Field.multiply(object3.x, object, object3.x);
        SecP521R1Field.subtract(object3.x, arrn, object3.x);
        object = new SecP521R1FieldElement((int[])object);
        SecP521R1Field.twice(secP521R1FieldElement.x, object.x);
        if (!bl) {
            SecP521R1Field.multiply(object.x, secP521R1FieldElement3.x, object.x);
        }
        bl = this.withCompression;
        return new SecP521R1Point(eCCurve, (ECFieldElement)object2, (ECFieldElement)object3, new ECFieldElement[]{object}, bl);
    }

    @Override
    public ECPoint twicePlus(ECPoint eCPoint) {
        if (this == eCPoint) {
            return this.threeTimes();
        }
        if (this.isInfinity()) {
            return eCPoint;
        }
        if (eCPoint.isInfinity()) {
            return this.twice();
        }
        if (this.y.isZero()) {
            return eCPoint;
        }
        return this.twice().add(eCPoint);
    }

    protected ECFieldElement two(ECFieldElement eCFieldElement) {
        return eCFieldElement.add(eCFieldElement);
    }
}

