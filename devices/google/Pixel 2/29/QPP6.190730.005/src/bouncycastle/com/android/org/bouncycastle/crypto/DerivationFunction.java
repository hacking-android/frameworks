/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.DerivationParameters;

public interface DerivationFunction {
    public int generateBytes(byte[] var1, int var2, int var3) throws DataLengthException, IllegalArgumentException;

    public void init(DerivationParameters var1);
}

