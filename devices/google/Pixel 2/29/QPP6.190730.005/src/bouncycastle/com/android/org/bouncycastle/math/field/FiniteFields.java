/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.field;

import com.android.org.bouncycastle.math.field.FiniteField;
import com.android.org.bouncycastle.math.field.GF2Polynomial;
import com.android.org.bouncycastle.math.field.GenericPolynomialExtensionField;
import com.android.org.bouncycastle.math.field.Polynomial;
import com.android.org.bouncycastle.math.field.PolynomialExtensionField;
import com.android.org.bouncycastle.math.field.PrimeField;
import java.math.BigInteger;

public abstract class FiniteFields {
    static final FiniteField GF_2 = new PrimeField(BigInteger.valueOf(2L));
    static final FiniteField GF_3 = new PrimeField(BigInteger.valueOf(3L));

    public static PolynomialExtensionField getBinaryExtensionField(int[] arrn) {
        if (arrn[0] == 0) {
            for (int i = 1; i < arrn.length; ++i) {
                if (arrn[i] > arrn[i - 1]) {
                    continue;
                }
                throw new IllegalArgumentException("Polynomial exponents must be montonically increasing");
            }
            return new GenericPolynomialExtensionField(GF_2, new GF2Polynomial(arrn));
        }
        throw new IllegalArgumentException("Irreducible polynomials in GF(2) must have constant term");
    }

    public static FiniteField getPrimeField(BigInteger bigInteger) {
        int n = bigInteger.bitLength();
        if (bigInteger.signum() > 0 && n >= 2) {
            if (n < 3) {
                n = bigInteger.intValue();
                if (n != 2) {
                    if (n == 3) {
                        return GF_3;
                    }
                } else {
                    return GF_2;
                }
            }
            return new PrimeField(bigInteger);
        }
        throw new IllegalArgumentException("'characteristic' must be >= 2");
    }
}

