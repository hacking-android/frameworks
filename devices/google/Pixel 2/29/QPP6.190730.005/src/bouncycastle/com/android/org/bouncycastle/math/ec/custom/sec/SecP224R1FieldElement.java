/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP224R1Curve;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP224R1Field;
import com.android.org.bouncycastle.math.raw.Mod;
import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.math.raw.Nat224;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;

public class SecP224R1FieldElement
extends ECFieldElement.AbstractFp {
    public static final BigInteger Q = SecP224R1Curve.q;
    protected int[] x;

    public SecP224R1FieldElement() {
        this.x = Nat224.create();
    }

    public SecP224R1FieldElement(BigInteger bigInteger) {
        if (bigInteger != null && bigInteger.signum() >= 0 && bigInteger.compareTo(Q) < 0) {
            this.x = SecP224R1Field.fromBigInteger(bigInteger);
            return;
        }
        throw new IllegalArgumentException("x value invalid for SecP224R1FieldElement");
    }

    protected SecP224R1FieldElement(int[] arrn) {
        this.x = arrn;
    }

    private static void RM(int[] arrn, int[] arrn2, int[] arrn3, int[] arrn4, int[] arrn5, int[] arrn6, int[] arrn7) {
        SecP224R1Field.multiply(arrn5, arrn3, arrn7);
        SecP224R1Field.multiply(arrn7, arrn, arrn7);
        SecP224R1Field.multiply(arrn4, arrn2, arrn6);
        SecP224R1Field.add(arrn6, arrn7, arrn6);
        SecP224R1Field.multiply(arrn4, arrn3, arrn7);
        Nat224.copy(arrn6, arrn4);
        SecP224R1Field.multiply(arrn5, arrn2, arrn5);
        SecP224R1Field.add(arrn5, arrn7, arrn5);
        SecP224R1Field.square(arrn5, arrn6);
        SecP224R1Field.multiply(arrn6, arrn, arrn6);
    }

    private static void RP(int[] arrn, int[] arrn2, int[] arrn3, int[] arrn4, int[] arrn5) {
        Nat224.copy(arrn, arrn4);
        int[] arrn6 = Nat224.create();
        int[] arrn7 = Nat224.create();
        for (int i = 0; i < 7; ++i) {
            Nat224.copy(arrn2, arrn6);
            Nat224.copy(arrn3, arrn7);
            int n = 1 << i;
            while (--n >= 0) {
                SecP224R1FieldElement.RS(arrn2, arrn3, arrn4, arrn5);
            }
            SecP224R1FieldElement.RM(arrn, arrn6, arrn7, arrn2, arrn3, arrn4, arrn5);
        }
    }

    private static void RS(int[] arrn, int[] arrn2, int[] arrn3, int[] arrn4) {
        SecP224R1Field.multiply(arrn2, arrn, arrn2);
        SecP224R1Field.twice(arrn2, arrn2);
        SecP224R1Field.square(arrn, arrn4);
        SecP224R1Field.add(arrn3, arrn4, arrn);
        SecP224R1Field.multiply(arrn3, arrn4, arrn3);
        SecP224R1Field.reduce32(Nat.shiftUpBits(7, arrn3, 2, 0), arrn3);
    }

    private static boolean isSquare(int[] arrn) {
        int[] arrn2 = Nat224.create();
        int[] arrn3 = Nat224.create();
        Nat224.copy(arrn, arrn2);
        for (int i = 0; i < 7; ++i) {
            Nat224.copy(arrn2, arrn3);
            SecP224R1Field.squareN(arrn2, 1 << i, arrn2);
            SecP224R1Field.multiply(arrn2, arrn3, arrn2);
        }
        SecP224R1Field.squareN(arrn2, 95, arrn2);
        return Nat224.isOne(arrn2);
    }

    private static boolean trySqrt(int[] arrn, int[] arrn2, int[] arrn3) {
        int[] arrn4 = Nat224.create();
        Nat224.copy(arrn2, arrn4);
        int[] arrn5 = Nat224.create();
        arrn5[0] = 1;
        arrn2 = Nat224.create();
        SecP224R1FieldElement.RP(arrn, arrn4, arrn5, arrn2, arrn3);
        arrn = Nat224.create();
        int[] arrn6 = Nat224.create();
        for (int i = 1; i < 96; ++i) {
            Nat224.copy(arrn4, arrn);
            Nat224.copy(arrn5, arrn6);
            SecP224R1FieldElement.RS(arrn4, arrn5, arrn2, arrn3);
            if (!Nat224.isZero(arrn4)) continue;
            Mod.invert(SecP224R1Field.P, arrn6, arrn3);
            SecP224R1Field.multiply(arrn3, arrn, arrn3);
            return true;
        }
        return false;
    }

    @Override
    public ECFieldElement add(ECFieldElement eCFieldElement) {
        int[] arrn = Nat224.create();
        SecP224R1Field.add(this.x, ((SecP224R1FieldElement)eCFieldElement).x, arrn);
        return new SecP224R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement addOne() {
        int[] arrn = Nat224.create();
        SecP224R1Field.addOne(this.x, arrn);
        return new SecP224R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement divide(ECFieldElement eCFieldElement) {
        int[] arrn = Nat224.create();
        Mod.invert(SecP224R1Field.P, ((SecP224R1FieldElement)eCFieldElement).x, arrn);
        SecP224R1Field.multiply(arrn, this.x, arrn);
        return new SecP224R1FieldElement(arrn);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof SecP224R1FieldElement)) {
            return false;
        }
        object = (SecP224R1FieldElement)object;
        return Nat224.eq(this.x, ((SecP224R1FieldElement)object).x);
    }

    @Override
    public String getFieldName() {
        return "SecP224R1Field";
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
        Mod.invert(SecP224R1Field.P, this.x, arrn);
        return new SecP224R1FieldElement(arrn);
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
        SecP224R1Field.multiply(this.x, ((SecP224R1FieldElement)eCFieldElement).x, arrn);
        return new SecP224R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement negate() {
        int[] arrn = Nat224.create();
        SecP224R1Field.negate(this.x, arrn);
        return new SecP224R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement sqrt() {
        int[] arrn = this.x;
        if (!Nat224.isZero(arrn) && !Nat224.isOne(arrn)) {
            int[] arrn2 = Nat224.create();
            SecP224R1Field.negate(arrn, arrn2);
            int[] arrn3 = Mod.random(SecP224R1Field.P);
            int[] arrn4 = Nat224.create();
            boolean bl = SecP224R1FieldElement.isSquare(arrn);
            SecP224R1FieldElement secP224R1FieldElement = null;
            if (!bl) {
                return null;
            }
            while (!SecP224R1FieldElement.trySqrt(arrn2, arrn3, arrn4)) {
                SecP224R1Field.addOne(arrn3, arrn3);
            }
            SecP224R1Field.square(arrn4, arrn3);
            if (Nat224.eq(arrn, arrn3)) {
                secP224R1FieldElement = new SecP224R1FieldElement(arrn4);
            }
            return secP224R1FieldElement;
        }
        return this;
    }

    @Override
    public ECFieldElement square() {
        int[] arrn = Nat224.create();
        SecP224R1Field.square(this.x, arrn);
        return new SecP224R1FieldElement(arrn);
    }

    @Override
    public ECFieldElement subtract(ECFieldElement eCFieldElement) {
        int[] arrn = Nat224.create();
        SecP224R1Field.subtract(this.x, ((SecP224R1FieldElement)eCFieldElement).x, arrn);
        return new SecP224R1FieldElement(arrn);
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

