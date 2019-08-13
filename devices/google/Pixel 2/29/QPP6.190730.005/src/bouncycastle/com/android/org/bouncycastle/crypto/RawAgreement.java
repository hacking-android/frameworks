/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.CipherParameters;

public interface RawAgreement {
    public void calculateAgreement(CipherParameters var1, byte[] var2, int var3);

    public int getAgreementSize();

    public void init(CipherParameters var1);
}

