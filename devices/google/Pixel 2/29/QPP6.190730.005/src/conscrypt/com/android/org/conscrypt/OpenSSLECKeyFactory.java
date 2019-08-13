/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.OpenSSLECPrivateKey;
import com.android.org.conscrypt.OpenSSLECPublicKey;
import com.android.org.conscrypt.OpenSSLKey;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public final class OpenSSLECKeyFactory
extends KeyFactorySpi {
    @Override
    protected PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec != null) {
            if (keySpec instanceof ECPrivateKeySpec) {
                return new OpenSSLECPrivateKey((ECPrivateKeySpec)keySpec);
            }
            if (keySpec instanceof PKCS8EncodedKeySpec) {
                return OpenSSLKey.getPrivateKey((PKCS8EncodedKeySpec)keySpec, 408);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Must use ECPrivateKeySpec or PKCS8EncodedKeySpec; was ");
            stringBuilder.append(keySpec.getClass().getName());
            throw new InvalidKeySpecException(stringBuilder.toString());
        }
        throw new InvalidKeySpecException("keySpec == null");
    }

    @Override
    protected PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec != null) {
            if (keySpec instanceof ECPublicKeySpec) {
                return new OpenSSLECPublicKey((ECPublicKeySpec)keySpec);
            }
            if (keySpec instanceof X509EncodedKeySpec) {
                return OpenSSLKey.getPublicKey((X509EncodedKeySpec)keySpec, 408);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Must use ECPublicKeySpec or X509EncodedKeySpec; was ");
            stringBuilder.append(keySpec.getClass().getName());
            throw new InvalidKeySpecException(stringBuilder.toString());
        }
        throw new InvalidKeySpecException("keySpec == null");
    }

    @Override
    protected <T extends KeySpec> T engineGetKeySpec(Key key, Class<T> object) throws InvalidKeySpecException {
        if (key != null) {
            if (object != null) {
                if ("EC".equals(key.getAlgorithm())) {
                    if (key instanceof ECPublicKey && ECPublicKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        key = (ECPublicKey)key;
                        return (T)new ECPublicKeySpec(key.getW(), key.getParams());
                    }
                    if (key instanceof PublicKey && ECPublicKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        object = key.getEncoded();
                        if ("X.509".equals(key.getFormat()) && object != null) {
                            key = (ECPublicKey)this.engineGeneratePublic(new X509EncodedKeySpec((byte[])object));
                            return (T)new ECPublicKeySpec(key.getW(), key.getParams());
                        }
                        throw new InvalidKeySpecException("Not a valid X.509 encoding");
                    }
                    if (key instanceof ECPrivateKey && ECPrivateKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        key = (ECPrivateKey)key;
                        return (T)new ECPrivateKeySpec(key.getS(), key.getParams());
                    }
                    if (key instanceof PrivateKey && ECPrivateKeySpec.class.isAssignableFrom((Class<?>)object)) {
                        object = key.getEncoded();
                        if ("PKCS#8".equals(key.getFormat()) && object != null) {
                            key = (ECPrivateKey)this.engineGeneratePrivate(new PKCS8EncodedKeySpec((byte[])object));
                            return (T)new ECPrivateKeySpec(key.getS(), key.getParams());
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
                throw new InvalidKeySpecException("Key must be an EC key");
            }
            throw new InvalidKeySpecException("keySpec == null");
        }
        throw new InvalidKeySpecException("key == null");
    }

    @Override
    protected Key engineTranslateKey(Key object) throws InvalidKeyException {
        if (object != null) {
            if (!(object instanceof OpenSSLECPublicKey) && !(object instanceof OpenSSLECPrivateKey)) {
                if (object instanceof ECPublicKey) {
                    Object object2 = (ECPublicKey)object;
                    object = object2.getW();
                    ECParameterSpec eCParameterSpec = object2.getParams();
                    try {
                        object2 = new ECPublicKeySpec((ECPoint)object, eCParameterSpec);
                        object = this.engineGeneratePublic((KeySpec)object2);
                        return object;
                    }
                    catch (InvalidKeySpecException invalidKeySpecException) {
                        throw new InvalidKeyException(invalidKeySpecException);
                    }
                }
                if (object instanceof ECPrivateKey) {
                    Object object3 = (ECPrivateKey)object;
                    object = object3.getS();
                    ECParameterSpec eCParameterSpec = object3.getParams();
                    try {
                        object3 = new ECPrivateKeySpec((BigInteger)object, eCParameterSpec);
                        object = this.engineGeneratePrivate((KeySpec)object3);
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
                stringBuilder.append("Key must be EC public or private key; was ");
                stringBuilder.append(object.getClass().getName());
                throw new InvalidKeyException(stringBuilder.toString());
            }
            return object;
        }
        throw new InvalidKeyException("key == null");
    }
}

