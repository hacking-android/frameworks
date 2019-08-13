/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.paddings;

import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.paddings.BlockCipherPadding;
import java.security.SecureRandom;

public class PKCS7Padding
implements BlockCipherPadding {
    @Override
    public int addPadding(byte[] arrby, int n) {
        byte by = (byte)(arrby.length - n);
        while (n < arrby.length) {
            arrby[n] = by;
            ++n;
        }
        return by;
    }

    @Override
    public String getPaddingName() {
        return "PKCS7";
    }

    @Override
    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
    }

    @Override
    public int padCount(byte[] arrby) throws InvalidCipherTextException {
        int n = arrby[arrby.length - 1] & 255;
        byte by = (byte)n;
        int n2 = n > arrby.length ? 1 : 0;
        boolean bl = n == 0;
        bl = n2 | bl;
        for (n2 = 0; n2 < arrby.length; ++n2) {
            boolean bl2 = arrby.length - n2 <= n;
            boolean bl3 = arrby[n2] != by;
            bl |= bl2 & bl3;
        }
        if (!bl) {
            return n;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}

