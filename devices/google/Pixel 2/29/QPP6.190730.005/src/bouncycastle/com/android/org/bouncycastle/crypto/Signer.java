/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.CryptoException;
import com.android.org.bouncycastle.crypto.DataLengthException;

public interface Signer {
    public byte[] generateSignature() throws CryptoException, DataLengthException;

    public void init(boolean var1, CipherParameters var2);

    public void reset();

    public void update(byte var1);

    public void update(byte[] var1, int var2, int var3);

    public boolean verifySignature(byte[] var1);
}

