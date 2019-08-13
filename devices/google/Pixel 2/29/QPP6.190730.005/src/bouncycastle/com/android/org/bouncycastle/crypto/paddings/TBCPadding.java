/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.paddings;

import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.paddings.BlockCipherPadding;
import java.security.SecureRandom;

public class TBCPadding
implements BlockCipherPadding {
    @Override
    public int addPadding(byte[] arrby, int n) {
        byte by;
        int n2 = arrby.length;
        int n3 = 255;
        if (n > 0) {
            if ((arrby[n - 1] & 1) != 0) {
                n3 = 0;
            }
            by = (byte)n3;
            n3 = n;
        } else {
            if ((arrby[arrby.length - 1] & 1) != 0) {
                n3 = 0;
            }
            by = (byte)n3;
            n3 = n;
        }
        while (n3 < arrby.length) {
            arrby[n3] = by;
            ++n3;
        }
        return n2 - n;
    }

    @Override
    public String getPaddingName() {
        return "TBC";
    }

    @Override
    public void init(SecureRandom secureRandom) throws IllegalArgumentException {
    }

    @Override
    public int padCount(byte[] arrby) throws InvalidCipherTextException {
        int n;
        byte by = arrby[arrby.length - 1];
        for (n = arrby.length - 1; n > 0 && arrby[n - 1] == by; --n) {
        }
        return arrby.length - n;
    }
}

