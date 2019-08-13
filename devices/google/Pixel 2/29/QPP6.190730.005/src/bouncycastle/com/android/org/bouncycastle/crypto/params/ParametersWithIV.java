/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.CipherParameters;

public class ParametersWithIV
implements CipherParameters {
    private byte[] iv;
    private CipherParameters parameters;

    public ParametersWithIV(CipherParameters cipherParameters, byte[] arrby) {
        this(cipherParameters, arrby, 0, arrby.length);
    }

    public ParametersWithIV(CipherParameters cipherParameters, byte[] arrby, int n, int n2) {
        this.iv = new byte[n2];
        this.parameters = cipherParameters;
        System.arraycopy((byte[])arrby, (int)n, (byte[])this.iv, (int)0, (int)n2);
    }

    public byte[] getIV() {
        return this.iv;
    }

    public CipherParameters getParameters() {
        return this.parameters;
    }
}

