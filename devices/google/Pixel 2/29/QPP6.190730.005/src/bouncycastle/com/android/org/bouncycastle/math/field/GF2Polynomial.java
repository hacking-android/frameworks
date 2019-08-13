/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.field;

import com.android.org.bouncycastle.math.field.Polynomial;
import com.android.org.bouncycastle.util.Arrays;

class GF2Polynomial
implements Polynomial {
    protected final int[] exponents;

    GF2Polynomial(int[] arrn) {
        this.exponents = Arrays.clone(arrn);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof GF2Polynomial)) {
            return false;
        }
        object = (GF2Polynomial)object;
        return Arrays.areEqual(this.exponents, ((GF2Polynomial)object).exponents);
    }

    @Override
    public int getDegree() {
        int[] arrn = this.exponents;
        return arrn[arrn.length - 1];
    }

    @Override
    public int[] getExponentsPresent() {
        return Arrays.clone(this.exponents);
    }

    public int hashCode() {
        return Arrays.hashCode(this.exponents);
    }
}

