/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.params;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.util.Arrays;

public class AEADParameters
implements CipherParameters {
    private byte[] associatedText;
    private KeyParameter key;
    private int macSize;
    private byte[] nonce;

    public AEADParameters(KeyParameter keyParameter, int n, byte[] arrby) {
        this(keyParameter, n, arrby, null);
    }

    public AEADParameters(KeyParameter keyParameter, int n, byte[] arrby, byte[] arrby2) {
        this.key = keyParameter;
        this.nonce = Arrays.clone(arrby);
        this.macSize = n;
        this.associatedText = Arrays.clone(arrby2);
    }

    public byte[] getAssociatedText() {
        return Arrays.clone(this.associatedText);
    }

    public KeyParameter getKey() {
        return this.key;
    }

    public int getMacSize() {
        return this.macSize;
    }

    public byte[] getNonce() {
        return Arrays.clone(this.nonce);
    }
}

