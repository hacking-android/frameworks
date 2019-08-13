/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec;

import com.android.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.org.bouncycastle.math.ec.ECConstants;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECLookupTable;
import com.android.org.bouncycastle.math.ec.ECMultiplier;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.GLVMultiplier;
import com.android.org.bouncycastle.math.ec.LongArray;
import com.android.org.bouncycastle.math.ec.PreCompCallback;
import com.android.org.bouncycastle.math.ec.PreCompInfo;
import com.android.org.bouncycastle.math.ec.Tnaf;
import com.android.org.bouncycastle.math.ec.WNafL2RMultiplier;
import com.android.org.bouncycastle.math.ec.WTauNafMultiplier;
import com.android.org.bouncycastle.math.ec.endo.ECEndomorphism;
import com.android.org.bouncycastle.math.ec.endo.GLVEndomorphism;
import com.android.org.bouncycastle.math.field.FiniteField;
import com.android.org.bouncycastle.math.field.FiniteFields;
import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.util.BigIntegers;
import com.android.org.bouncycastle.util.Integers;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Random;

public abstract class ECCurve {
    public static final int COORD_AFFINE = 0;
    public static final int COORD_HOMOGENEOUS = 1;
    public static final int COORD_JACOBIAN = 2;
    public static final int COORD_JACOBIAN_CHUDNOVSKY = 3;
    public static final int COORD_JACOBIAN_MODIFIED = 4;
    public static final int COORD_LAMBDA_AFFINE = 5;
    public static final int COORD_LAMBDA_PROJECTIVE = 6;
    public static final int COORD_SKEWED = 7;
    protected ECFieldElement a;
    protected ECFieldElement b;
    protected BigInteger cofactor;
    protected int coord = 0;
    protected ECEndomorphism endomorphism = null;
    protected FiniteField field;
    protected ECMultiplier multiplier = null;
    protected BigInteger order;

    protected ECCurve(FiniteField finiteField) {
        this.field = finiteField;
    }

    public static int[] getAllCoordinateSystems() {
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    }

    protected void checkPoint(ECPoint eCPoint) {
        if (eCPoint != null && this == eCPoint.getCurve()) {
            return;
        }
        throw new IllegalArgumentException("'point' must be non-null and on this curve");
    }

    protected void checkPoints(ECPoint[] arreCPoint) {
        this.checkPoints(arreCPoint, 0, arreCPoint.length);
    }

    protected void checkPoints(ECPoint[] arreCPoint, int n, int n2) {
        if (arreCPoint != null) {
            if (n >= 0 && n2 >= 0 && n <= arreCPoint.length - n2) {
                for (int i = 0; i < n2; ++i) {
                    ECPoint eCPoint = arreCPoint[n + i];
                    if (eCPoint == null || this == eCPoint.getCurve()) continue;
                    throw new IllegalArgumentException("'points' entries must be null or on this curve");
                }
                return;
            }
            throw new IllegalArgumentException("invalid range specified for 'points'");
        }
        throw new IllegalArgumentException("'points' cannot be null");
    }

    protected abstract ECCurve cloneCurve();

    public Config configure() {
        synchronized (this) {
            Config config = new Config(this.coord, this.endomorphism, this.multiplier);
            return config;
        }
    }

