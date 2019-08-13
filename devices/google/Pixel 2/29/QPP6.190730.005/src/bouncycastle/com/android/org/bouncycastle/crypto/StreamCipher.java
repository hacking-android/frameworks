/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;

public interface StreamCipher {
    public String getAlgorithmName();

    public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException;

    public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException;

    public void reset();

    public byte returnByte(byte var1);
}

