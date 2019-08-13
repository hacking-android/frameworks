/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.KeyStoreException;

interface KeyStoreCryptoOperationStreamer {
    public byte[] doFinal(byte[] var1, int var2, int var3, byte[] var4, byte[] var5) throws KeyStoreException;

    public long getConsumedInputSizeBytes();

    public long getProducedOutputSizeBytes();

    public byte[] update(byte[] var1, int var2, int var3) throws KeyStoreException;
}

