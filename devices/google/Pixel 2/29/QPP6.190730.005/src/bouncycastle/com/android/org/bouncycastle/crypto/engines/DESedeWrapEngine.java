/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.engines;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.Wrapper;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.crypto.engines.DESedeEngine;
import com.android.org.bouncycastle.crypto.modes.CBCBlockCipher;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.util.Arrays;
import java.security.SecureRandom;

public class DESedeWrapEngine
implements Wrapper {
    private static final byte[] IV2 = new byte[]{74, -35, -94, 44, 121, -24, 33, 5};
    byte[] digest = new byte[20];
    private CBCBlockCipher engine;
    private boolean forWrapping;
    private byte[] iv;
    private KeyParameter param;
    private ParametersWithIV paramPlusIV;
    Digest sha1 = AndroidDigestFactory.getSHA1();

    private byte[] calculateCMSKeyChecksum(byte[] arrby) {
        byte[] arrby2 = new byte[8];
        this.sha1.update(arrby, 0, arrby.length);
        this.sha1.doFinal(this.digest, 0);
        System.arraycopy((byte[])this.digest, (int)0, (byte[])arrby2, (int)0, (int)8);
        return arrby2;
    }

    private boolean checkCMSKeyChecksum(byte[] arrby, byte[] arrby2) {
        return Arrays.constantTimeAreEqual(this.calculateCMSKeyChecksum(arrby), arrby2);
    }

    private static byte[] reverse(byte[] arrby) {
        byte[] arrby2 = new byte[arrby.length];
        for (int i = 0; i < arrby.length; ++i) {
            arrby2[i] = arrby[arrby.length - (i + 1)];
        }
        return arrby2;
    }

    @Override
    public String getAlgorithmName() {
        return "DESede";
    }

    @Override
    public void init(boolean bl, CipherParameters arrby) {
        CipherParameters cipherParameters;
        this.forWrapping = bl;
        this.engine = new CBCBlockCipher(new DESedeEngine());
        if (arrby instanceof ParametersWithRandom) {
            arrby = (ParametersWithRandom)arrby;
            cipherParameters = arrby.getParameters();
            arrby = arrby.getRandom();
        } else {
            SecureRandom secureRandom = CryptoServicesRegistrar.getSecureRandom();
            cipherParameters = arrby;
            arrby = secureRandom;
        }
        if (cipherParameters instanceof KeyParameter) {
            this.param = (KeyParameter)cipherParameters;
            if (this.forWrapping) {
                this.iv = new byte[8];
                arrby.nextBytes(this.iv);
                this.paramPlusIV = new ParametersWithIV(this.param, this.iv);
            }
        } else if (cipherParameters instanceof ParametersWithIV) {
            this.paramPlusIV = (ParametersWithIV)cipherParameters;
            this.iv = this.paramPlusIV.getIV();
            this.param = (KeyParameter)this.paramPlusIV.getParameters();
            if (this.forWrapping) {
                arrby = this.iv;
                if (arrby == null || arrby.length != 8) {
                    throw new IllegalArgumentException("IV is not 8 octets");
                }
            } else {
                throw new IllegalArgumentException("You should not supply an IV for unwrapping");
            }
        }
    }

    @Override
    public byte[] unwrap(byte[] object, int n, int n2) throws InvalidCipherTextException {
        if (!this.forWrapping) {
            if (object != null) {
                int n3 = this.engine.getBlockSize();
                if (n2 % n3 == 0) {
                    byte[] arrby = new ParametersWithIV(this.param, IV2);
                    this.engine.init(false, (CipherParameters)arrby);
                    arrby = new byte[n2];
                    for (int i = 0; i != n2; i += n3) {
                        this.engine.processBlock((byte[])object, n + i, arrby, i);
                    }
                    object = DESedeWrapEngine.reverse(arrby);
                    this.iv = new byte[8];
                    arrby = new byte[((Object)object).length - 8];
                    System.arraycopy((byte[])object, (int)0, (byte[])this.iv, (int)0, (int)8);
                    System.arraycopy((byte[])object, (int)8, (byte[])arrby, (int)0, (int)(((Object)object).length - 8));
                    this.paramPlusIV = new ParametersWithIV(this.param, this.iv);
                    this.engine.init(false, this.paramPlusIV);
                    object = new byte[arrby.length];
                    for (n = 0; n != ((Object)object).length; n += n3) {
                        this.engine.processBlock(arrby, n, (byte[])object, n);
                    }
                    byte[] arrby2 = new byte[((Object)object).length - 8];
                    arrby = new byte[8];
                    System.arraycopy((byte[])object, (int)0, (byte[])arrby2, (int)0, (int)(((Object)object).length - 8));
                    System.arraycopy((byte[])object, (int)(((Object)object).length - 8), (byte[])arrby, (int)0, (int)8);
                    if (this.checkCMSKeyChecksum(arrby2, arrby)) {
                        return arrby2;
                    }
                    throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Ciphertext not multiple of ");
                ((StringBuilder)object).append(n3);
                throw new InvalidCipherTextException(((StringBuilder)object).toString());
            }
            throw new InvalidCipherTextException("Null pointer as ciphertext");
        }
        throw new IllegalStateException("Not set for unwrapping");
    }

    @Override
    public byte[] wrap(byte[] arrby, int n, int n2) {
        if (this.forWrapping) {
            Object object = new byte[n2];
            System.arraycopy((byte[])arrby, (int)n, (byte[])object, (int)0, (int)n2);
            byte[] arrby2 = this.calculateCMSKeyChecksum((byte[])object);
            arrby = new byte[((byte[])object).length + arrby2.length];
            System.arraycopy((byte[])object, (int)0, (byte[])arrby, (int)0, (int)((byte[])object).length);
            System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)((byte[])object).length, (int)arrby2.length);
            n2 = this.engine.getBlockSize();
            if (arrby.length % n2 == 0) {
                this.engine.init(true, this.paramPlusIV);
                object = new byte[arrby.length];
                for (n = 0; n != arrby.length; n += n2) {
                    this.engine.processBlock(arrby, n, (byte[])object, n);
                }
                arrby2 = this.iv;
                arrby = new byte[arrby2.length + ((byte[])object).length];
                System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)0, (int)arrby2.length);
                System.arraycopy((byte[])object, (int)0, (byte[])arrby, (int)this.iv.length, (int)((byte[])object).length);
                arrby = DESedeWrapEngine.reverse(arrby);
                object = new ParametersWithIV(this.param, IV2);
                this.engine.init(true, (CipherParameters)object);
                for (n = 0; n != arrby.length; n += n2) {
                    this.engine.processBlock(arrby, n, arrby, n);
                }
                return arrby;
            }
            throw new IllegalStateException("Not multiple of block length");
        }
        throw new IllegalStateException("Not initialized for wrapping");
    }
}

