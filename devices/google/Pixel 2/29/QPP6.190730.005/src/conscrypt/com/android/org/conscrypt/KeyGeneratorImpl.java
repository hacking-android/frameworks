/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public abstract class KeyGeneratorImpl
extends KeyGeneratorSpi {
    private final String algorithm;
    private int keySizeBits;
    protected SecureRandom secureRandom;

    private KeyGeneratorImpl(String string, int n) {
        this.algorithm = string;
        this.keySizeBits = n;
    }

    protected void checkKeySize(int n) {
        if (n > 0) {
            return;
        }
        throw new InvalidParameterException("Key size must be positive");
    }

    protected byte[] doKeyGeneration(int n) {
        byte[] arrby = new byte[n];
        this.secureRandom.nextBytes(arrby);
        return arrby;
    }

    @Override
    protected SecretKey engineGenerateKey() {
        if (this.secureRandom == null) {
            this.secureRandom = new SecureRandom();
        }
        return new SecretKeySpec(this.doKeyGeneration((this.keySizeBits + 7) / 8), this.algorithm);
    }

    @Override
    protected void engineInit(int n, SecureRandom secureRandom) {
        this.checkKeySize(n);
        this.keySizeBits = n;
        this.secureRandom = secureRandom;
    }

    @Override
    protected void engineInit(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom serializable) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec == null) {
            throw new InvalidAlgorithmParameterException("No params provided");
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unknown param type: ");
        ((StringBuilder)serializable).append(algorithmParameterSpec.getClass().getName());
        throw new InvalidAlgorithmParameterException(((StringBuilder)serializable).toString());
    }

    public static final class AES
    extends KeyGeneratorImpl {
        public AES() {
            super("AES", 128);
        }

        @Override
        protected void checkKeySize(int n) {
            if (n != 128 && n != 192 && n != 256) {
                throw new InvalidParameterException("Key size must be either 128, 192, or 256 bits");
            }
        }
    }

    public static final class ARC4
    extends KeyGeneratorImpl {
        public ARC4() {
            super("ARC4", 128);
        }

        @Override
        protected void checkKeySize(int n) {
            if (n >= 40 && 2048 >= n) {
                return;
            }
            throw new InvalidParameterException("Key size must be between 40 and 2048 bits");
        }
    }

    public static final class ChaCha20
    extends KeyGeneratorImpl {
        public ChaCha20() {
            super("ChaCha20", 256);
        }

        @Override
        protected void checkKeySize(int n) {
            if (n == 256) {
                return;
            }
            throw new InvalidParameterException("Key size must be 256 bits");
        }
    }

    public static final class DESEDE
    extends KeyGeneratorImpl {
        public DESEDE() {
            super("DESEDE", 192);
        }

        @Override
        protected void checkKeySize(int n) {
            if (n != 112 && n != 168) {
                throw new InvalidParameterException("Key size must be either 112 or 168 bits");
            }
        }

        @Override
        protected byte[] doKeyGeneration(int n) {
            byte[] arrby = new byte[24];
            this.secureRandom.nextBytes(arrby);
            for (int i = 0; i < arrby.length; ++i) {
                if (Integer.bitCount(arrby[i]) % 2 != 0) continue;
                arrby[i] = (byte)(arrby[i] ^ 1);
            }
            if (n == 14) {
                System.arraycopy(arrby, 0, arrby, 16, 8);
            }
            return arrby;
        }
    }

    public static final class HmacMD5
    extends KeyGeneratorImpl {
        public HmacMD5() {
            super("HmacMD5", 128);
        }
    }

    public static final class HmacSHA1
    extends KeyGeneratorImpl {
        public HmacSHA1() {
            super("HmacSHA1", 160);
        }
    }

    public static final class HmacSHA224
    extends KeyGeneratorImpl {
        public HmacSHA224() {
            super("HmacSHA224", 224);
        }
    }

    public static final class HmacSHA256
    extends KeyGeneratorImpl {
        public HmacSHA256() {
            super("HmacSHA256", 256);
        }
    }

    public static final class HmacSHA384
    extends KeyGeneratorImpl {
        public HmacSHA384() {
            super("HmacSHA384", 384);
        }
    }

    public static final class HmacSHA512
    extends KeyGeneratorImpl {
        public HmacSHA512() {
            super("HmacSHA512", 512);
        }
    }

}

