/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.paddings;

import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import java.security.SecureRandom;

public interface BlockCipherPadding {
    public int addPadding(byte[] var1, int var2);

    public String getPaddingName();

    public void init(SecureRandom var1) throws IllegalArgumentException;

    public int padCount(byte[] var1) throws InvalidCipherTextException;
}

