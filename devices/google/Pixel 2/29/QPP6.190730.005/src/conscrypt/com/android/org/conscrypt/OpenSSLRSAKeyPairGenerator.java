/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLRSAPrivateKey;
import com.android.org.conscrypt.OpenSSLRSAPublicKey;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;

public final class OpenSSLRSAKeyPairGenerator
extends KeyPairGeneratorSpi {
    private int modulusBits = 2048;
    private byte[] publicExponent = new byte[]{1, 0, 1};

    @Override
    public KeyPair generateKeyPair() {
        OpenSSLKey openSSLKey = new OpenSSLKey(NativeCrypto.RSA_generate_key_ex(this.modulusBits, this.publicExponent));
        OpenSSLRSAPrivateKey openSSLRSAPrivateKey = OpenSSLRSAPrivateKey.getInstance(openSSLKey);
        return new KeyPair(new OpenSSLRSAPublicKey(openSSLKey), openSSLRSAPrivateKey);
    }

    @Override
    public void initialize(int n, SecureRandom secureRandom) {
        this.modulusBits = n;
    }

    @Override
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom serializable) throws InvalidAlgorithmParameterException {
        if (algorithmParameterSpec instanceof RSAKeyGenParameterSpec) {
            serializable = ((RSAKeyGenParameterSpec)(algorithmParameterSpec = (RSAKeyGenParameterSpec)algorithmParameterSpec)).getPublicExponent();
            if (serializable != null) {
                this.publicExponent = ((BigInteger)serializable).toByteArray();
            }
            this.modulusBits = ((RSAKeyGenParameterSpec)algorithmParameterSpec).getKeysize();
            return;
        }
        throw new InvalidAlgorithmParameterException("Only RSAKeyGenParameterSpec supported");
    }
}

