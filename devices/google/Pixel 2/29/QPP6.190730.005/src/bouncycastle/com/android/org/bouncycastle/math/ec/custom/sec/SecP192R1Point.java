/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP192R1Field;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP192R1FieldElement;
import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat192;

public class SecP192R1Point
extends ECPoint.AbstractFp {
    public SecP192R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        this(eCCurve, eCFieldElement, eCFieldElement2, false);
    }

    public SecP192R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
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

    SecP192R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
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
        int[] arrn = (int[])this.x;
        Object object2 = (int[])this.y;
        Object object3 = (int[])object.getXCoord();
        int[] arrn2 = (int[])object.getYCoord();
        SecP192R1FieldElement secP192R1FieldElement = (SecP192R1FieldElement)this.zs[0];
        SecP192R1FieldElement secP192R1FieldElement2 = (SecP192R1FieldElement)object.getZCoord(0);
        int[] arrn3 = Nat192.createExt();
        int[] arrn4 = Nat192.create();
        int[] arrn5 = Nat192.create();
        int[] arrn6 = Nat192.create();
        boolean bl = secP192R1FieldElement.isOne();
        if (bl) {
            object3 = object3.x;
            object = arrn2.x;
        } else {
            object = arrn5;
            SecP192R1Field.square(secP192R1FieldElement.x, object);
            SecP192R1Field.multiply(object, object3.x, arrn4);
            SecP192R1Field.multiply(object, secP192R1FieldElement.x, object);
            SecP192R1Field.multiply(object, arrn2.x, object);
            object3 = arrn4;
        }
        boolean bl2 = secP192R1FieldElement2.isOne();
        if (bl2) {
            arrn = arrn.x;
            object2 = object2.x;
        } else {
            SecP192R1Field.square(secP192R1FieldElement2.x, arrn6);
            SecP192R1Field.multiply(arrn6, arrn.x, arrn3);
            SecP192R1Field.multiply(arrn6, secP192R1FieldElement2.x, arrn6);
            SecP192R1Field.multiply(arrn6, object2.x, arrn6);
            arrn = arrn3;
            object2 = arrn6;
        }
        arrn2 = Nat192.create();
        SecP192R1Field.subtract(arrn, object3, arrn2);
        SecP192R1Field.subtract(object2, object, arrn4);
        if (Nat192.isZero(arrn2)) {
            if (Nat192.isZero(arrn4)) {
                return this.twice();
            }
            return eCCurve.getInfinity();
        }
        SecP192R1Field.square(arrn2, arrn5);
        object = Nat192.create();
        SecP192R1Field.multiply(arrn5, arrn2, object);
        SecP192R1Field.multiply(arrn5, arrn, arrn5);
        SecP192R1Field.negate(object, object);
        Nat192.mul(object2, object, arrn3);
        SecP192R1Field.reduce32(Nat192.addBothTo(arrn5, arrn5, object), object);
        object3 = new SecP192R1FieldElement(arrn6);
        SecP192R1Field.square(arrn4, object3.x);
        SecP192R1Field.subtract(object3.x, object, object3.x);
        object = new SecP192R1FieldElement((int[])object);
        SecP192R1Field.subtract(arrn5, object3.x, object.x);
        SecP192R1Field.multiplyAddToExt(object.x, arrn4, arrn3);
        SecP192R1Field.reduce(arrn3, object.x);
        object2 = new SecP192R1FieldElement(arrn2);
        if (!bl) {
            SecP192R1Field.multiply(object2.x, secP192R1FieldElement.x, object2.x);
        }
        if (!bl2) {
            SecP192R1Field.multiply(object2.x, secP192R1FieldElement2.x, object2.x);
        }
        bl2 = this.withCompression;
        return new SecP192R1Point(eCCurve, (ECFieldElement)object3, (ECFieldElement)object, new ECFieldElement[]{object2}, bl2);
    }

    @Override
    protected ECPoint detach() {
        return new SecP192R1Point(null, this.getAffineXCoord(), this.getAffineYCoord());
    }

    @Override
    public ECPoint negate() {
        if (this.isInfinity()) {
            return this;
        }
        return new SecP192R1Point(this.curve, this.x, this.y.negate(), this.zs, this.withCompression);
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
        SecP192R1FieldElement secP192R1FieldElement = (SecP192R1FieldElement)this.y;
        if (secP192R1FieldElement.isZero()) {
            return eCCurve.getInfinity();
        }
        SecP192R1FieldElement secP192R1FieldElement2 = (SecP192R1FieldElement)this.x;
        SecP192R1FieldElement secP192R1FieldElement3 = (SecP192R1FieldElement)this.zs[0];
        int[] arrn = Nat192.create();
        Object object = Nat192.create();
        int[] arrn2 = Nat192.create();
        SecP192R1Field.square(secP192R1FieldElement.x, arrn2);
        int[] arrn3 = Nat192.create();
        SecP192R1Field.square(arrn2, arrn3);
        boolean bl = secP192R1FieldElement3.isOne();
        Object object2 = secP192R1FieldElement3.x;
        if (!bl) {
            SecP192R1Field.square(secP192R1FieldElement3.x, object);
            object2 = object;
        }
        SecP192R1Field.subtract(secP192R1FieldElement2.x, object2, arrn);
        SecP192R1Field.add(secP192R1FieldElement2.x, object2, object);
        SecP192R1Field.multiply(object, arrn, object);
        SecP192R1Field.reduce32(Nat192.addBothTo(object, object, object), object);
        SecP192R1Field.multiply(arrn2, secP192R1FieldElement2.x, arrn2);
        SecP192R1Field.reduce32(Nat.shiftUpBits(6, arrn2, 2, 0), arrn2);
        SecP192R1Field.reduce32(Nat.shiftUpBits(6, arrn3, 3, 0, arrn), arrn);
        secP192R1FieldElement2 = new SecP192R1FieldElement(arrn3);
        SecP192R1Field.square(object, secP192R1FieldElement2.x);
        SecP192R1Field.subtract(secP192R1FieldElement2.x, arrn2, secP192R1FieldElement2.x);
        SecP192R1Field.subtract(secP192R1FieldElement2.x, arrn2, secP192R1FieldElement2.x);
        object2 = new SecP192R1FieldElement(arrn2);
        SecP192R1Field.subtract(arrn2, secP192R1FieldElement2.x, object2.x);
        SecP192R1Field.multiply(object2.x, object, object2.x);
        SecP192R1Field.subtract(object2.x, arrn, object2.x);
        object = new SecP192R1FieldElement((int[])object);
        SecP192R1Field.twice(secP192R1FieldElement.x, object.x);
        if (!bl) {
            SecP192R1Field.multiply(object.x, secP192R1FieldElement3.x, object.x);
        }
        bl = this.withCompression;
        return new SecP192R1Point(eCCurve, secP192R1FieldElement2, (ECFieldElement)object2, new ECFieldElement[]{object}, bl);
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

