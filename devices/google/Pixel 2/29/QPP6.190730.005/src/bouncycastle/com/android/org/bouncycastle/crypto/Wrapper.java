/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;

public interface Wrapper {
    public String getAlgorithmName();

    public void init(boolean var1, CipherParameters var2);

    public byte[] unwrap(byte[] var1, int var2, int var3) throws InvalidCipherTextException;

    public byte[] wrap(byte[] var1, int var2, int var3);
}

