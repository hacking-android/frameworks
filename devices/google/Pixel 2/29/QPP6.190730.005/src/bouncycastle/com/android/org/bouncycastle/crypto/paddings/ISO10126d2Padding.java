/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.paddings;

import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.paddings.BlockCipherPadding;
import java.security.SecureRandom;

public class ISO10126d2Padding
implements BlockCipherPadding {
    SecureRandom random;

    @Override
    public int addPadding(byte[] arrby, int n) {
        byte by = (byte)(arrby.length - n);
        while (n < arrby.length - 1) {
            arrby[n] = (byte)this.random.nextInt();
            ++n;
        }
        arrby[n] = by;
        return by;
    }

    @Override
    public String getPaddingName() {
        return "ISO10126-2";
    }

    @Override
    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
        this.random = secureRandom != null ? secureRandom : CryptoServicesRegistrar.getSecureRandom();
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

