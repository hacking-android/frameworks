/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLBIOInputStream;
import com.android.org.conscrypt.OpenSSLECPrivateKey;
import com.android.org.conscrypt.OpenSSLECPublicKey;
import com.android.org.conscrypt.OpenSSLKeyHolder;
import com.android.org.conscrypt.OpenSSLRSAPrivateKey;
import com.android.org.conscrypt.OpenSSLRSAPublicKey;
import com.android.org.conscrypt.OpenSSLX509CertificateFactory;
import com.android.org.conscrypt.Platform;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.InputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

final class OpenSSLKey {
    private final NativeRef.EVP_PKEY ctx;
    private final boolean wrapped;

    @UnsupportedAppUsage
    OpenSSLKey(long l) {
        this(l, false);
    }

    OpenSSLKey(long l, boolean bl) {
        this.ctx = new NativeRef.EVP_PKEY(l);
        this.wrapped = bl;
    }

    static OpenSSLKey fromECPrivateKeyForTLSStackOnly(PrivateKey privateKey, ECParameterSpec eCParameterSpec) throws InvalidKeyException {
        OpenSSLKey openSSLKey = OpenSSLKey.getOpenSSLKey(privateKey);
        if (openSSLKey != null) {
            return openSSLKey;
        }
        openSSLKey = OpenSSLKey.fromKeyMaterial(privateKey);
        if (openSSLKey != null) {
            return openSSLKey;
        }
        return OpenSSLECPrivateKey.wrapJCAPrivateKeyForTLSStackOnly(privateKey, eCParameterSpec);
    }

    private static OpenSSLKey fromKeyMaterial(PrivateKey object) throws InvalidKeyException {
        if (!"PKCS#8".equals(object.getFormat())) {
            return null;
        }
        if ((object = object.getEncoded()) == null) {
            return null;
        }
        try {
            object = new OpenSSLKey(NativeCrypto.EVP_parse_private_key(object));
            return object;
        }
        catch (OpenSSLX509CertificateFactory.ParsingException parsingException) {
            throw new InvalidKeyException(parsingException);
        }
    }

    @UnsupportedAppUsage
    static OpenSSLKey fromPrivateKey(PrivateKey object) throws InvalidKeyException {
        if (object instanceof OpenSSLKeyHolder) {
            return ((OpenSSLKeyHolder)object).getOpenSSLKey();
        }
        String string = object.getFormat();
        if (string == null) {
            return OpenSSLKey.wrapPrivateKey((PrivateKey)object);
        }
        if ("PKCS#8".equals(object.getFormat())) {
            if (object.getEncoded() != null) {
                try {
                    object = new OpenSSLKey(NativeCrypto.EVP_parse_private_key(object.getEncoded()));
                    return object;
                }
                catch (OpenSSLX509CertificateFactory.ParsingException parsingException) {
                    throw new InvalidKeyException(parsingException);
                }
            }
            throw new InvalidKeyException("Key encoding is null");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown key format ");
        ((StringBuilder)object).append(string);
        throw new InvalidKeyException(((StringBuilder)object).toString());
    }

    static OpenSSLKey fromPrivateKeyForTLSStackOnly(PrivateKey privateKey, PublicKey publicKey) throws InvalidKeyException {
        OpenSSLKey openSSLKey = OpenSSLKey.getOpenSSLKey(privateKey);
        if (openSSLKey != null) {
            return openSSLKey;
        }
        openSSLKey = OpenSSLKey.fromKeyMaterial(privateKey);
        if (openSSLKey != null) {
            return openSSLKey;
        }
        return OpenSSLKey.wrapJCAPrivateKeyForTLSStackOnly(privateKey, publicKey);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static OpenSSLKey fromPrivateKeyPemInputStream(InputStream inputStream) throws InvalidKeyException {
        Throwable throwable2222;
        inputStream = new OpenSSLBIOInputStream(inputStream, true);
        long l = NativeCrypto.PEM_read_bio_PrivateKey(((OpenSSLBIOInputStream)inputStream).getBioContext());
        if (l == 0L) {
            ((OpenSSLBIOInputStream)inputStream).release();
            return null;
        }
        OpenSSLKey openSSLKey = new OpenSSLKey(l);
        ((OpenSSLBIOInputStream)inputStream).release();
        return openSSLKey;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                InvalidKeyException invalidKeyException = new InvalidKeyException(exception);
                throw invalidKeyException;
            }
        }
        ((OpenSSLBIOInputStream)inputStream).release();
        throw throwable2222;
    }

    static OpenSSLKey fromPublicKey(PublicKey object) throws InvalidKeyException {
        if (object instanceof OpenSSLKeyHolder) {
            return ((OpenSSLKeyHolder)object).getOpenSSLKey();
        }
        if ("X.509".equals(object.getFormat())) {
            if (object.getEncoded() != null) {
                try {
                    object = new OpenSSLKey(NativeCrypto.EVP_parse_public_key(object.getEncoded()));
                    return object;
                }
                catch (Exception exception) {
                    throw new InvalidKeyException(exception);
                }
            }
            throw new InvalidKeyException("Key encoding is null");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown key format ");
        stringBuilder.append(object.getFormat());
        throw new InvalidKeyException(stringBuilder.toString());
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static OpenSSLKey fromPublicKeyPemInputStream(InputStream inputStream) throws InvalidKeyException {
        Throwable throwable2222;
        inputStream = new OpenSSLBIOInputStream(inputStream, true);
        long l = NativeCrypto.PEM_read_bio_PUBKEY(((OpenSSLBIOInputStream)inputStream).getBioContext());
        if (l == 0L) {
            ((OpenSSLBIOInputStream)inputStream).release();
            return null;
        }
        OpenSSLKey openSSLKey = new OpenSSLKey(l);
        ((OpenSSLBIOInputStream)inputStream).release();
        return openSSLKey;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                InvalidKeyException invalidKeyException = new InvalidKeyException(exception);
                throw invalidKeyException;
            }
        }
        ((OpenSSLBIOInputStream)inputStream).release();
        throw throwable2222;
    }

