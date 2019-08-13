/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECLookupTable;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP192R1FieldElement;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP192R1Point;
import com.android.org.bouncycastle.math.raw.Nat192;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;

public class SecP192R1Curve
extends ECCurve.AbstractFp {
    private static final int SecP192R1_DEFAULT_COORDS = 2;
    public static final BigInteger q = new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF"));
    protected SecP192R1Point infinity = new SecP192R1Point(this, null, null);

    public SecP192R1Curve() {
        super(q);
        this.a = this.fromBigInteger(new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFC")));
        this.b = this.fromBigInteger(new BigInteger(1, Hex.decode("64210519E59C80E70FA7E9AB72243049FEB8DEECC146B9B1")));
        this.order = new BigInteger(1, Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override
    protected ECCurve cloneCurve() {
        return new SecP192R1Curve();
    }

    @Override
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] arreCPoint, int n, final int n2) {
        final int[] arrn = new int[n2 * 6 * 2];
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            ECPoint eCPoint = arreCPoint[n + i];
            Nat192.copy(((SecP192R1FieldElement)eCPoint.getRawXCoord()).x, 0, arrn, n3);
            Nat192.copy(((SecP192R1FieldElement)eCPoint.getRawYCoord()).x, 0, arrn, n3 += 6);
            n3 += 6;
        }
        return new ECLookupTable(){

            @Override
            public int getSize() {
                return n2;
            }

            @Override
            public ECPoint lookup(int n) {
                int[] arrn4 = Nat192.create();
                int[] arrn2 = Nat192.create();
                int n22 = 0;
                for (int i = 0; i < n2; ++i) {
                    int n3 = (i ^ n) - 1 >> 31;
                    for (int j = 0; j < 6; ++j) {
                        int n4 = arrn4[j];
                        int[] arrn3 = arrn;
                        arrn4[j] = n4 ^ arrn3[n22 + j] & n3;
                        arrn2[j] = arrn2[j] ^ arrn3[n22 + 6 + j] & n3;
                    }
                    n22 += 12;
                }
                return SecP192R1Curve.this.createRawPoint(new SecP192R1FieldElement(arrn4), new SecP192R1FieldElement(arrn2), false);
            }
        };
    }

    @Override
    protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
        return new SecP192R1Point(this, eCFieldElement, eCFieldElement2, bl);
    }

    @Override
    protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
        return new SecP192R1Point(this, eCFieldElement, eCFieldElement2, arreCFieldElement, bl);
    }

    @Override
    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecP192R1FieldElement(bigInteger);
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

