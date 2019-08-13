/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.Signer;

public interface SignerWithRecovery
extends Signer {
    public byte[] getRecoveredMessage();

    public boolean hasFullMessage();

    public void updateWithRecoveredMessage(byte[] var1) throws InvalidCipherTextException;
}

