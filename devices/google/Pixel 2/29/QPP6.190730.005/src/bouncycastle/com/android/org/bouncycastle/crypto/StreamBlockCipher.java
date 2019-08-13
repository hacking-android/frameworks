/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.StreamCipher;

public abstract class StreamBlockCipher
implements BlockCipher,
StreamCipher {
    private final BlockCipher cipher;

    protected StreamBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
    }

    protected abstract byte calculateByte(byte var1);

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override
    public int processBytes(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws DataLengthException {
        if (n + n2 <= arrby.length) {
            if (n3 + n2 <= arrby2.length) {
                for (int i = n; i < n + n2; ++i) {
                    arrby2[n3] = this.calculateByte(arrby[i]);
                    ++n3;
                }
                return n2;
            }
            throw new OutputLengthException("output buffer too short");
        }
        throw new DataLengthException("input buffer too small");
    }

    @Override
    public final byte returnByte(byte by) {
        return this.calculateByte(by);
    }
}

