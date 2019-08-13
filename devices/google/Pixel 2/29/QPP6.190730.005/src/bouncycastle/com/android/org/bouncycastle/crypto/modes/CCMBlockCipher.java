/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import com.android.org.bouncycastle.crypto.modes.AEADBlockCipher;
import com.android.org.bouncycastle.crypto.modes.SICBlockCipher;
import com.android.org.bouncycastle.crypto.params.AEADParameters;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.util.Arrays;
import java.io.ByteArrayOutputStream;

public class CCMBlockCipher
implements AEADBlockCipher {
    private ExposedByteArrayOutputStream associatedText = new ExposedByteArrayOutputStream();
    private int blockSize;
    private BlockCipher cipher;
    private ExposedByteArrayOutputStream data = new ExposedByteArrayOutputStream();
    private boolean forEncryption;
    private byte[] initialAssociatedText;
    private CipherParameters keyParam;
    private byte[] macBlock;
    private int macSize;
    private byte[] nonce;

    public CCMBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
        int n = this.blockSize = blockCipher.getBlockSize();
        this.macBlock = new byte[n];
        if (n == 16) {
            return;
        }
        throw new IllegalArgumentException("cipher required with a block size of 16.");
    }

    private int calculateMac(byte[] arrby, int n, int n2, byte[] arrby2) {
        CBCBlockCipherMac cBCBlockCipherMac = new CBCBlockCipherMac(this.cipher, this.macSize * 8);
        cBCBlockCipherMac.init(this.keyParam);
        byte[] arrby3 = new byte[16];
        if (this.hasAssociatedText()) {
            arrby3[0] = (byte)(arrby3[0] | 64);
        }
        arrby3[0] = (byte)(arrby3[0] | ((cBCBlockCipherMac.getMacSize() - 2) / 2 & 7) << 3);
        int n3 = arrby3[0];
        byte[] arrby4 = this.nonce;
        arrby3[0] = (byte)(n3 | 15 - arrby4.length - 1 & 7);
        System.arraycopy((byte[])arrby4, (int)0, (byte[])arrby3, (int)1, (int)arrby4.length);
        int n4 = n2;
        n3 = 1;
        while (n4 > 0) {
            arrby3[arrby3.length - n3] = (byte)(n4 & 255);
            n4 >>>= 8;
            ++n3;
        }
        cBCBlockCipherMac.update(arrby3, 0, arrby3.length);
        if (this.hasAssociatedText()) {
            n4 = this.getAssociatedTextLength();
            if (n4 < 65280) {
                cBCBlockCipherMac.update((byte)(n4 >> 8));
                cBCBlockCipherMac.update((byte)n4);
                n3 = 2;
            } else {
                cBCBlockCipherMac.update((byte)-1);
                cBCBlockCipherMac.update((byte)-2);
                cBCBlockCipherMac.update((byte)(n4 >> 24));
                cBCBlockCipherMac.update((byte)(n4 >> 16));
                cBCBlockCipherMac.update((byte)(n4 >> 8));
                cBCBlockCipherMac.update((byte)n4);
                n3 = 6;
            }
            arrby3 = this.initialAssociatedText;
            if (arrby3 != null) {
                cBCBlockCipherMac.update(arrby3, 0, arrby3.length);
            }
            if (this.associatedText.size() > 0) {
                cBCBlockCipherMac.update(this.associatedText.getBuffer(), 0, this.associatedText.size());
            }
            if ((n3 = (n3 + n4) % 16) != 0) {
                while (n3 != 16) {
                    cBCBlockCipherMac.update((byte)0);
                    ++n3;
                }
            }
        }
        cBCBlockCipherMac.update(arrby, n, n2);
        return cBCBlockCipherMac.doFinal(arrby2, 0);
    }

    private int getAssociatedTextLength() {
        int n = this.associatedText.size();
        byte[] arrby = this.initialAssociatedText;
        int n2 = arrby == null ? 0 : arrby.length;
        return n + n2;
    }

    private boolean hasAssociatedText() {
        boolean bl = this.getAssociatedTextLength() > 0;
        return bl;
    }

    @Override
    public int doFinal(byte[] arrby, int n) throws IllegalStateException, InvalidCipherTextException {
        n = this.processPacket(this.data.getBuffer(), 0, this.data.size(), arrby, n);
        this.reset();
        return n;
    }

    @Override
    public String getAlgorithmName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.cipher.getAlgorithmName());
        stringBuilder.append("/CCM");
        return stringBuilder.toString();
    }

    @Override
    public byte[] getMac() {
        byte[] arrby = new byte[this.macSize];
        System.arraycopy((byte[])this.macBlock, (int)0, (byte[])arrby, (int)0, (int)arrby.length);
        return arrby;
    }

    @Override
    public int getOutputSize(int n) {
        int n2 = this.data.size() + n;
        if (this.forEncryption) {
            return this.macSize + n2;
        }
        n = this.macSize;
        n = n2 < n ? 0 : n2 - n;
        return n;
    }

    @Override
    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override
    public int getUpdateOutputSize(int n) {
        return 0;
    }

    @Override
    public void init(boolean bl, CipherParameters arrby) throws IllegalArgumentException {
        block7 : {
            block6 : {
                block5 : {
                    this.forEncryption = bl;
                    if (!(arrby instanceof AEADParameters)) break block5;
                    arrby = (AEADParameters)arrby;
                    this.nonce = arrby.getNonce();
                    this.initialAssociatedText = arrby.getAssociatedText();
                    this.macSize = arrby.getMacSize() / 8;
                    arrby = arrby.getKey();
                    break block6;
                }
                if (!(arrby instanceof ParametersWithIV)) break block7;
                arrby = (ParametersWithIV)arrby;
                this.nonce = arrby.getIV();
                this.initialAssociatedText = null;
                this.macSize = this.macBlock.length / 2;
                arrby = arrby.getParameters();
            }
            if (arrby != null) {
                this.keyParam = arrby;
            }
            if ((arrby = this.nonce) != null && arrby.length >= 7 && arrby.length <= 13) {
                this.reset();
                return;
            }
            throw new IllegalArgumentException("nonce must have length from 7 to 13 octets");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid parameters passed to CCM: ");
        stringBuilder.append(arrby.getClass().getName());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void processAADByte(byte by) {
        this.associatedText.write(by);
    }

    @Override
    public void processAADBytes(byte[] arrby, int n, int n2) {
        this.associatedText.write(arrby, n, n2);
    }

    @Override
    public int processByte(byte by, byte[] arrby, int n) throws DataLengthException, IllegalStateException {
        this.data.write(by);
        return 0;
    }

    @Override
    public int processBytes(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws DataLengthException, IllegalStateException {
        if (arrby.length >= n + n2) {
            this.data.write(arrby, n, n2);
            return 0;
        }
        throw new DataLengthException("Input buffer too short");
    }

    public int processPacket(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws IllegalStateException, InvalidCipherTextException, DataLengthException {
        block7 : {
            block11 : {
                block12 : {
                    block13 : {
                        block10 : {
                            int n4;
                            byte[] arrby3;
                            int n5;
                            Object object;
                            block8 : {
                                block9 : {
                                    int n6;
                                    if (this.keyParam == null) break block7;
                                    n5 = 15 - this.nonce.length;
                                    if (n5 < 4 && n2 >= 1 << n5 * 8) {
                                        throw new IllegalStateException("CCM packet too large for choice of q.");
                                    }
                                    arrby3 = new byte[this.blockSize];
                                    arrby3[0] = (byte)(n5 - 1 & 7);
                                    object = this.nonce;
                                    System.arraycopy((byte[])object, (int)0, (byte[])arrby3, (int)1, (int)((byte[])object).length);
                                    object = new SICBlockCipher(this.cipher);
                                    object.init(this.forEncryption, new ParametersWithIV(this.keyParam, arrby3));
                                    n4 = n3;
                                    if (!this.forEncryption) break block8;
                                    int n7 = this.macSize + n2;
                                    if (arrby2.length < n7 + n3) break block9;
                                    this.calculateMac(arrby, n, n2, this.macBlock);
                                    arrby3 = new byte[this.blockSize];
                                    object.processBlock(this.macBlock, 0, arrby3, 0);
                                    for (n5 = n; n5 < n + n2 - (n6 = this.blockSize); n5 += n6) {
                                        object.processBlock(arrby, n5, arrby2, n4);
                                        n6 = this.blockSize;
                                        n4 += n6;
                                    }
                                    byte[] arrby4 = new byte[n6];
                                    System.arraycopy((byte[])arrby, (int)n5, (byte[])arrby4, (int)0, (int)(n2 + n - n5));
                                    object.processBlock(arrby4, 0, arrby4, 0);
                                    System.arraycopy((byte[])arrby4, (int)0, (byte[])arrby2, (int)n4, (int)(n2 + n - n5));
                                    System.arraycopy((byte[])arrby3, (int)0, (byte[])arrby2, (int)(n3 + n2), (int)this.macSize);
                                    n = n7;
                                    break block10;
                                }
                                throw new OutputLengthException("Output buffer too short.");
                            }
                            int n8 = this.macSize;
                            if (n2 < n8) break block11;
                            int n9 = n2 - n8;
                            if (arrby2.length < n9 + n3) break block12;
                            System.arraycopy((byte[])arrby, (int)(n + n9), (byte[])this.macBlock, (int)0, (int)n8);
                            arrby3 = this.macBlock;
                            object.processBlock(arrby3, 0, arrby3, 0);
                            int n10 = this.macSize;
                            do {
                                arrby3 = this.macBlock;
                                n2 = n5;
                                n8 = n4;
                                if (n10 == arrby3.length) break;
                                arrby3[n10] = (byte)(false ? 1 : 0);
                                ++n10;
                            } while (true);
                            while (n2 < n + n9 - (n5 = this.blockSize)) {
                                object.processBlock(arrby, n2, arrby2, n8);
                                n5 = this.blockSize;
                                n8 += n5;
                                n2 += n5;
                            }
                            arrby3 = new byte[n5];
                            System.arraycopy((byte[])arrby, (int)n2, (byte[])arrby3, (int)0, (int)(n9 - (n2 - n)));
                            object.processBlock(arrby3, 0, arrby3, 0);
                            System.arraycopy((byte[])arrby3, (int)0, (byte[])arrby2, (int)n8, (int)(n9 - (n2 - n)));
                            arrby = new byte[this.blockSize];
                            this.calculateMac(arrby2, n3, n9, arrby);
                            if (!Arrays.constantTimeAreEqual(this.macBlock, arrby)) break block13;
                            n = n9;
                        }
                        return n;
                    }
                    throw new InvalidCipherTextException("mac check in CCM failed");
                }
                throw new OutputLengthException("Output buffer too short.");
            }
            throw new InvalidCipherTextException("data too short");
        }
        throw new IllegalStateException("CCM cipher unitialized.");
    }

    public byte[] processPacket(byte[] arrby, int n, int n2) throws IllegalStateException, InvalidCipherTextException {
        block4 : {
            byte[] arrby2;
            block3 : {
                block2 : {
                    if (!this.forEncryption) break block2;
                    arrby2 = new byte[this.macSize + n2];
                    break block3;
                }
                int n3 = this.macSize;
                if (n2 < n3) break block4;
                arrby2 = new byte[n2 - n3];
            }
            this.processPacket(arrby, n, n2, arrby2, 0);
            return arrby2;
        }
        throw new InvalidCipherTextException("data too short");
    }

    @Override
    public void reset() {
        this.cipher.reset();
        this.associatedText.reset();
        this.data.reset();
    }

    private class ExposedByteArrayOutputStream
    extends ByteArrayOutputStream {
        public byte[] getBuffer() {
            return this.buf;
        }
    }

}

