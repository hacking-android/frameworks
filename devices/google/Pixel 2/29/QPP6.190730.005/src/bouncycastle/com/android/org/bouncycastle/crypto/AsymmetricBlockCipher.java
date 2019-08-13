/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;

public interface AsymmetricBlockCipher {
    public int getInputBlockSize();

    public int getOutputBlockSize();

    public void init(boolean var1, CipherParameters var2);

    public byte[] processBlock(byte[] var1, int var2, int var3) throws InvalidCipherTextException;
}

