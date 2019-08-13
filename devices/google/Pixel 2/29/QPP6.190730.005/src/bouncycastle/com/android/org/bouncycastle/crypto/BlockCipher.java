/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;

public interface BlockCipher {
    public String getAlgorithmName();

    public int getBlockSize();

    public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException;

    public int processBlock(byte[] var1, int var2, byte[] var3, int var4) throws DataLengthException, IllegalStateException;

    public void reset();
}

