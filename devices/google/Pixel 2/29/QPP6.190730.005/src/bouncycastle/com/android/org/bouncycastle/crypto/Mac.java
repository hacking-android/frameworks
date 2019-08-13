/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;

public interface Mac {
    public int doFinal(byte[] var1, int var2) throws DataLengthException, IllegalStateException;

    public String getAlgorithmName();

    public int getMacSize();

    public void init(CipherParameters var1) throws IllegalArgumentException;

    public void reset();

    public void update(byte var1) throws IllegalStateException;

    public void update(byte[] var1, int var2, int var3) throws DataLengthException, IllegalStateException;
}

