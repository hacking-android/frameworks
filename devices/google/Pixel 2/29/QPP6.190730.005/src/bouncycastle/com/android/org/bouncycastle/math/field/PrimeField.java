/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.field;

import com.android.org.bouncycastle.math.field.FiniteField;
import java.math.BigInteger;

class PrimeField
implements FiniteField {
    protected final BigInteger characteristic;

    PrimeField(BigInteger bigInteger) {
        this.characteristic = bigInteger;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PrimeField)) {
            return false;
        }
        object = (PrimeField)object;
        return this.characteristic.equals(((PrimeField)object).characteristic);
    }

    @Override
    public BigInteger getCharacteristic() {
        return this.characteristic;
    }

    @Override
    public int getDimension() {
        return 1;
    }

    public int hashCode() {
        return this.characteristic.hashCode();
    }
}

