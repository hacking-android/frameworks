/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP256K1Curve;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP256K1Field;
import com.android.org.bouncycastle.math.raw.Mod;
import com.android.org.bouncycastle.math.raw.Nat256;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

public class SecP256K1FieldElement
extends ECFieldElement.AbstractFp {
    public static final BigInteger Q = SecP256K1Curve.q;
    protected int[] x;

    public SecP256K1FieldElement() {
        this.x = Nat256.create();
    }

    public SecP256K1FieldElement(BigInteger bigInteger) {
        if (bigInteger != null && bigInteger.signum() >= 0 && bigInteger.compareTo(Q) < 0) {
            this.x = SecP256K1Field.fromBigInteger(bigInteger);
            return;
        }
        throw new IllegalArgumentException("x value invalid for SecP256K1FieldElement");
    }

    protected SecP256K1FieldElement(int[] arrn) {
        this.x = arrn;
    }

    @Override
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] arrn = Nat256.create();
        SecP256K1Field.add(this.x, ((SecP256K1FieldElement)eCFieldElement).x, arrn);
        return new SecP256K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement addOne() {
        int[] arrn = Nat256.create();
        SecP256K1Field.addOne(this.x, arrn);
        return new SecP256K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] arrn = Nat256.create();
        Mod.invert(SecP256K1Field.P, ((SecP256K1FieldElement)eCFieldElement).x, arrn);
        SecP256K1Field.multiply(arrn, this.x, arrn);
        return new SecP256K1FieldElement(arrn);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SecP256K1FieldElement)) {
            return false;
        }
        object = (SecP256K1FieldElement)object;
        return Nat256.eq(this.x, ((SecP256K1FieldElement)object).x);
    }

    @Override
    public String getFieldName() {
        return "SecP256K1Field";
    }

    @Override
    public int getFieldSize() {
        return Q.bitLength();
    }

    public int hashCode() {
        return Q.hashCode() ^ Arrays.hashCode(this.x, 0, 8);
    }

    @Override
    public ECFieldElement invert() {
        int[] arrn = Nat256.create();
        Mod.invert(SecP256K1Field.P, this.x, arrn);
        return new SecP256K1FieldElement(arrn);
    }

    @Override
    public boolean isOne() {
        return Nat256.isOne(this.x);
    }

    @Override
    public boolean isZero() {
        return Nat256.isZero(this.x);
    }

    @Override
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] arrn = Nat256.create();
        SecP256K1Field.multiply(this.x, ((SecP256K1FieldElement)eCFieldElement).x, arrn);
        return new SecP256K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement negate() {
        int[] arrn = Nat256.create();
        SecP256K1Field.negate(this.x, arrn);
        return new SecP256K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement sqrt() {
        int[] arrn = this.x;
        if (!Nat256.isZero(arrn) && !Nat256.isOne(arrn)) {
            int[] arrn2 = Nat256.create();
            SecP256K1Field.square(arrn, arrn2);
            SecP256K1Field.multiply(arrn2, arrn, arrn2);
            int[] arrn3 = Nat256.create();
            SecP256K1Field.square(arrn2, arrn3);
            SecP256K1Field.multiply(arrn3, arrn, arrn3);
            Object object = Nat256.create();
            SecP256K1Field.squareN(arrn3, 3, object);
            SecP256K1Field.multiply(object, arrn3, object);
            SecP256K1Field.squareN(object, 3, object);
            SecP256K1Field.multiply(object, arrn3, object);
            SecP256K1Field.squareN(object, 2, object);
            SecP256K1Field.multiply(object, arrn2, object);
            int[] arrn4 = Nat256.create();
            SecP256K1Field.squareN(object, 11, arrn4);
            SecP256K1Field.multiply(arrn4, object, arrn4);
            SecP256K1Field.squareN(arrn4, 22, object);
            SecP256K1Field.multiply(object, arrn4, object);
            int[] arrn5 = Nat256.create();
            SecP256K1Field.squareN(object, 44, arrn5);
            SecP256K1Field.multiply(arrn5, object, arrn5);
            int[] arrn6 = Nat256.create();
            SecP256K1Field.squareN(arrn5, 88, arrn6);
            SecP256K1Field.multiply(arrn6, arrn5, arrn6);
            SecP256K1Field.squareN(arrn6, 44, arrn5);
            SecP256K1Field.multiply(arrn5, object, arrn5);
            SecP256K1Field.squareN(arrn5, 3, object);
            SecP256K1Field.multiply(object, arrn3, object);
            SecP256K1Field.squareN(object, 23, object);
            SecP256K1Field.multiply(object, arrn4, object);
            SecP256K1Field.squareN(object, 6, object);
            SecP256K1Field.multiply(object, arrn2, object);
            SecP256K1Field.squareN(object, 2, object);
            SecP256K1Field.square(object, arrn2);
            object = Nat256.eq(arrn, arrn2) ? new SecP256K1FieldElement((int[])object) : null;
            return object;
        }
        return this;
    }

    @Override
    public ECFieldElement square() {
        int[] arrn = Nat256.create();
        SecP256K1Field.square(this.x, arrn);
        return new SecP256K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] arrn = Nat256.create();
        SecP256K1Field.subtract(this.x, ((SecP256K1FieldElement)eCFieldElement).x, arrn);
        return new SecP256K1FieldElement(arrn);
    }

    @Override
    public boolean testBitZero() {
        int[] arrn = this.x;
        boolean bl = false;
        if (Nat256.getBit(arrn, 0) == 1) {
            bl = true;
        }
        return bl;
    }

    @Override
    public BigInteger toBigInteger() {
        return Nat256.toBigInteger(this.x);
    }
}

