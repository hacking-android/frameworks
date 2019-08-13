/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLECGroupContext;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLKeyHolder;
import com.android.org.conscrypt.OpenSSLX509CertificateFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECKey;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

final class OpenSSLECPrivateKey
implements ECPrivateKey,
OpenSSLKeyHolder {
    private static final String ALGORITHM = "EC";
    private static final long serialVersionUID = -4036633595001083922L;
    protected transient OpenSSLECGroupContext group;
    protected transient OpenSSLKey key;

    OpenSSLECPrivateKey(OpenSSLECGroupContext openSSLECGroupContext, OpenSSLKey openSSLKey) {
        this.group = openSSLECGroupContext;
        this.key = openSSLKey;
    }

    OpenSSLECPrivateKey(OpenSSLKey openSSLKey) {
        this.group = new OpenSSLECGroupContext(new NativeRef.EC_GROUP(NativeCrypto.EC_KEY_get1_group(openSSLKey.getNativeRef())));
        this.key = openSSLKey;
    }

    OpenSSLECPrivateKey(ECPrivateKeySpec object) throws InvalidKeySpecException {
        try {
            this.group = OpenSSLECGroupContext.getInstance(((ECPrivateKeySpec)object).getParams());
            BigInteger bigInteger = ((ECPrivateKeySpec)object).getS();
            this.key = object = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_EC_KEY(this.group.getNativeRef(), null, bigInteger.toByteArray()));
            return;
        }
        catch (Exception exception) {
            throw new InvalidKeySpecException(exception);
        }
    }

    static OpenSSLKey getInstance(ECPrivateKey object) throws InvalidKeyException {
        try {
            OpenSSLECGroupContext openSSLECGroupContext = OpenSSLECGroupContext.getInstance(object.getParams());
            if (object.getFormat() == null) {
                return OpenSSLECPrivateKey.wrapPlatformKey((ECPrivateKey)object, openSSLECGroupContext);
            }
            object = object.getS();
            object = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_EC_KEY(openSSLECGroupContext.getNativeRef(), null, ((BigInteger)object).toByteArray()));
            return object;
        }
        catch (Exception exception) {
            throw new InvalidKeyException(exception);
        }
    }

    private BigInteger getPrivateKey() {
        return new BigInteger(NativeCrypto.EC_KEY_get_private_key(this.key.getNativeRef()));
    }

    private void readObject(ObjectInputStream arrby) throws IOException, ClassNotFoundException {
        arrby.defaultReadObject();
        arrby = (byte[])arrby.readObject();
        try {
            OpenSSLKey openSSLKey;
            this.key = openSSLKey = new OpenSSLKey(NativeCrypto.EVP_parse_private_key(arrby));
            this.group = new OpenSSLECGroupContext(new NativeRef.EC_GROUP(NativeCrypto.EC_KEY_get1_group(this.key.getNativeRef())));
            return;
        }
        catch (OpenSSLX509CertificateFactory.ParsingException parsingException) {
            throw new IOException(parsingException);
        }
    }

    static OpenSSLKey wrapJCAPrivateKeyForTLSStackOnly(PrivateKey privateKey, PublicKey publicKey) throws InvalidKeyException {
        Object object = null;
        if (privateKey instanceof ECKey) {
            object = ((ECKey)((Object)privateKey)).getParams();
        } else if (publicKey instanceof ECKey) {
            object = ((ECKey)((Object)publicKey)).getParams();
        }
        if (object != null) {
            return OpenSSLECPrivateKey.wrapJCAPrivateKeyForTLSStackOnly(privateKey, (ECParameterSpec)object);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("EC parameters not available. Private: ");
        ((StringBuilder)object).append(privateKey);
        ((StringBuilder)object).append(", public: ");
        ((StringBuilder)object).append(publicKey);
        throw new InvalidKeyException(((StringBuilder)object).toString());
    }

    static OpenSSLKey wrapJCAPrivateKeyForTLSStackOnly(PrivateKey privateKey, ECParameterSpec object) throws InvalidKeyException {
        ECParameterSpec eCParameterSpec = object;
        if (object == null) {
            eCParameterSpec = object;
            if (privateKey instanceof ECKey) {
                eCParameterSpec = ((ECKey)((Object)privateKey)).getParams();
            }
        }
        if (eCParameterSpec != null) {
            try {
                object = OpenSSLECGroupContext.getInstance(eCParameterSpec);
                return new OpenSSLKey(NativeCrypto.getECPrivateKeyWrapper(privateKey, ((OpenSSLECGroupContext)object).getNativeRef()), true);
            }
            catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid EC parameters: ");
                stringBuilder.append(eCParameterSpec);
                throw new InvalidKeyException(stringBuilder.toString());
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("EC parameters not available: ");
        ((StringBuilder)object).append(privateKey);
        throw new InvalidKeyException(((StringBuilder)object).toString());
    }

    static OpenSSLKey wrapPlatformKey(ECPrivateKey eCPrivateKey) throws InvalidKeyException {
        OpenSSLECGroupContext openSSLECGroupContext;
        try {
            openSSLECGroupContext = OpenSSLECGroupContext.getInstance(eCPrivateKey.getParams());
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new InvalidKeyException("Unknown group parameters", invalidAlgorithmParameterException);
        }
        return OpenSSLECPrivateKey.wrapPlatformKey(eCPrivateKey, openSSLECGroupContext);
    }

    private static OpenSSLKey wrapPlatformKey(ECPrivateKey eCPrivateKey, OpenSSLECGroupContext openSSLECGroupContext) throws InvalidKeyException {
        return new OpenSSLKey(NativeCrypto.getECPrivateKeyWrapper(eCPrivateKey, openSSLECGroupContext.getNativeRef()), true);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.getEncoded());
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof OpenSSLECPrivateKey) {
            object = (OpenSSLECPrivateKey)object;
            return this.key.equals(((OpenSSLECPrivateKey)object).key);
        }
        if (!(object instanceof ECPrivateKey)) {
            return false;
        }
        Object object2 = (ECPrivateKey)object;
        if (!this.getPrivateKey().equals(object2.getS())) {
            return false;
        }
        object = this.getParams();
        object2 = object2.getParams();
        if (!(((ECParameterSpec)object).getCurve().equals(((ECParameterSpec)object2).getCurve()) && ((ECParameterSpec)object).getGenerator().equals(((ECParameterSpec)object2).getGenerator()) && ((ECParameterSpec)object).getOrder().equals(((ECParameterSpec)object2).getOrder()) && ((ECParameterSpec)object).getCofactor() == ((ECParameterSpec)object2).getCofactor())) {
            bl = false;
        }
        return bl;
    }

    @Override
    public String getAlgorithm() {
        return ALGORITHM;
    }

    @Override
    public byte[] getEncoded() {
        return NativeCrypto.EVP_marshal_private_key(this.key.getNativeRef());
    }

    @Override
    public String getFormat() {
        return "PKCS#8";
    }

    @Override
    public OpenSSLKey getOpenSSLKey() {
        return this.key;
    }

    @Override
    public ECParameterSpec getParams() {
        return this.group.getECParameterSpec();
    }

    @Override
    public BigInteger getS() {
        return this.getPrivateKey();
    }

    public int hashCode() {
        return Arrays.hashCode(NativeCrypto.EVP_marshal_private_key(this.key.getNativeRef()));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("OpenSSLECPrivateKey{");
        stringBuilder.append("params={");
        stringBuilder.append(NativeCrypto.EVP_PKEY_print_params(this.key.getNativeRef()));
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }
}

