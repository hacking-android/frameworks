/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.engines;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.engines.DESEngine;
import com.android.org.bouncycastle.crypto.params.KeyParameter;

public class DESedeEngine
extends DESEngine {
    protected static final int BLOCK_SIZE = 8;
    private boolean forEncryption;
    private int[] workingKey1 = null;
    private int[] workingKey2 = null;
    private int[] workingKey3 = null;

    @Override
    public String getAlgorithmName() {
        return "DESede";
    }

    @Override
    public int getBlockSize() {
        return 8;
    }

    @Override
    public void init(boolean bl, CipherParameters arrby) {
        if (arrby instanceof KeyParameter) {
            if ((arrby = ((KeyParameter)arrby).getKey()).length != 24 && arrby.length != 16) {
                throw new IllegalArgumentException("key size must be 16 or 24 bytes.");
            }
            this.forEncryption = bl;
            byte[] arrby2 = new byte[8];
            System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)0, (int)arrby2.length);
            this.workingKey1 = this.generateWorkingKey(bl, arrby2);
            arrby2 = new byte[8];
            System.arraycopy((byte[])arrby, (int)8, (byte[])arrby2, (int)0, (int)arrby2.length);
            this.workingKey2 = this.generateWorkingKey(bl ^ true, arrby2);
            if (arrby.length == 24) {
                arrby2 = new byte[8];
                System.arraycopy((byte[])arrby, (int)16, (byte[])arrby2, (int)0, (int)arrby2.length);
                this.workingKey3 = this.generateWorkingKey(bl, arrby2);
            } else {
                this.workingKey3 = this.workingKey1;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid parameter passed to DESede init - ");
        stringBuilder.append(arrby.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int processBlock(byte[] arrby, int n, byte[] arrby2, int n2) {
        int[] arrn = this.workingKey1;
        if (arrn != null) {
            if (n + 8 <= arrby.length) {
                if (n2 + 8 <= arrby2.length) {
                    byte[] arrby3 = new byte[8];
                    if (this.forEncryption) {
                        this.desFunc(arrn, arrby, n, arrby3, 0);
                        this.desFunc(this.workingKey2, arrby3, 0, arrby3, 0);
                        this.desFunc(this.workingKey3, arrby3, 0, arrby2, n2);
                    } else {
                        this.desFunc(this.workingKey3, arrby, n, arrby3, 0);
                        this.desFunc(this.workingKey2, arrby3, 0, arrby3, 0);
                        this.desFunc(this.workingKey1, arrby3, 0, arrby2, n2);
                    }
                    return 8;
                }
                throw new OutputLengthException("output buffer too short");
            }
            throw new DataLengthException("input buffer too short");
        }
        throw new IllegalStateException("DESede engine not initialised");
    }

    @Override
    public void reset() {
    }
}

