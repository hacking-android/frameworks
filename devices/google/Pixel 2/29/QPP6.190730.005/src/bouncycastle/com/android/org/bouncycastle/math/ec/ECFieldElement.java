/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.ECConstants;
import com.android.org.bouncycastle.math.ec.LongArray;
import com.android.org.bouncycastle.math.raw.Mod;
import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.BigIntegers;
import java.math.BigInteger;
import java.util.Random;

public abstract class ECFieldElement
implements ECConstants {
    public abstract ECFieldElement add(ECFieldElement var1);

    public abstract ECFieldElement addOne();

    public int bitLength() {
        return this.toBigInteger().bitLength();
    }

    public abstract ECFieldElement divide(ECFieldElement var1);

    public byte[] getEncoded() {
        return BigIntegers.asUnsignedByteArray((this.getFieldSize() + 7) / 8, this.toBigInteger());
    }

    public abstract String getFieldName();

    public abstract int getFieldSize();

    public abstract ECFieldElement invert();

    public boolean isOne() {
        int n = this.bitLength();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isZero() {
        boolean bl = this.toBigInteger().signum() == 0;
        return bl;
    }

    public abstract ECFieldElement multiply(ECFieldElement var1);

    public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        return this.multiply(eCFieldElement).subtract(eCFieldElement2.multiply(eCFieldElement3));
    }

    public ECFieldElement multiplyPlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
        return this.multiply(eCFieldElement).add(eCFieldElement2.multiply(eCFieldElement3));
    }

    public abstract ECFieldElement negate();

    public abstract ECFieldElement sqrt();

    public abstract ECFieldElement square();

    public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return this.square().subtract(eCFieldElement.multiply(eCFieldElement2));
    }

    public ECFieldElement squarePlusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return this.square().add(eCFieldElement.multiply(eCFieldElement2));
    }

    public ECFieldElement squarePow(int n) {
        ECFieldElement eCFieldElement = this;
        for (int i = 0; i < n; ++i) {
            eCFieldElement = eCFieldElement.square();
        }
        return eCFieldElement;
    }

    public abstract ECFieldElement subtract(ECFieldElement var1);

    public boolean testBitZero() {
        return this.toBigInteger().testBit(0);
    }

    public abstract BigInteger toBigInteger();

    public String toString() {
        return this.toBigInteger().toString(16);
    }

    public static abstract class AbstractF2m
    extends ECFieldElement {
        public ECFieldElement halfTrace() {
            int n = this.getFieldSize();
            if ((n & 1) != 0) {
                ECFieldElement eCFieldElement;
                ECFieldElement eCFieldElement2 = eCFieldElement = this;
                for (int i = 2; i < n; i += 2) {
                    eCFieldElement = eCFieldElement.squarePow(2);
                    eCFieldElement2 = eCFieldElement2.add(eCFieldElement);
                }
                return eCFieldElement2;
            }
            throw new IllegalStateException("Half-trace only defined for odd m");
        }

        public int trace() {
            ECFieldElement eCFieldElement;
            int n = this.getFieldSize();
            ECFieldElement eCFieldElement2 = eCFieldElement = this;
            for (int i = 1; i < n; ++i) {
                eCFieldElement = eCFieldElement.square();
                eCFieldElement2 = eCFieldElement2.add(eCFieldElement);
            }
            if (eCFieldElement2.isZero()) {
                return 0;
            }
            if (eCFieldElement2.isOne()) {
                return 1;
            }
            throw new IllegalStateException("Internal error in trace calculation");
        }
    }

    public static abstract class AbstractFp
    extends ECFieldElement {
    }

    public static class F2m
    extends AbstractF2m {
        public static final int GNB = 1;
        public static final int PPB = 3;
        public static final int TPB = 2;
        private int[] ks;
        private int m;
        private int representation;
        LongArray x;

        public F2m(int n, int n2, int n3, int n4, BigInteger bigInteger) {
            block2 : {
                block5 : {
                    block6 : {
                        block4 : {
                            block3 : {
                                if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > n) break block2;
                                if (n3 != 0 || n4 != 0) break block3;
                                this.representation = 2;
                                this.ks = new int[]{n2};
                                break block4;
                            }
                            if (n3 >= n4) break block5;
                            if (n3 <= 0) break block6;
                            this.representation = 3;
                            this.ks = new int[]{n2, n3, n4};
                        }
                        this.m = n;
                        this.x = new LongArray(bigInteger);
                        return;
                    }
                    throw new IllegalArgumentException("k2 must be larger than 0");
                }
                throw new IllegalArgumentException("k2 must be smaller than k3");
            }
            throw new IllegalArgumentException("x value invalid in F2m field element");
        }

        F2m(int n, int[] arrn, LongArray longArray) {
            this.m = n;
            n = arrn.length == 1 ? 2 : 3;
            this.representation = n;
            this.ks = arrn;
            this.x = longArray;
        }

        public static void checkFieldElements(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            if (eCFieldElement instanceof F2m && eCFieldElement2 instanceof F2m) {
                eCFieldElement = (F2m)eCFieldElement;
                eCFieldElement2 = (F2m)eCFieldElement2;
                if (((F2m)eCFieldElement).representation == ((F2m)eCFieldElement2).representation) {
                    if (((F2m)eCFieldElement).m == ((F2m)eCFieldElement2).m && Arrays.areEqual(((F2m)eCFieldElement).ks, ((F2m)eCFieldElement2).ks)) {
                        return;
                    }
                    throw new IllegalArgumentException("Field elements are not elements of the same field F2m");
                }
                throw new IllegalArgumentException("One of the F2m field elements has incorrect representation");
            }
            throw new IllegalArgumentException("Field elements are not both instances of ECFieldElement.F2m");
        }

        @Override
        public ECFieldElement add(ECFieldElement eCFieldElement) {
            LongArray longArray = (LongArray)this.x.clone();
            longArray.addShiftedByWords(((F2m)eCFieldElement).x, 0);
            return new F2m(this.m, this.ks, longArray);
        }

        @Override
        public ECFieldElement addOne() {
            return new F2m(this.m, this.ks, this.x.addOne());
        }

        @Override
        public int bitLength() {
            return this.x.degree();
        }

        @Override
        public ECFieldElement divide(ECFieldElement eCFieldElement) {
            return this.multiply(eCFieldElement.invert());
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof F2m)) {
                return false;
            }
            object = (F2m)object;
            if (this.m != ((F2m)object).m || this.representation != ((F2m)object).representation || !Arrays.areEqual(this.ks, ((F2m)object).ks) || !this.x.equals(((F2m)object).x)) {
                bl = false;
            }
            return bl;
        }

        @Override
        public String getFieldName() {
            return "F2m";
        }

        @Override
        public int getFieldSize() {
            return this.m;
        }

        public int getK1() {
            return this.ks[0];
        }

        public int getK2() {
            int[] arrn = this.ks;
            int n = arrn.length >= 2 ? arrn[1] : 0;
            return n;
        }

        public int getK3() {
            int[] arrn = this.ks;
            int n = arrn.length >= 3 ? arrn[2] : 0;
            return n;
        }

        public int getM() {
            return this.m;
        }

        public int getRepresentation() {
            return this.representation;
        }

        public int hashCode() {
            return this.x.hashCode() ^ this.m ^ Arrays.hashCode(this.ks);
        }

        @Override
        public ECFieldElement invert() {
            int n = this.m;
            int[] arrn = this.ks;
            return new F2m(n, arrn, this.x.modInverse(n, arrn));
        }

        @Override
        public boolean isOne() {
            return this.x.isOne();
        }

        @Override
        public boolean isZero() {
            return this.x.isZero();
        }

        @Override
        public ECFieldElement multiply(ECFieldElement eCFieldElement) {
            int n = this.m;
            int[] arrn = this.ks;
            return new F2m(n, arrn, this.x.modMultiply(((F2m)eCFieldElement).x, n, arrn));
        }

        @Override
        public ECFieldElement multiplyMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3) {
            return this.multiplyPlusProduct(eCFieldElement, eCFieldElement2, eCFieldElement3);
        }

        @Override
        public ECFieldElement multiplyPlusProduct(ECFieldElement object, ECFieldElement object2, ECFieldElement object3) {
            block3 : {
                block2 : {
                    LongArray longArray = this.x;
                    LongArray longArray2 = ((F2m)object).x;
                    object = ((F2m)object2).x;
                    object3 = ((F2m)object3).x;
                    object2 = longArray.multiply(longArray2, this.m, this.ks);
                    object3 = ((LongArray)object).multiply((LongArray)object3, this.m, this.ks);
                    if (object2 == longArray) break block2;
                    object = object2;
                    if (object2 != longArray2) break block3;
                }
                object = (LongArray)((LongArray)object2).clone();
            }
            ((LongArray)object).addShiftedByWords((LongArray)object3, 0);
            ((LongArray)object).reduce(this.m, this.ks);
            return new F2m(this.m, this.ks, (LongArray)object);
        }

        @Override
        public ECFieldElement negate() {
            return this;
        }

        @Override
        public ECFieldElement sqrt() {
            ECFieldElement eCFieldElement = !this.x.isZero() && !this.x.isOne() ? this.squarePow(this.m - 1) : this;
            return eCFieldElement;
        }

        @Override
        public ECFieldElement square() {
            int n = this.m;
            int[] arrn = this.ks;
            return new F2m(n, arrn, this.x.modSquare(n, arrn));
        }

        @Override
        public ECFieldElement squareMinusProduct(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            return this.squarePlusProduct(eCFieldElement, eCFieldElement2);
        }

        @Override
        public ECFieldElement squarePlusProduct(ECFieldElement object, ECFieldElement object2) {
            LongArray longArray = this.x;
            object = ((F2m)object).x;
            LongArray longArray2 = ((F2m)object2).x;
            object2 = longArray.square(this.m, this.ks);
            longArray2 = ((LongArray)object).multiply(longArray2, this.m, this.ks);
            object = object2;
            if (object2 == longArray) {
                object = (LongArray)((LongArray)object2).clone();
            }
            ((LongArray)object).addShiftedByWords(longArray2, 0);
            ((LongArray)object).reduce(this.m, this.ks);
            return new F2m(this.m, this.ks, (LongArray)object);
        }

        @Override
        public ECFieldElement squarePow(int n) {
            Object object;
            if (n < 1) {
                object = this;
            } else {
                int n2 = this.m;
                object = this.ks;
                object = new F2m(n2, (int[])object, this.x.modSquareN(n, n2, (int[])object));
            }
            return object;
        }

        @Override
        public ECFieldElement subtract(ECFieldElement eCFieldElement) {
            return this.add(eCFieldElement);
        }

        @Override
        public boolean testBitZero() {
            return this.x.testBitZero();
        }

        @Override
        public BigInteger toBigInteger() {
            return this.x.toBigInteger();
        }
    }

    public static class Fp
    extends AbstractFp {
        BigInteger q;
        BigInteger r;
        BigInteger x;

        public Fp(BigInteger bigInteger, BigInteger bigInteger2) {
            this(bigInteger, Fp.calculateResidue(bigInteger), bigInteger2);
        }

        Fp(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
            if (bigInteger3 != null && bigInteger3.signum() >= 0 && bigInteger3.compareTo(bigInteger) < 0) {
                this.q = bigInteger;
                this.r = bigInteger2;
                this.x = bigInteger3;
                return;
            }
            throw new IllegalArgumentException("x value invalid in Fp field element");
        }

        static BigInteger calculateResidue(BigInteger bigInteger) {
            int n = bigInteger.bitLength();
            if (n >= 96 && bigInteger.shiftRight(n - 64).longValue() == -1L) {
                return ONE.shiftLeft(n).subtract(bigInteger);
            }
            return null;
        }

        private ECFieldElement checkSqrt(ECFieldElement eCFieldElement) {
            if (!eCFieldElement.square().equals(this)) {
                eCFieldElement = null;
            }
            return eCFieldElement;
        }

        private BigInteger[] lucasSequence(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
            int n = bigInteger3.bitLength();
            int n2 = bigInteger3.getLowestSetBit();
            BigInteger bigInteger4 = ECConstants.ONE;
            BigInteger bigInteger5 = ECConstants.TWO;
            BigInteger bigInteger6 = bigInteger;
            BigInteger bigInteger7 = ECConstants.ONE;
            BigInteger bigInteger8 = ECConstants.ONE;
            --n;
            while (n >= n2 + 1) {
                bigInteger7 = this.modMult(bigInteger7, bigInteger8);
                if (bigInteger3.testBit(n)) {
                    bigInteger8 = this.modMult(bigInteger7, bigInteger2);
                    bigInteger4 = this.modMult(bigInteger4, bigInteger6);
                    bigInteger5 = this.modReduce(bigInteger6.multiply(bigInteger5).subtract(bigInteger.multiply(bigInteger7)));
                    bigInteger6 = this.modReduce(bigInteger6.multiply(bigInteger6).subtract(bigInteger8.shiftLeft(1)));
                } else {
                    bigInteger8 = bigInteger7;
                    bigInteger4 = this.modReduce(bigInteger4.multiply(bigInteger5).subtract(bigInteger7));
                    bigInteger6 = this.modReduce(bigInteger6.multiply(bigInteger5).subtract(bigInteger.multiply(bigInteger7)));
                    bigInteger5 = this.modReduce(bigInteger5.multiply(bigInteger5).subtract(bigInteger7.shiftLeft(1)));
                }
                --n;
            }
            bigInteger8 = this.modMult(bigInteger7, bigInteger8);
            bigInteger7 = this.modMult(bigInteger8, bigInteger2);
            bigInteger3 = this.modReduce(bigInteger4.multiply(bigInteger5).subtract(bigInteger8));
            bigInteger2 = this.modReduce(bigInteger6.multiply(bigInteger5).subtract(bigInteger.multiply(bigInteger8)));
            bigInteger = this.modMult(bigInteger8, bigInteger7);
            for (n = 1; n <= n2; ++n) {
                bigInteger3 = this.modMult(bigInteger3, bigInteger2);
                bigInteger2 = this.modReduce(bigInteger2.multiply(bigInteger2).subtract(bigInteger.shiftLeft(1)));
                bigInteger = this.modMult(bigInteger, bigInteger);
            }
            return new BigInteger[]{bigInteger3, bigInteger2};
        }

        @Override
        public ECFieldElement add(ECFieldElement eCFieldElement) {
            return new Fp(this.q, this.r, this.modAdd(this.x, eCFieldElement.toBigInteger()));
        }

        @Override
        public ECFieldElement addOne() {
            BigInteger bigInteger;
            BigInteger bigInteger2 = bigInteger = this.x.add(ECConstants.ONE);
            if (bigInteger.compareTo(this.q) == 0) {
                bigInteger2 = ECConstants.ZERO;
            }
            return new Fp(this.q, this.r, bigInteger2);
        }

        @Override
        public ECFieldElement divide(ECFieldElement eCFieldElement) {
            return new Fp(this.q, this.r, this.modMult(this.x, this.modInverse(eCFieldElement.toBigInteger())));
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof Fp)) {
                return false;
            }
            object = (Fp)object;
            if (!this.q.equals(((Fp)object).q) || !this.x.equals(((Fp)object).x)) {
                bl = false;
            }
            return bl;
        }

        @Override
        public String getFieldName() {
            return "Fp";
        }

        @Override
        public int getFieldSize() {
            return this.q.bitLength();
        }

        public BigInteger getQ() {
            return this.q;
        }

        public int hashCode() {
            return this.q.hashCode() ^ this.x.hashCode();
        }

        @Override
        public ECFieldElement invert() {
            return new Fp(this.q, this.r, this.modInverse(this.x));
        }

        protected BigInteger modAdd(BigInteger bigInteger, BigInteger bigInteger2) {
            bigInteger = bigInteger2 = bigInteger.add(bigInteger2);
            if (bigInteger2.compareTo(this.q) >= 0) {
                bigInteger = bigInteger2.subtract(this.q);
            }
            return bigInteger;
        }

        protected BigInteger modDouble(BigInteger bigInteger) {
            BigInteger bigInteger2;
            bigInteger = bigInteger2 = bigInteger.shiftLeft(1);
            if (bigInteger2.compareTo(this.q) >= 0) {
                bigInteger = bigInteger2.subtract(this.q);
            }
            return bigInteger;
        }

        protected BigInteger modHalf(BigInteger bigInteger) {
            BigInteger bigInteger2 = bigInteger;
            if (bigInteger.testBit(0)) {
                bigInteger2 = this.q.add(bigInteger);
            }
            return bigInteger2.shiftRight(1);
        }

        protected BigInteger modHalfAbs(BigInteger bigInteger) {
            BigInteger bigInteger2 = bigInteger;
            if (bigInteger.testBit(0)) {
                bigInteger2 = this.q.subtract(bigInteger);
            }
            return bigInteger2.shiftRight(1);
        }

        protected BigInteger modInverse(BigInteger arrn) {
            int n = this.getFieldSize();
            int n2 = n + 31 >> 5;
            int[] arrn2 = Nat.fromBigInteger(n, this.q);
            arrn = Nat.fromBigInteger(n, (BigInteger)arrn);
            int[] arrn3 = Nat.create(n2);
            Mod.invert(arrn2, arrn, arrn3);
            return Nat.toBigInteger(n2, arrn3);
        }

        protected BigInteger modMult(BigInteger bigInteger, BigInteger bigInteger2) {
            return this.modReduce(bigInteger.multiply(bigInteger2));
        }

        protected BigInteger modReduce(BigInteger bigInteger) {
            BigInteger bigInteger2;
            if (this.r != null) {
                boolean bl = bigInteger.signum() < 0;
                bigInteger2 = bigInteger;
                if (bl) {
                    bigInteger2 = bigInteger.abs();
                }
                int n = this.q.bitLength();
                boolean bl2 = this.r.equals(ECConstants.ONE);
                do {
                    bigInteger = bigInteger2;
                    if (bigInteger2.bitLength() <= n + 1) break;
                    BigInteger bigInteger3 = bigInteger2.shiftRight(n);
                    bigInteger2 = bigInteger2.subtract(bigInteger3.shiftLeft(n));
                    bigInteger = bigInteger3;
                    if (!bl2) {
                        bigInteger = bigInteger3.multiply(this.r);
                    }
                    bigInteger2 = bigInteger.add(bigInteger2);
                } while (true);
                while (bigInteger.compareTo(this.q) >= 0) {
                    bigInteger = bigInteger.subtract(this.q);
                }
                bigInteger2 = bigInteger;
                if (bl) {
                    bigInteger2 = bigInteger;
                    if (bigInteger.signum() != 0) {
                        bigInteger2 = this.q.subtract(bigInteger);
                    }
                }
            } else {
                bigInteger2 = bigInteger.mod(this.q);
            }
            return bigInteger2;
        }

        protected BigInteger modSubtract(BigInteger bigInteger, BigInteger bigInteger2) {
            bigInteger = bigInteger2 = bigInteger.subtract(bigInteger2);
            if (bigInteger2.signum() < 0) {
                bigInteger = bigInteger2.add(this.q);
            }
            return bigInteger;
        }

        @Override
        public ECFieldElement multiply(ECFieldElement eCFieldElement) {
            return new Fp(this.q, this.r, this.modMult(this.x, eCFieldElement.toBigInteger()));
        }

        @Override
        public ECFieldElement multiplyMinusProduct(ECFieldElement object, ECFieldElement object2, ECFieldElement object3) {
            BigInteger bigInteger = this.x;
            object = ((ECFieldElement)object).toBigInteger();
            object2 = ((ECFieldElement)object2).toBigInteger();
            object3 = ((ECFieldElement)object3).toBigInteger();
            object = bigInteger.multiply((BigInteger)object);
            object2 = ((BigInteger)object2).multiply((BigInteger)object3);
            return new Fp(this.q, this.r, this.modReduce(((BigInteger)object).subtract((BigInteger)object2)));
        }

        @Override
        public ECFieldElement multiplyPlusProduct(ECFieldElement object, ECFieldElement object2, ECFieldElement object3) {
            BigInteger bigInteger = this.x;
            BigInteger bigInteger2 = ((ECFieldElement)object).toBigInteger();
            object = ((ECFieldElement)object2).toBigInteger();
            object2 = ((ECFieldElement)object3).toBigInteger();
            object3 = bigInteger.multiply(bigInteger2);
            object = ((BigInteger)object).multiply((BigInteger)object2);
            return new Fp(this.q, this.r, this.modReduce(((BigInteger)object3).add((BigInteger)object)));
        }

        @Override
        public ECFieldElement negate() {
            Object object;
            if (this.x.signum() == 0) {
                object = this;
            } else {
                object = this.q;
                object = new Fp((BigInteger)object, this.r, ((BigInteger)object).subtract(this.x));
            }
            return object;
        }

        @Override
        public ECFieldElement sqrt() {
            if (!this.isZero() && !this.isOne()) {
                if (this.q.testBit(0)) {
                    if (this.q.testBit(1)) {
                        BigInteger bigInteger = this.q.shiftRight(2).add(ECConstants.ONE);
                        BigInteger bigInteger2 = this.q;
                        return this.checkSqrt(new Fp(bigInteger2, this.r, this.x.modPow(bigInteger, bigInteger2)));
                    }
                    if (this.q.testBit(2)) {
                        BigInteger bigInteger = this.x.modPow(this.q.shiftRight(3), this.q);
                        BigInteger bigInteger3 = this.modMult(bigInteger, this.x);
                        if (this.modMult(bigInteger3, bigInteger).equals(ECConstants.ONE)) {
                            return this.checkSqrt(new Fp(this.q, this.r, bigInteger3));
                        }
                        bigInteger = this.modMult(bigInteger3, ECConstants.TWO.modPow(this.q.shiftRight(2), this.q));
                        return this.checkSqrt(new Fp(this.q, this.r, bigInteger));
                    }
                    BigInteger bigInteger = this.q.shiftRight(1);
                    if (!this.x.modPow(bigInteger, this.q).equals(ECConstants.ONE)) {
                        return null;
                    }
                    BigInteger bigInteger4 = this.x;
                    BigInteger bigInteger5 = this.modDouble(this.modDouble(bigInteger4));
                    BigInteger bigInteger6 = bigInteger.add(ECConstants.ONE);
                    BigInteger bigInteger7 = this.q.subtract(ECConstants.ONE);
                    Random random = new Random();
                    do {
                        BigInteger bigInteger8;
                        if ((bigInteger8 = new BigInteger(this.q.bitLength(), random)).compareTo(this.q) >= 0 || !this.modReduce(bigInteger8.multiply(bigInteger8).subtract(bigInteger5)).modPow(bigInteger, this.q).equals(bigInteger7)) {
                            continue;
                        }
                        Object object = this.lucasSequence(bigInteger8, bigInteger4, bigInteger6);
                        bigInteger8 = object[0];
                        if (this.modMult((BigInteger)(object = object[1]), (BigInteger)object).equals(bigInteger5)) {
                            return new Fp(this.q, this.r, this.modHalfAbs((BigInteger)object));
                        }
                        if (!bigInteger8.equals(ECConstants.ONE) && !bigInteger8.equals(bigInteger7)) break;
                    } while (true);
                    return null;
                }
                throw new RuntimeException("not done yet");
            }
            return this;
        }

        @Override
        public ECFieldElement square() {
            BigInteger bigInteger = this.q;
            BigInteger bigInteger2 = this.r;
            BigInteger bigInteger3 = this.x;
            return new Fp(bigInteger, bigInteger2, this.modMult(bigInteger3, bigInteger3));
        }

        @Override
        public ECFieldElement squareMinusProduct(ECFieldElement object, ECFieldElement object2) {
            BigInteger bigInteger = this.x;
            object = ((ECFieldElement)object).toBigInteger();
            BigInteger bigInteger2 = ((ECFieldElement)object2).toBigInteger();
            object2 = bigInteger.multiply(bigInteger);
            object = ((BigInteger)object).multiply(bigInteger2);
            return new Fp(this.q, this.r, this.modReduce(((BigInteger)object2).subtract((BigInteger)object)));
        }

        @Override
        public ECFieldElement squarePlusProduct(ECFieldElement object, ECFieldElement object2) {
            BigInteger bigInteger = this.x;
            object = ((ECFieldElement)object).toBigInteger();
            BigInteger bigInteger2 = ((ECFieldElement)object2).toBigInteger();
            object2 = bigInteger.multiply(bigInteger);
            object = ((BigInteger)object).multiply(bigInteger2);
            return new Fp(this.q, this.r, this.modReduce(((BigInteger)object2).add((BigInteger)object)));
        }

        @Override
        public ECFieldElement subtract(ECFieldElement eCFieldElement) {
            return new Fp(this.q, this.r, this.modSubtract(this.x, eCFieldElement.toBigInteger()));
        }

        @Override
        public BigInteger toBigInteger() {
            return this.x;
        }
    }

}