    private static OpenSSLKey getOpenSSLKey(PrivateKey privateKey) {
        if (privateKey instanceof OpenSSLKeyHolder) {
            return ((OpenSSLKeyHolder)((Object)privateKey)).getOpenSSLKey();
        }
        if ("RSA".equals(privateKey.getAlgorithm())) {
            return Platform.wrapRsaKey(privateKey);
        }
        return null;
    }

    static PrivateKey getPrivateKey(PKCS8EncodedKeySpec object, int n) throws InvalidKeySpecException {
        try {
            object = new OpenSSLKey(NativeCrypto.EVP_parse_private_key(((PKCS8EncodedKeySpec)object).getEncoded()));
        }
        catch (Exception exception) {
            throw new InvalidKeySpecException(exception);
        }
        if (NativeCrypto.EVP_PKEY_type(((OpenSSLKey)object).getNativeRef()) == n) {
            try {
                object = ((OpenSSLKey)object).getPrivateKey();
                return object;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new InvalidKeySpecException(noSuchAlgorithmException);
            }
        }
        throw new InvalidKeySpecException("Unexpected key type");
    }

    static PublicKey getPublicKey(X509EncodedKeySpec object, int n) throws InvalidKeySpecException {
        try {
            object = new OpenSSLKey(NativeCrypto.EVP_parse_public_key(((X509EncodedKeySpec)object).getEncoded()));
        }
        catch (Exception exception) {
            throw new InvalidKeySpecException(exception);
        }
        if (NativeCrypto.EVP_PKEY_type(((OpenSSLKey)object).getNativeRef()) == n) {
            try {
                object = ((OpenSSLKey)object).getPublicKey();
                return object;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new InvalidKeySpecException(noSuchAlgorithmException);
            }
        }
        throw new InvalidKeySpecException("Unexpected key type");
    }

    private static OpenSSLKey wrapJCAPrivateKeyForTLSStackOnly(PrivateKey serializable, PublicKey publicKey) throws InvalidKeyException {
        String string = serializable.getAlgorithm();
        if ("RSA".equals(string)) {
            return OpenSSLRSAPrivateKey.wrapJCAPrivateKeyForTLSStackOnly((PrivateKey)serializable, publicKey);
        }
        if ("EC".equals(string)) {
            return OpenSSLECPrivateKey.wrapJCAPrivateKeyForTLSStackOnly((PrivateKey)serializable, publicKey);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unsupported key algorithm: ");
        ((StringBuilder)serializable).append(string);
        throw new InvalidKeyException(((StringBuilder)serializable).toString());
    }

    private static OpenSSLKey wrapPrivateKey(PrivateKey privateKey) throws InvalidKeyException {
        if (privateKey instanceof RSAPrivateKey) {
            return OpenSSLRSAPrivateKey.wrapPlatformKey((RSAPrivateKey)privateKey);
        }
        if (privateKey instanceof ECPrivateKey) {
            return OpenSSLECPrivateKey.wrapPlatformKey((ECPrivateKey)privateKey);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown key type: ");
        stringBuilder.append(privateKey.toString());
        throw new InvalidKeyException(stringBuilder.toString());
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof OpenSSLKey)) {
            return false;
        }
        if (this.ctx.equals(((OpenSSLKey)(object = (OpenSSLKey)object)).getNativeRef())) {
            return true;
        }
        if (NativeCrypto.EVP_PKEY_cmp(this.ctx, ((OpenSSLKey)object).getNativeRef()) != 1) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    NativeRef.EVP_PKEY getNativeRef() {
        return this.ctx;
    }

    PrivateKey getPrivateKey() throws NoSuchAlgorithmException {
        int n = NativeCrypto.EVP_PKEY_type(this.ctx);
        if (n != 6) {
            if (n == 408) {
                return new OpenSSLECPrivateKey(this);
            }
            throw new NoSuchAlgorithmException("unknown PKEY type");
        }
        return new OpenSSLRSAPrivateKey(this);
    }

    @UnsupportedAppUsage
    PublicKey getPublicKey() throws NoSuchAlgorithmException {
        int n = NativeCrypto.EVP_PKEY_type(this.ctx);
        if (n != 6) {
            if (n == 408) {
                return new OpenSSLECPublicKey(this);
            }
            throw new NoSuchAlgorithmException("unknown PKEY type");
        }
        return new OpenSSLRSAPublicKey(this);
    }

    public int hashCode() {
        return this.ctx.hashCode();
    }

    boolean isWrapped() {
        return this.wrapped;
    }
}

