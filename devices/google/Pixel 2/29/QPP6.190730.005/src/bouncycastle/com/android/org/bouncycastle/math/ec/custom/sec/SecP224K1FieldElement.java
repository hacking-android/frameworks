/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP224K1Curve;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP224K1Field;
import com.android.org.bouncycastle.math.raw.Mod;
import com.android.org.bouncycastle.math.raw.Nat224;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

public class SecP224K1FieldElement
extends ECFieldElement.AbstractFp {
    private static final int[] PRECOMP_POW2;
    public static final BigInteger Q;
    protected int[] x;

    static {
        Q = SecP224K1Curve.q;
        PRECOMP_POW2 = new int[]{868209154, -587542221, 579297866, -1014948952, -1470801668, 514782679, -1897982644};
    }

    public SecP224K1FieldElement() {
        this.x = Nat224.create();
    }

    public SecP224K1FieldElement(BigInteger bigInteger) {
        if (bigInteger != null && bigInteger.signum() >= 0 && bigInteger.compareTo(Q) < 0) {
            this.x = SecP224K1Field.fromBigInteger(bigInteger);
            return;
        }
        throw new IllegalArgumentException("x value invalid for SecP224K1FieldElement");
    }

    protected SecP224K1FieldElement(int[] arrn) {
        this.x = arrn;
    }

    @Override
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] arrn = Nat224.create();
        SecP224K1Field.add(this.x, ((SecP224K1FieldElement)eCFieldElement).x, arrn);
        return new SecP224K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement addOne() {
        int[] arrn = Nat224.create();
        SecP224K1Field.addOne(this.x, arrn);
        return new SecP224K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] arrn = Nat224.create();
        Mod.invert(SecP224K1Field.P, ((SecP224K1FieldElement)eCFieldElement).x, arrn);
        SecP224K1Field.multiply(arrn, this.x, arrn);
        return new SecP224K1FieldElement(arrn);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SecP224K1FieldElement)) {
            return false;
        }
        object = (SecP224K1FieldElement)object;
        return Nat224.eq(this.x, ((SecP224K1FieldElement)object).x);
    }

    @Override
    public String getFieldName() {
        return "SecP224K1Field";
    }

    @Override
    public int getFieldSize() {
        return Q.bitLength();
    }

    public int hashCode() {
        return Q.hashCode() ^ Arrays.hashCode(this.x, 0, 7);
    }

    @Override
    public ECFieldElement invert() {
        int[] arrn = Nat224.create();
        Mod.invert(SecP224K1Field.P, this.x, arrn);
        return new SecP224K1FieldElement(arrn);
    }

    @Override
    public boolean isOne() {
        return Nat224.isOne(this.x);
    }

    @Override
    public boolean isZero() {
        return Nat224.isZero(this.x);
    }

    @Override
    public ECFieldElement multiply(ECFieldElement eCFieldElement) {
        int[] arrn = Nat224.create();
        SecP224K1Field.multiply(this.x, ((SecP224K1FieldElement)eCFieldElement).x, arrn);
        return new SecP224K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement negate() {
        int[] arrn = Nat224.create();
        SecP224K1Field.negate(this.x, arrn);
        return new SecP224K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement sqrt() {
        int[] arrn = this.x;
        if (!Nat224.isZero(arrn) && !Nat224.isOne(arrn)) {
            int[] arrn2 = Nat224.create();
            SecP224K1Field.square(arrn, arrn2);
            SecP224K1Field.multiply(arrn2, arrn, arrn2);
            SecP224K1Field.square(arrn2, arrn2);
            SecP224K1Field.multiply(arrn2, arrn, arrn2);
            int[] arrn3 = Nat224.create();
            SecP224K1Field.square(arrn2, arrn3);
            SecP224K1Field.multiply(arrn3, arrn, arrn3);
            int[] arrn4 = Nat224.create();
            SecP224K1Field.squareN(arrn3, 4, arrn4);
            SecP224K1Field.multiply(arrn4, arrn3, arrn4);
            int[] arrn5 = Nat224.create();
            SecP224K1Field.squareN(arrn4, 3, arrn5);
            SecP224K1Field.multiply(arrn5, arrn2, arrn5);
            SecP224K1Field.squareN(arrn5, 8, arrn5);
            SecP224K1Field.multiply(arrn5, arrn4, arrn5);
            SecP224K1Field.squareN(arrn5, 4, arrn4);
            SecP224K1Field.multiply(arrn4, arrn3, arrn4);
            SecP224K1Field.squareN(arrn4, 19, arrn3);
            SecP224K1Field.multiply(arrn3, arrn5, arrn3);
            int[] arrn6 = Nat224.create();
            SecP224K1Field.squareN(arrn3, 42, arrn6);
            SecP224K1Field.multiply(arrn6, arrn3, arrn6);
            SecP224K1Field.squareN(arrn6, 23, arrn3);
            SecP224K1Field.multiply(arrn3, arrn4, arrn3);
            SecP224K1Field.squareN(arrn3, 84, arrn4);
            SecP224K1Field.multiply(arrn4, arrn6, arrn4);
            SecP224K1Field.squareN(arrn4, 20, arrn4);
            SecP224K1Field.multiply(arrn4, arrn5, arrn4);
            SecP224K1Field.squareN(arrn4, 3, arrn4);
            SecP224K1Field.multiply(arrn4, arrn, arrn4);
            SecP224K1Field.squareN(arrn4, 2, arrn4);
            SecP224K1Field.multiply(arrn4, arrn, arrn4);
            SecP224K1Field.squareN(arrn4, 4, arrn4);
            SecP224K1Field.multiply(arrn4, arrn2, arrn4);
            SecP224K1Field.square(arrn4, arrn4);
            SecP224K1Field.square(arrn4, arrn6);
            if (Nat224.eq(arrn, arrn6)) {
                return new SecP224K1FieldElement(arrn4);
            }
            SecP224K1Field.multiply(arrn4, PRECOMP_POW2, arrn4);
            SecP224K1Field.square(arrn4, arrn6);
            if (Nat224.eq(arrn, arrn6)) {
                return new SecP224K1FieldElement(arrn4);
            }
            return null;
        }
        return this;
    }

    @Override
    public ECFieldElement square() {
        int[] arrn = Nat224.create();
        SecP224K1Field.square(this.x, arrn);
        return new SecP224K1FieldElement(arrn);
    }

    @Override
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] arrn = Nat224.create();
        SecP224K1Field.subtract(this.x, ((SecP224K1FieldElement)eCFieldElement).x, arrn);
        return new SecP224K1FieldElement(arrn);
    }

    @Override
    public boolean testBitZero() {
        int[] arrn = this.x;
        boolean bl = false;
        if (Nat224.getBit(arrn, 0) == 1) {
            bl = true;
        }
        return bl;
    }

    @Override
    public BigInteger toBigInteger() {
        return Nat224.toBigInteger(this.x);
    }
}

