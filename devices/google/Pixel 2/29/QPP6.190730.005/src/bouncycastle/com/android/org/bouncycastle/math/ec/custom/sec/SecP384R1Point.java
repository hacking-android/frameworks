/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP384R1Field;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement;
import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat384;

public class SecP384R1Point
extends ECPoint.AbstractFp {
    public SecP384R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        this(eCCurve, eCFieldElement, eCFieldElement2, false);
    }

    public SecP384R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
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

    SecP384R1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
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
        Object object3 = (int[])this.x;
        int[] arrn = (int[])this.y;
        SecP384R1FieldElement secP384R1FieldElement = (SecP384R1FieldElement)object.getXCoord();
        int[] arrn2 = (int[])object.getYCoord();
        SecP384R1FieldElement secP384R1FieldElement2 = (SecP384R1FieldElement)this.zs[0];
        SecP384R1FieldElement secP384R1FieldElement3 = (SecP384R1FieldElement)object.getZCoord(0);
        int[] arrn3 = Nat.create(24);
        int[] arrn4 = Nat.create(24);
        int[] arrn5 = Nat.create(12);
        int[] arrn6 = Nat.create(12);
        boolean bl = secP384R1FieldElement2.isOne();
        if (bl) {
            object2 = secP384R1FieldElement.x;
            object = arrn2.x;
        } else {
            SecP384R1Field.square(secP384R1FieldElement2.x, arrn5);
            object2 = arrn4;
            SecP384R1Field.multiply(arrn5, secP384R1FieldElement.x, object2);
            SecP384R1Field.multiply(arrn5, secP384R1FieldElement2.x, arrn5);
            SecP384R1Field.multiply(arrn5, arrn2.x, arrn5);
            object = arrn5;
        }
        boolean bl2 = secP384R1FieldElement3.isOne();
        if (bl2) {
            object3 = object3.x;
            arrn2 = arrn.x;
        } else {
            arrn2 = arrn6;
            SecP384R1Field.square(secP384R1FieldElement3.x, arrn2);
            SecP384R1Field.multiply(arrn2, object3.x, arrn3);
            SecP384R1Field.multiply(arrn2, secP384R1FieldElement3.x, arrn2);
            SecP384R1Field.multiply(arrn2, arrn.x, arrn2);
            object3 = arrn3;
        }
        arrn = Nat.create(12);
        SecP384R1Field.subtract(object3, object2, arrn);
        object2 = Nat.create(12);
        SecP384R1Field.subtract(arrn2, object, object2);
        if (Nat.isZero(12, arrn)) {
            if (Nat.isZero(12, object2)) {
                return this.twice();
            }
            return eCCurve.getInfinity();
        }
        SecP384R1Field.square(arrn, arrn5);
        object = Nat.create(12);
        SecP384R1Field.multiply(arrn5, arrn, object);
        SecP384R1Field.multiply(arrn5, object3, arrn5);
        SecP384R1Field.negate(object, object);
        Nat384.mul(arrn2, object, arrn3);
        SecP384R1Field.reduce32(Nat.addBothTo(12, arrn5, arrn5, object), object);
        object3 = new SecP384R1FieldElement(arrn6);
        SecP384R1Field.square(object2, object3.x);
        SecP384R1Field.subtract(object3.x, object, object3.x);
        object = new SecP384R1FieldElement((int[])object);
        SecP384R1Field.subtract(arrn5, object3.x, object.x);
        Nat384.mul(object.x, object2, arrn4);
        SecP384R1Field.addExt(arrn3, arrn4, arrn3);
        SecP384R1Field.reduce(arrn3, object.x);
        object2 = new SecP384R1FieldElement(arrn);
        if (!bl) {
            SecP384R1Field.multiply(object2.x, secP384R1FieldElement2.x, object2.x);
        }
        if (!bl2) {
            SecP384R1Field.multiply(object2.x, secP384R1FieldElement3.x, object2.x);
        }
        bl = this.withCompression;
        return new SecP384R1Point(eCCurve, (ECFieldElement)object3, (ECFieldElement)object, new ECFieldElement[]{object2}, bl);
    }

    @Override
    protected ECPoint detach() {
        return new SecP384R1Point(null, this.getAffineXCoord(), this.getAffineYCoord());
    }

    @Override
    public ECPoint negate() {
        if (this.isInfinity()) {
            return this;
        }
        return new SecP384R1Point(this.curve, this.x, this.y.negate(), this.zs, this.withCompression);
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
        SecP384R1FieldElement secP384R1FieldElement = (SecP384R1FieldElement)this.y;
        if (secP384R1FieldElement.isZero()) {
            return eCCurve.getInfinity();
        }
        SecP384R1FieldElement secP384R1FieldElement2 = (SecP384R1FieldElement)this.x;
        SecP384R1FieldElement secP384R1FieldElement3 = (SecP384R1FieldElement)this.zs[0];
        int[] arrn = Nat.create(12);
        Object object = Nat.create(12);
        int[] arrn2 = Nat.create(12);
        SecP384R1Field.square(secP384R1FieldElement.x, arrn2);
        int[] arrn3 = Nat.create(12);
        SecP384R1Field.square(arrn2, arrn3);
        boolean bl = secP384R1FieldElement3.isOne();
        Object object2 = secP384R1FieldElement3.x;
        if (!bl) {
            SecP384R1Field.square(secP384R1FieldElement3.x, object);
            object2 = object;
        }
        SecP384R1Field.subtract(secP384R1FieldElement2.x, object2, arrn);
        SecP384R1Field.add(secP384R1FieldElement2.x, object2, object);
        SecP384R1Field.multiply(object, arrn, object);
        SecP384R1Field.reduce32(Nat.addBothTo(12, object, object, object), object);
        SecP384R1Field.multiply(arrn2, secP384R1FieldElement2.x, arrn2);
        SecP384R1Field.reduce32(Nat.shiftUpBits(12, arrn2, 2, 0), arrn2);
        SecP384R1Field.reduce32(Nat.shiftUpBits(12, arrn3, 3, 0, arrn), arrn);
        secP384R1FieldElement2 = new SecP384R1FieldElement(arrn3);
        SecP384R1Field.square(object, secP384R1FieldElement2.x);
        SecP384R1Field.subtract(secP384R1FieldElement2.x, arrn2, secP384R1FieldElement2.x);
        SecP384R1Field.subtract(secP384R1FieldElement2.x, arrn2, secP384R1FieldElement2.x);
        object2 = new SecP384R1FieldElement(arrn2);
        SecP384R1Field.subtract(arrn2, secP384R1FieldElement2.x, object2.x);
        SecP384R1Field.multiply(object2.x, object, object2.x);
        SecP384R1Field.subtract(object2.x, arrn, object2.x);
        object = new SecP384R1FieldElement((int[])object);
        SecP384R1Field.twice(secP384R1FieldElement.x, object.x);
        if (!bl) {
            SecP384R1Field.multiply(object.x, secP384R1FieldElement3.x, object.x);
        }
        bl = this.withCompression;
        return new SecP384R1Point(eCCurve, secP384R1FieldElement2, (ECFieldElement)object2, new ECFieldElement[]{object}, bl);
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

