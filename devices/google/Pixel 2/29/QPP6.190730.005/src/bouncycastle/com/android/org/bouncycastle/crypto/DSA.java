/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.CipherParameters;
import java.math.BigInteger;

public interface DSA {
    public BigInteger[] generateSignature(byte[] var1);

    public void init(boolean var1, CipherParameters var2);

    public boolean verifySignature(byte[] var1, BigInteger var2, BigInteger var3);
}

