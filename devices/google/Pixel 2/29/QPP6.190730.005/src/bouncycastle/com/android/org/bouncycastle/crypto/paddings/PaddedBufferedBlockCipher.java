/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.paddings;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.BufferedBlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.paddings.BlockCipherPadding;
import com.android.org.bouncycastle.crypto.paddings.PKCS7Padding;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import java.security.SecureRandom;

public class PaddedBufferedBlockCipher
extends BufferedBlockCipher {
    BlockCipherPadding padding;

    public PaddedBufferedBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, new PKCS7Padding());
    }

    public PaddedBufferedBlockCipher(BlockCipher blockCipher, BlockCipherPadding blockCipherPadding) {
        this.cipher = blockCipher;
        this.padding = blockCipherPadding;
        this.buf = new byte[blockCipher.getBlockSize()];
        this.bufOff = 0;
    }

    @Override
    public int doFinal(byte[] arrby, int n) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
        block11 : {
            block10 : {
                int n2;
                int n3;
                block9 : {
                    n3 = this.cipher.getBlockSize();
                    n2 = 0;
                    if (!this.forEncryption) break block9;
                    if (this.bufOff == n3) {
                        if (n3 * 2 + n <= arrby.length) {
                            n2 = this.cipher.processBlock(this.buf, 0, arrby, n);
                            this.bufOff = 0;
                        } else {
                            this.reset();
                            throw new OutputLengthException("output buffer too short");
                        }
                    }
                    this.padding.addPadding(this.buf, this.bufOff);
                    n = n2 + this.cipher.processBlock(this.buf, 0, arrby, n + n2);
                    this.reset();
                    break block10;
                }
                if (this.bufOff != n3) break block11;
                n2 = this.cipher.processBlock(this.buf, 0, this.buf, 0);
                this.bufOff = 0;
                System.arraycopy((byte[])this.buf, (int)0, (byte[])arrby, (int)n, (int)(n2 -= this.padding.padCount(this.buf)));
                n = n2;
            }
            return n;
            finally {
                this.reset();
            }
        }
        this.reset();
        throw new DataLengthException("last block incomplete in decryption");
    }

    @Override
    public int getOutputSize(int n) {
        int n2 = (n = this.bufOff + n) % this.buf.length;
        if (n2 == 0) {
            if (this.forEncryption) {
                return this.buf.length + n;
            }
            return n;
        }
        return n - n2 + this.buf.length;
    }

    @Override
    public int getUpdateOutputSize(int n) {
        int n2 = this.bufOff + n;
        n = n2 % this.buf.length;
        if (n == 0) {
            return Math.max(0, n2 - this.buf.length);
        }
        return n2 - n;
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
        this.forEncryption = bl;
        this.reset();
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters = (ParametersWithRandom)cipherParameters;
            this.padding.init(((ParametersWithRandom)cipherParameters).getRandom());
            this.cipher.init(bl, ((ParametersWithRandom)cipherParameters).getParameters());
        } else {
            this.padding.init(null);
            this.cipher.init(bl, cipherParameters);
        }
    }

    @Override
    public int processByte(byte by, byte[] arrby, int n) throws DataLengthException, IllegalStateException {
        int n2 = 0;
        if (this.bufOff == this.buf.length) {
            n2 = this.cipher.processBlock(this.buf, 0, arrby, n);
            this.bufOff = 0;
        }
        arrby = this.buf;
        n = this.bufOff;
        this.bufOff = n + 1;
        arrby[n] = by;
        return n2;
    }

    @Override
    public int processBytes(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws DataLengthException, IllegalStateException {
        if (n2 >= 0) {
            int n4 = this.getBlockSize();
            int n5 = this.getUpdateOutputSize(n2);
            if (n5 > 0 && n3 + n5 > arrby2.length) {
                throw new OutputLengthException("output buffer too short");
            }
            int n6 = 0;
            int n7 = this.buf.length - this.bufOff;
            int n8 = n;
            int n9 = n2;
            if (n2 > n7) {
                System.arraycopy((byte[])arrby, (int)n, (byte[])this.buf, (int)this.bufOff, (int)n7);
                n5 = 0 + this.cipher.processBlock(this.buf, 0, arrby2, n3);
                this.bufOff = 0;
                n8 = n + n7;
                n = n2 -= n7;
                n2 = n8;
                do {
                    n6 = n5;
                    n8 = n2;
                    n9 = n;
                    if (n <= this.buf.length) break;
                    n5 += this.cipher.processBlock(arrby, n2, arrby2, n3 + n5);
                    n -= n4;
                    n2 += n4;
                } while (true);
            }
            System.arraycopy((byte[])arrby, (int)n8, (byte[])this.buf, (int)this.bufOff, (int)n9);
            this.bufOff += n9;
            return n6;
        }
        throw new IllegalArgumentException("Can't have a negative input length!");
    }
}

