/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.math.field;

import java.math.BigInteger;

public interface FiniteField {
    public BigInteger getCharacteristic();

    public int getDimension();
}

