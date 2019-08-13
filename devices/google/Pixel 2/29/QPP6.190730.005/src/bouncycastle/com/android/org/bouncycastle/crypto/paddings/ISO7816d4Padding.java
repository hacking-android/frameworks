/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.paddings;

import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.paddings.BlockCipherPadding;
import java.security.SecureRandom;

public class ISO7816d4Padding
implements BlockCipherPadding {
    @Override
    public int addPadding(byte[] arrby, int n) {
        int n2 = arrby.length;
        arrby[n] = (byte)-128;
        for (int i = n + 1; i < arrby.length; ++i) {
            arrby[i] = (byte)(false ? 1 : 0);
        }
        return n2 - n;
    }

    @Override
    public String getPaddingName() {
        return "ISO7816-4";
    }

    @Override
    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
    }

    @Override
    public int padCount(byte[] arrby) throws InvalidCipherTextException {
        int n;
        for (n = arrby.length - 1; n > 0 && arrby[n] == 0; --n) {
        }
        if (arrby[n] == -128) {
            return arrby.length - n;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}

