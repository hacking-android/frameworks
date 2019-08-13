/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLKeyHolder;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

public class OpenSSLRSAPublicKey
implements RSAPublicKey,
OpenSSLKeyHolder {
    private static final long serialVersionUID = 123125005824688292L;
    private transient boolean fetchedParams;
    private transient OpenSSLKey key;
    private BigInteger modulus;
    private BigInteger publicExponent;

    OpenSSLRSAPublicKey(OpenSSLKey openSSLKey) {
        this.key = openSSLKey;
    }

    OpenSSLRSAPublicKey(RSAPublicKeySpec rSAPublicKeySpec) throws InvalidKeySpecException {
        try {
            OpenSSLKey openSSLKey;
            this.key = openSSLKey = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(rSAPublicKeySpec.getModulus().toByteArray(), rSAPublicKeySpec.getPublicExponent().toByteArray(), null, null, null, null, null, null));
            return;
        }
        catch (Exception exception) {
            throw new InvalidKeySpecException(exception);
        }
    }

    private void ensureReadParams() {
        synchronized (this) {
            BigInteger bigInteger;
            block4 : {
                boolean bl = this.fetchedParams;
                if (!bl) break block4;
                return;
            }
            byte[][] arrby = NativeCrypto.get_RSA_public_params(this.key.getNativeRef());
            this.modulus = bigInteger = new BigInteger(arrby[0]);
            this.publicExponent = bigInteger = new BigInteger(arrby[1]);
            this.fetchedParams = true;
            return;
        }
    }

    static OpenSSLKey getInstance(RSAPublicKey object) throws InvalidKeyException {
        try {
            object = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(object.getModulus().toByteArray(), object.getPublicExponent().toByteArray(), null, null, null, null, null, null));
            return object;
        }
        catch (Exception exception) {
            throw new InvalidKeyException(exception);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_RSA(this.modulus.toByteArray(), this.publicExponent.toByteArray(), null, null, null, null, null, null));
        this.fetchedParams = true;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.ensureReadParams();
        objectOutputStream.defaultWriteObject();
    }

    public boolean equals(Object object) {
        OpenSSLRSAPublicKey openSSLRSAPublicKey;
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof OpenSSLRSAPublicKey && this.key.equals((openSSLRSAPublicKey = (OpenSSLRSAPublicKey)object).getOpenSSLKey())) {
            return true;
        }
        if (!(object instanceof RSAPublicKey)) {
            return false;
        }
        this.ensureReadParams();
        object = (RSAPublicKey)object;
        if (!this.modulus.equals(object.getModulus()) || !this.publicExponent.equals(object.getPublicExponent())) {
            bl = false;
        }
        return bl;
    }

    @Override
    public String getAlgorithm() {
        return "RSA";
    }

    @Override
    public byte[] getEncoded() {
        return NativeCrypto.EVP_marshal_public_key(this.key.getNativeRef());
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    @Override
    public BigInteger getModulus() {
        this.ensureReadParams();
        return this.modulus;
    }

    @Override
    public OpenSSLKey getOpenSSLKey() {
        return this.key;
    }

    @Override
    public BigInteger getPublicExponent() {
        this.ensureReadParams();
        return this.publicExponent;
    }

    public int hashCode() {
        this.ensureReadParams();
        return this.modulus.hashCode() ^ this.publicExponent.hashCode();
    }

    public String toString() {
        this.ensureReadParams();
        StringBuilder stringBuilder = new StringBuilder("OpenSSLRSAPublicKey{");
        stringBuilder.append("modulus=");
        stringBuilder.append(this.modulus.toString(16));
        stringBuilder.append(',');
        stringBuilder.append("publicExponent=");
        stringBuilder.append(this.publicExponent.toString(16));
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

