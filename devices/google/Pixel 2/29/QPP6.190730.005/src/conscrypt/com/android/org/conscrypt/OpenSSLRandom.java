/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import java.io.Serializable;
import java.security.SecureRandomSpi;

public final class OpenSSLRandom
extends SecureRandomSpi
implements Serializable {
    private static final long serialVersionUID = 8506210602917522861L;

    @Override
    protected byte[] engineGenerateSeed(int n) {
        byte[] arrby = new byte[n];
        NativeCrypto.RAND_bytes(arrby);
        return arrby;
    }

    @Override
    protected void engineNextBytes(byte[] arrby) {
        NativeCrypto.RAND_bytes(arrby);
    }

    @Override
    protected void engineSetSeed(byte[] arrby) {
        if (arrby != null) {
            return;
        }
        throw new NullPointerException("seed == null");
    }
}

