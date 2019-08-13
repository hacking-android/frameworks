/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.engines;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.StreamCipher;
import com.android.org.bouncycastle.crypto.params.KeyParameter;

public class RC4Engine
implements StreamCipher {
    private static final int STATE_LENGTH = 256;
    private byte[] engineState = null;
    private byte[] workingKey = null;
    private int x = 0;
    private int y = 0;

    private void setKey(byte[] arrby) {
        int n;
        this.workingKey = arrby;
        this.x = 0;
        this.y = 0;
        if (this.engineState == null) {
            this.engineState = new byte[256];
        }
        for (n = 0; n < 256; ++n) {
            this.engineState[n] = (byte)n;
        }
        int n2 = 0;
        int n3 = 0;
        for (n = 0; n < 256; ++n) {
            byte by = arrby[n2];
            byte[] arrby2 = this.engineState;
            n3 = (by & 255) + arrby2[n] + n3 & 255;
            by = arrby2[n];
            arrby2[n] = arrby2[n3];
            arrby2[n3] = by;
            n2 = (n2 + 1) % arrby.length;
        }
    }

    @Override
    public String getAlgorithmName() {
        return "RC4";
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.workingKey = ((KeyParameter)cipherParameters).getKey();
            this.setKey(this.workingKey);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid parameter passed to RC4 init - ");
        stringBuilder.append(cipherParameters.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int processBytes(byte[] arrby, int n, int n2, byte[] arrby2, int n3) {
        if (n + n2 <= arrby.length) {
            if (n3 + n2 <= arrby2.length) {
                for (int i = 0; i < n2; ++i) {
                    this.x = this.x + 1 & 255;
                    byte[] arrby3 = this.engineState;
                    int n4 = this.x;
                    this.y = arrby3[n4] + this.y & 255;
                    byte by = arrby3[n4];
                    int n5 = this.y;
                    arrby3[n4] = arrby3[n5];
                    arrby3[n5] = by;
                    by = arrby[i + n];
                    arrby2[i + n3] = (byte)(arrby3[arrby3[n4] + arrby3[n5] & 255] ^ by);
                }
                return n2;
            }
            throw new OutputLengthException("output buffer too short");
        }
        throw new DataLengthException("input buffer too short");
    }

    @Override
    public void reset() {
        this.setKey(this.workingKey);
    }

    @Override
    public byte returnByte(byte by) {
        this.x = this.x + 1 & 255;
        byte[] arrby = this.engineState;
        int n = this.x;
        this.y = arrby[n] + this.y & 255;
        byte by2 = arrby[n];
        int n2 = this.y;
        arrby[n] = arrby[n2];
        arrby[n2] = by2;
        return (byte)(arrby[arrby[n] + arrby[n2] & 255] ^ by);
    }
}