    public ECLookupTable createCacheSafeLookupTable(ECPoint[] arreCPoint, int n, final int n2) {
        final int n3 = this.getFieldSize() + 7 >>> 3;
        final byte[] arrby = new byte[n2 * n3 * 2];
        int n4 = 0;
        for (int i = 0; i < n2; ++i) {
            byte[] arrby2 = arreCPoint[n + i];
            byte[] arrby3 = arrby2.getRawXCoord().toBigInteger().toByteArray();
            arrby2 = arrby2.getRawYCoord().toBigInteger().toByteArray();
            int n5 = arrby3.length;
            int n6 = 0;
            n5 = n5 > n3 ? 1 : 0;
            int n7 = arrby3.length - n5;
            if (arrby2.length > n3) {
                n6 = 1;
            }
            int n8 = arrby2.length - n6;
            System.arraycopy((byte[])arrby3, (int)n5, (byte[])arrby, (int)(n4 + n3 - n7), (int)n7);
            System.arraycopy((byte[])arrby2, (int)n6, (byte[])arrby, (int)((n4 += n3) + n3 - n8), (int)n8);
            n4 += n3;
        }
        return new ECLookupTable(){

            @Override
            public int getSize() {
                return n2;
            }

            @Override
            public ECPoint lookup(int n) {
                byte[] arrby4;
                int n22 = n3;
                byte[] arrby2 = new byte[n22];
                byte[] arrby3 = new byte[n22];
                int n32 = 0;
                for (n22 = 0; n22 < n2; ++n22) {
                    int n4;
                    int n5 = (n22 ^ n) - 1 >> 31;
                    for (int i = 0; i < (n4 = n3); ++i) {
                        byte by = arrby2[i];
                        arrby4 = arrby;
                        arrby2[i] = (byte)(by ^ arrby4[n32 + i] & n5);
                        by = arrby3[i];
                        arrby3[i] = (byte)(arrby4[n4 + n32 + i] & n5 ^ by);
                    }
                    n32 += n4 * 2;
                }
                arrby4 = ECCurve.this;
                return arrby4.createRawPoint(arrby4.fromBigInteger(new BigInteger(1, arrby2)), ECCurve.this.fromBigInteger(new BigInteger(1, arrby3)), false);
            }
        };
    }

    protected ECMultiplier createDefaultMultiplier() {
        ECEndomorphism eCEndomorphism = this.endomorphism;
        if (eCEndomorphism instanceof GLVEndomorphism) {
            return new GLVMultiplier(this, (GLVEndomorphism)eCEndomorphism);
        }
        return new WNafL2RMultiplier();
    }

    public ECPoint createPoint(BigInteger bigInteger, BigInteger bigInteger2) {
        return this.createPoint(bigInteger, bigInteger2, false);
    }

    public ECPoint createPoint(BigInteger bigInteger, BigInteger bigInteger2, boolean bl) {
        return this.createRawPoint(this.fromBigInteger(bigInteger), this.fromBigInteger(bigInteger2), bl);
    }

