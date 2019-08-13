/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.BufferedBlockCipher;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.StreamBlockCipher;
import com.android.org.bouncycastle.crypto.modes.CBCBlockCipher;

public class CTSBlockCipher
extends BufferedBlockCipher {
    private int blockSize;

    public CTSBlockCipher(BlockCipher blockCipher) {
        if (!(blockCipher instanceof StreamBlockCipher)) {
            this.cipher = blockCipher;
            this.blockSize = blockCipher.getBlockSize();
            this.buf = new byte[this.blockSize * 2];
            this.bufOff = 0;
            return;
        }
        throw new IllegalArgumentException("CTSBlockCipher can only accept ECB, or CBC ciphers");
    }

    @Override
    public int doFinal(byte[] arrby, int n) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
        block14 : {
            block18 : {
                block17 : {
                    byte[] arrby2;
                    int n2;
                    int n3;
                    block15 : {
                        block16 : {
                            if (this.bufOff + n > arrby.length) break block14;
                            n3 = this.cipher.getBlockSize();
                            n2 = this.bufOff - n3;
                            arrby2 = new byte[n3];
                            if (!this.forEncryption) break block15;
                            if (this.bufOff < n3) break block16;
                            this.cipher.processBlock(this.buf, 0, arrby2, 0);
                            if (this.bufOff > n3) {
                                int n4;
                                for (n4 = this.bufOff; n4 != this.buf.length; ++n4) {
                                    this.buf[n4] = arrby2[n4 - n3];
                                }
                                for (n4 = n3; n4 != this.bufOff; ++n4) {
                                    byte[] arrby3 = this.buf;
                                    arrby3[n4] = (byte)(arrby3[n4] ^ arrby2[n4 - n3]);
                                }
                                if (this.cipher instanceof CBCBlockCipher) {
                                    ((CBCBlockCipher)this.cipher).getUnderlyingCipher().processBlock(this.buf, n3, arrby, n);
                                } else {
                                    this.cipher.processBlock(this.buf, n3, arrby, n);
                                }
                                System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)(n + n3), (int)n2);
                            } else {
                                System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)n, (int)n3);
                            }
                            break block17;
                        }
                        throw new DataLengthException("need at least one block of input for CTS");
                    }
                    if (this.bufOff < n3) break block18;
                    byte[] arrby4 = new byte[n3];
                    if (this.bufOff > n3) {
                        if (this.cipher instanceof CBCBlockCipher) {
                            ((CBCBlockCipher)this.cipher).getUnderlyingCipher().processBlock(this.buf, 0, arrby2, 0);
                        } else {
                            this.cipher.processBlock(this.buf, 0, arrby2, 0);
                        }
                        for (int i = n3; i != this.bufOff; ++i) {
                            arrby4[i - n3] = (byte)(arrby2[i - n3] ^ this.buf[i]);
                        }
                        System.arraycopy((byte[])this.buf, (int)n3, (byte[])arrby2, (int)0, (int)n2);
                        this.cipher.processBlock(arrby2, 0, arrby, n);
                        System.arraycopy((byte[])arrby4, (int)0, (byte[])arrby, (int)(n + n3), (int)n2);
                    } else {
                        this.cipher.processBlock(this.buf, 0, arrby2, 0);
                        System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)n, (int)n3);
                    }
                }
                n = this.bufOff;
                this.reset();
                return n;
            }
            throw new DataLengthException("need at least one block of input for CTS");
        }
        throw new OutputLengthException("output buffer to small in doFinal");
    }

    @Override
    public int getOutputSize(int n) {
        return this.bufOff + n;
    }

    @Override
    public int getUpdateOutputSize(int n) {
        int n2 = this.bufOff + n;
        n = n2 % this.buf.length;
        if (n == 0) {
            return n2 - this.buf.length;
        }
        return n2 - n;
    }

    @Override
    public int processByte(byte by, byte[] arrby, int n) throws DataLengthException, IllegalStateException {
        int n2 = 0;
        if (this.bufOff == this.buf.length) {
            n2 = this.cipher.processBlock(this.buf, 0, arrby, n);
            System.arraycopy((byte[])this.buf, (int)this.blockSize, (byte[])this.buf, (int)0, (int)this.blockSize);
            this.bufOff = this.blockSize;
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
                System.arraycopy((byte[])this.buf, (int)n4, (byte[])this.buf, (int)0, (int)n4);
                this.bufOff = n4;
                n6 = n + n7;
                n = n2 -= n7;
                n2 = n6;
                do {
                    n6 = n5;
                    n8 = n2;
                    n9 = n;
                    if (n <= n4) break;
                    System.arraycopy((byte[])arrby, (int)n2, (byte[])this.buf, (int)this.bufOff, (int)n4);
                    n5 += this.cipher.processBlock(this.buf, 0, arrby2, n3 + n5);
                    System.arraycopy((byte[])this.buf, (int)n4, (byte[])this.buf, (int)0, (int)n4);
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

