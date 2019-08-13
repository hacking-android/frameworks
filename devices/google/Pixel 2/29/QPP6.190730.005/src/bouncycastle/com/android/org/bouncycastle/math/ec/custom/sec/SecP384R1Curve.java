/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECLookupTable;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP384R1Point;
import com.android.org.bouncycastle.math.raw.Nat;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;

public class SecP384R1Curve
extends ECCurve.AbstractFp {
    private static final int SecP384R1_DEFAULT_COORDS = 2;
    public static final BigInteger q = new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFF"));
    protected SecP384R1Point infinity = new SecP384R1Point(this, null, null);

    public SecP384R1Curve() {
        super(q);
        this.a = this.fromBigInteger(new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFC")));
        this.b = this.fromBigInteger(new BigInteger(1, Hex.decode("B3312FA7E23EE7E4988E056BE3F82D19181D9C6EFE8141120314088F5013875AC656398D8A2ED19D2A85C8EDD3EC2AEF")));
        this.order = new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC7634D81F4372DDF581A0DB248B0A77AECEC196ACCC52973"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override
    protected ECCurve cloneCurve() {
        return new SecP384R1Curve();
    }

    @Override
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] arreCPoint, int n, final int n2) {
        final int[] arrn = new int[n2 * 12 * 2];
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            ECPoint eCPoint = arreCPoint[n + i];
            Nat.copy(12, ((SecP384R1FieldElement)eCPoint.getRawXCoord()).x, 0, arrn, n3);
            Nat.copy(12, ((SecP384R1FieldElement)eCPoint.getRawYCoord()).x, 0, arrn, n3 += 12);
            n3 += 12;
        }
        return new ECLookupTable(){

            @Override
            public int getSize() {
                return n2;
            }

            @Override
            public ECPoint lookup(int n) {
                int[] arrn4 = Nat.create(12);
                int[] arrn2 = Nat.create(12);
                int n22 = 0;
                for (int i = 0; i < n2; ++i) {
                    int n3 = (i ^ n) - 1 >> 31;
                    for (int j = 0; j < 12; ++j) {
                        int n4 = arrn4[j];
                        int[] arrn3 = arrn;
                        arrn4[j] = n4 ^ arrn3[n22 + j] & n3;
                        arrn2[j] = arrn2[j] ^ arrn3[n22 + 12 + j] & n3;
                    }
                    n22 += 24;
                }
                return SecP384R1Curve.this.createRawPoint(new SecP384R1FieldElement(arrn4), new SecP384R1FieldElement(arrn2), false);
            }
        };
    }

    @Override
    protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
        return new SecP384R1Point(this, eCFieldElement, eCFieldElement2, bl);
    }

    @Override
    protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
        return new SecP384R1Point(this, eCFieldElement, eCFieldElement2, arreCFieldElement, bl);
    }

    @Override
    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecP384R1FieldElement(bigInteger);
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

