/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.ec.custom.sec;

import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECLookupTable;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement;
import com.android.org.bouncycastle.math.ec.custom.sec.SecP256R1Point;
import com.android.org.bouncycastle.math.raw.Nat256;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.math.BigInteger;

public class SecP256R1Curve
extends ECCurve.AbstractFp {
    private static final int SecP256R1_DEFAULT_COORDS = 2;
    public static final BigInteger q = new BigInteger(1, Hex.decode("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF"));
    protected SecP256R1Point infinity = new SecP256R1Point(this, null, null);

    public SecP256R1Curve() {
        super(q);
        this.a = this.fromBigInteger(new BigInteger(1, Hex.decode("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC")));
        this.b = this.fromBigInteger(new BigInteger(1, Hex.decode("5AC635D8AA3A93E7B3EBBD55769886BC651D06B0CC53B0F63BCE3C3E27D2604B")));
        this.order = new BigInteger(1, Hex.decode("FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551"));
        this.cofactor = BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override
    protected ECCurve cloneCurve() {
        return new SecP256R1Curve();
    }

    @Override
    public ECLookupTable createCacheSafeLookupTable(ECPoint[] arreCPoint, int n, final int n2) {
        final int[] arrn = new int[n2 * 8 * 2];
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            ECPoint eCPoint = arreCPoint[n + i];
            Nat256.copy(((SecP256R1FieldElement)eCPoint.getRawXCoord()).x, 0, arrn, n3);
            Nat256.copy(((SecP256R1FieldElement)eCPoint.getRawYCoord()).x, 0, arrn, n3 += 8);
            n3 += 8;
        }
        return new ECLookupTable(){

            @Override
            public int getSize() {
                return n2;
            }

            @Override
            public ECPoint lookup(int n) {
                int[] arrn4 = Nat256.create();
                int[] arrn2 = Nat256.create();
                int n22 = 0;
                for (int i = 0; i < n2; ++i) {
                    int n3 = (i ^ n) - 1 >> 31;
                    for (int j = 0; j < 8; ++j) {
                        int n4 = arrn4[j];
                        int[] arrn3 = arrn;
                        arrn4[j] = n4 ^ arrn3[n22 + j] & n3;
                        arrn2[j] = arrn2[j] ^ arrn3[n22 + 8 + j] & n3;
                    }
                    n22 += 16;
                }
                return SecP256R1Curve.this.createRawPoint(new SecP256R1FieldElement(arrn4), new SecP256R1FieldElement(arrn2), false);
            }
        };
    }

    @Override
    protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean bl) {
        return new SecP256R1Point(this, eCFieldElement, eCFieldElement2, bl);
    }

    @Override
    protected ECPoint createRawPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] arreCFieldElement, boolean bl) {
        return new SecP256R1Point(this, eCFieldElement, eCFieldElement2, arreCFieldElement, bl);
    }

    @Override
    public ECFieldElement fromBigInteger(BigInteger bigInteger) {
        return new SecP256R1FieldElement(bigInteger);
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

