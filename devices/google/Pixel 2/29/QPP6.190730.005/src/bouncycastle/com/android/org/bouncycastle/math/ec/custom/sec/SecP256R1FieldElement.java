/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP256R1Curve;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP256R1Field;
import com.android.org.bouncycastle.math.raw.Mod;
import com.android.org.bouncycastle.math.raw.Nat256;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

public class SecP256R1FieldElement
extends ECFieldElement.AbstractFp {
    public static final BigInteger Q = SecP256R1Curve.q;
    protected int[] x;

    public SecP256R1FieldElement() {
        this.x = Nat256.create();
    }

    public SecP256R1FieldElement(BigInteger bigInteger) {
        if (bigInteger != null && bigInteger.signum() >= 0 && bigInteger.compareTo(Q) < 0) {
            this.x = SecP256R1Field.fromBigInteger(bigInteger);
            return;
        }
        throw new IllegalArgumentException("x value invalid for SecP256R1FieldElement");
    }

    protected SecP256R1FieldElement(int[] arrn) {
        this.x = arrn;
    }

    @Override
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] arrn = Nat256.create();
        SecP256R1Field.add(this.x, ((SecP256R1FieldElement)eCFieldElement).x, arrn);
        return new SecP256R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement addOne() {
        int[] arrn = Nat256.create();
        SecP256R1Field.addOne(this.x, arrn);
        return new SecP256R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] arrn = Nat256.create();
        Mod.invert(SecP256R1Field.P, ((SecP256R1FieldElement)eCFieldElement).x, arrn);
        SecP256R1Field.multiply(arrn, this.x, arrn);
        return new SecP256R1FieldElement(arrn);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SecP256R1FieldElement)) {
            return false;
        }
        object = (SecP256R1FieldElement)object;
        return Nat256.eq(this.x, ((SecP256R1FieldElement)object).x);
    }

    @Override
    public String getFieldName() {
        return "SecP256R1Field";
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
        Mod.invert(SecP256R1Field.P, this.x, arrn);
        return new SecP256R1FieldElement(arrn);
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
        SecP256R1Field.multiply(this.x, ((SecP256R1FieldElement)eCFieldElement).x, arrn);
        return new SecP256R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement negate() {
        int[] arrn = Nat256.create();
        SecP256R1Field.negate(this.x, arrn);
        return new SecP256R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement sqrt() {
        int[] arrn = this.x;
        if (!Nat256.isZero(arrn) && !Nat256.isOne(arrn)) {
            int[] arrn2 = Nat256.create();
            Object object = Nat256.create();
            SecP256R1Field.square(arrn, arrn2);
            SecP256R1Field.multiply(arrn2, arrn, arrn2);
            SecP256R1Field.squareN(arrn2, 2, object);
            SecP256R1Field.multiply(object, arrn2, object);
            SecP256R1Field.squareN(object, 4, arrn2);
            SecP256R1Field.multiply(arrn2, object, arrn2);
            SecP256R1Field.squareN(arrn2, 8, object);
            SecP256R1Field.multiply(object, arrn2, object);
            SecP256R1Field.squareN(object, 16, arrn2);
            SecP256R1Field.multiply(arrn2, object, arrn2);
            SecP256R1Field.squareN(arrn2, 32, arrn2);
            SecP256R1Field.multiply(arrn2, arrn, arrn2);
            SecP256R1Field.squareN(arrn2, 96, arrn2);
            SecP256R1Field.multiply(arrn2, arrn, arrn2);
            SecP256R1Field.squareN(arrn2, 94, arrn2);
            SecP256R1Field.square(arrn2, object);
            object = Nat256.eq(arrn, object) ? new SecP256R1FieldElement(arrn2) : null;
            return object;
        }
        return this;
    }

    @Override
    public ECFieldElement square() {
        int[] arrn = Nat256.create();
        SecP256R1Field.square(this.x, arrn);
        return new SecP256R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] arrn = Nat256.create();
        SecP256R1Field.subtract(this.x, ((SecP256R1FieldElement)eCFieldElement).x, arrn);
        return new SecP256R1FieldElement(arrn);
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

