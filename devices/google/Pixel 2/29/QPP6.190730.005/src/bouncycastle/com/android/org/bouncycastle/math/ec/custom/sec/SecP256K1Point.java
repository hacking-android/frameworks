/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP256K1Field;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP256K1FieldElement;
import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat256;

public class SecP256K1Point
extends ECPoint.AbstractFp {
    public SecP256K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        this(eCCurve, eCFieldElement, eCFieldElement2, false);
    }

    public SecP256K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
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

    SecP256K1Point(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
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
        SecP256K1FieldElement secP256K1FieldElement = (SecP256K1FieldElement)object.getXCoord();
        int[] arrn2 = (int[])object.getYCoord();
        SecP256K1FieldElement secP256K1FieldElement2 = (SecP256K1FieldElement)this.zs[0];
        SecP256K1FieldElement secP256K1FieldElement3 = (SecP256K1FieldElement)object.getZCoord(0);
        int[] arrn3 = Nat256.createExt();
        int[] arrn4 = Nat256.create();
        int[] arrn5 = Nat256.create();
        int[] arrn6 = Nat256.create();
        boolean bl = secP256K1FieldElement2.isOne();
        if (bl) {
            object = secP256K1FieldElement.x;
            object2 = arrn2.x;
        } else {
            object2 = arrn5;
            SecP256K1Field.square(secP256K1FieldElement2.x, object2);
            SecP256K1Field.multiply(object2, secP256K1FieldElement.x, arrn4);
            SecP256K1Field.multiply(object2, secP256K1FieldElement2.x, object2);
            SecP256K1Field.multiply(object2, arrn2.x, object2);
            object = arrn4;
        }
        boolean bl2 = secP256K1FieldElement3.isOne();
        if (bl2) {
            arrn = arrn.x;
            object3 = object3.x;
        } else {
            SecP256K1Field.square(secP256K1FieldElement3.x, arrn6);
            SecP256K1Field.multiply(arrn6, arrn.x, arrn3);
            SecP256K1Field.multiply(arrn6, secP256K1FieldElement3.x, arrn6);
            SecP256K1Field.multiply(arrn6, object3.x, arrn6);
            arrn = arrn3;
            object3 = arrn6;
        }
        arrn2 = Nat256.create();
        SecP256K1Field.subtract(arrn, object, arrn2);
        SecP256K1Field.subtract(object3, object2, arrn4);
        if (Nat256.isZero(arrn2)) {
            if (Nat256.isZero(arrn4)) {
                return this.twice();
            }
            return eCCurve.getInfinity();
        }
        SecP256K1Field.square(arrn2, arrn5);
        object = Nat256.create();
        SecP256K1Field.multiply(arrn5, arrn2, object);
        SecP256K1Field.multiply(arrn5, arrn, arrn5);
        SecP256K1Field.negate(object, object);
        Nat256.mul(object3, object, arrn3);
        SecP256K1Field.reduce32(Nat256.addBothTo(arrn5, arrn5, object), object);
        object2 = new SecP256K1FieldElement(arrn6);
        SecP256K1Field.square(arrn4, object2.x);
        SecP256K1Field.subtract(object2.x, object, object2.x);
        object = new SecP256K1FieldElement((int[])object);
        SecP256K1Field.subtract(arrn5, object2.x, object.x);
        SecP256K1Field.multiplyAddToExt(object.x, arrn4, arrn3);
        SecP256K1Field.reduce(arrn3, object.x);
        object3 = new SecP256K1FieldElement(arrn2);
        if (!bl) {
            SecP256K1Field.multiply(object3.x, secP256K1FieldElement2.x, object3.x);
        }
        if (!bl2) {
            SecP256K1Field.multiply(object3.x, secP256K1FieldElement3.x, object3.x);
        }
        bl = this.withCompression;
        return new SecP256K1Point(eCCurve, (ECFieldElement)object2, (ECFieldElement)object, new ECFieldElement[]{object3}, bl);
    }

    @Override
    protected ECPoint detach() {
        return new SecP256K1Point(null, this.getAffineXCoord(), this.getAffineYCoord());
    }

    @Override
    public ECPoint negate() {
        if (this.isInfinity()) {
            return this;
        }
        return new SecP256K1Point(this.curve, this.x, this.y.negate(), this.zs, this.withCompression);
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
        SecP256K1FieldElement secP256K1FieldElement = (SecP256K1FieldElement)this.y;
        if (secP256K1FieldElement.isZero()) {
            return eCCurve.getInfinity();
        }
        SecP256K1FieldElement secP256K1FieldElement2 = (SecP256K1FieldElement)this.x;
        SecP256K1FieldElement secP256K1FieldElement3 = (SecP256K1FieldElement)this.zs[0];
        int[] arrn = Nat256.create();
        SecP256K1Field.square(secP256K1FieldElement.x, arrn);
        Object object = Nat256.create();
        SecP256K1Field.square(arrn, object);
        Object object2 = Nat256.create();
        SecP256K1Field.square(secP256K1FieldElement2.x, object2);
        SecP256K1Field.reduce32(Nat256.addBothTo(object2, object2, object2), object2);
        SecP256K1Field.multiply(arrn, secP256K1FieldElement2.x, arrn);
        SecP256K1Field.reduce32(Nat.shiftUpBits(8, arrn, 2, 0), arrn);
        int[] arrn2 = Nat256.create();
        SecP256K1Field.reduce32(Nat.shiftUpBits(8, object, 3, 0, arrn2), arrn2);
        object = new SecP256K1FieldElement((int[])object);
        SecP256K1Field.square(object2, object.x);
        SecP256K1Field.subtract(object.x, arrn, object.x);
        SecP256K1Field.subtract(object.x, arrn, object.x);
        secP256K1FieldElement2 = new SecP256K1FieldElement(arrn);
        SecP256K1Field.subtract(arrn, object.x, secP256K1FieldElement2.x);
        SecP256K1Field.multiply(secP256K1FieldElement2.x, object2, secP256K1FieldElement2.x);
        SecP256K1Field.subtract(secP256K1FieldElement2.x, arrn2, secP256K1FieldElement2.x);
        object2 = new SecP256K1FieldElement((int[])object2);
        SecP256K1Field.twice(secP256K1FieldElement.x, object2.x);
        if (!secP256K1FieldElement3.isOne()) {
            SecP256K1Field.multiply(object2.x, secP256K1FieldElement3.x, object2.x);
        }
        boolean bl = this.withCompression;
        return new SecP256K1Point(eCCurve, (ECFieldElement)object, secP256K1FieldElement2, new ECFieldElement[]{object2}, bl);
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

