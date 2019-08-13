/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.util;

import java.io.IOException;
import java.math.BigInteger;

public interface DSAEncoder {
    public BigInteger[] decode(byte[] var1) throws IOException;

    public byte[] encode(BigInteger var1, BigInteger var2) throws IOException;
}

