/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.util.Arrays;

public class CBCBlockCipher
implements BlockCipher {
    private byte[] IV;
    private int blockSize;
    private byte[] cbcNextV;
    private byte[] cbcV;
    private BlockCipher cipher = null;
    private boolean encrypting;

    public CBCBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
        int n = this.blockSize = blockCipher.getBlockSize();
        this.IV = new byte[n];
        this.cbcV = new byte[n];
        this.cbcNextV = new byte[n];
    }

    private int decryptBlock(byte[] arrby, int n, byte[] arrby2, int n2) throws DataLengthException, IllegalStateException {
        int n3 = this.blockSize;
        if (n + n3 <= arrby.length) {
            System.arraycopy((byte[])arrby, (int)n, (byte[])this.cbcNextV, (int)0, (int)n3);
            n3 = this.cipher.processBlock(arrby, n, arrby2, n2);
            for (n = 0; n < this.blockSize; ++n) {
                int n4 = n2 + n;
                arrby2[n4] = (byte)(arrby2[n4] ^ this.cbcV[n]);
            }
            arrby = this.cbcV;
            this.cbcV = this.cbcNextV;
            this.cbcNextV = arrby;
            return n3;
        }
        throw new DataLengthException("input buffer too short");
    }

    private int encryptBlock(byte[] arrby, int n, byte[] arrby2, int n2) throws DataLengthException, IllegalStateException {
        if (this.blockSize + n <= arrby.length) {
            for (int i = 0; i < this.blockSize; ++i) {
                byte[] arrby3 = this.cbcV;
                arrby3[i] = (byte)(arrby3[i] ^ arrby[n + i]);
            }
            n = this.cipher.processBlock(this.cbcV, 0, arrby2, n2);
            arrby = this.cbcV;
            System.arraycopy((byte[])arrby2, (int)n2, (byte[])arrby, (int)0, (int)arrby.length);
            return n;
        }
        throw new DataLengthException("input buffer too short");
    }

    @Override
    public String getAlgorithmName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.cipher.getAlgorithmName());
        stringBuilder.append("/CBC");
        return stringBuilder.toString();
    }

    @Override
    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) throws IllegalArgumentException {
        block11 : {
            block9 : {
                boolean bl2;
                block10 : {
                    block7 : {
                        block8 : {
                            bl2 = this.encrypting;
                            this.encrypting = bl;
                            if (!(cipherParameters instanceof ParametersWithIV)) break block7;
                            byte[] arrby = ((ParametersWithIV)(cipherParameters = (ParametersWithIV)cipherParameters)).getIV();
                            if (arrby.length != this.blockSize) break block8;
                            System.arraycopy((byte[])arrby, (int)0, (byte[])this.IV, (int)0, (int)arrby.length);
                            this.reset();
                            if (((ParametersWithIV)cipherParameters).getParameters() != null) {
                                this.cipher.init(bl, ((ParametersWithIV)cipherParameters).getParameters());
                            } else if (bl2 != bl) {
                                throw new IllegalArgumentException("cannot change encrypting state without providing key.");
                            }
                            break block9;
                        }
                        throw new IllegalArgumentException("initialisation vector must be the same length as block size");
                    }
                    this.reset();
                    if (cipherParameters == null) break block10;
                    this.cipher.init(bl, cipherParameters);
                    break block9;
                }
                if (bl2 != bl) break block11;
            }
            return;
        }
        throw new IllegalArgumentException("cannot change encrypting state without providing key.");
    }

    @Override
    public int processBlock(byte[] arrby, int n, byte[] arrby2, int n2) throws DataLengthException, IllegalStateException {
        n = this.encrypting ? this.encryptBlock(arrby, n, arrby2, n2) : this.decryptBlock(arrby, n, arrby2, n2);
        return n;
    }

    @Override
    public void reset() {
        byte[] arrby = this.IV;
        System.arraycopy((byte[])arrby, (int)0, (byte[])this.cbcV, (int)0, (int)arrby.length);
        Arrays.fill(this.cbcNextV, (byte)0);
        this.cipher.reset();
    }
}

