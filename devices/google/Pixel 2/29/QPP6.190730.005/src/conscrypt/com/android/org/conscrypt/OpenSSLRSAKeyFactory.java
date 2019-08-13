/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLRSAPrivateCrtKey;
import com.android.org.conscrypt.OpenSSLRSAPrivateKey;
import com.android.org.conscrypt.OpenSSLRSAPublicKey;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public final class OpenSSLRSAKeyFactory
extends KeyFactorySpi {
    @Override
    protected PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec != null) {
            if (keySpec instanceof RSAPrivateCrtKeySpec) {
                return new OpenSSLRSAPrivateCrtKey((RSAPrivateCrtKeySpec)keySpec);
            }
            if (keySpec instanceof RSAPrivateKeySpec) {
                return new OpenSSLRSAPrivateKey((RSAPrivateKeySpec)keySpec);
            }
            if (keySpec instanceof PKCS8EncodedKeySpec) {
                return OpenSSLKey.getPrivateKey((PKCS8EncodedKeySpec)keySpec, 6);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Must use RSAPublicKeySpec or PKCS8EncodedKeySpec; was ");
            stringBuilder.append(keySpec.getClass().getName());
            throw new InvalidKeySpecException(stringBuilder.toString());
        }
        throw new InvalidKeySpecException("keySpec == null");
    }

    @Override
    protected PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec != null) {
            if (keySpec instanceof RSAPublicKeySpec) {
                return new OpenSSLRSAPublicKey((RSAPublicKeySpec)keySpec);
            }
            if (keySpec instanceof X509EncodedKeySpec) {
                return OpenSSLKey.getPublicKey((X509EncodedKeySpec)keySpec, 6);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Must use RSAPublicKeySpec or X509EncodedKeySpec; was ");
            stringBuilder.append(keySpec.getClass().getName());
            throw new InvalidKeySpecException(stringBuilder.toString());
        }
        throw new InvalidKeySpecException("keySpec == null");
    }

    @Override
    protected <T extends KeySpec> T engineGetKeySpec(Key key, Class<T> object) throws InvalidKeySpecException {
        if (key != null) {
            if (object != null) {
                if ("RSA".equals(key.getAlgorithm())) {
                    if (key instanceof RSAPublicKey && RSAPublicKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        key = (RSAPublicKey)key;
                        return (T)new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
                    }
                    if (key instanceof PublicKey && RSAPublicKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        object = key.getEncoded();
                        if ("X.509".equals(key.getFormat()) && object != null) {
                            key = (RSAPublicKey)this.engineGeneratePublic(new X509EncodedKeySpec((byte[])object));
                            return (T)new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
                        }
                        throw new InvalidKeySpecException("Not a valid X.509 encoding");
                    }
                    if (key instanceof RSAPrivateCrtKey && RSAPrivateCrtKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        key = (RSAPrivateCrtKey)key;
                        return (T)new RSAPrivateCrtKeySpec(key.getModulus(), key.getPublicExponent(), key.getPrivateExponent(), key.getPrimeP(), key.getPrimeQ(), key.getPrimeExponentP(), key.getPrimeExponentQ(), key.getCrtCoefficient());
                    }
                    if (key instanceof RSAPrivateCrtKey && RSAPrivateKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        key = (RSAPrivateCrtKey)key;
                        return (T)new RSAPrivateKeySpec(key.getModulus(), key.getPrivateExponent());
                    }
                    if (key instanceof RSAPrivateKey && RSAPrivateKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        key = (RSAPrivateKey)key;
                        return (T)new RSAPrivateKeySpec(key.getModulus(), key.getPrivateExponent());
                    }
                    if (key instanceof PrivateKey && RSAPrivateCrtKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        object = key.getEncoded();
                        if ("PKCS#8".equals(key.getFormat()) && object != null) {
                            key = (RSAPrivateKey)this.engineGeneratePrivate(new PKCS8EncodedKeySpec((byte[])object));
                            if (key instanceof RSAPrivateCrtKey) {
                                key = (RSAPrivateCrtKey)key;
                                return (T)new RSAPrivateCrtKeySpec(key.getModulus(), key.getPublicExponent(), key.getPrivateExponent(), key.getPrimeP(), key.getPrimeQ(), key.getPrimeExponentP(), key.getPrimeExponentQ(), key.getCrtCoefficient());
                            }
                            throw new InvalidKeySpecException("Encoded key is not an RSAPrivateCrtKey");
                        }
                        throw new InvalidKeySpecException("Not a valid PKCS#8 encoding");
                    }
                    if (key instanceof PrivateKey && RSAPrivateKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        object = key.getEncoded();
                        if ("PKCS#8".equals(key.getFormat()) && object != null) {
                            key = (RSAPrivateKey)this.engineGeneratePrivate(new PKCS8EncodedKeySpec((byte[])object));
                            return (T)new RSAPrivateKeySpec(key.getModulus(), key.getPrivateExponent());
                        }
                        throw new InvalidKeySpecException("Not a valid PKCS#8 encoding");
                    }
                    if (key instanceof PrivateKey && PKCS8EncodedKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        object = key.getEncoded();
                        if ("PKCS#8".equals(key.getFormat())) {
                            if (object != null) {
                                return (T)new PKCS8EncodedKeySpec((byte[])object);
                            }
                            throw new InvalidKeySpecException("Key is not encodable");
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Encoding type must be PKCS#8; was ");
                        ((StringBuilder)object).append(key.getFormat());
                        throw new InvalidKeySpecException(((StringBuilder)object).toString());
                    }
                    if (key instanceof PublicKey && X509EncodedKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        object = key.getEncoded();
                        if ("X.509".equals(key.getFormat())) {
                            if (object != null) {
                                return (T)new X509EncodedKeySpec((byte[])object);
                            }
                            throw new InvalidKeySpecException("Key is not encodable");
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Encoding type must be X.509; was ");
                        ((StringBuilder)object).append(key.getFormat());
                        throw new InvalidKeySpecException(((StringBuilder)object).toString());
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported key type and key spec combination; key=");
                    stringBuilder.append(key.getClass().getName());
                    stringBuilder.append(", keySpec=");
                    stringBuilder.append(((Class)object).getName());
                    throw new InvalidKeySpecException(stringBuilder.toString());
                }
                throw new InvalidKeySpecException("Key must be a RSA key");
            }
            throw new InvalidKeySpecException("keySpec == null");
        }
        throw new InvalidKeySpecException("key == null");
    }

    @Override
    protected Key engineTranslateKey(Key object) throws InvalidKeyException {
        if (object != null) {
            if (!(object instanceof OpenSSLRSAPublicKey) && !(object instanceof OpenSSLRSAPrivateKey)) {
                if (object instanceof RSAPublicKey) {
                    object = (RSAPublicKey)object;
                    try {
                        RSAPublicKeySpec rSAPublicKeySpec = new RSAPublicKeySpec(object.getModulus(), object.getPublicExponent());
                        object = this.engineGeneratePublic(rSAPublicKeySpec);
                        return object;
                    }
                    catch (InvalidKeySpecException invalidKeySpecException) {
                        throw new InvalidKeyException(invalidKeySpecException);
                    }
                }
                if (object instanceof RSAPrivateCrtKey) {
                    Serializable serializable = (RSAPrivateCrtKey)object;
                    BigInteger bigInteger = serializable.getModulus();
                    BigInteger bigInteger2 = serializable.getPublicExponent();
                    object = serializable.getPrivateExponent();
                    BigInteger bigInteger3 = serializable.getPrimeP();
                    BigInteger bigInteger4 = serializable.getPrimeQ();
                    BigInteger bigInteger5 = serializable.getPrimeExponentP();
                    BigInteger bigInteger6 = serializable.getPrimeExponentQ();
                    serializable = serializable.getCrtCoefficient();
                    try {
                        RSAPrivateCrtKeySpec rSAPrivateCrtKeySpec = new RSAPrivateCrtKeySpec(bigInteger, bigInteger2, (BigInteger)object, bigInteger3, bigInteger4, bigInteger5, bigInteger6, (BigInteger)serializable);
                        object = this.engineGeneratePrivate(rSAPrivateCrtKeySpec);
                        return object;
                    }
                    catch (InvalidKeySpecException invalidKeySpecException) {
                        throw new InvalidKeyException(invalidKeySpecException);
                    }
                }
                if (object instanceof RSAPrivateKey) {
                    Serializable serializable = (RSAPrivateKey)object;
                    object = serializable.getModulus();
                    serializable = serializable.getPrivateExponent();
                    try {
                        RSAPrivateKeySpec rSAPrivateKeySpec = new RSAPrivateKeySpec((BigInteger)object, (BigInteger)serializable);
                        object = this.engineGeneratePrivate(rSAPrivateKeySpec);
                        return object;
                    }
                    catch (InvalidKeySpecException invalidKeySpecException) {
                        throw new InvalidKeyException(invalidKeySpecException);
                    }
                }
                if (object instanceof PrivateKey && "PKCS#8".equals(object.getFormat())) {
                    byte[] arrby = object.getEncoded();
                    if (arrby != null) {
                        try {
                            object = new PKCS8EncodedKeySpec(arrby);
                            object = this.engineGeneratePrivate((KeySpec)object);
                            return object;
                        }
                        catch (InvalidKeySpecException invalidKeySpecException) {
                            throw new InvalidKeyException(invalidKeySpecException);
                        }
                    }
                    throw new InvalidKeyException("Key does not support encoding");
                }
                if (object instanceof PublicKey && "X.509".equals(object.getFormat())) {
                    if ((object = object.getEncoded()) != null) {
                        try {
                            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec((byte[])object);
                            object = this.engineGeneratePublic(x509EncodedKeySpec);
                            return object;
                        }
                        catch (InvalidKeySpecException invalidKeySpecException) {
                            throw new InvalidKeyException(invalidKeySpecException);
                        }
                    }
                    throw new InvalidKeyException("Key does not support encoding");
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Key must be an RSA public or private key; was ");
                stringBuilder.append(object.getClass().getName());
                throw new InvalidKeyException(stringBuilder.toString());
            }
            return object;
        }
        throw new InvalidKeyException("key == null");
    }
}

