/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLECGroupContext;
import com.android.org.conscrypt.OpenSSLECPointContext;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLKeyHolder;
import com.android.org.conscrypt.OpenSSLX509CertificateFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

final class OpenSSLECPublicKey
implements ECPublicKey,
OpenSSLKeyHolder {
    private static final String ALGORITHM = "EC";
    private static final long serialVersionUID = 3215842926808298020L;
    protected transient OpenSSLECGroupContext group;
    protected transient OpenSSLKey key;

    OpenSSLECPublicKey(OpenSSLECGroupContext openSSLECGroupContext, OpenSSLKey openSSLKey) {
        this.group = openSSLECGroupContext;
        this.key = openSSLKey;
    }

    OpenSSLECPublicKey(OpenSSLKey openSSLKey) {
        this.group = new OpenSSLECGroupContext(new NativeRef.EC_GROUP(NativeCrypto.EC_KEY_get1_group(openSSLKey.getNativeRef())));
        this.key = openSSLKey;
    }

    OpenSSLECPublicKey(ECPublicKeySpec object) throws InvalidKeySpecException {
        try {
            this.group = OpenSSLECGroupContext.getInstance(((ECPublicKeySpec)object).getParams());
            OpenSSLECPointContext openSSLECPointContext = OpenSSLECPointContext.getInstance(this.group, ((ECPublicKeySpec)object).getW());
            this.key = object = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_EC_KEY(this.group.getNativeRef(), openSSLECPointContext.getNativeRef(), null));
            return;
        }
        catch (Exception exception) {
            throw new InvalidKeySpecException(exception);
        }
    }

    static OpenSSLKey getInstance(ECPublicKey object) throws InvalidKeyException {
        try {
            OpenSSLECGroupContext openSSLECGroupContext = OpenSSLECGroupContext.getInstance(object.getParams());
            object = OpenSSLECPointContext.getInstance(openSSLECGroupContext, object.getW());
            object = new OpenSSLKey(NativeCrypto.EVP_PKEY_new_EC_KEY(openSSLECGroupContext.getNativeRef(), ((OpenSSLECPointContext)object).getNativeRef(), null));
            return object;
        }
        catch (Exception exception) {
            throw new InvalidKeyException(exception);
        }
    }

    private ECPoint getPublicKey() {
        return new OpenSSLECPointContext(this.group, new NativeRef.EC_POINT(NativeCrypto.EC_KEY_get_public_key(this.key.getNativeRef()))).getECPoint();
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        byte[] arrby = (byte[])((ObjectInputStream)object).readObject();
        try {
            this.key = object = new OpenSSLKey(NativeCrypto.EVP_parse_public_key(arrby));
            this.group = new OpenSSLECGroupContext(new NativeRef.EC_GROUP(NativeCrypto.EC_KEY_get1_group(this.key.getNativeRef())));
            return;
        }
        catch (OpenSSLX509CertificateFactory.ParsingException parsingException) {
            throw new IOException(parsingException);
        }
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
        if (object instanceof OpenSSLECPublicKey) {
            object = (OpenSSLECPublicKey)object;
            return this.key.equals(((OpenSSLECPublicKey)object).key);
        }
        if (!(object instanceof ECPublicKey)) {
            return false;
        }
        Object object2 = (ECPublicKey)object;
        if (!this.getPublicKey().equals(object2.getW())) {
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
        return NativeCrypto.EVP_marshal_public_key(this.key.getNativeRef());
    }

    @Override
    public String getFormat() {
        return "X.509";
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
    public ECPoint getW() {
        return this.getPublicKey();
    }

    public int hashCode() {
        return Arrays.hashCode(NativeCrypto.EVP_marshal_public_key(this.key.getNativeRef()));
    }

    public String toString() {
        return NativeCrypto.EVP_PKEY_print_public(this.key.getNativeRef());
    }
}

