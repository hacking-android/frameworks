/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP192K1Curve;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP192K1Field;
import com.android.org.bouncycastle.math.raw.Mod;
import com.android.org.bouncycastle.math.raw.Nat192;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

public class SecP192K1FieldElement
extends ECFieldElement.AbstractFp {
    public static final BigInteger Q = SecP192K1Curve.q;
    protected int[] x;

    public SecP192K1FieldElement() {
        this.x = Nat192.create();
    }

    public SecP192K1FieldElement(BigInteger bigInteger) {
        if (bigInteger != null && bigInteger.signum() >= 0 && bigInteger.compareTo(Q) < 0) {
            this.x = SecP192K1Field.fromBigInteger(bigInteger);
            return;
        }
        throw new IllegalArgumentException("x value invalid for SecP192K1FieldElement");
    }

    protected SecP192K1FieldElement(int[] arrn) {
        this.x = arrn;
    }

    @Override
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] arrn = Nat192.create();
        SecP192K1Field.add(this.x, ((SecP192K1FieldElement)eCFieldElement).x, arrn);
        return new SecP192K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement addOne() {
        int[] arrn = Nat192.create();
        SecP192K1Field.addOne(this.x, arrn);
        return new SecP192K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] arrn = Nat192.create();
        Mod.invert(SecP192K1Field.P, ((SecP192K1FieldElement)eCFieldElement).x, arrn);
        SecP192K1Field.multiply(arrn, this.x, arrn);
        return new SecP192K1FieldElement(arrn);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SecP192K1FieldElement)) {
            return false;
        }
        object = (SecP192K1FieldElement)object;
        return Nat192.eq(this.x, ((SecP192K1FieldElement)object).x);
    }

    @Override
    public String getFieldName() {
        return "SecP192K1Field";
    }

    @Override
    public int getFieldSize() {
        return Q.bitLength();
    }

    public int hashCode() {
        return Q.hashCode() ^ Arrays.hashCode(this.x, 0, 6);
    }

    @Override
    public ECFieldElement invert() {
        int[] arrn = Nat192.create();
        Mod.invert(SecP192K1Field.P, this.x, arrn);
        return new SecP192K1FieldElement(arrn);
    }

    @Override
    public boolean isOne() {
        return Nat192.isOne(this.x);
    }

    @Override
    public boolean isZero() {
        return Nat192.isZero(this.x);
    }

    @Override
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] arrn = Nat192.create();
        SecP192K1Field.multiply(this.x, ((SecP192K1FieldElement)eCFieldElement).x, arrn);
        return new SecP192K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement negate() {
        int[] arrn = Nat192.create();
        SecP192K1Field.negate(this.x, arrn);
        return new SecP192K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement sqrt() {
        int[] arrn = this.x;
        if (!Nat192.isZero(arrn) && !Nat192.isOne(arrn)) {
            Object object = Nat192.create();
            SecP192K1Field.square(arrn, object);
            SecP192K1Field.multiply(object, arrn, object);
            int[] arrn2 = Nat192.create();
            SecP192K1Field.square(object, arrn2);
            SecP192K1Field.multiply(arrn2, arrn, arrn2);
            int[] arrn3 = Nat192.create();
            SecP192K1Field.squareN(arrn2, 3, arrn3);
            SecP192K1Field.multiply(arrn3, arrn2, arrn3);
            SecP192K1Field.squareN(arrn3, 2, arrn3);
            SecP192K1Field.multiply(arrn3, object, arrn3);
            SecP192K1Field.squareN(arrn3, 8, object);
            SecP192K1Field.multiply(object, arrn3, object);
            SecP192K1Field.squareN(object, 3, arrn3);
            SecP192K1Field.multiply(arrn3, arrn2, arrn3);
            int[] arrn4 = Nat192.create();
            SecP192K1Field.squareN(arrn3, 16, arrn4);
            SecP192K1Field.multiply(arrn4, object, arrn4);
            SecP192K1Field.squareN(arrn4, 35, object);
            SecP192K1Field.multiply(object, arrn4, object);
            SecP192K1Field.squareN(object, 70, arrn4);
            SecP192K1Field.multiply(arrn4, object, arrn4);
            SecP192K1Field.squareN(arrn4, 19, object);
            SecP192K1Field.multiply(object, arrn3, object);
            SecP192K1Field.squareN(object, 20, object);
            SecP192K1Field.multiply(object, arrn3, object);
            SecP192K1Field.squareN(object, 4, object);
            SecP192K1Field.multiply(object, arrn2, object);
            SecP192K1Field.squareN(object, 6, object);
            SecP192K1Field.multiply(object, arrn2, object);
            SecP192K1Field.square(object, object);
            SecP192K1Field.square(object, arrn2);
            object = Nat192.eq(arrn, arrn2) ? new SecP192K1FieldElement((int[])object) : null;
            return object;
        }
        return this;
    }

    @Override
    public ECFieldElement square() {
        int[] arrn = Nat192.create();
        SecP192K1Field.square(this.x, arrn);
        return new SecP192K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] arrn = Nat192.create();
        SecP192K1Field.subtract(this.x, ((SecP192K1FieldElement)eCFieldElement).x, arrn);
        return new SecP192K1FieldElement(arrn);
    }

    @Override
    public boolean testBitZero() {
        int[] arrn = this.x;
        boolean bl = false;
        if (Nat192.getBit(arrn, 0) == 1) {
            bl = true;
        }
        return bl;
    }

    @Override
    public BigInteger toBigInteger() {
        return Nat192.toBigInteger(this.x);
    }
}

