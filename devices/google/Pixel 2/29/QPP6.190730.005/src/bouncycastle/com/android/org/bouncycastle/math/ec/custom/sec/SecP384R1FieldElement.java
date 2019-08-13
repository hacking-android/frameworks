/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP384R1Curve;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP384R1Field;
import com.android.org.bouncycastle.math.raw.Mod;
import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

public class SecP384R1FieldElement
extends ECFieldElement.AbstractFp {
    public static final BigInteger Q = SecP384R1Curve.q;
    protected int[] x;

    public SecP384R1FieldElement() {
        this.x = Nat.create(12);
    }

    public SecP384R1FieldElement(BigInteger bigInteger) {
        if (bigInteger != null && bigInteger.signum() >= 0 && bigInteger.compareTo(Q) < 0) {
            this.x = SecP384R1Field.fromBigInteger(bigInteger);
            return;
        }
        throw new IllegalArgumentException("x value invalid for SecP384R1FieldElement");
    }

    protected SecP384R1FieldElement(int[] arrn) {
        this.x = arrn;
    }

    @Override
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] arrn = Nat.create(12);
        SecP384R1Field.add(this.x, ((SecP384R1FieldElement)eCFieldElement).x, arrn);
        return new SecP384R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement addOne() {
        int[] arrn = Nat.create(12);
        SecP384R1Field.addOne(this.x, arrn);
        return new SecP384R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] arrn = Nat.create(12);
        Mod.invert(SecP384R1Field.P, ((SecP384R1FieldElement)eCFieldElement).x, arrn);
        SecP384R1Field.multiply(arrn, this.x, arrn);
        return new SecP384R1FieldElement(arrn);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SecP384R1FieldElement)) {
            return false;
        }
        object = (SecP384R1FieldElement)object;
        return Nat.eq(12, this.x, ((SecP384R1FieldElement)object).x);
    }

    @Override
    public String getFieldName() {
        return "SecP384R1Field";
    }

    @Override
    public int getFieldSize() {
        return Q.bitLength();
    }

    public int hashCode() {
        return Q.hashCode() ^ Arrays.hashCode(this.x, 0, 12);
    }

    @Override
    public ECFieldElement invert() {
        int[] arrn = Nat.create(12);
        Mod.invert(SecP384R1Field.P, this.x, arrn);
        return new SecP384R1FieldElement(arrn);
    }

    @Override
    public boolean isOne() {
        return Nat.isOne(12, this.x);
    }

    @Override
    public boolean isZero() {
        return Nat.isZero(12, this.x);
    }

    @Override
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] arrn = Nat.create(12);
        SecP384R1Field.multiply(this.x, ((SecP384R1FieldElement)eCFieldElement).x, arrn);
        return new SecP384R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement negate() {
        int[] arrn = Nat.create(12);
        SecP384R1Field.negate(this.x, arrn);
        return new SecP384R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement sqrt() {
        int[] arrn = this.x;
        if (!Nat.isZero(12, arrn) && !Nat.isOne(12, arrn)) {
            int[] arrn2 = Nat.create(12);
            Object object = Nat.create(12);
            int[] arrn3 = Nat.create(12);
            int[] arrn4 = Nat.create(12);
            SecP384R1Field.square(arrn, arrn2);
            SecP384R1Field.multiply(arrn2, arrn, arrn2);
            SecP384R1Field.squareN(arrn2, 2, object);
            SecP384R1Field.multiply(object, arrn2, object);
            SecP384R1Field.square(object, object);
            SecP384R1Field.multiply(object, arrn, object);
            SecP384R1Field.squareN(object, 5, arrn3);
            SecP384R1Field.multiply(arrn3, object, arrn3);
            SecP384R1Field.squareN(arrn3, 5, arrn4);
            SecP384R1Field.multiply(arrn4, object, arrn4);
            SecP384R1Field.squareN(arrn4, 15, object);
            SecP384R1Field.multiply(object, arrn4, object);
            SecP384R1Field.squareN(object, 2, arrn3);
            SecP384R1Field.multiply(arrn2, arrn3, arrn2);
            SecP384R1Field.squareN(arrn3, 28, arrn3);
            SecP384R1Field.multiply(object, arrn3, object);
            SecP384R1Field.squareN(object, 60, arrn3);
            SecP384R1Field.multiply(arrn3, object, arrn3);
            SecP384R1Field.squareN(arrn3, 120, object);
            SecP384R1Field.multiply(object, arrn3, object);
            SecP384R1Field.squareN(object, 15, object);
            SecP384R1Field.multiply(object, arrn4, object);
            SecP384R1Field.squareN(object, 33, object);
            SecP384R1Field.multiply(object, arrn2, object);
            SecP384R1Field.squareN(object, 64, object);
            SecP384R1Field.multiply(object, arrn, object);
            SecP384R1Field.squareN(object, 30, arrn2);
            SecP384R1Field.square(arrn2, object);
            object = Nat.eq(12, arrn, object) ? new SecP384R1FieldElement(arrn2) : null;
            return object;
        }
        return this;
    }

    @Override
    public ECFieldElement square() {
        int[] arrn = Nat.create(12);
        SecP384R1Field.square(this.x, arrn);
        return new SecP384R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] arrn = Nat.create(12);
        SecP384R1Field.subtract(this.x, ((SecP384R1FieldElement)eCFieldElement).x, arrn);
        return new SecP384R1FieldElement(arrn);
    }

    @Override
    public boolean testBitZero() {
        int[] arrn = this.x;
        boolean bl = false;
        if (Nat.getBit(arrn, 0) == 1) {
            bl = true;
        }
        return bl;
    }

    @Override
    public BigInteger toBigInteger() {
        return Nat.toBigInteger(12, this.x);
    }
}

