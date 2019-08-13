/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

public interface Digest {
    public int doFinal(byte[] var1, int var2);

    public String getAlgorithmName();

    public int getDigestSize();

    public void reset();

    public void update(byte var1);

    public void update(byte[] var1, int var2, int var3);
}

