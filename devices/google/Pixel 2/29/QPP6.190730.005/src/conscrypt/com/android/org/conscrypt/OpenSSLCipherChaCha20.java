/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLCipher;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;

public class OpenSSLCipherChaCha20
extends OpenSSLCipher {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int BLOCK_SIZE_BYTES = 64;
    private static final int NONCE_SIZE_BYTES = 12;
    private int blockCounter = 0;
    private int currentBlockConsumedBytes = 0;

    private void reset() {
        this.blockCounter = 0;
        this.currentBlockConsumedBytes = 0;
    }

    @Override
    void checkSupportedKeySize(int n) throws InvalidKeyException {
        if (n == 32) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported key size: ");
        stringBuilder.append(n);
        stringBuilder.append(" bytes (must be 32)");
        throw new InvalidKeyException(stringBuilder.toString());
    }

    @Override
    void checkSupportedMode(OpenSSLCipher.Mode mode) throws NoSuchAlgorithmException {
        if (mode == OpenSSLCipher.Mode.NONE) {
            return;
        }
        throw new NoSuchAlgorithmException("Mode must be NONE");
    }

    @Override
    void checkSupportedPadding(OpenSSLCipher.Padding padding) throws NoSuchPaddingException {
        if (padding == OpenSSLCipher.Padding.NOPADDING) {
            return;
        }
        throw new NoSuchPaddingException("Must be NoPadding");
    }

    @Override
    int doFinalInternal(byte[] arrby, int n, int n2) throws IllegalBlockSizeException, BadPaddingException, ShortBufferException {
        this.reset();
        return 0;
    }

    @Override
    void engineInitInternal(byte[] object, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        block8 : {
            block7 : {
                block5 : {
                    block6 : {
                        if (!(algorithmParameterSpec instanceof IvParameterSpec)) break block5;
                        object = (IvParameterSpec)algorithmParameterSpec;
                        if (((IvParameterSpec)object).getIV().length != 12) break block6;
                        this.iv = ((IvParameterSpec)object).getIV();
                        break block7;
                    }
                    throw new InvalidAlgorithmParameterException("IV must be 12 bytes long");
                }
                if (!this.isEncrypting()) break block8;
                this.iv = new byte[12];
                if (secureRandom != null) {
                    secureRandom.nextBytes(this.iv);
                } else {
                    NativeCrypto.RAND_bytes(this.iv);
                }
            }
            return;
        }
        throw new InvalidAlgorithmParameterException("IV must be specified when decrypting");
    }

    @Override
    String getBaseCipherName() {
        return "ChaCha20";
    }

    @Override
    int getCipherBlockSize() {
        return 0;
    }

    @Override
    int getOutputSizeForFinal(int n) {
        return n;
    }

    @Override
    int getOutputSizeForUpdate(int n) {
        return n;
    }

    @Override
    int updateInternal(byte[] arrby, int n, int n2, byte[] arrby2, int n3, int n4) throws ShortBufferException {
        if (n2 <= arrby2.length - n3) {
            n4 = this.currentBlockConsumedBytes;
            if (n4 > 0) {
                int n5 = Math.min(64 - n4, n2);
                byte[] arrby3 = new byte[64];
                byte[] arrby4 = new byte[64];
                System.arraycopy(arrby, n, arrby3, this.currentBlockConsumedBytes, n5);
                NativeCrypto.chacha20_encrypt_decrypt(arrby3, 0, arrby4, 0, 64, this.encodedKey, this.iv, this.blockCounter);
                System.arraycopy(arrby4, this.currentBlockConsumedBytes, arrby2, n3, n5);
                this.currentBlockConsumedBytes += n5;
                if (this.currentBlockConsumedBytes < 64) {
                    return n5;
                }
                this.currentBlockConsumedBytes = 0;
                n += n5;
                ++this.blockCounter;
                n4 = n3 + n5;
                n3 = n2 - n5;
            } else {
                n4 = n3;
                n3 = n2;
            }
            NativeCrypto.chacha20_encrypt_decrypt(arrby, n, arrby2, n4, n3, this.encodedKey, this.iv, this.blockCounter);
            this.currentBlockConsumedBytes = n3 % 64;
            this.blockCounter += n3 / 64;
            return n2;
        }
        throw new ShortBufferException("Insufficient output space");
    }
}

