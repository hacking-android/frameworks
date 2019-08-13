/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP224K1Field;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement;
import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat224;

public class SecP224K1Point
extends ECPoint.AbstractFp {
    public SecP224K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        this(eCCurve, eCFieldElement, eCFieldElement2, false);
    }

    public SecP224K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
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

    SecP224K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
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
        Object object3 = (int[])object.getXCoord();
        int[] arrn2 = (int[])object.getYCoord();
        SecP224K1FieldElement secP224K1FieldElement = (SecP224K1FieldElement)this.zs[0];
        SecP224K1FieldElement secP224K1FieldElement2 = (SecP224K1FieldElement)object.getZCoord(0);
        int[] arrn3 = Nat224.createExt();
        int[] arrn4 = Nat224.create();
        int[] arrn5 = Nat224.create();
        int[] arrn6 = Nat224.create();
        boolean bl = secP224K1FieldElement.isOne();
        if (bl) {
            object3 = object3.x;
            object = arrn2.x;
        } else {
            object = arrn5;
            SecP224K1Field.square(secP224K1FieldElement.x, object);
            SecP224K1Field.multiply(object, object3.x, arrn4);
            SecP224K1Field.multiply(object, secP224K1FieldElement.x, object);
            SecP224K1Field.multiply(object, arrn2.x, object);
            object3 = arrn4;
        }
        boolean bl2 = secP224K1FieldElement2.isOne();
        if (bl2) {
            object2 = object2.x;
            arrn = arrn.x;
        } else {
            SecP224K1Field.square(secP224K1FieldElement2.x, arrn6);
            SecP224K1Field.multiply(arrn6, object2.x, arrn3);
            SecP224K1Field.multiply(arrn6, secP224K1FieldElement2.x, arrn6);
            SecP224K1Field.multiply(arrn6, arrn.x, arrn6);
            object2 = arrn3;
            arrn = arrn6;
        }
        arrn2 = Nat224.create();
        SecP224K1Field.subtract(object2, object3, arrn2);
        SecP224K1Field.subtract(arrn, object, arrn4);
        if (Nat224.isZero(arrn2)) {
            if (Nat224.isZero(arrn4)) {
                return this.twice();
            }
            return eCCurve.getInfinity();
        }
        SecP224K1Field.square(arrn2, arrn5);
        object = Nat224.create();
        SecP224K1Field.multiply(arrn5, arrn2, object);
        SecP224K1Field.multiply(arrn5, object2, arrn5);
        SecP224K1Field.negate(object, object);
        Nat224.mul(arrn, object, arrn3);
        SecP224K1Field.reduce32(Nat224.addBothTo(arrn5, arrn5, object), object);
        object3 = new SecP224K1FieldElement(arrn6);
        SecP224K1Field.square(arrn4, object3.x);
        SecP224K1Field.subtract(object3.x, object, object3.x);
        object = new SecP224K1FieldElement((int[])object);
        SecP224K1Field.subtract(arrn5, object3.x, object.x);
        SecP224K1Field.multiplyAddToExt(object.x, arrn4, arrn3);
        SecP224K1Field.reduce(arrn3, object.x);
        object2 = new SecP224K1FieldElement(arrn2);
        if (!bl) {
            SecP224K1Field.multiply(object2.x, secP224K1FieldElement.x, object2.x);
        }
        if (!bl2) {
            SecP224K1Field.multiply(object2.x, secP224K1FieldElement2.x, object2.x);
        }
        bl2 = this.withCompression;
        return new SecP224K1Point(eCCurve, (ECFieldElement)object3, (ECFieldElement)object, new ECFieldElement[]{object2}, bl2);
    }

    @Override
    protected ECPoint detach() {
        return new SecP224K1Point(null, this.getAffineXCoord(), this.getAffineYCoord());
    }

    @Override
    public ECPoint negate() {
        if (this.isInfinity()) {
            return this;
        }
        return new SecP224K1Point(this.curve, this.x, this.y.negate(), this.zs, this.withCompression);
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
        SecP224K1FieldElement secP224K1FieldElement = (SecP224K1FieldElement)this.y;
        if (secP224K1FieldElement.isZero()) {
            return eCCurve.getInfinity();
        }
        int[] arrn = (int[])this.x;
        SecP224K1FieldElement secP224K1FieldElement2 = (SecP224K1FieldElement)this.zs[0];
        int[] arrn2 = Nat224.create();
        SecP224K1Field.square(secP224K1FieldElement.x, arrn2);
        Object object = Nat224.create();
        SecP224K1Field.square(arrn2, object);
        Object object2 = Nat224.create();
        SecP224K1Field.square(arrn.x, object2);
        SecP224K1Field.reduce32(Nat224.addBothTo(object2, object2, object2), object2);
        SecP224K1Field.multiply(arrn2, arrn.x, arrn2);
        SecP224K1Field.reduce32(Nat.shiftUpBits(7, arrn2, 2, 0), arrn2);
        arrn = Nat224.create();
        SecP224K1Field.reduce32(Nat.shiftUpBits(7, object, 3, 0, arrn), arrn);
        object = new SecP224K1FieldElement((int[])object);
        SecP224K1Field.square(object2, object.x);
        SecP224K1Field.subtract(object.x, arrn2, object.x);
        SecP224K1Field.subtract(object.x, arrn2, object.x);
        SecP224K1FieldElement secP224K1FieldElement3 = new SecP224K1FieldElement(arrn2);
        SecP224K1Field.subtract(arrn2, object.x, secP224K1FieldElement3.x);
        SecP224K1Field.multiply(secP224K1FieldElement3.x, object2, secP224K1FieldElement3.x);
        SecP224K1Field.subtract(secP224K1FieldElement3.x, arrn, secP224K1FieldElement3.x);
        object2 = new SecP224K1FieldElement((int[])object2);
        SecP224K1Field.twice(secP224K1FieldElement.x, object2.x);
        if (!secP224K1FieldElement2.isOne()) {
            SecP224K1Field.multiply(object2.x, secP224K1FieldElement2.x, object2.x);
        }
        boolean bl = this.withCompression;
        return new SecP224K1Point(eCCurve, (ECFieldElement)object, secP224K1FieldElement3, new ECFieldElement[]{object2}, bl);
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

