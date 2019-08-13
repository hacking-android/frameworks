/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.field;

import com.android.org.bouncycastle.math.field.FiniteField;
import com.android.org.bouncycastle.math.field.Polynomial;
import com.android.org.bouncycastle.math.field.PolynomialExtensionField;
import com.android.org.bouncycastle.util.Integers;
import java.math.BigInteger;

class GenericPolynomialExtensionField
implements PolynomialExtensionField {
    protected final Polynomial minimalPolynomial;
    protected final FiniteField subfield;

    GenericPolynomialExtensionField(FiniteField finiteField, Polynomial polynomial) {
        this.subfield = finiteField;
        this.minimalPolynomial = polynomial;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof GenericPolynomialExtensionField)) {
            return false;
        }
        object = (GenericPolynomialExtensionField)object;
        if (!this.subfield.equals(((GenericPolynomialExtensionField)object).subfield) || !this.minimalPolynomial.equals(((GenericPolynomialExtensionField)object).minimalPolynomial)) {
            bl = false;
        }
        return bl;
    }

    @Override
    public BigInteger getCharacteristic() {
        return this.subfield.getCharacteristic();
    }

    @Override
    public int getDegree() {
        return this.minimalPolynomial.getDegree();
    }

    @Override
    public int getDimension() {
        return this.subfield.getDimension() * this.minimalPolynomial.getDegree();
    }

    @Override
    public Polynomial getMinimalPolynomial() {
        return this.minimalPolynomial;
    }

    @Override
    public FiniteField getSubfield() {
        return this.subfield;
    }

    public int hashCode() {
        return this.subfield.hashCode() ^ Integers.rotateLeft(this.minimalPolynomial.hashCode(), 16);
    }
}

