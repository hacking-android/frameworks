/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.paddings;

import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.paddings.BlockCipherPadding;
import java.security.SecureRandom;

public class X923Padding
implements BlockCipherPadding {
    SecureRandom random = null;

    @Override
    public int addPadding(byte[] arrby, int n) {
        byte by = (byte)(arrby.length - n);
        while (n < arrby.length - 1) {
            SecureRandom secureRandom = this.random;
            arrby[n] = secureRandom == null ? (byte)(false ? 1 : 0) : (byte)((byte)secureRandom.nextInt());
            ++n;
        }
        arrby[n] = by;
        return by;
    }

    @Override
    public String getPaddingName() {
        return "X9.23";
    }

    @Override
    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
        this.random = secureRandom;
    }

    @Override
    public int padCount(byte[] arrby) throws InvalidCipherTextException {
        int n = arrby[arrby.length - 1] & 255;
        if (n <= arrby.length) {
            return n;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}

