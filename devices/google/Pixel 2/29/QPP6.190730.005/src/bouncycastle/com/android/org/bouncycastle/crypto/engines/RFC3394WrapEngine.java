/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.engines;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.Wrapper;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.util.Arrays;

public class RFC3394WrapEngine
implements Wrapper {
    private BlockCipher engine;
    private boolean forWrapping;
    private byte[] iv = new byte[]{-90, -90, -90, -90, -90, -90, -90, -90};
    private KeyParameter param;
    private boolean wrapCipherMode;

    public RFC3394WrapEngine(BlockCipher blockCipher) {
        this(blockCipher, false);
    }

    public RFC3394WrapEngine(BlockCipher blockCipher, boolean bl) {
        this.engine = blockCipher;
        this.wrapCipherMode = bl ^ true;
    }

    @Override
    public String getAlgorithmName() {
        return this.engine.getAlgorithmName();
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) {
        this.forWrapping = bl;
        CipherParameters cipherParameters2 = cipherParameters;
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters2 = ((ParametersWithRandom)cipherParameters).getParameters();
        }
        if (cipherParameters2 instanceof KeyParameter) {
            this.param = (KeyParameter)cipherParameters2;
        } else if (cipherParameters2 instanceof ParametersWithIV) {
            this.iv = ((ParametersWithIV)cipherParameters2).getIV();
            this.param = (KeyParameter)((ParametersWithIV)cipherParameters2).getParameters();
            if (this.iv.length != 8) {
                throw new IllegalArgumentException("IV not equal to 8");
            }
        }
    }

    @Override
    public byte[] unwrap(byte[] arrby, int n, int n2) throws InvalidCipherTextException {
        if (!this.forWrapping) {
            int n3 = n2 / 8;
            if (n3 * 8 == n2) {
                byte[] arrby2 = this.iv;
                byte[] arrby3 = new byte[n2 - arrby2.length];
                byte[] arrby4 = new byte[arrby2.length];
                int n4 = arrby2.length;
                int n5 = 8;
                byte[] arrby5 = new byte[n4 + 8];
                System.arraycopy((byte[])arrby, (int)n, (byte[])arrby4, (int)0, (int)arrby2.length);
                arrby2 = this.iv;
                System.arraycopy((byte[])arrby, (int)(arrby2.length + n), (byte[])arrby3, (int)0, (int)(n2 - arrby2.length));
                this.engine.init(this.wrapCipherMode ^ true, this.param);
                n4 = n3 - 1;
                for (n = 5; n >= 0; --n) {
                    for (n2 = n4; n2 >= 1; --n2) {
                        System.arraycopy((byte[])arrby4, (int)0, (byte[])arrby5, (int)0, (int)this.iv.length);
                        System.arraycopy((byte[])arrby3, (int)((n2 - 1) * n5), (byte[])arrby5, (int)this.iv.length, (int)n5);
                        n3 = n4 * n + n2;
                        n5 = 1;
                        while (n3 != 0) {
                            byte by = (byte)n3;
                            int n6 = this.iv.length - n5;
                            arrby5[n6] = (byte)(arrby5[n6] ^ by);
                            n3 >>>= 8;
                            ++n5;
                        }
                        this.engine.processBlock(arrby5, 0, arrby5, 0);
                        n5 = 8;
                        System.arraycopy((byte[])arrby5, (int)0, (byte[])arrby4, (int)0, (int)8);
                        System.arraycopy((byte[])arrby5, (int)8, (byte[])arrby3, (int)((n2 - 1) * 8), (int)8);
                    }
                }
                if (Arrays.constantTimeAreEqual(arrby4, this.iv)) {
                    return arrby3;
                }
                throw new InvalidCipherTextException("checksum failed");
            }
            throw new InvalidCipherTextException("unwrap data must be a multiple of 8 bytes");
        }
        throw new IllegalStateException("not set for unwrapping");
    }

    @Override
    public byte[] wrap(byte[] arrby, int n, int n2) {
        if (this.forWrapping) {
            int n3 = n2 / 8;
            if (n3 * 8 == n2) {
                byte[] arrby2 = this.iv;
                byte[] arrby3 = new byte[arrby2.length + n2];
                byte[] arrby4 = new byte[arrby2.length + 8];
                System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby3, (int)0, (int)arrby2.length);
                System.arraycopy((byte[])arrby, (int)n, (byte[])arrby3, (int)this.iv.length, (int)n2);
                this.engine.init(this.wrapCipherMode, this.param);
                for (n = 0; n != 6; ++n) {
                    for (n2 = 1; n2 <= n3; ++n2) {
                        System.arraycopy((byte[])arrby3, (int)0, (byte[])arrby4, (int)0, (int)this.iv.length);
                        System.arraycopy((byte[])arrby3, (int)(n2 * 8), (byte[])arrby4, (int)this.iv.length, (int)8);
                        this.engine.processBlock(arrby4, 0, arrby4, 0);
                        int n4 = n3 * n + n2;
                        int n5 = 1;
                        while (n4 != 0) {
                            byte by = (byte)n4;
                            int n6 = this.iv.length - n5;
                            arrby4[n6] = (byte)(arrby4[n6] ^ by);
                            n4 >>>= 8;
                            ++n5;
                        }
                        System.arraycopy((byte[])arrby4, (int)0, (byte[])arrby3, (int)0, (int)8);
                        System.arraycopy((byte[])arrby4, (int)8, (byte[])arrby3, (int)(n2 * 8), (int)8);
                    }
                }
                return arrby3;
            }
            throw new DataLengthException("wrap data must be a multiple of 8 bytes");
        }
        throw new IllegalStateException("not set for wrapping");
    }
}

