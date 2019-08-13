/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.util;

import com.android.org.bouncycastle.jcajce.util.JcaJceHelper;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;

public class DefaultJcaJceHelper
implements JcaJceHelper {
    @Override
    public AlgorithmParameterGenerator createAlgorithmParameterGenerator(String string) throws NoSuchAlgorithmException {
        return AlgorithmParameterGenerator.getInstance(string);
    }

    @Override
    public AlgorithmParameters createAlgorithmParameters(String string) throws NoSuchAlgorithmException {
        return AlgorithmParameters.getInstance(string);
    }

    @Override
    public CertificateFactory createCertificateFactory(String string) throws CertificateException {
        return CertificateFactory.getInstance(string);
    }

    @Override
    public Cipher createCipher(String string) throws NoSuchAlgorithmException, NoSuchPaddingException {
        return Cipher.getInstance(string);
    }

    @Override
    public MessageDigest createDigest(String string) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(string);
    }

    @Override
    public KeyAgreement createKeyAgreement(String string) throws NoSuchAlgorithmException {
        return KeyAgreement.getInstance(string);
    }

    @Override
    public KeyFactory createKeyFactory(String string) throws NoSuchAlgorithmException {
        return KeyFactory.getInstance(string);
    }

    @Override
    public KeyGenerator createKeyGenerator(String string) throws NoSuchAlgorithmException {
        return KeyGenerator.getInstance(string);
    }

    @Override
    public KeyPairGenerator createKeyPairGenerator(String string) throws NoSuchAlgorithmException {
        return KeyPairGenerator.getInstance(string);
    }

    @Override
    public Mac createMac(String string) throws NoSuchAlgorithmException {
        return Mac.getInstance(string);
    }

    @Override
    public SecretKeyFactory createSecretKeyFactory(String string) throws NoSuchAlgorithmException {
        return SecretKeyFactory.getInstance(string);
    }

    @Override
    public SecureRandom createSecureRandom(String string) throws NoSuchAlgorithmException {
        return SecureRandom.getInstance(string);
    }

    @Override
    public Signature createSignature(String string) throws NoSuchAlgorithmException {
        return Signature.getInstance(string);
    }
}

