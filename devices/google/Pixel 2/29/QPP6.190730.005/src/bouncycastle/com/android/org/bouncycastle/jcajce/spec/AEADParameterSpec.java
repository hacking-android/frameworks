/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.spec;

import com.android.org.bouncycastle.util.Arrays;
import javax.crypto.spec.IvParameterSpec;

public class AEADParameterSpec
extends IvParameterSpec {
    private final byte[] associatedData;
    private final int macSizeInBits;

    public AEADParameterSpec(byte[] arrby, int n) {
        this(arrby, n, null);
    }

    public AEADParameterSpec(byte[] arrby, int n, byte[] arrby2) {
        super(arrby);
        this.macSizeInBits = n;
        this.associatedData = Arrays.clone(arrby2);
    }

    public byte[] getAssociatedData() {
        return Arrays.clone(this.associatedData);
    }

    public int getMacSizeInBits() {
        return this.macSizeInBits;
    }

    public byte[] getNonce() {
        return this.getIV();
    }
}

