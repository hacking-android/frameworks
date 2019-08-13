/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.StreamCipher;

public class BufferedBlockCipher {
    protected byte[] buf;
    protected int bufOff;
    protected BlockCipher cipher;
    protected boolean forEncryption;
    protected boolean partialBlockOkay;
    protected boolean pgpCFB;

    protected BufferedBlockCipher() {
    }

    public BufferedBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
        this.buf = new byte[blockCipher.getBlockSize()];
        boolean bl = false;
        this.bufOff = 0;
        String string = blockCipher.getAlgorithmName();
        int n = string.indexOf(47) + 1;
        boolean bl2 = n > 0 && string.startsWith("PGP", n);
        this.pgpCFB = bl2;
        if (!this.pgpCFB && !(blockCipher instanceof StreamCipher)) {
            bl2 = bl;
            if (n > 0) {
                bl2 = bl;
                if (string.startsWith("OpenPGP", n)) {
                    bl2 = true;
                }
            }
            this.partialBlockOkay = bl2;
        } else {
            this.partialBlockOkay = true;
        }
    }

    public int doFinal(byte[] object, int n) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
        int n2 = 0;
        try {
            if (this.bufOff + n <= ((byte[])object).length) {
                if (this.bufOff != 0) {
                    if (this.partialBlockOkay) {
                        this.cipher.processBlock(this.buf, 0, this.buf, 0);
                        n2 = this.bufOff;
                        this.bufOff = 0;
                        System.arraycopy((byte[])this.buf, (int)0, (byte[])object, (int)n, (int)n2);
                    } else {
                        object = new DataLengthException("data not block size aligned");
                        throw object;
                    }
                }
                return n2;
            }
            object = new OutputLengthException("output buffer too short for doFinal()");
            throw object;
        }
        finally {
            this.reset();
        }
    }

    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    public int getOutputSize(int n) {
        return this.bufOff + n;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    public int getUpdateOutputSize(int n) {
        int n2 = this.bufOff + n;
        n = this.pgpCFB ? (this.forEncryption ? n2 % this.buf.length - (this.cipher.getBlockSize() + 2) : n2 % this.buf.length) : n2 % this.buf.length;
        return n2 - n;
    }

    public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
        this.forEncryption = bl;
        this.reset();
        this.cipher.init(bl, cipherParameters);
    }

    public int processByte(byte by, byte[] arrby, int n) throws DataLengthException, IllegalStateException {
        byte by2 = 0;
        byte[] arrby2 = this.buf;
        int n2 = this.bufOff;
        this.bufOff = n2 + 1;
        arrby2[n2] = by;
        by = by2;
        if (this.bufOff == arrby2.length) {
            by = (byte)this.cipher.processBlock(arrby2, 0, arrby, n);
            this.bufOff = 0;
        }
        return by;
    }

    public int processBytes(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws DataLengthException, IllegalStateException {
        if (n2 >= 0) {
            int n4 = this.getBlockSize();
            int n5 = this.getUpdateOutputSize(n2);
            if (n5 > 0 && n3 + n5 > arrby2.length) {
                throw new OutputLengthException("output buffer too short");
            }
            n5 = 0;
            byte[] arrby3 = this.buf;
            int n6 = arrby3.length;
            int n7 = this.bufOff;
            int n8 = n6 - n7;
            n6 = n;
            int n9 = n2;
            if (n2 > n8) {
                System.arraycopy((byte[])arrby, (int)n, (byte[])arrby3, (int)n7, (int)n8);
                n7 = 0 + this.cipher.processBlock(this.buf, 0, arrby2, n3);
                this.bufOff = 0;
                n2 -= n8;
                n += n8;
                do {
                    n5 = n7;
                    n6 = n;
                    n9 = n2;
                    if (n2 <= this.buf.length) break;
                    n7 += this.cipher.processBlock(arrby, n, arrby2, n3 + n7);
                    n2 -= n4;
                    n += n4;
                } while (true);
            }
            System.arraycopy((byte[])arrby, (int)n6, (byte[])this.buf, (int)this.bufOff, (int)n9);
            this.bufOff += n9;
            n2 = this.bufOff;
            arrby = this.buf;
            n = n5;
            if (n2 == arrby.length) {
                n = n5 + this.cipher.processBlock(arrby, 0, arrby2, n3 + n5);
                this.bufOff = 0;
            }
            return n;
        }
        throw new IllegalArgumentException("Can't have a negative input length!");
    }

    public void reset() {
        byte[] arrby;
        for (int i = 0; i < (arrby = this.buf).length; ++i) {
            arrby[i] = (byte)(false ? 1 : 0);
        }
        this.bufOff = 0;
        this.cipher.reset();
    }
}

