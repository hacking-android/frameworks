/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.util.Arrays;

public class DHValidationParameters {
    private int counter;
    private byte[] seed;

    public DHValidationParameters(byte[] arrby, int n) {
        this.seed = Arrays.clone(arrby);
        this.counter = n;
    }

    public boolean equals(Object object) {
        if (!(object instanceof DHValidationParameters)) {
            return false;
        }
        object = (DHValidationParameters)object;
        if (((DHValidationParameters)object).counter != this.counter) {
            return false;
        }
        return Arrays.areEqual(this.seed, ((DHValidationParameters)object).seed);
    }

    public int getCounter() {
        return this.counter;
    }

    public byte[] getSeed() {
        return Arrays.clone(this.seed);
    }

    public int hashCode() {
        return this.counter ^ Arrays.hashCode(this.seed);
    }
}

