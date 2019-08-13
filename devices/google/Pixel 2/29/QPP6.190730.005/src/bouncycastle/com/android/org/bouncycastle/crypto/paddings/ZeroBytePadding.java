/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.paddings;

import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.paddings.BlockCipherPadding;
import java.security.SecureRandom;

public class ZeroBytePadding
implements BlockCipherPadding {
    @Override
    public int addPadding(byte[] arrby, int n) {
        int n2 = arrby.length;
        for (int i = n; i < arrby.length; ++i) {
            arrby[i] = (byte)(false ? 1 : 0);
        }
        return n2 - n;
    }

    @Override
    public String getPaddingName() {
        return "ZeroByte";
    }

    @Override
    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
    }

    @Override
    public int padCount(byte[] arrby) throws InvalidCipherTextException {
        int n;
        for (n = arrby.length; n > 0 && arrby[n - 1] == 0; --n) {
        }
        return arrby.length - n;
    }
}

