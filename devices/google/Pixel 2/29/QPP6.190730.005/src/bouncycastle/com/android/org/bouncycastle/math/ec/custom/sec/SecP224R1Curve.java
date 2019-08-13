/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECLookupTable;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP224R1Point;
import com.android.org.bouncycastle.math.raw.Nat224;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;

public class SecP224R1Curve
extends ECCurve.AbstractFp {
    private static final int SecP224R1_DEFAULT_COORDS = 2;
    public static final BigInteger q = new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000000000000000000001"));
    protected SecP224R1Point infinity = new SecP224R1Point(this, null, null);

    public SecP224R1Curve() {
        super(q);
        this.a = this.fromBigInteger(new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFE")));
        this.b = this.fromBigInteger(new BigInteger(1, Hex.decode("B4050A850C04B3ABF54132565044B0B7D7BFD8BA270B39432355FFB4")));
        this.order = new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFF16A2E0B8F03E13DD29455C5C2A3D"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override
    protected ECCurve cloneCurve() {
        return new SecP224R1Curve();
    }

    @Override
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] arreCPoint, int n, final int n2) {
        final int[] arrn = new int[n2 * 7 * 2];
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            ECPoint eCPoint = arreCPoint[n + i];
            Nat224.copy(((SecP224R1FieldElement)eCPoint.getRawXCoord()).x, 0, arrn, n3);
            Nat224.copy(((SecP224R1FieldElement)eCPoint.getRawYCoord()).x, 0, arrn, n3 += 7);
            n3 += 7;
        }
        return new ECLookupTable(){

            @Override
            public int getSize() {
                return n2;
            }

            @Override
            public ECPoint lookup(int n) {
                int[] arrn4 = Nat224.create();
                int[] arrn2 = Nat224.create();
                int n22 = 0;
                for (int i = 0; i < n2; ++i) {
                    int n3 = (i ^ n) - 1 >> 31;
                    for (int j = 0; j < 7; ++j) {
                        int n4 = arrn4[j];
                        int[] arrn3 = arrn;
                        arrn4[j] = n4 ^ arrn3[n22 + j] & n3;
                        arrn2[j] = arrn2[j] ^ arrn3[n22 + 7 + j] & n3;
                    }
                    n22 += 14;
                }
                return SecP224R1Curve.this.createRawPoint(new SecP224R1FieldElement(arrn4), new SecP224R1FieldElement(arrn2), false);
            }
        };
    }

    @Override
    protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
        return new SecP224R1Point(this, eCFieldElement, eCFieldElement2, bl);
    }

    @Override
    protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
        return new SecP224R1Point(this, eCFieldElement, eCFieldElement2, arreCFieldElement, bl);
    }

    @Override
    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecP224R1FieldElement(bigInteger);
    }

    @Override
    public int getFieldSize() {
        return q.bitLength();
    }

    @Override
    public ECPoint getInfinity() {
        return this.infinity;
    }

    public BigInteger getQ() {
        return q;
    }

    @Override
    public boolean supportsCoordinateSystem(int n) {
        return n == 2;
    }

}

