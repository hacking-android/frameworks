/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.StreamBlockCipher;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;

public class OFBBlockCipher
extends StreamBlockCipher {
    private byte[] IV;
    private final int blockSize;
    private int byteCount;
    private final BlockCipher cipher;
    private byte[] ofbOutV;
    private byte[] ofbV;

    public OFBBlockCipher(BlockCipher blockCipher, int n) {
        super(blockCipher);
        this.cipher = blockCipher;
        this.blockSize = n / 8;
        this.IV = new byte[blockCipher.getBlockSize()];
        this.ofbV = new byte[blockCipher.getBlockSize()];
        this.ofbOutV = new byte[blockCipher.getBlockSize()];
    }

    @Override
    protected byte calculateByte(byte by) throws DataLengthException, IllegalStateException {
        if (this.byteCount == 0) {
            this.cipher.processBlock(this.ofbV, 0, this.ofbOutV, 0);
        }
        byte[] arrby = this.ofbOutV;
        int n = this.byteCount;
        this.byteCount = n + 1;
        byte by2 = (byte)(arrby[n] ^ by);
        n = this.byteCount;
        by = (byte)this.blockSize;
        if (n == by) {
            this.byteCount = 0;
            arrby = this.ofbV;
            System.arraycopy((byte[])arrby, (int)by, (byte[])arrby, (int)0, (int)(arrby.length - by));
            arrby = this.ofbOutV;
            byte[] arrby2 = this.ofbV;
            n = arrby2.length;
            by = (byte)this.blockSize;
            System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)(n - by), (int)by);
        }
        return by2;
    }

    @Override
    public String getAlgorithmName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.cipher.getAlgorithmName());
        stringBuilder.append("/OFB");
        stringBuilder.append(this.blockSize * 8);
        return stringBuilder.toString();
    }

    @Override
    public int getBlockSize() {
        return this.blockSize;
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
        if (cipherParameters instanceof ParametersWithIV) {
            byte[] arrby;
            byte[] arrby2 = ((ParametersWithIV)(cipherParameters = (ParametersWithIV)cipherParameters)).getIV();
            int n = arrby2.length;
            if (n < (arrby = this.IV).length) {
                System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)(arrby.length - arrby2.length), (int)arrby2.length);
                for (n = 0; n < (arrby = this.IV).length - arrby2.length; ++n) {
                    arrby[n] = (byte)(false ? 1 : 0);
                }
            } else {
                System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)0, (int)arrby.length);
            }
            this.reset();
            if (((ParametersWithIV)cipherParameters).getParameters() != null) {
                this.cipher.init(true, ((ParametersWithIV)cipherParameters).getParameters());
            }
        } else {
            this.reset();
            if (cipherParameters != null) {
                this.cipher.init(true, cipherParameters);
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
        System.arraycopy((byte[])arrby, (int)0, (byte[])this.ofbV, (int)0, (int)arrby.length);
        this.byteCount = 0;
        this.cipher.reset();
    }
}