    protected abstract ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, boolean var3);

    protected abstract ECPoint createRawPoint(ECFieldElement var1, ECFieldElement var2, ECFieldElement[] var3, boolean var4);

    public ECPoint decodePoint(byte[] object) {
        block16 : {
            byte by;
            block13 : {
                block8 : {
                    block15 : {
                        int n;
                        block9 : {
                            block14 : {
                                block10 : {
                                    block11 : {
                                        block12 : {
                                            n = (this.getFieldSize() + 7) / 8;
                                            boolean bl = false;
                                            by = object[0];
                                            if (by == 0) break block8;
                                            if (by == 2 || by == 3) break block9;
                                            if (by == 4) break block10;
                                            if (by != 6 && by != 7) {
                                                object = new StringBuilder();
                                                ((StringBuilder)object).append("Invalid point encoding 0x");
                                                ((StringBuilder)object).append(Integer.toString(by, 16));
                                                throw new IllegalArgumentException(((StringBuilder)object).toString());
                                            }
                                            if (((Object)object).length != n * 2 + 1) break block11;
                                            BigInteger bigInteger = BigIntegers.fromUnsignedByteArray((byte[])object, 1, n);
                                            object = BigIntegers.fromUnsignedByteArray((byte[])object, n + 1, n);
                                            boolean bl2 = ((BigInteger)object).testBit(0);
                                            if (by == 7) {
                                                bl = true;
                                            }
                                            if (bl2 != bl) break block12;
                                            object = this.validatePoint(bigInteger, (BigInteger)object);
                                            break block13;
                                        }
                                        throw new IllegalArgumentException("Inconsistent Y coordinate in hybrid encoding");
                                    }
                                    throw new IllegalArgumentException("Incorrect length for hybrid encoding");
                                }
                                if (((byte[])object).length != n * 2 + 1) break block14;
                                object = this.validatePoint(BigIntegers.fromUnsignedByteArray((byte[])object, 1, n), BigIntegers.fromUnsignedByteArray((byte[])object, n + 1, n));
                                break block13;
                            }
                            throw new IllegalArgumentException("Incorrect length for uncompressed encoding");
                        }
                        if (((byte[])object).length != n + 1) break block15;
                        if (!((ECPoint)(object = this.decompressPoint(by & 1, BigIntegers.fromUnsignedByteArray((byte[])object, 1, n)))).implIsValid(true, true)) {
                            throw new IllegalArgumentException("Invalid point");
                        }
                        break block13;
                    }
                    throw new IllegalArgumentException("Incorrect length for compressed encoding");
                }
                if (((byte[])object).length != 1) break block16;
                object = this.getInfinity();
            }
            if (by != 0 && ((ECPoint)object).isInfinity()) {
                throw new IllegalArgumentException("Invalid infinity encoding");
            }
            return object;
        }
        throw new IllegalArgumentException("Incorrect length for infinity encoding");
    }

    protected abstract ECPoint decompressPoint(int var1, BigInteger var2);

    public boolean equals(ECCurve eCCurve) {
        boolean bl = this == eCCurve || eCCurve != null && this.getField().equals(eCCurve.getField()) && this.getA().toBigInteger().equals(eCCurve.getA().toBigInteger()) && this.getB().toBigInteger().equals(eCCurve.getB().toBigInteger());
        return bl;
    }

    public boolean equals(Object object) {
        boolean bl = this == object || object instanceof ECCurve && this.equals((ECCurve)object);
        return bl;
    }

    public abstract ECFieldElement fromBigInteger(BigInteger var1);

    public ECFieldElement getA() {
        return this.a;
    }

    public ECFieldElement getB() {
        return this.b;
    }

    public BigInteger getCofactor() {
        return this.cofactor;
    }

    public int getCoordinateSystem() {
        return this.coord;
    }

    public ECEndomorphism getEndomorphism() {
        return this.endomorphism;
    }

    public FiniteField getField() {
        return this.field;
    }

    public abstract int getFieldSize();

    public abstract ECPoint getInfinity();

    public ECMultiplier getMultiplier() {
        synchronized (this) {
            if (this.multiplier == null) {
                this.multiplier = this.createDefaultMultiplier();
            }
            ECMultiplier eCMultiplier = this.multiplier;
            return eCMultiplier;
        }
    }

    public BigInteger getOrder() {
        return this.order;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public PreCompInfo getPreCompInfo(ECPoint object, String string) {
        this.checkPoint((ECPoint)object);
        // MONITORENTER : object
        Hashtable hashtable = ((ECPoint)object).preCompTable;
        // MONITOREXIT : object
        if (hashtable == null) {
            return null;
        }
        // MONITORENTER : hashtable
        object = (PreCompInfo)hashtable.get(string);
        // MONITOREXIT : hashtable
        return object;
    }

    public int hashCode() {
        return this.getField().hashCode() ^ Integers.rotateLeft(this.getA().toBigInteger().hashCode(), 8) ^ Integers.rotateLeft(this.getB().toBigInteger().hashCode(), 16);
    }

    public ECPoint importPoint(ECPoint eCPoint) {
        if (this == eCPoint.getCurve()) {
            return eCPoint;
        }
        if (eCPoint.isInfinity()) {
            return this.getInfinity();
        }
        eCPoint = eCPoint.normalize();
        return this.createPoint(eCPoint.getXCoord().toBigInteger(), eCPoint.getYCoord().toBigInteger(), eCPoint.withCompression);
    }

    public abstract boolean isValidFieldElement(BigInteger var1);

    public void normalizeAll(ECPoint[] arreCPoint) {
        this.normalizeAll(arreCPoint, 0, arreCPoint.length, null);
    }

    public void normalizeAll(ECPoint[] arreCPoint, int n, int n2, ECFieldElement eCFieldElement) {
        block7 : {
            this.checkPoints(arreCPoint, n, n2);
            int n3 = this.getCoordinateSystem();
            if (n3 == 0 || n3 == 5) break block7;
            ECFieldElement[] arreCFieldElement = new ECFieldElement[n2];
            int[] arrn = new int[n2];
            n3 = 0;
            for (int i = 0; i < n2; ++i) {
                int n4;
                block8 : {
                    ECPoint eCPoint;
                    block9 : {
                        eCPoint = arreCPoint[n + i];
                        n4 = n3;
                        if (eCPoint == null) break block8;
                        if (eCFieldElement != null) break block9;
                        n4 = n3;
                        if (eCPoint.isNormalized()) break block8;
                    }
                    arreCFieldElement[n3] = eCPoint.getZCoord(0);
                    arrn[n3] = n + i;
                    n4 = n3 + 1;
                }
                n3 = n4;
            }
            if (n3 == 0) {
                return;
            }
            ECAlgorithms.montgomeryTrick(arreCFieldElement, 0, n3, eCFieldElement);
            for (n = 0; n < n3; ++n) {
                n2 = arrn[n];
                arreCPoint[n2] = arreCPoint[n2].normalize(arreCFieldElement[n]);
            }
            return;
        }
        if (eCFieldElement == null) {
            return;
        }
        throw new IllegalArgumentException("'iso' not valid for affine coordinates");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public PreCompInfo precompute(ECPoint object, String string, PreCompCallback object2) {
        Hashtable<String, Object> hashtable;
        this.checkPoint((ECPoint)object);
        synchronized (object) {
            Hashtable<String, Object> hashtable2;
            hashtable = hashtable2 = ((ECPoint)object).preCompTable;
            if (hashtable2 == null) {
                hashtable = hashtable2 = new Hashtable<String, Object>(4);
                ((ECPoint)object).preCompTable = hashtable2;
            }
        }
        synchronized (hashtable) {
            object = (PreCompInfo)hashtable.get(string);
            object2 = object2.precompute((PreCompInfo)object);
            if (object2 != object) {
                hashtable.put(string, object2);
            }
            return object2;
        }
    }

    public boolean supportsCoordinateSystem(int n) {
        boolean bl = n == 0;
        return bl;
    }

    public ECPoint validatePoint(BigInteger object, BigInteger bigInteger) {
        if (((ECPoint)(object = this.createPoint((BigInteger)object, bigInteger))).isValid()) {
            return object;
        }
        throw new IllegalArgumentException("Invalid point coordinates");
    }

    public ECPoint validatePoint(BigInteger object, BigInteger bigInteger, boolean bl) {
        if (((ECPoint)(object = this.createPoint((BigInteger)object, bigInteger, bl))).isValid()) {
            return object;
        }
        throw new IllegalArgumentException("Invalid point coordinates");
    }

    public static abstract class AbstractF2m
    extends ECCurve {
        private BigInteger[] si = null;

        protected AbstractF2m(int n, int n2, int n3, int n4) {
            super(AbstractF2m.buildField(n, n2, n3, n4));
        }

        private static FiniteField buildField(int n, int n2, int n3, int n4) {
            if (n2 != 0) {
                if (n3 == 0) {
                    if (n4 == 0) {
                        return FiniteFields.getBinaryExtensionField(new int[]{0, n2, n});
                    }
                    throw new IllegalArgumentException("k3 must be 0 if k2 == 0");
                }
                if (n3 > n2) {
                    if (n4 > n3) {
                        return FiniteFields.getBinaryExtensionField(new int[]{0, n2, n3, n4, n});
                    }
                    throw new IllegalArgumentException("k3 must be > k2");
                }
                throw new IllegalArgumentException("k2 must be > k1");
            }
            throw new IllegalArgumentException("k1 must be > 0");
        }

        public static BigInteger inverse(int n, int[] arrn, BigInteger bigInteger) {
            return new LongArray(bigInteger).modInverse(n, arrn).toBigInteger();
        }

        @Override
        public ECPoint createPoint(BigInteger object, BigInteger bigInteger, boolean bl) {
            ECFieldElement eCFieldElement = this.fromBigInteger((BigInteger)object);
            object = this.fromBigInteger(bigInteger);
            int n = this.getCoordinateSystem();
            if (n == 5 || n == 6) {
                if (eCFieldElement.isZero()) {
                    if (!((ECFieldElement)object).square().equals(this.getB())) {
                        throw new IllegalArgumentException();
                    }
                } else {
                    object = ((ECFieldElement)object).divide(eCFieldElement).add(eCFieldElement);
                }
            }
            return this.createRawPoint(eCFieldElement, (ECFieldElement)object, bl);
        }

        @Override
        protected ECPoint decompressPoint(int n, BigInteger object) {
            ECFieldElement eCFieldElement = this.fromBigInteger((BigInteger)object);
            object = null;
            if (eCFieldElement.isZero()) {
                object = this.getB().sqrt();
            } else {
                ECFieldElement eCFieldElement2 = this.solveQuadraticEquation(eCFieldElement.square().invert().multiply(this.getB()).add(this.getA()).add(eCFieldElement));
                if (eCFieldElement2 != null) {
                    boolean bl = eCFieldElement2.testBitZero();
                    boolean bl2 = n == 1;
                    object = eCFieldElement2;
                    if (bl != bl2) {
                        object = eCFieldElement2.addOne();
                    }
                    object = (n = this.getCoordinateSystem()) != 5 && n != 6 ? ((ECFieldElement)object).multiply(eCFieldElement) : ((ECFieldElement)object).add(eCFieldElement);
                }
            }
            if (object != null) {
                return this.createRawPoint(eCFieldElement, (ECFieldElement)object, true);
            }
            throw new IllegalArgumentException("Invalid point compression");
        }

        BigInteger[] getSi() {
            synchronized (this) {
                if (this.si == null) {
                    this.si = Tnaf.getSi(this);
                }
                BigInteger[] arrbigInteger = this.si;
                return arrbigInteger;
            }
        }

        public boolean isKoblitz() {
            boolean bl = this.order != null && this.cofactor != null && this.b.isOne() && (this.a.isZero() || this.a.isOne());
            return bl;
        }

        @Override
        public boolean isValidFieldElement(BigInteger bigInteger) {
            boolean bl = bigInteger != null && bigInteger.signum() >= 0 && bigInteger.bitLength() <= this.getFieldSize();
            return bl;
        }

        protected ECFieldElement solveQuadraticEquation(ECFieldElement eCFieldElement) {
            ECFieldElement eCFieldElement2;
            if (eCFieldElement.isZero()) {
                return eCFieldElement;
            }
            ECFieldElement eCFieldElement3 = this.fromBigInteger(ECConstants.ZERO);
            int n = this.getFieldSize();
            Random random = new Random();
            do {
                ECFieldElement eCFieldElement4 = this.fromBigInteger(new BigInteger(n, random));
                eCFieldElement2 = eCFieldElement3;
                ECFieldElement eCFieldElement5 = eCFieldElement;
                for (int i = 1; i < n; ++i) {
                    eCFieldElement5 = eCFieldElement5.square();
                    eCFieldElement2 = eCFieldElement2.square().add(eCFieldElement5.multiply(eCFieldElement4));
                    eCFieldElement5 = eCFieldElement5.add(eCFieldElement);
                }
                if (eCFieldElement5.isZero()) continue;
                return null;
            } while (eCFieldElement2.square().add(eCFieldElement2).isZero());
            return eCFieldElement2;
        }
    }

    public static abstract class AbstractFp
    extends ECCurve {
        protected AbstractFp(BigInteger bigInteger) {
            super(FiniteFields.getPrimeField(bigInteger));
        }

        @Override
        protected ECPoint decompressPoint(int n, BigInteger object) {
            ECFieldElement eCFieldElement = this.fromBigInteger((BigInteger)object);
            ECFieldElement eCFieldElement2 = eCFieldElement.square().add(this.a).multiply(eCFieldElement).add(this.b).sqrt();
            if (eCFieldElement2 != null) {
                boolean bl = eCFieldElement2.testBitZero();
                boolean bl2 = n == 1;
                object = eCFieldElement2;
                if (bl != bl2) {
                    object = eCFieldElement2.negate();
                }
                return this.createRawPoint(eCFieldElement, (ECFieldElement)object, true);
            }
            throw new IllegalArgumentException("Invalid point compression");
        }

        @Override
        public boolean isValidFieldElement(BigInteger bigInteger) {
            boolean bl = bigInteger != null && bigInteger.signum() >= 0 && bigInteger.compareTo(this.getField().getCharacteristic()) < 0;
            return bl;
        }
    }

    public class Config {
        protected int coord;
        protected ECEndomorphism endomorphism;
        protected ECMultiplier multiplier;

        Config(int n, ECEndomorphism eCEndomorphism, ECMultiplier eCMultiplier) {
            this.coord = n;
            this.endomorphism = eCEndomorphism;
            this.multiplier = eCMultiplier;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public ECCurve create() {
            if (!ECCurve.this.supportsCoordinateSystem(this.coord)) {
                throw new IllegalStateException("unsupported coordinate system");
            }
            ECCurve eCCurve = ECCurve.this.cloneCurve();
            if (eCCurve != ECCurve.this) {
                synchronized (eCCurve) {
                    eCCurve.coord = this.coord;
                    eCCurve.endomorphism = this.endomorphism;
                    eCCurve.multiplier = this.multiplier;
                    return eCCurve;
                }
            }
            throw new IllegalStateException("implementation returned current curve");
        }

        public Config setCoordinateSystem(int n) {
            this.coord = n;
            return this;
        }

        public Config setEndomorphism(ECEndomorphism eCEndomorphism) {
            this.endomorphism = eCEndomorphism;
            return this;
        }

        public Config setMultiplier(ECMultiplier eCMultiplier) {
            this.multiplier = eCMultiplier;
            return this;
        }
    }

    public static class F2m
    extends AbstractF2m {
        private static final int F2M_DEFAULT_COORDS = 6;
        private ECPoint.F2m infinity;
        private int k1;
        private int k2;
        private int k3;
        private int m;

        protected F2m(int n, int n2, int n3, int n4, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, BigInteger bigInteger, BigInteger bigInteger2) {
            super(n, n2, n3, n4);
            this.m = n;
            this.k1 = n2;
            this.k2 = n3;
            this.k3 = n4;
            this.order = bigInteger;
            this.cofactor = bigInteger2;
            this.infinity = new ECPoint.F2m(this, null, null, false);
            this.a = eCFieldElement;
            this.b = eCFieldElement2;
            this.coord = 6;
        }

        public F2m(int n, int n2, int n3, int n4, BigInteger bigInteger, BigInteger bigInteger2) {
            this(n, n2, n3, n4, bigInteger, bigInteger2, null, null);
        }

        public F2m(int n, int n2, int n3, int n4, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
            super(n, n2, n3, n4);
            this.m = n;
            this.k1 = n2;
            this.k2 = n3;
            this.k3 = n4;
            this.order = bigInteger3;
            this.cofactor = bigInteger4;
            this.infinity = new ECPoint.F2m(this, null, null, false);
            this.a = this.fromBigInteger(bigInteger);
            this.b = this.fromBigInteger(bigInteger2);
            this.coord = 6;
        }

        public F2m(int n, int n2, BigInteger bigInteger, BigInteger bigInteger2) {
            this(n, n2, 0, 0, bigInteger, bigInteger2, null, null);
        }

        public F2m(int n, int n2, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
            this(n, n2, 0, 0, bigInteger, bigInteger2, bigInteger3, bigInteger4);
        }

        @Override
        protected ECCurve cloneCurve() {
            return new F2m(this.m, this.k1, this.k2, this.k3, this.a, this.b, this.order, this.cofactor);
        }

        @Override
        public ECLookupTable createCacheSafeLookupTable(ECPoint[] arreCPoint, int n, final int n2) {
            final int n3 = this.m + 63 >>> 6;
            final int[] arrn = this.isTrinomial() ? new int[]{this.k1} : new int[]{this.k1, this.k2, this.k3};
            final long[] arrl = new long[n2 * n3 * 2];
            int n4 = 0;
            for (int i = 0; i < n2; ++i) {
                ECPoint eCPoint = arreCPoint[n + i];
                ((ECFieldElement.F2m)eCPoint.getRawXCoord()).x.copyTo(arrl, n4);
                ((ECFieldElement.F2m)eCPoint.getRawYCoord()).x.copyTo(arrl, n4 += n3);
                n4 += n3;
            }
            return new ECLookupTable(){

                @Override
                public int getSize() {
                    return n2;
                }

                @Override
                public ECPoint lookup(int n) {
                    long[] arrl4;
                    long[] arrl2 = Nat.create64(n3);
                    long[] arrl3 = Nat.create64(n3);
                    int n22 = 0;
                    for (int i = 0; i < n2; ++i) {
                        int n32;
                        long l = (i ^ n) - 1 >> 31;
                        for (int j = 0; j < (n32 = n3); ++j) {
                            long l2 = arrl2[j];
                            arrl4 = arrl;
                            arrl2[j] = l2 ^ arrl4[n22 + j] & l;
                            arrl3[j] = arrl3[j] ^ arrl4[n32 + n22 + j] & l;
                        }
                        n22 += n32 * 2;
                    }
                    arrl4 = this;
                    return arrl4.createRawPoint(new ECFieldElement.F2m(((F2m)arrl4).m, arrn, new LongArray(arrl2)), new ECFieldElement.F2m(m, arrn, new LongArray(arrl3)), false);
                }
            };
        }

        @Override
        protected ECMultiplier createDefaultMultiplier() {
            if (this.isKoblitz()) {
                return new WTauNafMultiplier();
            }
            return super.createDefaultMultiplier();
        }

        @Override
        protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
            return new ECPoint.F2m(this, eCFieldElement, eCFieldElement2, bl);
        }

        @Override
        protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
            return new ECPoint.F2m(this, eCFieldElement, eCFieldElement2, arreCFieldElement, bl);
        }

        @Override
        public ECFieldElement fromBigInteger(BigInteger bigInteger) {
            return new ECFieldElement.F2m(this.m, this.k1, this.k2, this.k3, bigInteger);
        }

        @Override
        public int getFieldSize() {
            return this.m;
        }

        @Override
        public ECPoint getInfinity() {
            return this.infinity;
        }

        public int getK1() {
            return this.k1;
        }

        public int getK2() {
            return this.k2;
        }

        public int getK3() {
            return this.k3;
        }

        public int getM() {
            return this.m;
        }

        public boolean isTrinomial() {
            boolean bl = this.k2 == 0 && this.k3 == 0;
            return bl;
        }

        @Override
        public boolean supportsCoordinateSystem(int n) {
            return n == 0 || n == 1 || n == 6;
        }

    }

    public static class Fp
    extends AbstractFp {
        private static final int FP_DEFAULT_COORDS = 4;
        ECPoint.Fp infinity;
        BigInteger q;
        BigInteger r;

        protected Fp(BigInteger bigInteger, BigInteger bigInteger2, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            this(bigInteger, bigInteger2, eCFieldElement, eCFieldElement2, null, null);
        }

        protected Fp(BigInteger bigInteger, BigInteger bigInteger2, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, BigInteger bigInteger3, BigInteger bigInteger4) {
            super(bigInteger);
            this.q = bigInteger;
            this.r = bigInteger2;
            this.infinity = new ECPoint.Fp(this, null, null, false);
            this.a = eCFieldElement;
            this.b = eCFieldElement2;
            this.order = bigInteger3;
            this.cofactor = bigInteger4;
            this.coord = 4;
        }

        public Fp(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
            this(bigInteger, bigInteger2, bigInteger3, null, null);
        }

        public Fp(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5) {
            super(bigInteger);
            this.q = bigInteger;
            this.r = ECFieldElement.Fp.calculateResidue(bigInteger);
            this.infinity = new ECPoint.Fp(this, null, null, false);
            this.a = this.fromBigInteger(bigInteger2);
            this.b = this.fromBigInteger(bigInteger3);
            this.order = bigInteger4;
            this.cofactor = bigInteger5;
            this.coord = 4;
        }

        @Override
        protected ECCurve cloneCurve() {
            return new Fp(this.q, this.r, this.a, this.b, this.order, this.cofactor);
        }

        @Override
        protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
            return new ECPoint.Fp(this, eCFieldElement, eCFieldElement2, bl);
        }

        @Override
        protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
            return new ECPoint.Fp(this, eCFieldElement, eCFieldElement2, arreCFieldElement, bl);
        }

        @Override
        public ECFieldElement fromBigInteger(BigInteger bigInteger) {
            return new ECFieldElement.Fp(this.q, this.r, bigInteger);
        }

        @Override
        public int getFieldSize() {
            return this.q.bitLength();
        }

        @Override
        public ECPoint getInfinity() {
            return this.infinity;
        }

        public BigInteger getQ() {
            return this.q;
        }

        @Override
        public ECPoint importPoint(ECPoint eCPoint) {
            int n;
            if (!(this == eCPoint.getCurve() || this.getCoordinateSystem() != 2 || eCPoint.isInfinity() || (n = eCPoint.getCurve().getCoordinateSystem()) != 2 && n != 3 && n != 4)) {
                ECFieldElement eCFieldElement = this.fromBigInteger(eCPoint.x.toBigInteger());
                ECFieldElement eCFieldElement2 = this.fromBigInteger(eCPoint.y.toBigInteger());
                ECFieldElement eCFieldElement3 = this.fromBigInteger(eCPoint.zs[0].toBigInteger());
                boolean bl = eCPoint.withCompression;
                return new ECPoint.Fp(this, eCFieldElement, eCFieldElement2, new ECFieldElement[]{eCFieldElement3}, bl);
            }
            return super.importPoint(eCPoint);
        }

        @Override
        public boolean supportsCoordinateSystem(int n) {
            return n == 0 || n == 1 || n == 2 || n == 4;
        }
    }

}

