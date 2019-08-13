/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLKeyHolder;
import com.android.org.conscrypt.OpenSSLRSAPrivateCrtKey;
import com.android.org.conscrypt.Platform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

class OpenSSLRSAPrivateKey
implements RSAPrivateKey,
OpenSSLKeyHolder {
    private static final long serialVersionUID = 4872170254439578735L;
    transient boolean fetchedParams;
    transient OpenSSLKey key;
    BigInteger modulus;
    BigInteger privateExponent;

    OpenSSLRSAPrivateKey(OpenSSLKey openSSLKey) {
        this.key = openSSLKey;
    }

    OpenSSLRSAPrivateKey(OpenSSLKey openSSLKey, byte[][] arrby) {
        this(openSSLKey);
        this.readParams(arrby);
        this.fetchedParams = true;
    }

    public OpenSSLRSAPrivateKey(RSAPrivateKeySpec rSAPrivateKeySpec) throws InvalidKeySpecException {
        this(OpenSSLRSAPrivateKey.init(rSAPrivateKeySpec));
    }

    static OpenSSLKey getInstance(RSAPrivateKey object) throws InvalidKeyException {
        if (object.getFormat() == null) {
            return OpenSSLRSAPrivateKey.wrapPlatformKey((RSAPrivateKey)object);
        }
        BigInteger bigInteger = object.getModulus();
        object = object.getPrivateExponent();
        if (bigInteger != null) {
            if (object != null) {
                try {
                    object = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(bigInteger.toByteArray(), null, ((BigInteger)object).toByteArray(), null, null, null, null, null));
                    return object;
                }
                catch (Exception exception) {
                    throw new InvalidKeyException(exception);
                }
            }
            throw new InvalidKeyException("privateExponent == null");
        }
        throw new InvalidKeyException("modulus == null");
    }

    static OpenSSLRSAPrivateKey getInstance(OpenSSLKey openSSLKey) {
        byte[][] arrby = NativeCrypto.get_RSA_private_params(openSSLKey.getNativeRef());
        if (arrby[1] != null) {
            return new OpenSSLRSAPrivateCrtKey(openSSLKey, arrby);
        }
        return new OpenSSLRSAPrivateKey(openSSLKey, arrby);
    }

    private static OpenSSLKey init(RSAPrivateKeySpec object) throws InvalidKeySpecException {
        BigInteger bigInteger = ((RSAPrivateKeySpec)object).getModulus();
        object = ((RSAPrivateKeySpec)object).getPrivateExponent();
        if (bigInteger != null) {
            if (object != null) {
                try {
                    object = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(bigInteger.toByteArray(), null, ((BigInteger)object).toByteArray(), null, null, null, null, null));
                    return object;
                }
                catch (Exception exception) {
                    throw new InvalidKeySpecException(exception);
                }
            }
            throw new InvalidKeySpecException("privateExponent == null");
        }
        throw new InvalidKeySpecException("modulus == null");
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(this.modulus.toByteArray(), null, this.privateExponent.toByteArray(), null, null, null, null, null));
        this.fetchedParams = true;
    }

    static OpenSSLKey wrapJCAPrivateKeyForTLSStackOnly(PrivateKey privateKey, PublicKey publicKey) throws InvalidKeyException {
        Serializable serializable = null;
        if (privateKey instanceof RSAKey) {
            serializable = ((RSAKey)((Object)privateKey)).getModulus();
        } else if (publicKey instanceof RSAKey) {
            serializable = ((RSAKey)((Object)publicKey)).getModulus();
        }
        if (serializable != null) {
            return new OpenSSLKey(NativeCrypto.getRSAPrivateKeyWrapper(privateKey, ((BigInteger)serializable).toByteArray()), true);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("RSA modulus not available. Private: ");
        ((StringBuilder)serializable).append(privateKey);
        ((StringBuilder)serializable).append(", public: ");
        ((StringBuilder)serializable).append(publicKey);
        throw new InvalidKeyException(((StringBuilder)serializable).toString());
    }

    static OpenSSLKey wrapPlatformKey(RSAPrivateKey rSAPrivateKey) throws InvalidKeyException {
        OpenSSLKey openSSLKey = Platform.wrapRsaKey(rSAPrivateKey);
        if (openSSLKey != null) {
            return openSSLKey;
        }
        return new OpenSSLKey(NativeCrypto.getRSAPrivateKeyWrapper(rSAPrivateKey, rSAPrivateKey.getModulus().toByteArray()), true);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.ensureReadParams();
        objectOutputStream.defaultWriteObject();
    }

    final void ensureReadParams() {
        synchronized (this) {
            block4 : {
                boolean bl = this.fetchedParams;
                if (!bl) break block4;
                return;
            }
            this.readParams(NativeCrypto.get_RSA_private_params(this.key.getNativeRef()));
            this.fetchedParams = true;
            return;
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof OpenSSLRSAPrivateKey) {
            object = (OpenSSLRSAPrivateKey)object;
            return this.key.equals(((OpenSSLRSAPrivateKey)object).getOpenSSLKey());
        }
        if (object instanceof RSAPrivateKey) {
            this.ensureReadParams();
            object = (RSAPrivateKey)object;
            if (!this.modulus.equals(object.getModulus()) || !this.privateExponent.equals(object.getPrivateExponent())) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public final String getAlgorithm() {
        return "RSA";
    }

    @Override
    public final byte[] getEncoded() {
        return NativeCrypto.EVP_marshal_private_key(this.key.getNativeRef());
    }

    @Override
    public final String getFormat() {
        return "PKCS#8";
    }

    @Override
    public final BigInteger getModulus() {
        this.ensureReadParams();
        return this.modulus;
    }

    @Override
    public OpenSSLKey getOpenSSLKey() {
        return this.key;
    }

    @Override
    public final BigInteger getPrivateExponent() {
        this.ensureReadParams();
        return this.privateExponent;
    }

    public int hashCode() {
        this.ensureReadParams();
        int n = 1 * 3 + this.modulus.hashCode();
        BigInteger bigInteger = this.privateExponent;
        int n2 = n;
        if (bigInteger != null) {
            n2 = n * 7 + bigInteger.hashCode();
        }
        return n2;
    }

    void readParams(byte[][] arrby) {
        if (arrby[0] != null) {
            if (arrby[2] != null) {
                this.modulus = new BigInteger(arrby[0]);
                if (arrby[2] != null) {
                    this.privateExponent = new BigInteger(arrby[2]);
                }
                return;
            }
            throw new NullPointerException("privateExponent == null");
        }
        throw new NullPointerException("modulus == null");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("OpenSSLRSAPrivateKey{");
        this.ensureReadParams();
        stringBuilder.append("modulus=");
        stringBuilder.append(this.modulus.toString(16));
        return stringBuilder.toString();
    }
}

