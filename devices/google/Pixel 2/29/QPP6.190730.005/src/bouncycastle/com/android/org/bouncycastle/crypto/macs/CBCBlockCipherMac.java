/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.macs;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.Mac;
import com.android.org.bouncycastle.crypto.modes.CBCBlockCipher;
import com.android.org.bouncycastle.crypto.paddings.BlockCipherPadding;

public class CBCBlockCipherMac
implements Mac {
    private byte[] buf;
    private int bufOff;
    private BlockCipher cipher;
    private byte[] mac;
    private int macSize;
    private BlockCipherPadding padding;

    public CBCBlockCipherMac(BlockCipher blockCipher) {
        this(blockCipher, blockCipher.getBlockSize() * 8 / 2, null);
    }

    public CBCBlockCipherMac(BlockCipher blockCipher, int n) {
        this(blockCipher, n, null);
    }

    public CBCBlockCipherMac(BlockCipher blockCipher, int n, BlockCipherPadding blockCipherPadding) {
        if (n % 8 == 0) {
            this.cipher = new CBCBlockCipher(blockCipher);
            this.padding = blockCipherPadding;
            this.macSize = n / 8;
            this.mac = new byte[blockCipher.getBlockSize()];
            this.buf = new byte[blockCipher.getBlockSize()];
            this.bufOff = 0;
            return;
        }
        throw new IllegalArgumentException("MAC size must be multiple of 8");
    }

    public CBCBlockCipherMac(BlockCipher blockCipher, BlockCipherPadding blockCipherPadding) {
        this(blockCipher, blockCipher.getBlockSize() * 8 / 2, blockCipherPadding);
    }

    @Override
    public int doFinal(byte[] arrby, int n) {
        int n2 = this.cipher.getBlockSize();
        if (this.padding == null) {
            int n3;
            while ((n3 = this.bufOff) < n2) {
                this.buf[n3] = (byte)(false ? 1 : 0);
                this.bufOff = n3 + 1;
            }
        } else {
            if (this.bufOff == n2) {
                this.cipher.processBlock(this.buf, 0, this.mac, 0);
                this.bufOff = 0;
            }
            this.padding.addPadding(this.buf, this.bufOff);
        }
        this.cipher.processBlock(this.buf, 0, this.mac, 0);
        System.arraycopy((byte[])this.mac, (int)0, (byte[])arrby, (int)n, (int)this.macSize);
        this.reset();
        return this.macSize;
    }

    @Override
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName();
    }

    @Override
    public int getMacSize() {
        return this.macSize;
    }

    @Override
    public void init(CipherParameters cipherParameters) {
        this.reset();
        this.cipher.init(true, cipherParameters);
    }

    @Override
    public void reset() {
        byte[] arrby;
        for (int i = 0; i < (arrby = this.buf).length; ++i) {
            arrby[i] = (byte)(false ? 1 : 0);
        }
        this.bufOff = 0;
        this.cipher.reset();
    }

    @Override
    public void update(byte by) {
        int n = this.bufOff;
        byte[] arrby = this.buf;
        if (n == arrby.length) {
            this.cipher.processBlock(arrby, 0, this.mac, 0);
            this.bufOff = 0;
        }
        arrby = this.buf;
        n = this.bufOff;
        this.bufOff = n + 1;
        arrby[n] = by;
    }

    @Override
    public void update(byte[] arrby, int n, int n2) {
        if (n2 >= 0) {
            int n3 = this.cipher.getBlockSize();
            int n4 = this.bufOff;
            int n5 = n3 - n4;
            int n6 = n;
            int n7 = n2;
            if (n2 > n5) {
                System.arraycopy((byte[])arrby, (int)n, (byte[])this.buf, (int)n4, (int)n5);
                this.cipher.processBlock(this.buf, 0, this.mac, 0);
                this.bufOff = 0;
                n2 -= n5;
                n += n5;
                do {
                    n6 = n;
                    n7 = n2;
                    if (n2 <= n3) break;
                    this.cipher.processBlock(arrby, n, this.mac, 0);
                    n2 -= n3;
                    n += n3;
                } while (true);
            }
            System.arraycopy((byte[])arrby, (int)n6, (byte[])this.buf, (int)this.bufOff, (int)n7);
            this.bufOff += n7;
            return;
        }
        throw new IllegalArgumentException("Can't have a negative input length!");
    }
}

