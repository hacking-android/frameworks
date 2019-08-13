/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.CipherParameters;

public class KeyParameter
implements CipherParameters {
    private byte[] key;

    public KeyParameter(byte[] arrby) {
        this(arrby, 0, arrby.length);
    }

    public KeyParameter(byte[] arrby, int n, int n2) {
        this.key = new byte[n2];
        System.arraycopy((byte[])arrby, (int)n, (byte[])this.key, (int)0, (int)n2);
    }

    public byte[] getKey() {
        return this.key;
    }
}

