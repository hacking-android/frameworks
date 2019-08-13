/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP521R1Curve;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP521R1Field;
import com.android.org.bouncycastle.math.raw.Mod;
import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

public class SecP521R1FieldElement
extends ECFieldElement.AbstractFp {
    public static final BigInteger Q = SecP521R1Curve.q;
    protected int[] x;

    public SecP521R1FieldElement() {
        this.x = Nat.create(17);
    }

    public SecP521R1FieldElement(BigInteger bigInteger) {
        if (bigInteger != null && bigInteger.signum() >= 0 && bigInteger.compareTo(Q) < 0) {
            this.x = SecP521R1Field.fromBigInteger(bigInteger);
            return;
        }
        throw new IllegalArgumentException("x value invalid for SecP521R1FieldElement");
    }

    protected SecP521R1FieldElement(int[] arrn) {
        this.x = arrn;
    }

    @Override
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] arrn = Nat.create(17);
        SecP521R1Field.add(this.x, ((SecP521R1FieldElement)eCFieldElement).x, arrn);
        return new SecP521R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement addOne() {
        int[] arrn = Nat.create(17);
        SecP521R1Field.addOne(this.x, arrn);
        return new SecP521R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] arrn = Nat.create(17);
        Mod.invert(SecP521R1Field.P, ((SecP521R1FieldElement)eCFieldElement).x, arrn);
        SecP521R1Field.multiply(arrn, this.x, arrn);
        return new SecP521R1FieldElement(arrn);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SecP521R1FieldElement)) {
            return false;
        }
        object = (SecP521R1FieldElement)object;
        return Nat.eq(17, this.x, ((SecP521R1FieldElement)object).x);
    }

    @Override
    public String getFieldName() {
        return "SecP521R1Field";
    }

    @Override
    public int getFieldSize() {
        return Q.bitLength();
    }

    public int hashCode() {
        return Q.hashCode() ^ Arrays.hashCode(this.x, 0, 17);
    }

    @Override
    public ECFieldElement invert() {
        int[] arrn = Nat.create(17);
        Mod.invert(SecP521R1Field.P, this.x, arrn);
        return new SecP521R1FieldElement(arrn);
    }

    @Override
    public boolean isOne() {
        return Nat.isOne(17, this.x);
    }

    @Override
    public boolean isZero() {
        return Nat.isZero(17, this.x);
    }

    @Override
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] arrn = Nat.create(17);
        SecP521R1Field.multiply(this.x, ((SecP521R1FieldElement)eCFieldElement).x, arrn);
        return new SecP521R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement negate() {
        int[] arrn = Nat.create(17);
        SecP521R1Field.negate(this.x, arrn);
        return new SecP521R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement sqrt() {
        int[] arrn = this.x;
        if (!Nat.isZero(17, arrn) && !Nat.isOne(17, arrn)) {
            int[] arrn2 = Nat.create(17);
            Object object = Nat.create(17);
            SecP521R1Field.squareN(arrn, 519, arrn2);
            SecP521R1Field.square(arrn2, object);
            object = Nat.eq(17, arrn, object) ? new SecP521R1FieldElement(arrn2) : null;
            return object;
        }
        return this;
    }

    @Override
    public ECFieldElement square() {
        int[] arrn = Nat.create(17);
        SecP521R1Field.square(this.x, arrn);
        return new SecP521R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] arrn = Nat.create(17);
        SecP521R1Field.subtract(this.x, ((SecP521R1FieldElement)eCFieldElement).x, arrn);
        return new SecP521R1FieldElement(arrn);
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
        return Nat.toBigInteger(17, this.x);
    }
}

