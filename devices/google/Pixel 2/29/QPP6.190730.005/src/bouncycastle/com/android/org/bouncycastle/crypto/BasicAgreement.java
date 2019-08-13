/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.CipherParameters;
import java.math.BigInteger;

public interface BasicAgreement {
    public BigInteger calculateAgreement(CipherParameters var1);

    public int getFieldSize();

    public void init(CipherParameters var1);
}

