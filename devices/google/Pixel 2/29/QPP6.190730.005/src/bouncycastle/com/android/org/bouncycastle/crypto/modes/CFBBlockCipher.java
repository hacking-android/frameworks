/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.StreamBlockCipher;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.util.Arrays;

public class CFBBlockCipher
extends StreamBlockCipher {
    private byte[] IV;
    private int blockSize;
    private int byteCount;
    private byte[] cfbOutV;
    private byte[] cfbV;
    private BlockCipher cipher = null;
    private boolean encrypting;
    private byte[] inBuf;

    public CFBBlockCipher(BlockCipher blockCipher, int n) {
        super(blockCipher);
        this.cipher = blockCipher;
        this.blockSize = n / 8;
        this.IV = new byte[blockCipher.getBlockSize()];
        this.cfbV = new byte[blockCipher.getBlockSize()];
        this.cfbOutV = new byte[blockCipher.getBlockSize()];
        this.inBuf = new byte[this.blockSize];
    }

    private byte decryptByte(byte by) {
        if (this.byteCount == 0) {
            this.cipher.processBlock(this.cfbV, 0, this.cfbOutV, 0);
        }
        byte[] arrby = this.inBuf;
        int n = this.byteCount;
        arrby[n] = by;
        arrby = this.cfbOutV;
        this.byteCount = n + 1;
        byte by2 = (byte)(arrby[n] ^ by);
        n = this.byteCount;
        by = (byte)this.blockSize;
        if (n == by) {
            this.byteCount = 0;
            arrby = this.cfbV;
            System.arraycopy((byte[])arrby, (int)by, (byte[])arrby, (int)0, (int)(arrby.length - by));
            byte[] arrby2 = this.inBuf;
            arrby = this.cfbV;
            n = arrby.length;
            by = (byte)this.blockSize;
            System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)(n - by), (int)by);
        }
        return by2;
    }

    private byte encryptByte(byte by) {
        if (this.byteCount == 0) {
            this.cipher.processBlock(this.cfbV, 0, this.cfbOutV, 0);
        }
        byte[] arrby = this.cfbOutV;
        int n = this.byteCount;
        byte by2 = (byte)(arrby[n] ^ by);
        arrby = this.inBuf;
        this.byteCount = n + 1;
        arrby[n] = by2;
        by = (byte)this.byteCount;
        n = this.blockSize;
        if (by == n) {
            this.byteCount = 0;
            arrby = this.cfbV;
            System.arraycopy((byte[])arrby, (int)n, (byte[])arrby, (int)0, (int)(arrby.length - n));
            arrby = this.inBuf;
            byte[] arrby2 = this.cfbV;
            by = (byte)arrby2.length;
            n = this.blockSize;
            System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)(by - n), (int)n);
        }
        return by2;
    }

    @Override
    protected byte calculateByte(byte by) throws DataLengthException, IllegalStateException {
        byte by2;
        byte by3;
        by = this.encrypting ? (by2 = this.encryptByte(by)) : (by3 = this.decryptByte(by));
        return by;
    }

    public int decryptBlock(byte[] arrby, int n, byte[] arrby2, int n2) throws DataLengthException, IllegalStateException {
        this.processBytes(arrby, n, this.blockSize, arrby2, n2);
        return this.blockSize;
    }

    public int encryptBlock(byte[] arrby, int n, byte[] arrby2, int n2) throws DataLengthException, IllegalStateException {
        this.processBytes(arrby, n, this.blockSize, arrby2, n2);
        return this.blockSize;
    }

    @Override
    public String getAlgorithmName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.cipher.getAlgorithmName());
        stringBuilder.append("/CFB");
        stringBuilder.append(this.blockSize * 8);
        return stringBuilder.toString();
    }

    @Override
    public int getBlockSize() {
        return this.blockSize;
    }

    public byte[] getCurrentIV() {
        return Arrays.clone(this.cfbV);
    }

    @Override
    public void init(boolean bl, CipherParameters arrby) throws IllegalArgumentException {
        this.encrypting = bl;
        if (arrby instanceof ParametersWithIV) {
            byte[] arrby2;
            ParametersWithIV parametersWithIV = (ParametersWithIV)arrby;
            arrby = parametersWithIV.getIV();
            int n = arrby.length;
            if (n < (arrby2 = this.IV).length) {
                System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)(arrby2.length - arrby.length), (int)arrby.length);
                for (n = 0; n < (arrby2 = this.IV).length - arrby.length; ++n) {
                    arrby2[n] = (byte)(false ? 1 : 0);
                }
            } else {
                System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)0, (int)arrby2.length);
            }
            this.reset();
            if (parametersWithIV.getParameters() != null) {
                this.cipher.init(true, parametersWithIV.getParameters());
            }
        } else {
            this.reset();
            if (arrby != null) {
                this.cipher.init(true, (CipherParameters)arrby);
            }
        }
    }

    @Override
    public int processBlock(byte[] arrby, int n, byte[] arrby2, int n2) throws DataLengthException, IllegalStateException {
        this.processBytes(arrby, n, this.blockSize, arrby2, n2);
        return this.blockSize;
    }

    @Override
    public void reset() {
        byte[] arrby = this.IV;
        System.arraycopy((byte[])arrby, (int)0, (byte[])this.cfbV, (int)0, (int)arrby.length);
        Arrays.fill(this.inBuf, (byte)0);
        this.byteCount = 0;
        this.cipher.reset();
    }
}

