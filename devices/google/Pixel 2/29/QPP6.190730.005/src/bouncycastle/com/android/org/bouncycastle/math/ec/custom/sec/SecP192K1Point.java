/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP192K1Field;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement;
import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat192;

public class SecP192K1Point
extends ECPoint.AbstractFp {
    public SecP192K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        this(eCCurve, eCFieldElement, eCFieldElement2, false);
    }

    public SecP192K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
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

    SecP192K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
        super(eCCurve, eCFieldElement, eCFieldElement2, arreCFieldElement);
        this.withCompression = bl;
    }

    @Override
    public ECPoint add(ECPoint object) {
        Object object2;
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
        int[] arrn = (int[])this.x;
        Object object3 = (int[])this.y;
        SecP192K1FieldElement secP192K1FieldElement = (SecP192K1FieldElement)object.getXCoord();
        int[] arrn2 = (int[])object.getYCoord();
        SecP192K1FieldElement secP192K1FieldElement2 = (SecP192K1FieldElement)this.zs[0];
        SecP192K1FieldElement secP192K1FieldElement3 = (SecP192K1FieldElement)object.getZCoord(0);
        int[] arrn3 = Nat192.createExt();
        int[] arrn4 = Nat192.create();
        int[] arrn5 = Nat192.create();
        int[] arrn6 = Nat192.create();
        boolean bl = secP192K1FieldElement2.isOne();
        if (bl) {
            object = secP192K1FieldElement.x;
            object2 = arrn2.x;
        } else {
            object2 = arrn5;
            SecP192K1Field.square(secP192K1FieldElement2.x, object2);
            SecP192K1Field.multiply(object2, secP192K1FieldElement.x, arrn4);
            SecP192K1Field.multiply(object2, secP192K1FieldElement2.x, object2);
            SecP192K1Field.multiply(object2, arrn2.x, object2);
            object = arrn4;
        }
        boolean bl2 = secP192K1FieldElement3.isOne();
        if (bl2) {
            arrn = arrn.x;
            object3 = object3.x;
        } else {
            SecP192K1Field.square(secP192K1FieldElement3.x, arrn6);
            SecP192K1Field.multiply(arrn6, arrn.x, arrn3);
            SecP192K1Field.multiply(arrn6, secP192K1FieldElement3.x, arrn6);
            SecP192K1Field.multiply(arrn6, object3.x, arrn6);
            arrn = arrn3;
            object3 = arrn6;
        }
        arrn2 = Nat192.create();
        SecP192K1Field.subtract(arrn, object, arrn2);
        SecP192K1Field.subtract(object3, object2, arrn4);
        if (Nat192.isZero(arrn2)) {
            if (Nat192.isZero(arrn4)) {
                return this.twice();
            }
            return eCCurve.getInfinity();
        }
        SecP192K1Field.square(arrn2, arrn5);
        object = Nat192.create();
        SecP192K1Field.multiply(arrn5, arrn2, object);
        SecP192K1Field.multiply(arrn5, arrn, arrn5);
        SecP192K1Field.negate(object, object);
        Nat192.mul(object3, object, arrn3);
        SecP192K1Field.reduce32(Nat192.addBothTo(arrn5, arrn5, object), object);
        object2 = new SecP192K1FieldElement(arrn6);
        SecP192K1Field.square(arrn4, object2.x);
        SecP192K1Field.subtract(object2.x, object, object2.x);
        object = new SecP192K1FieldElement((int[])object);
        SecP192K1Field.subtract(arrn5, object2.x, object.x);
        SecP192K1Field.multiplyAddToExt(object.x, arrn4, arrn3);
        SecP192K1Field.reduce(arrn3, object.x);
        object3 = new SecP192K1FieldElement(arrn2);
        if (!bl) {
            SecP192K1Field.multiply(object3.x, secP192K1FieldElement2.x, object3.x);
        }
        if (!bl2) {
            SecP192K1Field.multiply(object3.x, secP192K1FieldElement3.x, object3.x);
        }
        bl2 = this.withCompression;
        return new SecP192K1Point(eCCurve, (ECFieldElement)object2, (ECFieldElement)object, new ECFieldElement[]{object3}, bl2);
    }

    @Override
    protected ECPoint detach() {
        return new SecP192K1Point(null, this.getAffineXCoord(), this.getAffineYCoord());
    }

    @Override
    public ECPoint negate() {
        if (this.isInfinity()) {
            return this;
        }
        return new SecP192K1Point(this.curve, this.x, this.y.negate(), this.zs, this.withCompression);
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
        SecP192K1FieldElement secP192K1FieldElement = (SecP192K1FieldElement)this.y;
        if (secP192K1FieldElement.isZero()) {
            return eCCurve.getInfinity();
        }
        int[] arrn = (int[])this.x;
        SecP192K1FieldElement secP192K1FieldElement2 = (SecP192K1FieldElement)this.zs[0];
        int[] arrn2 = Nat192.create();
        SecP192K1Field.square(secP192K1FieldElement.x, arrn2);
        Object object = Nat192.create();
        SecP192K1Field.square(arrn2, object);
        Object object2 = Nat192.create();
        SecP192K1Field.square(arrn.x, object2);
        SecP192K1Field.reduce32(Nat192.addBothTo(object2, object2, object2), object2);
        SecP192K1Field.multiply(arrn2, arrn.x, arrn2);
        SecP192K1Field.reduce32(Nat.shiftUpBits(6, arrn2, 2, 0), arrn2);
        arrn = Nat192.create();
        SecP192K1Field.reduce32(Nat.shiftUpBits(6, object, 3, 0, arrn), arrn);
        SecP192K1FieldElement secP192K1FieldElement3 = new SecP192K1FieldElement((int[])object);
        SecP192K1Field.square(object2, secP192K1FieldElement3.x);
        SecP192K1Field.subtract(secP192K1FieldElement3.x, arrn2, secP192K1FieldElement3.x);
        SecP192K1Field.subtract(secP192K1FieldElement3.x, arrn2, secP192K1FieldElement3.x);
        object = new SecP192K1FieldElement(arrn2);
        SecP192K1Field.subtract(arrn2, secP192K1FieldElement3.x, object.x);
        SecP192K1Field.multiply(object.x, object2, object.x);
        SecP192K1Field.subtract(object.x, arrn, object.x);
        object2 = new SecP192K1FieldElement((int[])object2);
        SecP192K1Field.twice(secP192K1FieldElement.x, object2.x);
        if (!secP192K1FieldElement2.isOne()) {
            SecP192K1Field.multiply(object2.x, secP192K1FieldElement2.x, object2.x);
        }
        boolean bl = this.withCompression;
        return new SecP192K1Point(eCCurve, secP192K1FieldElement3, (ECFieldElement)object, new ECFieldElement[]{object2}, bl);
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
}

