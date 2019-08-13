/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.util.Arrays;

public class DSAValidationParameters {
    private int counter;
    private byte[] seed;
    private int usageIndex;

    public DSAValidationParameters(byte[] arrby, int n) {
        this(arrby, n, -1);
    }

    public DSAValidationParameters(byte[] arrby, int n, int n2) {
        this.seed = Arrays.clone(arrby);
        this.counter = n;
        this.usageIndex = n2;
    }

    public boolean equals(Object object) {
        if (!(object instanceof DSAValidationParameters)) {
            return false;
        }
        object = (DSAValidationParameters)object;
        if (((DSAValidationParameters)object).counter != this.counter) {
            return false;
        }
        return Arrays.areEqual(this.seed, ((DSAValidationParameters)object).seed);
    }

    public int getCounter() {
        return this.counter;
    }

    public byte[] getSeed() {
        return Arrays.clone(this.seed);
    }

    public int getUsageIndex() {
        return this.usageIndex;
    }

    public int hashCode() {
        return this.counter ^ Arrays.hashCode(this.seed);
    }
}

