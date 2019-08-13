/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;

public interface AEADBlockCipher {
    public int doFinal(byte[] var1, int var2) throws IllegalStateException, InvalidCipherTextException;

    public String getAlgorithmName();

    public byte[] getMac();

    public int getOutputSize(int var1);

    public BlockCipher getUnderlyingCipher();

    public int getUpdateOutputSize(int var1);

    public void init(boolean var1, CipherParameters var2) throws IllegalArgumentException;

    public void processAADByte(byte var1);

    public void processAADBytes(byte[] var1, int var2, int var3);

    public int processByte(byte var1, byte[] var2, int var3) throws DataLengthException;

    public int processBytes(byte[] var1, int var2, int var3, byte[] var4, int var5) throws DataLengthException;

    public void reset();
}

